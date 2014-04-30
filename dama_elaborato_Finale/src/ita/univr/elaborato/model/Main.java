package ita.univr.elaborato.model;

import ita.univr.elaborato.controller.IA;
import ita.univr.elaborato.view.CampoDiGioco;
import ita.univr.elaborato.view.Pulsante;

public class Main {
	
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		CampoDiGioco campo = new CampoDiGioco();
		campo.setPrima(true);
		
		boolean nonAncora = false;
		boolean turno = false;
		
		Controllo controllo = new Controllo();
		campo.setVisible(true);
		javax.swing.JOptionPane.showMessageDialog( null, "Istruzioni:\nPer muovere una pedina normale, selezionare la pedina e successivamente\nselezionare la casella vuota dove si desidera spostarla.\n\nIn caso di mangiata, selezionare prima la pedina da muovere\ne selezionare poi la pedina da mangiare;\nInoltre, sarà impossibile muovere le altre pedine.\n\nNel caso venissero selezionate consecutivamente due caselle\n non adiacenti diagonalmente, fare doppio clic sulla pedina che si desidera muovere.");
		String campopr = campo.toString();
		
		while(true) {
			
			int x = campo.PrendiX();
			int y = campo.PrendiY();
			int xp = campo.PrendiXp();
			int yp = campo.PrendiYp();
			boolean mossa = campo.getPrima();
			
			boolean NuovaPartita = campo.retPartita();
			boolean Esci = campo.retEsci();
			   if(NuovaPartita){
			    campo.setVisible(false);
			    campo = new CampoDiGioco();
			    campo.repaint();
			    campo.revalidate();
			    NuovaPartita=false;
			    nonAncora = false;
			    turno = false;
			    controllo = new Controllo();
			    campo.setVisible(true);
			   }
		   if(Esci){
			    break;
		   }
			
			int cont =0, cont2 =0;
			for(int xt=0;xt<8;xt++) {
			     for(int yt=0;yt<8;yt++) {
			      int vin = campo.retPulsanti()[xt][yt].prendiStato();
			      if(vin == 2 || vin == 4) cont++;
			      if(vin == 3 || vin == 5) cont2++;
			     }
			    }
			if(cont==0 || !controllo.CanMove(campo)) {
			     javax.swing.JOptionPane.showMessageDialog(null, "Hai Perso!");
			     break;
			}
			if(cont2==0 || !controllo.CanMoveIA(campo)) {
			     javax.swing.JOptionPane.showMessageDialog(null, "Hai Vinto!");
			     break;
			}
			
			
		//	System.out.println(x + " " + y + " " + xp + " " + yp + " " + nonAncora); //DEBUG
			if(((x!=0)&&(y!=0))||((xp!=0)&&(yp!=0))) nonAncora = mossa;
			if(nonAncora) { 
				
				if(!turno){
					controllo.Muovi(x,y,xp,yp,campo,turno);
				}else{
					IA Pc = new IA(campo);
					campo = Pc.retCampo();
					controllo.resetta(campo);
				}
				campo.repaint();
				campo.revalidate();
				campo.setVisible(true);
				if((campopr.compareTo(campo.toString())!=0)&&(!controllo.retDoppia()))
					turno = !turno;
					
				campopr = campo.toString();
				}
			}
		campo.setVisible(false);
	}
}
	

