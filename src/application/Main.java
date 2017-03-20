package application;

import application.hud.*;
import application.physics.*;
import javafx.application.Application;

import java.awt.Event;
import java.awt.Frame;
import java.awt.Window;
import java.util.Random;



import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.input.KeyCode;
import javafx.scene.effect.DropShadow;




public class Main extends Application {
	//Setting the MENU
	
	private MenuBox meniu;
    private static String menuTitle = "4PONG Menu";
    
    
    
    
	//User Actions can be NONE, LEFT MOVE or RIGHT MOVE.
	private enum P_Actions{
		NONE,LEFT,RIGHT,PAUSE
	}
	
	//Private declarations of the WIDTH and HEIGHT of the Main Frame
	private static final int WIN_H = 600;
	private static final int WIN_W = 450;
	
	//Private declarations of the BALL RADIUS and Player's BAT rectangle
	private static final int DiametruMinge = 10;
	private static final int Paleta_L = 100;
	private static final int Paleta_I = 20;
	
	//Speed variables.
	private static double normal;
	private static double average;
	private static double medium;
	private static double fast;
	private static double extreme;
	private static Duration PAUSE;
	
	
	
	
	private static final Image table = new Image("res/PongTable_v1.jpg");
	private static final Image P1 = new Image("res/PongBat_P1_v1.png");
	private static final Image P2 = new Image("res/PongBat_P2_v1.png");
	private static final Image P1win = new Image("res/P1WIN.png");
	private static final Image P2win = new Image("res/P2WIN.png");
	private static final Image imagine_meniu = new Image("res/MenuBG.jpg");
	private static final Font font = new Font("res/neuropol.ttf",20);

	private static ImageView iv = new ImageView(); //pongtable
	private static ImageView Bat1 = new ImageView(); //batp1
	private static ImageView Bat2 = new ImageView(); //batp2
	private static ImageView P1_WINS = new ImageView(); //TEXT WIN p1
	private static ImageView P2_WINS = new ImageView(); //TEXT WIN p1
	
	//Creation of the shapes
	private Circle minge = new Circle(DiametruMinge);	
	private Rectangle paleta = new Rectangle(Paleta_L,Paleta_I);
	private Rectangle paleta2 = new Rectangle(Paleta_L,Paleta_I);
	private Text P1_score = new Text();
	private Text P2_score = new Text();
	private Text WIN = new Text();
	
	//Var's for holding Score Text
	private int aScore = Huds.actualScore(-1);
	private int bScore = Huds.actualScore(-1);
	
	//Hosting the Menu's alerts
	private static Alert reguli = new Alert(AlertType.INFORMATION);
	private static Alert dev = new Alert(AlertType.INFORMATION);
	private static Alert tbc = new Alert(AlertType.INFORMATION);
	
	
	
	
	
	//Where the ball's going to
	private boolean mingeSus = false, mingeStanga = false;
	private static boolean izstarted = false;
	
	//For the entry point, our player isn't pressing any keys.
	private P_Actions actiune = P_Actions.NONE;
	
	
	
	
	//Effects
	MotionBlur motionBlur = new MotionBlur();
	
	//Timeline - Ball animations
	private static Timeline timeline = new Timeline();
	private static boolean merge_mingea = false;
	
	
	
