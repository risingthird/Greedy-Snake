import java.util.LinkedList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Observable;

class Node{
	int x;
	int y;
	
	public Node(int x, int y){
		this.x=x;
		this.y=y;
	}
}

public class Snake_Body extends Observable implements Runnable{
	int [][] MAP; // to show whether there's snake or food 
	LinkedList<Node> NodeArray= new LinkedList<Node>();  // create an empty Linked list to present snake body
	int direction=2;  // the direction of movement
	Node food;
	
	final static int LEFT=1;
	final static int UP=2;
	final static int RIGHT=3;
	final static int DOWN=4;
	
	final static int FOOD=1;
	final static int SNAKE=2;
	final static int EMPTY=3;
	
	final static boolean ALIVE=true;
	final static boolean DEAD=false;
	
	int timeInterval=200;  // the rate of screen update
	double speedChangeRate=0.75;  // the parameter to adjust speed
	boolean running = false; // status of snake
	boolean paused = false; // status of game
	boolean lifestatus=ALIVE;
	
	int InitialBody=6;
	int score = 0;
	int eaten = 0; // score = eaten*10 while eaten<=5; score= score+(eaten-5)*25;
	
	int MAP_X=30;
	int MAP_Y=20;
	int InitialX=0; // The initial location of the snake head
	int InitialY=0;
	
	public Snake_Body(){reset();}
	
	public void reset(){
		direction=Snake_Body.UP;
		score=0;
		eaten=0;
		InitialBody=4;
		paused=false;
		lifestatus=ALIVE;
		MAP=new int [MAP_X][MAP_Y];  //create MAP
	    for(int i=0;i<MAP_X;i++)
	    	for(int j=0;j<MAP_Y;j++)
	    		MAP[i][j]=EMPTY;
		/*
	    for (int i=0;i<MAP_X;i++)
			for (int j=0;j<MAP_Y;j++){
				if (i==0 || i==MAP_X-1)
					MAP[i][j]=true;
				else if(j==0 ||j==MAP_Y-1)
					MAP[i][j]=true;
			}
		*/
		while ((InitialX<3) || (InitialX>24) || (InitialY<2) || (InitialY>14)){
			InitialX= (int) (Math.random()*MAP_X);
			InitialY= (int) (Math.random()*MAP_Y);
		} // make sure the initial location is not that close to the wall
		
		// initialize the snake with 6 nodes
		for (int i=0;i<InitialBody;i++){
			NodeArray.addLast(new Node(InitialX+i,InitialY));
			MAP[InitialX+i][InitialY]=SNAKE;
		}
		food=createFood();
	}
	
	public Node createFood(){
		int foodX;
		int foodY;
		do{
			foodX=(int) (Math.random()*MAP_X);
			foodY=(int) (Math.random()*MAP_Y);
		}while (MAP[foodX][foodY]==SNAKE);
		MAP[foodX][foodY]=FOOD;
		return new Node(foodX,foodY);
		
	}

	public void ChangeDirection(int NewDirection){
		if (direction%2 != NewDirection%2)
			direction=NewDirection;
	}
	
	public boolean Move(){
		Node first=(Node) NodeArray.getFirst();
		int x= first.x;
		int y=first.y;
		switch(direction){
		case UP:
			y--;
			break;
		case LEFT:
			x--;
			break;
		case RIGHT:
			x++;
			break;
		case DOWN:
			y++;
			break;
		}
		if (x>0 && x<MAP_X && y>0 && y<MAP_Y){
			if (MAP[x][y]==FOOD){
				NodeArray.addFirst(food);
				MAP[x][y]=SNAKE;
				eaten++;
				score+=getScore();
				food=createFood();
				return true;
			}
			else if(MAP[x][y]==SNAKE)   // what if set running to pause
				{lifestatus=DEAD;
				 return false;}
			else{
				NodeArray.addFirst((Node)new Node(x,y));
				MAP[x][y]=SNAKE;
				Node n=(Node) NodeArray.removeLast();
				MAP[n.x][n.y]=EMPTY;    // remove the tail, and set to empty
				return true;
			}
		}
		else
			lifestatus=DEAD; // touch the wall
			return false;
	}

	public void Accelerate(){
		timeInterval=(int) (timeInterval*speedChangeRate);
	}
	
	public void SlowDown(){
		timeInterval=(int) (timeInterval/speedChangeRate);
	}
	
	// the rule to get score
	public int getScore(){
		if (eaten<=5)
			return 10/(timeInterval/200);
		else if(eaten<=10)
			return 25/(timeInterval/200);
		else if(eaten<=20)
			return 50/(timeInterval/200);
		else
			return 100/(timeInterval/200);
	}

	public void run(){
		running=true;
		while(running){
			try{
				Thread.sleep(timeInterval);
				} catch(Exception e){break;}
			if(!paused){
				if(Move()){
					setChanged();
					notifyObservers();
				}
				else{break;}    // !!!!!!!!!!!!!!!!!!!!!!! suppose to print "LOSE" on the screen, no frame programmed yet
			}
			}
		running=false;
		}
	
	public void setPause(){
		paused=(!paused);
	}
}




