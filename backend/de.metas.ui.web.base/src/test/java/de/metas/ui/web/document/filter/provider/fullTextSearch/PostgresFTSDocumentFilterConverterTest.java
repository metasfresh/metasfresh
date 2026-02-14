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
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.sql.FilterSql;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.window.model.sql.SqlOptions;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PostgresFTSDocumentFilterConverterTest
{
	private PostgresFTSDocumentFilterConverter converter;
	private SqlDocumentFilterConverterContext context;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		converter = new PostgresFTSDocumentFilterConverter();
		context = SqlDocumentFilterConverterContext.builder().build();
	}

	private DocumentFilter createFTSFilter(@NonNull final String searchText, @NonNull final String tableName)
	{
		return DocumentFilter.builder()
				.filterId(PostgresFTSDocumentFilterDescriptorsProviderFactory.FILTER_ID)
				.addParameter(DocumentFilterParam.ofNameEqualsValue(
						PostgresFTSDocumentFilterDescriptorsProviderFactory.PARAM_SearchText, searchText))
				.addInternalParameter(DocumentFilterParam.ofNameEqualsValue(
						PostgresFTSDocumentFilterDescriptorsProviderFactory.PARAM_TableName, tableName))
				.build();
	}

	private DocumentFilter createFTSFilterWithBlankSearch()
	{
		return DocumentFilter.builder()
				.filterId(PostgresFTSDocumentFilterDescriptorsProviderFactory.FILTER_ID)
				.addParameter(DocumentFilterParam.ofNameEqualsValue(
						PostgresFTSDocumentFilterDescriptorsProviderFactory.PARAM_SearchText, ""))
				.addInternalParameter(DocumentFilterParam.ofNameEqualsValue(
						PostgresFTSDocumentFilterDescriptorsProviderFactory.PARAM_TableName, I_C_BPartner.Table_Name))
				.build();
	}

	@Test
	void canConvert_matchingFilterId()
	{
		assertThat(converter.canConvert(PostgresFTSDocumentFilterDescriptorsProviderFactory.FILTER_ID)).isTrue();
	}

	@Test
	void canConvert_nonMatchingFilterId()
	{
		assertThat(converter.canConvert("some-other-filter")).isFalse();
	}

	@Test
	void blankSearchText_returns_ALLOW_ALL()
	{
		final FilterSql result = converter.getSql(
				createFTSFilterWithBlankSearch(),
				SqlOptions.usingTableName("C_BPartner"),
				context);

		assertThat(result).isSameAs(FilterSql.ALLOW_ALL);
	}

	@Test
	void unsupportedTable_returns_ALLOW_ALL()
	{
		final DocumentFilter filter = createFTSFilter("test search", "C_Order");
		final FilterSql result = converter.getSql(
				filter,
				SqlOptions.usingTableName("C_Order"),
				context);

		assertThat(result).isSameAs(FilterSql.ALLOW_ALL);
	}

	@Test
	void noTableName_returns_ALLOW_ALL()
	{
		final DocumentFilter filter = DocumentFilter.builder()
				.filterId(PostgresFTSDocumentFilterDescriptorsProviderFactory.FILTER_ID)
				.addParameter(DocumentFilterParam.ofNameEqualsValue(
						PostgresFTSDocumentFilterDescriptorsProviderFactory.PARAM_SearchText, "test"))
				.build();

		final FilterSql result = converter.getSql(
				filter,
				SqlOptions.usingTableName("C_BPartner"),
				context);

		assertThat(result).isSameAs(FilterSql.ALLOW_ALL);
	}

	@Nested
	class BPartnerFTS
	{
		@Test
		void whereClause_contains_BPartner_FTS_table()
		{
			final DocumentFilter filter = createFTSFilter("John", I_C_BPartner.Table_Name);
			final FilterSql result = converter.getSql(
					filter,
					SqlOptions.usingTableName("C_BPartner"),
					context);

			assertThat(result).isNotNull();

			final SqlAndParams whereClause = result.getWhereClause();
			assertThat(whereClause).isNotNull();

			final String sql = whereClause.getSql();
			assertThat(sql).contains("C_BPartner_FTS");
			assertThat(sql).contains("C_BPartner.C_BPartner_ID IN (");
			assertThat(sql).contains("websearch_to_tsquery(get_fts_config(), ?)");
		}

		@Test
		void orderBy_contains_ts_rank()
		{
			final DocumentFilter filter = createFTSFilter("John", I_C_BPartner.Table_Name);
			final FilterSql result = converter.getSql(
					filter,
					SqlOptions.usingTableName("C_BPartner"),
					context);

			assertThat(result).isNotNull();

			final SqlAndParams orderBy = result.getOrderBy();
			assertThat(orderBy).isNotNull();

			final String sql = orderBy.getSql();
			assertThat(sql).contains("ts_rank(fts_bpartner.fts_document");
			assertThat(sql).contains("DESC NULLS LAST");
		}

		@Test
		void uses_tableAlias_when_provided()
		{
			final DocumentFilter filter = createFTSFilter("John", I_C_BPartner.Table_Name);
			final FilterSql result = converter.getSql(
					filter,
					SqlOptions.usingTableAlias("bp"),
					context);

			assertThat(result).isNotNull();
			final String sql = result.getWhereClause().getSql();
			assertThat(sql).contains("bp.C_BPartner_ID IN (");
		}
	}

	@Nested
	class ProductFTS
	{
		@Test
		void whereClause_contains_Product_FTS_table()
		{
			final DocumentFilter filter = createFTSFilter("Widget", I_M_Product.Table_Name);
			final FilterSql result = converter.getSql(
					filter,
					SqlOptions.usingTableName("M_Product"),
					context);

			assertThat(result).isNotNull();

			final SqlAndParams whereClause = result.getWhereClause();
			assertThat(whereClause).isNotNull();

			final String sql = whereClause.getSql();
			assertThat(sql).contains("M_Product_FTS");
			assertThat(sql).contains("M_Product.M_Product_ID IN (");
			assertThat(sql).contains("websearch_to_tsquery(get_fts_config(), ?)");
		}

		@Test
		void orderBy_contains_ts_rank()
		{
			final DocumentFilter filter = createFTSFilter("Widget", I_M_Product.Table_Name);
			final FilterSql result = converter.getSql(
					filter,
					SqlOptions.usingTableName("M_Product"),
					context);

			assertThat(result).isNotNull();
			final String sql = result.getOrderBy().getSql();
			assertThat(sql).contains("ts_rank(fts_product.fts_document");
		}
	}

	@Nested
	class InvoiceFTS
	{
		@Test
		void whereClause_contains_Invoice_FTS_table()
		{
			final DocumentFilter filter = createFTSFilter("INV-001", I_C_Invoice.Table_Name);
			final FilterSql result = converter.getSql(
					filter,
					SqlOptions.usingTableName("C_Invoice"),
					context);

			assertThat(result).isNotNull();

			final SqlAndParams whereClause = result.getWhereClause();
			assertThat(whereClause).isNotNull();

			final String sql = whereClause.getSql();
			assertThat(sql).contains("C_Invoice_FTS");
			assertThat(sql).contains("C_Invoice.C_Invoice_ID IN (");
			assertThat(sql).contains("websearch_to_tsquery(get_fts_config(), ?)");
		}

		@Test
		void orderBy_contains_DateInvoiced_DESC()
		{
			final DocumentFilter filter = createFTSFilter("INV-001", I_C_Invoice.Table_Name);
			final FilterSql result = converter.getSql(
					filter,
					SqlOptions.usingTableName("C_Invoice"),
					context);

			assertThat(result).isNotNull();
			final String sql = result.getOrderBy().getSql();
			assertThat(sql).contains("C_Invoice.DateInvoiced DESC");
		}

		@Test
		void orderBy_contains_FTS_match_first_ordering()
		{
			final DocumentFilter filter = createFTSFilter("INV-001", I_C_Invoice.Table_Name);
			final FilterSql result = converter.getSql(
					filter,
					SqlOptions.usingTableName("C_Invoice"),
					context);

			assertThat(result).isNotNull();
			final String sql = result.getOrderBy().getSql();
			// Invoice uses CASE WHEN EXISTS to prioritize FTS matches over ngram matches
			assertThat(sql).contains("CASE WHEN EXISTS");
			assertThat(sql).contains("THEN 0 ELSE 1 END)");
		}

		@Test
		void uses_tableAlias_when_provided()
		{
			final DocumentFilter filter = createFTSFilter("INV-001", I_C_Invoice.Table_Name);
			final FilterSql result = converter.getSql(
					filter,
					SqlOptions.usingTableAlias("inv"),
					context);

			assertThat(result).isNotNull();
			final String whereSql = result.getWhereClause().getSql();
			assertThat(whereSql).contains("inv.C_Invoice_ID IN (");

			final String orderSql = result.getOrderBy().getSql();
			assertThat(orderSql).contains("inv.DateInvoiced DESC");
		}
	}

	@Nested
	class SearchTextParams
	{
		@Test
		void whereClause_binds_searchText_as_parameter()
		{
			final DocumentFilter filter = createFTSFilter("Müller GmbH", I_C_BPartner.Table_Name);
			final FilterSql result = converter.getSql(
					filter,
					SqlOptions.usingTableName("C_BPartner"),
					context);

			assertThat(result).isNotNull();

			final SqlAndParams whereClause = result.getWhereClause();
			// The search text should be bound as a SQL parameter, not inlined
			assertThat(whereClause.getSql()).doesNotContain("Müller GmbH");
			assertThat(whereClause.getSqlParams()).contains("Müller GmbH");
		}
	}
}
