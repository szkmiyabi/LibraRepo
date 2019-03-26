package wa.LibraRepo;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LibraRepo {

	private String projectID;
	private WebDriver wd;
	private int systemWait;
	private int longWait;
	private int midWait;
	private int shortWait;
	private String driver_path;
	private Boolean headless_flag;
	private String uid;
	private String pswd;
	private String app_url = "https://accessibility.jp/libra/";
	private String rep_index_url_base = "http://jis.infocreate.co.jp/diagnose/indexv2/report/projID/";
	private String rep_detail_url_base = "http://jis.infocreate.co.jp/diagnose/indexv2/report2/projID/";
	private String sv_mainpage_url_base = "http://jis.infocreate.co.jp/diagnose/indexv2/index/projID/";
	
	//コンストラクタ
	public LibraRepo(String uid, String pswd,  String projectID, int[] appWait,  String os, String driver_type, String headless_flag) {
		this.uid = uid;
		this.pswd = pswd;
		this.projectID = projectID;
		systemWait = appWait[0];
		longWait = appWait[1];
		midWait = appWait[2];
		shortWait = appWait[3];
		
		//driverパスの設定
		if(os.equals("windows")) {
			if(driver_type.equals("firefox")) {
				driver_path = "./geckodriver.exe";
				System.setProperty("webdriver.firefox.driver", driver_path);
			} else if(driver_type.equals("chrome")) {
				driver_path = "./chromedriver.exe";
				System.setProperty("webdriver.chrome.driver", driver_path);
			}
		} else if(os.equals("mac") || os.equals("unix")) {
			if(driver_type.equals("firefox")) {
				driver_path = "geckodriver";
				System.setProperty("webdriver.firefox.driver", driver_path);
			} else if(driver_type.equals("chrome")) {
				driver_path = "chromedriver";
				System.setProperty("webdriver.chrome.driver", driver_path);
			}
		}
		
		//driverオプションの設定
		if(driver_type.equals("firefox")) {
			FirefoxOptions fxopt = new FirefoxOptions();
			//fxopt.addArguments("-window-size=1280,900");
			if(headless_flag.equals("yes")) fxopt.addArguments("-headless");
			wd = new FirefoxDriver(fxopt);
		} else if(driver_type.equals("chrome")) {
			ChromeOptions chopt = new ChromeOptions();
			//chopt.addArguments("--disable-xss-auditor");
			//chopt.addArguments("--window-size=1280,900");
			if(headless_flag.equals("yes")) chopt.addArguments("--headless");
			wd = new ChromeDriver(chopt);
		}
		
		wd.manage().timeouts().implicitlyWait(systemWait, TimeUnit.SECONDS);
		wd.get(app_url);
		
	}
	
	//シャットダウン
	public void shutdown() {
		wd.quit();
	}
	
}
