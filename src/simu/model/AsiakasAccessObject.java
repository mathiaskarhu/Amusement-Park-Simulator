package simu.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import simu.framework.Saapumisprosessi;
import simu.framework.Trace;

/**
 * Käyttää Hibernatea, määritetään asiakasrekisteri, sekä statistiikka suorituskykysuureista.
 * @version 1.0
 * @author Santeri, Mathias, Ilkka
 *
 */

public class AsiakasAccessObject implements IDAO {
	private SessionFactory istuntotehdas = null;
	private ArrayList<Asiakas> asiakasRekisteri = new ArrayList<Asiakas>();
	private Suorituskykysuureet suorituskykysuureet = new Suorituskykysuureet();
	private static HashMap<TapahtumanTyyppi, Statistiikka> palveluStats;

	public AsiakasAccessObject() {
		try {
			istuntotehdas = new Configuration().configure().buildSessionFactory();
		} catch (Exception e) {
			System.err.println("Istuntotehtaan luominen ei 	onnistunut." + e.getMessage());
			System.exit(-1);
		}
	}

	//Luodaan asiakas
	@Override
	public boolean createAsiakas(Asiakas asiakas) {
		try {
			asiakasRekisteri.add(asiakas);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	//Päivitetään jono
	@Override
	public boolean updateJonotus(Asiakas asiakas) {
		try {
			asiakasRekisteri.set(asiakas.getId(), asiakas);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public void setStatistiikka(HashMap<TapahtumanTyyppi, Statistiikka> a) {
		palveluStats = a;
	}

	//Lähetetään asiakasrekisterin tiedot tietokantaan
	@Override
	public boolean lahetaTiedot() {
		Transaction transaktio = null;
		Trace.out(Trace.Level.INFO, "Tallennetaan tietoja tietokantaan... Älä sammuta ohjelmaa!");
		try (Session istunto = istuntotehdas.openSession()) {

			for (Asiakas asiakas : asiakasRekisteri) {
				transaktio = istunto.beginTransaction();
				istunto.saveOrUpdate(asiakas);
				transaktio.commit();
			}
			Trace.out(Trace.Level.INFO, "Tiedot tallennettu tietokantaan");
			return true;
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			Trace.out(Trace.Level.INFO, "Tallentamisessa tapahtui virhe");
			throw e;
		}
	}
	
	//Lähetetään statistiikka tietokantaan
	@Override
	public boolean lahetaStatsit() {
		Transaction transaktio = null;
		
		Trace.out(Trace.Level.INFO, "Tallennetaan statseja tietokantaan... Älä sammuta ohjelmaa!");
		try (Session istunto = istuntotehdas.openSession()) {
			
			for(TapahtumanTyyppi tyyppi : TapahtumanTyyppi.values()) {
				transaktio = istunto.beginTransaction();
				istunto.saveOrUpdate(palveluStats.get(tyyppi));
				transaktio.commit();
			}
			
			Trace.out(Trace.Level.INFO, "Statsit tallennettu tietokantaan");
			return true;
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			Trace.out(Trace.Level.INFO, "Tallentamisessa tapahtui virhe");
			throw e;
		}
	}
	

	//Suljetaan tietokantayhteys
	@Override
	protected void finalize() {
		try {
			if (istuntotehdas != null)
				istuntotehdas.close(); // sulkee automaattisesti muutkin avatut resurssit
		} catch (Exception e) {
			System.err.println("Istuntotehtaan sulkeminen epäonnistui: " + e.getMessage());
		}
	}
}