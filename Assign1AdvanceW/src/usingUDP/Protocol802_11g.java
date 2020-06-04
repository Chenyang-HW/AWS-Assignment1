package usingUDP;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Protocol802_11g {

	static int SIFSt = 10;
	static int Slott = 9;
	static int DIFSt;

	static int Preamblet;
	static int Extension=6;//Signal Extension appended for 802.11g

	static int RTSf = 20;
	static int RTSt;
	static int CTSf = 14;
	static int CTSt;
	static int DATAf = 1500;
	static int DATAt;
	static int ACKf = 14;
	static int ACKt;
	static int Tailf = 6;
	
	static int MACHEADERf = 34;
	static int SNAPLLAf = 8;
	static int DATAbit;

	static int Symboltime = 4;

	static BigInteger totalframe = Main.totalframe;
	static BigInteger onceframe = new BigInteger("1500");
	static String DataRate = Main.DataRate;
	
	/**
	 * @author Chenyang_16206550 
	 * A single data frame exchange equation:
	 *         DIFS+RTS+SIFS+CTS+SIFS+DATA+SIFS+ACK 
	 * The follow three parts:
	 *         Throughtput(); Oncetime(); Totaltime(): 
	 * Throughtput(): to calculate
	 *         Throughput: to calculate Once Transmission Time
	 *         Onceframe is 1500;
	 * Oncetime equation is:
	 *         DIFSt+Preamblet+RTSt+Extension+SIFSt+Preamblet+CTSt+Extension+SIFSt+Preamblet+DATAt+Extension+SIFSt+Preamblet+ACKt+Extension
	 * Throughtput Equation is: 
	 * 		   Once_frame/Once_time
	 * 
	 * TotalTime Equation is:
	 * 		  OnceTime*TransmissionTimes
	 */

	public static double Oncetime() {// Real
		Preamblet = 20;
		
		double oncetime = DIFSt() + Preamblet + RTSt() + Extension + SIFSt + Preamblet + CTSt() + Extension + SIFSt
				+ Preamblet + DATAt() + Extension + SIFSt + Preamblet + ACKt() + Extension;
		return oncetime;
	}
	
	public static BigDecimal Throughtput() {// Real
		BigDecimal OnceframeD = new BigDecimal(onceframe);
		BigDecimal OnceframeDb=OnceframeD.multiply(BigDecimal.valueOf(8));
		BigDecimal throughtput;
		BigDecimal Oncetime = new BigDecimal(Oncetime());
		
		throughtput = OnceframeDb.divide(Oncetime, 4, BigDecimal.ROUND_HALF_UP);
		return throughtput;
	}
	
	public static BigDecimal Totaltime() {// Real
		BigDecimal totaltime;
		
		BigInteger TimesBM = totalframe.divide(onceframe);
		BigInteger TimesB = TimesBM.add(BigInteger.valueOf(1));
		BigDecimal oncetimeB = BigDecimal.valueOf(Oncetime());
		totaltime = oncetimeB.multiply(new BigDecimal(TimesB));
		return totaltime;
	}
	
	/**
	 * @author Chenyang_16206550
	 *
	 */
	public static int DIFSt() {					// Waiting DIFS time
		DIFSt = 2 * Slott + SIFSt;
		return DIFSt;
	}

	public static int RTSt() {					// RTS transmission time
		int RTSSymbols = ((RTSf * 8 + Tailf) % DATAbit()) == 0 ? ((RTSf * 8 + Tailf) / DATAbit())
				: ((RTSf * 8 + Tailf) / DATAbit() + 1);
		RTSt = RTSSymbols * Symboltime;
		return RTSt;
	}

	public static int CTSt() {					// DIFS transmission time
		int CTSSymbols = ((CTSf * 8 + Tailf) % DATAbit()) == 0 ? ((CTSf * 8 + Tailf) / DATAbit())
				: ((CTSf * 8 + Tailf) / DATAbit() + 1);
		CTSt = CTSSymbols * Symboltime;
		return CTSt;
	}

	public static int DATAbit() {				// Data bits per OFDM symbol
		BlockSizeG bs = new BlockSizeG();
		DATAbit = (int) ((bs.NBit * bs.CRateN * bs.NChan) / bs.CRateD);
		return DATAbit;
	}

	public static int DATAt() {					// Total size for once transmission: TotalDATAf = DATAf + MACHEADERf + SNAPLLAf
		int TotalDATAf = DATAf + MACHEADERf + SNAPLLAf;
		int DATASymbols = ((TotalDATAf * 8 + Tailf) % DATAbit()) == 0 ? ((TotalDATAf * 8 + Tailf) / DATAbit())
				: (((TotalDATAf * 8 + Tailf) / DATAbit()) + 1);
		DATAt = DATASymbols * Symboltime;
		return DATAt;
	}

	public static int ACKt() {					// ACK transmission time
		int ACKSymbols = ((ACKf * 8 + Tailf) % DATAbit()) == 0 ? ((ACKf * 8 + Tailf) / DATAbit())
				: ((ACKf * 8 + Tailf) / DATAbit() + 1);
		ACKt = ACKSymbols * Symboltime;
		return ACKt;
	}
	
}
class BlockSizeG {								// Encoding Block Sizes for 802.11a

	int NBit;
	int CRateN;								    // numerator of CRate
	int CRateD;                                 // denominator of CRate
	int NChan;
	static String DataRate = Main.DataRate;		   // Get from User's input

	public BlockSizeG() {
		switch (DataRate) {
		case ("54"):
			this.NBit = 6;
			this.CRateN = 3;
			this.CRateD = 4;
			this.NChan = 48;
			break;
		case ("48"):
			this.NBit = 6;
			this.CRateN = 2;
			this.CRateD = 3;
			this.NChan = 48;
			break;
		case ("36"):
			this.NBit = 4;
			this.CRateN = 3;
			this.CRateD = 4;
			this.NChan = 48;
			break;
		case ("24"):
			this.NBit = 4;
			this.CRateN = 1;
			this.CRateD = 2;
			this.NChan = 48;
			break;
		case ("18"):
			this.NBit = 2;
			this.CRateN = 3;
			this.CRateD = 4;
			this.NChan = 48;
			break;
		case ("12"):
			this.NBit = 2;
			this.CRateN = 1;
			this.CRateD = 2;
			this.NChan = 48;
			break;
		case ("9"):
			this.NBit = 1;
			this.CRateN = 3;
			this.CRateD = 4;
			this.NChan = 48;
			break;
		case ("6"):
			this.NBit = 1;
			this.CRateN = 1;
			this.CRateD = 2;
			this.NChan = 48;
			break;
		default:
			System.out.println("default");
			break;
		}
	}

}
