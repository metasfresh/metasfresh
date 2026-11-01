package de.metas.inoutcandidate.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.inout.util.DeliveryGroupCandidate;
import org.adempiere.inout.util.DeliveryGroupCandidateGroupId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Order;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ShipmentScheduleReferencedLine;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.order.DeliveryRule;
import de.metas.shipping.ShipperId;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class ShipmentScheduleUpdaterTest
{
	private static final ShipperId SHIPPER_ID = ShipperId.ofRepoId(20);
	private static final WarehouseId WAREHOUSE_ID = WarehouseId.ofRepoId(35);

	private ShipmentScheduleUpdater shipmentScheduleUpdater;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		this.shipmentScheduleUpdater = ShipmentScheduleUpdater.newInstanceForUnitTesting();
	}

	/**
	 * Calls updateSchedule with an empty list.
	 */
	@Test
	public void updateSchedules_emptyList()
	{
		final List<OlAndSched> olAndScheds = new ArrayList<>();

		shipmentScheduleUpdater.updateSchedules(Env.getCtx(), olAndScheds);
	}

	@Test
	public void createGroup()
	{
		final I_M_ShipmentSchedule sched = newInstance(I_M_ShipmentSchedule.class);
		sched.setBPartnerAddress_Override("bPartnerAddress");
		sched.setM_Warehouse_Override_ID(WAREHOUSE_ID.getRepoId());
		// save(sched); // not needed

		final TableRecordReference orderRef = TableRecordReference.of(I_C_Order.Table_Name, 10);
		final ShipmentScheduleReferencedLine scheduleSourceDoc = ShipmentScheduleReferencedLine.builder()
				.recordRef(orderRef)
				.shipperId(ShipperId.optionalOfRepoId(SHIPPER_ID.getRepoId()))
				.warehouseId(WarehouseId.ofRepoId(30)) // different from the sched's effective WH
				.documentLineDescriptor(OrderLineDescriptor.builder().build()) // documentLineDescriptor is not relevant for this test
				.build();

		// invoke method under test
		final DeliveryGroupCandidate result = shipmentScheduleUpdater.createGroup(scheduleSourceDoc, sched);

		assertThat(result.getGroupId()).isEqualTo(DeliveryGroupCandidateGroupId.of(orderRef));
		assertThat(result.getShipperId().get()).isEqualTo(SHIPPER_ID);
		assertThat(result.getWarehouseId()).isEqualTo(WAREHOUSE_ID);
		assertThat(result.getBPartnerAddress()).isEqualTo("bPartnerAddress");
	}

	@Test
	public void updateProcessedFlag()
	{
		final I_M_ShipmentSchedule sched = newInstance(I_M_ShipmentSchedule.class);
		sched.setQtyReserved(BigDecimal.TEN);

		shipmentScheduleUpdater.updateProcessedFlag(sched);
		assertThat(sched.isProcessed()).isFalse();

		sched.setIsClosed(true);
		shipmentScheduleUpdater.updateProcessedFlag(sched);
		assertThat(sched.isProcessed()).isTrue();
	}

	@Test
	public void updateProcessedFlag_explicitZeroQtyToDeliverOverride_isTreatedAsOverride()
	{
		final I_M_ShipmentSchedule schedWithZeroOverride = newInstance(I_M_ShipmentSchedule.class);
		schedWithZeroOverride.setQtyToDeliver_Override(BigDecimal.ZERO); // explicit 0
		shipmentScheduleUpdater.updateProcessedFlag(schedWithZeroOverride);
		assertThat(schedWithZeroOverride.isProcessed())
				.as("explicit QtyToDeliver_Override=0 is an override => schedule is not processed")
				.isFalse();
	}

	@Test
	public void updateProcessedFlag_nullQtyToDeliverOverride_nothingReserved_isProcessed()
	{
		final I_M_ShipmentSchedule schedWithoutOverride = newInstance(I_M_ShipmentSchedule.class); // QtyToDeliver_Override=NULL
		shipmentScheduleUpdater.updateProcessedFlag(schedWithoutOverride);
		assertThat(schedWithoutOverride.isProcessed())
				.as("NULL QtyToDeliver_Override, nothing reserved/picked => processed")
				.isTrue();
	}

	private OlAndSched createOlAndSched(final String qtyOrdered, final String qtyToDeliverOverride, final String qtyToDeliverOverrideFulfilled)
	{
		final I_M_ShipmentSchedule sched = newInstance(I_M_ShipmentSchedule.class);
		sched.setQtyToDeliver_Override(new BigDecimal(qtyToDeliverOverride));
		sched.setQtyToDeliver_OverrideFulfilled(new BigDecimal(qtyToDeliverOverrideFulfilled));
		saveRecord(sched);

		final BigDecimal qtyOrderedBD = new BigDecimal(qtyOrdered);
		return OlAndSched.builder()
				.shipmentSchedule(sched)
				.deliverRequest(() -> qtyOrderedBD)
				.build();
	}

	@Test
	public void computeQtyRequiredForOlAndSched_explicitZeroOverride_nothingFulfilled_isZero()
	{
		final OlAndSched olAndSched = createOlAndSched("72", "0", "0");

		final BigDecimal qtyRequired = shipmentScheduleUpdater.computeQtyRequiredForOlAndSched(olAndSched, DeliveryRule.FORCE);

		assertThat(qtyRequired).isEqualByComparingTo("0");
	}

	@Test
	public void computeQtyRequiredForOlAndSched_positiveOverride_partiallyFulfilled_isDelta()
	{
		final OlAndSched olAndSched = createOlAndSched("72", "10", "3");

		final BigDecimal qtyRequired = shipmentScheduleUpdater.computeQtyRequiredForOlAndSched(olAndSched, DeliveryRule.FORCE);

		assertThat(qtyRequired).isEqualByComparingTo("7");
	}

	@Test
	public void computeQtyRequiredForOlAndSched_positiveOverride_fullyFulfilled_fallsBackToQtyOrderedMinusDelivered()
	{
		final OlAndSched olAndSched = createOlAndSched("72", "10", "10");

		final BigDecimal qtyRequired = shipmentScheduleUpdater.computeQtyRequiredForOlAndSched(olAndSched, DeliveryRule.FORCE);

		assertThat(qtyRequired).isEqualByComparingTo("72"); // QtyOrdered(72) - QtyDelivered(0)
	}
}
