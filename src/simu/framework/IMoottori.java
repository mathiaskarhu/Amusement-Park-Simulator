package simu.framework;

public interface IMoottori { // UUSI
		
	// Kontrolleri käyttää tätä rajapintaa
	
	public void setSimulointiaika(double aika);
	public void setViive(long aika);
	public void setI(int i);
	public void setJ(int j);
	public long getViive();
}
