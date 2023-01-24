package simu.model;

import java.util.ArrayList;
import javax.persistence.*;

/**
 * Pitää kirjaa ja luo taulun palvelupistekohtaisista suorituskykysuureista. 
 * @version 1.0
 * @author Santeri, Mathias, Ilkka
 *
 */

@Entity
@Table(name = "statistiikka")

public class Statistiikka implements IStatistiikka {
	
	@Id
	@Column(name = "Id")
	private String serviceType;
	@Column(name = "arrivalCount")
	private int arrivalCount = 0;
	@Column(name = "completedCount")
	private int completedCount = 0;
	@Column(name = "busyTime")
	private double busyTime = 0;
	@Column(name = "responseTime")
	private double responseTime = 0;
	@Column(name = "utilization")
	private double utilization = 0;
	@Column(name = "waitingTime")
	private double waitingTime = 0;
	@Column(name = "serviceTime")
	private double serviceTime = 0;
	@Column(name = "throughput")
	private double throughput = 0;
	private ArrayList<Double> responseTimeList;

	public Statistiikka(TapahtumanTyyppi serviceType) {
		responseTimeList = new ArrayList<>();
		this.serviceType = serviceType.toString();
	}
	
	@Override
	public void setArrivalCount() {
		arrivalCount += 1;
	}
	
	@Override
	public void setCompletedCount(int a) {
		completedCount += a;
	}
	
	@Override
	public void setBusyTime(double a) {
		busyTime += a;
	}
	
	@Override
	public void setResponseTime() {
		responseTime = busyTime/completedCount;
	}
	
	@Override
	public void setUtilization(double a) {
		utilization = busyTime/a;
	}
	
	@Override
	public void setWaitingTime(double a) {
		waitingTime += a;
	}
	
	@Override
	public void setServiceTime() {
		serviceTime = busyTime/completedCount;
	}
	
	@Override
	public void setThroughput(double time) {
		throughput = completedCount/time;
	}

	public int getArrivalCount() {
		return arrivalCount;
	}

	public int getCompletedCount() {
		return completedCount;
	}

	public double getBusyTime() {
		return busyTime;
	}

	public double getResponseTime() {
		return responseTime;
	}

	public double getUtilization() {
		return utilization;
	}

	public double getWaitingTime() {
		return waitingTime;
	}

	public double getServiceTime() {
		return serviceTime;
	}

	public double getThroughput() {
		return throughput;
	}
}