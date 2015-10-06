/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *
 * Copyright (C) 2005 Robert Klein. robeklein@hotmail.com
 * Contributor(s): Low Heng Sin hengsin@avantz.com
 *                 Teo Sarca, SC ARHIPAC SERVICE SRL
 *****************************************************************************/
package org.adempiere.pipo.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import javax.xml.transform.sax.TransformerHandler;

import org.adempiere.pipo.AbstractElementHandler;
import org.adempiere.pipo.Element;
import org.adempiere.pipo.PackOut;
import org.adempiere.pipo.exception.DatabaseAccessException;
import org.adempiere.pipo.exception.POSaveFailedException;
import org.compiere.model.MTable;
import org.compiere.model.X_AD_Package_Exp_Detail;
import org.compiere.model.X_AD_PrintFormat;
import org.compiere.model.X_AD_PrintFormatItem;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class PrintFormatElementHandler extends AbstractElementHandler {

	private PrintFormatItemElementHandler itemHandler = new PrintFormatItemElementHandler();

	private List<Integer> formats = new ArrayList<Integer>();
	
	public void startElement(Properties ctx, Element element)
			throws SAXException {
		String elementValue = element.getElementValue();
		int AD_Backup_ID = -1;
		String Object_Status = null;
		Attributes atts = element.attributes;
		log.info(elementValue + " " + atts.getValue("Name"));

		String name = atts.getValue("Name");
		int id = get_IDWithColumn(ctx, "AD_PrintFormat", "Name", name);
		X_AD_PrintFormat m_PrintFormat = new X_AD_PrintFormat(ctx, id,
				getTrxName(ctx));
		if (id <= 0 && atts.getValue("AD_PrintFormat_ID") != null && Integer.parseInt(atts.getValue("AD_PrintFormat_ID")) <= PackOut.MAX_OFFICIAL_ID)
			m_PrintFormat.setAD_PrintFormat_ID(Integer.parseInt(atts.getValue("AD_PrintFormat_ID")));
		if (id > 0) {
			AD_Backup_ID = copyRecord(ctx, "AD_PrintFormat", m_PrintFormat);
			Object_Status = "Update";
		} else {
			Object_Status = "New";
			AD_Backup_ID = 0;
		}

		name = atts.getValue("ADReportviewnameID");
		if (name != null && name.trim().length() > 0) {
			id = get_IDWithColumn(ctx, "AD_ReportView", "Name", name);
			if (id <= 0) {
				element.defer = true;
				return;
			}
			m_PrintFormat.setAD_ReportView_ID(id);
		}

		name = atts.getValue("ADTableNameID");
		id = get_IDWithColumn(ctx, "AD_Table", "TableName", name);
		if (id == 0) {
			MTable m_Table = new MTable(ctx, 0, getTrxName(ctx));
			m_Table.setAccessLevel("3");
			m_Table.setName(name);
			m_Table.setTableName(name);
			if (m_Table.save(getTrxName(ctx)) == true) {
				record_log(ctx, 1, m_Table.getName(), "Table",
						m_Table.get_ID(), 0, "New", "AD_Table",
						get_IDWithColumn(ctx, "AD_Table", "TableName",
								"AD_Table"));
			} else {
				record_log(ctx, 0, m_Table.getName(), "Table",
						m_Table.get_ID(), 0, "New", "AD_Table",
						get_IDWithColumn(ctx, "AD_Table", "TableName",
								"AD_Table"));
			}
			id = get_IDWithColumn(ctx, "AD_Table", "TableName", name);
		}
		m_PrintFormat.setAD_Table_ID(id);

		name = atts.getValue("ADPrintTableFormatID");
		if (name != null && name.trim().length() > 0) {
			id = get_IDWithColumn(ctx, "AD_PrintTableFormat", "Name", name);
			if (id <= 0) {
				element.defer = true;
				return;
			}
			m_PrintFormat.setAD_PrintTableFormat_ID(id);
		}

		name = atts.getValue("ADPrintColorID");
		if (name != null && name.trim().length() > 0) {
			id = get_IDWithColumn(ctx, "AD_PrintColor", "Name", name);
			if (id <= 0) {
				element.defer = true;
				return;
			}
			m_PrintFormat.setAD_PrintColor_ID(id);
		}

		name = atts.getValue("ADPrintFontID");
		if (name != null && name.trim().length() > 0) {
			id = get_IDWithColumn(ctx, "AD_PrintFont", "Name", name);
			if (id <= 0) {
				element.defer = true;
				return;
			}
			m_PrintFormat.setAD_PrintFont_ID(id);
		}

		name = atts.getValue("ADPrintPaperID");
		if (name != null && name.trim().length() > 0) {
			id = get_IDWithColumn(ctx, "AD_PrintPaper", "Name", name);
			if (id <= 0) {
				element.defer = true;
				return;
			}
			m_PrintFormat.setAD_PrintPaper_ID(id);
		}

		m_PrintFormat.setDescription(getStringValue(atts, "Description"));
		m_PrintFormat.setName(atts.getValue("Name"));
		m_PrintFormat.setPrinterName(getStringValue(atts, "PrinterName"));
		m_PrintFormat.setFooterMargin(Integer.parseInt(atts
				.getValue("FooterMargin")));

		m_PrintFormat.setHeaderMargin(Integer.parseInt(atts
				.getValue("HeaderMargin")));
		m_PrintFormat.setCreateCopy(atts.getValue("CreateCopy"));
		m_PrintFormat.setIsActive(atts.getValue("isActive") != null ? Boolean
				.valueOf(atts.getValue("isActive")).booleanValue() : true);

		m_PrintFormat.setIsTableBased(Boolean.valueOf(
				atts.getValue("isTableBased")).booleanValue());
		m_PrintFormat.setIsForm(Boolean.valueOf(atts.getValue("isForm"))
				.booleanValue());
		m_PrintFormat.setIsStandardHeaderFooter(Boolean.valueOf(
				atts.getValue("isStandardHeader")).booleanValue());

		m_PrintFormat.setIsDefault(Boolean.valueOf(atts.getValue("isDefault"))
				.booleanValue());
		if (m_PrintFormat.save(getTrxName(ctx)) == true) {
			record_log(ctx, 1, m_PrintFormat.getName(), "PrintFormat",
					m_PrintFormat.get_ID(), AD_Backup_ID, Object_Status,
					"AD_PrintFormat", get_IDWithColumn(ctx, "AD_Table",
							"TableName", "AD_PrintFormat"));
			element.recordId = m_PrintFormat.getAD_PrintFormat_ID();
		} else {
			record_log(ctx, 0, m_PrintFormat.getName(), "PrintFormat",
					m_PrintFormat.get_ID(), AD_Backup_ID, Object_Status,
					"AD_PrintFormat", get_IDWithColumn(ctx, "AD_Table",
							"TableName", "AD_PrintFormat"));
			throw new POSaveFailedException("Failed to save Print Format");
		}
	}

	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		int AD_PrintFormat_ID = Env.getContextAsInt(ctx, X_AD_Package_Exp_Detail.COLUMNNAME_AD_PrintFormat_ID);
		PackOut packOut = (PackOut) ctx.get("PackOutProcess");
		
		if (formats.contains(AD_PrintFormat_ID))
			return;
		formats.add(AD_PrintFormat_ID);
		AttributesImpl atts = new AttributesImpl();
		String sql = null;
		sql = "SELECT AD_PrintFormat_ID "
				+ "FROM AD_PrintFormat "
				+ "WHERE (AD_PrintFormat_ID IN "
				+ "(SELECT AD_PrintFormatChild_ID FROM AD_PrintFormatItem WHERE AD_PrintFormat_ID = "
				+ AD_PrintFormat_ID + " AND PrintFormatType = 'P' GROUP BY AD_PrintFormatChild_ID) OR AD_PrintFormat_ID = "
				+ AD_PrintFormat_ID + ")";
		
		PreparedStatement pstmt = null;
		pstmt = DB.prepareStatement(sql, getTrxName(ctx));
		try {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				X_AD_PrintFormat m_Printformat = new X_AD_PrintFormat(ctx, rs.getInt("AD_PrintFormat_ID"), null);
				
				if (m_Printformat.getAD_PrintPaper_ID() > 0)
					packOut.createPrintPaper(m_Printformat.getAD_PrintPaper_ID(), document);
				
				createPrintFormatBinding(atts, m_Printformat);
				document.startElement("", "", "printformat", atts);

				String sql2 = "SELECT * FROM AD_PrintFormatItem WHERE AD_PrintFormat_ID= "
						+ m_Printformat.getAD_PrintFormat_ID()
						+ " ORDER BY "+X_AD_PrintFormatItem.COLUMNNAME_SeqNo+","+X_AD_PrintFormatItem.COLUMNNAME_AD_PrintFormatItem_ID;
				PreparedStatement pstmt2 = null;
				pstmt2 = DB.prepareStatement(sql2, getTrxName(ctx));
				try {
					ResultSet rs2 = pstmt2.executeQuery();
					while (rs2.next()) {
						createItem(ctx, document, rs2
								.getInt("AD_PrintFormatItem_ID"));
					}
					rs2.close();
					pstmt2.close();
					pstmt2 = null;
				} finally {
					try {
						if (pstmt2 != null)
							pstmt2.close();
					} catch (Exception e) {
					}
					pstmt2 = null;
				}
				document.endElement("", "", "printformat");
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			if (e instanceof SAXException)
				throw (SAXException) e;
			else if (e instanceof SQLException)
				throw new DatabaseAccessException("Failed to export print format.", e);
			else if (e instanceof RuntimeException)
				throw (RuntimeException) e;
			else
				throw new RuntimeException("Failed to export print format.", e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
			}
			pstmt = null;
		}

	}

	private void createItem(Properties ctx, TransformerHandler document,
			int AD_PrintFormatItem_ID) throws SAXException {
		Env.setContext(ctx,
				X_AD_PrintFormatItem.COLUMNNAME_AD_PrintFormatItem_ID,
				AD_PrintFormatItem_ID);
		itemHandler.create(ctx, document);
		ctx.remove(X_AD_PrintFormatItem.COLUMNNAME_AD_PrintFormatItem_ID);
	}

	private AttributesImpl createPrintFormatBinding(AttributesImpl atts,
			X_AD_PrintFormat m_Printformat) {
		String sql = null;
		String name = null;
		atts.clear();
		if (m_Printformat.getAD_PrintFormat_ID() <= PackOut.MAX_OFFICIAL_ID)
	        atts.addAttribute("","","AD_PrintFormat_ID","CDATA",Integer.toString(m_Printformat.getAD_PrintFormat_ID()));
		if (m_Printformat.getAD_ReportView_ID() > 0) {
			sql = "SELECT Name FROM AD_ReportView WHERE AD_ReportView_ID=?";
			name = DB.getSQLValueString(null, sql, m_Printformat
					.getAD_ReportView_ID());
			atts.addAttribute("", "", "ADReportviewnameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADReportviewnameID", "CDATA", "");

		if (m_Printformat.getAD_Table_ID() > 0) {
			sql = "SELECT TableName FROM AD_Table WHERE AD_Table_ID=?";
			name = DB.getSQLValueString(null, sql, m_Printformat
					.getAD_Table_ID());
			atts.addAttribute("", "", "ADTableNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADTableNameID", "CDATA", "");

		if (m_Printformat.getAD_PrintTableFormat_ID() > 0) {
			sql = "SELECT Name FROM AD_PrintTableFormat WHERE AD_PrintTableFormat_ID=?";
			name = DB.getSQLValueString(null, sql, m_Printformat
					.getAD_PrintTableFormat_ID());
			atts.addAttribute("", "", "ADPrintTableFormatID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADPrintTableFormatID", "CDATA", "");

		if (m_Printformat.getAD_PrintColor_ID() > 0) {
			sql = "SELECT Name FROM AD_PrintColor WHERE AD_PrintColor_ID=?";
			name = DB.getSQLValueString(null, sql, m_Printformat
					.getAD_PrintColor_ID());
			atts.addAttribute("", "", "ADPrintColorID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADPrintColorID", "CDATA", "");

		if (m_Printformat.getAD_PrintFont_ID() > 0) {
			sql = "SELECT Name FROM AD_PrintFont WHERE AD_PrintFont_ID=?";
			name = DB.getSQLValueString(null, sql, m_Printformat
					.getAD_PrintFont_ID());
			atts.addAttribute("", "", "ADPrintFontID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADPrintFontID", "CDATA", "");

		if (m_Printformat.getAD_PrintPaper_ID() > 0) {
			sql = "SELECT Name FROM AD_PrintPaper WHERE AD_PrintPaper_ID=?";
			name = DB.getSQLValueString(null, sql, m_Printformat
					.getAD_PrintPaper_ID());
			atts.addAttribute("", "", "ADPrintPaperID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADPrintPaperID", "CDATA", "");

		atts.addAttribute("", "", "Description", "CDATA",
				(m_Printformat.getDescription() != null ? m_Printformat
						.getDescription() : ""));
		atts
				.addAttribute("", "", "Name", "CDATA",
						(m_Printformat.getName() != null ? m_Printformat
								.getName() : ""));
		atts.addAttribute("", "", "PrinterName", "CDATA",
				(m_Printformat.getPrinterName() != null ? m_Printformat
						.getPrinterName() : ""));
		atts.addAttribute("", "", "FooterMargin", "CDATA", ""
				+ m_Printformat.getFooterMargin());
		atts.addAttribute("", "", "HeaderMargin", "CDATA", ""
				+ m_Printformat.getHeaderMargin());
		atts.addAttribute("", "", "CreateCopy", "CDATA", (m_Printformat
				.getCreateCopy() != null ? m_Printformat.getCreateCopy() : ""));
		atts.addAttribute("", "", "isActive", "CDATA", (m_Printformat
				.isActive() == true ? "true" : "false"));
		atts.addAttribute("", "", "isTableBased", "CDATA", (m_Printformat
				.isTableBased() == true ? "true" : "false"));
		atts.addAttribute("", "", "isForm", "CDATA",
				(m_Printformat.isForm() == true ? "true" : "false"));
		atts.addAttribute("", "", "isStandardHeader", "CDATA", (m_Printformat
				.isStandardHeaderFooter() == true ? "true" : "false"));
		atts.addAttribute("", "", "isDefault", "CDATA", (m_Printformat
				.isDefault() == true ? "true" : "false"));
		return atts;
	}
}
