package de.metas.util;

/*
 * #%L
 * de.metas.util
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


import java.lang.reflect.Field;

import org.adempiere.util.testservice.IMockedMultitonService;
import org.adempiere.util.testservice.IMockedSingletonService;
import org.adempiere.util.testservice.ITestMissingService;
import org.adempiere.util.testservice.ITestServiceWithFailingConstructor;
import org.adempiere.util.testservice.ITestServiceWithPrivateImplementation;
import org.adempiere.util.testservice.impl.MockedMultitonService;
import org.adempiere.util.testservice.impl.MockedSingletonService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheStats;

import de.metas.util.IMultitonService;
import de.metas.util.IService;
import de.metas.util.ISingletonService;
import de.metas.util.Services;
import de.metas.util.exceptions.ServicesException;

public class ServicesTest
{
	@Before
	@After
	public void resetServices()
	{
		Services.clear();
		MockedMultitonService.resetNextInstanceNumber();
	}

	@Test
	public void test_reset()
	{
		Assert.assertEquals("LoadedServicesCount", 0, Services.getLoadedServicesCount());

		Services.get(IMockedSingletonService.class);
		Assert.assertEquals("LoadedServicesCount", 1, Services.getLoadedServicesCount());

		Services.clear();

		Assert.assertEquals("LoadedServicesCount", 0, Services.getLoadedServicesCount());

		//
		// Check implementation specifics
		final Cache<Class<? extends IService>, Object> servicesMap = getServicesInternalMap();
		final CacheStats cacheStats = servicesMap.stats();
		Assert.assertEquals("ServicesMap - size", 0, servicesMap.size());
		Assert.assertEquals("ServicesMap - Stats - loadCount", 0, cacheStats.loadCount());
		Assert.assertEquals("ServicesMap - Stats - hitCount", 0, cacheStats.hitCount());
		Assert.assertEquals("ServicesMap - Stats - missCount", 0, cacheStats.missCount());
	}

	/**
	 * Make sure we get the SAME instance each time we call {@link Services#get(Class)} for a {@link ISingletonService}.
	 */
	@Test
	public void test_getSingleton_SameInstanceOnEachCall()
	{
		MockedSingletonService.resetNextInstanceNumber();

		final int expectedInstanceNo = 1;
		Assert.assertEquals("Invalid NEXT_INSTANCE_NUMBER", expectedInstanceNo, MockedSingletonService.NEXT_INSTANCE_NUMBER);

		final IMockedSingletonService instance1 = Services.get(IMockedSingletonService.class);
		Assert.assertEquals("Invalid instanceNo", expectedInstanceNo, instance1.getInstanceNo());

		final IMockedSingletonService instance2 = Services.get(IMockedSingletonService.class);
		Assert.assertEquals("Invalid instanceNo", expectedInstanceNo, instance2.getInstanceNo());
		Assert.assertSame("instance2 shall be the same as instance1", instance1, instance2);

		//
		// Clear and test it again
		Services.clear();

		// Make sure a new instance is created
		final IMockedSingletonService instance3 = Services.get(IMockedSingletonService.class);
		Assert.assertEquals("Invalid instanceNo", 2, instance3.getInstanceNo());
		// Check again, this time shall be the same
		final IMockedSingletonService instance4 = Services.get(IMockedSingletonService.class);
		Assert.assertEquals("Invalid instanceNo", 2, instance4.getInstanceNo());
	}

	@Test(expected = ServicesException.class)
	public void test_getSingleton_MissingService()
	{
		Services.get(ITestMissingService.class);
	}

	@Test
	public void test_getSingleton_ServiceWithPrivateImplementation()
	{
		final ITestServiceWithPrivateImplementation service = Services.get(ITestServiceWithPrivateImplementation.class);
		Assert.assertNotNull(service);
	}

	@Test(expected = ServicesException.class)
	public void test_getSingleton_ServiceWithFailingConstructor()
	{
		Services.get(ITestServiceWithFailingConstructor.class);
	}

	/**
	 * Make sure we get a new instance each time we call {@link Services#get(Class)} for a {@link IMultitonService}.
	 */
	@Test
	public void test_getMultiton_NewInstanceOnEachCall()
	{
		MockedMultitonService.resetNextInstanceNumber();

		int expectedInstanceNo = 1;
		Assert.assertEquals("Invalid NEXT_INSTANCE_NUMBER", expectedInstanceNo, MockedMultitonService.NEXT_INSTANCE_NUMBER);

		// NOTE:
		// * increasing because when creating a new multiton, one instance is lost in the internal fucked-up implementation of Services
		// * on new implementation, this does not longer applies
		// expectedInstanceNo++;

		final IMockedMultitonService instance1 = Services.get(IMockedMultitonService.class);
		Assert.assertEquals("Invalid instanceNo", expectedInstanceNo, instance1.getInstanceNo());

		expectedInstanceNo++;
		final IMockedMultitonService instance2 = Services.get(IMockedMultitonService.class);
		Assert.assertEquals("Invalid instanceNo", expectedInstanceNo, instance2.getInstanceNo());
		Assert.assertNotSame("instance2 shall be unique", instance1, instance2);

		Assert.assertEquals("No services shall be actually cached because we worked only with multitons",
				0, // expected
				Services.getLoadedServicesCount());
	}

	private final Cache<Class<? extends IService>, Object> getServicesInternalMap()
	{
		try
		{
			final Field servicesField = Services.class.getDeclaredField("services");
			servicesField.setAccessible(true);

			final Object servicesMapObj = servicesField.get(null);
			@SuppressWarnings("unchecked")
			final Cache<Class<? extends IService>, Object> servicesMap = (Cache<Class<? extends IService>, Object>)servicesMapObj;
			return servicesMap;
		}
		catch (Exception e)
		{
			throw new RuntimeException("Cannot get services internal map", e);
		}
	}
}
