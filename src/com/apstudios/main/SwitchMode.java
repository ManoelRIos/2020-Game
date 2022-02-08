package com.apstudios.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

public class SwitchMode{
	public String[] options = {"solo","duo","voltar","sair"};
	
	public boolean up,down,enter;
	
	public int currentOption = 0;


	public int maxOption = options.length - 1;
	
	
	
	
	

	
	public void tick() {
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
			if(options[currentOption] == "solo") {
				Game.gameState = "SINGLE";
			}else if(options[currentOption] == "duo") {
					Game.gameState = "DUO";
			}else if(options[currentOption] == "voltar") {
				Game.gameState = "MENU";
			}else if(options[currentOption] == "sair") {
				System.exit(1);
			}
		}

	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		//tela fundo
		g2.setColor(new Color(0,50,0,150));
		g2.fillRect(0, 0,Toolkit.getDefaultToolkit().getScreenSize().width,
				Toolkit.getDefaultToolkit().getScreenSize().height);
		
		//logo
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.BOLD,100));
		
		g.drawString("2020", (Game.WIDTH*Game.SCALE) / 2 + 180,
				(Game.HEIGHT*Game.SCALE) / 2 - 30);
	
		//Opcoes de menu
				g.setColor(Color.white);
				g.setFont(new Font("arial",Font.BOLD,30));
				
				g.drawString("Solo",(Game.WIDTH*Game.SCALE) / 2 + 250, 400);
			    g.drawString("Duo", (Game.WIDTH*Game.SCALE) / 2 + 255, 440);
				g.drawString("Voltar", (Game.WIDTH*Game.SCALE) / 2 + 245, 480);
				g.drawString("Sair", (Game.WIDTH*Game.SCALE) / 2 + 260, 520);
				
				if(options[currentOption] == "solo") {
					g.drawString(">", (Game.WIDTH*Game.SCALE) / 2 + 210, 400);
					
				}else if(options[currentOption] == "duo") {
					g.drawString(">", (Game.WIDTH*Game.SCALE) / 2 + 215, 440);
					
				}else if(options[currentOption] == "voltar") {
					g.drawString(">", (Game.WIDTH*Game.SCALE) / 2 + 220,480);
					
				}else if(options[currentOption] == "sair") {
					g.drawString(">", (Game.WIDTH*Game.SCALE) / 2 + 210,520);
					
		
	 }
	}
}


