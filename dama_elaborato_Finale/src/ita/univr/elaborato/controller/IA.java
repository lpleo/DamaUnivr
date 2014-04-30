package ita.univr.elaborato.controller;

import java.util.Random;

import ita.univr.elaborato.model.Controllo;
import ita.univr.elaborato.view.CampoDiGioco;
import ita.univr.elaborato.view.Pulsante;

public class IA {
	private CampoDiGioco Campo;
	private CampoDiGioco VirtualCampo;
	int [][] peso =  new int [8][8];
	Controllo Computer = new Controllo();
		
	public IA(CampoDiGioco campo){
		this.Campo=campo;
		this.VirtualCampo=campo;
		int[][] pesata=new int[8][8];
		Random r= new Random();
		int max=0 ,Xmax = -1,Ymax = -1,n=r.nextInt(2);
		for (int c=0; c<8; c++)
			for (int i=0; i<8; i++)
					if((c+i)%2 == 0){
						pesata [c][i]+= EatCoder(c,i,9,9,9,9,VirtualCampo);		// assegno i pesi per la mangiata
						if (pesata [c][i] > max){						// trovo il peso massimo
							max = pesata[c][i];
							Xmax = c;
							Ymax = i;
						}
					}
		
		for (int c=0; c<8; c++){
			for (int i=0; i<8; i++)
				System.out.println(peso[c][i] + " ");
			System.out.println("\n");
		}
		
		if ( max == 0 ){
			for (int c=0; c<8; c++)
				for (int i=0; i<8; i++)
					if((c+i)%2==0){
						peso[c][i] = MoveCoder(c,i,false);					// assegno i pesi per la mossa
						if (peso [c][i] > max){						// trovo il peso massimo
							max = peso[c][i];
							Xmax = c;
							Ymax = i;
						}else
							if(peso[c][i] == max && (n=r.nextInt(2) %2) == 0){
								Xmax = c;
								Ymax = i;
							}
					}
			boolean IA_hai_perso;
			if (max == 0)
				IA_hai_perso = true;	// ha perso l'IA
			else{
				muovi(Xmax,Ymax);
				System.out.println("e qui?");
			}
		}else{
			System.out.println("che cosa succede qua? x:" + Xmax + " y:" + Ymax);
			mangia(Xmax, Ymax);	
		}
	}
	
	private CampoDiGioco settaS(int x, int y,CampoDiGioco Nuovo) {
		Nuovo.SetX(x);
		Nuovo.SetY(y);
		Nuovo.SetS(Nuovo.toStringState(x, y));
		return Nuovo;
	}
	
	private  CampoDiGioco settaSp(int x, int y,CampoDiGioco Nuovo) {
		Nuovo.SetXp(x);
		Nuovo.SetYp(y);
		Nuovo.SetSp(Nuovo.toStringState(x, y));
		return Nuovo;
	}

	public CampoDiGioco retCampo(){
		return this.Campo;
	}

