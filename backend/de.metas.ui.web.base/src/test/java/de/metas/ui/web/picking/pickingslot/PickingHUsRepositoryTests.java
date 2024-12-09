package de.metas.ui.web.picking.pickingslot;

<<<<<<< HEAD
import com.google.common.collect.ListMultimap;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
=======
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import de.metas.ad_reference.ADReferenceService;
import de.metas.ad_reference.AdRefListRepositoryMocked;
import de.metas.ad_reference.AdRefTableRepositoryMocked;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateRepository;
<<<<<<< HEAD
import de.metas.handlingunits.picking.PickingCandidateStatus;
import de.metas.handlingunits.picking.PickingCandidatesQuery;
import de.metas.inout.ShipmentScheduleId;
=======
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.PickingCandidateStatus;
import de.metas.handlingunits.picking.PickingCandidatesQuery;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.sourcehu.HuId2SourceHUsService;
import de.metas.handlingunits.trace.HUTraceRepository;
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderId;
import de.metas.picking.api.PickingConfigRepository;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.quantity.Quantity;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorRowId;
import de.metas.ui.web.handlingunits.HUEditorRowType;
import de.metas.ui.web.handlingunits.HUEditorViewRepository;
<<<<<<< HEAD
import de.metas.ui.web.picking.pickingslot.PickingHURowsRepository.PickedHUEditorRow;
import de.metas.ui.web.window.datatypes.WindowId;
=======
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Services;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class PickingHUsRepositoryTests
{
	private static final ShipmentScheduleId M_SHIPMENT_SCHEDULE_ID = ShipmentScheduleId.ofRepoId(123);

	private final boolean RACK_SYSTEM_PICKINGSLOT_YES = true;
	private final boolean RACK_SYSTEM_PICKINGSLOT_NO = false;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
<<<<<<< HEAD
=======

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	private I_C_UOM createUOM()
	{
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		saveRecord(uom);
		return uom;
	}

<<<<<<< HEAD
=======
	private I_M_ShipmentSchedule createShipmentSchedule()
	{
		final I_M_ShipmentSchedule shipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
		shipmentSchedule.setM_ShipmentSchedule_ID(M_SHIPMENT_SCHEDULE_ID.getRepoId());
		shipmentSchedule.setC_Order_ID(123);
		saveRecord(shipmentSchedule);
		return shipmentSchedule;
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	private HuId createHU()
	{
		final I_M_HU hu = newInstance(I_M_HU.class);
		hu.setHUStatus(X_M_HU.HUSTATUS_Active);
		saveRecord(hu);
		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());
		return huId;
	}

<<<<<<< HEAD
	private static final PickingSlotId createPickingSlot(final boolean pickingRackSystem)
=======
	private static PickingSlotId createPickingSlot(final boolean pickingRackSystem)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		final I_M_PickingSlot pickingSlot = newInstance(I_M_PickingSlot.class);
		pickingSlot.setIsPickingRackSystem(pickingRackSystem);
		saveRecord(pickingSlot);
		return PickingSlotId.ofRepoId(pickingSlot.getM_PickingSlot_ID());
	}

	/**
	 * Tests {@link PickingHURowsRepository#retrievePickedHUsIndexedByPickingSlotId(PickingSlotRepoQuery)} with a simple mocked {@link HUEditorViewRepository} that returns one HURow.
<<<<<<< HEAD
	 * 
=======
	 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * @param pickingCandidateStatus this value is given to the picking candidate we test with, and we verify that this value is correctly translated it to the resulting {@link PickingSlotHUEditorRow#isProcessed()}.
	 */
	@Test
	public void test_retrieveHUsIndexedByPickingSlotId_Draft_RackSystem()
	{
		test_retrieveHUsIndexedByPickingSlotId(PickingCandidateStatus.Draft, RACK_SYSTEM_PICKINGSLOT_YES);
	}

	@Test
	public void test_retrieveHUsIndexedByPickingSlotId_Draft_NotRackSystem()
	{
		test_retrieveHUsIndexedByPickingSlotId(PickingCandidateStatus.Draft, RACK_SYSTEM_PICKINGSLOT_NO);
	}

	@Test
	public void test_retrieveHUsIndexedByPickingSlotId_Processed_RackSystem()
	{
		test_retrieveHUsIndexedByPickingSlotId(PickingCandidateStatus.Processed, RACK_SYSTEM_PICKINGSLOT_YES);
	}

	@Test
	public void test_retrieveHUsIndexedByPickingSlotId_Processed_NotRackSystem()
	{
		test_retrieveHUsIndexedByPickingSlotId(PickingCandidateStatus.Processed, RACK_SYSTEM_PICKINGSLOT_NO);
	}

	@Test
	public void test_retrieveHUsIndexedByPickingSlotId_Closed_RackSystem()
	{
		test_retrieveHUsIndexedByPickingSlotId(PickingCandidateStatus.Closed, RACK_SYSTEM_PICKINGSLOT_YES);
	}

	@Test
	public void test_retrieveHUsIndexedByPickingSlotId_Closed_NotRackSystem()
	{
		test_retrieveHUsIndexedByPickingSlotId(PickingCandidateStatus.Closed, RACK_SYSTEM_PICKINGSLOT_NO);
	}

	private void test_retrieveHUsIndexedByPickingSlotId(@NonNull final PickingCandidateStatus processingStatus, final boolean pickingRackSystem)
	{
		final I_C_UOM uom = createUOM();
<<<<<<< HEAD
=======
		final I_M_ShipmentSchedule shipmentSchedule = createShipmentSchedule();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		final PickingSlotId pickingSlotId = createPickingSlot(pickingRackSystem);
		final HuId huId = createHU();

		final PickingCandidateRepository pickingCandidatesRepo = new PickingCandidateRepository();
		pickingCandidatesRepo.save(PickingCandidate.builder()
<<<<<<< HEAD
				.processingStatus(processingStatus)
				.qtyPicked(Quantity.zero(uom))
				.shipmentScheduleId(M_SHIPMENT_SCHEDULE_ID)
				.pickingSlotId(pickingSlotId)
				.pickFrom(PickFrom.ofHuId(huId))
				.packedToHuId(PickingCandidateStatus.Draft.equals(processingStatus) ? null : huId)
				.build());
=======
										   .processingStatus(processingStatus)
										   .qtyPicked(Quantity.zero(uom))
										   .shipmentScheduleId(ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID()))
										   .pickingSlotId(pickingSlotId)
										   .pickFrom(PickFrom.ofHuId(huId))
										   .packedToHuId(PickingCandidateStatus.Draft.equals(processingStatus) ? null : huId)
										   .build());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		final HUEditorRow huEditorRow = HUEditorRow
				.builder(WindowId.of(423))
				.setRowId(HUEditorRowId.ofTopLevelHU(huId))
				.setType(HUEditorRowType.LU)
				.setTopLevel(true)
				.build();

		final boolean expectNoRows = PickingCandidateStatus.Closed.equals(processingStatus) && pickingRackSystem;
		final MockedHUEditorViewRepository huEditorViewRepository = new MockedHUEditorViewRepository();
		if (!expectNoRows)
		{
			huEditorViewRepository.addRow(huEditorRow);
		}

