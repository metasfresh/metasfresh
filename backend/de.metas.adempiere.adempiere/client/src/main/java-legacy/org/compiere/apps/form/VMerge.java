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
package org.compiere.apps.form;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.slf4j.Logger;

import de.metas.i18n.Msg;
import de.metas.logging.LogManager;

import org.compiere.apps.ADialog;
import org.compiere.apps.ConfirmPanel;
import org.compiere.grid.ed.VLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

/**
 *	Merge Dialog.
 * 	Restriction - fails for Accounting
 *
 *	@author Jorg Janke
 *	@version $Id: VMerge.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class VMerge extends Merge implements FormPanel, ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 149783846292562740L;
	
	private CPanel panel = new CPanel();
	
	/**	FormFrame			*/
	private FormFrame 		m_frame;

	private CLabel[]	m_label = null;
	private VLookup[]	m_from = null;
	private VLookup[]	m_to = null;

	private BorderLayout mainLayout = new BorderLayout();
	private CPanel CenterPanel = new CPanel();
	private GridLayout centerLayout = new GridLayout();
	private CLabel mergeFromLabel = new CLabel();
	private CLabel mergeToLabel = new CLabel();
	private ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();

	/**
	 *	Initialize Panel
	 *  @param WindowNo window
	 *  @param frame frame
	 */
	@Override
	public void init (int WindowNo, FormFrame frame)
	{
		m_WindowNo = WindowNo;
		m_frame = frame;
		log.info( "VMerge.init - WinNo=" + m_WindowNo);
		try
		{
			preInit();
			jbInit ();
			frame.getContentPane().add(panel, BorderLayout.CENTER);
		//	frame.getContentPane().add(statusBar, BorderLayout.SOUTH);
		}
		catch (Exception ex)
		{
			log.error("", ex);
		}
	}	//	init

	/**
	 * 	Pre Init
	 */
	public void preInit()
	{
		int count = 4;			//	** Update **
		m_columnName = new String[count];
		m_label = new CLabel[count];
		m_from = new VLookup[count];
		m_to = new VLookup[count];
		
		//	** Update **
		preInit (0, 2163, DisplayType.TableDir, AD_ORG_ID);		//	C_Order.AD_Org_ID
		preInit (1, 2762, DisplayType.Search, C_BPARTNER_ID);	//	C_Order.C_BPartner_ID
		preInit (2, 971, DisplayType.Search, AD_USER_ID);		//	AD_User_Roles.AD_User_ID
		preInit (3, 2221, DisplayType.Search, M_PRODUCT_ID);	//	C_OrderLine.M_Product_ID
	}	//	preInit

	/**
	 * 	Pre Init Line
	 *	@param index index
	 *	@param AD_Column_ID id
	 *	@param displayType display type
	 *	@param ColumnName column name
	 */
	private void preInit (int index, int AD_Column_ID, int displayType, String ColumnName)
	{
		m_columnName[index] = ColumnName;
		String what = Msg.translate(Env.getCtx(), ColumnName);
		m_label[index] = new CLabel(what);
		m_from[index] = new VLookup (ColumnName, false, false, true,
			MLookupFactory.newInstance().get (Env.getCtx(), m_WindowNo, 0, AD_Column_ID, displayType));
		m_to[index] = new VLookup (ColumnName, false, false, true,
			MLookupFactory.newInstance().get (Env.getCtx(), m_WindowNo, 0, AD_Column_ID, displayType));
	}	//	preInit

	/**
	 * 	Static init
	 */
	void jbInit ()
	{
		panel.setLayout (mainLayout);
		mainLayout.setHgap (5);
		mainLayout.setVgap (5);
		//
		panel.add (confirmPanel, BorderLayout.SOUTH);
		confirmPanel.setActionListener(this);
		//
		centerLayout.setHgap (5);
		centerLayout.setVgap (5);
		centerLayout.setColumns (3);
		centerLayout.setRows (m_label.length+1);
		//
		CenterPanel.setLayout (centerLayout);
		panel.add (CenterPanel, BorderLayout.CENTER);
		CenterPanel.add (new CLabel(), null);
		CenterPanel.add (mergeFromLabel, null);
		CenterPanel.add (mergeToLabel, null);
		//
		Font heading = mergeFromLabel.getFont();
		heading = new Font(heading.getName(), Font.BOLD, heading.getSize());
		mergeFromLabel.setFont (heading);
		mergeFromLabel.setRequestFocusEnabled (false);
		mergeFromLabel.setText (Msg.getMsg(Env.getCtx(), "MergeFrom"));
		mergeToLabel.setFont (heading);
		mergeToLabel.setText (Msg.getMsg(Env.getCtx(), "MergeTo"));
		//
		for (int i = 0; i < m_label.length; i++)
		{
			CenterPanel.add (m_label[i], null);
			CenterPanel.add (m_from[i], null);
			CenterPanel.add (m_to[i], null);
		}
	}	//	jbInit

	/**
	 * 	Dispose
	 */
	@Override
	public void dispose()
	{
		if (m_frame != null)
			m_frame.dispose();
		m_frame = null;
	}	//	dispose

	/**
	 *  Action Listener
	 *  @param e event
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
		if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			dispose();
			return;
		}
		
		String columnName = null;
		String from_Info = null;
		String to_Info = null;
		int from_ID = 0;
		int to_ID = 0;
		
		//	get first merge pair
		for (int i = 0; (i < m_columnName.length && from_ID == 0 && to_ID == 0); i++)
		{
			Object value = m_from[i].getValue();
			if (value != null)
			{
				if (value instanceof Integer)
					from_ID = ((Integer)value).intValue();
				else
					continue;
				value = m_to[i].getValue();
				if (value != null && value instanceof Integer)
					to_ID = ((Integer)value).intValue();
				else
					from_ID = 0;
				if (from_ID != 0)
				{
					columnName = m_columnName[i];
					from_Info = m_from[i].getDisplay ();
					to_Info = m_to[i].getDisplay ();
				}
			}
		}	//	get first merge pair

		if (from_ID == 0 || from_ID == to_ID)
			return;

		String msg = Msg.getMsg(Env.getCtx(), "MergeFrom") + " = " + from_Info
			+ "\n" + Msg.getMsg(Env.getCtx(), "MergeTo") + " = " + to_Info;
		if (!ADialog.ask(m_WindowNo, panel, "MergeQuestion", msg))
			return;

		updateDeleteTable(columnName);

		panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		confirmPanel.getOKButton().setEnabled(false);
		//
		boolean success = merge (columnName, from_ID, to_ID);
		postMerge(columnName, to_ID);
		//
		confirmPanel.getOKButton().setEnabled(true);
		panel.setCursor(Cursor.getDefaultCursor());
		//
		if (success)
		{
			ADialog.info(m_WindowNo, panel, "MergeSuccess", 
				msg + " #" + m_totalCount);
		}
		else
		{
			ADialog.error(m_WindowNo, panel, "MergeError", 
				m_errorLog.toString());
			return;
		}
		dispose();
	}   //  actionPerformed
}	//	VMerge
