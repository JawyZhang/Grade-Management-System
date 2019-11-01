package BackgroundOperation;

import DbOperation.DbUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student {
	private String studentId;
	private String studentName;
	private String className;
	private String collegeName;
	
	public Student(String studentId) {
		setStudentId(studentId);
		setStudentName(Student.getStudentnameByStudentid(studentId));
		setClassName(Student.getStudentclassByStudentid(studentId));
		setCollegeName(Student.getStudentcollegeByStudentid(studentId));
	}
	public Student(String studentName, String college) {
		setStudentName(studentName);
		setCollegeName(college);
		String sqlString = "SELECT studentId,class FROM student where studentName = ? and college = ?";
		PreparedStatement ps = DbUtil.executePreparedStatement(sqlString);
		try {
			ps.setString(1, studentName);
			ps.setString(2, college);
			ResultSet resultSet =  ps.executeQuery();
			if(resultSet.next()) {
				setStudentId(resultSet.getString("studentId"));
				setClassName(resultSet.getString("class"));
				resultSet.close();
				ps.close();
			}else {
				resultSet.close();
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
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
		String sqlString = "SELECT studentId,courseId,score FROM score WHERE studentId = "+this.studentId;
		ResultSet resultSet = DbUtil.executeQuery(sqlString);
		return resultSet;
	}
	
	public ResultSet getScore(int grade) {
		String sqlString = "SELECT studentId,courseId,score FROM score WHERE studentId = "+this.studentId+" and courseId in "+
							"(SELECT courseId FROM course WHERE courseGrade = "+grade+")";
		ResultSet resultSet = DbUtil.executeQuery(sqlString);
		return resultSet;
	}

	public static String getStudentnameByStudentid(String studentId) {
		String sqlString = "SELECT studentName FROM student where studentId = ?";
		PreparedStatement ps = DbUtil.executePreparedStatement(sqlString);
		try {
			ps.setString(1, studentId);
			ResultSet resultSet =  ps.executeQuery();
			if(resultSet.next()) {
				String str = resultSet.getString("studentName");
				resultSet.close();
				ps.close();
				return str;
			}else {
				resultSet.close();
				ps.close();
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getStudentclassByStudentid(String studentId) {
		String sqlString = "SELECT class FROM student where studentId = ?";
		PreparedStatement ps = DbUtil.executePreparedStatement(sqlString);
		try {
			ps.setString(1, studentId);
			ResultSet resultSet =  ps.executeQuery();
			if(resultSet.next()) {
				String str = resultSet.getString("class");
				resultSet.close();
				ps.close();
				return str;
			}else {
				resultSet.close();
				ps.close();
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getStudentcollegeByStudentid(String studentId) {
		String sqlString = "SELECT college FROM student where studentId = ?";
		PreparedStatement ps = DbUtil.executePreparedStatement(sqlString);
		try {
			ps.setString(1, studentId);
			ResultSet resultSet =  ps.executeQuery();
			if(resultSet.next()) {
				String str = resultSet.getString("college");
				resultSet.close();
				ps.close();
				return str;
			}else {
				resultSet.close();
				ps.close();
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
