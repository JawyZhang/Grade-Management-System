package BackgroundOperation;

import DbOperation.DbUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student {
	private String studentId;
	private String className;
	private String collegeName;
	
	public Student(String studentId) {
		setStudentId(studentId);
		String sqlString = "SELECT studentName,class,college FROM student WHERE studentId = ?";
		PreparedStatement ps = DbUtil.executePreparedStatement(sqlString);
		try {
			ps.setString(1, studentId);
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()) {
				setClassName(resultSet.getString("class"));
				setCollegeName(resultSet.getString("college"));
			}else {
				setClassName("�޴�ѧ��");
				setCollegeName("�޴�ѧ��");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			className = "����";
			collegeName = "����";
		}
	}
	public Student(String studentId, String className, String college) {
		setStudentId(studentId);
		setClassName(className);
		setCollegeName(college);
	}
	
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	
	public ResultSet getScore() {
		String sqlString = "SELECT studentId,courseId,score FROM score WHERE studentId = ?";
		PreparedStatement ps = DbUtil.executePreparedStatement(sqlString);
		try {
			ps.setString(1, this.studentId);
			return ps.executeQuery();
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    	return null;
	    }
	}
	
	public ResultSet getScore(int grade) {
		String sqlString = "SELECT studentId,courseId,score FROM score WHERE studentId = ? and courseId in "+
							"(SELECT courseId FROM course WHERE courseGrade = ?)";
		PreparedStatement ps = DbUtil.executePreparedStatement(sqlString);
		try {
			ps.setString(1, this.studentId);
			ps.setInt(2, grade);
			return ps.executeQuery();
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    	return null;
	    }
	}
	
	public static String getStudentnameByStudentid(String studentId) {
		String sqlString = "SELECT studentName FROM student where studentId = ?";
		PreparedStatement ps = DbUtil.executePreparedStatement(sqlString);
		try {
			ps.setString(1, studentId);
			ResultSet resultSet =  ps.executeQuery();
			if(resultSet.next()) {
				return resultSet.getString("studentName");
			}else {
				return null;
			}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    	return null;
	    }
	}
}
