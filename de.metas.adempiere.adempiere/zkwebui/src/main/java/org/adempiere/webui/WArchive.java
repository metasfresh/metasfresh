package org.adempiere.webui;

/*
 * #%L
 * de.metas.adempiere.adempiere.zkwebui
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.adempiere.webui.apps.form.WArchiveViewer;
import org.adempiere.webui.component.Window;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.session.SessionManager;
import org.compiere.model.MBPartner;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;

/**
 *	Archive Button Consequences.
 *	Popup Menu
 *	
 *  @author Jorg Janke
 *  @version $Id: AArchive.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public class WArchive implements EventListener
{
	/**
	 * 	Constructor
	 *	@param invoker button
	 *	@param AD_Table_ID table
	 *	@param Record_ID record
	 */
	public WArchive (Component invoker, int AD_Table_ID, int Record_ID)
	{
		log.info("AD_Table_ID=" + AD_Table_ID + ", Record_ID=" + Record_ID);
		m_AD_Table_ID = AD_Table_ID;
		m_Record_ID = Record_ID;
		getArchives(invoker);
	}

	/**	The Table						*/
	private int			m_AD_Table_ID;
	/** The Record						*/
	private int			m_Record_ID;
	
	/**	The Popup						*/
	private Menupopup 	m_popup = new Menupopup();
	private Menuitem 	m_reports = null;
	private Menuitem 	m_reportsAll = null;
	private Menuitem 	m_documents = null;	
//	private JPopupMenu 	m_popup = new JPopupMenu("ArchiveMenu");
	
	/** Where Clause					*/
	StringBuffer 		m_where = null;
	
	/**	Logger	*/
	private static Logger	log	= LogManager.getLogger(WArchive.class);
	
	/**
	 * 	Display Request Options - New/Existing.
	 * 	@param invoker button
	 */
	private void getArchives(Component invoker)
	{
		int reportCount = 0;
		int documentCount = 0;
		
		m_where = new StringBuffer();
		m_where.append("(AD_Table_ID=").append(m_AD_Table_ID)
			.append(" AND Record_ID=").append(m_Record_ID)
			.append(")");
		//	Get all for BP
		if (m_AD_Table_ID == MBPartner.Table_ID)
			m_where.append(" OR C_BPartner_ID=").append(m_Record_ID);
		//
		StringBuffer sql = new StringBuffer("SELECT IsReport, COUNT(*) FROM AD_Archive ")
			.append("WHERE (AD_Table_ID=? AND Record_ID=?) ");
		if (m_AD_Table_ID == MBPartner.Table_ID)
			sql.append(" OR C_BPartner_ID=?");
		sql.append(" GROUP BY IsReport"); 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql.toString(), null);
			pstmt.setInt(1, m_AD_Table_ID);
			pstmt.setInt(2, m_Record_ID);
			if (m_AD_Table_ID == MBPartner.Table_ID)
				pstmt.setInt(3, m_Record_ID);
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				if ("Y".equals(rs.getString(1)))
					reportCount += rs.getInt(2);
				else
					documentCount += rs.getInt(2);
			}
		}
		catch (Exception e)
		{
			log.error(sql.toString(), e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		//
		if (documentCount > 0)
		{
			m_documents = new Menuitem(Msg.getMsg(Env.getCtx(), "ArchivedDocuments") 
					+ " (" + documentCount + ")");
			m_documents.addEventListener(Events.ON_CLICK, this);
			m_popup.appendChild(m_documents);
		}
		if (reportCount > 0)
		{
			m_reports = new Menuitem(Msg.getMsg(Env.getCtx(), "ArchivedReports") 
					+ " (" + reportCount + ")");
			m_reports.addEventListener(Events.ON_CLICK, this);
			m_popup.appendChild(m_reports);
		}
		//	All Reports
		String sql1 = "SELECT COUNT(*) FROM AD_Archive WHERE AD_Table_ID=? AND IsReport='Y'";
		int allReports = DB.getSQLValue(null, sql1, m_AD_Table_ID); 
		if (allReports > 0)
		{
			m_reportsAll = new Menuitem(Msg.getMsg(Env.getCtx(), "ArchivedReportsAll") 
					+ " (" + reportCount + ")");
			m_reportsAll.addEventListener(Events.ON_CLICK, this);
			m_popup.appendChild(m_reportsAll);
		}
		
		if (documentCount == 0 && reportCount == 0 && allReports == 0)
			m_popup.appendChild(new Menuitem(Msg.getMsg(Env.getCtx(), "ArchivedNone")));
		//
			
		m_popup.setPage(invoker.getPage());
		m_popup.open(invoker);
	}	//	getZoomTargets
	
	/**
	 * 	Listner
	 *	@param e event
	 */
	public void onEvent(Event e) throws Exception 
	{
		if (e.getTarget() instanceof Menuitem) 
		{
			int AD_Form_ID = 118;	//	ArchiveViewer
			ADForm form = ADForm.openForm(AD_Form_ID);
			
			WArchiveViewer av = (WArchiveViewer) form.getICustomForm();
			if (e.getTarget() == m_documents)
				av.query(false, m_AD_Table_ID, m_Record_ID);
			else if (e.getTarget() == m_reports)
				av.query(true, m_AD_Table_ID, m_Record_ID);
			else	//	all Reports
				av.query(true, m_AD_Table_ID, 0);

			form.setAttribute(Window.MODE_KEY, Window.MODE_EMBEDDED);
			SessionManager.getAppDesktop().showWindow(form);
		}
	}
}
