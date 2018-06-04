package de.metas.adempiere.util.cache;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.AvailableSingletonServicesTester;
import org.junit.Before;
import org.junit.Test;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class Fresh_AvailableSingletonServices_Test
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void test()
	{
		AvailableSingletonServicesTester.newInstance()
				// .skipServiceInterface(de.metas.dpd.service.IDPDRoutingservice.class) // is registered programmatically
				.skipServiceInterface(org.adempiere.inout.replenish.service.IReplenishForFutureQty.class) // is registered programmatically
				.skipServiceInterface(de.metas.adempiere.service.IAppDictionaryBL.class) // is registered programmatically
				.skipServiceInterface(de.metas.letters.api.ITextTemplateBL.class) // is registered programmatically
				.skipServiceInterface(de.metas.procurement.base.IAgentSyncBL.class) // JAX-RS
				.skipServiceInterface(org.eevolution.mrp.api.ILiberoMRPContextFactory.class) // skip for now because the impl it's coming from spring context
				.skipServiceInterface(de.metas.material.planning.IMRPContextFactory.class) // skip for now because the impl it's coming from spring context
				//
				.skipServiceInterface(org.adempiere.util.testservice.ITestServiceWithFailingConstructor.class) // because it's supposed to fail
				.skipServiceInterface(org.adempiere.util.testservice.ITestMissingService.class) // because it's supposed to fail
				.skipServiceInterfaceIfStartsWith("org.adempiere.util.proxy.impl.JavaAssistInterceptorTests") // some test interface
				.skipServiceInterface(de.metas.adempiere.util.cache.testservices.ITestServiceWithPrivateCachedMethod.class)
				.test();
	}
}
