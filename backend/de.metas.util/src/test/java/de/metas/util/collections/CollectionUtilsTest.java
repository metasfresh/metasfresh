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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.ImmutableMapEntry;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

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
			final Object result = CollectionUtils.emptyOrSingleElement(ImmutableList.of());
			assertThat(result).isNull();
		}

		@Test
		void oneElement()
		{
			final String result = CollectionUtils.emptyOrSingleElement(ImmutableList.of("1"));
			assertThat(result).isEqualTo("1");
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
	class mergeElementToMap
	{
		@SafeVarargs
		private final ImmutableMap<String, ImmutableMapEntry<String, String>> mapOf(
				final ImmutableMapEntry<String, String>... entries
		)
		{
			return Maps.uniqueIndex(Arrays.asList(entries), ImmutableMapEntry::getKey);
		}

		@Test
		void addNewElement()
		{
			assertThat(
					CollectionUtils.mergeElementToMap(
							mapOf(
									ImmutableMapEntry.of("K1", "V1")
							),
							ImmutableMapEntry.of("K2", "V2"),
							ImmutableMapEntry::getKey)
			).isEqualTo(mapOf(
					ImmutableMapEntry.of("K1", "V1"),
					ImmutableMapEntry.of("K2", "V2")
			));
		}

		@Test
		void replaceExistingElement()
		{
			assertThat(
					CollectionUtils.mergeElementToMap(
							mapOf(
									ImmutableMapEntry.of("K1", "V1")
							),
							ImmutableMapEntry.of("K1", "V1.1"),
							ImmutableMapEntry::getKey)
			).isEqualTo(mapOf(
					ImmutableMapEntry.of("K1", "V1.1")
			));
		}

		@Test
		void addSameElement()
		{
			final ImmutableMap<String, ImmutableMapEntry<String, String>> map = mapOf(
					ImmutableMapEntry.of("K1", "V1")
			);
			assertThat(
					CollectionUtils.mergeElementToMap(
							map,
							ImmutableMapEntry.of("K1", "V1"),
							ImmutableMapEntry::getKey)
			).isSameAs(map);
		}

	}
}