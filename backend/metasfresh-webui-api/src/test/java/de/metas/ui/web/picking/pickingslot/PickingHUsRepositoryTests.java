package de.metas.ui.web.picking.pickingslot;

import com.google.common.collect.ListMultimap;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.PickingCandidateStatus;
import de.metas.handlingunits.picking.PickingCandidatesQuery;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.quantity.Quantity;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorRowId;
import de.metas.ui.web.handlingunits.HUEditorRowType;
import de.metas.ui.web.handlingunits.HUEditorViewRepository;
import de.metas.ui.web.picking.pickingslot.PickingHURowsRepository.PickedHUEditorRow;
import de.metas.ui.web.window.datatypes.WindowId;
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

public class PickingHUsRepositoryTests
{
	private static final ShipmentScheduleId M_SHIPMENT_SCHEDULE_ID = ShipmentScheduleId.ofRepoId(123);

	private final boolean RACK_SYSTEM_PICKINGSLOT_YES = true;
	private final boolean RACK_SYSTEM_PICKINGSLOT_NO = false;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	private I_C_UOM createUOM()
	{
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		saveRecord(uom);
		return uom;
	}

	private HuId createHU()
	{
		final I_M_HU hu = newInstance(I_M_HU.class);
		hu.setHUStatus(X_M_HU.HUSTATUS_Active);
		saveRecord(hu);
		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());
		return huId;
	}

	private static final PickingSlotId createPickingSlot(final boolean pickingRackSystem)
	{
		final I_M_PickingSlot pickingSlot = newInstance(I_M_PickingSlot.class);
		pickingSlot.setIsPickingRackSystem(pickingRackSystem);
		saveRecord(pickingSlot);
		return PickingSlotId.ofRepoId(pickingSlot.getM_PickingSlot_ID());
	}

	/**
	 * Tests {@link PickingHURowsRepository#retrievePickedHUsIndexedByPickingSlotId(PickingSlotRepoQuery)} with a simple mocked {@link HUEditorViewRepository} that returns one HURow.
	 * 
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

		final PickingSlotId pickingSlotId = createPickingSlot(pickingRackSystem);
		final HuId huId = createHU();

		final PickingCandidateRepository pickingCandidatesRepo = new PickingCandidateRepository();
		pickingCandidatesRepo.save(PickingCandidate.builder()
				.processingStatus(processingStatus)
				.qtyPicked(Quantity.zero(uom))
				.shipmentScheduleId(M_SHIPMENT_SCHEDULE_ID)
				.pickingSlotId(pickingSlotId)
				.pickFrom(PickFrom.ofHuId(huId))
				.packedToHuId(PickingCandidateStatus.Draft.equals(processingStatus) ? null : huId)
				.build());

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

		final PickingHURowsRepository pickingHUsRepository = new PickingHURowsRepository(
				() -> huEditorViewRepository,
				new PickingCandidateRepository());
		final ListMultimap<PickingSlotId, PickedHUEditorRow> result = pickingHUsRepository.retrievePickedHUsIndexedByPickingSlotId(
				PickingCandidatesQuery.builder()
						.shipmentScheduleId(M_SHIPMENT_SCHEDULE_ID)
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
			final PickedHUEditorRow expectedRow = new PickedHUEditorRow(huEditorRow, expectedProcessed);
			assertThat(resultRow).isEqualTo(expectedRow);
		}
	}
}
