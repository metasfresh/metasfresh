package de.metas.inoutcandidate.api.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.inout.util.IShipmentCandidates;
import org.adempiere.inout.util.ShipmentCandidates;
import org.junit.Assert;
import org.junit.Test;

import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.X_M_ShipmentSchedule;

/*
 * #%L
 * de.metas.swat.base
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

public class ShipmentScheduleQtysHelperTest extends ShipmentScheduleTestBase
{
	/**
	 * set qtyToDeliver_Override to 10, qtyToDeliver is still on 0
	 */
	@Test
	public void testQtyToDeliver1()
	{
		I_M_ShipmentSchedule sched = createShipmentSchedule(new BigDecimal("14"));
		sched.setQtyToDeliver_Override(new BigDecimal("10"));
		ShipmentScheduleQtysHelper.setQtyToDeliverWhenNullInoutLine(sched);
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
		ShipmentScheduleQtysHelper.setQtyToDeliverWhenNullInoutLine(sched);
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
		ShipmentScheduleQtysHelper.setQtyToDeliverWhenNullInoutLine(sched);
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
		ShipmentScheduleQtysHelper.setQtyToDeliverWhenNullInoutLine(sched);
		Assert.assertEquals("Invalid qtyToDeliver", BigDecimal.valueOf(0), sched.getQtyToDeliver());
	}

	@Test
	public void test_updateQtyToDeliver_DeliveryStop()
	{
		final BigDecimal qtyOrdered = new BigDecimal("14");
		final BigDecimal qtyToDeliver_Override = new BigDecimal("10");

		final I_M_ShipmentSchedule sched = createShipmentSchedule(qtyOrdered);
		sched.setQtyToDeliver_Override(qtyToDeliver_Override);
		sched.setDeliveryRule(X_M_ShipmentSchedule.DELIVERYRULE_Force);

		final OlAndSched olAndSched = OlAndSched.builder()
				.shipmentSchedule(sched)
				.deliverRequest(() -> qtyOrdered)
				.build();
		final IShipmentCandidates shipmentCandidates = new ShipmentCandidates();

		sched.setIsDeliveryStop(false);
		ShipmentScheduleQtysHelper.updateQtyToDeliver(olAndSched, shipmentCandidates);

		assertThat(sched.getQtyToDeliver())
				.as("QtyToDeliver (with NO delivery stop)")
				.isEqualByComparingTo(qtyToDeliver_Override);

		sched.setIsDeliveryStop(true);
		ShipmentScheduleQtysHelper.updateQtyToDeliver(olAndSched, shipmentCandidates);

		assertThat(sched.getQtyToDeliver())
				.as("QtyToDeliver (with delivery stop)")
				.isZero();
	}

}
