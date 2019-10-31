package SystemUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherView_layoutController implements Initializable {
    @FXML
    public TableView tableView;
    @FXML
    public Button btn_ok;
    @FXML
    public Button btn_delete;
    @FXML
    public Button btn_log;
    @FXML
    public TextField tf_sid;
    @FXML
    public TextField tf_cid;
    @FXML
    public TextField tf_score;
    @FXML
    public Button btn_alter;
    @FXML
    public TableColumn tc_sid;
    @FXML
    public TableColumn tc_cid;
    @FXML
    public TableColumn tc_score;


    public void ok_onClick(ActionEvent actionEvent) {
    System.exit(0);
    }

    public void delete_onclick(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tc_sid.setCellFactory(new PropertyValueFactory("Student ID"));
    }

    public void log_onclick(ActionEvent actionEvent) {
        String sid = tf_sid.getText();
        String cid = tf_cid.getText();
        String score = tf_score.getText();
        String tid = TeacherView.tid;
        System.out.println(sid);
        System.out.println(cid);
        System.out.println(score);
        System.out.println(tid);


    }
}
