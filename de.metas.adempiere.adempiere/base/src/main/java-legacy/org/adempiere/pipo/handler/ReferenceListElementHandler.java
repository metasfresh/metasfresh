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
import org.compiere.model.X_AD_Ref_List;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class ReferenceListElementHandler extends AbstractElementHandler {

	public void startElement(Properties ctx, Element element)
			throws SAXException {
		String elementValue = element.getElementValue();
		int AD_Backup_ID = -1;
		String Object_Status = null;
		Attributes atts = element.attributes;
		log.info(elementValue + " " + atts.getValue("Name"));
		// TODO: Solve for date issues with valuefrom valueto
		String entitytype = atts.getValue("EntityType");
		if (isProcessElement(ctx, entitytype)) {
			if (element.parent != null && element.parent.skip) {
				element.skip = true;
				return;
			}
			String name = atts.getValue("Name");
			String value = atts.getValue("Value");
			int AD_Reference_ID = 0;
			if (element.parent != null && element.parent.getElementValue().equals("reference") &&
				element.parent.recordId > 0) {
				AD_Reference_ID = element.parent.recordId;
			} else {
				AD_Reference_ID = get_IDWithColumn(ctx, "AD_Reference", "Name",
						atts.getValue("ADRefenceNameID"));
			}
			
			int AD_Ref_List_ID = get_IDWithMasterAndColumn(ctx, "AD_Ref_List", "Value", value, "AD_Reference", AD_Reference_ID);
			X_AD_Ref_List m_Ref_List = new X_AD_Ref_List(ctx, AD_Ref_List_ID,
					getTrxName(ctx));
			if (AD_Ref_List_ID <= 0 && atts.getValue("AD_Ref_List_ID") != null && Integer.parseInt(atts.getValue("AD_Ref_List_ID")) <= PackOut.MAX_OFFICIAL_ID)
				m_Ref_List.setAD_Ref_List_ID(Integer.parseInt(atts.getValue("AD_Ref_List_ID")));
			if (AD_Ref_List_ID > 0) {
				AD_Backup_ID = copyRecord(ctx, "AD_Ref_List", m_Ref_List);
				Object_Status = "Update";
			} else {
				Object_Status = "New";
				AD_Backup_ID = 0;
			}
			
			m_Ref_List.setAD_Reference_ID(AD_Reference_ID);
			m_Ref_List.setDescription(getStringValue(atts,"Description"));
			m_Ref_List.setEntityType(atts.getValue("EntityType"));
			m_Ref_List.setName(atts.getValue("Name"));
			m_Ref_List.setValue(value);
			m_Ref_List.setIsActive(atts.getValue("isActive") != null ? Boolean
					.valueOf(atts.getValue("isActive")).booleanValue() : true);
			
			if (m_Ref_List.save(getTrxName(ctx)) == true) {
				record_log(ctx, 1, m_Ref_List.getName(), "Reference List",
						m_Ref_List.get_ID(), AD_Backup_ID, Object_Status,
						"AD_Ref_List", get_IDWithColumn(ctx, "AD_Table",
								"TableName", "AD_Ref_List"));
			} else {
				record_log(ctx, 0, m_Ref_List.getName(), "Reference List",
						m_Ref_List.get_ID(), AD_Backup_ID, Object_Status,
						"AD_Ref_List", get_IDWithColumn(ctx, "AD_Table",
								"TableName", "AD_Ref_List"));
				throw new POSaveFailedException("ReferenceList");
			}
		} else {
			element.skip = true;
		}
	}

	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		int AD_Ref_List_ID = Env.getContextAsInt(ctx,
				X_AD_Ref_List.COLUMNNAME_AD_Ref_List_ID);
		X_AD_Ref_List m_Ref_List = new X_AD_Ref_List(ctx, AD_Ref_List_ID,
				getTrxName(ctx));
		AttributesImpl atts = new AttributesImpl();
		createRefListBinding(atts, m_Ref_List);
		document.startElement("", "", "referencelist", atts);
		document.endElement("", "", "referencelist");
	}

	private AttributesImpl createRefListBinding(AttributesImpl atts,
			X_AD_Ref_List m_Ref_List) {
		String sql = null;
		String name = null;
		atts.clear();
		if (m_Ref_List.getAD_Ref_List_ID() <= PackOut.MAX_OFFICIAL_ID)
			atts.addAttribute("", "", "AD_Ref_List_ID", "CDATA", Integer.toString(m_Ref_List.getAD_Ref_List_ID()));
		if (m_Ref_List.getAD_Ref_List_ID() > 0) {
			sql = "SELECT Name FROM AD_Ref_List WHERE AD_Ref_List_ID=?";
			name = DB.getSQLValueString(null, sql, m_Ref_List
					.getAD_Ref_List_ID());
			if (name != null)
				atts.addAttribute("", "", "ADReflistNameID", "CDATA", name);
			else
				atts.addAttribute("", "", "ADReflistNameID", "CDATA", "");
		} else {
			atts.addAttribute("", "", "ADReflistNameID", "CDATA", "");
		}
		
		if (m_Ref_List.getAD_Reference_ID() > 0) {
			sql = "SELECT Name FROM AD_Reference WHERE AD_Reference_ID=?";
			name = DB.getSQLValueString(null, sql, m_Ref_List
					.getAD_Reference_ID());
			if (name != null)
				atts.addAttribute("", "", "ADRefenceNameID", "CDATA", name);
			else
				atts.addAttribute("", "", "ADRefenceNameID", "CDATA", "");
		} else {
			atts.addAttribute("", "", "ADRefenceNameID", "CDATA", "");
		}
		
		atts.addAttribute("", "", "Description", "CDATA", (m_Ref_List
				.getDescription() != null ? m_Ref_List.getDescription() : ""));
		atts.addAttribute("", "", "EntityType", "CDATA", (m_Ref_List
				.getEntityType() != null ? m_Ref_List.getEntityType() : ""));
		atts.addAttribute("", "", "Name", "CDATA",
				(m_Ref_List.getName() != null ? m_Ref_List.getName() : ""));
		atts.addAttribute("", "", "isActive", "CDATA",
				(m_Ref_List.isActive() == true ? "true" : "false"));
		// atts.addAttribute("","","ValidFrom","CDATA",(m_Ref_List.getValidFrom
		// ().toGMTString() != null ?
		// m_Ref_List.getValidFrom().toGMTString():""));
		// atts.addAttribute("","","ValidTo","CDATA",(m_Ref_List.getValidTo
		// ().toGMTString() != null ?
		// m_Ref_List.getValidTo().toGMTString():""));
		atts.addAttribute("", "", "Value", "CDATA",
				(m_Ref_List.getValue() != null ? m_Ref_List.getValue() : ""));
		return atts;
	}
}
