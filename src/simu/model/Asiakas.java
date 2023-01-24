package simu.model;

import simu.framework.Kello;
import simu.framework.Trace;
import javax.persistence.*;

/**
 * Sisältää asiakkaan rakenteen
 * @version 1.0
 * @author Santeri, Mathias, Ilkka
 *
 */

// TODO useamman mitattavan/laskettavan asian implementointi

@Entity
@Table(name="asiakasrekisteri")
public class Asiakas {
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "jonotusaika")
	private double jonotusaika;
	private double saapumisaika;
	private double poistumisaika;
	@Transient
	private double hengailuaika = 0;
	@Transient
	private double jonotus_alkaa;
	@Transient
	private double jonotus_paattyy;
	@Transient
	private double jonotusaika_lipunmyynti = 0;
	@Transient
	private double jonotusaika_laitteet = 0;
	@Transient
	private double jonotusaika_ravintola = 0;
	@Transient
	private double jonotusaika_WC = 0;
	@Transient
	private double jonotusaika_sealife = 0;
	@Transient
	private int kaynnit_laitteet;
	@Transient
	private int kaynnit_WC;
	@Transient
	private static int i = 1;
	@Transient
	private static long sum = 0;
	@Transient
	private double energiataso = (int) (Math.random() * 100) + 50;
	@Transient
	private boolean lippu = false;
	@Transient
	private int osto_todennakoisuus = 0;
	
	public Asiakas() {
		id = i++;

		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo " + saapumisaika);
	}

	public double getPoistumisaika() {
		return poistumisaika;
	}

	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika;
	}

	public double getSaapumisaika() {
		return saapumisaika;
	}

	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}

	public int getId() {
		return id;
	}

	//Asetetaan asiakkaan energiataso
	public void setEnergiataso(double energiataso) {
		this.energiataso = energiataso;
	}

	//Palauttaa asiakkaan energiatason
	public double getEnergiataso() {
		return energiataso;
	}

	public void aloitaJonotusAika() {
		jonotus_alkaa = Kello.getInstance().getAika();
	}

	public void lopetaJonotusAika() {
		jonotus_paattyy = Kello.getInstance().getAika();
	}

	public double getJonotusaika() {
		jonotusaika = jonotusaika + (jonotus_paattyy - jonotus_alkaa);
		return jonotusaika;
	}

	public double getHengailuaika() {
		return hengailuaika;
	}

	public void setHengailuaika(double hengailuaika) {
		this.hengailuaika = hengailuaika;
	}

	public double getJonotusaika_lipunmyynti() {
		return jonotusaika_lipunmyynti;
	}

	public void setJonotusaika_lipunmyynti(double jonotusaika_lipunmyynti) {
		this.jonotusaika_lipunmyynti = jonotusaika_lipunmyynti;
	}

	public double getJonotusaika_laitteet() {
		return jonotusaika_laitteet;
	}

	public void setJonotusaika_laitteet(double jonotusaika_laitteet) {
		this.jonotusaika_laitteet = jonotusaika_laitteet;
	}

	public double getJonotusaika_ravintola() {
		return jonotusaika_ravintola;
	}

	public void setJonotusaika_ravintola(double jonotusaika_ravintola) {
		this.jonotusaika_ravintola = jonotusaika_ravintola;
	}

	public double getJonotusaika_WC() {
		return jonotusaika_WC;
	}

	public void setJonotusaika_WC(double jonotusaika_WC) {
		this.jonotusaika_WC = jonotusaika_WC;
	}

	public double getJonotusaika_sealife() {
		return jonotusaika_sealife;
	}

	public void setJonotusaika_sealife(double jonotusaika_sealife) {
		this.jonotusaika_sealife = jonotusaika_sealife;
	}

	public int getKaynnit_laitteet() {
		return kaynnit_laitteet;
	}

	public void setKaynnit_laitteet(int kaynnit_laitteet) {
		this.kaynnit_laitteet = kaynnit_laitteet;
	}

	public int getKaynnit_WC() {
		return kaynnit_WC;
	}

	public void setKaynnit_WC(int kaynnit_WC) {
		this.kaynnit_WC = kaynnit_WC;
	}

	public void setJonotusaika(double jonotusaika) {
		this.jonotusaika = jonotusaika;
	}

	//Tulostetaan toimintaraportteja konsoliin
	public void raportti() {
		Trace.out(Trace.Level.INFO, "\nAsiakas " + id + " valmis! ");
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " saapui: " + saapumisaika);
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " poistui: " + poistumisaika);
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " viipyi: " + (poistumisaika - saapumisaika));
		sum += (poistumisaika - saapumisaika);
		double keskiarvo = sum / id;
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo tähän asti " + keskiarvo);
	}

	//Käsketään asiakasta ostamaan lippu.
	public boolean ostaLippu() {
		lippu = true;
		return lippu;
	}
	
	//Jos asiakas pyrkii laitteeseen ilman lippua, niin estetään se. Kutsutaan OmaMoottorissa.
	public boolean tarkistaLippu() {
		return lippu;
	}
	
	//Lisätään todennäköisyyttä jolla asiakas ostaa lipun. Kutsutaan OmaMoottorissa
	public void ostoTodennakoisuus() {
		if(osto_todennakoisuus < 9) {
			osto_todennakoisuus++;
		}
	}
	
	public int getOstoTodennakoisuus() {
		return osto_todennakoisuus;
	}
}