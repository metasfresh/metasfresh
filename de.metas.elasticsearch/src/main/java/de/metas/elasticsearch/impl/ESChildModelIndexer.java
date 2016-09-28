package de.metas.elasticsearch.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.elasticsearch.denormalizers.IESModelDenormalizer;

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

public final class ESChildModelIndexer
{
	private final Client elasticsearchClient;
	private final ObjectMapper jsonObjectMapper;

	private final String indexName;
	private final String indexType;
	private final String parentIndexType;

	private final IChildModelsExtractor<Object, Object> childModelsExtractor;

	private final IESModelDenormalizer childModelDenormalizer;

	<ChildModelType, ParentModelType> ESChildModelIndexer(final ESChildModelIndexerBuilder<ChildModelType, ParentModelType> builder)
	{
		super();
		elasticsearchClient = builder.getElasticsearchClient();
		jsonObjectMapper = builder.getJsonObjectMapper();

		indexName = builder.getIndexName();
		indexType = builder.getIndexType();
		parentIndexType = builder.getParentIndexType();

		childModelDenormalizer = builder.getChildModelDenormalizer();
		childModelsExtractor = builder.getChildModelsExtractor();
	}
	
	public final void appendIndexTypeMapping(XContentBuilder indexTypeMappingBuilder) throws IOException
	{
		//@formatter:off
		indexTypeMappingBuilder
			.startObject(indexType);
		
				//
				// Dynamic template
				ESModelIndexer.appendDynamicTemplatesMappings(indexTypeMappingBuilder);
	
				//
				// Parent
				indexTypeMappingBuilder
					.startObject("_parent")
						.field("type", parentIndexType)
					.endObject();
	
				//
				// properties
				indexTypeMappingBuilder.startObject("properties");
				childModelDenormalizer.appendMapping(indexTypeMappingBuilder, null);
				indexTypeMappingBuilder.endObject(); // properties
				indexTypeMappingBuilder
				//
			.endObject(); // ROOT end
		//@formatter:on
	}


	public Stream<IndexRequestBuilder> createIndexRequestsByParent(final String parentId, final Object parentModel)
	{
		return childModelsExtractor.extractChildModels(parentModel)
				.stream()
				.map(childModel -> createIndexRequest(parentId, childModel));
	}

	private IndexRequestBuilder createIndexRequest(final String parentId, final Object childModel)
	{
		String esDocumentId = null;
		Object esDocument = null;
		String esDocumentJson = null;
		try
		{
			esDocumentId = childModelDenormalizer.extractId(childModel);

			esDocument = childModelDenormalizer.denormalize(childModel);
			esDocumentJson = jsonObjectMapper.writeValueAsString(esDocument);

			final IndexRequestBuilder indexRequestBuilder = elasticsearchClient.prepareIndex(indexName, indexType, esDocumentId);
			indexRequestBuilder.setParent(parentId);
			indexRequestBuilder.setSource(esDocumentJson);
			return indexRequestBuilder;
		}
		catch (final Exception e)
		{
			final String errmsg = "Failed preparing index query for " + childModel
					+ "\n Denormalized: " + childModelDenormalizer
					+ "\n Document ID: " + esDocumentId
					+ "\n Document(JSON): " + esDocumentJson;
			throw new AdempiereException(errmsg, e);
		}
	}

	public DeleteRequestBuilder createDeleteRequest(final String parentId)
	{
		return elasticsearchClient.prepareDelete()
				.setIndex(indexName)
				.setType(indexType)
				.setParent(parentId);
	}

	@FunctionalInterface
	public static interface IChildModelsExtractor<SourceModelType, ModelType>
	{
		List<ModelType> extractChildModels(final SourceModelType parentModel);
	}

}
