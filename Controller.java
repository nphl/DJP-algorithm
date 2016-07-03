package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public class Controller implements Initializable{

    @FXML TextField fileinptext;
    @FXML Tab drawPage;
    @FXML TextArea filefield;
    @FXML Button drawButton;
    int[] inpInt=null;
    int[] readyArr=null;
    int size=0;
    @FXML public void enterPressed(){
        fileOk();
    }
    @FXML public void drawPressed(){
        int j;
        for(j=0; j<inpInt.length;j++) if(inpInt[j]==0) break;
        readyArr = new int[j];
        for(int i=0; i<j; i++) readyArr[i] = inpInt[i];
        GraphFrame frm = new GraphFrame(readyArr);
        frm.setVisible(true);

    }

    @FXML public void fileOk(){
        int  i=0;
        String buff, buff2= new String();
        DataInputStream input=null;
        try(DataInputStream streambuff = new DataInputStream(new FileInputStream(fileinptext.getText()))){
            size = streambuff.available();
        }
        catch (IOException ex2){
            filefield.setText("Wrong path");
            filefield.setVisible(true);
            return;
        }
        inpInt = new int[size];
        try {
            input = new DataInputStream(new FileInputStream(fileinptext.getText()));

            while((buff = input.readLine())!=null){
                StringTokenizer stoken = new StringTokenizer(buff);
                while(stoken.hasMoreTokens()){
                    int ibuff = Integer.parseInt(stoken.nextToken());
                    inpInt[i] = ibuff;
                    buff2 += Integer.toString(ibuff)+" ";
                    if(i%3==1) buff2+="\n";
                    i++;
                }
            }
        }
        catch(IOException ex){

        }

        filefield.setText(buff2);
        filefield.setVisible(true);
        drawPage.setDisable(false);
        drawButton.setDisable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle res){

        drawPage.setDisable(true);
        filefield.setVisible(false);
        drawButton.setDisable(true);
    }


}
