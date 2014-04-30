package ita.univr.elaborato.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Pulsante extends JButton {
	private int state;
	private int x;
	private int y;
	private CampoDiGioco campo;
	public Pulsante (int x,int y,int state,CampoDiGioco campo) {
		this.campo = campo;
		this.x = x;
		this.y = y;
		this.state = state;
		
		Color();
		setListener();
	}
	
	
	private void Color() {
		javax.swing.ImageIcon b = new javax.swing.ImageIcon("src/Immagini/b.png");
		javax.swing.ImageIcon n = new javax.swing.ImageIcon("src/Immagini/n.png");
		javax.swing.ImageIcon db = new javax.swing.ImageIcon("src/Immagini/db.png");
		javax.swing.ImageIcon dn = new javax.swing.ImageIcon("src/Immagini/dn.png");
		switch(state){
		case 1: this.setBackground(Color.black); this.setIcon(null);	break;
		case 2: this.setBackground(Color.black); this.setIcon(b);		break;
		case 3: this.setBackground(Color.black); this.setIcon(n);	break;
		case 4: this.setBackground(Color.black); this.setIcon(db);	break;
		case 5: this.setBackground(Color.black); this.setIcon(dn);	break;
		default:  this.setBackground(Color.white);	break; }
	}


	private void setListener() {
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				
				boolean prima = campo.getPrima();
				
				if(prima) {
					campo.SetX(x);
					campo.SetY(y);
					campo.SetS(state);
					campo.setPrima(false);
				}
				
				else {
					campo.SetXp(x);
					campo.SetYp(y);
					campo.SetSp(state);
					campo.setPrima(true);
				}
				
			}
		});
	}
	
	public void cambiaStato(int state) {
		this.state = state;
	}
	
	public int prendiStato() {
		return this.state;
	}
	
}
