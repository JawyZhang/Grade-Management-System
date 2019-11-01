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
    JButton addItem = null;
    JScrollPane jsb = null;
    JRadioButton student = null;
    JRadioButton teacher = null;
    ButtonGroup bg = null;
    JPanel navigator = null;
    JPanel downButton = null;
    JComboBox c = null;
    JComboBox college = null;
    JButton search = null;
    DefaultTableModel tableModel = null;
    JButton modify = null;

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
                    if (addItem.isEnabled() == false) {
                        addItem.setEnabled(true);
                    }
                    if (delete.isEnabled() == false) {
                        delete.setEnabled(true);
                    }
                    if (modify.isEnabled() == false) {
                        modify.setEnabled(true);
                    }
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
        c=new JComboBox();
        getCollege();
        navigator.add(college);
        navigator.add(search);

        addItem = new JButton("add");
        addItem.setEnabled(false);
        addItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                addData();
            }
        });

        delete = new JButton("delete");
        delete.setEnabled(false);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                deleteItem();
            }
        });

        modify = new JButton("modify");
        modify.setEnabled(false);
        modify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                modifyData();
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

        downButton.add(addItem);
        downButton.add(delete);
        downButton.add(modify);

        this.setLayout(new BorderLayout());
        this.add(navigator, BorderLayout.NORTH);
        jsb = new JScrollPane(jTable);
        jsb.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.add(jsb, BorderLayout.CENTER);
        this.add(downButton, BorderLayout.SOUTH);

        this.setTitle("Administrator:");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(200, 200);
        this.setVisible(true);
    }

    private void getCollege() throws SQLException {
        String sql = "SELECT name FROM college";
        ResultSet rs = DbUtil.executeQuery(sql);
        college.addItem("--è¯·é€‰æ‹©--");
        try {
            while (rs.next()) {
                college.addItem(rs.getString("name"));
                c.addItem(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        rs.close();
        DbUtil.close();
    }

    private void modifyData(){
        if (jTable != null) {
            int row = jTable.getSelectedRow();
            String sql = "UPDATE ";//student WHERE id = '1710120009'";
            if (student.isSelected()) {
                sql += "student ";
            } else {
                sql += "teacher ";
            }
            sql+="SET";
            for(int i=0;i<jTable.getColumnCount();i++){
                sql+=" "+jTable.getColumnName(i) +" = '"+jTable.getValueAt(row,i)+"', ";
            }
            sql = sql.substring(0, sql.length() - 2);

            if(student.isSelected()){
                sql += " WHERE (studentId = '" + jTable.getValueAt(row, 0).toString() + "')";
            }else{
                sql += " WHERE (id = '" + jTable.getValueAt(row, 0).toString() + "')";
            }
            System.out.println(sql);
            String message = "?Â·??????:";
            for(int i = 0;i<jTable.getColumnCount();i++){
                message += jTable.getColumnName(i)+":"+jTable.getValueAt(row,i)+" ";
            }
            message+="??????????????";
            int n = JOptionPane.showConfirmDialog(null, message, "??????", JOptionPane.YES_NO_OPTION);
            if (n == 0) {
                DbUtil.executeUpdate(sql);
                DbUtil.close();
                try {
                    SearchData();
                } catch (SQLException e) {
                    e.printStackTrace();

                    System.out.println("????????????");

                }
            } else {
                DbUtil.close();
                return;
            }
        }
    }

    private void SearchData() throws SQLException {
        if (tableModel != null) {
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            if (student.isSelected()) {
                tableModel.addColumn("studentId");
                tableModel.addColumn("studentName");
                tableModel.addColumn("class");
            }else{
                tableModel.addColumn("id");
                tableModel.addColumn("name");
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
                    row.add(rs.getString("studentId"));
                    row.add(rs.getString("studentName"));
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
            jTable.getColumnModel().getColumn(tableModel.getColumnCount()-1).setCellEditor(new DefaultCellEditor(c));
            rs.close();
            conn.close();
            DbUtil.close();
            tableModel.fireTableDataChanged();
        }
    }

    private void addData() {
        for (int i = 0; i < jTable.getColumnCount(); i++) {
            if (jTable.getValueAt(jTable.getRowCount() - 1, i) == null || jTable.getValueAt(jTable.getRowCount() - 1, i).equals("")) {
                JOptionPane.showMessageDialog(null, "Ã¨?Â¨??????Ã¨????â€°?????Â°??????", "Ã¨???â€˜?", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        String sql = "INSERT INTO ";
        if (student.isSelected()) {
            sql += "student VALUES(?,?,?,?)";
        } else {
            sql += "teacher VALUES(?,?,?)";
        }
        Connection conn = DbUtil.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            for (int i = 0; i < jTable.getColumnCount(); i++){
                ps.setString(i+1,jTable.getValueAt(jTable.getRowCount() - 1, i).toString());
            }
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "????????â€™?â€¦???Â°??????");
            ps.close();
            conn.close();
            DbUtil.close();
            SearchData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    private void deleteItem() {
        if (jTable != null) {
            int row = jTable.getSelectedRow();
            String sql = "DELETE FROM ";//student WHERE id = '1710120009'";
            if (student.isSelected()) {
                sql += "student ";
                sql += "WHERE studentId = '" + jTable.getValueAt(row, 0).toString() + "'";
            } else {
                sql += "teacher ";
                sql += "WHERE id = '" + jTable.getValueAt(row, 0).toString() + "'";
            }
            int n = JOptionPane.showConfirmDialog(null, "???Ã¨?Â¤?? Ã©?Â¤id???" + jTable.getValueAt(row, 0).toString() + "???Ã¨?Â°?????â€”???", "?? Ã©?Â¤Ã©??", JOptionPane.YES_NO_OPTION);

            if (n == 0) {
                DbUtil.executeUpdate(sql);
                DbUtil.close();
                try {
                    SearchData();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("?â€°??????Â°?????â€Ã¨??");
                }
            } else {
                DbUtil.close();
                return;
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