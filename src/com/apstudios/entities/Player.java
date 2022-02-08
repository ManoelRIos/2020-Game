package com.apstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.apstudios.main.Game;
import com.apstudios.world.Camera;
import com.apstudios.world.World;

public class Player extends Entity{
	
	public boolean right,up,left,down;
	public static int right_dir = 0,left_dir = 1, down_dir = 3, up_dir = 2;
	public static int dir = right_dir;
	public double speed = 1.1;
	
	private int frames = 0,maxFrames = 5,index = 0,maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	
	private BufferedImage playerDamage;
	
	
	public int ammo = 0;
	
	public boolean isDamaged = false;
	private int damageFrames = 0;
	
	public boolean shootP1 = false,mouseShoot = false;
	
	public double life = 100,maxlife=100;
	public static int lixo =0;
	public int mx,my;
	

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		
		playerDamage = Game.spritesheet.getSprite(0, 16,16,16);
		for(int i =0; i < 4; i++){
			rightPlayer[i] = Game.spritesheet.getSprite(80 + (i*16), 0, 16, 16);
		}
		for(int i =0; i < 4; i++){
			leftPlayer[i] = Game.spritesheet.getSprite(80 + (i*16), 16, 16, 16);
		}
		for(int i =0; i < 4; i++){
			upPlayer[i] = Game.spritesheet.getSprite(80 + (i*16), 64, 16, 16);
		}
		for(int i =0; i < 4; i++){
			downPlayer[i] = Game.spritesheet.getSprite(80 + (i*16), 48, 16, 16);
		}
		
		
	}
	
	public void tick(){
		moved = false;
		if(right && World.isFree((int)(x+speed),this.getY())) {
			moved = true;
			dir = right_dir;
			x+=speed;
		}
		 if(left && World.isFree((int)(x-speed),this.getY())) {
			moved = true;
			dir = left_dir;
			x-=speed;
		}
		 if(up && World.isFree(this.getX(),(int)(y-speed))){
			moved = true;
			dir = up_dir;
			y-=speed;
		}
		 if(down && World.isFree(this.getX(),(int)(y+speed))){
			moved = true;
			dir = down_dir;
			y+=speed;
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
			}
		}
		
		checkCollisionLifePack();
		checkCollisionAmmo();
		
		if(isDamaged) {
			this.damageFrames++;
			if(this.damageFrames == 8) {
				this.damageFrames = 0;
				isDamaged = false;
			}
		}
		//lembrar de verificar se balas atravessam tiles.
		if(shootP1) {
			shootP1 = false;
				ammo--;
			//Criar bala e atirar! pelo "x"
			int dy = 0;
			int dx = 0;
			int px = 0;
			int py = 6;
			if(dir == right_dir ) {
				px = 18;
				dx = 1;
			} if (dir == left_dir ){
				px = -8;
				dx = -1;
			}
			if(dir == up_dir ) {
				py = -4;
				dy = -1;
			}if(dir == down_dir ){
				py = 0;
				dy = 1;
			}
			 
			BulletShoot bullet = new BulletShoot(this.getX()+px,this.getY()+py,3,3,null,dx,dy);
			Game.bullets.add(bullet);
			
			
			}
			
		
		if(life<=0) {
			//Game over!
			life = 0;
			Game.gameState = "GAME_OVER";
		}
		
		updateCamera();
	
	}
	
	public void updateCamera() {
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2),0,World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2),0,World.HEIGHT*16 - Game.HEIGHT);
	}
	
	public void checkCollisionAmmo() {
		for(int i = 0; i < Game.entities.size(); i++){
			Entity atual = Game.entities.get(i);
			if(atual instanceof Bullet) {
				if(Entity.isColidding(this, atual)) {
					ammo+=5;
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void checkCollisionLifePack(){
		for(int i = 0; i < Game.entities.size(); i++){
			Entity atual = Game.entities.get(i);
			if(atual instanceof Lifepack) {
				if(Entity.isColidding(this, atual)) {
					life+=1;
					lixo+=1;
					Game.entities.remove(atual);
					if(life > 100) {
						life = 100;
					}
				}
			}
		}
	}

	
	public void render(Graphics g) {
		
		if(!isDamaged) {
			if(dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX() - Camera.x,this.getY() - Camera.y, null);
			}else if(dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX() - Camera.x,this.getY() - Camera.y, null);
			
			}else if(dir == up_dir) {
				g.drawImage(upPlayer[index], this.getX() - Camera.x,this.getY() - Camera.y, null);
				
			}else if(dir == down_dir) {
				g.drawImage(downPlayer[index], this.getX() - Camera.x,this.getY() - Camera.y, null);
			}
			}else {
			g.drawImage(playerDamage, this.getX()-Camera.x, this.getY() - Camera.y,null);
			
			
		}
	}

}
