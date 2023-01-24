package view;

/**
 * Käyttöliittymänäkymän rajapinta
 * @version 1.0
 * @author Santeri, Mathias, Ilkka
 *
 */

public interface ISimulaattorinUI {
	
	// Kontrolleri tarvitsee syötteitä, jotka se välittää Moottorille
	public double getAika();
	public long getViive();
	public int getI();
	public int getJ();
	
	//Kontrolleri antaa käyttöliittymälle tuloksia, joita Moottori tuottaa 
	public void setLoppuaika(double aika);
	
	// Kontrolleri tarvitsee  
	public IVisualisointi getVisualisointi();

}