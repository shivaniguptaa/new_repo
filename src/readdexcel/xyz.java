package com.chtr.tmoauto.webui;
shivani
<<<<<<< Updated upstream
shivani333
=======

shivani2
shivani3
>>>>>>> Stashed changes
import com.chtr.tmoauto.logging.*;
import com.chtr.tmoauto.util.DateTime;
//import com.chtr.tmoauto.util.MSExcel;
import com.chtr.tmoauto.util.ALM;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.support.ui.Select;
import org.webbitserver.helpers.Base64;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class GUI 
{
	 
	public static WebDriver driver;	
	public static WebDriver driverie;
	public static WebDriver driverchrome;
	
	public static Logging log = new Logging();
	public static DateTime dt = new DateTime();
	public static ALM fwalm = new ALM();
	
	public static String window_handle = "";
	public static String return_get_text = "";
	public static Boolean return_existence;
	public static String out_object_testdata;
	public static String out_object_extrainfo = "";

	/**
	 * This function is used to switch to default content.  Used right after
	 * you switch to a frame, then to get back to default main objects use
	 * this switch to default content function.
	 * 
	 * @since: 6/7/2017
	 * @author: Mark Elking
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	
	public void fw_switchto_defaultcontent() throws ClassNotFoundException, SQLException, IOException, InterruptedException
	{
		
		driver.switchTo().defaultContent();
		
		log.fw_writeLogEntry("Switch to Default Conent", "0");
		
	}
	
	/**
	 * This function will click on security certificate.
	 * 
	 * @throws InterruptedException
	 * @author Mark Elking
	 * @throws IOException 
	 * @since 6/6/2017
	 * 
	 */
	
	public void fw_click_security_certificate() throws InterruptedException, IOException 
	{
		
		String out_message = "Click Security Certificate - Certificate NOT FOUND";
		
		boolean isPresent = driver.findElements(fw_get_element_object("id", "overridelink")).size() > 0;
		if (isPresent == true)
		{
			fw_click_button("Click Continue", "NA", "id", "overridelink", "", 0);
			out_message = "Click Security Certificate (FOUND and CLICKED)";
		}
		
		log.fw_writeLogEntry(out_message, "0");
		
	}
	
	/**
	 *
	 * This function gets a variable value.
	 *  
	 * @param variable_name
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @author Mark Elking
	 * @throws InterruptedException 
	 * @since 6/5/2017
	 */
	
	public String fw_get_variable(String variable_name) throws MalformedURLException, IOException, InterruptedException 
	{
		
		String workspace_name = fw_get_workspace_name();
		String variable_file = workspace_name + "\\variables\\" + variable_name;	
		
		String variable_value = "";				
		FileReader fileReader = null;
		BufferedReader bfrReader = null;
		String strLine = null;
		
		fileReader = new FileReader(variable_file);
		bfrReader = new BufferedReader(fileReader);
		
		while ((strLine = bfrReader.readLine()) != null)
		{	
			variable_value = variable_value + strLine;
		}
		if (null != bfrReader)
		{
			bfrReader.close();
		}
		if (fileReader != null)
		{
			fileReader.close();
		}		
		
		return variable_value;
		
	}
	
	/**
	 * This function is used to convert string case to upper or lower.
	 * 
	 * @param: filename
	 * @param: case_to_convert_to
	 * @since: 6/1/2017
	 * @author: Mark Elking
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	
	public void fw_convert_string_case(String filename, String case_to_convert_to) throws ClassNotFoundException, SQLException, IOException, InterruptedException
	{
		
		String workspace_name = fw_get_workspace_name();
		String variable_file = workspace_name + "\\variables\\" + filename;			
		String string_value = fw_get_value_from_file(variable_file);
		
		case_to_convert_to = case_to_convert_to.toUpperCase();
		if (case_to_convert_to.equals("UPPER"))
		{
			string_value = string_value.toUpperCase();
		}
		else if (case_to_convert_to.equals("LOWER"))
		{
			string_value = string_value.toLowerCase();
		}
		
		fw_set_variable("outconvertedstringcase", string_value);
		
		log.fw_writeLogEntry("Convert String Case (Input File: " + filename + ", Case to Convert to: " + case_to_convert_to + ", Output File: outconvertedstringcase, Output Value: " + string_value + ")", "0");
		
	}

	/**
	 * This function is used to get current URL.
	 * 
	 * @since: 6/1/2017
	 * @author: Mark Elking
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	
	public void fw_get_current_url() throws ClassNotFoundException, SQLException, IOException, InterruptedException
	{
		
		String current_url = driver.getCurrentUrl();
		
		fw_set_variable("currenturl", current_url);
		
		log.fw_writeLogEntry("Get Current URL (URL: " + current_url + ")", "0");
		
	}

	/**
	 * This function is used to get the computer name.
	 * 
	 * @param: dbConnectionString
	 * @param: userName
	 * @param: password
	 * @since: 4/30/2017
	 * @author: Mark Elking
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	
	public void fw_get_computername() throws ClassNotFoundException, SQLException, IOException, InterruptedException
	{
		
		String hostname = "Unknown";

		try
		{
		    InetAddress addr;
		    addr = InetAddress.getLocalHost();
		    hostname = addr.getHostName();
		}
		catch (UnknownHostException ex)
		{
		    System.out.println("Hostname can not be resolved");
		}
		
		fw_set_variable("SKIP--hostname", hostname);
		
	}
	
	/**
	 * This function is used to write a logging record to database.
	 * 
	 * @param: dbConnectionString
	 * @param: userName
	 * @param: password
	 * @since: 4/30/2017
	 * @author: Mark Elking
	 * @throws InterruptedException 
	 * @throws IOException 
	 */

	public void fw_write_logging_database(String fname, String appname, String userid, String requesttype, String workstation, String testentity, String testcaseid, String logmessage, String execstatus, String teststepidfailed, String teststepdescfailed, String logoutputfile) throws ClassNotFoundException, SQLException, IOException, InterruptedException
	{
		
		String workspace_name = fw_get_workspace_name();
		String variables_path = workspace_name + "\\variables\\";
		
		String userName = fw_get_variable("usernameTESTDB");
		String password = fw_get_variable("passwordTESTDB");
		String connection_string = fw_get_variable("connectionstringTESTDB");
		
		Connection connection = null;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connection = DriverManager.getConnection(connection_string, userName, password);
		
		String sql_query = "select count(*) cntval from tbl_tclogging where logging_outputfile = '" + logoutputfile + "'";
		
		ResultSet rs = null;
		Statement st = connection.createStatement();
		rs = st.executeQuery(sql_query);
		
		int out_count = 0;
		
		if (rs.next())
		{
			out_count = rs.getInt("cntval");
		}
		
		if (out_count == 0)
		{
			sql_query = "INSERT INTO tbl_tclogging (logging_auto_id, logging_date_created, logging_last_modified_by, logging_last_modified_date, logging_application, logging_userfirstname, logging_userid, logging_requesttype, logging_workstation, logging_entity, logging_testid, logging_message, LOGGING_EXECUTION_STATUS, LOGGING_TESTSTEP_ID_FAILED, LOGGING_TESTSTEP_DESC_FAILED, LOGGING_OUTPUTFILE, logging_componentname, logging_objectname) VALUES (seq_logging_id.nextval, sysdate, '" + fname + "', sysdate, '" + appname + "', '" + fname + "', '" + userid + "', '" + requesttype + "', '" + workstation + "', '" + testentity + "', '" + testcaseid + "', '" + logmessage + "', '" + execstatus + "', '" + teststepidfailed + "', '" + teststepdescfailed + "', '" + logoutputfile + "', 'NA', 'NA')";
		}
		else
		{
			if (execstatus.equals("PASSED"))
			{
				teststepidfailed = "";
				teststepdescfailed = "";
			}
			else
			{
				teststepidfailed = fw_get_value_from_file(variables_path + "failedstepnumber");
				teststepdescfailed = fw_get_value_from_file(variables_path + "failedstepdesc");
				int teststepdescfailed_length = teststepdescfailed.length();
				if (teststepdescfailed_length > 495)
				{
					teststepdescfailed = teststepdescfailed.substring(0, 490);
				}
			}
			sql_query = "update tbl_tclogging set LOGGING_EXECUTION_STATUS = '" + execstatus + "', LOGGING_TESTSTEP_ID_FAILED = '" + teststepidfailed + "', LOGGING_TESTSTEP_DESC_FAILED = '" + teststepdescfailed + "' where LOGGING_OUTPUTFILE = '" + logoutputfile + "'";
		}
		
		ResultSet rs2 = null;
		Statement st2 = connection.createStatement();
		rs2 = st2.executeQuery(sql_query);
		
		connection.close();

		//log.fw_writeLogEntry("Get Logging Database (Name: " + sequence_name + ", Value: " + output_value + ", Index: " + index_value + ", Variable File: sequencevalue" + index_value + ")" + text_msg, rc_val);
		
	}
	
	/**
	 * This function is used to get a spa location.
	 * 
	 * @param: dbConnectionString
	 * @param: userName
	 * @param: password
	 * @since: 4/26/2017
	 * @author: Mark Elking
	 * @throws InterruptedException 
	 * @throws IOException 
	 */

	public void fw_testdata_get_spalocation(String envname, String switchtype, String ratecenter, String accounttype, String index_value) throws ClassNotFoundException, SQLException, IOException, InterruptedException
	{
		
		String workspace_name = fw_get_workspace_name();
				
		if (envname.contains("FILE_"))
		{
			String[] envname_arr = envname.split("_");
			String envname_file = envname_arr[1];
					
			String variable_file = workspace_name + "\\variables\\" + envname_file;			
			envname = fw_get_value_from_file(variable_file);
		}
		if (switchtype.contains("FILE_"))
		{
			String[] switchtype_arr = switchtype.split("_");
			String switchtype_file = switchtype_arr[1];
					
			String variable_file = workspace_name + "\\variables\\" + switchtype_file;			
			switchtype = fw_get_value_from_file(variable_file);
		}
		if (ratecenter.contains("FILE_"))
		{
			String[] ratecenter_arr = ratecenter.split("_");
			String ratecenter_file = ratecenter_arr[1];
					
			String variable_file = workspace_name + "\\variables\\" + ratecenter_file;			
			ratecenter = fw_get_value_from_file(variable_file);
		}
		if (accounttype.contains("FILE_"))
		{
			String[] accounttype_arr = accounttype.split("_");
			String accounttype_file = accounttype_arr[1];
					
			String variable_file = workspace_name + "\\variables\\" + accounttype_file;			
			accounttype = fw_get_value_from_file(variable_file);
		}
		
		String sql_query = "";
		
		String userName = fw_get_variable("usernameTESTDB");
		String password = fw_get_variable("passwordTESTDB");
		String connection_string = fw_get_variable("connectionstringTESTDB");
		
		Connection connection = null;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connection = DriverManager.getConnection(connection_string, userName, password);
		
		sql_query = "select spalocation_system, spalocation_prin, spalocation_agent, spalocation_slbos_address1, spalocation_slbos_locationid, spalocation_slbos_zipcode, spalocation_slbos_city, spalocation_slbos_state, spalocation_devicetype from tbl_spalocation where spalocation_environment = '" + envname + "' and spalocation_switch_type = '" + switchtype + "' and spalocation_rate_center = '" + ratecenter +"' and spalocation_accounttype = '" + accounttype + "'";
		
		ResultSet rs = null;
		Statement st = connection.createStatement();
		rs = st.executeQuery(sql_query);
		
		String rc_val = "1";
		String text_msg = " - No SPALocation Found in TBL_SPALOCATION table";

		String out_spalocation_system = "";
		String out_spalocation_prin = "";
		String out_spalocation_agent = "";
		String out_spalocation_slbos_address1 = "";
		String out_spalocation_slbos_locationid = "";
		String out_spalocation_slbos_zipcode = "";
		String out_spalocation_slbos_city = "";
		String out_spalocation_slbos_state = "";
		String out_spalocation_account_prefix = "";
		String out_spalocation_devicetype = "";
		
		if (rs.next())
		{
			rc_val = "0";
			text_msg = "";
			
			out_spalocation_system = rs.getString("spalocation_system");
			out_spalocation_prin = rs.getString("spalocation_prin");
			out_spalocation_agent = rs.getString("spalocation_agent");
			out_spalocation_slbos_address1 = rs.getString("spalocation_slbos_address1");
			out_spalocation_slbos_locationid = rs.getString("spalocation_slbos_locationid");
			out_spalocation_slbos_zipcode = rs.getString("spalocation_slbos_zipcode");
			out_spalocation_slbos_city = rs.getString("spalocation_slbos_city");
			out_spalocation_slbos_state = rs.getString("spalocation_slbos_state");
			out_spalocation_account_prefix = out_spalocation_system + out_spalocation_prin.substring(0, 2) + out_spalocation_agent.substring(0, 3);
			out_spalocation_devicetype = rs.getString("spalocation_devicetype");
					
			fw_set_variable("outspalocationsystem" + index_value, out_spalocation_system);
			fw_set_variable("outspalocationprin" + index_value, out_spalocation_prin);
			fw_set_variable("outspalocationagent" + index_value, out_spalocation_agent);
			fw_set_variable("outspalocationslbosaddress1" + index_value, out_spalocation_slbos_address1);
			fw_set_variable("outspalocationslboslocationid" + index_value, out_spalocation_slbos_locationid);
			fw_set_variable("outspalocationslboszipcode" + index_value, out_spalocation_slbos_zipcode);
			fw_set_variable("outspalocationslboscity" + index_value, out_spalocation_slbos_city);
			fw_set_variable("outspalocationslbosstate" + index_value, out_spalocation_slbos_state);
			fw_set_variable("outspalocationaccountprefix" + index_value, out_spalocation_account_prefix);
			fw_set_variable("outspalocationdevicetype" + index_value, out_spalocation_devicetype);
			
		}
	
		connection.close();

		log.fw_writeLogEntry("Get SPA Location (Env: " + envname + ", Switch Type: " + switchtype + ", Rate Center: " + ratecenter + ", Account Type: " + accounttype + ", Index: " + index_value + ", Account Prefix: " + out_spalocation_account_prefix + ")" + text_msg, rc_val);
		
	}
	
	/**
	 * This function is used to get sequence.
	 * 
	 * @param: dbConnectionString
	 * @param: userName
	 * @param: password
	 * @since: 4/19/2017
	 * @author: Mark Elking
	 * @throws InterruptedException 
	 * @throws IOException 
	 */

	public void fw_testdata_get_sequence(String sequence_name, String index_value) throws ClassNotFoundException, SQLException, IOException, InterruptedException
	{
		
		String sql_query = "";
		
		String userName = fw_get_variable("usernameTESTDB");
		String password = fw_get_variable("passwordTESTDB");
		String connection_string = fw_get_variable("connectionstringTESTDB");
		
		Connection connection = null;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connection = DriverManager.getConnection(connection_string, userName, password);
				
		sql_query = "select sequence_auto_id, sequence_entity, sequence_value from tbl_sequence where sequence_entity = '" + sequence_name + "'";
		
		ResultSet rs = null;
		Statement st = connection.createStatement();
		rs = st.executeQuery(sql_query);
		
		String rc_val = "1";
		String text_msg = " - No Sequence Found in TBL_SEQUENCE table";

		String out_sequence_value = "";
		String final_sequence_value = "";
		String output_value = "";
		
		if (rs.next())
		{
			rc_val = "0";
			text_msg = "";
			int autoid = rs.getInt("sequence_auto_id");
		
			out_sequence_value = rs.getString("sequence_value");
			int out_sequence_value_length = out_sequence_value.length();
			
			int temp_sequence_value = Integer.valueOf(out_sequence_value);
			temp_sequence_value = temp_sequence_value + 1;
			final_sequence_value = String.valueOf(temp_sequence_value);
			int final_sequence_value_length = final_sequence_value.length();
			
			int temp_difference = out_sequence_value_length - final_sequence_value_length;
			
			for (int j = 1; j<temp_difference+1;j++)
			{
				output_value = output_value + "0";
			}
			
			output_value = output_value + final_sequence_value;
			
			fw_set_variable("sequencevalue" + index_value, output_value);
			
			String sql_update = "update tbl_sequence set sequence_value = '" + output_value + "', sequence_comments = 'updated sequence_value', sequence_last_modified_date = sysdate where sequence_auto_id = " + autoid;
			
			ResultSet rs2 = null;
			Statement st2 = connection.createStatement();
			rs2 = st2.executeQuery(sql_update);
			
		}
	
		connection.close();

		log.fw_writeLogEntry("Get Sequence (Name: " + sequence_name + ", Value: " + output_value + ", Index: " + index_value + ", Variable File: sequencevalue" + index_value + ")" + text_msg, rc_val);
		
	}
	
	/**
	 * This function is used to get a phone number.
	 * 
	 * @param: dbConnectionString
	 * @param: userName
	 * @param: password
	 * @since: 4/19/2017
	 * @author: Mark Elking
	 * @throws InterruptedException 
	 * @throws IOException 
	 */

	public void fw_testdata_get_phone_number(String phone_type, String phone_switch_type, String account_type, String index_value) throws ClassNotFoundException, SQLException, IOException, InterruptedException
	{
		
		String workspace_name = fw_get_workspace_name();
		String file_name_path = workspace_name + "\\variables\\ENVSELECTED";
		String env_value = fw_get_value_from_file(file_name_path);
		
		String sql_query = "";
		
		String userName = fw_get_variable("usernameTESTDB");
		String password = fw_get_variable("passwordTESTDB");
		String connection_string = fw_get_variable("connectionstringTESTDB");
		
		Connection connection = null;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connection = DriverManager.getConnection(connection_string, userName, password);
		
		account_type = account_type.toUpperCase();
		phone_switch_type = phone_switch_type.toUpperCase();		
		String rate_center_file_name_value = workspace_name + "\\variables\\ENVRateCenter" + phone_switch_type + account_type;
		String phone_rate_center = fw_get_value_from_file(rate_center_file_name_value);
		
		if (env_value.equals("PROD"))
		{
			sql_query = "select phone_auto_id, phone_value from tbl_phone where phone_available_flag = 'Y' and phone_type = '" + phone_type + "' and phone_switch_type = '" + phone_switch_type + "' and phone_rate_center = '" + phone_rate_center + "' and phone_environment = 'PROD' and rownum = 1";
		}
		else
		{
			sql_query = "select phone_auto_id, phone_value from tbl_phone where phone_available_flag = 'Y' and phone_type = '" + phone_type + "' and phone_switch_type = '" + phone_switch_type + "' and phone_rate_center = '" + phone_rate_center + "' and phone_environment = 'TEST' and rownum = 1";
		}
		ResultSet rs = null;
		Statement st = connection.createStatement();
		rs = st.executeQuery(sql_query);
		
		String rc_val = "1";
		String text_msg = " - No TN Found in TBL_PHONE table";

		String out_tn = "";
		String out_tn_first_three_digits = "";
		
		if (rs.next())
		{
			rc_val = "0";
			text_msg = "";
			int autoid = rs.getInt("phone_auto_id");
		
			out_tn = rs.getString("phone_value");
			out_tn_first_three_digits = out_tn.substring(0, 3);
					
			fw_set_variable("tnvalue" + index_value, out_tn);
			fw_set_variable("tnvaluenpa" + index_value, out_tn_first_three_digits);
			
			String sql_update = "update tbl_phone set phone_available_flag = 'N', phone_comments = 'updated phone_available flag to N', phone_last_modified_date = sysdate where phone_auto_id = " + autoid;
			
			ResultSet rs2 = null;
			Statement st2 = connection.createStatement();
			rs2 = st2.executeQuery(sql_update);
			
		}
	
		connection.close();

		log.fw_writeLogEntry("Get TN (Type: " + phone_type + ", Switch Type: " + phone_switch_type + ", Rate Center: " + phone_rate_center + ", Phone Number: " + out_tn + ", Index: " + index_value + ")" + text_msg, rc_val);
		
	}
	
	/**
	 * This function gets environment to execute test.
	 * 
	 * @since: 4/6/2017
	 * @author: Mark Elking
	 * @throws InterruptedException 
	 * @throws IOException 
	 */

	public void fw_get_environment_to_execute_tests(String input_filename) throws ClassNotFoundException, SQLException, IOException, InterruptedException
	{
		
		String workspace_name = fw_get_workspace_name();
		
		String envselectedfile = workspace_name + "\\variables\\ENVSELECTED";
		File file = new File(envselectedfile);
		if(file.delete()){
			System.out.println(file.getName() + " is deleted!");
		}    				
		
		if (workspace_name.toUpperCase().contains("JENKINS"))
		{
			String filename = "JENKINSENVIRONMENT";
    		
			File file1 =new File("C:\\Temp\\" + filename);
		    File file2 =new File(workspace_name + "\\variables\\" + filename);
		   
			try 
			{
				fileCopy(file1, file2);
			} catch (IOException e) 
			{
				e.printStackTrace();
				
				System.out.println(" ");
				System.out.println(" ***** Stopping Execution because FILE " + file1 + " NOT found ****** ");
				System.out.println(" ");
				System.exit(0);
				
			}
			fw_set_variable("SKIP--ENVSELECTED", "FILE_JENKINSENVIRONMENT");
		}
		else
		{

			String appname = fw_get_value_from_file(workspace_name + "\\variables\\appname");
			
			String sql_query = "";
			
			String userName = fw_get_variable("usernameTESTDB");
			String password = fw_get_variable("passwordTESTDB");
			String connection_string = fw_get_variable("connectionstringTESTDB");
			
			Connection connection = null;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(connection_string, userName, password);
			
			sql_query = "select distinct environment_name from tbl_environment where environment_application = '" + appname + "'";
			
			ResultSet rs = null;
			Statement st = connection.createStatement();
			rs = st.executeQuery(sql_query);
			
			String environmentname = "";
			String results_found = "no";
			
			List<String> optionList = new ArrayList<String>();
			
			while (rs.next())
			{
				results_found = "yes";
				
				environmentname = rs.getString("environment_name");
				
				if (environmentname == null)
				{
					environmentname = "";
				}
				
				optionList.add(environmentname);
			}
			
			connection.close();
	
			if (results_found == "yes")
			{		
				Object[] options = optionList.toArray();
				Object environment_selected = JOptionPane.showInputDialog(null, "Choose Environment", "Environment", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			    System.out.println("Environment Selected: " + environment_selected);
			    String out_environment_selected = environment_selected.toString();
			    
				fw_set_variable("SKIP--ENVSELECTED", out_environment_selected);
				
			}
		
		}
		
		String variables_file = workspace_name + "\\variables\\ENVSELECTED";
		String temp_out_value = fw_get_value_from_file(variables_file);
		String out_value = temp_out_value.trim();
		fw_set_variable("SKIP--ENVSELECTED", out_value);
		
	}
	
	/**
	 * This function gets user name for user running tests.
	 * 
	 * @since: 4/6/2017
	 * @author: Mark Elking
	 * @throws InterruptedException 
	 * @throws IOException 
	 */

	public String fw_get_user_name(String application_name) throws ClassNotFoundException, SQLException, IOException, InterruptedException
	{
		
		String username = "";
		String record_cnt = "";
		String test_type = "";
		String alm_column_header = "";
		
		String workspace_name = fw_get_workspace_name();
		
		String alm_user_id = "svc_automation";
		
		String datasource = "ora-dev01.corp.chartercom.com:1521/SINST01D.CORP.CHARTERCOM.COM";
		String userName = "TEST_AUTOMATION";
		String password = "y_Kjny_6R_t5";
		String connection_string = "jdbc:oracle:thin:@" + datasource;
		
		fw_set_variable("SKIP--datasourceTESTDB",datasource);
		fw_set_variable("SKIP--usernameTESTDB",userName);
		fw_set_variable("SKIP--passwordTESTDB",password);
		fw_set_variable("SKIP--connectionstringTESTDB",connection_string);
		
		if (workspace_name.toUpperCase().contains("JENKINS"))
		{
		
			// JENKINSEXECUTIONUSER
			
			String filename = "JENKINSEXECUTIONUSER";
    		
			File file1 =new File("C:\\Temp\\" + filename);
		    File file2 =new File(workspace_name + "\\variables\\" + filename);
		   
			try 
			{
				fileCopy(file1, file2);
			} catch (IOException e) 
			{
				e.printStackTrace();
				
				System.out.println(" ");
				System.out.println(" ***** Stopping Execution because FILE " + file1 + " NOT found ****** ");
				System.out.println(" ");
				System.exit(0);
				
			}
			
			username = fw_get_value_from_file(workspace_name + "\\variables\\" + filename);
			username = username.toUpperCase().trim();
			
			if (username.contains(","))
			{
				String[] username_arr = username.split(",");
				
				username = username_arr[0].trim();
				alm_column_header = username_arr[1].trim();
			}
			else
			{
				if (username.contains("JS"))
				{
					test_type = "JENKINSSMOKE";
					alm_column_header = username;
				}
				else if (username.contains("JR"))
				{
					test_type = "JENKINSREGRESSION";
					alm_column_header = username;
				}
				else
				{
					test_type = "JENKINSUNIT";
					alm_column_header = "EU1";
				}
			}
			
			fw_set_variable("SKIP--" + filename, username);
			
			
			// JENKINSNETWORKID
			
			String filename2 = "JENKINSNETWORKID";
    		
			File file3 =new File("C:\\Temp\\" + filename2);
		    File file4 =new File(workspace_name + "\\variables\\" + filename2);
		   
			try 
			{
				fileCopy(file3, file4);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				
				System.out.println(" ");
				System.out.println(" ***** Stopping Execution because FILE " + file3 + " NOT found ****** ");
				System.out.println(" ");
				System.exit(0);
				
			}
			
			alm_user_id = fw_get_value_from_file(workspace_name + "\\variables\\" + filename2);
			alm_user_id = alm_user_id.toLowerCase().trim();
			
		}
		else
		{
			
			JFrame frame = new JFrame("InputDialog");
			username = JOptionPane.showInputDialog(frame, "User?");
			username = username.toUpperCase().trim();
			
			test_type = "ECLIPSEUNIT";
			
			if (username.contains(","))
			{
				String[] username_arr = username.split(",");
				
				username = username_arr[0];
				alm_column_header = username_arr[1];
			}
			else
			{
				alm_column_header = "EU1";
			}
			
			Connection connection = null;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(connection_string, userName, password);
			String sql_query = "select count(*) cnt_val from tbl_tcexecparms where execparms_application = '" + application_name + "' and execparms_user = '" + username + "'";
			ResultSet rs = null;
			Statement st = connection.createStatement();
			rs = st.executeQuery(sql_query);
			rs.next();
			record_cnt = rs.getString("cnt_val").toString();
			if (record_cnt.equals("0"))
			{
				System.out.println(" ");
				System.out.println(" ***** Stopping Execution because USER " + username + " was NOT found in ExecParms table for this application ****** ");
				System.out.println(" ");
				System.exit(0);
			}
			else
			{
				String sql_query2 = "select security_userid from tbl_tcsecurity where security_userfirstname = '" + username + "'";
				ResultSet rs2 = null;
				Statement st2 = connection.createStatement();
				rs2 = st2.executeQuery(sql_query2);
				rs2.next();
				alm_user_id = rs2.getString("security_userid").toString();
			}
			connection.close();
			
		}
		
		fw_set_variable("SKIP--appname", application_name.toUpperCase());
		fw_set_variable("SKIP--fname", username.toUpperCase());
		fw_set_variable("SKIP--TESTTYPE", test_type);
		fw_set_variable("SKIP--ALMUSERIDLOGIN", alm_user_id);
		fw_set_variable("SKIP--ALMCOLUMNHEADER", alm_column_header);
		
		System.out.println("App Name: " + application_name.toUpperCase());
		System.out.println("First Name: " + username.toUpperCase());
		System.out.println("Test Type: " + test_type);
		System.out.println("ALM User ID: " + alm_user_id);
		System.out.println("ALM Column Header: " + alm_column_header);
		System.out.println("");
		
		return username;
		
	}
	
	/**
	 * This function is used to get test execution parms.
	 * 
	 * @since: 4/6/2017
	 * @author: Mark Elking
	 * @throws InterruptedException 
	 * @throws IOException 
	 */

	public String fw_get_test_execparms(String application_name, String username) throws ClassNotFoundException, SQLException, IOException, InterruptedException
	{
		
		fw_set_variable("SKIP--appname", application_name);
		
		String test_cases_to_execute_list = "";
		
		String userName = fw_get_variable("usernameTESTDB");
		String password = fw_get_variable("passwordTESTDB");
		String connection_string = fw_get_variable("connectionstringTESTDB");
		
		Connection connection = null;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connection = DriverManager.getConnection(connection_string, userName, password);
		
		String sql_query = "select execparms_testid, execparms_executionflag from tbl_tcexecparms where execparms_application = '" + application_name + "' and execparms_user = '" + username + "' order by execparms_executionorder";
		
		ResultSet rs = null;
		Statement st = connection.createStatement();
		rs = st.executeQuery(sql_query);
		
		String execparms_testid = "";
		String execparms_executionflag = "";
		String config_ids_list = "";
		String worksheet_list = "";
		
		int total_count = 0;

		String cur_TC_testid = "";
		String cur_config_id = "";
		
		while (rs.next())
		{
			
			total_count = total_count + 1;
			
			execparms_testid = "" + rs.getString("execparms_testid");
			execparms_executionflag = "" + rs.getString("execparms_executionflag");
			
			if (execparms_testid.startsWith("TC") && execparms_testid.contains("-") && execparms_executionflag.contains("Y"))
			{
				String[] ep_sheetname_arr = execparms_testid.split("-");
				
				cur_TC_testid = ep_sheetname_arr[0];
				cur_config_id = ep_sheetname_arr[1];
				
				test_cases_to_execute_list = test_cases_to_execute_list + cur_TC_testid + ",";
				config_ids_list = config_ids_list + cur_config_id + ",";
				worksheet_list = worksheet_list + execparms_testid + ",";
			}
			else if (execparms_testid.startsWith("TC") && execparms_executionflag.contains("Y"))
			{
				test_cases_to_execute_list = test_cases_to_execute_list + execparms_testid + ",";
				config_ids_list = config_ids_list + "NA,";
				worksheet_list = worksheet_list + execparms_testid + ",";
			}

		}
		
		if (!config_ids_list.isEmpty())
		{
			int config_ids_list_len = config_ids_list.length();
			config_ids_list = config_ids_list.substring(0, config_ids_list_len-1);
			fw_set_variable("SKIP--configids", config_ids_list);
		}
		
		if (!test_cases_to_execute_list.isEmpty())
		{
			int test_cases_to_execute_list_len = test_cases_to_execute_list.length();
			test_cases_to_execute_list = test_cases_to_execute_list.substring(0, test_cases_to_execute_list_len-1);
			fw_set_variable("SKIP--testids", test_cases_to_execute_list);
		}
		
		if (!worksheet_list.isEmpty())
		{
			int worksheet_list_len = worksheet_list.length();
			worksheet_list = worksheet_list.substring(0, worksheet_list_len-1);
			fw_set_variable("SKIP--worksheetlist", worksheet_list);
		}
		
		connection.close();
		
		return test_cases_to_execute_list;
		
	}
	
	/**
	 * This function is used to get a test objects.
	 * 
	 * @since: 4/6/2017
	 * @author: Mark Elking
	 * @throws InterruptedException 
	 * @throws IOException 
	 */

	public void fw_get_test_object(String application_name) throws ClassNotFoundException, SQLException, IOException, InterruptedException
	{
		
		String userName = fw_get_variable("usernameTESTDB");
		String password = fw_get_variable("passwordTESTDB");
		String connection_string = fw_get_variable("connectionstringTESTDB");
		
		Connection connection = null;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connection = DriverManager.getConnection(connection_string, userName, password);
		
		String sql_query = "select object_name, object_tagnameattributevalue, object_extrainfo from tbl_tcobject where object_application = '" + application_name + "'";
		
		ResultSet rs = null;
		Statement st = connection.createStatement();
		rs = st.executeQuery(sql_query);
		
		String rc_val = "1";
		String text_msg = " - No Test Object Found in TBL_TCOBJECT table for this Application";
		
		String object_name = "";
		String object_def = "";
		String object_extrainfo = "";
		
		String out_object_name = "";
		String out_object_def = "";
		String out_object_extrainfo = "";
		
		int total_count = 0;
		
		while (rs.next())
		{
			rc_val = "0";
			text_msg = "";
			
			total_count = total_count + 1;
			
			object_name = rs.getString("object_name");
			object_def = rs.getString("object_tagnameattributevalue");
			object_extrainfo = rs.getString("object_extrainfo");
			
			out_object_name = out_object_name + object_name + "$$$$";
			out_object_def = out_object_def + object_def + "$$$$";
			out_object_extrainfo = out_object_extrainfo + object_extrainfo + "$$$$";
		}
		
		out_object_def = out_object_def.replace("@@@", "'");
		
		fw_set_variable("NOSUB!!out_object_name_obj", out_object_name);
		fw_set_variable("NOSUB!!out_object_def_obj", out_object_def);
		fw_set_variable("NOSUB!!out_object_extrainfo_obj", out_object_extrainfo);
		
		String tot_total_count = "" + total_count;
		fw_set_variable("total_objects", tot_total_count);
		
		connection.close();

		log.fw_writeLogEntry("Get Test Object (Application: " + application_name + ")" + text_msg, rc_val);
		
	}
	
	/**
	 * This function is used to get a test component.
	 * 
	 * @since: 4/3/2017
	 * @author: Mark Elking
	 * @throws InterruptedException 
	 * @throws IOException 
	 */

	public void fw_get_test_component(String application_name) throws ClassNotFoundException, SQLException, IOException, InterruptedException
	{
		
		String userName = fw_get_variable("usernameTESTDB");
		String password = fw_get_variable("passwordTESTDB");
		String connection_string = fw_get_variable("connectionstringTESTDB");
		
		Connection connection = null;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connection = DriverManager.getConnection(connection_string, userName, password);
		
		String sql_query = "select COMPONENT_EVENTNAME, COMPONENT_OBJECTNAME, COMPONENT_TESTDATA, COMPONENT_OBJECTTOLOOKFOR, COMPONENT_WAITTIME from tbl_tccomponent where component_application = '" + application_name + "' order by component_name, component_executionorder";
		
		ResultSet rs = null;
		Statement st = connection.createStatement();
		rs = st.executeQuery(sql_query);
		
		String rc_val = "1";
		String text_msg = " - No Test Component Found in TBL_TCCOMPONENT table for this Application";
		
		String event_name = "";
		String object_name = "";
		String testdata_val = "";
		String objecttolookfor_val = "";
		String waittime_val = "";
		
		String out_event_name = "";
		String out_object_name = "";
		String out_testdata_val = "";
		String out_objecttolookfor_val = "";
		String out_waittime_val = "";
		
		int total_test_case_steps = 0;
		
		while (rs.next())
		{
			rc_val = "0";
			text_msg = "";
			
			total_test_case_steps = total_test_case_steps + 1;
			
			event_name = rs.getString("COMPONENT_EVENTNAME");
			object_name = rs.getString("COMPONENT_OBJECTNAME");
			testdata_val = rs.getString("COMPONENT_TESTDATA");
			objecttolookfor_val = rs.getString("COMPONENT_OBJECTTOLOOKFOR");
			waittime_val = rs.getString("COMPONENT_WAITTIME");
			
			out_event_name = out_event_name + event_name + "$$$$";
			out_object_name = out_object_name + object_name + "$$$$";
			out_testdata_val = out_testdata_val + testdata_val + "$$$$";
			out_objecttolookfor_val = out_objecttolookfor_val + objecttolookfor_val + "$$$$";
			out_waittime_val = out_waittime_val + waittime_val + "$$$$";			
		}
	
		out_object_name = out_object_name.replace("@@@", "'");
		out_testdata_val = out_testdata_val.replace("@@@", "'");
		out_objecttolookfor_val = out_objecttolookfor_val.replace("@@@", "'");
		
		fw_set_variable("NOSUB!!out_event_name_comp", out_event_name);
		fw_set_variable("NOSUB!!out_object_name_comp", out_object_name);
		fw_set_variable("NOSUB!!out_testdata_val_comp", out_testdata_val);
		fw_set_variable("NOSUB!!out_objecttolookfor_val_comp", out_objecttolookfor_val);
		fw_set_variable("NOSUB!!out_waittime_val_comp", out_waittime_val);
		
		String tot_test_case_steps = "" + total_test_case_steps;
		fw_set_variable("total_test_case_steps_comp", tot_test_case_steps);
		
		connection.close();

		log.fw_writeLogEntry("Get Test Component (Application: " + application_name + ")" + text_msg, rc_val);
		
	}
	
	/**
	 * This function is used to get a test step.
	 * 
	 * @since: 4/3/2017
	 * @author: Mark Elking
	 * @throws InterruptedException 
	 * @throws IOException 
	 */

	public String fw_get_test_step(String variable_file, int index) throws ClassNotFoundException, SQLException, IOException, InterruptedException
	{
		
		String workspace_name = fw_get_workspace_name();
		String variables_path = workspace_name + "\\variables\\";
		String out_value = fw_get_value_from_file(variables_path + variable_file);
		String[] out_value_arr = out_value.split("\\$\\$\\$\\$");
		String out_val = out_value_arr[index-1];	
		
		return out_val;
		
	}
	
	/**
	 * This function is used to get a test case.
	 * 
	 * @since: 4/3/2017
	 * @author: Mark Elking
	 * @throws InterruptedException 
	 * @throws IOException 
	 */

	public void fw_get_test_case(String application_name, String test_id) throws ClassNotFoundException, SQLException, IOException, InterruptedException
	{
				
		String userName = fw_get_variable("usernameTESTDB");
		String password = fw_get_variable("passwordTESTDB");
		String connection_string = fw_get_variable("connectionstringTESTDB");
		
		Connection connection = null;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connection = DriverManager.getConnection(connection_string, userName, password);
		
		String sql_query = "select testcase_EVENTNAME, testcase_OBJECTNAME, testcase_TESTDATA, testcase_OBJECTTOLOOKFOR, testcase_WAITTIME from tbl_tctestcase where testcase_application = '" + application_name + "' and testcase_testid = '" + test_id + "' order by testcase_executionorder";
		
		ResultSet rs = null;
		Statement st = connection.createStatement();
		rs = st.executeQuery(sql_query);
		
		String rc_val = "1";
		String text_msg = " - No Test Case Found in TBL_TESTCASE table for this Application/TestID";
		
		String event_name = "";
		String object_name = "";
		String testdata_val = "";
		String objecttolookfor_val = "";
		String waittime_val = "";
		
		String out_event_name = "";
		String out_object_name = "";
		String out_testdata_val = "";
		String out_objecttolookfor_val = "";
		String out_waittime_val = "";
				
		int total_test_case_steps = 0;
		
		while (rs.next())
		{
			rc_val = "0";
			text_msg = "";
			
			total_test_case_steps = total_test_case_steps + 1;
			
			event_name = rs.getString("testcase_EVENTNAME");
			object_name = rs.getString("testcase_OBJECTNAME");
			testdata_val = rs.getString("testcase_TESTDATA");
			objecttolookfor_val = rs.getString("testcase_OBJECTTOLOOKFOR");
			waittime_val = rs.getString("testcase_WAITTIME");
			
			out_event_name = out_event_name + event_name + "$$$$";
			out_object_name = out_object_name + object_name + "$$$$";
			out_testdata_val = out_testdata_val + testdata_val + "$$$$";
			out_objecttolookfor_val = out_objecttolookfor_val + objecttolookfor_val + "$$$$";
			out_waittime_val = out_waittime_val + waittime_val + "$$$$";			
		}
		
		out_object_name = out_object_name.replace("@@@", "'");
		out_testdata_val = out_testdata_val.replace("@@@", "'");
		out_objecttolookfor_val = out_objecttolookfor_val.replace("@@@", "'");
		
		fw_set_variable("NOSUB!!out_event_name", out_event_name);
		fw_set_variable("NOSUB!!out_object_name", out_object_name);
		fw_set_variable("NOSUB!!out_testdata_val", out_testdata_val);
		fw_set_variable("NOSUB!!out_objecttolookfor_val", out_objecttolookfor_val);
		fw_set_variable("NOSUB!!out_waittime_val", out_waittime_val);
				
		String tot_test_case_steps = "" + total_test_case_steps;
		fw_set_variable("total_test_case_steps", tot_test_case_steps);
		
		connection.close();

		log.fw_writeLogEntry("Get Test Case (Application: " + application_name + ", Test ID: " + test_id + ")" + text_msg, rc_val);
		
	}
	
	/**
	 * This function is used to get a device.
	 * 
	 * @param: dbConnectionString
	 * @param: userName
	 * @param: password
	 * @since: 3/27/2017
	 * @author: Mark Elking
	 * @throws InterruptedException 
	 * @throws IOException 
	 */

	public void fw_testdata_get_device(String device_real_fake_flag, String device_type, String device_port_type, String index_value) throws ClassNotFoundException, SQLException, IOException, InterruptedException
	{
		
		String workspace_name = fw_get_workspace_name();
		String file_name_path = workspace_name + "\\variables\\ENVSELECTED";
		String env_value = fw_get_value_from_file(file_name_path);
		
		String sql_query = "";
		
		String userName = fw_get_variable("usernameTESTDB");
		String password = fw_get_variable("passwordTESTDB");
		String connection_string = fw_get_variable("connectionstringTESTDB");
		
		Connection connection = null;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connection = DriverManager.getConnection(connection_string, userName, password);
		
		if (device_type.equals("MTA") && env_value.equals("PROD") && device_real_fake_flag.equals("R"))
		{
			sql_query = "select * from (select * from tbl_device where device_available_flag = 'Y' and device_type = '" + device_type + "' and device_real_fake_flag = '" + device_real_fake_flag + "' and device_port_type = '" + device_port_type + "' and device_environment = 'PROD' union select * from tbl_device where device_available_flag = 'N' and device_type = '" + device_type + "' and device_real_fake_flag = '" + device_real_fake_flag + "' and device_port_type = '" + device_port_type + "' and device_environment = 'PROD' and device_last_modified_date < sysdate - interval '1' hour) where rownum = 1";
		}
		else if (device_type.equals("MTA") && !env_value.equals("PROD") && device_real_fake_flag.equals("R"))
		{
			sql_query = "select * from (select * from tbl_device where device_available_flag = 'Y' and device_type = '" + device_type + "' and device_real_fake_flag = '" + device_real_fake_flag + "' and device_port_type = '" + device_port_type + "' and device_environment <> 'PROD' union select * from tbl_device where device_available_flag = 'N' and device_type = '" + device_type + "' and device_real_fake_flag = '" + device_real_fake_flag + "' and device_port_type = '" + device_port_type + "' and device_environment <> 'PROD' and device_last_modified_date < sysdate - interval '1' hour) where rownum = 1";
		}
		else if (device_type.equals("MTA"))
		{
			sql_query = "select device_auto_id, device_cmmac, device_mtamac from tbl_device where device_available_flag = 'Y' and device_type = '" + device_type + "' and device_real_fake_flag = '" + device_real_fake_flag + "' and device_port_type = '" + device_port_type + "' and device_environment <> 'PROD' and rownum = 1";
		}
		else if (device_type.substring(0, 1).equals("T"))
		{
			sql_query = "select device_auto_id, device_video_serial_number, device_video_estbmac, device_video_ecmmac from tbl_device where device_available_flag = 'Y' and device_type = '" + device_type + "' and device_real_fake_flag = '" + device_real_fake_flag + "' and device_environment = '" + env_value + "'	and rownum = 1";
		}
		ResultSet rs = null;
		Statement st = connection.createStatement();
		rs = st.executeQuery(sql_query);
		
		String rc_val = "1";
		String text_msg = " - No Device Found in TBL_DEVICE table";

		String out_device_cmmac = "";
		String out_device_mtamac = "";
		
		String out_device_video_serial_number = "";
		String out_device_video_estbmac = "";
		String out_device_video_ecmmac = "";

		String out_device_info = "";
		
		if (rs.next())
		{
			rc_val = "0";
			text_msg = "";
			int autoid = rs.getInt("device_auto_id");
			
			if (device_type.equals("MTA"))
			{
				out_device_cmmac = rs.getString("device_cmmac");
				out_device_mtamac = rs.getString("device_mtamac");
				fw_set_variable("devicecmmac" + index_value, out_device_cmmac);
				fw_set_variable("devicemtamac" + index_value, out_device_mtamac);
				out_device_info = ", Device CMMAC: " + out_device_cmmac + ", Device MTAMAC: " + out_device_mtamac;	
			}
			else if (device_type.substring(0, 1).equals("T"))
			{
				out_device_video_serial_number = rs.getString("device_video_serial_number");
				out_device_video_estbmac = rs.getString("device_video_estbmac");
				out_device_video_ecmmac = rs.getString("device_video_ecmmac");
				fw_set_variable("devicevideoserialnumber" + index_value, out_device_video_serial_number);
				fw_set_variable("devicevideoestbmac" + index_value, out_device_video_estbmac);
				fw_set_variable("devicevideoecmmac" + index_value, out_device_video_ecmmac);
				out_device_info = ", Device Video Serial Number: " + out_device_video_serial_number + ", Device Video ESTBMAC: " + out_device_video_estbmac + ", Device Video ECMMAC: " + out_device_video_ecmmac;
			}
			
			String sql_update = "update tbl_device set device_available_flag = 'N', device_comments = 'updated device_available flag to N', device_last_modified_date = sysdate where device_auto_id = " + autoid;
			
			ResultSet rs2 = null;
			Statement st2 = connection.createStatement();
			rs2 = st2.executeQuery(sql_update);
			
		}
	
		connection.close();

		log.fw_writeLogEntry("Get Device (Real or Fake: " + device_real_fake_flag + ", Device Type: " + device_type + ", Device Port Type: " + device_port_type + out_device_info + ", Index: " + index_value + ")" + text_msg, rc_val);
		
	}
	
	/**
	 * This function is used to execute sql query.
	 * 
	 * @param: dbConnectionString
	 * @param: userName
	 * @param: password
	 * @since: 3/7/2017
	 * @author: Mark Elking
	 * @throws InterruptedException 
	 * @throws IOException 
	 */

	public void fw_execute_sql(String data_source_name, String sql_query, String values_to_get) throws ClassNotFoundException, SQLException, IOException, InterruptedException
	{

		//String original_sql_query = sql_query;
		
		String workspace_name = fw_get_workspace_name();
		
		for (int x=1;x<10;x++)
		{
			int left_caret_pos = sql_query.indexOf("<");
			
			if (left_caret_pos == -1)
			{
				break;
			}
			else
			{
				int right_caret_pos = sql_query.indexOf(">");
				String file_name = sql_query.substring(left_caret_pos + 1, right_caret_pos);
				String file_name_path = workspace_name + "\\variables\\" + file_name;
				String entity_value = fw_get_value_from_file(file_name_path);
				
				String first_string = sql_query.substring(0, left_caret_pos);
				String second_string = entity_value;
				String third_string = sql_query.substring(right_caret_pos + 1, sql_query.length());

				sql_query = first_string + second_string + third_string;
				
			}
		}
		
		System.out.println("SQL Query: " + sql_query);
		
		String file_data_source = workspace_name + "\\variables\\" + "ENV" + data_source_name + "DATASOURCE";
		String file_user_id = workspace_name + "\\variables\\" + "ENV" + data_source_name + "USERID";
		String file_pass_id = workspace_name + "\\variables\\" + "ENV" + data_source_name + "PASSID";

		String datasource = fw_get_value_from_file(file_data_source);
		String userName = fw_get_value_from_file(file_user_id);
		String password = fw_get_value_from_file(file_pass_id);
		
		String connection_string = "jdbc:oracle:thin:@" + datasource;
		
		Connection connection = null;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connection = DriverManager.getConnection(connection_string, userName, password);
		
		ResultSet rs = null;
		Statement st = connection.createStatement();
		rs = st.executeQuery(sql_query);
		rs.next();
		
		String out_values = "";
		String value_current = "";
		String out_value_current = "";
		String[] values_to_get_arr = values_to_get.split(",");
		for (int x=0;x<values_to_get_arr.length;x++)
		{
			value_current = values_to_get_arr[x];
			out_value_current = rs.getString(value_current);
			out_values = out_values + out_value_current + ",";
			fw_set_variable(value_current, out_value_current);
		}
		
		connection.close();
		
		log.fw_writeLogEntry("Execute SQL (Data Source Name: " + data_source_name + ", Columns: " + values_to_get + ", Values: " + out_values + ", SQL Query: " + sql_query + ")", "0");
		
	}
	
	/**
	 * This function will terminate all processes.
	 * The input into this function is a list of processes comma delimited to be terminated.
	 * Example is "chrome.exe,excel.exe"
	 *  
	 * @param process_list
	 * @throws IOException
	 * @author Mark Elking
	 * @since 10/20/2016
	 * 
	 */
	
	public void fw_terminate_window_processes(String process_list) throws IOException 
	{
		
		String process_name = "";
		String[] process_list_arr = process_list.split(",");
		for (int x=0;x<process_list_arr.length;x++)
		{
			process_name = process_list_arr[x];
			Runtime.getRuntime().exec("taskkill /IM " + process_name + " /F");
		}
		
	}
	    
	/**
	 * This function launches a browser and defines Driver.
	 * The input required for this function is the browser type (example is IE or CHROME)
	 * 
	 * @param browsertype
	 * @author Mark Elking
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @since 10/26/2016
	 */
	
	public void fw_launch_browser(String browsertype) throws InterruptedException, IOException
	{
		String window_handle = "";
		
		String[] browsertype_arr = browsertype.split(",");
		browsertype = browsertype_arr[0];
		
		String proxytype = "NA";
		if (browsertype.contains(","))
		{
			proxytype = browsertype_arr[1];
		}
		
		String return_code = "";
		String failed_msg = "";
		
		if (browsertype.equalsIgnoreCase("IE"))
		{
			return_code = "0";
			System.setProperty("webdriver.ie.driver", "driver/IEDriverServer.exe");
			DesiredCapabilities caps = DesiredCapabilities.internetExplorer(); 
			caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			// Added by Mark/Gaurav on 1/13/2017
			caps.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			// End of Added by Mark/Gaurav on 1/13/2017
			
			// Added by Mark/Gaurav/Atul/Rohit on 1/13/2017
			if (!proxytype.equals("NA"))
			{
				Proxy proxy=new Proxy();
				
				if (proxytype.equals("PROXY"))
				{
					proxy.setProxyType(ProxyType.DIRECT);
				}
				else if (proxytype.equals("NOPROXY"))
				{
					proxy.setNoProxy(null);
				}
				
				caps.setCapability(CapabilityType.PROXY, proxy);
				caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				
			}
			// End on Added by Mark/Gaurav/Atul/Rohit on 1/13/2017
			
			//driver = new InternetExplorerDriver(caps);
			driverie = new InternetExplorerDriver(caps);
			fw_switch_to_driver("IE");
			window_handle = fw_get_window_handle();
			fw_set_variable("LAUNCH_BROWSER_WINDOW_HANDLE", window_handle);
			
		}
		else if (browsertype.equalsIgnoreCase("CHROME"))
		{
			return_code = "0";
			System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
			DesiredCapabilities caps = DesiredCapabilities.chrome();
			
			// Added by Mark/Gaurav on 1/13/2017
			caps.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			// End of Added by Mark/Gaurav on 1/13/2017
			
			// Added by Mark/Gaurav/Atul/Rohit on 1/13/2017
			if (!proxytype.equals("NA"))
			{
				Proxy proxy=new Proxy();
				
				if (proxytype.equals("PROXY"))
				{
					proxy.setProxyType(ProxyType.DIRECT);
				}
				else if (proxytype.equals("NOPROXY"))
				{
					proxy.setNoProxy(null);
				}
				
				caps.setCapability(CapabilityType.PROXY, proxy);
				caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				
			}
			// End on Added by Mark/Gaurav/Atul/Rohit on 1/13/2017
			
			//driver = new ChromeDriver(caps);
			driverchrome = new ChromeDriver(caps);
			fw_switch_to_driver("CHROME");
			window_handle = fw_get_window_handle();
			fw_set_variable("LAUNCH_BROWSER_WINDOW_HANDLE", window_handle);
			
		}
		else 
		{		
			return_code = "1";
			failed_msg = " - Browser Type not defined.";	
		}
		
		log.fw_writeLogEntry("Launch Browser (Browser Type: " + browsertype + ")" + failed_msg, return_code);
		
	}

	/**
	 * 
	 * This function switches to driver.
	 * 
	 * @throws InterruptedException
	 * @author Mark Elking
	 * @throws IOException 
	 * @since 1/18/2017
	 * 
	 */
	
	public void fw_switch_to_driver(String browser_type) throws InterruptedException, IOException
	{
		
		if (browser_type.equalsIgnoreCase("IE"))
		{
			driver = driverie;
		}
		else if (browser_type.equalsIgnoreCase("CHROME"))
		{
			driver = driverchrome;
		}
		
		log.fw_writeLogEntry("Switch To Driver (Browser Type: " + browser_type + ")", "0");
		
	}
	
	/**
	 * 
	 * This function clicks the browser back button.
	 * 
	 * @throws InterruptedException
	 * @author Mark Elking
	 * @throws IOException 
	 * @since 3/14/2017
	 * 
	 */
	
	public void fw_browser_navigate_back() throws InterruptedException, IOException
	{
		
		driver.navigate().back();
		
		log.fw_writeLogEntry("Click Browser Back Button", "0");
		
	}
	
	/**
	 * 
	 * This function switches between frames.
	 * 
	 * @throws InterruptedException
	 * @author Mark Elking
	 * @throws IOException 
	 * @since 1/17/2017
	 * 
	 */
	
	public void fw_switch_frame(String fieldname, String tagname, String locator, String locatorvalue, String fieldvalue, long milliseconds_to_wait) throws InterruptedException, IOException
	{

		String text_msg = "";
		String ret_cd = "0";
		
		try
		{
			
			WebElement webelement = driver.findElement(fw_get_element_object(locator, locatorvalue));		
			driver.switchTo().frame(webelement);
		
			log.fw_writeLogEntry("Switch To Frame (Object Name: " + fieldname, ret_cd);
		
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
			text_msg = "*************** EXCEPTION ****************";
			ret_cd = "1";
			
			String exception_string = ExceptionUtils.getStackTrace(e);
			log.fw_writeLogEntry("Switch To Frame (Object Name: " + fieldname + ") - " + text_msg + " - " + exception_string, ret_cd);
			
		}
		
	}
	
	/**
	 * This function closes window.
	 * 
	 * @throws InterruptedException
	 * @author Mark Elking
	 * @throws IOException 
	 * @since 1/16/2017
	 * 
	 */
	
	public void fw_close_window() throws InterruptedException, IOException 
	{
		
		// Store the current window handle
		String winHandleBefore = driver.getWindowHandle();

		// Perform the click operation that opens new window

		// Switch to new window opened
		String window_closed = "";
				
		for(String winHandle : driver.getWindowHandles()){
			window_closed = winHandle;
		    driver.switchTo().window(winHandle);
		}

		// Perform the actions on new window

		// Close the new window, if that window no more required
		driver.close();

		// Switch back to original browser (first window)
		driver.switchTo().window(winHandleBefore);

		// Continue with original browser (first window)
		
		log.fw_writeLogEntry("Close Window (Handle: " + window_closed + ")", "0");
		
	}

	/**
	 * This function gets window handle.
	 * 
	 * @throws InterruptedException
	 * @author Mark Elking
	 * @throws IOException 
	 * @since 1/24/2017
	 * 
	 */
	
	public String fw_get_window_handle() throws InterruptedException, IOException 
	{
		
		window_handle = driver.getWindowHandle();
		fw_set_variable("WindowHandle", window_handle);
		
		log.fw_writeLogEntry("Get Window Handle (Handle: " + window_handle + ")", "0");
		
		return window_handle;
		
	}
	
	/**
	 * This function switches to new window.
	 * 
	 * @throws InterruptedException
	 * @author Mark Elking
	 * @throws IOException 
	 * @since 1/24/2017
	 * 
	 */
	
	public void fw_switch_to_new_window() throws InterruptedException, IOException 
	{
		
		String current_window_handle = "";
		
		for(String winHandle : driver.getWindowHandles()){
		    driver.switchTo().window(winHandle);
		    current_window_handle = winHandle;
		}
		
		log.fw_writeLogEntry("Switch to Window (Handle: " + current_window_handle + ")", "0");
		
	}
	
	/**
	 * This function switches to window based on window handle.
	 * 
	 * @throws InterruptedException
	 * @author Mark Elking
	 * @throws IOException 
	 * @since 1/24/2017
	 * 
	 */
	
	public void fw_switch_to_window(String window_handle_value) throws InterruptedException, IOException 
	{
		
		// NEW
		if (window_handle_value.contains("FILE_"))
		{
			String workspace_name = fw_get_workspace_name();
			String variables_path = workspace_name + "\\variables\\";
			
			String[] tc_test_data_array = window_handle_value.split("_");
			String tc_test_data_filename = tc_test_data_array[1];			
			window_handle_value = fw_get_value_from_file(variables_path + tc_test_data_filename);
		}
		// END OF NEW
					
		driver.switchTo().window(window_handle_value);
		
		log.fw_writeLogEntry("Switch to Window (Handle: " + window_handle_value + ")", "0");
		
	}
	
	/**
	 * This function accepts an alert.
	 * 
	 * @throws InterruptedException
	 * @author Mark Elking
	 * @throws IOException 
	 * @since 1/19/2017
	 * 
	 */
	
	public void fw_accept_alert() throws InterruptedException, IOException 
	{
		
		driver.switchTo().alert().accept();
		
		log.fw_writeLogEntry("Accept Alert", "0");
		
	}
	
	/**
	 * This function navigates to a URL.
	 * 
	 * @param url
	 * @throws InterruptedException
	 * @author Mark Elking
	 * @throws IOException 
	 * @since 9/5/2016
	 * 
	 */
	
	public void fw_navigate_to_url(String url) throws InterruptedException, IOException 
	{
		
		driver.manage().window().maximize();
		driver.navigate().to(url);		
		
		log.fw_writeLogEntry("Navigate to URL (URL: " + url + ")", "0");
		
	}
	
	/**
	 * This function will login to SSO.
	 * 
	 * @param userid
	 * @param passid
	 * @throws InterruptedException
	 * @author Mark Elking
	 * @throws IOException 
	 * @since 2/23/2017
	 * 
	 */
	
	public void fw_login_to_SSO(String userid, String passid) throws InterruptedException, IOException 
	{
		
		String text_msg = "";
		
		String current_window_handle = "";
		for(String winHandle : driver.getWindowHandles())
		{
			driver.switchTo().window(winHandle);
		    current_window_handle = winHandle;
		}
		Thread.sleep(2000);
		
		String window_url = driver.switchTo().window(current_window_handle).getCurrentUrl();
		
		Thread.sleep(2000);
		
		if (window_url.contains("esso"))
		{
			//String after_window_handle = driver.switchTo().window(current_window_handle).getWindowHandle();
			
			fw_enter_data_into_text_field("LOGIN_Username", "input", "id", "username", userid, 0);
			fw_enter_data_into_text_field("LOGIN_Username", "input", "id", "password", passid, 0);
			fw_click_button("LOGIN_Username", "input", "name", "loginButton2", passid, 0);
			
			Thread.sleep(20000);
			
			String workspace_name = fw_get_workspace_name();
			String variables_path = workspace_name + "\\variables\\";	
			String before_window_handle = fw_get_value_from_file(variables_path + "LAUNCH_BROWSER_WINDOW_HANDLE");
			
			driver.switchTo().window(before_window_handle);
			
		}
		else
		{
			text_msg = " - SSO Login NOT Found.";
		}
		
		log.fw_writeLogEntry("Login to SSO (User ID: " + userid + ", Pass ID: " + passid + ")" + text_msg, "0");
		
	}
	
	/**
	 * This function enters data into a text field.
	 * The inputs required for this function are fieldname, tagname, attribute, attributevalue, fieldvalue and milliseconds to wait.
	 * 		fieldname - any text value representing the field.  Example is "Address".
	 * 		tagname - the tagname used to help search for the object on the page.  Example is "input".
	 * 		locator - the locator used to help identify an object on the page.  Example is "id" or "name".
	 * 		locatorvalue - the locator used to help identify an object on the page.  Example is "phoneNumber".
	 * 		fieldvalue - the value to put into the field once the object has been identified.
	 * 		milliseconds - the time to wait after the action has been performed on the specified object.
	 * 
	 * @param fieldname
	 * @param tagname
	 * @param locator
	 * @param locatorvalue
	 * @param fieldvalue
	 * @param milliseconds_to_wait
	 * @throws InterruptedException
	 * 
	 * @author Mark Elking
	 * @throws IOException 
	 * @since 10/26/2016
	 * 
	 */
	
	public void fw_enter_data_into_text_field(String fieldname, String tagname, String locator, String locatorvalue, String fieldvalue, long milliseconds_to_wait) throws InterruptedException, IOException 
	{
		
		String text_msg = "";
		String ret_cd = "0";
		
		try
		{
			
			// NEW
			if (fieldvalue.contains("FILE_"))
			{
				String workspace_name = fw_get_workspace_name();
				String variables_path = workspace_name + "\\variables\\";
				
				String[] tc_test_data_array = fieldvalue.split("_");
				String tc_test_data_filename = tc_test_data_array[1];			
				fieldvalue = fw_get_value_from_file(variables_path + tc_test_data_filename);
			}
			// END OF NEW
					
			String found_flag = "";
			
			if (fieldvalue.contains("KEYTAB"))
			{
				String[] fieldvalue_arr = fieldvalue.split(",");
				int num_tabs = Integer.valueOf(fieldvalue_arr[1]);
				for (int r=1;r<num_tabs+1;r++)
				{
					driver.switchTo().activeElement().sendKeys(Keys.TAB);
				}
				found_flag = "YES";
			}
			else if (fieldvalue.contains("KEYENTER"))
			{
				driver.switchTo().activeElement().sendKeys(Keys.ENTER);
				found_flag = "YES";
			}
			else if (fieldvalue.contains("KEYDATA"))
			{
				
				driver.findElement(By.xpath(locatorvalue)).click();
						
				String[] fieldvalue_arr = fieldvalue.split(",");
				String key_data = fieldvalue_arr[1];
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				executor.executeScript("arguments[0].setAttribute('value','" + key_data + "');", driver.findElement(By.xpath(locatorvalue)));
				Thread.sleep(3000);
				found_flag = "YES";
				
			}
			else if (locator.equalsIgnoreCase("name") || locator.equalsIgnoreCase("id") || locator.equalsIgnoreCase("class") || locator.equalsIgnoreCase("css") || locator.equalsIgnoreCase("xpath") || locator.equalsIgnoreCase("link") || locator.equalsIgnoreCase("partiallink"))
			{
				if(!driver.findElements(fw_get_element_object(locator, locatorvalue)).isEmpty())
				{
					found_flag = "YES";
					
					if (fieldvalue.contains("NOCLEAR"))
					{
						String[] fieldvalue_arr = fieldvalue.split(",");
						String fieldvalue_output = fieldvalue_arr[1];
						driver.findElement(fw_get_element_object(locator, locatorvalue)).sendKeys(fieldvalue_output);
					}
					else
					{
						driver.findElement(fw_get_element_object(locator, locatorvalue)).clear();
						driver.findElement(fw_get_element_object(locator, locatorvalue)).sendKeys(fieldvalue);
					}
				}
			}
			else if (locator.equals("NA")) 
			{
				
				List<WebElement> rows = driver.findElements(By.tagName(tagname));
				Iterator<WebElement> iter = rows.iterator();
				
				while (iter.hasNext()) 
				{
					WebElement item = iter.next();
					String label = item.getText().trim();
					
					if (label.contains(locatorvalue))
					{
						found_flag = "YES";
						item.clear();
						item.sendKeys(fieldvalue);
						break;
					}
				}
			}
			else 
			{
				
				List<WebElement> rows = driver.findElements(By.tagName(tagname));
				Iterator<WebElement> iter = rows.iterator();
				
				while (iter.hasNext()) 
				{
					WebElement item = iter.next();
					String label = item.getAttribute(locator);
					
					if (label.contains(locatorvalue))
					{
						found_flag = "YES";
						item.clear();
						item.sendKeys(fieldvalue);
						break;
					}
				}
			}
			
			if (found_flag.equals("YES"))
			{
				ret_cd = "0";
			}
			else
			{
				text_msg = "*************** Textbox NOT Found ****************";
				ret_cd = "1";
			}
			
			log.fw_writeLogEntry("Enter Data into Textbox (Name: " + fieldname + ", Value: " + fieldvalue + ")" + text_msg, ret_cd);
		
			Thread.sleep(milliseconds_to_wait);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
			text_msg = "*************** EXCEPTION ****************";
			ret_cd = "1";
			
			String exception_string = ExceptionUtils.getStackTrace(e);
			log.fw_writeLogEntry("Enter Data into Textbox (Name: " + fieldname + ", Value: " + fieldvalue + ") - " + text_msg + " - " + exception_string, ret_cd);
			
		}
		
	}
	
	/**
	 * This function selects checkbox.
	 * The inputs required for this function are fieldname, tagname, attribute, attributevalue, fieldvalue and milliseconds to wait.
	 * 		fieldname - any text value representing the field.  Example is "Address".
	 * 		tagname - the tagname used to help search for the object on the page.  Example is "input".
	 * 		locator - the locator used to help identify an object on the page.  Example is "id" or "name".
	 * 		locatorvalue - the locator used to help identify an object on the page.  Example is "phoneNumber".
	 * 		fieldvalue - the value to put into the field once the object has been identified.
	 * 		milliseconds - the time to wait after the action has been performed on the specified object.
	 * 
	 * @param fieldname
	 * @param tagname
	 * @param locator
	 * @param locatorvalue
	 * @param fieldvalue
	 * @param milliseconds_to_wait
	 * @throws InterruptedException
	 * 
	 * @author Mark Elking
	 * @throws IOException 
	 * @since 1/18/2017
	 * 
	 */
	
	public void fw_select_checkbox(String fieldname, String tagname, String locator, String locatorvalue, String fieldvalue, long milliseconds_to_wait) throws InterruptedException, IOException {
		
		String text_msg = "";
		String ret_cd = "0";
		String found_flag = "";
		
		try
		{
			
			if (locator.equalsIgnoreCase("name") || locator.equalsIgnoreCase("id") || locator.equalsIgnoreCase("class") || locator.equalsIgnoreCase("css") || locator.equalsIgnoreCase("xpath") || locator.equalsIgnoreCase("link") || locator.equalsIgnoreCase("partiallink"))
			{
				if(!driver.findElements(fw_get_element_object(locator, locatorvalue)).isEmpty())
				{
					found_flag = "YES";
					driver.findElement(fw_get_element_object(locator, locatorvalue)).click();
				}
			}
			else if (locator.equals("NA")) 
			{
				
				List<WebElement> rows = driver.findElements(By.tagName(tagname));
				Iterator<WebElement> iter = rows.iterator();
				
				while (iter.hasNext()) 
				{
					WebElement item = iter.next();
					String label = item.getText().trim();
					
					if (label.contains(locatorvalue))
					{
						found_flag = "YES";
						item.click();
						break;
					}
				}
			}
			else 
			{
				
				List<WebElement> rows = driver.findElements(By.tagName(tagname));
				Iterator<WebElement> iter = rows.iterator();
				
				while (iter.hasNext()) 
				{
					WebElement item = iter.next();
					String label = item.getAttribute(locator);
					
					if (label.contains(locatorvalue))
					{
						found_flag = "YES";
						item.click();
						break;
					}
				}
			}
			
			if (found_flag.equals("YES"))
			{
				ret_cd = "0";
			}
			else
			{
				text_msg = "*************** Checkbox NOT Found ****************";
				ret_cd = "1";
			}
			
			log.fw_writeLogEntry("Select Checkbox (Name: " + fieldname + ")" + text_msg, ret_cd);
			
			Thread.sleep(milliseconds_to_wait);
		
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
			text_msg = "*************** EXCEPTION ****************";
			ret_cd = "1";
			
			String exception_string = ExceptionUtils.getStackTrace(e);			
			log.fw_writeLogEntry("Select Checkbox (Name: " + fieldname + ") - " + text_msg + " - " + exception_string, ret_cd);
		}
		
	}

	/**
	 * This function clicks element using Javascript.
	 * The inputs required for this function are fieldname, tagname, attribute, attributevalue, fieldvalue and milliseconds to wait.
	 * 		fieldname - any text value representing the field.  Example is "Address".
	 * 		tagname - the tagname used to help search for the object on the page.  Example is "input".
	 * 		locator - the locator used to help identify an object on the page.  Example is "id" or "name".
	 * 		locatorvalue - the locator used to help identify an object on the page.  Example is "phoneNumber".
	 * 		fieldvalue - the value to put into the field once the object has been identified.
	 * 		milliseconds - the time to wait after the action has been performed on the specified object.
	 * 
	 * @param fieldname
	 * @param tagname
	 * @param locator
	 * @param locatorvalue
	 * @param fieldvalue
	 * @param milliseconds_to_wait
	 * @throws InterruptedException
	 * 
	 * @author Mark Elking
	 * @throws IOException 
	 * @since 1/17/2017
	 * 
	 */
	
	public void fw_click_element_using_javascript(String fieldname, String tagname, String locator, String locatorvalue, String fieldvalue, long milliseconds_to_wait) throws InterruptedException, IOException {
		
		String rc_val = "";
		String text_msg = "";
		
		try
		{
			
			if(!driver.findElements(fw_get_element_object(locator, locatorvalue)).isEmpty())
			{
				rc_val = "0";
				
				WebElement webelement = driver.findElement(fw_get_element_object(locator, locatorvalue));
				JavascriptExecutor js0 = (JavascriptExecutor) driver;
				js0.executeScript("arguments[0].click();", webelement);
			}
			else
			{
				rc_val = "1";
				text_msg = " - Object Not Found";
			}
			
			log.fw_writeLogEntry("Click Element Using Javascript (Name: " + fieldname + ")" + text_msg, rc_val);
			
			Thread.sleep(milliseconds_to_wait);
		
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
			text_msg = "*************** EXCEPTION ****************";
			rc_val = "1";
			
			String exception_string = ExceptionUtils.getStackTrace(e);			
			log.fw_writeLogEntry("Click Element Using Javascript (Name: " + fieldname + ") - " + text_msg + " - " + exception_string, rc_val);
			
		}
		
	}

	/**
	 * This function will get a webelements object.
	 * The inputs required for this function are fieldname, tagname, attribute, attributevalue, fieldvalue and milliseconds to wait.
	 * 		fieldname - any text value representing the field.  Example is "Address".
	 * 		tagname - the tagname used to help search for the object on the page.  Example is "input".
	 * 		locator - the locator used to help identify an object on the page.  Example is "id" or "name".
	 * 		locatorvalue - the locator used to help identify an object on the page.  Example is "phoneNumber".
	 * 		fieldvalue - the value to put into the field once the object has been identified.
	 * 		milliseconds - the time to wait after the action has been performed on the specified object.
	 * 
	 * @param fieldname
	 * @param tagname
	 * @param locator
	 * @param locatorvalue
	 * @param fieldvalue
	 * @param milliseconds_to_wait
	 * @throws InterruptedException
	 * 
	 * @author Mark Elking
	 * @throws IOException 
	 * @since 1/18/2017
	 * 
	 */
	
	public List<WebElement> fw_get_webelements_object(String fieldname, String tagname, String locator, String locatorvalue, String fieldvalue, long milliseconds_to_wait) throws InterruptedException, IOException {
		
		String text_msg = "";
		String ret_cd = "0";
		String found_flag = "";
		List<WebElement> webelement = null;
		
		try
		{
					
			if (locator.equalsIgnoreCase("name") || locator.equalsIgnoreCase("id") || locator.equalsIgnoreCase("class") || locator.equalsIgnoreCase("css") || locator.equalsIgnoreCase("xpath") || locator.equalsIgnoreCase("link") || locator.equalsIgnoreCase("partiallink"))
			{
				if(!driver.findElements(fw_get_element_object(locator, locatorvalue)).isEmpty())
				{
					found_flag = "YES";
					webelement = driver.findElements(fw_get_element_object(locator, locatorvalue));
				}
			}
			else if (!locator.equals("NA"))  
			{
				found_flag = "YES";
				webelement = driver.findElements(By.tagName(tagname));
			}
			
			if (found_flag.equals("YES"))
			{
				ret_cd = "0";
			}
			else
			{
				text_msg = "*************** WebElements Object NOT Found ****************";
				ret_cd = "1";
			}
			
			log.fw_writeLogEntry("Get WebElement Object (Name: " + fieldname + ")" + text_msg, ret_cd);
			
			Thread.sleep(milliseconds_to_wait);
		
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
			text_msg = "*************** EXCEPTION ****************";
			ret_cd = "1";
			
			String exception_string = ExceptionUtils.getStackTrace(e);			
			log.fw_writeLogEntry("Get WebElement Object (Name: " + fieldname + ") - " + text_msg + " - " + exception_string, ret_cd);			
		}
		
		return webelement;
		
	}
	
	// ******************************************************************************
	// *
	// * Name:        fw_get_element_object
	// * Author:      Gaurav Kumar
	// * Date:   	  11/16/2016
	// * Description: Method to get By object according to passed 
	// *              parameter values for locator
	// *
	// ******************************************************************************

	public By fw_get_element_object(String locName, String locValue) 
	{

		if (locName.equalsIgnoreCase("XPATH")) 
		{
			return By.xpath(locValue);
		}
		if (locName.equalsIgnoreCase("ID")) 
		{
			return By.id(locValue);
		}
		else if (locName.equalsIgnoreCase("CLASS")) 
		{
			return By.className(locValue);
		}
		else if (locName.equalsIgnoreCase("NAME")) 
		{
			return By.name(locValue);
		}
		else if (locName.equalsIgnoreCase("CSS")) 
		{
			return By.cssSelector(locValue);
		}
		else if (locName.equalsIgnoreCase("LINK")) 
		{
			return By.linkText(locValue);
		}
		else if (locName.equalsIgnoreCase("PARTIALLINK")) 
		{
			return By.partialLinkText(locValue);
		} 
		else 
		{
			System.out.println("Wrong entry ");
		}
		
		return null;

	}	
		
	/**
	 * This function clicks a button.
	 * The inputs required for this function are fieldname, tagname, attribute, attributevalue, fieldvalue and milliseconds to wait.
	 * 		fieldname - any text value representing the field.  Example is "Address".
	 * 		tagname - the tagname used to help search for the object on the page.  Example is "input".
	 * 		locator - the locator used to help identify an object on the page.  Example is "id" or "name".
	 * 		locatorvalue - the locator used to help identify an object on the page.  Example is "phoneNumber".
	 * 		fieldvalue - the value to put into the field once the object has been identified.
	 * 		milliseconds - the time to wait after the action has been performed on the specified object.
	 * 
	 * @param fieldname
	 * @param tagname
	 * @param locator
	 * @param locatorvalue
	 * @param fieldvalue
	 * @param milliseconds_to_wait
	 * @throws InterruptedException
	 * 
	 * @author Mark Elking
	 * @throws IOException 
	 * @since 10/26/2016
	 * 
	 */
	
	public void fw_click_button(String fieldname, String tagname, String locator, String locatorvalue, String fieldvalue, long milliseconds_to_wait) throws InterruptedException, IOException {
		
		String text_msg = "";
		String ret_cd = "0";
		
		try
		{
			
			String button_ready_flag = "";
			boolean button_isEmpty;
			boolean button_isEnabled;
			
			if (locator.equalsIgnoreCase("name") || locator.equalsIgnoreCase("id") || locator.equalsIgnoreCase("class") || locator.equalsIgnoreCase("css") || locator.equalsIgnoreCase("xpath") || locator.equalsIgnoreCase("link") || locator.equalsIgnoreCase("partiallink"))
			{
				for (int m=1;m<10;m++)
				{
					button_isEmpty = driver.findElements(fw_get_element_object(locator, locatorvalue)).isEmpty();
					button_isEnabled = driver.findElement(fw_get_element_object(locator, locatorvalue)).isEnabled();
					
					if(!button_isEmpty && button_isEnabled)
					{
						button_ready_flag = "YES";
						driver.findElement(fw_get_element_object(locator, locatorvalue)).click();
						break;
					}
					
					Thread.sleep(1000);
					
				}
			}
			else if (locator.equals("NA")) 
			{
				
				List<WebElement> rows = driver.findElements(By.tagName(tagname));
				Iterator<WebElement> iter = rows.iterator();
				
				while (iter.hasNext()) 
				{
					WebElement item = iter.next();
					String label = item.getText().trim();
					
					if (label.contains(locatorvalue))
					{
						button_isEnabled = item.isEnabled();
						if (button_isEnabled)
						{
							item.click();
							button_ready_flag = "YES";
						}
						else
						{
							button_ready_flag = "NO";
						}
						
						break;
					}
				}
			}
			else 
			{
				
				List<WebElement> rows = driver.findElements(By.tagName(tagname));
				Iterator<WebElement> iter = rows.iterator();
				
				while (iter.hasNext()) 
				{
					WebElement item = iter.next();
					String label = item.getAttribute(locator);
					
					if (label.contains(locatorvalue))
					{
						button_isEnabled = item.isEnabled();
						if (button_isEnabled)
						{
							item.click();
							button_ready_flag = "YES";
						}
						else
						{
							button_ready_flag = "NO";
						}
						
						break;
					}
				}
			}
			
			if (button_ready_flag.equals("YES"))
			{
				ret_cd = "0";
			}
			else
			{
				text_msg = "*************** Button NOT Found or Button Disabled ****************";
				ret_cd = "1";
			}
			
			log.fw_writeLogEntry("Click Button (Name: " + fieldname + ")" + text_msg, ret_cd);
			
			Thread.sleep(milliseconds_to_wait);
		
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
			text_msg = "*************** EXCEPTION ****************";
			ret_cd = "1";
			
			String exception_string = ExceptionUtils.getStackTrace(e);			
			log.fw_writeLogEntry("Click Button (Name: " + fieldname + ") - " + text_msg + " - " + exception_string, ret_cd);
		}
		
	}

	/**
	 * This function clicks on a textbox field and enters data into that textbox.
	 * The inputs required for this function are fieldname, tagname, attribute, attributevalue, fieldvalue and milliseconds to wait.
	 * 		fieldname - any text value representing the field.  Example is "Address".
	 * 		tagname - the tagname used to help search for the object on the page.  Example is "input".
	 * 		locator - the locator used to help identify an object on the page.  Example is "id" or "name".
	 * 		locatorvalue - the locator used to help identify an object on the page.  Example is "phoneNumber".
	 * 		fieldvalue - the value to put into the field once the object has been identified.
	 * 		milliseconds - the time to wait after the action has been performed on the specified object.
	 * 
	 * @param fieldname
	 * @param tagname
	 * @param locator
	 * @param locatorvalue
	 * @param fieldvalue
	 * @param milliseconds_to_wait
	 * @throws InterruptedException
	 * 
	 * @author Mark Elking
	 * @throws IOException 
	 * @since 2/28/2017
	 * 
	 */
	
	public void fw_click_and_enter_data(String fieldname, String tagname, String locator, String locatorvalue, String fieldvalue, long milliseconds_to_wait) throws InterruptedException, IOException {
		
		String text_msg = "";
		String ret_cd = "0";
		
		try
		{
			
			// NEW
			if (fieldvalue.contains("FILE_"))
			{
				String workspace_name = fw_get_workspace_name();
				String variables_path = workspace_name + "\\variables\\";
				
				String[] tc_test_data_array = fieldvalue.split("_");
				String tc_test_data_filename = tc_test_data_array[1];			
				fieldvalue = fw_get_value_from_file(variables_path + tc_test_data_filename);
			}
			// END OF NEW
					
			String field_ready_flag = "";
			boolean field_isEmpty;
			boolean field_isEnabled;
			
			if (locator.equalsIgnoreCase("name") || locator.equalsIgnoreCase("id") || locator.equalsIgnoreCase("class") || locator.equalsIgnoreCase("css") || locator.equalsIgnoreCase("xpath") || locator.equalsIgnoreCase("link") || locator.equalsIgnoreCase("partiallink"))
			{
				for (int m=1;m<10;m++)
				{
					field_isEmpty = driver.findElements(fw_get_element_object(locator, locatorvalue)).isEmpty();
					field_isEnabled = driver.findElement(fw_get_element_object(locator, locatorvalue)).isEnabled();
					
					if(!field_isEmpty && field_isEnabled)
					{
						field_ready_flag = "YES";
						driver.findElement(fw_get_element_object(locator, locatorvalue)).click();
						driver.switchTo().activeElement().sendKeys(fieldvalue);
						break;
					}
					
					Thread.sleep(1000);
					
				}
			}
			else if (locator.equals("NA")) 
			{
				
				List<WebElement> rows = driver.findElements(By.tagName(tagname));
				Iterator<WebElement> iter = rows.iterator();
				
				while (iter.hasNext()) 
				{
					WebElement item = iter.next();
					String label = item.getText().trim();
					
					if (label.contains(locatorvalue))
					{
						field_isEnabled = item.isEnabled();
						if (field_isEnabled)
						{
							item.click();
							driver.switchTo().activeElement().sendKeys(fieldvalue);
							field_ready_flag = "YES";
						}
						else
						{
							field_ready_flag = "NO";
						}
						
						break;
					}
				}
			}
			else 
			{
				
				List<WebElement> rows = driver.findElements(By.tagName(tagname));
				Iterator<WebElement> iter = rows.iterator();
				
				while (iter.hasNext()) 
				{
					WebElement item = iter.next();
					String label = item.getAttribute(locator);
					
					if (label.contains(locatorvalue))
					{
						field_isEnabled = item.isEnabled();
						if (field_isEnabled)
						{
							item.click();
							driver.switchTo().activeElement().sendKeys(fieldvalue);
							field_ready_flag = "YES";
						}
						else
						{
							field_ready_flag = "NO";
						}
						
						break;
					}
				}
			}
			
			if (field_ready_flag.equals("YES"))
			{
				ret_cd = "0";
			}
			else
			{
				text_msg = "*************** Field NOT Found or Field Disabled ****************";
				ret_cd = "1";
			}
			
			log.fw_writeLogEntry("Click and Enter Data into Textbox (Name: " + fieldname + ", Value: " + fieldvalue + ")" + text_msg, ret_cd);
			
			Thread.sleep(milliseconds_to_wait);
		
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
			text_msg = "*************** EXCEPTION ****************";
			ret_cd = "1";
			
			String exception_string = ExceptionUtils.getStackTrace(e);			
			log.fw_writeLogEntry("Click and Enter Data into Textbox (Name: " + fieldname + ", Value: " + fieldvalue + ") - " + text_msg + " - " + exception_string, ret_cd);
		}
		
	}
	
	/**
	 * This function selects a value from a list by value.
	 * The inputs required for this function are fieldname, tagname, attribute, attributevalue, fieldvalue and milliseconds to wait.
	 * 		fieldname - any text value representing the field.  Example is "Address".
	 * 		tagname - the tagname used to help search for the object on the page.  Example is "input".
	 * 		locator - the locator used to help identify an object on the page.  Example is "id" or "name".
	 * 		locatorvalue - the locator used to help identify an object on the page.  Example is "phoneNumber".
	 * 		fieldvalue - the value to put into the field once the object has been identified.
	 * 		milliseconds - the time to wait after the action has been performed on the specified object.
	 * 
	 * @param fieldname
	 * @param tagname
	 * @param locator
	 * @param locatorvalue
	 * @param fieldvalue
	 * @param milliseconds_to_wait
	 * @throws InterruptedException
	 * 
	 * @author Mark Elking
	 * @throws IOException 
	 * @since 10/29/2016
	 * 
	 */
	
	public void fw_select_from_a_list_by_value(String fieldname, String tagname, String locator, String locatorvalue, String fieldvalue, long milliseconds_to_wait) throws InterruptedException, IOException 
	{
				
		String text_msg = "";
		String ret_cd = "0";
		
		try
		{
			
			String found_flag = "";
			
			// NEW
			if (fieldvalue.contains("FILE_"))
			{
				String workspace_name = fw_get_workspace_name();
				String variables_path = workspace_name + "\\variables\\";
				
				String[] tc_test_data_array = fieldvalue.split("_");
				String tc_test_data_filename = tc_test_data_array[1];			
				fieldvalue = fw_get_value_from_file(variables_path + tc_test_data_filename);
			}
			// END OF NEW
						
			if (locator.equalsIgnoreCase("name") || locator.equalsIgnoreCase("id") || locator.equalsIgnoreCase("class") || locator.equalsIgnoreCase("css") || locator.equalsIgnoreCase("xpath") || locator.equalsIgnoreCase("link") || locator.equalsIgnoreCase("partiallink"))
			{
				if(!driver.findElements(fw_get_element_object(locator, locatorvalue)).isEmpty())
				{
					found_flag = "YES";
					WebElement element = driver.findElement(fw_get_element_object(locator, locatorvalue));
					Select listbox = new Select(element);
			        listbox.selectByValue(fieldvalue);
				}
			}
			else if (locator.equals("NA")) 
			{
				
				List<WebElement> rows = driver.findElements(By.tagName(tagname));
				Iterator<WebElement> iter = rows.iterator();
				
				while (iter.hasNext()) 
				{
					WebElement item = iter.next();
					String label = item.getText().trim();
					
					if (label.contains(fieldvalue))
					{
						found_flag = "YES";
						item.click();
						break;
					}
				}
			}
			else 
			{
				
				List<WebElement> rows = driver.findElements(By.tagName(tagname));
				Iterator<WebElement> iter = rows.iterator();
				
				while (iter.hasNext()) 
				{
					WebElement item = iter.next();
					String label = item.getAttribute(locator);
					
					if (label.contains(locatorvalue))
					{
						found_flag = "YES";
						WebElement element = item;
						Select listbox = new Select(element);
				        listbox.selectByValue(fieldvalue);
						break;
					}
				}
			}
			
			if (found_flag.equals("YES"))
			{
				ret_cd = "0";
			}
			else
			{
				text_msg = "*************** Listbox NOT Found ****************";
				ret_cd = "1";
			}
			
			log.fw_writeLogEntry("Select from a List By Value (Name: " + fieldname + ", Value: " + fieldvalue + ")" + text_msg, ret_cd);
			
			Thread.sleep(milliseconds_to_wait);

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
			text_msg = "*************** EXCEPTION ****************";
			ret_cd = "1";
			
			String exception_string = ExceptionUtils.getStackTrace(e);			
			log.fw_writeLogEntry("Select from a List By Value (Name: " + fieldname + ", Value: " + fieldvalue + ") - " + text_msg + " - " + exception_string, ret_cd);
		}
		
	}

	/**
	 * This function selects a value from a list by visible text.
	 * The inputs required for this function are fieldname, tagname, attribute, attributevalue, fieldvalue and milliseconds to wait.
	 * 		fieldname - any text value representing the field.  Example is "Address".
	 * 		tagname - the tagname used to help search for the object on the page.  Example is "input".
	 * 		locator - the locator used to help identify an object on the page.  Example is "id" or "name".
	 * 		locatorvalue - the locator used to help identify an object on the page.  Example is "phoneNumber".
	 * 		fieldvalue - the value to put into the field once the object has been identified.
	 * 		milliseconds - the time to wait after the action has been performed on the specified object.
	 * 
	 * @param fieldname
	 * @param tagname
	 * @param locator
	 * @param locatorvalue
	 * @param fieldvalue
	 * @param milliseconds_to_wait
	 * @throws InterruptedException
	 * 
	 * @author Mark Elking
	 * @throws IOException 
	 * @since 1/17/2017
	 * 
	 */
	
	public void fw_select_from_a_list_by_visible_text(String fieldname, String tagname, String locator, String locatorvalue, String fieldvalue, long milliseconds_to_wait) throws InterruptedException, IOException 
	{
				
		String text_msg = "";
		String ret_cd = "0";
		
		try
		{
			
			String found_flag = "";
			
			// NEW
			if (fieldvalue.contains("FILE_"))
			{
				String workspace_name = fw_get_workspace_name();
				String variables_path = workspace_name + "\\variables\\";
				
				String[] tc_test_data_array = fieldvalue.split("_");
				String tc_test_data_filename = tc_test_data_array[1];			
				fieldvalue = fw_get_value_from_file(variables_path + tc_test_data_filename);
			}
			// END OF NEW
					
			if (locator.equalsIgnoreCase("name") || locator.equalsIgnoreCase("id") || locator.equalsIgnoreCase("class") || locator.equalsIgnoreCase("css") || locator.equalsIgnoreCase("xpath") || locator.equalsIgnoreCase("link") || locator.equalsIgnoreCase("partiallink"))
			{
				if(!driver.findElements(fw_get_element_object(locator, locatorvalue)).isEmpty())
				{
					found_flag = "YES";
					WebElement element = driver.findElement(fw_get_element_object(locator, locatorvalue));
					Select listbox = new Select(element);
			        listbox.selectByVisibleText(fieldvalue);
				}
			}
			else if (locator.equals("NA")) 
			{
				
				List<WebElement> rows = driver.findElements(By.tagName(tagname));
				Iterator<WebElement> iter = rows.iterator();
				
				while (iter.hasNext()) 
				{
					WebElement item = iter.next();
					String label = item.getText().trim();
					
					if (label.contains(fieldvalue))
					{
						found_flag = "YES";
						item.click();
						break;
					}
				}
			}
			else 
			{
				
				List<WebElement> rows = driver.findElements(By.tagName(tagname));
				Iterator<WebElement> iter = rows.iterator();
				
				while (iter.hasNext()) 
				{
					WebElement item = iter.next();
					String label = item.getAttribute(locator);
					
					if (label.contains(locatorvalue))
					{
						found_flag = "YES";
						WebElement element = item;
						Select listbox = new Select(element);
				        listbox.selectByVisibleText(fieldvalue);
						break;
					}
				}
			}
			
			if (found_flag.equals("YES"))
			{
				ret_cd = "0";
			}
			else
			{
				text_msg = "*************** Listbox NOT Found ****************";
				ret_cd = "1";
			}
			
			log.fw_writeLogEntry("Select from a List By Visible Text (Name: " + fieldname + ", Value: " + fieldvalue + ")" + text_msg, ret_cd);
			
			Thread.sleep(milliseconds_to_wait);
		
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
			text_msg = "*************** EXCEPTION ****************";
			ret_cd = "1";
			
			String exception_string = ExceptionUtils.getStackTrace(e);			
			log.fw_writeLogEntry("Select from a List By Visible Text (Name: " + fieldname + ", Value: " + fieldvalue + ") - " + text_msg + " - " + exception_string, ret_cd);
		}
		
	}
	
	//******************************************************************************
	//*
	//*	Name: 	fw_check_for_loading_page
	//*	Author: Mark Elking
	//*	Date: 	10/26/2016
	//*
	//******************************************************************************
	
	public String fw_check_for_loading_page(String tagname, String text_to_look_for, String number_loops, String milliseconds_to_wait_per_loop, String attribute_name) throws InterruptedException, IOException 
	{
		
		String text_msg = "";
		String ret_cd = "0";
		String found_flag = "no";
		long total_milliseconds = 0;
		
		try
		{
				
			String[] waitandtagname_arr = tagname.split("--");
			long initial_milliseconds_to_wait = 0;
			
			if (waitandtagname_arr.length == 2)
			{
				initial_milliseconds_to_wait = Long.valueOf(waitandtagname_arr[0]);
				Thread.sleep(initial_milliseconds_to_wait);
				tagname = waitandtagname_arr[1];	
			}
			else
			{
				tagname = waitandtagname_arr[0];
			}
			
			long ms_to_wait_per_loop;
			ms_to_wait_per_loop = Long.valueOf(milliseconds_to_wait_per_loop);
			
			int num_loops;
			num_loops = Integer.valueOf(number_loops);
			
			int x;
			
			if (attribute_name.equals("NA"))
			{
			
				for (x=1;x<num_loops;x++)
				{	
					
					Thread.sleep(ms_to_wait_per_loop);
					
					List<WebElement> rows = driver.findElements(By.tagName(tagname));		
					Iterator<WebElement> iter = rows.iterator();
									
					while (iter.hasNext()) {
						WebElement item = iter.next();
						String label = item.getText().trim();
						
						if (label.contains(text_to_look_for))
						{
							found_flag = "yes";
							total_milliseconds = ms_to_wait_per_loop * x;
							break;
						}
					}
				
					if (found_flag.equals("yes"))
					{
						break;
					}
					else
					{
						Thread.sleep(ms_to_wait_per_loop);
					}
				}
			
			}
			else
			{
				// Added by Mark on 1/14/2017
				
				for (x=1;x<num_loops;x++)
				{	
					
					List<WebElement> rows = driver.findElements(By.tagName(tagname));		
					Iterator<WebElement> iter = rows.iterator();
					
					while (iter.hasNext()) {
						WebElement item = iter.next();
						String label = item.getAttribute(attribute_name).trim();
						
						if (label.contains(text_to_look_for))
						{
							found_flag = "yes";
							total_milliseconds = ms_to_wait_per_loop * x;
							break;
						}
					}
					
					if (found_flag.equals("yes"))
					{
						break;
					}
					else
					{
						Thread.sleep(ms_to_wait_per_loop);
					}
				
				}
				// End of Added by Mark on 1/14/2017
			}
			
			if (found_flag == "yes")
			{
				ret_cd = "0";
			}
			else
			{
				text_msg = "*************** Tag NOT Found ********************";
				ret_cd = "1";
			}
			
			log.fw_writeLogEntry("Check for Loading Page (FOUND, Tag: " + tagname + ", Text: " + text_to_look_for + ", # Loops: " + number_loops + ", Msecs to Wait per Loop: " + milliseconds_to_wait_per_loop + ", Msecs Elapsed: " + total_milliseconds + ", Attribute Name: " + attribute_name + ")" + text_msg, ret_cd);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
			text_msg = "*************** EXCEPTION ****************";
			ret_cd = "1";
			
			String exception_string = ExceptionUtils.getStackTrace(e);			
			log.fw_writeLogEntry("Check for Loading Page (Tag: " + tagname + ", Text: " + text_to_look_for + ", # Loops: " + number_loops + ", Msecs to Wait per Loop: " + milliseconds_to_wait_per_loop + ", Msecs Elapsed: " + total_milliseconds + ", Attribute Name: " + attribute_name + ") - " + text_msg + " - " + exception_string, ret_cd);
		}
		
		return found_flag;
		
	}

	/**
	 * This function gets text from page.
	 * The inputs required for this function are fieldname, tagname, attribute, attributevalue, fieldvalue and milliseconds to wait.
	 * 		fieldname - any text value representing the field.  Example is "Address".
	 * 		tagname - the tagname used to help search for the object on the page.  Example is "input".
	 * 		locator - the locator used to help identify an object on the page.  Example is "id" or "name".
	 * 		locatorvalue - the locator used to help identify an object on the page.  Example is "phoneNumber".
	 * 		fieldvalue - the value to put into the field once the object has been identified.
	 * 		milliseconds - the time to wait after the action has been performed on the specified object.
	 * 
	 * @param fieldname
	 * @param tagname
	 * @param locator
	 * @param locatorvalue
	 * @param fieldvalue
	 * @param milliseconds_to_wait
	 * @throws InterruptedException
	 * 
	 * @author Mark Elking
	 * @throws IOException 
	 * @since 12/01/2016
	 * 
	 */
	
	public String fw_get_text(String fieldname, String tagname, String locator, String locatorvalue, String fieldvalue, long milliseconds_to_wait) throws InterruptedException, IOException {
		
		// NEW CODE
		
		return_get_text = "";
		String text_msg = "";
		String ret_cd = "0";
		String ready_flag = "";
		boolean field_isEmpty;
		boolean field_isEnabled;
		String outputfile = "";
		
		try
		{
					
			if (locator.equalsIgnoreCase("name") || locator.equalsIgnoreCase("id") || locator.equalsIgnoreCase("class") || locator.equalsIgnoreCase("css") || locator.equalsIgnoreCase("xpath") || locator.equalsIgnoreCase("link") || locator.equalsIgnoreCase("partiallink"))
			{
				for (int m=1;m<10;m++)
				{
					field_isEmpty = driver.findElements(fw_get_element_object(locator, locatorvalue)).isEmpty();
					field_isEnabled = driver.findElement(fw_get_element_object(locator, locatorvalue)).isEnabled();
					
					if(!field_isEmpty && field_isEnabled)
					{
						ready_flag = "YES";
						return_get_text = driver.findElement(fw_get_element_object(locator, locatorvalue)).getText();
						break;
					}
					
					Thread.sleep(1000);
					
				}
			}
			else if (locator.equals("NA")) 
			{
				
				List<WebElement> rows = driver.findElements(By.tagName(tagname));
				Iterator<WebElement> iter = rows.iterator();
				
				while (iter.hasNext()) 
				{
					WebElement item = iter.next();
					String label = item.getText().trim();
					
					if (label.contains(locatorvalue))
					{
						ready_flag = "YES";
						return_get_text = item.getText();
						break;
					}
				}
			}
			else 
			{
				
				List<WebElement> rows = driver.findElements(By.tagName(tagname));
				Iterator<WebElement> iter = rows.iterator();
				
				while (iter.hasNext()) 
				{
					WebElement item = iter.next();
					String label = item.getAttribute(locator);
					
					if (label.contains(locatorvalue))
					{
						ready_flag = "YES";
						return_get_text = item.getText();
						break;
					}
				}
			}
			
			if (ready_flag.equals("YES"))
			{
				ret_cd = "0";
				
				if (fieldvalue.contains("FILE--"))
				{
					String[] fieldvalue_arr_value = fieldvalue.split("--");
					outputfile = fieldvalue_arr_value[1];
				}
				else
				{
					outputfile = "GETTEXT" + fieldname;
				}
				//fw_set_variable("GETTEXT" + fieldname, return_get_text);
				fw_set_variable(outputfile, return_get_text);
			}
			else
			{
				text_msg = "*************** Field NOT Found or Field Disabled ****************";
				ret_cd = "1";
			}
					
			log.fw_writeLogEntry("Get Text (Name: " + fieldname + ", Value: " + fieldvalue + ", Text: " + return_get_text + ")" + text_msg, ret_cd);
				
			Thread.sleep(milliseconds_to_wait);
		
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
			text_msg = "*************** EXCEPTION ****************";
			ret_cd = "1";
			
			String exception_string = ExceptionUtils.getStackTrace(e);			
			log.fw_writeLogEntry("Get Text (Name: " + fieldname + ", Value: " + fieldvalue + ", Text: " + return_get_text + ") - " + text_msg + " - " + exception_string, ret_cd);
		}
		
		return return_get_text;
		
	}
	
	/**
	 * This function quits the driver.
	 * 
	 * @author Mark Elking
	 * @throws InterruptedException 
	 * @since 12/09/2016
	 * 
	 */
	
	public void fw_quit_driver() throws InterruptedException
	{
		if (driver != null)
		{
			driver.close();
			driver.quit();
		}
	}

	/**
	 * This function validates text.
	 * 
	 * @author Mark Elking
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @since 1/23/2017
	 * 
	 */
	
	public void fw_validate_text(String fieldname, String expected_text, String actual_text) throws InterruptedException, IOException
	{
		
		String ret_cd = "";
		String text_msg = "";
		
		if (actual_text.contains(expected_text))
		{
			ret_cd = "0";
		}
		else
		{
			text_msg = "*************** Expected Text NOT Contained in Actual Text ****************";
			ret_cd = "1";
			
			// Added by Mark on 6/8/2017
			String execution_flag = fw_get_variable("stopexecutionuponfailure").trim();
			if (execution_flag.equals("yes"))
			{
				log.fw_writeLogEntry(" ", "NA");
				log.fw_writeLogEntry(" ***** Stopping Execution ****** ", "NA");
				log.fw_writeLogEntry(" ", "NA");
				System.exit(0);
			}
			// End of Added by Mark on 6/8/2017
		}
				
		log.fw_writeLogEntry("Validate Text (Name: " + fieldname + ", Expected: " + expected_text + ", Actual: " + actual_text + ")" + text_msg, ret_cd);
		
	}
	
	/**
	 * This function sets the gets worksheet list.
	 * 
	 * @author Mark Elking
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @since 3/31/2017
	 * 
	 */
	
	public String fw_get_worksheet_list(String test_case_id) throws IOException, InterruptedException 
	{
		
		String workspace_name = fw_get_workspace_name();
        String variables_path = workspace_name + "\\variables\\";
		String worksheetlist = fw_get_value_from_file(variables_path + "worksheetlist");
		String testidslist = fw_get_value_from_file(variables_path + "testids");
		String cur_test_id = "";
		String worksheet_name = "";
		
		String[] testidslist_arr = testidslist.split(",");
		String[] worksheetlist_arr = worksheetlist.split(",");
		int testidslist_len = testidslist_arr.length;
		for (int i=0;i<testidslist_len;i++)
		{
			cur_test_id = testidslist_arr[i];
			if (cur_test_id.contains(test_case_id))
			{
				worksheet_name = worksheetlist_arr[i];
				break;
			}
		}
		
		return worksheet_name;
						
	}
	
	/**
	 * 
	 * * This function creates environment variables.
	 * 
	 * @param environment_value
	 * @author Mark Elking
	 * @since 3/6/2017
	 * @return 
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	 
	public void fw_create_environment_variables(String environment_value) throws IOException, InterruptedException, ClassNotFoundException, SQLException
	{
		
		String workspace_name = fw_get_workspace_name();
		String appname = fw_get_value_from_file(workspace_name + "\\variables\\appname");
		
		String sql_query = "";
		
		String userName = fw_get_variable("usernameTESTDB");
		String password = fw_get_variable("passwordTESTDB");
		String connection_string = fw_get_variable("connectionstringTESTDB");
		
		Connection connection = null;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connection = DriverManager.getConnection(connection_string, userName, password);
		
		sql_query = "select environment_columnheader, environment_columnvalue from tbl_environment where environment_application = '" + appname + "' and environment_name = '" + environment_value + "'";
		
		ResultSet rs = null;
		Statement st = connection.createStatement();
		rs = st.executeQuery(sql_query);
		
		String rc_val = "1";
		String text_msg = " - No Environment Info Found in TBL_ENVIRONMENT table";
		String environment_columnheader = "";
		String environment_columnvalue = "";
		
		while (rs.next())
		{
			rc_val = "0";
			text_msg = "";
			
			environment_columnheader = rs.getString("environment_columnheader");
			environment_columnvalue = rs.getString("environment_columnvalue");
			
			if (environment_columnvalue == null)
			{
				environment_columnvalue = "";
			}
		
			fw_set_variable("ENV" + environment_columnheader, environment_columnvalue);
			
			
		}
	
		connection.close();

		
		if (rc_val.equals("1"))
		{
			text_msg = "***** Environment value NOT found ****";
		}
		
		log.fw_writeLogEntry("Create Environment Variables (Environment: " + environment_value + ", App: " + appname + ")" + text_msg, rc_val);		
		
	}
	
	/**
	 * This function takes object name and performs respective webui event on that object.
	 * 
	 * @param tab_name
	 * @param event_name
	 * @param in_object_key_name
	 * @param milliseconds_to_wait
	 * @throws InterruptedException
	 * @author Mark Elking
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @since 12/27/2016
	 */
	
	public void fw_event (String configuration_map_fullpath, String tab_name, String tc_event_name, String tc_object_name, String tc_test_data, String object_to_look_for_after_object_event, String milliseconds_to_wait_after_object_event) throws InterruptedException, IOException, ParseException, NoSuchAlgorithmException, KeyManagementException, ClassNotFoundException, SQLException{
		
		String object_objectname = "";
		String object_tagnameattributevalue = "";
		String object_extrainfo = "";
		String object_tagname = "";
		String object_attribute = "";
		String object_attributevalue = "";
		
		String workspace_name = fw_get_workspace_name();
		String variables_path = workspace_name + "\\variables\\";
		String responses_path = workspace_name + "\\webservices\\responses";
				
		out_object_testdata = tc_test_data;

		int RowObjects = Integer.parseInt(fw_get_value_from_file(variables_path + "total_objects"));
		for (int y=1;y<RowObjects+1;y++)
		{
			
			object_objectname = fw_get_test_step("out_object_name_obj", y);
			object_tagnameattributevalue = fw_get_test_step("out_object_def_obj", y);
			object_extrainfo = fw_get_test_step("out_object_extrainfo_obj", y);
			
			if (object_objectname.equals(tc_object_name))
			{
				String[] object_tagnameattributevalue_arr = object_tagnameattributevalue.split(",");
				
				object_tagname = object_tagnameattributevalue_arr[0];
				object_attribute = object_tagnameattributevalue_arr[1];
				object_attributevalue = object_tagnameattributevalue_arr[2];
				break;
			}
			
		}
		
		// Modified by Mark on 5/26/2017 to handle multiple dynamic objects in same property
		
		if (object_attributevalue.contains("---FILE_"))
		{
			String out_object_attributevalue = "";
			String object_temp = "";
			
			String[] object_attributevalue_array = object_attributevalue.split("---");
			
			int object_attributevalue_len = object_attributevalue_array.length;
			
			for (int x=0;x<object_attributevalue_len;x++)
			{
				object_temp = object_attributevalue_array[x];
				
				if (object_temp.contains("FILE_"))
				{
					String[] file_array = object_temp.split("_");
					String filenamevalue = file_array[1];	
					String workspacename = fw_get_workspace_name();
					object_temp = fw_get_value_from_file(workspacename + "\\variables\\" + filenamevalue);
				}
				out_object_attributevalue = out_object_attributevalue + object_temp;
			}
		
			object_attributevalue = out_object_attributevalue;
			
		}
		
		// End of Modified by Mark on 5/26/2017
				
		if (tc_event_name.equals("XMLExecute"))
		{
		
			String workspacename = fw_get_workspace_name();
			String variable_file = workspacename + "\\variables\\ENVSELECTED";
			String environment_selected = fw_get_value_from_file(variable_file);
			
			String[] tc_object_name_arr = tc_object_name.split("_");
			int arr_length = tc_object_name_arr.length;
		
			String webservice_type = "";
			String webservice_name = "";
			String column_name = "";
			
			if (arr_length == 3)
			{
				webservice_type = tc_object_name_arr[0];
				webservice_name = tc_object_name_arr[1];
				column_name = tc_object_name_arr[2];
			}
			else
			{
				webservice_type = tc_object_name_arr[0];
				webservice_name = tc_object_name_arr[1];
				column_name = webservice_name;
			}
			
			fw_set_variable("webservice_type", webservice_type);
			
			String current_endpoint = "";
			String current_credentials = "";
			
			String current_endpoint_file = workspacename + "\\variables\\ENV" + column_name + "ENDPOINT";
			current_endpoint = fw_get_value_from_file(current_endpoint_file);
			 
			// Substitute Dynamic Endpoint
			
			for (int x=1;x<10;x++)
			{
				int left_caret_pos = current_endpoint.indexOf("{");
				
				if (left_caret_pos == -1)
				{
					break;
				}
				else
				{
					int right_caret_pos = current_endpoint.indexOf("}");
					String file_name = current_endpoint.substring(left_caret_pos + 1, right_caret_pos);
					String file_name_path = workspace_name + "\\variables\\" + file_name;
					String entity_value = fw_get_value_from_file(file_name_path);
					
					String first_string = current_endpoint.substring(0, left_caret_pos);
					String second_string = entity_value;
					String third_string = current_endpoint.substring(right_caret_pos + 1, current_endpoint.length());

					current_endpoint = first_string + second_string + third_string;
					
				}
			}
			
			// End of Substitute Dynamic Endpoint
			
			String current_credentials_file = workspacename + "\\variables\\ENV" + column_name + "CREDS";
			current_credentials = fw_get_value_from_file(current_credentials_file);
				
			if (current_credentials.contains(":"))
			{
				String[] current_credentials_arr = current_credentials.split(":");
				String userid = current_credentials_arr[0];
				String passid = current_credentials_arr[1];
				fw_set_variable("WSUSERID", userid);
				fw_set_variable("WSPASSID", passid);
			}
			else
			{
				fw_set_variable("WSUSERID", "");
				fw_set_variable("WSPASSID", "");
			}	
			
			
			int num_loops = 1;
			long wait_per_loop = 0;
			String execute_and_validate_flag = "";
			String text_to_look_for_in_response = "";
			
			if (object_to_look_for_after_object_event.contains("--"))
			{
				String[] objecttolookforafterobjectevent_arr = object_to_look_for_after_object_event.split("--");
				text_to_look_for_in_response = objecttolookforafterobjectevent_arr[0];
				num_loops = Integer.valueOf(objecttolookforafterobjectevent_arr[1]);
				wait_per_loop = Long.valueOf(objecttolookforafterobjectevent_arr[2]);
				
				execute_and_validate_flag = "YES";
				
				log.fw_writeLogEntry("", "NA");
				log.fw_writeLogEntry("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^", "NA");
				log.fw_writeLogEntry("Webservice Loop Check.......", "NA");
				log.fw_writeLogEntry("     Webservice:          " + webservice_name, "NA");
				log.fw_writeLogEntry("     # Loops:             " + num_loops, "NA");
				log.fw_writeLogEntry("     Msecs Wait per Loop: " + wait_per_loop, "NA");
				log.fw_writeLogEntry("", "NA");
				
			}
			
						
			String return_code = "";
			String text_msg = "";
			String ret_cd = "";
			String text_to_look_for_returned = "";
			String return_code_output = "";
			int difference = 0;
			
			for (int r=0;r<num_loops;r++)
			{
				
				fw_execute_xml(webservice_name, current_endpoint, current_credentials, tc_test_data, 0);
				
				if (execute_and_validate_flag.equals("YES"))
				{
					text_msg = "";
					
					return_code_output = fw_validate_text_in_xml_response(webservice_name, text_to_look_for_in_response, "NO", 0);
					
					String[] return_code_arr = return_code_output.split("--");
					return_code = return_code_arr[0];
					text_to_look_for_returned = return_code_arr[1];
					
					if (return_code.equals("0"))
					{
						ret_cd = "0";
						log.fw_writeLogEntry("Validate Text in XML Response (XML Response File: " + webservice_name + ", Text to Look for: " + text_to_look_for_returned + ")" + text_msg, ret_cd);
						break;
					}
					else
					{
						difference = num_loops - r;
						if (difference == 1)
						{
							ret_cd = "1";
							text_msg = "*****" + text_to_look_for_returned + " NOT found in Webservice response " + webservice_name + "***";
							log.fw_writeLogEntry("Validate Text in XML Response (XML Response File: " + webservice_name + ", Text to Look for: " + text_to_look_for_returned + ")" + text_msg, ret_cd);

							log.fw_writeLogEntry("", "NA");
							log.fw_writeLogEntry("End of Webservice Loop Check.......", "NA");
							log.fw_writeLogEntry("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^", "NA");
							log.fw_writeLogEntry("", "NA");
						}
						else
						{
							ret_cd = "NA";
							text_msg = "*****" + text_to_look_for_returned + " NOT found in Webservice response " + webservice_name + "***";
							log.fw_writeLogEntry("Validate Text in XML Response (XML Response File: " + webservice_name + ", Text to Look for: " + text_to_look_for_returned + ")" + text_msg, ret_cd);
							Thread.sleep(wait_per_loop);							
						}
					}
				}
			}
			
		}
		else if (tc_event_name.equals("SQLExecute"))
		{
			String[] tc_object_name_arr = tc_object_name.split("_");
			String data_source_name = tc_object_name_arr[1];
			
			String[] tc_test_data_arr = tc_test_data.split("--");
			String column_values_to_get = tc_test_data_arr[0];
			String sql_to_execute = tc_test_data_arr[1];
			
			fw_execute_sql(data_source_name, sql_to_execute, column_values_to_get);
		}
		else if (tc_event_name.equals("ConvertStringCase"))
		{
			String[] tc_test_data_arr = tc_test_data.split(",");
			String file_name_temp = tc_test_data_arr[0];
			String case_to_convert_to = tc_test_data_arr[1];
			
			String[] file_name_arr = file_name_temp.split("_");
			String file_name = file_name_arr[1];
			
			fw_convert_string_case(file_name, case_to_convert_to);
		}
		else if (tc_event_name.equals("GetCurrentURL"))
		{
			fw_get_current_url();
		}
		else if (tc_event_name.equals("GetDevice"))
		{	
			String fake_real_flag = "";
			String device_type = "";
			String device_type_temp = "";
			String device_type_file = "";
			String port_type = "";
			String index_value = "";
			
			if (tc_test_data.contains("--"))
			{
				String[] tc_test_data_arr = tc_test_data.split("--");
				fake_real_flag = tc_test_data_arr[0];
				
				device_type_temp = tc_test_data_arr[1];
				if (device_type_temp.contains("FILE_"))
				{
					String[] device_type_arr = device_type_temp.split("_");
					device_type_file = device_type_arr[1];
					String workspacename = fw_get_workspace_name();
					String variable_file = workspacename + "\\variables\\" + device_type_file;
					device_type = fw_get_value_from_file(variable_file);
				}
				else
				{
					device_type = device_type_temp;
				}
				
				port_type = tc_test_data_arr[2];
				index_value = tc_test_data_arr[3];
			}
			else
			{
				String[] tc_test_data_arr = tc_test_data.split("_");
				fake_real_flag = tc_test_data_arr[0];
				device_type = tc_test_data_arr[1];
				port_type = tc_test_data_arr[2];
				index_value = tc_test_data_arr[3];
			}
			fw_testdata_get_device(fake_real_flag, device_type, port_type, index_value);
		}
		else if (tc_event_name.equals("GetSPALocation"))
		{	
			
			String[] tc_test_data_arr = tc_test_data.split("--");
			String env_value = tc_test_data_arr[0];
			String switch_type_value = tc_test_data_arr[1];
			String rate_center_value = tc_test_data_arr[2];
			String account_type_value = tc_test_data_arr[3];
			String index_value = tc_test_data_arr[4];
			
			fw_testdata_get_spalocation(env_value, switch_type_value, rate_center_value, account_type_value, index_value);
		}
		else if (tc_event_name.equals("GenerateRandomCharacters"))
		{	
			
			String[] tc_test_data_arr = tc_test_data.split("_");
			String candidate_characters = tc_test_data_arr[0];
			String length_of_characters = tc_test_data_arr[1];
			
			fw_generate_random_characters(candidate_characters, length_of_characters);
		}
		else if (tc_event_name.equals("XMLValidateTextinXMLResponse"))
		{
			String[] tc_object_name_arr = tc_object_name.split("_");
			String webservice_name = tc_object_name_arr[1];
			
			fw_validate_text_in_xml_response(webservice_name, tc_test_data, "YES", 0);
		}
		else if (tc_event_name.equals("XMLValidateTextNOTinXMLResponse"))
		{
			String[] tc_object_name_arr = tc_object_name.split("_");
			String webservice_name = tc_object_name_arr[1];
			
			fw_validate_text_notin_xml_response(webservice_name, tc_test_data, "YES", 0);
		}
		else if (tc_event_name.equals("XMLGetValueByTagname"))
		{
			String[] tc_object_name_arr = tc_object_name.split("_");
			String webservice_name = tc_object_name_arr[1];
			
			fw_get_value_from_xml_based_on_tagname(webservice_name, tc_test_data, 0);
		}
		else if (tc_event_name.equals("XMLGetValueBySearchCriteria"))
		{
			String[] tc_object_name_arr = tc_object_name.split("_");
			String webservice_name = tc_object_name_arr[1];
			
			fw_get_value_from_xml_based_on_searchcriteria(webservice_name, tc_test_data, 0);
		}
		else if (tc_event_name.equals("XMLGetValueByMultipleTagnames"))
		{
			String[] tc_object_name_arr = tc_object_name.split("_");
			String webservice_name = tc_object_name_arr[1];
			
			fw_get_value_from_xml_based_on_multiple_tagnames(webservice_name, tc_test_data, 0);
		}
		else if (tc_event_name.equals("GetTN"))
		{	
			String[] tc_data_arr = tc_test_data.split("_");
			String port_type = tc_data_arr[0];
			String phone_switch_type = tc_data_arr[1];
			String account_type = tc_data_arr[2];
			String index_value = tc_data_arr[3];
			fw_testdata_get_phone_number(port_type, phone_switch_type, account_type, index_value);
		}
		else if (tc_event_name.equals("GetSequence"))
		{	
			String[] tc_data_arr = tc_test_data.split("_");
			String sequenceentity = tc_data_arr[0];
			String index_value = tc_data_arr[1];
			fw_testdata_get_sequence(sequenceentity, index_value);
		}
		else if (tc_event_name.equals("IncrementValueByOne"))
		{			
			fw_increment_value_by_one(tc_test_data);
		}
		else if (tc_event_name.equals("NavigateBack"))
		{			
			fw_browser_navigate_back();
		}
		else if (tc_event_name.equals("GetCurrentDate"))
		{
			String[] tc_test_data_arr = tc_test_data.split(",");
			String variable_name = tc_test_data_arr[0];
			String date_format = tc_test_data_arr[1];
			
			dt.fw_generate_datetime_current(variable_name, date_format);
		}
		else if (tc_event_name.equals("GetFutureDate"))
		{
			String[] tc_test_data_arr = tc_test_data.split(",");
			String variable_name = tc_test_data_arr[0];
			String date_format = tc_test_data_arr[1];
			int number_of_days = Integer.valueOf(tc_test_data_arr[2]);
			
			dt.fw_generate_datetime_future(variable_name, date_format, number_of_days);
		}
		else if (tc_event_name.equals("SetVariable"))
		{
			String[] tc_test_data_arr = tc_test_data.split(",");
			String variable_name = tc_test_data_arr[0];
			
			// Modified by Mark on 5/2/2017
			//String variable_value = tc_test_data_arr[1];
			int pos_first_comma = tc_test_data.indexOf(",");
			int temp_value = tc_test_data.length();
			String variable_value = tc_test_data.substring(pos_first_comma+1, temp_value);
			// End of Modified by Mark on 5/2/2017
			
			fw_set_variable(variable_name, variable_value);
		}
		else if (tc_event_name.equals("GetWindowHandle"))
		{
			fw_get_window_handle();
		}
		else if (tc_event_name.equals("LoginToSSO"))
		{
			String[] tc_test_data_arr = tc_test_data.split(",");
			String user_id = tc_test_data_arr[0];
			String pass_id = tc_test_data_arr[1];
			
			fw_login_to_SSO(user_id, pass_id);
		}
		else if (tc_event_name.equals("SwitchToWindow"))
		{
			fw_switch_to_window(tc_test_data);
		}
		else if (tc_event_name.equals("SwitchToNewWindow"))
		{
			fw_switch_to_new_window();
		}
		else if (tc_event_name.equals("ValidateText"))
		{
			
			String split_delimiter = "";
			
			if (tc_test_data.contains("--"))
			{
				split_delimiter = "--";
			}
			else
			{
				split_delimiter = ",";
			}
			
			// Added by Mark on 6/8/2017
			if (tc_test_data.contains("STOPEXECUTIONONFAIL"))
			{
				fw_set_variable("stopexecutionuponfailure","yes");
			}
			else
			{
				fw_set_variable("stopexecutionuponfailure","no");
			}
			// End of Added by Mark on 6/8/2017
			
			String[] tc_test_data_arr = tc_test_data.split(split_delimiter);
			String expected_value = tc_test_data_arr[0];
			String actual_value = tc_test_data_arr[1];
			
			// Actual
			if (actual_value.contains("FILE_"))
			{
				workspace_name = fw_get_workspace_name();
				variables_path = workspace_name + "\\variables\\";
				
				String[] actual_value_arr = actual_value.split("_");
				String actual_value_file = actual_value_arr[1];			
				actual_value = fw_get_value_from_file(variables_path + actual_value_file);
			}
			// END OF Actual
			
			// Expected
			if (expected_value.contains("FILE_"))
			{
				workspace_name = fw_get_workspace_name();
				variables_path = workspace_name + "\\variables\\";
				
				String[] expected_value_arr = expected_value.split("_");
				String expected_value_file = expected_value_arr[1];			
				expected_value = fw_get_value_from_file(variables_path + expected_value_file);
			}
			// END OF Expected
						
			fw_validate_text(tc_object_name, expected_value, actual_value);
		}
		else if (tc_event_name.equals("LaunchBrowser"))
		{
			fw_launch_browser(tc_test_data);
		}
		else if (tc_event_name.equals("NavigateToURL"))
		{
			String workspacename = fw_get_workspace_name();
			String output_url_value = fw_get_value_from_file(workspacename + "\\variables\\ENV" + tc_test_data);
			
			fw_navigate_to_url(output_url_value);
		}
		else if (tc_event_name.equals("TerminateWindowProcesses"))
		{
			fw_terminate_window_processes(tc_test_data);	
		}
		else if (tc_event_name.equals("EnterDataTextbox"))
		{
			fw_enter_data_into_text_field(tc_object_name, object_tagname, object_attribute, object_attributevalue, tc_test_data, 0);	
		}
		else if (tc_event_name.equals("SwitchToFrame"))
		{
			fw_switch_frame(tc_object_name, object_tagname, object_attribute, object_attributevalue, tc_test_data, 0);	
		}
		else if (tc_event_name.equals("ClickAndEnterData"))
		{
			fw_click_and_enter_data(tc_object_name, object_tagname, object_attribute, object_attributevalue, tc_test_data, 0);	
		}
		else if (tc_event_name.equals("GetAttribute"))
		{
			fw_get_attribute_value(object_attribute, object_attributevalue, tc_test_data, 0);
		}
		else if (tc_event_name.equals("WriteLogHeader"))
		{
			log.fw_writeLogEntry(tc_test_data, "LOGHEADER");
		}
		else if (tc_event_name.equals("CheckForElementExistence"))
		{
			fw_check_element_existence(tc_object_name, object_tagname, object_attribute, object_attributevalue, tc_test_data, 0);	
		}
		else if (tc_event_name.equals("SwitchToDefaultContent"))
		{
			fw_switchto_defaultcontent();
		}
		else if (tc_event_name.equals("AcceptAlert"))
		{
			fw_accept_alert();
		}
		else if (tc_event_name.equals("SwitchToDriver"))
		{
			fw_switch_to_driver(tc_test_data);
		}
		else if (tc_event_name.equals("SelectCheckbox"))
		{
			fw_select_checkbox(tc_object_name, object_tagname, object_attribute, object_attributevalue, tc_test_data, 0);
			
			if (!object_extrainfo.contains("NO"))
			{
				if (out_object_extrainfo.isEmpty())
				{
					out_object_extrainfo = object_extrainfo;
				}
				else
				{
					out_object_extrainfo = out_object_extrainfo + "," + object_extrainfo;
				}
			}	
		}
		else if (tc_event_name.equals("ClickJAVASCRIPT"))
		{
			fw_click_element_using_javascript(tc_object_name, object_tagname, object_attribute, object_attributevalue, tc_test_data, 0);
			
			if (!object_extrainfo.contains("NO"))
			{
				if (out_object_extrainfo.isEmpty())
				{
					out_object_extrainfo = object_extrainfo;
				}
				else
				{
					out_object_extrainfo = out_object_extrainfo + "," + object_extrainfo;
				}
			}
			
		}
		else if (tc_event_name.equals("ClickButton"))
		{
			fw_click_button(tc_object_name, object_tagname, object_attribute, object_attributevalue, tc_test_data, 0);
		}
		else if (tc_event_name.equals("ClickSecurityCertificate"))
		{
			fw_click_security_certificate();
		}
		else if (tc_event_name.equals("SelectListValueByValue"))
		{
			fw_select_from_a_list_by_value(tc_object_name, object_tagname, object_attribute, object_attributevalue, tc_test_data, 0);	
		}
		else if (tc_event_name.equals("SelectListValueByVisibleText"))
		{
			fw_select_from_a_list_by_visible_text(tc_object_name, object_tagname, object_attribute, object_attributevalue, tc_test_data, 0);	
		}
		else if (tc_event_name.equals("GetText"))
		{
			fw_get_text(tc_object_name, object_tagname, object_attribute, object_attributevalue, tc_test_data, 0);
		}
		else if (tc_event_name.equals("StopExecution"))
		{
			
			log.fw_writeLogEntry(" ", "NA");
			log.fw_writeLogEntry(" ***** Stopping Execution ****** ", "NA");
			log.fw_writeLogEntry(" ", "NA");
			
			String runidvalue2 = fw_get_value_from_file(variables_path + "RUNIDVALUE");
			Object runidval = runidvalue2;
			String almtestid = fw_get_value_from_file(variables_path + "ALMTESTID");
			fwalm.fw_update_hpalm_test_case_execution_status(runidval, almtestid, "Blocked");
			
			System.exit(0);
		}
		
		if (!object_to_look_for_after_object_event.equals("NA") && !object_to_look_for_after_object_event.isEmpty()) 
		{
			if (tc_event_name.equals("XMLExecute"))
			{
				
			}
			else
			{
				String[] objecttolookforafterobjectevent_arr = object_to_look_for_after_object_event.split(",");
				int arr_length = objecttolookforafterobjectevent_arr.length;
				
				String tagname = objecttolookforafterobjectevent_arr[0];
				String text_to_look_for = objecttolookforafterobjectevent_arr[1];
				String number_loops = objecttolookforafterobjectevent_arr[2];
				String milliseconds_to_wait_per_loop = objecttolookforafterobjectevent_arr[3];
				
				String attribute_name = "NA";
				if (arr_length == 5)
				{
					attribute_name = objecttolookforafterobjectevent_arr[4];
				}
				
				fw_check_for_loading_page(tagname, text_to_look_for, number_loops, milliseconds_to_wait_per_loop, attribute_name);
			}
		}

		if (!milliseconds_to_wait_after_object_event.equals("0") && !milliseconds_to_wait_after_object_event.isEmpty())
		{
			Thread.sleep(Long.valueOf(milliseconds_to_wait_after_object_event));		
		}
		
	}
	
	/**
	 *
	 * This function executes an XML Request.
	 *  
	 * @param fileInput
	 * @param endpoint
	 * @param creds
	 * @param in_string
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @author Mark Elking
	 * @throws InterruptedException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 * @since 12/30/2016
	 */
	
	public String fw_execute_xml(String fileInput, String endpoint, String creds, String in_string, long milliseconds_to_wait) throws MalformedURLException, IOException, InterruptedException, NoSuchAlgorithmException, KeyManagementException 
	{

		String text_msg = "";
		String ret_cd = "0";
		String outputString = "";
		
		if (!fileInput.isEmpty() && !endpoint.isEmpty())
		{
			String workspace_name = fw_get_workspace_name();
			String template_input_file = workspace_name + "\\webservices\\templates\\" + fileInput;
			
			//Code to make a webservice HTTP request
			String responseString = "";
			
			/* Start of Fix */
			
	        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
	            public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
	            public void checkClientTrusted(X509Certificate[] certs, String authType) { }
	            public void checkServerTrusted(X509Certificate[] certs, String authType) { }

	        } };

	        SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

	        // Create all-trusting host name verifier
	        HostnameVerifier allHostsValid = new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) { return true; }
	        };
	        // Install the all-trusting host verifier
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	        
	        /* End of the fix*/
	        
	        
			URL url = new URL(endpoint);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection)connection;
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			
			// Get input file and convert to string
			
			String xmlInput = "";				
			FileReader fileReader = null;
			BufferedReader bfrReader = null;
			String strLine = null;
	
			fileReader = new FileReader(template_input_file);
			bfrReader = new BufferedReader(fileReader);
			while ((strLine = bfrReader.readLine()) != null)
			{
				
				String[] in_string_subs_arr = in_string.split("--");
				
				for (int f=0;f<in_string_subs_arr.length;f++)
				{
					String in_string_sub = in_string_subs_arr[f];
					
					String[] in_string_arr = in_string_sub.split(",");
					String in_string_variable_name = in_string_arr[0];
					String in_string_value = in_string_arr[1];
					
					if (strLine.contains(in_string_variable_name))
					{
						int pos_string = strLine.indexOf(in_string_variable_name);
						String first_str = strLine.substring(0, pos_string);
						int len_strLine = strLine.length();
						int len_in_string_variable_name = in_string_variable_name.length();
						int len_first_str = first_str.length();
						int start_pos = len_first_str + len_in_string_variable_name;
						String third_str = strLine.substring(start_pos, len_strLine);
						
						String sub_value = in_string_value;
						
						// Get dynamic value out of the file
						
						if (in_string_value.toUpperCase().contains("FILE"))
						{
							String[] in_string_value_arr = in_string_value.split("_");
							String in_variable_name = in_string_value_arr[1];
							
							String variable_file = workspace_name + "\\variables\\" + in_variable_name;
							String varInput = fw_get_value_from_file(variable_file);
							sub_value = varInput;
						}
						
						// Added on 4/27/2017
						
						if (in_string_value.toUpperCase().contains("FUNCTION_"))
						{
							String[] in_string_value_arr = in_string_value.split("_");
							String in_function_name = in_string_value_arr[1];
							
							if (in_function_name.equals("WSTIMESTAMP"))
							{
								int in_expired_time_in_seconds = Integer.valueOf(in_string_value_arr[2]);
								sub_value = fw_get_webservice_security_header_timestamp(in_expired_time_in_seconds);
							}
							else if (in_function_name.equals("WSUSERTOKEN"))
							{
								String[] creds_arr = creds.split(":");
								String username = creds_arr[0];
								String password = creds_arr[1];
								sub_value = fw_get_webservice_security_header_usertoken(username, password);
							}
							else
							{
								sub_value = "WS_FUNCTION_NOT_FOUND - FUNCTION NAME SHOULD BE WSTIMESTAMP or WSUSERTOKEN";
							}
						}
						// End of Added on 4/27/2017						
						
						// End of Get dynamic value out of the file
						
						strLine = first_str + sub_value + third_str;
					}
				
				}
				
				xmlInput = xmlInput + strLine;
				
			}
			if (null != bfrReader)
			{
				bfrReader.close();
			}
			if (fileReader != null)
			{
				fileReader.close();
			}		
			
			// Write runtime request xml into output runtime request file

			String runtime_request_output_file = workspace_name + "\\webservices\\runtime\\requests\\" + fileInput;
			
			try(PrintWriter out = new PrintWriter(runtime_request_output_file))
			{
			    out.println(xmlInput);
			    out.close();
			}
			// End of Write runtime request xml into output runtime request file
	
			
			// Execute webservice
			
			byte[] buffer = new byte[xmlInput.length()];
			buffer = xmlInput.getBytes();
			bout.write(buffer);
			byte[] b = bout.toByteArray();
			//httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
			//httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			
			
			String variable_file = workspace_name + "\\variables\\webservice_type";
			String webservice_type = fw_get_value_from_file(variable_file);
			
			if (fileInput.toUpperCase().contains("SONUS"))
			{
				httpConn.setRequestProperty("SOAPAction", "retrieveRequest");
			}
            
			if (webservice_type.equals("JSON"))
			{
				httpConn.setRequestProperty("Content-Type", "application/json");
				httpConn.setRequestProperty("accept", "application/json");
				httpConn.setRequestProperty("accept-encoding", "gzip, deflate");
				httpConn.setRequestProperty("accept-language", "en-US,en;q=0.8");	
			}
			else
			{
				httpConn.setRequestProperty("Content-Type", "text/xml");	
				//httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				
			}
			
			if (fileInput.toUpperCase().contains("META"))
			{
				String encodeCreds = "Basic " + "VmlydE1ldGFUTU8tQVVUTzpiMWVfMil4NQ==";
				httpConn.setRequestProperty("Authorization", encodeCreds);
			}
			else if (!creds.isEmpty())
			{
				String encodeCreds = "Basic " + new String(new Base64().encode(creds.getBytes()));
				httpConn.setRequestProperty("Authorization", encodeCreds);
			}
			
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			OutputStream out = httpConn.getOutputStream();
			//Write the content of the request to the outputstream of the HTTP Connection.
			out.write(b);
			out.close();
			//Ready with sending the request.
			
			// Added 204 condition on 4/11/2017
			// HTTP Status Code 204: The server has successfully fulfilled the request 
			// and that there is no additional content to send in the response payload body.
			
			if(httpConn.getResponseCode() == 204)
			{
				ret_cd = "0";
			}
			else
			{
				
				// Added on 3/27/2017
				BufferedReader in = null;
				
				int junk = httpConn.getResponseCode();
				//System.out.println("Junk:" + junk);
				
				if(httpConn.getResponseCode() == 200 || httpConn.getResponseCode() == 307)
				{
					in = new BufferedReader(new
							InputStreamReader(httpConn.getInputStream()));
				}
				else
				{
					in = new BufferedReader(new
							InputStreamReader(httpConn.getErrorStream()));
				}
				// End of Added on 3/27/2017
				
				//Read the response.
				//InputStreamReader isr =
				//new InputStreamReader(httpConn.getInputStream());
				//BufferedReader in = new BufferedReader(isr);
				 
				//Write the SOAP message response to a String.
				while ((responseString = in.readLine()) != null) {
				outputString = outputString + responseString;
				}
				
	
				// Write runtime request xml into output runtime request file
		
				String runtime_response_output_file = workspace_name + "\\webservices\\runtime\\responses\\" + fileInput;
				
				try(PrintWriter outresponse = new PrintWriter(runtime_response_output_file))
				{
					outresponse.println(outputString);
					outresponse.close();
				}
				
				// End of Write runtime request xml into output runtime request file
				
				ret_cd = "0";
			
			}
			
		}
		else
		{

			if (fileInput.isEmpty())
			{
				text_msg = "*************** fileInput is Not Defined ****************";
				ret_cd = "1";	
			}
			if (endpoint.isEmpty())
			{
				text_msg = "*************** endpoint is Not Defined ****************";
				ret_cd = "1";
			}
			/*if (creds.isEmpty())
			{
				text_msg = "*************** creds are Not Defined ****************";
				ret_cd = "1";
			}
			*/
			
		}
		
		log.fw_writeLogEntry("Execute XML (Name: " + fileInput + ", Value: " + in_string + ")" + text_msg, ret_cd);
		
		Thread.sleep(milliseconds_to_wait);
		
		
		return outputString;
	
	}
	
	/**
	 *
	 * This function gets a value from an XML file based on tagname.
	 *  
	 * @param fileInput
	 * @param tagname_to_look_for
	 * @param milliseconds_to_wait
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @author Mark Elking
	 * @throws InterruptedException 
	 * @since 1/27/2017
	 */
	
	public void fw_get_value_from_xml_based_on_tagname(String fileInput, String tagname_to_look_for, long milliseconds_to_wait) throws MalformedURLException, IOException, InterruptedException 
	{

		String text_msg = "";
		String ret_cd = "0";
		
		String workspace_name = fw_get_workspace_name();
		String xml_response_input_file = workspace_name + "\\webservices\\runtime\\responses\\" + fileInput;
		String xmlInput = fw_get_value_from_file(xml_response_input_file);
		
		// Find String
		
		String input_string = xmlInput;
		String current_character;
		String output_value = "";
		String start_index_found_flag = "";
		
		int pos_string = input_string.indexOf("<" + tagname_to_look_for + ">");
		int start_index = 0;
		int end_index = 0;
		
		if (pos_string == -1)
		{
			text_msg = "*****<" + tagname_to_look_for + "> NOT found in Webservice response " + fileInput + "***";
			ret_cd = "1";
		}
		else
		{
			for (int p=1;p<75;p++)
			{
				current_character = input_string.substring(pos_string + p, pos_string + p + 1);
				if (current_character.equals(">"))
				{
					start_index = pos_string + p + 1;
					start_index_found_flag = "yes";
				}
				
				if (current_character.equals("<") && start_index_found_flag.equals("yes"))
				{
					end_index = pos_string + p;
					output_value = input_string.substring(start_index, end_index);
					ret_cd = "0";
					break;
				}
			}
		}
		
		
		// End of Find String
		
		// Write variable value into variable file
		
		String runtime_request_output_file = workspace_name + "\\variables\\" + tagname_to_look_for;
		
		try(PrintWriter out = new PrintWriter(runtime_request_output_file))
		{
		    out.println(output_value);
		    out.close();
		}
		
		// End of Write variable value into variable file
		
		log.fw_writeLogEntry("Get Value from XML Response by Tag Name (XML Response File: " + fileInput + ", Tagname to Look For: " + tagname_to_look_for + ", Value: " + output_value + ")" + text_msg, ret_cd);
		
		Thread.sleep(milliseconds_to_wait);
	
	}

	/**
	 *
	 * This function gets a value from an XML file based on search criteria.
	 *  
	 * @param fileInput
	 * @param tagname_to_look_for
	 * @param milliseconds_to_wait
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @author Mark Elking
	 * @throws InterruptedException 
	 * @since 4/27/2017
	 */
	
	public void fw_get_value_from_xml_based_on_searchcriteria(String fileInput, String search_criteria, long milliseconds_to_wait) throws MalformedURLException, IOException, InterruptedException 
	{

		String text_msg = "";
		String ret_cd = "0";
		
		String workspace_name = fw_get_workspace_name();
		String xml_response_input_file = workspace_name + "\\webservices\\runtime\\responses\\" + fileInput;
		String xmlInput = fw_get_value_from_file(xml_response_input_file);
		
		// Find String
		
		String input_string = xmlInput;	

		int pos_search_criteria = input_string.indexOf(search_criteria);
		int pos_end_carat = input_string.indexOf(">", pos_search_criteria + 1);
		int pos_value = input_string.indexOf("value=", pos_search_criteria + 1);
		String out_value = "";
		
		if (pos_search_criteria == -1)
		{
			text_msg = "*****" + search_criteria + " NOT found in Webservice response " + fileInput + "***";
			ret_cd = "1";
		}
		else if ((pos_end_carat > pos_value) && (pos_value != -1))
		{
			int start_index = input_string.indexOf("\"", pos_value);
			int end_index = input_string.indexOf("\"", start_index+1);
			out_value = input_string.substring(start_index + 1, end_index);
			
			ret_cd = "0";
		}
		else if (pos_value == -1)
		{
			text_msg = "*****value= NOT found for this search criteria " + search_criteria + " in Webservice response " + fileInput + "***";
			ret_cd = "1";
		}
		else
		{
			text_msg = "***** > not found in Webservice response " + fileInput + "***";
			ret_cd = "1";
		}
		
		
		// End of Find String
		
		// Write variable value into variable file
		
		String runtime_request_output_file = workspace_name + "\\variables\\" + "outputvaluebasedonsearchcriteria";
		
		try(PrintWriter out = new PrintWriter(runtime_request_output_file))
		{
		    out.println(out_value);
		    out.close();
		}
		
		// End of Write variable value into variable file
		
		log.fw_writeLogEntry("Get Value from XML Response by Search Criteria (XML Response File: " + fileInput + ", Search Criteria: " + search_criteria + ", Value: " + out_value + ")" + text_msg, ret_cd);
		
		Thread.sleep(milliseconds_to_wait);
	
	}
	
	/**
	 *
	 * This function gets a value from an XML file based on multiple tagnames.
	 *  
	 * @param fileInput
	 * @param input_string
	 * @param milliseconds_to_wait
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @author Mark Elking
	 * @throws InterruptedException 
	 * @since 2/7/2017
	 */
	
	public void fw_get_value_from_xml_based_on_multiple_tagnames(String fileInput, String tagname_string, long milliseconds_to_wait) throws MalformedURLException, IOException, InterruptedException 
	{

		String text_msg = "";
		String ret_cd = "0";
		String output_string = "";
		
		String workspace_name = fw_get_workspace_name();
		String xml_response_input_file = workspace_name + "\\webservices\\runtime\\responses\\" + fileInput;
		String xmlInput = fw_get_value_from_file(xml_response_input_file);
		
		// Find String
		
		String[] tagname_string_arr = tagname_string.split("&&");
		String tagname_list = tagname_string_arr[0];
		String tagname_output_file = tagname_string_arr[1];
		String[] tagname_list_arr = tagname_list.split(",");
		int tagname_list_len = tagname_list_arr.length;
		
		String xml_response_string = xmlInput;
		boolean continue_flag = true;
		int initial_pos_string = 0;
		int cur_pos_string = 1;
		String current_string = "";
		String tags_exceeded = "";
		
		while (continue_flag) 
		{
			
			tags_exceeded = "NO";
			text_msg = "";
			
			for (int i=0;i<tagname_list_len;i++)
			{
				String[] tagname_arr = tagname_list_arr[i].split("--");
				String tagname_event = tagname_arr[0];
				String tagname_string_to_look_for = tagname_arr[1];
				
				// Substitute FILE_
				if (tagname_string_to_look_for.contains("FILE_"))
				{
					String[] tagname_string_to_look_for_arr = tagname_string_to_look_for.split("_");
					String text_to_validate_file = tagname_string_to_look_for_arr[1];
					
					String[] text_to_validate_file_arr = text_to_validate_file.split("##");
					String text_filename = text_to_validate_file_arr[0];
					String text_remaining = text_to_validate_file_arr[1];
					
					String variable_file = workspace_name + "\\variables\\" + text_filename;			

					FileReader fileReader2 = null;
					BufferedReader bfrReader2 = null;
					String strLine2;
					
					fileReader2 = new FileReader(variable_file);
					bfrReader2 = new BufferedReader(fileReader2);
					tagname_string_to_look_for = "";
					while ((strLine2 = bfrReader2.readLine()) != null)
					{	
						tagname_string_to_look_for = tagname_string_to_look_for + strLine2;	
					}
					if (null != bfrReader2)
					{
						bfrReader2.close();
					}
					if (fileReader2 != null)
					{
						fileReader2.close();
					}
					
					tagname_string_to_look_for = tagname_string_to_look_for + text_remaining;
					
				}
				// End of Substitute FILE_
				
				if (!tagname_event.contains("("))
				{
					cur_pos_string = xml_response_string.indexOf(tagname_string_to_look_for, cur_pos_string);
					initial_pos_string = cur_pos_string;
					if (cur_pos_string == -1)
					{
						continue_flag = false;
						ret_cd = "1";
						text_msg = tagname_string_to_look_for + " not found";
						break;
					}
				}
				else
				{
					int left_paren_pos = tagname_event.indexOf("(");
					int right_paren_pos = tagname_event.indexOf(")");
					String number_tags = tagname_event.substring(left_paren_pos + 1, right_paren_pos);
					int num_tags = Integer.valueOf(number_tags);
					int cur_tags = 0;
					int tagname_string_to_look_for_len = tagname_string_to_look_for.length();
					String count_tag_string = "";
					
					if (tagname_event.contains("(") && tagname_event.contains("SEARCHFORWARD"))
					{	
						for (int j=cur_pos_string;j<10000000;j++)
						{
							current_string = xml_response_string.substring(j, j + tagname_string_to_look_for_len);
							count_tag_string = xml_response_string.substring(j, j + 2);
							
							if (current_string.equals(tagname_string_to_look_for))
							{
								cur_pos_string = j;
								break;
							}
							
							if (count_tag_string.equals("</"))
							{
								cur_tags = cur_tags + 1;
								if (cur_tags == num_tags)
								{
									tags_exceeded = "YES";
									ret_cd = "1";
									text_msg = "Forward Current tags greater than the number expected";
									break;
								}
							}
							
						}
					}
					else if (tagname_event.contains("(") && tagname_event.contains("SEARCHBACKWARD"))
					{
						for (int j=cur_pos_string;j>0;j--)
						{
							current_string = xml_response_string.substring(j, j + tagname_string_to_look_for_len);
							count_tag_string = xml_response_string.substring(j, j + 2);
							
							if (current_string.equals(tagname_string_to_look_for))
							{
								cur_pos_string = j;
								break;
							}
							
							if (count_tag_string.equals("</"))
							{
								cur_tags = cur_tags + 1;
								if (cur_tags == num_tags)
								{
									tags_exceeded = "YES";
									ret_cd = "1";
									text_msg = "Backward Current tags greater than the number expected";
									break;
								}
							}
							
						}
					}	
				}
				
			}
			
			if (tags_exceeded.equals("YES"))
			{
				cur_pos_string = initial_pos_string + 5;	
			}
			else
			{
			
				int pos_output_begin = xml_response_string.indexOf(">",cur_pos_string + 2);
				int pos_output_end = xml_response_string.indexOf("<",cur_pos_string + 2);
				
				output_string = xml_response_string.substring(pos_output_begin + 1, pos_output_end);
				
				// Write variable value into variable file
				String runtime_request_output_file = workspace_name + "\\variables\\" + tagname_output_file;
				
				try(PrintWriter out = new PrintWriter(runtime_request_output_file))
				{
				    out.println(output_string);
				    out.close();
				}
				// End of Write variable value into variable file
				
				ret_cd = "0";
				continue_flag=false;
				
			}
			
		}
		
		log.fw_writeLogEntry("Get Value from XML Response based on Multiple Tagnames (XML Response File: " + fileInput + ", Tagname String: " + tagname_string + ", Output File: " + tagname_output_file + ", Output String: " + output_string + ")" + text_msg, ret_cd);
		
		Thread.sleep(milliseconds_to_wait);
	
	}
	
	/**
	 *
	 * This function get the content of a file and put into a string value.
	 *  
	 * @param file_name
	 * @author Mark Elking
	 * @throws IOException 
	 * @since 2/5/2017
	 */

	public String fw_get_value_from_file(String file_name) throws IOException 
	{
		
		String variable_value = "";				
		FileReader fileReader = null;
		BufferedReader bfrReader = null;
		String strLine = null;
		
		fileReader = new FileReader(file_name);
		bfrReader = new BufferedReader(fileReader);
		
		while ((strLine = bfrReader.readLine()) != null)
		{	
			variable_value = variable_value + strLine;
		}
		if (null != bfrReader)
		{
			bfrReader.close();
		}
		if (fileReader != null)
		{
			fileReader.close();
		}		
		
		return variable_value;
		
	}
	
	/**
	 *
	 * This function gets workspace name.
	 *  
	 * @author Mark Elking
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @since 2/15/2017
	 */

	public String fw_get_workspace_name() throws IOException, InterruptedException 
	{
		
		String workspace_name = System.getProperty("user.dir");
		if (!workspace_name.contains("\\")) 
		{
			workspace_name.replace("\\","\\\\");	
		}
		
		return workspace_name;
		
	}
	
	/**
	 *
	 * This function validates text in XML response.
	 *  
	 * @param fileInput
	 * @param text_to_validate
	 * @param write_out_log_entry_flag
	 * @param milliseconds_to_wait
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @author Mark Elking
	 * @throws InterruptedException 
	 * @since 1/27/2017
	 */

	public String fw_validate_text_in_xml_response(String fileInput, String text_to_validate, String write_out_log_entry_flag, long milliseconds_to_wait) throws MalformedURLException, IOException, InterruptedException 
	{

		String text_msg = "";
		String ret_cd = "";
		
		String workspace_name = fw_get_workspace_name();
		String xml_response_input_file = workspace_name + "\\webservices\\runtime\\responses\\" + fileInput;
		String xmlInput = fw_get_value_from_file(xml_response_input_file);
		
		// Build Text to Validate String
		
		String[] text_to_validate_arr = text_to_validate.split(",");
		int text_to_validate_arr_length = text_to_validate_arr.length;
		String text_to_validate_current;
		
		String strLine2;
		String text_to_validate_final = "";
		
		for (int p=0;p<text_to_validate_arr_length;p++)
		{
			text_to_validate_current = text_to_validate_arr[p];
			
			if (text_to_validate_current.contains("FILE_"))
			{
				String[] text_to_validate_current_arr = text_to_validate_current.split("_");
				String text_to_validate_file = text_to_validate_current_arr[1];
						
				String variable_file = workspace_name + "\\variables\\" + text_to_validate_file;			
				strLine2 = fw_get_value_from_file(variable_file);
				text_to_validate_final = text_to_validate_final + strLine2;
				
			}
			else
			{
				text_to_validate_final = text_to_validate_final + text_to_validate_current;
			}
		}
		
		// End of Build Text to Validate String
		
				
		
		// Find String
		
		int pos_string = xmlInput.indexOf(text_to_validate_final);
		
		if (pos_string == -1)
		{
			text_msg = "*****" + text_to_validate_final + " NOT found in Webservice response " + fileInput + "***";
			ret_cd = "1";
		}
		else
		{
			ret_cd = "0";
		}
		
		String return_string = ret_cd + "--" + text_to_validate_final;
		
		// End of Find String

		if (write_out_log_entry_flag.toUpperCase().equals("YES"))
		{
			log.fw_writeLogEntry("Validate Text in XML Response (XML Response File: " + fileInput + ", Text to Look for: " + text_to_validate_final + ")" + text_msg, ret_cd);
		}
		
		Thread.sleep(milliseconds_to_wait);
	
		return return_string;
		
	}

	/**
	 *
	 * This function validates text NOT in XML response.
	 *  
	 * @param fileInput
	 * @param text_to_validate
	 * @param write_out_log_entry_flag
	 * @param milliseconds_to_wait
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @author Mark Elking
	 * @throws InterruptedException 
	 * @since 1/27/2017
	 */

	public String fw_validate_text_notin_xml_response(String fileInput, String text_to_validate, String write_out_log_entry_flag, long milliseconds_to_wait) throws MalformedURLException, IOException, InterruptedException 
	{

		String text_msg = "";
		String ret_cd = "";
		
		String workspace_name = fw_get_workspace_name();
		String xml_response_input_file = workspace_name + "\\webservices\\runtime\\responses\\" + fileInput;
		String xmlInput = fw_get_value_from_file(xml_response_input_file);
		
		// Build Text to Validate String
		
		String[] text_to_validate_arr = text_to_validate.split(",");
		int text_to_validate_arr_length = text_to_validate_arr.length;
		String text_to_validate_current;
		
		String strLine2;
		String text_to_validate_final = "";
		
		for (int p=0;p<text_to_validate_arr_length;p++)
		{
			text_to_validate_current = text_to_validate_arr[p];
			
			if (text_to_validate_current.contains("FILE_"))
			{
				String[] text_to_validate_current_arr = text_to_validate_current.split("_");
				String text_to_validate_file = text_to_validate_current_arr[1];
				
				String variable_file = workspace_name + "\\variables\\" + text_to_validate_file;			
				strLine2 = fw_get_value_from_file(variable_file);
				text_to_validate_final = text_to_validate_final + strLine2;
				
			}
			else
			{
				text_to_validate_final = text_to_validate_final + text_to_validate_current;
			}
		}
		
		// End of Build Text to Validate String
		
				
		
		// Find String
		
		int pos_string = xmlInput.indexOf(text_to_validate_final);
		
		if (pos_string == -1)
		{
			ret_cd = "0";
		}
		else
		{
			ret_cd = "1";
			text_msg = "*****" + text_to_validate_final + " unexpectedly found in Webservice response " + fileInput + "***";
		}
		
		String return_string = ret_cd + "--" + text_to_validate_final;
		
		// End of Find String

		if (write_out_log_entry_flag.toUpperCase().equals("YES"))
		{
			log.fw_writeLogEntry("Validate Text NOT in XML Response (XML Response File: " + fileInput + ", Text to Look for: " + text_to_validate_final + ")" + text_msg, ret_cd);
		}
		
		Thread.sleep(milliseconds_to_wait);
	
		return return_string;
		
	}
	
	/**
	 *
	 * This function sets a variable.
	 *  
	 * @param variable_name
	 * @param variable_value
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @author Mark Elking
	 * @throws InterruptedException 
	 * @since 1/30/2017
	 */
	
	public void fw_set_variable(String variable_name, String variable_value) throws MalformedURLException, IOException, InterruptedException 
	{

		String text_msg = "";
		String output_string = "";
		String file_name = "";
		String skiplog = "";
		String nosub = "";
		
		if (variable_name.contains("SKIP"))
		{
			String[] variable_name_arr = variable_name.split("--");
			variable_name = variable_name_arr[1];
			skiplog = "YES";
		}
		
		if (variable_name.contains("NOSUB!!"))
		{
			String[] variable_name_arr = variable_name.split("!!");
			variable_name = variable_name_arr[1];
			nosub = "YES";
			skiplog = "YES";
		}
		
		String workspace_name = fw_get_workspace_name();
		
		if (nosub.equals("YES"))
		{
			output_string = variable_value;
		}
		else if (variable_value.contains("FILE_"))
		{
			
			String[] variable_value_arr = variable_value.split("--");
			
			int num_strings = variable_value_arr.length;
			String current_string = "";
			
			for (int y=0;y<num_strings;y++)
			{
				
				current_string = variable_value_arr[y];
			
				if (current_string.contains("FILE_"))
				{
					String[] current_string_arr = current_string.split("_");
					file_name = current_string_arr[1];
					
					String variable_file = workspace_name + "\\variables\\" + file_name;
					String varInput = fw_get_value_from_file(variable_file);

					output_string = output_string + varInput;
				
				}
				else
				{
					output_string = output_string + current_string;
				}
				
			}
		}
		else
		{
			output_string = variable_value;
		}		
		
		String variable_file = "";
		
		if (variable_name.equals("WORKSPACE"))
		{
			variable_file = "C:\\Temp\\" + variable_name;	
		}
		else
		{
			variable_file = workspace_name + "\\variables\\" + variable_name;	
		}
		
		try(PrintWriter out = new PrintWriter(variable_file))
		{
		    out.println(output_string);
		    out.close();
		}
		
		if (!skiplog.equals("YES"))
		{
			log.fw_writeLogEntry("Set Variable (Name: " + variable_name + ", Input Value: " + variable_value + ", Output Value: " + output_string + ")" + text_msg, "0");
		}
	}
	
	/**
	 *
	 * This function completes TPV.
	 *  
	 * @param phone_number_file
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @author Mark Elking
	 * @throws InterruptedException 
	 * @since 2/8/2017
	 */
	
	public void fw_complete_tpv(String phone_number_file) throws MalformedURLException, IOException, InterruptedException 
	{

		String text_msg = "";
		String input_value = "";
		
		String workspace_name = fw_get_workspace_name();
		//String completetpvexecutable = "C:\\Program Files\\Microsoft Office\\Office12\\excel.exe " + workspace_name + "\\webservices\\runtime\\tpv\\CompleteTPV.xls";
		String completetpvexecutable = workspace_name + "\\webservices\\tpv\\CompleteTPV.xls";
		String variable_file = workspace_name + "\\variables\\" + phone_number_file;
		input_value = fw_get_value_from_file(variable_file);
		
		String output_file = workspace_name + "\\webservices\\tpv\\completeTPVphone";
		
		try(PrintWriter out = new PrintWriter(output_file))
		{
		    out.println(input_value);
		    out.close();
		}
		
		try
		{    
			//Runtime.getRuntime().exec(completetpvexecutable);
			Desktop.getDesktop().open(new File(completetpvexecutable));
        }
		catch(IOException  e)
		{
			e.printStackTrace();
        }  
		
		log.fw_writeLogEntry("XML Complete TPV (Variable File: " + variable_file + ", Runtime File: " + output_file + ")" + text_msg, "0");
	
	}
	
	/**
	 *
	 * This function increments a value by 1.
	 *  
	 * @param variable_name
	 * @param variable_value
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @author Mark Elking
	 * @throws InterruptedException 
	 * @since 2/8/2017
	 */
	
	public void fw_increment_value_by_one(String file_name) throws MalformedURLException, IOException, InterruptedException 
	{

		String text_msg = "";
		String input_value = "";
		
		String workspace_name = fw_get_workspace_name();
		String variable_file = workspace_name + "\\variables\\" + file_name;
		input_value = fw_get_value_from_file(variable_file);
		
		int output_value = Integer.valueOf(input_value) + 1;
		
		try(PrintWriter out = new PrintWriter(variable_file))
		{
		    out.println(output_value);
		    out.close();
		}
		
		log.fw_writeLogEntry("Increment Value by 1 (Name: " + file_name + ", New Value: " + output_value + ")" + text_msg, "0");
	
	}
	
	/**
	 * This function checks to see if element exists.
	 * The inputs required for this function are fieldname, tagname, attribute, attributevalue, fieldvalue and milliseconds to wait.
	 * 		fieldname - any text value representing the field.  Example is "Address".
	 * 		tagname - the tagname used to help search for the object on the page.  Example is "input".
	 * 		locator - the locator used to help identify an object on the page.  Example is "id" or "name".
	 * 		locatorvalue - the locator used to help identify an object on the page.  Example is "phoneNumber".
	 * 		fieldvalue - the value to put into the field once the object has been identified.
	 * 		milliseconds - the time to wait after the action has been performed on the specified object.
	 * 
	 * @param fieldname
	 * @param tagname
	 * @param locator
	 * @param locatorvalue
	 * @param fieldvalue
	 * @param milliseconds_to_wait
	 * @throws InterruptedException
	 * 
	 * @author Mark Elking
	 * @throws IOException 
	 * @since 1/13/2017
	 * 
	 */
	
	public Boolean fw_check_element_existence(String fieldname, String tagname, String locator, String locatorvalue, String fieldvalue, long milliseconds_to_wait) throws InterruptedException, IOException 
	{

		String text_msg = "";
		String ret_cd = "";
		String field_ready_flag = "";
		boolean field_isEmpty=true;
		boolean field_isEnabled=false;
		boolean field_isDisplayed=false;
		return_existence=false;
		int num_loops = 1;
		long wait_time_per_loop = 1000;
		
		
		try
		{
			
			if (!fieldvalue.equals(""))
			{
				String[] fieldvalue_arr = fieldvalue.split(",");
				num_loops = Integer.valueOf(fieldvalue_arr[0]);
				wait_time_per_loop = Long.valueOf(fieldvalue_arr[1]);
			}
			
			if (locator.equalsIgnoreCase("name") || locator.equalsIgnoreCase("id") || locator.equalsIgnoreCase("class") || locator.equalsIgnoreCase("css") || locator.equalsIgnoreCase("xpath") || locator.equalsIgnoreCase("link") || locator.equalsIgnoreCase("partiallink"))
			{
				for (int m=1;m<num_loops;m++)
				{
					field_isEmpty = driver.findElements(fw_get_element_object(locator, locatorvalue)).isEmpty();
					
					try
					{
						field_isEnabled = driver.findElement(fw_get_element_object(locator, locatorvalue)).isEnabled();
					}
					catch (Exception e) 
					{
						System.out.println("Field isEnabled Check, Inside Loop: " + m + ".  Continuing check....");
					}

					try
					{
						field_isDisplayed = driver.findElement(fw_get_element_object(locator, locatorvalue)).isDisplayed();
					}
					catch (Exception e) 
					{
						System.out.println("Field isDisplayed Check, Inside Loop: " + m + ".  Continuing check....");
					}
					
					if(!field_isEmpty && field_isEnabled && field_isDisplayed)
					{
						field_ready_flag = "YES";
						break;
					}
					else
					{
						Thread.sleep(wait_time_per_loop);
					}
					
				}
			}
			else if (locator.equals("NA")) 
			{
				
				for (int m=1;m<num_loops;m++)
				{
					List<WebElement> rows = driver.findElements(By.tagName(tagname));
					Iterator<WebElement> iter = rows.iterator();
					
					while (iter.hasNext()) {
						WebElement item = iter.next();
						String label = item.getText().trim();
						
						if (label.contains(locatorvalue))
						{
							field_isEmpty = false;
							field_isEnabled = item.isEnabled();
							field_isDisplayed = item.isDisplayed();
							
							if(field_isEnabled && field_isDisplayed)
							{
								field_ready_flag = "YES";
								break;
							}
						}
					}
					
					if (field_ready_flag.equals("YES"))
					{
						break;
					}
				}
			}
			
			if (field_ready_flag.equals("YES"))
			{
				ret_cd = "0";
				return_existence = true;
			}
			else
			{
				text_msg = "*************** Field NOT Found or Field Disabled or Field Not Displayed ****************";
				ret_cd = "1";
				return_existence = false;
			}
			
			log.fw_writeLogEntry("Check Element Existence (Name: " + fieldname + ", Value: " + fieldvalue + ", Element Exists? " + return_existence + ", isEmpty: " + field_isEmpty + ", isEnabled: " + field_isEnabled + ", isDisplayed: " + field_isDisplayed + ")" + text_msg, ret_cd);
			
			Thread.sleep(milliseconds_to_wait);
		
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
			text_msg = "*************** EXCEPTION ****************";
			ret_cd = "1";
			
			String exception_string = ExceptionUtils.getStackTrace(e);			
			log.fw_writeLogEntry("Check Element Existence (Name: " + fieldname + ", Value: " + fieldvalue + ", Element Exists? " + return_existence + ", isEmpty: " + field_isEmpty + ", isEnabled: " + field_isEnabled + ", isDisplayed: " + field_isDisplayed + ") - " + text_msg + " - " + exception_string, ret_cd);
		}
		
		return return_existence;
			
	}

	/**
	 * This function returns the attribute value for the given attribute name.
	 * The inputs required for this function are fieldname, tagname, attribute, attributevalue, fieldvalue and milliseconds to wait.
	 * 		locator - the locator used to help identify an object on the page.  Example is "id" or "name".
	 * 		locatorvalue - the locator used to help identify an object on the page.  Example is "phoneNumber".
	 * 		attribute_name - the attribute name of the object which you want the value for.
	 * 		milliseconds - the time to wait after the action has been performed on the specified object.
	 * 
	 * @param locator
	 * @param locatorvalue
	 * @param attribute_name
	 * @param milliseconds_to_wait
	 * @throws InterruptedException
	 * 
	 * @author Mark Elking
	 * @throws IOException 
	 * @since 1/15/2017
	 * 
	 */
	
	public String fw_get_attribute_value(String locator, String locatorvalue, String attribute_name, long milliseconds_to_wait) throws InterruptedException, IOException 
	{

		String attribute_value = "";
		
		try
		{
			
			if (locator.equals("name")) 
			{
				attribute_value = driver.findElement(By.name(locatorvalue)).getAttribute(attribute_name);
			}
			else if (locator.equals("id")) 
			{
				attribute_value = driver.findElement(By.id(locatorvalue)).getAttribute(attribute_name);
			}
			else if (locator.equals("class")) 
			{
				attribute_value = driver.findElement(By.className(locatorvalue)).getAttribute(attribute_name);
			}
			else if (locator.equals("css")) 
			{
				attribute_value = driver.findElement(By.cssSelector(locatorvalue)).getAttribute(attribute_name);
			}
			else if (locator.equals("xpath")) 
			{
				attribute_value = driver.findElement(By.xpath(locatorvalue)).getAttribute(attribute_name);
			}
			
			fw_set_variable("attributevalue", attribute_value);
			
			log.fw_writeLogEntry("     Get Attribute Value (Locator: " + locator + ", Locator Value: " + locatorvalue + ", Attribute Name: " + attribute_name + ", Attribute Value: " + attribute_value + ")", "NA");
			
			Thread.sleep(milliseconds_to_wait);
		
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
			String text_msg = "*************** EXCEPTION ****************";
			
			String exception_string = ExceptionUtils.getStackTrace(e);			
			log.fw_writeLogEntry("     Get Attribute Value (Locator: " + locator + ", Locator Value: " + locatorvalue + ", Attribute Name: " + attribute_name + ", Attribute Value: " + attribute_value + ") - " + text_msg + " - " + exception_string, "NA");
		}
		
		return attribute_value;
		
	}
	
	/**
	 * 
	 * * This function generates random string based on characters and length provided.
	 * 
	 * @param candidateChars
	 * @param length
	 * @author Mark Elking
	 * @since 4/26/2017
	 * @return 
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	
	public void fw_generate_random_characters(String candidateChars, String length) throws MalformedURLException, IOException, InterruptedException 
	{
		int temp_length = Integer.valueOf(length);
	    StringBuilder sb = new StringBuilder();
	    Random random = new Random();
	    for (int i = 0; i < temp_length; i++) {
	        sb.append(candidateChars.charAt(random.nextInt(candidateChars
	                .length())));
	    }

	    fw_set_variable("generaterandomcharacter", sb.toString());
	    
	    log.fw_writeLogEntry("Generate Random Characters (Candidate of Characters: " + candidateChars + ", Length Desired: " + length + ", Generated String: " + sb.toString() + ")", "0");
	    
	}

	/**
    * This method writes out a webservice security header timestamp.
    * 
    * @param expiredTimeInSeconds - accepts any time in seconds
    * @return - Dynamically generated security time stamp with expiration based on seconds parameter along with Security tag
    * @author Danny M. Byers
    * @since 04/26/2017
    * 
    */
	
	public String fw_get_webservice_security_header_timestamp(int expiredTimeInSeconds) throws IOException, InterruptedException
	{
		
		Logging log = new Logging();
      
		log.fw_writeLogEntry("", "NA");
      
		// takes input of seconds to and coverts it to milliseconds
		int timeToAdd = (expiredTimeInSeconds * 1000);
      
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
      
		// Convert timestamp to instant
		Instant instant = timestamp.toInstant();
      
		// adds two minutes to expired timestamp
		Instant instantExpired = instant.plusMillis(timeToAdd);
      
		// converts back to epoch timestamp format to append to end of TS values
		long instantEpoch = instant.toEpochMilli();
      
		String xmlInput =
         "<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
         "    <wsu:Timestamp wsu:Id=\"TS-5162820DAF979C7B8E" + instantEpoch + "\">\n" +
         "        <wsu:Created>" + instant + "</wsu:Created>\n" +
         "        <wsu:Expires>" + instantExpired + "</wsu:Expires>\n" +
         "    </wsu:Timestamp>\n\n";

		log.fw_writeLogEntry("     XML Security Header Timestamp: " + xmlInput, "NA");
      
		return xmlInput;
		
   }
   
   /**
    * This method writes out a webservice security header usertoken.  Must be called after GetWebServiceSecurityHeaderTimeStamp(int expiredTimeInSeconds).
    * Takes time stamp 
    * 
    * @param expiredTimeInSeconds - accepts any time in seconds
    * @param wsUsername - webservice username credential
    * @param wsPassword - webservice password credential
    * @return - Dynamically generated security time stamp and user token with expiration based on seconds parameter along with Security closing tag
    * @author Danny M. Byers
    * @since 04/26/2017
    * 
    */
   
	public String fw_get_webservice_security_header_usertoken(String wsUsername, String wsPassword) throws IOException, InterruptedException
	{
		
		Logging log = new Logging();
    
		log.fw_writeLogEntry("", "NA");
      
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
      
		// Convert timestamp to instant
		Instant instant = timestamp.toInstant();
      
		// converts back to epoch timestamp format to append to end of TS values
		long instantEpoch = instant.toEpochMilli();
      
		// this is the key to the password. It takes the timestamp's epoch value, and combines it with the password and
		// encodes it in base64
		// used the full path here java.util.Base64 since you use a different Base64 package above.
		byte[] encodedBytes = java.util.Base64.getEncoder().encode((instant + "/" + wsPassword).getBytes());
      
		String xmlInput =
         "    <wsse:UsernameToken wsu:Id=\"UsernameToken-5162820DAF979C7B8E" + instantEpoch + "\">\n" +
         "        <wsse:Username>" + wsUsername + "</wsse:Username>\n" +
         "        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">"
            + wsPassword + "</wsse:Password>\n" +
         "        <wsse:Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">"
            + new String(encodedBytes) + "</wsse:Nonce>\n" +
         "        <wsu:Created>" + instant + "</wsu:Created>\n" +
         "    </wsse:UsernameToken>\n" +
         "</wsse:Security>\n\n";

		log.fw_writeLogEntry("     XML Security Header UserToken: " + xmlInput, "NA");
      
		return xmlInput;
		
	}
	
	/**
	 * This function copies a file.
	 * 
	 * @since: 5/22/2017
	 * @author: Mark Elking
	 * @throws IOException 
	 */
	
	public void fileCopy( File in, File out ) throws IOException
    {
        FileChannel inChannel = new FileInputStream( in ).getChannel();
        FileChannel outChannel = new FileOutputStream( out ).getChannel();
        try
        {
            // Try to change this but this is the number I tried.. for Windows, 64Mb - 32Kb)
            int maxCount = (64 * 1024 * 1024) - (32 * 1024);
            long size = inChannel.size();
            long position = 0;
            while ( position < size )
            {
               position += inChannel.transferTo( position, maxCount, outChannel );
            }
            System.out.println("File Successfully Copied..");
        }
        finally
        {
            if ( inChannel != null )
            {
               inChannel.close();
            }
            if ( outChannel != null )
            {
                outChannel.close();
            }
        }
    }
	
	/**
	 * This function gets user name for user running tests.
	 * 
	 * @since: 4/6/2017
	 * @author: Mark Elking
	 * @throws InterruptedException 
	 * @throws IOException 
	 */

	public String fw_get_user_name_backup20170608(String application_name) throws ClassNotFoundException, SQLException, IOException, InterruptedException
	{
		
		String username = "";
		String record_cnt = "";
		String test_type = "";
		String alm_column_header = "";
		
		String workspace_name = fw_get_workspace_name();
		
		String alm_user_id = "svc_automation";
		
		String datasource = "ora-dev01.corp.chartercom.com:1521/SINST01D.CORP.CHARTERCOM.COM";
		String userName = "TEST_AUTOMATION";
		String password = "y_Kjny_6R_t5";
		String connection_string = "jdbc:oracle:thin:@" + datasource;
		
		fw_set_variable("SKIP--datasourceTESTDB",datasource);
		fw_set_variable("SKIP--usernameTESTDB",userName);
		fw_set_variable("SKIP--passwordTESTDB",password);
		fw_set_variable("SKIP--connectionstringTESTDB",connection_string);
		
		if (workspace_name.toUpperCase().contains("JENKINS"))
		{
		
			// JENKINSEXECUTIONUSER
			
			String filename = "JENKINSEXECUTIONUSER";
    		
			File file1 =new File("C:\\Temp\\" + filename);
		    File file2 =new File(workspace_name + "\\variables\\" + filename);
		   
			try 
			{
				fileCopy(file1, file2);
			} catch (IOException e) 
			{
				e.printStackTrace();
				
				System.out.println(" ");
				System.out.println(" ***** Stopping Execution because FILE " + file1 + " NOT found ****** ");
				System.out.println(" ");
				System.exit(0);
				
			}
			
			username = fw_get_value_from_file(workspace_name + "\\variables\\" + filename);
			username = username.toUpperCase().trim();
			
			fw_set_variable("SKIP--" + filename, username);
			
			if (username.toUpperCase().contains("JS"))
			{
				test_type = "JENKINSSMOKE";
				alm_column_header = username;
			}
			else if (username.toUpperCase().contains("JR"))
			{
				test_type = "JENKINSREGRESSION";
				alm_column_header = username;
			}
			else
			{
				test_type = "JENKINSUNIT";
				alm_column_header = "Value";
			}
			
			
			
			// JENKINSNETWORKID
			
			String filename2 = "JENKINSNETWORKID";
    		
			File file3 =new File("C:\\Temp\\" + filename2);
		    File file4 =new File(workspace_name + "\\variables\\" + filename2);
		   
			try 
			{
				fileCopy(file3, file4);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				
				System.out.println(" ");
				System.out.println(" ***** Stopping Execution because FILE " + file3 + " NOT found ****** ");
				System.out.println(" ");
				System.exit(0);
				
			}
			
			alm_user_id = fw_get_value_from_file(workspace_name + "\\variables\\" + filename2);
			alm_user_id = alm_user_id.toLowerCase().trim();
			
		}
		else
		{
			
			JFrame frame = new JFrame("InputDialog");
			username = JOptionPane.showInputDialog(frame, "User?");
			username = username.toUpperCase();
			
			test_type = "ECLIPSEUNIT";
			
			if (username.startsWith("JS"))
			{
				alm_column_header = username;
			}
			else
			{
				alm_column_header = "Value";
			}
			
			Connection connection = null;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(connection_string, userName, password);
			String sql_query = "select count(*) cnt_val from tbl_tcexecparms where execparms_application = '" + application_name + "' and execparms_user = '" + username + "'";
			ResultSet rs = null;
			Statement st = connection.createStatement();
			rs = st.executeQuery(sql_query);
			rs.next();
			record_cnt = rs.getString("cnt_val").toString();
			if (record_cnt.equals("0"))
			{
				System.out.println(" ");
				System.out.println(" ***** Stopping Execution because USER " + username + " was NOT found in ExecParms table for this application ****** ");
				System.out.println(" ");
				System.exit(0);
			}
			else
			{
				String sql_query2 = "select security_userid from tbl_tcsecurity where security_userfirstname = '" + username + "'";
				ResultSet rs2 = null;
				Statement st2 = connection.createStatement();
				rs2 = st2.executeQuery(sql_query2);
				rs2.next();
				alm_user_id = rs2.getString("security_userid").toString();
			}
			connection.close();
			
		}
		
		fw_set_variable("SKIP--appname", application_name.toUpperCase());
		fw_set_variable("SKIP--fname", username.toUpperCase());
		fw_set_variable("SKIP--TESTTYPE", test_type);
		fw_set_variable("SKIP--ALMUSERIDLOGIN", alm_user_id);
		fw_set_variable("SKIP--ALMCOLUMNHEADER", alm_column_header);
		
		return username;
		
	}
	
	/**
	 * This function checks to see if element exists.
	 * The inputs required for this function are fieldname, tagname, attribute, attributevalue, fieldvalue and milliseconds to wait.
	 * 		fieldname - any text value representing the field.  Example is "Address".
	 * 		tagname - the tagname used to help search for the object on the page.  Example is "input".
	 * 		locator - the locator used to help identify an object on the page.  Example is "id" or "name".
	 * 		locatorvalue - the locator used to help identify an object on the page.  Example is "phoneNumber".
	 * 		fieldvalue - the value to put into the field once the object has been identified.
	 * 		milliseconds - the time to wait after the action has been performed on the specified object.
	 * 
	 * @param fieldname
	 * @param tagname
	 * @param locator
	 * @param locatorvalue
	 * @param fieldvalue
	 * @param milliseconds_to_wait
	 * @throws InterruptedException
	 * 
	 * @author Mark Elking
	 * @throws IOException 
	 * @since 1/13/2017
	 * 
	 */
	
	public Boolean fw_check_element_existence_backup20170612(String fieldname, String tagname, String locator, String locatorvalue, String fieldvalue, long milliseconds_to_wait) throws InterruptedException, IOException 
	{

		String text_msg = "";
		String ret_cd = "";
		String field_ready_flag = "";
		boolean field_isEmpty=true;
		boolean field_isEnabled=false;
		boolean field_isDisplayed=false;
		return_existence=false;
		int num_loops = 1;
		long wait_time_per_loop = 1000;
		
		
		try
		{
			
			if (!fieldvalue.equals(""))
			{
				String[] fieldvalue_arr = fieldvalue.split(",");
				num_loops = Integer.valueOf(fieldvalue_arr[0]);
				wait_time_per_loop = Long.valueOf(fieldvalue_arr[1]);
			}
			
			if (locator.equalsIgnoreCase("name") || locator.equalsIgnoreCase("id") || locator.equalsIgnoreCase("class") || locator.equalsIgnoreCase("css") || locator.equalsIgnoreCase("xpath") || locator.equalsIgnoreCase("link") || locator.equalsIgnoreCase("partiallink"))
			{
				for (int m=1;m<num_loops;m++)
				{
					field_isEmpty = driver.findElements(fw_get_element_object(locator, locatorvalue)).isEmpty();
					field_isEnabled = driver.findElement(fw_get_element_object(locator, locatorvalue)).isEnabled();
					field_isDisplayed = driver.findElement(fw_get_element_object(locator, locatorvalue)).isDisplayed();
					
					if(!field_isEmpty && field_isEnabled && field_isDisplayed)
					{
						field_ready_flag = "YES";
						break;
					}
					else
					{
						Thread.sleep(wait_time_per_loop);
					}
					
				}
			}
			else if (locator.equals("NA")) 
			{
				
				for (int m=1;m<num_loops;m++)
				{
					List<WebElement> rows = driver.findElements(By.tagName(tagname));
					Iterator<WebElement> iter = rows.iterator();
					
					while (iter.hasNext()) {
						WebElement item = iter.next();
						String label = item.getText().trim();
						
						if (label.contains(locatorvalue))
						{
							field_isEmpty = false;
							field_isEnabled = item.isEnabled();
							field_isDisplayed = item.isDisplayed();
							
							if(field_isEnabled && field_isDisplayed)
							{
								field_ready_flag = "YES";
								break;
							}
						}
					}
					
					if (field_ready_flag.equals("YES"))
					{
						break;
					}
				}
			}
			
			if (field_ready_flag.equals("YES"))
			{
				ret_cd = "0";
				return_existence = true;
			}
			else
			{
				text_msg = "*************** Field NOT Found or Field Disabled or Field Not Displayed ****************";
				ret_cd = "1";
				return_existence = false;
			}
			
			log.fw_writeLogEntry("Check Element Existence (Name: " + fieldname + ", Value: " + fieldvalue + ", Element Exists? " + return_existence + ", isEmpty: " + field_isEmpty + ", isEnabled: " + field_isEnabled + ", isDisplayed: " + field_isDisplayed + ")" + text_msg, ret_cd);
			
			Thread.sleep(milliseconds_to_wait);
		
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
			text_msg = "*************** EXCEPTION ****************";
			ret_cd = "1";
			
			String exception_string = ExceptionUtils.getStackTrace(e);			
			log.fw_writeLogEntry("Check Element Existence (Name: " + fieldname + ", Value: " + fieldvalue + ", Element Exists? " + return_existence + ", isEmpty: " + field_isEmpty + ", isEnabled: " + field_isEnabled + ", isDisplayed: " + field_isDisplayed + ") - " + text_msg + " - " + exception_string, ret_cd);
		}
		
		return return_existence;
			
	}
	
}

