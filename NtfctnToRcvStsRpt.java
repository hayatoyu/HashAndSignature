/**
 * 
 */
package SBVdcs;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author §E
 *
 */

@XmlRootElement(name="NtfctnToRcvStsRpt")
public class NtfctnToRcvStsRpt {

	@XmlElement(name="GrpHdt")
	public SBVClass.GrpHdr GrpHdr;
	
	@XmlElement(name="OrgnlNtfctnAndSts")
	public OrgnlNtfctnAndSts OrgnlNtfctnAndSts = new OrgnlNtfctnAndSts();
	
	public static class OrgnlNtfctnAndSts {
		
		@XmlElement(name="OrgnlMsgId")
		public String OrgnlMsgId;
		
		@XmlElement(name="AddtlStsInf")
		public String AddtlStsInf;
	}
}
