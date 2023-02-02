import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class processApplication {

	public static ArrayList<Process> processes = new ArrayList<>();
	public static List<Process> arr = new ArrayList<>();

	public static void SRTF() {
		int[] flag = new int[processes.size()]; // to check if the process is completed or not || 0 not completed and 1
												// // // is completed
		int st = 0, tot = 0;
		int n = processes.size();
		int turnAroundtTime[] = new int[n];
		int waitTime[] = new int[n];
		int burstTime[] = new int[n];
		double avgwait = 0;
		double avgtat = 0;
		for (int i = 0; i < n; i++) {
			flag[i] = 0;
			burstTime[i] = processes.get(i).getBurstTime();
		}
		while (true) {
			int min = 99;
			int c = n;
			if (tot == n)
				break;
			for (int i = 0; i < n; i++) {
				if ((processes.get(i).getArrivaleTime() <= st) && (flag[i] == 0)
						&& (processes.get(i).getBurstTime() < min)) {
					min = processes.get(i).getBurstTime();
					c = i;
				}
			}
			if (c == n)
				st++;
			else {
				processes.get(c).setBurstTime(processes.get(c).getBurstTime() - 1);
				st++;
				if (processes.get(c).getBurstTime() == 0) {
					processes.get(c).setFininshTime(st);
					flag[c] = 1;
					tot++;
				}
			}
		}
		for (int i = 0; i < n; i++) {
			turnAroundtTime[i] = processes.get(i).getFinishTime() - processes.get(i).getArrivaleTime();
			waitTime[i] = processes.get(i).getTurnAroundTime() - burstTime[i];
			avgwait += waitTime[i];
			avgtat += turnAroundtTime[i];
		}
		// display
		System.out.println("PID " + "Burst Time " + "Waiting Time" + " Turn Around Time");
		// display the information: PID BurstTime waitingTime turnAroundTime
		for (int i = 0; i < n; i++) {
			System.out.println(" " + processes.get(i).getPID() + "\t" + burstTime[i] + "\t" + waitTime[i] + "\t"
					+ turnAroundtTime[i]);
		}
		System.out.println("Average waiting time = " + (float) avgwait / (float) n);
		System.out.println("Average turn around time = " + (float) avgtat / (float) n);
	}

	public static void Priority() {

		int current_time = 0;
		int total_turnaround_time = 0;
		int total_waiting_time = 0;
		double avgtt = 0;
		double avgwt = 0;

		// calculate
		for (int i = 0; i < arr.size(); i++) {
			arr.get(i).runningTime = current_time;
			arr.get(i).completion_time = arr.get(i).runningTime + arr.get(i).burstTime;
			arr.get(i).turnaround_time = arr.get(i).completion_time;
			arr.get(i).waiting_time = arr.get(i).turnaround_time - arr.get(i).burstTime;

			total_turnaround_time += arr.get(i).turnaround_time;
			total_waiting_time += arr.get(i).waiting_time;
			current_time = arr.get(i).completion_time;

		}

		// printing
		System.out.println(
				"PID " + "\t" + "Burst Time " + "\t" + "Priority" + "\t" + "Waiting Time" + "\t" + " Turn Around Time");

		for (int i = 0; i < arr.size(); i++) {

			System.out.println(arr.get(i).pid + " \t" + arr.get(i).burstTime + "         \t" + arr.get(i).priority
					+ "         \t" + arr.get(i).waiting_time + "               \t" + arr.get(i).turnaround_time);

		}
		avgtt = total_turnaround_time / arr.size();
		avgwt = total_waiting_time / arr.size();
		System.out.println("Average waiting time = " + avgwt);
		System.out.println("Average turn around time = " + avgtt);

	}

	public static void RR(int n) {
		int avrgW = 0;
		int avrgtat = 0;

		int tempB[] = new int[processes.size()];
		int tempA[] = new int[processes.size()];

		for (int i = 0; i < tempB.length; i++) {
			tempB[i] = processes.get(i).getBurstTime();
			tempA[i] = processes.get(i).getArrivaleTime();
		}

		int t = 0;

		int watingT[] = new int[processes.size()];

		while (true) {
			boolean flag = true;
			for (int i = 0; i < processes.size(); i++) {

				if (tempA[i] <= t) {
					if (tempA[i] <= n) {
						if (tempB[i] > 0) {
							flag = false;
							if (tempB[i] > n) {

								t = t + n;
								tempB[i] = tempB[i] - n;
								tempA[i] = tempA[i] + n;
							} else {

								t = t + tempB[i];

								watingT[i] = t - processes.get(i).getBurstTime() - processes.get(i).getArrivaleTime();
								tempB[i] = 0;
							}
						}
					} else if (tempA[i] > n) {

						for (int j = 0; j < processes.size(); j++) {

							if (tempA[j] < tempA[i]) {
								if (tempB[j] > 0) {
									flag = false;
									if (tempB[j] > n) {
										t = t + n;
										tempB[j] = tempB[j] - n;
										tempA[j] = tempA[j] + n;
									} else {
										t = t + tempB[j];
										watingT[j] = t - processes.get(i).getBurstTime()
												- processes.get(i).getArrivaleTime();
										tempB[j] = 0;
									}
								}
							}
						}

						if (tempB[i] > 0) {
							flag = false;

							if (tempB[i] > n) {
								t = t + n;
								tempB[i] = tempB[i] - n;
								tempA[i] = tempA[i] + n;
							} else {
								t = t + tempB[i];
								watingT[i] = t - processes.get(i).getBurstTime() - processes.get(i).getArrivaleTime();
								tempB[i] = 0;
							}
						}
					}
				}

				else if (tempA[i] > t) {
					t++;
					i--;
				}
			}
			if (flag) {
				break;
			}
		}
		int tat[] = new int[processes.size()];
		for (int j = 0; j < tat.length; j++) {
			tat[j] = watingT[j] + processes.get(j).getBurstTime();
		}

		System.out.println("name AT  bt  tat  WT");
		for (int i = 0; i < processes.size(); i++) {
			System.out.println(" " + processes.get(i).getPID() + "    " + processes.get(i).getArrivaleTime() + "    "
					+ processes.get(i).getBurstTime() + "    " + tat[i] + "    " + watingT[i]);

			avrgW = avrgW + watingT[i];
			avrgtat = avrgtat + tat[i];
		}

		System.out.println("Average waiting time is " + (float) avrgW / processes.size());
		System.out.println("Average turn tround  time is " + (float) avrgtat / processes.size());
		System.out.println("Your chosen quantum is " + n);
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the type of operation SRTF or Priority or RR");
		String input = sc.next();
		String choice = input.toLowerCase();
		switch (choice) {
		case "srtf":
			String fn = "C:\\Users\\abdul\\Desktop\\KSU\\6\\CSC227\\testdata1.txt";
			File file = new File(fn);
			Scanner sn = new Scanner(file);
			while (sn.hasNextLine()) {
				String[] line = sn.nextLine().split(",");
				Process pr = new Process(Integer.parseInt(line[0]), Integer.parseInt(line[1]),
						Integer.parseInt(line[2]));
				processes.add(pr);
			}
			SRTF();
			break;
		case "rr":
			String rr = "C:\\Users\\abdul\\Desktop\\KSU\\6\\CSC227\\testdata1rr.txt";
			File f = new File(rr);
			Scanner s = new Scanner(f);
			Scanner in = new Scanner(System.in);
			System.out.print("Please enter your choosen quantum : ");
			int quant = in.nextInt();
			if (quant <= 0) {
				System.out.println("quantom cannot be less than or equal 0");
			} else {
				while (s.hasNextLine()) {
					String[] line = s.nextLine().split(",");
					Process pr = new Process(Integer.parseInt(line[0]), Integer.parseInt(line[1]),
							Integer.parseInt(line[2]));
					processes.add(pr);
				}
				RR(quant);

			}
			break;
		case "priority":
			String fil = "C:\\\\Users\\\\abdul\\\\Desktop\\\\KSU\\\\6\\\\CSC227\\\\testdata1Priority.txt";
			File ff = new File(fil);
			Scanner scan = new Scanner(ff);

			while (scan.hasNextLine()) {
				String y = scan.nextLine();
				String[] line = scan.nextLine().split(",");
				Process pr = new Process(y, Integer.parseInt(line[0]), Integer.parseInt(line[1]));
				arr.add(pr);
			}
			Collections.sort(arr);
			Priority();
			break;
		}
	}
}
