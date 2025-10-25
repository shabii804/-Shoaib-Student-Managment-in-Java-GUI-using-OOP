import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

// Student classa
class Student {
    private String studentId;
    private String name;
    private String classNumber;

    public Student(String studentId, String name, String classNumber) {
        this.studentId = studentId;
        this.name = name;
        this.classNumber = classNumber;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }
}

// StudentManager to manage students array
class StudentManager {
    private Student[] students;
    private int count;

    public StudentManager(int capacity) {
        students = new Student[capacity];
        count = 0;
    }

    public void addStudent(Student s) {
        if (count < students.length) {
            students[count++] = s;
        } else {
            JOptionPane.showMessageDialog(null, "Student list full!");
        }
    }

    public int getStudentCount() {
        return count;
    }

    public Student getStudentAt(int index) {
        if (index >= 0 && index < count) {
            return students[index];
        }
        return null;
    }

    public Student searchStudentById(String id) {
        for (int i = 0; i < count; i++) {
            if (students[i].getStudentId().equals(id)) {
                return students[i];
            }
        }
        return null;
    }

    public boolean deleteStudentById(String id) {
        for (int i = 0; i < count; i++) {
            if (students[i].getStudentId().equals(id)) {
                for (int j = i; j < count - 1; j++) {
                    students[j] = students[j + 1];
                }
                students[count - 1] = null;
                count--;
                return true;
            }
        }
        return false;
    }
}

// Admin login form
class AdminLogin extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;

    public AdminLogin() {
        setTitle("Admin Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 30, 80, 25);
        add(userLabel);

        userField = new JTextField();
        userField.setBounds(120, 30, 180, 25);
        add(userField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 70, 80, 25);
        add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(120, 70, 180, 25);
        add(passField);

        loginButton = new JButton("Login");
        loginButton.setBounds(120, 110, 100, 30);
        add(loginButton);

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            // For simplicity, hardcoded admin credentials:
            if (username.equals("admin") && password.equals("1234")) {
                StudentManager manager = new StudentManager(100);
                new MainMenu(manager).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password");
            }
        });
    }
}

// Main menu form
class MainMenu extends JFrame {
    private JButton addButton, showButton, updateButton, deleteButton, exitButton;
    private StudentManager studentManager;

