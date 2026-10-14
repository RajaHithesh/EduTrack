package edutrack;

import edutrack.Models.Assignment;
import edutrack.Models.Course;
import edutrack.Models.Enrollment;
import edutrack.Models.Examination;
import edutrack.Models.Faculty;
import edutrack.Models.LearningResource;
import edutrack.Models.SearchResult;
import edutrack.Models.Student;
import edutrack.Models.Submission;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
    private final AcademicService service;
    private final Scanner scanner;
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ConsoleApp() {
        this.service = new AcademicService();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            printHeader();
            System.out.println("1. Student Records");
            System.out.println("2. Faculty Directory");
            System.out.println("3. Course Management");
            System.out.println("4. Student Enrollments");
            System.out.println("5. Assignment Management");
            System.out.println("6. Examination Management");
            System.out.println("7. Academic Timetable");
            System.out.println("8. Learning Resources");
            System.out.println("9. Academic Reports");
            System.out.println("10. System Information");
            System.out.println("11. Exit");
            System.out.print("Choose an option: ");
            String choice = safeReadLine();
            switch (choice) {
                case "1": manageStudents(); break;
                case "2": manageFaculty(); break;
                case "3": manageCourses(); break;
                case "4": manageEnrollments(); break;
                case "5": manageAssignments(); break;
                case "6": manageExaminations(); break;
                case "7": manageTimetable(); break;
                case "8": manageResources(); break;
                case "9": manageReports(); break;
                case "10": showSystemInfo(); break;
                case "11": System.out.println("Thank you for using EduTrack."); return;
                default: System.out.println("Invalid option.");
            }
            System.out.println();
        }
    }

    private void manageStudents() {
        while (true) {
            System.out.println("\nSTUDENT RECORDS");
            System.out.println("1. Register student");
            System.out.println("2. View all students");
            System.out.println("3. Search students");
            System.out.println("4. View student profile");
            System.out.println("5. Update student");
            System.out.println("6. Remove student");
            System.out.println("0. Back");
            System.out.print("Choose an option: ");
            String choice = safeReadLine();
            try {
                switch (choice) {
                    case "1": addStudent(); break;
                    case "2": printStudents(service.getStudents()); break;
                    case "3": searchStudents(); break;
                    case "4": viewStudentProfile(); break;
                    case "5": updateStudent(); break;
                    case "6": deleteStudent(); break;
                    case "0": return;
                    default: System.out.println("Invalid option.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void addStudent() {
        Student s = new Student();
        s.setStudentId(prompt("Student ID"));
        s.setName(prompt("Full name"));
        s.setDepartment(prompt("Department"));
        s.setProgram(prompt("Degree program"));
        s.setYear(readInt("Year of study", 1, 8));
        s.setEmail(prompt("Email"));
        s.setPhone(prompt("Contact number"));
        s.setStatus(prompt("Enrollment status", "Active"));
        service.addStudent(s);
        System.out.println("Student registered successfully.");
    }

    private void searchStudents() {
        String query = prompt("Search by student ID, name, department, program, or email");
        printStudents(service.searchStudents(query));
    }

    private void viewStudentProfile() {
        String id = prompt("Student ID");
        Student student = service.findStudentById(id);
        if (student == null) {
            System.out.println("No student record found for ID: " + id);
            return;
        }
        System.out.println("\nStudent Profile");
        System.out.println("ID: " + student.getStudentId());
        System.out.println("Name: " + student.getName());
        System.out.println("Department: " + student.getDepartment());
        System.out.println("Program: " + student.getProgram());
        System.out.println("Year: " + student.getYear());
        System.out.println("Email: " + student.getEmail());
        System.out.println("Phone: " + student.getPhone());
        System.out.println("Status: " + student.getStatus());
        List<Enrollment> enrollments = service.enrollmentsForStudent(id);
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments on record.");
            return;
        }
        System.out.println("Enrolled courses:");
        for (Enrollment enrollment : enrollments) {
            System.out.println("- " + enrollment.getCourseCode() + " (" + enrollment.getStatus() + ")");
        }
    }

    private void updateStudent() {
        String id = prompt("Student ID to update");
        Student student = service.findStudentById(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        student.setName(promptWithDefault("Full name", student.getName()));
        student.setDepartment(promptWithDefault("Department", student.getDepartment()));
        student.setProgram(promptWithDefault("Degree program", student.getProgram()));
        student.setYear(readIntWithDefault("Year of study", student.getYear(), 1, 8));
        student.setEmail(promptWithDefault("Email", student.getEmail()));
        student.setPhone(promptWithDefault("Contact number", student.getPhone()));
        student.setStatus(promptWithDefault("Enrollment status", student.getStatus()));
        service.updateStudent(student);
        System.out.println("Student record updated successfully.");
    }

    private void deleteStudent() {
        String id = prompt("Student ID to remove");
        if (confirm("Remove this student record?")) {
            service.deleteStudent(id);
            System.out.println("Student record removed successfully.");
        }
    }

    private void manageFaculty() {
        while (true) {
            System.out.println("\nFACULTY DIRECTORY");
            System.out.println("1. Add faculty member");
            System.out.println("2. View faculty directory");
            System.out.println("3. Search faculty");
            System.out.println("4. Update faculty");
            System.out.println("5. Remove faculty");
            System.out.println("0. Back");
            String choice = safeReadLine();
            try {
                switch (choice) {
                    case "1": addFaculty(); break;
                    case "2": printFaculty(service.getFaculties()); break;
                    case "3": searchFaculty(); break;
                    case "4": updateFaculty(); break;
                    case "5": deleteFaculty(); break;
                    case "0": return;
                    default: System.out.println("Invalid option.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void addFaculty() {
        Faculty faculty = new Faculty();
        faculty.setFacultyId(prompt("Faculty ID"));
        faculty.setName(prompt("Full name"));
        faculty.setDepartment(prompt("Department"));
        faculty.setSpecialization(prompt("Specialization"));
        faculty.setDesignation(prompt("Designation"));
        faculty.setEmail(prompt("Email"));
        faculty.setPhone(prompt("Phone"));
        faculty.setAvailability(prompt("Availability", "Available"));
        service.addFaculty(faculty);
        System.out.println("Faculty member added successfully.");
    }

    private void searchFaculty() {
        String query = prompt("Search by faculty ID, name, department, or specialization");
        printFaculty(service.searchFaculty(query));
    }

    private void updateFaculty() {
        String id = prompt("Faculty ID to update");
        Faculty faculty = service.findFacultyById(id);
        if (faculty == null) {
            System.out.println("Faculty not found.");
            return;
        }
        faculty.setName(promptWithDefault("Full name", faculty.getName()));
        faculty.setDepartment(promptWithDefault("Department", faculty.getDepartment()));
        faculty.setSpecialization(promptWithDefault("Specialization", faculty.getSpecialization()));
        faculty.setDesignation(promptWithDefault("Designation", faculty.getDesignation()));
        faculty.setEmail(promptWithDefault("Email", faculty.getEmail()));
        faculty.setPhone(promptWithDefault("Phone", faculty.getPhone()));
        faculty.setAvailability(promptWithDefault("Availability", faculty.getAvailability()));
        service.updateFaculty(faculty);
        System.out.println("Faculty updated successfully.");
    }

    private void deleteFaculty() {
        String id = prompt("Faculty ID to remove");
        if (confirm("Remove this faculty record?")) {
            service.deleteFaculty(id);
            System.out.println("Faculty removed successfully.");
        }
    }

    private void manageCourses() {
        while (true) {
            System.out.println("\nCOURSE MANAGEMENT");
            System.out.println("1. Add course");
            System.out.println("2. View all courses");
            System.out.println("3. Search courses");
            System.out.println("4. Update course");
            System.out.println("5. Remove course");
            System.out.println("6. View enrolled students");
            System.out.println("0. Back");
            String choice = safeReadLine();
            try {
                switch (choice) {
                    case "1": addCourse(); break;
                    case "2": printCourses(service.getCourses()); break;
                    case "3": searchCourses(); break;
                    case "4": updateCourse(); break;
                    case "5": deleteCourse(); break;
                    case "6": viewCourseEnrolledStudents(); break;
                    case "0": return;
                    default: System.out.println("Invalid option.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void addCourse() {
        Course course = new Course();
        course.setCourseCode(prompt("Course code"));
        course.setTitle(prompt("Course title"));
        course.setDepartment(prompt("Department"));
        course.setCredits(readInt("Credits", 1, 12));
        course.setSemester(readInt("Semester", 1, 12));
        course.setCapacity(readInt("Enrollment capacity", 1, 500));
        course.setDescription(prompt("Course description"));
        service.addCourse(course);
        System.out.println("Course added successfully.");
    }

    private void searchCourses() {
        String query = prompt("Search by code, title, department, or description");
        printCourses(service.searchCourses(query));
    }

    private void updateCourse() {
        String code = prompt("Course code to update");
        Course course = service.findCourseByCode(code);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }
        course.setTitle(promptWithDefault("Course title", course.getTitle()));
        course.setDepartment(promptWithDefault("Department", course.getDepartment()));
        course.setCredits(readIntWithDefault("Credits", course.getCredits(), 1, 12));
        course.setSemester(readIntWithDefault("Semester", course.getSemester(), 1, 12));
        course.setCapacity(readIntWithDefault("Enrollment capacity", course.getCapacity(), 1, 500));
        course.setDescription(promptWithDefault("Course description", course.getDescription()));
        service.updateCourse(course);
        System.out.println("Course updated successfully.");
    }

    private void deleteCourse() {
        String code = prompt("Course code to remove");
        if (confirm("Delete this course?")) {
            service.deleteCourse(code);
            System.out.println("Course removed successfully.");
        }
    }

    private void viewCourseEnrolledStudents() {
        String code = prompt("Course code");
        Course course = service.findCourseByCode(code);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }
        List<String> names = service.getCourseStudentNames(code);
        if (names.isEmpty()) {
            System.out.println("No students are enrolled in this course.");
            return;
        }
        System.out.println("Students enrolled in " + course.getTitle() + ":");
        for (String name : names) {
            System.out.println("- " + name);
        }
    }

    private void manageEnrollments() {
        while (true) {
            System.out.println("\nSTUDENT ENROLLMENTS");
            System.out.println("1. Enroll a student");
            System.out.println("2. View student enrollments");
            System.out.println("3. View course enrollment list");
            System.out.println("4. Withdraw enrollment");
            System.out.println("0. Back");
            String choice = safeReadLine();
            try {
                switch (choice) {
                    case "1": enrollStudent(); break;
                    case "2": viewStudentEnrollments(); break;
                    case "3": viewCourseEnrollments(); break;
                    case "4": withdrawEnrollment(); break;
                    case "0": return;
                    default: System.out.println("Invalid option.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void enrollStudent() {
        String studentId = prompt("Student ID");
        String courseCode = prompt("Course code");
        service.addEnrollment(studentId, courseCode);
        System.out.println("Enrollment recorded successfully.");
    }

    private void viewStudentEnrollments() {
        String studentId = prompt("Student ID");
        List<Enrollment> list = service.enrollmentsForStudent(studentId);
        if (list.isEmpty()) {
            System.out.println("No enrollments found for this student.");
            return;
        }
        System.out.println("Student enrollments: ");
        for (Enrollment e : list) {
            System.out.println("- " + e.getCourseCode() + " on " + e.getEnrollmentDate().format(dateFormat));
        }
    }

    private void viewCourseEnrollments() {
        String courseCode = prompt("Course code");
        List<Enrollment> list = service.enrollmentsForCourse(courseCode);
        if (list.isEmpty()) {
            System.out.println("No students are enrolled in this course.");
            return;
        }
        System.out.println("Course enrollments:");
        for (Enrollment e : list) {
            Student student = service.findStudentById(e.getStudentId());
            System.out.println("- " + (student != null ? student.getName() : e.getStudentId()) + " (" + e.getStudentId() + ")");
        }
    }

    private void withdrawEnrollment() {
        String studentId = prompt("Student ID");
        String courseCode = prompt("Course code");
        service.withdrawEnrollment(studentId, courseCode);
        System.out.println("Enrollment withdrawn successfully.");
    }

    private void manageAssignments() {
        while (true) {
            System.out.println("\nASSIGNMENT MANAGEMENT");
            System.out.println("1. Create assignment");
            System.out.println("2. View assignments by course");
            System.out.println("3. Record submission");
            System.out.println("4. View pending submissions");
            System.out.println("5. Similarity check");
            System.out.println("0. Back");
            String choice = safeReadLine();
            try {
                switch (choice) {
                    case "1": addAssignment(); break;
                    case "2": viewAssignmentsByCourse(); break;
                    case "3": recordSubmission(); break;
                    case "4": viewPendingAssignments(); break;
                    case "5": runTextSimilarity(); break;
                    case "0": return;
                    default: System.out.println("Invalid option.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void addAssignment() {
        Assignment assignment = new Assignment();
        assignment.setAssignmentId(prompt("Assignment ID"));
        assignment.setCourseCode(prompt("Course code"));
        assignment.setTitle(prompt("Assignment title"));
        assignment.setDescription(prompt("Assignment description"));
        assignment.setDueDate(LocalDate.parse(prompt("Due date (yyyy-MM-dd)"), dateFormat));
        assignment.setMaxMarks(readInt("Maximum marks", 1, 1000));
        service.addAssignment(assignment);
        System.out.println("Assignment created successfully.");
    }

    private void viewAssignmentsByCourse() {
        String code = prompt("Course code");
        List<Assignment> list = service.assignmentsForCourse(code);
        if (list.isEmpty()) {
            System.out.println("No assignments for this course.");
            return;
        }
        System.out.println("Assignments for course: " + code);
        for (Assignment a : list) {
            System.out.println("- " + a.getAssignmentId() + " | " + a.getTitle() + " | Due: " + a.getDueDate().format(dateFormat));
        }
    }

    private void recordSubmission() {
        Submission submission = new Submission();
        submission.setSubmissionId(prompt("Submission ID"));
        submission.setAssignmentId(prompt("Assignment ID"));
        submission.setStudentId(prompt("Student ID"));
        submission.setSubmissionText(prompt("Submitted text or summary"));
        submission.setSubmittedDate(LocalDate.parse(prompt("Submission date (yyyy-MM-dd)"), dateFormat));
        submission.setMarks(readDouble("Marks obtained", 0.0, 1000.0));
        service.recordSubmission(submission);
        System.out.println("Submission recorded successfully.");
    }

    private void viewPendingAssignments() {
        List<Assignment> pending = service.getPendingAssignments();
        if (pending.isEmpty()) {
            System.out.println("No pending work found.");
            return;
        }
        System.out.println("Pending or overdue assignments");
        for (Assignment a : pending) {
            System.out.println("- " + a.getAssignmentId() + " | " + a.getTitle() + " | Course: " + a.getCourseCode() + " | Due: " + a.getDueDate().format(dateFormat));
        }
    }

    private void runTextSimilarity() {
        String phrase = prompt("Enter text to compare against stored assignment text");
        List<SearchResult> matches = service.getSimilarTextMatches(phrase);
        if (matches.isEmpty()) {
            System.out.println("No strong text matches found.");
            return;
        }
        System.out.println("Text similarity results");
        for (SearchResult result : matches) {
            System.out.println("- " + result.getLabel() + " (" + result.getId() + ") - " + result.getDetail());
        }
    }

    private void manageExaminations() {
        while (true) {
            System.out.println("\nEXAMINATION MANAGEMENT");
            System.out.println("1. Create examination");
            System.out.println("2. View examinations by course");
            System.out.println("3. View conflict report");
            System.out.println("4. Update examination");
            System.out.println("5. Cancel examination");
            System.out.println("0. Back");
            String choice = safeReadLine();
            try {
                switch (choice) {
                    case "1": addExamination(); break;
                    case "2": viewExaminationsByCourse(); break;
                    case "3": viewExamConflictReport(); break;
                    case "4": updateExamination(); break;
                    case "5": cancelExamination(); break;
                    case "0": return;
                    default: System.out.println("Invalid option.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void addExamination() {
        Examination examination = new Examination();
        examination.setExaminationId(prompt("Examination ID"));
        examination.setCourseCode(prompt("Course code"));
        examination.setDepartment(prompt("Department"));
        examination.setExamDate(LocalDate.parse(prompt("Exam date (yyyy-MM-dd)"), dateFormat));
        examination.setStartTime(prompt("Start time (HH:mm)"));
        examination.setDurationMinutes(readInt("Duration in minutes", 30, 480));
        examination.setVenue(prompt("Venue"));
        examination.setStatus("Scheduled");
        service.addExamination(examination);
        System.out.println("Examination scheduled successfully.");
    }

    private void viewExaminationsByCourse() {
        String code = prompt("Course code");
        List<Examination> list = service.getExaminationsForCourse(code);
        if (list.isEmpty()) {
            System.out.println("No examinations for this course.");
            return;
        }
        for (Examination e : list) {
            System.out.println("- " + e.getExaminationId() + " | " + e.getExamDate().format(dateFormat) + " | " + e.getStartTime() + " | " + e.getVenue());
        }
    }

    private void viewExamConflictReport() {
        List<String> conflicts = service.getExamConflictReport();
        if (conflicts.isEmpty()) {
            System.out.println("No examination conflicts identified.");
        } else {
            for (String conflict : conflicts) {
                System.out.println(conflict);
            }
        }
    }

    private void updateExamination() {
        String id = prompt("Examination ID");
        Examination exam = service.findExaminationById(id);
        if (exam == null) {
            System.out.println("Examination not found.");
            return;
        }
        exam.setCourseCode(promptWithDefault("Course code", exam.getCourseCode()));
        exam.setDepartment(promptWithDefault("Department", exam.getDepartment()));
        exam.setExamDate(LocalDate.parse(promptWithDefault("Exam date (yyyy-MM-dd)", exam.getExamDate().format(dateFormat)), dateFormat));
        exam.setStartTime(promptWithDefault("Start time (HH:mm)", exam.getStartTime()));
        exam.setDurationMinutes(readIntWithDefault("Duration in minutes", exam.getDurationMinutes(), 30, 480));
        exam.setVenue(promptWithDefault("Venue", exam.getVenue()));
        service.updateExamination(exam);
        System.out.println("Examination updated successfully.");
    }

    private void cancelExamination() {
        String id = prompt("Examination ID");
        if (confirm("Cancel this examination?")) {
            service.cancelExamination(id);
            System.out.println("Examination cancelled.");
        }
    }

    private void manageTimetable() {
        while (true) {
            System.out.println("\nACADEMIC TIMETABLE");
            System.out.println("1. Assign faculty to course");
            System.out.println("2. View faculty assignments");
            System.out.println("3. View timetable summary");
            System.out.println("0. Back");
            String choice = safeReadLine();
            try {
                switch (choice) {
                    case "1": assignFacultyToCourse(); break;
                    case "2": viewFacultyAssignments(); break;
                    case "3": timetableSummary(); break;
                    case "0": return;
                    default: System.out.println("Invalid option.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void assignFacultyToCourse() {
        String facultyId = prompt("Faculty ID");
        String courseCode = prompt("Course code");
        service.assignFacultyToCourse(facultyId, courseCode);
        System.out.println("Faculty assigned to course successfully.");
    }

    private void viewFacultyAssignments() {
        if (service.getFacultyAssignments().isEmpty()) {
            System.out.println("No faculty-course assignments recorded.");
            return;
        }
        for (var assignment : service.getFacultyAssignments()) {
            System.out.println("- " + assignment.getFacultyId() + " -> " + assignment.getCourseCode() + " (" + assignment.getSemester() + ")");
        }
    }

    private void timetableSummary() {
        List<SearchResult> summary = service.getFacultyCourseSummary();
        if (summary.isEmpty()) {
            System.out.println("No assignments available.");
            return;
        }
        System.out.println("Faculty workload summary");
        for (SearchResult item : summary) {
            System.out.println("- " + item.getLabel() + " : " + item.getDetail() + " assigned courses");
        }
    }

    private void manageResources() {
        while (true) {
            System.out.println("\nLEARNING RESOURCES");
            System.out.println("1. Add resource");
            System.out.println("2. View resource inventory");
            System.out.println("3. Search resources");
            System.out.println("4. Update resource");
            System.out.println("5. Remove resource");
            System.out.println("0. Back");
            String choice = safeReadLine();
            try {
                switch (choice) {
                    case "1": addResource(); break;
                    case "2": viewResources(); break;
                    case "3": searchResources(); break;
                    case "4": updateResource(); break;
                    case "5": deleteResource(); break;
                    case "0": return;
                    default: System.out.println("Invalid option.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void addResource() {
        LearningResource resource = new LearningResource();
        resource.setResourceId(prompt("Resource ID"));
        resource.setTitle(prompt("Title"));
        resource.setCourseCode(prompt("Course code"));
        resource.setAuthor(prompt("Author"));
        resource.setResourceType(prompt("Resource type"));
        resource.setUrl(prompt("URL or link"));
        resource.setSummary(prompt("Summary"));
        resource.setKeywords(prompt("Keywords"));
        service.addResource(resource);
        System.out.println("Learning resource added successfully.");
    }

    private void viewResources() {
        if (service.getResources().isEmpty()) {
            System.out.println("No learning resources registered.");
            return;
        }
        for (LearningResource resource : service.getResources()) {
            System.out.println("- " + resource.getTitle() + " | " + resource.getCourseCode() + " | " + resource.getResourceType());
        }
    }

    private void searchResources() {
        String query = prompt("Search by title, course, author, type, or keyword");
        List<LearningResource> matches = service.searchResources(query);
        if (matches.isEmpty()) {
            System.out.println("No matching resources found.");
            return;
        }
        for (LearningResource resource : matches) {
            System.out.println("- " + resource.getTitle() + " | " + resource.getCourseCode() + " | " + resource.getAuthor());
        }
    }

    private void updateResource() {
        String id = prompt("Resource ID");
        LearningResource resource = service.findResourceById(id);
        if (resource == null) {
            System.out.println("Resource not found.");
            return;
        }
        resource.setTitle(promptWithDefault("Title", resource.getTitle()));
        resource.setCourseCode(promptWithDefault("Course code", resource.getCourseCode()));
        resource.setAuthor(promptWithDefault("Author", resource.getAuthor()));
        resource.setResourceType(promptWithDefault("Resource type", resource.getResourceType()));
        resource.setUrl(promptWithDefault("URL or link", resource.getUrl()));
        resource.setSummary(promptWithDefault("Summary", resource.getSummary()));
        resource.setKeywords(promptWithDefault("Keywords", resource.getKeywords()));
        service.updateResource(resource);
        System.out.println("Resource updated successfully.");
    }

    private void deleteResource() {
        String id = prompt("Resource ID");
        if (confirm("Delete this learning resource?")) {
            service.deleteResource(id);
            System.out.println("Resource deleted successfully.");
        }
    }

    private void manageReports() {
        System.out.println("\nACADEMIC REPORTS");
        System.out.println("1. Academic summary");
        System.out.println("2. Course enrollment summary");
        System.out.println("3. Faculty assignment summary");
        System.out.println("4. Examination conflict report");
        System.out.println("5. Student marks overview");
        System.out.println("0. Back");
        String choice = safeReadLine();
        try {
            switch (choice) {
                case "1": showAcademicSummary(); break;
                case "2": showCourseEnrollmentSummary(); break;
                case "3": showFacultySummary(); break;
                case "4": viewExamConflictReport(); break;
                case "5": showMarksOverview(); break;
                case "0": return;
                default: System.out.println("Invalid option.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void showAcademicSummary() {
        List<SearchResult> stats = service.getAcademicReport();
        for (SearchResult result : stats) {
            System.out.println(result.getLabel() + ": " + result.getDetail());
        }
    }

    private void showCourseEnrollmentSummary() {
        List<SearchResult> summary = service.getCourseEnrollmentSummary();
        for (SearchResult item : summary) {
            System.out.println(item.getId() + " | " + item.getLabel() + " | " + item.getDetail());
        }
    }

    private void showFacultySummary() {
        List<SearchResult> summary = service.getFacultyCourseSummary();
        for (SearchResult item : summary) {
            System.out.println(item.getId() + " | " + item.getLabel() + " | " + item.getDetail());
        }
    }

    private void showMarksOverview() {
        if (service.getSubmissions().isEmpty()) {
            System.out.println("No submission records available.");
            return;
        }
        double total = 0;
        for (Submission submission : service.getSubmissions()) {
            if (submission.getMarks() != null) total += submission.getMarks();
        }
        System.out.println("Average marks across submissions: " + (total / service.getSubmissions().size()));
        for (Submission submission : service.getSubmissions()) {
            Assignment assignment = service.findAssignmentById(submission.getAssignmentId());
            String label = assignment != null ? assignment.getTitle() : submission.getAssignmentId();
            System.out.println(submission.getStudentId() + " | " + label + " | " + submission.getMarks() + " | " + submission.getStatus());
        }
    }

    private void showSystemInfo() {
        System.out.println("EDUTRACK — UNIVERSITY ACADEMIC MANAGEMENT SYSTEM");
        System.out.println("Data directory: data/");
        System.out.println("Records loaded: " + service.getStudents().size() + " students, " + service.getCourses().size() + " courses, " + service.getFacultyAssignments().size() + " faculty assignments.");
    }

    private void printHeader() {
        System.out.println("\n====================================================");
        System.out.println("EDUTRACK — UNIVERSITY ACADEMIC MANAGEMENT SYSTEM");
        System.out.println("====================================================");
    }

    private void printStudents(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }
        String[][] rows = new String[students.size() + 1][];
        rows[0] = new String[]{"ID", "Name", "Department", "Program", "Year", "Email"};
        int index = 1;
        for (Student student : students) {
            rows[index++] = new String[]{student.getStudentId(), student.getName(), student.getDepartment(), student.getProgram(), String.valueOf(student.getYear()), student.getEmail()};
        }
        printTable(rows);
    }

    private void printFaculty(List<Faculty> facultyList) {
        if (facultyList.isEmpty()) {
            System.out.println("No faculty records found.");
            return;
        }
        String[][] rows = new String[facultyList.size() + 1][];
        rows[0] = new String[]{"ID", "Name", "Department", "Specialization", "Availability"};
        int index = 1;
        for (Faculty faculty : facultyList) {
            rows[index++] = new String[]{faculty.getFacultyId(), faculty.getName(), faculty.getDepartment(), faculty.getSpecialization(), faculty.getAvailability()};
        }
        printTable(rows);
    }

    private void printCourses(List<Course> courseList) {
        if (courseList.isEmpty()) {
            System.out.println("No course records found.");
            return;
        }
        String[][] rows = new String[courseList.size() + 1][];
        rows[0] = new String[]{"Code", "Title", "Department", "Credits", "Capacity"};
        int index = 1;
        for (Course course : courseList) {
            rows[index++] = new String[]{course.getCourseCode(), course.getTitle(), course.getDepartment(), String.valueOf(course.getCredits()), String.valueOf(course.getCapacity())};
        }
        printTable(rows);
    }

    private void printTable(String[][] rows) {
        if (rows == null || rows.length == 0) {
            System.out.println("No data found.");
            return;
        }
        int columns = rows[0].length;
        int[] widths = new int[columns];
        for (String[] row : rows) {
            for (int i = 0; i < columns; i++) {
                String value = row[i] == null ? "" : row[i];
                widths[i] = Math.max(widths[i], value.length());
            }
        }

        StringBuilder border = new StringBuilder();
        for (int width : widths) {
            border.append("-").append("-".repeat(Math.max(0, width + 2)));
        }
        border.append("-");

        System.out.println(border);
        for (int i = 0; i < rows.length; i++) {
            String[] row = rows[i];
            StringBuilder current = new StringBuilder();
            for (int j = 0; j < columns; j++) {
                String value = row[j] == null ? "" : row[j];
                current.append("| ").append(String.format("%-" + widths[j] + "s", value)).append(" ");
            }
            current.append("|");
            System.out.println(current);
            if (i == 0) {
                System.out.println(border);
            }
        }
        System.out.println(border);
    }

    private String prompt(String label) {
        System.out.print(label + ": ");
        return safeReadLine();
    }

    private String prompt(String label, String defaultValue) {
        System.out.print(label + " [" + defaultValue + "] : ");
        String value = safeReadLine();
        return value == null || value.trim().isEmpty() ? defaultValue : value.trim();
    }

    private String promptWithDefault(String label, String defaultValue) {
        return prompt(label, defaultValue);
    }

    private int readInt(String label, int min, int max) {
        while (true) {
            System.out.print(label + " (" + min + "-" + max + "): ");
            String input = safeReadLine();
            try {
                int value = Integer.parseInt(input.trim());
                if (value < min || value > max) {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                    continue;
                }
                return value;
            } catch (Exception e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    private int readIntWithDefault(String label, int defaultValue, int min, int max) {
        System.out.print(label + " [" + defaultValue + "] (" + min + "-" + max + "): ");
        String input = safeReadLine();
        if (input == null || input.trim().isEmpty()) return defaultValue;
        try {
            int value = Integer.parseInt(input.trim());
            if (value < min || value > max) throw new NumberFormatException();
            return value;
        } catch (Exception e) {
            System.out.println("Invalid value. Using current value: " + defaultValue);
            return defaultValue;
        }
    }

    private double readDouble(String label, double min, double max) {
        while (true) {
            System.out.print(label + " (" + min + "-" + max + "): ");
            String input = safeReadLine();
            try {
                double value = Double.parseDouble(input.trim());
                if (value < min || value > max) {
                    System.out.println("Please enter a value between " + min + " and " + max + ".");
                    continue;
                }
                return value;
            } catch (Exception e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    private boolean confirm(String message) {
        System.out.print(message + " (Y/N): ");
        String answer = safeReadLine();
        return answer != null && answer.equalsIgnoreCase("Y");
    }

    private String safeReadLine() {
        if (!scanner.hasNextLine()) return "";
        return scanner.nextLine().trim();
    }
}
