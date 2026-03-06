/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.async.processor.impl;

import de.metas.async.QueueProcessorTestBase;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IStatefulWorkpackageProcessorFactory;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StatefulWorkpackageProcessorFactoryTest extends QueueProcessorTestBase
{
	private StatefulWorkpackageProcessorFactory statefulFactory;

	@Override
	public void beforeTestCustomized()
	{
		statefulFactory = new StatefulWorkpackageProcessorFactory();
	}

	/**
	 * Test that service is available and it's a stateFUL service
	 */
	@Test
	public void testServiceIsAvailable()
	{
		// Make sure that there are no services registered
		Services.clear();

		final IStatefulWorkpackageProcessorFactory factory = Services.get(IStatefulWorkpackageProcessorFactory.class);
		Assertions.assertNotNull(factory, "No factory was found for " + IStatefulWorkpackageProcessorFactory.class);

		final IStatefulWorkpackageProcessorFactory factory2 = Services.get(IStatefulWorkpackageProcessorFactory.class);
		Assertions.assertNotNull(factory2, "No factory2 was found for " + IStatefulWorkpackageProcessorFactory.class);

		Assertions.assertNotSame(factory2, factory, "Each time when we retrieve an " + IStatefulWorkpackageProcessorFactory.class + " we shall get a new instance");
	}

	@Test
	public void test_registerWorkpackageProcessor_null()
	{
		Assertions.assertThrows(AdempiereException.class, () -> statefulFactory.registerWorkpackageProcessor(null));
	}

	@Test
	public void test_getWorkpackageProcessorInstance()
	{
		// Create the stateless package processor
		helper.createPackageProcessor(ctx, TestableWorkpackageProcessor2.class);

		// Create and register the stateful package processor
		final IWorkpackageProcessor statefulPackageProcessor = new TestableWorkpackageProcessor1();
		statefulFactory.registerWorkpackageProcessor(statefulPackageProcessor);

		//
		// Test stateless workpackage processors - each time we need to get a different instance
		final IWorkpackageProcessor statelessPackageProcessorActual1 = statefulFactory.getWorkpackageProcessorInstance(TestableWorkpackageProcessor2.class.getName());
		assertThat(statelessPackageProcessorActual1)
				.as("Invalid statelessPackageProcessorActual1").isInstanceOf(TestableWorkpackageProcessor2.class);

		final IWorkpackageProcessor statelessPackageProcessorActual2 = statefulFactory.getWorkpackageProcessorInstance(TestableWorkpackageProcessor2.class.getName());
		assertThat(statelessPackageProcessorActual2).as("Invalid statelessPackageProcessorActual2").isInstanceOf(TestableWorkpackageProcessor2.class);
		assertThat(statelessPackageProcessorActual2).as("Invalid statelessPackageProcessorActual2").isNotSameAs(statelessPackageProcessorActual1);

		//
		// Test stateful workpackage processor - each time we need to get THE SAME instance
		final IWorkpackageProcessor statefulPackageProcessorActual = statefulFactory.getWorkpackageProcessorInstance(TestableWorkpackageProcessor1.class.getName());
		assertThat(statefulPackageProcessorActual).as("Invalid statefulPackageProcessorActual").isSameAs(statefulPackageProcessor);
	}

	public static class TestableWorkpackageProcessor1 implements IWorkpackageProcessor
	{

		@Override
		public Result processWorkPackage(I_C_Queue_WorkPackage workpackage, String localTrxName)
		{
			return Result.SUCCESS;
		}
	}

	public static class TestableWorkpackageProcessor2 implements IWorkpackageProcessor
	{

		@Override
		public Result processWorkPackage(I_C_Queue_WorkPackage workpackage, String localTrxName)
		{
			return Result.SUCCESS;
		}
	}
}
