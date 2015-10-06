/**
 * 
 */
package de.metas.commission.dashboard;

/*
 * #%L
 * de.metas.commission.zkwebui
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


import java.util.HashMap;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.webui.apps.ProcessModalDialog;
import org.adempiere.webui.component.GridPanel;
import org.adempiere.webui.dashboard.DashboardPanel;
import org.adempiere.webui.session.SessionManager;
import org.compiere.model.GridTab;
import org.compiere.model.GridWindow;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.MQuery;
import org.compiere.model.MTable;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;

/**
 * @author teo_sarca
 *
 */
public class DPCommisionView extends DashboardPanel implements EventListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -118973999367179570L;

	public static final String TableName = "P_AdvCommissionDocs";
	
	private final int windowNo;
	private GridTab gridTab;

	public DPCommisionView()
	{
		super();
		this.windowNo = SessionManager.getAppDesktop().registerWindow(this);
		init();
	}
	
	private void init()
	{
		final Toolbar toolbar = new Toolbar();
		this.appendChild(toolbar);
		final Toolbarbutton butPrint = new Toolbarbutton(null, "/images/Print16.png");
		butPrint.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event event) throws Exception {
				printDocument();
			}
		});
		toolbar.appendChild(butPrint);
		//
		final MQuery query = new MQuery(TableName);
		query.addRestriction("AD_User_ID", MQuery.EQUAL, getAD_User_ID());
		gridTab = createGridTab(windowNo, TableName, query);
		
		GridPanel gridPanel = new GridPanel(windowNo);
		gridPanel.refresh(gridTab);
		gridPanel.showGrid(true);
		gridPanel.setWidth("100%");
		gridPanel.setHeight("300px");
		gridPanel.setStyle("position:relative; width:100%; height:300px;");
		// Reset Column Sizes
		for (Object c : gridPanel.getListbox().getColumns().getChildren())
		{
			((org.zkoss.zul.Column)c).setWidth(null);
		}
		this.appendChild(gridPanel);
	}

	@Override
	public void onEvent(Event event) throws Exception
	{
	}
	
	public int getAD_User_ID()
	{
		return Env.getAD_User_ID(Env.getCtx());
	}
	
	public int getWindowNo()
	{
		return 0;
	}
	
	public static GridTab createGridTab(int windowNo, String tableName, MQuery query)
	{
		// TODO: refactor and move createGridTab to a general util class
		final MTable table = MTable.get(Env.getCtx(), tableName);
		final int AD_Table_ID = table.getAD_Table_ID();
		final int AD_Window_ID = table.getAD_Window_ID();
		final GridWindow gridWindow = GridWindow.get(Env.getCtx(), windowNo, AD_Window_ID, false);
		if (gridWindow == null)
		{
			throw new AdempiereException("Can not load window for table "+tableName+" (AD_Window_ID="+AD_Window_ID+")."
					+" Please check permissions.");
		}
		for (int i = 0; i < gridWindow.getTabCount(); i++)
		{
			GridTab gridTab = gridWindow.getTab(i);
			if (gridTab.getAD_Table_ID() != AD_Table_ID || gridTab.getTabLevel() != 0)
			{
				continue;
			}
			if (!gridTab.isLoadComplete())
			{
				gridWindow.initTab(i);
			}
			gridTab.setQuery(query);
			gridTab.query(false);
			
			return gridTab;
		}
		return null;
	}
	
	private static final HashMap<Integer, Integer> s_printProcessCache = new HashMap<Integer, Integer>(10);
	private int getPrintProcess_ID(int AD_Table_ID)
	{
		Integer process_id = s_printProcessCache.get(AD_Table_ID);
		if (process_id != null && process_id > 0)
			return process_id;
		//
		int window_id = -1;
		if (MTable.getTable_ID(I_C_Invoice.Table_Name) == AD_Table_ID)
			window_id = 540035; // TODO: hardcoded
		if (window_id <= 0)
			window_id = MTable.get(Env.getCtx(), AD_Table_ID).getAD_Window_ID();
		process_id = DB.getSQLValueEx(null,
				"SELECT AD_Process_ID FROM AD_Tab WHERE AD_Table_ID=? AND AD_Window_ID=? AND TabLevel=0 ORDER BY AD_Tab_ID",
				AD_Table_ID, window_id);
		if (process_id > 0)
		{
			s_printProcessCache.put(AD_Table_ID, process_id);
		}
		return process_id;
	}
	
	private static int getValueAsInt(GridTab gridTab, String columnName)
	{
		Object value = gridTab.getValue(columnName);
		if (value instanceof Number)
		{
			return ((Number)value).intValue();
		}
		else if (value instanceof String)
		{
			try
			{
				return Integer.valueOf(value.toString());
			}
			catch (NumberFormatException e)
			{
				return -1;
			}
		}
		else
		{
			return -1;
		}
	}
	
	private void runProcess(int AD_Table_ID, int Record_ID)
	{
		final int process_id = getPrintProcess_ID(AD_Table_ID);
		if (process_id <= 0)
			throw new AdempiereException("@NotFound@ @AD_Process_ID@");
		//
		ProcessModalDialog dialog = new ProcessModalDialog(
				null, getWindowNo(),
				process_id, AD_Table_ID, Record_ID, true);
		if (dialog.isValid())
		{
			dialog.runProcess();
			// metas: tsa: following approach sometimes produces a lock - see US134
//			dialog.setPosition("center");
//			try
//			{
//				dialog.setPage(this.getPage());
//				dialog.doModal();
//			}
//			catch (InterruptedException e)
//			{
//			}
		}
	}

	private void printDocument()
	{
		int AD_Table_ID = getValueAsInt(gridTab, "AD_Table_ID");
		int Record_ID = getValueAsInt(gridTab, "Record_ID");
		if (AD_Table_ID <= 0 || Record_ID <= 0)
			return;
		
		runProcess(AD_Table_ID, Record_ID);
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T getComponent(AbstractComponent c, Class<T> type)
	{
		if (type.isInstance(c))
			return (T)c;
		for (Object cc : c.getChildren())
		{
			if (type.isInstance(cc))
				return (T)cc;
			else if (cc instanceof AbstractComponent)
				return getComponent((AbstractComponent)cc, type);
		}
		return null;
	}
}
