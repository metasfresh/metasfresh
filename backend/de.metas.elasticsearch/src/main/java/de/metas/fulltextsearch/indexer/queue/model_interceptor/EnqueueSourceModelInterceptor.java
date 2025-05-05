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

import com.google.common.collect.ImmutableList;
import de.metas.fulltextsearch.config.FTSConfigService;
import de.metas.fulltextsearch.config.FTSConfigSourceTable;
import de.metas.fulltextsearch.config.FTSConfigSourceTablesMap;
import de.metas.fulltextsearch.indexer.queue.ModelToIndexEnqueueRequest;
import de.metas.fulltextsearch.indexer.queue.ModelToIndexEventType;
import de.metas.fulltextsearch.indexer.queue.ModelsToIndexQueueService;
import de.metas.logging.LogManager;
import de.metas.util.NumberUtils;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.table.api.TableAndColumnName;
import org.adempiere.ad.table.api.TableName;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Client;
import org.elasticsearch.common.util.set.Sets;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class EnqueueSourceModelInterceptor extends AbstractModelInterceptor
{
	private static final Logger logger = LogManager.getLogger(EnqueueSourceModelInterceptor.class);
	private final ModelsToIndexQueueService queueService;
	private final FTSConfigService configService;

	private IModelValidationEngine engine;
	@NonNull private FTSConfigSourceTablesMap _sourceTablesRegistered = FTSConfigSourceTablesMap.EMPTY;

	public EnqueueSourceModelInterceptor(
			@NonNull final ModelsToIndexQueueService queueService,
			@NonNull final FTSConfigService configService)
	{
		this.queueService = queueService;
		this.configService = configService;
	}

	@PostConstruct
	public void postConstruct()
	{
		configService.addListener(this::registerInterceptors);
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
			logger.warn("Skip registering interceptors because engine is not yet available");
			return;
		}

		final FTSConfigSourceTablesMap sourceTablesAlreadyRegistered = _sourceTablesRegistered;
		final FTSConfigSourceTablesMap sourceTablesToRegisterTarget = configService.getSourceTables();

		if (!Objects.equals(sourceTablesAlreadyRegistered.getTableNames(), sourceTablesToRegisterTarget.getTableNames()))
		{
			final Set<TableName> sourceTableNamesToUnregister = Sets.difference(sourceTablesAlreadyRegistered.getTableNames(), sourceTablesToRegisterTarget.getTableNames());
			final Set<TableName> sourceTableNamesToRegister = Sets.difference(sourceTablesToRegisterTarget.getTableNames(), sourceTablesAlreadyRegistered.getTableNames());

			if (!sourceTableNamesToUnregister.isEmpty())
			{
				sourceTableNamesToUnregister.forEach(sourceTableName -> engine.removeModelChange(sourceTableName.getAsString(), this));
				logger.info("Unregistered from {}", sourceTableNamesToUnregister);
			}

			if (!sourceTableNamesToRegister.isEmpty())
			{
				sourceTableNamesToRegister.forEach(sourceTableName -> engine.addModelChange(sourceTableName.getAsString(), this));
				logger.info("Registered for {}", sourceTableNamesToRegister);
			}
		}

		this._sourceTablesRegistered = sourceTablesToRegisterTarget;
	}

	private synchronized FTSConfigSourceTablesMap getSourceTables()
	{
		return _sourceTablesRegistered;
	}

	@Override
	public void onModelChange(final Object model, final ModelChangeType changeType)
	{
		if (changeType.isNewOrChange())
		{
			if (changeType.isAfter())
			{
				queueService.enqueue(extractRequests(model, ModelToIndexEventType.MODEL_CREATED_OR_UPDATED, getSourceTables()));
			}
		}
		else if (changeType.isDelete())
		{
			if (changeType.isBefore())
			{
				queueService.enqueue(extractRequests(model, ModelToIndexEventType.MODEL_REMOVED, getSourceTables()));
			}
		}
	}

	public static List<ModelToIndexEnqueueRequest> extractRequests(
			@NonNull final Object model,
			@NonNull final ModelToIndexEventType eventType,
			@NonNull final FTSConfigSourceTablesMap sourceTablesMap)
	{
		final TableName tableName = TableName.ofString(InterfaceWrapperHelper.getModelTableName(model));
		final int recordId = InterfaceWrapperHelper.getId(model);
		final TableRecordReference recordRef = TableRecordReference.of(tableName.getAsString(), recordId);
		final boolean recordIsActive = InterfaceWrapperHelper.isActive(model);

		final ImmutableList<FTSConfigSourceTable> sourceTables = sourceTablesMap.getByTableName(tableName);
		final ArrayList<ModelToIndexEnqueueRequest> result = new ArrayList<>(sourceTables.size());

		for (final FTSConfigSourceTable sourceTable : sourceTables)
		{
			final TableRecordReference recordRefEffective;
			final ModelToIndexEventType eventTypeEffective;

			final TableRecordReference parentRecordRef = extractParentRecordRef(model, sourceTable);
			if (parentRecordRef != null)
			{
				recordRefEffective = parentRecordRef;
				eventTypeEffective = ModelToIndexEventType.MODEL_CREATED_OR_UPDATED;
			}
			else
			{
				recordRefEffective = recordRef;
				if (recordIsActive)
				{
					eventTypeEffective = eventType;
				}
				else
				{
					eventTypeEffective = ModelToIndexEventType.MODEL_REMOVED;
				}
			}

			result.add(ModelToIndexEnqueueRequest.builder()
					.ftsConfigId(sourceTable.getFtsConfigId())
					.eventType(eventTypeEffective)
					.sourceModelRef(recordRefEffective)
					.build());
		}

		return result;
	}

	@Nullable
	private static TableRecordReference extractParentRecordRef(@NonNull final Object model, @NonNull final FTSConfigSourceTable sourceTable)
	{
		final TableAndColumnName parentColumnName = sourceTable.getParentColumnName();
		if (parentColumnName == null)
		{
			return null;
		}

		final int parentId = InterfaceWrapperHelper.getValue(model, parentColumnName.getColumnNameAsString())
				.map(NumberUtils::asIntegerOrNull)
				.orElse(-1);
		if (parentId <= 0)
		{
			return null;
		}

		return TableRecordReference.of(parentColumnName.getTableNameAsString(), parentId);
	}
}
