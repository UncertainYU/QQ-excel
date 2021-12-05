package ProcessData;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.OutputStream;
import java.util.ArrayList;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ProcessDataClass {
	private String content="";//读入data内容
	private int id=0;//共计多少个好友
	private ArrayList<String> datalist=new ArrayList<String>();//数据列表
	//读入data
	public void getData(File fp) throws Exception {
		BufferedReader in=new BufferedReader(new FileReader(fp));
		String line;
		while((line=in.readLine())!=null) {
			content+=line;
		}
		System.out.println("数据读入完毕");
	}
	//处理data
	public void classifydata() throws Exception{
		String test1[];
		test1=content.split("\"uin\"");
		int i=0;
		for(String test:test1) {
			String uid,name,remark;//代表QQ，名称，以及备注
			if(test.contains("name")==true) {
			uid=test.substring(test.indexOf(":")+1, test.indexOf(","));
			name=test.substring(test.indexOf("name")+7, test.indexOf("remark")-4);
			remark=test.substring(test.indexOf("remark")+9, test.indexOf("img")-4);
			datalist.add(uid+"#"+name+"#"+remark);
			//#号用于分割，为输出数据做准备
			i++;
			}
		};
		System.out.println("数据处理完毕");
	}
	//输出data
	public void writedataExcel(OutputStream os) throws Exception {
		WritableWorkbook wb=Workbook.createWorkbook(os);
		WritableSheet ws=wb.createSheet("data", 0);
		Label t1=new Label(0,0,"QQ号");
		ws.addCell(t1);
		Label t2=new Label(1,0,"昵称");
		ws.addCell(t2);
		Label t3=new Label(2,0,"备注");
		ws.addCell(t3);
		int i=0,j=1;
		for(String test:datalist) {
			String t[]=test.split("#");
			for(String test1:t) {
				Label a=new Label(i,j,test1);
				ws.addCell(a);
				i++;
			}
			j++;i=0;
		}
		wb.write();
		wb.close();
		os.close();
		System.out.println("数据输出完毕");
	}
}