	private int MoveCoder(int x, int y, boolean rico) {		
		VirtualCampo=settaS(x,y,VirtualCampo);			// l'IA seleziona questa pedina
	
		// studio i casi della dama verso l'alto
		if ( VirtualCampo.PrendiS() == 5 ){	
			if (x-1 >=0 && y-1>=0){
				VirtualCampo=settaSp(x-1,y-1,VirtualCampo);
				if ( VirtualCampo.PrendiSp() == 1){		// se a Asx è vuoto incremento il peso
					if(!rico)
						peso[x][y]+=7 + MoveCoder(x-1,y-1,true);
					else
						peso[x][y]+=7;
					}
				if (rico && VirtualCampo.PrendiSp() == 4)
					peso[x][y]= (peso[x][y]/4)+2;
			}
			if (x-1>=0 && y+1<8){
				VirtualCampo=settaSp(x-1,y+1,VirtualCampo);
				if ( VirtualCampo.PrendiSp() == 1){		// se a Adx è vuoto incremento il peso
					if(!rico)
						peso[x][y]+= 7+ MoveCoder(x-1,y+1,true);
					else
						peso[x][y]+=7;
				}
			}
			if (x<7 && y-1>=0){
				VirtualCampo=settaSp(x+1,y-1,VirtualCampo);
				if ( VirtualCampo.PrendiSp() == 1){		// se a Bsx è vuoto incremento il peso
					if(!rico)
						peso[x][y]+=7 + MoveCoder(x+1,y-1,true);
					else
						peso[x][y]+=7;
					}
				if (rico && VirtualCampo.PrendiSp() == 4)
					peso[x][y]= (peso[x][y]/4)+2;
			}
			if (x<7 && y+1<8){
				VirtualCampo=settaSp(x+1,y+1,VirtualCampo);
				if ( VirtualCampo.PrendiSp() == 1){		// se a dx è vuoto incremento il peso
					if(!rico)
						peso[x][y]+= 7+ MoveCoder(x+1,y+1,true);
					else
						peso[x][y]+=7;
				}
			}
			if (VirtualCampo.PrendiSp() == 4){
				VirtualCampo=settaSp(x+1,y+1,VirtualCampo);
				if(VirtualCampo.PrendiSp() != 1)
					peso[x][y]+=10;
				else
					peso[x][y]-=10;
			}
		}
		// studio i casi della dama e della pedina verso il basso
		if(VirtualCampo.PrendiS()==3){
			if(x+1<8 && y-1>=0){
				VirtualCampo=settaSp(x+1,y-1,VirtualCampo);
				if (VirtualCampo.PrendiSp() == 1){			// se a sx è vuoto incremento il peso
					if(!rico)
						peso[x][y]+= 5 + MoveCoder(x+1,y-1,true);
					else
						peso[x][y]+=5;
					if(!rico && VirtualCampo.PrendiS() == 3 && x==6)
						peso[x][y]+=50;
				}
				/*if (rico && VirtualCampo.PrendiSp() == 2 || VirtualCampo.PrendiSp() == 4)
					peso[x][y]= (peso[x][y]/2)+2;*/
			}
			if(x+1<8 && y+1<8){
				VirtualCampo=settaSp(x+1,y+1,VirtualCampo);
				if (VirtualCampo.PrendiSp() == 1){			// se a dx è vuoto incremento il peso
					if(!rico)
						peso[x][y]+= 5 + MoveCoder(x+1,y+1,true);
					else
						peso[x][y]+=5;
					if(!rico && VirtualCampo.PrendiS() == 3 && x==6)
						peso[x][y]+=50;
				}
				/*if (rico && VirtualCampo.PrendiSp() == 2 || VirtualCampo.PrendiSp() == 4)
					peso[x][y]= (peso[x][y]/2)+2;*/
			}
			
			
				
				
			peso[x][y]+=0;
		}
		
		
		if(!rico)
			return peso[x][y];		// ritorno il peso calcolato
		else{
			int temp = peso[x][y];
			peso[x][y]=0;
			return temp;
		}
	}



