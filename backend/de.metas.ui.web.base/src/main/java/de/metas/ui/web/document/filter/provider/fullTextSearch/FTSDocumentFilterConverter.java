/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.document.filter.provider.fullTextSearch;

import de.metas.elasticsearch.model.I_T_ES_FTS_Search_Result;
import de.metas.fulltextsearch.config.FTSConfig;
import de.metas.fulltextsearch.config.FTSFilterDescriptor;
import de.metas.fulltextsearch.query.FTSSearchRequest;
import de.metas.fulltextsearch.query.FTSSearchResult;
import de.metas.fulltextsearch.query.FTSSearchService;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.document.filter.sql.SqlParamsCollector;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.window.model.sql.SqlOptions;
import de.metas.util.Check;

import javax.annotation.Nullable;
import java.util.UUID;

public class FTSDocumentFilterConverter implements SqlDocumentFilterConverter
{
	public static final FTSDocumentFilterConverter instance = new FTSDocumentFilterConverter();

	@Override
	public boolean canConvert(final String filterId)
	{
		return FTSDocumentFilterDescriptorsProviderFactory.FILTER_ID.equals(filterId);
	}

	@Nullable
	@Override
	public String getSql(
			final SqlParamsCollector sqlParamsOut,
			final DocumentFilter filter,
			final SqlOptions sqlOpts,
			final SqlDocumentFilterConverterContext context_NOTUSED)
	{
		final String searchText = filter.getParameterValueAsString(FTSDocumentFilterDescriptorsProviderFactory.PARAM_SearchText);
		if (searchText == null || Check.isBlank(searchText))
		{
			return null;
		}

		final FTSFilterContext ftsContext = filter.getParameterValueAs(FTSDocumentFilterDescriptorsProviderFactory.PARAM_Context);
		Check.assumeNotNull(ftsContext, "Parameter ftsContext is not null"); // shall not happen

		final FTSFilterDescriptor ftsFilterDescriptor = ftsContext.getFilterDescriptor();
		final FTSSearchService searchService = ftsContext.getSearchService();
		final FTSConfig ftsConfig = searchService.getConfigById(ftsFilterDescriptor.getFtsConfigId());

		final FTSSearchResult ftsResult = searchService.search(FTSSearchRequest.builder()
				.searchId(UUID.randomUUID().toString())
				.searchText(searchText)
				.esIndexName(ftsConfig.getEsIndexName())
				.filterDescriptor(ftsFilterDescriptor)
				.build());

		if (ftsResult.isEmpty())
		{
			return "1=0";
		}

		final SqlAndParams.Builder innerWhereClause = SqlAndParams.builder();
		for (final FTSFilterDescriptor.JoinColumn joinColumn : ftsFilterDescriptor.getJoinColumns())
		{
			if (joinColumn.isNullable())
			{
				innerWhereClause
						.append(" AND (")
						.append(joinColumn.getSelectionTableColumnName()).append(" IS NULL")
						.append(" OR ")
						.append(sqlOpts.getTableNameOrAlias()).append(".").append(joinColumn.getTargetTableColumnName())
						.append(" IS NOT DISTINCT FROM ")
						.append(joinColumn.getSelectionTableColumnName())
						.append(")");
			}
			else
			{
				innerWhereClause
						.append(" AND ")
						.append(sqlOpts.getTableNameOrAlias()).append(".").append(joinColumn.getTargetTableColumnName())
						.append("=")
						.append(joinColumn.getSelectionTableColumnName());
			}
		}

		final SqlAndParams sql = SqlAndParams.builder()
				.append("EXISTS (")
				.append(" SELECT 1 FROM ").append(I_T_ES_FTS_Search_Result.Table_Name)
				.append(" WHERE ")
				.append(" ").append(I_T_ES_FTS_Search_Result.COLUMNNAME_Search_UUID).append("=?", ftsResult.getSearchId())
				.append(" ").append(innerWhereClause.build())
				.append(")")
				.build();

		sqlParamsOut.collectAll(sql.getSqlParams());
		return sql.getSql();
	}
}