    public MainMenu(StudentManager manager) {
        this.studentManager = manager;

        setTitle("School Management System - Main Menu");
        setSize(350, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        addButton = new JButton("Add Student");
        addButton.setBounds(100, 30, 150, 30);
        add(addButton);

        showButton = new JButton("Show Students");
        showButton.setBounds(100, 75, 150, 30);
        add(showButton);

        updateButton = new JButton("Update Student");
        updateButton.setBounds(100, 120, 150, 30);
        add(updateButton);

        deleteButton = new JButton("Delete Student");
        deleteButton.setBounds(100, 165, 150, 30);
        add(deleteButton);

        exitButton = new JButton("Exit");
        exitButton.setBounds(100, 210, 150, 30);
        add(exitButton);

        addButton.addActionListener(e -> {
            AddStudentForm addForm = new AddStudentForm(studentManager);
            addForm.setVisible(true);
        });

        showButton.addActionListener(e -> {
            ShowStudentsForm showForm = new ShowStudentsForm(studentManager);
            showForm.setVisible(true);
        });

        updateButton.addActionListener(e -> {
            UpdateStudentForm updateForm = new UpdateStudentForm(studentManager);
            updateForm.setVisible(true);
        });

        deleteButton.addActionListener(e -> {
            DeleteStudentForm deleteForm = new DeleteStudentForm(studentManager);
            deleteForm.setVisible(true);
        });

        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
}

// Add Student Form
class AddStudentForm extends JFrame {
    private JTextField idField, nameField, classField;
    private JButton addButton, cancelButton;
    private StudentManager studentManager;

    public AddStudentForm(StudentManager manager) {
        this.studentManager = manager;

        setTitle("Add Student");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel idLabel = new JLabel("Student ID:");
        idLabel.setBounds(30, 30, 100, 25);
        add(idLabel);

        idField = new JTextField();
        idField.setBounds(140, 30, 150, 25);
        add(idField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 70, 100, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(140, 70, 150, 25);
        add(nameField);

        JLabel classLabel = new JLabel("Class Number:");
        classLabel.setBounds(30, 110, 100, 25);
        add(classLabel);

        classField = new JTextField();
        classField.setBounds(140, 110, 150, 25);
        add(classField);

        addButton = new JButton("Add");
        addButton.setBounds(50, 160, 100, 30);
        add(addButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(180, 160, 100, 30);
        add(cancelButton);

        addButton.addActionListener(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String classNum = classField.getText().trim();

            if (id.isEmpty() || name.isEmpty() || classNum.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields");
                return;
            }

            if (studentManager.searchStudentById(id) != null) {
                JOptionPane.showMessageDialog(this, "Student ID already exists");
                return;
            }

            Student student = new Student(id, name, classNum);
            studentManager.addStudent(student);

            JOptionPane.showMessageDialog(this, "Student added successfully!");

            idField.setText("");
            nameField.setText("");
            classField.setText("");
        });

        cancelButton.addActionListener(e -> dispose());
    }
}

// Show Students Form
class ShowStudentsForm extends JFrame {
    private JTable studentTable;
    private StudentManager studentManager;

    public ShowStudentsForm(StudentManager manager) {
        this.studentManager = manager;

        setTitle("Show Students");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"Student ID", "Name", "Class Number"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (int i = 0; i < studentManager.getStudentCount(); i++) {
            Student s = studentManager.getStudentAt(i);
            String[] row = {s.getStudentId(), s.getName(), s.getClassNumber()};
            model.addRow(row);
        }

        studentTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        add(scrollPane, BorderLayout.CENTER);
    }
}
 
// Update Student Form
class UpdateStudentForm extends JFrame {
    private JTextField idField, nameField, classField;
    private JButton searchButton, updateButton, cancelButton;
    private StudentManager studentManager;
    private Student currentStudent;

    public UpdateStudentForm(StudentManager manager) {
        this.studentManager = manager;

        setTitle("Update Student");
        setSize(350, 270);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel idLabel = new JLabel("Enter Student ID:");
        idLabel.setBounds(30, 30, 120, 25);
        add(idLabel);

        idField = new JTextField();
        idField.setBounds(150, 30, 150, 25);
        add(idField);

        searchButton = new JButton("Search");
        searchButton.setBounds(120, 65, 100, 25);
        add(searchButton);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 110, 100, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 110, 150, 25);
        add(nameField);

        JLabel classLabel = new JLabel("Class Number:");
        classLabel.setBounds(30, 150, 100, 25);
        add(classLabel);

        classField = new JTextField();
        classField.setBounds(150, 150, 150, 25);
        add(classField);

        updateButton = new JButton("Update");
        updateButton.setBounds(50, 200, 100, 30);
        add(updateButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(180, 200, 100, 30);
        add(cancelButton);

        nameField.setEnabled(false);
        classField.setEnabled(false);
        updateButton.setEnabled(false);

        searchButton.addActionListener(e -> {
            String id = idField.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter Student ID to search");
                return;
            }

            currentStudent = studentManager.searchStudentById(id);
            if (currentStudent == null) {
                JOptionPane.showMessageDialog(this, "Student not found");
                nameField.setText("");
                classField.setText("");
                nameField.setEnabled(false);
                classField.setEnabled(false);
                updateButton.setEnabled(false);
            } else {
                nameField.setText(currentStudent.getName());
                classField.setText(currentStudent.getClassNumber());
                nameField.setEnabled(true);
                classField.setEnabled(true);
                updateButton.setEnabled(true);
            }
        });

        updateButton.addActionListener(e -> {
            String newName = nameField.getText().trim();
            String newClass = classField.getText().trim();

            if (newName.isEmpty() || newClass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields");
                return;
            }

            currentStudent.setName(newName);
            currentStudent.setClassNumber(newClass);
            JOptionPane.showMessageDialog(this, "Student updated successfully");
            dispose();
        });

        cancelButton.addActionListener(e -> dispose());
    }
}

// Delete Student Form
class DeleteStudentForm extends JFrame {
    private JTextField idField;
    private JButton deleteButton, cancelButton;
    private StudentManager studentManager;

    public DeleteStudentForm(StudentManager manager) {
        this.studentManager = manager;

        setTitle("Delete Student");
        setSize(350, 180);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel idLabel = new JLabel("Enter Student ID to Delete:");
        idLabel.setBounds(30, 30, 180, 25);
        add(idLabel);

        idField = new JTextField();
        idField.setBounds(210, 30, 100, 25);
        add(idField);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(50, 80, 100, 30);
        add(deleteButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(180, 80, 100, 30);
        add(cancelButton);

        deleteButton.addActionListener(e -> {
            String id = idField.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Student ID");
                return;
            }

            boolean deleted = studentManager.deleteStudentById(id);
            if (deleted) {
                JOptionPane.showMessageDialog(this, "Student deleted successfully");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Student ID not found");
            }
        });

        cancelButton.addActionListener(e -> dispose());
    }
}

// Main class to start the program
public class SchoolManagementSystem {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdminLogin().setVisible(true);
        });
    }
}
