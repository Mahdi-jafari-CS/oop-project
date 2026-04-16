package ui;

import exceptions.CreditLimitException;
import exceptions.FailLimitException;
import exceptions.HIndexTooLowException;
import exceptions.NotResearcherException;
import model.comparators.ResearchPaperComparators;
import model.courses.Course;
import model.courses.Lesson;
import model.courses.Mark;
import model.research.ResearchPaper;
import model.research.ResearchProject;
import model.research.Researcher;
import model.users.Admin;
import model.users.Employee;
import model.users.Manager;
import model.users.Student;
import model.users.Teacher;
import model.users.User;
import storage.DataStorage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Console-based user interface for the Research University Information System.
 */
public class ConsoleUI {
    private DataStorage dataStorage;
    private User currentUser;
    private java.util.Scanner scanner;

    public ConsoleUI() {
        this.dataStorage = DataStorage.getInstance();
        this.scanner = new java.util.Scanner(System.in);
        this.currentUser = null;
    }

    /**
     * Main entry point for the console UI.
     */
    public void run() {
        System.out.println("=========================================");
        System.out.println("  RESEARCH UNIVERSITY INFORMATION SYSTEM");
        System.out.println("=========================================");

        System.out.println("Initializing sample data...");
        dataStorage.initializeSampleData();
        System.out.println();

        while (true) {
            if (currentUser == null) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private void showLoginMenu() {
        System.out.println("\n=== LOGIN ===");
        System.out.println("1. Login");
        System.out.println("2. View System Statistics");
        System.out.println("3. Exit");
        System.out.print("Select option: ");

        int choice = getIntInput(1, 3);

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                showStatistics();
                break;
            case 3:
                System.out.println("Exiting system. Goodbye!");
                System.exit(0);
                break;
            default:
                break;
        }
    }

    private void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = dataStorage.getUser(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            System.out.println("Login successful! Welcome, " + user.getFullName() + "!");
        } else {
            System.out.println("Invalid username or password!");
        }
    }

    private void showStatistics() {
        System.out.println("\n" + dataStorage.getStatistics());
        waitForEnter();
    }

    private void showMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("Logged in as: " + currentUser.getFullName() + " (" + currentUser.getUserType() + ")");
        System.out.println("1. View Profile");
        System.out.println("2. Logout");

