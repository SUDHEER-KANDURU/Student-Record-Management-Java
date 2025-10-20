import java.io.*;
import java.util.*;

class Student implements Serializable {
    int roll;
    String name;
    int age;
    float marks;

    public Student(int roll, String name, int age, float marks) {
        this.roll = roll;
        this.name = name;
        this.age = age;
        this.marks = marks;
    }

    public String toString() {
        return String.format("Roll: %d | Name: %s | Age: %d | Marks: %.2f", roll, name, age, marks);
    }
}

public class StudentRecordManagement {
    static final String FILE_NAME = "students.dat";
    static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        loadData();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Student Record Management System ===");
            System.out.println("1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> addStudent(sc);
                case 2 -> displayStudents();
                case 3 -> searchStudent(sc);
                case 4 -> updateStudent(sc);
                case 5 -> deleteStudent(sc);
                case 6 -> {
                    saveData();
                    System.out.println("Exiting... Data saved successfully!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // Add new student
    static void addStudent(Scanner sc) {
        System.out.print("Enter Roll Number: ");
        int roll = sc.nextInt();
        sc.nextLine(); // consume newline
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        System.out.print("Enter Marks: ");
        float marks = sc.nextFloat();

        students.add(new Student(roll, name, age, marks));
        saveData();
        System.out.println("Student added successfully!");
    }

    // Display all students
    static void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No records found!");
            return;
        }
        System.out.println("\n--- Student Records ---");
        for (Student s : students) {
            System.out.println(s);
        }
    }

    // Search by roll
    static void searchStudent(Scanner sc) {
        System.out.print("Enter Roll Number to Search: ");
        int roll = sc.nextInt();
        for (Student s : students) {
            if (s.roll == roll) {
                System.out.println("Record Found: " + s);
                return;
            }
        }
        System.out.println("Record not found!");
    }

    // Update details
    static void updateStudent(Scanner sc) {
        System.out.print("Enter Roll Number to Update: ");
        int roll = sc.nextInt();
        sc.nextLine(); // consume newline
        for (Student s : students) {
            if (s.roll == roll) {
                System.out.print("Enter new Name: ");
                s.name = sc.nextLine();
                System.out.print("Enter new Age: ");
                s.age = sc.nextInt();
                System.out.print("Enter new Marks: ");
                s.marks = sc.nextFloat();
                saveData();
                System.out.println("Record updated successfully!");
                return;
            }
        }
        System.out.println("Record not found!");
    }

    // Delete student
    static void deleteStudent(Scanner sc) {
        System.out.print("Enter Roll Number to Delete: ");
        int roll = sc.nextInt();
        Iterator<Student> itr = students.iterator();
        while (itr.hasNext()) {
            Student s = itr.next();
            if (s.roll == roll) {
                itr.remove();
                saveData();
                System.out.println("Record deleted successfully!");
                return;
            }
        }
        System.out.println("Record not found!");
    }

    // Load data from file
    @SuppressWarnings("unchecked")
    static void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (ArrayList<Student>) ois.readObject();
        } catch (Exception e) {
            // File may not exist on first run — ignore
        }
    }

    // Save data to file
    static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving data!");
        }
    }
}
