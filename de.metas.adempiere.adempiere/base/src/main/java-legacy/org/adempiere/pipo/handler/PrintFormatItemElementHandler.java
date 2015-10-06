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

import java.util.Properties;

import javax.xml.transform.sax.TransformerHandler;

import org.adempiere.pipo.AbstractElementHandler;
import org.adempiere.pipo.Element;
import org.adempiere.pipo.PackOut;
import org.adempiere.pipo.exception.POSaveFailedException;
import org.compiere.model.X_AD_PrintFormatItem;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class PrintFormatItemElementHandler extends AbstractElementHandler {

	public void startElement(Properties ctx, Element element)
			throws SAXException {
		String elementValue = element.getElementValue();
		int AD_Backup_ID = -1;
		String Object_Status = null;
		Attributes atts = element.attributes;
		log.info(elementValue + " " + atts.getValue("Name"));
		if (element.parent != null && element.parent.getElementValue().equals("printformat") &&
			element.parent.defer) {
			element.defer = true;
			return;
		}

		String name = atts.getValue("Name");
		int id = get_IDWithMaster(ctx, "AD_PrintFormatItem", name,
				"AD_PrintFormat", atts.getValue("ADPrintFormatNameID"));

		X_AD_PrintFormatItem m_PrintFormatItem = new X_AD_PrintFormatItem(ctx,
				id, getTrxName(ctx));
		if (id <= 0 && atts.getValue("AD_PrintFormatItem_ID") != null && Integer.parseInt(atts.getValue("AD_PrintFormatItem_ID")) <= PackOut.MAX_OFFICIAL_ID)
			m_PrintFormatItem.setAD_PrintFormatItem_ID(Integer.parseInt(atts.getValue("AD_PrintFormatItem_ID")));
		if (id > 0) {
			AD_Backup_ID = copyRecord(ctx, "AD_PrintFormatItem",
					m_PrintFormatItem);
			Object_Status = "Update";
		} else {
			Object_Status = "New";
			AD_Backup_ID = 0;
		}
		m_PrintFormatItem.setName(name);
		name = atts.getValue("ADPrintFormatNameID");
		if (element.parent != null && element.parent.getElementValue().equals("printformat") &&
			element.parent.recordId != 0) {
			id = element.parent.recordId;
		} else {
			id = get_IDWithColumn(ctx, "AD_PrintFormat", "Name", name);
			if (element.parent != null && element.parent.getElementValue().equals("printformat") &&
				id > 0) {
				element.parent.recordId = id;
			}
		}
		if (id <= 0) {
			element.defer = true;
			return;
		}
		m_PrintFormatItem.setAD_PrintFormat_ID(id);
				
		name = atts.getValue("ADTableNameID");
		int tableid = get_IDWithColumn(ctx, "AD_Table", "TableName", name);
		name = atts.getValue("ADColumnNameID");
		id = get_IDWithMasterAndColumn(ctx, "AD_Column", "ColumnName", name,
				"AD_Table", tableid);
		if (id > 0)
			m_PrintFormatItem.setAD_Column_ID(id);
		
		name = atts.getValue("ADPrintFormatChildNameID");
		if (name != null && name.trim().length() > 0) {
			id = get_IDWithColumn(ctx, "AD_PrintFormat", "Name", name);			
			if (id <= 0) {
				element.defer = true;
				element.unresolved = "AD_PrintFormat: " + name;
				return;
			}
			m_PrintFormatItem.setAD_PrintFormatChild_ID(id);
		}

		name = atts.getValue("ADPrintGraphID");
		if (name != null && name.trim().length() > 0) {
			id = get_IDWithColumn(ctx, "AD_PrintGraph", "Name", name);
			//TODO: export and import of ad_printgraph
			/*
			if (id <= 0) {
				element.defer = true;
				return;
			}*/
			if (id > 0)
				m_PrintFormatItem.setAD_PrintGraph_ID(id);
		}

		name = atts.getValue("ADPrintColorID");
		if (name != null && name.trim().length() > 0) {
			id = get_IDWithColumn(ctx, "AD_PrintColor", "Name", name);
			//TODO: export and import of ad_printcolor
			/*
			if (id <= 0) {
				element.defer = true;
				return;
			}*/
			if (id > 0)
				m_PrintFormatItem.setAD_PrintColor_ID(id);
		}

		name = atts.getValue("ADPrintFontID");
		if (name != null && name.trim().length() > 0) {
			id = get_IDWithColumn(ctx, "AD_PrintFont", "Name", name);
			//TODO: export and import of print font
			/*
			if (id <= 0) {
				element.defer = true;
				return;
			}*/
			if (id > 0)
				m_PrintFormatItem.setAD_PrintFont_ID(id);
		}

		m_PrintFormatItem.setPrintName(getStringValue(atts, "PrintName"));
		m_PrintFormatItem.setName(atts.getValue("Name"));
		m_PrintFormatItem.setPrintAreaType(getStringValue(atts,"PrintAreaType"));

		m_PrintFormatItem.setSeqNo(Integer.parseInt(atts.getValue("SeqNo")));
		m_PrintFormatItem.setPrintFormatType(getStringValue(atts,"PrintFormatType"));
		m_PrintFormatItem.setXSpace(Integer.parseInt(atts.getValue("XSpace")));

		m_PrintFormatItem.setYSpace(Integer.parseInt(atts.getValue("YSpace")));
		m_PrintFormatItem.setXPosition(Integer.parseInt(atts
				.getValue("Xposition")));
		m_PrintFormatItem.setYPosition(Integer.parseInt(atts
				.getValue("Yposition")));

		m_PrintFormatItem.setMaxWidth(Integer.parseInt(atts
				.getValue("MaxWidth")));
		m_PrintFormatItem.setMaxHeight(Integer.parseInt(atts
				.getValue("MaxHieght")));
		m_PrintFormatItem.setSortNo(Integer.parseInt(atts.getValue("SortNo")));

		m_PrintFormatItem.setFieldAlignmentType(getStringValue(atts
				,"FieldAlignmentType"));
		m_PrintFormatItem.setLineAlignmentType(getStringValue(atts
				,"LineAlignmentType"));
		m_PrintFormatItem.setImageURL(getStringValue(atts,"ImageURL"));
		m_PrintFormatItem.setArcDiameter(Integer.parseInt(atts
				.getValue("ArcDiameter")));
		m_PrintFormatItem.setLineWidth(Integer.parseInt(atts
				.getValue("LineWidth")));
		m_PrintFormatItem.setShapeType(getStringValue(atts,"ShapeType"));

		m_PrintFormatItem.setBelowColumn(Integer.parseInt(atts
				.getValue("BelowColumn")));
		m_PrintFormatItem.setPrintNameSuffix(getStringValue(atts,"PrintNameSuffix"));
		m_PrintFormatItem.setRunningTotalLines(Integer.parseInt(atts
				.getValue("RunningTotalLines")));

		m_PrintFormatItem
				.setIsActive(atts.getValue("isActive") != null ? Boolean
						.valueOf(atts.getValue("isActive")).booleanValue()
						: true);
		m_PrintFormatItem.setIsPrinted(Boolean.valueOf(
				atts.getValue("isPrinted")).booleanValue());
		m_PrintFormatItem.setIsRelativePosition(Boolean.valueOf(
				atts.getValue("isRelativePosition")).booleanValue());
		m_PrintFormatItem.setIsNextLine(Boolean.valueOf(
				atts.getValue("isNextLine")).booleanValue());

		m_PrintFormatItem.setIsHeightOneLine(Boolean.valueOf(
				atts.getValue("isHeightOneLine")).booleanValue());
		m_PrintFormatItem.setIsOrderBy(Boolean.valueOf(
				atts.getValue("isOrderBy")).booleanValue());
		m_PrintFormatItem.setIsGroupBy(Boolean.valueOf(
				atts.getValue("isGroupBy")).booleanValue());

		m_PrintFormatItem.setIsPageBreak(Boolean.valueOf(
				atts.getValue("isPageBreak")).booleanValue());
		m_PrintFormatItem.setIsSummarized(Boolean.valueOf(
				atts.getValue("isSummarized")).booleanValue());
		m_PrintFormatItem.setImageIsAttached(Boolean.valueOf(
				atts.getValue("isImageIsAttached")).booleanValue());

		m_PrintFormatItem.setIsAveraged(Boolean.valueOf(
				atts.getValue("isAveraged")).booleanValue());
		m_PrintFormatItem.setIsCounted(Boolean.valueOf(
				atts.getValue("isCounted")).booleanValue());
		m_PrintFormatItem.setIsSetNLPosition(Boolean.valueOf(
				atts.getValue("isSetNLPosition")).booleanValue());
		m_PrintFormatItem.setIsSuppressNull(Boolean.valueOf(
				atts.getValue("isSuppressNull")).booleanValue());

		m_PrintFormatItem.setIsFixedWidth(Boolean.valueOf(
				atts.getValue("isFixedWidth")).booleanValue());
		m_PrintFormatItem.setIsNextPage(Boolean.valueOf(
				atts.getValue("isNextPage")).booleanValue());
		m_PrintFormatItem.setIsMaxCalc(Boolean.valueOf(
				atts.getValue("isMaxCalc")).booleanValue());
		m_PrintFormatItem.setIsMinCalc(Boolean.valueOf(
				atts.getValue("isMinCalc")).booleanValue());

		m_PrintFormatItem.setIsRunningTotal(Boolean.valueOf(
				atts.getValue("isRunningTotal")).booleanValue());
		m_PrintFormatItem.setIsVarianceCalc(Boolean.valueOf(
				atts.getValue("isVarianceCalc")).booleanValue());
		m_PrintFormatItem.setIsDeviationCalc(Boolean.valueOf(
				atts.getValue("isDeviationCalc")).booleanValue());
		
		// BarCode Type
		String barCodeType = atts.getValue(X_AD_PrintFormatItem.COLUMNNAME_BarcodeType);
		m_PrintFormatItem.setBarcodeType(barCodeType);

		if (m_PrintFormatItem.save(getTrxName(ctx)) == true) {
			record_log(ctx, 1, m_PrintFormatItem.getName(), "PrintFormatItem",
					m_PrintFormatItem.get_ID(), AD_Backup_ID, Object_Status,
					"AD_PrintFormatItem", get_IDWithColumn(ctx, "AD_Table",
							"TableName", "AD_PrintFormatItem"));
		} else {
			record_log(ctx, 0, m_PrintFormatItem.getName(), "PrintFormatItem",
					m_PrintFormatItem.get_ID(), AD_Backup_ID, Object_Status,
					"AD_PrintFormatItem", get_IDWithColumn(ctx, "AD_Table",
							"TableName", "AD_PrintFormatItem"));
			throw new POSaveFailedException("PrintFormatItem");
		}
	}

	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		int AD_PrintFormatItem_ID = Env.getContextAsInt(ctx,
				X_AD_PrintFormatItem.COLUMNNAME_AD_PrintFormatItem_ID);
		X_AD_PrintFormatItem m_PrintFormatItem = new X_AD_PrintFormatItem(ctx,
				AD_PrintFormatItem_ID, null);
		AttributesImpl atts = new AttributesImpl();
		createPrintFormatItemBinding(atts, m_PrintFormatItem);
		document.startElement("", "", "printformatitem", atts);
		document.endElement("", "", "printformatitem");
	}

	private AttributesImpl createPrintFormatItemBinding(AttributesImpl atts,
			X_AD_PrintFormatItem m_PrintformatItem) {
		String sql = null;
		String name = null;
		atts.clear();
		if (m_PrintformatItem.getAD_PrintFormatItem_ID() <= PackOut.MAX_OFFICIAL_ID)
	        atts.addAttribute("","","AD_PrintFormatItem_ID","CDATA",Integer.toString(m_PrintformatItem.getAD_PrintFormatItem_ID()));
		if (m_PrintformatItem.getAD_PrintFormat_ID() > 0) {
			sql = "SELECT Name FROM AD_PrintFormat WHERE AD_PrintFormat_ID=?";
			name = DB.getSQLValueString(null, sql, m_PrintformatItem
					.getAD_PrintFormat_ID());
			atts.addAttribute("", "", "ADPrintFormatNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADPrintFormatNameID", "CDATA", "");

		if (m_PrintformatItem.getAD_PrintFormatChild_ID() > 0) {
			sql = "SELECT Name FROM AD_PrintFormat WHERE AD_PrintFormat_ID=?";
			name = DB.getSQLValueString(null, sql, m_PrintformatItem
					.getAD_PrintFormatChild_ID());
			atts
					.addAttribute("", "", "ADPrintFormatChildNameID", "CDATA",
							name);
		} else
			atts.addAttribute("", "", "ADPrintFormatChildNameID", "CDATA", "");

		if (m_PrintformatItem.getAD_Column_ID() > 0) {
			sql = "SELECT ColumnName FROM AD_Column WHERE AD_Column_ID=?";
			name = DB.getSQLValueString(null, sql, m_PrintformatItem
					.getAD_Column_ID());
			atts.addAttribute("", "", "ADColumnNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADColumnNameID", "CDATA", "");

		if (m_PrintformatItem.getAD_Column_ID() > 0) {
			sql = "SELECT AD_Table_ID FROM AD_Column WHERE AD_Column_ID=?";
			int tableID = DB.getSQLValue(null, sql, m_PrintformatItem
					.getAD_Column_ID());
			sql = "SELECT TableName FROM AD_Table WHERE AD_Table_ID=?";
			name = DB.getSQLValueString(null, sql, tableID);
			atts.addAttribute("", "", "ADTableNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADTableNameID", "CDATA", "");

		if (m_PrintformatItem.getAD_PrintGraph_ID() > 0) {
			sql = "SELECT Name FROM AD_PrintGraph WHERE AD_PrintGraph_ID=?";
			name = DB.getSQLValueString(null, sql, m_PrintformatItem
					.getAD_PrintGraph_ID());
			atts.addAttribute("", "", "ADPrintGraphID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADPrintGraphID", "CDATA", "");

		if (m_PrintformatItem.getAD_PrintColor_ID() > 0) {
			sql = "SELECT Name FROM AD_PrintColor WHERE AD_PrintColor_ID=?";
			name = DB.getSQLValueString(null, sql, m_PrintformatItem
					.getAD_PrintColor_ID());
			atts.addAttribute("", "", "ADPrintColorID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADPrintColorID", "CDATA", "");

		if (m_PrintformatItem.getAD_PrintFont_ID() > 0) {
			sql = "SELECT Name FROM AD_PrintFont WHERE AD_PrintFont_ID=?";
			name = DB.getSQLValueString(null, sql, m_PrintformatItem
					.getAD_PrintFont_ID());
			atts.addAttribute("", "", "ADPrintFontID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADPrintFontID", "CDATA", "");

		atts.addAttribute("", "", "PrintName", "CDATA",
				(m_PrintformatItem.getPrintName() != null ? m_PrintformatItem
						.getPrintName() : ""));
		atts.addAttribute("", "", "Name", "CDATA",
				(m_PrintformatItem.getName() != null ? m_PrintformatItem
						.getName() : ""));
		atts.addAttribute("", "", "PrintAreaType", "CDATA", (m_PrintformatItem
				.getPrintAreaType() != null ? m_PrintformatItem
				.getPrintAreaType() : ""));
		atts.addAttribute("", "", "SeqNo", "CDATA", ""
				+ m_PrintformatItem.getSeqNo());
		atts.addAttribute("", "", "PrintFormatType", "CDATA", m_PrintformatItem
				.getPrintFormatType());
		atts.addAttribute("", "", "XSpace", "CDATA", ("" + m_PrintformatItem
				.getXSpace()));
		atts.addAttribute("", "", "YSpace", "CDATA", ("" + m_PrintformatItem
				.getYSpace()));
		atts.addAttribute("", "", "Xposition", "CDATA", ("" + m_PrintformatItem
				.getXPosition()));
		atts.addAttribute("", "", "Yposition", "CDATA", ("" + m_PrintformatItem
				.getYPosition()));
		atts.addAttribute("", "", "MaxWidth", "CDATA", ("" + m_PrintformatItem
				.getMaxWidth()));
		atts.addAttribute("", "", "MaxHieght", "CDATA", ("" + m_PrintformatItem
				.getMaxHeight()));
		atts.addAttribute("", "", "SortNo", "CDATA", ("" + m_PrintformatItem
				.getSortNo()));
		atts
				.addAttribute(
						"",
						"",
						"FieldAlignmentType",
						"CDATA",
						(m_PrintformatItem.getFieldAlignmentType() != null ? m_PrintformatItem
								.getFieldAlignmentType()
								: ""));
		atts
				.addAttribute(
						"",
						"",
						"LineAlignmentType",
						"CDATA",
						(m_PrintformatItem.getLineAlignmentType() != null ? m_PrintformatItem
								.getLineAlignmentType()
								: ""));
		atts.addAttribute("", "", "ImageURL", "CDATA", (m_PrintformatItem
				.getImageURL() != null ? m_PrintformatItem.getImageURL() : ""));
		atts.addAttribute("", "", "BelowColumn", "CDATA",
				("" + m_PrintformatItem.getBelowColumn()));
		atts.addAttribute("", "", "RunningTotalLines", "CDATA",
				("" + m_PrintformatItem.getRunningTotalLines()));
		atts
				.addAttribute(
						"",
						"",
						"PrintNameSuffix",
						"CDATA",
						(m_PrintformatItem.getPrintNameSuffix() != null ? m_PrintformatItem
								.getPrintNameSuffix()
								: ""));
		atts.addAttribute("", "", "ArcDiameter", "CDATA", ""
				+ m_PrintformatItem.getArcDiameter());
		atts.addAttribute("", "", "LineWidth", "CDATA", ""
				+ m_PrintformatItem.getLineWidth());
		atts
				.addAttribute("", "", "ShapeType", "CDATA", m_PrintformatItem
						.getShapeType() != null ? m_PrintformatItem
						.getShapeType() : "");
		atts.addAttribute("", "", "isActive", "CDATA", (m_PrintformatItem
				.isActive() == true ? "true" : "false"));
		atts.addAttribute("", "", "isPrinted", "CDATA", (m_PrintformatItem
				.isPrinted() == true ? "true" : "false"));
		atts.addAttribute("", "", "isRelativePosition", "CDATA",
				(m_PrintformatItem.isRelativePosition() == true ? "true"
						: "false"));
		atts.addAttribute("", "", "isNextLine", "CDATA", (m_PrintformatItem
				.isNextLine() == true ? "true" : "false"));
		atts
				.addAttribute("", "", "isHeightOneLine", "CDATA",
						(m_PrintformatItem.isHeightOneLine() == true ? "true"
								: "false"));
		atts.addAttribute("", "", "isOrderBy", "CDATA", (m_PrintformatItem
				.isOrderBy() == true ? "true" : "false"));
		atts.addAttribute("", "", "isGroupBy", "CDATA", (m_PrintformatItem
				.isGroupBy() == true ? "true" : "false"));
		atts.addAttribute("", "", "isPageBreak", "CDATA", (m_PrintformatItem
				.isPageBreak() == true ? "true" : "false"));
		atts.addAttribute("", "", "isSummarized", "CDATA", (m_PrintformatItem
				.isSummarized() == true ? "true" : "false"));
		atts.addAttribute("", "", "isImageIsAttached", "CDATA",
				(m_PrintformatItem.isImageIsAttached() == true ? "true"
						: "false"));
		atts.addAttribute("", "", "isAveraged", "CDATA", (m_PrintformatItem
				.isAveraged() == true ? "true" : "false"));
		atts.addAttribute("", "", "isCounted", "CDATA", (m_PrintformatItem
				.isCounted() == true ? "true" : "false"));
		atts
				.addAttribute("", "", "isSetNLPosition", "CDATA",
						(m_PrintformatItem.isSetNLPosition() == true ? "true"
								: "false"));
		atts.addAttribute("", "", "isSuppressNull", "CDATA", (m_PrintformatItem
				.isSuppressNull() == true ? "true" : "false"));
		atts.addAttribute("", "", "isFixedWidth", "CDATA", (m_PrintformatItem
				.isFixedWidth() == true ? "true" : "false"));
		atts.addAttribute("", "", "isNextPage", "CDATA", (m_PrintformatItem
				.isNextPage() == true ? "true" : "false"));
		atts.addAttribute("", "", "isMaxCalc", "CDATA", (m_PrintformatItem
				.isMaxCalc() == true ? "true" : "false"));
		atts.addAttribute("", "", "isMinCalc", "CDATA", (m_PrintformatItem
				.isMinCalc() == true ? "true" : "false"));
		atts.addAttribute("", "", "isRunningTotal", "CDATA", (m_PrintformatItem
				.isRunningTotal() == true ? "true" : "false"));
		atts.addAttribute("", "", "isVarianceCalc", "CDATA", (m_PrintformatItem
				.isVarianceCalc() == true ? "true" : "false"));
		atts
				.addAttribute("", "", "isDeviationCalc", "CDATA",
						(m_PrintformatItem.isDeviationCalc() == true ? "true"
								: "false"));
		// BarCode Type
		if (m_PrintformatItem.getBarcodeType() != null)
			atts.addAttribute("", "", X_AD_PrintFormatItem.COLUMNNAME_BarcodeType, "CDATA",
					m_PrintformatItem.getBarcodeType());

		return atts;
	}
}
