package sample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.lang.Math;

import static javax.swing.SwingConstants.SOUTH;

public class GraphFrame  extends JFrame{

    private int widht=700;
    private int height=500;

    private int numOfUnits, numOfEdges;
    private Algorithm prim;
    private Vector<GraphStruct> abc;
    private Vector<GraphStruct> reDraw;
    private int [][] adjMatrix;
    double angle;
    int r=height/3, //height - высота окна
            hset=height/3+50, //сдвиг центра окружности, в которую вписан граф, от левого верхнего угла по вертикали
            wset=widht/2;//сдвиг по горизонтали

    private int x1,y1,x2,y2;

    private JButton finBtn = new JButton("Final step");
    private JButton nextBtn = new JButton("Next step");

    public GraphFrame(int[] intArr){
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        this.setSize(widht,height);
        this.setTitle("JPD algorithm");

        JPanel pane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        pane.setLayout(new GridBagLayout());
        JButton nextBtn = new JButton("Next step");

        c.anchor = GridBagConstraints.PAGE_END;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(nextBtn, c);

        JButton finBtn = new JButton("Final step");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        pane.add(finBtn, c);
        this.getContentPane();
        prim = new Algorithm();
        abc = new Vector<>();
        reDraw = new Vector<>();
        this.setVect(intArr);
        this.setAdjMatrix();

        int test = prim.mstPrim(adjMatrix,numOfUnits);
        System.out.println(Integer.toString(test));
        setReDraw(prim.reDraw);
        angle = 2*Math.PI/numOfUnits; //numOfUnits - число вершин графа

    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        String temp;
        int shift = 10;
        g2.setStroke(new BasicStroke(8f));

        for(int i=1; i<=numOfUnits;i++) {
            x1 = (int)(r*Math.cos(angle*i) + wset);
            y1 = (int)(r*Math.sin(angle*i) + hset);
            g2.drawLine(x1, y1, x1, y1);
            x1+=shift;
            y1+=shift*2;
            temp = Integer.toString(i);
            g2.drawString(temp, x1, y1);
        }
        g2.setStroke(new BasicStroke(1.5f));

        for (int i = 0; i<abc.size(); i++){		 //abc - вектор типа <int,int,double>, abc.size=число ребер
            x1 = (int)(r*Math.cos(angle*abc.get(i).first) + wset); //abc[i].first - начальная вершина ребра
            y1 = (int)(r*Math.sin(angle*abc.get(i).first) + hset);
            x2 = (int)(r*Math.cos(angle*abc.get(i).second) + wset);//abc[i].second - конечная вершина ребра
            y2 = (int)(r*Math.sin(angle*abc.get(i).second) + hset);

            g2.drawLine(x1, y1, x2, y2);
            x1 +=shift;
            y1 +=shift*2;
            x2 +=shift;
            y2 +=shift*2;
            temp = Double.toString(abc.get(i).weight);//abc[i].weight - конечная вершина ребра
            g2.drawString(temp, (x1+x2)/2, (y1+y2)/2);
        }
        g2.setPaint(Color.GREEN);
        g2.setStroke(new BasicStroke(3f));
        for(int i=0;i<reDraw.size();i++){
            x1 = (int)(r*Math.cos(angle*reDraw.get(i).first) + wset); //abc[i].first - начальная вершина ребра
            y1 = (int)(r*Math.sin(angle*reDraw.get(i).first) + hset);
            x2 = (int)(r*Math.cos(angle*reDraw.get(i).second) + wset);//abc[i].second - конечная вершина ребра
            y2 = (int)(r*Math.sin(angle*reDraw.get(i).second) + hset);

            g2.drawLine(x1, y1, x2, y2);

        }
        for(int i=1; i<=numOfUnits;i++) {
            x1 = (int)(r*Math.cos(angle*i) + wset);
            y1 = (int)(r*Math.sin(angle*i) + hset);
            g2.drawLine(x1, y1, x1, y1);
            x1+=shift;
            y1+=shift*2;
            temp = Integer.toString(i);
            g2.drawString(temp, x1, y1);
        }

    }


    private void setVect(int[] intArr) {
        GraphStruct temp;
        numOfUnits = intArr[0];
        numOfEdges = intArr[1];
        for (int i = 0; i < (intArr.length -2)/3; i++) {
            temp = new GraphStruct(intArr[i*3 +2],intArr[i*3 +3],intArr[i*3 +4]);
            abc.add(temp);
        }

    }
    private void setAdjMatrix(){
        adjMatrix = new int[numOfUnits][numOfUnits];
        //prim.fill2(adjMatrix,numOfUnits,prim.INF);
        for(int i=0; i<numOfUnits;i++){
            for(int j=0; j<numOfUnits;j++) adjMatrix[i][j] = prim.INF;
        }
        for(int i=0; i<abc.size();i++){
            adjMatrix[abc.get(i).first-1][abc.get(i).second-1] = (int)abc.get(i).weight;
            adjMatrix[abc.get(i).second-1][abc.get(i).first-1] = (int)abc.get(i).weight;
        }
    }
    private void setReDraw(int[][] tdr){
        GraphStruct temp;
        for(int i=0;i<numOfUnits;i++){
            for(int j=0; j<numOfUnits;j++){
                if(tdr[i][j]!=0){
                    temp = new GraphStruct(i+1,j+1,tdr[i][j]);
                    reDraw.add(temp);
                    tdr[j][i]=0;
                }
            }
        }


    }
    public class GraphStruct{
       private int first;
       private int second;
       private double weight;
        public GraphStruct(int first, int second, double weight){
            this.first = first;
            this.second = second;
            this.weight = weight;
        }
    }

}
