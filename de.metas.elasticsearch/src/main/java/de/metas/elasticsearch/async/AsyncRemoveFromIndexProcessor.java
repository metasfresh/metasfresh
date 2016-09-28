package de.metas.elasticsearch.async;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.util.Env;

import com.google.common.collect.ListMultimap;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.elasticsearch.IESModelIndexingService;

/*
 * #%L
 * de.metas.elasticsearch
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

public class AsyncRemoveFromIndexProcessor extends WorkpackageProcessorAdapter
{
	public static final void enqueue(final List<ITableRecordReference> models)
	{
		final Properties ctx = Env.getCtx();
		Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, AsyncRemoveFromIndexProcessor.class)
				.newBlock()
				.newWorkpackage()
				.bindToTrxName(ITrx.TRXNAME_ThreadInherited)
				.addElements(models)
				.build();
	}

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		final boolean skipAlreadyScheduledItems = true;
		final ListMultimap<Integer, String> idsByTable = Services.get(IQueueDAO.class)
				.retrieveQueueElements(workpackage, skipAlreadyScheduledItems)
				.stream()
				.map(qe -> GuavaCollectors.entry(qe.getAD_Table_ID(), String.valueOf(qe.getRecord_ID())))
				.collect(GuavaCollectors.toImmutableListMultimap());

		if (idsByTable.isEmpty())
		{
			throw new AdempiereException("No source models found");
		}

		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		final IESModelIndexingService indexingService = Services.get(IESModelIndexingService.class);
		for (final Entry<Integer, Collection<String>> entry : idsByTable.asMap().entrySet())
		{
			final int adTableId = entry.getKey();
			final String tableName = adTableDAO.retrieveTableName(adTableId);
			final Collection<String> ids = entry.getValue();
			indexingService.removeFromIndexesByIds(tableName, ids);
		}

		return Result.SUCCESS;
	}
}
