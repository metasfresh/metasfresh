package de.metas.elasticsearch.impl;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.elasticsearch.IESIndexerResult;
import de.metas.elasticsearch.IESModelIndexerTrigger;
import de.metas.elasticsearch.IESModelIndexingService;
import de.metas.elasticsearch.async.AsyncRemoveFromIndexProcessor;
import de.metas.logging.LogManager;

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

public class ESOnDeleteTriggerInterceptor extends AbstractModelInterceptor implements IESModelIndexerTrigger
{
	// services
	private static final Logger logger = LogManager.getLogger(ESDocumentIndexTriggerInterceptor.class);

	private final String modelTableName;
	private final String modelIndexerId;

	private boolean triggerInstalled = false;

	public ESOnDeleteTriggerInterceptor(final String modelTableName, final String modelIndexerId)
	{
		super();

		Check.assumeNotEmpty(modelTableName, "modelTableName is not empty");
		this.modelTableName = modelTableName;

		Check.assumeNotEmpty(modelIndexerId, "modelIndexerId is not empty");
		this.modelIndexerId = modelIndexerId;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("modelTableName", modelTableName)
				.add("modelIndexerId", modelIndexerId)
				.add("installed", triggerInstalled)
				.toString();
	}

	@Override
	public synchronized void install()
	{
		if (!triggerInstalled)
		{
			Services.get(IModelInterceptorRegistry.class).addModelInterceptor(this);
		}
		triggerInstalled = true;
	}

	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		engine.addModelChange(modelTableName, this);
	}

	@Override
	public void onModelChange(final Object model, final ModelChangeType changeType)
	{
		try
		{
			switch (changeType)
			{
				case BEFORE_DELETE:
					// NOTE: triggering on BEFORE because on AFTER we won't be able to fetch the model IDs
					removeFromIndexes(model);
					break;
				default:
					// nothing
					break;
			}
		}
		catch (final Exception ex)
		{
			logger.warn("Failed indexing: {} ({})", model, changeType, ex);
		}
	}

	private final void removeFromIndexes(final Object model)
	{
		removeFromIndexesSync(model);
	}

	private final void removeFromIndexesSync(final Object model)
	{
		final int modelIdInt = InterfaceWrapperHelper.getId(model);
		final String modelId = String.valueOf(modelIdInt);

		final IESIndexerResult result = Services.get(IESModelIndexingService.class)
				.getModelIndexerById(modelIndexerId)
				.removeFromIndexByIds(ImmutableList.of(modelId));
		if (result.hasFailures())
		{
			logger.error("Failed indexing {}", result);
		}
	}

	private final void removeFromIndexesAsync(final Object model)
	{
		final int modelIdInt = InterfaceWrapperHelper.getId(model);

		AsyncRemoveFromIndexProcessor.enqueue(modelIndexerId, modelTableName, ImmutableList.of(modelIdInt));
	}

	@Override
	public IQueryFilter<Object> getMatchingModelsFilter()
	{
		return ConstantQueryFilter.of(false);
	}

}
