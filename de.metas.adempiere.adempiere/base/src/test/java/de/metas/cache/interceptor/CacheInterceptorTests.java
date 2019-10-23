package de.metas.cache.interceptor;

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


import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import org.adempiere.util.proxy.Cached;
import org.adempiere.util.proxy.impl.JavaAssistInterceptor;
import org.compiere.Adempiere;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.cache.CCache;
import de.metas.cache.CacheMgt;
import de.metas.cache.interceptor.CacheInterceptor;
import de.metas.cache.interceptor.CacheIntrospectionException;
import de.metas.cache.interceptor.testservices.ITestServiceWithCacheReload;
import de.metas.cache.interceptor.testservices.ITestServiceWithCachedMethod;
import de.metas.cache.interceptor.testservices.ITestServiceWithInvalidMethod;
import de.metas.cache.interceptor.testservices.ITestServiceWithMutableMethodParameter;
import de.metas.cache.interceptor.testservices.ITestServiceWithPrivateCachedMethod;
import de.metas.cache.interceptor.testservices.impl.TestServiceWithCacheReload;
import de.metas.cache.interceptor.testservices.impl.TestServiceWithCachedMethod;
import de.metas.cache.interceptor.testservices.impl.TestServiceWithCachedMethod.TestException;
import de.metas.util.Services;
import de.metas.util.exceptions.ServicesException;

public class CacheInterceptorTests
{
	@Before
	public void init()
	{
		Adempiere.enableUnitTestMode();

		Services.clear();

		// Setup Services and CacheInterceptor
		Services.getInterceptor().registerInterceptor(Cached.class, new CacheInterceptor());

		setFailOnError(true);
	}

	private final void setFailOnError(final boolean failOnError)
	{
		JavaAssistInterceptor.FAIL_ON_ERROR = failOnError;
	}

	/**
	 * Quick test to make sure that {@link Services} and {@link CacheInterceptor} are working OK together.
	 */
	@Test
	public void testServiceWithCachedMethod()
	{
		final ITestServiceWithCachedMethod service = Services.get(ITestServiceWithCachedMethod.class);

		final String randomString1 = service.randomString();
		final String randomString2 = service.randomString();
		Assert.assertEquals(
				"Shall return the same randomString because method is cached",
				randomString1, randomString2);
	}

	@Test
	public void test_TestServiceWithCachedMethod_HasCorrectCacheNames()
	{
		Assert.assertEquals("TableName1", CCache.extractTableNameForCacheName(TestServiceWithCachedMethod.CacheName1));
		Assert.assertEquals("TableName2", CCache.extractTableNameForCacheName(TestServiceWithCachedMethod.CacheName2));
	}

	@Test
	public void testCachedValue()
	{
		final TestServiceWithCachedMethod obj = (TestServiceWithCachedMethod)Services.get(ITestServiceWithCachedMethod.class);

		final Object value1 = "CachedValue";
		obj.cachedValueToReturn = value1;
		Assert.assertSame(obj.cachedValueToReturn, obj.getCachedValue(1));
		Assert.assertSame(value1, obj.getCachedValue(1));

		final Object value2 = "CachedValue#2";
		obj.cachedValueToReturn = value2;
		// Shall be cached already for param=1
		Assert.assertSame(value1, obj.getCachedValue(1));

		// Shall NOT BE cached for param=2
		Assert.assertSame(obj.cachedValueToReturn, obj.getCachedValue(2));
		Assert.assertSame(value2, obj.getCachedValue(2));
	}

	@Test
	public void testCachedMethodWhichReturnsNull()
	{
		final TestServiceWithCachedMethod obj = (TestServiceWithCachedMethod)Services.get(ITestServiceWithCachedMethod.class);

		obj.cachedValueToReturn = null;
		Assert.assertNull(null, obj.getCachedValue(1));

		// Make sure it's cached
		obj.cachedValueToReturn = "SomeOtherValue";
		Assert.assertNull(null, obj.getCachedValue(1));

	}

