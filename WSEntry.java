/**
 * 
 */
package SBVdcs;

import java.util.Date;

import javax.xml.bind.JAXBException;



/**
 * @author §E
 *
 */
public class WSEntry {

	public void receive(String msg) {
		System.out.println(msg);
	}
	
	public String send() {
		return "this message from WSEntry";
	}
	
	
	public String ReceiveRequest() {
		
		// Receive the request from SBV
		
		// Check the signature
		
		// Check the request type
		
		// return data
		
		
		BAHeader header = new BAHeader();
		
		header.AppHdr.Fr.OrgId.Nm = "SCSB";
		header.AppHdr.Fr.OrgId.Id.OrgId.Othr.Id = "12345678";
		
		header.AppHdr.To.OrgId.Nm = "SBV";
		header.AppHdr.To.OrgId.Id.OrgId.Othr.Id = "87654321";
		
		header.AppHdr.BizMsgIdr = "This is a test message";
		header.AppHdr.MsgDefIdr = "camt.053.001.06";
		header.AppHdr.CreDt = new Date();
		header.AppHdr.Sgntr = "Not ready yet lol";
		
		try {
			return BeanExchangeXml.beanToXml(header.AppHdr, BAHeader.AppHdr.class);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}
	}
	

	
}
