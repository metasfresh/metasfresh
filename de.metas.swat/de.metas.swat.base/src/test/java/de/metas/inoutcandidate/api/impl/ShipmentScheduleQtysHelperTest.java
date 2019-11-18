package de.metas.inoutcandidate.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.inout.util.DeliveryGroupCandidate;
import org.adempiere.inout.util.DeliveryGroupCandidateGroupId;
import org.adempiere.inout.util.DeliveryLineCandidate;
import org.adempiere.inout.util.IShipmentSchedulesDuringUpdate;
import org.adempiere.inout.util.IShipmentSchedulesDuringUpdate.CompleteStatus;
import org.adempiere.inout.util.ShipmentSchedulesDuringUpdate;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Order;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.i18n.IMsgBL;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.DeliveryRule;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;

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

public class ShipmentScheduleQtysHelperTest
{

	private String deliveryStopStatusMessage;

	private String closedStatusMessage;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final IMsgBL msgBL = Services.get(IMsgBL.class);
		deliveryStopStatusMessage = msgBL.getMsg(Env.getCtx(), ShipmentScheduleQtysHelper.MSG_DeliveryStopStatus);
		closedStatusMessage = msgBL.getMsg(Env.getCtx(), ShipmentScheduleQtysHelper.MSG_ClosedStatus);
	}

	private static I_M_ShipmentSchedule createShipmentSchedule(final BigDecimal qty)
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
		shipmentSchedule.setDeliveryRule(DeliveryRule.AVAILABILITY.getCode());

		save(shipmentSchedule);

		return shipmentSchedule;
	}

	/**
	 * set qtyToDeliver_Override to 10, qtyToDeliver is still on 0
	 */
	@Test
	public void testQtyToDeliver1()
	{
		I_M_ShipmentSchedule sched = createShipmentSchedule(new BigDecimal("14"));
		sched.setQtyToDeliver_Override(new BigDecimal("10"));
		ShipmentScheduleQtysHelper.setQtyToDeliverForDiscardedShipmentSchedule(sched);
		assertThat(sched.getQtyToDeliver()).as("qtyToDeliver").isZero();
	}

	/**
	 * set qtyToDeliver_Override to 10, set DeliveryRule_Override to F, qtyToDeliver is set on 10;
	 */
	@Test
	public void testQtyToDeliver2()
	{
		I_M_ShipmentSchedule sched = createShipmentSchedule(new BigDecimal("14"));
		sched.setQtyToDeliver_Override(new BigDecimal("10"));
		sched.setDeliveryRule(DeliveryRule.FORCE.getCode());
		ShipmentScheduleQtysHelper.setQtyToDeliverForDiscardedShipmentSchedule(sched);
		assertThat(sched.getQtyToDeliver()).as("qtyToDeliver").isEqualByComparingTo("10");
	}

	/**
	 * set qtyToDeliver_Override to null, set DeliveryRule_Override to F, qtyToDeliver is set on 14 (as qtyOrdered);
	 */
	@Test
	public void testQtyToDeliver3()
	{
		I_M_ShipmentSchedule sched = createShipmentSchedule(new BigDecimal("14"));
		sched.setDeliveryRule(DeliveryRule.FORCE.getCode());
		ShipmentScheduleQtysHelper.setQtyToDeliverForDiscardedShipmentSchedule(sched);
		assertThat(sched.getQtyToDeliver()).as("qtyToDeliver").isEqualByComparingTo("14");
	}

	/**
	 * left qtyToDeliver_Override to null, set DeliveryRule_Override to null, qtyToDeliver is set on 0.
	 */
	@Test
	public void testQtyToDeliver4()
	{
		I_M_ShipmentSchedule sched = createShipmentSchedule(new BigDecimal("14"));
		sched.setQtyToDeliver_Override(new BigDecimal("10"));
		ShipmentScheduleQtysHelper.setQtyToDeliverForDiscardedShipmentSchedule(sched);
		assertThat(sched.getQtyToDeliver()).as("qtyToDeliver").isZero();
	}

	@Test
	public void test_updateQtyToDeliver_LineCandidate()
	{
		final BigDecimal qtyOrdered = new BigDecimal("14");
		final I_M_ShipmentSchedule sched = createShipmentSchedule(qtyOrdered);
		final TableRecordReference orderReference = TableRecordReference.of(I_C_Order.Table_Name, 10);

		final DeliveryGroupCandidate deliveryGroupCandidate = DeliveryGroupCandidate.builder()
				.bPartnerAddress("bPartnerAddress")
				.groupId(DeliveryGroupCandidateGroupId.of(orderReference))
				.shipperId(ShipperId.optionalOfRepoId(20))
				.warehouseId(WarehouseId.ofRepoId(30))
				.build();
		final DeliveryLineCandidate deliveryLineCandidate = deliveryGroupCandidate.createAndAddLineCandidate(sched, CompleteStatus.OK);
		deliveryLineCandidate.setQtyToDeliver(BigDecimal.TEN);

		final IShipmentSchedulesDuringUpdate shipmentCandidates = new ShipmentSchedulesDuringUpdate();
		shipmentCandidates.addGroup(deliveryGroupCandidate);
		shipmentCandidates.addLine(deliveryLineCandidate);

		final OlAndSched olAndSched = createOlAndSchedForSchedAndQtyOrdered(sched, qtyOrdered);

		sched.setDeliveryRule(DeliveryRule.AVAILABILITY.getCode());
		ShipmentScheduleQtysHelper.updateQtyToDeliver(olAndSched, shipmentCandidates);
		assertThat(sched.getQtyToDeliver()).isEqualByComparingTo("10");

		sched.setDeliveryRule(DeliveryRule.FORCE.getCode());
		sched.setQtyOrdered_Override(qtyOrdered);
		ShipmentScheduleQtysHelper.updateQtyToDeliver(olAndSched, shipmentCandidates);
		assertThat(sched.getQtyToDeliver())
				.as("Even with DeliveryRule=F and a different QtyOrdered_Override, the deliveryLineCandidate's Qty shall be used")
				.isEqualByComparingTo("10");
		// ...note that the logic will set the deliveryLineCandidate's qty to the expected value of QtyOrdered_Override, which is however not the ShipmentScheduleQtysHelper's business
	}

	@Test
	public void test_updateQtyToDeliver_DeliveryStop()
	{
		final BigDecimal qtyOrdered = new BigDecimal("14");
		final BigDecimal qtyToDeliver_Override = new BigDecimal("10");

		final I_M_ShipmentSchedule sched = createShipmentSchedule(qtyOrdered);
		sched.setQtyToDeliver_Override(qtyToDeliver_Override);
		sched.setDeliveryRule(DeliveryRule.FORCE.getCode());

		final OlAndSched olAndSched = createOlAndSchedForSchedAndQtyOrdered(sched, qtyOrdered);
		final IShipmentSchedulesDuringUpdate shipmentCandidates = new ShipmentSchedulesDuringUpdate();

		sched.setIsDeliveryStop(false);
		ShipmentScheduleQtysHelper.updateQtyToDeliver(olAndSched, shipmentCandidates);

		assertThat(sched.getQtyToDeliver())
				.as("QtyToDeliver (with NO delivery stop and DeliveryRule=Force)")
				.isEqualByComparingTo(qtyToDeliver_Override);

		sched.setIsDeliveryStop(true);
		ShipmentScheduleQtysHelper.updateQtyToDeliver(olAndSched, shipmentCandidates);

		assertThat(sched.getQtyToDeliver())
				.as("QtyToDeliver (with delivery stop, despite DeliveryRule=Force)")
				.isZero();
		assertThat(sched.getStatus()).contains(deliveryStopStatusMessage);
	}

	private OlAndSched createOlAndSchedForSchedAndQtyOrdered(final I_M_ShipmentSchedule sched, final BigDecimal qtyOrdered)
	{
		final OlAndSched olAndSched = OlAndSched.builder()
				.shipmentSchedule(sched)
				.deliverRequest(() -> qtyOrdered)
				.build();
		return olAndSched;
	}

	@Test
	public void test_updateQtyToDeliver_closed()
	{
		final BigDecimal qtyToDeliver_Override = new BigDecimal("10");
		final BigDecimal qtyOrdered = new BigDecimal("14");
		final I_M_ShipmentSchedule sched = createShipmentSchedule(qtyOrdered);
		sched.setQtyToDeliver_Override(qtyToDeliver_Override);
		sched.setDeliveryRule(DeliveryRule.FORCE.getCode());

		final OlAndSched olAndSched = createOlAndSchedForSchedAndQtyOrdered(sched, qtyOrdered);

		ShipmentScheduleQtysHelper.updateQtyToDeliver(olAndSched, new ShipmentSchedulesDuringUpdate());
		assertThat(sched.getQtyToDeliver()).isEqualByComparingTo("10"); // guard

		sched.setIsClosed(true);
		ShipmentScheduleQtysHelper.updateQtyToDeliver(olAndSched, new ShipmentSchedulesDuringUpdate());
		assertThat(sched.getQtyToDeliver()).isZero();
		assertThat(sched.getStatus()).contains(closedStatusMessage);
	}

	@Test
	public void test_updateQtyToDeliver_closed_and_deliveryStop()
	{
		final BigDecimal qtyToDeliver_Override = new BigDecimal("10");
		final BigDecimal qtyOrdered = new BigDecimal("14");
		final I_M_ShipmentSchedule sched = createShipmentSchedule(qtyOrdered);
		sched.setQtyToDeliver_Override(qtyToDeliver_Override);
		sched.setDeliveryRule(DeliveryRule.FORCE.getCode());

		final OlAndSched olAndSched = createOlAndSchedForSchedAndQtyOrdered(sched, qtyOrdered);

		sched.setIsClosed(true);
		sched.setIsDeliveryStop(true);
		ShipmentScheduleQtysHelper.updateQtyToDeliver(olAndSched, new ShipmentSchedulesDuringUpdate());
		assertThat(sched.getQtyToDeliver()).isZero();
		assertThat(sched.getStatus()).contains(closedStatusMessage);
		assertThat(sched.getStatus()).contains(deliveryStopStatusMessage);
	}
}
