package de.metas.inoutcandidate.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.metas.i18n.IMsgBL;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.DeliveryRule;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

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

	@Builder(builderMethodName = "shipmentSchedule", builderClassName = "ShipmentScheduleBuilder")
	private static I_M_ShipmentSchedule createShipmentSchedule(
			@NonNull final String qty,
			final String qtyToDeliverOverride,
			@NonNull final DeliveryRule deliveryRule)
	{
		final I_M_ShipmentSchedule shipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
		shipmentSchedule.setAD_User_ID(123);
		shipmentSchedule.setAD_Org_ID(0);
		shipmentSchedule.setAD_Table_ID(0);
		shipmentSchedule.setBPartnerAddress("address");
		shipmentSchedule.setC_BPartner_ID(0);
		shipmentSchedule.setBill_BPartner_ID(0);
		shipmentSchedule.setC_BPartner_Location_ID(0);

		shipmentSchedule.setDeliveryRule(deliveryRule.getCode());

		shipmentSchedule.setQtyOrdered_Calculated(new BigDecimal(qty));
		shipmentSchedule.setQtyReserved(new BigDecimal(qty));

		if (qtyToDeliverOverride != null)
		{
			shipmentSchedule.setQtyToDeliver_Override(new BigDecimal(qtyToDeliverOverride));
		}

		saveRecord(shipmentSchedule);

		return shipmentSchedule;
	}

	@Nested
	public class setQtyToDeliverForDiscardedShipmentSchedule
	{
		@Test
		public void not_DeliveryRule_Force()
		{
			final I_M_ShipmentSchedule sched = shipmentSchedule()
					.qty("14")
					.qtyToDeliverOverride("10")
					.deliveryRule(DeliveryRule.AVAILABILITY)
					.build();

			ShipmentScheduleQtysHelper.setQtyToDeliverForDiscardedShipmentSchedule(sched);
			assertThat(sched.getQtyToDeliver()).as("qtyToDeliver").isZero();
		}

		/**
		 * set qtyToDeliver_Override to null, set DeliveryRule_Override to F, qtyToDeliver is set on 14 (as qtyOrdered);
		 */
		@Test
		public void withoutQtyToDeliverOverride()
		{
			final I_M_ShipmentSchedule sched = shipmentSchedule()
					.qty("14")
					.deliveryRule(DeliveryRule.FORCE)
					.build();

			ShipmentScheduleQtysHelper.setQtyToDeliverForDiscardedShipmentSchedule(sched);
			assertThat(sched.getQtyToDeliver()).as("qtyToDeliver").isEqualByComparingTo("14");
		}

		/**
		 * set qtyToDeliver_Override to 10, set DeliveryRule_Override to F, qtyToDeliver is set on 10;
		 */
		@Test
		public void withQtyToDeliverOverride()
		{
			final I_M_ShipmentSchedule sched = shipmentSchedule()
					.qty("14")
					.qtyToDeliverOverride("10")
					.deliveryRule(DeliveryRule.FORCE)
					.build();

			ShipmentScheduleQtysHelper.setQtyToDeliverForDiscardedShipmentSchedule(sched);
			assertThat(sched.getQtyToDeliver()).as("qtyToDeliver").isEqualByComparingTo("10");
		}
	}

	@Nested
	public class updateQtyToDeliver
	{
		private OlAndSched createOlAndSchedForSchedAndQtyOrdered(final I_M_ShipmentSchedule sched, final String qtyOrdered)
		{
			final BigDecimal qtyOrderedBD = new BigDecimal(qtyOrdered);

			return OlAndSched.builder()
					.shipmentSchedule(sched)
					.deliverRequest(() -> qtyOrderedBD)
					.build();
		}

		@Test
		public void respect_DeliveryLineCandidate_QtyToDeliver()
		{
			final I_M_ShipmentSchedule sched = shipmentSchedule()
					.qty("14")
					.deliveryRule(DeliveryRule.AVAILABILITY)
					.build();

			final DeliveryGroupCandidate deliveryGroupCandidate = DeliveryGroupCandidate.builder()
					.bPartnerAddress("bPartnerAddress")
					.groupId(DeliveryGroupCandidateGroupId.of(TableRecordReference.of(I_C_Order.Table_Name, 10)))
					.shipperId(ShipperId.optionalOfRepoId(20))
					.warehouseId(WarehouseId.ofRepoId(30))
					.build();
			final DeliveryLineCandidate deliveryLineCandidate = deliveryGroupCandidate.createAndAddLineCandidate(sched, CompleteStatus.OK);
			deliveryLineCandidate.setQtyToDeliver(new BigDecimal("10"));

			final IShipmentSchedulesDuringUpdate shipmentCandidates = new ShipmentSchedulesDuringUpdate();
			shipmentCandidates.addGroup(deliveryGroupCandidate);
			shipmentCandidates.addLine(deliveryLineCandidate);

			final OlAndSched olAndSched = createOlAndSchedForSchedAndQtyOrdered(sched, "14");

			sched.setDeliveryRule(DeliveryRule.AVAILABILITY.getCode());
			ShipmentScheduleQtysHelper.updateQtyToDeliver(olAndSched, shipmentCandidates);
			assertThat(sched.getQtyToDeliver()).isEqualByComparingTo("10");

			sched.setDeliveryRule(DeliveryRule.FORCE.getCode());
			sched.setQtyOrdered_Override(new BigDecimal("14"));
			ShipmentScheduleQtysHelper.updateQtyToDeliver(olAndSched, shipmentCandidates);
			assertThat(sched.getQtyToDeliver())
					.as("Even with DeliveryRule=F and a different QtyOrdered_Override, the deliveryLineCandidate's Qty shall be used")
					.isEqualByComparingTo("10");
			// ...note that the logic will set the deliveryLineCandidate's qty to the expected value of QtyOrdered_Override, which is however not the ShipmentScheduleQtysHelper's business
		}

		@Test
		public void deliveryStop()
		{
			final I_M_ShipmentSchedule sched = shipmentSchedule()
					.qty("14")
					.qtyToDeliverOverride("10")
					.deliveryRule(DeliveryRule.FORCE)
					.build();

			final OlAndSched olAndSched = createOlAndSchedForSchedAndQtyOrdered(sched, "14");
			final IShipmentSchedulesDuringUpdate shipmentCandidates = new ShipmentSchedulesDuringUpdate();

			sched.setIsDeliveryStop(false);
			ShipmentScheduleQtysHelper.updateQtyToDeliver(olAndSched, shipmentCandidates);
			assertThat(sched.getQtyToDeliver())
					.as("QtyToDeliver (with NO delivery stop and DeliveryRule=Force)")
					.isEqualByComparingTo("10");

			sched.setIsDeliveryStop(true);
			ShipmentScheduleQtysHelper.updateQtyToDeliver(olAndSched, shipmentCandidates);
			assertThat(sched.getQtyToDeliver())
					.as("QtyToDeliver (with delivery stop, despite DeliveryRule=Force)")
					.isZero();
			assertThat(sched.getStatus()).contains(deliveryStopStatusMessage);
		}

		@Test
		public void closed()
		{
			final I_M_ShipmentSchedule sched = shipmentSchedule()
					.qty("14")
					.qtyToDeliverOverride("10")
					.deliveryRule(DeliveryRule.FORCE)
					.build();

			final OlAndSched olAndSched = createOlAndSchedForSchedAndQtyOrdered(sched, "14");

			ShipmentScheduleQtysHelper.updateQtyToDeliver(olAndSched, new ShipmentSchedulesDuringUpdate());
			assertThat(sched.getQtyToDeliver()).isEqualByComparingTo("10"); // guard

			sched.setIsClosed(true);
			ShipmentScheduleQtysHelper.updateQtyToDeliver(olAndSched, new ShipmentSchedulesDuringUpdate());
			assertThat(sched.getQtyToDeliver()).isZero();
			assertThat(sched.getStatus()).contains(closedStatusMessage);
		}

		@Test
		public void closed_and_deliveryStop()
		{
			final I_M_ShipmentSchedule sched = shipmentSchedule()
					.qty("14")
					.qtyToDeliverOverride("10")
					.deliveryRule(DeliveryRule.FORCE)
					.build();

			final OlAndSched olAndSched = createOlAndSchedForSchedAndQtyOrdered(sched, "14");

			sched.setIsClosed(true);
			sched.setIsDeliveryStop(true);
			ShipmentScheduleQtysHelper.updateQtyToDeliver(olAndSched, new ShipmentSchedulesDuringUpdate());
			assertThat(sched.getQtyToDeliver()).isZero();
			assertThat(sched.getStatus()).contains(closedStatusMessage);
			assertThat(sched.getStatus()).contains(deliveryStopStatusMessage);
		}
	}
}
