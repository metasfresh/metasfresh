package de.metas.ui.web.handlingunits;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.ui.web.document.filter.DocumentFilterList;
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
	private CompositeSqlDocumentFilterConverter converters;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(HUReservationRepository.class);

		converters = CompositeSqlDocumentFilterConverter.builder()
				.converter(HUIdsFilterHelper.SQL_DOCUMENT_FILTER_CONVERTER)
				.converter(new OtherFilterConverter())
				.build();
	}

	private ObjectAssert<SqlAndParams> assertThatGetSqlOf(final DocumentFilterList filters)
	{
		final SqlAndParams sql = converters.getSql(
				filters,
				SqlOptions.usingTableAlias("dummyTableAlias"),
				SqlDocumentFilterConverterContext.EMPTY)
				.toSqlAndParams()
				.orElse(null);

		return assertThat(sql);
	}

	@Nested
	class acceptAll
	{
		@Test
		void acceptAll_test()
		{
			final HUIdsFilterData data = HUIdsFilterData.acceptAll();
			final DocumentFilterList filter = DocumentFilterList.of(
					HUIdsFilterHelper.createFilter(data),
					OtherFilterConverter.newFilter());

			assertThatGetSqlOf(filter).isEqualTo(SqlAndParams.of(
					"((OTHER_FILTER_CLAUSE) OR (OTHER_FILTER_INCLUDE_CLAUSE)) AND NOT (OTHER_FILTER_EXCLUDE_CLAUSE)"));
		}

		@Test
		void acceptAll_then_addMustHUIds()
		{
			final HUIdsFilterData data = HUIdsFilterData.acceptAll();
			data.mustHUIds(ImmutableSet.of(HuId.ofRepoId(1)));

			final DocumentFilterList filter = DocumentFilterList.of(
					HUIdsFilterHelper.createFilter(data),
					OtherFilterConverter.newFilter());

			assertThatGetSqlOf(filter).isEqualTo(SqlAndParams.of(
					"((OTHER_FILTER_CLAUSE) OR (M_HU_ID=?) OR (OTHER_FILTER_INCLUDE_CLAUSE)) AND NOT (OTHER_FILTER_EXCLUDE_CLAUSE)",
					1));
		}

		@Test
		void acceptAll_then_mustId1_then_shallNot1()
		{
			final HUIdsFilterData data = HUIdsFilterData.acceptAll();
			data.mustHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			data.shallNotHUIds(ImmutableSet.of(HuId.ofRepoId(1)));

			final DocumentFilterList filter = DocumentFilterList.of(
					HUIdsFilterHelper.createFilter(data),
					OtherFilterConverter.newFilter());

			assertThatGetSqlOf(filter).isEqualTo(SqlAndParams.of(
					"((OTHER_FILTER_CLAUSE) OR (OTHER_FILTER_INCLUDE_CLAUSE)) AND NOT ((M_HU_ID=?) OR (OTHER_FILTER_EXCLUDE_CLAUSE))",
					1));
		}

		@Test
		void acceptAll_then_mustId1_then_shallNot2()
		{
			final HUIdsFilterData data = HUIdsFilterData.acceptAll();
			data.mustHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			data.shallNotHUIds(ImmutableSet.of(HuId.ofRepoId(2)));

			final DocumentFilterList filter = DocumentFilterList.of(
					HUIdsFilterHelper.createFilter(data),
					OtherFilterConverter.newFilter());

			assertThatGetSqlOf(filter).isEqualTo(SqlAndParams.of(
				"((OTHER_FILTER_CLAUSE) OR (M_HU_ID=?) OR (OTHER_FILTER_INCLUDE_CLAUSE)) AND NOT ((M_HU_ID=?) OR (OTHER_FILTER_EXCLUDE_CLAUSE))",
				1,2));
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
			final HUIdsFilterData data = HUIdsFilterData.ofHUIds(ImmutableList.of());
			final DocumentFilterList filter = DocumentFilterList.of(
					HUIdsFilterHelper.createFilter(data),
					OtherFilterConverter.newFilter());

			assertThatGetSqlOf(filter).isEqualTo(SqlAndParams.of(
					"1=0"));
		}

		@Test
		void initialEmpty_then_addMustHUIds()
		{
			final HUIdsFilterData data = HUIdsFilterData.ofHUIds(ImmutableSet.of());
			data.mustHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			final DocumentFilterList filter = DocumentFilterList.of(
					HUIdsFilterHelper.createFilter(data),
					OtherFilterConverter.newFilter());

			assertThatGetSqlOf(filter).isEqualTo(SqlAndParams.of(
					"((M_HU_ID=?) OR (OTHER_FILTER_INCLUDE_CLAUSE)) AND NOT (OTHER_FILTER_EXCLUDE_CLAUSE)",
					1));
		}

		@Test
		void initialNotEmpty()
		{
			final HUIdsFilterData data = HUIdsFilterData.ofHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			final DocumentFilterList filter = DocumentFilterList.of(
					HUIdsFilterHelper.createFilter(data),
					OtherFilterConverter.newFilter());

			assertThatGetSqlOf(filter).isEqualTo(SqlAndParams.of(
					"(((M_HU_ID=?) AND (OTHER_FILTER_CLAUSE)) OR (OTHER_FILTER_INCLUDE_CLAUSE)) AND NOT (OTHER_FILTER_EXCLUDE_CLAUSE)", 1));
		}

		@Test
		void initialNotEmpty_then_addMustHUIds()
		{
			final HUIdsFilterData data = HUIdsFilterData.ofHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			data.mustHUIds(ImmutableSet.of(HuId.ofRepoId(2)));

			final DocumentFilterList filter = DocumentFilterList.of(
					HUIdsFilterHelper.createFilter(data),
					OtherFilterConverter.newFilter());

			assertThatGetSqlOf(filter).isEqualTo(SqlAndParams.of(
					"(((M_HU_ID=?) AND (OTHER_FILTER_CLAUSE)) OR (M_HU_ID=?) OR (OTHER_FILTER_INCLUDE_CLAUSE)) AND NOT (OTHER_FILTER_EXCLUDE_CLAUSE)",
					1, 2));
		}

		@Test
		void initialNotEmpty_then_addMustHUIds_then_addShallNotHUIds()
		{
			final HUIdsFilterData data = HUIdsFilterData.ofHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			data.mustHUIds(ImmutableSet.of(HuId.ofRepoId(2)));
			data.shallNotHUIds(ImmutableSet.of(HuId.ofRepoId(1)));

			final DocumentFilterList filter = DocumentFilterList.of(
					HUIdsFilterHelper.createFilter(data),
					OtherFilterConverter.newFilter());

			assertThatGetSqlOf(filter).isEqualTo(SqlAndParams.of(
					"(((M_HU_ID=?) AND (OTHER_FILTER_CLAUSE)) OR (M_HU_ID=?) OR (OTHER_FILTER_INCLUDE_CLAUSE)) AND NOT ((M_HU_ID=?) OR (OTHER_FILTER_EXCLUDE_CLAUSE))",
					1, 2, 1));
		}
	}
}
