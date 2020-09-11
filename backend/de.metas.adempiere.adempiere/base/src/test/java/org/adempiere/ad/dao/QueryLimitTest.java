package org.adempiere.ad.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

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

		assertThat(QueryLimit.ofInt(5).toInt()).isEqualTo(5);

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

	private static List<String> listOfSize(int size)
	{
		final ArrayList<String> list = new ArrayList<>(size);
		while (list.size() < size)
		{
			list.add("element");
		}
		return list;
	}
}
