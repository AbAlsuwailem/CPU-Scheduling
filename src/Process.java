
public class Process implements Comparable<Process> {
	public String pid;
	public int PID;
	public int priority;
	public int burstTime;
	public int arrivaleTime;
	public int runningTime;
	public int waiting_time;
	public int completion_time;
	public int turnaround_time;
	public int finishTime;

	public Process(int pid, int arrivale, int burst) {
		this.PID = pid;
		this.arrivaleTime = arrivale;
		this.burstTime = burst;
		
	}
	public Process(String pid,int bt ,int pr) {
		this.pid=pid;
		this.burstTime=bt;
		this.priority=pr;
		
		
	}
	public void setArrivalTime(int arrival) {
		this.arrivaleTime = arrival;
	}

	public int getTurnAroundTime() {
		return finishTime - arrivaleTime;
	}

	public int getWaitingTime() {
		return this.getTurnAroundTime() - this.burstTime;
	}

	public int getRemainingTime() {
		return this.burstTime - this.runningTime;
	}

// end of srtf

//start of priority:
	@Override
	public int compareTo(Process p) {

		if (this.priority > p.priority)
			return 1;
		else
			return -1;
	}
//end of priority

	
	
	
	
	
	//setters and getters
	public void setrunningTime(int rn) {
		this.runningTime = rn;
	}

	public int getRunningTime() {
		return runningTime;
	}

	public void setFininshTime(int finish) {
		this.finishTime = finish;
	}

	public int getFinishTime() {
		return finishTime;
	}

	public void setPID(int pID) {
		PID = pID;
	}

	public int getPID() {
		return PID;
	}

	public int getBurstTime() {
		return burstTime;
	}

	public void setBurstTime(int burstTime) {
		this.burstTime = burstTime;
	}

	public int getArrivaleTime() {
		return arrivaleTime;
	}

	public void setArrivaleTime(int arrivaleTime) {
		this.arrivaleTime = arrivaleTime;
	}

}
