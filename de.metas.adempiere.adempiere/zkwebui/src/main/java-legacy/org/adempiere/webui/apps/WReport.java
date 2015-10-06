/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2007 Adempiere, Inc. All Rights Reserved.                *
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
 * Copyright (C) 2007 Low Heng Sin hengsin@avantz.com
 * _____________________________________________
 *****************************************************************************/
package org.adempiere.webui.apps;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.sql.RowSet;

import org.adempiere.webui.window.FDialog;
import org.compiere.model.MQuery;
import org.compiere.model.MTable;
import org.compiere.model.PrintInfo;
import org.compiere.print.MPrintFormat;
import org.compiere.print.ReportCtl;
import org.compiere.print.ReportEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;

/**
 * Base on org.compiere.print.AReport
 * @author Low Heng Sin
 *
 */
public class WReport implements EventListener {

	/**
	 *	Constructor
	 *
	 *  @param AD_Table_ID table
	 *  @param invoker component to display popup (optional)
	 *  @param query query
	 */
	public WReport (int AD_Table_ID, MQuery	query)
	{
		new WReport(AD_Table_ID, query, null, 0);
	}
	
	/**
	 *	Constructor
	 *
	 *  @param AD_Table_ID table
	 *  @param invoker component to display popup (optional)
	 *  @param query query
	 *  @param parent The invoking parent window
	 *  @param WindowNo The invoking parent window number
	 */
	public WReport (int AD_Table_ID, MQuery	query, Component parent, 
			int WindowNo)
	{
		log.config("AD_Table_ID=" + AD_Table_ID + " " + query);
		if (!Env.getUserRolePermissions().isCanReport(AD_Table_ID))
		{
			FDialog.error(0, "AccessCannotReport", query.getTableName());
			return;
		}

		m_query = query;
		this.parent = parent;
		this.WindowNo = WindowNo;

		//	See What is there
		getPrintFormats (AD_Table_ID);
	}	//	AReport

	/**	The Query						*/
	private MQuery	 	m_query;
	private Menupopup 	m_popup;
	/**	The Option List					*/
	private ArrayList<KeyNamePair>	m_list = new ArrayList<KeyNamePair>();
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(WReport.class);
	/** The parent window for locking/unlocking during process execution */
	Component parent;
	/** The parent window number */
	int WindowNo;

	/**
	 * 	Get the Print Formats for the table.
	 *  Fill the list and the popup menu
	 * 	@param AD_Table_ID table
	 *  @param invoker component to display popup (optional)
	 */
	private void getPrintFormats (int AD_Table_ID)
	{
		int AD_Client_ID = Env.getAD_Client_ID(Env.getCtx());
		RowSet rowSet = MPrintFormat.getAccessiblePrintFormats(AD_Table_ID, -1, null);
		KeyNamePair pp = null;
		try
		{
			while (rowSet.next())
			{
				pp = new KeyNamePair (rowSet.getInt(1), rowSet.getString(2));
				if (rowSet.getInt(3) == AD_Client_ID)
				{
					m_list.add(pp);
				}
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}

		//	No Format exists - create it
		if (m_list.size() == 0)
		{
			if (pp == null)
				createNewFormat (AD_Table_ID);		//	calls launch
			else
				copyFormat(pp.getKey(), AD_Client_ID);
		}
		//	One Format exists or no invoker - show it
		else if (m_list.size() == 1)
			launchReport ((KeyNamePair)m_list.get(0));
		//	Multiple Formats exist - show selection
		else 
			showPopup();	//	below button
	}	//	getPrintFormats

	private void showPopup() {
		m_popup = new Menupopup();		
		for(int i = 0; i < m_list.size(); i++)
		{
			KeyNamePair pp = (KeyNamePair) m_list.get(i);
			Menuitem menuitem = new Menuitem(pp.getName());
			menuitem.setValue(i + "");
			menuitem.addEventListener(Events.ON_CLICK, this);
			m_popup.appendChild(menuitem);
		}
		m_popup.setPage(parent.getPage());
		m_popup.open(parent);
	}

	/**
	 * 	Create and Launch new Format for table
	 * 	@param AD_Table_ID table
	 */
	private void createNewFormat (int AD_Table_ID)
	{
		MPrintFormat pf = MPrintFormat.createFromTable(Env.getCtx(), AD_Table_ID);
		launchReport (pf);
	}	//	createNewFormat

	/**
	 * 	Copy existing Format
	 * 	@param AD_PrintFormat_ID print format
	 * 	@param To_Client_ID to client
	 */
	private void copyFormat (int AD_PrintFormat_ID, int To_Client_ID)
	{
		MPrintFormat pf = MPrintFormat.copyToClient(Env.getCtx(), AD_PrintFormat_ID, To_Client_ID);
		launchReport (pf);
	}	//	copyFormatFromClient

	/**
	 * 	Launch Report
	 * 	@param pp Key=AD_PrintFormat_ID
	 */
	private void launchReport (KeyNamePair pp)
	{
		MPrintFormat pf = MPrintFormat.get(Env.getCtx(), pp.getKey(), false);
		launchReport (pf);
	}	//	launchReport

	/**
	 * 	Launch Report
	 * 	@param pf print format
	 */
	private void launchReport (MPrintFormat pf)
	{
		int Record_ID = 0;
		if (m_query.getRestrictionCount()==1 && m_query.getCode(0) instanceof Integer)
			Record_ID = ((Integer)m_query.getCode(0)).intValue();
		PrintInfo info = new PrintInfo(
			pf.getName(),
			pf.getAD_Table_ID(),
			Record_ID);
		info.setDescription(m_query.getInfo());
		
		if(pf != null && pf.getJasperProcess_ID() > 0)
		{
			// It's a report using the JasperReports engine
			ProcessInfo pi = new ProcessInfo ("", pf.getJasperProcess_ID(), pf.getAD_Table_ID(), Record_ID);
			
			//	Execute Process
			WProcessCtl.process(null, WindowNo, pi, null);
		}
		else
		{
			// It's a default report using the standard printing engine
			ReportEngine re = new ReportEngine (Env.getCtx(), pf, m_query, info);
			ReportCtl.preview(re);
		}
	}	//	launchReport

	/**************************************************************************
	 * 	Get AD_Table_ID for Table Name
	 * 	@param tableName table name
	 * 	@return AD_Table_ID or 0
	 */
	public static int getAD_Table_ID (String tableName)
	{
		return MTable.getTable_ID(tableName);
	}	//	getAD_Table_ID

	public void onEvent(Event event) {
		if(event.getTarget() instanceof Menuitem)
		{
			Menuitem mi = (Menuitem) event.getTarget();
			launchReport(m_list.get(Integer.parseInt(mi.getValue().toString())));
		}
	}		
}
