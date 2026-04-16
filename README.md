# Research University Information System

This project is a Java OOP console application for managing university users, courses, and research activities.

## Project Structure

- `Main.java`
  - Entry point of the application.
  - Starts the console interface.

- `src/`
  - Main source code folder.

### `src/exceptions/`
Custom domain exceptions used by business logic.
Examples: credit limit checks, failed course limits, researcher-related validation.

### `src/model/`
Core domain model classes.

- `src/model/users/`
  - User hierarchy (`User`, `Student`, `Teacher`, `Manager`, `Admin`, etc.).
  - Covers inheritance, polymorphism, and role-specific behavior.

- `src/model/courses/`
  - Course-related entities (`Course`, `Lesson`, `Mark`).
  - Handles registration, schedule, and grading data.

- `src/model/research/`
  - Research domain entities and decorators (`Researcher`, `ResearchPaper`, `ResearchProject`, etc.).
  - Uses Decorator and Strategy-style patterns.

- `src/model/comparators/`
  - Sorting strategies for research papers.

- `src/model/enums/`
  - Enumerations for fixed values (lesson type, title, student year, etc.).

### `src/service/`
Service-level logic such as notifications and observer integration.

### `src/storage/`
Persistence and data loading/saving logic.

- `src/storage/DataStorage.java`
  - Central storage manager.
  - One of the largest files; keep changes careful and controlled.

### `src/ui/`
Console interface logic.

- `src/ui/ConsoleUI.java`
  - Main command-line interaction layer.
  - Large orchestrator for menus and user actions.

## Other Important Files

- `TODO_ASSIGNMENTS.md`
  - Team task distribution and implementation checklist.

- `university_data.ser`
  - Serialized data file used for persistence.

- `bin/`
  - Compiled output/classes (if generated in this workspace).

## Build and Run

Compile:

```bash
javac -d /tmp/oop-build Main.java $(find src -name '*.java' | sort)
```

Run:

```bash
java -cp /tmp/oop-build Main
```

## Team Workflow Guidance

- Work only in your assigned files from `TODO_ASSIGNMENTS.md`.
- Do not modify other members' files to avoid merge conflicts.
- Replace TODO training comments with real implementation when completing tasks.
- Push to GitHub regularly to keep useful commit history for reporting.
