package org.adempiere.ad.callout.api.impl;

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


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adempiere.ad.callout.api.ICalloutFactory;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.ad.callout.spi.ICalloutProvider;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.callout.spi.impl.ProgramaticCalloutProvider;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.model.MTable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProgramaticCalloutProviderTest
{
	private ProgramaticCalloutProvider provider;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		provider = (ProgramaticCalloutProvider)Services.get(IProgramaticCalloutProvider.class);
	}

	@Test
	public void test_StandardCase()
	{
		final MockedCalloutInstance calloutInstance = new MockedCalloutInstance();
		provider.registerCallout("MyTableName", "MyColumnName", calloutInstance);

		final PlainCalloutField field = new PlainCalloutField();
		field.setAD_Table_ID(MTable.getTable_ID("MyTableName"));
		field.setColumnName("MyColumnName");

		final List<ICalloutInstance> calloutInstances = provider.getCallouts(field);
		Assert.assertEquals("Invalid callout instances retrieved",
				Collections.singletonList(calloutInstance),
				calloutInstances);
	}

	@Test
	public void test_StandardCase_CalloutNotFound()
	{
		final MockedCalloutInstance calloutInstance = new MockedCalloutInstance();
		provider.registerCallout("MyTableName", "MyColumnName", calloutInstance);

		final PlainCalloutField field = new PlainCalloutField();
		field.setAD_Table_ID(MTable.getTable_ID("MyTableName"));
		field.setColumnName("MyColumnName" + "_NOT_FOUND");

		final List<ICalloutInstance> calloutInstances = provider.getCallouts(field);
		Assert.assertEquals("Invalid callout instances retrieved",
				Collections.emptyList(),
				calloutInstances);
	}

	@Test
	public void test_CalloutNotRegisteredTwice()
	{
		final String calloutInstanceId = "MockedCalloutInstance-1";

		final MockedCalloutInstance calloutInstance1 = new MockedCalloutInstance(calloutInstanceId);
		Assert.assertTrue("Callout " + calloutInstance1 + " shall be registered",
				provider.registerCallout("MyTableName", "MyColumnName", calloutInstance1));

		final MockedCalloutInstance calloutInstance2 = new MockedCalloutInstance(calloutInstanceId);
		Assert.assertFalse("Callout " + calloutInstance2 + " shall not be registered because it has the same ID as " + calloutInstance1,
				provider.registerCallout("MyTableName", "MyColumnName", calloutInstance2));

	}

	@Test
	public void test_integration_RegisterTo_CalloutFactory()
	{
		final ICalloutFactory calloutFactory = Services.get(ICalloutFactory.class);
		Assert.assertFalse("Provider " + provider + " shall not be registered at this moment",
				calloutFactory.getCalloutProviders().contains(provider));

		// Register some callouts callout and expected to have the provider registered to factory
		for (int i = 1; i <= 100; i++)
		{
			final MockedCalloutInstance calloutInstance = new MockedCalloutInstance();
			provider.registerCallout("MyTableName", "MyColumnName", calloutInstance);
			Assert.assertTrue("Provider " + provider + " shall not be registered at this moment",
					calloutFactory.getCalloutProviders().contains(provider));
		}

		// Make sure provider is registered only once
		final List<ICalloutProvider> programaticProviders = new ArrayList<ICalloutProvider>();
		for (final ICalloutProvider p : calloutFactory.getCalloutProviders())
		{
			if (p instanceof IProgramaticCalloutProvider)
			{
				programaticProviders.add(p);
			}
		}
		Assert.assertEquals("Provider " + provider + " shall be registered only once",
				Collections.singletonList(provider),
				programaticProviders);
	}
}
