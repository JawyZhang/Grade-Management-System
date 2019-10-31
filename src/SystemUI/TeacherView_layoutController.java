package SystemUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;



public class TeacherView_layoutController {
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


    public void ok_onClick(ActionEvent actionEvent) {
    System.exit(0);
    }

    public void delete_onclick(ActionEvent actionEvent) {

    }
}
