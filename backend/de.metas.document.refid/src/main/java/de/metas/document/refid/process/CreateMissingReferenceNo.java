package de.metas.document.refid.process;

/*
 * #%L
 * de.metas.document.refid
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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.DB;

import de.metas.document.refid.api.IReferenceNoBL;
import de.metas.document.refid.api.IReferenceNoGeneratorInstance;
import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 * Create missing reference numbers
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/03779_Create_Reference_numbers_for_existing_invoices_%282013011110000032 %29
 */
public class CreateMissingReferenceNo extends JavaProcess
{
	private boolean p_IsTest = true;

	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParametersAsArray())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
			{
				;
			}
			else if (name.equals("IsTest"))
			{
				p_IsTest = para.getParameterAsBoolean();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		final List<IReferenceNoGeneratorInstance> generatorInstancesAll = Services.get(IReferenceNoBL.class).getReferenceNoGeneratorInstances(getCtx());

		final Map<Integer, List<IReferenceNoGeneratorInstance>> tableId2GeneratorInstances = groupByTableId(generatorInstancesAll);

		for (final Integer adTableId : tableId2GeneratorInstances.keySet())
		{
			final List<IReferenceNoGeneratorInstance> generatorInstances = tableId2GeneratorInstances.get(adTableId);
			int count_ok = 0;
			int count_skipped = 0;

			final Iterator<PO> it = selectRecordsWithoutReferenceNos(adTableId, generatorInstances);
			while (it.hasNext())
			{
				final PO po = it.next();

				for (IReferenceNoGeneratorInstance generatorInstance : generatorInstances)
				{
					if (createReferenceNo(po, generatorInstance))
					{
						count_ok++;
					}
					else
					{
						count_skipped++;
					}
				}
			}
			if (count_ok != 0 || count_skipped != 0)
			{
				final String tableName = MTable.getTableName(getCtx(), adTableId);
				addLog("Table " + tableName + ": " + count_ok + " reference numbers generated; " + count_skipped + " skipped.");
			}

		}

		if (p_IsTest)
		{
			throw new AdempiereException("ROLLBACK");
		}

		clearSelection();

		return "OK";
	}

	private void clearSelection()
	{
		final int countDeleted = DB.deleteT_Selection(getAD_PInstance_ID(), ITrx.TRXNAME_None);
		log.info("Cleared " + countDeleted + " items from T_Selection");
	}

	private Iterator<PO> selectRecordsWithoutReferenceNos(final int adTableId, final List<IReferenceNoGeneratorInstance> generatorInstances)
	{
		// Make sure selection buffer is empty
		clearSelection();

		final MTable table = MTable.get(getCtx(), adTableId);

		int count = 0;
		final List<Integer> checkedReferenceNoTypes = new ArrayList<Integer>();
		for (final IReferenceNoGeneratorInstance generatorInstance : generatorInstances)
		{
			final int referenceNoTypeId = generatorInstance.getType().getC_ReferenceNo_Type_ID();
			if (checkedReferenceNoTypes.contains(referenceNoTypeId))
			{
				continue;
			}

			count += selectRecordsWithoutReferenceNos(table, referenceNoTypeId);
		}

		final String tableName = table.getTableName();

		log.info("Iterating " + count + " records from table " + tableName);

		final Iterator<PO> it = new Query(getCtx(), tableName, null, get_TrxName())
				.setOnlySelection(getAD_PInstance_ID())
				.iterate(null, false); // clazz=null, guaranteed=false (not needed because we already built our selection)

		return it;
	}

	private int selectRecordsWithoutReferenceNos(final MTable table, int referenceNoTypeId)
	{
		final Properties ctx = getCtx();

		final String tableName = table.getTableName();
		final String[] keyColumnNames = table.getKeyColumns();
		if (keyColumnNames == null || keyColumnNames.length != 1)
		{
			addLog("Skip table " + tableName + " because it does not have simple primary key");
			return 0;
		}
		final String keyColumnName = keyColumnNames[0];
		final String keyColumnNameFQ = tableName + "." + keyColumnName;

		final String whereClause = keyColumnNameFQ + " NOT IN ("
				+ " SELECT rd." + I_C_ReferenceNo_Doc.COLUMNNAME_Record_ID
				+ " FROM " + I_C_ReferenceNo_Doc.Table_Name + " rd"
				+ " INNER JOIN " + I_C_ReferenceNo.Table_Name + " r ON (rd." + I_C_ReferenceNo_Doc.COLUMNNAME_C_ReferenceNo_ID + "=r." + I_C_ReferenceNo.COLUMNNAME_C_ReferenceNo_ID + ")"
				+ " WHERE rd." + I_C_ReferenceNo_Doc.COLUMNNAME_AD_Table_ID + "=? AND r." + I_C_ReferenceNo.COLUMNNAME_C_ReferenceNo_Type_ID + "=?"
				+ ")";

		// NOTE: running out of transaction because: massive amount of data, no integrity issue
		final int count = new Query(ctx, tableName, whereClause, ITrx.TRXNAME_None)
				.setParameters(table.getAD_Table_ID(), referenceNoTypeId)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.createSelection(getAD_PInstance_ID());

		log.info("Selected " + count + " candidates from table " + tableName + " (C_ReferenceNo_Type_ID=" + referenceNoTypeId + ")");
		return count < 0 ? 0 : count;
	}

	private boolean createReferenceNo(PO po, IReferenceNoGeneratorInstance generatorInstance)
	{
		try
		{
			Services.get(IReferenceNoBL.class).linkReferenceNo(po, generatorInstance);

			if (p_IsTest)
			{
				rollback();
			}
			else
			{
				commitEx();
			}

			return true;
		}
		catch (Exception e)
		{
			addLog("Cannot create reference no for " + po + " (generator=" + generatorInstance + "): " + e.getLocalizedMessage());
			log.warn(e.getLocalizedMessage(), e);
		}

		return false;
	}

	private static Map<Integer, List<IReferenceNoGeneratorInstance>> groupByTableId(final List<IReferenceNoGeneratorInstance> generatorInstances)
	{
		final Map<Integer, List<IReferenceNoGeneratorInstance>> map = new HashMap<Integer, List<IReferenceNoGeneratorInstance>>();

		for (final IReferenceNoGeneratorInstance generatorInstance : generatorInstances)
		{
			for (final int adTableId : generatorInstance.getAssignedTableIds())
			{
				List<IReferenceNoGeneratorInstance> list = map.get(adTableId);
				if (list == null)
				{
					list = new ArrayList<IReferenceNoGeneratorInstance>();
					map.put(adTableId, list);
				}
				list.add(generatorInstance);
			}
		}

		return map;
	}
}
