package org.compiere.apps;

import java.util.function.Supplier;

import javax.swing.JFrame;

import org.adempiere.ui.api.IGridTabSummaryInfo;
import org.adempiere.util.Check;
import org.compiere.model.GridTab;
import org.compiere.model.X_AD_Process;
import org.compiere.util.ASyncProcess;
import org.compiere.util.Env;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
 * %%
 * Copyright (C) 2016 metas GmbH
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

public final class ProcessDialogBuilder
{
	private ProcessPanelWindow window;
	private int AD_Process_ID;
	private boolean isSOTrx;
	private int windowNo = Env.WINDOW_MAIN;
	private int tabNo = Env.TAB_None;
	private IGridTabSummaryInfo gridTabSummaryInfo;
	private String whereClause;
	private int AD_Table_ID;
	private int Record_ID;

	private boolean skipResultsPanel = false;
	private ASyncProcess asyncParent;
	private boolean printPreview;
	/**
	 * @see X_AD_Process#SHOWHELP_AD_Reference_ID
	 */
	private String showHelp = null;
	private Boolean allowProcessReRun = null;

	ProcessDialogBuilder()
	{
		super();
	}

	public ProcessDialog showModal(final JFrame parentFrame)
	{
		buildPrepare();

		return new ProcessModalDialog(parentFrame, this);
	}

	public ProcessDialog show()
	{
		buildPrepare();

		final ProcessFrame frame = new ProcessFrame(this);
		if (!frame.isDisposed())
		{
			AEnv.addToWindowManager(frame);
		}

		return frame;
	}

	private void buildPrepare()
	{
	}

	ProcessPanel buildPanel()
	{
		return new ProcessPanel(this);
	}

	ProcessDialogBuilder setWindow(final ProcessPanelWindow window)
	{
		this.window = window;
		return this;
	}

	ProcessPanelWindow getWindow()
	{
		Check.assumeNotNull(window, "Parameter window is not null");
		return window;
	}

	public ProcessDialogBuilder setWindowAndTabNo(final int windowNo, final int tabNo)
	{
		this.windowNo = windowNo;
		this.tabNo = tabNo;
		return this;
	}

	public ProcessDialogBuilder setWindowAndTabNo(final int windowNo)
	{
		this.windowNo = windowNo;
		tabNo = Env.TAB_None;
		return this;
	}

	public int getWindowNo()
	{
		return windowNo;
	}

	public int getTabNo()
	{
		return tabNo;
	}

	public ProcessDialogBuilder setAD_Process_ID(final int AD_Process_ID)
	{
		this.AD_Process_ID = AD_Process_ID;
		return this;
	}

	public int getAD_Process_ID()
	{
		return AD_Process_ID;
	}

	public ProcessDialogBuilder setIsSOTrx(final boolean isSOTrx)
	{
		this.isSOTrx = isSOTrx;
		return this;
	}

	public boolean isSOTrx()
	{
		return isSOTrx;
	}

	public ProcessDialogBuilder setGridTabSummaryInfo(final IGridTabSummaryInfo gridTabSummaryInfo)
	{
		this.gridTabSummaryInfo = gridTabSummaryInfo;
		return this;
	}

	public IGridTabSummaryInfo getGridTabSummaryInfo()
	{
		return gridTabSummaryInfo;
	}

	public ProcessDialogBuilder setWhereClause(final String whereClause)
	{
		this.whereClause = whereClause;
		return this;
	}

	public String getWhereClause()
	{
		return whereClause;
	}

	public ProcessDialogBuilder setTableAndRecord(final int AD_Table_ID, final int Record_ID)
	{
		this.AD_Table_ID = AD_Table_ID;
		this.Record_ID = Record_ID;
		return this;
	}

	public int getAD_Table_ID()
	{
		return AD_Table_ID;
	}

	public int getRecord_ID()
	{
		return Record_ID;
	}

	/**
	 * @param showHelp
	 * @see X_AD_Process#SHOWHELP_AD_Reference_ID
	 */
	public ProcessDialogBuilder setShowHelp(final String showHelp)
	{
		this.showHelp = showHelp;
		return this;
	}

	String getShowHelp(final Supplier<String> defaultValueSupplier)
	{
		return showHelp != null ? showHelp : defaultValueSupplier.get(); 
	}

	ProcessDialogBuilder skipResultsPanel()
	{
		skipResultsPanel = true;
		return this;
	}

	public boolean isSkipResultsPanel()
	{
		return skipResultsPanel;
	}
	
	public ProcessDialogBuilder setAllowProcessReRun(final Boolean allowProcessReRun)
	{
		this.allowProcessReRun = allowProcessReRun;
		return this;
	}
	
	boolean isAllowProcessReRun(final Supplier<Boolean> defaultValueSupplier)
	{
		return allowProcessReRun != null ? allowProcessReRun : defaultValueSupplier.get();
	}

	public ProcessDialogBuilder setFromGridTab(GridTab gridTab)
	{
		final int windowNo = gridTab.getWindowNo();
		final int tabNo = gridTab.getTabNo();

		setWindowAndTabNo(windowNo, tabNo);
		setIsSOTrx(Env.isSOTrx(gridTab.getCtx(), windowNo));
		setGridTabSummaryInfo(gridTab.getLastSummaryInfo());
		setTableAndRecord(gridTab.getAD_Table_ID(), gridTab.getRecord_ID());
		setWhereClause(gridTab.getTableModel().getSelectWhereClauseFinal());
		skipResultsPanel();

		return this;
	}

	public ProcessDialogBuilder setASyncParent(ASyncProcess asyncParent)
	{
		this.asyncParent = asyncParent;
		return this;
	}

	public ASyncProcess getAsyncParent()
	{
		return asyncParent;
	}

	public ProcessDialogBuilder setPrintPreview(final boolean printPreview)
	{
		this.printPreview = printPreview;
		return this;
	}

	public boolean isPrintPreview()
	{
		return printPreview;
	}
}