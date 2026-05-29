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
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.processor.IWorkpackageProcessorFactory;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * NOTE: most of the tests are already performed in {@link StatefulWorkpackageProcessorFactoryTest} which indirectly is testing {@link WorkpackageProcessorFactory}.
 *
 * @author tsa
 */
public class WorkpackageProcessorFactoryTest extends QueueProcessorTestBase
{
	/**
	 * Test that service is available and it's a stateLESS service
	 */
	@Test
	public void testServiceIsAvailable()
	{
		// Make sure that there are no services registered
		Services.clear();

		final IWorkpackageProcessorFactory statelessFactory = Services.get(IWorkpackageProcessorFactory.class);
		Assertions.assertNotNull(statelessFactory, "No statelessFactory was found for " + IWorkpackageProcessorFactory.class);

		final IWorkpackageProcessorFactory statelessFactory2 = Services.get(IWorkpackageProcessorFactory.class);
		Assertions.assertNotNull(statelessFactory2, "No statelessFactory2 was found for " + IWorkpackageProcessorFactory.class);

		Assertions.assertSame(
				statelessFactory2, statelessFactory, "No statelessFactory was found for " + IWorkpackageProcessorFactory.class);
	}

	@Test
	public void test_getWorkpackageProcessor_invalid_packageProcessorId()
	{
		Assertions.assertThrows(AdempiereException.class, () -> Services.get(IWorkpackageProcessorFactory.class).getWorkpackageProcessor(Env.getCtx(), 0));
		;
	}

	@Test
	public void test_getWorkpackageProcessor_notFound_packageProcessorId()
	{
		Assertions.assertThrows(AdempiereException.class, () -> Services.get(IWorkpackageProcessorFactory.class).getWorkpackageProcessor(Env.getCtx(), 12345));
	}

	/**
	 * Test that {@link WorkpackageProcessorFactory#getWorkpackageProcessor(I_C_Queue_PackageProcessor)} is returning a new instance each time.
	 * <p>
	 * We need this feature in case we want to use stateful processors.
	 */
	@Test
	public void test_getWorkpackageProcessor_NewIntanceShallBeReturnedEachTime()
	{
		final I_C_Queue_PackageProcessor packageProcessorDef = helper.createPackageProcessor(ctx, MockedWorkpackageProcessor.class);

		final WorkpackageProcessorFactory factory = new WorkpackageProcessorFactory();

		final IWorkpackageProcessor packageProcessor1 = factory.getWorkpackageProcessor(packageProcessorDef);
		Assertions.assertNotNull(packageProcessor1, "Workpackage processor not found");

		final IWorkpackageProcessor packageProcessor2 = factory.getWorkpackageProcessor(packageProcessorDef);
		Assertions.assertNotNull(packageProcessor2, "Workpackage processor not found");

		Assertions.assertNotSame(packageProcessor2, packageProcessor1, "Each time when we ask for an package processor instance, a new instance shall be returned");
	}

}
