package BackgroundOperation;

import DbOperation.DbUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Teacher {
	private String teacherId;
	private String name;
	private String collegeName;
	
	public Teacher(String teacherId) {
		setTeacherId(teacherId);
		setName(Teacher.getTeacherNameById(teacherId));
		setCollegeName(Teacher.getTeacherCollegeById(teacherId));
	}
	public Teacher(String name, String collegeName) {
		setName(name);
		setCollegeName(collegeName);
		String sqlString = "SELECT id FROM teacher WHERE name = ? and college = ?";
		PreparedStatement ps = DbUtil.executePreparedStatement(sqlString);
		try {
			ps.setString(1, name);
			ps.setString(2, collegeName);
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()) {
				setTeacherId(resultSet.getString("id"));
				ps.close();
				resultSet.close();
			}else {
				ps.close();
				resultSet.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public static String getTeacherNameById(String teacherId){
		String sqlString = "SELECT name FROM teacher WHERE id = ?";
		PreparedStatement ps = DbUtil.executePreparedStatement(sqlString);
		try {
			ps.setString(1, teacherId);
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()) {
				String str = resultSet.getString("name");
				ps.close();
				resultSet.close();
				return  str;
			}else {
				ps.close();
				resultSet.close();
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getTeacherCollegeById(String teacherId){
		String sqlString = "SELECT college FROM teacher WHERE id = ?";
		PreparedStatement ps = DbUtil.executePreparedStatement(sqlString);
		try {
			ps.setString(1, teacherId);
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()) {
				String str = resultSet.getString("college");
				ps.close();
				resultSet.close();
				return  str;
			}else {
				ps.close();
				resultSet.close();
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ResultSet getCourse() {
		String sqlString = "SELECT courseId,courseGrade,courseName FROM course WHERE teacherId = ?";
		PreparedStatement ps = DbUtil.executePreparedStatement(sqlString);
		try {
			ps.setString(1, this.teacherId);
			ResultSet resultSet = ps.executeQuery();
			ps.close();
			return resultSet;
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    	return null;
	    }
	}
	
	public ResultSet getCourseStudentAndScore(String courseId) {
		String sqlString = "SELECT studentId,score FROM score where courseId = ?";
		PreparedStatement ps = DbUtil.executePreparedStatement(sqlString);
		try {
			ps.setString(1, courseId);
			ResultSet resultSet = ps.executeQuery();
			ps.close();
			return resultSet;
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    	return null;
	    }
	}
}
