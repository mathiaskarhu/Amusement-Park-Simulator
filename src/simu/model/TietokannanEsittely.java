package simu.model;

import java.net.URI;

/**
 * Tietokannan tietojen esittely ilman tietokantayhteyden käyttämistä.
 * Avaa phpMyAdmin sivuston oletusselaimella ja kirjautuu sisään simulaattorin tietokantaan.
 * @version 1.0
 * @author Ilkka
 *
 */

public class TietokannanEsittely {

	public void esittely() {
		try {
			/* muokkaa tiedot omiksi
			URI uri = new URI(
				"https://osoite/phpMyAdmin/index.phproute=/sql&server=1&db=tunnus&table=statistiikka&pos=0");
			*/
			java.awt.Desktop.getDesktop().browse(uri);
			System.out.println("Database view opened in browser");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}