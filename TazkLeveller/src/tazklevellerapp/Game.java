package tazklevellerapp;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

//main class where every thing is handled.

public class Game extends Canvas implements Runnable{
	
	
	private static final long serialVersionUID = 1550691097823471818L;
//It was Width 640 and Height 12 * 9
	public static final int WIDTH = 400, HEIGHT = WIDTH / 12 * 19 ;
	
	private Thread thread;
	private boolean running = false;
	
	
	public Game() {
		new Window(WIDTH, HEIGHT, "Task Leveler", this);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
	}
 
	public synchronized void stop() {
		try {
			
			thread.join();
			running = false;
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
	}
		
	public void run(){
		//Game Loop, Popular and difficult, Credit to 
		 long lastTime = System.nanoTime();
	        double amountOfTicks = 60.0;
	        double ns = 1000000000 / amountOfTicks;
	        double delta = 0;
	        long timer = System.currentTimeMillis();
	        int frames = 0;
	        while(running)
	        {
	                    long now = System.nanoTime();
	                    delta += (now - lastTime) / ns;
	                    lastTime = now;
	                    while(delta >=1)
	                            {
	                                tick();
	                                delta--;
	                            }
	                            if(running)
	                                render();
	                            frames++;
	                            
	                            if(System.currentTimeMillis() - timer > 1000)
	                            {
	                                timer += 1000;
	                                System.out.println("FPS: "+ frames);
	                                frames = 0;
	                            }
	        }
	                stop();
		
		
	}
	
	private void tick() {
		
	}
	
	private void render() {
		//add BufferStrategy to library as Import java.awt
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
			
		}
			
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.white);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.dispose();
		bs.show();
		
	}
	
	
	public static void main(String[] Arg) {
		
			new Game();

		
	}
	
	
}
