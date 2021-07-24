package com.tpcop.DataAPI.controller;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tpcop.DataAPI.entity.Holiday;
import com.tpcop.DataAPI.entity.User;
import com.tpcop.DataAPI.model.ATModel;
import com.tpcop.DataAPI.model.Lunar;
import com.tpcop.DataAPI.model.Solar;
import com.tpcop.DataAPI.repository.IHolidayRepository;
import com.tpcop.DataAPI.repository.IUserRepository;
import com.tpcop.DataAPI.utils.LunarSolarConverter;

@RestController
@RequestMapping("/utils/excel/")
public class ExcelController {
	private Workbook workbook = new XSSFWorkbook();

	private XSSFFont headerFont;
	private XSSFFont tableHeaderFont;
	private XSSFFont greenRowFont;
	private XSSFFont redRowFont;
	private XSSFFont companyFont;
	private XSSFFont companyTextFont;
	private XSSFFont signatureFont;
	private XSSFFont fontBold;

	private XSSFCellStyle cellHeaderStyle;
	private XSSFCellStyle cellTableHeaderStyle;
	private XSSFCellStyle cellTableRowStyle;
	private XSSFCellStyle cellTableRowGreenStyle;
	private XSSFCellStyle cellTableRowRedStyle;
	private XSSFCellStyle cellCompanyStyle;
	private XSSFCellStyle cellSignatureStyle;

	private List<String> tableHeaders = new ArrayList<String>();

	@Autowired
	private IUserRepository iUserRepository;

	@Autowired
	private IHolidayRepository iHolidayRepository;

