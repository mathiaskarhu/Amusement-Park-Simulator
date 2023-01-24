package controller;

import javafx.application.Platform;
import simu.framework.IMoottori;
import simu.model.Asiakas;
import simu.model.OmaMoottori;
import simu.model.Palvelupiste;
import simu.model.TapahtumanTyyppi;
import view.ISimulaattorinUI;
import view.IVisualisointi;

/**
 * Kontrolleri käynnistää simulaattorin ja ohjaa Moottoria
 * @version 1.0
 * @author Santeri, Mathias, Ilkka
 *
 */

public class Kontrolleri implements IKontrolleri{   // UUSI
	
	private IMoottori moottori; 
	private ISimulaattorinUI ui;
	
	public Kontrolleri(ISimulaattorinUI ui) {
		this.ui = ui;
	}

	
	// Moottorin ohjausta:
		
	@Override
	public void kaynnistaSimulointi() {
		moottori = new OmaMoottori(this); // luodaan uusi moottorisäie jokaista simulointia varten
		moottori.setSimulointiaika(ui.getAika());
		moottori.setViive(ui.getViive());
		//Asetetaan jakauman parametrit
		moottori.setI(ui.getI());
		moottori.setJ(ui.getJ());
		((Thread)moottori).start();
		
	}
	//Palauttaa käyttöliittymässä syötetyn jakauman keskiarvon
	public int getI() {
		return ui.getI();
	}
	//Palauttaa käyttöliittymässä syötetyn jakauman varianssin
	public int getJ() {
		return ui.getJ();
	}
	
	@Override
	public void hidasta() { // hidastetaan moottorisäiettä
		moottori.setViive((long)(moottori.getViive()*1.10));
	}

	@Override
	public void nopeuta() { // nopeutetaan moottorisäiettä
		moottori.setViive((long)(moottori.getViive()*0.9));
	}
	
	
	
	// Simulointitulosten välittämistä käyttöliittymään.
	// Koska FX-ui:n päivitykset tulevat moottorisäikeestä, ne pitää ohjata JavaFX-säikeeseen:
		
	@Override
	public void naytaLoppuaika(double aika) {
		Platform.runLater(()->ui.setLoppuaika(aika)); 
	}

	//Luodaan asiakkaan visuaalinen esitys palvelupisteelle
	@Override
	public void visualisoiAsiakas(TapahtumanTyyppi tyyppi, Asiakas a) {
		Platform.runLater(new Runnable(){
			public void run(){
				ui.getVisualisointi().uusiAsiakas(tyyppi, a);
			}
		});
	}
	
	//Poistetaan asiakkaan visuaalinen esitys palvelupisteeltä
	@Override
	public void poistaAsiakas(Asiakas a) {
	
		Platform.runLater(new Runnable(){
			public void run(){
				ui.getVisualisointi().poistaAsiakas(a);
			}
		});
	}
}