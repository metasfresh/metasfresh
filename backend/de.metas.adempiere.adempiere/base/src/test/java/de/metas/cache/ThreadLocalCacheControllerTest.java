/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cache;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.util.lang.IAutoCloseable;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;

public class ThreadLocalCacheControllerTest
{
	@Test
	public void givenNoCacheSet_whenGetFromNonDictionaryCCache_thenRecompute()
	{
		final CCache<String, String> cache = CCache.<String, String>builder()
				.cacheName("TestingCache")
				.tableName("TestTableName")
				.build();

		final String cacheKey = "cacheKey";
		final CachedValue cachedValue = new CachedValue("InitialValue");

		final Supplier<String> getFromCache = () -> cache.getOrLoad(cacheKey, key -> cachedValue.getValue());

		testCacheMethod(getFromCache, cachedValue, false);
	}

	@Test
	public void givenNoCacheSet_whenGetAllFromNonDictionaryCCache_thenRecompute()
	{
		//given
		final CCache<String, String> cache = CCache.<String, String>builder()
				.cacheName("TestingCache")
				.tableName("TestTableName")
				.build();

		final String cacheKey1 = "cacheKey";
		final CachedValue cachedValue = new CachedValue("InitialValue");

		final Supplier<String> getFromCache = () -> cache.getAllOrLoad(ImmutableSet.of(cacheKey1),
																	   keySet -> ImmutableMap.of(cacheKey1, cachedValue.getValue()))
				.iterator().next();

		testCacheMethod(getFromCache, cachedValue, false);
	}

	@Test
	public void givenNoCacheSet_whenGetFromDictionaryCCache_thenReturnFromCache()
	{
		//given
		final CCache<String, String> cache = CCache.<String, String>builder()
				.cacheName("TestingCache")
				.tableName("AD_TestTableName")
				.build();

		final String cacheKey = "cacheKey";
		final CachedValue cachedValue = new CachedValue("InitialValue");

		final Supplier<String> getFromCache = () -> cache.getOrLoad(cacheKey, key -> cachedValue.getValue());

		testCacheMethod(getFromCache, cachedValue, true);
	}

	private void testCacheMethod(
			@NonNull final Supplier<String> getFromCacheMethod,
			@NonNull final CachedValue cachedValue,
			final boolean isDictionaryCache)
	{
		//making sure the cache is properly set up
		String valueInCache = getFromCacheMethod.get();
		assertThat(valueInCache).isEqualTo("InitialValue");
		cachedValue.setValue("UpdatedValue");

		valueInCache = getFromCacheMethod.get();
		assertThat(valueInCache).isEqualTo("InitialValue");

		//when
		try (final IAutoCloseable ignored = ThreadLocalCacheController.instance.temporaryDisableCache())
		{
			//then
			assertThat(getFromCacheMethod.get()).isEqualTo(isDictionaryCache ? "InitialValue" : "UpdatedValue");
		}

		//then
		cachedValue.setValue("UpdatedAgain");
		assertThat(getFromCacheMethod.get()).isEqualTo(isDictionaryCache ? "InitialValue" : "UpdatedValue");
	}

	@Setter
	@Getter
	@AllArgsConstructor
	private static class CachedValue
	{
		private String value;
	}
}
