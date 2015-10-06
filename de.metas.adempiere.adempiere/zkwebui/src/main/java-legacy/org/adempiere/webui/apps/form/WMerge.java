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
package org.adempiere.webui.apps.form;

import java.util.logging.Level;

import org.adempiere.webui.apps.BusyDialog;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.editor.WEditor;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.editor.WTableDirEditor;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.IFormController;
import org.adempiere.webui.session.SessionManager;
import org.adempiere.webui.window.FDialog;
import org.compiere.apps.form.Merge;
import org.compiere.model.Lookup;
import org.compiere.model.MLookupFactory;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zk.au.out.AuEcho;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.South;

/**
 *	Merge Dialog.
 * 	Restriction - fails for Accounting
 *
 *	@author Jorg Janke
 *	@version $Id: VMerge.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class WMerge extends Merge implements IFormController, EventListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5797395051958101596L;
	
	private WMergeUI form;

	private Label[]	m_label = null;
	private WEditor[]	m_from = null;
	private WEditor[]	m_to = null;

	private Borderlayout mainLayout = new Borderlayout();
	private Panel CenterPanel = new Panel();
	private Grid centerLayout = GridFactory.newGridLayout();
	private Label mergeFromLabel = new Label();
	private Label mergeToLabel = new Label();
	private ConfirmPanel confirmPanel = new ConfirmPanel(true);
	private String m_msg;
	private boolean m_success;
	private BusyDialog progressWindow;

	private MergeRunnable runnable;

	/**
	 *	Initialize Panel
	 */
	public WMerge()
	{
		log.info( "VMerge.init - WinNo=" + m_WindowNo);
		try
		{
			preInit();
			zkInit ();
		}
		catch (Exception ex)
		{
			log.log(Level.SEVERE, "", ex);
		}
	}	//	init

	/**
	 * 	Pre Init
	 */
	private void preInit()
	{
		int count = 4;			//	** Update **
		m_columnName = new String[count];
		m_label = new Label[count];
		m_from = new WEditor[count];
		m_to = new WEditor[count];

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
		m_label[index] = new Label(what);
		Lookup lookup = MLookupFactory.get (Env.getCtx(), m_WindowNo, 0, AD_Column_ID, displayType);
		if (displayType == DisplayType.Search)
		{
			m_from[index] = new WSearchEditor(ColumnName, false, false, true, lookup);
			m_to[index] = new WSearchEditor (ColumnName, false, false, true, lookup);
		}
		else
		{
			m_from[index] = new WTableDirEditor(ColumnName, false, false, true, lookup);
			m_to[index] = new WTableDirEditor (ColumnName, false, false, true, lookup);
		}
		
	}	//	preInit

	/**
	 * 	Static init
	 * 	@throws java.lang.Exception
	 */
	void zkInit () throws Exception
	{
		form = new WMergeUI(this);		
		form.appendChild (mainLayout);
		mainLayout.setHeight("100%");
		mainLayout.setWidth("100%");
		//
		South south = new South();
		mainLayout.appendChild(south);
		south.appendChild(confirmPanel);
		confirmPanel.addActionListener(this);
		//
		Rows rows = centerLayout.newRows();
		
		//
		CenterPanel.appendChild(centerLayout);
		
		Center center = new Center();
		mainLayout.appendChild(center);
		center.appendChild(CenterPanel);
		
		Row row = rows.newRow();
		row.appendChild(new Label());
		row.appendChild(mergeFromLabel);
		row.appendChild(mergeToLabel);
		//
		mergeFromLabel.setText (Msg.getMsg(Env.getCtx(), "MergeFrom"));
		mergeFromLabel.setStyle("font-weight: bold");
		mergeToLabel.setText (Msg.getMsg(Env.getCtx(), "MergeTo"));
		mergeToLabel.setStyle("font-weight: bold");
		//
		for (int i = 0; i < m_label.length; i++)
		{
			row = rows.newRow();
			row.appendChild(m_label[i]);
			row.appendChild(m_from[i].getComponent());
			row.appendChild(m_to[i].getComponent());
		}
	}	//	jbInit

	/**
	 * 	Dispose
	 */
	public void dispose()
	{
		SessionManager.getAppDesktop().closeActiveWindow();
	}	//	dispose

	/**
	 *  Action Listener
	 *  @param e event
	 */
	public void onEvent (Event e)
	{
		if (e.getTarget().getId().equals(ConfirmPanel.A_CANCEL))
		{
			dispose();
			return;
		}
		//
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

		m_msg = Msg.getMsg(Env.getCtx(), "MergeFrom") + " = " + from_Info
			+ "\n" + Msg.getMsg(Env.getCtx(), "MergeTo") + " = " + to_Info;				
		if (!FDialog.ask(m_WindowNo, form, "MergeQuestion", m_msg))
			return;
		
		updateDeleteTable(columnName);

		progressWindow = new BusyDialog();
		progressWindow.setPage(form.getPage());
		progressWindow.doHighlighted();
		
		runnable = new MergeRunnable(columnName, from_ID, to_ID);
		Clients.response(new AuEcho(form, "runProcess", null));
	}   //  actionPerformed
	
	class MergeRunnable implements Runnable {
		private int to_ID;
		private int from_ID;
		private String columnName;
		private MergeRunnable(String columnName, int from_ID, int to_ID) {
			this.columnName = columnName;
			this.from_ID = from_ID;
			this.to_ID = to_ID;
		}
		public void run() {
			try {                    
				m_success = merge (columnName, from_ID, to_ID);
				postMerge(columnName, to_ID);
			} finally{
				Clients.showBusy(null, false);
				Clients.response(new AuEcho(form, "onAfterProcess", null));
			}
		}		
	}

	public void runProcess() 
	{
		runnable.run();
	}
	
	public void onAfterProcess() 
	{
		if (m_success)
		{
			FDialog.info (m_WindowNo, form, "MergeSuccess", 
				m_msg + " #" + m_totalCount);
		}
		else
		{
			FDialog.error(m_WindowNo, form, "MergeError", 
				m_errorLog.toString());
			return;
		}
		dispose();
	}

	public ADForm getForm() 
	{
		return form;
	}
}	//	VMerge
