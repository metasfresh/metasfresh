package de.metas.elasticsearch.indexer.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.concurrent.Immutable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.collections.IteratorUtils;
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

import de.metas.elasticsearch.denormalizers.IESModelDenormalizer;
import de.metas.elasticsearch.indexer.IESIndexerResult;
import de.metas.elasticsearch.indexer.IESModelIndexer;
import de.metas.elasticsearch.trigger.IESModelIndexerTrigger;
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

@Immutable
public final class ESModelIndexer implements IESModelIndexer
{
	private static final transient Logger logger = LogManager.getLogger(ESModelIndexer.class);

	private final Client _elasticsearchClient;
	private final ObjectMapper jsonObjectMapper;

	private final String _id;
	private final String _indexName;
	private final String _indexType;

	private final String _modelTableName;
	private final IESModelDenormalizer _modelDenormalizer;

	private final List<IESModelIndexerTrigger> triggers;

	/* package */ <ModelType> ESModelIndexer(final ESModelIndexerBuilder builder)
	{
		super();

		_elasticsearchClient = builder.getElasticsearchClient();
		jsonObjectMapper = builder.getJsonObjectMapper();

		_id = builder.getId();
		_indexName = builder.getIndexName();
		_indexType = builder.getIndexType();
		_modelTableName = builder.getModelTableName();
		_modelDenormalizer = builder.getModelDenormalizer();
		triggers = ImmutableList.copyOf(builder.getTriggers());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("id", _id)
				// .add("indexName", _indexName)
				// .add("indexType", _indexType)
				.add("modelTableName", _modelTableName)
				.add("denormalizer", _modelDenormalizer)
				.toString();
	}

	@Override
	public String getId()
	{
		return _id;
	}

	@Override
	public String getIndexName()
	{
		return _indexName;
	}

	@Override
	public String getIndexType()
	{
		return _indexType;
	}

	@Override
	public String getModelTableName()
	{
		return _modelTableName;
	}

	private IESModelDenormalizer getModelDenormalizer()
	{
		return _modelDenormalizer;
	}

	private Client getClient()
	{
		return _elasticsearchClient;
	}

	@Override
	public void createUpdateIndex()
	{
		final IndicesAdminClient indices = getClient().admin().indices();

		//
		// Create index if does not exist
		final String indexName = getIndexName();
		final boolean indexExists = indices
				.prepareExists(indexName)
				.get()
				.isExists();
		if (indexExists)
		{
			// stop here
			return;
		}
		else
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

		//
		//
		createUpdateIndexTypeMapping();
	}

	private final void createUpdateIndexTypeMapping()
	{
		XContentBuilder indexTypeMappingBuilder = null;
		String mapping = null;
		try
		{
			indexTypeMappingBuilder = XContentFactory.jsonBuilder();

			//@formatter:off
			indexTypeMappingBuilder
				.startObject();

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

			mapping = indexTypeMappingBuilder.string();
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed building index mapping: " + this
					+ "\n Mapping so far: " + toStringOrNull(indexTypeMappingBuilder) //
					, ex);
		}

		//
		// Update index type mapping
		try
		{
			final IndicesAdminClient indices = getClient().admin().indices();
			final PutMappingResponse putMappingResponse = indices.preparePutMapping(getIndexName())
					.setType(getIndexType())
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

	static final String toStringOrNull(final XContentBuilder builder)
	{
		if (builder == null)
		{
			return null;
		}

		try
		{
			return builder.string();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	private Stream<IndexRequestBuilder> createIndexRequests(final Object model)
	{
		// NOTE: for debugging purposes we are checking it each time
		// createUpdateIndex();

		//
		// model (parent) index requests
		final Stream<IndexRequestBuilder> result = Stream.of(model)
				.map(indexer -> createIndexRequestForModel(model));

		return result;
	}

	private IndexRequestBuilder createIndexRequestForModel(final Object model)
	{
		final IESModelDenormalizer modelDenormalizer = getModelDenormalizer();

		String esDocumentId = null;
		Object esDocument = null;
		String esDocumentJson = null;
		try
		{
			esDocumentId = modelDenormalizer.extractId(model);
			esDocument = modelDenormalizer.denormalize(model);
			esDocumentJson = jsonObjectMapper.writeValueAsString(esDocument);

			final IndexRequestBuilder indexRequestBuilder = getClient().prepareIndex(getIndexName(), getIndexType(), esDocumentId);
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
	public IESIndexerResult addToIndex(final Iterator<?> models)
	{
		final BulkRequestBuilder bulkRequest = getClient().prepareBulk();

		try
		{
			IteratorUtils.stream(models)
					.flatMap(model -> createIndexRequests(model))
					.forEach(bulkRequest::add);

			if (bulkRequest.numberOfActions() <= 0)
			{
				return IESIndexerResult.NULL;
			}

			final BulkResponse bulkResponse = bulkRequest.execute().actionGet();
			final IESIndexerResult esResponse = ESIndexerResult.of(bulkResponse);

			logger.debug("Added {}", esResponse);
			return esResponse;
		}
		catch (final AdempiereException ex)
		{
			throw ex;
		}
		catch (final Exception ex)
		{
			final int count = bulkRequest.numberOfActions();
			final String errmsg = "Failed indexing " + count + " models because: " + ex.getLocalizedMessage()
					+ "\n Indexer: " + this;
			throw new AdempiereException(errmsg, ex);
		}
	}

	@Override
	public IESIndexerResult removeFromIndexByIds(final Collection<String> ids)
	{
		final BulkRequestBuilder bulkRequest = getClient().prepareBulk();

		try
		{
			createDeleteRequests(ids)
					.forEach(bulkRequest::add);

			final BulkResponse bulkResponse = bulkRequest.execute().actionGet();
			final IESIndexerResult esResponse = ESIndexerResult.of(bulkResponse);

			logger.debug("Deleted {}", esResponse);
			return esResponse;
		}
		catch (final AdempiereException ex)
		{
			throw ex;
		}
		catch (final Exception ex)
		{
			final int count = bulkRequest.numberOfActions();
			final String errmsg = "Failed removing " + count + " models because: " + ex.getLocalizedMessage()
					+ "\n Indexer: " + this;
			throw new AdempiereException(errmsg, ex);
		}
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
		final Client elasticsearchClient = getClient();
		final String indexName = getIndexName();
		final String indexType = getIndexType();
		final Stream<DeleteRequestBuilder> result = Stream.of(id)
				.map(currentId -> elasticsearchClient.prepareDelete(indexName, indexType, currentId));

		return result;
	}

	@Override
	public List<IESModelIndexerTrigger> getTriggers()
	{
		return triggers;
	}
}
