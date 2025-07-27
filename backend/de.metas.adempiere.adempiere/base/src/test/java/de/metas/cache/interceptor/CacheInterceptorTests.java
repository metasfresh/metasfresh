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

import de.metas.cache.CCache;
import de.metas.cache.CacheMgt;
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
import org.adempiere.util.proxy.Cached;
import org.adempiere.util.proxy.impl.JavaAssistInterceptor;
import org.compiere.Adempiere;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CacheInterceptorTests
{
	@BeforeEach
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
		
		assertThat(randomString2)
				.as("Shall return the same randomString because method is cached")
				.isEqualTo(randomString1);
	}

	@Test
	public void test_TestServiceWithCachedMethod_HasCorrectCacheNames()
	{
		assertThat(CCache.extractTableNameForCacheName(TestServiceWithCachedMethod.CacheName1)).isEqualTo("TableName1");
		assertThat(CCache.extractTableNameForCacheName(TestServiceWithCachedMethod.CacheName2)).isEqualTo("TableName2");
	}

	@Test
	public void testCachedValue()
	{
		final TestServiceWithCachedMethod obj = (TestServiceWithCachedMethod)Services.get(ITestServiceWithCachedMethod.class);

		final Object value1 = "CachedValue";
		obj.cachedValueToReturn = value1;
		assertThat(obj.getCachedValue(1)).isSameAs(obj.cachedValueToReturn);
		assertThat(obj.getCachedValue(1)).isSameAs(value1);

		final Object value2 = "CachedValue#2";
		obj.cachedValueToReturn = value2;
		// Shall be cached already for param=1
		assertThat(obj.getCachedValue(1)).isSameAs(value1);

		// Shall NOT BE cached for param=2
		assertThat(obj.getCachedValue(2)).isSameAs(obj.cachedValueToReturn);
		assertThat(obj.getCachedValue(2)).isSameAs(value2);
	}

	@Test
	public void testCachedMethodWhichReturnsNull()
	{
		final TestServiceWithCachedMethod obj = (TestServiceWithCachedMethod)Services.get(ITestServiceWithCachedMethod.class);

		obj.cachedValueToReturn = null;
		assertThat(obj.getCachedValue(1)).isNull();

		// Make sure it's cached
		obj.cachedValueToReturn = "SomeOtherValue";
		assertThat(obj.getCachedValue(1)).isNull();
	}

	@Test
	public void testCachedValueWithCacheName()
	{
		final TestServiceWithCachedMethod obj = (TestServiceWithCachedMethod)Services.get(ITestServiceWithCachedMethod.class);

		final Object value1 = "CachedValue#1";
		{
			obj.cachedValueToReturn = value1;
			assertThat(obj.getCachedValueWithCacheName1(1)).isSameAs(obj.cachedValueToReturn);
			assertThat(obj.getCachedValueWithCacheName1(1)).isSameAs(value1);

			// make sure value is cached
			obj.cachedValueToReturn = UUID.randomUUID().toString();
			assertThat(obj.getCachedValueWithCacheName1(1)).isSameAs(value1);
		}

		final Object value2 = "CachedValue#2";
		{
			obj.cachedValueToReturn = value2;
			assertThat(obj.getCachedValueWithCacheName2(1)).isSameAs(obj.cachedValueToReturn);
			assertThat(obj.getCachedValueWithCacheName2(1)).isSameAs(value2);

			// make sure value is cached
			obj.cachedValueToReturn = UUID.randomUUID().toString();
			assertThat(obj.getCachedValueWithCacheName2(1)).isSameAs(value2);
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
		assertThat(obj.getCachedValueWithCacheName1(1)).isSameAs(value3);
		assertThat(obj.getCachedValueWithCacheName1(2)).isSameAs(value3);
		// "getCachedValueWithCacheName2(1)" shall return the old value
		assertThat(obj.getCachedValueWithCacheName2(1)).isSameAs(value2);
		// "getCachedValueWithCacheName2(2)" shall return the new value because even if it was not reset, it's the first time called with this param
		assertThat(obj.getCachedValueWithCacheName2(2)).isSameAs(value3);
	}

	@Test
	public void testCacheIgnore()
	{
		final TestServiceWithCachedMethod obj = (TestServiceWithCachedMethod)Services.get(ITestServiceWithCachedMethod.class);

		final Object value1 = "CachedValue#1";
		obj.cachedValueToReturn = value1;
		assertThat(obj.getCachedValueWithCacheIgnore(1, 1)).isSameAs(obj.cachedValueToReturn);
		assertThat(obj.getCachedValueWithCacheIgnore(1, 1)).isSameAs(value1);

		final Object value2 = "CachedValue#2";
		obj.cachedValueToReturn = value2;
		assertThat(obj.getCachedValueWithCacheIgnore(1, 1)).isSameAs(value1);
		assertThat(obj.getCachedValueWithCacheIgnore(1, 2)).isSameAs(value1);
		assertThat(obj.getCachedValueWithCacheIgnore(2, 1)).isSameAs(value2);
	}

	@Test
	public void testCachedMethodWhichThrowsException()
	{
		final TestServiceWithCachedMethod obj = (TestServiceWithCachedMethod)Services.get(ITestServiceWithCachedMethod.class);

		final String exceptionMessage = "test1";

		//
		// Call the method which throws exception and make sure we got the right one
		obj.cachedValueToReturn = "value1";
		assertThatThrownBy(() -> obj.cachedMethodWhichThrowsException(exceptionMessage))
				.isInstanceOf(TestException.class)
				.hasMessage(exceptionMessage)
				.satisfies(ex -> {
					TestException testEx = (TestException) ex;
					assertThat(testEx.getCachedValue()).as("Exception cachedValue").isSameAs("value1");
				});

		//
		// Call it again, just to make sure it's not cached
		obj.cachedValueToReturn = "value2";
		assertThatThrownBy(() -> obj.cachedMethodWhichThrowsException(exceptionMessage))
				.isInstanceOf(TestException.class)
				.hasMessage(exceptionMessage)
				.satisfies(ex -> {
					TestException testEx = (TestException) ex;
					assertThat(testEx.getCachedValue()).as("Exception cachedValue").isSameAs("value2");
				});
	}

	@Test
	public void testInvalidCachedMethod()
	{
		final ITestServiceWithInvalidMethod service = Services.get(ITestServiceWithInvalidMethod.class);

		// NOTE: if Adempiere.isUnitTestMode(), then we expect here an exception to be thrown
		assertThatThrownBy(() -> {
			final String randomString1 = service.invalidCachCtxParam("ctx");
			final String randomString2 = service.invalidCachCtxParam("ctx");
			assertThat(randomString2)
					.as("return values shall be different because method was not cached because it was invalid defined")
					.isNotEqualTo(randomString1);
		}).isInstanceOf(CacheIntrospectionException.class);
	}

	@Test
	public void testMethodWithMutableParameter()
	{
		final ITestServiceWithMutableMethodParameter service = Services.get(ITestServiceWithMutableMethodParameter.class);

		final Properties ctx = new Properties();

		// NOTE: if Adempiere.isUnitTestMode(), then we expect here an exception to be thrown
		assertThatThrownBy(() -> {
			final String randomString1 = service.methodWithMutableCachedParameter(ctx);
			final String randomString2 = service.methodWithMutableCachedParameter(ctx);
			assertThat(randomString2)
					.as("return values shall be different because method was not cached because it was invalid defined")
					.isNotEqualTo(randomString1);
		}).isInstanceOf(CacheIntrospectionException.class);
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
		
		assertThat(randomString2)
				.as("Shall return same values because the same parameter value is provided.\n In case it fails, it means that the internal cache key was changed.")
				.isEqualTo(randomString1);
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
		assertThat(service.cachedMethodWithSkipIfNotNull(param1, skipIfNotNullParam_Null))
				.as("Method shall be cached because SkipIfNotNull parameter was null")
				.isEqualTo(result1);

		//
		// Test when using a not null parameter
		// Expectation: calling the method twice with same parameters, shall return different results (i.e. not cached)
		{
			final Object resultWhenNotNull = service.cachedMethodWithSkipIfNotNull(param1, skipIfNotNullParam_NotNull);
			assertThat(service.cachedMethodWithSkipIfNotNull(param1, skipIfNotNullParam_NotNull))
					.as("Method shall NOT be cached because SkipIfNotNull parameter was NOT null")
					.isNotEqualTo(resultWhenNotNull);
		}
	}

	@Test
	public void testMethodCallingPrivateCachedMethod_FailOnErrorEnabled()
	{
		assertThatThrownBy(() -> Services.get(ITestServiceWithPrivateCachedMethod.class))
				.isInstanceOf(ServicesException.class);
	}

	@Test
	public void testMethodCallingPrivateCachedMethod_FailOnErrorDisabled()
	{
		setFailOnError(false);

		final ITestServiceWithPrivateCachedMethod service = Services.get(ITestServiceWithPrivateCachedMethod.class);

		final Object result1 = service.methodCallingPrivateCachedMethod(1);
		assertThat(service.methodCallingPrivateCachedMethod(1))
				.as("Method shall NOT be cached because private methods are not supported")
				.isNotEqualTo(result1);
	}

	@Test
	public void testMethodCallingPackageLevelCachedMethod()
	{
		final ITestServiceWithCachedMethod service = Services.get(ITestServiceWithCachedMethod.class);

		//
		// Test for parameter "1"
		final Object result1 = service.methodCallingPackageLevelCachedMethod(1);
		assertThat(service.methodCallingPackageLevelCachedMethod(1))
				.as("Method shall be cached")
				.isEqualTo(result1);

		//
		// Test for parameter "2"
		final Object result2 = service.methodCallingPackageLevelCachedMethod(2);
		assertThat(service.methodCallingPackageLevelCachedMethod(2))
				.as("Method shall be cached")
				.isEqualTo(result2);
		
		assertThat(result2)
				.as("Method shall be cached but shall not have the same value as for previous parameter")
				.isNotEqualTo(result1);
	}
	
	@Test
	public void testCacheReloadIfTrue()
	{
		TestServiceWithCacheReload service = (TestServiceWithCacheReload)Services.get(ITestServiceWithCacheReload.class);
		
		final String id1 = "id1";
		
		service.cachedValueToReturn = "1";
		assertThat(service.getReload(id1, false)).isEqualTo("1");
		
		// Make sure, while we are not triggering cache reload, the cached value it's used
		service.cachedValueToReturn = "2";
		assertThat(service.getReload(id1, false)).isEqualTo("1");
		
		// Make sure, when we are triggering cache reload, the NEW value is used and cached
		service.cachedValueToReturn = "2";
		assertThat(service.getReload(id1, true)).isEqualTo("2");
		assertThat(service.getReload(id1, false)).isEqualTo("2");
	}
}