	@Test
	public void testCachedValueWithCacheName()
	{
		final TestServiceWithCachedMethod obj = (TestServiceWithCachedMethod)Services.get(ITestServiceWithCachedMethod.class);

		final Object value1 = "CachedValue#1";
		{
			obj.cachedValueToReturn = value1;
			Assert.assertSame(obj.cachedValueToReturn, obj.getCachedValueWithCacheName1(1));
			Assert.assertSame(value1, obj.getCachedValueWithCacheName1(1));

			// make sure value is cached
			obj.cachedValueToReturn = UUID.randomUUID().toString();
			Assert.assertSame(value1, obj.getCachedValueWithCacheName1(1));
		}

		final Object value2 = "CachedValue#2";
		{
			obj.cachedValueToReturn = value2;
			Assert.assertSame(obj.cachedValueToReturn, obj.getCachedValueWithCacheName2(1));
			Assert.assertSame(value2, obj.getCachedValueWithCacheName2(1));

			// make sure value is cached
			obj.cachedValueToReturn = UUID.randomUUID().toString();
			Assert.assertSame(value2, obj.getCachedValueWithCacheName2(1));
		}

		//
		// Set valueToReturn to "value3" and reset the "CacheName1"
		final Object value3 = "CachedValue#3";
		{
			obj.cachedValueToReturn = value3;
			final CacheMgt cacheMgt = CacheMgt.get();
			cacheMgt.reset(CCache.extractTableNameForCacheName(TestServiceWithCachedMethod.CacheName1));
		}

		//
		// Test after cache reset:
		// "getCachedValueWithCacheName1" shall always return the name value because it was reset
		Assert.assertSame(value3, obj.getCachedValueWithCacheName1(1));
		Assert.assertSame(value3, obj.getCachedValueWithCacheName1(2));
		// "getCachedValueWithCacheName2(1)" shall return the old value
		Assert.assertSame(value2, obj.getCachedValueWithCacheName2(1));
		// "getCachedValueWithCacheName2(2)" shall return the new value because even if it was not reset, it's the first time called with this param
		Assert.assertSame(value3, obj.getCachedValueWithCacheName2(2));
	}

	@Test
	public void testCacheIgnore()
	{
		final TestServiceWithCachedMethod obj = (TestServiceWithCachedMethod)Services.get(ITestServiceWithCachedMethod.class);

		final Object value1 = "CachedValue#1";
		obj.cachedValueToReturn = value1;
		Assert.assertSame(obj.cachedValueToReturn, obj.getCachedValueWithCacheIgnore(1, 1));
		Assert.assertSame(value1, obj.getCachedValueWithCacheIgnore(1, 1));

		final Object value2 = "CachedValue#2";
		obj.cachedValueToReturn = value2;
		Assert.assertSame(value1, obj.getCachedValueWithCacheIgnore(1, 1));
		Assert.assertSame(value1, obj.getCachedValueWithCacheIgnore(1, 2));
		Assert.assertSame(value2, obj.getCachedValueWithCacheIgnore(2, 1));
	}

	@Test
	public void testCachedMethodWhichThrowsException()
	{
		final TestServiceWithCachedMethod obj = (TestServiceWithCachedMethod)Services.get(ITestServiceWithCachedMethod.class);

		final String exceptionMessage = "test1";

		//
		// Call the method which throws exception and make sure we got the right one
		try
		{
			obj.cachedValueToReturn = "value1";
			obj.cachedMethodWhichThrowsException(exceptionMessage);
			Assert.fail("Exception should be thrown");
		}
		catch (TestException e)
		{
			Assert.assertEquals("Exception message", exceptionMessage, e.getMessage());
			Assert.assertSame("Exception cachedValue", "value1", e.getCachedValue());
		}

		//
		// Call it again, just to make sure it's not cached
		try
		{
			obj.cachedValueToReturn = "value2";
			obj.cachedMethodWhichThrowsException(exceptionMessage);
			Assert.fail("Exception should be thrown");
		}
		catch (TestException e)
		{
			Assert.assertEquals("Exception message", exceptionMessage, e.getMessage());
			Assert.assertSame("Exception cachedValue", "value2", e.getCachedValue());
		}
	}

	@Test(expected = CacheIntrospectionException.class)
	public void testInvalidCachedMethod()
	{
		final ITestServiceWithInvalidMethod service = Services.get(ITestServiceWithInvalidMethod.class);

		// NOTE: if Adempiere.isUnitTestMode(), then we expect here an exception to be thrown
		final String randomString1 = service.invalidCachCtxParam("ctx");
		final String randomString2 = service.invalidCachCtxParam("ctx");
		Assert.assertNotEquals("return values shall be different because method was not cached because it was invalid defined",
				randomString1, randomString2);
	}

	@Test(expected = CacheIntrospectionException.class)
	public void testMethodWithMutableParameter()
	{
		final ITestServiceWithMutableMethodParameter service = Services.get(ITestServiceWithMutableMethodParameter.class);

		final Properties ctx = new Properties();

		// NOTE: if Adempiere.isUnitTestMode(), then we expect here an exception to be thrown
		final String randomString1 = service.methodWithMutableCachedParameter(ctx);
		final String randomString2 = service.methodWithMutableCachedParameter(ctx);
		Assert.assertNotEquals("return values shall be different because method was not cached because it was invalid defined",
				randomString1, randomString2);
	}

