package ita.univr.elaborato.controller;

import ita.univr.elaborato.model.Controllo;
import ita.univr.elaborato.view.CampoDiGioco;

public class ObbligoMangiata {
	private CampoDiGioco campo;
	private Controllo Computer;
	private boolean ritorno;
	int[][] eat= new int[8][8];
	
	
	
	public ObbligoMangiata(CampoDiGioco campo){
		this.campo=campo;
		int counter=0;
		ritorno=false;
		
		int StateX = campo.PrendiX();
		int StateY = campo.PrendiY();
		
		for (int c=0; c<8; c++)
			for (int i=0; i<8; i++)
					if((c+i)%2 == 0){
						eat[c][i]=EatCoder(c, i);
						if(eat[c][i]>0)
							counter++;
					}
		if (counter == 0)
			ritorno = true;
		
		settaS(StateX,StateY);
		
	}
	
	public boolean retObbligo(){
		return ritorno;
	}
	
	public int retEat(int x, int y){
		return eat[x][y];
	}

	private int EatCoder(int x,int y) {

		settaS(x,y);
		
		if(x>=0 && x<6 && campo.PrendiS() == 4){
			if( y>2 && y<8){
				settaS(x+1,y-1);
				settaSp(x+2,y-2);
				if((campo.PrendiS()== 3 || campo.PrendiS() == 5 ) && campo.PrendiSp() == 1)
					return 1;
			}
			if(y>=0 && y<6){
				settaS(x+1,y+1);
				settaSp(x+2,y+2);
				if((campo.PrendiS()== 3 || campo.PrendiS() == 5 ) && campo.PrendiSp() == 1)
					return 1;
			}
		}
		
		if(x>1 && x<8 && campo.PrendiS() == 4 || campo.PrendiS() == 2){
			if(y>2 && y<8){
				int S = campo.PrendiS();
				settaS(x-1,y-1);
				settaSp(x-2,y-2);
				if(campo.PrendiS()== 3 && S == 2 && campo.PrendiSp() == 1)
					return 1;
				if(campo.PrendiS()== 5 && S == 4 && campo.PrendiSp() == 1)
					return 1;
			}
			if(y>=0 && y<6){
				int S = campo.PrendiS();
				settaS(x-1,y+1);
				settaSp(x-2,y+2);
				if(campo.PrendiS()== 3 && S == 2 && campo.PrendiSp() == 1)
					return 1;
				if(campo.PrendiS()== 5 && S == 4 && campo.PrendiSp() == 1)
					return 1;
			}
		}
		return 0;			
	}
	
	private void settaS(int x, int y) {
		campo.SetX(x);
		campo.SetY(y);
		campo.SetS(campo.toStringState(x, y));
	}
	
	private void settaSp(int x, int y) {
		campo.SetX(x);
		campo.SetY(y);
		campo.SetS(campo.toStringState(x, y));
	}
}
