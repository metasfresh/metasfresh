package de.metas.dlm.migrator.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxSavepoint;
import org.adempiere.ad.trx.spi.TrxListenerAdapter;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.util.TrxRunnable;
import org.slf4j.Logger;

import de.metas.adempiere.service.IColumnBL;
import de.metas.dlm.Partition;
import de.metas.dlm.migrator.IMigratorService;
import de.metas.dlm.model.IDLMAware;
import de.metas.logging.LogManager;

/*
 * #%L
 * metasfresh-dlm
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class MigratorService implements IMigratorService
{
	protected final transient Logger logger = LogManager.getLogger(getClass());

	@Override
	public void testMigratePartition(final Partition partition)
	{
		updateDLMLevel(partition, DLM_Level_TEST);

		// if we got here without a DLMException, then the partition can be migrated
	}

	private void updateDLMLevel(final Partition partition, final int targetDlmLevel)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		final Map<String, List<IDLMAware>> table2Record = partition.getRecords()
				.stream()
				.collect(Collectors.groupingBy(dlmAware -> InterfaceWrapperHelper.getModelTableName(dlmAware)));

		// note that a TrxRunnable suffices. We need no TrxRunnable2 etc, because we don'T intend to do error handling in this method.
		trxManager.run(
				new TrxRunnable()
				{
					@Override
					public void run(final String localTrxName) throws Exception
					{
						final ITrxSavepoint savepoint = trxManager.getTrx(localTrxName).createTrxSavepoint(localTrxName + "_geforeChanges");

						final ITrxListenerManager trxListenerManager = trxManager.getTrxListenerManager(localTrxName);

						trxListenerManager.registerListener(new TrxListenerAdapter()
						{
							@Override
							public void afterCommit(final ITrx trx)
							{
								trx.rollback(savepoint);
							}
						});

						updateDLMLevel0(table2Record, targetDlmLevel, InterfaceWrapperHelper.getContextAware(partition));
					}

				});
	}

	private void updateDLMLevel0(final Map<String, List<IDLMAware>> table2Record, final int targetDlmLevel, final IContextAware partition)
	{

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IColumnBL columnBL = Services.get(IColumnBL.class);

		for (final Entry<String, List<IDLMAware>> tableWithRecords : table2Record.entrySet())
		{
			final String tableName = tableWithRecords.getKey();
			final String keyColumn = columnBL.getSingleKeyColumn(tableName);

			final Integer[] recordIds = tableWithRecords
					.getValue()
					.stream()
					.map(r -> InterfaceWrapperHelper.getId(r))
					.toArray(size -> new Integer[size]);

			final int updated = queryBL.createQueryBuilder(IDLMAware.class, tableName, partition)
					.addInArrayFilter(keyColumn, recordIds)
					.create()
					.updateDirectly()
					.addSetColumnValue(IDLMAware.COLUMNNAME_DLM_Level, targetDlmLevel)
					.execute();
			logger.debug("Table {}: updated {} records to DLM_Level={}", tableName, updated, targetDlmLevel);
		}
	}

}
