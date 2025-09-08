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

package de.metas.ui.web.handlingunits.filter;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.sql.FilterSql;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverters;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.window.model.sql.SqlOptions;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.RecursiveComparisonAssert;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class HUIdsSqlDocumentFilterConverterTest
{
	private static final DocumentFilter NULL_NOISE_FILTER = null;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(HUReservationRepository.class);
	}

	private RecursiveComparisonAssert<?> assertThatGetSqlOf(
			@NonNull final HUIdsFilterData huIdsFilterData,
			@Nullable final DocumentFilter noiseFilter)
	{
		final SqlDocumentFilterConverter converters = createConverters();

		final DocumentFilterList filters = Stream.of(HUIdsFilterHelper.createFilter(huIdsFilterData), noiseFilter)
				.filter(Objects::nonNull)
				.collect(DocumentFilterList.toDocumentFilterList());
		System.out.println("------------------------------------------------------------------------------------------------------------------");
		System.out.println("Converting " + filters);

		final FilterSql result = converters.getSql(
				filters,
				SqlOptions.usingTableAlias("dummyTableAlias"),
				SqlDocumentFilterConverterContext.builder().build());
		System.out.println("Got result " + result);

		return assertThat(result)
				.usingRecursiveComparison();
	}

	private static SqlDocumentFilterConverter createConverters()
	{
		return SqlDocumentFilterConverters.listBuilder()
				.converter(HUIdsFilterHelper.SQL_DOCUMENT_FILTER_CONVERTER)
				.build()
				.withFallback(createNoiseSqlConverter());
	}

	private static SqlDocumentFilterConverter createNoiseSqlConverter()
	{
		return new SqlDocumentFilterConverter()
		{
			@Override
			public boolean canConvert(final String filterId)
			{
				return true;
			}

			@Nullable
			@Override
			public FilterSql getSql(final DocumentFilter filter, final SqlOptions sqlOpts, final SqlDocumentFilterConverterContext context)
			{
				final String noiseSql = filter.getParameterValueAsString("NOISE_SQL_PARAM", null);
				assert noiseSql != null;
				return FilterSql.ofWhereClause(noiseSql);
			}
		};
	}

	@SuppressWarnings("SameParameterValue")
	private static DocumentFilter noiseFilter(@NonNull final String noiseSql)
	{
		return DocumentFilter.singleParameterFilter("NOISE_FILTER_ID", "NOISE_SQL_PARAM", DocumentFilterParam.Operator.EQUAL, noiseSql);
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
			assertThatGetSqlOf(data, NULL_NOISE_FILTER)
					.isEqualTo(FilterSql.ALLOW_ALL);

			assertThatGetSqlOf(data, noiseFilter("some noise"))
					.isEqualTo(FilterSql.ofWhereClause("/* NOISE_FILTER_ID */ (some noise)"));
		}

		@Test
		void acceptAll_then_mustID1()
		{
			final HUIdsFilterData data = HUIdsFilterData.acceptAll();
			data.mustHUIds(ImmutableSet.of(HuId.ofRepoId(1)));

			assertThatGetSqlOf(data, NULL_NOISE_FILTER)
					.isEqualTo(FilterSql.builder()
							.whereClause(null)
							.alwaysIncludeSql(FilterSql.RecordsToAlwaysIncludeSql.ofColumnNameAndRecordIds("M_HU_ID", 1))
							.build());

			assertThatGetSqlOf(data, noiseFilter("SomeColumn=1234"))
					.isEqualTo(FilterSql.builder()
							.whereClause(SqlAndParams.of("/* NOISE_FILTER_ID */ (SomeColumn=1234)"))
							.alwaysIncludeSql(FilterSql.RecordsToAlwaysIncludeSql.ofColumnNameAndRecordIds("M_HU_ID", 1))
							.build());
		}

		@Test
		void acceptAll_then_mustID1_then_shallNotID1()
		{
			final HUIdsFilterData data = HUIdsFilterData.acceptAll();
			data.mustHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			data.shallNotHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			// NOTE: the order in which we call the mustHUIds and shallNotHUIds is SUPER important!!!

			assertThatGetSqlOf(data, NULL_NOISE_FILTER)
					.isEqualTo(FilterSql.builder()
							.whereClause(SqlAndParams.of("/* huIds */ ((NOT (M_HU_ID=?)))", 1))
							.alwaysIncludeSql(null)
							.build());

			assertThatGetSqlOf(data, noiseFilter("SomeColumn=1234"))
					.isEqualTo(FilterSql.builder()
							.whereClause(SqlAndParams.of("/* huIds */ ((NOT (M_HU_ID=?)))"
									+ "\n AND /* NOISE_FILTER_ID */ (SomeColumn=1234)", 1))
							.alwaysIncludeSql(null)
							.build());
		}

		@Test
		void acceptAll_then_shallNotID1_then_mustID1()
		{
			final HUIdsFilterData data = HUIdsFilterData.acceptAll();
			data.shallNotHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			data.mustHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			// NOTE: the order in which we call the mustHUIds and shallNotHUIds is SUPER important!!!

			assertThatGetSqlOf(data, NULL_NOISE_FILTER)
					.isEqualTo(FilterSql.builder()
							.whereClause(null)
							.alwaysIncludeSql(FilterSql.RecordsToAlwaysIncludeSql.ofColumnNameAndRecordIds("M_HU_ID", 1))
							.build());

			assertThatGetSqlOf(data, noiseFilter("SomeColumn=1234"))
					.isEqualTo(FilterSql.builder()
							.whereClause(SqlAndParams.of("/* NOISE_FILTER_ID */ (SomeColumn=1234)"))
							.alwaysIncludeSql(FilterSql.RecordsToAlwaysIncludeSql.ofColumnNameAndRecordIds("M_HU_ID", 1))
							.build());
		}

	}

	@Nested
	class ofHUIds
	{
		@Nested
		class acceptNone
		{
			/**
			 * Verifies that if {@link HUIdsFilterHelper#createFilter(java.util.Collection)} is called with an empty list,
			 * then the filter's SQL does <b>not</b> select every single f**king HU on this planet.
			 */
			@Test
			void acceptNone_test()
			{
				assertThatGetSqlOf(HUIdsFilterData.ofHUIds(ImmutableList.of()), NULL_NOISE_FILTER)
						.isEqualTo(FilterSql.ALLOW_NONE);

				assertThatGetSqlOf(HUIdsFilterData.ofHUIds(ImmutableList.of()), noiseFilter("SomeColumn=1234"))
						.isEqualTo(FilterSql.ALLOW_NONE);

			}

			@Test
			void acceptNone_then_mustID1()
			{
				final HUIdsFilterData data = HUIdsFilterData.ofHUIds(ImmutableSet.of());
				data.mustHUIds(ImmutableSet.of(HuId.ofRepoId(1)));

				assertThatGetSqlOf(data, NULL_NOISE_FILTER)
						.isEqualTo(FilterSql.builder()
								.whereClause(SqlAndParams.of("/* huIds */ ((M_HU_ID=?))", 1))
								.alwaysIncludeSql(FilterSql.RecordsToAlwaysIncludeSql.ofColumnNameAndRecordIds("M_HU_ID", 1))
								.build());

				assertThatGetSqlOf(data, noiseFilter("SomeColumn=1234"))
						.isEqualTo(FilterSql.builder()
								.whereClause(SqlAndParams.of("/* huIds */ ((M_HU_ID=?))"
										+ "\n AND /* NOISE_FILTER_ID */ (SomeColumn=1234)", 1))
								.alwaysIncludeSql(FilterSql.RecordsToAlwaysIncludeSql.ofColumnNameAndRecordIds("M_HU_ID", 1))
								.build());
			}

			@Test
			void acceptNone_then_mustID1_then_shallNotID1()
			{
				final HUIdsFilterData data = HUIdsFilterData.ofHUIds(ImmutableSet.of());
				data.mustHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
				data.shallNotHUIds(ImmutableSet.of(HuId.ofRepoId(1)));

				assertThatGetSqlOf(data, NULL_NOISE_FILTER)
						.isEqualTo(FilterSql.builder()
								.whereClause(SqlAndParams.of("1=0"))
								.alwaysIncludeSql(null)
								.build());

				assertThatGetSqlOf(data, noiseFilter("SomeColumn=1234"))
						.isEqualTo(FilterSql.builder()
								.whereClause(SqlAndParams.of("1=0"))
								.alwaysIncludeSql(null)
								.build());
			}

			@Test
			void acceptNone_then_shallNotID1_then_mustID1()
			{
				final HUIdsFilterData data = HUIdsFilterData.ofHUIds(ImmutableSet.of());
				data.shallNotHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
				data.mustHUIds(ImmutableSet.of(HuId.ofRepoId(1)));

				assertThatGetSqlOf(data, NULL_NOISE_FILTER)
						.isEqualTo(FilterSql.builder()
								.whereClause(SqlAndParams.of("/* huIds */ ((M_HU_ID=?))", 1))
								.alwaysIncludeSql(FilterSql.RecordsToAlwaysIncludeSql.ofColumnNameAndRecordIds("M_HU_ID", 1))
								.build());

				assertThatGetSqlOf(data, noiseFilter("SomeColumn=1234"))
						.isEqualTo(FilterSql.builder()
								.whereClause(SqlAndParams.of("/* huIds */ ((M_HU_ID=?))"
										+ "\n AND /* NOISE_FILTER_ID */ (SomeColumn=1234)", 1))
								.alwaysIncludeSql(FilterSql.RecordsToAlwaysIncludeSql.ofColumnNameAndRecordIds("M_HU_ID", 1))
								.build());
			}
		}

		@Nested
		class acceptSome
		{
			@Test
			void acceptID1()
			{
				final HUIdsFilterData data = HUIdsFilterData.ofHUIds(ImmutableSet.of(HuId.ofRepoId(1)));

				assertThatGetSqlOf(data, NULL_NOISE_FILTER)
						.isEqualTo(FilterSql.builder()
								.whereClause(SqlAndParams.of("/* huIds */ ((M_HU_ID=?))", 1))
								.build());

				assertThatGetSqlOf(data, noiseFilter("SomeColumn=1234"))
						.isEqualTo(FilterSql.builder()
								.whereClause(SqlAndParams.of("/* huIds */ ((M_HU_ID=?))"
										+ "\n AND /* NOISE_FILTER_ID */ (SomeColumn=1234)", 1))
								.build());
			}

			@Test
			void acceptID1_then_mustID2()
			{
				final HUIdsFilterData data = HUIdsFilterData.ofHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
				data.mustHUIds(ImmutableSet.of(HuId.ofRepoId(2)));

				assertThatGetSqlOf(data, NULL_NOISE_FILTER)
						.isEqualTo(FilterSql.builder()
								.whereClause(SqlAndParams.of("/* huIds */ ((M_HU_ID IN (?,?)))", 1, 2))
								.alwaysIncludeSql(FilterSql.RecordsToAlwaysIncludeSql.ofColumnNameAndRecordIds("M_HU_ID", 2))
								.build());

				assertThatGetSqlOf(data, noiseFilter("SomeColumn=1234"))
						.isEqualTo(FilterSql.builder()
								.whereClause(SqlAndParams.of("/* huIds */ ((M_HU_ID IN (?,?)))"
										+ "\n AND /* NOISE_FILTER_ID */ (SomeColumn=1234)", 1, 2))
								.alwaysIncludeSql(FilterSql.RecordsToAlwaysIncludeSql.ofColumnNameAndRecordIds("M_HU_ID", 2))
								.build());
			}

			@Test
			void acceptID1_then_mustID2_then_shallNotID1()
			{
				final HUIdsFilterData data = HUIdsFilterData.ofHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
				data.mustHUIds(ImmutableSet.of(HuId.ofRepoId(2)));
				data.shallNotHUIds(ImmutableSet.of(HuId.ofRepoId(1)));

				assertThatGetSqlOf(data, NULL_NOISE_FILTER)
						.isEqualTo(FilterSql.builder()
								.whereClause(SqlAndParams.of("/* huIds */ ((M_HU_ID=?))", 2))
								.alwaysIncludeSql(FilterSql.RecordsToAlwaysIncludeSql.ofColumnNameAndRecordIds("M_HU_ID", 2))
								.build());

				assertThatGetSqlOf(data, noiseFilter("SomeColumn=1234"))
						.isEqualTo(FilterSql.builder()
								.whereClause(SqlAndParams.of("/* huIds */ ((M_HU_ID=?))"
										+ "\n AND /* NOISE_FILTER_ID */ (SomeColumn=1234)", 2))
								.alwaysIncludeSql(FilterSql.RecordsToAlwaysIncludeSql.ofColumnNameAndRecordIds("M_HU_ID", 2))
								.build());
			}
		}
	}
}
