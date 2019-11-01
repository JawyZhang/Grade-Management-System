package SystemUI;

import BackgroundOperation.Course;
import BackgroundOperation.Student;
import DbOperation.DbUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class StudentView extends JFrame {
    JTable jTable = null;
    JScrollPane jsb = null;
    JPanel navigator = null;
    public static JComboBox grade = null;
    JButton search = null;
    public static DefaultTableModel tableModel = null;
    public static Student student = null;

    public StudentView(String studentId) throws SQLException {
        this.student = new Student(studentId);
        navigator = new JPanel();
        navigator.setLayout(new FlowLayout());

        search = new JButton("search");
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if(grade.getSelectedIndex()!=0){
                        StudentView.dataInit(Integer.parseInt(grade.getSelectedItem().toString()));
                    }else{
                        StudentView.dataInit();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        grade = new JComboBox();
        getGrade();
        navigator.add(grade);
        navigator.add(search);


        tableModel = new DefaultTableModel();
        jTable = new JTable(tableModel);
        jTable.setBorder(BorderFactory.createLoweredBevelBorder());
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        jTable.setDefaultRenderer(Object.class, r);
        dataInit();

        this.setLayout(new BorderLayout());
        this.add(navigator, BorderLayout.NORTH);
        jsb = new JScrollPane(jTable);
        jsb.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.add(jsb, BorderLayout.CENTER);

        this.setTitle("Student??");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(200, 200);
        this.setVisible(true);
    }

    private void getGrade() throws SQLException {
        grade.addItem("-grade-");
        for(int i = 1; i<=8; i++){
            grade.addItem(i);
        }
    }

    public static void dataInit() throws SQLException {
        if (tableModel != null) {
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("courseId");
            tableModel.addColumn("courseName");
            tableModel.addColumn("score");
            tableModel.addColumn("grade");
            ResultSet rs = student.getScore();
            Vector row = null;
            while (rs.next()) {
                row = new Vector();
                row.add(rs.getString("courseId"));
                row.add(Course.getCourseName(rs.getString("courseId")));
                row.add(rs.getInt("score"));
                row.add(Course.getCourseGrade(rs.getString("courseId")));
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

    public static void dataInit(int grade) throws SQLException {
        if (tableModel != null) {
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("courseId");
            tableModel.addColumn("courseName");
            tableModel.addColumn("score");
            tableModel.addColumn("grade");
            ResultSet rs = student.getScore(grade);
            Vector row = null;
            while (rs.next()) {
                row = new Vector();
                row.add(rs.getString("courseId"));
                row.add(Course.getCourseName(rs.getString("courseId")));
                row.add(rs.getInt("score"));
                row.add(Course.getCourseGrade(rs.getString("courseId")));
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


    public static void main(String[] args) throws SQLException {
        StudentView av = new StudentView("1710120007");
    }
}
