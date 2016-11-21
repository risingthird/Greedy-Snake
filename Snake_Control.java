import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Snake_Control implements KeyListener {
	Snake_Body body;
	Snake_Control(Snake_Body body){
		this.body=body;
	}
	@Override
	public void keyPressed(KeyEvent e){
		int keycode=e.getKeyCode();
		if (body.running){
			switch(keycode){
				case KeyEvent.VK_UP:
					body.ChangeDirection(body.UP);
					break;
				case KeyEvent.VK_LEFT:
					body.ChangeDirection(body.LEFT);
					break;
				case KeyEvent.VK_DOWN:
					body.ChangeDirection(body.DOWN);
					break;
				case KeyEvent.VK_RIGHT:
					body.ChangeDirection(body.RIGHT);
					break;
				case KeyEvent.VK_Q:
					body.Accelerate();
					break;
				case KeyEvent.VK_E:
					body.SlowDown();
					break;
				case KeyEvent.VK_P:
					body.setPause();
					break;
				default:
			}
		}
		
		// press enter or s to start
		if(keycode==KeyEvent.VK_ENTER || keycode==KeyEvent.VK_S){
			body.reset();
		}
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
}
