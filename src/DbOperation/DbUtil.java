package DbOperation;

import java.sql.*;

public class DbUtil {
    /**
     * 声明连接数据库的信息，如数据库URL、用户名及密码
     */
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/user?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    /**
     * 声明JDBC相关对象
     */
    protected static Statement s = null;
    protected static Statement ps = null;
    protected static ResultSet rs = null;
    protected static Connection conn = null;

    /**
     * 创建数据库连接
     *synchronized修饰一个方法，被修饰的方法称为同步方法，其作用的范围是整个方法，作用的对象是调用这个方法的对象；
     * @return conn
     */
    public static synchronized Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 执行INSERT/UPDATE/DELETE SQL语句
     *
     * @param sql
     *            SQL语句，字符串类型
     * @return 执行结果，int类型
     */
    public static int executeUpdate(String sql) {
        int count = 0;
        try {
            s = getConnection().createStatement();
            count = s.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 执行SELECT SQL语句
     *
     * @param sql
     *            SQL语句，字符串类型
     * @return ResultSet结果集
     */
    public static ResultSet executeQuery(String sql) {

        try {
            s = getConnection().createStatement();
            rs = s.executeQuery(sql);
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return rs;
    }
    /**
     * 执行SELECT SQL语句使用PreparedStatment
     * @param sql
     * @return
     */
    public static ResultSet executeQuerywithPrepare(String sql) {

        try {
            s = getConnection().prepareStatement(sql);
            rs = s.executeQuery(sql);
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return rs;
    }

    /**
     * 执行动态SQL语句
     *
     * @param sql
     *            含有参数的动态SQL语句。
     * @return 返回PreparedStatement对象
     */
    public static PreparedStatement executePreparedStatement(String sql) {
        PreparedStatement ps = null;
        try {
            ps = getConnection().prepareStatement(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ps;
    }

    /**
     * 事务回滚
     */
    public static void rollback() {
        try {
            getConnection().rollback();
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    /**
     * 关闭数据库连接对象
     */
    public static void close() {
        try {
            if (rs != null)
                rs.close();
            if (s != null)
                s.close();
            if (conn != null)
                conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        /*
         * String sql =
         * "INSERT INTO tb_users(fd_username,fd_password,fd_usertype,fd_gender,fd_birthdate,fd_email) VALUES ('Wangli','aWeY92,zeP', "
         * + "'管理员','女','1999-10-22','allen@henu.edu.cn')"; executeUpdate(sql);
         * close();
         */
        // 编写SQL语句
        String sql = "INSERT INTO tb_users(fd_username,fd_password,fd_usertype,fd_gender,fd_email,"
                + "fd_birthdate, fd_introduction,fd_hobby) VALUES (?,?,?,?,?,?,?,?)";

        // 执行SQL
        PreparedStatement ps = DbUtil.executePreparedStatement(sql);
        try {
            ps.setString(1, "username");
            ps.setString(2, "password");
            ps.setString(3, "1");
            ps.setString(4, "男");
            ps.setString(5, "email");
            ps.setString(6, "birthdate");
            ps.setString(7, "introduction");
            ps.setString(8, "hobby");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int result = 0;
        try {
            result = ps.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            ps.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
