package de.metas.cache.interceptor.testservices.impl;

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


import java.util.UUID;

import org.adempiere.util.proxy.Cached;
import org.junit.Ignore;

import de.metas.adempiere.util.cache.annotations.CacheSkipIfNotNull;
import de.metas.cache.annotation.CacheIgnore;
import de.metas.cache.annotation.CacheTrx;
import de.metas.cache.interceptor.testservices.ITestServiceWithCachedMethod;

@Ignore
public class TestServiceWithCachedMethod implements ITestServiceWithCachedMethod
{
	public static final String CacheName1 = "TableName1#CacheName";
	public static final String CacheName2 = "TableName2#CacheName";

	public Object cachedValueToReturn = null;

	public static class TestException extends RuntimeException
	{
		private static final long serialVersionUID = 1L;
		private final Object cachedValue;

		public TestException(final String message, final Object cachedValue)
		{
			super(message);
			this.cachedValue = cachedValue;
		}

		public Object getCachedValue()
		{
			return cachedValue;
		}
	}

	@Override
	@Cached
	public String randomString()
	{
		return UUID.randomUUID().toString();
	}

	@Override
	@Cached
	public Object getCachedValue(final int param1)
	{
		return cachedValueToReturn;
	}

	@Override
	@Cached(cacheName = CacheName1)
	public Object getCachedValueWithCacheName1(final int param1)
	{
		return cachedValueToReturn;
	}

	@Override
	@Cached(cacheName = CacheName2)
	public Object getCachedValueWithCacheName2(final int param1)
	{
		return cachedValueToReturn;
	}

	@Override
	@Cached
	public Object getCachedValueWithCacheIgnore(final int param1, @CacheIgnore final int ignoredParam)
	{
		return cachedValueToReturn;
	}

	@Override
	@Cached
	public Object cachedMethodWhichThrowsException(final String message)
	{
		throw new TestException(message, cachedValueToReturn);
	}

	@Override
	@Cached
	public Object cachedMethodWithTrx(final int param1, @CacheTrx final String trxName)
	{
		return cachedValueToReturn;
	}

	@Override
	@Cached
	public Object cachedMethodWithSkipIfNotNull(final int param1, @CacheSkipIfNotNull final Object skipIfNotNullParam)
	{
		return UUID.randomUUID().toString();
	}

	@Override
	public Object methodCallingPackageLevelCachedMethod(final int param1)
	{
		return cachedPackageLevelMethod(param1);
	}

	@Cached
	/*package*/Object cachedPackageLevelMethod(final int param1)
	{
		return UUID.randomUUID().toString();
	}


}
