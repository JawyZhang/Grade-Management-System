package SystemUI;

import DbOperation.DbUtil;
import java.sql.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class LaunchView extends Application {
    String id;
    String password;
    Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Please Login");
        FlowPane pane = new FlowPane();
        pane.setPadding(new Insets(11, 12, 13, 14));
        pane.setVgap(15);
        pane.setHgap(15);


        Label l_id = new Label("Please input your id:");
        TextField tf_id = new TextField();
        tf_id.textProperty().addListener(new ChangeListener<String>() { //设置只能输入数字
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    tf_id.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        Label l_password = new Label("Please input your password:");
        PasswordField pw_password = new PasswordField();

        Button bt_sub = new Button("Submit");


        bt_sub.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                //提交前非空检查
                if (tf_id.getText() == null || tf_id.getText().equals("")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Id  can not be null");
                    alert.showAndWait()
                            .filter(response -> response == ButtonType.OK);
                    System.out.println("id is null");
                    return;
                } else if (pw_password.getText() == null || pw_password.getText().equals("")) {
                    System.out.println("password is null");
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Password can not be null");
                    alert.showAndWait()
                            .filter(response -> response == ButtonType.OK);
                    return;
                }
                /*****************************
                 * 判断身份并查表
                 * ****************************
                 */
                password = pw_password.getText();
                id = tf_id.getText();
                try {
                    Login(id, password);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }


        });

        pane.getChildren().addAll(l_id, tf_id, l_password, pw_password, bt_sub);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void Login(String id, String password) throws SQLException {
        String sql = "select * from user where id = ? and password = ?";

        Connection cnn = DbUtil.getConnection();
        PreparedStatement ps = null;
        try {
            ps = cnn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ps.setString(1, id);
        ps.setString(2, password);
        System.out.println(sql);

        ResultSet rs = ps.executeQuery();

        if(!rs.next())
        {
            System.out.println("Your id or password is wrong");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Your id or password is wrong");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK);
        }
        else
        {
            if(rs.getString("userType").equals("teacher")) {
                TeacherCourseSelectView tv = new TeacherCourseSelectView(id);
                primaryStage.close();
            }else if(rs.getString("userType").equals("student")) {
                StudentView sv = new StudentView(id);
                primaryStage.close();
            }else if(rs.getString("userType").equals("admin")) {
                AdminView av = new AdminView();
                primaryStage.close();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please contact administrater");
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK);
            }
        }
    }
}
