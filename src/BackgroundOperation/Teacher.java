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
		String sqlString = "SELECT name,college FROM teacher WHERE id = ?";
		PreparedStatement ps = DbUtil.executePreparedStatement(sqlString);
		try {
			ps.setString(1, teacherId);
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()) {
				setName(resultSet.getString("name"));
				setCollegeName(resultSet.getString("college"));
			}else {
				setName("�޴˽̹���");
				setCollegeName("�޴˽̹�ѧ��");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			name = "����";
			collegeName = "����";
		}
	}
	public Teacher(String name, String collegeName) {
		setName(name);
		setCollegeName(collegeName);
	}
	public Teacher(String teacherId, String name, String collegeName) {
		setTeacherId(teacherId);
		setName(name);
		setCollegeName(collegeName);
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
	
	public ResultSet getCourse() {
		String sqlString = "SELECT courseId,courseGrade,courseName FROM course WHERE teacherId = ?";
		PreparedStatement ps = DbUtil.executePreparedStatement(sqlString);
		try {
			ps.setString(1, this.teacherId);
			return ps.executeQuery();
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
			return ps.executeQuery();
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    	return null;
	    }
	}
}
