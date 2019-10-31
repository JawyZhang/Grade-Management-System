package SystemUI;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.text.TabableView;

public class TeacherView extends Application {
    @FXML
    private TabableView tabableView ;

    @FXML
    private Button btn_ok;
    public static String tid;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("TeacherView_layout.fxml"));
        Scene scene = new Scene(root,600,400);
        tid = "1710121285";
//        StudentView_bean bean = new StudentView_bean(sid,cid,score,tid);
//        ObservableList<StudentView_bean> list = FXCollections.observableArrayList();
//        list.add(bean);
//        tableView = new TableView(list);
//        tc_sid.setCellFactory(new Callback<TableColumn.CellDataFeatures<StudentView_bean,String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<StudentView_bean, String> param) {
//                SimpleStringProperty sid = new SimpleStringProperty(param.getValue().getSid());
//                return sid;
//            }
//        });
        stage.setScene(scene);
        stage.show();
    }
}
