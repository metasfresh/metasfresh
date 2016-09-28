package de.metas.elasticsearch.async;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.util.Env;

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

public class AsyncAddToIndexProcessor extends WorkpackageProcessorAdapter
{
	public static final void enqueue(final List<Object> sourceModels)
	{
		final Properties ctx = Env.getCtx();
		Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, AsyncAddToIndexProcessor.class)
				.newBlock()
				.newWorkpackage()
				.bindToTrxName(ITrx.TRXNAME_ThreadInherited)
				.addElements(sourceModels)
				.build();
	}

	@Override
	public Result processWorkPackage(I_C_Queue_WorkPackage workpackage, String localTrxName)
	{
		final List<Object> sourceModels = retrieveItems(Object.class);
		if (sourceModels.isEmpty())
		{
			throw new AdempiereException("No source models found");
		}
		Services.get(IESModelIndexingService.class)
				.addToIndexes(sourceModels.iterator());

		return Result.SUCCESS;
	}

}
