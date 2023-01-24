package simu.model;

/**
 * Rajapinta suorituskykysuureita varten
 * @version 1.0
 * @author Santeri, Mathias, Ilkka
 *
 */

public interface IStatistiikka {

	public void setArrivalCount();
	public void setCompletedCount(int a);
	public void setBusyTime(double a);
	public void setResponseTime();
	public void setUtilization(double a);
	public void setWaitingTime(double a);
	public void setServiceTime();
	public void setThroughput(double time);
	
	public int getArrivalCount();
	public int getCompletedCount();
	public double getBusyTime();
	public double getResponseTime();
	public double getUtilization();
	public double getWaitingTime();
	public double getServiceTime();
	public double getThroughput();

}