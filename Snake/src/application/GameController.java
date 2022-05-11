package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;




public class GameController implements Initializable {
	@FXML
	Canvas canvas;
	
	GraphicsContext g;
	SnakeController controller;
	AnimationTimer timer;
	Dir input;
	boolean gotInput;
	double fps;
	double prev;
	
	
	@FXML
	private void handleUserInput(KeyEvent e) {
		switch(e.getCode()) {
		case LEFT: case RIGHT: case UP: case DOWN:
			if(!gotInput) {
				gotInput = true;
				input = Dir.valueOf(e.getCode().toString());
			}
		default:
		}
	}
	
	public void gameOver() {
		g.setFont(Font.font(20));
		g.strokeText("GAME OVER", canvas.getWidth()/2, 
				canvas.getWidth()/2);		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Cell.cellSize = 10;
		g = canvas.getGraphicsContext2D();
		controller = new SnakeController(g,
				(int)canvas.getWidth()/Cell.cellSize, 
				(int)canvas.getHeight()/Cell.cellSize);
		fps = 20;
		prev = 0;
		input = Dir.DOWN;
		gotInput = false;
		
		
		timer = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				if(now - prev > 1000000000L/fps) {
					prev = now;
					controller.move(input);
					controller.drawFrame();
					gotInput = false;
					if(controller.gameOver) {
						timer.stop();
						gameOver();
					}
				}
					
			}
		};
		
		timer.start();
	}
	
	
}