	private Stage win;
	private Parent createContent(){
		Pane acasa = new Pane();
		acasa.setPrefSize(WIN_W, WIN_H);
		
		//Set the Menu
	
            ImageView menubgimg = new ImageView();
            menubgimg.setImage(imagine_meniu);
            menubgimg.setFitWidth(450);
            menubgimg.setFitHeight(600);
            menubgimg.setVisible(true);
            
            
            
            // Menu Alerts
            reguli.setTitle("REGULI JOC");
            reguli.setHeaderText(null);
            reguli.setContentText("1. Cine ajunge la un scor de 15 puncte castiga,\n"
            		    		+ "2. Odata la 4 puncte viteza creste,\n"	
            		            + "3. POWER-UPs ( TBC )");
            dev.setTitle("CREDITs");
            dev.setHeaderText(null);
            dev.setContentText("Game developed by Bic \"WIKIN\" Sergiu,\n"
            		    		+ "CONTACT: wikin_n@yahoo.com \n"	
            		            + "(C) 2017 - All rights reserved.");
            tbc.setTitle("TBC");
            tbc.setHeaderText(null);
            tbc.setContentText("To Be Continued...");
            
        	
            //Menu Items
            MenuItem Quit = new MenuItem("QUIT");
            Quit.setOnMouseClicked(event -> System.exit(0));
            
            MenuItem Start = new MenuItem("Start Game");
            Start.setOnMouseClicked(event -> {
            	menubgimg.setVisible(false);
            	izstarted = true;
				meniu.hide();
				
				
				
            });
        
            
            MenuItem Rules = new MenuItem("Rules");
            Rules.setOnMouseClicked(event -> reguli.showAndWait());
            
            MenuItem Credits = new MenuItem("Credits");
            Credits.setOnMouseClicked(event -> dev.showAndWait());
            
            MenuItem MP = new MenuItem("MultiPlayer");
            MP.setOnMouseClicked(event -> tbc.showAndWait());
            
            MenuItem Options = new MenuItem("Options");
            Options.setOnMouseClicked(event -> tbc.showAndWait());
            
            
            
            
            
            
            
            
            //Menu itself
            meniu = new MenuBox(menuTitle,
            		Start,
                    Rules,
                    Options,
                    MP,
                    Credits,
                    Quit);
                    
           
            
            

            
		
		
		
		//Set the ball's color
		minge.setFill(Color.WHITESMOKE);
		minge.setSmooth(true);
		//Set the ball's effect
		motionBlur.setAngle(minge.getLayoutY());
		motionBlur.setRadius(DiametruMinge/2);
		minge.setEffect(motionBlur);
		
		
		
		//P1 starting position
		paleta.setLayoutX(WIN_W / 2);
		paleta.setLayoutY(WIN_H - Paleta_I);
		
		//P2 starting position
		paleta2.setLayoutX(0+(WIN_W/2)-(Paleta_L/2));
		paleta2.setLayoutY(0+(WIN_H - WIN_H));
		
		
		//Set the transparency of the BATs rectangles
		paleta2.setFill(Color.RED);
		paleta2.setOpacity(0);
		paleta.setFill(Color.DARKSEAGREEN);
		paleta.setOpacity(0);
		paleta2.setRotate(180);
		
		//Assign ImageView to objects
	    Bat1.setImage(P1);
	    Bat1.setEffect(new Glow(1.2));
	    Bat1.setPreserveRatio(true);
	    
	    Bat2.setImage(P2);
	    Bat2.setEffect(new Glow(1.2));
	    Bat2.setPreserveRatio(true);
	    
	    
	    //BAT's positions.
	    Bat1.setLayoutX(paleta.getLayoutX());
	    Bat1.setLayoutY(paleta.getLayoutY());
	    
	    
	    Bat2.setLayoutY(paleta2.getLayoutY());
	    Bat2.setLayoutX(paleta2.getLayoutX());
		
		
		//P1 Score Text
		P1_score.setFill(Color.GHOSTWHITE);
		P1_score.setEffect(new Glow(0.8));
		P1_score.setLayoutX(WIN_W-Paleta_I);
		P1_score.setLayoutY(WIN_H/2 + Paleta_L/2);
		
		//P2 Score Text
		P2_score.setFill(Color.GHOSTWHITE);
		P2_score.setEffect(new Glow(0.8));
		P2_score.setLayoutX(WIN_W-Paleta_I);
		P2_score.setLayoutY(WIN_H/2 - Paleta_L/2);
		
		
		//WIN TEXT
		WIN.setFill(Color.BLACK);
		WIN.setLayoutX(WIN_W/2);
		WIN.setLayoutY(WIN_H/2);
		WIN.setScaleX(2);
		WIN.setScaleY(2);
		
		//Loading speed variables.
		normal = SPO.getNormal();
		average = SPO.getAverage();
		medium = SPO.getMedium();
		fast = SPO.getFast();
		extreme = SPO.getExtreme();
		
		
		
		
		
		
		//Update every 16miliseconds
		KeyFrame frame = new KeyFrame(Duration.seconds(0.016),event -> {
			//If there's any ball errors return
			
			//PAUSE WTF
			if(!izstarted)
				return;
				else if(!merge_mingea)
				return;
			
			
			
			
				
				
			
			//AI
			SPO.setAIonline(true);
			SPO.setDummyAI(true);
			if(SPO.isAIonline() == true){
			
				if(mingeStanga!=false){
					paleta2.setLayoutX(paleta2.getLayoutX()-4);
					Bat2.setLayoutX(paleta2.getLayoutX());
				}else{
					paleta2.setLayoutX(paleta2.getLayoutX()+4);
					Bat2.setLayoutX(paleta2.getLayoutX());
				}
			if(SPO.isDummyAI()==true){
				paleta2.setLayoutX(paleta2.getLayoutX()+Math.random());
				Bat2.setLayoutX(paleta2.getLayoutX());
			}
		
			
				
				if(paleta2.getLayoutX() - 5 >= 0)
					paleta2.setLayoutX(paleta2.getLayoutX() - 5);
				
				Bat2.setLayoutX(paleta2.getLayoutX());
				
				
				if(paleta2.getLayoutX() + Paleta_L + 5 <= WIN_W)
					paleta2.setLayoutX(paleta2.getLayoutX() + 5);
				
				Bat2.setLayoutX(paleta2.getLayoutX());
			}
			//Check user actions
			switch(actiune){
			case LEFT:
				if(paleta.getLayoutX() - 5 >= 0)
					paleta.setLayoutX(paleta.getLayoutX() - 5);
				    Bat1.setLayoutX(paleta.getLayoutX());
				
				/*if(paleta2.getLayoutX() - 5 >= 0)
					paleta2.setLayoutX(paleta2.getLayoutX() - 5);*/
				break;
			case RIGHT:
				if(paleta.getLayoutX() + Paleta_L + 5 <= WIN_W)
					paleta.setLayoutX(paleta.getLayoutX() + 5);
				   Bat1.setLayoutX(paleta.getLayoutX());
				
				/*if(paleta2.getLayoutX() + Paleta_L + 5 <= WIN_W)
					paleta2.setLayoutX(paleta2.getLayoutX() + 5);*/
				break;
			case NONE:
				break;
				
				
			
			}
			
			//Check where the ball is going
			//minge.setLayoutX(minge.getLayoutX() + (mingeStanga ? -5 : 5));
			//minge.setLayoutY(minge.getLayoutY() + (mingeSus ? -5 : 5));
			//minge.setLayoutX(minge.getLayoutX() + (mingeSus ? -5 : 5));
			minge.setLayoutY(minge.getLayoutY() + (mingeSus ? -5 : 5));
			minge.setLayoutX(minge.getLayoutX() + (mingeStanga ? -5 : 5));
			
			
			
			
			
			
			//Check the ball and the bat of the Player Actions
			if(minge.getLayoutX() - DiametruMinge == 0){
				mingeStanga = false;
			}else if(minge.getLayoutX() + DiametruMinge == WIN_W){
				mingeStanga = true;
			}
			checkTop();
			//TOP event
			if(minge.getLayoutY() + DiametruMinge == 0){
				//TOP COLLISION BOOLEAN
				mingeSus=true;
				P1_score.setText(""+(aScore++));
				checkwin();
				
			}
			// TOP BAT COLLISION (solved)
			
			
			
			
			
			// ---Resolving the Top collision -- 
			else if(minge.getLayoutY() + DiametruMinge == WIN_H - Paleta_I
					&& minge.getLayoutX() + DiametruMinge >= paleta.getLayoutX()
					&& minge.getLayoutX() - DiametruMinge <= paleta.getLayoutX() + Paleta_L)
				mingeSus = true;
			
			//BOTTOM COLLIDE
			if(minge.getLayoutY() + DiametruMinge == WIN_H){
				P2_score.setText(""+(bScore++));
				checkwin();
				
			}
			
			
			//P1 or P2 WIN
		
			
		});
		timeline.getKeyFrames().add(frame);
		timeline.setCycleCount(Timeline.INDEFINITE);
	
		iv.setImage(table);
		iv.setFitHeight(600);
		iv.setFitWidth(450);
		iv.setPreserveRatio(true);
		
		
	     
		
		
		acasa.getChildren().addAll(iv,Bat1,Bat2,minge,paleta2,paleta,
								   P1_score,P2_score,P1_WINS,P2_WINS,
								   menubgimg,meniu);
		
		return acasa;
		
	}
	
