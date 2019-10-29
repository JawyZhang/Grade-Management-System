package DbOperation;

import java.sql.*;

public class createUser {
    public static void main(String [] args) throws SQLException {
        Connection conn = DbUtil.getConnection();
        PreparedStatement ps1 = conn.prepareStatement("SELECT id,name FROM student");
        ResultSet rs = ps1.executeQuery();
        PreparedStatement ps2 = conn.prepareStatement("INSERT INTO user(id,userName,password) VALUES(?,?,?)");
        while(rs.next()){//把已存在的学生添加到
          ps2.setString(1,rs.getString(1));
          ps2.setString(2,rs.getString(2));
          ps2.setString(3,rs.getString(1));//密码同id
          System.out.println(rs.getString(1)+" "+rs.getString(2)+"\n");
          ps2.executeUpdate();
        }
        rs.close();
        ps1.close();
        ps2.close();
        conn.close();
    }
}
