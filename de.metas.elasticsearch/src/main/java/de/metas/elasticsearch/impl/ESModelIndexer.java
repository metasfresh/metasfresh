package de.metas.elasticsearch.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.elasticsearch.IESModelIndexer;
import de.metas.elasticsearch.denormalizers.IESModelDenormalizer;
import de.metas.elasticsearch.types.ESDataType;
import de.metas.elasticsearch.types.ESIndexType;
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

public final class ESModelIndexer implements IESModelIndexer
{
	private static final Logger logger = LogManager.getLogger(ESModelIndexer.class);

	private final Client elasticsearchClient;
	private final ObjectMapper jsonObjectMapper;

	private final String indexName;
	private final String indexType;

	private final String modelTableName;
	private final IESModelDenormalizer modelDenormalizer;

	private final List<ESChildModelIndexer> childIndexers;

	/* package */ <ModelType> ESModelIndexer(final ESModelIndexerBuilder<ModelType> builder)
	{
		super();

		elasticsearchClient = builder.getElasticsearchClient();
		jsonObjectMapper = builder.getJsonObjectMapper();

		indexName = builder.getIndexName();
		indexType = builder.getIndexType();
		modelTableName = builder.getModelTableName();
		modelDenormalizer = builder.getModelDenormalizer();

		//
		// Child indexers
		final List<ESChildModelIndexerBuilder<?, ModelType>> childIndexerBuilders = builder.getChildIndexerBuilders();
		if (childIndexerBuilders == null || childIndexerBuilders.isEmpty())
		{
			childIndexers = ImmutableList.of();
		}
		else
		{
			final ImmutableList.Builder<ESChildModelIndexer> childIndexers = ImmutableList.builder();
			for (final ESChildModelIndexerBuilder<?, ModelType> childIndexerBuilder : childIndexerBuilders)
			{
				final ESChildModelIndexer childIndexer = new ESChildModelIndexer(childIndexerBuilder);
				childIndexers.add(childIndexer);
			}
			this.childIndexers = childIndexers.build();
		}
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("indexName", indexName)
				.add("indexType", indexType)
				.add("denormalizer", modelDenormalizer)
				.add("childIndexers", childIndexers.isEmpty() ? null : childIndexers)
				.toString();
	}

	@Override
	public String getIndexName()
	{
		return indexName;
	}

	@Override
	public String getIndexType()
	{
		return indexType;
	}

	@Override
	public String getModelTableName()
	{
		return modelTableName;
	}

	private IESModelDenormalizer getModelDenormalizer()
	{
		return modelDenormalizer;
	}

	private Client getClient()
	{
		return elasticsearchClient;
	}

	@Override
	public void createUpdateIndex()
	{
		final IndicesAdminClient indices = getClient().admin().indices();

		//
		// Create index if does not exist
		final boolean indexExists = indices
				.prepareExists(indexName)
				.get()
				.isExists();
		if (!indexExists)
		{
			final boolean acknowledged = indices
					.prepareCreate(indexName)
					.get()
					.isAcknowledged();
			if (!acknowledged)
			{
				throw new AdempiereException("Cannot create index: " + indexName);
			}
		}

		XContentBuilder indexMappingBuilder = null;
		String mapping = null;
		try
		{
			indexMappingBuilder = XContentFactory.jsonBuilder();
			indexMappingBuilder
					.startObject() // ROOT
					.startObject("mappings");
			
			appendIndexMapping(indexMappingBuilder);

			indexMappingBuilder
					.endObject() // mappings
					.endObject(); // ROOT

			mapping = indexMappingBuilder.string();
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Failed building index mapping: " + this
					+ "\n Mapping so far: " + toStringOrNull(indexMappingBuilder) //
					, ex);
		}