	private Effect Effect(Glow glow) {
		// TODO Auto-generated method stub
		return null;
	}

	private void restartJoc(){
		stopJoc();
		startJoc();
	  
	}
	private void checkwin(){
	    switch(aScore){
	    case 4:
	    	timeline.setRate(average);
	    	restartJoc();
	    	break;
	    case 8:
			timeline.setRate(medium);
	    	restartJoc();
		    break;
	    case 12:
			timeline.setRate(fast);
	    	restartJoc();
		    break;
	    case 14:
			timeline.setRate(extreme);
	    	restartJoc();
		    break;
	    case 15:
	    	P1_WINS.setLayoutX(WIN_W/2);
			P1_WINS.setLayoutY(WIN_H/2);
			P1_WINS.setImage(P1win);
			stopJoc();
			break;
	   
		default:
			switch(bScore)
			{
			case 4:
				timeline.setRate(average);
		    	restartJoc();
			    break;
			case 8:
				timeline.setRate(medium);
		    	restartJoc();
			    break;
			case 12:
				timeline.setRate(fast);
		    	restartJoc();
			    break;
			case 14:
				timeline.setRate(extreme);
		    	restartJoc();
			    break;
			case 15:
				P2_WINS.setLayoutX(WIN_W/2);
				P2_WINS.setLayoutY(WIN_H/2);
				P2_WINS.setImage(P2win);
				stopJoc();
				break;
			default:restartJoc();
			break;
			}
		}
	

		
		
		
	   
	
	}
	private static void stopJoc(){
		merge_mingea = false;
		
		timeline.stop();
	}
	private void startJoc(){
		Random random = new Random();
		mingeSus = random.nextBoolean();
		minge.setLayoutX((WIN_W-DiametruMinge) / 2);
		minge.setLayoutY((WIN_H-DiametruMinge) / 2);
		//minge.setLayoutY((WIN_H-DiametruMinge) / 2);
		P1_score.setText(""+(aScore));
		P2_score.setText(""+(bScore));
		
		
		
		timeline.play();
		merge_mingea = true;
	}
	private void checkTop (){
		if(minge.getLayoutY() + DiametruMinge == paleta2.getLayoutY() + Paleta_I*2
				&& minge.getLayoutX() - DiametruMinge <= paleta2.getLayoutX() + Paleta_L
				&& minge.getLayoutX() + DiametruMinge >= paleta2.getLayoutX())
			mingeSus=false;
	}
	
	
	@Override
	public void start(Stage primaryStage){
		win = primaryStage;
		Scene scene = new Scene(createContent());
		
		
		scene.setOnKeyPressed(event -> {
			switch(event.getCode()){
			case LEFT:
				actiune = P_Actions.LEFT;
				break;
			case RIGHT:
				actiune = P_Actions.RIGHT;
				break;
			case A:
				actiune = P_Actions.LEFT;
				break;
			case D:
				actiune = P_Actions.RIGHT;
				break;
			  
			
			
			
		}});
	
			    
		
		
		
		
		scene.setOnKeyReleased(event -> {
			switch(event.getCode()){
			case LEFT:
				actiune = P_Actions.NONE;
				break;
			case RIGHT:
				actiune = P_Actions.NONE;
				break;
			case A:
				actiune = P_Actions.NONE;
				break;
			case D:
				actiune = P_Actions.NONE;
				break;
	
			
				
			}
			scene.getAntiAliasing();
			
		});
		win.setTitle("wPong v1");
		win.setScene(scene);
		win.show();
		startJoc();
		
	}
	
