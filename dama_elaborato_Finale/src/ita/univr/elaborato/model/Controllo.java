package ita.univr.elaborato.model;

import java.awt.Color;
import java.util.Random;

import javax.swing.JOptionPane;

import ita.univr.elaborato.controller.ObbligoMangiata;
import ita.univr.elaborato.view.CampoDiGioco;
import ita.univr.elaborato.view.Pulsante;

public class Controllo {
	
	private boolean doppio, Gbloccato,Ibloccato;
	private int cont;
	public Controllo() {
		
	}
	
	public void Muovi(int x, int y, int xp, int yp, CampoDiGioco campo , boolean turno){
		
		campo = settaS(x,y, campo);
		int state = campo.PrendiS();// prendi s � della prima pedina quindi ok
		
		if(!turno){
			ObbligoMangiata control = new ObbligoMangiata(campo);
			System.out.println("Sono in Muovi con X:"+x+" Y:"+y+ " S:"+ state + " Xp:"+xp+" Yp:"+yp);
			
			if(((!turno && (state == 3 || state == 5)) || (turno && (state == 2 || state == 4)))) resetta(campo); //nope
			else if(state!=2 && state!=4 ) System.out.println("mossa non effettuata1"); //se parto da stato nero,sto cazzo
			else if(x==xp && y==yp) System.out.println("mossa non effettuata2");  // stessa casella
			else if(state == campo.PrendiSp()) System.out.println("mossa non possibile1"); //mossa su stessa pedina
			else if((state==2) && !(((xp==x-1)&&(yp==y-1))||((xp==x-1)&&(yp==y+1))) && (x>0 || x<8) && (y>=0 || y<8)) System.out.println("mossa non possibile2"); 
			//se damone non muove oltre il suo raggio...
			else if((state == 4) && !(((xp==x-1 || xp==x+1 ) && ((yp==y-1) || yp==y+1 ))) ) System.out.println("mossa non possibile3");
			else if(MossaObbligata(x,y,xp,yp,state,campo)==true) {
				   resetta(campo);
			} else if(!CanEat(x,y,xp,yp,campo)){
				if(canMove(campo))
				   MossaNormale(x,y,xp,yp, campo);
				   }
				  else {
				   Mangia(x,y,xp,yp, campo);
				  }
				
				
				
				/* if(MossaObbligata(x,y,xp,yp,state,campo)) {
				resetta(campo);
				/*una pedina può mangiare...
				non può muovere normalmente (obbligo di mangiata)
			}
			else {
				System.out.println("Perch� non vai?");
					if(control.retObbligo()){
						System.out.println("gi� 1");
						MossaNormale(x,y,xp,yp, campo);
					}else {
						System.out.println("gi� 2");
						switch(control.retEat(x, y)){
							case 1: Mangia(x,y,x+1,y-1, campo); break;
							case 2: Mangia(x,y,x+1,y+1, campo); break;
							case 3: Mangia(x,y,x-1,y-1, campo); break;
							case 4: Mangia(x,y,x-1,y+1, campo); break;
						}
				}		*/
		}else{
			System.out.println("Sono in Muovi con Ia in X:"+x+" Y:"+y+ " S:"+ state + " Xp:"+xp+" Yp:"+yp);
			
			if(((!turno && (state == 3 || state == 5)) || (turno && (state == 2 || state == 4)))) resetta(campo); //nope
			else if(state!=3 && state!=5 ) System.out.println("mossa non effettuata3"); //se parto da stato nero,sto cazzo
			else if(x==xp && y==yp) System.out.println("mossa non effettuata4");  // stessa casella
			else if(state == campo.PrendiSp()) System.out.println("mossa non possibile4"); //mossa su stessa pedina
			else if((state==3) && !(((xp==x+1)&&(yp==y-1))||((xp==x+1)&&(yp==y+1))) && (x>0 || x<8) && (y>=0 || y<8)) System.out.println("mossa non possibile5"); 
			//se damone non muove oltre il suo raggio...
			else if((state == 5) && !(((xp==x-1 || xp==x+1 ) && ((yp==y-1) || yp==y+1 ))) ) System.out.println("mossa non possibile6");
			else 
				MossaNormale(x,y,xp,yp, campo);
			/*else if(MossaObbligata(x,y,xp,yp,state,campo)) {
				JOptionPane.showMessageDialog(campo, "Puoi Mangiare!");
				/*una pedina può mangiare...
				non può muovere normalmente (obbligo di mangiata)
			}*/
			}
		// non posso fare il passo doppio, così diventa controintuitivo. vedere se si può fixare
	}
	
