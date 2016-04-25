package test.integration.swat.bPartner;

/*
 * #%L
 * de.metas.swat.ait
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


import org.compiere.model.I_C_BPartner;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.metas.adempiere.ait.event.AIntegrationTestDriver;
import de.metas.adempiere.ait.event.EventType;
import de.metas.adempiere.ait.helper.Helper;
import de.metas.adempiere.ait.helper.IHelper;
import de.metas.adempiere.ait.test.IntegrationTestRunner;

@RunWith(IntegrationTestRunner.class)
public class BPartnerTestDriver extends AIntegrationTestDriver
{

	@Override
	public IHelper newHelper()
	{
		return new Helper();
	}

	@Test
	public void createBPartner()
	{
		// give our listeners a chance to verify that the system is in the correct state for testing
		fireTestEvent(EventType.BPARTNER_CREATE_BEFORE, null);

		getHelper().getConfig().setC_BPartner_Name("BPartnerTestDriver_createBPartner_(*)");
		
		// Note not calling
		// helper.getC_BPartner(AIntegrationTestDriver.testConfig.getC_BPartner_Value()); because we explicitly want to
		// make a new BPartner here.
		final I_C_BPartner bPartner = getHelper().mkBPartnerHelper().getC_BPartner(getHelper().getConfig());

		// give out listeners a chance to verify the system state after bPartner has been created
		fireTestEvent(EventType.BPARTNER_CREATE_AFTER, bPartner);
	}

	@AfterClass
	public static void allListenersCalled()
	{
		assertAllListenersCalled(BPartnerTestDriver.class);
	}
}
