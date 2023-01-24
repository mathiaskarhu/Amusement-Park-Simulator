package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import eduni.distributions.Negexp;
import simu.model.Tekijat;

/**
 * @version 1.0
 * @author Santeri, Mathias, Ilkka
 *
 */

public class TekijatTest {
	private Tekijat tekijat = new Tekijat(new Negexp(15, 5));

	@Test
	@DisplayName("Testataan saataako")
	public void testSataako() {
		tekijat.tarkistaSataako();
	}
	
	@Test
	@DisplayName("Testataan laskeeko energiataso sateella")
	public void testLaskeekoEnergiatasoSateella() {
		tekijat.sataakoKulutus();
	}
}