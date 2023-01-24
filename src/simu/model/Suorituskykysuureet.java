package simu.model;

import java.util.HashMap;

import net.bytebuddy.asm.Advice.This;

/**
 * Kerää suorituskykysuuredataa hashmappeihin
 * @version 1.0
 * @author Santeri, Mathias, Ilkka
 *
 */

public class Suorituskykysuureet {
	
	private double responseTime = 0;
	private static double busyTime = 0;
	private static double time = 0;
	private double utilization = 0;
	private double throughput = 0;
	private double serviceTime = 0;
	private static double waitingTime = 0;
	private static double averageResponseTime = 0;
	private static double averageQueueLength = 0;
	private static int arrivalCount = 0;
	private static int completedCount = 0;
	private double startBusyTime = 0;
	
	//Aktiiviajan laskemisen apumuuttujat
	private static double apu1;
	private static double apu2;
	private static double apu3 = 0;
	
	//Pitää kirjaa jokaisen luokan busy time
	private static HashMap<TapahtumanTyyppi, Double> busyTimeMap = new HashMap<>();
	private static HashMap<TapahtumanTyyppi, Double> startBusyMap = new HashMap<>();
	private static HashMap<TapahtumanTyyppi, Statistiikka> palveluStats = new HashMap<>();
	
	//Luodaan oma statistiikkaolio jokaiselle tapahtumantyypille
	public Suorituskykysuureet() {
		for (TapahtumanTyyppi pisteet : TapahtumanTyyppi.values()) {
			palveluStats.put(pisteet, new Statistiikka(pisteet));
		}
		
		for (TapahtumanTyyppi pisteet : TapahtumanTyyppi.values()) {
			busyTimeMap.put(pisteet, startBusyTime);
			startBusyMap.put(pisteet, startBusyTime);
		}
	}
	
	//Palvelupistekohtaiset suureet
	public static void arrival(TapahtumanTyyppi tyyppi) {
		palveluStats.get(tyyppi).setArrivalCount();
		arrivalCount += 1;
	}
	
	//Pyydetään OmaMoottorissa
	public static void setCompletedCount(TapahtumanTyyppi tyyppi, int a) {
		completedCount += a;
		palveluStats.get(tyyppi).setCompletedCount(a);
	}
	
	//Pyydetään Palvelupisteessä
	public static void setBusyTime(TapahtumanTyyppi tyyppi) {
		busyTime += busyTimeMap.get(tyyppi);
		palveluStats.get(tyyppi).setBusyTime(busyTimeMap.get(tyyppi));
	}
	
	//Pyydetään OmaMoottorissa
	public static void setResponseTime(TapahtumanTyyppi tyyppi) {
		palveluStats.get(tyyppi).setResponseTime();
	}
	
	//Pyydetään OmaMoottorissa
	public static void setUtilization(TapahtumanTyyppi tyyppi) {
		palveluStats.get(tyyppi).setUtilization(time);
	}
	
	public static void setWaitingTime(TapahtumanTyyppi tyyppi, Double a) {
		waitingTime += a;
		palveluStats.get(tyyppi).setWaitingTime(a);
	}
	
	public static void setServiceTime(TapahtumanTyyppi tyyppi) {
		palveluStats.get(tyyppi).setServiceTime();
	}
	
	public static void setThroughput(TapahtumanTyyppi tyyppi) {
		palveluStats.get(tyyppi).setThroughput(time);
	}
	
	public static void setTime(double a) {
		time = a;
	}
	
	// OmaMoottorilta saapumiset
	public static void arrivals(int random) {
		switch (random) {
		
		case 0:
			arrival(TapahtumanTyyppi.HENGAILU);
			break;
		case 1:
			arrival(TapahtumanTyyppi.LIPPUPISTE);
			break;
		case 2:
			arrival(TapahtumanTyyppi.KARUSELLI);
			break;
		case 3:
			arrival(TapahtumanTyyppi.VUORISTORATA);
			break;
		case 4:
			arrival(TapahtumanTyyppi.MAAILMANPYÖRÄ);
			break;
		case 5:
			arrival(TapahtumanTyyppi.SEALIFE);
			break;
		case 6:
			arrival(TapahtumanTyyppi.RAVINTOLA);
			break;
		case 7:
			arrival(TapahtumanTyyppi.KIOSKI);
			break;
		case 8:
			arrival(TapahtumanTyyppi.WC);
			break;
		}
	}
	
	//Palauttaa kaikki arrivalit
	public static int getArrivalCount() {
		return arrivalCount;
	}
	
	//Palauttaa arrivalit tyypin perusteella
	public static int getArrivalsByType(TapahtumanTyyppi tyyppi) {
		return palveluStats.get(tyyppi).getArrivalCount();
	}
	
	public static HashMap<TapahtumanTyyppi, Statistiikka> getTypeStats() {
		return palveluStats;
	}
	
	public static void completion(TapahtumanTyyppi tyyppi) {
		palveluStats.get(tyyppi).setCompletedCount(1);
	}
	
	//Omamoottori completions
	public static void completions(int t) {
		switch (t) {
		case 1:
			completion(TapahtumanTyyppi.LIPPUPISTE);
			break;
		case 2:
			completion(TapahtumanTyyppi.KARUSELLI);
			break;
		case 3:
			completion(TapahtumanTyyppi.VUORISTORATA);
			break;
		case 4:
			completion(TapahtumanTyyppi.MAAILMANPYÖRÄ);
			break;
		case 5:
			completion(TapahtumanTyyppi.SEALIFE);
			break;
		case 6:
			completion(TapahtumanTyyppi.RAVINTOLA);
			break;
		case 7:
			completion(TapahtumanTyyppi.KIOSKI);
			break;
		case 8:
			completion(TapahtumanTyyppi.WC);
			break;
		case 9:
			completion(TapahtumanTyyppi.POISTUMINEN);
			break;
		}
		
	}
	
	// Palvelupiste kertoo olevansa käytössä
	public void startBusy(TapahtumanTyyppi tyyppi, double time) {
		// Tallennetaan aktiivisuuden alkamisaika
		startBusyMap.replace(tyyppi, time);
	}
	// Palvelupiste kertoo olevansa vapaana
	public void stopBusy(TapahtumanTyyppi tyyppi, double time) {
		// Lasketaan toteutunut aktiiviaika
		 apu1 = time - startBusyMap.get(tyyppi);
		 apu2 = busyTimeMap.get(tyyppi);
		 // Tallennetaan palvelupisteen uusi kumulatiivinen aktiivisuusaika
		 busyTimeMap.replace(tyyppi, apu2 + apu1);
		 palveluStats.get(tyyppi).setBusyTime(busyTimeMap.get(tyyppi));
		 apu1 = 0;
		 apu2 = 0;
		 busyTimeMap.replace(tyyppi, (double) 0);
	}
	
}