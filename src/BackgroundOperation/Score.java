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
				return this.score;
			}else {
				return -1;
			}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    	return -1;
	    }
	}
}
