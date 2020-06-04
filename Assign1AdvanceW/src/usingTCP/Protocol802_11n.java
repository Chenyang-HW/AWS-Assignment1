package usingTCP;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Protocol802_11n {

	static double SIFSt = 16;// 802.11n/ac SIFS = 16 μs
	static double Slott = 9;
	static double DIFSt;

	static double Preamblet;
	static int Nss;
	static int NChan;

	static int RTSf = 20;
	static double RTSt;
	static int CTSf = 14;
	static double CTSt;
	static int DATAf = 1500;
	static double DATAt;
	static int ACKf = 14;
	static double ACKt;
	static int Tailf = 6;

	static int MACHEADERf = 40;
	static int TCPACKf = 40;// TCP/IP header
	static double TCPACKt;

	static int SNAPLLAf = 8;
	static int DATAbit;

	static double Symboltime = 3.6;

	static BigInteger totalframe = Main.totalframe;
	static BigInteger onceframe = new BigInteger("1500");
	static String DataRate = Main.DataRate;

	/**
	 * @author Chenyang_16206550 A single data frame exchange equation:
	 *         DIFS+RTS+SIFS+CTS+SIFS+
	 *         TCPDATA+SIFSt+ACK+DIFS++RTS+SIFS+CTS+SIFS+TCPACK+SIFS+ACK; The follow
	 *         three parts: Throughtput(); Totaltime(); Totalframe(): Throughtput():
	 *         to calculate Throughput Total_time(): to calculate Total Transmission
	 *         Time Total_frame(): to store the total data frame 10GB=10737418240;
	 *         Total_time Equation is:
	 *         DIFSt+Preamblet+RTSt+SIFSt+Preamblet+CTSt+SIFSt+Preamblet+TCPDATAt+SIFSt+Preamblet+ACKt+DIFSt+Preamblet
	 *         +RTSt+SIFSt+Preamblet+CTSt+SIFSt+Preamblet+TCPACKt+SIFSt+Preamblet+ACKt;
	 *         Throughtput Equation is: Total_frame/Total_time
	 */
	public static double OncetimeR() {// Real
		Nss=1;
		Preamblet = 20;
		NChan=52;
		
		double oncetime = DIFSt() + Preamblet + RTSt() + SIFSt + Preamblet + CTSt() + SIFSt + Preamblet + DATAt()
				+ SIFSt + Preamblet + ACKt() + DIFSt() + Preamblet + RTSt() + SIFSt + Preamblet + CTSt() + SIFSt
				+ Preamblet + TCPACKt() + SIFSt + Preamblet + ACKt();
		return oncetime;
	}
	
	public static BigDecimal ThroughtputR() {// Real
		BigDecimal OnceframeD = new BigDecimal(onceframe);
		BigDecimal OnceframeDb=OnceframeD.multiply(BigDecimal.valueOf(8));
		BigDecimal throughtputR;
		BigDecimal OncetimeR = new BigDecimal(OncetimeR());
		
		throughtputR = OnceframeDb.divide(OncetimeR, 4, BigDecimal.ROUND_HALF_UP);
		return throughtputR;
	}
	
	public static double OncetimeB() {// Best
		Nss=4;
		Preamblet = 46;
		NChan=108;
		
		double oncetime = DIFSt() + Preamblet + RTSt() + SIFSt + Preamblet + CTSt() + SIFSt + Preamblet + DATAt()
				+ SIFSt + Preamblet + ACKt() + DIFSt() + Preamblet + RTSt() + SIFSt + Preamblet + CTSt() + SIFSt
				+ Preamblet + TCPACKt() + SIFSt + Preamblet + ACKt();
		return oncetime;
	}
	 
	public static BigDecimal ThroughtputB() {// Best
		BigDecimal OnceframeD = new BigDecimal(onceframe);
		BigDecimal OnceframeDb=OnceframeD.multiply(BigDecimal.valueOf(8));
		BigDecimal throughtputB;
		BigDecimal OncetimeB = new BigDecimal(OncetimeB());
		
		throughtputB = OnceframeDb.divide(OncetimeB, 4, BigDecimal.ROUND_HALF_UP);
		return throughtputB;
	}

	public static BigDecimal TotaltimeR() {// Real
		BigDecimal totaltime;
		
		BigInteger TimesBM = totalframe.divide(onceframe);
		BigInteger TimesB = TimesBM.add(BigInteger.valueOf(1));
		BigDecimal oncetimeB = BigDecimal.valueOf(OncetimeR());
		totaltime = oncetimeB.multiply(new BigDecimal(TimesB));
		return totaltime;
	}

	public static BigDecimal TotaltimeB() {// Best
		BigDecimal totaltime;
		
		BigInteger TimesBM = totalframe.divide(onceframe);
		BigInteger TimesB = TimesBM.add(BigInteger.valueOf(1));
		BigDecimal oncetimeB = BigDecimal.valueOf(OncetimeB());
		totaltime = oncetimeB.multiply(new BigDecimal(TimesB));
		return totaltime;
	}
	/**
	 * @author Chenyang_16206550
	 *
	 */
	public static double DIFSt() { // Waiting DIFS time
		DIFSt = 2 * Slott + SIFSt;
		return DIFSt;
	}

	public static double RTSt() { // RTS transmission time
		int RTSSymbols = (int) (((RTSf * 8 + Tailf) % DATAbit()) == 0 ? ((RTSf * 8 + Tailf) / DATAbit())
				: ((RTSf * 8 + Tailf) / DATAbit() + 1));
		RTSt = RTSSymbols * Symboltime;
		return RTSt;
	}

	public static double CTSt() { // DIFS transmission time
		double CTSSymbols = ((CTSf * 8 + Tailf) % DATAbit()) == 0 ? ((CTSf * 8 + Tailf) / DATAbit())
				: ((CTSf * 8 + Tailf) / DATAbit() + 1);
		CTSt = CTSSymbols * Symboltime;
		return CTSt;
	}

	public static int DATAbit() { // Data bits per OFDM symbol
		BlockSizeN bs = new BlockSizeN();
		DATAbit = ((bs.NBit * bs.CRateN * NChan) / bs.CRateD) * Nss;
		return DATAbit;
	}

	public static double DATAt() { // Total size for once transmission: TotalDATAf = DATAf + MACHEADERf + SNAPLLAf
		int TotalDATAf = DATAf + MACHEADERf + SNAPLLAf;
		int DATASymbols = ((TotalDATAf * 8 + Tailf) % DATAbit()) == 0 ? ((TotalDATAf * 8 + Tailf) / DATAbit())
				: (((TotalDATAf * 8 + Tailf) / DATAbit()) + 1);
		DATAt = DATASymbols * Symboltime;
		return DATAt;
	}

	public static double ACKt() { // ACK transmission time
		int ACKSymbols = ((ACKf * 8 + Tailf) % DATAbit()) == 0 ? ((ACKf * 8 + Tailf) / DATAbit())
				: ((ACKf * 8 + Tailf) / DATAbit() + 1);
		ACKt = ACKSymbols * Symboltime;
		return ACKt;
	}

	public static double TCPACKt() { // TCPACK transmission time
		int TotalACKf = TCPACKf + MACHEADERf + SNAPLLAf;
		int TCPACKSymbols = ((TotalACKf * 8 + 6) % DATAbit()) == 0 ? ((TotalACKf * 8 + 6) / DATAbit())
				: ((TotalACKf * 8 + 6) / DATAbit() + 1);
		TCPACKt = TCPACKSymbols * Symboltime;
		return TCPACKt;
	}

}

