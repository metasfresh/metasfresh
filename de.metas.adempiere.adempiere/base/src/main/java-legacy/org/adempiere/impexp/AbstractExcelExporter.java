/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2008 SC ARHIPAC SERVICE SRL. All Rights Reserved.            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.impexp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Properties;
import org.slf4j.Logger;

import de.metas.i18n.Language;
import de.metas.i18n.Msg;
import de.metas.logging.LogManager;

import org.adempiere.util.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.compiere.Adempiere;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Util;

/**
 * Abstract MS Excel Format (xls) Exporter
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 */
public abstract class AbstractExcelExporter
{
	/**
	 * Is the current Row a Function Row
	 * @return true if function row
	 */
	public abstract boolean isFunctionRow();
	
	/**
	 * Get Columns Count
	 * @return number of columns
	 */
	public abstract int getColumnCount();
	
	/**
	 * Get Rows Count
	 * @return number of rows
	 */
	public abstract int getRowCount();
	
	/**
	 * Set current row
	 * @param row row index
	 */
	protected abstract void setCurrentRow(int row);
	
	/**
	 * Check if column is printed (displayed) 
	 * @param col column index
	 * @return true if is visible
	 */
	public abstract boolean isColumnPrinted(int col);

	/**
	 * Get column header name
	 * @param col column index
	 * @return header name
	 */
	public abstract String getHeaderName(int col);
	
	/**
	 * Get cell display type (see {@link DisplayType})
	 * @param row row index
	 * @param col column index
	 * @return display type
	 */
	public abstract int getDisplayType(int row, int col);
	
	/**
	 * Get cell value
	 * @param row row index
	 * @param col column index
	 * @return cell value
	 */
	public abstract Object getValueAt(int row, int col);
	
	/**
	 * Check if there is a page break on given cell
	 * @param row row index
	 * @param col column index
	 * @return true if there is a page break
	 */
	public abstract boolean isPageBreak(int row, int col);

	/** Logger */
	protected final Logger log = LogManager.getLogger(getClass());
	//
	private HSSFWorkbook m_workbook;
	private HSSFDataFormat m_dataFormat;
	private HSSFFont m_fontHeader = null;
	private HSSFFont m_fontDefault = null;
	private Language m_lang = null;
	private int m_sheetCount = 0;
	//
	private int m_colSplit = 1;
	private int m_rowSplit = 1;
	/** Styles cache */
	private HashMap<String, HSSFCellStyle> m_styles = new HashMap<String, HSSFCellStyle>();

	public AbstractExcelExporter() {
		m_workbook = new HSSFWorkbook();
		m_dataFormat = m_workbook.createDataFormat();
	}

	protected Properties getCtx() {
		return Env.getCtx();
	}

	protected void setFreezePane(int colSplit, int rowSplit) {
		m_colSplit = colSplit;
		m_rowSplit = rowSplit;
	}

	private String fixString(String str)
	{
		// ms excel doesn't support UTF8 charset
		return StringUtils.stripDiacritics(str);
	}

	protected Language getLanguage() {
		if (m_lang == null)
			m_lang = Env.getLanguage(getCtx());
		return m_lang;
	}

	private HSSFFont getFont(boolean isHeader) {
		HSSFFont font = null;
		if (isHeader) {
			if (m_fontHeader == null) {
				m_fontHeader = m_workbook.createFont();
				m_fontHeader.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			}
			font = m_fontHeader;
		}
		else if (isFunctionRow()) {
			font = m_workbook.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setItalic(true);
		}
		else {
			if (m_fontDefault == null) {
				m_fontDefault = m_workbook.createFont();
			}
			font = m_fontDefault;
		}
		return font;
	}

	/**
	 * Get Excel number format string by given {@link NumberFormat}
	 * @param df number format
	 * @param isHighlightNegativeNumbers highlight negative numbers using RED color
	 * @return number excel format string
	 */
	private String getFormatString(NumberFormat df, boolean isHighlightNegativeNumbers) {
		StringBuffer format = new StringBuffer();
		int integerDigitsMin = df.getMinimumIntegerDigits();
		int integerDigitsMax = df.getMaximumIntegerDigits();
		for (int i = 0; i < integerDigitsMax; i++) {
			if (i < integerDigitsMin)
				format.insert(0, "0");
			else
				format.insert(0, "#");
			if (i == 2) {
				format.insert(0, ",");
			}
		}
		int fractionDigitsMin = df.getMinimumFractionDigits();
		int fractionDigitsMax = df.getMaximumFractionDigits();
		for (int i = 0; i < fractionDigitsMax; i++) {
			if (i == 0)
				format.append(".");
			if (i < fractionDigitsMin)
				format.append("0");
			else
				format.append("#");
		}
		if (isHighlightNegativeNumbers) {
			String f = format.toString();
			format = new StringBuffer(f).append(";[RED]-").append(f);
		}
		//
		if (LogManager.isLevelFinest()) log.trace("NumberFormat: "+format);
		return format.toString();

	}