	private int EatCoder(int x, int y,int s, int xp, int yp, int sp,CampoDiGioco Nuovo) {
		CampoDiGioco Temp = Nuovo;
		Temp=settaS(x,y,Temp);		// l'IA seleziona questa pedina
		boolean t=false;
		
		
		String pesi="";
		for (int c=0; c<8; c++){
			for (int i=0; i<8; i++)
				pesi+= peso[c][i]+" ";
			pesi+="\n";
		}
		System.out.println(pesi);
		
		String pesi2="";
		for (int c=0; c<8; c++){
			for (int i=0; i<8; i++)
				pesi2+= peso[c][i]+" ";
			pesi2+="\n";
		}
		System.out.println(pesi2);
		
		if (s != 9 ){			// se sono stati inizializzati allora li salvo
			Temp.SetS(s);	
			Temp=settaSp(xp,yp,Temp);
			Temp.SetSp(sp);
			t=true;
			//System.out.println("Controllo 1-> X:"+ x + " Y:"+y+" peso:"+peso[x][y]);
		}
		System.out.println("Controllo 2-> X:"+ x + " Y:"+y+" peso:"+peso[x][y]);
		// studio i casi di mangiata della dama verso l'alto
		if( Temp.PrendiS() == 5){	
			System.out.println("Controllo 3-> X:"+ x + " Y:"+y+" peso:"+peso[x][y]);
			if(x-2>=0 && y-2>=0){	
				System.out.println("Controllo 4-> X:"+ x + " Y:"+y+" peso:"+peso[x][y]);
				Temp=settaSp(x-1,y-1,Temp);		// prendo come Next la casella a sx
				// controllo se posso mangiare in alto a sx per la dama
				if (Computer.CanEat( Temp.PrendiX() , Temp.PrendiY() , Temp.PrendiXp() , Temp.PrendiYp() , Temp) ){				
					System.out.println("Controllo 5-> X:"+ x + " Y:"+y+" peso:"+peso[x][y]);
					peso[x][y] += 100 + EatCoder(x-2,y-2,Temp.PrendiS(),x-1,y-1,1,Temp);
				}
			}
			
			if(x-2>=0 && y+2<8){
				System.out.println("Controllo 6-> X:"+ x + " Y:"+y+" peso:"+peso[x][y]);
				Temp=settaSp(x-1,y+1,Temp);		// prendo come Next la casella a dx
				// controllo se posso mangiare in alto a dx per la dama
				if (Temp.PrendiS() == 5 && Computer.CanEat(Temp.PrendiX(),Temp.PrendiY(),Temp.PrendiXp(),Temp.PrendiYp(),Temp) ){
					System.out.println("Controllo 7-> X:"+ x + " Y:"+y+" peso:"+peso[x][y]);
					peso[x][y] += 100 + EatCoder(x-2,y+2,Temp.PrendiS(),x-1,y+1,1,Temp);
				}
			}
		}		
		
		// studio i casi di mangiata per la pedina e per la dama verso il basso
		if(x+2<8 && y-2>=0){	
			System.out.println("Controllo 8-> X:"+ x + " Y:"+y+" peso:"+peso[x][y]);
			Temp=settaSp(x+1,y-1,Temp);		// prendo come Next la casella a sx
			// controllo se posso mangiare in basso a sx per la pedina
			if (Temp.PrendiS() == 3 && Computer.CanEat(Temp.PrendiX(),Temp.PrendiY(),Temp.PrendiXp(),Temp.PrendiYp(),Temp) ){
				System.out.println("Controllo 9-> X:"+ x + " Y:"+y+" peso:"+peso[x][y]);
				peso[x][y] += 10 + EatCoder(x+2,y-2,Temp.PrendiS(),x+1,y-1,1,Temp);
			}
			
			// controllo se posso mangiare in basso a sx per la dama
			if (Temp.PrendiS() == 5 && Computer.CanEat(Temp.PrendiX(),Temp.PrendiY(),Temp.PrendiXp(),Temp.PrendiYp(),Temp) ){
				System.out.println("Controllo 10-> X:"+ x + " Y:"+y+" peso:"+peso[x][y]);
				peso[x][y] += 100 + EatCoder(x+2,y-2,Temp.PrendiS(),x+1,y-1,1,Temp);
			}
		}
				
		if(x+2<8 && y+2<8){	
			System.out.println("Controllo 11-> X:"+ x + " Y:"+y+" peso:"+peso[x][y]);
			Temp=settaSp(x+1,y+1,Temp);		// prendo come Next la casella a dx
			// controllo se posso mangiare in basso a dx per la pedina
			if (Temp.PrendiS() == 3 && Computer.CanEat(Temp.PrendiX(),Temp.PrendiY(),Temp.PrendiXp(),Temp.PrendiYp(),Temp) ){
				System.out.println("Controllo 12-> X:"+ x + " Y:"+y+" peso:"+peso[x][y]);
				peso[x][y] += 10 + EatCoder(x+2,y+2,Temp.PrendiS(),x+1,y+1,1,Temp);
			}
	
			// controllo se posso mangiare in basso a dx per la dama
			if (Temp.PrendiS() == 5 && Computer.CanEat(Temp.PrendiX(),Temp.PrendiY(),Temp.PrendiXp(),Temp.PrendiYp(),Temp) ){
				System.out.println("Controllo 13-> X:"+ x + " Y:"+y+" peso:"+peso[x][y]);
				peso[x][y] += 100 + EatCoder(x+2,y+2,Temp.PrendiS(),x+1,y+1,1,Temp);
			}
		}
		System.out.println("Sono qui.. P:"+peso[x][y]+" T:"+t);
		if(t && peso[x][y]==0){
			peso[x][y]=1;
			System.out.println("Sono anche qui.. P:"+peso[x][y]+" x:"+x+" y"+y);
		}
		
		String pesi3="------------------------------\n";
		for (int c=0; c<8; c++){
			for (int i=0; i<8; i++)
				pesi3+= peso[c][i]+" ";
			pesi3+="\n";
		}
		System.out.println(pesi3);
		
		return peso[x][y];
	}
	
private void mangia(int x, int y) {
		Campo=settaS(x,y,Campo);		// setto la pedina
		
		//if (x>1)	
		String pesi="";
		for (int c=0; c<8; c++){
			for (int i=0; i<8; i++)
				pesi+= peso[c][i]+" ";
			pesi+="\n";
		}
		System.out.println(pesi);
		
		
		if((x>1 && y>1) && (x<6 && y<6)){	
			System.out.println("Punto 1:" + peso[x][y]);
			if(Campo.PrendiS() == 5 ){										// tutti i casi della dama
				System.out.println("Punto 2:" + peso[x][y]);
				if (peso[x-2][y-2]>peso[x-2][y+2] ){
					System.out.println("Punto 3:" + peso[x][y]);
					if (peso[x-2][y-2]>peso[x+2][y-2]){
						System.out.println("Punto 4:" + peso[x][y]);
						if(peso[x-2][y-2]>peso[x+2][y+2]){
							System.out.println("Punto 5:" + peso[x][y]);
							Campo=settaS(x,y,Campo);
							Campo=settaSp(x-1,y-1,Campo);
							if(Computer.CanEat(x, y, x-1, y-1, Campo)){
								System.out.println("Punto 6:" + peso[x][y]);
								Computer.Mangia(x, y, x -1, y-1 , Campo);
							}
						}else{
							System.out.println("Punto 7:" + peso[x][y]);
							Campo=settaS(x,y,Campo);
							Campo=settaSp(x+1,y+1,Campo);
							if(Computer.CanEat(x, y, x+1, y+1, Campo)){
								System.out.println("Punto 8:" + peso[x][y]);
								Computer.Mangia(x, y, x +1, y+1 , Campo);
							}
						}
					}else{
						System.out.println("Punto 9:" + peso[x][y]);
						if (peso[x+2][y-2]>peso[x+2][y+2]){
							System.out.println("Punto 10:" + peso[x][y]);
							Campo=settaS(x,y,Campo);
							Campo=settaSp(x+1,y-1,Campo);
							if(Computer.CanEat(x, y, x+1, y-1, Campo)){
								System.out.println("Punto 11:" + peso[x][y]);
								Computer.Mangia(x, y, x +1, y-1 , Campo);
							}
						}else{
							System.out.println("Punto 12:" + peso[x][y]);
							Campo=settaS(x,y,Campo);
							Campo=settaSp(x+1,y-1,Campo);
							if(Computer.CanEat(x, y, x+1, y+1, Campo)){
								System.out.println("Punto 13:" + peso[x][y]);
								Computer.Mangia(x, y, x +1, y+1 , Campo);
							}
						}
					}
				}else{
					System.out.println("Punto 14:" + peso[x][y]);
					if (peso[x-2][y+2]>peso[x+2][y-2]){
						System.out.println("Punto 15:" + peso[x][y]);
						if (peso[x-2][y+2]>	peso[x+2][y+2]){
							System.out.println("Punto 16:" + peso[x][y]);
							Campo=settaS(x,y,Campo);
							Campo=settaSp(x-1,y+1,Campo);
							if(Computer.CanEat(x, y, x-1, y+1, Campo)){
								System.out.println("Punto 17:" + peso[x][y]);
								Computer.Mangia(x, y, x -1, y+1 , Campo);
							}
						}else{
							System.out.println("Punto 18:" + peso[x][y]);
							Campo=settaS(x,y,Campo);
							Campo=settaSp(x+1,y+1,Campo);
							if(Computer.CanEat(x, y, x+1, y+1, Campo)){
								System.out.println("Punto 19:" + peso[x][y]);
								Computer.Mangia(x, y, x +1, y+1 , Campo);
							}
						}
					}else{
						System.out.println("Punto 20:" + peso[x][y]);
						if (peso[x+2][y-2]>peso[x+2][y+2]){
							System.out.println("Punto 21:" + peso[x][y]);
							Campo=settaS(x,y,Campo);
							Campo=settaSp(x+1,y-1,Campo);
							if(Computer.CanEat(x, y, x+1, y-1, Campo)){
								System.out.println("Punto 22:" + peso[x][y]);
								Computer.Mangia(x, y, x +1, y-1 , Campo);
							}
						}else{
							System.out.println("Punto 23:" + peso[x][y]);
							Campo=settaS(x,y,Campo);
							Campo=settaSp(x+1,y+1,Campo);
							if(Computer.CanEat(x, y, x+1, y+1, Campo)){
								System.out.println("Punto 24:" + peso[x][y]);
								Computer.Mangia(x, y, x +1, y+1 , Campo);
							}
						}
					}
				}
			}else{
				System.out.println("Punto 25:" + peso[x][y]);
				if(peso[x+2][y-2]>peso[x+2][y+2]){
					System.out.println("Punto 26:" + peso[x][y]);
					Campo=settaS(x,y,Campo);
					Campo=settaSp(x+1,y-1,Campo);
					if(Computer.CanEat(x, y, x+1, y-1, Campo)){
						System.out.println("Punto 27:" + peso[x][y]);
						Computer.Mangia(x, y, x +1, y-1 , Campo);
					}
				}else{
					System.out.println("Punto 28:" + peso[x][y]+ " Sx:"+ peso[x+2][y-2] + " Dx" + peso[x+2][y+2]); /// qui si pianta con lo scema posto qui sotto
					Campo=settaS(x,y,Campo);
					Campo=settaSp(x+1,y+1,Campo);
					if(Computer.CanEat(x,y,x+1,y+1, Campo)){
						System.out.println("Punto 29:" + peso[x][y]);
						Computer.Mangia(x, y, x +1, y+1 , Campo);
					}
				}
			}
		}else{
			System.out.println("Punto 30:" + peso[x][y]);
			if(y<2){
				System.out.println("Punto 31:" + peso[x][y]);
				if(x<2){
					System.out.println("Punto 32:" + peso[x][y]);
					Campo=settaS(x,y,Campo);
					Campo=settaSp(x+1,y+1,Campo);
					if (Computer.CanEat(x, y, x+1, y+1, Campo)){		//mangio a dx in basso
						System.out.println("Punto 33:" + peso[x][y]);
						Computer.Mangia(x, y, x +1, y+1 , Campo);
					}
				}
				if(x>1 && x<6){
					System.out.println("Punto 34:" + peso[x][y]);
					if(peso[x-2][y+2]>peso[x+2][y+2] && Campo.PrendiS() == 5 ){
						System.out.println("Punto 35:" + peso[x][y]);
						Campo=settaS(x,y,Campo);
						Campo=settaSp(x-1,y+1,Campo);
						if (Computer.CanEat(x, y, x-1, y+1, Campo)){
							System.out.println("Punto 36:" + peso[x][y]);
							Computer.Mangia(x, y, x-1, y+1 , Campo);
						}
					}else{
						System.out.println("Punto 37:" + peso[x][y]);
						Campo=settaS(x,y,Campo);
						Campo=settaSp(x+1,y+1,Campo);
						if (Computer.CanEat(x, y, x+1, y+1, Campo)){
							System.out.println("Punto 38:" + peso[x][y]);
							Computer.Mangia(x, y, x+1, y+1 , Campo);
						}
					}
				}
				if (x>5){
					System.out.println("Punto 39:" + peso[x][y]);
					Campo=settaS(x,y,Campo);
					Campo=settaSp(x-1,y+1,Campo);
					if (Computer.CanEat(x, y, x-1, y+1, Campo)){
						System.out.println("Punto 40:" + peso[x][y]);
						Computer.Mangia(x, y, x-1, y+1 , Campo);
					}
				}
			}
			if(y>1 && y<6){
				System.out.println("Punto 41:" + peso[x][y]);
				if(x<2){
					System.out.println("Punto 42:" + peso[x][y]);
					if(peso[x+2][y-2]>peso[x+2][y+2]){
						System.out.println("Punto 43:" + peso[x][y]);
						Campo=settaS(x,y,Campo);
						Campo=settaSp(x+1,y-1,Campo);
						if (Computer.CanEat(x, y, x+1, y-1, Campo)){
							System.out.println("Punto 44:" + peso[x][y]);
							Computer.Mangia(x, y, x+1, y-1 , Campo);
						}
					}else{
						System.out.println("Punto 45:" + peso[x][y]);
						Campo=settaS(x,y,Campo);
						Campo=settaSp(x+1,y+1,Campo);
						if (Computer.CanEat(x, y, x+1, y+1, Campo)){
							System.out.println("Punto 46:" + peso[x][y]);
							Computer.Mangia(x, y, x+1, y+1 , Campo);
						}
					}
				}
				if(x>5 && Campo.PrendiS()==5){
					System.out.println("Punto 47:" + peso[x][y]);
					if(peso[x-2][y-2]>peso[x-2][y+2]){
						System.out.println("Punto 48:" + peso[x][y]);
						Campo=settaS(x,y,Campo);
						Campo=settaSp(x-1,y-1,Campo);
						if (Computer.CanEat(x, y, x-1, y-1, Campo)){
							System.out.println("Punto 49:" + peso[x][y]);
							Computer.Mangia(x, y, x-1, y-1 , Campo);
						}
					}else{
						System.out.println("Punto 50:" + peso[x][y]);
						Campo=settaS(x,y,Campo);
						Campo=settaSp(x-1,y+1,Campo);
						if (Computer.CanEat(x, y, x-1, y+1, Campo)){
							System.out.println("Punto 51:" + peso[x][y]);
							Computer.Mangia(x, y, x-1, y+1 , Campo);
						}
					}
				}
			}
			if(y>5){
				System.out.println("Punto 52:" + peso[x][y]);
				if(x<2){
					Campo=settaS(x,y,Campo);
					Campo=settaSp(x+1,y-1,Campo);
					if (Computer.CanEat(x, y, x+1, y-1, Campo)){
						System.out.println("Punto 53:" + peso[x][y]);
						Computer.Mangia(x, y, x +1, y-1 , Campo);
					}
				}
				if(x>1 && x<6){
					System.out.println("Punto 54:" + peso[x][y]);
					if(peso[x-2][y-2]>peso[x+2][y-2] && Campo.PrendiS() == 5 ){
						System.out.println("Punto 55:" + peso[x][y]);
						Campo=settaS(x,y,Campo);
						Campo=settaSp(x-1,y-1,Campo);
						if (Computer.CanEat(x, y, x-1, y-1, Campo)){
							System.out.println("Punto 56:" + peso[x][y]);
							Computer.Mangia(x, y, x-1, y-1 , Campo);
						}
					}else{						
						System.out.println("Punto 57:" + peso[x][y]);
						Campo=settaS(x,y,Campo);
						Campo=settaSp(x+1,y-1,Campo);
						if (Computer.CanEat(x, y, x+1, y-1, Campo)){
							System.out.println("Punto 58:" + peso[x][y]);
							Computer.Mangia(x, y, x+1, y-1 , Campo);
						}
					}
				}
				if (x>5){
					System.out.println("Punto 59:" + peso[x][y]);
					Campo=settaS(x,y,Campo);
					Campo=settaSp(x-1,y-1,Campo);
					if (Computer.CanEat(x, y, x-1, y-1, Campo)){
						System.out.println("Punto 60:" + peso[x][y]);
						Computer.Mangia(x, y, x-1, y-1 , Campo);
					}
				}
			}
		}
			
		/*if((x>1 && y>1) ||(x<6 && y<6)){
			if(peso[x+2][y-2]>peso[x+2][y+2] && Computer.CanEat(x, y, x+1, y-1, Campo))					// tutti i casi della pedina
				Computer.Mangia(x, y, x +1, y-1 , Campo);
			else
				if(Computer.CanEat(x, y, x+1, y+1, Campo))
					Computer.Mangia(x, y, x +1, y+1 , Campo);
		}*/
		System.out.println("Punto di fine!");
	}

