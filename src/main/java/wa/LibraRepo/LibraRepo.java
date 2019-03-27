package wa.LibraRepo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.TakesScreenshot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


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
	private String index_url = "https://jis.infocreate.co.jp/";
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
			if(headless_flag.equals("yes")) fxopt.addArguments("-headless");
			wd = new FirefoxDriver(fxopt);
		} else if(driver_type.equals("chrome")) {
			ChromeOptions chopt = new ChromeOptions();
			if(headless_flag.equals("yes")) chopt.addArguments("--headless");
			wd = new ChromeDriver(chopt);
		}
		
		wd.manage().timeouts().implicitlyWait(systemWait, TimeUnit.SECONDS);
		wd.manage().window().setSize(new Dimension(1280, 900));
		
		//Test code
		/*
		System.out.println(LibraRepoDateUtil.fetch_filename_from_datetime("txt"));
		List<String> tmp = LibraRepoFiles.open_text_data("guideline_datas.txt");
		for(int i=0; i<tmp.size(); i++) {
			String row = tmp.get(i);
			System.out.println(row);
		}
		*/
		
		wd.get(app_url);
		
	}
	
	//WebDriverのゲッター
	public WebDriver getWd() {
		return wd;
	}
	
	//スクリーンショットを取る
	public void screenshot(String filename) throws Exception {
		TakesScreenshot sc = (TakesScreenshot)wd;
		Path save_dir = Paths.get("screenshots");
		Files.createDirectories(save_dir);
		Path save_path = save_dir.resolve(filename);
		Files.write(save_path, sc.getScreenshotAs(OutputType.BYTES));
	}
	
	//シャットダウン
	public void shutdown() {
		wd.quit();
	}
	
	//ログイン
	public void login() {
		wd.findElement(By.id("uid")).sendKeys(uid);
		wd.findElement(By.id("pswd")).sendKeys(pswd);
		wd.findElement(By.id("btn")).click();
	}
	
	//ログアウト
	public void logout() {
		wd.get(index_url);
		try { Thread.sleep(shortWait); } catch(InterruptedException e) {}
		//try { screenshot("louout-check-chrome.png"); } catch (Exception e) {}
		WebElement btnWrap = wd.findElement(By.id("btn"));
		WebElement btnBase = btnWrap.findElement(By.tagName("ul"));
		WebElement btnBaseInner = btnBase.findElement(By.className("btn2"));
		WebElement btnA = btnBaseInner.findElement(By.tagName("a"));
		btnA.click();
	}
	
	//レポートインデックスページに遷移
	public void browse_repo() {
		wd.get(rep_index_url_base + projectID);
	}
	
	//レポート詳細ページのURL生成
	public String fetch_report_detail_path(String pageID, String guidelineID) {
		return rep_detail_url_base + projectID + "/controlID/"  + pageID + "/guideline/" + guidelineID + "/";
	}
	
	//DOMオブジェクトを取得
	public org.jsoup.nodes.Document get_dom() {
		String html_str = wd.getPageSource();
		org.jsoup.nodes.Document doc = Jsoup.parse(html_str);
		return doc;
	}
	
	
}
