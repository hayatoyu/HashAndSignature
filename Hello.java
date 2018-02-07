
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.json.*;

public class Hello {

	public static void main(String[] args) throws JAXBException {
		// TODO Auto-generated method stub
		
		BAHeader header = new BAHeader();
		
		//header.AppHdr.CharSet = "UTF8";
		
		//header.AppHdr.Fr.OrgId.Nm = "This is a testing unit.";
		//header.AppHdr.Fr.OrgId.Id.OrgId.Othr.Id = "12345678";
		header.AppHdr.Fr.OrgId.Id = null;
		
		header.AppHdr.To.OrgId.Nm = "This is a testing unit";
		header.AppHdr.To.OrgId.Id.OrgId.Othr.Id = "87654321";
		
		header.AppHdr.BizMsgIdr = "Test";
		
		header.AppHdr.MsgDefIdr = "camt.060.001.03";
		header.AppHdr.CreDt = new Date();
		header.AppHdr.Sgntr = "This is a test";
		
		String str = BeanExchangeXml.beanToXml(header.AppHdr, BAHeader.AppHdr.class);
		
		System.out.println(str);
		
		Date date = new Date();
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(date));
		System.out.println(new SimpleDateFormat("hh:mm:ss").format(date));
		
		/*
		List<String> hobby = new ArrayList();
		hobby.add("籃球");
		hobby.add("棒球");
		hobby.add("音樂");
		hobby.add("乒乓球");
		
		List<Student> studentList = new ArrayList();
		Student st1 = new Student("張三","男",1,"資優班",hobby);
		Student st2 = new Student("李四","男",2,"普通班",hobby);
		Student st3 = new Student("小華","女",3,"技職班",hobby);
		studentList.add(st1);
		studentList.add(st2);
		studentList.add(st3);
		
		StudentList students = new StudentList();
		students.setStudents(studentList);
		
		String str = BeanExchangeXml.beanToXml(students, StudentList.class);
		
		System.out.println(str);
		*/
		
		/*
		JSONObject obj = new JSONObject();
		try {
			obj.put("A", "aaa");
			obj.put("B", 123);
		} catch(Exception e) {
			
		}
		*/
		
	}

	
}
