package SystemUI;

import DbOperation.DbUtil;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import javax.swing.text.TabExpander;
import javax.swing.text.TabableView;
import java.awt.*;
import java.rmi.server.ExportException;

public class StudentView extends Application{

    @FXML
    private int id ;
    private Stage stage1 ;
    private  TabableView tabableView;
    @FXML
    private Button btn_ok;
    @Override
    public void start(Stage stage) throws Exception {


            Parent root = FXMLLoader.load(getClass().getResource("StudentView_layout.fxml"));
            Scene scene = new Scene(root,600,400);
            this.stage1 = stage;

            stage1.setScene(scene);
            stage1.show();

    }


    public void onClick(ActionEvent actionEvent) {
        try {

           System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }
    public void setInformation(int id)
    {
        this.id = id;

    }
}
