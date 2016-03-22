/**
 * 
 */
package de.metas.web.component;

/*
 * #%L
 * de.metas.swat.zkwebui
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


import org.adempiere.webui.component.CWindowToolbar;
import org.adempiere.webui.event.ToolbarListener;
import org.adempiere.webui.panel.ADTabpanel;
import org.adempiere.webui.window.FDialog;
import org.compiere.model.DataStatusEvent;
import org.compiere.model.DataStatusListener;
import org.compiere.model.GridTab;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import de.metas.logging.MetasfreshLastError;

/**
 * Toolbar for CRUD operations. At this moment we support following: Ignore, Save, Delete, Refresh
 * 
 * @author tsa
 * 
 */
public class ADTabpanelCRUDToolbarListener implements ToolbarListener, DataStatusListener
{
	private final ADTabpanel tabpanel;
	private final GridTab gridTab;
	private final CWindowToolbar toolbar;
	private ADTabpanel parentTabpanel = null;

	public ADTabpanelCRUDToolbarListener(final ADTabpanel tabpanel)
	{
		this(null, tabpanel);
	}

	public ADTabpanelCRUDToolbarListener(final CWindowToolbar toolbar, final ADTabpanel tabpanel)
	{
		this.tabpanel = tabpanel;
		gridTab = tabpanel.getGridTab();
		if (toolbar == null)
		{
			this.toolbar = createToolbar(tabpanel);
		}
		else
		{
			this.toolbar = toolbar;
		}
		updateToolbarStatus();
		//
		this.toolbar.addListener(this);
		gridTab.addDataStatusListener(this);
	}

	public ADTabpanel getParentTabpanel()
	{
		return parentTabpanel;
	}

	public void setParentTabpanel(final ADTabpanel tab)
	{
		parentTabpanel = tab;
	}

	private static CWindowToolbar createToolbar(final ADTabpanel tabpanel)
	{
		final CWindowToolbar toolbar = new CWindowToolbar();
		toolbar.setVisibleAll(false);
		toolbar.setVisible("Ignore", true);
		toolbar.setVisible("Save", true);
		toolbar.setVisible("Delete", true);
		// toolbar.setVisible("DeleteSelection", true);
		toolbar.setVisible("Refresh", true);
		return toolbar;
	}

	public CWindowToolbar getToolbar()
	{
		return toolbar;
	}

	@Override
	public void onZoomAcross()
	{
	}

	@Override
	public void onToggle()
	{
	}

	@Override
	public void onSave()
	{
		onSave(true);
	}

	private boolean onSave(final boolean onSaveEvent)
	{
		final boolean retValue = gridTab.dataSave(true);

		if (!retValue)
		{
			showLastError();
			return false;
		}
		else if (!onSaveEvent) // need manual refresh
		{
			gridTab.setCurrentRow(gridTab.getCurrentRow());
		}
		tabpanel.dynamicDisplay(0);
		tabpanel.afterSave(onSaveEvent);
		return true;
	}

	@Override
	public void onRequests()
	{
	}

	@Override
	public void onReport()
	{
	}

	@Override
	public void onRefresh()
	{
		onRefresh(true);
	}

	private void onRefresh(final boolean fireEvent)
	{
		onSave(false);
		gridTab.dataRefreshAll(); // (fireEvent)
		tabpanel.dynamicDisplay(0);
		// focusToActivePanel();

	}

	@Override
	public void onProductInfo()
	{
	}

	@Override
	public void onPrint()
	{
	}

	@Override
	public void onPrevious()
	{
	}

	@Override
	public void onParentRecord()
	{
	}

	@Override
	public void onNext()
	{
	}

	@Override
	public void onNew()
	{
	}

	@Override
	public void onLast()
	{
	}

	@Override
	public void onIgnore()
	{
		gridTab.dataIgnore();
		gridTab.dataRefresh(); // false);
		tabpanel.dynamicDisplay(0);
		toolbar.enableIgnore(false);
	}

	@Override
	public void onHistoryRecords()
	{
	}

	@Override
	public void onHelp()
	{
	}

	@Override
	public void onFirst()
	{
	}

	@Override
	public void onFind()
	{
	}

	@Override
	public void onDetailRecord()
	{
	}

	@Override
	public void onDelete()
	{
		if (gridTab.isReadOnly())
		{
			return;
		}

		if (FDialog.ask(gridTab.getWindowNo(), null, "DeleteRecord?"))
		{
			// error will be catch in the dataStatusChanged event
			gridTab.dataDelete();
		}
		tabpanel.dynamicDisplay(0);
		// focusToActivePanel();
	}

	@Override
	public void onAttachment()
	{
	}

	@Override
	public void onArchive()
	{
	}

	@Override
	public void onActiveWorkflows()
	{
	}

	@Override
	public void onExport()
	{
		// nothing
	}

	public void updateToolbarStatus()
	{
		updateToolbarStatus(null);
	}

	private void updateToolbarStatus(final DataStatusEvent e)
	{
		if (!gridTab.isOpen())
		{
			return;
		}
		if (e != null && e.getCurrentRow() != gridTab.getCurrentRow())
		{
			return;
		}

		// see org.adempiere.webui.panel.AbstractADWindowPanel.dataStatusChanged(DataStatusEvent)

		final boolean eChanged = e == null ? false : e.isChanged();
		final boolean eInserting = e == null ? false : e.isInserting();
		final int eTotalRows = e == null ? gridTab.getRowCount() : e.getTotalRows();

		// update Navigation
		// boolean firstRow = e.isFirstRow();
		// boolean lastRow = e.isLastRow();
		// toolbar.enableFirstNavigation(!firstRow && !gridTab.isSortTab());
		// toolbar.enableLastNavigation(!lastRow && !gridTab.isSortTab());

		// update Change
		final boolean changed = eChanged || eInserting;
		// boolChanges = changed;
		boolean readOnly = gridTab.isReadOnly();
		boolean insertRecord = !readOnly;

		if (insertRecord)
		{
			insertRecord = gridTab.isInsertRecord();
		}
		// toolbar.enabledNew(!changed && insertRecord && !curTab.isSortTab());
		toolbar.enabledNew(insertRecord && !gridTab.isSortTab());
		// toolbar.enableCopy(!changed && insertRecord && !gridTab.isSortTab());
		toolbar.enableRefresh(!changed);
		toolbar.enableDelete(!changed && !readOnly && !gridTab.isSortTab());
		toolbar.enableDeleteSelection(!changed && !readOnly && !gridTab.isSortTab());

		//
		if (readOnly && gridTab.isAlwaysUpdateField())
		{
			readOnly = false;
		}
		toolbar.enableIgnore(changed && !readOnly);
		toolbar.enableSave(changed && !readOnly);

		//
		// No Rows
		if (eTotalRows == 0 && insertRecord)
		{
			toolbar.enabledNew(true);
			toolbar.enableDelete(false);
			toolbar.enableDeleteSelection(false);
		}
	}

	@Override
	public void dataStatusChanged(final DataStatusEvent e)
	{
		updateToolbarStatus(e);
	}

	private void showLastError()
	{
		final String msg = MetasfreshLastError.retrieveErrorString(null);
		if (msg != null)
		{
			showErrorMessage(Msg.getMsg(Env.getCtx(), msg));
		}
		// other error will be catch in the dataStatusChanged event
	}

	private void showErrorMessage(final String message)
	{
		FDialog.error(gridTab.getWindowNo(), null, "Error", message);
	}
}