	private void muovi(int xmax, int ymax){
		Campo=settaS(xmax,ymax,Campo);
		
		Random random = new Random();
		int n = random.nextInt(4);
		boolean exit= false;
		
		System.out.println("ci sono arrivato?\n" + Campo.PrendiX() + " " + Campo.PrendiY() + " " + Campo.PrendiS()+" P:"+peso[xmax][ymax]);
		// controllo in modo casuale quale casella mi aveva incrementato il contatore e, una volta trovata, sposto lì la pedina
		do{
			System.out.println("ci arrivo? S:" + Campo.PrendiS() + " Sp:" + Campo.PrendiSp() + " n:" + n + " xmax:"+xmax+" ymax:"+ymax);
			if (Campo.PrendiS() == 5 && n == 0 && xmax>0 && ymax>0){
				System.out.println("eccomi 1! S:" + Campo.PrendiS() + " Sp:" + Campo.PrendiSp());
				Campo=settaSp(xmax-1,ymax-1,Campo);
				if (Campo.PrendiSp() == 1){
					Computer.Muovi(xmax, ymax, xmax-1, ymax-1, Campo, true);
					exit = true;
				}	
			}
			if (Campo.PrendiS() == 5 && n == 1 && xmax>0 && ymax<7){
				System.out.println("eccomi 2! S:" + Campo.PrendiS() + " Sp:" + Campo.PrendiSp());
				Campo=settaSp(xmax-1,ymax+1,Campo);
				if (Campo.PrendiSp() == 1){
					Computer.Muovi(xmax, ymax, xmax-1, ymax+1, Campo, true);
					exit = true;
				}	
			}
			if (n == 2 && xmax<7 && ymax>0){
				System.out.println("eccomi 3! S:" + Campo.PrendiS() + " Sp:" + Campo.PrendiSp());
				Campo=settaSp(xmax+1,ymax-1,Campo);
				if (Campo.PrendiSp() == 1){
					Computer.Muovi(xmax, ymax, xmax+1, ymax-1, Campo, true);
					exit = true;
				}					
			}
			if (n == 3 && xmax<7 && ymax<7){
				System.out.println("eccomi 4! S:" + Campo.PrendiS() + " Sp:" + Campo.PrendiSp());
				Campo=settaSp(xmax+1,ymax+1,Campo);
				if (Campo.PrendiSp() == 1){
					Computer.Muovi(xmax, ymax, xmax+1, ymax+1, Campo, true);
					exit = true;
				}	
			}
			n = random.nextInt(4);
		}while (!exit);
	}	
}