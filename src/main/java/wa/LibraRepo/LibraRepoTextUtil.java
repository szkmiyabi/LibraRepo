package wa.LibraRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class LibraRepoTextUtil {

	//srccodeのデコード
	public static String src_decode(String str) {
		String data = str;
		data = tag_decode(data);
		data = br_decode(data);
		return data;
	}
	
	//brタグを改行コード変換
	private static String br_decode(String str) {
		return Pattern.compile("<br>").matcher(str).replaceAll("\r\n");
	}
	
	//タグをデコード
	private static String tag_decode(String str) {
		String data = str;
		data = Pattern.compile("&lt;").matcher(data).replaceAll("<");
		data = Pattern.compile("&gt;").matcher(data).replaceAll(">");
		return data;
	}
	
	//プロジェクトIDかどうか判定
	public static Boolean is_projectID(String str) {
		Pattern pt = Pattern.compile("[0-9]+");
		Matcher mt = pt.matcher(str);
		if(mt.find()) return true;
		else return false;
	}
	
	//カンマ区切りテキストかどうか判定
	public static Boolean is_csv(String str) {
		Pattern pt = Pattern.compile(",");
		Matcher mt = pt.matcher(str);
		if(mt.find()) return true;
		else return false;
	}

	//レポートのヘッダー行を生成
	public static List<String> get_header() {
		List<String> head_row = new ArrayList<String>();
		head_row.add("管理番号");
		head_row.add("URL");
		head_row.add("達成基準");
		head_row.add("状況/要件");
		head_row.add("実装番号");
		head_row.add("検査結果");
		head_row.add("検査員");
		head_row.add("コメント");
		head_row.add("対象ソースコード");
		head_row.add("修正ソースコード");
		return head_row;
	}
	
}