	private boolean canMove(CampoDiGioco campo) {
		
		CampoDiGioco temp = campo;
		
		for(int c=0;c<8;c++)
			for(int i=0;i<8;i++){
				int s=temp.retPulsanti()[c][i].prendiStato();
				switch (s){
				case 4:
					try{
						int sp=temp.retPulsanti()[c+1][i-1].prendiStato();
						if(sp == 1)
							return true;
						}
						catch (Exception e){
							// null
						}
						try{
						int sp=temp.retPulsanti()[c+1][i+1].prendiStato();
						if(sp == 1)
							return true;
						}
						catch (Exception e){
							// null
						}
				case 2:
					try{
						int sp=temp.retPulsanti()[c-1][i-1].prendiStato();
						if(sp == 1)
							return true;
						}
						catch (Exception e){
							// null
						}
						try{
						int sp=temp.retPulsanti()[c-1][i+1].prendiStato();
						if(sp == 1)
							return true;
						}
						catch (Exception e){
							// null
						}
				default: break;
				}
			}
				
		Gbloccato=true;
		return false;
	}
private boolean canMoveIA(CampoDiGioco campo) {
		
		CampoDiGioco temp = campo;
		
		for(int c=0;c<8;c++)
			for(int i=0;i<8;i++){
				int s=temp.retPulsanti()[c][i].prendiStato();
				switch (s){
				case 5:
						try{
						int sp=temp.retPulsanti()[c-1][i-1].prendiStato();
						if(sp == 1)
							return true;
						}
						catch (Exception e){
							// null
						}
						try{
						int sp=temp.retPulsanti()[c-1][i+1].prendiStato();
						if(sp == 1)
							return true;
						}
						catch (Exception e){
							// null
						}
				case 3:
						try{
						int sp=temp.retPulsanti()[c+1][i-1].prendiStato();
						if(sp == 1)
							return true;
						}
						catch (Exception e){
							// null
						}
						try{
						int sp=temp.retPulsanti()[c+1][i+1].prendiStato();
						if(sp == 1)
							return true;
						}
						catch (Exception e){
							// null
						}
						break;
				default: break;
				}
			}
				
		Ibloccato=true;
		return false;
	}
	
	public boolean retGBloccato(){
		return Gbloccato;
	}
	public boolean retIBloccato(){
		return Ibloccato;
	}

	private CampoDiGioco settaS(int x, int y,CampoDiGioco campo) {
		campo.SetX(x);
		campo.SetY(y);
		campo.SetS(campo.toStringState(x, y));
		return campo;
	}

