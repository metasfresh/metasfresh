package org.adempiere.ad.dao.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.annotations.VisibleForTesting;

import de.metas.adempiere.model.I_C_Order;
import de.metas.document.engine.DocStatus;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

public class InArrayQueryFilterTest
{
	private final Properties ctx = null; // Context is not used in InArrayQueryFilter

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void test_ColumnName_NULL()
	{
		final String columnName = null;

		// Expect exception:
		assertThatThrownBy(() -> new InArrayQueryFilter<>(columnName, Collections.emptyList()))
				.isInstanceOf(NullPointerException.class)
				.hasMessage("columnName");
	}

	@Test
	public void test_StandardCases()
	{
		assertFilter(
				// Input
				"MyColumnName",
				Arrays.<Object> asList("Value1"),
				// Expected output
				"MyColumnName=?",
				Arrays.<Object> asList("Value1"));
		assertFilter(
				// Input
				"MyColumnName",
				Arrays.<Object> asList((Object)null),
				// Expected output
				"MyColumnName IS NULL",
				Collections.emptyList());

		assertFilter(
				// Input
				"MyColumnName",
				Arrays.<Object> asList("Value1", "Value2"),
				// Expected output
				"MyColumnName IN (?,?)",
				Arrays.<Object> asList("Value1", "Value2"));

		assertFilter(
				// Input
				"MyColumnName",
				Arrays.<Object> asList("Value1", null, "Value2", null),
				// Expected output
				"(MyColumnName IN (?,?) OR MyColumnName IS NULL)",
				Arrays.<Object> asList("Value1", "Value2"));

		assertFilter(
				// Input
				"MyColumnName",
				Arrays.<Object> asList((Object)null),
				// Expected output
				"MyColumnName IS NULL",
				Collections.emptyList());

		assertFilter(
				// Input
				"MyColumnName",
				Arrays.<Object> asList(null, null, null, null),
				// Expected output
				"MyColumnName IS NULL",
				Collections.emptyList());

		assertFilter(
				// Input
				"MyColumnName",
				Arrays.<Object> asList(null, null, null, null),
				// Expected output
				"MyColumnName IS NULL",
				Collections.emptyList());
	}

	@Test
	public void test_RepoIds()
	{
		assertFilter(
				// Input
				"MyColumnName",
				Arrays.<Object> asList("Value1", RepoId.ofRepoId(30), "Value2", null),
				// Expected output
				"(MyColumnName IN (?,?,?) OR MyColumnName IS NULL)",
				Arrays.<Object> asList("Value1", 30, "Value2"));
	}

	@Test
	public void test_ReferenceListAwareEnums()
	{
		final InArrayQueryFilter<Object> filter = new InArrayQueryFilter<>(
				"DocStatus",
				Arrays.<Object> asList(DocStatus.Completed, DocStatus.Closed, null));

		assertFilter(filter,
				// Expected output
				"(DocStatus IN (?,?) OR DocStatus IS NULL)",
				Arrays.<Object> asList("CO", "CL"));

		final I_C_Order order = newInstance(I_C_Order.class);

		order.setDocStatus(DocStatus.Drafted.getCode());
		assertThat(filter.accept(order)).isFalse();

		order.setDocStatus(DocStatus.Completed.getCode());
		assertThat(filter.accept(order)).isTrue();

		order.setDocStatus(DocStatus.Closed.getCode());
		assertThat(filter.accept(order)).isTrue();
	}

	@Value
	@VisibleForTesting
	public static final class RepoId implements RepoIdAware
	{
		@JsonCreator
		public static RepoId ofRepoId(final int repoId)
		{
			return new RepoId(repoId);
		}

		public static RepoId ofRepoIdOrNull(final int repoId)
		{
			return repoId > 0 ? ofRepoId(repoId) : null;
		}

		int repoId;

		@Override
		@JsonValue
		public int getRepoId()
		{
			return repoId;
		}
	}

	/**
	 * Regression: Make sure DefaultReturnWhenEmpty option is set to "true" by default
	 */
	@Test
	public void test_DefaultReturnWhenEmpty_Is_True()
	{
		final String columnName = "MyColumnName";
		final InArrayQueryFilter<Object> filter = new InArrayQueryFilter<>(columnName, Collections.emptyList());

		assertThat(filter.isDefaultReturnWhenEmpty()).isTrue();
	}

	@Test
	public void test_NoValues_DefaultReturnWhenEmpty_TRUE()
	{
		assertDefaultReturnWhenEmpty(true, null);
		assertDefaultReturnWhenEmpty(true, Collections.emptyList());
		assertDefaultReturnWhenEmpty(true, new ArrayList<>());
	}

	@Test
	public void test_NoValues_DefaultReturnWhenEmpty_FALSE()
	{
		assertDefaultReturnWhenEmpty(false, null);
		assertDefaultReturnWhenEmpty(false, Collections.emptyList());
		assertDefaultReturnWhenEmpty(false, new ArrayList<>());
	}

	@Test
	public void test_equals()
	{
		final InArrayQueryFilter<I_Test> filter1 = new InArrayQueryFilter<>("columnName", "value1", "value2");
		final InArrayQueryFilter<I_Test> filter2 = new InArrayQueryFilter<>("columnName", "value1", "value2");
		assertThat(filter1).isEqualTo(filter2);

		filter1.getSql(); // IMPORTANT: trigger sql build
		assertThat(filter1).isEqualTo(filter2);
	}

	private void assertDefaultReturnWhenEmpty(final boolean defaultReturnWhenEmpty, final List<Object> params)
	{
		final String columnName = "MyColumnName";
		final String sqlExpected = defaultReturnWhenEmpty ? InArrayQueryFilter.SQL_TRUE : InArrayQueryFilter.SQL_FALSE;
		final List<Object> sqlParamsExpected = Collections.emptyList();

		final InArrayQueryFilter<Object> filter = new InArrayQueryFilter<>(columnName, params);
		filter.setDefaultReturnWhenEmpty(defaultReturnWhenEmpty);

		assertFilter(filter, sqlExpected, sqlParamsExpected);
	}

	private void assertFilter(
			final String columnName,
			final List<Object> values,
			//
			final String sqlExpected,
			final List<Object> sqlParamsExpected)
	{
		final InArrayQueryFilter<Object> filter = new InArrayQueryFilter<>(columnName, values);
		assertFilter(filter, sqlExpected, sqlParamsExpected);
	}

	private void assertFilter(
			final InArrayQueryFilter<Object> filter,
			//
			final String sqlExpected,
			final List<Object> sqlParamsExpected)
	{
		assertThat(filter.getSql()).isEqualTo(sqlExpected);
		assertThat(filter.getSqlParams(ctx)).isEqualTo(sqlParamsExpected);
	}
}
