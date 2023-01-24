package simu.model;

import java.util.HashMap;

import org.junit.jupiter.params.provider.ValueSource;

import controller.IKontrolleri;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.Moottori;
import simu.framework.Saapumisprosessi;
import simu.framework.Tapahtuma;
import simu.framework.Trace;
import view.ISimulaattorinUI;
import view.IVisualisointi;
import view.SimulaattorinGUI;

/**
 * Ajaa simulaation laajempaa prosessia
 * @version 1.0
 * @author Santeri, Mathias, Ilkka
 *
 */

public class OmaMoottori extends Moottori {

	private Saapumisprosessi saapumisprosessi;
	private Tekijat tekijat;
	private IDAO data = new AsiakasAccessObject();
	private HashMap<Integer, Asiakas> asiakkaat = new HashMap<>();
	private IVisualisointi visualisointi;
	private ISimulaattorinUI ui = new SimulaattorinGUI();

	//Muuttujien arvot jakaumien parametreinä
	private int i;
	private int j;

	public OmaMoottori(IKontrolleri kontrolleri) {

		super(kontrolleri);

		//Asetetaan jakaumien parametrit
		setJakaumanParametrit();
		//Luodaan palvelupisteet
		palvelupisteet = new Palvelupiste[10];
		palvelupisteet[0] = new Palvelupiste(new Normal(i, j), tapahtumalista, TapahtumanTyyppi.HENGAILU);
		palvelupisteet[1] = new Palvelupiste(new Normal(i, j), tapahtumalista, TapahtumanTyyppi.LIPPUPISTE);
		palvelupisteet[2] = new Palvelupiste(new Normal(i, j), tapahtumalista, TapahtumanTyyppi.KARUSELLI);
		palvelupisteet[3] = new Palvelupiste(new Normal(i, j), tapahtumalista, TapahtumanTyyppi.VUORISTORATA);
		palvelupisteet[4] = new Palvelupiste(new Normal(i, j), tapahtumalista, TapahtumanTyyppi.MAAILMANPYÖRÄ);
		palvelupisteet[5] = new Palvelupiste(new Normal(i, j), tapahtumalista, TapahtumanTyyppi.SEALIFE);
		palvelupisteet[6] = new Palvelupiste(new Normal(i, j), tapahtumalista, TapahtumanTyyppi.RAVINTOLA);
		palvelupisteet[7] = new Palvelupiste(new Normal(i, j), tapahtumalista, TapahtumanTyyppi.KIOSKI);
		palvelupisteet[8] = new Palvelupiste(new Normal(i, j), tapahtumalista, TapahtumanTyyppi.WC);
		palvelupisteet[9] = new Palvelupiste(new Normal(i, j), tapahtumalista, TapahtumanTyyppi.POISTUMINEN);
		tekijat = new Tekijat(new Normal(0, 1));
		saapumisprosessi = new Saapumisprosessi(new Negexp(15, 5), tapahtumalista, TapahtumanTyyppi.SAAPUMINEN,
				tekijat);
	}

	// TODO: Palvelupistekohtainen parametrien asettaminen.

