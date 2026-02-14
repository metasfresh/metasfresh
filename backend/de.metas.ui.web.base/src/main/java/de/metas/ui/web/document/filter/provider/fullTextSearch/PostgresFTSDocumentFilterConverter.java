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
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_Product;

import javax.annotation.Nullable;
import java.math.BigDecimal;

public class PostgresFTSDocumentFilterConverter implements SqlDocumentFilterConverter
{
	@NonNull private final static String SYSCONFIG_DISTANCE_PARTNER = "de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.Distance.partner";
	@NonNull private final static String SYSCONFIG_NGRAM_LIMIT_PARTNER = "de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.NgramLimit.partner";
	@NonNull private final static String SYSCONFIG_DISTANCE_PRODUCT = "de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.Distance.product";
	@NonNull private final static String SYSCONFIG_NGRAM_LIMIT_PRODUCT = "de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.NgramLimit.product";
	@NonNull private final static String SYSCONFIG_DISTANCE_INVOICE = "de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.Distance.invoice";
	@NonNull private final static String SYSCONFIG_NGRAM_LIMIT_INVOICE = "de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.NgramLimit.invoice";
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
		final String searchText = filter.getParameterValueAsString(PostgresFTSDocumentFilterDescriptorsProviderFactory.PARAM_SearchText, null);
		final String tableName = filter.getParameterValueAsString(PostgresFTSDocumentFilterDescriptorsProviderFactory.PARAM_TableName, null);
		if (Check.isBlank(searchText))
		{
			return FilterSql.ALLOW_ALL;
		}
		else if (I_C_BPartner.Table_Name.equals(tableName))
		{
			return getSql(PostgresFTSFilterRequest.builder()
					.mainTableAlias(sqlOpts.getTableNameOrAlias())
					.searchText(searchText)
					.ftsTableAlias("fts_bpartner")
					.ftsTableName("C_BPartner_FTS")
					.keyColumnName(I_C_BPartner.COLUMNNAME_C_BPartner_ID)
					.distance(sysConfigBL.getBigDecimalValue(SYSCONFIG_DISTANCE_PARTNER, BigDecimal.ONE))
					.fuzzySearchLimit(sysConfigBL.getIntValue(SYSCONFIG_NGRAM_LIMIT_PARTNER, 1000))
					.build()
			);
		}
		else if (I_M_Product.Table_Name.equals(tableName))
		{
			return getSql(PostgresFTSFilterRequest.builder()
					.mainTableAlias(sqlOpts.getTableNameOrAlias())
					.searchText(searchText)
					.ftsTableAlias("fts_product")
					.ftsTableName("M_Product_FTS")
					.keyColumnName(I_M_Product.COLUMNNAME_M_Product_ID)
					.distance(sysConfigBL.getBigDecimalValue(SYSCONFIG_DISTANCE_PRODUCT, BigDecimal.ONE))
					.fuzzySearchLimit(sysConfigBL.getIntValue(SYSCONFIG_NGRAM_LIMIT_PRODUCT, 1000))
					.build()
			);
		}
		else if (I_C_Invoice.Table_Name.equals(tableName))
		{
			return getInvoiceSql(PostgresFTSFilterRequest.builder()
					.mainTableAlias(sqlOpts.getTableNameOrAlias())
					.searchText(searchText)
					.ftsTableAlias("fts_invoice")
					.ftsTableName("C_Invoice_FTS")
					.keyColumnName(I_C_Invoice.COLUMNNAME_C_Invoice_ID)
					.distance(sysConfigBL.getBigDecimalValue(SYSCONFIG_DISTANCE_INVOICE, BigDecimal.ONE))
					.fuzzySearchLimit(sysConfigBL.getIntValue(SYSCONFIG_NGRAM_LIMIT_INVOICE, 1000))
					.build()
			);
		}
		else
		{
			return FilterSql.ALLOW_ALL;
		}
	}

	private FilterSql getSql(@NonNull final PostgresFTSFilterRequest request)
	{
		final String searchText = request.getSearchText();
		final String ftsTableAlias = request.getFtsTableAlias();
		final String ftsTableName = request.getFtsTableName();
		final String keyColumnName = request.getKeyColumnName();
		final String mainTableKeyColumn = request.getMainTableKeyColumn();

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

		final BigDecimal distance = request.getDistance();
		if (distance.compareTo(BigDecimal.ZERO) > 0 && BigDecimal.ONE.compareTo(distance) > 0)
		{
			// https://www.postgresql.org/docs/current/pgtrgm.html (ngram search)
			final int fuzzySearchLimit = request.getFuzzySearchLimit();
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

	private FilterSql getInvoiceSql(@NonNull final PostgresFTSFilterRequest request)
	{
		final String searchText = request.getSearchText();
		final String ftsTableAlias = request.getFtsTableAlias();
		final String ftsTableName = request.getFtsTableName();
		final String keyColumnName = request.getKeyColumnName();
		final String mainTableAlias = request.getMainTableAlias();
		final String mainTableKeyColumn = request.getMainTableKeyColumn();

		// https://www.postgresql.org/docs/current/textsearch.html
		final SqlAndParams.Builder whereClause = SqlAndParams.builder()
				.append(mainTableKeyColumn).append(" IN (")
				.append("SELECT ").append(keyColumnName).append(" FROM ").append(ftsTableName)
				.append(" WHERE fts_document @@ websearch_to_tsquery(get_fts_config(), ?)", searchText);

		final SqlAndParams.Builder orderByClause = SqlAndParams.builder()
				// 1) FTS rows first (0), then n-gram (1)
				.append("(CASE WHEN EXISTS ("
						+ "SELECT 1 FROM ").append(ftsTableName).append(" ").append(ftsTableAlias)
				.append(" WHERE ").append(ftsTableAlias).append(".").append(keyColumnName)
				.append(" = ").append(mainTableKeyColumn)
				.append(" AND ").append(ftsTableAlias).append(".fts_document @@ websearch_to_tsquery(get_fts_config(), ?)")
				.append(") THEN 0 ELSE 1 END)", searchText)
				.append(" ASC, ")
				// 2) Then order by invoice date, newest first
				.append(mainTableAlias).append(".").append(I_C_Invoice.COLUMNNAME_DateInvoiced).append(" DESC");

		final BigDecimal distance = request.getDistance();
		if (distance.compareTo(BigDecimal.ZERO) > 0 && BigDecimal.ONE.compareTo(distance) > 0)
		{
			// https://www.postgresql.org/docs/current/pgtrgm.html (ngram search)
			final int fuzzySearchLimit = request.getFuzzySearchLimit();
			whereClause.append(" UNION ")
					.append("(SELECT ").append(keyColumnName).append(" FROM ").append(ftsTableName)
					.append(" WHERE fts_string <-> ? < ?", searchText, distance)
					.append(" ORDER BY fts_string <-> ? ASC", searchText)
					.append(" LIMIT ?)", fuzzySearchLimit);

			orderByClause.append(", ")
					.append("(SELECT ").append(ftsTableAlias).append(".fts_string <-> ?", searchText)
					.append(" FROM ").append(ftsTableName).append(" ").append(ftsTableAlias)
					.append(" WHERE ").append(ftsTableAlias).append(".").append(keyColumnName)
					.append(" = ").append(mainTableKeyColumn)
					.append(") ASC NULLS LAST");
		}

		whereClause.append(" )");

		return FilterSql.builder().whereClause(whereClause.build()).orderBy(orderByClause.build()).build();
	}

	@Builder
	@Value
	private static class PostgresFTSFilterRequest
	{
		@NonNull String mainTableAlias;
		@NonNull String searchText;
		@NonNull String ftsTableAlias;
		@NonNull String ftsTableName;
		@NonNull String keyColumnName;
		@NonNull BigDecimal distance;
		int fuzzySearchLimit;

		public String getMainTableKeyColumn()
		{
			return mainTableAlias + "." + keyColumnName;
		}
	}
}
