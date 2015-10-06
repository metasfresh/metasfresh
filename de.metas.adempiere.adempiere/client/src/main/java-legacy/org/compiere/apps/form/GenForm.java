/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.apps.form;

import java.util.ArrayList;

import org.compiere.minigrid.IMiniTable;
import org.compiere.print.MPrintFormat;
import org.compiere.process.ProcessInfo;
import org.compiere.util.Trx;

/**
 * Generate custom form base class
 * 
 */
public abstract class GenForm
{
	private boolean m_selectionActive = true;
	private String title;
	private int reportEngineType;
	private MPrintFormat printFormat = null;
	private String askPrintMsg;
	
	private Trx trx;
	private ProcessInfo pi;
	
	/** User selection */
	private ArrayList<Integer> selection = null;
	
	public void dynInit() throws Exception
	{
		
	}
	
	public abstract void configureMiniTable(IMiniTable miniTable);

	public abstract void saveSelection(IMiniTable miniTable);
	
	public void validate()
	{
		
	}

	public String generate()
	{
		return null;
	}
	
	public void executeQuery()
	{
		
	}

	public Trx getTrx() {
		return trx;
	}

	public void setTrx(Trx trx) {
		this.trx = trx;
	}

	public ProcessInfo getProcessInfo() {
		return pi;
	}

	public void setProcessInfo(ProcessInfo pi) {
		this.pi = pi;
	}

	public boolean isSelectionActive() {
		return m_selectionActive;
	}

	public void setSelectionActive(boolean active) {
		m_selectionActive = active;
	}

	public ArrayList<Integer> getSelection() {
		return selection;
	}

	public void setSelection(ArrayList<Integer> selection) {
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
}