// 72.2, 65, 57.8, 43.3, 28.9, 21.7, 14.4, 7.2
class BlockSizeN { // Encoding Block Sizes for 802.11a

	int NBit;
	int CRateN; // numerator of CRate
	int CRateD; // denominator of CRate
	static String DataRate = Main.DataRate; // Get from User's input

	public BlockSizeN() {
		switch (DataRate) {
		case ("72.2"):
			this.NBit = 6;
			this.CRateN = 5;
			this.CRateD = 6;
			break;
		case ("65"):
			this.NBit = 6;
			this.CRateN = 3;
			this.CRateD = 4;
			break;
		case ("57.8"):
			this.NBit = 6;
			this.CRateN = 2;
			this.CRateD = 3;
			break;
		case ("43.3"):
			this.NBit = 4;
			this.CRateN = 3;
			this.CRateD = 4;
			break;
		case ("28.9"):
			this.NBit = 4;
			this.CRateN = 1;
			this.CRateD = 2;
			break;
		case ("21.7"):
			this.NBit = 2;
			this.CRateN = 3;
			this.CRateD = 4;
			break;
		case ("14.4"):
			this.NBit = 2;
			this.CRateN = 1;
			this.CRateD = 2;
			break;
		case ("7.2"):
			this.NBit = 1;
			this.CRateN = 1;
			this.CRateD = 2;
			break;
		default:
			System.out.println("default");
			break;
		}
	}

}
