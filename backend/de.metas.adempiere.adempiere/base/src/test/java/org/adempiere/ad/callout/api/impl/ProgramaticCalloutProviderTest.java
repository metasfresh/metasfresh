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

import de.metas.util.Services;
import org.adempiere.ad.callout.api.ICalloutFactory;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.ad.callout.spi.ICalloutProvider;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.callout.spi.impl.ProgramaticCalloutProvider;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProgramaticCalloutProviderTest
{
	private ProgramaticCalloutProvider provider;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		provider = (ProgramaticCalloutProvider)Services.get(IProgramaticCalloutProvider.class);
	}

	@Test
	public void test_StandardCase()
	{
		final MockedCalloutField field = MockedCalloutField.createNewField("MyTableName", "MyColumnName");

		final MockedCalloutInstance calloutInstance = new MockedCalloutInstance();
		provider.registerCallout("MyTableName", "MyColumnName", calloutInstance);

		final List<ICalloutInstance> calloutInstances = provider.getCallouts(Env.getCtx(), field.getTableName())
				.getColumnCallouts(field.getColumnName());
		Assertions.assertEquals(Collections.singletonList(calloutInstance),
				calloutInstances, "Invalid callout instances retrieved");
	}

	@Test
	public void test_StandardCase_CalloutNotFound()
	{
		final MockedCalloutField field = MockedCalloutField.createNewField("MyTableName", "MyColumnName" + "_NOT_FOUND");

		final MockedCalloutInstance calloutInstance = new MockedCalloutInstance();
		MockedCalloutField.createNewField("MyTableName", "MyColumnName"); // calling it just to have the AD_Table and AD_Column records
		provider.registerCallout("MyTableName", "MyColumnName", calloutInstance);

		final List<ICalloutInstance> calloutInstances = provider.getCallouts(Env.getCtx(), field.getTableName())
				.getColumnCallouts(field.getColumnName());
		Assertions.assertEquals(Collections.emptyList(),
				calloutInstances,
				"Invalid callout instances retrieved");
	}

	@Test
	public void test_CalloutNotRegisteredTwice()
	{
		final String calloutInstanceId = "MockedCalloutInstance-1";

		MockedCalloutField.createNewField("MyTableName", "MyColumnName"); // calling it just to have the AD_Table and AD_Column records

		final MockedCalloutInstance calloutInstance1 = new MockedCalloutInstance(calloutInstanceId);
		Assertions.assertTrue(provider.registerCallout("MyTableName", "MyColumnName", calloutInstance1),
				"Callout " + calloutInstance1 + " shall be registered");

		final MockedCalloutInstance calloutInstance2 = new MockedCalloutInstance(calloutInstanceId);
		Assertions.assertFalse(provider.registerCallout("MyTableName", "MyColumnName", calloutInstance2),
				"Callout " + calloutInstance2 + " shall not be registered because it has the same ID as " + calloutInstance1);

	}

	@Test
	public void test_integration_RegisterTo_CalloutFactory()
	{
		final CalloutFactory calloutFactory = (CalloutFactory)Services.get(ICalloutFactory.class);
		Assertions.assertFalse(calloutFactory.getCalloutProvidersList().contains(provider),
				"Provider " + provider + " shall not be registered at this moment");

		MockedCalloutField.createNewField("MyTableName", "MyColumnName"); // calling it just to have the AD_Table and AD_Column records

		// Register some column callouts and expected to have the provider registered to factory
		for (int i = 1; i <= 100; i++)
		{
			final MockedCalloutInstance calloutInstance = new MockedCalloutInstance();
			provider.registerCallout("MyTableName", "MyColumnName", calloutInstance);
			Assertions.assertTrue(calloutFactory.getCalloutProvidersList().contains(provider),
					"Provider " + provider + " shall not be registered at this moment");
		}

		// Make sure provider is registered only once
		final List<ICalloutProvider> programaticProviders = new ArrayList<ICalloutProvider>();
		for (final ICalloutProvider p : calloutFactory.getCalloutProvidersList())
		{
			if (p instanceof IProgramaticCalloutProvider)
			{
				programaticProviders.add(p);
			}
		}
		Assertions.assertEquals(Collections.singletonList(provider),
				programaticProviders,
				"Provider " + provider + " shall be registered only once");
	}
}
