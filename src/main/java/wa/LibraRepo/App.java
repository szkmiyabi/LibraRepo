package wa.LibraRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App 
{
    public static void main( String[] args )
    {
    	// Test code
    	String projectID = "503";
    	
        String[] user_data = LibraRepoFiles.getUserProperties("user.yaml");
        String uid = user_data[0];
        String pswd = user_data[1];
        int systemWait = Integer.parseInt(user_data[2]);
        int longWait = Integer.parseInt(user_data[3]);
        int midWait = Integer.parseInt(user_data[4]);
        int shortWait = Integer.parseInt(user_data[5]);
        String os = user_data[6];
        String driver_type = user_data[7];
        String headless_flag = user_data[8];
    	int[] appWait = {systemWait, longWait, midWait, shortWait};
    	
        LibraRepo lrp = new LibraRepo(uid, pswd, projectID, appWait, os, driver_type, headless_flag);
		//try { lrp.screenshot(LibraRepoDateUtil.fetch_filename_from_datetime("png")); } catch(Exception e) {}

        lrp.login();
		try { Thread.sleep(3000); } catch(InterruptedException e) {}
		//try { lrp.screenshot(LibraRepoDateUtil.fetch_filename_from_datetime("png")); } catch(Exception e) {}

		lrp.browse_repo();
		try { Thread.sleep(3000); } catch(InterruptedException e) {}
		//try { lrp.screenshot(LibraRepoDateUtil.fetch_filename_from_datetime("png")); } catch(Exception e) {}

		lrp.fetch_report_sequential();
		
		lrp.logout();
		try { Thread.sleep(3000); } catch(InterruptedException e) {}
		lrp.shutdown();

    }
}
