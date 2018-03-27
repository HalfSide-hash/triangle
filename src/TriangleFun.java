/*Sylvester DelVillar & Parth Patel
 * October 12th, 2016
 * Section 2
 */

import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TriangleFun extends Application { 
	//this stuff basically accounts for screen size
	private static final int SIZE = 360; //360 because circle get it hahaha
    private int a = SIZE / 2;
    private int b = a;
    private int r = 4 * SIZE / 5;
    private int n;
    private Circle mainCircle;
    private static int[] x = new int[3];
    private static int[] y = new int[3];
    //needed for randomly putting the circles on
    Random rand = new Random();
  
	private double rade = 20; //just a constant to make it look nice when setting angle text
	private Circle[] smallCircle = new Circle[3]; //smaller circles on bigger circles
	private Line[] triangleLine = {new Line(), new Line(), new Line()};
	private Text[] angleText = {new Text(), new Text(), new Text()};
  
  
  @Override 
  public void start(Stage primaryStage) {  
    Pane pane = new Pane();  
    Scene scene = new Scene(pane, SIZE, SIZE);
    primaryStage.setTitle("Triangle Fun"); 
    primaryStage.setScene(scene);
    scene.setFill(Color.WHITE); //just to make sure it's white
    primaryStage.setResizable(false); //just in case the user tries any funny stuff
    primaryStage.show(); 
    
    //just in case the size is not big enough it'd change for any size
    a = (int) (scene.getHeight() / 2);
    b = (int) (scene.getWidth() / 2);
    
    //just checks to see which is smaller height or width
    int m =  Math.min(a, b);
    
    //based on the previous answer computes a suitable radius
    r = 4 * m / 5;
    n = (int) scene.getHeight();
    int r2 = Math.abs(m - r) / 2;
    
    //the main circle
    mainCircle = new Circle(a, b, r);
    mainCircle.setStroke(Color.BLACK);
    mainCircle.setFill(null);
    
    //the smaller circles randomly initialize on the bigger circles
    for (int i = 0; i < 3; i++) {
        double t = rand.nextInt() * Math.PI * i / n;
        x[i] = (int) Math.round(a + r * Math.cos(t));
        y[i] = (int) Math.round(b + r * Math.sin(t));
        smallCircle[i] = new Circle(x[i], y[i], r2);}
    
    //creates lines
    setLines();
    
    //puts the desired objects on the form
    pane.getChildren().addAll(mainCircle, smallCircle[0], smallCircle[1], smallCircle[2],
      triangleLine[0], triangleLine[1], triangleLine[2], angleText[0], angleText[1], angleText[2]);

    //mouse movement
    smallCircle[0].setOnMouseDragged(e -> { 
        // computes and display angles 
    	setLines(); // gets it to dynamically changed if dragged
    	//computing the angle to get the small circles to move along the big circle
    	double stheta = Math.atan2(e.getY() - mainCircle.getCenterY(), e.getX() - mainCircle.getCenterX());
        smallCircle[0].setCenterX(mainCircle.getCenterX() + mainCircle.getRadius() * Math.cos(stheta));
        smallCircle[0].setCenterY(mainCircle.getCenterY() + mainCircle.getRadius() * Math.sin(stheta));
    	//does it twice to dynamically change it
        setLines();
    });
 
    smallCircle[1].setOnMouseDragged(e -> { 
    	setLines();
    	double stheta = Math.atan2(e.getY() - mainCircle.getCenterY(), e.getX() - mainCircle.getCenterX());
        smallCircle[1].setCenterX(mainCircle.getCenterX() + mainCircle.getRadius() * Math.cos(stheta));
        smallCircle[1].setCenterY(mainCircle.getCenterY() + mainCircle.getRadius() * Math.sin(stheta));
        setLines();
    });
    
    smallCircle[2].setOnMouseDragged(e -> { 
    	setLines();
    	double stheta = Math.atan2(e.getY() - mainCircle.getCenterY(), e.getX() - mainCircle.getCenterX());
        smallCircle[2].setCenterX(mainCircle.getCenterX() + mainCircle.getRadius() * Math.cos(stheta));
        smallCircle[2].setCenterY(mainCircle.getCenterY() + mainCircle.getRadius() * Math.sin(stheta));
        setLines();
    });
  }
  
//to actually load the lines that connect to the smaller circles
  private void setLines() {
    
	for (int i = 0; i < 2; i++) {
	triangleLine[i].setStartX(smallCircle[i].getCenterX());//circle 1 
    triangleLine[i].setStartY(smallCircle[i].getCenterY());
    triangleLine[i].setEndX(smallCircle[i+1].getCenterX());//to circle 2 
    triangleLine[i].setEndY(smallCircle[i+1].getCenterY());
    	if (i == 0){ //for the third line
    		triangleLine[i+2].setStartX(smallCircle[i].getCenterX());
    	    triangleLine[i+2].setStartY(smallCircle[i].getCenterY());
    	    triangleLine[i+2].setEndX(smallCircle[i+2].getCenterX());
    	    triangleLine[i+2].setEndY(smallCircle[i+2].getCenterY());
    	}
	}
  
    // Computes the angles
    double a = new Point2D(smallCircle[2].getCenterX(), smallCircle[2].getCenterY()).
            distance(smallCircle[1].getCenterX(), smallCircle[1].getCenterY());
    double b = new Point2D(smallCircle[2].getCenterX(), smallCircle[2].getCenterY()).
            distance(smallCircle[0].getCenterX(), smallCircle[0].getCenterY());
    double c = new Point2D(smallCircle[1].getCenterX(), smallCircle[1].getCenterY()).
            distance(smallCircle[0].getCenterX(), smallCircle[0].getCenterY());     
    double[] angle = new double[3];
    angle[0] = Math.acos((a * a - b * b - c * c) / (-2 * b * c));
    angle[1] = Math.acos((b * b - a * a - c * c) / (-2 * a * c));
    angle[2] = Math.acos((c * c - b * b - a * a) / (-2 * a * b));

    for (int i = 0; i < 3; i++) {
      double stheta = Math.atan2(smallCircle[i].getCenterY() - mainCircle.getCenterY(), smallCircle[i].getCenterX() - mainCircle.getCenterX());
      //checks to adjust to keep the angle inside the circle
      if (stheta > 0 && Math.cos(stheta) > 0){ 
    	  angleText[i].setX(smallCircle[i].getCenterX() - rade );
          angleText[i].setY(smallCircle[i].getCenterY() - rade );
          angleText[i].setText(String.format("%.2f", Math.toDegrees(angle[i])));
      }
      else if (stheta < 0 && Math.cos(stheta) < 0){
    	  angleText[i].setX(smallCircle[i].getCenterX() + rade );
          angleText[i].setY(smallCircle[i].getCenterY() + rade );
          angleText[i].setText(String.format("%.2f", Math.toDegrees(angle[i])));
      }
      else if (stheta > 0 && Math.cos(stheta) < 0){
    	  angleText[i].setX(smallCircle[i].getCenterX() + rade );
          angleText[i].setY(smallCircle[i].getCenterY() - rade );
          angleText[i].setText(String.format("%.2f", Math.toDegrees(angle[i])));
      }
      else {
    	  angleText[i].setX(smallCircle[i].getCenterX() - (2 *rade));
          angleText[i].setY(smallCircle[i].getCenterY() + (2* rade));
          angleText[i].setText(String.format("%.2f", Math.toDegrees(angle[i])));
    }
   } 
  }
  
  //launches application
  public static void main(String[] args) {
    launch(args);
  }
}
