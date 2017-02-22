package de.metas.dlm.partitioner.process;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.compiere.util.TrxRunnable;

import de.metas.connection.IConnectionCustomizerService;
import de.metas.dlm.IDLMService;
import de.metas.dlm.connection.DLMConnectionCustomizer;
import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.model.I_DLM_Partition;
import de.metas.dlm.model.I_DLM_Partition_Workqueue;
import de.metas.process.JavaProcess;
import de.metas.process.Param;

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

/**
 * Sets {@link IDLMAware#COLUMNNAME_DLM_Partition_ID} to 0 for all records that reference the current partition
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class DLM_Partition_Destroy extends JavaProcess
{
	private final IDLMService dlmService = Services.get(IDLMService.class);

	private final IConnectionCustomizerService connectionCustomizerService = Services.get(IConnectionCustomizerService.class);

	@Param(mandatory = true, parameterName = "IsClear_DLM_Partition_Workqueue")
	private boolean isClearWorkQueue;

	@Override
	protected String doIt() throws Exception
	{
		final I_DLM_Partition partitionDB = getProcessInfo().getRecord(I_DLM_Partition.class);

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		try (final AutoCloseable customizer = connectionCustomizerService.registerTemporaryCustomizer(DLMConnectionCustomizer.seeThemAllCustomizer()))
		{
			trxManager.run("DLM_Partition_Destroy_ID_" + partitionDB.getDLM_Partition_ID(), // trxName prefix
					true,
					new TrxRunnable()
					{
						@Override
						public void run(final String localTrxName) throws Exception
						{
							destroyPartitionWithinTrx(partitionDB);
						}
					});

		}
		return MSG_OK;
	}

	private void destroyPartitionWithinTrx(final I_DLM_Partition partitionDB)
	{
		final PlainContextAware ctx = PlainContextAware.newWithThreadInheritedTrx(getCtx());

		final int updateCount = dlmService.directUpdateDLMColumn(
				ctx,
				partitionDB.getDLM_Partition_ID(),
				IDLMAware.COLUMNNAME_DLM_Partition_ID, 0);

		Loggables.get().addLog("Unassigned {} records from {}", updateCount, partitionDB);

		partitionDB.setPartitionSize(0);
		InterfaceWrapperHelper.save(partitionDB);

		if (isClearWorkQueue)
		{
			final IQueryBL queryBL = Services.get(IQueryBL.class);
			final int deleteCount = queryBL.createQueryBuilder(I_DLM_Partition_Workqueue.class, ctx)
					.addEqualsFilter(I_DLM_Partition_Workqueue.COLUMN_DLM_Partition_ID, partitionDB.getDLM_Partition_ID())
					.create()
					.deleteDirectly();

			Loggables.get().addLog("Deleted {} {} records that referenced", deleteCount, I_DLM_Partition_Workqueue.Table_Name, partitionDB);
		}
	}

}
