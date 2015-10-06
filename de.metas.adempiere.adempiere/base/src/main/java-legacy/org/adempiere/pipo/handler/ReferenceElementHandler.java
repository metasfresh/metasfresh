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
import org.compiere.model.X_AD_Ref_List;
import org.compiere.model.X_AD_Ref_Table;
import org.compiere.model.X_AD_Reference;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class ReferenceElementHandler extends AbstractElementHandler {

	private ReferenceListElementHandler listHandler = new ReferenceListElementHandler();
	private ReferenceTableElementHandler tableHandler = new ReferenceTableElementHandler();
	
	private List<Integer> references = new ArrayList<Integer>();

	public void startElement(Properties ctx, Element element)
			throws SAXException {
		String elementValue = element.getElementValue();
		int AD_Backup_ID = -1;
		String Object_Status = null;

		Attributes atts = element.attributes;
		log.info(elementValue + " " + atts.getValue("name"));

		String entitytype = atts.getValue("EntityType");
		String name = atts.getValue("name");

		if (isProcessElement(ctx, entitytype)) {
			int id = get_ID(ctx, "AD_Reference", name);

			X_AD_Reference m_Reference = new X_AD_Reference(ctx, id,
					getTrxName(ctx));
			if (id <= 0 && atts.getValue("AD_Reference_ID") != null && Integer.parseInt(atts.getValue("AD_Reference_ID")) <= PackOut.MAX_OFFICIAL_ID)
				m_Reference.setAD_Reference_ID(Integer.parseInt(atts.getValue("AD_Reference_ID")));
			if (id > 0) {
				AD_Backup_ID = copyRecord(ctx, "AD_Reference", m_Reference);
				Object_Status = "Update";
				if (references.contains(id)) {
					element.skip = true;
					return;
				}
			} else {
				Object_Status = "New";
				AD_Backup_ID = 0;
			}

			m_Reference.setDescription(getStringValue(atts,"Description"));
			m_Reference.setEntityType(atts.getValue("EntityType"));
			m_Reference.setHelp(getStringValue(atts,"Help"));
			m_Reference.setIsActive(atts.getValue("isActive") != null ? Boolean
					.valueOf(atts.getValue("isActive")).booleanValue() : true);
			m_Reference.setName(atts.getValue("name"));

			// m_Reference.setVFormat(atts.getValue("VFormat"));
			m_Reference.setValidationType(atts.getValue("ValidationType"));
			if (m_Reference.save(getTrxName(ctx)) == true) {
				record_log(ctx, 1, m_Reference.getName(), "Reference",
						m_Reference.get_ID(), AD_Backup_ID, Object_Status,
						"AD_Reference", get_IDWithColumn(ctx, "AD_Table",
								"TableName", "AD_Reference"));
				references.add(m_Reference.getAD_Reference_ID());
				element.recordId = m_Reference.getAD_Reference_ID();
			} else {
				record_log(ctx, 0, m_Reference.getName(), "Reference",
						m_Reference.get_ID(), AD_Backup_ID, Object_Status,
						"AD_Reference", get_IDWithColumn(ctx, "AD_Table",
								"TableName", "AD_Reference"));
				throw new POSaveFailedException("Reference");
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
				X_AD_Reference.COLUMNNAME_AD_Reference_ID);
		
		if (references.contains(Reference_id))
			return;
		
		references.add(Reference_id);
		AttributesImpl atts = new AttributesImpl();
		String sql = "SELECT * FROM AD_Reference WHERE AD_Reference_ID= "
				+ Reference_id;

		PreparedStatement pstmt = null;
		pstmt = DB.prepareStatement(sql, getTrxName(ctx));

		try {

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				X_AD_Reference m_Reference = new X_AD_Reference(ctx, rs
						.getInt("AD_Reference_ID"), null);
				createReferenceBinding(atts, m_Reference);
				document.startElement("", "", "reference", atts);

				if (m_Reference.getValidationType().compareTo("L") == 0) {
					String sql1 = "SELECT * FROM AD_Ref_List WHERE AD_Reference_ID= " + Reference_id
						+ " ORDER BY Value, AD_Ref_List_ID";

					PreparedStatement pstmt1 = null;
					pstmt1 = DB.prepareStatement(sql1, getTrxName(ctx));

					try {

						ResultSet rs1 = pstmt1.executeQuery();

						while (rs1.next()) {
							createReferenceList(ctx, document, rs1
									.getInt("AD_REF_LIST_ID"));
						}
						rs1.close();
						pstmt1.close();
						pstmt1 = null;
					}

					catch (Exception e) {
						log.log(Level.SEVERE, e.getLocalizedMessage(), e);
						if (e instanceof SAXException)
							throw (SAXException) e;
						else if (e instanceof SQLException)
							throw new DatabaseAccessException("Failed to export Reference.", e);
						else if (e instanceof RuntimeException)
							throw (RuntimeException) e;
						else
							throw new RuntimeException("Failed to export Reference.", e);
					} finally {
						try {
							if (pstmt1 != null)
								pstmt1.close();
						} catch (Exception e) {
						}
						pstmt1 = null;
					}

				} else if (m_Reference.getValidationType().compareTo("T") == 0) {
					String sql1 = "SELECT AD_Reference_ID FROM AD_Ref_Table WHERE AD_Reference_ID = "
							+ Reference_id;
					PreparedStatement pstmt1 = null;
					pstmt1 = DB.prepareStatement(sql1, getTrxName(ctx));

					try {

						ResultSet rs1 = pstmt1.executeQuery();

						while (rs1.next()) {
							createReferenceTable(ctx, document, Reference_id);
						}
						rs1.close();
						pstmt1.close();
						pstmt1 = null;
					}

					catch (Exception e) {
						log.log(Level.SEVERE, "getRef_Table", e);
					}

					finally {
						try {
							if (pstmt1 != null)
								pstmt1.close();
						} catch (Exception e) {
						}
						pstmt1 = null;
					}
				}
				document.endElement("", "", "reference");
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}

		catch (Exception e) {
			log.log(Level.SEVERE, "getRefence", e);
		}

		finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
			}
			pstmt = null;
		}

	}

	private void createReferenceTable(Properties ctx, TransformerHandler document,
			int reference_id) throws SAXException {
		Env.setContext(ctx, X_AD_Ref_Table.COLUMNNAME_AD_Reference_ID, reference_id);
		tableHandler.create(ctx, document);
		ctx.remove(X_AD_Ref_Table.COLUMNNAME_AD_Reference_ID);
	}

	private void createReferenceList(Properties ctx,
			TransformerHandler document, int AD_Ref_List_ID)
			throws SAXException {
		Env.setContext(ctx, X_AD_Ref_List.COLUMNNAME_AD_Ref_List_ID,
				AD_Ref_List_ID);
		listHandler.create(ctx, document);
		ctx.remove(X_AD_Ref_List.COLUMNNAME_AD_Ref_List_ID);
	}

	private AttributesImpl createReferenceBinding(AttributesImpl atts,
			X_AD_Reference m_Reference) {
		String sql = null;
		String name = null;
		atts.clear();
		if (m_Reference.getAD_Reference_ID() <= PackOut.MAX_OFFICIAL_ID)
			atts.addAttribute("", "", "AD_Reference_ID", "CDATA", Integer.toString(m_Reference.getAD_Reference_ID()));
		if (m_Reference.getAD_Reference_ID() > 0) {
			sql = "SELECT Name FROM AD_Reference WHERE AD_Reference_ID=?";
			name = DB.getSQLValueString(null, sql, m_Reference
					.getAD_Reference_ID());
			atts.addAttribute("", "", "name", "CDATA", name);
		} else
			atts.addAttribute("", "", "name", "CDATA", "");
		atts.addAttribute("", "", "Description", "CDATA", (m_Reference
				.getDescription() != null ? m_Reference.getDescription() : ""));
		atts.addAttribute("", "", "EntityType", "CDATA", (m_Reference
				.getEntityType() != null ? m_Reference.getEntityType() : ""));
		atts.addAttribute("", "", "Help", "CDATA",
				(m_Reference.getHelp() != null ? m_Reference.getHelp() : ""));
		atts.addAttribute("", "", "Name", "CDATA",
				(m_Reference.getName() != null ? m_Reference.getName() : ""));
		atts.addAttribute("", "", "isActive", "CDATA",
				(m_Reference.isActive() == true ? "true" : "false"));
		atts.addAttribute("", "", "VFormat", "CDATA",
				(m_Reference.getVFormat() != null ? m_Reference.getVFormat()
						: ""));
		atts.addAttribute("", "", "ValidationType", "CDATA", (m_Reference
				.getValidationType() != null ? m_Reference.getValidationType()
				: ""));
		return atts;
	}
}