	public ExcelController() {
		// TODO Auto-generated constructor stub
		headerFont = (XSSFFont) workbook.createFont();
		headerFont.setFontName("Segoe UI");
		headerFont.setFontHeight(20);
		headerFont.setColor(new XSSFColor(Color.decode("#7A8DC5")));
		headerFont.setBold(true);

		greenRowFont = (XSSFFont) workbook.createFont();
		greenRowFont.setFontName("Calibri");
		greenRowFont.setFontHeight(11);
		greenRowFont.setColor(new XSSFColor(Color.decode("#006100")));

		redRowFont = (XSSFFont) workbook.createFont();
		redRowFont.setFontName("Calibri");
		redRowFont.setFontHeight(11);
		redRowFont.setColor(new XSSFColor(Color.decode("#9C0006")));

		companyFont = (XSSFFont) workbook.createFont();
		companyFont.setFontName("Segoe UI");
		companyFont.setFontHeight(11);
		companyFont.setColor(new XSSFColor(Color.decode("#1F4E78")));
		companyFont.setBold(true);

		companyTextFont = (XSSFFont) workbook.createFont();
		companyTextFont.setFontName("Segoe UI");
		companyTextFont.setFontHeight(11);
		companyTextFont.setColor(new XSSFColor(Color.decode("#1F4E78")));

		tableHeaderFont = (XSSFFont) workbook.createFont();
		tableHeaderFont.setFontName("Segoe UI");
		tableHeaderFont.setFontHeight(12);
		tableHeaderFont.setColor(new XSSFColor(Color.white));
		tableHeaderFont.setBold(true);

		signatureFont = (XSSFFont) workbook.createFont();
		signatureFont.setFontName("Calibri");
		signatureFont.setFontHeight(14);

		fontBold = (XSSFFont) workbook.createFont();
		fontBold.setFontName("Segoe UI");
		fontBold.setFontHeight(11);
		fontBold.setBold(true);

		cellHeaderStyle = (XSSFCellStyle) workbook.createCellStyle();
		cellHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
		cellHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellHeaderStyle.setFont(headerFont);
		cellHeaderStyle.setWrapText(true);

		cellTableHeaderStyle = (XSSFCellStyle) workbook.createCellStyle();
		cellTableHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
		cellTableHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellTableHeaderStyle.setFillForegroundColor(new XSSFColor(Color.decode("#2C4574")));
		cellTableHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellTableHeaderStyle.setFont(tableHeaderFont);
		cellTableHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
		cellTableHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellTableHeaderStyle.setWrapText(true);
		cellTableHeaderStyle.setBorderTop(BorderStyle.THIN);
		cellTableHeaderStyle.setBorderRight(BorderStyle.THIN);
		cellTableHeaderStyle.setBorderBottom(BorderStyle.THIN);
		cellTableHeaderStyle.setBorderLeft(BorderStyle.THIN);

		cellTableRowStyle = (XSSFCellStyle) workbook.createCellStyle();
		cellTableRowStyle.setAlignment(HorizontalAlignment.CENTER);
		cellTableRowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellTableRowStyle.setAlignment(HorizontalAlignment.CENTER);
		cellTableRowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellTableRowStyle.setBorderTop(BorderStyle.THIN);
		cellTableRowStyle.setBorderRight(BorderStyle.THIN);
		cellTableRowStyle.setBorderBottom(BorderStyle.THIN);
		cellTableRowStyle.setBorderLeft(BorderStyle.THIN);
		cellTableRowStyle.setWrapText(true);

		cellTableRowGreenStyle = (XSSFCellStyle) workbook.createCellStyle();
		cellTableRowGreenStyle.setAlignment(HorizontalAlignment.CENTER);
		cellTableRowGreenStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellTableRowGreenStyle.setAlignment(HorizontalAlignment.CENTER);
		cellTableRowGreenStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellTableRowGreenStyle.setFillForegroundColor(new XSSFColor(Color.decode("#C6EFCE")));
		cellTableRowGreenStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellTableRowGreenStyle.setWrapText(true);
		cellTableRowGreenStyle.setFont(greenRowFont);

		cellTableRowRedStyle = (XSSFCellStyle) workbook.createCellStyle();
		cellTableRowRedStyle.setAlignment(HorizontalAlignment.CENTER);
		cellTableRowRedStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellTableRowRedStyle.setAlignment(HorizontalAlignment.CENTER);
		cellTableRowRedStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellTableRowRedStyle.setFillForegroundColor(new XSSFColor(Color.decode("#FFC7CE")));
		cellTableRowRedStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellTableRowRedStyle.setWrapText(true);
		cellTableRowRedStyle.setFont(redRowFont);
		cellTableRowRedStyle.setBorderTop(BorderStyle.THIN);
		cellTableRowRedStyle.setBorderRight(BorderStyle.THIN);
		cellTableRowRedStyle.setBorderBottom(BorderStyle.THIN);
		cellTableRowRedStyle.setBorderLeft(BorderStyle.THIN);

		cellCompanyStyle = (XSSFCellStyle) workbook.createCellStyle();
		cellCompanyStyle.setFont(companyFont);
		cellCompanyStyle.setAlignment(HorizontalAlignment.CENTER);
		cellCompanyStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		cellSignatureStyle = (XSSFCellStyle) workbook.createCellStyle();
		cellSignatureStyle.setFont(signatureFont);
		cellSignatureStyle.setAlignment(HorizontalAlignment.CENTER);
		cellSignatureStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		tableHeaders.add("STT");
		tableHeaders.add("Họ Và Tên");
		tableHeaders.add("Giới Tính");
		tableHeaders.add("Ngày Sinh");
		tableHeaders.add("Số ĐT");
		tableHeaders.add("Email");
		tableHeaders.add("Địa Chỉ");
		tableHeaders.add("Bộ Phận");
		tableHeaders.add("Ngày Vào");
		tableHeaders.add("Trạng Thái");
		tableHeaders.add("Ghi Chú");
	}

