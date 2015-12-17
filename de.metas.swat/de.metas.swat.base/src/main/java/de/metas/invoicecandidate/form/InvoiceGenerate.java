/**
 * 
 */
package de.metas.invoicecandidate.form;

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


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.compiere.apps.IStatusBar;
import org.compiere.apps.form.GenForm;
import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.GridWindow;
import org.compiere.model.Lookup;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.print.ReportEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.compiere.util.Util;

import de.metas.adempiere.ui.MiniTableUtil;
import de.metas.invoicecandidate.api.InvoiceCandidate_Constants;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

/**
 * @author tsa
 * 
 */
public abstract class InvoiceGenerate extends GenForm
{
	public static final String MSG_INVOICE_GENERATE_NO_CANDIDATES_SELECTED_0P = "InvoiceGenerate_No_Candidates_Selected";

	protected static final transient CLogger log = InvoiceCandidate_Constants.getLogger();

	private final int InvoiceCandidate_Window_ID = 540093; // HARDCODED
	// see de.metas.adempiere.process.C_Invoice_Candidate_Generate class
	public static final int C_Invoice_Candidate_Generate_Process_ID = 540166; // HARDCODED

	protected int m_WindowNo;

	private String sqlInvoiceCandidate;

	@Override
	public void dynInit() throws Exception
	{
		setTitle("InvGenerateInfo");
		setReportEngineType(ReportEngine.INVOICE);
		setAskPrintMsg("PrintInvoices");
	}

	@Override
	public void configureMiniTable(IMiniTable miniTable)
	{
		final boolean multiSelection = true;

		final GridTab gridTab = getInvoiceCandidateGridTab();
		final ColumnInfo[] layout = MiniTableUtil.createColumnInfo(gridTab);
		final String tableName = gridTab.getTableName();
		final String sqlFrom = tableName;
		final StringBuilder sqlWhere = new StringBuilder();
		
		if (!Util.isEmpty(gridTab.getWhereClause(), true))
		{
			sqlWhere.append(gridTab.getWhereClause());
		}
		if (sqlWhere.length() == 0)
		{
			sqlWhere.append("1=1");
		}
		sqlWhere.append(" AND ").append(I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice).append("<>0");
		sqlWhere.append(" AND ").append(I_C_Invoice_Candidate.COLUMNNAME_IsToClear).append("='N'");
		sqlWhere.append(" AND ").append(I_C_Invoice_Candidate.COLUMNNAME_Processed).append("='N'");
		sqlWhere.append(" AND ").append(I_C_Invoice_Candidate.COLUMNNAME_IsActive).append("='Y'");
		
		sqlInvoiceCandidate = miniTable.prepareTable(layout, sqlFrom, sqlWhere.toString(), multiSelection, tableName);
		miniTable.autoSize();
	}

	public void executeQuery(IMiniTable miniTable, String whereClause, List<Object> params)
	{
		StringBuffer sql = new StringBuffer(sqlInvoiceCandidate);
		if (!Util.isEmpty(whereClause, true))
			sql.append(" AND (").append(whereClause).append(")");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			DB.setParameters(pstmt, params);
			rs = pstmt.executeQuery();
			miniTable.loadTable(rs);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		int rows = miniTable.getRowCount();
		for (int i = 0; i < rows; i++)
		{
			IDColumn id = (IDColumn)miniTable.getValueAt(i, 0); // ID in column 0
			// log.fine( "Row=" + i + " - " + id);
			if (id != null && !id.isSelected())
				id.setSelected(true);
		}

		miniTable.autoSize();
		getStatusBar().setInfo(Integer.toString(rows));
	}

	@Override
	public void saveSelection(IMiniTable miniTable)
	{
		log.info("");
		// Array of Integers
		ArrayList<Integer> results = new ArrayList<Integer>();
		setSelection(null);

		// Get selected entries
		int rows = miniTable.getRowCount();
		for (int i = 0; i < rows; i++)
		{
			IDColumn id = (IDColumn)miniTable.getValueAt(i, 0); // ID in column 0
			// log.fine( "Row=" + i + " - " + id);
			if (id != null && id.isSelected())
				results.add(id.getRecord_ID());
		}

		if (results.size() == 0)
			return;
		log.config("Selected #" + results.size());
		setSelection(results);
	} // saveSelection

	private GridTab createGridTab(int windowId, String tableName)
	{
		int windowNo = 1000; // TODO
		GridWindow gridWindow = GridWindow.get(Env.getCtx(), windowNo, windowId);
		for (int i = 0; i < gridWindow.getTabCount(); i++)
		{
			GridTab gridTab = gridWindow.getTab(i);
			if (tableName.equals(gridTab.getTableName()) && gridTab.getTabLevel() == 0)
			{
				gridTab.initTab(false);
				return gridTab;
			}
		}
		return null;
	}

	public Lookup getLookup(String columnName)
	{
		GridField gridField = getInvoiceCandidateGridTab().getField(columnName);
		if (gridField == null)
			return null;
		return gridField.getLookup();
	}

	public String getColumnSQL(String columnName)
	{
		final GridField gridField = getInvoiceCandidateGridTab().getField(columnName);
		if (gridField == null)
			return null;
		
		return gridField.getColumnSQL(false);
	}

	protected GridTab getInvoiceCandidateGridTab()
	{
		if (invoiceCandidateTab == null)
		{
			this.invoiceCandidateTab = createGridTab(InvoiceCandidate_Window_ID, I_C_Invoice_Candidate.Table_Name);
		}
		return invoiceCandidateTab;
	}

	private GridTab invoiceCandidateTab;
	
	public abstract IStatusBar getStatusBar();
	
	@Override
	public String generate()
	{
		try
		{
			return generate0();
		}
		catch (Exception e)
		{
			return e.getLocalizedMessage();
		}
	}
	public String generate0()
	{
		final List<Integer> selection = getSelection();
		if (selection == null || selection.isEmpty())
		{
			return Msg.getMsg(Env.getCtx(), MSG_INVOICE_GENERATE_NO_CANDIDATES_SELECTED_0P);
		}
		
		String trxName = Trx.createTrxName("ICG");
		Trx trx = Trx.get(trxName, true);	//trx needs to be committed too
		
		setSelectionActive(false);  //  prevents from being called twice
		getStatusBar().setStatusLine(Msg.getMsg(Env.getCtx(), "InvGenerateGen"));
		getStatusBar().setStatusDB(String.valueOf(selection.size()));

		//	Prepare Process
		final MPInstance instance = new MPInstance(Env.getCtx(), C_Invoice_Candidate_Generate_Process_ID, 0, 0);
		instance.saveEx();
		
		//insert selection
		DB.createT_Selection(instance.getAD_PInstance_ID(), selection, trxName);
		
		final ProcessInfo pi = new ProcessInfo ("", instance.getAD_Process_ID());
		pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());

		//	Add Parameters
		final MPInstancePara para = new MPInstancePara(instance, 10);
		para.setParameter("Selection", "Y");
		para.saveEx();
		
		// Note: we don't have to set the CHECKBOX_IGNORE_INVOICE_SCHEDULE parameter, because our selection already
		// contains the correct rows
		
		setTrx(trx);
		setProcessInfo(pi);
		
		return null;
	}
}