	public boolean CanEat(int x, int y, int xp, int yp,CampoDiGioco campo){
		int state=campo.retPulsanti()[x][y].prendiStato();
		int statep=campo.retPulsanti()[xp][yp].prendiStato();
		if(((state==2 && (y-1 == yp && x-1 == xp) && statep == 3) && (yp-1 >= 0 && xp-1>=0))) if((campo.retPulsanti()[xp-1][yp-1].prendiStato()== 1)){recolor(6,campo.retPulsanti()[x][y]); recolor(7,campo.retPulsanti()[xp][yp]); return true;}
		if(((state==2 && (y+1 == yp && x-1 == xp) && statep == 3) && (yp+1 <8 && xp-1>=0))) if((campo.retPulsanti()[xp-1][yp+1].prendiStato()== 1)){recolor(6,campo.retPulsanti()[x][y]); recolor(7,campo.retPulsanti()[xp][yp]); return true;}
		if(((state==3 && (y-1 == yp && x+1 == xp) && statep == 2) && (yp-1 >= 0 && xp+1<8))) if((campo.retPulsanti()[xp+1][yp-1].prendiStato()== 1)) return true;
		if(((state==3 && (y+1 == yp && x+1 == xp) && statep == 2) && (yp+1 <8 && xp+1<8))) if((campo.retPulsanti()[xp+1][yp+1].prendiStato()== 1))  return true;
		if(((state==4 && (y-1 == yp && x-1 == xp) && (statep == 3 || statep == 5)) && (yp-1 >= 0 && xp-1>=0))) if((campo.retPulsanti()[xp-1][yp-1].prendiStato()== 1)) {recolor(6,campo.retPulsanti()[x][y]); recolor(7,campo.retPulsanti()[xp][yp]); return true;}
		if(((state==4 && (y+1 == yp && x-1 == xp) && (statep == 3 || statep == 5)) && (yp+1 <8 && xp-1>=0))) if((campo.retPulsanti()[xp-1][yp+1].prendiStato()== 1)){recolor(6,campo.retPulsanti()[x][y]); recolor(7,campo.retPulsanti()[xp][yp]); return true;}
		if(((state==4 && (y-1 == yp && x+1 == xp) && (statep == 3 || statep == 5)) && (yp-1 >= 0 && xp+1<8))) if((campo.retPulsanti()[xp+1][yp-1].prendiStato()== 1)){recolor(6,campo.retPulsanti()[x][y]); recolor(7,campo.retPulsanti()[xp][yp]); return true;}
		if(((state==4 && (y+1 == yp && x+1 == xp) && (statep == 3 || statep == 5)) && (yp+1 <8 && xp+1<8))) if((campo.retPulsanti()[xp+1][yp+1].prendiStato()== 1)) {recolor(6,campo.retPulsanti()[x][y]); recolor(7,campo.retPulsanti()[xp][yp]); return true;}
		if(((state==5 && (y-1 == yp && x-1 == xp) && (statep == 2 || statep == 4)) && (yp-1 >= 0 && xp-1>=0))) if((campo.retPulsanti()[xp-1][yp-1].prendiStato()== 1)) return true;
		if(((state==5 && (y+1 == yp && x-1 == xp) && (statep == 2 || statep == 4)) && (yp+1 <8 && xp-1>=0))) if((campo.retPulsanti()[xp-1][yp+1].prendiStato()== 1)) return true;
		if(((state==5 && (y-1 == yp && x+1 == xp) && (statep == 2 || statep == 4)) && (yp-1 >= 0 && xp+1<8))) if((campo.retPulsanti()[xp+1][yp-1].prendiStato()== 1)) return true;
		if(((state==5 && (y+1 == yp && x+1 == xp) && (statep == 2 || statep == 4)) && (yp+1 <8 && xp+1<8))) if((campo.retPulsanti()[xp+1][yp+1].prendiStato()== 1))  return true;
		return false;
	}
	
	private void MossaNormale(int x,int y,int xp,int yp,CampoDiGioco campo){
		int state = campo.PrendiS();
		int statep = campo.PrendiSp();
		Pulsante[][] pulsanti = campo.retPulsanti();
		if(((state==2 && statep==1) || (state==3)&&(statep==1))) { 
			pulsanti[x][y].cambiaStato(statep);
			pulsanti[xp][yp].cambiaStato(state);
			if((state==2)&&(xp==0)) { 
				pulsanti[xp][yp].cambiaStato(4);
				recolor(1,pulsanti[x][y]);
				recolor(4,pulsanti[xp][yp]);
			}
			else if((state==3)&&(xp==7)) {
				pulsanti[xp][yp].cambiaStato(5);
				recolor(1,pulsanti[x][y]);
				recolor(5,pulsanti[xp][yp]);
			}
			else {
				recolor(statep,pulsanti[x][y]);
				recolor(state,pulsanti[xp][yp]);
			}
		}

		if((state==4 || state==5) && (statep==1)) {
			pulsanti[x][y].cambiaStato(statep);
			pulsanti[xp][yp].cambiaStato(state);
			recolor(statep,pulsanti[x][y]);
			recolor(state,pulsanti[xp][yp]);
		}
	}
	
