package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{


    @FXML TextField fileinptext;
    @FXML
    Tab drawPage;
    @FXML public void fileOk(){
        drawPage.setDisable(false);
        fileinptext.setText("");
        fileinptext.setText("AWYEAAH");

    };

    @Override
    public void initialize(URL location, ResourceBundle res){

        drawPage.setDisable(true);
    };


}