        if (currentUser instanceof Admin) {
            showAdminMenu();
        } else if (currentUser instanceof Teacher) {
            showTeacherMenu();
        } else if (currentUser instanceof Student) {
            showStudentMenu();
        } else if (currentUser instanceof Manager) {
            showManagerMenu();
        }
    }

    private void showAdminMenu() {
        System.out.println("3. Manage Users");
        System.out.println("4. View User Logs");
        System.out.println("5. System Statistics");
        System.out.println("6. Save Data to File");
        System.out.println("7. Load Data from File");

        int choice = getIntInput(1, 7);
        switch (choice) {
            case 1:
                viewProfile();
                break;
            case 2:
                logout();
                break;
            case 3:
                manageUsers();
                break;
            case 4:
                viewUserLogs();
                break;
            case 5:
                showStatistics();
                break;
            case 6:
                saveData();
                break;
            case 7:
                loadData();
                break;
            default:
                break;
        }
    }

    private void showTeacherMenu() {
        System.out.println("3. View Courses");
        System.out.println("4. View Students");
        System.out.println("5. Put Marks");
        System.out.println("6. Manage Course");
        System.out.println("7. Send Message to Employee");
        System.out.println("8. Send Complaint/Request");
        System.out.println("9. View Inbox");
        System.out.println("10. Research Features");

        int choice = getIntInput(1, 10);
        switch (choice) {
            case 1:
                viewProfile();
                break;
            case 2:
                logout();
                break;
            case 3:
                viewTeacherCourses();
                break;
            case 4:
                viewTeacherStudents();
                break;
            case 5:
                putMarks();
                break;
            case 6:
                manageTeacherCourse();
                break;
            case 7:
                sendMessageToEmployee();
                break;
            case 8:
                sendComplaintOrRequest();
                break;
            case 9:
                viewEmployeeInbox();
                break;
            case 10:
                showResearchMenu();
                break;
            default:
                break;
        }
    }

    private void showStudentMenu() {
        System.out.println("3. View Courses");
        System.out.println("4. Register for Course");
        System.out.println("5. View Marks");
        System.out.println("6. View Transcript");
        System.out.println("7. View Teacher Info for Course");
        System.out.println("8. Rate Teacher");
        System.out.println("9. Research Features");

        int choice = getIntInput(1, 9);
        switch (choice) {
            case 1:
                viewProfile();
                break;
            case 2:
                logout();
                break;
            case 3:
                viewStudentCourses();
                break;
            case 4:
                registerForCourse();
                break;
            case 5:
                viewStudentMarks();
                break;
            case 6:
                viewTranscript();
                break;
            case 7:
                viewTeacherInfoForCourse();
                break;
            case 8:
                rateTeacher();
                break;
            case 9:
                showResearchMenu();
                break;
            default:
                break;
        }
    }

    private void showManagerMenu() {
        System.out.println("3. Approve Registrations");
        System.out.println("4. Assign Teachers to Courses");
        System.out.println("5. Generate Reports");
        System.out.println("6. Manage News");
        System.out.println("7. View Employee Requests");
        System.out.println("8. View Student/Teacher Info");

        int choice = getIntInput(1, 8);
        switch (choice) {
            case 1:
                viewProfile();
                break;
            case 2:
                logout();
                break;
            case 3:
                approveRegistrations();
                break;
            case 4:
                assignTeachersToCourses();
                break;
            case 5:
                generateReports();
                break;
            case 6:
                manageNews();
                break;
            case 7:
                viewEmployeeRequests();
                break;
            case 8:
                viewStudentTeacherInfo();
                break;
            default:
                break;
        }
    }

    private void showResearchMenu() {
        System.out.println("\n=== RESEARCH MENU ===");
        System.out.println("1. View Research Papers");
        System.out.println("2. Add Research Paper");
        System.out.println("3. View Research Projects");
        System.out.println("4. Join Research Project");
        System.out.println("5. Print Papers with Comparator");
        System.out.println("6. View Top Cited Researcher");
        System.out.println("7. Back to Main Menu");

        int choice = getIntInput(1, 7);
        switch (choice) {
            case 1:
                viewResearchPapers();
                break;
            case 2:
                addResearchPaper();
                break;
            case 3:
                viewResearchProjects();
                break;
            case 4:
                joinResearchProject();
                break;
            case 5:
                printPapersWithComparator();
                break;
            case 6:
                viewTopCitedResearcher();
                break;
            case 7:
                break;
            default:
                break;
        }
    }

    // ========== COMMON METHODS ==========

    private void viewProfile() {
        System.out.println("\n=== YOUR PROFILE ===");
        System.out.println(currentUser);
        waitForEnter();
    }

    private void logout() {
        System.out.println("Logging out... Goodbye, " + currentUser.getFullName() + "!");
        currentUser = null;
    }

    // ========== ADMIN METHODS ==========

    private void manageUsers() {
        System.out.println("\n=== MANAGE USERS ===");
        System.out.println("1. View All Users");
        System.out.println("2. Add New User");
        System.out.println("3. Remove User");
        System.out.println("4. Update User");
        System.out.println("5. Back");

        int choice = getIntInput(1, 5);
        switch (choice) {
            case 1:
                viewAllUsers();
                break;
            case 2:
                addNewUser();
                break;
            case 3:
                removeUser();
                break;
            case 4:
                updateUser();
                break;
            case 5:
                break;
            default:
                break;
        }
    }

    private void viewAllUsers() {
        System.out.println("\n=== ALL USERS ===");
        for (User user : dataStorage.getAllUsers()) {
            System.out.println(user);
        }
        waitForEnter();
    }

    private void addNewUser() {
        System.out.println("\n=== ADD USER ===");
        System.out.println("Select role:");
        System.out.println("1. Student");
        System.out.println("2. Teacher");
        System.out.println("3. Manager");
        System.out.println("4. Admin");

        int role = getIntInput(1, 4);

        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        if (dataStorage.getUser(username) != null) {
            System.out.println("Username already exists.");
            waitForEnter();
            return;
        }

        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("First name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        User created = null;
        switch (role) {
            case 1:
                System.out.print("Student ID: ");
                String studentId = scanner.nextLine();
                System.out.print("Major: ");
                String major = scanner.nextLine();
                System.out.println("Year (1-4):");
                int yearNum = getIntInput(1, 4);
                model.enums.StudentYear year = model.enums.StudentYear.values()[yearNum - 1];
                created = new Student(username, password, firstName, lastName, email, studentId, year, major);
                break;
            case 2:
                System.out.print("Employee ID: ");
                String teacherEmpId = scanner.nextLine();
                System.out.print("Salary: ");
                double teacherSalary = getDoubleInput(0, 1_000_000);
                System.out.print("Department: ");
                String teacherDepartment = scanner.nextLine();
                System.out.println("Title: 1.TUTOR 2.LECTOR 3.SENIOR_LECTOR 4.PROFESSOR");
                int titleIdx = getIntInput(1, 4);
                model.enums.Title title = model.enums.Title.values()[titleIdx - 1];
                created = new Teacher(username, password, firstName, lastName, email,
                        teacherEmpId, teacherSalary, teacherDepartment, title);
                break;
            case 3:
                System.out.print("Employee ID: ");
                String managerEmpId = scanner.nextLine();
                System.out.print("Salary: ");
                double managerSalary = getDoubleInput(0, 1_000_000);
                System.out.print("Department: ");
                String managerDepartment = scanner.nextLine();
                System.out.println("Manager type: 1.OR 2.DEPARTMENT 3.DEAN");
                int managerTypeIdx = getIntInput(1, 3);
                model.enums.ManagerType managerType = model.enums.ManagerType.values()[managerTypeIdx - 1];
                created = new Manager(username, password, firstName, lastName, email,
                        managerEmpId, managerSalary, managerDepartment, managerType);
                break;
            case 4:
                System.out.print("Employee ID: ");
                String adminEmpId = scanner.nextLine();
                System.out.print("Salary: ");
                double adminSalary = getDoubleInput(0, 1_000_000);
                System.out.print("Department: ");
                String adminDepartment = scanner.nextLine();
                created = new Admin(username, password, firstName, lastName, email,
                        adminEmpId, adminSalary, adminDepartment);
                break;
            default:
                break;
        }

        if (created != null) {
            dataStorage.addUser(created);
            if (currentUser instanceof Admin) {
                ((Admin) currentUser).logUserAction("Added user: " + created.getUsername());
            }
            System.out.println("User created successfully.");
        }
        waitForEnter();
    }

    private void removeUser() {
        System.out.print("Enter username to remove: ");
        String username = scanner.nextLine().trim();

        if (username.equals(currentUser.getUsername())) {
            System.out.println("You cannot remove the currently logged-in account.");
            waitForEnter();
            return;
        }

        boolean removed = dataStorage.removeUser(username);
        if (removed) {
            if (currentUser instanceof Admin) {
                ((Admin) currentUser).logUserAction("Removed user: " + username);
            }
            System.out.println("User removed successfully.");
        } else {
            System.out.println("User not found.");
        }
        waitForEnter();
    }

    private void updateUser() {
        System.out.print("Enter username to update: ");
        String username = scanner.nextLine().trim();

        if (dataStorage.getUser(username) == null) {
            System.out.println("User not found.");
            waitForEnter();
            return;
        }

        System.out.println("Leave field empty to keep current value.");
        System.out.print("New first name: ");
        String firstName = scanner.nextLine();
        System.out.print("New last name: ");
        String lastName = scanner.nextLine();
        System.out.print("New email: ");
        String email = scanner.nextLine();
        System.out.print("New password: ");
        String password = scanner.nextLine();

        boolean updated = dataStorage.updateUserBasicInfo(username, firstName, lastName, email, password);
        if (updated) {
            if (currentUser instanceof Admin) {
                ((Admin) currentUser).logUserAction("Updated user: " + username);
            }
            System.out.println("User updated successfully.");
        } else {
            System.out.println("Could not update user.");
        }

        waitForEnter();
    }

    private void viewUserLogs() {
        if (currentUser instanceof Admin) {
            Admin admin = (Admin) currentUser;
            System.out.println("\n=== USER ACTION LOGS ===");
            for (String log : admin.getUserActionsLog()) {
                System.out.println(log);
            }
        }
        waitForEnter();
    }

    private void saveData() {
        dataStorage.saveToFile("university_data.ser");
        waitForEnter();
    }

    private void loadData() {
        DataStorage.loadFromFile("university_data.ser");
        this.dataStorage = DataStorage.getInstance();
        waitForEnter();
    }

    // ========== TEACHER METHODS ==========

    private void viewTeacherCourses() {
        Teacher teacher = (Teacher) currentUser;
        System.out.println("\n=== YOUR COURSES ===");

        List<String> courseIds = new ArrayList<>(teacher.getCoursesTeaching());
        if (courseIds.isEmpty()) {
            for (Course course : dataStorage.getAllCourses()) {
                if (course.isTeacherInstructor(teacher.getEmployeeId())) {
                    courseIds.add(course.getCourseId());
                }
            }
        }

        if (courseIds.isEmpty()) {
            System.out.println("No courses assigned.");
        } else {
            for (String courseId : courseIds) {
                Course course = dataStorage.getCourse(courseId);
                if (course != null) {
                    System.out.println(course);
                }
            }
        }

        waitForEnter();
    }

    private void viewTeacherStudents() {
        Teacher teacher = (Teacher) currentUser;
        System.out.println("\n=== YOUR STUDENTS ===");

        Set<String> seenStudentIds = new HashSet<>();
        for (Course course : dataStorage.getAllCourses()) {
            if (!course.isTeacherInstructor(teacher.getEmployeeId())) {
                continue;
            }

            for (String studentId : course.getRegisteredStudentIds()) {
                if (seenStudentIds.add(studentId)) {
                    Student student = dataStorage.getStudentById(studentId);
                    if (student != null) {
                        System.out.println(student.getStudentId() + " - " + student.getFullName() +
                                " | GPA: " + String.format("%.2f", student.getGpa()));
                    }
                }
            }
        }

        if (seenStudentIds.isEmpty()) {
            System.out.println("No enrolled students found for your courses.");
        }

        waitForEnter();
    }

    private void putMarks() {
        System.out.println("\n=== PUT MARKS ===");
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine().trim();
        System.out.print("Enter course ID: ");
        String courseId = scanner.nextLine().trim();

        Student student = dataStorage.getStudentById(studentId);
        Course course = dataStorage.getCourse(courseId);
        if (student == null || course == null) {
            System.out.println("Invalid student ID or course ID.");
            waitForEnter();
            return;
        }

        System.out.print("Enter first attestation (0-30): ");
        double firstAtt = getDoubleInput(0, 30);
        System.out.print("Enter second attestation (0-30): ");
        double secondAtt = getDoubleInput(0, 30);
        System.out.print("Enter final exam (0-40): ");
        double finalExam = getDoubleInput(0, 40);

        Mark mark = new Mark(studentId, courseId, firstAtt, secondAtt, finalExam);
        dataStorage.addMark(mark);

        try {
            student.addCourseMark(courseId, mark.getTotalMark());
        } catch (FailLimitException e) {
            System.out.println("Warning: " + e.getMessage());
        }

        System.out.println("Mark added successfully!");
        System.out.println(mark.getMarkBreakdown());
        waitForEnter();
    }

    private void manageTeacherCourse() {
        Teacher teacher = (Teacher) currentUser;
        List<Course> ownedCourses = new ArrayList<>();
        for (Course course : dataStorage.getAllCourses()) {
            if (course.isTeacherInstructor(teacher.getEmployeeId())) {
                ownedCourses.add(course);
            }
        }

        if (ownedCourses.isEmpty()) {
            System.out.println("No courses assigned to manage.");
            waitForEnter();
            return;
        }

        System.out.println("Select course:");
        for (int i = 0; i < ownedCourses.size(); i++) {
            Course course = ownedCourses.get(i);
            System.out.println((i + 1) + ". " + course.getCourseId() + " - " + course.getCourseName());
        }
        int courseIdx = getIntInput(1, ownedCourses.size()) - 1;
        Course selected = ownedCourses.get(courseIdx);

        System.out.println("1. Add Lesson");
        System.out.println("2. Remove Lesson");
        int action = getIntInput(1, 2);

        if (action == 1) {
            try {
                System.out.print("Lesson ID: ");
                String lessonId = scanner.nextLine().trim();
                System.out.print("Topic: ");
                String topic = scanner.nextLine().trim();
                System.out.println("Lesson type: 1.LECTURE 2.PRACTICE");
                int typeOpt = getIntInput(1, 2);
                model.enums.LessonType type = (typeOpt == 1)
                        ? model.enums.LessonType.LECTURE
                        : model.enums.LessonType.PRACTICE;
                System.out.print("Day (MONDAY..SUNDAY): ");
                DayOfWeek day = DayOfWeek.valueOf(scanner.nextLine().trim().toUpperCase());
                System.out.print("Start time (HH:MM): ");
                LocalTime start = LocalTime.parse(scanner.nextLine().trim());
                System.out.print("End time (HH:MM): ");
                LocalTime end = LocalTime.parse(scanner.nextLine().trim());
                System.out.print("Room: ");
                String room = scanner.nextLine().trim();

                Lesson lesson = new Lesson(lessonId, type, topic, day, start, end, room, teacher.getEmployeeId());
                selected.addLesson(lesson);
                System.out.println("Lesson added.");
            } catch (Exception e) {
                System.out.println("Could not add lesson: " + e.getMessage());
            }
        } else {
            List<Lesson> lessons = selected.getLessons();
            if (lessons.isEmpty()) {
                System.out.println("No lessons to remove.");
            } else {
                for (int i = 0; i < lessons.size(); i++) {
                    System.out.println((i + 1) + ". " + lessons.get(i));
                }
                int lessonIdx = getIntInput(1, lessons.size()) - 1;
                selected.removeLesson(lessons.get(lessonIdx));
                System.out.println("Lesson removed.");
            }
        }

        waitForEnter();
    }

    private void sendMessageToEmployee() {
        System.out.println("\n=== SEND MESSAGE ===");
        List<Employee> employees = dataStorage.getAllEmployees();
        List<Employee> candidates = new ArrayList<>();
        for (Employee employee : employees) {
            if (!employee.getUsername().equals(currentUser.getUsername())) {
                candidates.add(employee);
            }
        }

        if (candidates.isEmpty()) {
            System.out.println("No other employees available.");
            waitForEnter();
            return;
        }

        for (int i = 0; i < candidates.size(); i++) {
            Employee employee = candidates.get(i);
            System.out.println((i + 1) + ". " + employee.getUsername() + " (" + employee.getUserType() + ")");
        }

        System.out.print("Select receiver: ");
        int idx = getIntInput(1, candidates.size()) - 1;
        System.out.print("Message text: ");
        String message = scanner.nextLine().trim();

        boolean ok = dataStorage.sendEmployeeMessage(currentUser.getUsername(), candidates.get(idx).getUsername(), message);
        System.out.println(ok ? "Message sent." : "Could not send message.");
        waitForEnter();
    }

    private void sendComplaintOrRequest() {
        System.out.println("\n=== SEND COMPLAINT/REQUEST ===");
        System.out.print("Enter complaint/request text: ");
        String text = scanner.nextLine().trim();
        dataStorage.submitEmployeeRequest(currentUser.getUsername(), text);
        System.out.println("Request submitted for manager review.");
        waitForEnter();
    }

    private void viewEmployeeInbox() {
        System.out.println("\n=== INBOX ===");
        List<String> messages = dataStorage.getEmployeeMessages(currentUser.getUsername());
        if (messages.isEmpty()) {
            System.out.println("No messages.");
        } else {
            for (String message : messages) {
                System.out.println(message);
            }
        }
        waitForEnter();
    }

    // ========== STUDENT METHODS ==========

    private void viewStudentCourses() {
        Student student = (Student) currentUser;
        System.out.println("\n=== YOUR COURSES ===");
        for (String courseId : student.getRegisteredCourses()) {
            Course course = dataStorage.getCourse(courseId);
            if (course != null) {
                System.out.println(course);
            }
        }
        System.out.println("Total Credits: " + student.getTotalCredits() + "/21");
        waitForEnter();
    }

    private void registerForCourse() {
        Student student = (Student) currentUser;
        System.out.println("\n=== AVAILABLE COURSES ===");
        for (Course course : dataStorage.getAllCourses()) {
            if (course.hasAvailableSpots()) {
                System.out.println(course.getCourseInfo());
            }
        }

        System.out.print("Enter course ID to register: ");
        String courseId = scanner.nextLine().trim();

        if (dataStorage.getCourse(courseId) == null) {
            System.out.println("Course not found!");
            waitForEnter();
            return;
        }

        boolean requested = dataStorage.requestCourseRegistration(student.getStudentId(), courseId);
        if (requested) {
            System.out.println("Registration request submitted. Waiting for manager approval.");
        } else {
            System.out.println("Could not submit registration request.");
        }

        waitForEnter();
    }

    private void viewStudentMarks() {
        Student student = (Student) currentUser;
        System.out.println("\n=== YOUR MARKS ===");
        for (Map.Entry<String, Double> entry : student.getCourseMarks().entrySet()) {
            String courseId = entry.getKey();
            Double mark = entry.getValue();
            Course course = dataStorage.getCourse(courseId);
            if (course != null) {
                System.out.println(course.getCourseName() + ": " +
                        (mark != null ? String.format("%.1f", mark) : "No grade"));
            }
        }
        System.out.println("GPA: " + String.format("%.2f", student.getGpa()));
        waitForEnter();
    }

    private void viewTranscript() {
        Student student = (Student) currentUser;
        System.out.println("\n=== YOUR TRANSCRIPT ===");
        System.out.println(student.getTranscript());

        if (student.needsResearchSupervisor()) {
            System.out.println("\nYou are a 4th year student and need a research supervisor.");
            assignSupervisorForStudent(student);
        }

        waitForEnter();
    }

    private void viewTeacherInfoForCourse() {
        Student student = (Student) currentUser;
        System.out.print("Enter course ID: ");
        String courseId = scanner.nextLine().trim();

        if (!student.getRegisteredCourses().contains(courseId)) {
            System.out.println("You are not registered for this course.");
            waitForEnter();
            return;
        }

        Course course = dataStorage.getCourse(courseId);
        if (course == null) {
            System.out.println("Course not found.");
            waitForEnter();
            return;
        }

        List<String> instructorIds = course.getInstructorIds();
        if (instructorIds.isEmpty()) {
            System.out.println("No teachers assigned to this course.");
        } else {
            for (String instructorId : instructorIds) {
                for (Teacher teacher : dataStorage.getAllTeachers()) {
                    if (teacher.getEmployeeId().equals(instructorId)) {
                        double avgRating = dataStorage.getTeacherAverageRating(teacher.getEmployeeId());
                        System.out.println(teacher.getFullName() + " | " + teacher.getTitle() +
                                " | Avg rating: " + String.format("%.2f", avgRating));
                    }
                }
            }
        }

        waitForEnter();
    }

    private void rateTeacher() {
        Student student = (Student) currentUser;
        System.out.print("Enter teacher employee ID: ");
        String teacherId = scanner.nextLine().trim();
        System.out.print("Rate 1-5: ");
        int rating = getIntInput(1, 5);

        boolean ok = dataStorage.rateTeacher(student.getStudentId(), teacherId, rating);
        System.out.println(ok ? "Rating submitted." : "Could not submit rating.");
        waitForEnter();
    }

    private void assignSupervisorForStudent(Student student) {
        List<Researcher> candidates = dataStorage.getAllResearchers();
        if (candidates.isEmpty()) {
            System.out.println("No researchers available for supervision.");
            return;
        }

        System.out.println("Available supervisors:");
        for (Researcher researcher : candidates) {
            System.out.println(researcher.getResearcherId() + " | " + researcher.getResearcherName() +
                    " | h-index=" + researcher.getHIndex());
        }

        System.out.print("Enter supervisor researcher ID (or empty to skip): ");
        String researcherId = scanner.nextLine().trim();
        if (researcherId.isEmpty()) {
            return;
        }

        Researcher researcher = dataStorage.getResearcher(researcherId);
        if (researcher == null) {
            System.out.println("Researcher not found.");
            return;
        }

        try {
            student.setResearchSupervisor(researcher);
            System.out.println("Supervisor assigned successfully.");
        } catch (HIndexTooLowException e) {
            System.out.println("Cannot assign supervisor: " + e.getMessage());
        }
    }

    // ========== MANAGER METHODS ==========

    private void approveRegistrations() {
        System.out.println("\n=== APPROVE REGISTRATIONS ===");

        List<DataStorage.PendingRegistration> pending = dataStorage.getPendingRegistrations();
        if (pending.isEmpty()) {
            System.out.println("No pending registrations.");
            waitForEnter();
            return;
        }

        for (int i = 0; i < pending.size(); i++) {
            System.out.println((i + 1) + ". " + pending.get(i));
        }
        System.out.println("0. Back");
        System.out.print("Choose request to approve: ");

        int choice = getIntInput(0, pending.size());
        if (choice == 0) {
            return;
        }

        DataStorage.PendingRegistration registration = pending.get(choice - 1);
        try {
            boolean approved = dataStorage.approveRegistration(
                    registration.getStudentId(),
                    registration.getCourseId()
            );
            System.out.println(approved ? "Registration approved." : "Could not approve registration.");
        } catch (CreditLimitException e) {
            System.out.println("Approval failed: " + e.getMessage());
        }

        waitForEnter();
    }

    private void assignTeachersToCourses() {
        System.out.println("\n=== ASSIGN TEACHERS TO COURSES ===");

        List<Teacher> teachers = dataStorage.getAllTeachers();
        List<Course> courses = dataStorage.getAllCourses();

        if (teachers.isEmpty() || courses.isEmpty()) {
            System.out.println("Teachers or courses are missing.");
            waitForEnter();
            return;
        }

        System.out.println("Teachers:");
        for (int i = 0; i < teachers.size(); i++) {
            Teacher teacher = teachers.get(i);
            System.out.println((i + 1) + ". " + teacher.getFullName() +
                    " [" + teacher.getEmployeeId() + "]");
        }

        System.out.println("Courses:");
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            System.out.println((i + 1) + ". " + course.getCourseId() + " - " + course.getCourseName());
        }

        System.out.print("Choose teacher number: ");
        int teacherIndex = getIntInput(1, teachers.size()) - 1;
        System.out.print("Choose course number: ");
        int courseIndex = getIntInput(1, courses.size()) - 1;

        Teacher teacher = teachers.get(teacherIndex);
        Course course = courses.get(courseIndex);

        course.addInstructor(teacher.getEmployeeId());
        teacher.addCourseTeaching(course.getCourseId());
        System.out.println("Teacher assigned successfully.");

        waitForEnter();
    }

    private void manageNews() {
        System.out.println("\n=== MANAGE NEWS ===");
        System.out.println("1. View News");
        System.out.println("2. Add News");
        int option = getIntInput(1, 2);

        if (option == 1) {
            List<DataStorage.NewsItem> newsItems = dataStorage.getNewsFeed();
            if (newsItems.isEmpty()) {
                System.out.println("No news yet.");
            } else {
                for (DataStorage.NewsItem newsItem : newsItems) {
                    System.out.println(newsItem);
                    System.out.println("--------------------------");
                }
            }
        } else {
            System.out.print("Title: ");
            String title = scanner.nextLine().trim();
            System.out.print("Body: ");
            String body = scanner.nextLine().trim();
            dataStorage.addNews(title, body, currentUser.getUsername());
            System.out.println("News item published.");
        }

        waitForEnter();
    }

    private void viewEmployeeRequests() {
        System.out.println("\n=== EMPLOYEE REQUESTS ===");
        List<DataStorage.EmployeeRequest> requests = dataStorage.getEmployeeRequests();
        if (requests.isEmpty()) {
            System.out.println("No employee requests available.");
            waitForEnter();
            return;
        }

        for (DataStorage.EmployeeRequest request : requests) {
            System.out.println(request);
        }

        System.out.print("Enter request ID to resolve (or empty to skip): ");
        String requestId = scanner.nextLine().trim();
        if (requestId.isEmpty()) {
            waitForEnter();
            return;
        }

        System.out.println("1. Approve");
        System.out.println("2. Reject");
        int decision = getIntInput(1, 2);
        boolean ok = dataStorage.resolveEmployeeRequest(requestId, decision == 1, currentUser.getUsername());
        System.out.println(ok ? "Request resolved." : "Request ID not found.");
        waitForEnter();
    }

    private void generateReports() {
        System.out.println("\n=== GENERATE REPORTS ===");
        System.out.println("1. Student Performance Report");
        System.out.println("2. Course Statistics Report");
        System.out.println("3. Research Activity Report");
        System.out.println("4. Marks Analytics Report");

        int choice = getIntInput(1, 4);
        switch (choice) {
            case 1:
                generateStudentPerformanceReport();
                break;
            case 2:
                generateCourseStatisticsReport();
                break;
            case 3:
                generateResearchActivityReport();
                break;
            case 4:
                generateMarksAnalyticsReport();
                break;
            default:
                break;
        }
    }

    private void generateStudentPerformanceReport() {
        System.out.println("\n=== STUDENT PERFORMANCE REPORT ===");
        for (Student student : dataStorage.getAllStudents()) {
            System.out.println(student.getFullName() + " - GPA: " +
                    String.format("%.2f", student.getGpa()) +
                    " - Credits: " + student.getTotalCredits());
        }
        waitForEnter();
    }

    private void generateCourseStatisticsReport() {
        System.out.println("\n=== COURSE STATISTICS REPORT ===");
        for (Course course : dataStorage.getAllCourses()) {
            System.out.println(course.getCourseName() +
                    " - Students: " + course.getRegisteredStudentIds().size() +
                    "/" + course.getMaxStudents());
        }
        waitForEnter();
    }

    private void generateResearchActivityReport() {
        System.out.println("\n=== RESEARCH ACTIVITY REPORT ===");
        System.out.println("Total Research Papers: " + dataStorage.getResearchPaperCount());
        System.out.println("Total Research Projects: " + dataStorage.getResearchProjectCount());
        System.out.println("Total Researchers: " + dataStorage.getAllResearchers().size());
        waitForEnter();
    }

    private void generateMarksAnalyticsReport() {
        System.out.println();
        System.out.println(dataStorage.getMarksAnalyticsReport());
        waitForEnter();
    }

    private void viewStudentTeacherInfo() {
        System.out.println("\n=== VIEW INFO ===");
        System.out.println("1. View All Students");
        System.out.println("2. View All Teachers");
        System.out.println("3. View Students Sorted by GPA");

        int choice = getIntInput(1, 3);
        switch (choice) {
            case 1:
                viewAllStudents();
                break;
            case 2:
                viewAllTeachers();
                break;
            case 3:
                viewStudentsSortedByGPA();
                break;
            default:
                break;
        }
    }

    private void viewAllStudents() {
        System.out.println("\n=== ALL STUDENTS ===");
        for (Student student : dataStorage.getAllStudents()) {
            System.out.println(student);
        }
        waitForEnter();
    }

    private void viewAllTeachers() {
        System.out.println("\n=== ALL TEACHERS ===");
        for (Teacher teacher : dataStorage.getAllTeachers()) {
            System.out.println(teacher);
        }
        waitForEnter();
    }

    private void viewStudentsSortedByGPA() {
        System.out.println("\n=== STUDENTS SORTED BY GPA (DESCENDING) ===");
        List<Student> students = dataStorage.getAllStudents();
        students.sort((s1, s2) -> Double.compare(s2.getGpa(), s1.getGpa()));
        for (Student student : students) {
            System.out.println(student.getFullName() + " - GPA: " +
                    String.format("%.2f", student.getGpa()) +
                    " - Year: " + student.getYear());
        }
        waitForEnter();
    }

    // ========== RESEARCH METHODS ==========

    private void viewResearchPapers() {
        System.out.println("\n=== ALL RESEARCH PAPERS ===");
        for (ResearchPaper paper : dataStorage.getAllResearchPapers()) {
            System.out.println(paper.getFormattedCitation());
            System.out.println("  Citations: " + paper.getCitations() +
                    " | Pages: " + paper.getPages() +
                    " | Impact: " + String.format("%.2f", paper.calculateImpactFactor()));
            System.out.println();
        }
        waitForEnter();
    }

    private void addResearchPaper() {
        System.out.println("\n=== ADD RESEARCH PAPER ===");

        Researcher researcher = ensureCurrentUserResearcher(true);
        if (researcher == null) {
            waitForEnter();
            return;
        }

        System.out.print("Title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Authors (comma separated): ");
        List<String> authors = parseCsv(scanner.nextLine());
        if (authors.isEmpty()) {
            authors.add(currentUser.getFullName());
        }

        System.out.print("Journal: ");
        String journal = scanner.nextLine().trim();

        System.out.print("Publication date (YYYY-MM-DD): ");
        LocalDate publicationDate = readDateInput();

        System.out.print("Citations: ");
        int citations = getIntInput(0, 1_000_000);

        System.out.print("Pages: ");
        int pages = getIntInput(1, 10000);

        System.out.print("DOI: ");
        String doi = scanner.nextLine().trim();

        System.out.print("Abstract: ");
        String abstractText = scanner.nextLine().trim();

        System.out.print("Keywords (comma separated): ");
        List<String> keywords = parseCsv(scanner.nextLine());

        ResearchPaper paper = new ResearchPaper(
                title,
                authors,
                journal,
                publicationDate,
                citations,
                pages,
                doi,
                abstractText,
                keywords
        );

        dataStorage.addResearchPaper(paper);
        researcher.addResearchPaper(paper);

        System.out.println("Research paper added successfully and linked to " + researcher.getResearcherName() + ".");
        waitForEnter();
    }

    private void viewResearchProjects() {
        System.out.println("\n=== ALL RESEARCH PROJECTS ===");
        for (ResearchProject project : dataStorage.getAllResearchProjects()) {
            System.out.println(project.getProjectReport());
            System.out.println();
        }
        waitForEnter();
    }

    private void joinResearchProject() {
        System.out.println("\n=== JOIN RESEARCH PROJECT ===");
        System.out.print("Enter project ID: ");
        String projectId = scanner.nextLine().trim();
        ResearchProject project = dataStorage.getResearchProject(projectId);

        if (project == null) {
            System.out.println("Project not found!");
            waitForEnter();
            return;
        }

        Researcher researcher = ensureCurrentUserResearcher(false);
        if (researcher == null) {
            System.out.println("Only researchers can join research projects.");
            waitForEnter();
            return;
        }

        try {
            project.addParticipant(researcher.getResearcherId(), true);
            researcher.addResearchProject(project);
            System.out.println("Successfully joined the project.");
        } catch (NotResearcherException e) {
            System.out.println("Could not join project: " + e.getMessage());
        }
        waitForEnter();
    }

    private void printPapersWithComparator() {
        System.out.println("\n=== PRINT PAPERS WITH COMPARATOR ===");
        ResearchPaperComparators.printAvailableComparators();
        System.out.print("Enter sorting option: ");
        String option = scanner.nextLine().trim();

        Comparator<ResearchPaper> comparator = ResearchPaperComparators.getComparator(option);

        System.out.println("Print mode:");
        System.out.println("1. All papers globally");
        System.out.println("2. Papers grouped by researcher");
        int mode = getIntInput(1, 2);

        if (mode == 1) {
            List<ResearchPaper> papers = dataStorage.getAllResearchPapers();
            papers.sort(comparator);

            System.out.println("\n=== PAPERS SORTED BY " + option.toUpperCase() + " ===");
            for (ResearchPaper paper : papers) {
                System.out.println(paper.getTitle());
                System.out.println("  Date: " + paper.getPublicationDate() +
                        " | Citations: " + paper.getCitations() +
                        " | Pages: " + paper.getPages());
                System.out.println();
            }
        } else {
            List<Researcher> researchers = dataStorage.getAllResearchers();
            if (researchers.isEmpty()) {
                System.out.println("No researchers found in the system.");
            } else {
                for (Researcher researcher : researchers) {
                    researcher.printPapers(comparator);
                    System.out.println("----------------------------------------------");
                }
            }
        }

        waitForEnter();
    }

    private void viewTopCitedResearcher() {
        System.out.println("\n=== TOP CITED RESEARCHER ===");
        System.out.println("1. Global");
        System.out.println("2. By school");
        System.out.println("3. By year");
        System.out.println("4. By school and year");

        int option = getIntInput(1, 4);

        Researcher topResearcher = null;
        String context = "";

        switch (option) {
            case 1:
                topResearcher = dataStorage.getTopCitedResearcher();
                context = "Global";
                break;
            case 2:
                System.out.print("Enter school (department/major): ");
                String school = scanner.nextLine().trim();
                topResearcher = dataStorage.getTopCitedResearcherBySchool(school);
                context = "School=" + school;
                break;
            case 3:
                System.out.print("Enter publication year (e.g., 2024): ");
                int year = getIntInput(1900, 3000);
                topResearcher = dataStorage.getTopCitedResearcherByYear(year);
                context = "Year=" + year;
                break;
            case 4:
                System.out.print("Enter school (department/major): ");
                String schoolYear = scanner.nextLine().trim();
                System.out.print("Enter publication year (e.g., 2024): ");
                int filterYear = getIntInput(1900, 3000);
                topResearcher = dataStorage.getTopCitedResearcherBySchoolAndYear(schoolYear, filterYear);
                context = "School=" + schoolYear + ", Year=" + filterYear;
                break;
            default:
                break;
        }

        if (topResearcher == null) {
            System.out.println("No matching researchers found.");
        } else {
            System.out.println("Context: " + context);
            System.out.println("Top Cited Researcher: " + topResearcher.getResearcherName());
            System.out.println("Researcher ID: " + topResearcher.getResearcherId());
            System.out.println("School: " + dataStorage.getResearcherSchool(topResearcher));
            System.out.println("Total Citations: " + topResearcher.getTotalCitations());
            System.out.println("h-index: " + topResearcher.getHIndex());
            System.out.println("Number of Papers: " + topResearcher.getResearchPapers().size());
        }

        waitForEnter();
    }

    // ========== HELPER METHODS ==========

    private Researcher ensureCurrentUserResearcher(boolean offerCreate) {
        Researcher researcher = dataStorage.getResearcherByUsername(currentUser.getUsername());
        if (researcher != null) {
            return researcher;
        }

        if (!offerCreate) {
            return null;
        }

        if (!(currentUser instanceof Student) && !(currentUser instanceof Employee)) {
            return null;
        }

        System.out.print("You are not registered as a researcher. Register now? (y/n): ");
        String answer = scanner.nextLine().trim();
        if (!"y".equalsIgnoreCase(answer)) {
            return null;
        }

        researcher = dataStorage.ensureResearcherForUser(currentUser);
        if (researcher != null) {
            System.out.println("Researcher profile created: " + researcher.getResearcherId());
        }

        return researcher;
    }

    private List<String> parseCsv(String input) {
        List<String> result = new ArrayList<>();
        if (input == null || input.trim().isEmpty()) {
            return result;
        }

        List<String> tokens = Arrays.asList(input.split(","));
        for (String token : tokens) {
            String trimmed = token.trim();
            if (!trimmed.isEmpty()) {
                result.add(trimmed);
            }
        }
        return result;
    }

    private LocalDate readDateInput() {
        while (true) {
            String raw = scanner.nextLine().trim();
            try {
                return LocalDate.parse(raw);
            } catch (DateTimeParseException e) {
                System.out.print("Invalid date format. Use YYYY-MM-DD: ");
            }
        }
    }

    private int getIntInput(int min, int max) {
        while (true) {
            try {
                System.out.print("> ");
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Please enter a number between " + min + " and " + max);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private double getDoubleInput(double min, double max) {
        while (true) {
            try {
                System.out.print("> ");
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Please enter a number between " + min + " and " + max);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private void waitForEnter() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
