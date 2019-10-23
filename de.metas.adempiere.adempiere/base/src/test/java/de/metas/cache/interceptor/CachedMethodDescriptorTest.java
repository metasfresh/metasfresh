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


import java.lang.reflect.Method;
import java.util.Properties;

import org.adempiere.util.proxy.Cached;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Test;

import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.annotation.CacheIgnore;
import de.metas.cache.annotation.CacheTrx;
import de.metas.cache.interceptor.CacheKeyBuilder;
import de.metas.cache.interceptor.CachedMethodDescriptor;

public class CachedMethodDescriptorTest
{
	public static class TestClass
	{
		@Cached
		public Object get(@CacheCtx Properties ctx, int testId)
		{
			return "" + testId;
		}

		@Cached
		public Object getPlusIgnore(@CacheCtx @CacheIgnore Properties ctx, int testId)
		{
			return "" + testId;
		}

		@Cached
		public Object getIgnoreFirst(@CacheIgnore @CacheCtx Properties ctx, int testId)
		{
			return "" + testId;
		}

		@Cached
		public Object getWithTrxName(final int testId, @CacheTrx final String trxName)
		{
			return "testId=" + testId + ", trxName=" + trxName;
		}
	}

	@Test
	public void test_CacheCtx() throws Exception
	{
		final TestClass testObj = new TestClass();
		final Method method = testObj.getClass().getMethod("get", Properties.class, int.class);
		final CachedMethodDescriptor methodDescriptor = new CachedMethodDescriptor(method);

		final Properties ctx = new Properties();
		Env.setContext(ctx, "#AD_Client_ID", 100);
		Env.setContext(ctx, "#AD_Role_ID", 100);
		Env.setContext(ctx, "#AD_User_ID", 100);
		Env.setContext(ctx, "#AD_Org_ID", 123);

		final int testId = 100;
		final Object[] params = new Object[] { ctx, testId };

		final Object keyArray1 = methodDescriptor.createKeyBuilder(testObj, params).buildKey();

		Env.setContext(ctx, "#AD_Org_ID", 124);
		final Object keyArray2 = methodDescriptor.createKeyBuilder(testObj, params).buildKey();

		Assert.assertEquals(keyArray1, keyArray2);

		Env.setContext(ctx, "#AD_Client_ID", 101);
		final Object keyArray3 = methodDescriptor.createKeyBuilder(testObj, params).buildKey();
		Assert.assertNotEquals(keyArray1, keyArray3);
	}

	@Test
	public void test_CacheCtx_ButIgnored() throws Exception
	{
		final TestClass testObj = new TestClass();
		final Method method = testObj.getClass().getMethod("getPlusIgnore", Properties.class, int.class);
		final CachedMethodDescriptor methodDescriptor = new CachedMethodDescriptor(method);

		final Properties ctx = new Properties();
		Env.setContext(ctx, "#AD_Client_ID", 100);
		Env.setContext(ctx, "#AD_Role_ID", 100);
		Env.setContext(ctx, "#AD_User_ID", 100);
		Env.setContext(ctx, "#AD_Org_ID", 123);

		final int testId = 100;
		final Object[] params = new Object[] { ctx, testId };

		final Object key1 = methodDescriptor.createKeyBuilder(testObj, params).buildKey();

		Env.setContext(ctx, "#AD_Client_ID", 101);
		final Object key2 = methodDescriptor.createKeyBuilder(testObj, params).buildKey();
		Assert.assertEquals(key1, key2);
	}

	@Test
	public void test_CacheTrx() throws Exception
	{
		final TestClass testObj = new TestClass();
		final Method method = testObj.getClass().getMethod("getWithTrxName", int.class, String.class);
		final CachedMethodDescriptor methodDescriptor = new CachedMethodDescriptor(method);

		final CacheKeyBuilder keyBuilder1 = methodDescriptor.createKeyBuilder(testObj, new Object[] { 100, "trxName1" });
		Assert.assertEquals("Invalid keyBuilder1's trxName", "trxName1", keyBuilder1.getTrxName());

		final CacheKeyBuilder keyBuilder2 = methodDescriptor.createKeyBuilder(testObj, new Object[] { 100, "trxName2" });
		Assert.assertEquals("Invalid keyBuilder2's trxName", "trxName2", keyBuilder2.getTrxName());

		Assert.assertEquals(
				"Keys shall be equal because trxName shall not be included (we are using different cache storages)",
				keyBuilder1.buildKey(), keyBuilder2.buildKey());
	}
}
