package edutrack;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Models {
    public static class Student {
        private String studentId;
        private String name;
        private String department;
        private String program;
        private int year;
        private String email;
        private String phone;
        private String status;

        public Student() {}

        public Student(String studentId, String name, String department, String program, int year, String email, String phone, String status) {
            this.studentId = studentId;
            this.name = name;
            this.department = department;
            this.program = program;
            this.year = year;
            this.email = email;
            this.phone = phone;
            this.status = status;
        }

        public static String[] headers() { return new String[]{"studentId","name","department","program","year","email","phone","status"}; }

        public String[] toCsvRow() { return new String[]{studentId,name,department,program,String.valueOf(year),email,phone,status}; }

        public static Student fromCsvRow(String[] row) {
            if (row == null || row.length < 8) return null;
            Student s = new Student();
            s.studentId = row[0] == null ? "" : row[0].trim();
            s.name = row[1] == null ? "" : row[1].trim();
            s.department = row[2] == null ? "" : row[2].trim();
            s.program = row[3] == null ? "" : row[3].trim();
            s.year = parseInt(row[4], 1);
            s.email = row[5] == null ? "" : row[5].trim();
            s.phone = row[6] == null ? "" : row[6].trim();
            s.status = row[7] == null ? "" : row[7].trim();
            return s;
        }

        private static int parseInt(String value, int fallback) { try { return Integer.parseInt(value.trim()); } catch (Exception e) { return fallback; } }

        public String getStudentId() { return studentId; }
        public void setStudentId(String studentId) { this.studentId = studentId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }
        public String getProgram() { return program; }
        public void setProgram(String program) { this.program = program; }
        public int getYear() { return year; }
        public void setYear(int year) { this.year = year; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    public static class Faculty {
        private String facultyId;
        private String name;
        private String department;
        private String specialization;
        private String designation;
        private String email;
        private String phone;
        private String availability;

        public Faculty() {}

        public Faculty(String facultyId, String name, String department, String specialization, String designation, String email, String phone, String availability) {
            this.facultyId = facultyId;
            this.name = name;
            this.department = department;
            this.specialization = specialization;
            this.designation = designation;
            this.email = email;
            this.phone = phone;
            this.availability = availability;
        }

        public static String[] headers() { return new String[]{"facultyId","name","department","specialization","designation","email","phone","availability"}; }

        public String[] toCsvRow() { return new String[]{facultyId,name,department,specialization,designation,email,phone,availability}; }

        public static Faculty fromCsvRow(String[] row) {
            if (row == null || row.length < 8) return null;
            Faculty f = new Faculty();
            f.facultyId = row[0] == null ? "" : row[0].trim();
            f.name = row[1] == null ? "" : row[1].trim();
            f.department = row[2] == null ? "" : row[2].trim();
            f.specialization = row[3] == null ? "" : row[3].trim();
            f.designation = row[4] == null ? "" : row[4].trim();
            f.email = row[5] == null ? "" : row[5].trim();
            f.phone = row[6] == null ? "" : row[6].trim();
            f.availability = row[7] == null ? "" : row[7].trim();
            return f;
        }

        public String getFacultyId() { return facultyId; }
        public void setFacultyId(String facultyId) { this.facultyId = facultyId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }
        public String getSpecialization() { return specialization; }
        public void setSpecialization(String specialization) { this.specialization = specialization; }
        public String getDesignation() { return designation; }
        public void setDesignation(String designation) { this.designation = designation; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getAvailability() { return availability; }
        public void setAvailability(String availability) { this.availability = availability; }
    }

    public static class Course {
        private String courseCode;
        private String title;
        private String department;
        private int credits;
        private int semester;
        private int capacity;
        private String description;

        public Course() {}

        public Course(String courseCode, String title, String department, int credits, int semester, int capacity, String description) {
            this.courseCode = courseCode;
            this.title = title;
            this.department = department;
            this.credits = credits;
            this.semester = semester;
            this.capacity = capacity;
            this.description = description;
        }

        public static String[] headers() { return new String[]{"courseCode","title","department","credits","semester","capacity","description"}; }

        public String[] toCsvRow() { return new String[]{courseCode,title,department,String.valueOf(credits),String.valueOf(semester),String.valueOf(capacity),description}; }

        public static Course fromCsvRow(String[] row) {
            if (row == null || row.length < 7) return null;
            Course c = new Course();
            c.courseCode = row[0] == null ? "" : row[0].trim();
            c.title = row[1] == null ? "" : row[1].trim();
            c.department = row[2] == null ? "" : row[2].trim();
            c.credits = parseInt(row[3], 1);
            c.semester = parseInt(row[4], 1);
            c.capacity = parseInt(row[5], 1);
            c.description = row[6] == null ? "" : row[6].trim();
            return c;
        }

        private static int parseInt(String value, int fallback) {
            try { return Integer.parseInt(value.trim()); } catch (Exception e) { return fallback; }
        }

        public String getCourseCode() { return courseCode; }
        public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }
        public int getCredits() { return credits; }
        public void setCredits(int credits) { this.credits = credits; }
        public int getSemester() { return semester; }
        public void setSemester(int semester) { this.semester = semester; }
        public int getCapacity() { return capacity; }
        public void setCapacity(int capacity) { this.capacity = capacity; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    public static class Enrollment {
        private String enrollmentId;
        private String studentId;
        private String courseCode;
        private LocalDate enrollmentDate;
        private String status;

        public Enrollment() {}

        public Enrollment(String enrollmentId, String studentId, String courseCode, LocalDate enrollmentDate, String status) {
            this.enrollmentId = enrollmentId;
            this.studentId = studentId;
            this.courseCode = courseCode;
            this.enrollmentDate = enrollmentDate;
            this.status = status;
        }

        public static String[] headers() { return new String[]{"enrollmentId","studentId","courseCode","enrollmentDate","status"}; }

        public String[] toCsvRow() { return new String[]{enrollmentId,studentId,courseCode,enrollmentDate.toString(),status}; }

        public static Enrollment fromCsvRow(String[] row) {
            if (row == null || row.length < 5) return null;
            Enrollment e = new Enrollment();
            e.enrollmentId = row[0] == null ? "" : row[0].trim();
            e.studentId = row[1] == null ? "" : row[1].trim();
            e.courseCode = row[2] == null ? "" : row[2].trim();
            e.enrollmentDate = LocalDate.parse(row[3].trim());
            e.status = row[4] == null ? "" : row[4].trim();
            return e;
        }

        public String getEnrollmentId() { return enrollmentId; }
        public void setEnrollmentId(String enrollmentId) { this.enrollmentId = enrollmentId; }
        public String getStudentId() { return studentId; }
        public void setStudentId(String studentId) { this.studentId = studentId; }
        public String getCourseCode() { return courseCode; }
        public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
        public LocalDate getEnrollmentDate() { return enrollmentDate; }
        public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    public static class Assignment {
        private String assignmentId;
        private String courseCode;
        private String title;
        private String description;
        private LocalDate dueDate;
        private int maxMarks;

        public Assignment() {}

        public Assignment(String assignmentId, String courseCode, String title, String description, LocalDate dueDate, int maxMarks) {
            this.assignmentId = assignmentId;
            this.courseCode = courseCode;
            this.title = title;
            this.description = description;
            this.dueDate = dueDate;
            this.maxMarks = maxMarks;
        }

        public static String[] headers() { return new String[]{"assignmentId","courseCode","title","description","dueDate","maxMarks"}; }

        public String[] toCsvRow() { return new String[]{assignmentId,courseCode,title,description,dueDate.toString(),String.valueOf(maxMarks)}; }

        public static Assignment fromCsvRow(String[] row) {
            if (row == null || row.length < 6) return null;
            Assignment a = new Assignment();
            a.assignmentId = row[0] == null ? "" : row[0].trim();
            a.courseCode = row[1] == null ? "" : row[1].trim();
            a.title = row[2] == null ? "" : row[2].trim();
            a.description = row[3] == null ? "" : row[3].trim();
            a.dueDate = LocalDate.parse(row[4].trim());
            a.maxMarks = parseInt(row[5], 100);
            return a;
        }

        private static int parseInt(String value, int fallback) { try { return Integer.parseInt(value.trim()); } catch (Exception e) { return fallback; } }

        public String getAssignmentId() { return assignmentId; }
        public void setAssignmentId(String assignmentId) { this.assignmentId = assignmentId; }
        public String getCourseCode() { return courseCode; }
        public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public LocalDate getDueDate() { return dueDate; }
        public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
        public int getMaxMarks() { return maxMarks; }
        public void setMaxMarks(int maxMarks) { this.maxMarks = maxMarks; }
    }

    public static class Submission {
        private String submissionId;
        private String assignmentId;
        private String studentId;
        private String submissionText;
        private LocalDate submittedDate;
        private Double marks;
        private String status;

        public Submission() {}

        public Submission(String submissionId, String assignmentId, String studentId, String submissionText, LocalDate submittedDate, Double marks, String status) {
            this.submissionId = submissionId;
            this.assignmentId = assignmentId;
            this.studentId = studentId;
            this.submissionText = submissionText;
            this.submittedDate = submittedDate;
            this.marks = marks;
            this.status = status;
        }

        public static String[] headers() { return new String[]{"submissionId","assignmentId","studentId","submissionText","submittedDate","marks","status"}; }

        public String[] toCsvRow() { return new String[]{submissionId,assignmentId,studentId,submissionText,submittedDate.toString(),String.valueOf(marks),status}; }

        public static Submission fromCsvRow(String[] row) {
            if (row == null || row.length < 7) return null;
            Submission s = new Submission();
            s.submissionId = row[0] == null ? "" : row[0].trim();
            s.assignmentId = row[1] == null ? "" : row[1].trim();
            s.studentId = row[2] == null ? "" : row[2].trim();
            s.submissionText = row[3] == null ? "" : row[3].trim();
            s.submittedDate = LocalDate.parse(row[4].trim());
            s.marks = parseDouble(row[5], 0.0);
            s.status = row[6] == null ? "" : row[6].trim();
            return s;
        }

        private static double parseDouble(String value, double fallback) { try { return Double.parseDouble(value.trim()); } catch (Exception e) { return fallback; } }

        public String getSubmissionId() { return submissionId; }
        public void setSubmissionId(String submissionId) { this.submissionId = submissionId; }
        public String getAssignmentId() { return assignmentId; }
        public void setAssignmentId(String assignmentId) { this.assignmentId = assignmentId; }
        public String getStudentId() { return studentId; }
        public void setStudentId(String studentId) { this.studentId = studentId; }
        public String getSubmissionText() { return submissionText; }
        public void setSubmissionText(String submissionText) { this.submissionText = submissionText; }
        public LocalDate getSubmittedDate() { return submittedDate; }
        public void setSubmittedDate(LocalDate submittedDate) { this.submittedDate = submittedDate; }
        public Double getMarks() { return marks; }
        public void setMarks(Double marks) { this.marks = marks; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    public static class Examination {
        private String examinationId;
        private String courseCode;
        private String department;
        private LocalDate examDate;
        private String startTime;
        private int durationMinutes;
        private String venue;
        private String status;

        public Examination() {}

        public Examination(String examinationId, String courseCode, String department, LocalDate examDate, String startTime, int durationMinutes, String venue, String status) {
            this.examinationId = examinationId;
            this.courseCode = courseCode;
            this.department = department;
            this.examDate = examDate;
            this.startTime = startTime;
            this.durationMinutes = durationMinutes;
            this.venue = venue;
            this.status = status;
        }

        public static String[] headers() { return new String[]{"examinationId","courseCode","department","examDate","startTime","durationMinutes","venue","status"}; }

        public String[] toCsvRow() { return new String[]{examinationId,courseCode,department,examDate.toString(),startTime,String.valueOf(durationMinutes),venue,status}; }

        public static Examination fromCsvRow(String[] row) {
            if (row == null || row.length < 8) return null;
            Examination e = new Examination();
            e.examinationId = row[0] == null ? "" : row[0].trim();
            e.courseCode = row[1] == null ? "" : row[1].trim();
            e.department = row[2] == null ? "" : row[2].trim();
            e.examDate = LocalDate.parse(row[3].trim());
            e.startTime = row[4] == null ? "" : row[4].trim();
            e.durationMinutes = parseInt(row[5], 90);
            e.venue = row[6] == null ? "" : row[6].trim();
            e.status = row[7] == null ? "" : row[7].trim();
            return e;
        }

        private static int parseInt(String value, int fallback) { try { return Integer.parseInt(value.trim()); } catch (Exception e) { return fallback; } }

        public String getExaminationId() { return examinationId; }
        public void setExaminationId(String examinationId) { this.examinationId = examinationId; }
        public String getCourseCode() { return courseCode; }
        public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }
        public LocalDate getExamDate() { return examDate; }
        public void setExamDate(LocalDate examDate) { this.examDate = examDate; }
        public String getStartTime() { return startTime; }
        public void setStartTime(String startTime) { this.startTime = startTime; }
        public int getDurationMinutes() { return durationMinutes; }
        public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
        public String getVenue() { return venue; }
        public void setVenue(String venue) { this.venue = venue; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    public static class FacultyCourseAssignment {
        private String assignmentId;
        private String facultyId;
        private String courseCode;
        private String semester;
        private String status;

        public FacultyCourseAssignment() {}

        public FacultyCourseAssignment(String assignmentId, String facultyId, String courseCode, String semester, String status) {
            this.assignmentId = assignmentId;
            this.facultyId = facultyId;
            this.courseCode = courseCode;
            this.semester = semester;
            this.status = status;
        }

        public static String[] headers() { return new String[]{"assignmentId","facultyId","courseCode","semester","status"}; }

        public String[] toCsvRow() { return new String[]{assignmentId,facultyId,courseCode,semester,status}; }

        public static FacultyCourseAssignment fromCsvRow(String[] row) {
            if (row == null || row.length < 5) return null;
            FacultyCourseAssignment a = new FacultyCourseAssignment();
            a.assignmentId = row[0] == null ? "" : row[0].trim();
            a.facultyId = row[1] == null ? "" : row[1].trim();
            a.courseCode = row[2] == null ? "" : row[2].trim();
            a.semester = row[3] == null ? "" : row[3].trim();
            a.status = row[4] == null ? "" : row[4].trim();
            return a;
        }

        public String getAssignmentId() { return assignmentId; }
        public void setAssignmentId(String assignmentId) { this.assignmentId = assignmentId; }
        public String getFacultyId() { return facultyId; }
        public void setFacultyId(String facultyId) { this.facultyId = facultyId; }
        public String getCourseCode() { return courseCode; }
        public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
        public String getSemester() { return semester; }
        public void setSemester(String semester) { this.semester = semester; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    public static class LearningResource {
        private String resourceId;
        private String title;
        private String courseCode;
        private String author;
        private String resourceType;
        private String url;
        private String summary;
        private String keywords;

        public LearningResource() {}

        public LearningResource(String resourceId, String title, String courseCode, String author, String resourceType, String url, String summary, String keywords) {
            this.resourceId = resourceId;
            this.title = title;
            this.courseCode = courseCode;
            this.author = author;
            this.resourceType = resourceType;
            this.url = url;
            this.summary = summary;
            this.keywords = keywords;
        }

        public static String[] headers() { return new String[]{"resourceId","title","courseCode","author","resourceType","url","summary","keywords"}; }

        public String[] toCsvRow() { return new String[]{resourceId,title,courseCode,author,resourceType,url,summary,keywords}; }

        public static LearningResource fromCsvRow(String[] row) {
            if (row == null || row.length < 8) return null;
            LearningResource r = new LearningResource();
            r.resourceId = row[0] == null ? "" : row[0].trim();
            r.title = row[1] == null ? "" : row[1].trim();
            r.courseCode = row[2] == null ? "" : row[2].trim();
            r.author = row[3] == null ? "" : row[3].trim();
            r.resourceType = row[4] == null ? "" : row[4].trim();
            r.url = row[5] == null ? "" : row[5].trim();
            r.summary = row[6] == null ? "" : row[6].trim();
            r.keywords = row[7] == null ? "" : row[7].trim();
            return r;
        }

        public String getResourceId() { return resourceId; }
        public void setResourceId(String resourceId) { this.resourceId = resourceId; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getCourseCode() { return courseCode; }
        public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }
        public String getResourceType() { return resourceType; }
        public void setResourceType(String resourceType) { this.resourceType = resourceType; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        public String getSummary() { return summary; }
        public void setSummary(String summary) { this.summary = summary; }
        public String getKeywords() { return keywords; }
        public void setKeywords(String keywords) { this.keywords = keywords; }
    }

    public static class SearchResult {
        private String id;
        private String label;
        private String detail;

        public SearchResult(String id, String label, String detail) {
            this.id = id;
            this.label = label;
            this.detail = detail;
        }

        public String getId() { return id; }
        public String getLabel() { return label; }
        public String getDetail() { return detail; }
    }

    public static List<String> normaliseTokens(String text) {
        if (text == null || text.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String[] parts = text.toLowerCase().replaceAll("[^a-z0-9\\s]", " ").split("\\s+");
        List<String> tokens = new ArrayList<>();
        for (String part : parts) {
            if (!part.trim().isEmpty()) tokens.add(part.trim());
        }
        return tokens;
    }
}
