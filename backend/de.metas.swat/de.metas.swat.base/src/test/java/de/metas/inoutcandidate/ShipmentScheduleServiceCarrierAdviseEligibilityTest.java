/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.inoutcandidate;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.business.BusinessTestHelper;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_Picking_Job_Schedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers the manual/auto split in {@link ShipmentScheduleService#isEligibleForCarrierAdvise}: manual
 * advise stays eligible while a picking-job-schedule merely EXISTS and is blocked only once picking is
 * actively started (an active, un-shipped picked qty), while auto advise is blocked the moment a
 * picking-job-schedule exists.
 */
class ShipmentScheduleServiceCarrierAdviseEligibilityTest
{
	private ShipmentScheduleService shipmentScheduleService;
	private I_C_UOM uom;
	private final ShipperId shipperId = ShipperId.ofRepoId(540001);

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		shipmentScheduleService = ShipmentScheduleService.newInstanceForUnitTesting();
		uom = BusinessTestHelper.createUomEach();
	}

	private ShipmentScheduleId createSchedule()
	{
		final I_M_ShipmentSchedule record = newInstance(I_M_ShipmentSchedule.class);
		record.setM_Shipper_ID(shipperId.getRepoId());
		record.setCarrier_Advising_Status(CarrierAdviseStatus.Completed.getCode());
		saveRecord(record);
		return ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID());
	}

	private void createPickingJobSchedule(final ShipmentScheduleId scheduleId)
	{
		final I_M_Picking_Job_Schedule record = newInstance(I_M_Picking_Job_Schedule.class);
		record.setM_ShipmentSchedule_ID(scheduleId.getRepoId());
		record.setC_UOM_ID(uom.getC_UOM_ID());
		record.setQtyToPick(BigDecimal.TEN);
		record.setProcessed(false);
		saveRecord(record);
	}

	private void createPickedQty(final ShipmentScheduleId scheduleId, final int qtyPicked)
	{
		final I_M_ShipmentSchedule_QtyPicked record = newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		record.setM_ShipmentSchedule_ID(scheduleId.getRepoId());
		record.setQtyPicked(BigDecimal.valueOf(qtyPicked));
		// M_InOutLine_ID left unset => not shipped, still an active pick
		saveRecord(record);
	}

	/** Builds the manual-advise domain object; the manual seam reads only these fields. */
	private ShipmentSchedule manualDomain(final ShipmentScheduleId scheduleId)
	{
		final Quantity zero = Quantity.of(0, uom);
		return ShipmentSchedule.builder()
				.id(scheduleId)
				.orgId(OrgId.ANY)
				.shipBPartnerId(BPartnerId.ofRepoId(540001))
				.shipLocationId(BPartnerLocationId.ofRepoId(540001, 540001))
				.productId(ProductId.ofRepoId(540001))
				.warehouseId(WarehouseId.MAIN)
				.quantityToDeliver(zero)
				.orderedQuantity(zero)
				.deliveredQuantity(zero)
				.exportStatus(de.metas.inoutcandidate.exportaudit.APIExportStatus.Pending)
				.shipperId(shipperId)
				.isActive(true)
				.carrierAdvisingStatus(CarrierAdviseStatus.Completed)
				.build();
	}

	private I_M_ShipmentSchedule autoRecord(final ShipmentScheduleId scheduleId)
	{
		return org.adempiere.model.InterfaceWrapperHelper.load(scheduleId, I_M_ShipmentSchedule.class);
	}

	@Test
	void manual_blocked_as_soon_as_pickingJobSchedule_exists()
	{
		final ShipmentScheduleId scheduleId = createSchedule();
		createPickingJobSchedule(scheduleId);
		// no picked qty => picking NOT actively started

		assertThat(shipmentScheduleService.isNotEligibleForManualCarrierAdvise(manualDomain(scheduleId), true))
				.as("manual (Advise/Advise_Schedule) advise must be BLOCKED as soon as a picking job schedule exists (picking-job-exists gate)")
				.isTrue();
	}

	@Test
	void manual_blocked_once_picking_actively_started()
	{
		final ShipmentScheduleId scheduleId = createSchedule();
		createPickingJobSchedule(scheduleId);
		createPickedQty(scheduleId, 3); // picking actively started

		assertThat(shipmentScheduleService.isNotEligibleForManualCarrierAdvise(manualDomain(scheduleId), true))
				.as("manual advise must be BLOCKED once picking is actively started")
				.isTrue();
	}

	/**
	 * Manual-SET path (Advise_Manual process, usePickingStartedGate=true):
	 * ALLOWED while picking-job-schedule merely exists but nothing picked.
	 */
	@Test
	void manualSet_allowed_while_pickingJobSchedule_merely_exists()
	{
		final ShipmentScheduleId scheduleId = createSchedule();
		createPickingJobSchedule(scheduleId);
		// no picked qty => picking NOT actively started

		assertThat(shipmentScheduleService.isNotEligibleForManualCarrierSet(manualDomain(scheduleId), true))
				.as("manual-set (Advise_Manual) must be ALLOWED while picking job schedule merely exists (picking-started gate)")
				.isFalse();
	}

	/**
	 * Manual-SET path (Advise_Manual process, usePickingStartedGate=true):
	 * BLOCKED once picking is actively started.
	 */
	@Test
	void manualSet_blocked_once_picking_actively_started()
	{
		final ShipmentScheduleId scheduleId = createSchedule();
		createPickingJobSchedule(scheduleId);
		createPickedQty(scheduleId, 3); // picking actively started

		assertThat(shipmentScheduleService.isNotEligibleForManualCarrierSet(manualDomain(scheduleId), true))
				.as("manual-set (Advise_Manual) must be BLOCKED once picking is actively started")
				.isTrue();
	}

	@Test
	void auto_blocked_as_soon_as_pickingJobSchedule_exists()
	{
		final ShipmentScheduleId scheduleId = createSchedule();
		// eligible while no picking-job-schedule exists
		assertThat(shipmentScheduleService.isEligibleForAutoCarrierAdvise(autoRecord(scheduleId)))
				.as("auto advise eligible before any picking job schedule exists")
				.isTrue();

		createPickingJobSchedule(scheduleId);

		assertThat(shipmentScheduleService.isEligibleForAutoCarrierAdvise(autoRecord(scheduleId)))
				.as("auto advise must remain BLOCKED the moment a picking job schedule exists (unchanged)")
				.isFalse();
	}
}
