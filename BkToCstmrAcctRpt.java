/**
 * 
 */
package SBVdcs;

import java.math.BigDecimal;

import java.util.Date;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author §E
 *
 */
@XmlRootElement(name="BkToCstmrAcctRpt")
public class BkToCstmrAcctRpt {

	@XmlElement(name="GrpHdr")
	public SBVClass.GrpHdr GrpHdr = new SBVClass.GrpHdr();
	
	@XmlElement(name="Stmt")
	public Stmt[] Stmt;
	
	
	
	public static class Stmt {
		
		@XmlElement(name="Id")
		public String Id;	// Message Code(Identification)
		
		private Date CreDtTm;
		
		public void setCreDtTm(Date d) {
			this.CreDtTm = d;
		}
		
		@XmlElement(name="CreDtTm")
		public String getCreDtTm() {
			return SBVClass.getUTCTimeFormat("YYYY-MM-DD'T'hh:mm:ss", CreDtTm);
		}
		
		@XmlElement(name="Acct")
		public Acct Acct = new Acct();
		
		@XmlElement(name="Bal")
		public Bal[] Bal;
		
		@XmlElement(name="Ntry")
		public Ntry[] Ntry;
	}
	
	public static class Acct {
		
		@XmlElement(name="Id")
		public Stmt_Acct_Id Id = new Stmt_Acct_Id();
		
		@XmlElement(name="Tp")
		public Stmt_Acct_Tp Tp = new Stmt_Acct_Tp();
		
		@XmlElement(name="Ccy")
		public SBVEnum.Currency Ccy;
		
		@XmlElement(name="Ownr")
		public Acct_Ownr Ownr = new Acct_Ownr();
	}
	
	public static class Stmt_Acct_Id {
		
		@XmlElement(name="Othr")
		public Stmt_Acct_Id_Othr Othr = new Stmt_Acct_Id_Othr();
	}
	
	public static class Stmt_Acct_Id_Othr {
		
		@XmlElement(name="Id")
		public String Id;	// Account number (Identification)
	}
	
	public static class Stmt_Acct_Tp {
		
		@XmlElement(name="Cd")
		public SBVEnum.AccountType Cd;		
	}
	
	public static class Acct_Ownr {
		
		@XmlElement(name="Nm")
		public String Nm;	//Name of Account holder
	}
	
	public static class Bal {
		
		@XmlElement(name="Tp")
		public Stmt_Bal_Tp Tp = new Stmt_Bal_Tp();
		
		private BigDecimal Amt;
		
		public void setAmt(String s) {
			Amt = new BigDecimal(s);
		}
		
		@XmlElement(name="Amt")
		public String getAmt() {
			/*
			DecimalFormat df = new DecimalFormat("#################0.00000");
			return df.format(Amt);
			*/
			return SBVClass.getDecimalFormat("#################0.00000", Amt);
		}
		
		@XmlElement(name="Ccy")
		public SBVEnum.Currency Ccy;
		
		@XmlElement(name="Dt")
		public Dt Dt = new Dt();
	}
	
	public static class Stmt_Bal_Tp {
		
		@XmlElement(name="CdOrPrtry")
		public CdOrPrtry CdOrPrtrt = new CdOrPrtry();
	}
	
	public static class CdOrPrtry {
		
		@XmlElement(name="Cd")
		public SBVEnum.BalanceCode Cd;
	}
	
	public static class Dt {
		
		private Date Dt;
		
		public void setDt(Date d) {
			Dt = d;
		}
		
		@XmlElement(name="Dt")
		public String getDt() {
			/*
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			df.setTimeZone(TimeZone.getTimeZone("UTC"));
			return df.format(Dt);
			*/
			return SBVClass.getUTCTimeFormat("YYYY-MM-DD", Dt);
		}
	}
	
	public static class Ntry {
		
		private BigDecimal Amt;
		
		public void setAmt(String s) {
			Amt = new BigDecimal(s);
		}
		
		@XmlElement(name="Amt")
		public String getAmt() {
			
			return SBVClass.getDecimalFormat("#################0.00000", Amt);
			
			/*
			DecimalFormat df = new DecimalFormat("#################0.00000");
			return df.format(Amt);
			*/
		}
		
		@XmlElement(name="Ccy")
		public SBVEnum.Currency Ccy;
		
		@XmlElement(name="CdtDbtInd")
		public SBVEnum.CdtDbtInd CdtDbtInd;
		
		@XmlElement(name="Sts")
		public String Sts = "INFO";	//Unknown CodeSet,default value is INFO.
		
		@XmlElement(name="ValDt")
		public ValDt ValDt = new ValDt();
		
		@XmlElement(name="NtryDtls")
		public NtryDtls[] NtryDtls;
	}
	
	public static class ValDt {
		private Date Dt;
		
		public void setDt(Date d) {
			Dt = d;
		}
		
		@XmlElement(name="Dt")
		public String getDt() {
			/*
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			df.setTimeZone(TimeZone.getTimeZone("UTC"));
			return df.format(Dt);
			*/
			return SBVClass.getUTCTimeFormat("YYYY-MM-DD", Dt);
		}
	}
	
	public static class NtryDtls {
		
		@XmlElement(name="Btch")
		public Btch Btch = new Btch();
	}
	
	public static class Btch {
		
		@XmlElement(name="NbOfTxs")
		public String NbOfTxs;	//Number Of Transactions
	}
}
