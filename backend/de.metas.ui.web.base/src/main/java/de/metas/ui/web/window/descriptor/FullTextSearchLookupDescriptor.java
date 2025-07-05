package de.metas.ui.web.window.descriptor;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.descriptor.sql.ISqlLookupDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlForFetchingLookupById;
import de.metas.ui.web.window.model.lookup.IdsToFilter;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFetcher;
import de.metas.util.NumberUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.model.InterfaceWrapperHelper;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
public class FullTextSearchLookupDescriptor implements ISqlLookupDescriptor, LookupDataSourceFetcher
{
	private static final Logger logger = LogManager.getLogger(FullTextSearchLookupDescriptor.class);

	// services
	ElasticsearchClient elasticsearchClient;

	String modelTableName;
	String esIndexName;
	String esKeyColumnName;
	String[] esSearchFieldNames;

	ISqlLookupDescriptor sqlLookupDescriptor;
	LookupDataSource databaseLookup;

	@Builder
	private FullTextSearchLookupDescriptor(
			@NonNull final ElasticsearchClient elasticsearchClient,
			@NonNull final String modelTableName,
			@NonNull final String esIndexName,
			@NonNull final Set<String> esSearchFieldNames,
			@Nullable final ISqlLookupDescriptor sqlLookupDescriptor,
			@NonNull final LookupDataSource databaseLookup)
	{
		this.elasticsearchClient = elasticsearchClient;

		this.modelTableName = modelTableName;

		this.esIndexName = esIndexName;
		esKeyColumnName = InterfaceWrapperHelper.getKeyColumnName(modelTableName);

		this.esSearchFieldNames = esSearchFieldNames.toArray(new String[0]);

		this.sqlLookupDescriptor = sqlLookupDescriptor;
		this.databaseLookup = databaseLookup;
	}

	@Override
	public Optional<String> getTableName()
	{
		return Optional.of(modelTableName);
	}

	@Override
	public LookupDataSourceContext.Builder newContextForFetchingById(final Object id)
	{
		return LookupDataSourceContext.builder(modelTableName)
				.putFilterById(IdsToFilter.ofSingleValue(id));
	}

	@Override
	public LookupValue retrieveLookupValueById(final @NonNull LookupDataSourceContext evalCtx)
	{
		return databaseLookup.findById(evalCtx.getSingleIdToFilterAsObject());
	}

	@Override
	public LookupDataSourceContext.Builder newContextForFetchingList()
	{
		return LookupDataSourceContext.builder(modelTableName);
	}

	@Override
	public LookupValuesPage retrieveEntities(final LookupDataSourceContext evalCtx)
	{
		logger.trace("Retrieving entries for: {}", evalCtx);

		if (evalCtx.isAnyFilter())
		{
			// usually that's the case of dropdowns. In that case we don't want to use elasticsearch.
			logger.trace("Fallback to database lookup because ANY filter was used");
			return databaseLookup.findEntities(evalCtx);
		}

		try
		{
			final Query query = createElasticsearchQuery(evalCtx);
			logger.trace("ES query: {}", query);

			final int maxSize = Math.min(evalCtx.getLimit(100), 100);

			final SearchRequest searchRequest = SearchRequest.of(s -> s
					.index(esIndexName)
					.query(query)
					.size(maxSize)
					.source(src -> src.filter(f -> f.includes(esKeyColumnName)))
			);

			final SearchResponse<Object> searchResponse = elasticsearchClient.search(searchRequest, Object.class);
			logger.trace("ES response: {}", searchResponse);

			final List<Integer> recordIds = searchResponse.hits().hits().stream()
					.map(this::extractId)
					.distinct()
					.collect(ImmutableList.toImmutableList());
			logger.trace("Record IDs: {}", recordIds);

			final LookupValuesList lookupValues = databaseLookup.findByIdsOrdered(recordIds);
			logger.trace("Lookup values: {}", lookupValues);

			return lookupValues.pageByOffsetAndLimit(0, Integer.MAX_VALUE);
		}
		catch (IOException e)
		{
			logger.error("Error executing Elasticsearch query", e);
			// Fallback to database lookup in case of ES error
			return databaseLookup.findEntities(evalCtx);
		}
	}

	private int extractId(@NonNull final Hit<Object> hit)
	{
		if (hit.source() instanceof java.util.Map)
		{
			@SuppressWarnings("unchecked")
			final java.util.Map<String, Object> source = (java.util.Map<String, Object>) hit.source();
			final Object value = source.get(esKeyColumnName);
			return NumberUtils.asInt(value, -1);
		}

		// Fallback: try to get from document ID if source is not available
		final String id = hit.id();
		return NumberUtils.asInt(id, -1);
	}

	private Query createElasticsearchQuery(final LookupDataSourceContext evalCtx)
	{
		final String text = evalCtx.getFilter();
		return MultiMatchQuery.of(m -> m
				.query(text)
				.fields(List.of(esSearchFieldNames))
		)._toQuery();
	}

	@Override
	public boolean isCached()
	{
		return true;
	}

	@Override
	public String getCachePrefix()
	{
		return null;
	}

	@Override
	public Optional<String> getLookupTableName()
	{
		return Optional.of(modelTableName);
	}

	@Override
	public void cacheInvalidate()
	{
		// nothing
	}

	@Override
	public LookupDataSourceFetcher getLookupDataSourceFetcher()
	{
		return this;
	}

	@Override
	public boolean isHighVolume()
	{
		return true;
	}

	@Override
	public LookupSource getLookupSourceType()
	{
		return LookupSource.lookup;
	}

	@Override
	public boolean hasParameters()
	{
		return true;
	}

	@Override
	public boolean isNumericKey()
	{
		return true;
	}

	@Override
	public Set<String> getDependsOnFieldNames()
	{
		return null;
	}

	@Override
	public Optional<WindowId> getZoomIntoWindowId()
	{
		return Optional.empty();
	}

	@Override
	public SqlForFetchingLookupById getSqlForFetchingLookupByIdExpression()
	{
		return sqlLookupDescriptor != null ? sqlLookupDescriptor.getSqlForFetchingLookupByIdExpression() : null;
	}
}