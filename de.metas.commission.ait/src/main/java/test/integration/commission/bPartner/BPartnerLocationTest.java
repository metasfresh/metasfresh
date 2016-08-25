package test.integration.commission.bPartner;

/*
 * #%L
 * de.metas.commission.ait
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




import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.Assert;
import org.junit.Test;

import de.metas.adempiere.ait.event.AIntegrationTestDriver;
import de.metas.adempiere.ait.helper.BPartnerHelper;
import de.metas.adempiere.ait.helper.Helper;
import de.metas.adempiere.ait.helper.IHelper;
import de.metas.commission.interfaces.I_C_BPartner_Location;
import de.metas.interfaces.I_C_BPartner;

public class BPartnerLocationTest extends AIntegrationTestDriver
{
	@Override
	public IHelper newHelper()
	{
		return new Helper();
	}

	@Test
	public void testAddress() throws Exception
	{
		final BPartnerHelper bpHelper = getHelper().mkBPartnerHelper();

		// Create partner
		I_C_BPartner bpartner = bpHelper.getC_BPartnerByName("TestDefContact_(*)");

		// Create Locations
		final I_C_BPartner_Location address1 = InterfaceWrapperHelper.create(bpHelper.createBPLocation(bpartner, "address1", false), I_C_BPartner_Location.class);
		final I_C_BPartner_Location address2 = InterfaceWrapperHelper.create(bpHelper.createBPLocation(bpartner, "address2", false), I_C_BPartner_Location.class);

		address1.setIsCommissionToDefault(true);
		InterfaceWrapperHelper.save(address1);

		Assert.assertTrue("IsCommissionToDefault shall be true", address1.isCommissionToDefault());

		address2.setIsCommissionToDefault(true);
		address2.setIsActive(true);
		InterfaceWrapperHelper.save(address2);
		InterfaceWrapperHelper.refresh(address1);

		Assert.assertTrue("IsCommissionToDefault shall be true", address2.isCommissionToDefault());
		Assert.assertFalse("IsCommissionToDefault shall be false", address1.isCommissionToDefault());

		address2.setIsActive(false);
		try
		{
			InterfaceWrapperHelper.save(address2);
			Assert.fail("The address was not saved.");
		}
		catch (AdempiereException e)
		{
			// The saving ended up with an exception, so the test ran properly.
		}

		address2.setIsActive(true);
		try
		{
			InterfaceWrapperHelper.delete(address2);
			Assert.fail("The address was not saved.");
		}
		catch (AdempiereException e)
		{
			// The saving ended up with an exception, so the test ran properly.
		}

	}

}
