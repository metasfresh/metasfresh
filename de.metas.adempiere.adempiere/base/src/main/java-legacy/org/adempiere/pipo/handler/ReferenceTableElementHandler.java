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
import java.util.Properties;
import java.util.logging.Level;

import javax.xml.transform.sax.TransformerHandler;

import org.adempiere.pipo.AbstractElementHandler;
import org.adempiere.pipo.Element;
import org.adempiere.pipo.PackOut;
import org.adempiere.pipo.exception.DatabaseAccessException;
import org.adempiere.pipo.exception.POSaveFailedException;
import org.compiere.model.MColumn;
import org.compiere.model.MTable;
import org.compiere.model.X_AD_Ref_Table;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class ReferenceTableElementHandler extends AbstractElementHandler {

	public void startElement(Properties ctx, Element element)
			throws SAXException {
		String elementValue = element.getElementValue();
		int AD_Backup_ID = -1;
		String Object_Status = null;

		log.info(elementValue);
		Attributes atts = element.attributes;
		String entitytype = atts.getValue("EntityType");
		String name = atts.getValue("ADRefenceNameID");
		if (isProcessElement(ctx, entitytype)) {
			if (element.parent != null && element.parent.skip) {
				element.skip = true;
				return;
			}
			
			int AD_Reference_ID = 0;
			if (element.parent != null && element.parent.getElementValue().equals("reference") &&
				element.parent.recordId > 0) {
				AD_Reference_ID = element.parent.recordId;
			} else {
				StringBuffer sqlB = new StringBuffer(
					"SELECT AD_Reference_ID FROM AD_Reference WHERE Name= ?");
				AD_Reference_ID = DB.getSQLValue(getTrxName(ctx), sqlB.toString(), name);
			}
			if (AD_Reference_ID <= 0 && atts.getValue("AD_Reference_ID") != null && Integer.parseInt(atts.getValue("AD_Reference_ID")) <= PackOut.MAX_OFFICIAL_ID)
				AD_Reference_ID = Integer.parseInt(atts.getValue("AD_Reference_ID"));
			
			StringBuffer sqlB = new StringBuffer(
					"SELECT Count(*) FROM AD_Ref_Table WHERE AD_Reference_ID= ?");
			int count = DB.getSQLValue(getTrxName(ctx), sqlB.toString(), AD_Reference_ID);
			int tableId = get_IDWithColumn(ctx, "AD_Table", "TableName", atts
					.getValue("ADTableNameID"));
			if (tableId == 0) {
				MTable m_Table = new MTable(ctx, 0, getTrxName(ctx));
				m_Table.setAccessLevel("3");
				m_Table.setName(atts.getValue("ADTableNameID"));
				m_Table.setTableName(atts.getValue("ADTableNameID"));
				if (m_Table.save(getTrxName(ctx)) == true) {
					record_log(ctx, 1, m_Table.getName(), "Table", m_Table
							.get_ID(), 0, "New", "AD_Table", get_IDWithColumn(
							ctx, "AD_Table", "TableName", "AD_Table"));
				} else {
					record_log(ctx, 0, m_Table.getName(), "Table", m_Table
							.get_ID(), 0, "New", "AD_Table", get_IDWithColumn(
							ctx, "AD_Table", "TableName", "AD_Table"));
				}
				tableId = get_IDWithColumn(ctx, "AD_Table", "TableName", atts
						.getValue("ADTableNameID"));
			}
			name = atts.getValue("ADDisplay");
			int DisplayId = get_IDWithMasterAndColumn(ctx, "AD_Column",
					"ColumnName", name, "AD_Table", tableId);
			if (DisplayId == 0) {
				MColumn m_Column = new MColumn(ctx, 0, getTrxName(ctx));
				m_Column.setAD_Table_ID(tableId);
				// m_Column.setVersion(new BigDecimal("1")); // use constructor
				// value
				m_Column.setColumnName(name);
				m_Column.setName(name);
				m_Column.setAD_Reference_ID(30);
				if (m_Column.save(getTrxName(ctx)) == true) {
					record_log(ctx, 1, m_Column.getName(), "Column", m_Column
							.get_ID(), 0, "New", "AD_Column", get_IDWithColumn(
							ctx, "AD_Table", "TableName", "AD_Column"));
				} else {
					record_log(ctx, 0, m_Column.getName(), "Column", m_Column
							.get_ID(), 0, "New", "AD_Column", get_IDWithColumn(
							ctx, "AD_Table", "TableName", "AD_Column"));
				}
			}
			name = atts.getValue("Key");
			int keyId = get_IDWithMasterAndColumn(ctx, "AD_Column",
					"ColumnName", name, "AD_Table", tableId);
			if (keyId == 0) {
				MColumn m_Column = new MColumn(ctx, 0, getTrxName(ctx));
				m_Column.setAD_Table_ID(tableId);
				// m_Column.setVersion(new BigDecimal("1")); // use constructor
				// value
				m_Column.setColumnName(name);
				m_Column.setName(name);
				m_Column.setAD_Reference_ID(30);
				if (m_Column.save(getTrxName(ctx)) == true) {
					record_log(ctx, 1, m_Column.getName(), "Column", m_Column
							.get_ID(), 0, "New", "AD_Column", get_IDWithColumn(
							ctx, "AD_Table", "TableName", "AD_Column"));
				} else {
					record_log(ctx, 0, m_Column.getName(), "Column", m_Column
							.get_ID(), 0, "New", "AD_Column", get_IDWithColumn(
							ctx, "AD_Table", "TableName", "AD_Column"));
				}
			}

			name = atts.getValue("ADDisplay");
			DisplayId = get_IDWithMasterAndColumn(ctx, "AD_Column",
					"ColumnName", name, "AD_Table", tableId);
			name = atts.getValue("Key");
			keyId = get_IDWithMasterAndColumn(ctx, "AD_Column", "ColumnName",
					name, "AD_Table", tableId);
			String entityType = atts.getValue("EntityType");
			String isValueDisplayed = atts.getValue("IsValueDisplayed");
			String OrderByClause = atts.getValue("OrderByClause").replaceAll("'", "''");
			String WhereClause = atts.getValue("WhereClause").replaceAll("'","''");
			if (count > 0) {
				sqlB = new StringBuffer("UPDATE AD_Ref_Table ").append(
						"SET AD_Table_ID = " + tableId).append(
						", AD_Display = " + DisplayId).append(
						", AD_Key = " + keyId).append(
						", isValueDisplayed = '" + isValueDisplayed).append(
						"', OrderByClause = '" + OrderByClause).append(
						"', EntityType ='" + entityType).append(
						"', WhereClause = '" + WhereClause).append(
						"' WHERE AD_Reference_ID = " + AD_Reference_ID);

				int no = DB.executeUpdate(sqlB.toString(), getTrxName(ctx));
				if (no > 0) {
					record_log(ctx, 1, atts.getValue("ADRefenceNameID"),
							"Reference Table", AD_Reference_ID, 0, "Update", "AD_Ref_Table",
							get_IDWithColumn(ctx, "AD_Table", "TableName",
									"AD_Ref_Table"));
				} else {
					record_log(ctx, 0, atts.getValue("ADRefenceNameID"),
							"Reference Table", AD_Reference_ID, 0, "Update", "AD_Ref_Table",
							get_IDWithColumn(ctx, "AD_Table", "TableName",
									"AD_Ref_Table"));
					throw new POSaveFailedException("ReferenceTable");
				}
			} else {
				sqlB = new StringBuffer("INSERT INTO AD_Ref_Table")
						.append(
								"(AD_Client_ID, AD_Org_ID, CreatedBy, UpdatedBy, ")
						.append(
								"AD_Reference_ID, AD_Table_ID, AD_Display, AD_Key ")
						.append(
								",entityType, isValueDisplayed, OrderByClause, ")
						.append(" WhereClause )").append(
								"VALUES(0, 0, 0, 0, " + AD_Reference_ID).append(
								", " + tableId).append(", " + DisplayId)
						.append(", " + keyId).append(", '" + entityType)
						.append("', '" + isValueDisplayed).append(
								"', '" + OrderByClause).append(
								"', '" + WhereClause + "')");

				int no = DB.executeUpdate(sqlB.toString(), getTrxName(ctx));
				if (no > 0) {
					record_log(ctx, 1, atts.getValue("ADRefenceNameID"),
							"Reference Table", AD_Reference_ID, 0, "New", "AD_Ref_Table",
							get_IDWithColumn(ctx, "AD_Table", "TableName",
									"AD_Ref_Table"));
				} else {
					record_log(ctx, 0, atts.getValue("ADRefenceNameID"),
							"Reference Table", AD_Reference_ID, 0, "New", "AD_Ref_Table",
							get_IDWithColumn(ctx, "AD_Table", "TableName",
									"AD_Ref_Table"));
					throw new POSaveFailedException("ReferenceTable");
				}
			}
		} else {
			element.skip = true;
		}
	}

	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		int Reference_id = Env.getContextAsInt(ctx,
				X_AD_Ref_Table.COLUMNNAME_AD_Reference_ID);
		AttributesImpl atts = new AttributesImpl();
		createReferenceTableBinding(ctx, atts, Reference_id);
		document.startElement("", "", "referencetable", atts);
		document.endElement("", "", "referencetable");
	}

	private AttributesImpl createReferenceTableBinding(Properties ctx,
			AttributesImpl atts, int reference_ID) {
		atts.clear();
		if (reference_ID <= PackOut.MAX_OFFICIAL_ID)
			atts.addAttribute("", "", "AD_Reference_ID", "CDATA", Integer.toString(reference_ID));
		String name = null;
		String sql = null;
		String sql1 = "SELECT * FROM AD_Ref_Table WHERE AD_Reference_ID= "
				+ reference_ID;

		PreparedStatement pstmt = null;
		pstmt = DB.prepareStatement(sql1, getTrxName(ctx));
		try {
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				sql = "SELECT Name FROM AD_Reference WHERE AD_Reference_ID=?";
				name = DB.getSQLValueString(null, sql, reference_ID);
				atts.addAttribute("", "", "ADRefenceNameID", "CDATA", name);

				if (rs.getInt("AD_Table_ID") > 0) {
					sql = "SELECT TableName FROM AD_Table WHERE AD_Table_ID=?";
					name = DB.getSQLValueString(null, sql, rs
							.getInt("AD_Table_ID"));
					atts.addAttribute("", "", "ADTableNameID", "CDATA", name);
				} else
					atts.addAttribute("", "", "ADTableNameID", "CDATA", "");

				if (rs.getInt("AD_Display") > 0) {
					sql = "SELECT ColumnName FROM AD_Column WHERE AD_Column_ID=?";
					name = DB.getSQLValueString(null, sql, rs
							.getInt("AD_Display"));
					atts.addAttribute("", "", "ADDisplay", "CDATA", name);
				} else
					atts.addAttribute("", "", "ADDisplay", "CDATA", "");

				if (rs.getInt("AD_Key") > 0) {
					sql = "SELECT ColumnName FROM AD_Column WHERE AD_Column_ID=?";
					name = DB.getSQLValueString(null, sql, rs.getInt("AD_Key"));
					atts.addAttribute("", "", "Key", "CDATA", name);
				} else
					atts.addAttribute("", "", "Key", "CDATA", "");
				atts.addAttribute("", "", "EntityType", "CDATA", (rs
						.getString("EntityType") != null ? rs
						.getString("EntityType") : ""));
				atts
						.addAttribute("", "", "IsValueDisplayed", "CDATA",
								(rs.getString("IsValueDisplayed")
										.compareTo("Y") == 0 ? "Y" : "N"));
				atts.addAttribute("", "", "OrderByClause", "CDATA", (rs
						.getString("OrderByClause") != null ? rs
						.getString("OrderByClause") : ""));
				atts.addAttribute("", "", "isActive", "CDATA", (rs.getString(
						"isActive").compareTo("Y") == 0 ? "Y" : "N"));
				atts.addAttribute("", "", "WhereClause", "CDATA", (rs
						.getString("WhereClause") != null ? rs
						.getString("WhereClause") : ""));

			}
			rs.close();
			pstmt.close();
			pstmt = null;
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new DatabaseAccessException("Failed to export Reference Table", e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
			}
			pstmt = null;
		}
		return atts;
	}
}
