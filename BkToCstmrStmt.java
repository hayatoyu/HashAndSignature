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

@XmlRootElement(name="BkToCstmrStmt")
public class BkToCstmrStmt {

	@XmlElement(name="GrpHdr")
	public SBVClass.GrpHdr GrpHdr = new SBVClass.GrpHdr();
	
	@XmlElement(name="Stmt")
	public Stmt[] Stmt;
	
	public static class Stmt {
		
		@XmlElement(name="Id")
		public String Id;	// Message Code(Identification)
		
		private Date CreDtTm;
		
		public void setCreDtTm(Date d) {
			CreDtTm = d;
		}
		
		@XmlElement(name="CreDtTm")
		public String getCreDtTm() {
			return SBVClass.getUTCTimeFormat("YYYY-MM-DD'T'hh:mm:ss", CreDtTm);
		}
		
		@XmlElement(name="FrToDt")
		public FrToDt FrToDt;
		
		@XmlElement(name="Acct")
		public Acct Acct = new Acct();
		
		@XmlElement(name="Bal")
		public Bal[] Bal;
		
		@XmlElement(name="Ntry")
		public Ntry[] Ntry;
	}
	
	public static class FrToDt {
		private Date FrDtTm;
		private Date ToDtTm;
		
		public void setFrDtTm(Date d) {
			FrDtTm = d;
		}
		
		public void setToDtTm(Date d) {
			ToDtTm = d;
		}
		
		@XmlElement(name="FrDtTm")
		public String getFrDtTm() {
			return SBVClass.getUTCTimeFormat("YYYY-MM-DD'T'hh:mm:ss", FrDtTm);
		}
		
		@XmlElement(name="ToDtTm")
		public String getToDtTm() {
			return SBVClass.getUTCTimeFormat("YYYY-MM-DD'T'hh:mm:ss", ToDtTm);
		}
	}
	
	public static class Acct {
		
		@XmlElement(name="Id")
		public Stmt_Acct_Id Id = new Stmt_Acct_Id();
		
		@XmlElement(name="Tp")
		public Stmt_Acct_Tp Tp = new Stmt_Acct_Tp();
		
		@XmlElement(name="Ccy")
		public SBVEnum.Currency Ccy;
		
		@XmlElement(name="Ownr")
		public Stmt_Acct_Ownr Ownr = new Stmt_Acct_Ownr();
	}
	
	public static class Stmt_Acct_Id {
		
		@XmlElement(name="Othr")
		public Stmt_Acct_Id_Othr Othr = new Stmt_Acct_Id_Othr();
	}
	
	public static class Stmt_Acct_Id_Othr {
		
		@XmlElement(name="Id")
		public String Id;	//Account number
	}
	
	public static class Stmt_Acct_Tp {
		
		@XmlElement(name="Cd")
		public SBVEnum.AccountType Cd;
	}
	
	public static class Stmt_Acct_Ownr {
		
		@XmlElement(name="Nm")
		public String Nm;
		
		@XmlElement(name="PstlAdr")
		public PstlAdr PstlAdr;
	}
	
	public static class PstlAdr {
		
		@XmlElement(name="BldgNb")
		public String BldgNb;	//Building Number
		
		@XmlElement(name="StrtNm")
		public String StrtNm;	//Street Name
		
		@XmlElement(name="TwnNm")
		public String TwnNm;	//District(Town Name)
		
		@XmlElement(name="PstCd")
		public String PstCd;	//Postal Code;6 digits
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
			return SBVClass.getDecimalFormat("#################0.00000", Amt);
		}
		
		@XmlElement(name="Ccy")
		public SBVEnum.Currency Ccy;
		
		@XmlElement(name="CdtDbtInd")
		public SBVEnum.CdtDbtInd CdtDbtInd;
		
		@XmlElement(name="Dt")
		public Dt Dt = new Dt();
	}
	
	public static class Stmt_Bal_Tp {
		
		@XmlElement(name="CdOrPrtry")
		public CdOrPrtry CdOrPrtry = new CdOrPrtry();
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
		}
		
		@XmlElement(name="Ccy")
		public SBVEnum.Currency Ccy;
		
		@XmlElement(name="CdtDbtInd")
		public SBVEnum.CdtDbtInd CdtDbtInd;
		
		@XmlElement(name="Sts")
		public String Sts = "INFO";
		
		@XmlElement(name="ValDt")
		public ValDt ValDt = new ValDt();
		
		@XmlElement(name="AcctSvcrRef")
		public String AcctSvcrRef;	//Document Ref. No.
		
		@XmlElement(name="Avlbty")
		public Avlbty[] Avlbty;
		
		@XmlElement(name="NtryDtls")
		public NtryDtls[] NtryDtls;
		
	}
	
	public static class ValDt {
		
		private Date Dt;	//Transaction date
		
		public void setDt(Date d) {
			Dt = d;
		}
		
		@XmlElement(name="Dt")
		public String getDt() {
			return SBVClass.getUTCTimeFormat("YYYY-MM-DD", Dt);
		}
	}
	
	public static class Avlbty {
		
		private Date Dt;
		
		public void setDt(Date d) {
			Dt = d;
		}
		
		@XmlElement(name="Dt")
		public String getDt() {
			return SBVClass.getUTCTimeFormat("YYYY-MM-DD", Dt);
		}
		
		private BigDecimal Amt;
		
		public void setAmt(String s) {
			Amt = new BigDecimal(s);
		}
		
		@XmlElement(name="Amt")
		public String getAmt() {
			return SBVClass.getDecimalFormat("#################0.00000", Amt);
		}
		
		@XmlElement(name="Ccy")
		public SBVEnum.Currency Ccy;
		
		@XmlElement(name="CdtDbtInd")
		public SBVEnum.CdtDbtInd CdtDbtInd;
		
	}
	
	public static class NtryDtls {
		
		@XmlElement(name="TxDtls")
		public TxDtls[] TxDtls;
	}
	
	public static class TxDtls {
		
		@XmlElement(name="AddtlTxInf")
		public String AddtlTxInf;	//Transaction details
	}
}
