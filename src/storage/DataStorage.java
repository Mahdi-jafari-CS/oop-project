                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                package storage;

import exceptions.CreditLimitException;
import exceptions.HIndexTooLowException;
import exceptions.NotResearcherException;
import model.courses.Course;
import model.courses.Mark;
import model.research.EmployeeResearcher;
import model.research.ResearchPaper;
import model.research.ResearchProject;
import model.research.Researcher;
import model.research.StudentResearcher;
import model.research.TeacherResearcher;
import model.users.Admin;
import model.users.Employee;
import model.users.Manager;
import model.users.Student;
import model.users.Teacher;
import model.users.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Singleton class for data storage and serialization
 * Implements Singleton pattern to ensure single instance
 */
public class DataStorage implements Serializable {
    private static final long serialVersionUID = 1L;
    private static DataStorage instance;

    // Data collections
    private Map<String, User> users;
    private Map<String, Course> courses;
    private Map<String, Mark> marks;
    private Map<String, Researcher> researchers;
    private Map<String, ResearchPaper> researchPapers;
    private Map<String, ResearchProject> researchProjects;

    // User type specific maps
    private Map<String, Admin> admins;
    private Map<String, Teacher> teachers;
    private Map<String, Student> students;
    private Map<String, Manager> managers;

    // Supporting indexes and workflows
    private Map<String, String> userToResearcherId;
    private Map<String, Set<String>> pendingRegistrations; // courseId -> studentId set
    private Map<String, List<Integer>> teacherRatings; // teacherEmployeeId -> ratings
    private Map<String, List<String>> employeeMessages; // username -> inbox messages
    private List<EmployeeRequest> employeeRequests;
    private List<NewsItem> newsFeed;

    /**
     * Value object for pending registration entries.
     */
    public static class PendingRegistration implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String studentId;
        private final String courseId;

        public PendingRegistration(String studentId, String courseId) {
            this.studentId = studentId;
            this.courseId = courseId;
        }

        public String getStudentId() {
            return studentId;
        }

        public String getCourseId() {
            return courseId;
        }

