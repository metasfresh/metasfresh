/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.sql.FilterSql;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.window.model.sql.SqlOptions;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;

import javax.annotation.Nullable;
import java.math.BigDecimal;

public class PostgresFTSDocumentFilterConverter implements SqlDocumentFilterConverter
{
	@NonNull private final static String SYSCONFIG_DISTANCE = "de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.Distance";
	@NonNull private final static String SYSCONFIG_NGRAM_LIMIT = "de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.NgramLimit";
	@NonNull public static final PostgresFTSDocumentFilterConverter instance = new PostgresFTSDocumentFilterConverter();

	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@Override
	public boolean canConvert(final String filterId)
	{
		return PostgresFTSDocumentFilterDescriptorsProviderFactory.FILTER_ID.equals(filterId);
	}

	@Nullable
	@Override
	public FilterSql getSql(
			@NonNull final DocumentFilter filter,
			@NonNull final SqlOptions sqlOpts,
			@NonNull final SqlDocumentFilterConverterContext context)
	{
		final String searchText = filter.getParameterValueAsString(FTSDocumentFilterDescriptorsProviderFactory.PARAM_SearchText, null);
		if (Check.isBlank(searchText))
		{
			return FilterSql.ALLOW_ALL;
		}

		final String mainTableAlias = sqlOpts.getTableNameOrAlias();
		final String ftsTableAlias = "fts_bpartner";
		final String ftsTableName = "C_BPartner_FTS";
		final String keyColumnName = "C_BPartner_ID";
		final String mainTableKeyColumn = mainTableAlias + "." + keyColumnName;

		// https://www.postgresql.org/docs/current/textsearch.html
		final SqlAndParams.Builder whereClause = SqlAndParams.builder()
				.append(mainTableKeyColumn).append(" IN (")
				.append("SELECT ").append(keyColumnName).append(" FROM ").append(ftsTableName)
				.append(" WHERE fts_document @@ websearch_to_tsquery(get_fts_config(), ?)", searchText);

		final SqlAndParams.Builder orderByClause = SqlAndParams.builder()
				.append("(SELECT ts_rank(").append(ftsTableAlias).append(".fts_document, websearch_to_tsquery(get_fts_config(), ?))", searchText)
				.append(" FROM ").append(ftsTableName).append(" ").append(ftsTableAlias)
				.append(" WHERE ").append(ftsTableAlias).append(".").append(keyColumnName).append(" = ").append(mainTableKeyColumn)
				.append(") DESC NULLS LAST");

		final BigDecimal distance = sysConfigBL.getBigDecimalValue(SYSCONFIG_DISTANCE, BigDecimal.ONE);
		if (distance.compareTo(BigDecimal.ZERO) > 0 && BigDecimal.ONE.compareTo(distance) > 0)
		{
			// https://www.postgresql.org/docs/current/pgtrgm.html (ngram search)
			final int fuzzySearchLimit = sysConfigBL.getIntValue(SYSCONFIG_NGRAM_LIMIT, 1000);
			whereClause.append(" UNION ")
					.append("(SELECT ").append(keyColumnName).append(" FROM ").append(ftsTableName)
					.append(" WHERE fts_string <-> ? < ?", searchText, distance)
					.append(" ORDER BY fts_string <-> ? ASC", searchText)
					.append(" LIMIT ?)", fuzzySearchLimit);

			orderByClause.append(", ")
					.append("(SELECT ").append(ftsTableAlias).append(".fts_string <-> ?", searchText)
					.append(" FROM ").append(ftsTableName).append(" ").append(ftsTableAlias)
					.append(" WHERE ").append(ftsTableAlias).append(".").append(keyColumnName).append(" = ").append(mainTableKeyColumn)
					.append(") ASC NULLS LAST");
		}

		whereClause.append(" )");

		return FilterSql.builder().whereClause(whereClause.build()).orderBy(orderByClause.build()).build();

	}
}
