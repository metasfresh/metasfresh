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
import lombok.NonNull;

import javax.annotation.Nullable;

public class PostgresFTSDocumentFilterConverter implements SqlDocumentFilterConverter
{
	private final static String SYSCONFIG_DISTANCE = "0.96"; //TODO

	public static final PostgresFTSDocumentFilterConverter instance = new PostgresFTSDocumentFilterConverter();

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

		final SqlAndParams whereClause = SqlAndParams.builder()
				.append("EXISTS (")
				.append(" SELECT 1 FROM ").append(ftsTableName).append(" ").append(ftsTableAlias)
				.append(" WHERE ").append(ftsTableAlias).append(".C_BPartner_ID = ").append(mainTableAlias).append(".C_BPartner_ID")
				.append(" AND (")
				.append(ftsTableAlias).append(".fts_document @@ websearch_to_tsquery(get_fts_config(), ?)", searchText) // https://www.postgresql.org/docs/current/textsearch.html
				.append(" OR ")
				.append(ftsTableAlias).append(".fts_string <-> ? < ", searchText).append(SYSCONFIG_DISTANCE) // https://www.postgresql.org/docs/current/pgtrgm.html (ngram search)
				.append(" )")
				.append(")")
				.build();

		return FilterSql.builder().whereClause(whereClause).build();

	}
}
