/**
 * 
 */
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
/**
 * @author §E
 *
 */
public class BeanExchangeXml {

	/*
	 * Serialize java object To Xml
	 * @param xmlPath
	 * */
	public static String beanToXml(Object obj,Class<?> load) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(load);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
		StringWriter writer = new StringWriter();
		marshaller.marshal(obj, writer);
		return writer.toString();
	}
}
