/**
 * 
 */
package SBVdcs;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



/**
 * @author §E
 *
 */
public class BAHeader {

	public AppHdr AppHdr = new AppHdr();

	@XmlRootElement(name="AppHdr")
	public static class AppHdr {
		
		@XmlElement(name="CharSet")
		public String CharSet;
		
		@XmlElement(name="Fr")
		public Fr Fr = new Fr();
		
		@XmlElement(name="To")
		public To To = new To();
		
		@XmlElement(name="BizMsgIdr")
		public String BizMsgIdr;
		
		@XmlElement(name="MsgDefIdr")
		public String MsgDefIdr;
		
		@XmlElement(name="CreDt")
		public Date CreDt;

		@XmlElement(name="Sgntr")
		public String Sgntr;
		
		public String getCreDt() {
			return SBVClass.getUTCTimeFormat("YYYY-MM-DD'T'hh:mm:ss", CreDt);
		}

		public void setCreDt(Date creDt) {
			CreDt = creDt;
		}
	}
	
	public static class Fr {
		
		@XmlElement(name="OrgId")
		public Fr_OrgId OrgId = new Fr_OrgId();
	}
	
	public static class Fr_OrgId {
		
		@XmlElement(name="Nm")
		public String Nm;
		
		@XmlElement(name="Id")
		public Fr_OrgId_Id Id = new Fr_OrgId_Id();
	}
	
	public static class Fr_OrgId_Id {
		@XmlElement(name="OrgId")
		public Fr_OrgId_Id_OrgId OrgId = new Fr_OrgId_Id_OrgId();
	}
	
	public static class Fr_OrgId_Id_OrgId {
		@XmlElement(name="Othr")
		public Fr_OrgId_Id_OrgId_Othr Othr = new Fr_OrgId_Id_OrgId_Othr();
	}
	
	public static class Fr_OrgId_Id_OrgId_Othr {
		@XmlElement(name="Id")
		public String Id;
	}
	
	public static class To {
		@XmlElement(name="OrgId")
		public To_OrgId OrgId = new To_OrgId();
	}
	
	public static class To_OrgId {
		@XmlElement(name="Nm")
		public String Nm;
		@XmlElement(name="Id")
		public To_OrgId_Id Id = new To_OrgId_Id();
	}
	
	public static class To_OrgId_Id {
		@XmlElement(name="OrgId")
		public To_OrgId_Id_OrgId OrgId = new To_OrgId_Id_OrgId();
	}
	
	public static class To_OrgId_Id_OrgId {
		@XmlElement(name="Othr")
		public To_OrgId_Id_OrgId_Othr Othr = new To_OrgId_Id_OrgId_Othr();
	}
	
	public static class To_OrgId_Id_OrgId_Othr {
		@XmlElement(name="Id")
		public String Id;
	}
}

