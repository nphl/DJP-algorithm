package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    @FXML
    TextField fileinptext;
    @FXML
    TextField numrootstext;
    @FXML
    TextField numribestext;
    @FXML
    TextField firstroottext;
    @FXML
    TextField secondroottext;
    @FXML
    TextField weighttext;

    @FXML
    Tab drawPage = new Tab();
    @FXML
    Tab inputPage = new Tab();
    @FXML
    TabPane mainTabPane = new TabPane();

    @FXML
    TextArea
    filefield;
    @FXML
    TextArea
    hinputfield;

    @FXML
    Button drawButton;
    @FXML
    Button okribebutton;

    @FXML
    Label hinputlabel;
    @FXML
    Label drawLbl1;

    @FXML
    Label drawLbl2;

    @FXML
    Circle
    baseDrawCircle;

    @FXML
    private Pane drawPane;

    int[] inpInt = null;
    int[] readyArr = null;
    int size = 0;
    int handInputTemp = 1;
    String handInputTempStr = new String();
    private Vector<GraphStruct> abc = new Vector<>();

    @FXML
    public void enterPressed() {
        fileOk();
    }
    @FXML
    public void edgeEnterPressed() { okRBPressed(); }
    @FXML
    public void numOfRootsEnterPressed() { okRRPressed();};

    @FXML
    public void okRRPressed() {
        if(numrootstext.getText().trim().isEmpty()) return;
        size = Integer.parseInt(numrootstext.getText());
        inpInt = new int[size * (size) + 4];
        inpInt[0] = size;

        firstroottext.setVisible(true);
        secondroottext.setVisible(true);
        weighttext.setVisible(true);
        okribebutton.setVisible(true);
        hinputlabel.setVisible(true);
    }

    @FXML
    public void okRBPressed() {
        if(firstroottext.getText().trim().isEmpty()||secondroottext.getText().trim().isEmpty()||weighttext.getText().trim().isEmpty()) return;
        inpInt[handInputTemp] = Integer.parseInt(firstroottext.getText());
        inpInt[handInputTemp + 1] = Integer.parseInt(secondroottext.getText());
        inpInt[handInputTemp + 2] = Integer.parseInt(weighttext.getText());
        handInputTemp += 3;
        handInputTempStr += firstroottext.getText() + " " + secondroottext.getText() + " " + weighttext.getText() + "\n";
        hinputfield.setVisible(true);
        hinputfield.setText(handInputTempStr);
        firstroottext.setText("");
        secondroottext.setText("");
        weighttext.setText("");
        drawButton.setDisable(false);
    }

    private void resizeInput() {
        int j;
        for (j = 0; j < inpInt.length; j++) if (inpInt[j] == 0) break;
        readyArr = new int[j];
        System.out.println(j);
        for (int i = 0; i < j; i++) readyArr[i] = inpInt[i];

        GraphStruct temp;
        for (int i = 0; i < (readyArr.length - 1) / 3; i++) {
            temp = new GraphStruct(readyArr[i * 3 + 1], readyArr[i * 3 + 2], readyArr[i * 3 + 3]);
            abc.add(temp);
        }
    }

    @FXML
    public void drawPressed() {

        resizeInput();
        SingleSelectionModel<Tab> selectionModel = mainTabPane.getSelectionModel();

        if(selectionModel.getSelectedIndex() == 0){
            selectionModel.select(1);
        } else {
            selectionModel.select(0);
        }
        drawPage.setDisable(false);
        drawGraph();
        //drawPage.

    }

    @FXML
    public void fileOk() {
        int i = 0;
        String buff, buff2 = new String();
        DataInputStream input = null;
        try (DataInputStream streambuff = new DataInputStream(new FileInputStream(fileinptext.getText()))) {
            size = streambuff.available();
        } catch (IOException ex2) {
            filefield.setText("Wrong path");
            filefield.setVisible(true);
            return;
        }
        inpInt = new int[size];
        try {
            input = new DataInputStream(new FileInputStream(fileinptext.getText()));

            while ((buff = input.readLine()) != null) {
                StringTokenizer stoken = new StringTokenizer(buff);
                while (stoken.hasMoreTokens()) {
                    int ibuff = Integer.parseInt(stoken.nextToken());
                    inpInt[i] = ibuff;
                    buff2 += Integer.toString(ibuff) + " ";
                    if (i % 3 == 0) buff2 += "\n";
                    i++;
                }
            }
        } catch (IOException ex) {

        }
        size = inpInt[0];
        filefield.setText(buff2);
        filefield.setVisible(true);
        //drawPage.setDisable(false);
        drawButton.setDisable(false);
    }

    private void drawGraph(){

        List CircList = new ArrayList<>(); //точки
        List LblList = new ArrayList<>(); //лейблы
        List LineList = new ArrayList<>(); // линии
        Label V1, V2, V3;
        Line edge;
        double x1,x2,y1,y2;
        int shiftx = 0, shifty=0;
        for (int i = 0; i < abc.size(); i++) {
            x1 = baseDrawCircle.getRadius() * Math.cos(2 * Math.PI / size*abc.get(i).first) + baseDrawCircle.getCenterX();
            y1 = baseDrawCircle.getRadius() * Math.sin(2 * Math.PI / size * abc.get(i).first) + baseDrawCircle.getCenterY();
            x2 = baseDrawCircle.getRadius() * Math.cos(2 * Math.PI / size * abc.get(i).second) + baseDrawCircle.getCenterX();
            y2 = baseDrawCircle.getRadius() * Math.sin(2 * Math.PI / size * abc.get(i).second) + baseDrawCircle.getCenterY();
          //  CircList.add(new Circle(x1, y1, 7, Color.GRAY));
          //  CircList.add(new Circle(x2, y2, 7, Color.GRAY));
          //  LblList.add(new Label(" " + i));
            edge = new Line(x1, y1, x2, y2);
            edge.setStrokeWidth(1);
            edge.setStroke(Color.WHITE);
            LineList.add(edge);
            drawPane.getChildren().addAll(edge, new Circle(x1, y1, 9, Color.CORNFLOWERBLUE), new Circle(x2, y2, 9, Color.CORNFLOWERBLUE));
            x1 += -4;
            y1 += -9;
            x2 += -4;
            y2 += -9; //рисование имен вершин
            V1 = new Label("" + abc.get(i).first);
            V1.setLayoutX(x1);
            V1.setLayoutY(y1);
            V2 = new Label("" + abc.get(i).second);
            V2.setLayoutX(x2);
            V2.setLayoutY(y2);
            V3 = new Label("" + abc.get(i).weight);
            V3.setLayoutX((x1 + x2)/2 + shiftx);
            V3.setLayoutY((y1 + y2)/2 + shifty);
            V1.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #660000; -fx-font-family: \"Impact\";");
            V2.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #660000; -fx-font-family: \"Impact\";");
            V3.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #FFFFFF; -fx-font-family: \"Impact\";");
            drawPane.getChildren().addAll(V1, V2, V3);
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle res) {

        drawPage.setDisable(true);
        filefield.setVisible(false);
        drawButton.setDisable(true);
        hinputfield.setVisible(false);
        okribebutton.setVisible(false);
        firstroottext.setVisible(false);
        secondroottext.setVisible(false);
        weighttext.setVisible(false);
        hinputlabel.setVisible(false);
       // mainTabPane.getTabs().addAll(inputPage, drawPage);
    }


}
