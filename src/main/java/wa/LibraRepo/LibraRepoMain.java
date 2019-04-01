package wa.LibraRepo;

import org.apache.commons.lang3.ArrayUtils;
import java.util.Arrays;

public class LibraRepoMain {
	
	//検査結果レポートメイン処理
	public static void do_report(String projectID, String any_pageID, String any_guideline) {
		
		//設定ファイルの読み込み
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
		
		//LibraRepoインスタンスの生成
		LibraRepo lrp = new LibraRepo(uid, pswd, projectID, appWait, os, driver_type, headless_flag);
		
		//ログイン
		lrp.login();
		try { Thread.sleep(3000); } catch(InterruptedException e) {}
		//レポートindexページ
		lrp.browse_repo();
		try { Thread.sleep(3000); } catch(InterruptedException e) {}
		
		//条件分岐
		if(any_pageID == "" && any_guideline == "") {
			//全レポート処理
			lrp.fetch_report_sequential();
			
		} else {
			//範囲指定レポート処理
			lrp.fetch_report_single(any_pageID, any_guideline);
			
		}

		//ログアウト
		lrp.logout();
		try { Thread.sleep(3000); } catch(InterruptedException e) {}
		lrp.shutdown();
	
	}
	
	//guidelineデータを初期化
	public static void do_reset_guideline() {
		//設定ファイルの読み込み
		String[] user_data = LibraRepoFiles.getUserProperties("user.yaml");
		String gLevel = user_data[9];

		//書き出し内容を配列で制御
		String[] gA = {"7.1.1.1","7.1.2.1","7.1.2.2","7.1.2.3","7.1.3.1","7.1.3.2","7.1.3.3","7.1.4.1","7.1.4.2","7.2.1.1","7.2.1.2","7.2.2.1","7.2.2.2","7.2.3.1","7.2.4.1","7.2.4.2","7.2.4.3","7.2.4.4","7.3.1.1","7.3.2.1","7.3.2.2","7.3.3.1","7.3.3.2","7.4.1.1","7.4.1.2"};
		String[] gAA = {"7.1.2.4","7.1.2.5","7.1.4.3","7.1.4.4","7.1.4.5","7.2.4.5","7.2.4.6","7.2.4.7","7.3.1.2","7.3.2.3","7.3.2.4","7.3.3.3","7.3.3.4"};
		String[] gAAA = {"7.1.2.6","7.1.2.7","7.1.2.8","7.1.2.9","7.1.4.6","7.1.4.7","7.1.4.8","7.1.4.9","7.2.1.3","7.2.2.3","7.2.2.4","7.2.2.5","7.2.3.2","7.2.4.8","7.2.4.9","7.2.4.10","7.3.1.3","7.3.1.4","7.3.1.5","7.3.1.6","7.3.2.5","7.3.3.5","7.3.3.6"};
		String[] guideline_names = null;
		if(gLevel.equals("A")) {
			guideline_names = ArrayUtils.addAll(gA);
		} else if(gLevel.equals("AA")) {
			guideline_names = ArrayUtils.addAll(gA, gAA);
		} else if(gLevel.equals("AAA")) {
			String[] tmp = ArrayUtils.addAll(gA, gAA);
			guideline_names = ArrayUtils.addAll(tmp, gAAA);
		}
		
		//テキストデータ書き込み
		LibraRepoFiles.write_text_data(guideline_names, "guideline_datas.txt");
	}

}
