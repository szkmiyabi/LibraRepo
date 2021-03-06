package wa.LibraRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
	private String uid;
	private String pswd;
	private String app_url = "https://accessibility.jp/libra/";
	private String index_url = "https://jis.infocreate.co.jp/";
	private String rep_index_url_base = "http://jis.infocreate.co.jp/diagnose/indexv2/report/projID/";
	private String rep_detail_url_base = "http://jis.infocreate.co.jp/diagnose/indexv2/report2/projID/";
	private String sv_mainpage_url_base = "http://jis.infocreate.co.jp/diagnose/indexv2/index/projID/";
	private String guideline_file_name = "guideline_datas.txt";
	private List<List<String>> rep_data;
	
	//コンストラクタ
	public LibraRepo(String uid, String pswd,  String projectID, int[] appWait,  String os, String driver_type, String headless_flag) {
		this.uid = uid;
		this.pswd = pswd;
		this.projectID = projectID;
		systemWait = appWait[0];
		longWait = appWait[1];
		midWait = appWait[2];
		shortWait = appWait[3];
		
		//レポートデータ初期化
		rep_data = new ArrayList<List<String>>();
		
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
		wd.get(app_url);
		
	}
	
	//WebDriverのゲッター
	public WebDriver getWd() {
		return wd;
	}
	
	//rep_dataのゲッター
	public List<List<String>> getRepData() {
		return rep_data;
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
	
	//PID一覧＋URL一覧データ生成
	public Map<String, String> get_page_list_data() {
		Map<String, String> datas = new TreeMap<String, String>();
		org.jsoup.nodes.Document dom = get_dom();
		org.jsoup.nodes.Element tbl = null;
		org.jsoup.select.Elements tbls = dom.select("table");
		for(int i=0; i<tbls.size(); i++) {
			if(i == 2) {
				tbl = (org.jsoup.nodes.Element)tbls.get(i);
			}
		}

		String tbl_html = tbl.outerHtml();
		org.jsoup.nodes.Document tbl_dom = Jsoup.parse(tbl_html);

		org.jsoup.select.Elements rows = tbl_dom.select("tr td:first-child");
		List<String> pids = new ArrayList<String>();
		for(org.jsoup.nodes.Element row : rows) {
			String td_val = row.text();
			pids.add(td_val);
		}
		int map_cnt = pids.size();
		rows = null;
		rows = tbl_dom.select("tr td:nth-child(2)");
		List<String> urls = new ArrayList<String>();
		for(org.jsoup.nodes.Element row : rows) {
			String td_val = row.text();
			urls.add(td_val);
		}
		for(int i=0; i<map_cnt; i++) {
			datas.put(pids.get(i), urls.get(i));
		}
		return datas;
	}
	
	//レポート詳細ページから検査結果データを生成
	List<List<String>> get_detail_table_data(String pageID, String pageURL, String guideline) {
		List<List<String>> datas = new ArrayList<List<String>>();
		org.jsoup.nodes.Document dom = get_dom();
		org.jsoup.nodes.Element tbl = null;
		org.jsoup.select.Elements tbls = dom.select("table");
		for(int i=0; i<tbls.size(); i++) {
			if(i == 2) {
				tbl = (org.jsoup.nodes.Element)tbls.get(i);
			}
		}
		String tbl_html = tbl.outerHtml();
		org.jsoup.nodes.Document tbl_dom = Jsoup.parse(tbl_html);
		org.jsoup.select.Elements trs = tbl_dom.select("tr");
		for(int i=0; i<trs.size(); i++) {
			if(i == 0) continue;
			List<String> row_datas = new ArrayList<String>();
			row_datas.add(pageID);
			row_datas.add(pageURL);
			row_datas.add(guideline);
			org.jsoup.nodes.Element tr = (org.jsoup.nodes.Element)trs.get(i);
			String tr_html = tr.outerHtml();
			//Jsoupパースバグの解消
			tr_html = "<html><head><meta charset='utf8'></head><body><table><tr>" + tr_html + "</tr></table></body></html>";
			org.jsoup.nodes.Document tr_dom = Jsoup.parse(tr_html);
			org.jsoup.select.Elements tds = tr_dom.select("td");
			int col_num = 0;
			for(int j=0; j<tds.size(); j++) {
				org.jsoup.nodes.Element td = (org.jsoup.nodes.Element)tds.get(j);
				String td_val = td.html();
				//コメント列はbrタグも含め実体参照デコード
				if(col_num == 4) {
					td_val = LibraRepoTextUtil.br_decode(td_val);
					td_val = LibraRepoTextUtil.tag_decode(td_val);
				//それ以外は、実体参照のみデコード
				} else {
					td_val = LibraRepoTextUtil.tag_decode(td_val);
				}
				if(td_val.equals("")) {
					row_datas.add("");
				} else {
					row_datas.add(td_val);
				}
				col_num++;
			}
			datas.add(row_datas);
		}
		return datas;
	}
	
	//レポートデータ生成
	public void fetch_report_sequential() {

		//header
		rep_data.add(LibraRepoTextUtil.get_header());
		wd.get(rep_index_url_base + projectID + "/");
		LibraRepoDateUtil.app_sleep(shortWait);
		
		List<String> guideline_rows = LibraRepoFiles.open_text_data(guideline_file_name);
		Map<String, String> page_rows = get_page_list_data();
		//guidelineのループ
		for(int i=0; i<guideline_rows.size(); i++) {			
			String guideline = guideline_rows.get(i);
			String guideline_disp = guideline; //println用
			//jis2016以前の達成基準番号に変換
			if(!LibraRepoTextUtil.is_jis2016_lower(guideline)) guideline = "7." + guideline;
			//pageのループ
			for(Map.Entry<String, String> page_row : page_rows.entrySet()) {
				String pageID = page_row.getKey();
				String pageURL = page_row.getValue();
				System.out.println(pageID + ", " + guideline_disp + " を処理しています。 (" + LibraRepoDateUtil.get_logtime() + ")");
				String path = fetch_report_detail_path(pageID, guideline);
				wd.get(path);
				LibraRepoDateUtil.app_sleep(shortWait);

				List<List<String>> tbl_data = get_detail_table_data(pageID, pageURL, guideline);
				rep_data.addAll(tbl_data);
			}
		}
		
	}
	
	//ページIDとガイドラインIDを個別に指定してレポートデータ作成
	public void fetch_report_single(String any_pageID, String any_guideline) {

		wd.get(rep_index_url_base + projectID + "/");
		LibraRepoDateUtil.app_sleep(shortWait);
		
		//処理対象PIDデータの処理
		List<String> qy_page_rows = new ArrayList<String>();
		Map<String, String> new_page_rows = new TreeMap<String, String>();
		Map<String, String> page_rows = get_page_list_data();
		if(any_pageID == "") {
			new_page_rows = page_rows;
		} else {
			//ループ用PIDマップの生成
			if(LibraRepoTextUtil.is_csv(any_pageID)) {
				String[] tmp_arr = any_pageID.split(",", 0);
				for(int i=0; i<tmp_arr.length; i++) {
					qy_page_rows.add(tmp_arr[i]);
				}
			} else {
				qy_page_rows.add(any_pageID);
			}
			for(int i=0; i<qy_page_rows.size(); i++) {
				String tmp_pid = qy_page_rows.get(i);
				for(Map.Entry<String, String> tmp_row : page_rows.entrySet()) {
					String key = tmp_row.getKey();
					String val = tmp_row.getValue();
					if(tmp_pid.equals(key)) {
						new_page_rows.put(key, val);
					}
				}
			}
			if(new_page_rows.isEmpty()) {
				System.out.println("-p オプションで指定したPIDが存在しません。処理を停止します。");
				return;
			}
		}
		
		//処理対象ガイドラインデータの処理
		List<String> guideline_rows = new ArrayList<String>();
		if(any_guideline == "") {
			guideline_rows = LibraRepoFiles.open_text_data(guideline_file_name);
		} else {
			if(LibraRepoTextUtil.is_csv(any_guideline)) {
				String[] tmp_arr = any_guideline.split(",", 0);
				for(int i=0; i<tmp_arr.length; i++) {
					guideline_rows.add(tmp_arr[i]);
				}
			} else {
				guideline_rows.add(any_guideline);
			}
		}

		//header
		rep_data.add(LibraRepoTextUtil.get_header());

		//guidelineのループ
		for(int i=0; i<guideline_rows.size(); i++) {			
			String guideline = guideline_rows.get(i);
			String guideline_disp = guideline; //println用
			//jis2016以前の達成基準番号に変換
			if(!LibraRepoTextUtil.is_jis2016_lower(guideline)) guideline = "7." + guideline;
			//pageのループ
			for(Map.Entry<String, String> page_row : new_page_rows.entrySet()) {
				String pageID = page_row.getKey();
				String pageURL = page_row.getValue();
				System.out.println(pageID + ", " + guideline_disp + " を処理しています。 (" + LibraRepoDateUtil.get_logtime() + ")");
				String path = fetch_report_detail_path(pageID, guideline);
				wd.get(path);
				LibraRepoDateUtil.app_sleep(shortWait);
				
				List<List<String>> tbl_data = get_detail_table_data(pageID, pageURL, guideline);
				rep_data.addAll(tbl_data);
			}
		}

	}
	
}
