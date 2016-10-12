/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.print;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;

import org.compiere.apps.ADialog;
import org.compiere.apps.ProcessDialog;
import org.compiere.model.MQuery;
import org.compiere.model.MTable;
import org.compiere.model.PrintInfo;
import org.compiere.util.ASyncProcess;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

/**
 *	Application Report Launcher.
 *  Called from APanel; Queries available Reports for Table.
 *  If no report available, a new one is created and launched.
 *  If there is one report, it is launched.
 *  If there are more, the available reports are displayed and the selected is launched.
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: AReport.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class AReport implements ActionListener
{
	/**
	 *	Constructor
	 *
	 *  @param AD_Table_ID table
	 *  @param invoker component to display popup (optional)
	 *  @param query query
	 */
	public AReport (int AD_Table_ID, JComponent invoker, MQuery	query)
	{
		this (AD_Table_ID, invoker, query, null, 0, Env.TAB_None);
	}
	
	/**
	 *	Constructor
	 *
	 *  @param AD_Table_ID table
	 *  @param invoker component to display popup (optional)
	 *  @param query query
	 *  @param parent The invoking parent window
	 *  @param WindowNo The invoking parent window number
	 *  @param tabNo the invoking parent tab number
	 */
	public AReport (int AD_Table_ID, JComponent invoker, MQuery	query, ASyncProcess parent, int WindowNo, int tabNo)
	{
		this(AD_Table_ID, invoker, query, parent, WindowNo, tabNo, null);
	}	//	AReport

	/**
	 *	Constructor
	 *
	 *  @param AD_Table_ID table
	 *  @param invoker component to display popup (optional)
	 *  @param query query
	 *  @param parent The invoking parent window
	 *  @param whereExtended The filtering where clause
	 *  @param WindowNo The invoking parent window number
	 *  @param tabNo the invoking parent tab number
	 */
	public AReport (int AD_Table_ID, JComponent invoker, MQuery	query, ASyncProcess parent, int WindowNo, int tabNo, String whereExtended)
	{
		super();
		
		log.info("AD_Table_ID=" + AD_Table_ID + " " + query);
		
		this.m_query = query;
		this.parent = parent;
		this.WindowNo = WindowNo;
		this.tabNo = tabNo;
		this.m_whereExtended = whereExtended;

		if (!Env.getUserRolePermissions().isCanReport(AD_Table_ID))
		{
			ADialog.error(0, invoker, "AccessCannotReport", query.getTableName());
			return;
		}

		if (whereExtended != null && whereExtended.length() > 0 && m_query != null)
			m_query.addRestriction(Env.parseContext(Env.getCtx(), WindowNo, whereExtended, false));

		//	See What is there
		getPrintFormats (AD_Table_ID, invoker);
	}	//	AReport

	/**	The Query						*/
	private final MQuery	 	m_query;
	/**	The Popup						*/
	private final JPopupMenu 	m_popup = new JPopupMenu("ReportMenu");
	/**	The Option List					*/
	private final List<KeyNamePair>	m_list = new ArrayList<KeyNamePair>();
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(AReport.class);
	/** The parent window for locking/unlocking during process execution */
	private final ASyncProcess parent;
	/** The filter to apply to this report */
	private final String m_whereExtended;
	/** The parent window number */
	private final int WindowNo;
	private final int tabNo;

	/**
	 * 	Get the Print Formats for the table.
	 *  Fill the list and the popup menu
	 * 	@param AD_Table_ID table
	 *  @param invoker component to display popup (optional)
	 */
	private void getPrintFormats (int AD_Table_ID, JComponent invoker)
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
					m_popup.add(pp.toString()).addActionListener(this);
				}
			}
		}
		catch (SQLException e)
		{
			log.error(e.getLocalizedMessage(), e);
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
		else if (m_list.size() == 1 || invoker == null)
			launchReport (m_list.get(0));
		//	Multiple Formats exist - show selection
		else if (invoker.isShowing())
			m_popup.show(invoker, 0, invoker.getHeight());	//	below button
	}	//	getPrintFormats

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
	private void launchReport(final MPrintFormat pf)
	{
		int Record_ID = 0;
		if (m_query.getRestrictionCount() == 1 && m_query.getCode(0) instanceof Integer)
		{
			Record_ID = ((Integer)m_query.getCode(0)).intValue();
		}

		// It's a report using the JasperReports engine
		if(pf != null && pf.getJasperProcess_ID() > 0)
		{
			// Execute Process
			ProcessDialog.builder()
					.setAD_Process_ID(pf.getJasperProcess_ID())
					.setTableAndRecord(pf.getAD_Table_ID(), Record_ID)
					.setWindowAndTabNo(WindowNo, tabNo)
					.setWhereClause(m_whereExtended)
					.setASyncParent(parent)
					.show();
		}
		// It's a default report using the standard printing engine
		else
		{
			final PrintInfo info = new PrintInfo(
					pf.getName(),
					pf.getAD_Table_ID(),
					Record_ID);
			info.setDescription(m_query.getInfo());
			
			final ReportEngine re = new ReportEngine (Env.getCtx(), pf, m_query, info);
			re.setWhereExtended(m_whereExtended);
			re.setWindowNo(WindowNo);
			ReportCtl.preview(re);
		}
	}	//	launchReport

	/**
	 * 	Action Listener
	 * 	@param e event
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		m_popup.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		String cmd = e.getActionCommand();
		for (int i = 0; i < m_list.size(); i++)
		{
			KeyNamePair pp = m_list.get(i);
			if (cmd.equals(pp.getName()))
			{
				launchReport (pp);
				return;
			}
		}
	}	//	actionPerformed

	
	/**************************************************************************
	 * 	Get AD_Table_ID for Table Name
	 * 	@param tableName table name
	 * 	@return AD_Table_ID or 0
	 */
	public static int getAD_Table_ID (String tableName)
	{
		return MTable.getTable_ID(tableName);
	}	//	getAD_Table_ID

}	//	AReport

