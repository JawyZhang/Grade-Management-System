package SystemUI;

import BackgroundOperation.*;
import DbOperation.DbUtil;

import javax.rmi.CORBA.Util;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TeacherView extends JFrame {
    JTable jTable = null;
    JButton log = null;
    JButton delete = null;
    JButton alter = null;
    JScrollPane jsb = null;
    JPanel downButton = null;
    public static DefaultTableModel tableModel = null;
    public static Teacher teacher;
    ScoreManagement scoreManagement;
    public static String courseId;

    public TeacherView(String teacherId, String courseId) throws SQLException {
        teacher = new Teacher(teacherId);
        scoreManagement = new ScoreManagement(teacher);
        this.courseId = courseId;

        log = new JButton("logScore");
        log.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (jTable!=null){
                    int row = jTable.getSelectedRow();
                    if(jTable.getValueAt(row,0)==null||jTable.getValueAt(row,0).toString().equals("")||jTable.getValueAt(row,2)==null||jTable.getValueAt(row,2).toString().equals("")){
                        JOptionPane.showMessageDialog(null,"请将学生Id和成绩填写完整并回车","错误",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String studentId = jTable.getValueAt(row,0).toString();
                    if(scoreManagement.logScore(new Score(new Course(courseId), new Student(studentId)),Integer.parseInt(jTable.getValueAt(row,2).toString()))){

                    };
                    try {
                        TeacherView.getData();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        delete = new JButton("deleteScore");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (jTable!=null){
                    int row = jTable.getSelectedRow();
                    String studentId = jTable.getValueAt(row,0).toString();
                    if(scoreManagement.deleteScore(new Score(new Course(courseId), new Student(studentId)))){
                        DbUtil.close();
                        System.out.println("成功");
                    }else{
                        System.out.println(studentId);
                        System.out.println("失败");
                        DbUtil.close();
                    }
                    try {
                        TeacherView.getData();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        alter = new JButton("alterScore");
        alter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (jTable!=null){
                    int row = jTable.getSelectedRow();
                    if(jTable.getValueAt(row,0)==null||jTable.getValueAt(row,0).toString().equals("")||jTable.getValueAt(row,2)==null||jTable.getValueAt(row,2).toString().equals("")){
                        JOptionPane.showMessageDialog(null,"请将学生Id和成绩填写完整并回车","错误",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String studentId = jTable.getValueAt(row,0).toString();
                    if(scoreManagement.alterScore(new Score(new Course(courseId), new Student(studentId)),Integer.parseInt(jTable.getValueAt(row,2).toString()))){

                    };
                    try {
                        TeacherView.getData();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        tableModel = new DefaultTableModel();
        jTable = new JTable(tableModel);
        jTable.setBorder(BorderFactory.createLoweredBevelBorder());
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        jTable.setDefaultRenderer(Object.class, r);
        getData();

        downButton = new JPanel();
        downButton.setLayout(new FlowLayout());
        downButton.add(log);
        downButton.add(alter);
        downButton.add(delete);

        this.setLayout(new BorderLayout());
        jsb = new JScrollPane(jTable);
        jsb.setBorder(new EmptyBorder(5,5,5,5));
        this.add(jsb, BorderLayout.CENTER);
        this.add(downButton, BorderLayout.SOUTH);

        this.setTitle("Course Select");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(200, 200);
        this.setVisible(true);
    }

    public static void getData() throws SQLException {
        if (tableModel != null) {
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("studentId");
            tableModel.addColumn("studentName");
            tableModel.addColumn("score");

            ResultSet rs = teacher.getCourseStudentAndScore(courseId);
            Vector row = null;
            while (rs.next()) {
                row = new Vector();
                row.add(rs.getString("studentId"));
                row.add(Student.getStudentnameByStudentid(rs.getString("studentId")));
                row.add(rs.getString("score"));
                if (tableModel != null) {
                    tableModel.addRow(row);
                }
            }

            row = null;
            tableModel.addRow(row);
            rs.close();
            DbUtil.close();
            tableModel.fireTableDataChanged();
        }
    }
}
