package RUNING;


import java.util.Scanner;

public class Main {
	public static String DataRate;
	public static String Standard;
	public static String Protocol;

	static String UDP = "UDP";
	static String TCP = "TCP";

	static String a = "802.11a";
	static String ac1 = "802.11ac1";
	static String ac2 = "802.11ac2";
	static String ax = "802.11ax";
	static String g = "802.11g";
	static String n = "802.11n";
	
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Please choose Protocol(UDP/TCP):");
		Protocol = sc.nextLine();
		System.out.println("Please choose Standard(802.11a, 802.11g, 802.11ac1, 802.11ac2, 802.11ax, 802.11n):");
		Standard = sc.nextLine();
		
		if (Main.Standard.equals(Main.a)) {
			System.out.println("Please choose DataRate(54, 48, 36, 24, 18, 12, 9, 6 Mbps):");
		}
		if (Main.Standard.equals(Main.ac1)) {
			System.out.println("Please choose DataRate(96.3, 86.7, 72.2, 65, 57.8, 43.3, 28.9, 21.7, 14.4, 7.2 Mbps):");
		}
		if (Main.Standard.equals(Main.ac2)) {
			System.out.println("Please choose DataRate(96.3, 86.7, 72.2, 65, 57.8, 43.3, 28.9, 21.7, 14.4, 7.2 Mbps):");
		}
		if (Main.Standard.equals(Main.ax)) {
			System.out.println("Please choose DataRate(143.4, 129.0, 114.7, 103.2, 86.0, 77.4, 68.8, 51.6, 34.4, 25.8, 17.2, 8.6 Mbps):");
		}
		if (Main.Standard.equals(Main.g)) {
			System.out.println("Please choose DataRate(54, 48, 36, 24, 18, 12, 9, 6 Mbps):");
		}
		if (Main.Standard.equals(Main.n)) {
			System.out.println("Please choose DataRate(72.2, 65, 57.8, 43.3, 28.9, 21.7, 14.4, 7.2 Mbps):");
		}
		DataRate = sc.nextLine();
		System.out.println("--------------------------------------------------");
		System.out.println("Protocol: " + Protocol + "; Standard:" + Standard + "; data rate:" + DataRate);
		
		if (Protocol.equals(UDP)) {
			new usingUDP.Main();
			usingUDP.Main.main(args);
		}
		if (Protocol.equals(TCP)) {
			new usingTCP.Main();
			usingTCP.Main.main(args);
		}

	}

}
