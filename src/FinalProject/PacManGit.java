package FinalProject;

import javafx.animation.Animation;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class PacManGit extends Sprite{

    private Control control;
    private double speed;
    MySounds mySounds;
    boolean timer;
    Animation animation;
    protected int movement;
    GraphicsContext graphicsContext;
    Maze pacmanOnMaze;

    public PacManGit(Pane layer, Image image, double x, double y, double dx, double dy, double speed, Control control, MySounds ms) {

        super(layer, image, x, y,  dx, dy);

        this.speed = speed;
        this.control = control;
        mySounds = ms;
        
        
    }



    public void setSpeed(double speed) {
    	this.speed = speed;
    }

    
    public void processInput() {

        if( control.isMoveUp()) {
        	movement = 1;
        	dx = 0;
            dy = -speed;
            
        } else if( control.isMoveDown()) {
        	movement = 2;
            dy = speed;
            dx = 0;
        }
        
        if( control.isMoveLeft()) {     
        	movement = 3;
            dx = -speed;
            dy = 0;
          
        } else if( control.isMoveRight()) {
        	movement = 4;
            dx = speed;
            dy = 0;
        } 


        
    }
    
    public boolean imageTimer() {
    	new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		                timer = true;
		            }
		        }, 
		        50 
		);
    	
    	return timer;
    }

    @Override
    public void move() {
        super.move();
        
        checkBounds();
    }

    private void checkBounds() {

        if( y < 0 ) {
            y = 0;
        } else if( (y+h) > Settings.SCENE_HEIGHT ) {
            y = Settings.SCENE_HEIGHT-h;
        }

        if( x < 0) {
            x = 0;
        } else if( (x+w) > Settings.SCENE_WIDTH ) {
            x = Settings.SCENE_WIDTH-w;
        }
    }

}