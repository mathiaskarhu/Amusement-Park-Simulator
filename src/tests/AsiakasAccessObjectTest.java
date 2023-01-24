package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import simu.model.IDAO;
import simu.model.Asiakas;
import simu.model.AsiakasAccessObject;

/**
 * @version 1.0
 * @author Santeri, Mathias, Ilkka
 *
 */

//Testit toimivat vain ja ainoastaan, kun Tracet kommentoi
public class AsiakasAccessObjectTest {
	
	private IDAO asiakasDAO = new AsiakasAccessObject(); // Sama DAO
	private Asiakas a = new Asiakas();

	// Testeissä aina sama asiakas
	@BeforeEach
	public void alkutoimet() {
		a = new Asiakas();
	}

	@Test
	@DisplayName("Testataan asiakkaan luominen.")
	public void testCreateAsiakas() {
		assertTrue(asiakasDAO.createAsiakas(a));
	}
	
	@Test
	@DisplayName("Testataan tietojen lähetys.")
	public void testTietojenLahetys() {
		assertTrue(asiakasDAO.lahetaTiedot());
	}
}