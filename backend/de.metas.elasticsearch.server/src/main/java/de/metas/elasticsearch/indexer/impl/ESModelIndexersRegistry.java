package de.metas.elasticsearch.indexer.impl;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.util.Check;
import org.compiere.Adempiere;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;

import de.metas.elasticsearch.config.ESModelIndexerConfigBuilder;
import de.metas.elasticsearch.indexer.IESModelIndexer;
import de.metas.elasticsearch.indexer.IESModelIndexersRegistry;
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

public class ESModelIndexersRegistry implements IESModelIndexersRegistry
{
	private static final Logger logger = LogManager.getLogger(ESModelIndexersRegistry.class);

	private final ConcurrentHashMap<String, IESModelIndexer> id2indexers = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, ImmutableList<IESModelIndexer>> modelTableName2indexers = new ConcurrentHashMap<>();

	@Autowired
	private Client elasticsearchClient;

	@Autowired
	private ObjectMapper jsonObjectMapper;

	public ESModelIndexersRegistry()
	{
		super();
		Adempiere.autowire(this);

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
		final ImmutableList<IESModelIndexer> modelIndexers = modelTableName2indexers.get(modelTableName);
		if (modelIndexers == null)
		{
			return ImmutableList.of();
		}
		return modelIndexers;
	}

	@Override
	public IESModelIndexer getModelIndexerById(final String modelIndexerId)
	{
		final IESModelIndexer indexer = id2indexers.get(modelIndexerId);
		if (indexer == null)
		{
			throw new IllegalArgumentException("No indexer found for modelIndexerId=" + modelIndexerId);
		}
		return indexer;
	}

	@Override
	public void addModelIndexer(final ESModelIndexerConfigBuilder config)
	{
		final IESModelIndexer indexer = new ESModelIndexerBuilder(this, config)
				.build();

		//
		// Register the indexer
		{
			Check.assumeNotNull(indexer, "Parameter indexer is not null");

			final IESModelIndexer oldIndexer = id2indexers.putIfAbsent(indexer.getId(), indexer);
			if (oldIndexer != null && !oldIndexer.equals(indexer))
			{
				logger.warn("Skip registering indexer {} because it was already registered");
				return;
			}

			modelTableName2indexers.compute(indexer.getModelTableName(), (modelTableName, existingModelIndexers) -> {
				if (existingModelIndexers == null || existingModelIndexers.isEmpty())
				{
					return ImmutableList.of(indexer);
				}
				else
				{
					return ImmutableList.<IESModelIndexer> builder().addAll(existingModelIndexers).add(indexer).build();
				}
			});

			logger.info("Registered indexer: {}", indexer);
		}

		//
		// Create/update mapping for our indexer
		try
		{
			indexer.createUpdateIndex();
			logger.info("Created/Updated index mapping for {}", indexer);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed creating/updating index for {}", indexer, ex);
		}
	}
}