	private HSSFCellStyle getStyle(int row, int col) {
		int displayType = getDisplayType(row, col);
		String key = "cell-"+col+"-"+displayType;
		HSSFCellStyle cs = m_styles.get(key);
		if (cs == null) {
			boolean isHighlightNegativeNumbers = true;
			cs = m_workbook.createCellStyle();
			HSSFFont font = getFont(false);
			cs.setFont(font);
			// Border
			cs.setBorderLeft((short)1);
			cs.setBorderTop((short)1);
			cs.setBorderRight((short)1);
			cs.setBorderBottom((short)1);
			//
			if (DisplayType.isDate(displayType)) {
				cs.setDataFormat(m_dataFormat.getFormat("DD.MM.YYYY"));
			}
			else if (DisplayType.isNumeric(displayType)) {
				DecimalFormat df = DisplayType.getNumberFormat(displayType, getLanguage());
				String format = getFormatString(df, isHighlightNegativeNumbers);
				cs.setDataFormat(m_dataFormat.getFormat(format));
			}
			m_styles.put(key, cs);
		}
		return cs;
	}

	private HSSFCellStyle getHeaderStyle(int col)
	{
		String key = "header-"+col;
		HSSFCellStyle cs_header = m_styles.get(key);
		if (cs_header == null) {
			HSSFFont font_header = getFont(true);
			cs_header = m_workbook.createCellStyle();
			cs_header.setFont(font_header);
			cs_header.setBorderLeft((short)2);
			cs_header.setBorderTop((short)2);
			cs_header.setBorderRight((short)2);
			cs_header.setBorderBottom((short)2);
			cs_header.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
			cs_header.setWrapText(true);
			m_styles.put(key, cs_header);
		}
		return cs_header;
	}

	private void fixColumnWidth(HSSFSheet sheet, int lastColumnIndex)
	{
		for (short colnum = 0; colnum < lastColumnIndex; colnum++)
		{
			sheet.autoSizeColumn(colnum);
		}
	}

	private void closeTableSheet(HSSFSheet prevSheet, String prevSheetName, int colCount)
	{
		if (prevSheet == null)
			return;
		//
		fixColumnWidth(prevSheet, colCount);
		if (m_colSplit >= 0 || m_rowSplit >= 0)
			prevSheet.createFreezePane(m_colSplit >= 0 ? m_colSplit : 0, m_rowSplit >= 0 ? m_rowSplit : 0);
		if (!Util.isEmpty(prevSheetName, true) && m_sheetCount > 0) {
			int prevSheetIndex = m_sheetCount - 1;
			try {
				m_workbook.setSheetName(prevSheetIndex, prevSheetName);
			}
			catch (Exception e) {
				log.warn("Error setting sheet "+prevSheetIndex+" name to "+prevSheetName, e);
			}
		}
	}
	private HSSFSheet createTableSheet()
	{
		HSSFSheet sheet= m_workbook.createSheet();
		formatPage(sheet);
		createHeaderFooter(sheet);
		createTableHeader(sheet);
		m_sheetCount++;
		//
		return sheet;
	}

	private void createTableHeader(HSSFSheet sheet)
	{
		short colnumMax = 0;

		HSSFRow row = sheet.createRow(0);
		//	for all columns
		short colnum = 0;
		for (int col = 0; col < getColumnCount(); col++)
		{
			if (colnum > colnumMax)
				colnumMax = colnum;
			//
			if (isColumnPrinted(col))
			{
				HSSFCell cell = row.createCell(colnum);
				//	header row
				HSSFCellStyle style = getHeaderStyle(col);
				cell.setCellStyle(style);
				String str = fixString(getHeaderName(col));
				
				// poi37, poi301 compatibility issue
				cell.setCellValue(str);
				//cell.setCellValue(new HSSFRichTextString(str));
				
				colnum++;
			}	//	printed
		}	//	for all columns
//		m_workbook.setRepeatingRowsAndColumns(m_sheetCount, 0, 0, 0, 0);
	}

