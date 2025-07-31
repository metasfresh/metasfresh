package org.eevolution.mrp;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.document.engine.IDocumentBL;
import de.metas.material.planning.MaterialPlanningConfiguration;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.eevolution.LiberoConfiguration;
import org.eevolution.mrp.api.impl.MRPTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, AbstractMRPTestBase.MRPTestWatcher.class})
@SpringBootTest(classes = {
		LiberoConfiguration.class,
		MaterialPlanningConfiguration.class
})
@ActiveProfiles("test")
public abstract class AbstractMRPTestBase
{
	protected MRPTestHelper helper;

	// services
	protected IDocumentBL docActionBL = null;

	protected boolean dumpDatabaseOnFail = true;

	public static class MRPTestWatcher implements TestWatcher
	{
		@Override
		public void testSuccessful(final ExtensionContext context)
		{
			// nothing
		}

		@Override
		public void testFailed(@NonNull final ExtensionContext context, final Throwable cause)
		{
			System.out.println("After test failed: " + context.getDisplayName());
			
			// Get the test instance to access helper
			final Object testInstance = context.getTestInstance().orElse(null);
			if (testInstance instanceof AbstractMRPTestBase)
			{
				final AbstractMRPTestBase testBase = (AbstractMRPTestBase) testInstance;
				if (testBase.helper != null)
				{
					testBase.helper.dumpMRPRecords("MRP records after test failed");
				}

				if (testBase.dumpDatabaseOnFail)
				{
					POJOLookupMap.get().dumpStatus();
				}
			}
		}
	}

	@BeforeEach
	public final void init()
	{
		this.helper = new MRPTestHelper();
		// this.mrpExecutor = helper.mrpExecutor;

		// services
		docActionBL = Services.get(IDocumentBL.class);

		afterInit();
	}

	/**
	 * Method called before running a test, after basic init.
	 */
	protected abstract void afterInit();
}