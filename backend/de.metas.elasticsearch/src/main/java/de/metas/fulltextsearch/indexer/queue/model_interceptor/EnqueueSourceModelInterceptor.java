/*
 * #%L
 * de.metas.elasticsearch
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.fulltextsearch.indexer.queue.model_interceptor;

import com.google.common.collect.ImmutableSet;
import de.metas.fulltextsearch.config.FTSConfigService;
import de.metas.fulltextsearch.indexer.queue.ModelToIndexEnqueueRequest;
import de.metas.fulltextsearch.indexer.queue.ModelToIndexEventType;
import de.metas.fulltextsearch.indexer.queue.ModelsToIndexQueueService;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Client;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class EnqueueSourceModelInterceptor extends AbstractModelInterceptor
{
	private static final Logger logger = LogManager.getLogger(EnqueueSourceModelInterceptor.class);
	private final ModelsToIndexQueueService queueService;
	private final FTSConfigService configRepository;

	private IModelValidationEngine engine;
	private ImmutableSet<String> _sourceTableNamesRegistered = null;

	public EnqueueSourceModelInterceptor(
			@NonNull final ModelsToIndexQueueService queueService,
			@NonNull final FTSConfigService configRepository)
	{
		this.queueService = queueService;
		this.configRepository = configRepository;
	}

	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		this.engine = engine;
		registerInterceptors();
	}

	private synchronized void registerInterceptors()
	{
		final IModelValidationEngine engine = this.engine;
		if (engine == null)
		{
			throw new AdempiereException("engine is null");
		}

		final ImmutableSet<String> sourceTableNamesToUnregister = _sourceTableNamesRegistered;
		if (sourceTableNamesToUnregister != null && !sourceTableNamesToUnregister.isEmpty())
		{
			sourceTableNamesToUnregister.forEach(sourceTableName -> engine.removeModelChange(sourceTableName, this));
			logger.info("Unregistered from {}", sourceTableNamesToUnregister);
		}

		_sourceTableNamesRegistered = configRepository.getSourceTableNames();
		_sourceTableNamesRegistered.forEach(sourceTableName -> engine.addModelChange(sourceTableName, this));
		logger.info("Registered for {}", _sourceTableNamesRegistered);
	}

	@Override
	public void onModelChange(final Object model, final ModelChangeType changeType)
	{
		if (changeType.isAfter())
		{
			queueService.enqueue(ModelToIndexEnqueueRequest.builder()
					.sourceModelRef(TableRecordReference.of(model))
					.eventType(toModelToIndexEventType(changeType))
					.build());
		}
	}

	private static ModelToIndexEventType toModelToIndexEventType(final ModelChangeType changeType)
	{
		if (changeType.isNewOrChange())
		{
			return ModelToIndexEventType.CREATED_OR_UPDATED;
		}
		else if (changeType.isDelete())
		{
			return ModelToIndexEventType.REMOVED;
		}
		else
		{
			throw new AdempiereException("Cannot convert " + changeType + " to " + ModelToIndexEventType.class);
		}
	}

}
