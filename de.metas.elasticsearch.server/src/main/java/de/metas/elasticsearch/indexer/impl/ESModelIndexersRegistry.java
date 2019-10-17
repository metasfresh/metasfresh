package de.metas.elasticsearch.indexer.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;

import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;

import de.metas.elasticsearch.config.ESModelIndexerConfigBuilder;
import de.metas.elasticsearch.config.ESModelIndexerId;
import de.metas.elasticsearch.config.ESModelIndexerProfile;
import de.metas.elasticsearch.indexer.ESModelIndexerDataSources;
import de.metas.elasticsearch.indexer.IESIndexerResult;
import de.metas.elasticsearch.indexer.IESModelIndexer;
import de.metas.elasticsearch.indexer.IESModelIndexersRegistry;
import de.metas.elasticsearch.indexer.SqlESModelIndexerDataSource;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;

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

public class ESModelIndexersRegistry implements IESModelIndexersRegistry
{
	private static final Logger logger = LogManager.getLogger(ESModelIndexersRegistry.class);

	private static final String SYSCONFIG_AUTOINDEX_MODELS = "de.metas.elasticsearch.indexer.AutoIndexModels";

	private final ConcurrentHashMap<ESModelIndexerId, IESModelIndexer> indexersById = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, ImmutableList<IESModelIndexer>> indexersByModelTableName = new ConcurrentHashMap<>();

	@Autowired
	private Client elasticsearchClient;

	@Autowired
	private ObjectMapper jsonObjectMapper;

	public ESModelIndexersRegistry()
	{
		SpringContextHolder.instance.autowire(this);

		logger.info("Elastic search client: {}", elasticsearchClient);
	}

	/* package */Client getElasticsearchClient()
	{
		return elasticsearchClient;
	}

	/* package */ObjectMapper getJsonObjectMapper()
	{
		return jsonObjectMapper;
	}

	@Override
	public Collection<IESModelIndexer> getModelIndexersByTableName(final String modelTableName)
	{
		final ImmutableList<IESModelIndexer> modelIndexers = indexersByModelTableName.get(modelTableName);
		if (modelIndexers == null)
		{
			return ImmutableList.of();
		}
		return modelIndexers;
	}

	@Override
	public IESModelIndexer getModelIndexerById(final ESModelIndexerId modelIndexerId)
	{
		final IESModelIndexer indexer = indexersById.get(modelIndexerId);
		if (indexer == null)
		{
			throw new IllegalArgumentException("No indexer found for modelIndexerId=" + modelIndexerId);
		}
		return indexer;
	}

	@Override
	public void addModelIndexer(final ESModelIndexerConfigBuilder config)
	{
		final IESModelIndexer indexer = new ESModelIndexerFactory(this, config)
				.indexSettingsJson(config.getIndexSettingsJson())
				.indexStringFullTextSearchAnalyzer(config.getIndexStringFullTextSearchAnalyzer())
				.create();

		addModelIndexer(indexer);
	}

	private void addModelIndexer(@NonNull final IESModelIndexer indexer)
	{
		//
		// Register the indexer
		{
			final IESModelIndexer oldIndexer = indexersById.putIfAbsent(indexer.getId(), indexer);
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

	private void createIndexAndAddAllModels(final IESModelIndexer indexer)
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

	private static ImmutableList<IESModelIndexer> merge(
			@Nullable final ImmutableList<IESModelIndexer> existingModelIndexers,
			@NonNull final IESModelIndexer modelIndexerToAdd)
	{
		if (existingModelIndexers == null || existingModelIndexers.isEmpty())
		{
			return ImmutableList.of(modelIndexerToAdd);
		}
		else
		{
			return ImmutableList.<IESModelIndexer> builder().addAll(existingModelIndexers).add(modelIndexerToAdd).build();
		}
	}

	@Override
	public Optional<IESModelIndexer> getFullTextSearchModelIndexer(final String modelTableName)
	{
		return getModelIndexersByTableName(modelTableName)
				.stream()
				.filter(indexer -> ESModelIndexerProfile.FULL_TEXT_SEARCH.equals(indexer.getProfile()))
				.findFirst();
	}

}
