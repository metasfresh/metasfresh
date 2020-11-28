package de.metas.elasticsearch.indexer.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.elasticsearch.config.ESModelIndexerId;
import de.metas.elasticsearch.config.ESModelIndexerProfile;
import de.metas.elasticsearch.denormalizers.IESModelDenormalizer;
import de.metas.elasticsearch.indexer.ESModelIndexerDataSource;
import de.metas.elasticsearch.indexer.IESIndexerResult;
import de.metas.elasticsearch.indexer.IESModelIndexer;
import de.metas.elasticsearch.trigger.IESModelIndexerTrigger;
import de.metas.elasticsearch.types.ESDataType;
import de.metas.elasticsearch.types.ESIndexType;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.IteratorUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

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

@Immutable
public final class ESModelIndexer implements IESModelIndexer
{
	// services
	private static final transient Logger logger = LogManager.getLogger(ESModelIndexer.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final Client elasticsearchClient;
	private final ObjectMapper jsonObjectMapper;

	@Getter
	private final ESModelIndexerId id;

	@Getter
	private final String modelTableName;
	@Getter(AccessLevel.PRIVATE)
	private final IESModelDenormalizer modelDenormalizer;

	@Getter
	private final ImmutableList<IESModelIndexerTrigger> triggers;

	private final ImmutableList<ESModelIndexer> includedModelIndexers;
	@Getter(AccessLevel.PRIVATE)
	private final String parentAttributeName;
	@Getter(AccessLevel.PRIVATE)
	private final String parentLinkColumnName;

	private final String indexSettingsJson;
	private final String indexStringFullTextSearchAnalyzer;

	@Builder
	private ESModelIndexer(
			@NonNull final Client elasticsearchClient,
			@NonNull final ObjectMapper jsonObjectMapper,
			@NonNull final ESModelIndexerId id,
			@NonNull final String modelTableName,
			@NonNull final IESModelDenormalizer modelDenormalizer,
			@NonNull @Singular final ImmutableList<ESModelIndexer> includedModelIndexers,
			@NonNull @Singular final ImmutableList<IESModelIndexerTrigger> triggers,
			//
			@Nullable final String parentAttributeName,
			@Nullable final String parentLinkColumnName,
			//
			final String indexSettingsJson,
			final String indexStringFullTextSearchAnalyzer)
	{
		this.elasticsearchClient = elasticsearchClient;
		this.jsonObjectMapper = jsonObjectMapper;

		this.id = id;
		this.modelTableName = modelTableName;
		this.modelDenormalizer = modelDenormalizer;
		this.triggers = triggers;

		this.includedModelIndexers = includedModelIndexers;
		this.parentAttributeName = parentAttributeName;
		this.parentLinkColumnName = parentLinkColumnName;

		this.indexSettingsJson = indexSettingsJson;
		this.indexStringFullTextSearchAnalyzer = indexStringFullTextSearchAnalyzer;
	}

	@Override
	public String toString()
	{
		// NOTE: keep it short
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("modelTableName", modelTableName)
				.toString();
	}

	@Override
	public String getIndexName()
	{
		return getId().getIndexName();
	}

	@Override
	public String getIndexType()
	{
		return getId().getIndexType();
	}

	@Override
	public ESModelIndexerProfile getProfile()
	{
		return getId().getProfile();
	}

	@Override
	public void deleteIndex()
	{
		final IndicesAdminClient indices = elasticsearchClient.admin().indices();

		//
		// Create index if does not exist
		final String indexName = getIndexName();
		final boolean indexExists = indices
				.prepareExists(indexName)
				.get()
				.isExists();
		if (!indexExists)
		{
			// nothing to delete
			return;
		}

		final boolean acknowledged = indices.prepareDelete(indexName)
				.get()
				.isAcknowledged();
		if (!acknowledged)
		{
			throw new AdempiereException("Cannot delete index: " + indexName);
		}
	}

	@Override
	public boolean createUpdateIndex()
	{
		// Do nothing if index already exists
		if (checkIndexExists())
		{
			// stop here
			logger.debug("Skip create/update index because index already exists: {}", getIndexName());
			return false;
		}

		//
		//
		createIndex();
		createUpdateIndexTypeMapping();

		return true; // index created now
	}

	private void createIndex()
	{
		final IndicesAdminClient indices = elasticsearchClient.admin().indices();
		final String indexName = getIndexName();

		final CreateIndexRequestBuilder requestBuilder = indices.prepareCreate(indexName);
		if (!Check.isEmpty(indexSettingsJson, true))
		{
			final Settings settings = Settings.builder().loadFromSource(indexSettingsJson, XContentType.JSON).build();
			requestBuilder.setSettings(settings);
		}

		final boolean acknowledged = requestBuilder.get().isAcknowledged();
		if (!acknowledged)
		{
			throw new AdempiereException("Cannot create index: " + indexName);
		}

		logger.debug("Index created: {} \nsettings: {}", indexName, indexSettingsJson);
	}

	private boolean checkIndexExists()
	{
		final IndicesAdminClient indices = elasticsearchClient.admin().indices();
		final String indexName = getIndexName();
		return indices
				.prepareExists(indexName)
				.get()
				.isExists();
	}

	private void createUpdateIndexTypeMapping()
	{
		final IndicesAdminClient indices = elasticsearchClient.admin().indices();

		XContentBuilder indexTypeMappingBuilder = null;
		String mapping = null;
		try
		{
			indexTypeMappingBuilder = XContentFactory.jsonBuilder();

			indexTypeMappingBuilder.startObject();
			appendMapping_DynamicTemplates(indexTypeMappingBuilder);
			appendMapping_Properties(indexTypeMappingBuilder);
			indexTypeMappingBuilder.endObject();

			mapping = toString(indexTypeMappingBuilder);
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
			final String indexName = getIndexName();
			final String indexType = getIndexType();
			final AcknowledgedResponse putMappingResponse = indices.preparePutMapping(indexName)
					.setType(indexType)
					.setSource(mapping)
					.get();
			if (!putMappingResponse.isAcknowledged())
			{
				throw new AdempiereException("Put mapping was not acknowledged for " + this);
			}

			logger.debug("Updated index mapping: {} (type={}) \n {}", indexName, indexType, mapping);
		}
		catch (final AdempiereException e)
		{
			throw e;
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed updating index type mapping: " + ex.getLocalizedMessage(), ex)
					.appendParametersToMessage()
					.setParameter("indexer", this)
					.setParameter("mapping", mapping);
		}
	}
	
	private void appendMapping_DynamicTemplates(final XContentBuilder builder) throws IOException
	{
		final ESModelIndexerProfile profile = getProfile();
		final ESIndexType stringIndexType;
		final String stringAnalyzer;
		if (profile == ESModelIndexerProfile.FULL_TEXT_SEARCH)
		{
			stringIndexType = ESIndexType.Analyzed;
			stringAnalyzer = indexStringFullTextSearchAnalyzer;
		}
		else
		{
			stringIndexType = ESIndexType.NotAnalyzed;
			stringAnalyzer = null;
		}

		//
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
							.field("index", stringIndexType)
							.field("analyzer", stringAnalyzer)
						.endObject()
					.endObject()
					//
				.endObject()// template end
			.endArray(); // dynamic_templates end
		//@formatter:on
	}

	private void appendMapping_Properties(final XContentBuilder builder) throws IOException
	{
		builder.startObject("properties");

		getModelDenormalizer().appendMapping(builder, null);

		for (final ESModelIndexer includedModelIndexer : includedModelIndexers)
		{
			final String parentAttributeName = includedModelIndexer.getParentAttributeName();
			final IESModelDenormalizer includedModelDenormalizer = includedModelIndexer.getModelDenormalizer();

			includedModelDenormalizer.appendMapping(builder, parentAttributeName);
		}

		builder.endObject();
	}

	private static String toStringOrNull(final XContentBuilder builder)
	{
		if (builder == null)
		{
			return null;
		}

		try
		{
			return toString(builder);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	private static String toString(final XContentBuilder builder)
	{
        builder.close();
        return builder.getOutputStream().toString();
	}


	private Stream<IndexRequestBuilder> createIndexRequestsAndStream(final Object model)
	{
		// NOTE: for debugging purposes we are checking it each time
		// createUpdateIndex();

		//
		// model (parent) index requests
		final Stream<IndexRequestBuilder> result = Stream.of(model)
				.map(this::createIndexRequestForModel);

		return result;
	}

	private IndexRequestBuilder createIndexRequestForModel(final Object model)
	{
		final IESModelDenormalizer modelDenormalizer = getModelDenormalizer();

		String esDocumentId = null;
		Map<String, Object> esDocument = null;
		String esDocumentJson = null;
		try
		{
			esDocumentId = modelDenormalizer.extractId(model);

			esDocument = modelDenormalizer.denormalize(model);
			for (final ESModelIndexer includedModelIndexer : includedModelIndexers)
			{
				final List<Map<String, Object>> includedDocuments = denormalizeIncludedForParent(model, includedModelIndexer);
				esDocument.put(includedModelIndexer.getParentAttributeName(), includedDocuments);
			}

			esDocumentJson = jsonObjectMapper.writeValueAsString(esDocument);

			final IndexRequestBuilder indexRequestBuilder = elasticsearchClient.prepareIndex(getIndexName(), getIndexType(), esDocumentId);
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

	private List<Map<String, Object>> denormalizeIncludedForParent(final Object parentModel, final ESModelIndexer includedModelIndexer)
	{
		final int parentId = InterfaceWrapperHelper.getId(parentModel);

		return queryBL.createQueryBuilder(includedModelIndexer.getModelTableName())
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(includedModelIndexer.getParentLinkColumnName(), parentId)
				.create()
				.stream()
				.map(includedModel -> denormalizeIncludedModel(includedModel, includedModelIndexer))
				.collect(ImmutableList.toImmutableList());
	}

	private Map<String, Object> denormalizeIncludedModel(final Object includedModel, final ESModelIndexer includedModelIndexer)
	{
		final IESModelDenormalizer modelDenormalizer = includedModelIndexer.getModelDenormalizer();
		return modelDenormalizer.denormalize(includedModel);
	}

	@Override
	public IESIndexerResult addToIndex(@NonNull final ESModelIndexerDataSource dataSource)
	{
		final BulkRequestBuilder bulkRequest = elasticsearchClient.prepareBulk();

		try
		{
			IteratorUtils.stream(dataSource.getModelsToIndex())
					.flatMap(this::createIndexRequestsAndStream)
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
		final BulkRequestBuilder bulkRequest = elasticsearchClient.prepareBulk();

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
		final String indexName = getIndexName();
		final String indexType = getIndexType();
		final Stream<DeleteRequestBuilder> result = Stream.of(id)
				.map(currentId -> elasticsearchClient.prepareDelete(indexName, indexType, currentId));

		return result;
	}

	@Override
	public Set<String> getFullTextSearchFieldNames()
	{
		final Stream<String> thisLevelfieldNames = getModelDenormalizer().getFullTextSearchFieldNames()
				.stream()
				.map(this::toFieldNameFQ);

		final Stream<String> includedFieldNames = includedModelIndexers.stream()
				.flatMap(includedModelIndexer -> includedModelIndexer.getFullTextSearchFieldNames().stream());

		return Stream.concat(thisLevelfieldNames, includedFieldNames)
				.collect(ImmutableSet.toImmutableSet());
	}

	private String toFieldNameFQ(final String fieldName)
	{
		final String parentAttributeName = getParentAttributeName();
		return parentAttributeName != null ? parentAttributeName + "." + fieldName : fieldName;
	}
}
