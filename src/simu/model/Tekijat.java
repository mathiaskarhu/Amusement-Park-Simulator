package simu.model;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Trace;

/**
 * Käytetään satunnaisten poikkeamien luomiseen
 * @version 1.0
 * @author Santeri, Mathias, Ilkka
 *
 */

// TODO Lisää Tekijöitä, kuten laitteiden vikaantuminen

public class Tekijat {
	private static boolean sataa = false;
	private static double kerroin = 1;
	private double paattyminen;
	private ContinuousGenerator generator;
	private int sateenTodennakoisyys = 0;
	private double kulutusSateella = 1.5;
	
	public Tekijat(ContinuousGenerator generator) {this.generator = generator;}
	
	//Asetetaan sade alkamaan satunnaisesti ja säädetään asiakkaiden energiankulutusta säätilan mukaisesti.
	//Käytetään satunnaislukugeneraattoria.
	public double tarkistaSataako() {
		if (!sataa && (generator.sample() >= sateenTodennakoisyys)) {
            sataa = true;
            kerroin = 2.0;
            paattyminen = Kello.getInstance().getAika() + ((int)(Math.random() * 100) + 50);
            Trace.out(Trace.Level.INFO, "##################### SADE ALKAA #########################" + Kello.getInstance().getAika());
            return kerroin;
        } else if (sataa && Kello.getInstance().getAika() > paattyminen) {
            sataa = false;
            kerroin = 1.0;
            Trace.out(Trace.Level.INFO, "###################### SADE LOPPUI #######################" + Kello.getInstance().getAika());
            return kerroin;
        } else {
            return kerroin;
        }
    }
	
	//Asetetaan asiakkaan energiankulutus sateen statuksen perusteella.
	public double sataakoKulutus() {
        if (sataa) {
        	return kulutusSateella;
        } else {
            return 1.0;
        }
    }
}