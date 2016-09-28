package de.metas.elasticsearch.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.adempiere.ad.modelvalidator.IModelInterceptor;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.document.engine.IDocActionBL;
import de.metas.elasticsearch.IESModelIndexer;
import de.metas.elasticsearch.IESModelIndexingService;
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

public class ESModelIndexingService implements IESModelIndexingService
{
	private static final Logger logger = LogManager.getLogger(ESModelIndexingService.class);

	private final ConcurrentHashMap<String, CopyOnWriteArrayList<IESModelIndexer>> modelTableName2indexers = new ConcurrentHashMap<>();

	private final ConcurrentHashMap<String, IModelInterceptor> triggerModelInterceptors = new ConcurrentHashMap<>();

	@Autowired
	private Client elasticsearchClient;

	@Autowired
	private ObjectMapper jsonObjectMapper;

	public ESModelIndexingService()
	{
		super();
		Adempiere.autowire(this);
	}

	Client getElasticsearchClient()
	{
		return elasticsearchClient;
	}

	ObjectMapper getJsonObjectMapper()
	{
		return jsonObjectMapper;
	}

	public void checkIndexes()
	{
		modelTableName2indexers.values()
				.stream()
				.flatMap(tableIndexers -> tableIndexers.stream())
				.forEach(indexer -> {
					try
					{
						indexer.createUpdateIndex();
					}
					catch (final Exception ex)
					{
						logger.warn("Failed creating/updating the index: {}. Skipped.", indexer, ex);
					}
				});
	}
	
	@Override
	public <ModelType> ESModelIndexerBuilder<ModelType> newIndexerBuilder(final Class<ModelType> modelClass)
	{
		return new ESModelIndexerBuilder<>(this, modelClass);
	}

	void addIndexer(final IESModelIndexer indexer)
	{
		//
		// Register the indexer
		Check.assumeNotNull(indexer, "Parameter indexer is not null");
		final String modelTableName = indexer.getModelTableName();
		final CopyOnWriteArrayList<IESModelIndexer> modelIndexers = modelTableName2indexers.computeIfAbsent(modelTableName, newSourceTableName -> new CopyOnWriteArrayList<>());
		final boolean indexerAdded = modelIndexers.addIfAbsent(indexer);
		if (!indexerAdded)
		{
			logger.warn("Skip registering indexer {} because it was already registered");
			return;
		}

		logger.info("Registered indexer: {}", indexer);

		//
		// Create/update mapping for our indexer
		try
		{
			indexer.createUpdateIndex();
			logger.info("Created/Updated index mapping for {}", indexer);
		}
		catch (Exception ex)
		{
			logger.warn("Failed creating/updating index for {}", indexer, ex);
		}

		//
		// Triggering
		triggerModelInterceptors.computeIfAbsent(modelTableName, this::createAndInstallTriggerInterceptor);
	}

	private final IModelInterceptor createAndInstallTriggerInterceptor(final String modelTableName)
	{
		if (Services.get(IDocActionBL.class).isDocumentTable(modelTableName))
		{
			final ESDocumentIndexTriggerInterceptor triggerModelInterceptor = new ESDocumentIndexTriggerInterceptor(modelTableName);
			Services.get(IModelInterceptorRegistry.class).addModelInterceptor(triggerModelInterceptor);

			logger.info("Installed index trigger model interceptor: {}", triggerModelInterceptor);
			return triggerModelInterceptor;
		}
		else
		{
			throw new IllegalArgumentException("Trigger interceptor not supported for " + modelTableName);
		}
	}

	@Override
	public void addToIndexes(final Object model)
	{
		final String modelTableName = InterfaceWrapperHelper.getModelTableName(model);
		final List<IESModelIndexer> modelIndexers = modelTableName2indexers.get(modelTableName);
		if (modelIndexers == null)
		{
			return;
		}

		for (final IESModelIndexer indexer : modelIndexers)
		{
			indexer.addToIndex(model);
		}
	}

	private Stream<IndexRequestBuilder> createIndexRequests(final Object sourceModel)
	{
		final String sourceTableName = InterfaceWrapperHelper.getModelTableName(sourceModel);
		final List<IESModelIndexer> modelIndexers = modelTableName2indexers.get(sourceTableName);
		if (modelIndexers == null)
		{
			return Stream.empty();
		}

		return modelIndexers.stream()
				.flatMap(indexer -> indexer.createIndexRequests(sourceModel));
	}

	@Override
	public ListenableActionFuture<BulkResponse> addToIndexes(final Iterator<Object> models)
	{
		final BulkRequestBuilder bulkRequest = elasticsearchClient.prepareBulk();

		asStream(models)
				.flatMap(model -> createIndexRequests(model))
				.forEach(bulkRequest::add);

		return bulkRequest.execute();
	}

	private static final Stream<Object> asStream(final Iterator<Object> models)
	{
		final Spliterator<Object> spliterator = Spliterators.spliteratorUnknownSize(models, Spliterator.ORDERED);
		final boolean parallel = false;
		return StreamSupport.stream(spliterator, parallel);
	}

	@Override
	public void removeFromIndexes(final Object model)
	{
		final String modelTableName = InterfaceWrapperHelper.getModelTableName(model);
		final List<IESModelIndexer> modelIndexers = modelTableName2indexers.get(modelTableName);
		if (modelIndexers == null)
		{
			return;
		}

		for (final IESModelIndexer indexer : modelIndexers)
		{
			indexer.removeFromIndex(model);
		}
	}

	@Override
	public void removeFromIndexesByIds(final String modelTableName, final Collection<String> ids)
	{
		final List<IESModelIndexer> modelIndexers = modelTableName2indexers.get(modelTableName);
		if (modelIndexers == null || modelIndexers.isEmpty())
		{
			return;
		}

		for (final IESModelIndexer indexer : modelIndexers)
		{
			indexer.removeFromIndexByIds(ids);
		}
	}

}
