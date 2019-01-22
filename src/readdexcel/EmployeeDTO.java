package readdexcel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class EmployeeDTO {
private String eName;
	
	private String ePasswd;
	
	private String eEmail;
	
	private String eAge;
	
	private String eDepartment;
	
	private String eSkill;

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

	public String getePasswd() {
		return ePasswd;
	}

	public void setePasswd(String ePasswd) {
		this.ePasswd = ePasswd;
	}

	public String geteEmail() {
		return eEmail;
	}

	public void seteEmail(String eEmail) {
		this.eEmail = eEmail;
	}

	public String geteAge() {
		return eAge;
	}

	public void seteAge(String eAge) {
		this.eAge = eAge;
	}

	public String geteDepartment() {
		return eDepartment;
	}

	public void seteDepartment(String eDepartment) {
		this.eDepartment = eDepartment;
	}

	public String geteSkill() {
		return eSkill;
	}

	public void seteSkill(String eSkill) {
		this.eSkill = eSkill;
	}

	public EmployeeDTO(String eName, String ePasswd, String eEmail, String eAge, String eDepartment, String eSkill) {
		super();
		this.eName = eName;
		this.ePasswd = ePasswd;
		this.eEmail = eEmail;
		this.eAge = eAge;
		this.eDepartment = eDepartment;
		this.eSkill = eSkill;
	}

	@Override
	public String toString() {
		StringBuffer retBuf = new StringBuffer();
		retBuf.append("Employee Info : Name = ");
		retBuf.append(this.geteName());
		retBuf.append(" , Password = ");
		retBuf.append(this.getePasswd());
		retBuf.append(" , Email = ");
		retBuf.append(this.geteEmail());
		retBuf.append(" , Age = ");
		retBuf.append(this.geteAge());
		retBuf.append(" , Department = ");
		retBuf.append(this.geteDepartment());
		retBuf.append(" , Skill = ");
		retBuf.append(this.geteSkill());
		return retBuf.toString();
	}
	/* Use Apacje POI to create a xlsx format excel file. 
	 * filePath : The excel file save path.
	 * employeeDtoList : A list of employee info that need to save.
	 * */
	private void createExcel(String filePath, List<EmployeeDTO> employeeDtoList)
	{
		if(filePath!=null && !"".equals(filePath.trim()))
		{
			try
			{
				/* Create excel workbook. */
				Workbook excelWookBook = new XSSFWorkbook();
				
				/* */
				CreationHelper createHelper = excelWookBook.getCreationHelper();
				
				/* Create employee info sheet. */
				Sheet employeeSheet = excelWookBook.createSheet("Employee Info");
	
				/* Create employee info row. Row number start with 0.
				 * The input parameter for method createRow(int rowNumber) is the row number that will be created.
				 * */
				
				/* First create header row. */
				Row headerRow = employeeSheet.createRow(0);
				
				headerRow.createCell(0).setCellValue("Name");
				headerRow.createCell(1).setCellValue("Password");
				headerRow.createCell(2).setCellValue("Email");
				headerRow.createCell(3).setCellValue("Age");
				headerRow.createCell(4).setCellValue("Department");
				headerRow.createCell(5).setCellValue("Skill");
				
	
				/* Loop for the employee dto list, add each employee data info into one row. */
				if(employeeDtoList!=null)
				{
					int size = employeeDtoList.size();
					for(int i=0;i<size;i++)
					{
						EmployeeDTO eDto = employeeDtoList.get(i);
						
						/* Create row to save employee info. */
						Row row = employeeSheet.createRow(i+1);
						
						row.createCell(0).setCellValue(eDto.geteName());
						row.createCell(1).setCellValue(eDto.getePasswd());
						row.createCell(2).setCellValue(eDto.geteEmail());
						row.createCell(3).setCellValue(eDto.geteAge());
						row.createCell(4).setCellValue(eDto.geteDepartment());
						row.createCell(5).setCellValue(eDto.geteSkill());
					}
				}
				
				/* Write to excel file */
				FileOutputStream fOut = new FileOutputStream(filePath);
				excelWookBook.write(fOut);
				fOut.close();
				
				System.out.println("File " + filePath + " is created successfully. ");
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
		/* Read data from an excel file. 
		 * Return: a 2 dimension list that contain all rows data in the file.
		 * filePath :  The excel file saved path.
		 * sheetName : The sheetName that need to read data.
		 * */
		private List<List<String>> readExcel(String filePath, String sheetName)
		{
			List<List<String>> ret = new ArrayList();
			if(filePath!=null && !"".equals(filePath.trim()) && sheetName!=null && !"".equals(sheetName.trim()))
			{
				try{
					/* First need to open the file. */
					FileInputStream fInputStream = new FileInputStream(filePath.trim());
		
					/* Create the workbook object. */
					Workbook excelWookBook = new XSSFWorkbook(fInputStream);
		
					/* Get the sheet by name. */
					Sheet employeeSheet = excelWookBook.getSheet(sheetName);
					
					int firstRowNum = employeeSheet.getFirstRowNum();
					int lastRowNum = employeeSheet.getLastRowNum();
					
					System.out.println("firstRowNum = " + firstRowNum);
					System.out.println("lastRowNum = " + lastRowNum);
					
					/* Because first row is header row, so we read data from second row. */
					for(int i=firstRowNum+1; i<lastRowNum+1; i++)
					{
						Row row = employeeSheet.getRow(i);
						
						int firstCellNum = row.getFirstCellNum();
						int lastCellNum = row.getLastCellNum();
						
						System.out.println("firstCellNum = " + firstCellNum);
						System.out.println("lastCellNum = " + lastCellNum);
						
						List<String> rowDataList = new ArrayList<String>();
						for(int j = firstCellNum; j < lastCellNum; j++)
						{
							String cellValue = row.getCell(j).getStringCellValue();
							rowDataList.add(cellValue);
						}
						
						ret.add(rowDataList);
					}
					
				}catch(Exception ex){
					ex.printStackTrace();
				}
			
			return ret;
		}
	}
		
		public static void main(String[] args) {
			
			String excelFilePath = "C:/Workspace/EmployeeInfo.xlsx";
			
			/* Create six employee data DTO object, add them all to a list. */
			List<EmployeeDTO> employeeDtoList = new ArrayList<EmployeeDTO>();
			
			EmployeeDTO employee1 = new EmployeeDTO("jack", "jack1232123", "jack@gmail.com", "35", "Dev" , "Java, Solr, Selenium.");
			EmployeeDTO employee2 = new EmployeeDTO("jackie", "jackie888888", "jackie@gmail.com", "35", "QA" , "Javascript, C++, Selenium.");
			EmployeeDTO employee3 = new EmployeeDTO("tom", "tom666666", "tom@hotmail.com", "35", "Dev" , "C++, Sybase, Objective C.");
			EmployeeDTO employee4 = new EmployeeDTO("richard", "richard9898", "richard@gmail.com", "38", "Dev" , "Java, J2EE, Spring.");
			EmployeeDTO employee5 = new EmployeeDTO("lily", "lily998889", "lily@163.com", "35", "QA" , "Hibernate, Struts, Webwork.");
			EmployeeDTO employee6 = new EmployeeDTO("steven", "steven9999999", "steven@yahoo.com", "30", "Dev" , "MFC, .net, Objective C.");
			employeeDtoList.add(employee1);
			employeeDtoList.add(employee2);
			employeeDtoList.add(employee3);
			employeeDtoList.add(employee4);
			employeeDtoList.add(employee5);
			employeeDtoList.add(employee6);
			
			/* Save above employee data in to excel file.*/
		
			ApachePOIExcel excelObj = new ApachePOIExcel();
			excelObj.createExcel(excelFilePath, employeeDtoList1);
			
			/* Read excel file data out, return a 2D list. */
			ApachePOIExcel excelObj1 = new ApachePOIExcel();
			List<List<String>> dataList = excelObj1.readExcel(excelFilePath, "Employee Info");
		}
}

}
}
