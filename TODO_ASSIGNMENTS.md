# TODO Assignments Checklist

This file tracks every method intentionally replaced with TODO guidance.
Use it as your team implementation queue.

## Team Members

- Мамедов Н. Т. - user and role logic
- Тайкпанов Р. Р. - course and service logic
- Каналхан Б. Н. - research engine logic

## Team Rule

- After finishing each task,please replace the TODO guidance comments with the real implementation and remove the training comments .

## Мамедов Н. Т. - User/Role Logic

- src/model/users/User.java
  - getFullName()
  - toString()
- src/model/users/Employee.java
  - toString()
- src/model/users/Admin.java
  - logUserAction(String action)
  - extractUsernameFromAction(String action)
  - toString()
- src/model/users/Manager.java
  - approveRequest(String requestId)
  - toString()
- src/model/users/Teacher.java
  - addCourseTeaching(String courseId)
  - isAutoResearcher()
  - toString()
- src/model/users/Student.java
  - registerForCourse(String courseId, int credits)
  - addCourseMark(String courseId, double mark)
  - calculateGPA()
  - getTranscript()
  - toString()

## Тайкпанов Р. Р. - Course/Service Logic

- src/model/courses/Course.java
  - registerStudent(String studentId)
  - getTotalLessonHours()
  - getCourseInfo()
  - toString()
- src/model/courses/Lesson.java
  - overlapsWith(Lesson other)
  - getScheduleString()
  - toString()
- src/model/courses/Mark.java
  - calculateGrade()
  - getGradePoints()
  - getMarkBreakdown()
  - toString()
- src/service/NotificationService.java
  - registerObserver(NotificationObserver observer)
  - notifyObservers(String eventType, String message, Object data)

## Каналхан Б. Н. - Research Engine

- src/model/comparators/ResearchPaperComparators.java
  - ByDateComparator.compare(ResearchPaper p1, ResearchPaper p2)
  - getComparator(String type)
- src/model/research/BasicResearcher.java
  - calculateHIndex()
  - getMostCitedPaper()
  - getResearcherStatistics()
- src/model/research/ResearcherDecorator.java
  - calculateHIndex()
  - printPapers(Comparator<ResearchPaper> comparator)
  - getResearcherStatistics()
- src/model/research/EmployeeResearcher.java
  - toString()
- src/model/research/StudentResearcher.java
  - needsResearchSupervisor()
  - toString()
- src/model/research/TeacherResearcher.java
  - isAutoResearcher()
  - toString()
- src/model/research/ResearchPaper.java
  - calculateImpactFactor()
  - compareTo(ResearchPaper other)
  - toString()
  - getFormattedCitation()
- src/model/research/ResearchProject.java
  - addParticipant(String researcherId, boolean isResearcher)
  - getProjectStatus()
  - toString()
  - getProjectReport()

## Files intentionally not modified

- src/storage/DataStorage.java (kept as requested)
- src/ui/ConsoleUI.java (kept large for stability)
