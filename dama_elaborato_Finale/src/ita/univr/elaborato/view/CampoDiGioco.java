package ita.univr.elaborato.view;



import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

	public class CampoDiGioco extends JFrame {
		public boolean partita;
		public boolean prima;
		private Pulsante[][] pulsanti;
		private int x,y,state;
		private int xp,yp,statep;
		public CampoDiGioco() {
			super("Nuova partita");
		
			this.setLayout(new BorderLayout());
			setSize(700,700);
			getContentPane().setBackground(Color.DARK_GRAY);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			add(dx(), BorderLayout.CENTER);
			creaMenu();
			partita = false;
				
		}
		


		private void creaMenu() {
			JMenuItem Menu = new JMenuItem("Menu");
			JMenuItem Gioco = new JMenuItem("Nuova Partita");
			JMenuItem Opzioni = new JMenuItem("Opzioni");
			JMenuItem Licenza = new JMenuItem("Licenza");
			JMenuItem Esci = new JMenuItem("Esci");
			
			JMenu testo = new JMenu("Menu");
			testo.add(Menu);
			testo.add(Gioco);
			testo.add(Opzioni);
			testo.add(Licenza);
			testo.add(Esci);
			
			/*JMenuItem Istruzioni = new JMenuItem("Istruzioni");
			JMenuItem Guida = new JMenuItem("Guida");
			JMenuItem Contatti = new JMenuItem("Contatti");
			
			JMenu testoHelp = new JMenu("Guida");
			testoHelp.add(Istruzioni);
			testoHelp.add(Guida);
			testoHelp.add(Contatti);*/
						
			JMenuBar bar = new JMenuBar();
		//	JMenuBar help = new JMenuBar();
			
			bar.add(testo);
		//	bar.add(testoHelp);
			//help.add(testoHelp);
			
			Gioco.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					partita = true;
				}

			});
			this.setJMenuBar(bar);
			//this.setJMenuBar(help);
		}

		private JPanel dx() {
			JPanel pannello = new JPanel();
			pannello.setSize(700,700);
			pannello.setLayout(new GridLayout());
			pannello.setLayout(new GridLayout(8,8));
			pulsanti = new Pulsante[8][8];
	
			for(int c=0;c<8;c++){
				for(int i=0;i<8;i++) {			
					if((c+i) % 2 != 0){
						pulsanti[c][i]= new Pulsante(c,i,0,this);	// creo la casella bianca
						pulsanti[c][i].setEnabled(false);
					}else if ( c<3 )
						pulsanti[c][i]= new Pulsante(c,i,3,this);	// creo la pedina nera
					else if ( c > 2 && c < 5 )
						pulsanti[c][i]= new Pulsante(c,i,1,this);	// creo la casella nera
					else
						pulsanti[c][i]= new Pulsante(c,i,2,this);	// creo la pedina bianca
					pannello.add(pulsanti[c][i]);
				}		
			}	
			
			return pannello;
		}
		
		
		public boolean retPartita(){
			return partita;
		}
		
		public Pulsante[][] retPulsanti() {
			return pulsanti;
		}
		
		public void newCampo(Pulsante[][] pulsanti) {
			this.pulsanti = pulsanti;
		}
		
		public void setPrima(boolean prima) {
			this.prima = prima;
		}
		
		public boolean getPrima() {
			return this.prima;
		}
		
		public void SetX (int x) {
			this.x=x;
		}
		public void SetY (int y) {
			this.y=y;
		}
		public void SetS (int s) {
			this.state=s;
		}
		
		public void SetXp (int xp) {
			this.xp=xp;
		}
		public void SetYp (int yp) {
			this.yp=yp;
		}
		public void SetSp (int sp) {
			this.statep=sp;
		}
		
		
		
		public int PrendiX() {
			return this.x;
		}
		public int PrendiY() {
			return this.y;
		}
		public int PrendiS() {
			return this.state;
		}
		
		public int PrendiXp() {
			return this.xp;
		}
		public int PrendiYp() {
			return this.yp;
		}
		public int PrendiSp() {
			return this.statep;
		}
		
		public String toString() {
			String ret ="";
			for(int c=0;c<8;c++) {
				for(int i=0;i<8;i++) {
					ret+=" " + this.retPulsanti()[c][i].prendiStato();
				}
			ret+="\n";
			}
			return ret;
		}
		public int toStringState(int x,int y) {
			for(int c=0;c<8;c++) 
				for(int i=0;i<8;i++) 
					if(c==x && i == y)
					return this.retPulsanti()[c][i].prendiStato();
			return 9;
		}
			
	}
