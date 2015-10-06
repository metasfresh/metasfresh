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
import org.compiere.model.X_AD_PrintFormat;
import org.compiere.model.X_AD_ReportView;
import org.compiere.model.X_AD_ReportView_Col;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class ReportViewElementHandler extends AbstractElementHandler {

	private ReportViewColElementHandler columnHandler = new ReportViewColElementHandler();

	private List<Integer> views = new ArrayList<Integer>();
	
	public void startElement(Properties ctx, Element element)
			throws SAXException {
		String elementValue = element.getElementValue();
		int AD_Backup_ID = -1;
		String Object_Status = null;
		Attributes atts = element.attributes;
		log.info(elementValue + " " + atts.getValue("ADReportviewnameID"));
		//String entitytype = atts.getValue("EntityType");
		String name = atts.getValue("ADReportviewnameID");

		int id = get_ID(ctx, "AD_ReportView", name);
		X_AD_ReportView m_Reportview = new X_AD_ReportView(ctx, id,
				getTrxName(ctx));
		if (id <= 0 && atts.getValue("AD_ReportView_ID") != null && Integer.parseInt(atts.getValue("AD_ReportView_ID")) <= PackOut.MAX_OFFICIAL_ID)
			m_Reportview.setAD_ReportView_ID(Integer.parseInt(atts.getValue("AD_ReportView_ID")));
		if (id > 0) {
			AD_Backup_ID = copyRecord(ctx, "AD_Reportview", m_Reportview);
			Object_Status = "Update";
		} else {
			Object_Status = "New";
			AD_Backup_ID = 0;
		}
		String Name = atts.getValue("ADTableNameID");
		id = get_IDWithColumn(ctx, "AD_Table", "TableName", Name);
		MTable m_Table = null;
		if (id == 0) {
			m_Table = new MTable(ctx, 0, getTrxName(ctx));
			m_Table.setAccessLevel("3");
			m_Table.setName(Name);
			m_Table.setTableName(Name);
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
			id = get_IDWithColumn(ctx, "AD_Table", "TableName", Name);
		}

		m_Reportview.setAD_Table_ID(id);
		m_Reportview.setDescription(getStringValue(atts,"Description"));
		m_Reportview.setEntityType(atts.getValue("EntityType"));
		m_Reportview.setName(atts.getValue("Name"));
		m_Reportview.setIsActive(atts.getValue("isActive") != null ? Boolean
				.valueOf(atts.getValue("isActive")).booleanValue() : true);
		m_Reportview.setOrderByClause(getStringValue(atts,"OrderByClause"));
		m_Reportview.setWhereClause(getStringValue(atts,"WhereClause"));
		if (m_Reportview.save(getTrxName(ctx)) == true) {
			record_log(ctx, 1, m_Reportview.getName(), "Reportview",
					m_Reportview.get_ID(), AD_Backup_ID, Object_Status,
					"AD_Reportview", get_IDWithColumn(ctx, "AD_Table",
							"TableName", "AD_Reportview"));
			element.recordId = m_Reportview.getAD_ReportView_ID();
		} else {
			record_log(ctx, 0, m_Reportview.getName(), "Reportview",
					m_Reportview.get_ID(), AD_Backup_ID, Object_Status,
					"AD_Reportview", get_IDWithColumn(ctx, "AD_Table",
							"TableName", "AD_Reportview"));
			throw new POSaveFailedException("ReportView");
		}
	}

	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		PackOut packOut = (PackOut) ctx.get("PackOutProcess");
		int AD_ReportView_ID = Env.getContextAsInt(ctx, "AD_ReportView_ID");
		if (views.contains(AD_ReportView_ID))
			return;
		
		views.add(AD_ReportView_ID);
		String sql = "SELECT * FROM AD_ReportView WHERE AD_ReportView_ID= " + AD_ReportView_ID;
		PreparedStatement pstmt = null;
		pstmt = DB.prepareStatement(sql, getTrxName(ctx));
		AttributesImpl atts = new AttributesImpl();
		try {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				X_AD_ReportView m_Reportview = new X_AD_ReportView(ctx, rs
						.getInt("AD_Reportview_ID"), null);
				atts = createReportViewBinding(atts, m_Reportview);
				document.startElement("", "", "reportview", atts);
				document.endElement("", "", "reportview");
				
				String sql1 = "SELECT * FROM AD_Printformat WHERE AD_Reportview_ID="+AD_ReportView_ID
						+" AND AD_Client_ID="+Env.getAD_Client_ID(ctx)
						+" ORDER BY "+X_AD_PrintFormat.COLUMNNAME_AD_PrintFormat_ID
						;
				PreparedStatement pstmt1 = null;
				pstmt1 = DB.prepareStatement(sql1, getTrxName(ctx));
				try {
					ResultSet rs1 = pstmt1.executeQuery();
					while (rs1.next()) {
						// Export Table if neccessary
						packOut.createTable(rs1.getInt("AD_Table_ID"), 
								document);
						packOut.createPrintFormat(rs1
								.getInt("AD_Printformat_ID"), document);
					}
					rs1.close();
					pstmt1.close();
					pstmt1 = null;
				} finally {
					try {
						if (pstmt1 != null)
							pstmt1.close();
					} catch (Exception e) {
					}
					pstmt1 = null;
				}
				atts.clear();
				sql1 = "SELECT * FROM AD_ReportView_Col WHERE AD_Reportview_ID= "
						+ AD_ReportView_ID;
				pstmt1 = null;
				pstmt1 = DB.prepareStatement(sql1, getTrxName(ctx));
				try {
					ResultSet rs1 = pstmt1.executeQuery();
					while (rs1.next()) {
						createReportViewCol(ctx, document, rs1
								.getInt("AD_ReportView_Col_ID"));
					}
					rs1.close();
					pstmt1.close();
					pstmt1 = null;
				} finally {
					try {
						if (pstmt1 != null)
							pstmt1.close();
					} catch (Exception e) {
					}
					pstmt1 = null;
				}
			}

			rs.close();
			pstmt.close();
			pstmt = null;
		}

		catch (Exception e) {
			log.log(Level.SEVERE, "reportview", e);
			if (e instanceof SAXException)
				throw (SAXException) e;
			else if (e instanceof SQLException)
				throw new DatabaseAccessException("Failed to export report view.", e);
			else if (e instanceof RuntimeException)
				throw (RuntimeException) e;
			else
				throw new RuntimeException("Failed to export report view.", e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
			}
			pstmt = null;
		}
	}

	private void createReportViewCol(Properties ctx,
			TransformerHandler document, int AD_ReportView_Col_ID)
			throws SAXException {
		Env.setContext(ctx,
				X_AD_ReportView_Col.COLUMNNAME_AD_ReportView_Col_ID,
				AD_ReportView_Col_ID);
		columnHandler.create(ctx, document);
		ctx.remove(X_AD_ReportView_Col.COLUMNNAME_AD_ReportView_Col_ID);
	}

	private AttributesImpl createReportViewBinding(AttributesImpl atts,
			X_AD_ReportView m_Reportview) {
		String sql = null;
		String name = null;
		atts.clear();
		if (m_Reportview.getAD_ReportView_ID() <= PackOut.MAX_OFFICIAL_ID)
	        atts.addAttribute("","","AD_ReportView_ID","CDATA",Integer.toString(m_Reportview.getAD_ReportView_ID()));

		if (m_Reportview.getAD_ReportView_ID() > 0) {
			sql = "SELECT Name FROM AD_ReportView WHERE AD_ReportView_ID=?";
			name = DB.getSQLValueString(null, sql, m_Reportview
					.getAD_ReportView_ID());
			atts.addAttribute("", "", "ADReportviewnameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADReportviewnameID", "CDATA", "");

		if (m_Reportview.getAD_Table_ID() > 0) {
			sql = "SELECT TableName FROM AD_Table WHERE AD_Table_ID=?";
			name = DB.getSQLValueString(null, sql, m_Reportview
					.getAD_Table_ID());
			atts.addAttribute("", "", "ADTableNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADTableNameID", "CDATA", "");

		atts
				.addAttribute("", "", "Description", "CDATA", (m_Reportview
						.getDescription() != null ? m_Reportview
						.getDescription() : ""));
		atts.addAttribute("", "", "EntityType", "CDATA", (m_Reportview
				.getEntityType() != null ? m_Reportview.getEntityType() : ""));
		atts.addAttribute("", "", "Name", "CDATA",
				(m_Reportview.getName() != null ? m_Reportview.getName() : ""));
		atts.addAttribute("", "", "isActive", "CDATA",
				(m_Reportview.isActive() == true ? "true" : "false"));
		atts.addAttribute("", "", "OrderByClause", "CDATA", (m_Reportview
				.getOrderByClause() != null ? m_Reportview.getOrderByClause()
				: ""));
		atts
				.addAttribute("", "", "WhereClause", "CDATA", (m_Reportview
						.getWhereClause() != null ? m_Reportview
						.getWhereClause() : ""));
		return atts;
	}
}
