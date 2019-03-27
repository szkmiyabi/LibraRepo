package wa.LibraRepo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LibraRepoDateUtil {
	
	private final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
	
	public static String fetch_filename_from_datetime(String extension) {
		LocalDateTime ld = LocalDateTime.now();
		return ld.format(dtf) + "." + extension;
	}
	
}
