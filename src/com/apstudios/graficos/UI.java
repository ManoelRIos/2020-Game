package com.apstudios.graficos;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import com.apstudios.entities.Player;
import com.apstudios.main.Game;

public class UI {
	

	public void render(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("roboto",Font.PLAIN,10));
		g.drawString("Points:"+Player.lixo,130, 11);
		g.setColor(Color.red);
		g.fillRect(8,4,70,8);
		g.setColor(Color.green);
		g.fillRect(8,4,(int)((Game.player.life/Game.player.maxlife)*70),8);
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.PLAIN,8));
		g.drawString((int)Game.player.life+"/"+(int)Game.player.maxlife,30,11);
		g.setFont(new Font("arial",Font.PLAIN,10));
		g.drawString("FPS:"+Game.frames, 225, 195);
		if(Game.gameState == "DUO") {
			g.setColor(Color.red);
			g.fillRect(200,4,70,8);
			g.setColor(Color.green);
			g.fillRect(200,4,(int)((Game.player2.life2/Game.player2.maxlife2)*70),8);
			g.setColor(Color.white);
			g.setFont(new Font("arial",Font.PLAIN,8));
			g.drawString((int)Game.player2.life2+"/"+(int)Game.player2.maxlife2,230,11);
		}

		
	}
	
}
