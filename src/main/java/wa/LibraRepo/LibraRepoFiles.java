package wa.LibraRepo;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class LibraRepoFiles {

	//Yamlファイルの読み込み
	private static InputStream readYamlFile(String fileName) {
		InputStream is = null;
		try
		{
			is = Files.newInputStream(Paths.get(fileName));
		} catch(Exception ex) {
		}
		return is;
	}
	
	//設定データファイルの読み込み
	public static String[] getUserProperties(String fileName) {
		String[] ret = new String[10];
		Yaml yaml = new Yaml();
		Map<String, Object> data = (Map<String, Object>) yaml.loadAs(readYamlFile(fileName), Map.class);
		int i = 0;
		for(Map.Entry<String, Object> entry : data.entrySet()) {
			if(entry.getValue() instanceof String) ret[i] = (String) entry.getValue();
			else ret[i] = String.valueOf(entry.getValue());
			i++;
		}
		return ret;
	}
	
	//テキストデータを配列として読み込み
	public static List<String> open_text_data(String filename) {
		List<String> ret = new ArrayList<String>();
		try {
			FileInputStream is = new FileInputStream(filename);
			InputStreamReader in = new InputStreamReader(is, "UTF-8");
			BufferedReader br = new BufferedReader(in);
			String line = "";
			while((line = br.readLine()) != null) {
				ret.add(line);
			}
			br.close();
			is.close();

		} catch(Exception ex) {}

		return ret;
	}
	
	//配列をテキストファイルとして書き込み
	public static void write_text_data(String[] rows, String filename) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filename)));
			for(int i=0; i<rows.length; i++) {
				String row = rows[i];
				bw.write(row);
				if(i != (rows.length - 1)) bw.newLine();
			}
			bw.close();
		} catch(IOException e) {
			System.out.println("エラーが発生しました。" + e.getStackTrace());
		}
	}

}
