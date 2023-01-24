package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import simu.model.Suorituskykysuureet;
import simu.model.TapahtumanTyyppi;

/**
 * @version 1.0
 * @author Santeri, Mathias, Ilkka
 *
 */

public class SuorituskykysuureetTest {

	private Suorituskykysuureet suorituskykysuureet = new Suorituskykysuureet();

	@Test
	@DisplayName("Testataan toimiiko arrival")
	public void testArrival() {
		Suorituskykysuureet.arrival(TapahtumanTyyppi.KARUSELLI);

		if (Suorituskykysuureet.getArrivalCount() == 1) {
			System.out.println("Metodi arrival() toimii");
		} else {
			System.out.println("Metodi arrival() ei toimi");
		}
	}

	@Test
	@DisplayName("Testataan toimiiko setCompletedCount")
	public void testCompletedCount() {
		Suorituskykysuureet.setCompletedCount(TapahtumanTyyppi.KARUSELLI, 1);

		if (Suorituskykysuureet.getTypeStats().get(TapahtumanTyyppi.KARUSELLI).getCompletedCount() == 1) {
			System.out.println("Metodi setCompletedCount() toimii");
		} else {
			System.out.println("Metodi setCompletedCount() ei toimi");
		}
	}
}