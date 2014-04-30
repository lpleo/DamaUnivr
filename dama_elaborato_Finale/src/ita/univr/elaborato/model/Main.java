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
		boolean NuovaPartita = campo.retPartita();
		
		boolean nonAncora = false;
		boolean turno = false;
		
		Controllo controllo = new Controllo();
		campo.setVisible(true);
		String campopr = campo.toString();
		
		while(true) {
			
			if(NuovaPartita){
				campo = new CampoDiGioco();
				campo.setVisible(true);
				NuovaPartita=false;
				
			}
			
			int cont =0, cont2 =0;
			for(int xt=0;xt<8;xt++) {
			     for(int yt=0;yt<8;yt++) {
			      int vin = campo.retPulsanti()[xt][yt].prendiStato();
			      if(vin == 2 || vin == 4) cont++;
			      if(vin == 3 || vin == 5) cont2++;
			      if (controllo.retGBloccato() == true)
			    	  cont=0;
			      if (controllo.retIBloccato() == true)
			    	  cont2=0;
			     }
			    }
			if(cont==0) {
			     javax.swing.JOptionPane.showMessageDialog(null, "Hai Perso!");
			     break;
			}
			if(cont2==0) {
			     javax.swing.JOptionPane.showMessageDialog(null, "Hai Vinto!");
			     break;
			}
			
			
			int x = campo.PrendiX();
			int y = campo.PrendiY();
			int xp = campo.PrendiXp();
			int yp = campo.PrendiYp();
			boolean mossa = campo.getPrima();
		//	System.out.println(x + " " + y + " " + xp + " " + yp + " " + nonAncora); //DEBUG
			if(((x!=0)&&(y!=0))||((xp!=0)&&(yp!=0))) nonAncora = mossa;
			if(nonAncora) { 
				
				if(!turno){
					System.out.println("aaaaaaaaaa");
					controllo.Muovi(x,y,xp,yp,campo,turno);
				}else{
					int savex = campo.PrendiX();
					int savey = campo.PrendiY();
					int savexp = campo.PrendiXp();
					int saveyp = campo.PrendiYp();
						//if(!stop){
							IA Pc = new IA(campo);
							System.out.println("bbbbbbbbbb");
							campo = Pc.retCampo();
							
						//}
					campo.SetX(savex);
					campo.SetY(savey);
					campo.SetXp(savexp);
					campo.SetYp(saveyp);
				}
			//	System.out.println(campo);
				campo.repaint();
				campo.revalidate();
				campo.setVisible(true);
				if((campopr.compareTo(campo.toString())!=0)&&(!controllo.retDoppia())){
					turno = !turno;
				}
				//controllo.setDoppia();
				campopr = campo.toString();
				
				}
			
			}
		
		
	}
	
	
}
	

