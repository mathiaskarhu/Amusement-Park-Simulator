package view;

import java.util.HashMap;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import simu.framework.Trace;
import simu.model.Asiakas;

/**
 * Määrittää asiakkaat sekä palvelupisteet objekteina visualisointinäkymään
 * @version 1.0
 * @author Santeri, Mathias, Ilkka
 *
 */

public class PisteHandler {
	private int x, y, sizeX, sizeY;
	private String color;
	private Rectangle rectangle;
	private Circle circle;
	private HashMap<Asiakas, Circle> asiakkaatHashMap = new HashMap<>();
	private int i;
	private int j;
	
	public PisteHandler(int x, int y, int sizeX, int sizeY, String color) {
		rectangle = new Rectangle(x, y, sizeX, sizeY);
		rectangle.setFill(Color.web(color));
		
		this.x = x+10;
		this.y = y;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.color = color;
		
		i = x;
		j = (y+10);
	}
	
	public Rectangle getRectangle() {
		return rectangle;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getSizeY() {
		return sizeY;
	}
	
	public int getSizeX() {
		return sizeX;
	}
	
	public void naytaAsiakas(Asiakas a) {
		asiakkaatHashMap.put(a, luoCircle());
		Trace.out(Trace.Level.INFO, "====================== NAYTA ASIAKAS" + a.getId() + "==========" + getSizeX());
	}
	
	//Luodaan asiakkaan pallo-objekti
	public Circle luoCircle() {
		i = i + 10;
		if (i >= (sizeX+x)-10) {
			i = x;
			j+=10;
		}
		
		if(j >= sizeY+y) {
			j = (y+10);
		}
		
		circle = new Circle(i, j, 5);
		return circle;
	}
	
	//Haetaan tietyn asiakkaan visuaalinen objekti
	public Circle getCircle(Asiakas a) {
		return asiakkaatHashMap.get(a);
	}
	
	public int handlerMaara() {
		return 0;
	}
}
