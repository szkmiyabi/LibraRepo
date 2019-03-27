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
		String[] ret = new String[9];
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

}
