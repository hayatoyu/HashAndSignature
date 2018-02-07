/**
 * 
 */
package SBVdcs;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author §E
 *
 */
@XmlRootElement(name="AccountRptgReq")
public class AccountRptgReq {

	@XmlElement(name="GrpHdr")
	public SBVClass.GrpHdr GrpHdr = new SBVClass.GrpHdr();
	
	@XmlElement(name="RptgReq")
	public RptgReq RptgReq = new RptgReq();
	
	public static class RptgReq {
		
		@XmlElement(name="ReqdMsgNmId")
		public SBVEnum.ReqdMsgNmEnum ReqdMsgNmId;  // Requesting type
		
		@XmlElement(name="Acct")
		public Acct Acct = new Acct();
		
		@XmlElement(name="AcctOwnr")
		public AcctOwnr AcctOwnr = new AcctOwnr();
		
		@XmlElement(name="RptgPrd")
		public RptgPrd RptgPrd = new RptgPrd();	// for account statement only
	}
	
	public static class Acct {
		
		@XmlElement(name="Id")
		public RptgReq_Acct_Id  Id = new RptgReq_Acct_Id();
		
		@XmlElement(name="Tp")
		public Tp Tp = new Tp();
		
		@XmlElement(name="Ccy")
		public SBVEnum.Currency Ccy;
	}
	
	public static class RptgReq_Acct_Id {
		
		@XmlElement(name="Othr")
		public RptgReq_Acct_Id_Othr Othr = new RptgReq_Acct_Id_Othr();
	}
	
	public static class RptgReq_Acct_Id_Othr {
		
		@XmlElement(name="Id")
		public String Id;
	}
	
	public static class Tp {
		
		@XmlElement(name="Cd")
		public SBVEnum.AccountType Cd;	//CodeSet for Account type
	}
	
	public static class AcctOwnr {
		
		@XmlElement(name="Pty")
		public Pty Pty = new Pty();
	}
	
	public static class Pty {
		
		@XmlElement(name="Nm")
		public String Nm;
		
		@XmlElement(name="Id")
		public RptgReq_AcctOwnr_Pty_Id Id = new RptgReq_AcctOwnr_Pty_Id();
	}
	
	public static class RptgReq_AcctOwnr_Pty_Id {
		
		// choice only one tag,assign the other to null
		@XmlElement(name="OrgId")
		public RptgReq_AcctOwnr_Pty_Id_OrgId OrgId = new RptgReq_AcctOwnr_Pty_Id_OrgId();
		
		@XmlElement(name="PrvtId")
		public RptgReq_AcctOwnr_Pty_Id_PrvtId PrvtId = new RptgReq_AcctOwnr_Pty_Id_PrvtId();
	}
	
	public static class RptgReq_AcctOwnr_Pty_Id_OrgId {
		
		@XmlElement(name="Othr")
		public RptgReq_AcctOwnr_Pty_Id_OrgId_Othr Othr = new RptgReq_AcctOwnr_Pty_Id_OrgId_Othr();
	}
	
	public static class RptgReq_AcctOwnr_Pty_Id_OrgId_Othr {
		
		@XmlElement(name="Id")
		public String Id;
	}
	
	public static class RptgReq_AcctOwnr_Pty_Id_PrvtId {
		
		@XmlElement(name="Othr")
		public RptgReq_AcctOwnr_Pty_Id_PrvtId_Othr Othr = new RptgReq_AcctOwnr_Pty_Id_PrvtId_Othr();
	}
	
	public static class RptgReq_AcctOwnr_Pty_Id_PrvtId_Othr {
		
		@XmlElement(name="Id")
		public String Id;
	}
	
	public static class RptgPrd {
		
		@XmlElement(name="FrToDt")
		public FrToDt FrToDt = new FrToDt();
		
		@XmlElement(name="FrToTm")
		public FrToTm FrToTm = new FrToTm();
		
		@XmlElement(name="Tp")
		public String Tp = "ALL";	// Unknown CodeSet,Default Value is ALL.
	}
	
	public static class FrToDt {
		
		
		private Date FrDt = new Date();	//YYYY-MM-DD;Start date
		
		public void setFrDt(Date d) {
			this.FrDt = d;
		}
		
		@XmlElement(name="FrDt")
		public String getFrDt() {
			return new SimpleDateFormat("yyyy-MM-dd").format(FrDt);
		}
		
		
		private Date ToDt;	//YYYY-MM-DD;End date
		
		public void setToDt(Date d) {
			this.ToDt = d;
		}
		
		@XmlElement(name="ToDt")
		public String getToDt() {
			return new SimpleDateFormat("yyyy-MM-dd").format(ToDt);
		}
	}
	
	public static class FrToTm {
				
		private Date FrTm;	//hh:mm:ss;Start time
		
		public void setFrTm(Date d) {
			this.FrTm = d;
		}
		
		@XmlElement(name="FrTm")
		public String getFrTm() {
			return new SimpleDateFormat("hh:mm:ss").format(FrTm);
		}
		
		private Date ToTm;	//hh:mm:ss;End time
		
		public void setToTm(Date d) {
			this.ToTm = d;
		}
		
		@XmlElement(name="ToTm")
		public String getToTm() {
			return new SimpleDateFormat("hh:mm:ss").format(ToTm);
		}
	}
}
