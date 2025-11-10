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

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class EmptyUtilTest
{
	@Test
	public void test_isEmpty()
	{
		test_isEmpty(true, null);
		test_isEmpty(true, Collections.emptyList());
		test_isEmpty(true, Collections.emptySet());
		test_isEmpty(true, new ArrayList<>());
		test_isEmpty(true, new HashSet<>());

		test_isEmpty(false, Collections.singletonList("1"));

		test_isEmpty(true, PlainIterable.of());
		test_isEmpty(false, PlainIterable.of("1"));
	}

	public void test_isEmpty(final boolean emptyExpected, final Object object)
	{
		final boolean emptyActual = EmptyUtil.isEmpty(object);
		Assertions.assertEquals(emptyExpected, emptyActual, "Invalid isEmpty result for object: " + object);
	}

	@EqualsAndHashCode
	@ToString
	private static class PlainIterable<T> implements Iterable<T>
	{
		private final List<T> list;

		private PlainIterable(@NonNull final List<T> list) {this.list = list;}

		@SafeVarargs
		public static <T> PlainIterable<T> of(@NonNull final T... items) {return new PlainIterable<>(Arrays.asList(items));}

		@Override
		public @NotNull Iterator<T> iterator() {return list.iterator();}
	}
}
