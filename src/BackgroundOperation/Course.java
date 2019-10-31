package BackgroundOperation;

import DbOperation.DbUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Course {
	private Teacher teacher;
	private String courseId;
	private String courseName;
	private int grade;
	
	public Course(Teacher teacher, String courseId, int grade) {
		setTeacher(teacher);
		setCourseId(courseId);
		setGrade(grade);
		setCourseName(Course.getCourseName(courseId));
	}
	public Course(Teacher teacher, String courseId, String courseName, int grade) {
		setTeacher(teacher);
		setCourseId(courseId);
		setCourseName(courseName);
		setGrade(grade);
	}
	
	public Course getCourse() {
		return this;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	public static String getCourseName(String courseId) {
		String sqlString = "SELECT courseName FROM course WHERE courseId = ?";
		PreparedStatement ps = DbUtil.executePreparedStatement(sqlString);
		try {
			ps.setString(1, courseId);
			ResultSet resultSet = ps.executeQuery();
			resultSet.next();
			return resultSet.getString("courseName");
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    	return null;
	    }
	}
}
