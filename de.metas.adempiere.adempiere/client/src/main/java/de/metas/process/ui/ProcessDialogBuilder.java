package de.metas.process.ui;

import java.util.function.Supplier;

import javax.swing.JFrame;

import org.compiere.apps.AEnv;
import org.compiere.model.GridTab;
import org.compiere.model.X_AD_Process;
import org.compiere.util.Env;
import org.compiere.util.Ini;

import de.metas.process.IProcessExecutionListener;

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
	private int AD_Process_ID;
	private boolean isSOTrx;
	private int windowNo = Env.WINDOW_MAIN;
	private int tabNo = Env.TAB_None;
	private String whereClause;
	private int AD_Table_ID;
	private int Record_ID;

	private boolean skipResultsPanel = false;
	private IProcessExecutionListener processExecutionListener;
	private Boolean _printPreview;
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

	int getWindowNo()
	{
		return windowNo;
	}

	int getTabNo()
	{
		return tabNo;
	}

	public ProcessDialogBuilder setAD_Process_ID(final int AD_Process_ID)
	{
		this.AD_Process_ID = AD_Process_ID;
		return this;
	}

	int getAD_Process_ID()
	{
		return AD_Process_ID;
	}

	public ProcessDialogBuilder setIsSOTrx(final boolean isSOTrx)
	{
		this.isSOTrx = isSOTrx;
		return this;
	}

	boolean isSOTrx()
	{
		return isSOTrx;
	}

	public ProcessDialogBuilder setWhereClause(final String whereClause)
	{
		this.whereClause = whereClause;
		return this;
	}

	String getWhereClause()
	{
		return whereClause;
	}

	public ProcessDialogBuilder setTableAndRecord(final int AD_Table_ID, final int Record_ID)
	{
		this.AD_Table_ID = AD_Table_ID;
		this.Record_ID = Record_ID;
		return this;
	}

	int getAD_Table_ID()
	{
		return AD_Table_ID;
	}

	int getRecord_ID()
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

	boolean isSkipResultsPanel()
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
		setTableAndRecord(gridTab.getAD_Table_ID(), gridTab.getRecord_ID());
		setWhereClause(gridTab.getTableModel().getSelectWhereClauseFinal());
		skipResultsPanel();

		return this;
	}

	public ProcessDialogBuilder setProcessExecutionListener(final IProcessExecutionListener processExecutionListener)
	{
		this.processExecutionListener = processExecutionListener;
		return this;
	}

	IProcessExecutionListener getProcessExecutionListener()
	{
		return processExecutionListener;
	}

	public ProcessDialogBuilder setPrintPreview(final boolean printPreview)
	{
		this._printPreview = printPreview;
		return this;
	}

	boolean isPrintPreview()
	{
		if(_printPreview != null)
		{
			return _printPreview;
		}
		
		return Ini.isPropertyBool(Ini.P_PRINTPREVIEW);
	}
}