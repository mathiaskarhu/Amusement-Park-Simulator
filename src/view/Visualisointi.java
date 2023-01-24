package view;

import java.util.HashMap;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import simu.framework.Trace;
import simu.model.Asiakas;
import simu.model.Suorituskykysuureet;
import simu.model.TapahtumanTyyppi;

/**
 * Simulaattorin toiminnan graafinen esittäminen.
 * @version 1.0
 * @author Santeri, Mathias, Ilkka
 *
 */

public class Visualisointi extends Pane implements IVisualisointi {

	private Rectangle rectangle;
	private Text text;
	private static HashMap<TapahtumanTyyppi, PisteHandler> pisteet = new HashMap<>();
	private HashMap<Asiakas, Circle> circle = new HashMap<>();
	private HashMap<TapahtumanTyyppi, Text> kaynnit = new HashMap<>();
	private Suorituskykysuureet suorituskykysuureet = new Suorituskykysuureet();
	
	public Visualisointi(int w, int h) {
		this.setPrefSize(w, h);
		muodostaPiste(275, 225, 250, 150, "ffffff", TapahtumanTyyppi.HENGAILU);
		muodostaPiste(50, 100, 100, 50, "F39C12", TapahtumanTyyppi.LIPPUPISTE);
		muodostaPiste(250, 50, 100, 50, "FF5733", TapahtumanTyyppi.KARUSELLI);
		muodostaPiste(450, 50, 100, 50, "3CB57D", TapahtumanTyyppi.VUORISTORATA);
		muodostaPiste(650, 100, 100, 50, "CCCCFF", TapahtumanTyyppi.MAAILMANPYÖRÄ);
		muodostaPiste(650, 450, 100, 50, "48C9B0", TapahtumanTyyppi.SEALIFE);
		muodostaPiste(450, 500, 100, 50, "AF7AC5", TapahtumanTyyppi.RAVINTOLA);
		muodostaPiste(250, 500, 100, 50, "FFC300", TapahtumanTyyppi.KIOSKI);
		muodostaPiste(50, 450, 100, 50, "6495ED", TapahtumanTyyppi.WC);
	}

	//Luodaan palvelupisteen visuaalinen esitys
	private void muodostaPiste(int x, int y, int sizeX, int sizeY, String color, TapahtumanTyyppi tyyppi) {
		pisteet.put(tyyppi, new PisteHandler(x, y, sizeX, sizeY, color));
		kaynnit.put(tyyppi, new Text(x, (y+sizeY+15), "Kaynnit: "));
		rectangle = pisteet.get(tyyppi).getRectangle();
		text = kaynnit.get(tyyppi);
		this.getChildren().add(new Text(x, (y-5), tyyppi.toString()));
		this.getChildren().add(rectangle);
		this.getChildren().add(text);
		Trace.out(Trace.Level.INFO, "Piste " + tyyppi.toString() + " " + pisteet.get(tyyppi).getX() + ", " + pisteet.get(tyyppi).getSizeY() + " luotu");
		Trace.out(Trace.Level.INFO, "" + TapahtumanTyyppi.KARUSELLI.vaatiiLipun(TapahtumanTyyppi.VUORISTORATA));
	}

	//Luodaan yksittäinen visuaalinen asiakasobjekti
	public void uusiAsiakas(TapahtumanTyyppi tyyppi, Asiakas a) {
		circle.put(a, pisteet.get(tyyppi).luoCircle());
		this.getChildren().add(circle.get(a));
		naytaKaynnit(tyyppi);
	}

	//Poistetaan yksittäinen visuaalinen asiakasobjekti
	public void poistaAsiakas(Asiakas a) {
		this.getChildren().remove(circle.get(a));
	}
	
	//Näytetään palvelupisteen kävijämäärä
	public void naytaKaynnit(TapahtumanTyyppi tyyppi) {
		kaynnit.get(tyyppi).setText("Käynnit: " + Suorituskykysuureet.getArrivalsByType(tyyppi));
	}
}