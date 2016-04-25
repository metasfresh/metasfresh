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
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.metas.adempiere.test.POTest;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

public class ShipmentScheduleBLTest extends ShipmentScheduleTestBase
{

	/**
	 * Calls updateSchedule with an empty list.
	 */
	@Test
	public void testEmpty()
	{
		final String trxName = "trxName";
		final List<OlAndSched> olAndScheds = new ArrayList<OlAndSched>();

		final ShipmentScheduleBL shipmentScheduleBL = new ShipmentScheduleBL();
		shipmentScheduleBL.updateSchedules(POTest.CTX, olAndScheds, true, null, null, trxName);
	}

	/**
	 * set qtyToDeliver_Override to 10, qtyToDeliver is still on 0
	 */
	@Test
	public void testQtyToDeliver1()
	{
		I_M_ShipmentSchedule sched = createShipmentSchedule(new BigDecimal("14"));
		sched.setQtyToDeliver_Override(new BigDecimal("10"));
		shipmentScheduleBL.setQtyToDeliverWhenNullInoutLine(sched);
		Assert.assertEquals("Invalid qtyToDeliver", BigDecimal.ZERO, sched.getQtyToDeliver());
	}

	/**
	 * set qtyToDeliver_Override to 10, set DeliveryRule_Override to F, qtyToDeliver is set on 10;
	 */
	@Test
	public void testQtyToDeliver2()
	{
		I_M_ShipmentSchedule sched = createShipmentSchedule(new BigDecimal("14"));
		sched.setQtyToDeliver_Override(new BigDecimal("10"));
		sched.setDeliveryRule("F");
		shipmentScheduleBL.setQtyToDeliverWhenNullInoutLine(sched);
		Assert.assertEquals("Invalid qtyToDeliver", BigDecimal.valueOf(10), sched.getQtyToDeliver());
	}

	/**
	 * set qtyToDeliver_Override to null, set DeliveryRule_Override to F, qtyToDeliver is set on 14 (as qtyOrdered);
	 */
	@Test
	public void testQtyToDeliver3()
	{
		I_M_ShipmentSchedule sched = createShipmentSchedule(new BigDecimal("14"));
		sched.setDeliveryRule("F");
		shipmentScheduleBL.setQtyToDeliverWhenNullInoutLine(sched);
		Assert.assertEquals("Invalid qtyToDeliver", BigDecimal.valueOf(14), sched.getQtyToDeliver());
	}

	/**
	 * left qtyToDeliver_Override to null, set DeliveryRule_Override to null, qtyToDeliver is set on 0.
	 */
	@Test
	public void testQtyToDeliver4()
	{
		I_M_ShipmentSchedule sched = createShipmentSchedule(new BigDecimal("14"));
		sched.setQtyToDeliver_Override(new BigDecimal("10"));
		shipmentScheduleBL.setQtyToDeliverWhenNullInoutLine(sched);
		Assert.assertEquals("Invalid qtyToDeliver", BigDecimal.valueOf(0), sched.getQtyToDeliver());
	}
}
