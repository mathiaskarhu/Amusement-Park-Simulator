package simu.framework;
import eduni.distributions.*;
import simu.model.TapahtumanTyyppi;
import simu.model.Tekijat;
public class Saapumisprosessi {
	
	private ContinuousGenerator generaattori;
	private Tapahtumalista tapahtumalista;
	private TapahtumanTyyppi tyyppi;
	private Tekijat tekijat;

	public Saapumisprosessi(ContinuousGenerator g, Tapahtumalista tl, TapahtumanTyyppi tyyppi, Tekijat tekijat){
		this.generaattori = g;
		this.tapahtumalista = tl;
		this.tyyppi = tyyppi;
		this.tekijat = tekijat;
	}

	public void generoiSeuraava(){
		Tapahtuma t = new Tapahtuma(tyyppi, Kello.getInstance().getAika() + generaattori.sample() * tekijat.tarkistaSataako());
		tapahtumalista.lisaa(t);
	}
}
