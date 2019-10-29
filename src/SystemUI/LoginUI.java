package SystemUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class LoginUI extends Application{

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Please Login");
        FlowPane pane = new FlowPane();
        pane.setPadding(new Insets(11,12,13,14));
        pane.setVgap(15);
        pane.setHgap(15);

        Label l_role = new Label("Please select your role:");
        ToggleGroup tg_role = new ToggleGroup();
        RadioButton rb_student = new RadioButton("Student");
        rb_student.setToggleGroup(tg_role);
        rb_student.setSelected(true);
        RadioButton rb_teacher = new RadioButton("teacher");
        rb_teacher.setToggleGroup(tg_role);
        RadioButton rb_admin = new RadioButton("admin");
        rb_admin.setToggleGroup(tg_role);


        Label l_id = new Label("Please input your id:");
        TextField tf_id = new TextField();

        Label l_password = new Label("Please input your password:");
        PasswordField pw_password = new PasswordField();

        Button bt_sub = new Button("Submit");

        pane.getChildren().addAll(l_role,rb_admin,rb_student,rb_teacher,l_id,tf_id,l_password,pw_password,bt_sub);

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
