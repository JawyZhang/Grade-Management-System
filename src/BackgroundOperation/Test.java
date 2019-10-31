package BackgroundOperation;

import java.sql.ResultSet;
import java.sql.SQLException;

//����Ϊ���������
public class Test {

	public static void main(String[] args) throws SQLException {
		Student student = new Student("1710120000");
		Teacher teacher = new Teacher("12865");
		Course course = new Course(teacher, "1", "�ߵ���ѧ",1);
		Score score = new Score(course, student);
		ScoreManagement scoreManagement = new ScoreManagement(teacher);
		
		ResultSet resultSet1 = student.getScore();
		resultSet1.next();
		System.out.println("ѧ���γ�����"+Course.getCourseName(resultSet1.getString("courseId"))+"�ɼ���"+resultSet1.getInt("score") );
		resultSet1.close();
		
		ResultSet resultSet2 = student.getScore(1);
		resultSet2.next();
		System.out.println("ѧ���γ�����"+Course.getCourseName(resultSet2.getString("courseId"))+"�ɼ���"+resultSet2.getInt("score") );
		resultSet2.close();
		
		ResultSet resultSet3 = teacher.getCourse();
		resultSet3.next();
		System.out.println("��ʦ�γ�����"+resultSet3.getString("courseName"));
		
		ResultSet resultSet4 = teacher.getCourseStudentAndScore(resultSet3.getString("courseId"));
		resultSet4.next();
		System.out.println("ѧ���ɼ���"+resultSet4.getString("score"));
		resultSet4.close();
		resultSet3.close();
		
		System.out.println("ѧ���ɼ���"+score.searchScore());
		
		
	}

}
