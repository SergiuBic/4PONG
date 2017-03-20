package application.physics;

import javafx.event.Event;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

//SPEED , POWER & OBJECTS!
public class SPO {
	
	private static double normal = 1.0;
	private static double average = 1.2;
	private static double medium = 1.3;
	private static double fast =  1.4;
	private static double extreme = 1.7;
	
	//Creation of the shapes
	private static int speedX;
	private static int speedY;
	private static boolean power;
	private static boolean velocity;
	
	//Setting up the DUMMY A.I.
	private static boolean AIonline;
    private static boolean DummyAI;
	
	
	
	
	
	
	
	
	
	SPO(){
		
		
		
		
	}
	public static double getNormal() {
		return normal;
	}
	public static double getAverage() {
		return average;
	}
	public static double getMedium() {
		return medium;
	}
	public static double getFast() {
		return fast;
	}
	public static double getExtreme() {
		return extreme;
	}
	public static boolean isAIonline() {
		return AIonline;
	}
	public static void setAIonline(boolean aIonline) {
		AIonline = aIonline;
	}
	public static boolean isDummyAI() {
		return DummyAI;
	}
	public static void setDummyAI(boolean dummyAI) {
		DummyAI = dummyAI;
	}

	
	
	
}
