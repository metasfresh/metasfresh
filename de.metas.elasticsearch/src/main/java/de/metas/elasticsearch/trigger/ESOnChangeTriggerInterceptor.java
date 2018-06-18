package de.metas.elasticsearch.trigger;

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

import com.google.common.collect.ImmutableList;

import de.metas.elasticsearch.IESSystem;
import de.metas.logging.LogManager;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@ToString
@EqualsAndHashCode(callSuper = false)
public class ESOnChangeTriggerInterceptor extends AbstractModelInterceptor implements IESModelIndexerTrigger
{
	// services
	private static final Logger logger = LogManager.getLogger(ESDocumentIndexTriggerInterceptor.class);

	private final String modelTableName;
	private final String modelIndexerId;
	private final boolean triggerOnNewOrChange;
	private final boolean triggerOnDelete;

	private boolean triggerInstalled = false;

	@Builder
	private ESOnChangeTriggerInterceptor(
			@NonNull final String modelTableName,
			@NonNull final String modelIndexerId,
			final boolean triggerOnNewOrChange,
			final boolean triggerOnDelete)
	{
		Check.assumeNotEmpty(modelTableName, "modelTableName is not empty");
		Check.assumeNotEmpty(modelIndexerId, "modelIndexerId is not empty");
		Check.assume(triggerOnNewOrChange || triggerOnDelete, "At least one trigger shall be enabled");

		this.modelTableName = modelTableName;
		this.modelIndexerId = modelIndexerId;
		this.triggerOnNewOrChange = triggerOnNewOrChange;
		this.triggerOnDelete = triggerOnDelete;
	}

	@Override
	public synchronized void install()
	{
		if (!triggerInstalled)
		{
			final IModelInterceptorRegistry modelInterceptorRegistry = Services.get(IModelInterceptorRegistry.class);
			modelInterceptorRegistry.addModelInterceptor(this);
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
			if (changeType.isNewOrChange() && changeType.isAfter())
			{
				if (triggerOnNewOrChange)
				{
					addToIndex(model);
				}
			}
			else if (changeType.isBefore() && changeType.isDelete())
			{
				// NOTE: triggering on BEFORE because on AFTER we won't be able to fetch the model IDs

				if (triggerOnDelete)
				{
					removeFromIndexes(model);
				}
			}
		}
		catch (final Exception ex)
		{
			logger.warn("Failed indexing: {} ({})", model, changeType, ex);
		}
	}

	private final void addToIndex(final Object model)
	{
		final int modelId = InterfaceWrapperHelper.getId(model);
		Services.get(IESSystem.class)
				.scheduler()
				.addToIndex(modelIndexerId, modelTableName, ImmutableList.of(modelId));
	}

	private final void removeFromIndexes(final Object model)
	{
		final int modelId = InterfaceWrapperHelper.getId(model);
		Services.get(IESSystem.class)
				.scheduler()
				.removeToIndex(modelIndexerId, modelTableName, ImmutableList.of(modelId));
	}

	@Override
	public IQueryFilter<Object> getMatchingModelsFilter()
	{
		return null;
	}

}
