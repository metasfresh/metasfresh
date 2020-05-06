package de.metas.inoutcandidate.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
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
}
