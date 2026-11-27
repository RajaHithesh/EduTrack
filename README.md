# 🎓 EduTrack – Student Academic Management System

![Java](https://img.shields.io/badge/Java-17%2B-orange)
![Platform](https://img.shields.io/badge/Platform-Cross--Platform-blue)
![Storage](https://img.shields.io/badge/Storage-CSV-green)
![Version Control](https://img.shields.io/badge/Version%20Control-Git-red)
![Project](https://img.shields.io/badge/Type-Console%20Application-success)

## 📌 Project Overview

**EduTrack** is a Java-based Student Academic Management System designed to simplify and organize academic administration through a menu-driven console application.

The application provides functionality for managing students, faculty, courses, enrollments, assignments, submissions, examinations, faculty-course assignments, and learning resources.

It uses **Object-Oriented Programming, Java Collections, Stream API, Functional Programming, File Handling, and custom algorithms** to implement academic management operations.

Instead of relying on an external database, EduTrack uses CSV files for persistent data storage, allowing application records to be saved and loaded between executions.

The project demonstrates how core Java concepts can be combined to build a structured, modular, and maintainable application.

## 🎯 Project Objectives

- Develop a structured Java-based academic management application.
- Apply object-oriented programming principles.
- Implement academic record management operations.
- Understand data persistence using file handling.
- Use Java Collections and Stream API for data processing.
- Implement searching, sorting, and validation techniques.
- Develop modular and maintainable application architecture.

## ✨ Key Features

### 1. Student Management
- Add and manage student records.
- Search for students using application-defined criteria.
- Validate student information.
- Prevent duplicate student records.

### 2. Faculty Management
- Maintain faculty information.
- Search and retrieve faculty records.
- Validate faculty details.
- Manage faculty-related academic information.

### 3. Course Management
- Create and manage courses.
- Maintain course information and capacity.
- Track course enrollments.
- Generate course enrollment summaries.

### 4. Student Enrollment
- Enroll students in available courses.
- Verify student and course existence.
- Prevent duplicate enrollments.
- Check student and course status.
- Validate course capacity before enrollment.

### 5. Assignment Management
- Create and manage assignments.
- Track student submissions.
- Validate submission marks against maximum marks.
- Identify late and on-time submissions.
- Retrieve pending assignments.

### 6. Examination Management
- Manage examination schedules.
- Validate examination details.
- Detect scheduling conflicts.
- Check overlapping examination time intervals.
- Consider venue, department, and course information when checking conflicts.
- Handle cancelled examinations appropriately.

### 7. Faculty-Course Assignment
- Assign faculty members to courses.
- Validate faculty and course information.
- Check department or specialization compatibility.
- Prevent duplicate faculty-course assignments.

### 8. Learning Resource Management
- Maintain learning resources.
- Store resource summaries and keywords.
- Search and retrieve relevant learning resources.
- Compare assignment descriptions with learning resource content using text similarity.

### 9. Academic Reports
- Generate course enrollment summaries.
- Retrieve faculty course summaries.
- Display academic information.
- Sort report results using Java Comparator.

### 10. Data Persistence
- Store application records in CSV files.
- Automatically create the data directory and required files.
- Load saved records when the application starts.
- Write updated records back to CSV files.
- Use temporary files and file replacement to reduce the risk of incomplete updates.

## 🧠 Java Concepts and Technical Implementation

The following concepts are implemented in the project source code.

### 1. Object-Oriented Programming (OOP)

EduTrack uses object-oriented programming to organize academic entities and application operations.

| Concept | Implementation |
|---|---|
| Classes and Objects | Student, Faculty, Course, Assignment, Examination, Enrollment, and other model classes |
| Encapsulation | Private fields with constructors, getters, and setters |
| Constructors | Initialize model objects with their required information |
| Static Members | Static nested model classes and utility methods |
| Abstraction through Layering | AcademicService separates business operations from the console interface |
| Composition | AcademicService manages collections of different academic model objects |

### 2. Java Collections Framework

Collections are used to store and process application records dynamically.

| Collection | Purpose |
|---|---|
| ArrayList | Stores students, faculty, courses, enrollments, assignments, and other records |
| HashSet | Stores unique text tokens during similarity calculations |
| List | Represents collections of academic records |
| Set | Supports unique-element operations |

### 3. Java Stream API

The Stream API is used to process collections efficiently and make data retrieval easier.

Implemented operations include:

- `filter()` – Filters records based on conditions.
- `map()` – Transforms data when required.
- `collect()` – Collects processed results.
- `anyMatch()` – Checks whether any element satisfies a condition.
- `sorted()` – Sorts records.
- `Comparator` – Defines sorting conditions.

### 4. Functional Programming

The project uses Java functional programming features, including:

- Lambda expressions.
- Method references.
- `Function<T, R>` functional interface.
- Stream-based collection processing.

For example, method references are used to convert CSV records into model objects.

### 5. File Handling and CSV Processing

EduTrack implements file-based data persistence using Java I/O and NIO APIs.

**Classes and APIs used:**

- `BufferedReader`
- `BufferedWriter`
- `FileReader`
- `FileWriter`
- `IOException`
- `Path`
- `Paths`
- `Files`
- `StandardCopyOption`

**Implemented functionality:**

- Reading records from CSV files.
- Writing records into CSV files.
- Parsing CSV values.
- Handling quoted CSV fields.
- Handling escaped double quotes.
- Escaping special CSV characters.
- Creating required directories and files.
- Updating files using temporary files and replacement.

### 6. Exception Handling and Validation

Exception handling is used to manage invalid operations and prevent unexpected application failures.

Implemented validations include:

- Duplicate record checks.
- Student and course existence checks.
- Enrollment capacity validation.
- Invalid submission marks.
- Examination scheduling conflicts.
- Faculty-course compatibility.
- Invalid input handling.

`IllegalArgumentException` and `IOException` are used in appropriate situations.

### 7. Java Date and Time API

The project uses Java's Date and Time API for academic scheduling.

- `LocalDate` – Handles academic dates and assignment deadlines.
- `DateTimeFormatter` – Formats dates for console display.
- Date comparisons – Identify late and on-time submissions.

## ⚙️ Algorithms and Problem-Solving Techniques

EduTrack also demonstrates practical algorithmic techniques.

| Algorithm / Technique | Implementation | Purpose |
|---|---|---|
| Linear Search | Collection filtering | Find academic records matching conditions |
| Sorting | Comparator and Stream API | Sort course and academic report results |
| Set Intersection | HashSet and `retainAll()` | Find common words between two text inputs |
| Dice Similarity Coefficient | Custom similarity calculation | Compare assignment descriptions and learning resources |
| Interval Overlap Detection | Time interval comparison | Identify examination scheduling conflicts |
| CSV Parsing | Character-by-character parsing | Correctly process quoted and escaped CSV values |

### Text Similarity – Dice Coefficient

The application implements a text similarity calculation to compare assignment descriptions and learning resource content.

**Working process:**

1. Convert text into lowercase.
2. Remove punctuation.
3. Split text into individual tokens.
4. Store tokens in HashSet collections.
5. Find common tokens using set intersection.
6. Calculate the similarity score.

The Dice Similarity Coefficient is calculated using:

\[
Dice(A,B)=\frac{2|A\cap B|}{|A|+|B|}
\]

Where:

- `A` represents the first set of text tokens.
- `B` represents the second set of text tokens.
- `A ∩ B` represents the common tokens.

This helps identify learning resources with similar textual content.

### Examination Conflict Detection

The application checks whether two examination time intervals overlap.

The overlap condition is:

```java
startA < endB && startB < endA
```

If the condition is true, the time intervals overlap.

The application also considers examination-related information such as venue, department, and course when validating scheduling conflicts.

## 🏗️ Application Architecture

EduTrack follows a modular structure that separates model definitions, business logic, user interaction, and data persistence.

```text
                  EduTrack Application
                           |
                           v
                       Main.java
                           |
                           v
                      ConsoleApp
                    (User Interface)
                           |
                           v
                    AcademicService
                   (Business Logic)
                           |
              +------------+------------+
              |                         |
              v                         v
         Models.java                CsvStore.java
       (Data Models)             (File Persistence)
                                        |
                                        v
                                   data/
                                  CSV Files
```

### Main Components

| Component | Responsibility |
|---|---|
| `Main.java` | Application entry point |
| `ConsoleApp.java` | Menu-driven console interface and user input |
| `AcademicService.java` | Business logic, validation, searching, reporting, and academic operations |
| `Models.java` | Academic entity classes and CSV conversion methods |
| `CsvStore.java` | CSV reading, writing, parsing, and file management |

This separation makes the application easier to understand, maintain, and extend.

## 📂 Project Structure

```text
EduTrack/
│
├── data/
│   ├── students.csv
│   ├── faculty.csv
│   ├── courses.csv
│   ├── enrollments.csv
│   ├── assignments.csv
│   ├── submissions.csv
│   ├── examinations.csv
│   ├── faculty_course_assignments.csv
│   └── learning_resources.csv
│
├── src/
│   └── edutrack/
│       ├── Main.java
│       ├── ConsoleApp.java
│       ├── AcademicService.java
│       ├── Models.java
│       └── CsvStore.java
│
├── out/
│   └── edutrack/
│       └── Compiled class files
│
└── README.md
```

*Note: CSV files are created by the application when initialized. The exact generated filenames depend on the implementation in CsvStore.*

## 🛠️ Technologies and Tools

| Technology | Usage |
|---|---|
| Java | Application development |
| JDK 17 or later | Compilation and execution |
| Java Collections Framework | Managing academic records |
| Java Stream API | Filtering, processing, and sorting data |
| Java I/O and NIO | File handling and CSV persistence |
| Java Date and Time API | Assignment deadlines and examination schedules |
| Git | Version control |
| GitHub | Source code hosting |
| VS Code / IntelliJ IDEA | Development environment |

## 📋 Prerequisites

Before running EduTrack, ensure that you have:

- Java Development Kit (JDK 17 or later).
- Git installed.
- A Java-compatible IDE or code editor.
- Terminal or command-line access.

Verify your Java installation:

```bash
java -version
javac -version
```

## 🚀 Installation and Execution

Follow these steps to download and run the project locally.

### Step 1: Clone the Repository

```bash
git clone https://github.com/RajaHithesh/EduTrack.git
```

### Step 2: Navigate to the Project

```bash
cd EduTrack
```

### Step 3: Compile the Java Source Files

```bash
javac -d out src/edutrack/*.java
```

### Step 4: Run the Application

```bash
java -cp out edutrack.Main
```

### Execution Notes

- Execute the commands from the project root directory.
- Ensure the package name is `edutrack`.
- Make sure the main class is `Main`.
- The application initializes its CSV storage through its data storage component.
- The `data/` directory is used for persistent application records.

## 🔄 Application Workflow

1. Start the application using `Main.java`.
2. Open the menu-driven interface through `ConsoleApp.java`.
3. Select the required academic management operation.
4. The console interface collects user input.
5. `AcademicService.java` processes the requested operation.
6. Model objects represent the academic information.
7. `CsvStore.java` reads or updates the corresponding CSV files.
8. Results and messages are displayed in the console.

## 🎓 Learning Outcomes

By developing EduTrack, the project demonstrates practical experience in:

- Applying object-oriented programming principles.
- Designing modular Java applications.
- Using Java Collections and Stream API.
- Implementing functional programming techniques.
- Working with CSV files and persistent storage.
- Developing custom algorithms for text similarity.
- Performing date and time validation.
- Detecting examination scheduling conflicts.
- Implementing validation and exception handling.
- Organizing source code into maintainable components.

## 📄 License

This project is developed for educational and learning purposes.

---

<div align="center">

**EduTrack – Organizing Academic Management Through Java.**

⭐ If you find this project useful, consider giving the repository a star!

</div>