	@Test
	public void testMethodWithDate()
	{
		final ITestServiceWithMutableMethodParameter service = Services.get(ITestServiceWithMutableMethodParameter.class);

		final long ts = System.currentTimeMillis();
		final Date date1 = new Date(ts);
		final String randomString1 = service.methodWithDate(date1);

		//
		// Changing "date1" because it's mutable
		// AIM: we want to prove that the cache-key is not changed
		date1.setTime(date1.getTime() + 10000);

		final Date date2 = new Date(ts);
		final String randomString2 = service.methodWithDate(date2);
		Assert.assertEquals("Shall return same values because the same parameter value is provided."
				+ "\n In case it fails, it means that the internal cache key was changed.",
				randomString1, randomString2);
	}

	@Test
	public void testMethodWithSkipIfNotNullParameter()
	{
		final ITestServiceWithCachedMethod service = Services.get(ITestServiceWithCachedMethod.class);

		final Object skipIfNotNullParam_Null = null;
		final Object skipIfNotNullParam_NotNull = new Object();
		final int param1 = 1;

		//
		// Test when using a null parameter
		// Expectation: calling the method twice with same parameters, shall return same results (i.e. cached)
		final Object result1 = service.cachedMethodWithSkipIfNotNull(param1, skipIfNotNullParam_Null);
		Assert.assertEquals("Method shall be cached because SkipIfNotNull parameter was null",
				result1, // expected
				service.cachedMethodWithSkipIfNotNull(param1, skipIfNotNullParam_Null)
				);

		//
		// Test when using a not null parameter
		// Expectation: calling the method twice with same parameters, shall return different results (i.e. not cached)
		{
			final Object resultWhenNotNull = service.cachedMethodWithSkipIfNotNull(param1, skipIfNotNullParam_NotNull);
			Assert.assertNotEquals("Method shall NOT be cached because SkipIfNotNull parameter was NOT null",
					resultWhenNotNull, // expected
					service.cachedMethodWithSkipIfNotNull(param1, skipIfNotNullParam_NotNull)
					);
		}
	}

	@Test(expected = ServicesException.class)
	public void testMethodCallingPrivateCachedMethod_FailOnErrorEnabled()
	{
		Services.get(ITestServiceWithPrivateCachedMethod.class);
	}

	@Test
	public void testMethodCallingPrivateCachedMethod_FailOnErrorDisabled()
	{
		setFailOnError(false);

		final ITestServiceWithPrivateCachedMethod service = Services.get(ITestServiceWithPrivateCachedMethod.class);

		final Object result1 = service.methodCallingPrivateCachedMethod(1);
		Assert.assertNotEquals("Method shall NOT be cached because private methods are not supported",
				result1, // expected
				service.methodCallingPrivateCachedMethod(1)
				);
	}

	@Test
	public void testMethodCallingPackageLevelCachedMethod()
	{
		final ITestServiceWithCachedMethod service = Services.get(ITestServiceWithCachedMethod.class);

		//
		// Test for parameter "1"
		final Object result1 = service.methodCallingPackageLevelCachedMethod(1);
		Assert.assertEquals("Method shall be cached",
				result1, // expected
				service.methodCallingPackageLevelCachedMethod(1)
				);

		//
		// Test for parameter "2"
		final Object result2 = service.methodCallingPackageLevelCachedMethod(2);
		Assert.assertEquals("Method shall be cached",
				result2, // expected
				service.methodCallingPackageLevelCachedMethod(2)
				);
		Assert.assertNotEquals("Method shall be cached but shall not have the same value as for previous parameter",
				result1, // expected
				result2
				);
	}
	
	@Test
	public void testCacheReloadIfTrue()
	{
		TestServiceWithCacheReload service = (TestServiceWithCacheReload)Services.get(ITestServiceWithCacheReload.class);
		
		final String id1 = "id1";
		
		service.cachedValueToReturn = "1";
		Assert.assertEquals("1", service.getReload(id1, false));
		
		// Make sure, while we are not triggering cache reload, the cached value it's used
		service.cachedValueToReturn = "2";
		Assert.assertEquals("1", service.getReload(id1, false));
		
		// Make sure, when we are triggering cache reload, the NEW value is used and cached
		service.cachedValueToReturn = "2";
		Assert.assertEquals("2", service.getReload(id1, true));
		Assert.assertEquals("2", service.getReload(id1, false));
	}

}