	protected void createHeaderFooter(HSSFSheet sheet)
	{
		// Sheet Header
		HSSFHeader header = sheet.getHeader();
		header.setRight(HSSFHeader.page()+ " / "+HSSFHeader.numPages());
		// Sheet Footer
		HSSFFooter footer = sheet.getFooter();
		footer.setLeft(Adempiere.getBrandCopyright());
		footer.setCenter(Env.getHeader(getCtx(), 0));
		Timestamp now = new Timestamp(System.currentTimeMillis());
		footer.setRight(DisplayType.getDateFormat(DisplayType.DateTime, getLanguage()).format(now));
	}

	protected void formatPage(HSSFSheet sheet)
	{
		sheet.setFitToPage(true);
		// Print Setup
		HSSFPrintSetup ps = sheet.getPrintSetup();
		ps.setFitWidth((short)1);
		ps.setNoColor(true);
		ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
		ps.setLandscape(false);
	}

	/**
	 * Export to given stream
	 * @param out
	 * @throws Exception
	 */
	public void export(OutputStream out)
	throws Exception
	{
		HSSFSheet sheet= createTableSheet();
		String sheetName = null;
		//
		int colnumMax = 0;
		for (int rownum = 0, xls_rownum = 1; rownum < getRowCount(); rownum++, xls_rownum++)
		{
			setCurrentRow(rownum);

			boolean isPageBreak = false;
			HSSFRow row = sheet.createRow(xls_rownum);
			//	for all columns
			int colnum = 0;
			for (int col = 0; col < getColumnCount(); col++)
			{
				if (colnum > colnumMax)
					colnumMax = colnum;
				//
				if (isColumnPrinted(col))
				{
					HSSFCell cell = row.createCell(colnum);
					
					// 03917: poi-3.7 doesn't have this method anymore
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16); // Bug-2017673 - Export Report as Excel - Bad Encoding
					
					// line row
					Object obj = getValueAt(rownum, col);
					int displayType = getDisplayType(rownum, col);
					if (obj == null)
						;
					else if (DisplayType.isDate(displayType)) {
						Timestamp value = (Timestamp)obj;
						cell.setCellValue(value);
					}
					else if (DisplayType.isNumeric(displayType)) {
						double value = 0;
						if (obj instanceof Number) {
							value = ((Number)obj).doubleValue();
						}
						cell.setCellValue(value);
					}
					else if (DisplayType.YesNo == displayType) {
						boolean value = false;
						if (obj instanceof Boolean)
							value = (Boolean)obj;
						else
							value = "Y".equals(obj);
						cell.setCellValue(new HSSFRichTextString(Msg.getMsg(getLanguage(), value == true ? "Y" : "N")));
					}
					else {
						String value = fixString(obj.toString());	//	formatted
						cell.setCellValue(new HSSFRichTextString(value));
					}
					//
					HSSFCellStyle style = getStyle(rownum, col);
					cell.setCellStyle(style);
					// Page break
					if (isPageBreak(rownum, col)) {
						isPageBreak = true;
						sheetName = fixString(cell.getRichStringCellValue().getString());
					}
					//
					colnum++;
				}	//	printed
			}	//	for all columns
			//
			// Page Break
			if (isPageBreak) {
				closeTableSheet(sheet, sheetName, colnumMax);
				sheet = createTableSheet();
				xls_rownum = 0;
				isPageBreak = false;
			}
		}	//	for all rows
		closeTableSheet(sheet, sheetName, colnumMax);
		//
		m_workbook.write(out);
		out.close();
		//
		// Workbook Info
		if (LogManager.isLevelFine()) {
			log.debug("Sheets #"+m_sheetCount);
			log.debug("Styles used #"+m_styles.size());
		}
	}

	/**
	 * Export to file
	 * @param file
	 * @param language reporting language
	 * @throws Exception
	 */
	public void export(File file, Language language)
	throws Exception
	{
		export(file, language, true);
	}

	/**
	 * Export to file
	 * @param file
	 * @param language reporting language
	 * @param autoOpen auto open file after generated
	 * @throws Exception
	 */
	public void export(File file, Language language, boolean autoOpen)
	throws Exception
	{
		m_lang = language;
		if (file == null)
			file = File.createTempFile("Report_", ".xls");
		FileOutputStream out = new FileOutputStream(file);
		export(out);
		if (autoOpen && Ini.isClient())
			Env.startBrowser(file.toURI().toString());
	}
}
