package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import simu.model.Asiakas;

/**
 * @version 1.0
 * @author Santeri, Mathias, Ilkka
 *
 */

public class AsiakasTest {
	
	private Asiakas a = new Asiakas();

	@Test
	@DisplayName("Testataan saadaanko asiakkaan id")
	public void testId() {
		a.getId();
		System.out.println(a.getId());
	}
	
	@Test
	@DisplayName("Testataan saadaanko asiakkaan saapumisaika")
	public void testSaapumisaika() {
		a.setSaapumisaika(1);
		a.getSaapumisaika();
		System.out.println(a.getSaapumisaika());
	}
	
	@Test
	@DisplayName("Testataan asiakkaan poistumisaika")
	public void testPoistumisaika() {
		a.setPoistumisaika(5);
		a.getPoistumisaika();
		System.out.println(a.getPoistumisaika());
	}
	
	@Test
	@DisplayName("Testataan asiakkaan lipunostaminen")
	public void testLipunOsto() {
		a.ostaLippu();
		if (a.ostaLippu() == true) {
			System.out.println("Lipun osto onnistui");
		} else {
			System.out.println("Lipun osto epäonnistui");
		}
	}
	
	@Test
	@DisplayName("Testataan lipuntarkastus")
	public void testTarkastaLippu() {
		a.tarkistaLippu();
		if (!a.tarkistaLippu()) {
			System.out.println("Lippu tarkastettu");
		} else {
			System.out.println("Lipun tarkastus epäonnistui");
		}
	}
}