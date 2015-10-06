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
 *****************************************************************************/
package org.adempiere.pipo.handler;

import java.util.Properties;

import javax.xml.transform.sax.TransformerHandler;

import org.adempiere.pipo.AbstractElementHandler;
import org.adempiere.pipo.Element;
import org.adempiere.pipo.PackOut;
import org.adempiere.pipo.exception.POSaveFailedException;
import org.compiere.model.X_AD_ReportView_Col;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class ReportViewColElementHandler extends AbstractElementHandler {

	public void startElement(Properties ctx, Element element)
			throws SAXException {
		String elementValue = element.getElementValue();
		int AD_Backup_ID = -1;
		String Object_Status = null;

		Attributes atts = element.attributes;
		log.info(elementValue + " " + atts.getValue("ADReportViewColID"));

		String entitytype = atts.getValue("EntityType");
		
		if (isProcessElement(ctx, entitytype)) {
			String name = atts.getValue("ADReportviewNameID");
			int AD_ReportView_ID = 0;
			if (element.parent != null && element.parent.getElementValue().equals("reportview") &&
				element.parent.recordId > 0) {
				AD_ReportView_ID = element.parent.recordId;
			} else {
				AD_ReportView_ID = get_IDWithColumn(ctx, "AD_ReportView", "Name", name);
			}
			if (AD_ReportView_ID <= 0) {
				element.defer = true;
				return;
			}
			
			name = atts.getValue("ADColumnNameID");
			int AD_Column_ID = 0;
			if (name != null && name.trim().length() > 0) {
				AD_Column_ID = get_IDWithColumn(ctx, "AD_Column", "Name", name);
				if (AD_Column_ID <= 0) {
					element.defer = true;
					return;
				}
			}
			
			String functionColumn = getStringValue(atts, "FunctionColumn");
			StringBuffer sql = new StringBuffer("SELECT AD_Reportview_Col_ID FROM AD_Reportview_Col ")
				.append(" WHERE AD_Column_ID ");
			if (AD_Column_ID > 0)
				sql.append(" = " + AD_Column_ID);
			else
				sql.append(" IS NULL ");
			sql.append(" AND FunctionColumn = ?");
			
			int id = DB.getSQLValue(getTrxName(ctx), sql.toString(), functionColumn);
			if (id < 0) id = 0;
			X_AD_ReportView_Col m_Reportview_Col = new X_AD_ReportView_Col(ctx,
					id, getTrxName(ctx));
			if (id <= 0 && atts.getValue("AD_ReportView_Col_ID") != null && Integer.parseInt(atts.getValue("AD_ReportView_Col_ID")) <= PackOut.MAX_OFFICIAL_ID)
				m_Reportview_Col.setAD_ReportView_Col_ID(Integer.parseInt(atts.getValue("AD_ReportView_Col_ID")));
			if (id > 0) {
				AD_Backup_ID = copyRecord(ctx, "AD_Reportview_Col",
						m_Reportview_Col);
				Object_Status = "Update";
			} else {
				Object_Status = "New";
				AD_Backup_ID = 0;
			}
			
			boolean isGroupFunction = Boolean.valueOf(
					atts.getValue("isGroupFunction")).booleanValue();
			
			m_Reportview_Col.setAD_ReportView_ID(AD_ReportView_ID);

			if (AD_Column_ID > 0) {
				m_Reportview_Col.setAD_Column_ID(id);
			}

			m_Reportview_Col.setFunctionColumn(functionColumn);
			m_Reportview_Col
					.setIsActive(atts.getValue("isActive") != null ? Boolean
							.valueOf(atts.getValue("isActive")).booleanValue()
							: true);
			m_Reportview_Col.setIsGroupFunction(isGroupFunction);
			if (m_Reportview_Col.save(getTrxName(ctx)) == true) {
				record_log(ctx, 1, "" + m_Reportview_Col.getAD_ReportView_ID(),
						"Reportview_Col", m_Reportview_Col.get_ID(),
						AD_Backup_ID, Object_Status, "AD_Reportview_Col",
						get_IDWithColumn(ctx, "AD_Table", "TableName",
								"AD_Reportview_Col"));
			} else {
				record_log(ctx, 0, "" + m_Reportview_Col.getAD_ReportView_ID(),
						"Reportview_Col", m_Reportview_Col.get_ID(),
						AD_Backup_ID, Object_Status, "AD_Reportview_Col",
						get_IDWithColumn(ctx, "AD_Table", "TableName",
								"AD_Reportview_Col"));
				throw new POSaveFailedException("ReportViewCol");
			}
		} else {
			element.skip = true;
		}
	}

	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		int AD_ReportView_Col_ID = Env.getContextAsInt(ctx,
				X_AD_ReportView_Col.COLUMNNAME_AD_ReportView_Col_ID);
		X_AD_ReportView_Col m_Reportview_Col = new X_AD_ReportView_Col(ctx,
				AD_ReportView_Col_ID, getTrxName(ctx));
		AttributesImpl atts = new AttributesImpl();
		createReportViewColBinding(atts, m_Reportview_Col);
		document.startElement("", "", "reportviewcol", atts);
		document.endElement("", "", "reportviewcol");
	}

	private AttributesImpl createReportViewColBinding(AttributesImpl atts,
			X_AD_ReportView_Col m_Reportview_Col) {
		String sql = null;
		String name = null;
		atts.clear();
		if (m_Reportview_Col.getAD_ReportView_Col_ID() <= PackOut.MAX_OFFICIAL_ID)
	        atts.addAttribute("","","AD_ReportView_Col_ID","CDATA",Integer.toString(m_Reportview_Col.getAD_ReportView_Col_ID()));
		if (m_Reportview_Col.getAD_Column_ID() > 0) {
			sql = "SELECT ColumnName FROM AD_Column WHERE AD_Column_ID=?";
			name = DB.getSQLValueString(null, sql, m_Reportview_Col
					.getAD_Column_ID());
			atts.addAttribute("", "", "ADColumnNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADColumnNameID", "CDATA", "");

		if (m_Reportview_Col.getAD_ReportView_ID() > 0) {
			sql = "SELECT Name FROM AD_Reportview WHERE AD_Reportview_ID=?";
			name = DB.getSQLValueString(null, sql, m_Reportview_Col
					.getAD_ReportView_ID());
			atts.addAttribute("", "", "ADReportviewNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADReportviewNameID", "CDATA", "");

		atts.addAttribute("", "", "FunctionColumn", "CDATA", (m_Reportview_Col
				.getFunctionColumn() != null ? m_Reportview_Col
				.getFunctionColumn() : ""));
		atts.addAttribute("", "", "isActive", "CDATA", (m_Reportview_Col
				.isActive() == true ? "true" : "false"));
		atts.addAttribute("", "", "isGroupFunction", "CDATA", (m_Reportview_Col
				.isGroupFunction() == true ? "true" : "false"));
		return atts;
	}
}
