package de.metas.elasticsearch.async;

import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.elasticsearch.IESIndexerResult;
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
	public static final void enqueue(final String modelIndexerId, final String modelTableName, final List<Integer> modelIds)
	{
		final Properties ctx = Env.getCtx();
		final List<ITableRecordReference> models = TableRecordReference.ofRecordIds(modelTableName, modelIds);

		//@formatter:off
		Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, AsyncAddToIndexProcessor.class)
				.newBlock()
				.newWorkpackage()
				.bindToThreadInheritedTrx()
				.addElements(models)
				.parameters()
					.setParameter(PARAMETERNAME_ModelIndexerId, modelIndexerId)
					.end()
				.build();
		//@formatter:on
	}

	private static final String PARAMETERNAME_ModelIndexerId = "ModelIndexerId";

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		final String modelIndexerId = getParameters().getParameterAsString(PARAMETERNAME_ModelIndexerId);
		Check.assumeNotEmpty(modelIndexerId, "modelIndexerId is not empty");

		final List<Object> modelsToIndex = retrieveItems(Object.class);
		if (modelsToIndex.isEmpty())
		{
			throw new AdempiereException("No source models found");
		}

		final IESIndexerResult result = Services.get(IESModelIndexingService.class)
				.getModelIndexerById(modelIndexerId)
				.addToIndex(modelsToIndex.iterator());

		getLoggable().addLog(result.getSummary());
		
		// TODO: figure out what to do with those records which failed to be indexed. Approaches:
		// * fail the entire workpackage (like we do it now)
		// * create another workpackage only with those?!
		result.throwExceceptionIfAnyFailure();

		return Result.SUCCESS;
	}

}
