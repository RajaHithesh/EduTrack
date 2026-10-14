package edutrack;

import edutrack.Models.Assignment;
import edutrack.Models.Course;
import edutrack.Models.Enrollment;
import edutrack.Models.Examination;
import edutrack.Models.Faculty;
import edutrack.Models.FacultyCourseAssignment;
import edutrack.Models.LearningResource;
import edutrack.Models.SearchResult;
import edutrack.Models.Student;
import edutrack.Models.Submission;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AcademicService {
    private final List<Student> students = new ArrayList<>();
    private final List<Faculty> faculties = new ArrayList<>();
    private final List<Course> courses = new ArrayList<>();
    private final List<Enrollment> enrollments = new ArrayList<>();
    private final List<Assignment> assignments = new ArrayList<>();
    private final List<Submission> submissions = new ArrayList<>();
    private final List<Examination> examinations = new ArrayList<>();
    private final List<FacultyCourseAssignment> facultyAssignments = new ArrayList<>();
    private final List<LearningResource> resources = new ArrayList<>();

    public AcademicService() {
        initializeStorage();
        loadAll();
    }

    public void initializeStorage() {
        CsvStore.ensureFile("students.csv", Student.headers());
        CsvStore.ensureFile("faculty.csv", Faculty.headers());
        CsvStore.ensureFile("courses.csv", Course.headers());
        CsvStore.ensureFile("enrollments.csv", Enrollment.headers());
        CsvStore.ensureFile("assignments.csv", Assignment.headers());
        CsvStore.ensureFile("submissions.csv", Submission.headers());
        CsvStore.ensureFile("examinations.csv", Examination.headers());
        CsvStore.ensureFile("faculty_course_assignments.csv", FacultyCourseAssignment.headers());
        CsvStore.ensureFile("learning_resources.csv", LearningResource.headers());
    }

    public void loadAll() {
        students.clear();
        students.addAll(CsvStore.readRecords("students.csv", Student::fromCsvRow));
        faculties.clear();
        faculties.addAll(CsvStore.readRecords("faculty.csv", Faculty::fromCsvRow));
        courses.clear();
        courses.addAll(CsvStore.readRecords("courses.csv", Course::fromCsvRow));
        enrollments.clear();
        enrollments.addAll(CsvStore.readRecords("enrollments.csv", Enrollment::fromCsvRow));
        assignments.clear();
        assignments.addAll(CsvStore.readRecords("assignments.csv", Assignment::fromCsvRow));
        submissions.clear();
        submissions.addAll(CsvStore.readRecords("submissions.csv", Submission::fromCsvRow));
        examinations.clear();
        examinations.addAll(CsvStore.readRecords("examinations.csv", Examination::fromCsvRow));
        facultyAssignments.clear();
        facultyAssignments.addAll(CsvStore.readRecords("faculty_course_assignments.csv", FacultyCourseAssignment::fromCsvRow));
        resources.clear();
        resources.addAll(CsvStore.readRecords("learning_resources.csv", LearningResource::fromCsvRow));
    }

    private void persistStudents() { CsvStore.writeRecords("students.csv", Student.headers(), students.stream().map(Student::toCsvRow).collect(Collectors.toList())); }
    private void persistFaculty() { CsvStore.writeRecords("faculty.csv", Faculty.headers(), faculties.stream().map(Faculty::toCsvRow).collect(Collectors.toList())); }
    private void persistCourses() { CsvStore.writeRecords("courses.csv", Course.headers(), courses.stream().map(Course::toCsvRow).collect(Collectors.toList())); }
    private void persistEnrollments() { CsvStore.writeRecords("enrollments.csv", Enrollment.headers(), enrollments.stream().map(Enrollment::toCsvRow).collect(Collectors.toList())); }
    private void persistAssignments() { CsvStore.writeRecords("assignments.csv", Assignment.headers(), assignments.stream().map(Assignment::toCsvRow).collect(Collectors.toList())); }
    private void persistSubmissions() { CsvStore.writeRecords("submissions.csv", Submission.headers(), submissions.stream().map(Submission::toCsvRow).collect(Collectors.toList())); }
    private void persistExaminations() { CsvStore.writeRecords("examinations.csv", Examination.headers(), examinations.stream().map(Examination::toCsvRow).collect(Collectors.toList())); }
    private void persistFacultyAssignments() { CsvStore.writeRecords("faculty_course_assignments.csv", FacultyCourseAssignment.headers(), facultyAssignments.stream().map(FacultyCourseAssignment::toCsvRow).collect(Collectors.toList())); }
    private void persistResources() { CsvStore.writeRecords("learning_resources.csv", LearningResource.headers(), resources.stream().map(LearningResource::toCsvRow).collect(Collectors.toList())); }

    public List<Student> getStudents() { return students; }
    public List<Faculty> getFaculties() { return faculties; }
    public List<Course> getCourses() { return courses; }
    public List<Enrollment> getEnrollments() { return enrollments; }
    public List<Assignment> getAssignments() { return assignments; }
    public List<Submission> getSubmissions() { return submissions; }
    public List<Examination> getExaminations() { return examinations; }
    public List<FacultyCourseAssignment> getFacultyAssignments() { return facultyAssignments; }
    public List<LearningResource> getResources() { return resources; }

    public void addStudent(Student student) {
        validateStudent(student);
        if (findStudentById(student.getStudentId()) != null) throw new IllegalArgumentException("A student with ID " + student.getStudentId() + " already exists.");
        students.add(student);
        persistStudents();
    }

    public void updateStudent(Student updated) {
        Student existing = findStudentById(updated.getStudentId());
        if (existing == null) throw new IllegalArgumentException("Student not found.");
        validateStudent(updated);
        int index = students.indexOf(existing);
        students.set(index, updated);
        persistStudents();
    }

    public void deleteStudent(String studentId) {
        Student student = findStudentById(studentId);
        if (student == null) throw new IllegalArgumentException("Student not found.");
        if (hasActiveEnrollments(studentId)) throw new IllegalArgumentException("Student has active enrollments. Withdraw them first.");
        students.remove(student);
        persistStudents();
    }

    public void addFaculty(Faculty faculty) {
        validateFaculty(faculty);
        if (findFacultyById(faculty.getFacultyId()) != null) throw new IllegalArgumentException("Faculty ID already exists.");
        faculties.add(faculty);
        persistFaculty();
    }

    public void updateFaculty(Faculty updated) {
        Faculty existing = findFacultyById(updated.getFacultyId());
        if (existing == null) throw new IllegalArgumentException("Faculty not found.");
        validateFaculty(updated);
        int index = faculties.indexOf(existing);
        faculties.set(index, updated);
        persistFaculty();
    }

    public void deleteFaculty(String facultyId) {
        Faculty faculty = findFacultyById(facultyId);
        if (faculty == null) throw new IllegalArgumentException("Faculty not found.");
        if (hasFacultyCourseAssignments(facultyId)) throw new IllegalArgumentException("Faculty is assigned to courses. Remove assignments first.");
        faculties.remove(faculty);
        persistFaculty();
    }

    public void addCourse(Course course) {
        validateCourse(course);
        if (findCourseByCode(course.getCourseCode()) != null) throw new IllegalArgumentException("Course code already exists.");
        courses.add(course);
        persistCourses();
    }

    public void updateCourse(Course updated) {
        Course existing = findCourseByCode(updated.getCourseCode());
        if (existing == null) throw new IllegalArgumentException("Course not found.");
        validateCourse(updated);
        int index = courses.indexOf(existing);
        courses.set(index, updated);
        persistCourses();
    }

    public void deleteCourse(String courseCode) {
        Course course = findCourseByCode(courseCode);
        if (course == null) throw new IllegalArgumentException("Course not found.");
        if (!enrollmentsForCourse(courseCode).isEmpty() || !assignmentsForCourse(courseCode).isEmpty()) throw new IllegalArgumentException("Course has enrollments or assignments. Remove them before deleting.");
        courses.remove(course);
        persistCourses();
    }

    public void addEnrollment(String studentId, String courseCode) {
        Student student = findStudentById(studentId);
        Course course = findCourseByCode(courseCode);
        if (student == null) throw new IllegalArgumentException("Student not found.");
        if (course == null) throw new IllegalArgumentException("Course not found.");
        if (!"Active".equalsIgnoreCase(student.getStatus()) && !"Enrolled".equalsIgnoreCase(student.getStatus())) throw new IllegalArgumentException("Student status is not active for enrollment.");
        if (findEnrollment(studentId, courseCode) != null) throw new IllegalArgumentException("Student is already enrolled in that course.");
        if (countEnrollmentsForCourse(courseCode) >= course.getCapacity()) throw new IllegalArgumentException("Course is at full capacity.");
        enrollments.add(new Enrollment("ENR-" + (enrollments.size() + 1), studentId, courseCode, LocalDate.now(), "Active"));
        persistEnrollments();
    }

    public void withdrawEnrollment(String studentId, String courseCode) {
        Enrollment enrollment = findEnrollment(studentId, courseCode);
        if (enrollment == null) throw new IllegalArgumentException("Enrollment not found.");
        enrollments.remove(enrollment);
        persistEnrollments();
    }

    public void addAssignment(Assignment assignment) {
        validateAssignment(assignment);
        if (findCourseByCode(assignment.getCourseCode()) == null) throw new IllegalArgumentException("Course not found.");
        assignments.add(assignment);
        persistAssignments();
    }

    public void recordSubmission(Submission submission) {
        validateSubmission(submission);
        Assignment assignment = findAssignmentById(submission.getAssignmentId());
        if (assignment == null) throw new IllegalArgumentException("Assignment not found.");
        if (findStudentById(submission.getStudentId()) == null) throw new IllegalArgumentException("Student not found.");
        if (submission.getMarks() != null && submission.getMarks() > assignment.getMaxMarks()) throw new IllegalArgumentException("Marks exceed the maximum allowed for the assignment.");
        if (submission.getSubmittedDate().isBefore(assignment.getDueDate()) || submission.getSubmittedDate().isEqual(assignment.getDueDate())) {
            submission.setStatus("Submitted");
        } else {
            submission.setStatus("Late");
        }
        submissions.add(submission);
        persistSubmissions();
    }

    public void updateSubmission(Submission updated) {
        Submission existing = findSubmissionById(updated.getSubmissionId());
        if (existing == null) throw new IllegalArgumentException("Submission not found.");
        validateSubmission(updated);
        int index = submissions.indexOf(existing);
        submissions.set(index, updated);
        persistSubmissions();
    }

    public void addExamination(Examination examination) {
        validateExamination(examination);
        if (findCourseByCode(examination.getCourseCode()) == null) throw new IllegalArgumentException("Course not found.");
        if (hasExaminationConflict(examination)) throw new IllegalArgumentException("This examination overlaps with an existing schedule for the same or related department/venue.");
        examinations.add(examination);
        persistExaminations();
    }

    public void updateExamination(Examination updated) {
        Examination existing = findExaminationById(updated.getExaminationId());
        if (existing == null) throw new IllegalArgumentException("Examination not found.");
        validateExamination(updated);
        int index = examinations.indexOf(existing);
        examinations.set(index, updated);
        persistExaminations();
    }

    public void cancelExamination(String examinationId) {
        Examination exam = findExaminationById(examinationId);
        if (exam == null) throw new IllegalArgumentException("Examination not found.");
        exam.setStatus("Cancelled");
        persistExaminations();
    }

    public void assignFacultyToCourse(String facultyId, String courseCode) {
        Faculty faculty = findFacultyById(facultyId);
        Course course = findCourseByCode(courseCode);
        if (faculty == null) throw new IllegalArgumentException("Faculty not found.");
        if (course == null) throw new IllegalArgumentException("Course not found.");
        if (!faculty.getDepartment().equalsIgnoreCase(course.getDepartment()) && !faculty.getSpecialization().toLowerCase().contains(course.getTitle().toLowerCase())) {
            throw new IllegalArgumentException("Faculty is not eligible for this course based on department or specialization.");
        }
        if (findFacultyCourseAssignment(facultyId, courseCode) != null) throw new IllegalArgumentException("Faculty is already assigned to this course.");
        facultyAssignments.add(new FacultyCourseAssignment("FAC-ASSIGN-" + (facultyAssignments.size() + 1), facultyId, courseCode, String.valueOf(course.getSemester()), "Active"));
        persistFacultyAssignments();
    }

    public void addResource(LearningResource resource) {
        validateResource(resource);
        if (findCourseByCode(resource.getCourseCode()) == null) throw new IllegalArgumentException("Course not found for the resource.");
        resources.add(resource);
        persistResources();
    }

    public void updateResource(LearningResource updated) {
        LearningResource existing = findResourceById(updated.getResourceId());
        if (existing == null) throw new IllegalArgumentException("Resource not found.");
        validateResource(updated);
        int index = resources.indexOf(existing);
        resources.set(index, updated);
        persistResources();
    }

    public void deleteResource(String resourceId) {
        LearningResource resource = findResourceById(resourceId);
        if (resource == null) throw new IllegalArgumentException("Resource not found.");
        resources.remove(resource);
        persistResources();
    }

    public Student findStudentById(String studentId) { for (Student s : students) if (s.getStudentId().equalsIgnoreCase(studentId)) return s; return null; }
    public Faculty findFacultyById(String facultyId) { for (Faculty f : faculties) if (f.getFacultyId().equalsIgnoreCase(facultyId)) return f; return null; }
    public Course findCourseByCode(String courseCode) { for (Course c : courses) if (c.getCourseCode().equalsIgnoreCase(courseCode)) return c; return null; }
    public Assignment findAssignmentById(String assignmentId) { for (Assignment a : assignments) if (a.getAssignmentId().equalsIgnoreCase(assignmentId)) return a; return null; }
    public Submission findSubmissionById(String submissionId) { for (Submission s : submissions) if (s.getSubmissionId().equalsIgnoreCase(submissionId)) return s; return null; }
    public Examination findExaminationById(String examinationId) { for (Examination e : examinations) if (e.getExaminationId().equalsIgnoreCase(examinationId)) return e; return null; }
    public LearningResource findResourceById(String resourceId) { for (LearningResource r : resources) if (r.getResourceId().equalsIgnoreCase(resourceId)) return r; return null; }
    public Enrollment findEnrollment(String studentId, String courseCode) { for (Enrollment e : enrollments) if (e.getStudentId().equalsIgnoreCase(studentId) && e.getCourseCode().equalsIgnoreCase(courseCode)) return e; return null; }
    public FacultyCourseAssignment findFacultyCourseAssignment(String facultyId, String courseCode) { for (FacultyCourseAssignment a : facultyAssignments) if (a.getFacultyId().equalsIgnoreCase(facultyId) && a.getCourseCode().equalsIgnoreCase(courseCode)) return a; return null; }

    public List<Student> searchStudents(String query) {
        String q = query.toLowerCase();
        return students.stream().filter(s ->
            s.getStudentId().toLowerCase().contains(q) ||
            s.getName().toLowerCase().contains(q) ||
            s.getDepartment().toLowerCase().contains(q) ||
            s.getProgram().toLowerCase().contains(q) ||
            s.getEmail().toLowerCase().contains(q)
        ).collect(Collectors.toList());
    }

    public List<Faculty> searchFaculty(String query) {
        String q = query.toLowerCase();
        return faculties.stream().filter(f ->
            f.getFacultyId().toLowerCase().contains(q) ||
            f.getName().toLowerCase().contains(q) ||
            f.getDepartment().toLowerCase().contains(q) ||
            f.getSpecialization().toLowerCase().contains(q)
        ).collect(Collectors.toList());
    }

    public List<Course> searchCourses(String query) {
        String q = query.toLowerCase();
        return courses.stream().filter(c ->
            c.getCourseCode().toLowerCase().contains(q) ||
            c.getTitle().toLowerCase().contains(q) ||
            c.getDepartment().toLowerCase().contains(q) ||
            c.getDescription().toLowerCase().contains(q)
        ).collect(Collectors.toList());
    }

    public List<LearningResource> searchResources(String query) {
        String q = query.toLowerCase();
        return resources.stream().filter(r ->
            r.getTitle().toLowerCase().contains(q) ||
            r.getCourseCode().toLowerCase().contains(q) ||
            r.getAuthor().toLowerCase().contains(q) ||
            r.getResourceType().toLowerCase().contains(q) ||
            r.getKeywords().toLowerCase().contains(q) ||
            r.getSummary().toLowerCase().contains(q)
        ).collect(Collectors.toList());
    }

    public List<Enrollment> enrollmentsForStudent(String studentId) { return enrollments.stream().filter(e -> e.getStudentId().equalsIgnoreCase(studentId)).collect(Collectors.toList()); }
    public List<Enrollment> enrollmentsForCourse(String courseCode) { return enrollments.stream().filter(e -> e.getCourseCode().equalsIgnoreCase(courseCode)).collect(Collectors.toList()); }
    public List<String> getCourseStudentNames(String courseCode) {
        List<String> names = new ArrayList<>();
        for (Enrollment e : enrollmentsForCourse(courseCode)) {
            Student student = findStudentById(e.getStudentId());
            if (student != null) names.add(student.getName());
        }
        return names;
    }
    public List<Assignment> assignmentsForCourse(String courseCode) { return assignments.stream().filter(a -> a.getCourseCode().equalsIgnoreCase(courseCode)).collect(Collectors.toList()); }
    public List<Assignment> getPendingAssignments() {
        List<Assignment> pending = new ArrayList<>();
        for (Assignment assignment : assignments) {
            boolean hasSubmission = submissions.stream().anyMatch(s -> s.getAssignmentId().equalsIgnoreCase(assignment.getAssignmentId()));
            if (!hasSubmission) {
                pending.add(assignment);
            } else if (submissions.stream().filter(s -> s.getAssignmentId().equalsIgnoreCase(assignment.getAssignmentId())).anyMatch(s -> s.getSubmittedDate().isAfter(assignment.getDueDate()))) {
                pending.add(assignment);
            }
        }
        return pending;
    }
    public List<Examination> getExaminationsForCourse(String courseCode) { return examinations.stream().filter(e -> e.getCourseCode().equalsIgnoreCase(courseCode)).collect(Collectors.toList()); }

    public List<SearchResult> getAcademicReport() {
        List<SearchResult> result = new ArrayList<>();
        result.add(new SearchResult("students", "Students", String.valueOf(students.size())));
        result.add(new SearchResult("faculty", "Faculty", String.valueOf(faculties.size())));
        result.add(new SearchResult("courses", "Courses", String.valueOf(courses.size())));
        result.add(new SearchResult("enrollments", "Enrollments", String.valueOf(enrollments.size())));
        result.add(new SearchResult("assignments", "Assignments", String.valueOf(assignments.size())));
        result.add(new SearchResult("resources", "Resources", String.valueOf(resources.size())));
        return result;
    }

    public List<SearchResult> getCourseEnrollmentSummary() {
        List<SearchResult> result = new ArrayList<>();
        for (Course course : courses) {
            int count = countEnrollmentsForCourse(course.getCourseCode());
            result.add(new SearchResult(course.getCourseCode(), course.getTitle(), count + "/" + course.getCapacity()));
        }
        result.sort(Comparator.comparing(SearchResult::getId));
        return result;
    }

    public List<SearchResult> getFacultyCourseSummary() {
        List<SearchResult> result = new ArrayList<>();
        for (Faculty faculty : faculties) {
            int count = 0;
            for (FacultyCourseAssignment assignment : facultyAssignments) {
                if (assignment.getFacultyId().equalsIgnoreCase(faculty.getFacultyId())) count++;
            }
            result.add(new SearchResult(faculty.getFacultyId(), faculty.getName(), String.valueOf(count)));
        }
        return result;
    }

    public List<String> getExamConflictReport() {
        List<String> conflicts = new ArrayList<>();
        for (int i = 0; i < examinations.size(); i++) {
            for (int j = i + 1; j < examinations.size(); j++) {
                Examination first = examinations.get(i);
                Examination second = examinations.get(j);
                if (first.getStatus().equalsIgnoreCase("Cancelled") || second.getStatus().equalsIgnoreCase("Cancelled")) continue;
                if (sameDateAndTimeConflict(first, second)) {
                    conflicts.add(first.getExaminationId() + " conflicts with " + second.getExaminationId() + " at " + second.getVenue() + ".");
                }
            }
        }
        return conflicts;
    }

    private boolean sameDateAndTimeConflict(Examination first, Examination second) {
        if (!first.getExamDate().equals(second.getExamDate())) return false;
        int startA = minutesFromTime(first.getStartTime());
        int endA = startA + first.getDurationMinutes();
        int startB = minutesFromTime(second.getStartTime());
        int endB = startB + second.getDurationMinutes();
        if (startA < endB && startB < endA) {
            return first.getVenue().equalsIgnoreCase(second.getVenue()) || first.getDepartment().equalsIgnoreCase(second.getDepartment()) || first.getCourseCode().equalsIgnoreCase(second.getCourseCode());
        }
        return false;
    }

    private int minutesFromTime(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }

    public List<SearchResult> getSimilarTextMatches(String term) {
        List<SearchResult> results = new ArrayList<>();
        for (Assignment assignment : assignments) {
            double score = similarity(term, assignment.getDescription());
            if (score > 0.15) {
                results.add(new SearchResult(assignment.getAssignmentId(), assignment.getTitle(), "Similarity: " + String.format("%.0f%%", score * 100)));
            }
        }
        for (LearningResource resource : resources) {
            double score = similarity(term, resource.getSummary() + " " + resource.getKeywords());
            if (score > 0.18) {
                results.add(new SearchResult(resource.getResourceId(), resource.getTitle(), "Similarity: " + String.format("%.0f%%", score * 100)));
            }
        }
        results.sort((a, b) -> Double.compare(parseScore(b.getDetail()), parseScore(a.getDetail())));
        return results;
    }

    private double parseScore(String detail) {
        try {
            String value = detail.replace("Similarity: ", "").replace("%", "");
            return Double.parseDouble(value) / 100.0;
        } catch (Exception e) { return 0.0; }
    }

    private double similarity(String left, String right) {
        if (left == null || right == null || left.isBlank() || right.isBlank()) return 0.0;
        Set<String> tokensA = new HashSet<>(Models.normaliseTokens(left));
        Set<String> tokensB = new HashSet<>(Models.normaliseTokens(right));
        if (tokensA.isEmpty() || tokensB.isEmpty()) return 0.0;
        Set<String> intersection = new HashSet<>(tokensA);
        intersection.retainAll(tokensB);
        if (intersection.isEmpty()) return 0.0;
        return (double) intersection.size() * 2 / (tokensA.size() + tokensB.size());
    }

    public boolean hasActiveEnrollments(String studentId) { return enrollments.stream().anyMatch(e -> e.getStudentId().equalsIgnoreCase(studentId)); }
    public boolean hasFacultyCourseAssignments(String facultyId) { return facultyAssignments.stream().anyMatch(a -> a.getFacultyId().equalsIgnoreCase(facultyId)); }
    public int countEnrollmentsForCourse(String courseCode) { return (int) enrollments.stream().filter(e -> e.getCourseCode().equalsIgnoreCase(courseCode)).count(); }

    private boolean hasExaminationConflict(Examination candidate) {
        for (Examination other : examinations) {
            if (other.getExaminationId().equalsIgnoreCase(candidate.getExaminationId())) continue;
            if (candidate.getExamDate().equals(other.getExamDate())) {
                int startA = minutesFromTime(candidate.getStartTime());
                int endA = startA + candidate.getDurationMinutes();
                int startB = minutesFromTime(other.getStartTime());
                int endB = startB + other.getDurationMinutes();
                if (startA < endB && startB < endA) {
                    if (candidate.getVenue().equalsIgnoreCase(other.getVenue()) || candidate.getDepartment().equalsIgnoreCase(other.getDepartment()) || candidate.getCourseCode().equalsIgnoreCase(other.getCourseCode())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void validateStudent(Student student) {
        if (student == null) throw new IllegalArgumentException("Student details are required.");
        if (isBlank(student.getStudentId())) throw new IllegalArgumentException("Student ID is required.");
        if (isBlank(student.getName())) throw new IllegalArgumentException("Student name is required.");
        if (isBlank(student.getDepartment())) throw new IllegalArgumentException("Department is required.");
        if (isBlank(student.getProgram())) throw new IllegalArgumentException("Program is required.");
        if (student.getYear() < 1 || student.getYear() > 8) throw new IllegalArgumentException("Year must be between 1 and 8.");
        if (!student.getEmail().contains("@")) throw new IllegalArgumentException("Email must be valid.");
        if (isBlank(student.getStatus())) student.setStatus("Active");
    }

    private void validateFaculty(Faculty faculty) {
        if (faculty == null) throw new IllegalArgumentException("Faculty details are required.");
        if (isBlank(faculty.getFacultyId())) throw new IllegalArgumentException("Faculty ID is required.");
        if (isBlank(faculty.getName())) throw new IllegalArgumentException("Faculty name is required.");
        if (isBlank(faculty.getDepartment())) throw new IllegalArgumentException("Department is required.");
        if (isBlank(faculty.getSpecialization())) throw new IllegalArgumentException("Specialization is required.");
        if (isBlank(faculty.getAvailability())) faculty.setAvailability("Available");
    }

    private void validateCourse(Course course) {
        if (course == null) throw new IllegalArgumentException("Course details are required.");
        if (isBlank(course.getCourseCode())) throw new IllegalArgumentException("Course code is required.");
        if (isBlank(course.getTitle())) throw new IllegalArgumentException("Course title is required.");
        if (isBlank(course.getDepartment())) throw new IllegalArgumentException("Department is required.");
        if (course.getCredits() <= 0) throw new IllegalArgumentException("Credits must be positive.");
        if (course.getSemester() <= 0) throw new IllegalArgumentException("Semester must be positive.");
        if (course.getCapacity() <= 0) throw new IllegalArgumentException("Capacity must be positive.");
    }

    private void validateAssignment(Assignment assignment) {
        if (assignment == null) throw new IllegalArgumentException("Assignment details are required.");
        if (isBlank(assignment.getAssignmentId())) throw new IllegalArgumentException("Assignment ID is required.");
        if (isBlank(assignment.getCourseCode())) throw new IllegalArgumentException("Course code is required.");
        if (isBlank(assignment.getTitle())) throw new IllegalArgumentException("Assignment title is required.");
        if (assignment.getMaxMarks() <= 0) throw new IllegalArgumentException("Maximum marks must be positive.");
        if (assignment.getDueDate() == null) throw new IllegalArgumentException("Due date is required.");
    }

    private void validateSubmission(Submission submission) {
        if (submission == null) throw new IllegalArgumentException("Submission details are required.");
        if (isBlank(submission.getSubmissionId())) throw new IllegalArgumentException("Submission ID is required.");
        if (isBlank(submission.getAssignmentId())) throw new IllegalArgumentException("Assignment ID is required.");
        if (isBlank(submission.getStudentId())) throw new IllegalArgumentException("Student ID is required.");
        if (submission.getSubmittedDate() == null) throw new IllegalArgumentException("Submission date is required.");
    }

    private void validateExamination(Examination exam) {
        if (exam == null) throw new IllegalArgumentException("Examination details are required.");
        if (isBlank(exam.getExaminationId())) throw new IllegalArgumentException("Examination ID is required.");
        if (isBlank(exam.getCourseCode())) throw new IllegalArgumentException("Course code is required.");
        if (isBlank(exam.getDepartment())) throw new IllegalArgumentException("Department is required.");
        if (exam.getDurationMinutes() <= 0) throw new IllegalArgumentException("Duration must be positive.");
        if (isBlank(exam.getStartTime())) throw new IllegalArgumentException("Examination start time is required.");
        if (isBlank(exam.getVenue())) throw new IllegalArgumentException("Venue is required.");
    }

    private void validateResource(LearningResource resource) {
        if (resource == null) throw new IllegalArgumentException("Resource details are required.");
        if (isBlank(resource.getResourceId())) throw new IllegalArgumentException("Resource ID is required.");
        if (isBlank(resource.getTitle())) throw new IllegalArgumentException("Title is required.");
        if (isBlank(resource.getCourseCode())) throw new IllegalArgumentException("Course code is required.");
        if (isBlank(resource.getResourceType())) throw new IllegalArgumentException("Resource type is required.");
    }

    private boolean isBlank(String value) { return value == null || value.trim().isEmpty(); }
}