        @Override
        public String toString() {
            return "Student ID: " + studentId + " | Course ID: " + courseId;
        }
    }

    /**
     * Value object for employee requests/complaints.
     */
    public static class EmployeeRequest implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String requestId;
        private final String employeeUsername;
        private final String text;
        private final String status;
        private final String resolvedBy;

        public EmployeeRequest(String requestId, String employeeUsername, String text, String status, String resolvedBy) {
            this.requestId = requestId;
            this.employeeUsername = employeeUsername;
            this.text = text;
            this.status = status;
            this.resolvedBy = resolvedBy;
        }

        public String getRequestId() {
            return requestId;
        }

        public String getEmployeeUsername() {
            return employeeUsername;
        }

        public String getText() {
            return text;
        }

        public String getStatus() {
            return status;
        }

        public String getResolvedBy() {
            return resolvedBy;
        }

        @Override
        public String toString() {
            return requestId + " | " + employeeUsername + " | " + status + " | " + text +
                    (resolvedBy != null ? " | resolved by " + resolvedBy : "");
        }
    }

    /**
     * Value object for manager news feed entries.
     */
    public static class NewsItem implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String title;
        private final String body;
        private final String author;
        private final LocalDate date;

        public NewsItem(String title, String body, String author, LocalDate date) {
            this.title = title;
            this.body = body;
            this.author = author;
            this.date = date;
        }

        @Override
        public String toString() {
            return "[" + date + "] " + title + " (by " + author + ")\n" + body;
        }
    }

    private DataStorage() {
        users = new HashMap<>();
        courses = new HashMap<>();
        marks = new HashMap<>();
        researchers = new HashMap<>();
        researchPapers = new HashMap<>();
        researchProjects = new HashMap<>();

        admins = new HashMap<>();
        teachers = new HashMap<>();
        students = new HashMap<>();
        managers = new HashMap<>();

        userToResearcherId = new HashMap<>();
        pendingRegistrations = new HashMap<>();
        teacherRatings = new HashMap<>();
        employeeMessages = new HashMap<>();
        employeeRequests = new ArrayList<>();
        newsFeed = new ArrayList<>();
    }

    /**
     * Get singleton instance
     */
    public static synchronized DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    /**
     * Initialize with sample data for demo
     */
    public void initializeSampleData() {
        // Create sample admin
        Admin admin = new Admin("admin", "admin123", "System", "Administrator",
                "admin@university.edu", "ADM001", 5000.0, "IT Department");
        addUser(admin);

        // Create sample teachers
        Teacher teacher1 = new Teacher("prof_john", "pass123", "John", "Doe",
                "john.doe@university.edu", "TCH001", 3000.0,
                "Computer Science", model.enums.Title.PROFESSOR);
        Teacher teacher2 = new Teacher("lector_jane", "pass123", "Jane", "Smith",
                "jane.smith@university.edu", "TCH002", 2500.0,
                "Mathematics", model.enums.Title.LECTOR);
        addUser(teacher1);
        addUser(teacher2);

        // Create sample students
        Student student1 = new Student("student1", "pass123", "Alice", "Johnson",
                "alice@student.edu", "STU001", model.enums.StudentYear.FIRST,
                "Computer Science");
        Student student2 = new Student("student2", "pass123", "Bob", "Williams",
                "bob@student.edu", "STU002", model.enums.StudentYear.FOURTH,
                "Mathematics");
        addUser(student1);
        addUser(student2);

        // Create sample manager (employee-researcher capable)
        Manager manager = new Manager("manager1", "pass123", "Charlie", "Brown",
                "charlie@university.edu", "MGR001", 4000.0,
                "Administration", model.enums.ManagerType.DEPARTMENT);
        addUser(manager);

        // Create sample courses
        Course course1 = new Course("CS101", "Introduction to Programming",
                "Basic programming concepts", 3, "Computer Science", 1, 30);
        Course course2 = new Course("MATH201", "Advanced Calculus",
                "Calculus for engineers", 4, "Mathematics", 2, 25);
        addCourse(course1);
        addCourse(course2);

        // Assign teachers to courses
        course1.addInstructor(teacher1.getEmployeeId());
        teacher1.addCourseTeaching(course1.getCourseId());

        course2.addInstructor(teacher2.getEmployeeId());
        teacher2.addCourseTeaching(course2.getCourseId());

        // Create sample research papers
        ResearchPaper paper1 = new ResearchPaper(
                "Machine Learning Advances",
                Arrays.asList("John Doe", "Jane Smith"),
                "IEEE Transactions on AI",
                LocalDate.of(2023, 5, 15),
                150,
                12,
                "10.1109/TAI.2023.1234567",
                "Abstract about machine learning...",
                Arrays.asList("machine learning", "artificial intelligence", "neural networks")
        );

        ResearchPaper paper2 = new ResearchPaper(
                "Quantum Computing Algorithms",
                Arrays.asList("John Doe"),
                "Nature Physics",
                LocalDate.of(2024, 2, 20),
                85,
                8,
                "10.1038/s41567-024-02478-2",
                "Abstract about quantum algorithms...",
                Arrays.asList("quantum computing", "algorithms", "quantum physics")
        );

        ResearchPaper paper3 = new ResearchPaper(
                "Scalable Data Systems",
                Arrays.asList("John Doe", "Charlie Brown"),
                "ACM Computing Surveys",
                LocalDate.of(2024, 11, 10),
                40,
                10,
                "10.1145/1234567.8901234",
                "Abstract about scalable data systems...",
                Arrays.asList("distributed systems", "databases", "scalability")
        );

        addResearchPaper(paper1);
        addResearchPaper(paper2);
        addResearchPaper(paper3);

        // Register researchers and connect papers/projects
        Researcher profResearcher = getResearcherByUsername(teacher1.getUsername());
        if (profResearcher != null) {
            profResearcher.addResearchPaper(paper1);
            profResearcher.addResearchPaper(paper2);
            profResearcher.addResearchPaper(paper3);
        }

        Researcher teacherResearcher = ensureResearcherForUser(teacher2);
        if (teacherResearcher != null) {
            teacherResearcher.addResearchPaper(paper1);
        }

        Researcher employeeResearcher = ensureResearcherForUser(manager);
        if (employeeResearcher != null) {
            employeeResearcher.addResearchPaper(paper3);
        }

        Researcher studentResearcher = ensureResearcherForUser(student2);
        if (studentResearcher != null) {
            studentResearcher.addResearchPaper(paper2);
        }

        // Create sample research project
        ResearchProject project1 = new ResearchProject(
                "PROJ001",
                "AI for Healthcare",
                "Applying AI to medical diagnosis",
                "National Science Foundation",
                500000.0
        );

        project1.addPublishedPaper(paper1);
        project1.addPublishedPaper(paper2);

        try {
            if (profResearcher != null) {
                project1.addParticipant(profResearcher.getResearcherId(), true);
                profResearcher.addResearchProject(project1);
            }
            if (studentResearcher != null) {
                project1.addParticipant(studentResearcher.getResearcherId(), true);
                studentResearcher.addResearchProject(project1);
            }
            if (employeeResearcher != null) {
                project1.addParticipant(employeeResearcher.getResearcherId(), true);
                employeeResearcher.addResearchProject(project1);
            }
        } catch (NotResearcherException e) {
            System.err.println("Error during sample project setup: " + e.getMessage());
        }

        addResearchProject(project1);

        addNews("Welcome", "System initialized with sample data.", "System");
        submitEmployeeRequest("lector_jane", "Request for additional lab room slots.");

        // Assign valid supervisor to 4th-year student
        if (studentResearcher instanceof StudentResearcher && profResearcher != null) {
            try {
                ((StudentResearcher) studentResearcher).getStudent().setResearchSupervisor(profResearcher);
            } catch (HIndexTooLowException e) {
                System.err.println("Supervisor assignment error in sample data: " + e.getMessage());
            }
        }

        System.out.println("Sample data initialized successfully!");
    }

    // User management methods

    public void addUser(User user) {
        users.put(user.getUsername(), user);

        // Add to specific type map
        if (user instanceof Admin) {
            admins.put(user.getUsername(), (Admin) user);
        } else if (user instanceof Teacher) {
            Teacher teacher = (Teacher) user;
            teachers.put(user.getUsername(), teacher);
            // Professors are always researchers
            if (teacher.isAutoResearcher()) {
                ensureResearcherForUser(teacher);
            }
        } else if (user instanceof Student) {
            students.put(user.getUsername(), (Student) user);
        } else if (user instanceof Manager) {
            managers.put(user.getUsername(), (Manager) user);
        }
    }

    public boolean removeUser(String username) {
        User removed = users.remove(username);
        if (removed == null) {
            return false;
        }

        admins.remove(username);
        teachers.remove(username);
        students.remove(username);
        managers.remove(username);

        String researcherId = userToResearcherId.remove(username);
        if (researcherId != null) {
            researchers.remove(researcherId);
        }

        return true;
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public boolean updateUserBasicInfo(String username, String firstName, String lastName, String email, String password) {
        User user = users.get(username);
        if (user == null) {
            return false;
        }

        if (firstName != null && !firstName.trim().isEmpty()) {
            user.setFirstName(firstName.trim());
        }
        if (lastName != null && !lastName.trim().isEmpty()) {
            user.setLastName(lastName.trim());
        }
        if (email != null && !email.trim().isEmpty()) {
            user.setEmail(email.trim());
        }
        if (password != null && !password.trim().isEmpty()) {
            user.setPassword(password);
        }

        return true;
    }

    public Admin getAdmin(String username) {
        return admins.get(username);
    }

    public Teacher getTeacher(String username) {
        return teachers.get(username);
    }

    public Student getStudent(String username) {
        return students.get(username);
    }

    public Student getStudentById(String studentId) {
        for (Student student : students.values()) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    public Manager getManager(String username) {
        return managers.get(username);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public List<Admin> getAllAdmins() {
        return new ArrayList<>(admins.values());
    }

    public List<Teacher> getAllTeachers() {
        return new ArrayList<>(teachers.values());
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }

    public List<Manager> getAllManagers() {
        return new ArrayList<>(managers.values());
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.addAll(admins.values());
        employees.addAll(teachers.values());
        employees.addAll(managers.values());
        return employees;
    }

    public boolean sendEmployeeMessage(String fromUsername, String toUsername, String message) {
        User sender = users.get(fromUsername);
        User receiver = users.get(toUsername);
        if (!(sender instanceof Employee) || !(receiver instanceof Employee)) {
            return false;
        }

        String payload = "From " + fromUsername + ": " + message;
        employeeMessages.computeIfAbsent(toUsername, k -> new ArrayList<>()).add(payload);
        return true;
    }

    public List<String> getEmployeeMessages(String username) {
        List<String> messages = employeeMessages.get(username);
        if (messages == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(messages);
    }

    public void submitEmployeeRequest(String employeeUsername, String text) {
        String requestId = "REQ-" + (employeeRequests.size() + 1);
        employeeRequests.add(new EmployeeRequest(requestId, employeeUsername, text, "PENDING", null));
    }

    public List<EmployeeRequest> getEmployeeRequests() {
        return new ArrayList<>(employeeRequests);
    }

    public boolean resolveEmployeeRequest(String requestId, boolean approve, String resolverUsername) {
        for (int i = 0; i < employeeRequests.size(); i++) {
            EmployeeRequest request = employeeRequests.get(i);
            if (request.getRequestId().equals(requestId)) {
                String status = approve ? "APPROVED" : "REJECTED";
                employeeRequests.set(i, new EmployeeRequest(
                        request.getRequestId(),
                        request.getEmployeeUsername(),
                        request.getText(),
                        status,
                        resolverUsername
                ));
                return true;
            }
        }
        return false;
    }

    public void addNews(String title, String body, String author) {
        newsFeed.add(new NewsItem(title, body, author, LocalDate.now()));
    }

    public List<NewsItem> getNewsFeed() {
        return new ArrayList<>(newsFeed);
    }

    public boolean rateTeacher(String studentId, String teacherEmployeeId, int rating) {
        if (rating < 1 || rating > 5) {
            return false;
        }
        if (getStudentById(studentId) == null) {
            return false;
        }

        Teacher target = null;
        for (Teacher teacher : teachers.values()) {
            if (teacher.getEmployeeId().equals(teacherEmployeeId)) {
                target = teacher;
                break;
            }
        }

        if (target == null) {
            return false;
        }

        teacherRatings.computeIfAbsent(teacherEmployeeId, key -> new ArrayList<>()).add(rating);
        return true;
    }

    public double getTeacherAverageRating(String teacherEmployeeId) {
        List<Integer> ratings = teacherRatings.get(teacherEmployeeId);
        if (ratings == null || ratings.isEmpty()) {
            return 0.0;
        }

        int total = 0;
        for (Integer value : ratings) {
            total += value;
        }
        return (double) total / ratings.size();
    }

    // Course registration workflow

    public boolean requestCourseRegistration(String studentId, String courseId) {
        if (getStudentById(studentId) == null || getCourse(courseId) == null) {
            return false;
        }

        pendingRegistrations
                .computeIfAbsent(courseId, key -> new HashSet<>())
                .add(studentId);
        return true;
    }

    public List<PendingRegistration> getPendingRegistrations() {
        List<PendingRegistration> result = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : pendingRegistrations.entrySet()) {
            for (String studentId : entry.getValue()) {
                result.add(new PendingRegistration(studentId, entry.getKey()));
            }
        }
        return result;
    }

    public boolean approveRegistration(String studentId, String courseId) throws CreditLimitException {
        Set<String> requestedStudents = pendingRegistrations.get(courseId);
        if (requestedStudents == null || !requestedStudents.contains(studentId)) {
            return false;
        }

        Student student = getStudentById(studentId);
        Course course = getCourse(courseId);
        if (student == null || course == null) {
            return false;
        }

        student.registerForCourse(courseId, course.getCredits());
        boolean registered = course.registerStudent(studentId);

        if (registered) {
            requestedStudents.remove(studentId);
            if (requestedStudents.isEmpty()) {
                pendingRegistrations.remove(courseId);
            }
            return true;
        }

        return false;
    }

    // Course management methods

    public void addCourse(Course course) {
        courses.put(course.getCourseId(), course);
    }

    public Course getCourse(String courseId) {
        return courses.get(courseId);
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses.values());
    }

    // Mark management methods

    public void addMark(Mark mark) {
        String key = mark.getStudentId() + "_" + mark.getCourseId();
        marks.put(key, mark);
    }

    public Mark getMark(String studentId, String courseId) {
        String key = studentId + "_" + courseId;
        return marks.get(key);
    }

    public List<Mark> getAllMarks() {
        return new ArrayList<>(marks.values());
    }

    public String getMarksAnalyticsReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== MARKS ANALYTICS REPORT ===\n");

        if (marks.isEmpty()) {
            sb.append("No marks available.\n");
            return sb.toString();
        }

        int passCount = 0;
        int failCount = 0;

        double total = 0.0;
        double totalFirst = 0.0;
        double totalSecond = 0.0;
        double totalFinal = 0.0;

        Map<String, Integer> gradeDistribution = new HashMap<>();

        for (Mark mark : marks.values()) {
            if (mark.isPassing()) {
                passCount++;
            } else {
                failCount++;
            }

            total += mark.getTotalMark();
            totalFirst += mark.getFirstAttestation();
            totalSecond += mark.getSecondAttestation();
            totalFinal += mark.getFinalExam();

            gradeDistribution.put(mark.getGrade(), gradeDistribution.getOrDefault(mark.getGrade(), 0) + 1);
        }

        int count = marks.size();
        double passRate = (double) passCount * 100.0 / count;

        sb.append("Total Marks: ").append(count).append("\n");
        sb.append("Pass Count: ").append(passCount).append("\n");
        sb.append("Fail Count: ").append(failCount).append("\n");
        sb.append("Pass Rate: ").append(String.format("%.2f%%", passRate)).append("\n");
        sb.append("Average Total Mark: ").append(String.format("%.2f", total / count)).append("\n");
        sb.append("Average 1st Attestation: ").append(String.format("%.2f", totalFirst / count)).append("\n");
        sb.append("Average 2nd Attestation: ").append(String.format("%.2f", totalSecond / count)).append("\n");
        sb.append("Average Final Exam: ").append(String.format("%.2f", totalFinal / count)).append("\n");

        sb.append("\nGrade Distribution:\n");
        List<String> grades = new ArrayList<>(gradeDistribution.keySet());
        grades.sort(String::compareTo);
        for (String grade : grades) {
            sb.append("  ").append(grade).append(": ").append(gradeDistribution.get(grade)).append("\n");
        }

        return sb.toString();
    }

    // Research management methods

    public void addResearcher(Researcher researcher) {
        researchers.put(researcher.getResearcherId(), researcher);
        mapResearcherToUsername(researcher);
    }

    public Researcher getResearcher(String researcherId) {
        return researchers.get(researcherId);
    }

    public Researcher getResearcherByUsername(String username) {
        String researcherId = userToResearcherId.get(username);
        if (researcherId == null) {
            return null;
        }
        return researchers.get(researcherId);
    }

    public Researcher ensureResearcherForUser(User user) {
        if (user == null) {
            return null;
        }

        Researcher existing = getResearcherByUsername(user.getUsername());
        if (existing != null) {
            return existing;
        }

        Researcher created = null;
        if (user instanceof Teacher) {
            created = new TeacherResearcher((Teacher) user);
        } else if (user instanceof Student) {
            created = new StudentResearcher((Student) user);
        } else if (user instanceof Employee) {
            created = new EmployeeResearcher((Employee) user);
        }

        if (created != null) {
            addResearcher(created);
        }
        return created;
    }

    private void mapResearcherToUsername(Researcher researcher) {
        if (researcher instanceof TeacherResearcher) {
            TeacherResearcher tr = (TeacherResearcher) researcher;
            userToResearcherId.put(tr.getTeacher().getUsername(), researcher.getResearcherId());
        } else if (researcher instanceof StudentResearcher) {
            StudentResearcher sr = (StudentResearcher) researcher;
            userToResearcherId.put(sr.getStudent().getUsername(), researcher.getResearcherId());
        } else if (researcher instanceof EmployeeResearcher) {
            EmployeeResearcher er = (EmployeeResearcher) researcher;
            userToResearcherId.put(er.getEmployee().getUsername(), researcher.getResearcherId());
        }
    }

    public List<Researcher> getAllResearchers() {
        return new ArrayList<>(researchers.values());
    }

    public void addResearchPaper(ResearchPaper paper) {
        researchPapers.put(paper.getDoi(), paper);
    }

    public ResearchPaper getResearchPaper(String doi) {
        return researchPapers.get(doi);
    }

    public List<ResearchPaper> getAllResearchPapers() {
        return new ArrayList<>(researchPapers.values());
    }

    public void addResearchProject(ResearchProject project) {
        researchProjects.put(project.getProjectId(), project);
    }

    public ResearchProject getResearchProject(String projectId) {
        return researchProjects.get(projectId);
    }

    public List<ResearchProject> getAllResearchProjects() {
        return new ArrayList<>(researchProjects.values());
    }

    public Researcher getTopCitedResearcher() {
        Researcher top = null;
        int max = -1;
        for (Researcher researcher : researchers.values()) {
            int totalCitations = researcher.getTotalCitations();
            if (totalCitations > max) {
                max = totalCitations;
                top = researcher;
            }
        }
        return top;
    }

    public Researcher getTopCitedResearcherBySchool(String school) {
        Researcher top = null;
        int max = -1;
        for (Researcher researcher : researchers.values()) {
            if (!matchesSchool(researcher, school)) {
                continue;
            }
            int totalCitations = researcher.getTotalCitations();
            if (totalCitations > max) {
                max = totalCitations;
                top = researcher;
            }
        }
        return top;
    }

    public Researcher getTopCitedResearcherByYear(int year) {
        Researcher top = null;
        int max = -1;
        for (Researcher researcher : researchers.values()) {
            int citationsForYear = getCitationsForYear(researcher, year);
            if (citationsForYear > max) {
                max = citationsForYear;
                top = researcher;
            }
        }
        return top;
    }

    public Researcher getTopCitedResearcherBySchoolAndYear(String school, int year) {
        Researcher top = null;
        int max = -1;
        for (Researcher researcher : researchers.values()) {
            if (!matchesSchool(researcher, school)) {
                continue;
            }
            int citationsForYear = getCitationsForYear(researcher, year);
            if (citationsForYear > max) {
                max = citationsForYear;
                top = researcher;
            }
        }
        return top;
    }

    public String getResearcherSchool(Researcher researcher) {
        if (researcher instanceof TeacherResearcher) {
            return ((TeacherResearcher) researcher).getTeacher().getDepartment();
        }
        if (researcher instanceof StudentResearcher) {
            return ((StudentResearcher) researcher).getStudent().getMajor();
        }
        if (researcher instanceof EmployeeResearcher) {
            return ((EmployeeResearcher) researcher).getEmployee().getDepartment();
        }
        return "Unknown";
    }

    private int getCitationsForYear(Researcher researcher, int year) {
        int total = 0;
        for (ResearchPaper paper : researcher.getResearchPapers()) {
            if (paper.getPublicationDate() != null && paper.getPublicationDate().getYear() == year) {
                total += paper.getCitations();
            }
        }
        return total;
    }

    private boolean matchesSchool(Researcher researcher, String school) {
        if (school == null || school.trim().isEmpty()) {
            return false;
        }
        String researcherSchool = getResearcherSchool(researcher);
        return researcherSchool != null && researcherSchool.equalsIgnoreCase(school.trim());
    }

    // Serialization methods

    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
            System.out.println("Data saved successfully to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    public static DataStorage loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            instance = (DataStorage) ois.readObject();
            System.out.println("Data loaded successfully from " + filename);
            return instance;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data: " + e.getMessage());
            return getInstance(); // Return new instance if loading fails
        }
    }

    // Statistics methods

    public int getUserCount() {
        return users.size();
    }

    public int getCourseCount() {
        return courses.size();
    }

    public int getResearchPaperCount() {
        return researchPapers.size();
    }

    public int getResearchProjectCount() {
        return researchProjects.size();
    }

    public String getStatistics() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== System Statistics ===\n");
        sb.append("Total Users: ").append(getUserCount()).append("\n");
        sb.append("  - Admins: ").append(admins.size()).append("\n");
        sb.append("  - Teachers: ").append(teachers.size()).append("\n");
        sb.append("  - Students: ").append(students.size()).append("\n");
        sb.append("  - Managers: ").append(managers.size()).append("\n");
        sb.append("Total Courses: ").append(getCourseCount()).append("\n");
        sb.append("Total Marks: ").append(marks.size()).append("\n");
        sb.append("Total Research Papers: ").append(getResearchPaperCount()).append("\n");
        sb.append("Total Research Projects: ").append(getResearchProjectCount()).append("\n");
        sb.append("Total Researchers: ").append(researchers.size()).append("\n");
        sb.append("Pending Registrations: ").append(getPendingRegistrations().size()).append("\n");
        sb.append("Employee Requests: ").append(employeeRequests.size()).append("\n");
        sb.append("News Items: ").append(newsFeed.size()).append("\n");
        return sb.toString();
    }
}
