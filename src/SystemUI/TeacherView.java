package SystemUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TablePosition;
import javafx.stage.Stage;

import javax.swing.text.TabableView;

public class TeacherView extends Application {
    @FXML
    private TabableView tabableView ;

    @FXML
    private Button btn_ok;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("TeacherView_layout.fxml"));
        Scene scene = new Scene(root,600,400);
        stage.setScene(scene);
        stage.show();
    }
}
