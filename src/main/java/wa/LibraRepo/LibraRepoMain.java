package wa.LibraRepo;

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

}
