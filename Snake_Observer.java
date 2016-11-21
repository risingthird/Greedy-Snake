import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.lang.Iterable;
public class Snake_Observer implements Observer {

	Snake_Control control=null;
	Snake_Body body=null;
	JFrame MainFrame;
	JLabel MyLabel;
	JPanel MyPanel;
	Container MyContainer;
	
	public final static int NodeWidth=10;
	public final static int NodeHeight=10;
	public final static int FrameX=30*10;
	public final static int FrameY=20*10+50;
	
	public final static Color SnakeHEAD= Color.black;
	public final static Color COLOR_FOOD=Color.RED;

	
	
	public Snake_Observer(Snake_Control control, Snake_Body body){
		this.body=body;
		this.control=control;
		
		MainFrame= new JFrame("贪吃蛤");
		MainFrame.setSize(FrameX, FrameY);
		MyContainer=MainFrame.getContentPane();
		MyPanel=new JPanel();
		
		MyLabel= new JLabel("Score: ");
		MyContainer.add(MyLabel, BorderLayout.NORTH);
		MyPanel.setSize(300, 200);
		MyPanel.addKeyListener(control);
		MyContainer.add(MyPanel);
		
		addButton(MyContainer, "开始", new ActionListener(){
			public void actionPerformed(ActionEvent event)
				{body.reset();}
		});
		
		/*addButton(MyContainer, "暂停", new ActionListener(){
			public void actionPerformed(ActionEvent event){
				body.setPause();
			}
		});*/
		
		MainFrame.addKeyListener(control);
		MainFrame.pack();
		MainFrame.setVisible(true);
		MainFrame.setResizable(true);
		MainFrame.setDefaultCloseOperation(3);
	}
	
	
	
	public void addButton (Container c,String title, ActionListener AL){
		JButton button= new JButton(title); 
		c.add(button,BorderLayout.SOUTH);
		button.addActionListener(AL);
	}
	
	public void drawNode(Graphics g, Node n){
		g.fillOval(n.x*NodeWidth,n.y*NodeHeight, NodeWidth, NodeHeight);
	}
	
	public void updateScore(){
		String s="Score: "+ body.score;
		MyLabel.setText(s);
	}

	public Color randomColor(){
		Color BodyColor;
		{
			BodyColor=new Color((int)Math.random()*255,(int)Math.random()*255,(int)Math.random()*255);
		}while (BodyColor== SnakeHEAD || BodyColor == COLOR_FOOD || BodyColor!=Color.WHITE);
		return BodyColor;
	}

	void repaint(){
		Graphics g= MyPanel.getGraphics();
		MyPanel.setBackground(Color.WHITE);
		
		//g.setColor(SnakeHEAD);
		LinkedList<Node> a= body.NodeArray;
		Iterator iter= a.iterator();
		//drawNode(g,(Node)iter.next());
		while(iter.hasNext()){
			System.out.println("abc");
			g.setColor(randomColor());
			drawNode(g,(Node)iter.next());
		}
		
		g.setColor(COLOR_FOOD);
		drawNode(g,body.food);
		
		updateScore();
	}
	
	
	
	
	@Override
	public void update(Observable o, Object arg) {
		repaint();	
	}
	
}

