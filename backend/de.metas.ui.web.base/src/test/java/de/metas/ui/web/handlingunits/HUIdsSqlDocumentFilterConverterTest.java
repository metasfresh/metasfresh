package de.metas.ui.web.handlingunits;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.ui.web.document.filter.sql.FilterSql;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.window.model.sql.SqlOptions;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.ObjectAssert;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class HUIdsSqlDocumentFilterConverterTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(HUReservationRepository.class);
	}

	private ObjectAssert<SqlAndParams> assertThatGetSqlOf(final HUIdsFilterData huIdsFilterData)
	{
		final SqlDocumentFilterConverterContext context = SqlDocumentFilterConverterContext.EMPTY;
		final FilterSql sql = HUIdsFilterHelper.SQL_DOCUMENT_FILTER_CONVERTER.getSql(
				HUIdsFilterHelper.createFilter(huIdsFilterData),
				SqlOptions.usingTableAlias("dummyTableAlias"),
				context);

		assert sql != null;
		return assertThat(sql.getWhereClause());
	}

	@Nested
	class acceptAll
	{
		@Test
		void acceptAll_test()
		{
			assertAcceptAll(HUIdsFilterData.acceptAll());
		}

		private void assertAcceptAll(final HUIdsFilterData data)
		{
			assertThatGetSqlOf(data)
					.isNull();
		}

		@Test
		void acceptAll_then_addMustHUIds()
		{
			final HUIdsFilterData data = HUIdsFilterData.acceptAll();
			data.mustHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			assertThatGetSqlOf(data)
					.isEqualTo(SqlAndParams.of("(M_HU_ID=?)", 1));
		}

		@Test
		void acceptAll_then_addMustHUIds_then_addShallNotHUIds()
		{
			final HUIdsFilterData data = HUIdsFilterData.acceptAll();
			data.mustHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			data.shallNotHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			assertAcceptAll(data);
		}
	}

	@Nested
	class ofHUIds
	{
		/**
		 * Verifies that if {@link HUIdsFilterHelper#createFilter(java.util.Collection)} is called with an empty list,
		 * then the filter's SQL does <b>not</b> select every single f**king HU on this planet.
		 */
		@Test
		void initialEmpty()
		{
			assertThatGetSqlOf(HUIdsFilterData.ofHUIds(ImmutableList.of()))
					.isEqualTo(FilterSql.ALLOW_NONE.getWhereClause());
		}

		@Test
		void initialEmpty_then_addMustHUIds()
		{
			final HUIdsFilterData data = HUIdsFilterData.ofHUIds(ImmutableSet.of());
			data.mustHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			assertThatGetSqlOf(data)
					.isEqualTo(SqlAndParams.of("(M_HU_ID=?)", 1));
		}

		@Test
		void initialNotEmpty()
		{
			final HUIdsFilterData data = HUIdsFilterData.ofHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			assertThatGetSqlOf(data)
					.isEqualTo(SqlAndParams.of("(M_HU_ID=?)", 1));
		}

		@Test
		void initialNotEmpty_then_addMustHUIds()
		{
			final HUIdsFilterData data = HUIdsFilterData.ofHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			data.mustHUIds(ImmutableSet.of(HuId.ofRepoId(2)));
			assertThatGetSqlOf(data)
					.isEqualTo(SqlAndParams.of("(M_HU_ID IN (?,?))", 1, 2));
		}

		@Test
		void initialNotEmpty_then_addMustHUIds_then_addShallNotHUIds()
		{
			final HUIdsFilterData data = HUIdsFilterData.ofHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			data.mustHUIds(ImmutableSet.of(HuId.ofRepoId(2)));
			data.shallNotHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			assertThatGetSqlOf(data)
					.isEqualTo(SqlAndParams.of("(M_HU_ID=?) AND (NOT (M_HU_ID=?))", 2, 1));
		}
	}
}
