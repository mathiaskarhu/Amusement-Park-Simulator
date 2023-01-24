package simu.model;

import java.util.LinkedList;
import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;

/**
 * Palvelupisteen rakenne
 * 
 * @version 1.0
 * @author Santeri, Mathias, Ilkka
 *
 */

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava
public class Palvelupiste {

	private LinkedList<Asiakas> jono = new LinkedList<Asiakas>(); // Tietorakennetoteutus
	private ContinuousGenerator generator;
	private Tapahtumalista tapahtumalista;
	private TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;
	private Suorituskykysuureet suorituskykysuureet = new Suorituskykysuureet();

	private boolean varattu = false;

	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi) {
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
	}

	public void lisaaJonoon(Asiakas a) { // Jonon 1. asiakas aina palvelussa
		jono.add(a);
		Trace.out(Trace.Level.INFO, skeduloitavanTapahtumanTyyppi + " Arrival count "
				+ Suorituskykysuureet.getArrivalsByType(skeduloitavanTapahtumanTyyppi) + " Asiakas " + a.getId());
	}

	public Asiakas otaJonosta() { // Poistetaan palvelussa ollut
		varattu = false;
		suorituskykysuureet.stopBusy(skeduloitavanTapahtumanTyyppi, Kello.getInstance().getAika());
		return jono.poll();
	}

	// Palauttaa kaikki käynnit
	public int naytaKaynnit() {
		return Suorituskykysuureet.getArrivalCount();
	}

	public void aloitaPalvelu() { // Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana

		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + jono.peek().getId());

		varattu = true;
		suorituskykysuureet.startBusy(skeduloitavanTapahtumanTyyppi, Kello.getInstance().getAika());
		double palveluaika = generator.sample();
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi, Kello.getInstance().getAika() + palveluaika));
	}

	//Onko palvelupiste varattu
	public boolean onVarattu() {
		return varattu;
	}

	//Onko palvelupisteen jonossa asiakkaita
	public boolean onJonossa() {
		return jono.size() != 0;
	}

	// Palauttaa tapahtumatyypin
	public TapahtumanTyyppi getTapahtumanTyyppi() {
		return skeduloitavanTapahtumanTyyppi;
	}
}