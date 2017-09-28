package de.metas.inoutcandidate.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

/*
 * #%L
 * de.metas.swat.base
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


import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

public abstract class ShipmentScheduleTestBase
{
	protected ShipmentScheduleBL shipmentScheduleBL;

	// Masterdata
	protected BigDecimal qtyOrdered;

	/**
	 * Watches current test and dumps the database to console in case of failure
	 */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		shipmentScheduleBL = new ShipmentScheduleBL();

		setup();
	}

	protected void setup()
	{
		// nothing to do
	}

	protected I_M_ShipmentSchedule createShipmentSchedule(final BigDecimal qty)
	{

		final I_M_ShipmentSchedule shipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
		shipmentSchedule.setQtyOrdered_Calculated(qty);
		shipmentSchedule.setAD_User_ID(123);
		shipmentSchedule.setAD_Org_ID(0);
		shipmentSchedule.setAD_Table_ID(0);
		shipmentSchedule.setBPartnerAddress("address");
		shipmentSchedule.setC_BPartner_ID(0);
		shipmentSchedule.setBill_BPartner_ID(0);
		shipmentSchedule.setC_BPartner_Location_ID(0);
		shipmentSchedule.setQtyReserved(qty);

		save(shipmentSchedule);

		return shipmentSchedule;
	}

}
