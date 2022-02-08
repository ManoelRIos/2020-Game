package com.apstudios.entities;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.apstudios.main.Game;
import com.apstudios.main.Sound;
import com.apstudios.world.Camera;
import com.apstudios.world.World;

public class Enemy extends Entity{
	
	public static double speed = 0.5;
	
	private int maskx = 8,masky = 8,maskw = 10,maskh = 10;
	
	private int frames = 0,maxFrames = 5,index = 0,maxIndex = 3;
	
	private BufferedImage[] sprites;
	
	private int life = 90;
	
	private boolean isDamaged = false;
	private int damageFrames = 10,damageCurrent = 0;

	public Enemy(int x, int y, int width, int height, BufferedImage[] sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[4];
		this.sprites[0] = sprite[0];
		this.sprites[1] = sprite[1];
		this.sprites[2] = sprite[2];
		this.sprites[3] = sprite[3];
	
	}

	public void tick(){
		
		if(isColiddingWithPlayer() == false && isColiddingWithPlayer2() == false){
		if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY()) &&
				!isColidding((int)(x+speed), this.getY())){
			x+=speed;
		}
		else if((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY()) &&
				!isColidding((int)(x-speed), this.getY())) {
			x-=speed;
		}
		
		if((int)y < Game.player.getY() && World.isFree(this.getX(), (int)(y+speed)) &&
				!isColidding(this.getX(), (int)(y+speed))){
			y+=speed;
		}
		else if((int)y > Game.player.getY() && World.isFree(this.getX(), (int)(y-speed)) &&
				!isColidding(this.getX(), (int)(y-speed))) {
			y-=speed;
		}
		}else{
			//Estamos colidindo
			if(Game.rand.nextInt(100) < 10){
				Sound.hurtEffect.play();
				Game.player.life-=3;
				Game.player.isDamaged = true;
			
			}
			if(Game.gameState == "DUO") {
				if(Game.rand.nextInt(100) < 10) {
					Sound.hurtEffect.play();
					Game.player2.life2 -= 3;
					Game.player2.isDamaged = true;
				}
			}
	}
			
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
			}
			
			collidingBullet();
			collidingBullet2();
			
			if(life <= 0) {
				destroySelf();
				return;
			}
			
			if(isDamaged) {
				this.damageCurrent++;
				if(this.damageCurrent == this.damageFrames) {
					this.damageCurrent = 0;
					this.isDamaged = false;
				}
			}
		
		
	
	}
	
	public void destroySelf() {
		if(Game.rand.nextInt(100) < 10) {
		Sound.zombieSom.play();
		}
		Game.enemies.remove(this);
		Game.entities.remove(this);
	}
	
	public void collidingBullet() {
		for(int i = 0; i < Game.bullets.size(); i++) {
			Entity e = Game.bullets.get(i);
				if(Entity.isColidding(this,e)) {
					isDamaged = true;
					life-=30;
					Game.bullets.remove(i);
					return;
				}
				}
	}
				public void collidingBullet2() {
					for(int i = 0; i < Game.bullets2.size(); i++) {
						Entity e = Game.bullets2.get(i);
							if(Entity.isColidding(this,e)) {
								isDamaged = true;
								life-=30;
								Game.bullets2.remove(i);
								return;
							}
		}
	
		
		
	}
	
	public boolean isColiddingWithPlayer(){
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx,this.getY() + masky,maskw,maskh);
		Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),16,16);
		
		return enemyCurrent.intersects(player);
	}
	
	public boolean isColiddingWithPlayer2(){
		if(Game.gameState == "DUO") {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx,this.getY() + masky,maskw,maskh);
		Rectangle player2 = new Rectangle(Game.player2.getX(),Game.player2.getY(),16,16);

		return enemyCurrent.intersects(player2);	
		}
		return false;
		
		}
	
	public boolean isColidding(int xnext,int ynext){
		Rectangle enemyCurrent = new Rectangle(xnext + maskx,ynext + masky,maskw,maskh);
		for(int i =0; i < Game.enemies.size(); i++){
			Enemy e = Game.enemies.get(i);
			if(e == this)
				continue;
			Rectangle targetEnemy = new Rectangle(e.getX()+ maskx,e.getY()+ masky,maskw,maskh);
			if(enemyCurrent.intersects(targetEnemy)){
				return true;
			}
		}
		
		return false;
	}
	
	public void render(Graphics g) {
		if(Game.gameState != "XIZUM") {
		if(!isDamaged)
			g.drawImage(sprites[index], this.getX() - Camera.x,this.getY() - Camera.y,null);
		else
			g.drawImage(Entity.ENEMY_FEEDBACK, this.getX() - Camera.x,this.getY() - Camera.y,null);
	}
	}
	
}
