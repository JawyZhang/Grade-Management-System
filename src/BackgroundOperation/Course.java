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


	public Course(String courseId){
		setTeacher(new Teacher(Course.getCourseTeacherId(courseId)));
		setCourseId(courseId);
		setCourseName(Course.getCourseName(courseId));
		setGrade(Course.getCourseGrade(courseId));
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
			String str = resultSet.getString("courseName");
			resultSet.close();
			ps.close();
			return str;
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    	return null;
	    }
	}

	public static String getCourseTeacherId(String courseId) {
		String sqlString = "SELECT teacherId FROM course WHERE courseId = ?";
		PreparedStatement ps = DbUtil.executePreparedStatement(sqlString);
		try {
			ps.setString(1, courseId);
			ResultSet resultSet = ps.executeQuery();
			resultSet.next();
			String str = resultSet.getString("teacherId");
			ps.close();
			resultSet.close();
			return str;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int getCourseGrade(String courseId) {
		String sqlString = "SELECT courseGrade FROM course WHERE courseId = ?";
		PreparedStatement ps = DbUtil.executePreparedStatement(sqlString);
		try {
			ps.setString(1, courseId);
			ResultSet resultSet = ps.executeQuery();
			resultSet.next();
			int result = resultSet.getInt("courseGrade");
			ps.close();
			resultSet.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
}
