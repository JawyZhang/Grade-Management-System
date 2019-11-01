package SystemUI;



import BackgroundOperation.ScoreManagement;
import BackgroundOperation.Teacher;
import DbOperation.DbUtil;

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

public class TeacherCourseSelectView extends JFrame {
    JTable jTable = null;
    JButton subbmit = null;
    JScrollPane jsb = null;
    JPanel downButton = null;
    DefaultTableModel tableModel = null;
    Teacher teacher;
    ScoreManagement scoreManagement;

    public TeacherCourseSelectView(String teacherId) throws SQLException {
        teacher = new Teacher(teacherId);
        scoreManagement = new ScoreManagement(teacher);

        subbmit = new JButton("select this course");
        subbmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (jTable!=null){
                    int row = jTable.getSelectedRow();
                    if(jTable.getSelectedRow() == jTable.getRowCount()-1){
                        JOptionPane.showMessageDialog(null,"Please select course!","Error",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String courseId = jTable.getValueAt(row,0).toString();
                    try {
                        TeacherView teacherView = new TeacherView(teacherId, courseId);
                        dispose();
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
        downButton.add(subbmit);

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

    private void getData() throws SQLException {
        if (tableModel != null) {
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("courseId");
            tableModel.addColumn("courseName");
            tableModel.addColumn("grade");

            ResultSet rs = teacher.getCourse();
            Vector row = null;
            while (rs.next()) {
                row = new Vector();
                row.add(rs.getString("courseId"));
                row.add(rs.getString("courseName"));
                row.add(rs.getString("courseGrade"));
                if (tableModel != null) {
                    tableModel.addRow(row);
                }
            }

            row = null;
            tableModel.addRow(row);
            rs.close();
            tableModel.fireTableDataChanged();
        }
    }

    public static void main(String[] args) throws SQLException {
        TeacherCourseSelectView tv = new TeacherCourseSelectView("12510");
    }
}