	public void setJakaumanParametrit() {
		this.i = kontrolleri.getI();
		this.j = kontrolleri.getJ();
	}

	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}

	@Override
	protected void suoritaTapahtuma(Tapahtuma t) { // B-vaiheen tapahtumat
		Asiakas a;
		int random;

		switch (t.getTyyppi()) {

		case SAAPUMINEN:
			a = new Asiakas();
			asiakkaat.put(a.getId(), a);
			palvelupisteet[0].lisaaJonoon(a);
			Suorituskykysuureet.arrivals(0);
			saapumisprosessi.generoiSeuraava();
			kontrolleri.visualisoiAsiakas(TapahtumanTyyppi.HENGAILU, a);
			break;

		// Hengailussa määritetään asiakkaan seuraava palvelupiste
		case HENGAILU:
			Trace.out(Trace.Level.INFO, "I ON " + i + " J ON " + j);
			a = palvelupisteet[0].otaJonosta();
			kontrolleri.poistaAsiakas(a);
			data.createAsiakas(a);
			a.aloitaJonotusAika();
			// data.updateJonotus(a);

			// Todennäköisyys asiakkaalla ostaa lippu
			if ((int) (Math.random() * 10) + 0 >= 10 - a.getOstoTodennakoisuus() && !a.tarkistaLippu()) {
				palvelupisteet[1].lisaaJonoon(a);
				kontrolleri.visualisoiAsiakas(palvelupisteet[1].getTapahtumanTyyppi(), a);
				Suorituskykysuureet.arrivals(1);
				break;
			}

			// Jos asiakkaan energiataso tippuu nollaan tai alle, niin tämä siirtyy
			// poistumiseen
			if (a.getEnergiataso() <= 0) {
				palvelupisteet[9].lisaaJonoon(a);
				Suorituskykysuureet.arrivals(9);
				break;
			}

			random = seuraavaTapahtuma();

			// Asiakasta ei siirretä lippupisteeseen, jos tällä on lippu
			while (random <= 1 && a.tarkistaLippu()) {
				random = seuraavaTapahtuma();
			}

			// Jos asiakkaalla ei ole lippua, niin tämän ostotodennäköisyys kasvaa yhdellä
			// Lähetetään samalla asiakas toiseen pisteeseen, jos seuraava piste sattuu
			// olemaan lippupiste, tai muuten vaatii lipun
			if (!a.tarkistaLippu()) {
				a.ostoTodennakoisuus();
				while (random <= 1 || palvelupisteet[random].getTapahtumanTyyppi()
						.vaatiiLipun(palvelupisteet[random].getTapahtumanTyyppi())) {
					random = seuraavaTapahtuma();
				}
			}

			palvelupisteet[random].lisaaJonoon(a);
			// Arvottu palvelupiste tiedoksi Arrival suorituskykysuureelle
			Suorituskykysuureet.arrivals(random);
			kontrolleri.visualisoiAsiakas(palvelupisteet[random].getTapahtumanTyyppi(), a);
			break;

		case LIPPUPISTE:
			a = palvelupisteet[1].otaJonosta();
			Suorituskykysuureet.completions(1);
			a.ostaLippu();
			suoritaJonotus(a, 20, 10, false);
			break;

		case KARUSELLI:
			a = palvelupisteet[2].otaJonosta();
			// Palvelu valmis tiedoksi Suorituskykysuureille
			Suorituskykysuureet.completions(2);
			suoritaJonotus(a, 30, 10, false);
			break;

		case VUORISTORATA:
			a = palvelupisteet[3].otaJonosta();
			Suorituskykysuureet.completions(3);
			suoritaJonotus(a, 40, 15, false);
			break;

		case MAAILMANPYÖRÄ:
			a = palvelupisteet[4].otaJonosta();
			Suorituskykysuureet.completions(4);
			suoritaJonotus(a, 40, 15, false);
			break;

		case SEALIFE:
			a = palvelupisteet[5].otaJonosta();
			Suorituskykysuureet.completions(5);
			suoritaJonotus(a, 40, 20, false);
			break;

		case RAVINTOLA:
			a = palvelupisteet[6].otaJonosta();
			Suorituskykysuureet.completions(6);
			suoritaJonotus(a, 30, 15, true);
			break;

		case KIOSKI:
			a = palvelupisteet[7].otaJonosta();
			Suorituskykysuureet.completions(7);
			suoritaJonotus(a, 30, 10, true);
			break;

		case WC:
			a = palvelupisteet[8].otaJonosta();
			Suorituskykysuureet.completions(8);
			suoritaJonotus(a, 40, 10, true);
			break;

		case POISTUMINEN:
			a = palvelupisteet[9].otaJonosta();
			Suorituskykysuureet.completions(9);
			a.setPoistumisaika(Kello.getInstance().getAika());
			a.raportti();
			System.out.println(a.getEnergiataso());
		}
	}

	// Määrittää satunnaisesti seuraavan tapahtuman ja palauttaa sen int-arvon
	public int seuraavaTapahtuma() {
		return (int) (Math.random() * (palvelupisteet.length - 2)) + 1;
	}

	// Suoritetaan jonotus. max, min ja boolean arvot sijoitetaan energiaanMuutos()
	// metodiin
	public void suoritaJonotus(Asiakas a, int max, int min, boolean plus) {
		a.lopetaJonotusAika();
		data.updateJonotus(a);

		energianMuutos(a, max, min, plus);
		if (a.getEnergiataso() <= 0) {
			palvelupisteet[palvelupisteet.length - 1].lisaaJonoon(a);
			kontrolleri.poistaAsiakas(a);
			return;
		} else {
			kontrolleri.poistaAsiakas(a);
			palvelupisteet[0].lisaaJonoon(a);
			Suorituskykysuureet.arrivals(0);
		}

		// Otetaan seuraavaan palvelupisteeseen jonottamisen alkamishetki muistiin.
		a.aloitaJonotusAika();
	}

	// Muutetaan asiakkaan energiatasoa tapahtuman rasittavuuden, jonotuksen keston ja sään mukaan.
	public void energianMuutos(Asiakas a, int max, int min, boolean plus) {
		if (plus) {
			a.setEnergiataso(a.getEnergiataso()
					+ ((int) ((Math.random() * max) + min) - a.getJonotusaika() / 100) * tekijat.sataakoKulutus());
		} else
			a.setEnergiataso(a.getEnergiataso()
					- ((int) ((Math.random() * max) + min) + a.getJonotusaika() / 100) * tekijat.sataakoKulutus());
	}

	@Override
	protected void tulokset() {

		kontrolleri.naytaLoppuaika(Kello.getInstance().getAika());
		Suorituskykysuureet.setTime(Kello.getInstance().getAika());

		for (TapahtumanTyyppi tyyppi : TapahtumanTyyppi.values()) {
			Trace.out(Trace.Level.INFO, "Pistekohtainen arrivalCount " + tyyppi.toString() + " "
					+ Suorituskykysuureet.getArrivalsByType(tyyppi));
			Trace.out(Trace.Level.INFO, "Pistekohtainen busyTime " + tyyppi.toString() + " "
					+ Suorituskykysuureet.getTypeStats().get(tyyppi).getBusyTime());
			Trace.out(Trace.Level.INFO, "Pistekohtainen completedCount " + tyyppi.toString() + " "
					+ Suorituskykysuureet.getTypeStats().get(tyyppi).getCompletedCount());
			Trace.out(Trace.Level.INFO, "Pistekohtainen responseTime " + tyyppi.toString() + " "
					+ Suorituskykysuureet.getTypeStats().get(tyyppi).getResponseTime());
			Trace.out(Trace.Level.INFO, "Pistekohtainen getWaitingTime " + tyyppi.toString() + " "
					+ Suorituskykysuureet.getTypeStats().get(tyyppi).getWaitingTime());
		}

		//Lähetetään tiedot ja statsit tietokantaan
		data.setStatistiikka(Suorituskykysuureet.getTypeStats());
		data.lahetaTiedot();
		data.lahetaStatsit();
		Trace.out(Trace.Level.INFO, "Kaikki saapumiset " + Suorituskykysuureet.getArrivalCount());
	}
}
