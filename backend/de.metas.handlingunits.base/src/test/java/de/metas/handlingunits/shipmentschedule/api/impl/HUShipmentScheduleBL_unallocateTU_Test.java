package de.metas.handlingunits.shipmentschedule.api.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.impl.ShipmentScheduleListAllocationSource;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleDAO;
import de.metas.handlingunits.shipmentschedule.util.ShipmentScheduleHelper;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers the shared-TU consignee-reset guard ({@code HUShipmentScheduleBL.resetConsigneeIfNoActivePickedRows},
 * backed by {@code IHUShipmentScheduleDAO.hasActiveQtyPickedForTopLevelHU}) on the ONE reset call-site
 * ({@link IHUShipmentScheduleBL#unallocateTU}) that is <b>dead code</b> today: its only caller,
 * {@code ShipmentScheduleHUAllocations.deleteAllocations}, is never constructed anywhere in the codebase, so
 * {@code unallocateTU} is not reachable from any mobile or desktop UI action. A faithful Playwright E2E is
 * therefore impossible for this path; the identical guard IS already E2E-covered on the two mobile-reachable
 * sites ({@code reduceQtyPickedForPickToTU} via {@code picking_partial_unpack_TU_floor_two_lines.spec.js} and
 * {@code deleteByTopLevelHUsAndShipmentScheduleId}). This test is the BL-level substitute for path (C).
 * <p>
 * {@code unallocateTU} is public and directly invokable, so this test calls it directly (no fabricated /
 * unreachable state): it builds a real bare TU shared by TWO real {@link I_M_ShipmentSchedule}s via the same
 * production allocation path ({@link ShipmentScheduleListAllocationSource} + {@link HUProducerDestination} +
 * {@link HULoader}, as already proven by {@code ShipmentScheduleListAllocationSourceTest}), then drives
 * {@code unallocateTU} for each schedule in turn. Per {@code unallocateTU}'s own contract (it throws if the
 * calling schedule's active QtyPicked qty on the TU is non-zero), the row being unallocated is zeroed first —
 * this mirrors the real caller's precondition ({@code AbstractHUAllocations} always zeroes/consumes a
 * schedule's qty before invoking {@code deleteAllocations}/{@code unallocateTU}), not a fabricated state.
 */
class HUShipmentScheduleBL_unallocateTU_Test extends AbstractHUTest
{
	private ShipmentScheduleHelper shipmentScheduleHelper;
	private I_M_HU_PI tuPI;

	private IHUShipmentScheduleBL huShipmentScheduleBL;
	private IHUShipmentScheduleDAO huShipmentScheduleDAO;
	private IHandlingUnitsBL handlingUnitsBL;

	@Override
	protected void initialize()
	{
		huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
		huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		shipmentScheduleHelper = new ShipmentScheduleHelper(helper);

		tuPI = helper.createHUDefinition("unallocateTU-TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item tuItem = helper.createHU_PI_Item_Material(tuPI);
		helper.assignProduct(tuItem, pTomatoId, new BigDecimal("100"), uomEach);
	}

	@Test
	void unallocateTU_retainsConsignee_whileOtherScheduleStillHoldsPickedQty_thenStripsItWhenLast()
	{
		// Two schedules sharing one bare TU, mirroring a 2-line sales_order aggregation onto a shared bare TU.
		final I_M_ShipmentSchedule schedule1 = shipmentScheduleHelper.createShipmentSchedule(pTomato, uomEach, new BigDecimal("5"), BigDecimal.ZERO);
		final I_M_ShipmentSchedule schedule2 = shipmentScheduleHelper.createShipmentSchedule(pTomato, uomEach, new BigDecimal("10"), BigDecimal.ZERO);

		final ShipmentScheduleListAllocationSource source = new ShipmentScheduleListAllocationSource(Arrays.asList(schedule1, schedule2));
		final HUProducerDestination destination = HUProducerDestination.of(tuPI);

		final IHUContext huContext = helper.getHUContext();
		final IAllocationRequest request = AllocationUtils.createQtyRequest(huContext, pTomato, new BigDecimal("12"), uomEach, helper.getTodayZonedDateTime());
		HULoader.of(source, destination).load(request);

		final List<I_M_HU> createdHUs = destination.getCreatedHUs();
		assertThat(createdHUs).as("both schedules' picked qty must land on ONE shared bare TU").hasSize(1);
		I_M_HU tuHU = createdHUs.get(0);
		final HuId tuId = HuId.ofRepoId(tuHU.getM_HU_ID());

		// Stamp the consignee on the shared TU, as the real pick flow does (setHUPartnerAndLocationFromSched) --
		// this test targets only the reset GUARD, so the stamp is applied directly.
		tuHU.setC_BPartner_ID(schedule1.getC_BPartner_ID());
		tuHU.setC_BPartner_Location_ID(schedule1.getC_BPartner_Location_ID());
		InterfaceWrapperHelper.save(tuHU);
		assertThat(schedule1.getC_BPartner_ID()).as("test precondition: schedules share one customer").isEqualTo(schedule2.getC_BPartner_ID());

		final String trxName = ITrx.TRXNAME_ThreadInherited;

		// schedule1's own row must be zeroed before calling unallocateTU (its own contract: throws otherwise).
		final I_M_ShipmentSchedule_QtyPicked row1 = CollectionUtils.singleElement(
				huShipmentScheduleDAO.retrieveSchedsQtyPickedForTU(schedule1.getM_ShipmentSchedule_ID(), tuId.getRepoId(), trxName));
		assertThat(row1.getQtyPicked()).as("schedule1's picked qty before zeroing").isGreaterThan(BigDecimal.ZERO);
		row1.setQtyPicked(BigDecimal.ZERO);
		InterfaceWrapperHelper.save(row1);

		// unallocate schedule1 while schedule2 STILL holds an active (non-zero) picked row on the same TU.
		huShipmentScheduleBL.unallocateTU(schedule1, tuHU, trxName);

		tuHU = handlingUnitsBL.getById(tuId);
		assertThat(tuHU.getC_BPartner_ID())
				.as("consignee must be RETAINED: schedule2 still holds an active picked row on the shared TU")
				.isEqualTo(schedule1.getC_BPartner_ID());
		assertThat(tuHU.getC_BPartner_Location_ID())
				.as("consignee location must be RETAINED: schedule2 still holds an active picked row on the shared TU")
				.isEqualTo(schedule1.getC_BPartner_Location_ID());

		// now zero + unallocate schedule2 (the LAST schedule holding an active row on the TU).
		final I_M_ShipmentSchedule_QtyPicked row2 = CollectionUtils.singleElement(
				huShipmentScheduleDAO.retrieveSchedsQtyPickedForTU(schedule2.getM_ShipmentSchedule_ID(), tuId.getRepoId(), trxName));
		assertThat(row2.getQtyPicked()).as("schedule2's picked qty before zeroing").isGreaterThan(BigDecimal.ZERO);
		row2.setQtyPicked(BigDecimal.ZERO);
		InterfaceWrapperHelper.save(row2);

		huShipmentScheduleBL.unallocateTU(schedule2, tuHU, trxName);

		tuHU = handlingUnitsBL.getById(tuId);
		assertThat(tuHU.getC_BPartner_ID())
				.as("consignee must be STRIPPED: no schedule holds an active picked row on the TU anymore")
				.isLessThanOrEqualTo(0);
		assertThat(tuHU.getC_BPartner_Location_ID())
				.as("consignee location must be STRIPPED: no schedule holds an active picked row on the TU anymore")
				.isLessThanOrEqualTo(0);
	}
}
