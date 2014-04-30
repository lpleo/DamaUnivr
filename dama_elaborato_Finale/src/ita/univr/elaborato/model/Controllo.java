package ita.univr.elaborato.model;

import java.awt.Color;

import ita.univr.elaborato.view.CampoDiGioco;
import ita.univr.elaborato.view.Pulsante;

public class Controllo {
	
	private boolean doppio,NonServeMaServe;
	public Controllo() {
	}
	
	public void Muovi(int x, int y, int xp, int yp, CampoDiGioco campo , boolean turno){
		
		campo = settaS(x,y, campo);
		int state = campo.PrendiS();// prendi lo stato della prima pedina
		System.out.println("Controllo 1");
		if(!turno){
			System.out.println("Sono in Muovi con X:"+x+" Y:"+y+ " S:"+ state + " Xp:"+xp+" Yp:"+yp);
			
			if(((!turno && (state == 3 || state == 5)) || (turno && (state == 2 || state == 4)))) resetta(campo);
			else if(state!=2 && state!=4 ) NonServeMaServe=false;
			else if(x==xp && y==yp) NonServeMaServe=false;
			else if(state == campo.PrendiSp()) NonServeMaServe=false;
			else if((state==2) && !(((xp==x-1)&&(yp==y-1))||((xp==x-1)&&(yp==y+1))) && (x>0 || x<8) && (y>=0 || y<8))NonServeMaServe=false; 
			else if((state == 4) && !(((xp==x-1 || xp==x+1 ) && ((yp==y-1) || yp==y+1 ))) ) NonServeMaServe=false;
			else if(MossaObbligata(x,y,xp,yp,state,campo)==true) {
				   resetta(campo);
			} else if(!CanEat(x,y,xp,yp,campo)){
				if(CanMove(campo))
				   MossaNormale(x,y,xp,yp, campo);
			}
				  else {
				   Mangia(x,y,xp,yp, campo);
				  }
		}else{
			System.out.println("Sono in Muovi con Ia in X:"+x+" Y:"+y+ " S:"+ state + " Xp:"+xp+" Yp:"+yp);
			
			if(((!turno && (state == 3 || state == 5)) || (turno && (state == 2 || state == 4)))) resetta(campo);
			else if(state!=3 && state!=5 )NonServeMaServe=false;
			else if(x==xp && y==yp) NonServeMaServe=false;
			else if(state == campo.PrendiSp()) NonServeMaServe=false;
			else if((state==3) && !(((xp==x+1)&&(yp==y-1))||((xp==x+1)&&(yp==y+1))) && (x>0 || x<8) && (y>=0 || y<8))NonServeMaServe=false; 
			else if((state == 5) && !(((xp==x-1 || xp==x+1 ) && ((yp==y-1) || yp==y+1 ))) ) NonServeMaServe=false;
			else 
				if(CanMoveIA(campo))
					MossaNormale(x,y,xp,yp, campo);
			}
	}
	
