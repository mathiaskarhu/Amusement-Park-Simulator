package controller;

import simu.model.Asiakas;
import simu.model.TapahtumanTyyppi;

/**
 * Rajapinta käyttöliittymälle
 * @version 1.0
 * @author Santeri, Mathias, Ilkka
 */

public interface IKontrolleri {
	
		// Rajapinta, joka tarjotaan  käyttöliittymälle:
	
		public void kaynnistaSimulointi();
		public void nopeuta();
		public void hidasta();
		public int getI();
		public int getJ();
		
		// Rajapinta, joka tarjotaan moottorille:
		
		public void naytaLoppuaika(double aika);
		public void visualisoiAsiakas(TapahtumanTyyppi tyyppi, Asiakas a);
		public void poistaAsiakas(Asiakas a);

}