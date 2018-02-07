/**
 * 
 */
package SBVdcs;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author §E
 *
 */
public class SBVClass {

	public static String getUTCTimeFormat(String s,Date d) {
		DateFormat df = new SimpleDateFormat(s);
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		return df.format(d);
	}
	
	public static String getDecimalFormat(String s,BigDecimal d) {
		DecimalFormat df = new DecimalFormat(s);
		return df.format(d);
	}
	
	public static class GrpHdr {
		@XmlElement(name="MsgId")
		public String MsgId;
		
		private Date CreDtTm;
		
		@XmlElement(name="CreDtTm")
		public String getCreDtTm() {
			return SBVClass.getUTCTimeFormat("YYYY-MM-DD'T'hh:mm:ss", CreDtTm);
		}
		
		public void setCreDtTm(Date d) {
			CreDtTm = d;
		}
	}
}
