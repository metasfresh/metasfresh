package de.metas.ui.web.window.descriptor;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.model.InterfaceWrapperHelper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.descriptor.sql.ISqlLookupDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlForFetchingLookupById;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFetcher;
import de.metas.util.NumberUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
	// services
	private static final Logger logger = LogManager.getLogger(FullTextSearchLookupDescriptor.class);
	private Client elasticsearchClient;

	private final String modelTableName;
	private final String esIndexName;
	private final String esKeyColumnName;
	private final String[] esSearchFieldNames;

	private final ISqlLookupDescriptor sqlLookupDescriptor;
	private final LookupDataSource databaseLookup;

	@Builder
	private FullTextSearchLookupDescriptor(
			@NonNull final Client elasticsearchClient,
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

		this.esSearchFieldNames = esSearchFieldNames.toArray(new String[esSearchFieldNames.size()]);

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
		return LookupDataSourceContext.builder(modelTableName).putFilterById(id);
	}

	@Override
	public LookupValue retrieveLookupValueById(final LookupDataSourceContext evalCtx)
	{
		return databaseLookup.findById(evalCtx.getIdToFilter());
	}

	@Override
	public LookupDataSourceContext.Builder newContextForFetchingList()
	{
		return LookupDataSourceContext.builder(modelTableName);
	}

	@Override
	public LookupValuesList retrieveEntities(final LookupDataSourceContext evalCtx)
	{
		logger.trace("Retrieving entries for: {}", evalCtx);

		if (evalCtx.isAnyFilter())
		{
			// usually that's the case of dropdowns. In that case we don't want to use elasticsearch.
			logger.trace("Fallback to database lookup because ANY filter was used");
			return databaseLookup.findEntities(evalCtx);
		}

		final QueryBuilder query = createElasticsearchQuery(evalCtx);
		logger.trace("ES query: {}", query);

		int maxSize = Math.min(evalCtx.getLimit(100), 100);
		final SearchResponse searchResponse = elasticsearchClient.prepareSearch(esIndexName)
				.setQuery(query)
				.setExplain(logger.isTraceEnabled())
				.setSize(maxSize)
				.addField(esKeyColumnName)
				.get();
		logger.trace("ES response: {}", searchResponse);

		final List<Integer> recordIds = Stream.of(searchResponse.getHits().getHits())
				.map(hit -> extractId(hit))
				.distinct()
				.collect(ImmutableList.toImmutableList());
		logger.trace("Record IDs: {}", recordIds);

		final LookupValuesList lookupValues = databaseLookup.findByIdsOrdered(recordIds);
		logger.trace("Lookup values: {}", lookupValues);

		return lookupValues;
	}

	private int extractId(@NonNull final SearchHit hit)
	{
		final Object value = hit
				.getFields()
				.get(esKeyColumnName)
				.getValue();
		return NumberUtils.asInt(value, -1);
	}

	private QueryBuilder createElasticsearchQuery(final LookupDataSourceContext evalCtx)
	{
		final String text = evalCtx.getFilter();
		return QueryBuilders.multiMatchQuery(text, esSearchFieldNames);
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
