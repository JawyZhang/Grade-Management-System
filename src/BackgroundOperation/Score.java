package BackgroundOperation;

import DbOperation.DbUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Score {
	private Course course;
	private Student student;
	private int score;
	
	public Score(Course course, Student student) {
		setCourse(course);
		setStudent(student);
		String sqlString = "SELECT score FROM score where studentId = ? and courseId = ?";
		PreparedStatement ps = DbUtil.executePreparedStatement(sqlString);
		ResultSet result;
		try {
			ps.setString(1, student.getStudentId());
			ps.setString(2, course.getCourseId());
			result = ps.executeQuery();
			if (result.next()) {
				this.score = result.getInt("score");
				result.close();
				ps.close();
			} else {
				result.close();
				ps.close();
				this.score = -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			this.score = -1;
		}
	}
	public Score(Course course, Student student, int score) {
		setCourse(course);
		setStudent(student);
		setScore(score);
	}
	
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	public int searchScore() {
		String sqlString = "SELECT score FROM score where studentId = ? and courseId = ?";
		PreparedStatement ps = DbUtil.executePreparedStatement(sqlString);
		ResultSet result;
		try {
			ps.setString(1, student.getStudentId());
			ps.setString(2, course.getCourseId());
			result = ps.executeQuery();
			if(result.next()) {
				this.score = result.getInt("score");
				ps.close();
				result.close();
				return this.score;
			}else {
				ps.close();
				result.close();
				return -1;
			}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    	return -1;
	    }
	}
}