	@GetMapping("/download")
	private ResponseEntity<InputStreamResource> createExcelFromDataBase() throws IOException {
		Sheet sheetDSNV, sheetATT;
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DATE);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);

		if (workbook.getSheet("HRM") == null) {
			sheetDSNV = workbook.createSheet("HRM");
		} else {
			sheetDSNV = workbook.getSheet("HRM");
		}

		if (workbook.getSheet("ATT") == null) {
			sheetATT = workbook.createSheet("ATT");
		} else {
			sheetATT = workbook.getSheet("ATT");
		}

		sheetDSNV = createDSNSSheet(sheetDSNV, day, month, year);
		sheetATT = createATTSheet(sheetATT, day, month, year);

		// convert to bytearray and send to client
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

		String filename = "HRM.xlsx";
		InputStreamResource file = new InputStreamResource(inputStream);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}

	private int createCompanyInfo(Sheet sheet, int indexRow) {
		// 3 dòng giới thiệu công ty
		for (int i = 0; i < 3; i++) {
			Row row = sheet.createRow(indexRow);
			Cell cell = row.createCell(0);
			switch (i) {
			case 0:
				cell.setCellValue("CÔNG TY TNHH KINH DOANH CÔNG NGHỆ THÔNG TIN");
				cell.setCellStyle(cellCompanyStyle);
				break;
			case 1:
				XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();
				cellStyle.cloneStyleFrom(cellCompanyStyle);
				cell.setCellValue("Địa Chỉ: Khu Công Nghệ Cao, Thành Phố Quy Nhơn, Tỉnh Bình Định");
				cell.setCellStyle(cellStyle);
				break;
			case 2:
				XSSFCellStyle cellStyle1 = (XSSFCellStyle) workbook.createCellStyle();
				cellStyle1.cloneStyleFrom(cellCompanyStyle);
				cell.setCellValue("Website: https://localhost.com");
				cell.setCellStyle(cellStyle1);
				break;
			default:
				break;
			}
			// end switch
			indexRow++;
		}
		return indexRow;
	}

	private Sheet createATTSheet(Sheet sheet, int day, int month, int year) {

		YearMonth yearMonth = YearMonth.of(year, month);
		int numOfDayInMonth = yearMonth.lengthOfMonth();

		List<ATModel> listAtModel = iUserRepository.getAllATModels();

		sheet.setColumnWidth(0, Math.round(5 * 256));
		sheet.setColumnWidth(1, Math.round(24 * 256 + 256));
		sheet.setDisplayGridlines(false);

		int indexRow = 0;

		try {
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 12));
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 12));
		} catch (Exception e) {
			// TODO: handle exception

		}

		// Thông tin công ty
		indexRow = createCompanyInfo(sheet, indexRow);

		// Danh sách nhân label sự row
		indexRow = createCentralLabel(sheet, indexRow, "BẢNG CHẤM CÔNG THEO NGÀY", numOfDayInMonth + 7);

		// Tính đến label row
		indexRow = createTopLeftTableLabel(sheet, indexRow, "Tháng " + month + " Năm " + year, 2);

		// Danh mục header của bảng row
		indexRow = createTableAttendenceHeader(sheet, indexRow, month, year, numOfDayInMonth);

		// Tạo table body
		indexRow = createTableATTTableRow(sheet, indexRow, listAtModel, numOfDayInMonth, month, year);
		indexRow++;

		// Tạo chữ ký
		indexRow = createSignature(sheet, indexRow, numOfDayInMonth - 1, numOfDayInMonth + 6, day, month, year);

		return sheet;
	}

	private Sheet createDSNSSheet(Sheet sheetDSNV, int day, int month, int year) {

		sheetDSNV.setColumnWidth(0, Math.round(5 * 256));
		sheetDSNV.setColumnWidth(1, Math.round(24 * 256 + 256));
		sheetDSNV.setColumnWidth(2, Math.round(10 * 256 + 256));
		sheetDSNV.setColumnWidth(3, Math.round(18 * 256 + 256));
		sheetDSNV.setColumnWidth(4, Math.round(15 * 256 + 256));
		sheetDSNV.setColumnWidth(5, Math.round(40 * 256 + 256));
		sheetDSNV.setColumnWidth(6, Math.round(32 * 256 + 256));
		sheetDSNV.setColumnWidth(7, Math.round(10 * 256 + 256));
		sheetDSNV.setColumnWidth(8, Math.round(18 * 256 + 256));
		sheetDSNV.setColumnWidth(9, Math.round(15 * 256 + 256));
		sheetDSNV.setColumnWidth(10, Math.round(40 * 256 + 256));

		sheetDSNV.setDisplayGridlines(false);
		int indexRow = 0;

		try {
			sheetDSNV.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
			sheetDSNV.addMergedRegion(new CellRangeAddress(1, 1, 0, 4));
			sheetDSNV.addMergedRegion(new CellRangeAddress(2, 2, 0, 4));
		} catch (Exception e) {
			// TODO: handle exception

		}

		// Thông tin công ty
		indexRow = createCompanyInfo(sheetDSNV, indexRow);

		// Danh sách nhân label sự row
		indexRow = createCentralLabel(sheetDSNV, indexRow, "DANH SÁCH NHÂN SỰ", 10);

		// Tính đến label row
		indexRow = createTopLeftTableLabel(sheetDSNV, indexRow,
				"Tính đến: Ngày " + day + " Tháng " + month + " Năm " + year, 1);

		// Danh mục header của bảng row
		indexRow = createTableHeader(sheetDSNV, indexRow);

		// Tạo table row
		List<User> data = iUserRepository.findAll();
		indexRow = createTableRows(sheetDSNV, indexRow, data);
		indexRow++;
		// Thêm Phần chữ kí
		indexRow = createSignature(sheetDSNV, indexRow, 9, 10, day, month, year);

		// set autobreak and fit property
		sheetDSNV.setAutobreaks(true);
		sheetDSNV.setFitToPage(true);
		workbook.setPrintArea(workbook.getSheetIndex(sheetDSNV), 0, 11, 0, indexRow + 3);
		return sheetDSNV;
	}

	private int createSignature(Sheet sheet, int indexRow, int fromCol, int toCol, int day, int month, int year) {
		try {
			sheet.addMergedRegion(new CellRangeAddress(indexRow, indexRow, fromCol, toCol));
			sheet.addMergedRegion(new CellRangeAddress(indexRow + 1, indexRow + 1, fromCol, toCol));
		} catch (Exception e) {
			// TODO: handle exception
		}

		for (int i = 0; i < 2; i++) {
			Row row2 = sheet.createRow(indexRow);
			Cell cell2 = row2.createCell(fromCol);
			if (i == 0) {
				cell2.setCellValue("Quy Nhơn, Ngày " + day + " Tháng " + month + " Năm " + year);
			} else {
				cell2.setCellValue("Trưởng Bộ Phận Nhân Sự");
			}
			cell2.setCellStyle(cellSignatureStyle);
			indexRow++;
		}
		return indexRow;
	}

	private int createCentralLabel(Sheet sheet, int indexRow, String label, int lastMergeCol) {
		try {
			sheet.addMergedRegion(new CellRangeAddress(indexRow, indexRow, 0, lastMergeCol));
			sheet.addMergedRegion(new CellRangeAddress(indexRow + 1, indexRow + 1, 0, lastMergeCol));
		} catch (Exception e) {
			// TODO: handle exception
		}
		XSSFRichTextString richTextString = new XSSFRichTextString();
		Row row = sheet.createRow(indexRow);
		row.setHeight((short) Math.round(27.75 * 20));
		Cell cell = row.createCell(0);
		richTextString.setString(label);
		richTextString.applyFont(headerFont);
		cell.setCellValue(richTextString);
		cell.setCellStyle(cellHeaderStyle);
		indexRow++;
		return indexRow;
	}

	private int createTopLeftTableLabel(Sheet sheet, int indexRow, String str, int type) {
		if (type == 1) {
			indexRow++;
			Row row = sheet.createRow(indexRow);
			Cell cell = row.createCell(0);
			XSSFCellStyle cellStyle1 = (XSSFCellStyle) workbook.createCellStyle();
			cellStyle1.cloneStyleFrom(cellCompanyStyle);
			cellStyle1.setAlignment(HorizontalAlignment.LEFT);
			cell.setCellValue(str);
			cell.setCellStyle(cellStyle1);
			indexRow++;
			return indexRow;
		} else {
			indexRow++;
			Row row = sheet.createRow(indexRow - 1);
			Cell cell = row.createCell(0);
			XSSFCellStyle cellStyle1 = (XSSFCellStyle) workbook.createCellStyle();
			cellStyle1.cloneStyleFrom(cellCompanyStyle);
			cellStyle1.setAlignment(HorizontalAlignment.CENTER);
			cellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
			cell.setCellValue(str);
			cell.setCellStyle(cellStyle1);
			indexRow++;
			return indexRow;
		}

	}

	private int createTableHeader(Sheet sheet, int indexRow) {
		Row tableHeaderRow = sheet.createRow(indexRow);
		tableHeaderRow.setHeight((short) (33 * 20));
		for (int i = 0; i < tableHeaders.size(); i++) {
			XSSFRichTextString tableHeaderString = new XSSFRichTextString();
			tableHeaderString.setString(tableHeaders.get(i));
			tableHeaderString.applyFont(tableHeaderFont);
			Cell cellTableHeader = tableHeaderRow.createCell(i);
			cellTableHeader.setCellValue(tableHeaderString);
			cellTableHeader.setCellStyle(cellTableHeaderStyle);
		}
		indexRow++;
		return indexRow;
	}

	private int createTableAttendenceHeader(Sheet sheet, int indexRow, int month, int year, int numOfDayInMonth) {

		try {
			sheet.addMergedRegion(CellRangeAddress.valueOf("A7:A8"));
			sheet.addMergedRegion(CellRangeAddress.valueOf("B7:B8"));
			sheet.addMergedRegion(CellRangeAddress.valueOf("C7:C8"));
			// Ngày trong tháng
			sheet.addMergedRegion(new CellRangeAddress(indexRow, indexRow, 3, 2 + numOfDayInMonth));
			// Tổng cộng
			sheet.addMergedRegion(
					new CellRangeAddress(indexRow, indexRow + 1, 2 + numOfDayInMonth + 1, 2 + numOfDayInMonth + 1));
			// Ngày nghỉ
			sheet.addMergedRegion(
					new CellRangeAddress(indexRow, indexRow, 2 + numOfDayInMonth + 2, 2 + numOfDayInMonth + 4));
		} catch (Exception e) {
			// TODO: handle exception
		}

		for (int c = 3; c < numOfDayInMonth + 3; c++) {
			sheet.setColumnWidth(c, Math.round(3 * 256 + 256));
		}

		// first table header row
		Row firstHeaderRow = sheet.createRow(indexRow);
		firstHeaderRow.setHeight((short) (20 * 20));

		XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();
		cellStyle.cloneStyleFrom(cellTableRowStyle);
		cellStyle.setFont(fontBold);
		XSSFCellStyle x = cellTableRowStyle;

		for (int i = 0; i < numOfDayInMonth + 7; i++) {
			Cell cell = firstHeaderRow.createCell(i);
			String str = "";
			// if to fill first header
			if (i == 0) {
				str = "ID";
				x = cellStyle;
			} else if (i == 1) {
				str = "Họ và tên";
				x = cellStyle;
			} else if (i == 2) {
				str = "Chức vụ";
				x = cellStyle;
			} else if (i == 3) {
				str = "Ngày trong tháng";
				x = cellStyle;
			} else if (i == numOfDayInMonth + 3) {
				str = "Tổng cộng";
				x = cellStyle;
			} else if (i == numOfDayInMonth + 4) {
				str = "Ngày nghỉ";
				x = cellStyle;
			}

			cell.setCellValue(str);
			cell.setCellStyle(x);
			x = cellTableRowStyle;
		}

		// second tableHeaderRow
		Row secondHeaderRow = sheet.createRow(indexRow + 1);
		secondHeaderRow.setHeight((short) (50 * 20));

		Calendar calendar = Calendar.getInstance();

		for (int i = 0; i < numOfDayInMonth + 7; i++) {
			Cell cell = secondHeaderRow.createCell(i);
			String str = "";

			if (i > 2 && i < numOfDayInMonth + 3) {
				calendar.set(Calendar.DATE, i - 2);
				int date = calendar.get(Calendar.DAY_OF_WEEK);
				switch (date) {
				case 1:
					str = i - 2 + "\n" + "CN";
					break;
				case 2:
					str = i - 2 + "\n" + "T2";
					break;

				case 3:
					str = i - 2 + "\n" + "T3";
					break;

				case 4:
					str = i - 2 + "\n" + "T4";
					break;

				case 5:
					str = i - 2 + "\n" + "T5";
					break;

				case 6:
					str = i - 2 + "\n" + "T6";
					break;

				case 7:
					str = i - 2 + "\n" + "T7";
					break;
				}
			}

			// if to fill second header
			if (i == numOfDayInMonth + 4) {
				str = "Nghỉ không lương";
				x = cellStyle;
			} else if (i == numOfDayInMonth + 5) {
				str = "Nghỉ lễ";
				x = cellStyle;
			} else if (i == numOfDayInMonth + 6) {
				str = "Nghỉ phép";
				x = cellStyle;
			}

			cell.setCellValue(str);
			cell.setCellStyle(x);
			x = cellTableRowStyle;
		}

		indexRow += 2;
		return indexRow;
	}

	private int createTableATTTableRow(Sheet sheet, int indexRow, List<ATModel> data, int numOfDay, int month,
			int year) {

		// first table header row
		Row firstHeaderRow = sheet.createRow(indexRow);
		firstHeaderRow.setHeight((short) (20 * 20));

		int totalHoliday = getTotalHoliday(numOfDay, month, year);
		System.out.println(totalHoliday);

		XSSFCellStyle x = cellTableRowStyle;
		char st = '\u2713';

		for (int j = 0; j < data.size(); j++) {
			Row row = sheet.createRow(indexRow);
			ATModel atModel = data.get(j);
			String[] dayAtWorks = atModel.getDayAtWorks().split("-");

			for (int i = 0; i < numOfDay + 7; i++) {
				String str = "";
				Cell cell = row.createCell(i);

				if (i > 2 && i < numOfDay + 3) {
					if (isThisDay(i - 2, dayAtWorks)) {
						str = String.valueOf(st);
					}
				}

				// if to fill first header
				if (i == 0) {
					str = atModel.getId() + "";
					// x = cellStyle;
				} else if (i == 1) {
					str = atModel.getFirstname() + " " + atModel.getLastname();
					// x = cellStyle;
				} else if (i == 2) {
					str = "-";
					// x = cellStyle;
				} else if (i == numOfDay + 3) {
					// cột tổng cộng
					str = dayAtWorks.length + "";
					// x = cellStyle;
				} else if (i == numOfDay + 4) {
					// cell nghỉ không lương
					int numOfHoliday = calNumOfLeaveWithSalaryDay(atModel.getId());
					str = (numOfDay - dayAtWorks.length - totalHoliday - numOfHoliday) + "";
					// x = cellStyle;
				} else if (i == numOfDay + 5) {
					// cell nghỉ lễ
					str = totalHoliday + "";
				} else if (i == numOfDay + 6) {
					// cell nghỉ phép
					int numOfHoliday = calNumOfLeaveWithSalaryDay(atModel.getId());
					str = (numOfHoliday == 0) ? "-" : numOfHoliday + "";
					// x = cellStyle;
				}

				cell.setCellValue(str);
				cell.setCellStyle(x);
				x = cellTableRowStyle;
			}
			indexRow++;
		}

		return indexRow;
	}

	private Boolean isThisDay(int day, String[] dateStrings) {
		for (String dbDay : dateStrings) {
			if (Integer.parseInt(dbDay) == day) {
				return true;
			}
		}
		return false;
	}

	private int createTableRows(Sheet sheet, int startIndex, List<User> listUser) {

		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");

		for (int i = 0; i < listUser.size(); i++) {
			Row row = sheet.createRow(startIndex);
			User user = listUser.get(i);

			for (int j = 0; j < tableHeaders.size(); j++) {
				Cell cell = row.createCell(j);
				String str;
				// start switch value
				switch (j) {
				case 0:
					str = user.getId() + "";
					break;
				case 1:
					str = user.getFirstname() + " " + user.getLastname();
					break;
				case 2:
					str = (user.getSex().equals("male")) ? "Nam" : "Nữ";
					break;
				case 3:
					try {
//						calendar.setTime(user.getBirthDay());
						str = format1.format(user.getBirthDay());
					} catch (Exception e) {
						// TODO: handle exception
						str = "";
					}
					break;
				case 4:
					str = user.getPhoneNumber();
					break;
				case 5:
					str = user.getEmail();
					break;
				case 6:
					str = user.getLocal();
					break;
				case 7:
					str = user.getDepartmentId() + "";
					break;
				case 8:
					try {
//						calendar.setTime(user.getBirthDay());
						str = format1.format(user.getCreatedDate());
					} catch (Exception e) {
						// TODO: handle exception
						str = "";
					}
					break;
				case 9:
					str = (user.getStatus() == 1) ? "Hoạt Động" : "Nghỉ";
					break;
				default:
					str = "";
					break;
				}
				// end switch
				cell.setCellValue(str);

				// add style one line white and one line green
//				if (i % 2 == 0) {
//					cell.setCellStyle(cellTableRowGreenStyle);
//				} else if (user.getStatus() != 1) {
//					cell.setCellStyle(cellTableRowRedStyle);
//				}

				cell.setCellStyle(cellTableRowStyle);
				if (user.getStatus() != 1) {
					cell.setCellStyle(cellTableRowRedStyle);
				}
			}
			startIndex++;
		}

		return startIndex;
	}

	private int getTotalHoliday(int numOfday, int month, int year) {
		Solar solar = new Solar();
		solar.setSolarMonth(month);
		solar.setSolarYear(year);
		int totalHoliday = 0;

		LunarSolarConverter lunarSolarConverter = new LunarSolarConverter();
		for (int i = 1; i <= numOfday; i++) {
			solar.setSolarDay(i);
			Lunar lunar = lunarSolarConverter.SolarToLunar(solar);

			// Tết dương lịch 1-1
			if (solar.getSolarDay() == 1 && solar.getSolarMonth() == 1) {
				totalHoliday++;
			}

			// Tết âm lịch 1-1 âm lịch
			if (lunar.getLunarDay() == 1) {
				if (lunar.getLunarMonth() == 1) {
					totalHoliday += 5;
				}
			}

			// Giỗ tổ 10-3 âm lịch
			if (lunar.getLunarDay() == 10) {
				if (lunar.getLunarMonth() == 3) {
					totalHoliday += 1;
				}
			}

			// 30-4
			if (solar.getSolarDay() == 30 && solar.getSolarMonth() == 4) {
				totalHoliday++;
			}

			// 1-5
			if (solar.getSolarDay() == 1 && solar.getSolarMonth() == 5) {
				totalHoliday++;
			}

			// 2-9
			if (solar.getSolarDay() == 2 && solar.getSolarMonth() == 9) {
				totalHoliday += 2;
			}
		}

		return totalHoliday;
	}

	private int calNumOfLeaveWithSalaryDay(long uid) {
		int numOfHoliday = 0;
		Holiday holiday = iHolidayRepository.findByUid(uid);
		if (holiday == null) {
			return numOfHoliday;
		}

		String[] listSplitRaw = holiday.getNumOfDays().split("-");
		for (String leaveDayRaw : listSplitRaw) {
			int startDay = Integer.parseInt(leaveDayRaw.split(":")[0]);
			int endDay = Integer.parseInt(leaveDayRaw.split(":")[1]);
			numOfHoliday += endDay - startDay;
		}

		return numOfHoliday;
	}

}
