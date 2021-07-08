package org.adempiere.ad.dao.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_Test;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.bpartner.BPartnerId;

public class CompareQueryFilterTest
{
	private Properties ctx;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		ctx = new Properties();
	}

	@Test
	public void test_getSql_NotEquals_NotNullValue()
	{
		final CompareQueryFilter<Object> filter = new CompareQueryFilter<>("MyColumn", Operator.NOT_EQUAL, 1);

		assertSql(filter,
				"(MyColumn <> ? OR MyColumn IS NULL)", // expected SQL
				Arrays.<Object> asList(1) // expected SQL params
		);
	}

	@Test
	public void test_getSql_NotEquals_NullValue()
	{
		final CompareQueryFilter<Object> filter = new CompareQueryFilter<>("MyColumn", Operator.NOT_EQUAL, null);

		assertSql(filter,
				"MyColumn IS NOT NULL", // expected SQL
				Arrays.<Object> asList() // expected SQL params
		);
	}

	private void assertSql(final CompareQueryFilter<Object> filter, final String expectedSql, final List<Object> expectedSqlParams)
	{
		assertThat(filter.getSql())
				.as("sql for " + filter)
				.isEqualTo(expectedSql);

		assertThat(filter.getSqlParams(ctx))
				.as("sqlParams for " + filter)
				.isEqualTo(expectedSqlParams);
	}

	@Test
	public void testCompare_Equals_Null()
	{
		final I_Test model = newInstance(I_Test.class);
		final CompareQueryFilter<Object> filter = new CompareQueryFilter<>(I_Test.COLUMNNAME_C_BPartner_ID, Operator.EQUAL, null);
		assertThat(filter.accept(model)).isTrue();
	}

	@Test
	public void testCompare_Equals_Integer()
	{
		final I_Test model = newInstance(I_Test.class);
		model.setC_BPartner_ID(123);
		final CompareQueryFilter<Object> filter = new CompareQueryFilter<>(I_Test.COLUMNNAME_C_BPartner_ID, Operator.EQUAL, 123);
		assertThat(filter.accept(model)).isTrue();
	}

	@Test
	public void testCompare_Equals_RepoIdAware()
	{
		final I_Test model = newInstance(I_Test.class);
		model.setC_BPartner_ID(123);
		final CompareQueryFilter<Object> filter = new CompareQueryFilter<>(I_Test.COLUMNNAME_C_BPartner_ID, Operator.EQUAL, BPartnerId.ofRepoId(123));
		assertThat(filter.accept(model)).isTrue();
	}

	@Test
	public void testCompare_Equals_Dates()
	{
		final LocalDate dateRef = LocalDate.of(2018, Month.DECEMBER, 20);

		final Object[] dates = new Object[] {
				dateRef,
				TimeUtil.asDate(dateRef),
				TimeUtil.asTimestamp(dateRef),
				TimeUtil.asLocalDateTime(dateRef),
				TimeUtil.asZonedDateTime(dateRef, SystemTime.zoneId()),
				TimeUtil.asInstant(dateRef)
		};

		for (final Object date : dates)
		{
			final I_Test model = newInstance(I_Test.class);
			model.setT_Date(TimeUtil.asTimestamp(date));
			final CompareQueryFilter<Object> filter = new CompareQueryFilter<>(I_Test.COLUMNNAME_T_Date, Operator.EQUAL, date);
			assertThat(filter.accept(model))
					.as("filter=" + filter + ", date=" + date + " (" + date.getClass() + ")")
					.isTrue();
		}
	}

	@Test
	public void testCompare_Equals_Boolean()
	{
		final I_Test model = newInstance(I_Test.class);

		for (final boolean value : Arrays.asList(true, false))
		{
			model.setIsActive(value);
			final CompareQueryFilter<Object> filter = new CompareQueryFilter<>(I_Test.COLUMNNAME_IsActive, Operator.EQUAL, value);
			assertThat(filter.accept(model))
					.as("filter=" + filter + ", value=" + value)
					.isTrue();
		}
	}

	@Test
	public void testCompare_Equals_String()
	{
		final I_Test model = newInstance(I_Test.class);

		model.setDescription("abc");
		final CompareQueryFilter<Object> filter = new CompareQueryFilter<>(I_Test.COLUMNNAME_Description, Operator.EQUAL, "abc");
		assertThat(filter.accept(model))
				.as("filter=" + filter)
				.isTrue();
	}

	@Test
	public void testCompare_Like()
	{
		final I_Test model = newInstance(I_Test.class);
		model.setDescription("abc");

		performTestWithLike(model, "_b_", true);
		performTestWithLike(model, "%b%", true);
		performTestWithLike(model, "abc", true);
		performTestWithLike(model, "b_", false);
		performTestWithLike(model, "b%", false);
		performTestWithLike(model, "abcd", false);
	}

	@Test
	public void testCompare_Like2()
	{
		final I_Test model = newInstance(I_Test.class);
		model.setDescription("zabcad");

		performTestWithLike(model, "abcd", false);
		performTestWithLike(model, "z%", true);
		performTestWithLike(model, "a%", false);
		performTestWithLike(model, "d%", false);
		performTestWithLike(model, "%d", true);
	}

	private void performTestWithLike(
			final I_Test model,
			final String value,
			final boolean expectedResult)
	{
		final CompareQueryFilter<Object> filter = new CompareQueryFilter<>(I_Test.COLUMNNAME_Description, Operator.STRING_LIKE, value);

		assertThat(filter.accept(model))
				.as("filter=" + filter)
				.isEqualTo(expectedResult);
	}

	@Test
	public void testCompare_equals_Booleans_NotSet()
	{
		final CompareQueryFilter<I_Test> processedIsFalse = new CompareQueryFilter<>(I_Test.COLUMNNAME_Processed, Operator.EQUAL, false);
		final I_Test model = newInstance(I_Test.class);
		// model.setProcessed(false); // IMPORTANT: do not set it! that's what we are testing actually
		assertThat(processedIsFalse.accept(model)).isTrue();
	}

	@Test
	public void testCompare_equals_Booleans_False()
	{
		final CompareQueryFilter<I_Test> processedIsFalse = new CompareQueryFilter<>(I_Test.COLUMNNAME_Processed, Operator.EQUAL, false);
		final I_Test model = newInstance(I_Test.class);
		model.setProcessed(false);
		assertThat(processedIsFalse.accept(model)).isTrue();
	}

	@Test
	public void testCompare_equals_Booleans_True()
	{
		final CompareQueryFilter<I_Test> processedIsFalse = new CompareQueryFilter<>(I_Test.COLUMNNAME_Processed, Operator.EQUAL, true);
		final I_Test model = newInstance(I_Test.class);
		model.setProcessed(true);
		assertThat(processedIsFalse.accept(model)).isTrue();
	}

	@Test
	public void test_equals()
	{
		final CompareQueryFilter<I_Test> filter1 = new CompareQueryFilter<>(I_Test.COLUMNNAME_Processed, Operator.EQUAL, true);
		final CompareQueryFilter<I_Test> filter2 = new CompareQueryFilter<>(I_Test.COLUMNNAME_Processed, Operator.EQUAL, true);
		assertThat(filter1).isEqualTo(filter2);

		filter1.getSql(); // trigget sql build
		assertThat(filter1).isEqualTo(filter2);
	}
}