		//
		// Update index type mapping
		try
		{
			final PutMappingResponse putMappingResponse = indices.preparePutMapping(indexName)
					.setSource(mapping)
					.get();
			if (!putMappingResponse.isAcknowledged())
			{
				throw new AdempiereException("Put mapping was not acknowledged for " + this);
			}
		}
		catch (final AdempiereException e)
		{
			throw e;
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed updating index type mapping: " + this, ex);
		}
	}

	private final void appendIndexMapping(final XContentBuilder indexMappingBuilder) throws IOException
	{
		appendIndexTypeMapping(indexMappingBuilder);

		for (final ESChildModelIndexer childIndexer : childIndexers)
		{
			childIndexer.appendIndexTypeMapping(indexMappingBuilder);
		}
	}

	private static final String toStringOrNull(final XContentBuilder builder)
	{
		if (builder == null)
		{
			return null;
		}

		try
		{
			return builder.string();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	private final void appendIndexTypeMapping(final XContentBuilder indexTypeMappingBuilder) throws IOException
	{
		//@formatter:off
		indexTypeMappingBuilder
			.startObject(indexType);

				//
				// Dynamic template
				appendDynamicTemplatesMappings(indexTypeMappingBuilder);

				//
				// properties
				indexTypeMappingBuilder.startObject("properties");
				getModelDenormalizer().appendMapping(indexTypeMappingBuilder, null);
				indexTypeMappingBuilder.endObject(); // properties
				indexTypeMappingBuilder
				//
			.endObject(); // ROOT end
		//@formatter:on
	}

	/* package */static final void appendDynamicTemplatesMappings(final XContentBuilder builder) throws IOException
	{
		//@formatter:off
		builder
			.startArray("dynamic_templates")
				.startObject() // template begin
					//
					// String types: don't analyze them by default
					.startObject(ESDataType.String.getEsTypeAsString())
						.field("match_mapping_type", ESDataType.String.getEsTypeAsString())
						.startObject("mapping")
							.field("type", ESDataType.String.getEsTypeAsString())
							.field("index", ESIndexType.NotAnalyzed.getEsTypeAsString())
						.endObject()
					.endObject()
					//
				.endObject()// template end
			.endArray(); // dynamic_templates end
		//@formatter:on
	}

	private String extractModelId(final Object model)
	{
		final String id = modelDenormalizer.extractId(model);
		return id;
	}

	@Override
	public Stream<IndexRequestBuilder> createIndexRequests(final Object model)
	{
		// FIXME: for debugging purposes we are checking it each time
		createUpdateIndex();

		//
		// model (parent) index requests
		final Stream<IndexRequestBuilder> modelIndexRequests = Stream.of(model)
				.map(indexer -> createIndexRequestForModel(model));

		//
		// Child index requests (if any)
		final String parentId = extractModelId(model);
		final Stream<IndexRequestBuilder> childIndexRequests = childIndexers.stream()
				.flatMap(childIndexer -> childIndexer.createIndexRequestsByParent(parentId, model));

		return Stream.concat(modelIndexRequests, childIndexRequests);
	}

	private IndexRequestBuilder createIndexRequestForModel(final Object model)
	{
		String esDocumentId = null;
		Object esDocument = null;
		String esDocumentJson = null;
		try
		{
			esDocumentId = modelDenormalizer.extractId(model);

			esDocument = modelDenormalizer.denormalize(model);
			esDocumentJson = jsonObjectMapper.writeValueAsString(esDocument);

			final IndexRequestBuilder indexRequestBuilder = getClient().prepareIndex(indexName, indexType, esDocumentId);
			indexRequestBuilder.setSource(esDocumentJson);
			return indexRequestBuilder;
		}
		catch (final Exception e)
		{
			final String errmsg = "Failed preparing index query for " + model
					+ "\n Denormalized: " + modelDenormalizer
					+ "\n Document ID: " + esDocumentId
					+ "\n Document(JSON): " + esDocumentJson;
			throw new AdempiereException(errmsg, e);
		}
	}

	@Override
	public void addToIndex(final Object model)
	{
		try
		{
			final BulkRequestBuilder bulkRequest = elasticsearchClient.prepareBulk();
			createIndexRequests(model)
					.forEach(bulkRequest::add);

			final BulkResponse response = bulkRequest.execute().actionGet();
			if (response.hasFailures())
			{
				throw new ElasticsearchException(response.buildFailureMessage());
			}
		}
		catch (final AdempiereException ex)
		{
			throw ex;
		}
		catch (final Exception ex)
		{
			final String errmsg = "Failed indexing: " + model
					+ "\n Indexer: " + this;
			throw new AdempiereException(errmsg, ex);
		}
	}

	@Override
	public void removeFromIndex(final Object sourceModel)
	{
		final String id = extractModelId(sourceModel);
		removeFromIndexByIds(ImmutableList.of(id));
	}

	@Override
	public void removeFromIndexByIds(final Collection<String> ids)
	{
		final Client client = getClient();
		final BulkRequestBuilder bulk = client.prepareBulk();

		createDeleteRequests(ids)
				.forEach(bulk::add);

		final BulkResponse response = bulk.execute().actionGet();
		logger.debug("Deleted {}", response);
	}

	private Stream<DeleteRequestBuilder> createDeleteRequests(final Collection<String> ids)
	{
		return ids.stream()
				.flatMap(this::createDeleteRequests);
	}

	private Stream<DeleteRequestBuilder> createDeleteRequests(final String id)
	{
		Check.assumeNotEmpty(id, "id is not empty");

		//
		// model (parent) delete requests
		final Stream<DeleteRequestBuilder> modelDeleteRequests = Stream.of(id)
				.map(currentId -> elasticsearchClient.prepareDelete(indexName, indexType, currentId));

		//
		// Child delete requests (if any)
		final Stream<DeleteRequestBuilder> childDeleteRequests = childIndexers.stream()
				.map(childIndexer -> childIndexer.createDeleteRequest(id));

		return Stream.concat(modelDeleteRequests, childDeleteRequests);

	}
}
