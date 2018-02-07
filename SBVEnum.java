/**
 * 
 */
package SBVdcs;

/**
 * @author §E
 *
 */
public class SBVEnum {

	public static class ReqdMsgNmEnum {
		public static String AccountReport = "camt.052.001.06";
		public static String AccountStatement = "camt.053.001.06";
	}
	
	/*
	public static class AccountType {
		public static String Current = "CACC";
		public static String CashPayment = "CASH";
		public static String Charges = "CHAR";
		public static String CashIncome = "CISH";
		public static String Loan = "Loan";
		public static String Settlement = "SACC";
		public static String Savings = "SVGS";
		public static String TransactingAccount = "TRAN";
		public static String OtherAccount = "OTHR";
	}
	*/
	
	public enum AccountType {
		CACC,	//Current
		CASH,	//CashPayment
		CHAR,	//Charges
		CISH,	//CashIncome
		LOAN,	//Loan
		SACC,	//Settlement
		SVGS,	//Savings
		TRAN,	//TransactingAccount
		OTHR	//OtherAccount
	}
	
	public enum BalanceCode {
		OPBD,	//Opening balance
		INFO,	//Available balance
		CLBD,	//Closing balance
		ITBD	//Balance in the period
	}
	
	public enum Currency {
		AED,AFN,ALL,AMD,ANG,AOA,ARS,AUD,AWG,AZN,
		BAM,BBD,BDT,BGN,BHD,BIF,BMD,BND,BOB,BOV,BRL,BSD,BTN,BWP,BYN,BZD,
		CAD,CDF,CHE,CHF,CHW,CLF,CLP,CNY,COP,COU,CRC,CUC,CUP,CVE,CZK,
		DJF,DKK,DOP,DZD,
		EGP,ERN,ETB,EUR,
		FJD,FKP,
		GBP,GEL,GHS,GIP,GMD,GNF,GTQ,GYD,
		HKD,HNL,HRK,HTG,HUF,
		IDR,ILS,INR,IQD,IRR,ISK,
		JMD,JOD,JPY,
		KES,KGS,KHR,KMF,KPW,KRW,KWD,KYD,KZT,
		LAK,LBP,LKR,LRD,LSL,LYD,
		MAD,MDL,MGA,MKD,MMK,MNT,MOP,MRO,MUR,MVR,MWK,MXN,MXV,MYR,MZN,
		NAD,NGN,NIO,NOK,NPR,NZD,
		OMR,
		PAB,PEN,PGK,PHP,PKR,PLN,PYG,
		QAR,
		RON,RSD,RUB,RWF,
		SAR,SBD,SCR,SDG,SEK,SGD,SHP,SLL,SOS,SRD,SSP,STD,SYP,SZL,
		THB,TJS,TMT,TND,TOP,TRY,TTD,TWD,TZS,
		UAH,UGX,USD,USN,USS,UYI,UYU,UZS,
		VEF,VND,VUV,
		WST,
		XAF,XAG,XAU,XBA,XBB,XBC,XBD,XCD,XDR,XFU,XOF,XPD,XPF,XPT,XSU,XTS,XUA,XXX,
		YER,
		ZAR,ZMW
	}
	
	public enum CdtDbtInd {
		CRDT,	//Credit transaction
		DBIT	//Debit transaction
	}
	

}
