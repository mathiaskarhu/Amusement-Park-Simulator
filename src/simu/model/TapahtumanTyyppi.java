package simu.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Määritellään tapahtumatyypit
 * @version 1.0
 * @author Santeri, Mathias, Ilkka
 *
 */

// TODO:
// Tapahtumien tyypit määritellään simulointimallin vaatimusten perusteella
public enum TapahtumanTyyppi {
	SAAPUMINEN(false),
	HENGAILU(false),
	LIPPUPISTE(false),
	KARUSELLI(true),
	VUORISTORATA(true),
	MAAILMANPYÖRÄ(true),
	SEALIFE(true),
	RAVINTOLA(false),
	KIOSKI(false),
	WC(false),
	POISTUMINEN(false);
	
	public static final Map<Boolean, TapahtumanTyyppi> BY_LIPPU = new HashMap<>();
	
	static {
		for (TapahtumanTyyppi e : values()) {
			BY_LIPPU.put(e.tarvitaan_lippu, e);
		}
	}
	
	public final Boolean tarvitaan_lippu;
	
	//Tarvitaanko palvelupisteeseen lippu
	private TapahtumanTyyppi(Boolean lippu) {
		this.tarvitaan_lippu = lippu;
	}
	
	//Erotetaan toisistaan ne palvelupisteet mihin tarvitaan lippu ja ne mihin ei tarvita
	public TapahtumanTyyppi getTyyppi() {
		return BY_LIPPU.get(tarvitaan_lippu);
	}
	
	public boolean vaatiiLipun(TapahtumanTyyppi tyyppi) {
		return tyyppi.tarvitaan_lippu;
	}
}
