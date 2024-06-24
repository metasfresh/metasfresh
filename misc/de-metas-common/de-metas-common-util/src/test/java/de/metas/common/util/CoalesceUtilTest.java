/*
 * #%L
 * de-metas-common-util
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

package de.metas.common.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class CoalesceUtilTest
{
	@Test
	public void test_coalesce3()
	{
		final Object o1 = new Object();
		final Object o2 = new Object();
		final Object o3 = new Object();
		test_coalesce3(o1, o1, o2, o3);
		test_coalesce3(o1, null, o1, o2);
		test_coalesce3(o1, null, null, o1);
		test_coalesce3(null, null, null, null);
	}

	public <T> void test_coalesce3(final T expected, final T value1, final T value2, final T value3)
	{
		{
			final T actual = CoalesceUtil.coalesce(value1, value2, value3);
			assertThat(actual).isSameAs(expected);
		}
		{
			final Object actual = CoalesceUtil.coalesce(new Object[] { value1, value2, value3 });
			assertThat(actual).isSameAs(expected);
		}
	}

	@Test
	public void firstGreaterThanZero()
	{
		assertThat(CoalesceUtil.firstGreaterThanZero(0, 3, 1)).isEqualTo(3);
	}
}
