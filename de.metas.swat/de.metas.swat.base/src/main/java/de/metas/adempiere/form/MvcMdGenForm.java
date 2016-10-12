package de.metas.adempiere.form;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.Set;

import org.compiere.minigrid.IMiniTable;
import org.compiere.print.MPrintFormat;

public class MvcMdGenForm {
	
	private boolean m_selectionActive = true;
	private String title;
	private int reportEngineType;
	private MPrintFormat printFormat = null;
	private String askPrintMsg;

	private IMiniTable miniTable;
	
	private final int m_WindowNo;
	
	public boolean uiLocked;
	
	public MvcMdGenForm(final int windowNo)
	{
		super();
		this.m_WindowNo = windowNo;
	}
	
	public void setMiniTable(final IMiniTable miniTable)
	{
		this.miniTable = miniTable;
	}
	/** User selection */
	private Set<Integer> selection = null;

	public boolean isSelectionActive() {
		return m_selectionActive;
	}

	public void setSelectionActive(boolean active) {
		m_selectionActive = active;
	}

	public Set<Integer> getSelection() {
		return selection;
	}

	public void setSelection(Set<Integer> selection) {
		this.selection = selection;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getReportEngineType() {
		return reportEngineType;
	}

	public void setReportEngineType(int reportEngineType) {
		this.reportEngineType = reportEngineType;
	}

	public MPrintFormat getPrintFormat() {
		return this.printFormat;
	}

	public void setPrintFormat(MPrintFormat printFormat) {
		this.printFormat = printFormat;
	}

	public String getAskPrintMsg() {
		return askPrintMsg;
	}

	public void setAskPrintMsg(String askPrintMsg) {
		this.askPrintMsg = askPrintMsg;
	}

	@SuppressWarnings("unchecked")
	public <T extends IMiniTable> T getMiniTable() {
		return (T)miniTable;
	}

	public int getWindowNo() {
		return m_WindowNo;
	}
}
