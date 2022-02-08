package com.apstudios.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;


public class Menu {

	public String[] options = {"novo jogo","sair"};
	
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	
	public boolean up,down,enter;
	
	public boolean pause = false; void tick() {
		if(up) {
			up = false;
			currentOption--;
			if(currentOption < 0)
				currentOption = maxOption;
		}
		if(down) {
			down = false;
			currentOption++;
			if(currentOption > maxOption)
				currentOption = 0;
		}
		if(enter) {
			enter = false;
			if(options[currentOption] == "novo jogo" ) {
				Game.gameState = "SwitchMode";
				pause = false;
			}else if(options[currentOption] == "sair") {
				System.exit(1);
			}
		}
	}

	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,10,0,170));
		g2.fillRect(0, 0,Toolkit.getDefaultToolkit().getScreenSize().width
				,Toolkit.getDefaultToolkit().getScreenSize().height);
		
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.BOLD,100));
		
		g.drawString("2020", (Game.WIDTH*Game.SCALE) / 2 + 180,
				(Game.HEIGHT*Game.SCALE) / 2 - 30);
		
		//Opcoes de menu
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.BOLD,24));
		if(pause == false)
			g.drawString("Novo jogo", (Game.WIDTH*Game.SCALE) / 2 + 220, 400);
		else
			g.drawString("Resumir", (Game.WIDTH*Game.SCALE) / 2 + 220, 400);
		g.drawString("Sair", (Game.WIDTH*Game.SCALE) / 2 + 240, 440);
		
		if(options[currentOption] == "novo jogo") {
			g.drawString(">", (Game.WIDTH*Game.SCALE) / 2 + 180, 400);
		
		}else if(options[currentOption] == "sair") {
			g.drawString(">", (Game.WIDTH*Game.SCALE) / 2 + 200, 440);
		}
	}
	
}
