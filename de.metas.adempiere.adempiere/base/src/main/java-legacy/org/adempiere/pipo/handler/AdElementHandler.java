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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.transform.sax.TransformerHandler;

import org.adempiere.pipo.AbstractElementHandler;
import org.adempiere.pipo.AttributeFiller;
import org.adempiere.pipo.Element;
import org.adempiere.pipo.PackOut;
import org.adempiere.pipo.PoFiller;
import org.adempiere.pipo.exception.POSaveFailedException;
import org.compiere.model.X_AD_Element;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class AdElementHandler extends AbstractElementHandler {

	private List<Integer> processedElements = new ArrayList<Integer>();
	
	private final String AD_ELEMENT = "AD_Element";
	

	public void startElement(Properties ctx, Element element)
			throws SAXException {
		String elementValue = element.getElementValue();
		int AD_Backup_ID = -1;
		String Object_Status = null;

		Attributes atts = element.attributes;
		log.info(elementValue + " " + atts.getValue("ColumnName"));

		String entitytype = atts.getValue("EntityType");
		String ColumnName = atts.getValue("ColumnName");

		if (isProcessElement(ctx, entitytype)) {
			
			int id = get_IDWithColumn(ctx, X_AD_Element.Table_Name, X_AD_Element.COLUMNNAME_ColumnName, ColumnName);

			X_AD_Element m_AdElement = new X_AD_Element(ctx, id,
					getTrxName(ctx));
			if (id <= 0 && atts.getValue("AD_Element_ID") != null && Integer.parseInt(atts.getValue("AD_Element_ID")) <= PackOut.MAX_OFFICIAL_ID)
				m_AdElement.setAD_Element_ID(Integer.parseInt(atts.getValue("AD_Element_ID")));
			if (id > 0) {
				AD_Backup_ID = copyRecord(ctx, AD_ELEMENT, m_AdElement);
				Object_Status = "Update";
				if (processedElements.contains(id)) {
					element.skip = true;
					return;
				}
			} else {
				Object_Status = "New";
				AD_Backup_ID = 0;
			}

			PoFiller pf = new PoFiller(m_AdElement, atts);
			
			pf.setBoolean("IsActive");
			
			pf.setString(X_AD_Element.COLUMNNAME_ColumnName);
			pf.setString(X_AD_Element.COLUMNNAME_Description);
			pf.setString(X_AD_Element.COLUMNNAME_EntityType);
			pf.setString(X_AD_Element.COLUMNNAME_Help);
			pf.setString(X_AD_Element.COLUMNNAME_Name);
			pf.setString(X_AD_Element.COLUMNNAME_PrintName);
			
			pf.setString(X_AD_Element.COLUMNNAME_PO_Description);
			pf.setString(X_AD_Element.COLUMNNAME_PO_Name);
			pf.setString(X_AD_Element.COLUMNNAME_PO_Help);
			pf.setString(X_AD_Element.COLUMNNAME_PO_PrintName);
			
			
			if (m_AdElement.save(getTrxName(ctx)) == true) {
				record_log(ctx, 1, m_AdElement.getName(), "Element",
						m_AdElement.get_ID(), AD_Backup_ID, Object_Status,
						AD_ELEMENT, get_IDWithColumn(ctx, "AD_Table",
								"TableName", AD_ELEMENT));
				
				element.recordId = m_AdElement.getAD_Element_ID();
				
				processedElements.add(m_AdElement.getAD_Element_ID());
				
			} else {
				record_log(ctx, 0, m_AdElement.getName(), "Element",
						m_AdElement.get_ID(), AD_Backup_ID, Object_Status,
						AD_ELEMENT, get_IDWithColumn(ctx, "AD_Table",
								"TableName", AD_ELEMENT));
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
		
		
		int adElement_id = Env.getContextAsInt(ctx,
				X_AD_Element.COLUMNNAME_AD_Element_ID);
		
		if (processedElements.contains(adElement_id))
			return;
		
		processedElements.add(adElement_id);
		
		X_AD_Element m_AdElement = new X_AD_Element(ctx, adElement_id, null);
		
		AttributesImpl atts = new AttributesImpl();
		createAdElementBinding(atts, m_AdElement);
		
		document.startElement("", "", "element", atts);
		
		PackOut packOut = (PackOut)ctx.get("PackOutProcess");

		packOut.createTranslations(X_AD_Element.Table_Name,
					m_AdElement.get_ID(), document);
		
		document.endElement("", "", "element");
	}

	
	private AttributesImpl createAdElementBinding(AttributesImpl atts,
			X_AD_Element m_AdElement) {
		
		AttributeFiller filler = new AttributeFiller(atts, m_AdElement);
		if (m_AdElement.getAD_Element_ID() <= PackOut.MAX_OFFICIAL_ID)
			filler.add(X_AD_Element.COLUMNNAME_AD_Element_ID);
		
		filler.add("IsActive");
		
		filler.add(X_AD_Element.COLUMNNAME_ColumnName);
		filler.add(X_AD_Element.COLUMNNAME_Description);
		filler.add(X_AD_Element.COLUMNNAME_EntityType);
		filler.add(X_AD_Element.COLUMNNAME_Help);
		filler.add(X_AD_Element.COLUMNNAME_Name);
		filler.add(X_AD_Element.COLUMNNAME_PrintName);
		
		filler.add(X_AD_Element.COLUMNNAME_PO_Description);
		filler.add(X_AD_Element.COLUMNNAME_PO_Name);
		filler.add(X_AD_Element.COLUMNNAME_PO_Help);
		filler.add(X_AD_Element.COLUMNNAME_PO_PrintName);
		
		return atts;
	}
}
