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

import org.compiere.util.Util.ArrayKey;
import org.junit.Assert;
import org.junit.Test;

import de.metas.cache.annotation.CacheReloadIfTrue;
import de.metas.cache.interceptor.CacheIntrospectionException;
import de.metas.cache.interceptor.CachedMethodDescriptor;
import de.metas.cache.interceptor.testservices.impl.TestServiceWithCacheReload_InvalidMethods;

/**
 * Tests if Cached methods which are using {@link CacheReloadIfTrue} are correctly introspected.
 * 
 * @author tsa
 *
 */
public class CacheReloadIfTrueInstrospectionTest
{
	@Test
	public void test_introspection_methodWithBooleanFlag() throws Exception
	{
		final TestServiceWithCacheReload_InvalidMethods testObj = new TestServiceWithCacheReload_InvalidMethods();

		final Method method = testObj.getClass().getMethod("methodWithBooleanFlag", String.class, Boolean.class);
		final CachedMethodDescriptor methodDescriptor = new CachedMethodDescriptor(method);

		// Make sure the "cache reload" flag is not part of the caching key
		final ArrayKey key1 = methodDescriptor.createKeyBuilder(testObj, new Object[] { "id1", false }).buildKey();
		final ArrayKey key2 = methodDescriptor.createKeyBuilder(testObj, new Object[] { "id1", true }).buildKey();
		Assert.assertEquals(key1, key2);
	}

	@Test
	public void test_introspection_methodWithBooleanPrimitiveFlag() throws Exception
	{
		final TestServiceWithCacheReload_InvalidMethods testObj = new TestServiceWithCacheReload_InvalidMethods();

		final Method method = testObj.getClass().getMethod("methodWithBooleanPrimitiveFlag", String.class, boolean.class);
		final CachedMethodDescriptor methodDescriptor = new CachedMethodDescriptor(method);

		// Make sure the "cache reload" flag is not part of the caching key
		final ArrayKey key1 = methodDescriptor.createKeyBuilder(testObj, new Object[] { "id1", false }).buildKey();
		final ArrayKey key2 = methodDescriptor.createKeyBuilder(testObj, new Object[] { "id1", true }).buildKey();
		Assert.assertEquals(key1, key2);
	}

	@Test(expected = CacheIntrospectionException.class)
	public void test_introspection_invalidMethod1() throws Exception
	{
		final TestServiceWithCacheReload_InvalidMethods testObj = new TestServiceWithCacheReload_InvalidMethods();

		final Method method = testObj.getClass().getMethod("invalidMethod1", String.class, Object.class);
		new CachedMethodDescriptor(method); // shall throw exception
	}

}