<<<<<<< HEAD
		final PickingHURowsRepository pickingHUsRepository = new PickingHURowsRepository(
				() -> huEditorViewRepository,
				new PickingCandidateRepository());
		final ListMultimap<PickingSlotId, PickedHUEditorRow> result = pickingHUsRepository.retrievePickedHUsIndexedByPickingSlotId(
				PickingCandidatesQuery.builder()
						.shipmentScheduleId(M_SHIPMENT_SCHEDULE_ID)
=======
		final PickingCandidateService pickingCandidateService = new PickingCandidateService(
				new PickingConfigRepository(),
				pickingCandidatesRepo,
				new HuId2SourceHUsService(new HUTraceRepository()),
				new HUReservationService(new HUReservationRepository()),
				Services.get(IBPartnerBL.class),
				new ADReferenceService(new AdRefListRepositoryMocked(), new AdRefTableRepositoryMocked()),
				InventoryService.newInstanceForUnitTesting());

		final PickingHURowsRepository pickingHUsRepository = new PickingHURowsRepository(
				() -> huEditorViewRepository,
				pickingCandidatesRepo,
				pickingCandidateService);
		final ListMultimap<PickingSlotId, PickedHUEditorRow> result = pickingHUsRepository.retrievePickedHUsIndexedByPickingSlotId(
				PickingCandidatesQuery.builder()
						.shipmentScheduleId(ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID()))
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
						.onlyNotClosedOrNotRackSystem(true)
						.build());

		if (expectNoRows)
		{
			// if 'pickingCandidate' is "closed", then nothing shall be returned
			assertThat(result.isEmpty()).isTrue();
		}
		else
		{
			assertThat(result.size()).isEqualTo(1);
			assertThat(result.get(pickingSlotId)).hasSize(1);

			final boolean expectedProcessed = !PickingCandidateStatus.Draft.equals(processingStatus);

			final PickedHUEditorRow resultRow = result.get(pickingSlotId).get(0);
<<<<<<< HEAD
			final PickedHUEditorRow expectedRow = new PickedHUEditorRow(huEditorRow, expectedProcessed);
=======
			final PickedHUEditorRow expectedRow = new PickedHUEditorRow(huEditorRow,
																		expectedProcessed,
																		expectedProcessed
																				? ImmutableMap.of()
																				: ImmutableMap.of(huId, ImmutableSet.of(OrderId.ofRepoId(shipmentSchedule.getC_Order_ID()))));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			assertThat(resultRow).isEqualTo(expectedRow);
		}
	}
}
