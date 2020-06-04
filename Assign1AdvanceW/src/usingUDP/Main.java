package usingUDP;

import java.math.BigInteger;

public class Main {

	static String DataRate;
	static String Standard;
	static String Protocol;

	static BigInteger totalframe = new BigInteger("10737418240");

	static String a = "802.11a";
	static String ac1 = "802.11ac1";
	static String ac2 = "802.11ac2";
	static String ax = "802.11ax";
	static String g = "802.11g";
	static String n = "802.11n";

	public static void main(String[] args) {

		Standard = RUNING.Main.Standard;
		Protocol = RUNING.Main.Protocol;
		DataRate = RUNING.Main.DataRate;
		System.out.println("--------------------------------------------------");
		System.out.println("The actual throughput [Mbps] in the normal case and in best case:");
		System.out.println("TotalFrame(10GB): " + totalframe);
		System.out.println("Note: 802.11a and 802.11g only have value in the normal case");
		System.out.println("--------------------------------------------------");
		Display display = new Display();

	}

}

class Display {

	public Display() {

		if (Main.Standard.equals(Main.a)) {
			System.out.println("Throughput [Mbps] in the normal case: " + Protocol802_11a.Throughtput());//
			System.out.println("The total time to transmit 10GB(μs): " + Protocol802_11a.Totaltime());
		}
		if (Main.Standard.equals(Main.ac1)) {
			System.out.println("Throughput [Mbps] in the normal case: " + Protocol802_11ac_wav1.ThroughtputR());//
			System.out.println("The total time to transmit 10GB(μs): " + Protocol802_11ac_wav1.TotaltimeR());
			System.out.println("Throughput [Mbps] in the best case: " + Protocol802_11ac_wav1.ThroughtputB());// }
			System.out.println("The total time to transmit 10GB(μs): " + Protocol802_11ac_wav1.TotaltimeB());
		}
		if (Main.Standard.equals(Main.ac2)) {
			System.out.println("Throughput [Mbps] in the normal case: " + Protocol802_11ac_wav2.ThroughtputR());//
			System.out.println("The total time to transmit 10GB(μs): " + Protocol802_11ac_wav2.TotaltimeR());
			System.out.println("Throughput [Mbps] in the best case: " + Protocol802_11ac_wav2.ThroughtputB());// }
			System.out.println("The total time to transmit 10GB(μs): " + Protocol802_11ac_wav2.TotaltimeB());
		}
		if (Main.Standard.equals(Main.ax)) {
			System.out.println("Throughput [Mbps] in the normal case: " + Protocol802_11ax.ThroughtputR());//
			System.out.println("The total time to transmit 10GB(μs): " + Protocol802_11ax.TotaltimeR());
			System.out.println("Throughput [Mbps] in the best case: " + Protocol802_11ax.ThroughtputB());// }
			System.out.println("The total time to transmit 10GB(μs): " + Protocol802_11ax.TotaltimeB());
		}
		if (Main.Standard.equals(Main.g)) {
			System.out.println("Throughput [Mbps] in the normal case: " + Protocol802_11g.Throughtput());
			System.out.println("The total time to transmit 10GB(μs): " + Protocol802_11g.Totaltime());
		}
		if (Main.Standard.equals(Main.n)) {
			System.out.println("Throughput [Mbps] in the normal case: " + Protocol802_11n.ThroughtputR());//
			System.out.println("The total time to transmit 10GB(μs): " + Protocol802_11n.TotaltimeR());
			System.out.println("Throughput [Mbps] in the best case: " + Protocol802_11n.ThroughtputB());//
			System.out.println("The total time to transmit 10GB(μs): " + Protocol802_11n.TotaltimeB());
		}
	}
}
