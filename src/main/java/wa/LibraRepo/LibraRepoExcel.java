package wa.LibraRepo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.List;
import java.util.ArrayList;


public class LibraRepoExcel {

	//Excelファイルに出力
	public static void save_xlsx(List<List<String>> datas) {
		SXSSFWorkbook wb = null;
		SXSSFSheet sh = null;
		FileOutputStream sw = null;
		String filename = LibraRepoDateUtil.fetch_filename_from_datetime("xlsx");
		
		try {
			wb = new SXSSFWorkbook();
			sh = wb.createSheet("検査結果");
			Row row;
			Cell cell;
			
			for(int i=0; i<datas.size(); i++) {
				List<String> data_rows = datas.get(i);
				row = sh.createRow(i);
				for(int j=0; j<data_rows.size(); j++) {
					String col = data_rows.get(j);
					cell = row.createCell(j);
					cell.setCellValue(col);
				}
			}
			sw = new FileOutputStream(filename);
			wb.write(sw);
			
		} catch(Exception e) {
			
		} finally {
			if(sw != null) {
				try { sw.close(); } catch(Exception e) {}
			}
			if(wb != null) {
				try { ((SXSSFWorkbook) wb).dispose(); } catch(Exception e) {}
			}
		}
		
	}

}
