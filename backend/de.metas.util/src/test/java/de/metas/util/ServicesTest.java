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

import com.google.common.cache.Cache;
import com.google.common.cache.CacheStats;
import de.metas.util.exceptions.ServicesException;
import org.adempiere.util.testservice.IMockedMultitonService;
import org.adempiere.util.testservice.IMockedSingletonService;
import org.adempiere.util.testservice.ITestMissingService;
import org.adempiere.util.testservice.ITestServiceWithFailingConstructor;
import org.adempiere.util.testservice.ITestServiceWithPrivateImplementation;
import org.adempiere.util.testservice.impl.MockedMultitonService;
import org.adempiere.util.testservice.impl.MockedSingletonService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class ServicesTest
{
	@BeforeEach
	@AfterEach
	public void resetServices()
	{
		Services.clear();
		MockedMultitonService.resetNextInstanceNumber();
	}

	@Test
	public void test_reset()
	{
		Assertions.assertEquals(0, Services.getLoadedServicesCount(), "LoadedServicesCount");

		Services.get(IMockedSingletonService.class);
		Assertions.assertEquals(1, Services.getLoadedServicesCount(), "LoadedServicesCount");

		Services.clear();

		Assertions.assertEquals(0, Services.getLoadedServicesCount(), "LoadedServicesCount");

		//
		// Check implementation specifics
		final Cache<Class<? extends IService>, Object> servicesMap = getServicesInternalMap();
		final CacheStats cacheStats = servicesMap.stats();
		Assertions.assertEquals(0, servicesMap.size(), "ServicesMap - size");
		Assertions.assertEquals(0, cacheStats.loadCount(), "ServicesMap - Stats - loadCount");
		Assertions.assertEquals(0, cacheStats.hitCount(), "ServicesMap - Stats - hitCount");
		Assertions.assertEquals(0, cacheStats.missCount(), "ServicesMap - Stats - missCount");
	}

	/**
	 * Make sure we get the SAME instance each time we call {@link Services#get(Class)} for a {@link ISingletonService}.
	 */
	@Test
	public void test_getSingleton_SameInstanceOnEachCall()
	{
		MockedSingletonService.resetNextInstanceNumber();

		final int expectedInstanceNo = 1;
		Assertions.assertEquals(expectedInstanceNo, MockedSingletonService.NEXT_INSTANCE_NUMBER, "Invalid NEXT_INSTANCE_NUMBER");

		final IMockedSingletonService instance1 = Services.get(IMockedSingletonService.class);
		Assertions.assertEquals(expectedInstanceNo, instance1.getInstanceNo(), "Invalid instanceNo");

		final IMockedSingletonService instance2 = Services.get(IMockedSingletonService.class);
		Assertions.assertEquals(expectedInstanceNo, instance2.getInstanceNo(), "Invalid instanceNo");
		Assertions.assertSame(instance1, instance2, "instance2 shall be the same as instance1");

		//
		// Clear and test it again
		Services.clear();

		// Make sure a new instance is created
		final IMockedSingletonService instance3 = Services.get(IMockedSingletonService.class);
		Assertions.assertEquals(2, instance3.getInstanceNo(), "Invalid instanceNo");
		// Check again, this time shall be the same
		final IMockedSingletonService instance4 = Services.get(IMockedSingletonService.class);
		Assertions.assertEquals(2, instance4.getInstanceNo(), "Invalid instanceNo");
	}

	@Test
	public void test_getSingleton_MissingService()
	{
		Assertions.assertThrows(ServicesException.class, () -> Services.get(ITestMissingService.class));
	}

	@Test
	public void test_getSingleton_ServiceWithPrivateImplementation()
	{
		final ITestServiceWithPrivateImplementation service = Services.get(ITestServiceWithPrivateImplementation.class);
		Assertions.assertNotNull(service);
	}

	@Test
	public void test_getSingleton_ServiceWithFailingConstructor()
	{
		Assertions.assertThrows(ServicesException.class, () -> Services.get(ITestServiceWithFailingConstructor.class));
	}

	/**
	 * Make sure we get a new instance each time we call {@link Services#get(Class)} for a {@link IMultitonService}.
	 */
	@Test
	public void test_getMultiton_NewInstanceOnEachCall()
	{
		MockedMultitonService.resetNextInstanceNumber();

		int expectedInstanceNo = 1;
		Assertions.assertEquals(expectedInstanceNo, MockedMultitonService.NEXT_INSTANCE_NUMBER, "Invalid NEXT_INSTANCE_NUMBER");

		// NOTE:
		// * increasing because when creating a new multiton, one instance is lost in the internal fucked-up implementation of Services
		// * on new implementation, this does not longer applies
		// expectedInstanceNo++;

		final IMockedMultitonService instance1 = Services.get(IMockedMultitonService.class);
		Assertions.assertEquals(expectedInstanceNo, instance1.getInstanceNo(), "Invalid instanceNo");

		expectedInstanceNo++;
		final IMockedMultitonService instance2 = Services.get(IMockedMultitonService.class);
		Assertions.assertEquals(expectedInstanceNo, instance2.getInstanceNo(), "Invalid instanceNo");
		Assertions.assertNotSame(instance1, instance2, "instance2 shall be unique");

		Assertions.assertEquals(
				0, // expected
				Services.getLoadedServicesCount(),
				"No services shall be actually cached because we worked only with multitons");
	}

	private final Cache<Class<? extends IService>, Object> getServicesInternalMap()
	{
		try
		{
			final Field servicesField = Services.class.getDeclaredField("services");
			servicesField.setAccessible(true);

			final Object servicesMapObj = servicesField.get(null);
			@SuppressWarnings("unchecked") final Cache<Class<? extends IService>, Object> servicesMap = (Cache<Class<? extends IService>, Object>)servicesMapObj;
			return servicesMap;
		}
		catch (Exception e)
		{
			throw new RuntimeException("Cannot get services internal map", e);
		}
	}
}