	private static class MenuBox extends StackPane {
        public MenuBox(String title, MenuItem... items) {
            Rectangle bg = new Rectangle(300, 600);
            bg.setOpacity(0.2);

            DropShadow shadow = new DropShadow(7, 5, 0, Color.BLACK);
            shadow.setSpread(0.8);

            bg.setEffect(shadow);

            Text text = new Text(title + "   ");
            text.setFont(font);
            text.setFill(Color.WHITE);

            Line hSep = new Line();
            hSep.setEndX(250);
            hSep.setStroke(Color.DARKGREY);
            hSep.setOpacity(0.4);

            Line vSep = new Line();
            vSep.setStartX(300);
            vSep.setEndX(300);
            vSep.setEndY(600);
            vSep.setStroke(Color.DARKOLIVEGREEN);
            vSep.setOpacity(0.4);

            VBox vbox = new VBox();
            vbox.setAlignment(Pos.TOP_RIGHT);
            vbox.setPadding(new Insets(60, 0, 0, 0));
            vbox.getChildren().addAll(text, hSep);
            vbox.getChildren().addAll(items);

            setAlignment(Pos.TOP_RIGHT);
            getChildren().addAll(bg, vSep, vbox);
        }

        public void show() {
            setVisible(true);
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), this);
            tt.setToX(0);
            tt.play();
            
            izstarted = false;
            
        }

        public void hide() {
        	
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), this);
            tt.setToX(-300);
            tt.setOnFinished(event -> setVisible(false));
            tt.play();
            
            
        }
    }

	private static class MenuItem extends StackPane {
        public MenuItem(String name) {
            Rectangle bg = new Rectangle(20, 20);

            LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[] {
                    new Stop(0, Color.BLACK),
                    new Stop(0.2, Color.YELLOW)
            });

            bg.setFill(gradient);
            bg.setVisible(false);
            bg.setEffect(new DropShadow(20, 5, 5, Color.BLACK));

            Text text = new Text(name + "      ");
            text.setFill(Color.LIGHTGREY);
            text.setFont(Font.font(20));

            setAlignment(Pos.CENTER_RIGHT);
            getChildren().addAll(bg, text);

            setOnMouseEntered(event -> {
                bg.setVisible(true);
                text.setFill(Color.YELLOW);
            });

            setOnMouseExited(event -> {
                bg.setVisible(false);
                text.setFill(Color.LIGHTGREY);
            });

            setOnMousePressed(event -> {
                bg.setFill(Color.RED);
                text.setFill(Color.RED);
            });

            setOnMouseReleased(event -> {
                bg.setFill(gradient);
                text.setFill(Color.RED);
            });
        }
    }
	public static void main(String[] args) {
		launch(args);
	}
}
