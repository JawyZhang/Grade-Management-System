package BackgroundOperation;

import DbOperation.DbUtil;

import javax.rmi.CORBA.Util;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ScoreManagement {
	private Teacher teacher;
	
	public ScoreManagement(Teacher teacher) {
		setTeacher(teacher);
	}
	
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	public boolean deleteScore(Score score) {
		System.out.println("‘À–– ±"+score.getStudent().getStudentId());
		String studentId = score.getStudent().getStudentId();
		String courseId = score.getCourse().getCourseId();
		String sqlString = "DELETE FROM score WHERE studentId = "+studentId+" and courseId = "+courseId;
		int result = DbUtil.executeUpdate(sqlString);
		DbUtil.close();
		if(result<1) {
			return false;
		}else {
			return true;
		}
	}
	
	public boolean logScore(Score score, int scoreGrade) {
		String studentId = score.getStudent().getStudentId();
		String courseId = score.getCourse().getCourseId();
		int score1 = scoreGrade;
		String sqlString = "INSERT INTO score(studentId,courseId,score) VALUES ("+studentId+","+courseId+","+score1+")";
		int result = DbUtil.executeUpdate(sqlString);
		if(result<1) {
			return false;
		}else {
			return true;
		}
	}
	
	public boolean alterScore(Score score, int scoreGrade) {
		String studentId = score.getStudent().getStudentId();
		String courseId = score.getCourse().getCourseId();
		String sqlString = "UPDATE score SET score = "+scoreGrade+" WHERE studentId = "+studentId+" and courseId = "+courseId;
		int result = DbUtil.executeUpdate(sqlString);
		if(result<1) {
			return false;
		}else {
			return true;
		}
	}
}