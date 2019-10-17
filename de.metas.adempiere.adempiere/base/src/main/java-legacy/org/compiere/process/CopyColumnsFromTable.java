/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 2007 ADempiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * Adempiere, Inc. *
 *****************************************************************************/
package org.compiere.process;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.table.api.impl.CopyColumnsProducer;
import org.adempiere.ad.table.api.impl.CopyColumnsResult;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Table;
import org.compiere.model.MColumn;
import org.compiere.model.MTable;

import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Loggables;

/**
 * Copy columns from one table to other
 */
public class CopyColumnsFromTable extends JavaProcess
{
	private int p_target_AD_Table_ID;
	private int p_source_AD_Table_ID;
	private boolean p_IsTest = false;
	private boolean p_IsSyncDatabase = false;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
			{
				continue;
			}
			else if (name.equals("AD_Table_ID"))
			{
				p_source_AD_Table_ID = para.getParameterAsInt();
			}
			else if (name.equals("IsTest"))
			{
				p_IsTest = para.getParameterAsBoolean();
			}
			else if (name.equals("IsSyncDatabase"))
			{
				p_IsSyncDatabase = para.getParameterAsBoolean();
			}
		}
		p_target_AD_Table_ID = getRecord_ID();
	}	// prepare

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final I_AD_Table targetTable = getTargetTable();

		final CopyColumnsResult result = CopyColumnsProducer.newInstance()
				.setLogger(Loggables.nop())
				.setTargetTable(targetTable)
				.setSourceColumns(getSourceColumns())
				.setSyncDatabase(p_IsSyncDatabase)
				.setDryRun(p_IsTest)
				.create();

		//
		return "" + result;
	}

	private I_AD_Table getTargetTable()
	{
		if (p_target_AD_Table_ID <= 0)
		{
			throw new AdempiereException("@NotFound@ @AD_Table_ID@ " + p_target_AD_Table_ID);
		}
		final MTable targetTable = new MTable(getCtx(), p_target_AD_Table_ID, get_TrxName());
		return targetTable;
	}

	private List<I_AD_Column> getSourceColumns()
	{
		if (p_source_AD_Table_ID <= 0)
		{
			throw new AdempiereException("@NotFound@ @AD_Table_ID@ " + p_source_AD_Table_ID);
		}
		final MTable sourceTable = new MTable(getCtx(), p_source_AD_Table_ID, get_TrxName());

		final MColumn[] sourceColumnsArr = sourceTable.getColumns(true);
		if (sourceColumnsArr == null || sourceColumnsArr.length == 0)
		{
			throw new AdempiereException("@NotFound@ @AD_Column_ID@ (@AD_Table_ID@:" + sourceTable.getTableName() + ")");
		}

		final List<I_AD_Column> sourceColumns = new ArrayList<>(sourceColumnsArr.length);
		for (final I_AD_Column sourceColumn : sourceColumnsArr)
		{
			sourceColumns.add(sourceColumn);
		}
		return sourceColumns;
	}
}
