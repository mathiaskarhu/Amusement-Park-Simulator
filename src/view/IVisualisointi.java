package view;

import simu.model.Asiakas;
import simu.model.TapahtumanTyyppi;

/**
 * Visualisointinäkymän rajapinta
 * @version 1.0
 * @author Santeri, Mathias, Ilkka
 *
 */

public interface IVisualisointi {

	public void uusiAsiakas(TapahtumanTyyppi tyyppi, Asiakas a);
	public void poistaAsiakas(Asiakas a);
	public void naytaKaynnit(TapahtumanTyyppi tyyppi);
}