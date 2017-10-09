package de.metas.ui.web.picking.pickingslot;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;

import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.model.X_M_Picking_Candidate;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorRowId;
import de.metas.ui.web.handlingunits.HUEditorRowType;
import de.metas.ui.web.handlingunits.HUEditorViewRepository;
import de.metas.ui.web.picking.pickingslot.PickingHURowsRepository.PickedHUEditorRow;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;
import mockit.Expectations;
import mockit.Mocked;

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
	private static final int M_HU_ID = 223;

	private static final int M_PICKINGSLOT_ID = 323;

	private static final int M_SHIPMENT_SCHEDULE_ID = 123;

	@Mocked
	private HUEditorViewRepository huEditorViewRepository;

	/**
	 * Needed to test {@link PickingHURowsRepository#retrieveSourceHUs(List)}.
	 */
	@Mocked
	private IHUPickingSlotBL huPickingSlotBL;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * Tests {@link PickingHURowsRepository#retrievePickedHUsIndexedByPickingSlotId(PickingSlotRepoQuery)} with a simple mocked {@link HUEditorViewRepository} that returns one HURow.
	 * 
	 * @param pickingCandidateStatus this value is given to the {@link I_M_Picking_Candidate} we test with, and we verify that this value is correctly translated it to the resulting {@link PickingSlotHUEditorRow#isProcessed()}.
	 */
	@Test
	public void test_retrieveHUsIndexedByPickingSlotId_IP()
	{
		test_retrieveHUsIndexedByPickingSlotId(X_M_Picking_Candidate.STATUS_IP);
	}

	@Test
	public void test_retrieveHUsIndexedByPickingSlotId_PR()
	{
		test_retrieveHUsIndexedByPickingSlotId(X_M_Picking_Candidate.STATUS_PR);
	}
	
	@Test
	public void test_retrieveHUsIndexedByPickingSlotId_CL()
	{
		test_retrieveHUsIndexedByPickingSlotId(X_M_Picking_Candidate.STATUS_CL);
	}
	
	private void test_retrieveHUsIndexedByPickingSlotId(@NonNull final String pickingCandidateStatus)
	{
		final I_M_Picking_Candidate pickingCandidate = newInstance(I_M_Picking_Candidate.class);
		pickingCandidate.setM_ShipmentSchedule_ID(M_SHIPMENT_SCHEDULE_ID);
		pickingCandidate.setM_HU_ID(M_HU_ID);
		pickingCandidate.setM_PickingSlot_ID(M_PICKINGSLOT_ID);
		pickingCandidate.setStatus(pickingCandidateStatus);
		save(pickingCandidate);

		final HUEditorRow huEditorRow = HUEditorRow
				.builder(WindowId.of(423))
				.setRowId(HUEditorRowId.ofTopLevelHU(M_HU_ID))
				.setType(HUEditorRowType.LU)
				.setTopLevel(true)
				.build();

		if (!X_M_Picking_Candidate.STATUS_CL.equals(pickingCandidateStatus))
		{
			// @formatter:off
			new Expectations() {{ huEditorViewRepository.retrieveForHUId(M_HU_ID); result = huEditorRow; }};
			// @formatter:on
		}

		final PickingHURowsRepository pickingHUsRepository = new PickingHURowsRepository(huEditorViewRepository);
		final ListMultimap<Integer, PickedHUEditorRow> result = pickingHUsRepository.retrievePickedHUsIndexedByPickingSlotId(PickingSlotRepoQuery.of(M_SHIPMENT_SCHEDULE_ID));

		if (X_M_Picking_Candidate.STATUS_CL.equals(pickingCandidateStatus))
		{
			// if 'pickingCandidate' is "closed", then nothing shall be returned
			assertThat(result.isEmpty()).isTrue();
		}
		else
		{
			assertThat(result.size()).isEqualTo(1);
			assertThat(result.get(M_PICKINGSLOT_ID)).hasSize(1);

			final boolean expectedProcessed = !X_M_Picking_Candidate.STATUS_IP.equals(pickingCandidateStatus);

			final PickedHUEditorRow resultRow = result.get(M_PICKINGSLOT_ID).get(0);
			final PickedHUEditorRow expectedRow = new PickedHUEditorRow(huEditorRow, expectedProcessed);
			assertThat(resultRow).isEqualTo(expectedRow);
		}
	}

	@Test
	public void test_retrieveSourceHUs_empty_shipmentScheduleIds()
	{
		final PickingHURowsRepository pickingHUsRepository = new PickingHURowsRepository(huEditorViewRepository);
		final List<HUEditorRow> sourceHUs = pickingHUsRepository.retrieveSourceHUs(ImmutableList.of());
		assertThat(sourceHUs).isEmpty();
	}
}
