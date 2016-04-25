package de.metas.inoutcandidate.api.impl;

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
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestWatcher;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

public abstract class ShipmentScheduleTestBase
{

	@BeforeClass
	public final static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
		POJOWrapper.setDefaultStrictValues(false);
	}

	protected ShipmentScheduleBL shipmentScheduleBL;
	protected Properties ctx;

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

		ctx = Env.getCtx();

		setup();
	}

	protected void setup()
	{
		// nothing to do

	}

	protected Properties getCtx()
	{
		return ctx;
	}

	protected I_M_ShipmentSchedule createShipmentSchedule(final BigDecimal qty)
	{

		final I_M_ShipmentSchedule shipmentSchedule = InterfaceWrapperHelper.create(ctx, I_M_ShipmentSchedule.class, ITrx.TRXNAME_None);
		shipmentSchedule.setQtyOrdered_Calculated(qty);
		shipmentSchedule.setAD_User_ID(123);
		shipmentSchedule.setAD_Org_ID(0);
		shipmentSchedule.setAD_Table_ID(0);
		shipmentSchedule.setBPartnerAddress("address");
		shipmentSchedule.setC_BPartner_ID(0);
		shipmentSchedule.setC_BPartner_Location_ID(0);
		shipmentSchedule.setQtyDeliverable(qty);
		shipmentSchedule.setQtyReserved(qty);

		InterfaceWrapperHelper.save(shipmentSchedule);

		return shipmentSchedule;
	}

}
