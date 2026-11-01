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

import de.metas.organization.OrgId;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.sql.FilterSql;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.window.model.sql.SqlOptions;
import de.metas.util.Services;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pins the SQL that {@link PostgresFTSDocumentFilterConverter} emits per table.
 * <p>
 * Unit layer only (no real DB, no tsvector/trigger/GIN): it asserts the generated SQL text + params.
 * The real full-text behaviour is proven end-to-end against the migrated stack, see the ES-drop e2e verification plan.
 * <p>
 * The table to search is carried in the internal filter parameter {@value #PARAM_TableName}
 * (same name the inner_silence_uat port uses). The literal is used on purpose instead of a constant of the
 * production class, so that this test fails on its assertions (not on compilation) while the M_Product branch is absent.
 */
public class PostgresFTSDocumentFilterConverterTest
{
	private static final String PARAM_TableName = "TableName";
	private static final String PARAM_Search = "Search";

	private static final String SYSCONFIG_PREFIX = "de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.";
	private static final String KEY_DISTANCE_PRODUCT = SYSCONFIG_PREFIX + "Distance.product";
	private static final String KEY_NGRAM_LIMIT_PRODUCT = SYSCONFIG_PREFIX + "NgramLimit.product";
	private static final String KEY_DISTANCE_PARTNER = SYSCONFIG_PREFIX + "Distance.partner";
	private static final String KEY_NGRAM_LIMIT_PARTNER = SYSCONFIG_PREFIX + "NgramLimit.partner";
	/** the old, un-suffixed keys: must NOT be read any more */
	private static final String KEY_DISTANCE_LEGACY = SYSCONFIG_PREFIX + "Distance";
	private static final String KEY_NGRAM_LIMIT_LEGACY = SYSCONFIG_PREFIX + "NgramLimit";

	private static final String SEARCH_TEXT = "chiquita bananen";

	private ISysConfigBL sysConfigBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		sysConfigBL = Services.get(ISysConfigBL.class);

		// distinct values per key, so that reading the wrong key is detectable.
		// Legacy keys = 1 would disable the fuzzy (UNION) part altogether, so a converter that still reads them emits no UNION.
		setSysConfig(KEY_DISTANCE_PRODUCT, "0.5");
		setSysConfig(KEY_NGRAM_LIMIT_PRODUCT, "7");
		setSysConfig(KEY_DISTANCE_PARTNER, "0.4");
		setSysConfig(KEY_NGRAM_LIMIT_PARTNER, "11");
		setSysConfig(KEY_DISTANCE_LEGACY, "1");
		setSysConfig(KEY_NGRAM_LIMIT_LEGACY, "999");
	}

	private void setSysConfig(final String name, final String value)
	{
		sysConfigBL.setValue(name, value, ClientId.SYSTEM, OrgId.ANY);
	}

	private static FilterSql convert(final String tableName)
	{
		return new PostgresFTSDocumentFilterConverter().getSql(
				DocumentFilter.builder()
						.filterId(PostgresFTSDocumentFilterDescriptorsProviderFactory.FILTER_ID)
						.addParameter(DocumentFilterParam.ofNameEqualsValue(PARAM_Search, SEARCH_TEXT))
						.addInternalParameter(DocumentFilterParam.ofNameEqualsValue(PARAM_TableName, tableName))
						.build(),
				SqlOptions.usingTableAlias("p"),
				SqlDocumentFilterConverterContext.builder().build());
	}

	@Test
	void mProduct_emitsMProductFtsSql_withProductSysconfigKeys()
	{
		final FilterSql result = convert("M_Product");

		final SqlAndParams where = result.getWhereClause();
		assertThat(where).as("whereClause").isNotNull();
		assertThat(where.getSql())
				.contains("p.M_Product_ID IN (")
				.contains("SELECT M_Product_ID FROM M_Product_FTS WHERE fts_document @@ websearch_to_tsquery(get_fts_config(), ?)")
				.doesNotContain("C_BPartner_FTS");

		// fuzzy (n-gram) part is present because Distance.product=0.5 is in (0,1); NgramLimit.product=7 is the LIMIT
		assertThat(where.getSql()).contains(" UNION ").contains("fts_string <-> ? < ?").contains("LIMIT ?");
		final Object[] params = where.getSqlParamsArray();
		assertThat(params).hasSize(5);
		assertThat(params[0]).isEqualTo(SEARCH_TEXT);
		assertThat(params[1]).isEqualTo(SEARCH_TEXT);
		assertThat((BigDecimal)params[2]).as("distance read from ...Distance.product").isEqualByComparingTo("0.5");
		assertThat(params[3]).isEqualTo(SEARCH_TEXT);
		assertThat(params[4]).as("limit read from ...NgramLimit.product").isEqualTo(7);

		final SqlAndParams orderBy = result.getOrderBy();
		assertThat(orderBy).as("orderBy").isNotNull();
		assertThat(orderBy.getSql())
				.contains("ts_rank(fts_product.fts_document, websearch_to_tsquery(get_fts_config(), ?))")
				.contains("FROM M_Product_FTS fts_product")
				.contains("fts_product.M_Product_ID = p.M_Product_ID");
	}

	@Test
	void cBPartner_readsPartnerSysconfigKeys_notLegacyKeys()
	{
		final FilterSql result = convert("C_BPartner");

		final SqlAndParams where = result.getWhereClause();
		assertThat(where).as("whereClause").isNotNull();
		assertThat(where.getSql())
				.contains("p.C_BPartner_ID IN (")
				.contains("SELECT C_BPartner_ID FROM C_BPartner_FTS WHERE fts_document @@ websearch_to_tsquery(get_fts_config(), ?)")
				.contains(" UNION ");
		final Object[] params = where.getSqlParamsArray();
		assertThat(params).hasSize(5);
		assertThat((BigDecimal)params[2]).as("distance read from ...Distance.partner").isEqualByComparingTo("0.4");
		assertThat(params[4]).as("limit read from ...NgramLimit.partner").isEqualTo(11);
	}
}
