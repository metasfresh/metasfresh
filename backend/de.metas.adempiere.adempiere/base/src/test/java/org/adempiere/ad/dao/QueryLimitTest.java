package org.adempiere.ad.dao;

import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class QueryLimitTest
{
	@SuppressWarnings("SameParameterValue")
	private static List<String> listOfSize(final int size)
	{
		final ArrayList<String> list = new ArrayList<>(size);
		while (list.size() < size)
		{
			list.add("element");
		}
		return list;
	}

	@Test
	public void ofInt()
	{
		assertThat(QueryLimit.ofInt(-100)).isSameAs(QueryLimit.NO_LIMIT);
		assertThat(QueryLimit.ofInt(-1)).isSameAs(QueryLimit.NO_LIMIT);
		assertThat(QueryLimit.ofInt(0)).isSameAs(QueryLimit.NO_LIMIT);

		assertThat(QueryLimit.ofInt(1)).isSameAs(QueryLimit.ONE);
		assertThat(QueryLimit.ofInt(2)).isSameAs(QueryLimit.TWO);
		assertThat(QueryLimit.ofInt(100)).isSameAs(QueryLimit.ONE_HUNDRED);
		assertThat(QueryLimit.ofInt(500)).isSameAs(QueryLimit.FIVE_HUNDRED);
		assertThat(QueryLimit.ofInt(1000)).isSameAs(QueryLimit.ONE_THOUSAND);

		assertThat(QueryLimit.ofInt(5).toInt()).isEqualTo(5);
	}

	@Test
	public void toIntOr()
	{
		assertThat(QueryLimit.NO_LIMIT.toIntOr(Integer.MAX_VALUE)).isEqualTo(Integer.MAX_VALUE);
		assertThat(QueryLimit.ofInt(1).toIntOr(Integer.MAX_VALUE)).isEqualTo(1);
	}

	@Test
	public void isNoLimit()
	{
		assertThat(QueryLimit.NO_LIMIT.isNoLimit()).isTrue();
		assertThat(QueryLimit.ofInt(1).isNoLimit()).isFalse();
	}

	@Test
	public void isLessThanOrEqualTo()
	{
		assertThat(QueryLimit.NO_LIMIT.isLessThanOrEqualTo(10)).isFalse();
		assertThat(QueryLimit.ofInt(9).isLessThanOrEqualTo(10)).isTrue();
		assertThat(QueryLimit.ofInt(10).isLessThanOrEqualTo(10)).isTrue();
		assertThat(QueryLimit.ofInt(11).isLessThanOrEqualTo(10)).isFalse();
	}

	@Test
	public void isLimitHitOrExceeded()
	{
		assertThat(QueryLimit.NO_LIMIT.isLimitHitOrExceeded(listOfSize(10))).isFalse();
		assertThat(QueryLimit.ofInt(9).isLimitHitOrExceeded(listOfSize(10))).isTrue();
		assertThat(QueryLimit.ofInt(10).isLimitHitOrExceeded(listOfSize(10))).isTrue();
		assertThat(QueryLimit.ofInt(11).isLimitHitOrExceeded(listOfSize(10))).isFalse();
	}

	@Nested
	public class minusSizeOf
	{
		@Test
		void noLimit_minus_listOf7()
		{
			assertThat(QueryLimit.NO_LIMIT.minusSizeOf(listOfSize(7)))
					.isSameAs(QueryLimit.NO_LIMIT);
		}

		@Test
		void limit10_minus_emptyList()
		{
			final QueryLimit limit = QueryLimit.ofInt(10);
			assertThat(limit.minusSizeOf(listOfSize(0)))
					.isSameAs(limit);
		}

		@Test
		void limit10_minus_listOf9()
		{
			assertThat(QueryLimit.ofInt(10).minusSizeOf(listOfSize(9)))
					.isEqualTo(QueryLimit.ofInt(1));
		}

		@Test
		void limit10_minus_listOf10()
		{
			assertThatThrownBy(() -> QueryLimit.ofInt(10).minusSizeOf(listOfSize(10)))
					.isInstanceOf(AdempiereException.class)
					.hasMessageStartingWith("Invalid collection size");
		}

		@Test
		void limit10_minus_listOf11()
		{
			assertThatThrownBy(() -> QueryLimit.ofInt(10).minusSizeOf(listOfSize(11)))
					.isInstanceOf(AdempiereException.class)
					.hasMessageStartingWith("Invalid collection size");
		}

	}
}
