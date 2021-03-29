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

package de.metas.elasticsearch.indexer.engine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.elasticsearch.config.ESModelIndexerId;
import de.metas.elasticsearch.config.ESModelIndexerProfile;
import de.metas.elasticsearch.config.ESTextAnalyzer;
import de.metas.elasticsearch.denormalizers.IESModelDenormalizer;
import de.metas.elasticsearch.indexer.source.ESModelIndexerDataSource;
import de.metas.elasticsearch.indexer.source.ESModelToIndex;
import de.metas.elasticsearch.trigger.IESModelIndexerTrigger;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

@Immutable
public final class ESModelIndexer
{
	// services
	private static final transient Logger logger = LogManager.getLogger(ESModelIndexer.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final RestHighLevelClient elasticsearchClient;
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
	private final ESTextAnalyzer indexStringFullTextSearchAnalyzer;

	@Builder
	private ESModelIndexer(
			@NonNull final RestHighLevelClient elasticsearchClient,
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
			final ESTextAnalyzer indexStringFullTextSearchAnalyzer)
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

	public String getIndexName()
	{
		return getId().getIndexName();
	}

	public String getIndexType()
	{
		return getId().getIndexType();
	}

	public ESModelIndexerProfile getProfile()
	{
		return getId().getProfile();
	}

	public void deleteIndex()
	{
		final String indexName = getIndexName();
		final DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
		deleteIndexRequest.indicesOptions(IndicesOptions.lenientExpandOpen());

		final AcknowledgedResponse response;
		try
		{
			response = elasticsearchClient
					.indices()
					.delete(deleteIndexRequest, RequestOptions.DEFAULT);
		}
		catch (final IOException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
		if (!response.isAcknowledged())
		{
			throw new AdempiereException("Cannot delete index: " + indexName);
		}
	}

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
		final String indexName = getIndexName();

		final CreateIndexRequest request = new CreateIndexRequest(indexName);

		if (!Check.isEmpty(indexSettingsJson, true))
		{
			final Settings settings = Settings.builder().loadFromSource(indexSettingsJson, XContentType.JSON).build();
			request.settings(settings);
		}

		final CreateIndexResponse createIndexResponse;
		try
		{
			createIndexResponse = elasticsearchClient
					.indices()
					.create(request, RequestOptions.DEFAULT);
		}
		catch (final IOException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}

		if (!createIndexResponse.isAcknowledged())
		{
			throw new AdempiereException("Cannot create index: " + indexName);
		}

		logger.debug("Index created: {} \nsettings: {}", indexName, indexSettingsJson);
	}

	private boolean checkIndexExists()
	{
		final String indexName = getIndexName();
		final GetIndexRequest request = new GetIndexRequest(indexName);

		try
		{
			return elasticsearchClient
					.indices()
					.exists(request, RequestOptions.DEFAULT);
		}
		catch (final IOException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	private void createUpdateIndexTypeMapping()
	{
		final String indexType = getIndexType();
		XContentBuilder indexTypeMappingBuilder = null;
		final String mapping;
		try
		{
			indexTypeMappingBuilder = XContentFactory.jsonBuilder();
			indexTypeMappingBuilder.startObject();
			//appendMapping_DynamicTemplates(indexTypeMappingBuilder);
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

			final PutMappingRequest request = new PutMappingRequest(indexName);
			request.source(mapping, XContentType.JSON);

			final AcknowledgedResponse putMappingResponse = elasticsearchClient.indices().putMapping(request, RequestOptions.DEFAULT);

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

	// private void appendMapping_DynamicTemplates(final XContentBuilder builder)
	// {
		// final ESModelIndexerProfile profile = getProfile();
		// final ESIndexType stringIndexType;
		// final ESTextAnalyzer stringAnalyzer;
		// if (profile == ESModelIndexerProfile.FULL_TEXT_SEARCH)
		// {
		// 	stringIndexType = ESIndexType.Analyzed;
		// 	stringAnalyzer = indexStringFullTextSearchAnalyzer;
		// }
		// else
		// {
		// 	stringIndexType = ESIndexType.NotAnalyzed;
		// 	stringAnalyzer = null;
		// }
		//
		// //
		// //@formatter:off
		// builder
		// 	.startArray("dynamic_templates")
		// 		.startObject() // template begin
		// 			//
		// 			// String types: don't analyze them by default
		// 			.startObject(ESDataType.String.getEsTypeAsString())
		// 				.field("match_mapping_type", ESDataType.String.getEsTypeAsString())
		// 				.startObject("mapping")
		// 					.field("type", ESDataType.String.getEsTypeAsString())
		// 					.field("index", stringIndexType.getEsTypeAsString())
		// 					.field("analyzer", stringAnalyzer != null ? stringAnalyzer.getAnalyzerAsString() : null)
		// 				.endObject()
		// 			.endObject()
		// 			//
		// 		.endObject()// template end
		// 	.endArray(); // dynamic_templates end
		// //@formatter:on
	// }

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

	@Nullable
	private static String toStringOrNull(@Nullable final XContentBuilder builder)
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

	private Stream<IndexRequest> createIndexRequestsAndStream(final ESModelToIndex model)
	{
		// NOTE: for debugging purposes we are checking it each time
		// createUpdateIndex();

		//
		// model (parent) index requests
		return Stream.of(model).map(this::createIndexRequestForModel);
	}

	private IndexRequest createIndexRequestForModel(final ESModelToIndex model)
	{
		final IESModelDenormalizer modelDenormalizer = getModelDenormalizer();

		String esDocumentId = null;
		final Map<String, Object> esDocument;
		String esDocumentJson = null;
		try
		{
			esDocumentId = modelDenormalizer.extractId(model);

			esDocument = modelDenormalizer.denormalizeModel(model);
			for (final ESModelIndexer includedModelIndexer : includedModelIndexers)
			{
				final List<Map<String, Object>> includedDocuments = denormalizeIncludedForParent(model, includedModelIndexer);
				esDocument.put(includedModelIndexer.getParentAttributeName(), includedDocuments);
			}

			esDocumentJson = jsonObjectMapper.writeValueAsString(esDocument);

			return new IndexRequest(getIndexName())
					.id(esDocumentId)
					//.type(getIndexType()) "types are in the process of being removed
					.source(esDocumentJson, XContentType.JSON);
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
				.map(ESModelToIndex::ofObject)
				.map(includedModel -> denormalizeIncludedModel(includedModel, includedModelIndexer))
				.collect(ImmutableList.toImmutableList());
	}

	private static Map<String, Object> denormalizeIncludedModel(final ESModelToIndex includedModel, final ESModelIndexer includedModelIndexer)
	{
		return includedModelIndexer.getModelDenormalizer().denormalizeModel(includedModel);
	}

	public IESIndexerResult addToIndex(@NonNull final ESModelIndexerDataSource dataSource)
	{
		createUpdateIndex();

		final BulkRequest bulkRequest = new BulkRequest();

		try
		{
			dataSource.streamModelsToIndex()
					.flatMap(this::createIndexRequestsAndStream)
					.forEach(bulkRequest::add);

			if (bulkRequest.numberOfActions() <= 0)
			{
				return IESIndexerResult.NULL;
			}

			final BulkResponse bulkResponse = elasticsearchClient.bulk(bulkRequest, RequestOptions.DEFAULT);

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

	public IESIndexerResult removeFromIndexByIds(final Collection<String> ids)
	{
		if(!checkIndexExists())
		{
			return IESIndexerResult.NULL;
		}

		final BulkRequest bulkRequest = new BulkRequest();

		try
		{
			createDeleteRequests(ids)
					.forEach(bulkRequest::add);

			final BulkResponse bulkResponse = elasticsearchClient.bulk(bulkRequest, RequestOptions.DEFAULT);
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

	private Stream<DeleteRequest> createDeleteRequests(final Collection<String> ids)
	{
		return ids.stream()
				.flatMap(this::createDeleteRequests);
	}

	private Stream<DeleteRequest> createDeleteRequests(final String id)
	{
		Check.assumeNotEmpty(id, "id is not empty");

		//
		// model (parent) delete requests
		final String indexName = getIndexName();

		return Stream.of(new DeleteRequest(indexName)
				//.type(getIndexType()) "Deprecated - Types are in the process of being removed."
				.id(id));
	}

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
