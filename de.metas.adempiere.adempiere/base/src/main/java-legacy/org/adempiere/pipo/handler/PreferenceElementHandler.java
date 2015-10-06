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
import org.compiere.model.MPreference;
import org.compiere.model.X_AD_Preference;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class PreferenceElementHandler extends AbstractElementHandler {

	public void startElement(Properties ctx, Element element)
			throws SAXException {
		String elementValue = element.getElementValue();
		log.info(elementValue);

		// TODO Add User_ID
		Attributes atts = element.attributes;
		int windowid = get_ID(ctx, "AD_Window", atts.getValue("ADWindowNameID"));
		if (windowid <= 0) {
			element.defer = true;
			return;
		}

		StringBuffer sqlB = new StringBuffer(
				"select AD_Preference_ID from AD_Preference where ").append(
				" Attribute = '" + atts.getValue("Attribute") + "'").append(
				" and AD_Window_ID = ?");
		int id = DB.getSQLValue(getTrxName(ctx), sqlB.toString(), windowid);
		MPreference m_Preference = new MPreference(ctx, id, getTrxName(ctx));
		int AD_Backup_ID = -1;
		String Object_Status = null;
		if (id <= 0 && atts.getValue("AD_Preference_ID") != null && Integer.parseInt(atts.getValue("AD_Preference_ID")) <= PackOut.MAX_OFFICIAL_ID)
			m_Preference.setAD_Preference_ID(Integer.parseInt(atts.getValue("AD_Preference_ID")));
		if (id > 0) {
			AD_Backup_ID = copyRecord(ctx, "AD_Preference", m_Preference);
			Object_Status = "Update";
		} else {
			Object_Status = "New";
			AD_Backup_ID = 0;
		}
		sqlB = null;
		m_Preference.setAD_Window_ID(windowid);
		m_Preference.setAttribute(atts.getValue("Attribute"));
		m_Preference.setValue(atts.getValue("Value"));
		if (m_Preference.save(getTrxName(ctx)) == true) {
			record_log(ctx, 1, m_Preference.getAttribute(), "Preference",
					m_Preference.get_ID(), AD_Backup_ID, Object_Status,
					"AD_Preference", get_IDWithColumn(ctx, "AD_Table",
							"TableName", "AD_Preference"));
		} else {
			record_log(ctx, 0, m_Preference.getAttribute(), "Preference",
					m_Preference.get_ID(), AD_Backup_ID, Object_Status,
					"AD_Preference", get_IDWithColumn(ctx, "AD_Table",
							"TableName", "AD_Preference"));
			throw new POSaveFailedException("Failed to save Preference");
		}
	}

	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		int AD_Preference_ID = Env.getContextAsInt(ctx,
				X_AD_Preference.COLUMNNAME_AD_Preference_ID);
		X_AD_Preference m_Preference = new X_AD_Preference(ctx,
				AD_Preference_ID, getTrxName(ctx));
		AttributesImpl atts = new AttributesImpl();
		createPreferenceBinding(atts, m_Preference);
		document.startElement("", "", "preference", atts);
		document.endElement("", "", "preference");
	}

	private AttributesImpl createPreferenceBinding(AttributesImpl atts,
			X_AD_Preference m_Preference) {
		String sql = null;
		String name = null;
		atts.clear();
		if (m_Preference.getAD_Preference_ID() <= PackOut.MAX_OFFICIAL_ID)
	        atts.addAttribute("","","AD_Preference_ID","CDATA",Integer.toString(m_Preference.getAD_Preference_ID()));
		sql = "SELECT Name FROM AD_Window WHERE AD_Window_ID=?";
		name = DB.getSQLValueString(null, sql, m_Preference.getAD_Window_ID());
		atts.addAttribute("", "", "ADWindowNameID", "CDATA", name);
		sql = "SELECT Name FROM AD_User WHERE AD_User_ID=?";
		name = DB.getSQLValueString(null, sql, m_Preference.getAD_User_ID());
		atts.addAttribute("", "", "ADUserNameID", "CDATA", name);
		atts.addAttribute("", "", "Attribute", "CDATA", m_Preference
				.getAttribute());
		atts.addAttribute("", "", "Value", "CDATA", m_Preference.getValue());
		return atts;
	}
}
