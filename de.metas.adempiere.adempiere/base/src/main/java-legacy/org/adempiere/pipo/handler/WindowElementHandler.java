/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.               *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *                                                                            *
 * Copyright (C) 2005 Robert Klein. robeklein@hotmail.com                     *
 * Contributor(s): Low Heng Sin hengsin@avantz.com                            *
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
import org.compiere.model.MWindow;
import org.compiere.model.X_AD_Preference;
import org.compiere.model.X_AD_Tab;
import org.compiere.model.X_AD_Window;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class WindowElementHandler extends AbstractElementHandler {

	private TabElementHandler tabHandler = new TabElementHandler();
	private PreferenceElementHandler preferenceHandler = new PreferenceElementHandler();
	
	private List<Integer> windows = new ArrayList<Integer>();

	public void startElement(Properties ctx, Element element)
			throws SAXException {
		// Check namespace.
		String elementValue = element.getElementValue();
		Attributes atts = element.attributes;
		log.info(elementValue + " " + atts.getValue("Name"));
		String entitytype = atts.getValue("EntityType");
		if (isProcessElement(ctx, entitytype)) {
			String name = atts.getValue("Name");
			int id = get_ID(ctx, "AD_Window", name);
			if (id > 0 && windows.contains(id)) {
				return;
			}
			MWindow m_Window = new MWindow(ctx, id, getTrxName(ctx));
			if (id <= 0 && atts.getValue("AD_Window_ID") != null && Integer.parseInt(atts.getValue("AD_Window_ID")) <= PackOut.MAX_OFFICIAL_ID)
				m_Window.setAD_Window_ID(Integer.parseInt(atts.getValue("AD_Window_ID")));
			String Object_Status = null;
			int AD_Backup_ID = -1;
			if (id > 0) {
				AD_Backup_ID = copyRecord(ctx, "AD_Window", m_Window);
				Object_Status = "Update";
			} else {
				Object_Status = "New";
				AD_Backup_ID = 0;
			}
			m_Window.setName(name);

			name = atts.getValue("ADImageNameID");
			if (name != null && name.trim().length() > 0) {
				id = get_IDWithColumn(ctx, "AD_Image", "Name", name);
				//TODO: export and import of ad_image
				/*
				if (id <= 0) {
					element.defer = true;
					return;
				}*/
				if (id > 0)
					m_Window.setAD_Image_ID(id);
			}

			name = atts.getValue("ADColorNameID");
			if (name != null && name.trim().length() > 0) {
				id = get_IDWithColumn(ctx, "AD_Color", "Name", name);
				//TODO: export and import of ad_color
				/*
				if (id <= 0) {
					element.defer = true;
					return;
				}*/
				if (id > 0)
					m_Window.setAD_Color_ID(id);
			}

			m_Window.setDescription(getStringValue(atts,"Description"));
			m_Window.setEntityType(atts.getValue("EntityType"));
			m_Window.setHelp(getStringValue(atts,"Help"));
			m_Window.setIsActive(atts.getValue("isActive") != null ? Boolean
					.valueOf(atts.getValue("isActive")).booleanValue() : true);
			m_Window.setIsBetaFunctionality(Boolean.valueOf(
					atts.getValue("isBetaFunctionality")).booleanValue());
			m_Window.setIsDefault(Boolean.valueOf(atts.getValue("isDefault"))
					.booleanValue());
			m_Window.setIsSOTrx(Boolean.valueOf(atts.getValue("isSOTrx"))
					.booleanValue());
			m_Window.setName(atts.getValue("Name"));
			m_Window.setProcessing(false);
			if (!Util.isEmpty(atts.getValue("WinHeight"), true))
				m_Window.setWinWidth(getValueInt(atts, "WinWidth", 0));
			if (!Util.isEmpty(atts.getValue("WinHeight"), true))
				m_Window.setWinHeight(getValueInt(atts, "WinHeight", 0));
			m_Window.setWindowType(atts.getValue("WindowType"));
			if (m_Window.save(getTrxName(ctx)) == true) {
				record_log(ctx, 1, m_Window.getName(), "Window", m_Window
						.get_ID(), AD_Backup_ID, Object_Status, "AD_Window",
						get_IDWithColumn(ctx, "AD_Table", "TableName",
								"AD_Window"));
				element.recordId = m_Window.getAD_Window_ID();
				windows.add(m_Window.getAD_Window_ID());
			} else {
				record_log(ctx, 0, m_Window.getName(), "Window", m_Window
						.get_ID(), AD_Backup_ID, Object_Status, "AD_Window",
						get_IDWithColumn(ctx, "AD_Table", "TableName",
								"AD_Window"));
				throw new POSaveFailedException("Window");
			}
		} else {
			element.skip = true;
		}
	}

	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		int AD_Window_ID = Env.getContextAsInt(ctx, "AD_Window_ID");
		PackOut packOut = (PackOut) ctx.get("PackOutProcess");

		X_AD_Window m_Window = new X_AD_Window(ctx, AD_Window_ID, null);
		AttributesImpl atts = new AttributesImpl();
		createWindowBinding(atts, m_Window);
		document.startElement("", "", "window", atts);
		// Tab Tag
		String sql = "SELECT * FROM AD_TAB WHERE AD_WINDOW_ID = "
				+ AD_Window_ID
				+" ORDER BY "+X_AD_Tab.COLUMNNAME_SeqNo+","+X_AD_Tab.COLUMNNAME_AD_Tab_ID
				;
		PreparedStatement pstmt = null;
		pstmt = DB.prepareStatement(sql, getTrxName(ctx));
		try {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				packOut.createTable(rs.getInt("AD_Table_ID"), document);
				createTab(ctx, document, rs.getInt("AD_Tab_ID"));
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			if (e instanceof SAXException)
				throw (SAXException) e;
			else if (e instanceof SQLException)
				throw new DatabaseAccessException("Failed to export window.", e);
			else if (e instanceof RuntimeException)
				throw (RuntimeException) e;
			else
				throw new RuntimeException("Failed to export window.", e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
			}
			pstmt = null;
		}
		
		//TODO: export of ad_image and ad_color use

		// Loop tags.
		document.endElement("", "", "window");

		// Preference Tag
		sql = "SELECT * FROM AD_PREFERENCE WHERE AD_WINDOW_ID = " + AD_Window_ID
		+" ORDER BY "+X_AD_Preference.COLUMNNAME_AD_Preference_ID;
		pstmt = null;
		pstmt = DB.prepareStatement(sql, getTrxName(ctx));
		try {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				createPreference(ctx, document, rs.getInt("AD_Preference_ID"));
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			if (e instanceof SAXException)
				throw (SAXException) e;
			else if (e instanceof SQLException)
				throw new DatabaseAccessException("Failed to export window preference.", e);
			else if (e instanceof RuntimeException)
				throw (RuntimeException) e;
			else
				throw new RuntimeException("Failed to export window preference.", e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
			}
			pstmt = null;
		}
	}

	private void createPreference(Properties ctx, TransformerHandler document,
			int AD_Preference_ID) throws SAXException {
		Env.setContext(ctx, X_AD_Preference.COLUMNNAME_AD_Preference_ID,
				AD_Preference_ID);
		preferenceHandler.create(ctx, document);
		ctx.remove(X_AD_Preference.COLUMNNAME_AD_Preference_ID);
	}

	private void createTab(Properties ctx, TransformerHandler document,
			int AD_Tab_ID) throws SAXException {
		Env.setContext(ctx, X_AD_Tab.COLUMNNAME_AD_Tab_ID, AD_Tab_ID);
		tabHandler.create(ctx, document);
		ctx.remove(X_AD_Tab.COLUMNNAME_AD_Tab_ID);
	}

	private AttributesImpl createWindowBinding(AttributesImpl atts,
			X_AD_Window m_Window) {
		atts.clear();
		if (m_Window.getAD_Window_ID() <= PackOut.MAX_OFFICIAL_ID)
			atts.addAttribute("", "", "AD_Window_ID", "CDATA", Integer.toString(m_Window.getAD_Window_ID()));
		String sql = "SELECT Name FROM AD_Window WHERE AD_Window_ID=?";
		String name = DB.getSQLValueString(null, sql, m_Window
				.getAD_Window_ID());
		atts.addAttribute("", "", "ADWindowNameID", "CDATA", name);
		if (m_Window.getAD_Image_ID() > 0) {
			sql = "SELECT Name FROM AD_Image WHERE AD_Image_ID=?";
			name = DB.getSQLValueString(null, sql, m_Window.getAD_Image_ID());
			if (name != null)
				atts.addAttribute("", "", "ADImageNameID", "CDATA", name);
			else
				atts.addAttribute("", "", "ADImageNameID", "CDATA", "");
		}
		else {
			atts.addAttribute("", "", "ADImageNameID", "CDATA", "");
		}
		
		if (m_Window.getAD_Color_ID() > 0) {
			sql = "SELECT Name FROM AD_Color WHERE AD_Color_ID=?";
			name = DB.getSQLValueString(null, sql, m_Window.getAD_Color_ID());
			if (name != null)
				atts.addAttribute("", "", "ADColorNameID", "CDATA", name);
			else
				atts.addAttribute("", "", "ADColorNameID", "CDATA", "");
		} else {
			atts.addAttribute("", "", "ADColorNameID", "CDATA", "");
		}
		
		atts.addAttribute("", "", "Description", "CDATA", (m_Window
				.getDescription() != null ? m_Window.getDescription() : ""));
		atts.addAttribute("", "", "EntityType", "CDATA", (m_Window
				.getEntityType() != null ? m_Window.getEntityType() : ""));
		atts.addAttribute("", "", "Help", "CDATA",
				(m_Window.getHelp() != null ? m_Window.getHelp() : ""));
		atts.addAttribute("", "", "isBetaFunctionality", "CDATA", (m_Window
				.isBetaFunctionality() == true ? "true" : "false"));
		atts.addAttribute("", "", "isDefault", "CDATA",
				(m_Window.isDefault() == true ? "true" : "false"));
		atts.addAttribute("", "", "isSOTrx", "CDATA",
				(m_Window.isSOTrx() == true ? "true" : "false"));
		atts.addAttribute("", "", "isActive", "CDATA",
				(m_Window.isActive() == true ? "true" : "false"));
		atts.addAttribute("", "", "Name", "CDATA",
				(m_Window.getName() != null ? m_Window.getName() : ""));
		atts.addAttribute("", "", "isProcessing", "CDATA", (m_Window
				.isProcessing() == true ? "true" : "false"));
		atts.addAttribute("", "", "WinHeight", "CDATA", (m_Window
				.getWinHeight() > 0 ? "" + m_Window.getWinHeight() : ""));
		atts.addAttribute("", "", "WinWidth", "CDATA", (m_Window
				.getWinWidth() > 0 ? "" + m_Window.getWinWidth() : ""));
		atts.addAttribute("", "", "WindowType", "CDATA", (m_Window
				.getWindowType() != null ? m_Window.getWindowType() : ""));
		return atts;
	}
	
	protected int getValueInt(Attributes atts, String name, int defaultValue)
	{
		String value = atts.getValue(name);
		if (Util.isEmpty(value, true))
			return defaultValue;
		 int i = Integer.parseInt(value.trim());
		 return i;
	}
}
