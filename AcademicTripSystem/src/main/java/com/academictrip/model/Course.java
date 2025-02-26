package com.academictrip.model;

public class Course {
    private String courseId;
    private String courseName;
    private String facultyId;

    // Constructors
    public Course() {}
    public Course(String courseId, String courseName, String facultyId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.facultyId = facultyId;
    }

    // Getters and Setters
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getFacultyId() { return facultyId; }
    public void setFacultyId(String facultyId) { this.facultyId = facultyId; }

    @Override
    public String toString() {
        return "Course [courseId=" + courseId + ", courseName=" + courseName + ", facultyId=" + facultyId + "]";
    }
}