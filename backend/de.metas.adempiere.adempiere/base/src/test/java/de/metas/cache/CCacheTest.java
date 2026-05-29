package de.metas.cache;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class CCacheTest
{
	public static class MyUncheckedException extends RuntimeException
	{
		private static final long serialVersionUID = 1L;
	}

	;

	public static class MyError extends Error
	{
		private static final long serialVersionUID = 1L;
	}

	;

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

		Assertions.assertNull(value);
		Assertions.assertFalse(cache.containsKey("key1"), "value shall not be cached because supplier returned null");
	}

	@Test
	public void test_get_LoaderThrowsUncheckedException()
	{
		CCache<String, String> cache = new CCache<String, String>("Test", 10);
		Assertions.assertThrows(MyUncheckedException.class, () -> cache.get("key1", new Supplier<String>()
		{
			@Override
			public String get()
			{
				throw new MyUncheckedException();
			}
		}));
	}

	@Test
	public void test_get_LoaderThrowsError()
	{
		CCache<String, String> cache = new CCache<String, String>("Test", 10);

		Assertions.assertThrows(MyError.class, () -> cache.get("key1", new Supplier<String>()
		{
			@Override
			public String get()
			{
				throw new MyError();
			}
		}));
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
		Assertions.assertFalse(cache.isReset(), "invalid isJustReset");

		// Test "get"
		final V valueActual = cache.get(key);
		Assertions.assertEquals(value, valueActual, "Invalid cached value for key=" + key);

		// Test "containsKey"
		final boolean containsKeyExpected = value != null;
		Assertions.assertEquals(containsKeyExpected, cache.containsKey(key), "Cache shall contain key=" + key);
	}

	private final <K, V> void assertEmpty(final CCache<K, V> cache)
	{
		final int sizeExpected = 0;
		assertSize(cache, sizeExpected);
	}

	private final <K, V> void assertSize(final CCache<K, V> cache, final int sizeExpected)
	{
		final boolean emptyExpected = sizeExpected == 0;
		Assertions.assertEquals(emptyExpected, cache.isEmpty(), "Invalid isEmpty() result");
		Assertions.assertEquals(sizeExpected, cache.size(), "Invalid size() result");

		Assertions.assertEquals(emptyExpected, cache.keySet().isEmpty(), "Invalid keySet().isEmpty() result");
		Assertions.assertEquals(sizeExpected, cache.keySet().size(), "Invalid keySet().size() result");

		Assertions.assertEquals(emptyExpected, cache.values().isEmpty(), "Invalid values().isEmpty() result");
		Assertions.assertEquals(sizeExpected, cache.values().size(), "Invalid values().size() result");
	}

	@Test
	public void test_IsEmpty()
	{
		final CCache<String, String> cache = new CCache<String, String>("Test", 10);
		Assertions.assertEquals(true, cache.isReset(), "invalid isJustReset");
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
		Assertions.assertEquals(true, cache.isReset(), "invalid isJustReset");
	}

	@Test
	public void test_removeKey()
	{
		final CCache<String, String> cache = new CCache<String, String>("Test", 10);
		Assertions.assertEquals(true, cache.isReset(), "invalid isJustReset");
		assertEmpty(cache);

		// Add one element and test
		testPutGet(cache, "key1", "value1");
		assertSize(cache, 1);

		// Remove a not existing key
		cache.remove("keyNotExists");
		assertSize(cache, 1);

		// Remove the element and test
		cache.remove("key1");
		Assertions.assertEquals(false, cache.isReset(), "invalid isJustReset. We expect false because we just removed from cache.");
		assertEmpty(cache);
	}

	@Test
	public void test_clear()
	{
		final CCache<String, String> cache = new CCache<String, String>("Test", 10);
		Assertions.assertEquals(true, cache.isReset(), "invalid isJustReset");
		assertEmpty(cache);

		// Add one element and test
		testPutGet(cache, "key1", "value1");
		assertSize(cache, 1);

		// Clear
		cache.reset();
		Assertions.assertEquals(true, cache.isReset(), "invalid isJustReset");
		assertEmpty(cache);
	}

	@Test
	public void test_putAll()
	{
		final Map<String, String> valuesToPut = ImmutableMap.<String, String>builder()
				.put("key1", "value1")
				.put("key2", "value2")
				.build();

		final CCache<String, String> cache = new CCache<String, String>("Test", 10);
		assertEmpty(cache);

		cache.putAll(valuesToPut);
		assertSize(cache, valuesToPut.size());

		Assertions.assertEquals("value1", cache.get("key1"), "Value shall exist");
		Assertions.assertEquals("value2", cache.get("key2"), "Value shall exist");

		//
		// Test putAll shall override existing value
		cache.putAll(Collections.singletonMap("key1", "value1_newValue"));
		Assertions.assertEquals("value1_newValue", cache.get("key1"), "Value shall exist");
	}

	@Test
	public void test_removalListener()
	{
		final HashMap<String, String> removedItems = new HashMap<>();

		final CCache<String, String> cache = CCache.<String, String>builder()
				.removalListener(removedItems::put)
				.build();

		cache.put("k1", "v1");

		assertThat(removedItems).isEmpty();
		cache.remove("k1");
		assertThat(removedItems).containsEntry("k1", "v1");
	}
}
