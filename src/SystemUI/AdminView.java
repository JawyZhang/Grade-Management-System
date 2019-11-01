package SystemUI;

import DbOperation.DbUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class AdminView extends JFrame {
    JButton delete = null;
    JTable jTable = null;
    JButton subbmit = null;
    JScrollPane jsb = null;
    JRadioButton student = null;
    JRadioButton teacher = null;
    ButtonGroup bg = null;
    JPanel navigator = null;
    JPanel downButton = null;
    JComboBox college = null;
    JButton search = null;
    DefaultTableModel tableModel = null;

    public AdminView() throws SQLException {
        navigator = new JPanel();
        navigator.setLayout(new FlowLayout());
        student = new JRadioButton("student");
        teacher = new JRadioButton("teacher");
        student.setSelected(true);
        search = new JButton("search");
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    SearchData();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        bg = new ButtonGroup();
        bg.add(student);
        bg.add(teacher);
        navigator.add(student);
        navigator.add(teacher);

        college = new JComboBox();
        getCollege();
        navigator.add(college);
        navigator.add(search);

        subbmit = new JButton("submit");
        subbmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                subbmit();
            }
        });

        delete = new JButton("delete");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                deleteItem();
            }
        });
        tableModel = new DefaultTableModel();
        jTable = new JTable(tableModel);
        jTable.setBorder(BorderFactory.createLoweredBevelBorder());
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        jTable.setDefaultRenderer(Object.class, r);
        downButton = new JPanel();
        downButton.setLayout(new FlowLayout());
        downButton.add(delete);
        downButton.add(subbmit);

        this.setLayout(new BorderLayout());
        this.add(navigator, BorderLayout.NORTH);
        jsb = new JScrollPane(jTable);
        jsb.setBorder(new EmptyBorder(5,5,5,5));
        this.add(jsb, BorderLayout.CENTER);
        this.add(downButton, BorderLayout.SOUTH);

        this.setTitle("Administrator：");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(200, 200);
        this.setVisible(true);
    }

    private void getCollege() throws SQLException {
        String sql = "SELECT name FROM college";
        ResultSet rs = DbUtil.executeQuery(sql);
        college.addItem("--请选择--");
        try {
            while (rs.next()) {
                college.addItem(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        rs.close();
        DbUtil.close();
    }

    private void SearchData() throws SQLException {
        if (tableModel != null) {
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("id");
            tableModel.addColumn("name");
            if (student.isSelected()) {
                tableModel.addColumn("class");
            }
            tableModel.addColumn("college");

            Connection conn = DbUtil.getConnection();
            String sql = "SELECT * FROM ";
            Vector row = null;
            ResultSet rs = null;
            if (student.isSelected()) {
                sql += "student ";
                if (college.getSelectedIndex() != 0) {
                    sql += "WHERE college = '";
                    sql += college.getSelectedItem().toString() + "'";
                }
                rs = DbUtil.executeQuery(sql);
                while (rs.next()) {
                    row = new Vector();
                    row.add(rs.getString("id"));
                    row.add(rs.getString("name"));
                    row.add(rs.getString("class"));
                    row.add(rs.getString("college"));
                    if (tableModel != null) {
                        tableModel.addRow(row);
                    }
                }
            } else {
                sql += "teacher ";
                if (college.getSelectedIndex() != 0) {
                    sql += "WHERE college = '";
                    sql += college.getSelectedItem().toString() + "'";
                }
                rs = DbUtil.executeQuery(sql);
                while (rs.next()) {
                    row = new Vector();
                    row.add(rs.getString("id"));
                    row.add(rs.getString("name"));
                    row.add(rs.getString("college"));
                    if (tableModel != null) {
                        tableModel.addRow(row);
                    }
                }
            }
            row = null;
            tableModel.addRow(row);
            rs.close();
            DbUtil.close();
            tableModel.fireTableDataChanged();
        }
    }

    private void subbmit(){

    }

    private void deleteItem() {
        if (jTable!=null){
            int row = jTable.getSelectedRow();
            String sql = "DELETE FROM ";//student WHERE id = '1710120009'";
            if (student.isSelected()){
                sql+="student ";
            }
            else{
                sql+="teacher ";
            }
            sql+="WHERE id = '"+jTable.getValueAt(row,0).toString()+"'";
            DbUtil.executeUpdate(sql);
            DbUtil.close();
            try {
                SearchData();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("找不到对应行");
            }
        }
    }

    private static String openFile() {//return fileName
        JFileChooser fDialog = new JFileChooser();
        fDialog.setDialogTitle("Select File");
        int returnVal = fDialog.showOpenDialog(null);
        if (JFileChooser.APPROVE_OPTION == returnVal) {
            return fDialog.getSelectedFile().toString();
        } else
            return null;
    }

    public static void main(String[] args) throws SQLException {
        AdminView av = new AdminView();
    }
}
