package com.apstudios.entities;



import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.apstudios.main.Game;
import com.apstudios.world.Camera;

public class BulletShoot extends Entity{

	private double dx;
	private double dy;
	private double spd = 4;
	
	private int life = 30,curLife = 0;
	
	
	public BulletShoot(int x, int y, int width, int height, BufferedImage sprite,double dx,double dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
	}
	
	public void tick() {
		x+=dx*spd;
		y+=dy*spd;
		curLife++;
		if(curLife == life ) {
			Game.bullets.remove(this);
			Game.bullets2.remove(this);
			return;
			
		}
		
		
	}

	
	
	public void render(Graphics g){
		if(Player.dir == Player.right_dir) {
			//Render bala para direita
		g.drawImage(Entity.BULLET_SHOOT_RIGHT, this.getX() - Camera.x,this.getY() - Camera.y,null);
		}else  if(Player.dir == Player.left_dir) {
			g.drawImage(Entity.BULLET_SHOOT_LEFT, this.getX() - Camera.x,this.getY() - Camera.y,null);
		}else if(Player.dir == Player.down_dir) {
			g.drawImage(Entity.BULLET_SHOOT_DOWN, this.getX() - Camera.x,this.getY() - Camera.y,null);
		}else if (Player.dir == Player.up_dir){
			g.drawImage(Entity.BULLET_SHOOT_UP, this.getX() - Camera.x,this.getY() - Camera.y,null);

		}else {
			if(Player2.dir == Player2.right_dir) {
				//Render bala para direita
			g.drawImage(Entity.BULLET_SHOOT_RIGHT, this.getX() - Camera.x,this.getY() - Camera.y,null);
			}else if(Player2.dir == Player2.left_dir) {
				g.drawImage(Entity.BULLET_SHOOT_LEFT, this.getX() - Camera.x,this.getY() - Camera.y,null);
			}else if(Player2.dir == Player2.down_dir) {
				g.drawImage(Entity.BULLET_SHOOT_DOWN, this.getX() - Camera.x,this.getY() - Camera.y,null);
			}else if (Player2.dir == Player2.up_dir){
				g.drawImage(Entity.BULLET_SHOOT_UP, this.getX() - Camera.x,this.getY() - Camera.y,null);

			}
		}
		
	}
}
