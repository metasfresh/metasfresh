/*
 * #%L
 * de.metas.elasticsearch.server
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

package de.metas.elasticsearch.indexer.registry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.elasticsearch.IESSystem;
import de.metas.elasticsearch.config.ESModelIndexerConfigBuilder;
import de.metas.elasticsearch.config.ESModelIndexerId;
import de.metas.elasticsearch.config.ESModelIndexerProfile;
import de.metas.elasticsearch.indexer.engine.ESModelIndexer;
import de.metas.elasticsearch.indexer.engine.IESIndexerResult;
import de.metas.elasticsearch.indexer.source.ESModelIndexerDataSources;
import de.metas.elasticsearch.indexer.source.SqlESModelIndexerDataSource;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ESModelIndexersRegistry
{
	// services
	private static final Logger logger = LogManager.getLogger(ESModelIndexersRegistry.class);
	private final IESSystem esSystem = Services.get(IESSystem.class);
	private final ObjectMapper jsonObjectMapper;

	private static final String SYSCONFIG_AUTOINDEX_MODELS = "de.metas.elasticsearch.indexer.AutoIndexModels";

	private final ConcurrentHashMap<ESModelIndexerId, ESModelIndexer> indexersById = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, ImmutableList<ESModelIndexer>> indexersByModelTableName = new ConcurrentHashMap<>();

	public ESModelIndexersRegistry(@NonNull final ObjectMapper jsonObjectMapper)
	{
		this.jsonObjectMapper = jsonObjectMapper;
	}

	public Collection<ESModelIndexer> getModelIndexersByTableName(@NonNull final String modelTableName)
	{
		final ImmutableList<ESModelIndexer> modelIndexers = indexersByModelTableName.get(modelTableName);
		if (modelIndexers == null)
		{
			return ImmutableList.of();
		}
		return modelIndexers;
	}

	public ESModelIndexer getModelIndexerById(@NonNull final ESModelIndexerId modelIndexerId)
	{
		final ESModelIndexer indexer = indexersById.get(modelIndexerId);
		if (indexer == null)
		{
			throw new IllegalArgumentException("No indexer found for modelIndexerId=" + modelIndexerId);
		}
		return indexer;
	}

	public void addModelIndexer(@NonNull final ESModelIndexerConfigBuilder config)
	{
		final ESModelIndexerFactory indexerFactory = ESModelIndexerFactory.builder()
				.elasticsearchClient(esSystem.elasticsearchClient())
				.jsonObjectMapper(jsonObjectMapper)
				.config(config)
				.build();

		final ESModelIndexer indexer = indexerFactory.create();

		addModelIndexer(indexer);
	}

	private void addModelIndexer(@NonNull final ESModelIndexer indexer)
	{
		//
		// Register the indexer
		{
			final ESModelIndexer oldIndexer = indexersById.putIfAbsent(indexer.getId(), indexer);
			if (oldIndexer != null && !oldIndexer.equals(indexer))
			{
				logger.warn("Skip registering indexer {} because it was already registered: {}", indexer, oldIndexer);
				return;
			}

			indexersByModelTableName.compute(indexer.getModelTableName(), (modelTableName, existingModelIndexers) -> merge(existingModelIndexers, indexer));
			logger.info("Registered indexer: {}", indexer);
		}

		//
		// Create/update mapping for our indexer
		try
		{
			createIndexAndAddAllModels(indexer);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed creating/updating index for {}", indexer, ex);
		}
	}

	private void createIndexAndAddAllModels(@NonNull final ESModelIndexer indexer)
	{
		final boolean indexJustCreated = indexer.createUpdateIndex();
		logger.info("Created/Updated index mapping for {}", indexer);

		if (indexJustCreated && isAutoIndexModelsForIndexName(indexer.getIndexName()))
		{
			final SqlESModelIndexerDataSource modelsToIndex = ESModelIndexerDataSources.allForModelIndexer(indexer);
			final IESIndexerResult indexingResult = indexer.addToIndex(modelsToIndex);
			logger.info("Indexed models for {}: {}", indexer, indexingResult.getSummary());
		}
	}

	private boolean isAutoIndexModelsForIndexName(final String indexName)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		if (!sysConfigBL.getBooleanValue(SYSCONFIG_AUTOINDEX_MODELS, true))
		{
			return false;
		}

		return sysConfigBL.getBooleanValue(SYSCONFIG_AUTOINDEX_MODELS + "." + indexName, true);
	}

	private static ImmutableList<ESModelIndexer> merge(
			@Nullable final ImmutableList<ESModelIndexer> existingModelIndexers,
			@NonNull final ESModelIndexer modelIndexerToAdd)
	{
		if (existingModelIndexers == null || existingModelIndexers.isEmpty())
		{
			return ImmutableList.of(modelIndexerToAdd);
		}
		else
		{
			return ImmutableList.<ESModelIndexer>builder().addAll(existingModelIndexers).add(modelIndexerToAdd).build();
		}
	}

	public Optional<ESModelIndexer> getFullTextSearchModelIndexer(final String modelTableName)
	{
		return getModelIndexersByTableName(modelTableName)
				.stream()
				.filter(indexer -> ESModelIndexerProfile.FULL_TEXT_SEARCH.equals(indexer.getProfile()))
				.findFirst();
	}

}
