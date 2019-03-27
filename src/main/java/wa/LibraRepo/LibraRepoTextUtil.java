package wa.LibraRepo;

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
		return Pattern.compile("<br>").matcher(str).replaceAll("\n");
	}
	
	//タグをデコード
	private static String tag_decode(String str) {
		String data = str;
		data = Pattern.compile("&lt;").matcher(data).replaceAll("<");
		data = Pattern.compile("&gt;").matcher(data).replaceAll(">");
		return data;
	}

}