	public boolean CanMove(CampoDiGioco campo) {
		
		for(int c=0;c<8;c++) {
			for(int i=0;i<8;i++){
				
				if(campo.retPulsanti()[c][i].prendiStato()==2) {
					try {
					if(campo.retPulsanti()[c-1][i-1].prendiStato()==1) return true;
					} catch (Exception e) {}
					try {
					if(campo.retPulsanti()[c-1][i+1].prendiStato()==1) return true;
					} catch (Exception e) {}
				}
				
				if(campo.retPulsanti()[c][i].prendiStato()==4) {
					try {
						if(campo.retPulsanti()[c+1][i-1].prendiStato()==1) return true;
					} catch (Exception e) {}
					try {
						if(campo.retPulsanti()[c+1][i+1].prendiStato()==1) return true;
					} catch (Exception e) {}
					try {
						if(campo.retPulsanti()[c-1][i-1].prendiStato()==1) return true;
					} catch (Exception e) {}
					try {
						if(campo.retPulsanti()[c-1][i+1].prendiStato()==1) return true;
					} catch (Exception e) {}
				}
				}
			}
		return false;
	}
public boolean CanMoveIA(CampoDiGioco campo) {
				
		for(int c=0;c<8;c++) {
			for(int i=0;i<8;i++){
				
				if(campo.retPulsanti()[c][i].prendiStato()==3) {
					try {
					if(campo.retPulsanti()[c+1][i-1].prendiStato()==1) return true;
					} catch (Exception e) {}
					try {
					if(campo.retPulsanti()[c+1][i+1].prendiStato()==1) return true;
					} catch (Exception e) {}
				}
				
				if(campo.retPulsanti()[c][i].prendiStato()==5) {
					try {
						if(campo.retPulsanti()[c+1][i-1].prendiStato()==1) return true;
					} catch (Exception e) {}
					try {
						if(campo.retPulsanti()[c+1][i+1].prendiStato()==1) return true;
					} catch (Exception e) {}
					try {
						if(campo.retPulsanti()[c-1][i-1].prendiStato()==1) return true;
					} catch (Exception e) {}
					try {
						if(campo.retPulsanti()[c-1][i+1].prendiStato()==1) return true;
					} catch (Exception e) {}
				}
				}
			}
		return false;
	}

	private CampoDiGioco settaS(int x, int y,CampoDiGioco campo) {
		campo.SetX(x);
		campo.SetY(y);
		campo.SetS(campo.toStringState(x, y));
		return campo;
	}

	public boolean CanEat(int x, int y, int xp, int yp,CampoDiGioco campo){
		System.out.println("Controllo 5");
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
		System.out.println("Controllo 6");
		return false;
	}
	
	private void MossaNormale(int x,int y,int xp,int yp,CampoDiGioco campo){
		System.out.println("Controllo 7");
		int state=campo.retPulsanti()[x][y].prendiStato();
		int statep=campo.retPulsanti()[xp][yp].prendiStato();
		Pulsante[][] pulsanti = campo.retPulsanti();
		if(((state==2 && statep==1) || (state==3)&&(statep==1))) { 
			System.out.println("Controllo 8");
			pulsanti[x][y].cambiaStato(statep);
			pulsanti[xp][yp].cambiaStato(state);
			if((state==2)&&(xp==0)) { 
				System.out.println("Controllo 9");
				pulsanti[xp][yp].cambiaStato(4);
				recolor(1,pulsanti[x][y]);
				recolor(4,pulsanti[xp][yp]);
			}
			else if((state==3)&&(xp==7)) {
				System.out.println("Controllo 10");
				pulsanti[xp][yp].cambiaStato(5);
				recolor(1,pulsanti[x][y]);
				recolor(5,pulsanti[xp][yp]);
			}
			else {
				System.out.println("Controllo 11");
				recolor(statep,pulsanti[x][y]);
				recolor(state,pulsanti[xp][yp]);
			}
		}
		if((state==4 || state==5) && (statep==1)) {
			System.out.println("Controllo 12");
			pulsanti[x][y].cambiaStato(statep);
			pulsanti[xp][yp].cambiaStato(state);
			recolor(statep,pulsanti[x][y]);
			recolor(state,pulsanti[xp][yp]);
		}
	}
	
