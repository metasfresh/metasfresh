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
 *                 Teo Sarca, teo.sarca@gmail.com
 *****************************************************************************/
package org.adempiere.pipo.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import org.compiere.model.X_AD_ImpFormat;
import org.compiere.model.X_AD_ImpFormat_Row;
import org.compiere.model.X_AD_Package_Exp_Detail;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class ImpFormatElementHandler extends AbstractElementHandler {

	private ImpFormatRowElementHandler rowHandler = new ImpFormatRowElementHandler();

	private List<Integer> formats = new ArrayList<Integer>();
	
	public void startElement(Properties ctx, Element element)
			throws SAXException {
		String elementValue = element.getElementValue();
		int AD_Backup_ID = -1;
		String Object_Status = null;
		Attributes atts = element.attributes;
		log.info(elementValue + " " + atts.getValue("Name"));

		int id = get_ID(ctx, "AD_ImpFormat", atts.getValue("Name"));
		X_AD_ImpFormat m_ImpFormat = new X_AD_ImpFormat(ctx, id,
				getTrxName(ctx));
		if (id <= 0 && atts.getValue("AD_ImpFormat_ID") != null && Integer.parseInt(atts.getValue("AD_ImpFormat_ID")) <= PackOut.MAX_OFFICIAL_ID)
			m_ImpFormat.setAD_ImpFormat_ID(Integer.parseInt(atts.getValue("AD_ImpFormat_ID")));
		if (id > 0) {
			AD_Backup_ID = copyRecord(ctx, "AD_ImpFormat", m_ImpFormat);
			Object_Status = "Update";
		} else {
			Object_Status = "New";
			AD_Backup_ID = 0;
		}
		m_ImpFormat.setName(atts.getValue("Name"));
		String name = atts.getValue("ADTableNameID");
		if (name != null && name.trim().length() > 0) {
			id = get_IDWithColumn(ctx, "AD_Table", "TableName", name);
			if (id <= 0) {
				element.defer = true;
				return;
			}
			m_ImpFormat.setAD_Table_ID(id);
		}

		m_ImpFormat.setIsActive(atts.getValue("isActive") != null ? Boolean
				.valueOf(atts.getValue("isActive")).booleanValue() : true);
		m_ImpFormat
				.setProcessing(atts.getValue("isProcessing") != null ? Boolean
						.valueOf(atts.getValue("isProcessing")).booleanValue()
						: true);
		m_ImpFormat.setName(atts.getValue("Name"));
		m_ImpFormat.setDescription(getStringValue(atts,"Description"));
		m_ImpFormat.setFormatType(atts.getValue("FormatType"));
		if (m_ImpFormat.save(getTrxName(ctx)) == true) {
			record_log(ctx, 1, m_ImpFormat.getName(), "ImpFormat", m_ImpFormat
					.get_ID(), AD_Backup_ID, Object_Status, "AD_ImpFormat",
					get_IDWithColumn(ctx, "AD_Table", "TableName",
							"AD_ImpFormat"));
		} else {
			record_log(ctx, 0, m_ImpFormat.getName(), "ImpFormat", m_ImpFormat
					.get_ID(), AD_Backup_ID, Object_Status, "AD_ImpFormat",
					get_IDWithColumn(ctx, "AD_Table", "TableName",
							"AD_ImpFormat"));
			throw new POSaveFailedException("Failed to save Import Format.");
		}
	}

	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		int import_id = Env.getContextAsInt(ctx,
				X_AD_Package_Exp_Detail.COLUMNNAME_AD_ImpFormat_ID);
		
		if (formats.contains(import_id))
			return;
		formats.add(import_id);
		AttributesImpl atts = new AttributesImpl();
		X_AD_ImpFormat m_ImpFormat = new X_AD_ImpFormat(ctx, import_id, null);
		atts = createImpFormatBinding(atts, m_ImpFormat);

		document.startElement("", "", "impformat", atts);
		String sql = "SELECT * FROM AD_ImpFormat_Row WHERE AD_ImpFormat_ID= " + import_id
		+" ORDER BY "+X_AD_ImpFormat_Row.COLUMNNAME_AD_ImpFormat_Row_ID;

		PreparedStatement pstmt = null;
		pstmt = DB.prepareStatement(sql, getTrxName(ctx));

		try {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				createImpFormatRow(ctx, document, rs
						.getInt("AD_ImpFormat_Row_ID"));
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		} catch (Exception e) {
			log.log(Level.SEVERE, "ImpFormat", e);
			throw new DatabaseAccessException("Failed to export Import Format.", e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
			}
			pstmt = null;
		}
		document.endElement("", "", "impformat");

	}

	private void createImpFormatRow(Properties ctx,
			TransformerHandler document, int AD_ImpFormat_Row_ID)
			throws SAXException {
		Env.setContext(ctx, X_AD_ImpFormat_Row.COLUMNNAME_AD_ImpFormat_Row_ID,
				AD_ImpFormat_Row_ID);
		rowHandler.create(ctx, document);
		ctx.remove(X_AD_ImpFormat_Row.COLUMNNAME_AD_ImpFormat_Row_ID);
	}

	private AttributesImpl createImpFormatBinding(AttributesImpl atts,
			X_AD_ImpFormat m_ImpFormat) {
		atts.clear();
		if (m_ImpFormat.getAD_ImpFormat_ID() <= PackOut.MAX_OFFICIAL_ID)
	        atts.addAttribute("","","AD_ImpFormat_ID","CDATA",Integer.toString(m_ImpFormat.getAD_ImpFormat_ID()));
		if (m_ImpFormat.getAD_Table_ID() > 0) {
			String sql = "SELECT TableName FROM AD_Table WHERE AD_Table_ID=?";
			String name = DB.getSQLValueString(null, sql, m_ImpFormat
					.getAD_Table_ID());
			atts.addAttribute("", "", "ADTableNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADTableNameID", "CDATA", "");

		atts.addAttribute("", "", "Name", "CDATA",
				(m_ImpFormat.getName() != null ? m_ImpFormat.getName() : ""));
		atts.addAttribute("", "", "isActive", "CDATA",
				(m_ImpFormat.isActive() == true ? "true" : "false"));
		atts.addAttribute("", "", "isProcessing", "CDATA", (m_ImpFormat
				.isProcessing() == true ? "true" : "false"));
		atts.addAttribute("", "", "Description", "CDATA", (m_ImpFormat
				.getDescription() != null ? m_ImpFormat.getDescription() : ""));
		atts.addAttribute("", "", "FormatType", "CDATA", (m_ImpFormat
				.getFormatType() != null ? m_ImpFormat.getFormatType() : ""));
		return atts;
	}
}
