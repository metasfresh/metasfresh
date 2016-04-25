package test.integration.swat.ad;

import org.adempiere.util.proxy.Cached;
import org.compiere.util.CacheMgt;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ch.qos.logback.classic.Level;
import de.metas.adempiere.util.CacheIgnore;
import de.metas.logging.LogManager;

public class CacheInterceptorTests
{
	public static class TestClass
	{
		public static final String CacheName1 = "1#CacheName";
		public static final String CacheName2 = "2#CacheName";

		public Object returnValue = null;

		@Cached
		public Object getCachedValue(int param1)
		{
			return returnValue;
		}

		@Cached(cacheName = CacheName1)
		public Object getCachedValueWithCacheName1(int param1)
		{
			return returnValue;
		}

		@Cached(cacheName = CacheName2)
		public Object getCachedValueWithCacheName2(int param1)
		{
			return returnValue;
		}

		@Cached
		public Object getCachedValueWithCacheIgnore(int param1, @CacheIgnore int ignoredParam)
		{
			return returnValue;
		}
	}

	@Before
	public void setup()
	{
		LogManager.setLevel(Level.INFO);
	}

	@Test
	public void testCachedValue() throws Exception
	{
		TestClass obj = new TestClass();

		final Object value1 = "CachedValue";
		obj.returnValue = value1;
		Assert.assertSame(obj.returnValue, obj.getCachedValue(1));
		Assert.assertSame(value1, obj.getCachedValue(1));

		final Object value2 = "CachedValue#2";
		obj.returnValue = value2;
		// Shall be cached already for param=1
		Assert.assertSame(value1, obj.getCachedValue(1));

		// Shall NOT BE cached for param=2
		Assert.assertSame(obj.returnValue, obj.getCachedValue(2));
		Assert.assertSame(value2, obj.getCachedValue(2));
	}

	@Test
	public void testCachedValueWithCacheName() throws Exception
	{
		TestClass obj = new TestClass();

		final Object value1 = "CachedValue#1";
		obj.returnValue = value1;
		Assert.assertSame(obj.returnValue, obj.getCachedValueWithCacheName1(1));
		Assert.assertSame(value1, obj.getCachedValueWithCacheName1(1));

		final Object value2 = "CachedValue#2";
		obj.returnValue = value2;
		Assert.assertSame(obj.returnValue, obj.getCachedValueWithCacheName2(1));
		Assert.assertSame(value2, obj.getCachedValueWithCacheName2(1));

		final Object value3 = "CachedValue#3";
		obj.returnValue = value3;
		CacheMgt.get().reset(TestClass.CacheName1);

		Assert.assertSame(value3, obj.getCachedValueWithCacheName1(1));
		Assert.assertSame(value3, obj.getCachedValueWithCacheName1(2));
		Assert.assertSame(value2, obj.getCachedValueWithCacheName2(1));
		Assert.assertSame(value3, obj.getCachedValueWithCacheName2(2));
		
		final Object value4 = "CachedValue#4";
		obj.returnValue = value4;
		CacheMgt.get().reset(TestClass.CacheName2);
		
		Assert.assertSame(value3, obj.getCachedValueWithCacheName1(1));
		Assert.assertSame(value3, obj.getCachedValueWithCacheName1(2));
		Assert.assertSame(value4, obj.getCachedValueWithCacheName1(3));
		Assert.assertSame(value4, obj.getCachedValueWithCacheName2(1));
		Assert.assertSame(value4, obj.getCachedValueWithCacheName2(2));
		Assert.assertSame(value4, obj.getCachedValueWithCacheName2(2));
	}

	@Test
	public void testCacheIgnore() throws Exception
	{
		TestClass obj = new TestClass();

		final Object value1 = "CachedValue#1";
		obj.returnValue = value1;
		Assert.assertSame(obj.returnValue, obj.getCachedValueWithCacheIgnore(1, 1));
		Assert.assertSame(value1, obj.getCachedValueWithCacheIgnore(1, 1));

		final Object value2 = "CachedValue#2";
		obj.returnValue = value2;
		Assert.assertSame(value1, obj.getCachedValueWithCacheIgnore(1, 1));
		Assert.assertSame(value1, obj.getCachedValueWithCacheIgnore(1, 2));
		Assert.assertSame(value2, obj.getCachedValueWithCacheIgnore(2, 1));
	}
}
