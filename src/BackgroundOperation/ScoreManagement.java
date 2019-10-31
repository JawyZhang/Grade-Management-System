package BackgroundOperation;

import DbOperation.DbUtil;

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
		String sqlString = "DELETE FROM score WHERE studentId = ? and courseId = ?";
		PreparedStatement ps = DbUtil.executePreparedStatement(sqlString);
		try {
			ps.setString(1, score.getCourse().getCourseId());
			ps.setString(2, score.getStudent().getStudentId());
			int result = ps.executeUpdate();
			if(result<1) {
				return false;
			}else {
				return true;
			}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    	return false;
	    }
	}
	
	public boolean logScore(Score score, int scoreGrade) {
		String sqlString = "INSERT INTO score(studentId,courseId,score) VALUES (?,?,?)";
		PreparedStatement ps = DbUtil.executePreparedStatement(sqlString);
		try {
			ps.setString(1, score.getStudent().getStudentId());
			ps.setString(2, score.getCourse().getCourseId());
			ps.setInt(3, scoreGrade);
			int result = ps.executeUpdate();
			if(result<1) {
				return false;
			}else {
				return true;
			}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    	return false;
	    }
	}
	
	public boolean alterScore(Score score, int scoreGrade) {
		String sqlString = "UPDATE score SET score = ? WHERE studentId = ? and teacherId = ?";
		PreparedStatement ps = DbUtil.executePreparedStatement(sqlString);
		try {
			ps.setInt(1, scoreGrade);
			ps.setString(2, score.getStudent().getStudentId());
			ps.setString(3, score.getCourse().getTeacher().getTeacherId());
			int result = ps.executeUpdate();
			if(result<1) {
				return false;
			}else {
				return true;
			}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    	return false;
	    }
	}
}