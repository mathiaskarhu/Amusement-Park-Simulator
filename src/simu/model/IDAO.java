package simu.model;

import java.util.HashMap;

/**
 * Hibernaten rajapinta
 * @version 1.0
 * @author Santeri, Mathias, Ilkka
 *
 */

public interface IDAO {
	public boolean createAsiakas(Asiakas asiakas);
	public boolean updateJonotus(Asiakas asiakas);
	public boolean lahetaTiedot();
	public void setStatistiikka(HashMap<TapahtumanTyyppi, Statistiikka> palveluStats);
	public boolean lahetaStatsit();
}
