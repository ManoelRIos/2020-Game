package com.apstudios.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import com.apstudios.entities.BulletShoot;
import com.apstudios.entities.Enemy;
import com.apstudios.entities.Entity;
import com.apstudios.entities.Player;
import com.apstudios.entities.Player2;
import com.apstudios.graficos.BufferedImageLoader;
import com.apstudios.graficos.Spritesheet;
import com.apstudios.graficos.UI;
import com.apstudios.world.World;

public class Game extends Canvas implements Runnable,KeyListener{


	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 280;
	public static final int HEIGHT = 200;
	public static final int SCALE = 3;
	private int CUR_LEVEL = 1,MAX_LEVEL = 6;
	private BufferedImage image;
	private BufferedImage background;
	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static List<BulletShoot> bullets;
	public static List<BulletShoot> bullets2;
	public static Spritesheet spritesheet;
	public static World world;
	public static Player player;
	public static Player2 player2;
	public static Random rand;
	public UI ui;
	public static int ticks = 0;
	public static int frames = 0;
	public static String gameState = "MENU";
	private boolean showMessageGameOver = true;
	private int framesGameOver = 0;
	private boolean restartGame = false;
	
	public SwitchMode escolha;
	public Menu menu;
	
	public static int[] pixels;
	public static int[] lightMap;

	
	public Game(){
		Sound.musicBackground.loop();
		rand = new Random();
		addKeyListener(this);
		setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		initFrame();
		//Inicializando objetos.
		ui = new UI();
		lightMap = new int[WIDTH*HEIGHT];
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		pixels =((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		bullets = new ArrayList<BulletShoot>();
		bullets2 = new ArrayList<BulletShoot>();
		
		spritesheet = new Spritesheet("/spritesheet.png");
		player = new Player(0,0,16,16,spritesheet.getSprite(32, 0,16,16));
		player2 = new Player2(0,0,16,16,spritesheet.getSprite(32, 0,16,16));
		entities.add(player);
		entities.add(player2);
		world = new World("/level1.png");
		
		
		menu = new Menu();
		escolha = new SwitchMode();
	}
	
	public void initFrame(){
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			background = loader.loadImage("/menubg.png");
		
		}catch(IOException e) {
			e.printStackTrace();
		}
		frame = new JFrame("2020");
		frame.add(this);
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start(){
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop(){
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		Game game = new Game();
		game.start();
	}
	
	public void tick(){
		if(gameState == "SINGLE") {
		this.restartGame = false;	
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
		}
		
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).tick();
		}
		if(enemies.size() == 0) {
			//Avan�ar para o pr�ximo level!
			CUR_LEVEL++;
			Enemy.speed += 0.01;
			if(CUR_LEVEL > MAX_LEVEL){
				CUR_LEVEL = 1;
			}
			String newWorld = "level"+CUR_LEVEL+".png";
			World.restartGame(newWorld);
		}
		
		}else if(gameState == "DUO") {
			this.restartGame = false;	
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}
			
			for(int i = 0; i < bullets.size(); i++) {
				bullets.get(i).tick();
			}
			for(int i = 0; i < bullets2.size(); i++) {
				bullets2.get(i).tick();
			}
			
			if(enemies.size() == 0) {
				//Avan�ar para o pr�ximo level!
				CUR_LEVEL++;
				Enemy.speed += 0.01;
				if(CUR_LEVEL > MAX_LEVEL){
					CUR_LEVEL = 1;
				}
				String newWorld = "level"+CUR_LEVEL+".png";
				World.restartGame(newWorld);
			
			}
			
			
	}else if(gameState == "GAME_OVER") {
			this.framesGameOver++;
			if(this.framesGameOver == 30) {
				this.framesGameOver = 0;
				if(this.showMessageGameOver)
					this.showMessageGameOver = false;
					else
						this.showMessageGameOver = true;
			}
			
			if(restartGame) {
				this.restartGame = false;
				Game.gameState = "MENU";
				CUR_LEVEL = 1;
				String newWorld = "level"+CUR_LEVEL+".png";
				World.restartGame(newWorld);
			}
		}else if(gameState == "MENU") {
			
			menu.tick();
			
		}else if(gameState == "SwitchMode") {
			escolha.tick();
			
		}
	}
	
	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
	
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0,WIDTH,HEIGHT);
		/*Renderiza��o do jogo*/
		world.render(g);
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(g);
		}
		for(int i = 0; i < bullets2.size(); i++) {
			bullets2.get(i).render(g);
		}
		ui.render(g);
		
		/***/
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0,Toolkit.getDefaultToolkit().getScreenSize().width,
				Toolkit.getDefaultToolkit().getScreenSize().height,null);
		if(gameState == "GAME_OVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,200));
			g2.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, 
					Toolkit.getDefaultToolkit().getScreenSize().height);
			g.setFont(new Font("arial",Font.BOLD,50));
			g.setColor(Color.white);
			g.drawString("Game Over",WIDTH*SCALE - 190,HEIGHT*SCALE - 220);
			g.setFont(new Font("arial",Font.BOLD,40));
			if(showMessageGameOver)
				g.drawString(">Pressione Enter para reiniciar<",WIDTH*SCALE - 400,
						HEIGHT* SCALE - 180);
			
		}else if(gameState == "MENU") {
			g.drawImage(background, 0, -20, Toolkit.getDefaultToolkit().getScreenSize().width
					, Toolkit.getDefaultToolkit().getScreenSize().height + 20,null);
			menu.render(g);
			
		}else if(gameState == "SwitchMode") {
			g.drawImage(background, 0, -20, Toolkit.getDefaultToolkit().getScreenSize().width
					, Toolkit.getDefaultToolkit().getScreenSize().height + 20,null);
			escolha.render(g);
		}
		bs.show();
	}
	
	public void run() {
		requestFocus();
		//Game Looping
		long lasTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000/amountOfTicks;
		double delta = 0;
		
		double timer = System.currentTimeMillis();
		boolean shouldRender = true;
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lasTime) / ns;
			lasTime = now;
			if(delta >= 1) {
				tick();
				ticks++;	
				delta --;
			}
			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(shouldRender) {
			frames++;
			render();
			}
			if(System.currentTimeMillis() - timer >= 1000){
				timer+=1000;
				System.out.println("ticks: "+ ticks+ " frames:"+ frames);
				frames = 0;
				ticks = 0;
				
			}
		}
}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {	
			player.right = true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT ){
			player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP ){
			player.up = true;
			if(gameState == "MENU") {
				menu.up = true;
			}else if (gameState == "SwitchMode"){
				escolha.up = true;
				}
			
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.down = true;
		
			
			if(gameState == "MENU") {
				menu.down = true;
			}else if (gameState == "SwitchMode"){
				escolha.down = true;
				}
		}
	if(e.getKeyCode() == KeyEvent.VK_M){
			
			player.shootP1 = true;
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_T){
			
			Player2.shootP2= true;
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.restartGame = true;
			if(gameState == "MENU") {
				menu.enter = true;
			}else if (gameState == "SwitchMode"){
				escolha.enter = true;
				}
			}
		
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			gameState = "MENU";
			menu.pause = true;
		}
	
			if(e.getKeyCode() == KeyEvent.VK_D) {	
				player2.right = true;
			}else if(e.getKeyCode() == KeyEvent.VK_A ){
				player2.left = true;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_W ){
				player2.up = true;
				if(gameState == "MENU") {
					menu.up = true;
				}else if (gameState == "SwitchMode"){
					escolha.up = true;
					}
				
			}else if(e.getKeyCode() == KeyEvent.VK_S) {
				player2.down = true;
			}

		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			player.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			player.left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP ){
			player.up = false;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN ) {
			player.down = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_D){
			player2.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_A){
			player2.left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_W ){
			player2.up = false;
		}else if(e.getKeyCode() == KeyEvent.VK_S ) {
			player2.down = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