	public void Mangia(int x,int y,int xp,int yp,CampoDiGioco campo) {
		int state = campo.PrendiS();
		int statep = campo.PrendiSp();
		boolean temp = true;
		boolean temp2 = true;
		Pulsante[][] pulsanti = campo.retPulsanti();
		this.doppio=false;
		int xd=0,yd=0;
		if(((state==2 && (y-1 == yp && x-1 == xp) && statep == 3) && (yp-1 >=0 && xp-1>=0))) if((campo.retPulsanti()[xp-1][yp-1].prendiStato()== 1)) {
			//mangiata sx pedina rossa
			mangiaSXA(x,y,xp,yp,state,pulsanti);
			if((state==2 && xp-1==0)) {
				pulsanti[xp-1][yp-1].cambiaStato(4);
				recolor(4,pulsanti[xp-1][yp-1]);
			}
			xd=xp-1;
			yd=yp-1;
			try {
			if(CanEat(xd,yd,xd-1,yd-1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
			if(CanEat(xd,yd,xd-1,yd+1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
		}
		
		if(((state==2 && (y+1 == yp && x-1 == xp) && statep == 3) && (yp+1 <8 && xp-1>=0))) if((campo.retPulsanti()[xp-1][yp+1].prendiStato()== 1)) {
			//mangiata dx pedina rossa
			mangiaDXA(x,y,xp,yp,state,pulsanti);
			if((state==2 && xp-1==0)) {
				pulsanti[xp-1][yp+1].cambiaStato(4);
				recolor(4,pulsanti[xp-1][yp+1]);
			}
			xd=xp-1;
			yd=yp+1;
			try {
			if(CanEat(xd,yd,xd-1,yd-1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
			if(CanEat(xd,yd,xd-1,yd+1,campo)) doppio=true;
			} catch (Exception e) {
				
			}
		}
		
		
		if(((state==3 && (y-1 == yp && x+1 == xp) && statep == 2) && (yp-1 >= 0 && xp+1<8))) if((campo.retPulsanti()[xp+1][yp-1].prendiStato()== 1)) {
			//mangiata sx pedina gialla
			System.out.println("non vuoi mangiare1? x:" + x + " y:"+ y + " xp:" + xp + " yp:" + yp + campo);
			
			mangiaSXB(x,y,xp,yp,state,pulsanti);
			if((state==3 && xp+1==7)) {
				pulsanti[xp+1][yp-1].cambiaStato(5);
				recolor(5,pulsanti[xp+1][yp-1]);
			}
			xd=xp+1;
			yd=yp-1;
			try {
			if(CanEat(xd,yd,xd+1,yd-1,campo)) Mangia(xd,yd,xd+1,yd-1,campo);
			} catch(Exception e) {
				// null
			}
			try{
			if(CanEat(xd,yd,xd+1,yd+1,campo)) Mangia(xd,yd,xd+1,yd+1,campo);
			}
			catch(Exception e) {
				//null
			}
			/*if(temp) doppio=true;
			else if(temp2) doppio=true;
			else doppio=false;*/
		}
		
		if(((state==3 && (y+1 == yp && x+1 == xp) && statep == 2) && (yp+1 <8 && xp+1<8))) if((campo.retPulsanti()[xp+1][yp+1].prendiStato()== 1)) {
			//mangiata dx pedina gialla
			System.out.println("non vuoi mangiare2? x:" + x + " y:"+ y + " xp:" + xp + " yp:" + yp + campo);
			mangiaDXB(x,y,xp,yp,state,pulsanti);
			if((state==3 && xp+1==7)) {
				pulsanti[xp+1][yp+1].cambiaStato(5);
				recolor(5,pulsanti[xp+1][yp+1]);
			}
			xd=xp+1;
			yd=yp+1;
			try {	
			if(CanEat(xd,yd,xd+1,yd-1,campo)) Mangia(xd,yd,xd+1,yd-1,campo);
			} catch(Exception e) {
				// null
			}
			try{
			if(CanEat(xd,yd,xd+1,yd+1,campo)) Mangia(xd,yd,xd+1,yd+1,campo);
			} 
			catch(Exception e) {
				//null
			}
		}
		
		if(((state==4 && (y-1 == yp && x-1 == xp) && (statep == 3 || statep == 5)) && (yp-1 >= 0 && xp-1>=0))) if((campo.retPulsanti()[xp-1][yp-1].prendiStato()== 1)) {
			mangiaSXA(x,y,xp,yp,state,pulsanti);
			xd=xp-1;
			yd=yp-1;
			try {
			if(CanEat(xd,yd,xd-1,yd-1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
			if(CanEat(xd,yd,xd-1,yd+1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
			if(CanEat(xd,yd,xd+1,yd-1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
			if(CanEat(xd,yd,xd+1,yd+1,campo)) doppio=true;
			} catch(Exception e) {
				
			}
		}
		
		if(((state==4 && (y+1 == yp && x-1 == xp) && (statep == 3 || statep == 5)) && (yp+1 <8 && xp-1>=0))) if((campo.retPulsanti()[xp-1][yp+1].prendiStato()== 1)) {
			mangiaDXA(x,y,xp,yp,state,pulsanti);
			xd=xp-1;
			yd=yp+1;
			try {
			if(CanEat(xd,yd,xd-1,yd-1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
			if(CanEat(xd,yd,xd-1,yd+1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
			if(CanEat(xd,yd,xd+1,yd-1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
			if(CanEat(xd,yd,xd+1,yd+1,campo)) doppio=true;
			} catch(Exception e) {
				
			}
		}
		
		if(((state==4 && (y-1 == yp && x+1 == xp) && (statep == 3 || statep == 5)) && (yp-1 >= 0 && xp+1<8))) if((campo.retPulsanti()[xp+1][yp-1].prendiStato()== 1)) {
			mangiaSXB(x,y,xp,yp,state,pulsanti);
			xd=xp+1;
			yd=yp-1;
			try {
			if(CanEat(xd,yd,xd-1,yd-1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
			if(CanEat(xd,yd,xd-1,yd+1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
			if(CanEat(xd,yd,xd+1,yd-1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
			if(CanEat(xd,yd,xd+1,yd+1,campo)) doppio=true;
			} catch(Exception e) {
				
			}
		}
		
		if(((state==4 && (y+1 == yp && x+1 == xp) && (statep == 3 || statep == 5)) && (yp+1 <8 && xp+1<8))) if((campo.retPulsanti()[xp+1][yp+1].prendiStato()== 1)) {
			mangiaDXB(x,y,xp,yp,state,pulsanti);
			xd=xp+1;
			yd=yp+1;
			try {
			if(CanEat(xd,yd,xd-1,yd-1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
			if(CanEat(xd,yd,xd-1,yd+1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
			if(CanEat(xd,yd,xd+1,yd-1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
			if(CanEat(xd,yd,xd+1,yd+1,campo)) doppio=true;
			} catch(Exception e) {
				
			}
		}
		
		if(((state==5 && (y-1 == yp && x-1 == xp) && (statep == 2 || statep == 4)) && (yp-1 >= 0 && xp-1>=0))) if((campo.retPulsanti()[xp-1][yp-1].prendiStato()== 1)) {
			System.out.println("non vuoi mangiare? x:" + x + " y:"+ y + " xp:" + xp + " yp:" + yp + campo);
			mangiaSXA(x,y,xp,yp,state,pulsanti);
			xd=xp-1;
			yd=yp-1;
			try {
			if(CanEat(xd,yd,xd-1,yd-1,campo)) Mangia(xd,yd,xd-1,yd-1,campo);
			} catch(Exception e) {
				// null
			}
			try{
			if(CanEat(xd,yd,xd-1,yd+1,campo)) Mangia(xd,yd,xd-1,yd+1,campo);
			} catch(Exception e) {
				// null
			}
			try{
			if(CanEat(xd,yd,xd+1,yd-1,campo)) Mangia(xd,yd,xd+1,yd-1,campo);
			} catch(Exception e) {
				// null
			}
			try{
			if(CanEat(xd,yd,xd+1,yd+1,campo)) Mangia(xd,yd,xd+1,yd+1,campo);
			} catch(Exception e) {
				
			}
			System.out.println("Sono Qui 1 con doppio:"+doppio);
		}
		
		if(((state==5 && (y+1 == yp && x-1 == xp) && (statep == 2 || statep == 4)) && (yp+1 <8 && xp-1>=0))) if((campo.retPulsanti()[xp-1][yp+1].prendiStato()== 1)){
			System.out.println("non vuoi mangiare? x:" + x + " y:"+ y + " xp:" + xp + " yp:" + yp + campo);
			mangiaDXA(x,y,xp,yp,state,pulsanti);
			xd=xp-1;
			yd=yp+1;
			try {
				if(CanEat(xd,yd,xd-1,yd-1,campo)) Mangia(xd,yd,xd-1,yd-1,campo);
			} catch(Exception e) {
				// null
			}
			try{
				if(CanEat(xd,yd,xd-1,yd+1,campo)) Mangia(xd,yd,xd-1,yd+1,campo);
			} catch(Exception e) {
				// null
			}
			try{
				if(CanEat(xd,yd,xd+1,yd-1,campo)) Mangia(xd,yd,xd+1,yd-1,campo);
			} catch(Exception e) {
				// null
			}
			try{
				if(CanEat(xd,yd,xd+1,yd+1,campo)) Mangia(xd,yd,xd+1,yd+1,campo);
			} catch(Exception e) {
				
			}
			System.out.println("Sono Qui 2 con doppio:"+doppio);
		}
		
		if(((state==5 && (y-1 == yp && x+1 == xp) && (statep == 2 || statep == 4)) && (yp-1 >= 0 && xp+1<8))) if((campo.retPulsanti()[xp+1][yp-1].prendiStato()== 1)){
			System.out.println("non vuoi mangiare? x:" + x + " y:"+ y + " xp:" + xp + " yp:" + yp + campo);
			mangiaSXB(x,y,xp,yp,state,pulsanti);
			xd=xp+1;
			yd=yp-1;
			try {
				if(CanEat(xd,yd,xd-1,yd-1,campo)) Mangia(xd,yd,xd-1,yd-1,campo);
			} catch(Exception e) {
				// null
			}
			try{
				if(CanEat(xd,yd,xd-1,yd+1,campo)) Mangia(xd,yd,xd-1,yd+1,campo);
			} catch(Exception e) {
				// null
			}
			try{
				if(CanEat(xd,yd,xd+1,yd-1,campo)) Mangia(xd,yd,xd+1,yd-1,campo);
			} catch(Exception e) {
				// null
			}
			try{
				if(CanEat(xd,yd,xd+1,yd+1,campo)) Mangia(xd,yd,xd+1,yd+1,campo);
			} catch(Exception e) {
				
			}
			System.out.println("Sono Qui 3 con doppio:"+doppio);
		}
		
		if(((state==5 && (y+1 == yp && x+1 == xp) && (statep == 2 || statep == 4)) && (yp+1 <8 && xp+1<8))) if((campo.retPulsanti()[xp+1][yp+1].prendiStato()== 1)){
			System.out.println("non vuoi mangiare? x:" + x + " y:"+ y + " xp:" + xp + " yp:" + yp + campo);
			mangiaDXB(x,y,xp,yp,state,pulsanti);
			xd=xp+1;
			yd=yp+1;
			try {
				if(CanEat(xd,yd,xd-1,yd-1,campo)) Mangia(xd,yd,xd-1,yd-1,campo);
			} catch(Exception e) {
				// null
			}
			try{
				if(CanEat(xd,yd,xd-1,yd+1,campo)) Mangia(xd,yd,xd-1,yd+1,campo);
			} catch(Exception e) {
				// null
			}
			try{
				if(CanEat(xd,yd,xd+1,yd-1,campo)) Mangia(xd,yd,xd+1,yd-1,campo);
			} catch(Exception e) {
				// null
			}
			try{
				if(CanEat(xd,yd,xd+1,yd+1,campo)) Mangia(xd,yd,xd+1,yd+1,campo);
			} catch(Exception e) {
				
			}
			System.out.println("Sono Qui 4 con doppio:"+doppio);
		}
		resetta(campo);
	}
	
	public boolean retDoppia() {
		boolean temp = doppio;
		doppio=false;
		return temp;
	}
	
	public void setDoppia() {
		this.doppio=false;
	}
	
	private void recolor(int state, Pulsante pulsante) {
		javax.swing.ImageIcon b = new javax.swing.ImageIcon("src/Immagini/b.png");
		javax.swing.ImageIcon n = new javax.swing.ImageIcon("src/Immagini/n.png");
		javax.swing.ImageIcon db = new javax.swing.ImageIcon("src/Immagini/db.png");
		javax.swing.ImageIcon dn = new javax.swing.ImageIcon("src/Immagini/dn.png");
		switch(state){
		case 1: pulsante.setBackground(Color.black); pulsante.setIcon(null); 	break;
		case 2: pulsante.setBackground(Color.black); pulsante.setIcon(b);	break;
		case 3: pulsante.setBackground(Color.black); pulsante.setIcon(n);	break;
		case 4: pulsante.setBackground(Color.black); pulsante.setIcon(db);	break;
		case 5: pulsante.setBackground(Color.black); pulsante.setIcon(dn);	break;
		case 6: pulsante.setBackground(Color.GREEN);break;
		case 7: pulsante.setBackground(Color.YELLOW);break;
		default:  pulsante.setBackground(Color.black);	break;
		}	
	}
	
	private void resetta(CampoDiGioco campo) {
		campo.SetX(0);
		campo.SetY(0);
		campo.SetXp(0);
		campo.SetYp(0);
	}
	
	private void mangiaSXA(int x, int y, int xp, int yp, int state, Pulsante pulsanti[][]) {
		pulsanti[xp-1][yp-1].cambiaStato(state);
		pulsanti[x][y].cambiaStato(1);
		pulsanti[xp][yp].cambiaStato(1);
		recolor(state,pulsanti[xp-1][yp-1]);
		recolor(1,pulsanti[x][y]);
		recolor(1,pulsanti[xp][yp]);
	}
	
	private void mangiaDXA(int x, int y, int xp, int yp, int state, Pulsante pulsanti[][]) {
		pulsanti[xp-1][yp+1].cambiaStato(state);
		pulsanti[x][y].cambiaStato(1);
		pulsanti[xp][yp].cambiaStato(1);
		recolor(state,pulsanti[xp-1][yp+1]);
		recolor(1,pulsanti[x][y]);
		recolor(1,pulsanti[xp][yp]);
	}
	
	private void mangiaSXB(int x, int y, int xp, int yp, int state, Pulsante pulsanti[][]) {
		pulsanti[xp+1][yp-1].cambiaStato(state);
		pulsanti[x][y].cambiaStato(1);
		pulsanti[xp][yp].cambiaStato(1);
		recolor(state,pulsanti[xp+1][yp-1]);
		recolor(1,pulsanti[x][y]);
		recolor(1,pulsanti[xp][yp]);
	}
	
	private void mangiaDXB(int x, int y, int xp, int yp, int state, Pulsante pulsanti[][]) {
		pulsanti[xp+1][yp+1].cambiaStato(state);
		pulsanti[x][y].cambiaStato(1);
		pulsanti[xp][yp].cambiaStato(1);
		recolor(state,pulsanti[xp+1][yp+1]);
		recolor(1,pulsanti[x][y]);
		recolor(1,pulsanti[xp][yp]);
	}
	
	private boolean MossaObbligata(int x, int y, int xp, int yp, int state, CampoDiGioco campo) {
		  if(CanEat(x,y,xp,yp,campo)) return false;
		  for(int xt=0;xt<8;xt++) {
		   for(int yt=0;yt<8;yt++) {
		    if(state==2) {
		     try {
		     if(CanEat(xt,yt,xt-1,yt-1,campo)) return true;
		     if(CanEat(xt,yt,xt-1,yt+1,campo)) return true;
		     }
		     catch(Exception e) {
		      //null
		     }
		      
		    }
		    try{
		    if(state==4) {
		     if(CanEat(xt,yt,xt-1,yt-1,campo)) return true;
		     if(CanEat(xt,yt,xt-1,yt+1,campo)) return true;
		     if(CanEat(xt,yt,xt+1,yt-1,campo)) return true;
		     if(CanEat(xt,yt,xt+1,yt+1,campo)) return true;
		    }
		    } catch (Exception e){
		    	//null
		    }
		    
		    /*if(state==3) {
		     if(CanEat(xt,yt,xt+1,yt-1,campo)) casovero = true;
		     if(CanEat(xt,yt,xt+1,yt+1,campo)) casovero = true;
		    }
		    
		    if(state==5) {
		     if(CanEat(xt,yt,xt-1,yt-1,campo)) casovero = true;
		     if(CanEat(xt,yt,xt-1,yt+1,campo)) casovero = true;
		     if(CanEat(xt,yt,xt+1,yt-1,campo)) casovero = true;
		     if(CanEat(xt,yt,xt+1,yt+1,campo)) casovero = true;
		    }*/
		    
		    }
		  }
		  return false;
		 }
	///////////////////////////////////////////////////////
	//
	// BUG :
	// 3) COLLEGARE (E SPERARE) CHE L'IA FUNZIONI
	// 6) SPERIAMO DI ARRIVARE IN FONDO PRIMA O POI
	// 7) MANGIATA CHE FA CAGARE
	// 8) SONO CAZZI SE SI PERDE IL TEMPO (CLICK SBAGLIATO?)
	// 11) ERRORI IN ALCUNI CASI DI MANGIATE = SONO CAZZI. SEMBRA PROBLEMA TURNI NO CONTROLLI
	// 12) IMPLEMENTARE MANGIATA OBBLIGATORIA NEL TURNO
	// 13)
	//		
	///////////////////////////////////////////////////////
	
}
