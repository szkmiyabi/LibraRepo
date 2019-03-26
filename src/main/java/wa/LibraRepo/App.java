package wa.LibraRepo;

import wa.LibraRepo.LibraRepo;

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
    	
    	LibraRepoFiles fo = new LibraRepoFiles();
        String[] user_data = fo.getUserProperties("user.yaml");
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
        lrp.login();
		try { Thread.sleep(6000); } catch(InterruptedException e) {}
		try { lrp.screenshot("firefox-login.png"); } catch(Exception e) {}
		lrp.getWd().get("https://jis.infocreate.co.jp/diagnose/result/index/projID/503");
		try { lrp.screenshot("chrome-repo.png"); } catch(Exception e) {}
		try { Thread.sleep(3000); } catch(InterruptedException e) {}
		lrp.logout();
		try { Thread.sleep(3000); } catch(InterruptedException e) {}
		lrp.shutdown();

    }
}