	public void Mangia(int x,int y,int xp,int yp,CampoDiGioco campo) {
		System.out.println("Controllo 13");
		int state=campo.retPulsanti()[x][y].prendiStato();
		int statep=campo.retPulsanti()[xp][yp].prendiStato();
		boolean temp = false;
		boolean temp2 = false;
		Pulsante[][] pulsanti = campo.retPulsanti();
		this.doppio=false;
		int xd=0,yd=0;
		if(((state==2 && (y-1 == yp && x-1 == xp) && statep == 3) && (yp-1 >=0 && xp-1>=0))) if((campo.retPulsanti()[xp-1][yp-1].prendiStato()== 1)) {
			//mangiata sx pedina rossa
			System.out.println("Controllo 14");
			mangiaSXA(x,y,xp,yp,state,pulsanti);
			if((state==2 && xp-1==0)) {
				pulsanti[xp-1][yp-1].cambiaStato(4);
				recolor(4,pulsanti[xp-1][yp-1]);
				temp = true;
			}
			xd=xp-1;
			yd=yp-1;
			try {
				System.out.println("Controllo 15");
			if(CanEat(xd,yd,xd-1,yd-1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
				System.out.println("Controllo 16");
			if(CanEat(xd,yd,xd-1,yd+1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
		}
		
		if(((state==2 && (y+1 == yp && x-1 == xp) && statep == 3) && (yp+1 <8 && xp-1>=0))) if((campo.retPulsanti()[xp-1][yp+1].prendiStato()== 1)) {
			//mangiata dx pedina rossa
			System.out.println("Controllo 17");
			mangiaDXA(x,y,xp,yp,state,pulsanti);
			if((state==2 && xp-1==0)) {
				System.out.println("Controllo 18");
				pulsanti[xp-1][yp+1].cambiaStato(4);
				recolor(4,pulsanti[xp-1][yp+1]);
				temp=true;
			}
			xd=xp-1;
			yd=yp+1;
			try {
				System.out.println("Controllo 19");
			if(CanEat(xd,yd,xd-1,yd-1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
				System.out.println("Controllo 20");
			if(CanEat(xd,yd,xd-1,yd+1,campo)) doppio=true;
			} catch (Exception e) {
				
			}
		}
		
		
		if(((state==3 && (y-1 == yp && x+1 == xp) && statep == 2) && (yp-1 >= 0 && xp+1<8))) if((campo.retPulsanti()[xp+1][yp-1].prendiStato()== 1)) {
			//mangiata sx pedina gialla
			System.out.println("non vuoi mangiare1? x:" + x + " y:"+ y + " xp:" + xp + " yp:" + yp + campo);
			
			mangiaSXB(x,y,xp,yp,state,pulsanti);
			if((state==3 && xp+1==7)) {
				System.out.println("Controllo 21");
				pulsanti[xp+1][yp-1].cambiaStato(5);
				recolor(5,pulsanti[xp+1][yp-1]);
				temp2= true;
			}else{
				xd=xp+1;
				yd=yp-1;
				try {
					System.out.println("Controllo 22");
				if(CanEat(xd,yd,xd+1,yd-1,campo)) Mangia(xd,yd,xd+1,yd-1,campo);
				} catch(Exception e) {
					// null
				}
				try{
					System.out.println("Controllo 23");
				if(CanEat(xd,yd,xd+1,yd+1,campo)) Mangia(xd,yd,xd+1,yd+1,campo);
				}
				catch(Exception e) {
					//null
				}
			}
		}
		
		if(((state==3 && (y+1 == yp && x+1 == xp) && statep == 2) && (yp+1 <8 && xp+1<8))) if((campo.retPulsanti()[xp+1][yp+1].prendiStato()== 1)) {
			//mangiata dx pedina gialla
			System.out.println("non vuoi mangiare2? x:" + x + " y:"+ y + " xp:" + xp + " yp:" + yp + campo);
			mangiaDXB(x,y,xp,yp,state,pulsanti);
			if((state==3 && xp+1==7)) {
				System.out.println("Controllo 24");
				pulsanti[xp+1][yp+1].cambiaStato(5);
				recolor(5,pulsanti[xp+1][yp+1]);
				temp2=true;
			}else{
				xd=xp+1;
				yd=yp+1;
				try {	
					System.out.println("Controllo 25");
				if(CanEat(xd,yd,xd+1,yd-1,campo)) Mangia(xd,yd,xd+1,yd-1,campo);
				} catch(Exception e) {
					// null
				}
				try{System.out.println("Controllo 26");
					
				if(CanEat(xd,yd,xd+1,yd+1,campo)) Mangia(xd,yd,xd+1,yd+1,campo);
				} 
				catch(Exception e) {
					//null
				}
			}
		}
		
		if(((!temp && state==4 && (y-1 == yp && x-1 == xp) && (statep == 3 || statep == 5)) && (yp-1 >= 0 && xp-1>=0))) if((campo.retPulsanti()[xp-1][yp-1].prendiStato()== 1)) {
			System.out.println("Controllo 27");
			mangiaSXA(x,y,xp,yp,state,pulsanti);
			xd=xp-1;
			yd=yp-1;
			try {
				System.out.println("Controllo 28");
			if(CanEat(xd,yd,xd-1,yd-1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
				System.out.println("Controllo 29");
			if(CanEat(xd,yd,xd-1,yd+1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
				System.out.println("Controllo 30");
			if(CanEat(xd,yd,xd+1,yd-1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
				System.out.println("Controllo 31");
			if(CanEat(xd,yd,xd+1,yd+1,campo)) doppio=true;
			} catch(Exception e) {
				
			}
		}
		
		if(((!temp && state==4 && (y+1 == yp && x-1 == xp) && (statep == 3 || statep == 5)) && (yp+1 <8 && xp-1>=0))) if((campo.retPulsanti()[xp-1][yp+1].prendiStato()== 1)) {
			System.out.println("Controllo 32");
			mangiaDXA(x,y,xp,yp,state,pulsanti);
			xd=xp-1;
			yd=yp+1;
			try {
				System.out.println("Controllo 33");
			if(CanEat(xd,yd,xd-1,yd-1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
				System.out.println("Controllo 34");
			if(CanEat(xd,yd,xd-1,yd+1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
				System.out.println("Controllo 35");
			if(CanEat(xd,yd,xd+1,yd-1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
				System.out.println("Controllo 36");
			if(CanEat(xd,yd,xd+1,yd+1,campo)) doppio=true;
			} catch(Exception e) {
				
			}
		}
		
		if(((!temp && state==4 && (y-1 == yp && x+1 == xp) && (statep == 3 || statep == 5)) && (yp-1 >= 0 && xp+1<8))) if((campo.retPulsanti()[xp+1][yp-1].prendiStato()== 1)) {
			System.out.println("Controllo 37");
			mangiaSXB(x,y,xp,yp,state,pulsanti);
			xd=xp+1;
			yd=yp-1;
			try {
				System.out.println("Controllo 38");
			if(CanEat(xd,yd,xd-1,yd-1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
				System.out.println("Controllo 39");
			if(CanEat(xd,yd,xd-1,yd+1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
				System.out.println("Controllo 40");
			if(CanEat(xd,yd,xd+1,yd-1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
				System.out.println("Controllo 41");
			if(CanEat(xd,yd,xd+1,yd+1,campo)) doppio=true;
			} catch(Exception e) {
				
			}
		}
		
		if(((!temp && state==4 && (y+1 == yp && x+1 == xp) && (statep == 3 || statep == 5)) && (yp+1 <8 && xp+1<8))) if((campo.retPulsanti()[xp+1][yp+1].prendiStato()== 1)) {
			System.out.println("Controllo 42");
			mangiaDXB(x,y,xp,yp,state,pulsanti);
			xd=xp+1;
			yd=yp+1;
			try {
				System.out.println("Controllo 43");
			if(CanEat(xd,yd,xd-1,yd-1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
				System.out.println("Controllo 44");
			if(CanEat(xd,yd,xd-1,yd+1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
				System.out.println("Controllo 45");
			if(CanEat(xd,yd,xd+1,yd-1,campo)) doppio=true;
			} catch(Exception e) {
				// null
			}
			try{
				System.out.println("Controllo 46");
			if(CanEat(xd,yd,xd+1,yd+1,campo)) doppio=true;
			} catch(Exception e) {
				
			}
		}
		
		if(((!temp2 && state==5 && (y-1 == yp && x-1 == xp) && (statep == 2 || statep == 4)) && (yp-1 >= 0 && xp-1>=0))) if((campo.retPulsanti()[xp-1][yp-1].prendiStato()== 1)) {
			System.out.println("non vuoi mangiare? x:" + x + " y:"+ y + " xp:" + xp + " yp:" + yp + campo);
			mangiaSXA(x,y,xp,yp,state,pulsanti);
			xd=xp-1;
			yd=yp-1;
			try {
				System.out.println("Controllo 47");
			if(CanEat(xd,yd,xd-1,yd-1,campo)) Mangia(xd,yd,xd-1,yd-1,campo);
			} catch(Exception e) {
				// null
			}
			try{
				System.out.println("Controllo 48");
			if(CanEat(xd,yd,xd-1,yd+1,campo)) Mangia(xd,yd,xd-1,yd+1,campo);
			} catch(Exception e) {
				// null
			}
			try{
				System.out.println("Controllo 49");
			if(CanEat(xd,yd,xd+1,yd-1,campo)) Mangia(xd,yd,xd+1,yd-1,campo);
			} catch(Exception e) {
				// null
			}
			try{
				System.out.println("Controllo 50");
			if(CanEat(xd,yd,xd+1,yd+1,campo)) Mangia(xd,yd,xd+1,yd+1,campo);
			} catch(Exception e) {
				
			}
			System.out.println("Sono Qui 1 con doppio:"+doppio);
		}
		
		if(((!temp2 && state==5 && (y+1 == yp && x-1 == xp) && (statep == 2 || statep == 4)) && (yp+1 <8 && xp-1>=0))) if((campo.retPulsanti()[xp-1][yp+1].prendiStato()== 1)){
			System.out.println("non vuoi mangiare? x:" + x + " y:"+ y + " xp:" + xp + " yp:" + yp + campo);
			mangiaDXA(x,y,xp,yp,state,pulsanti);
			xd=xp-1;
			yd=yp+1;
			try {
				System.out.println("Controllo 51");
				if(CanEat(xd,yd,xd-1,yd-1,campo)) Mangia(xd,yd,xd-1,yd-1,campo);
			} catch(Exception e) {
				// null
			}
			try{
				System.out.println("Controllo 52");
				if(CanEat(xd,yd,xd-1,yd+1,campo)) Mangia(xd,yd,xd-1,yd+1,campo);
			} catch(Exception e) {
				// null
			}
			try{
				System.out.println("Controllo 53");
				if(CanEat(xd,yd,xd+1,yd-1,campo)) Mangia(xd,yd,xd+1,yd-1,campo);
			} catch(Exception e) {
				// null
			}
			try{
				System.out.println("Controllo 54");
				if(CanEat(xd,yd,xd+1,yd+1,campo)) Mangia(xd,yd,xd+1,yd+1,campo);
			} catch(Exception e) {
				
			}
			System.out.println("Sono Qui 2 con doppio:"+doppio);
		}
		
		if(((!temp2 && state==5 && (y-1 == yp && x+1 == xp) && (statep == 2 || statep == 4)) && (yp-1 >= 0 && xp+1<8))) if((campo.retPulsanti()[xp+1][yp-1].prendiStato()== 1)){
			System.out.println("non vuoi mangiare? x:" + x + " y:"+ y + " xp:" + xp + " yp:" + yp + campo);
			mangiaSXB(x,y,xp,yp,state,pulsanti);
			xd=xp+1;
			yd=yp-1;
			try {
				System.out.println("Controllo 55");
				if(CanEat(xd,yd,xd-1,yd-1,campo)) Mangia(xd,yd,xd-1,yd-1,campo);
			} catch(Exception e) {
				// null
			}
			try{
				System.out.println("Controllo 56");
				if(CanEat(xd,yd,xd-1,yd+1,campo)) Mangia(xd,yd,xd-1,yd+1,campo);
			} catch(Exception e) {
				// null
			}
			try{
				System.out.println("Controllo 57");
				if(CanEat(xd,yd,xd+1,yd-1,campo)) Mangia(xd,yd,xd+1,yd-1,campo);
			} catch(Exception e) {
				// null
			}
			try{
				System.out.println("Controllo 58");
				if(CanEat(xd,yd,xd+1,yd+1,campo)) Mangia(xd,yd,xd+1,yd+1,campo);
			} catch(Exception e) {
				
			}
			System.out.println("Sono Qui 3 con doppio:"+doppio);
		}
		
		if(((!temp2 && state==5 && (y+1 == yp && x+1 == xp) && (statep == 2 || statep == 4)) && (yp+1 <8 && xp+1<8))) if((campo.retPulsanti()[xp+1][yp+1].prendiStato()== 1)){
			System.out.println("non vuoi mangiare? x:" + x + " y:"+ y + " xp:" + xp + " yp:" + yp + campo);
			mangiaDXB(x,y,xp,yp,state,pulsanti);
			xd=xp+1;
			yd=yp+1;
			try {
				System.out.println("Controllo 59");
				if(CanEat(xd,yd,xd-1,yd-1,campo)) Mangia(xd,yd,xd-1,yd-1,campo);
			} catch(Exception e) {
				// null
			}
			try{
				System.out.println("Controllo 60");
				if(CanEat(xd,yd,xd-1,yd+1,campo)) Mangia(xd,yd,xd-1,yd+1,campo);
			} catch(Exception e) {
				// null
			}
			try{
				System.out.println("Controllo 61");
				if(CanEat(xd,yd,xd+1,yd-1,campo)) Mangia(xd,yd,xd+1,yd-1,campo);
			} catch(Exception e) {
				// null
			}
			try{
				System.out.println("Controllo 62");
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
	
	public void resetta(CampoDiGioco campo) {
		campo.SetX(0);
		campo.SetY(0);
		campo.SetXp(0);
		campo.SetYp(0);
	}
	
	private void mangiaSXA(int x, int y, int xp, int yp, int state, Pulsante pulsanti[][]) {
		System.out.println("Controllo 63");
		pulsanti[xp-1][yp-1].cambiaStato(state);
		pulsanti[x][y].cambiaStato(1);
		pulsanti[xp][yp].cambiaStato(1);
		recolor(state,pulsanti[xp-1][yp-1]);
		recolor(1,pulsanti[x][y]);
		recolor(1,pulsanti[xp][yp]);
	}
	
	private void mangiaDXA(int x, int y, int xp, int yp, int state, Pulsante pulsanti[][]) {
		System.out.println("Controllo 64");
		pulsanti[xp-1][yp+1].cambiaStato(state);
		pulsanti[x][y].cambiaStato(1);
		pulsanti[xp][yp].cambiaStato(1);
		recolor(state,pulsanti[xp-1][yp+1]);
		recolor(1,pulsanti[x][y]);
		recolor(1,pulsanti[xp][yp]);
	}
	
	private void mangiaSXB(int x, int y, int xp, int yp, int state, Pulsante pulsanti[][]) {
		System.out.println("Controllo 65");
		pulsanti[xp+1][yp-1].cambiaStato(state);
		pulsanti[x][y].cambiaStato(1);
		pulsanti[xp][yp].cambiaStato(1);
		recolor(state,pulsanti[xp+1][yp-1]);
		recolor(1,pulsanti[x][y]);
		recolor(1,pulsanti[xp][yp]);
	}
	
	private void mangiaDXB(int x, int y, int xp, int yp, int state, Pulsante pulsanti[][]) {
		System.out.println("Controllo 66");
		pulsanti[xp+1][yp+1].cambiaStato(state);
		pulsanti[x][y].cambiaStato(1);
		pulsanti[xp][yp].cambiaStato(1);
		recolor(state,pulsanti[xp+1][yp+1]);
		recolor(1,pulsanti[x][y]);
		recolor(1,pulsanti[xp][yp]);
	}
	
	private boolean MossaObbligata(int x, int y, int xp, int yp, int state, CampoDiGioco campo) {
		System.out.println("Controllo 67");
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
		    }
		  }
		  return false;
		 }
}
