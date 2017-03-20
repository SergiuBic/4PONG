package application.hud;

import application.*;
import javafx.application.Application;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
public class Huds {

	public static int actualScore(int s) {
		return Hud(s);
	}
	private static int Hud(int score){
		boolean changescore = false;
		if(changescore = true)
		score+=1;
		
		return score;
	}
	
	
	
	
	
}
