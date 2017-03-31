package org.compiere.util;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

public class CCacheTest
{
	public static class MyUncheckedException extends RuntimeException
	{
		private static final long serialVersionUID = 1L;
	};

	public static class MyError extends Error
	{
		private static final long serialVersionUID = 1L;
	};

	@Test
	public void test_get_LoaderReturnsNull()
	{
		CCache<String, String> cache = new CCache<String, String>("Test", 10);
		final String value = cache.get("key1", new Supplier<String>()
		{

			@Override
			public String get()
			{
				return null;
			}
		});

		Assert.assertNull(value);
		Assert.assertFalse("value shall not be cached because supplier returned null", cache.containsKey("key1"));
	}

	@Test(expected = MyUncheckedException.class)
	public void test_get_LoaderThrowsUncheckedException()
	{
		CCache<String, String> cache = new CCache<String, String>("Test", 10);
		cache.get("key1", new Supplier<String>()
		{

			@Override
			public String get()
			{
				throw new MyUncheckedException();
			}
		});
	}

	@Test(expected = MyError.class)
	public void test_get_LoaderThrowsError()
	{
		CCache<String, String> cache = new CCache<String, String>("Test", 10);
		cache.get("key1", new Supplier<String>()
		{

			@Override
			public String get()
			{
				throw new MyError();
			}
		});
	}

	@Test
	public void test_putNull()
	{
		final CCache<String, String> cache = new CCache<String, String>("Test", 10);
		testPutGet(cache, "key1", null);
	}

	@Test
	public void test_putGetStandard()
	{
		final CCache<String, String> cache = new CCache<String, String>("Test", 10);
		testPutGet(cache, "key1", "value1");
		testPutGet(cache, "key2", "value2");
		testPutGet(cache, "key3", "value3");
		testPutGet(cache, "key1", "value1_1");
	}

	private final <K, V> void testPutGet(final CCache<K, V> cache, final K key, final V value)
	{
		cache.put(key, value);
		Assert.assertFalse("invalid isJustReset", cache.isReset());

		// Test "get"
		final V valueActual = cache.get(key);
		Assert.assertEquals("Invalid cached value for key=" + key, value, valueActual);

		// Test "containsKey"
		final boolean containsKeyExpected = value != null;
		Assert.assertEquals("Cache shall contain key=" + key, containsKeyExpected, cache.containsKey(key));
	}

	private final <K, V> void assertEmpty(final CCache<K, V> cache)
	{
		final int sizeExpected = 0;
		assertSize(cache, sizeExpected);
	}

	private final <K, V> void assertSize(final CCache<K, V> cache, final int sizeExpected)
	{
		final boolean emptyExpected = sizeExpected == 0;
		Assert.assertEquals("Invalid isEmpty() result", emptyExpected, cache.isEmpty());
		Assert.assertEquals("Invalid size() result", sizeExpected, cache.size());

		Assert.assertEquals("Invalid keySet().isEmpty() result", emptyExpected, cache.keySet().isEmpty());
		Assert.assertEquals("Invalid keySet().size() result", sizeExpected, cache.keySet().size());

		Assert.assertEquals("Invalid values().isEmpty() result", emptyExpected, cache.values().isEmpty());
		Assert.assertEquals("Invalid values().size() result", sizeExpected, cache.values().size());
	}

	@Test
	public void test_IsEmpty()
	{
		final CCache<String, String> cache = new CCache<String, String>("Test", 10);
		Assert.assertEquals("invalid isJustReset", true, cache.isReset());
		assertEmpty(cache);

		// Add one element and test
		testPutGet(cache, "key1", "value1");
		assertSize(cache, 1);

		// Put it again, we expect the same size
		testPutGet(cache, "key1", "value1");
		assertSize(cache, 1);

		// Add another element and test
		testPutGet(cache, "key2", "value1");
		assertSize(cache, 2);

		cache.reset();
		assertEmpty(cache);
		Assert.assertEquals("invalid isJustReset", true, cache.isReset());
	}

	@Test
	public void test_removeKey()
	{
		final CCache<String, String> cache = new CCache<String, String>("Test", 10);
		Assert.assertEquals("invalid isJustReset", true, cache.isReset());
		assertEmpty(cache);

		// Add one element and test
		testPutGet(cache, "key1", "value1");
		assertSize(cache, 1);

		// Remove a not existing key
		cache.remove("keyNotExists");
		assertSize(cache, 1);

		// Remove the element and test
		cache.remove("key1");
		Assert.assertEquals("invalid isJustReset. We expect false because we just removed from cache.", false, cache.isReset());
		assertEmpty(cache);
	}

	@Test
	public void test_clear()
	{
		final CCache<String, String> cache = new CCache<String, String>("Test", 10);
		Assert.assertEquals("invalid isJustReset", true, cache.isReset());
		assertEmpty(cache);

		// Add one element and test
		testPutGet(cache, "key1", "value1");
		assertSize(cache, 1);

		// Clear
		cache.clear();
		Assert.assertEquals("invalid isJustReset", true, cache.isReset());
		assertEmpty(cache);
	}

	@Test
	public void test_putAll()
	{
		final Map<String, String> valuesToPut = ImmutableMap.<String, String> builder()
				.put("key1", "value1")
				.put("key2", "value2")
				.build();

		final CCache<String, String> cache = new CCache<String, String>("Test", 10);
		assertEmpty(cache);

		cache.putAll(valuesToPut);
		assertSize(cache, valuesToPut.size());

		Assert.assertEquals("Value shall exist", "value1", cache.get("key1"));
		Assert.assertEquals("Value shall exist", "value2", cache.get("key2"));

		//
		// Test putAll shall override existing value
		cache.putAll(Collections.singletonMap("key1", "value1_newValue"));
		Assert.assertEquals("Value shall exist", "value1_newValue", cache.get("key1"));
	}
}
