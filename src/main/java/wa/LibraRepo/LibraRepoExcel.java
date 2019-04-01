package wa.LibraRepo;

import java.awt.Color;
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
import org.apache.poi.ss.usermodel.HorizontalAlignment;
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
			
			//hederセルのスタイル
			CellStyle s_header = wb.createCellStyle();
			s_header.setBorderTop(BorderStyle.THIN);
			s_header.setBorderBottom(BorderStyle.THIN);
			s_header.setBorderLeft(BorderStyle.THIN);
			s_header.setBorderRight(BorderStyle.THIN);
			s_header.setAlignment(HorizontalAlignment.CENTER);
			Font s_font = wb.createFont();
			s_font.setBold(true);
			s_header.setFont(s_font);
			
			//適合スタイル
			CellStyle s_ok = wb.createCellStyle();
			s_ok.setBorderTop(BorderStyle.THIN);
			s_ok.setBorderBottom(BorderStyle.THIN);
			s_ok.setBorderLeft(BorderStyle.THIN);
			s_ok.setBorderRight(BorderStyle.THIN);
			s_ok.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			s_ok.setFillForegroundColor(HSSFColor.HSSFColorPredefined.SKY_BLUE.getIndex());
			
			//適合(注記)スタイル
			CellStyle s_ok2 = wb.createCellStyle();
			s_ok2.setBorderTop(BorderStyle.THIN);
			s_ok2.setBorderBottom(BorderStyle.THIN);
			s_ok2.setBorderLeft(BorderStyle.THIN);
			s_ok2.setBorderRight(BorderStyle.THIN);
			s_ok2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			s_ok2.setFillForegroundColor(HSSFColor.HSSFColorPredefined.BRIGHT_GREEN.getIndex());
			
			//不適合スタイル
			CellStyle s_fail = wb.createCellStyle();
			s_fail.setBorderTop(BorderStyle.THIN);
			s_fail.setBorderBottom(BorderStyle.THIN);
			s_fail.setBorderLeft(BorderStyle.THIN);
			s_fail.setBorderRight(BorderStyle.THIN);
			s_fail.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			s_fail.setFillForegroundColor(HSSFColor.HSSFColorPredefined.CORAL.getIndex());
			
			//非適用スタイル
			CellStyle s_na = wb.createCellStyle();
			s_na.setBorderTop(BorderStyle.THIN);
			s_na.setBorderBottom(BorderStyle.THIN);
			s_na.setBorderLeft(BorderStyle.THIN);
			s_na.setBorderRight(BorderStyle.THIN);
			s_na.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			s_na.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			
			//それ以外のスタイル
			CellStyle s_etc = wb.createCellStyle();
			s_etc.setBorderTop(BorderStyle.THIN);
			s_etc.setBorderBottom(BorderStyle.THIN);
			s_etc.setBorderLeft(BorderStyle.THIN);
			s_etc.setBorderRight(BorderStyle.THIN);

			int sv_index = 5;
			
			
			for(int i=0; i<datas.size(); i++) {
				List<String> data_rows = datas.get(i);
				row = sh.createRow(i);
				for(int j=0; j<data_rows.size(); j++) {
					String col = data_rows.get(j);
					cell = row.createCell(j);
					cell.setCellValue(col);
					
					//header cell
					if(i == 0) {
						cell.setCellStyle(s_header);
					//data cell
					} else {
						String sv_val = data_rows.get(sv_index);
						//System.out.println(sv_val);
						if(sv_val.equals("適合")) {
							cell.setCellStyle(s_ok);
						} else if(sv_val.equals("適合(注記)")) {
							cell.setCellStyle(s_ok2);
						} else if(sv_val.equals("不適合")) {
							cell.setCellStyle(s_fail);
						} else if(sv_val.equals("非適用")) {
							cell.setCellStyle(s_na);
						} else {
							cell.setCellStyle(s_etc);
						}
					}
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
