package com.academictrip.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.academictrip.model.Course;
import com.academictrip.model.Faculty;
import com.academictrip.util.DatabaseUtil;

public class CourseDAO {
    // Generate course_id (e.g., COU001)
    private String generateCourseId() throws SQLException {
        String prefix = "COU";
        String sql = "SELECT MAX(course_id) AS max_id FROM Course";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String maxId = rs.getString("max_id");
                if (maxId == null) {
					return prefix + "001";
				}
                int numericPart = Integer.parseInt(maxId.replace(prefix, ""));
                return String.format("%s%03d", prefix, numericPart + 1);
            }
            return prefix + "001";
        }
    }

    /**
     * Insert a new course
     * @param course The course to insert
     * @return true if insertion was successful, false otherwise
     * @throws SQLException if database error occurs
     */
    public boolean insertCourse(Course course) throws SQLException {
        String sql = "INSERT INTO Course (course_id, course_name, faculty_id) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, course.getCourseId());
            stmt.setString(2, course.getCourseName());
            stmt.setString(3, course.getFacultyId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    /**
     * Get a course by ID
     * @param courseId The ID of the course
     * @return Course object or null if not found
     * @throws SQLException if database error occurs
     */
    public Course getCourseById(String courseId) throws SQLException {
        String sql = "SELECT c.*, f.name AS faculty_name FROM Course c " +
                     "LEFT JOIN Faculty f ON c.faculty_id = f.faculty_id " +
                     "WHERE c.course_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, courseId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Course course = new Course();
                    course.setCourseId(rs.getString("course_id"));
                    course.setCourseName(rs.getString("course_name"));
                    course.setFacultyId(rs.getString("faculty_id"));

                    // Create and set Faculty if available
                    String facultyName = rs.getString("faculty_name");
                    if (facultyName != null) {
                        Faculty faculty = new Faculty();
                        faculty.setFacultyId(rs.getString("faculty_id"));
                        faculty.setName(facultyName);
                        course.setFaculty(faculty);
                    }

                    return course;
                }
            }
        }

        return null;
    }

    /**
     * Get all courses
     * @return List of all courses
     * @throws SQLException if database error occurs
     */
    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.*, f.name AS faculty_name FROM Course c " +
                     "LEFT JOIN Faculty f ON c.faculty_id = f.faculty_id " +
                     "ORDER BY c.course_name";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Course course = new Course();
                course.setCourseId(rs.getString("course_id"));
                course.setCourseName(rs.getString("course_name"));
                course.setFacultyId(rs.getString("faculty_id"));

                // Create and set Faculty if available
                String facultyName = rs.getString("faculty_name");
                if (facultyName != null) {
                    Faculty faculty = new Faculty();
                    faculty.setFacultyId(rs.getString("faculty_id"));
                    faculty.setName(facultyName);
                    course.setFaculty(faculty);
                }

                courses.add(course);
            }
        }

        return courses;
    }

    /**
     * Get courses by faculty ID
     * @param facultyId The faculty ID to filter by
     * @return List of courses belonging to the faculty
     * @throws SQLException if database error occurs
     */
    public List<Course> getCoursesByFaculty(String facultyId) throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.* FROM Course c WHERE c.faculty_id = ? ORDER BY c.course_name";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, facultyId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Course course = new Course();
                    course.setCourseId(rs.getString("course_id"));
                    course.setCourseName(rs.getString("course_name"));
                    course.setFacultyId(rs.getString("faculty_id"));

                    courses.add(course);
                }
            }
        }

        return courses;
    }

    /**
     * Update an existing course
     * @param course The course to update
     * @return true if update was successful, false otherwise
     * @throws SQLException if database error occurs
     */
    public boolean updateCourse(Course course) throws SQLException {
        String sql = "UPDATE Course SET course_name = ?, faculty_id = ? WHERE course_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, course.getCourseName());
            stmt.setString(2, course.getFacultyId());
            stmt.setString(3, course.getCourseId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    /**
     * Delete a course
     * @param courseId The ID of the course to delete
     * @return true if deletion was successful, false otherwise
     * @throws SQLException if database error occurs
     */
    public boolean deleteCourse(String courseId) throws SQLException {
        String sql = "DELETE FROM Course WHERE course_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, courseId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public Course findCourseByName(String name) throws SQLException {
        String sql = "SELECT * FROM Course WHERE course_name = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Course(
                        rs.getString("course_id"),
                        rs.getString("course_name"),
                        rs.getString("faculty_id")
                    );
                }
                return null;
            }
        }
    }
}