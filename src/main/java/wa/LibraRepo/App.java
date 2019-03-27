package wa.LibraRepo;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Hello world!
 *
 */
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
		try { lrp.screenshot(LibraRepoDateUtil.fetch_filename_from_datetime("png")); } catch(Exception e) {}

        lrp.login();
		try { Thread.sleep(3000); } catch(InterruptedException e) {}
		try { lrp.screenshot(LibraRepoDateUtil.fetch_filename_from_datetime("png")); } catch(Exception e) {}

		lrp.browse_repo();
		try { Thread.sleep(3000); } catch(InterruptedException e) {}
		try { lrp.screenshot(LibraRepoDateUtil.fetch_filename_from_datetime("png")); } catch(Exception e) {}
		//org.jsoup.nodes.Document doc = lrp.get_dom();
		//System.out.println(doc.html());
		/*
		org.jsoup.select.Elements elms = doc.select("table");
		int cnt = 0;
		for(org.jsoup.nodes.Element elm : elms) {
			if(cnt==2) {
				System.out.println(elm.html());
			}
			cnt++;
		}*/
		List<String> rows = lrp.get_page_list_data();
		for(String row : rows) {
			System.out.println(row);
		}
		
		
		/*
		List<String> rows = LibraRepoFiles.open_text_data("guideline_datas.txt");
		for(int i=0; i<rows.size(); i++) {
			String row = rows.get(i);
			String crurl = lrp.fetch_report_detail_path("wake0001", row);
			System.out.println(crurl);
			lrp.getWd().get(crurl);
			try { Thread.sleep(3000); } catch(InterruptedException e) {}
			try { lrp.screenshot(LibraRepoDateUtil.fetch_filename_from_datetime("png")); } catch(Exception e) {}
		}
		*/

		lrp.logout();
		try { Thread.sleep(3000); } catch(InterruptedException e) {}
		lrp.shutdown();

    }
}
