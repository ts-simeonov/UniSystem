import java.io.*;
import java.net.*;
import java.util.*;
import java.util.stream.Collectors;

public class Server {
    public static final String FILENAME = "users.bin";
    private Object lock;
    ServerSocket server;

    public void saveUsers(List<User> list) {
        try(ObjectOutputStream fileOut = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            fileOut.writeObject(list);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> loadUsers() {
        try(ObjectInputStream fileIn = new ObjectInputStream(new FileInputStream(FILENAME))) {
            return (List<User>) fileIn.readObject();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void init() {
        if (new File(FILENAME).exists()) return;
        ArrayList<User> list = new ArrayList<>();
        list.add(new Admin("Boss", "1234")); // Not the most secure password, I'll admit
        saveUsers(list);
    }

    public Server() {
        lock = new Object();
        init();
    }

    public void start() {
        try {
            server = new ServerSocket(8080, 50, InetAddress.getLocalHost());
            while (true) {
                Socket client = server.accept();
                Thread clientThread = new Thread(() ->
                {
                    Scanner in = null;
                    PrintWriter out = null;
                    try {
                        in = new Scanner(client.getInputStream());
                        out = new PrintWriter(client.getOutputStream());
                        serverMenu(in, out);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    finally {
                        if(in != null) {
                            in.close();
                        }
                        if(out != null) {
                            out.close();
                        }
                    }
                });
                clientThread.start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public User login(String username, String password) {
        synchronized(lock) {
            List<User> list = loadUsers();
            for (User user : list) {
                if(Objects.equals(username, user.getUsername()) && Objects.equals(password, user.getPassword())) {
                    return user;
                }
            }
            return null;
        }
    }

    public void register(UserType usertype, String username, String password) throws Exception {
        User user = UserBuilder.createUser(usertype, username, password);
        if(user == null) {
            throw new Exception("You've entered a nonexistent user type!");
        }
        synchronized(lock) {
            List<User> users = loadUsers();
            users.add(user);
            saveUsers(users);
        }
    }

    public void adminMenu(Scanner in, PrintWriter out) {
        out.println("Logged in as admin.\nDo you want to register a new user? Y/N");
        if (!(in.nextLine()).equalsIgnoreCase("Y")) {
            out.println("Goodbye");
            return;
        }
        try {
            out.println("What type of user: ");
            UserType type = UserType.valueOf(in.nextLine());
            out.println("Username: ");
            String username = in.nextLine();
            out.println("Password: ");
            String password = in.nextLine();

            register(type, username, password);
            out.println("Successfully added the user!");
        }
        catch (IllegalArgumentException e) {
            out.println("Invalid user type!");
        }
        catch (Exception e) {
            out.println(e.getMessage());
        }

    }

    public void studentMenu(Scanner in, PrintWriter out, Student student) {
        out.println("Welcome, student!\nHere are your grades:");
        List<Grade> sortedGrades = student.getGrades()
                .stream()
                .sorted(Comparator.comparingInt(Grade::getSemester).thenComparing(Grade::getSubject))
                .collect(Collectors.toList());
        out.println(sortedGrades);
    }

    public void teacherMenu(Scanner in, PrintWriter out) {
        out.println("Welcome, teacher!");
        out.println("Do you want to add a grade? Y/N");
        if (!in.nextLine().equalsIgnoreCase("Y")) {
            out.println("Goodbye");
            return;
        }
        try {
            out.println("Enter Faculty Number: ");
            String facNum = in.nextLine();
            out.println("Enter Subject: ");
            String subject = in.nextLine();
            out.println("Enter Semester: ");
            int semester = in.nextInt();
            out.println("Enter Mark: ");
            int mark = in.nextInt();

            Grade newGrade = new Grade(subject, semester, mark);

            synchronized (lock) {
                List<User> list = loadUsers();
                for (User user : list) {
                    if (user.getUsername().equals(facNum) && user.getUserType() == UserType.STUDENT) {
                        Student student = (Student) user;
                        student.getGrades().add(newGrade);
                        saveUsers(list);
                        return;
                    }
                }
                out.println("Student was not found!");
            }
        }
        catch (InputMismatchException e) {
            out.println("Enter an integer!");
        }
        catch (Exception e) {
            out.println("An error occurred!");
            e.printStackTrace();
        }
    }

    public void serverMenu(Scanner in, PrintWriter out) {
        while (true) {
            out.println("Hello, please log in:\nUsername:");
            String username = in.nextLine();

            out.println("Password: ");
            String password = in.nextLine();

            User user = login(username, password);

            if (user == null) {
                out.println("Sorry, wrong username or password!");
                continue;
            }
            switch(user.getUserType()) {
                case STUDENT: {
                    studentMenu(in, out, (Student) user);
                    break;
                }
                case ADMIN: {
                    adminMenu(in, out);
                    break;
                }
                case TEACHER: {
                    teacherMenu(in, out);
                    break;
                }
            }
        }
    }
}
