package com.apstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.apstudios.main.Game;
import com.apstudios.world.Camera;
import com.apstudios.world.World;

public class Player2 extends Entity{
	public boolean right,up,left,down;
	public static int right_dir = 0,left_dir = 1, down_dir = 3, up_dir = 2;
	public static int dir = right_dir;
	public double speed = 1.1;
	
	private int frames = 0,maxFrames = 5,index = 0,maxIndex = 2;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	
	private BufferedImage playerDamage2;
	

	
	public int ammo = 0;
	
	public boolean isDamaged = false;
	private int damageFrames = 0;
	
	public static boolean shootP2 = false;
	
	public double life2 = 100;
	public double maxlife2=100;
	public int mx,my;

	public Player2(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[3];
		leftPlayer = new BufferedImage[3];
		upPlayer = new BufferedImage[3];
		downPlayer = new BufferedImage[3];
		
		playerDamage2 = Game.spritesheet.getSprite(0, 16,16,16);
		for(int i =0; i < 3; i++){
			rightPlayer[i] = Game.spritesheet.getSprite(80 + (i*16), 112, 16, 16);
		}
		for(int i =0; i < 3; i++){
			leftPlayer[i] = Game.spritesheet.getSprite( 80+ (i*16), 128, 16, 16);
		}
		for(int i =0; i < 3; i++){
			upPlayer[i] = Game.spritesheet.getSprite(80 + (i*16), 96, 16, 16);
		}
		for(int i =0; i < 3; i++){
			downPlayer[i] = Game.spritesheet.getSprite(80 + (i*16),80 , 16, 16);
		}
		
		
	}
	
	public void tick(){
		if(Game.gameState != "SINGLE") {
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
		if(shootP2) {
			shootP2= false;
				ammo--;
			//Criar bala e atirar! pelo "x"
			int dy = 0;
			int dx = 0;
			int px = 0;
			int py = 7;
			if(dir == right_dir) {
				px = 18;
				dx = 1;
			} if (dir == left_dir){
				px = -8;
				dx = -1;
			}
			 if(dir == up_dir) {
				py = -4;
				dy = -1;
			}if(dir == down_dir) {
				py = 0;
				dy = 1;
			}
			BulletShoot bullet2 = new BulletShoot(this.getX()+px,this.getY()+py,3,3,null,dx,dy);
			Game.bullets2.add(bullet2);
			}
		}
		if(life2<=0) {
			//Game over!
			life2 = 0;
			Game.gameState = "GAME_OVER";
		}	
	
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
					life2+=1;
					Game.entities.remove(atual);
					if(life2> 100) {
						life2 = 100;
					}
				}
			}
		}
	}

	
	public void render(Graphics g) {
		if(Game.gameState != "SINGLE") {
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
			g.drawImage(playerDamage2, this.getX()-Camera.x, this.getY() - Camera.y,null);
			
			
		}
	}
}
	
}

