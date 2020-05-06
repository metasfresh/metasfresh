package de.metas.ui.web.document.filter.provider.fullTextSearch;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.compiere.util.DB;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.jgoodies.common.base.Objects;

import de.metas.logging.LogManager;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.document.filter.sql.SqlParamsCollector;
import de.metas.ui.web.window.model.sql.SqlOptions;
import de.metas.util.Check;
import de.metas.util.NumberUtils;

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

public class FullTextSearchSqlDocumentFilterConverter implements SqlDocumentFilterConverter
{
	public static final transient FullTextSearchSqlDocumentFilterConverter instance = new FullTextSearchSqlDocumentFilterConverter();

	static final String FILTER_ID = "full-text-search";

	static final String PARAM_SearchText = "Search";
	static final String PARAM_Context = "Context";

	private static final Logger logger = LogManager.getLogger(FullTextSearchSqlDocumentFilterConverter.class);

	private FullTextSearchSqlDocumentFilterConverter()
	{
	}

	@Override
	public boolean canConvert(final String filterId)
	{
		return Objects.equals(filterId, FILTER_ID);
	}

	@Override
	public String getSql(
			final SqlParamsCollector sqlParamsOut,
			final DocumentFilter filter,
			final SqlOptions sqlOpts,
			final SqlDocumentFilterConverterContext context)
	{
		final String text = filter.getParameterValueAsString(PARAM_SearchText);
		if (Check.isEmpty(text, true))
		{
			return "1=1";
		}

		final FullTextSearchFilterContext ftsContext = filter.getParameterValueAs(PARAM_Context);
		Check.assumeNotNull(ftsContext, "Parameter ftsContext is not null"); // shall not happen
		logger.trace("context: {}", ftsContext);
		final Client elasticsearchClient = ftsContext.getElasticsearchClient();
		final String esIndexName = ftsContext.getEsIndexName();
		final String keyColumnName = ftsContext.getKeyColumnName();
		final String esKeyColumnName = ftsContext.getEsKeyColumnName();

		final QueryBuilder query = QueryBuilders.multiMatchQuery(text, ftsContext.getEsSearchFieldNamesAsArray());
		logger.trace("ES query: {}", query);

		final SearchResponse searchResponse = elasticsearchClient.prepareSearch(esIndexName)
				.setQuery(query)
				.setExplain(logger.isTraceEnabled())
				.get();
		logger.trace("ES response: {}", searchResponse);

		final List<Integer> recordIds = Stream.of(searchResponse.getHits().getHits())
				.map(hit -> extractId(hit, esKeyColumnName))
				.filter(id -> id >= 0)
				.distinct()
				.collect(ImmutableList.toImmutableList());
		logger.trace("Record IDs: {}", recordIds);
		if (recordIds.isEmpty())
		{
			return "1=0";
		}

		final String keyColumnNameFQ = sqlOpts.getTableNameOrAlias() + "." + keyColumnName;
		return DB.buildSqlList(keyColumnNameFQ, recordIds, null);
	}

	private int extractId(final SearchHit hit, final String esKeyColumnName)
	{
		final Map<String, Object> source = hit.getSource();
		return NumberUtils.asInt(source.get(esKeyColumnName), -1);
	}

}
