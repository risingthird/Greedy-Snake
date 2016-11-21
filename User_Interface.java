
public class User_Interface {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Snake_Body snake= new Snake_Body();
			Snake_Control control=new Snake_Control(snake);
			Snake_Observer observe=new Snake_Observer(control,snake);
			snake.addObserver(observe);
			(new Thread(snake)).start();
	}

}
