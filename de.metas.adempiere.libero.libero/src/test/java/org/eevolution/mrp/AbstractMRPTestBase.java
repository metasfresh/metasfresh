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

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.util.Services;
import org.eevolution.LiberoConfiguration;
import org.eevolution.api.IDDOrderBL;
import org.eevolution.mrp.api.impl.MRPTestHelper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.document.engine.IDocumentBL;
import de.metas.material.planning.MaterialPlanningConfiguration;

@RunWith(SpringRunner.class)
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
	protected IDDOrderBL ddOrderBL = null;

	protected boolean dumpDatabaseOnFail = true;

	@Rule
	public TestWatcher testWatchman = new TestWatcher()
	{
		@Override
		protected void succeeded(final Description description)
		{
			// nothing
		}

		@Override
		protected void failed(final Throwable e, final Description description)
		{
			System.out.println("After test failed: " + description.getDisplayName());
			if (helper != null)
			{
				helper.dumpMRPRecords("MRP records after test failed");
			}

			if (dumpDatabaseOnFail)
			{
				POJOLookupMap.get().dumpStatus();
			}
		}

		@Override
		protected void finished(final Description description)
		{
			// nothing
		}
	};

	@Before
	public final void init()
	{
		this.helper = new MRPTestHelper();
		// this.mrpExecutor = helper.mrpExecutor;

		// services
		docActionBL = Services.get(IDocumentBL.class);
		ddOrderBL = Services.get(IDDOrderBL.class);

		afterInit();
	}

	/**
	 * Method called before running a test, after basic init.
	 */
	protected abstract void afterInit();
}
