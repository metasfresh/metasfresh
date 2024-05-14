/*
 * #%L
 * de.metas.util
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

package de.metas.util.collections;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSetMultimap;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CollectionUtilsTest
{

	@Nested
	class emptyOrSingleElement
	{
		@Test
		void empty()
		{
			assertThat(CollectionUtils.<Object>emptyOrSingleElement(ImmutableList.of())).isNull();
		}

		@Test
		void oneElement()
		{
			assertThat(CollectionUtils.emptyOrSingleElement(ImmutableList.of("1"))).isEqualTo("1");
		}

		@Test
		void twoIdenticalElements()
		{
			// NOTE: checking if elements are distinct is not the job of this method
			assertThatThrownBy(() -> CollectionUtils.emptyOrSingleElement(ImmutableList.of("1", "1")))
					.isInstanceOf(RuntimeException.class)
					.hasMessageStartingWith("The given collection needs to have ZERO or ONE item");
		}

		@Test
		void moreElements()
		{
			assertThatThrownBy(() -> CollectionUtils.emptyOrSingleElement(ImmutableList.of("1", "2")))
					.isInstanceOf(RuntimeException.class)
					.hasMessageStartingWith("The given collection needs to have ZERO or ONE item");
		}
	}

	@Nested
	class mapKeys_SetMultimap
	{
		@Test
		void empty()
		{
			final ImmutableSetMultimap<Object, Object> multimap = ImmutableSetMultimap.of();
			assertThat(CollectionUtils.mapKeys(multimap, k -> k)).isSameAs(multimap);
		}

		@Test
		void noChange()
		{
			final ImmutableSetMultimap<Integer, String> multimap = ImmutableSetMultimap.of(1, "one", 2, "two");
			assertThat(CollectionUtils.mapKeys(multimap, k -> k)).isSameAs(multimap);
		}

		@Test
		void oneElementChanged()
		{
			final ImmutableSetMultimap<Integer, String> multimap = ImmutableSetMultimap.of(1, "one", 2, "two");
			assertThat(CollectionUtils.mapKeys(multimap, k -> k == 1 ? k * 10 : k))
					.isEqualTo(ImmutableSetMultimap.of(10, "one", 2, "two"));
		}

	}
}