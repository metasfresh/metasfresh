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


import java.util.List;
import java.util.Properties;

import org.adempiere.apps.toolbar.AProcessModel;
import org.adempiere.webui.apps.ProcessModalDialog;
import org.adempiere.webui.component.CWindowToolbar;
import org.adempiere.webui.component.ToolBarButton;
import org.adempiere.webui.panel.AbstractADWindowPanel;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_Process;
import org.compiere.util.Env;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;

public class WProcess
{
	private final AProcessModel model = new AProcessModel();

	private final AbstractADWindowPanel parent;
	private ToolBarButton button;

	public static void createAction(AbstractADWindowPanel parent)
	{
		new WProcess(parent);
	}

	public WProcess(AbstractADWindowPanel parent)
	{
		this.parent = parent;
		initAction();
	}

	public Properties getCtx()
	{
		return Env.getCtx();
	}

	public GridTab getGridTab()
	{
		return this.parent.getActiveGridTab();
	}

	private void initAction()
	{
		CWindowToolbar toolbar = parent.getToolbar();
		button = toolbar.createButton("Process", "Process", "", null);
		button.setDisabled(false);
		button.addEventListener(Events.ON_CLICK, new EventListener()
		{
			@Override
			public void onEvent(Event event) throws Exception
			{
				showPopup();
			}
		});
	}

	private Menupopup getPopupMenu()
	{
		Menupopup popup = new Menupopup();

		List<I_AD_Process> processes = model.fetchProcesses(getCtx(), getGridTab());
		if (processes.size() == 0)
			return null;

		for (I_AD_Process process : processes)
		{
			Menuitem mi = createProcessMenuItem(process);
			popup.appendChild(mi);
		}

		return popup;
	}

	public void showPopup()
	{
		Menupopup popup = getPopupMenu();
		if (popup == null)
			return;

		popup.setPage(button.getPage());
		popup.open(button);
	}

	private Menuitem createProcessMenuItem(final I_AD_Process process)
	{
		final Menuitem mi = new org.zkoss.zul.Menuitem(model.getDisplayName(process));
		mi.setTooltiptext(model.getDescription(process));
		mi.addEventListener(Events.ON_CLICK, new EventListener()
		{
			@Override
			public void onEvent(Event event) throws Exception
			{
				startProcess(process);
			}
		});
		return mi;
	}

	private void startProcess(I_AD_Process process)
	{
		GridTab gridTab = getGridTab();
		if (gridTab == null)
		{
			return;
		}
		int tableId = gridTab.getAD_Table_ID();
		int recordId = gridTab.getRecord_ID();
		int windowNo = parent.getWindowNo();

		ProcessModalDialog dialog = new ProcessModalDialog(
				parent, // aProcess,
				windowNo,
				process.getAD_Process_ID(),
				tableId, recordId,
				true, // autoStart
				gridTab.getTableModel().getSelectWhereClauseFinal()
		);
		if (dialog.isValid())
		{
			dialog.setPosition("center");
			try
			{
				dialog.setPage(parent.getComponent().getPage());
				dialog.doModal();
			}
			catch (InterruptedException e)
			{
			}
		}
	}
}
