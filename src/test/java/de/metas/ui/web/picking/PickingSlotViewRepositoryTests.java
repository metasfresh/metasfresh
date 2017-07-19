package de.metas.ui.web.picking;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;

import de.metas.picking.model.I_M_PickingSlot;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorRowType;
import de.metas.ui.web.picking.PickingHUsRepository.PickingSlotHUEditorRow;
import de.metas.ui.web.picking.PickingSlotRepoQuery.PickingCandidate;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.lookup.NullLookupDataSource;
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

public class PickingSlotViewRepositoryTests
{
	@Mocked
	private PickingHUsRepository pickingHUsRepo;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * Verifies {@link PickingSlotViewRepository#retrieveRowsByShipmentScheduleId(PickingSlotRepoQuery)} when there are no HUs (no picking candidates) assigned to a picking slot, but the query
	 */
	@Test
	public void testRetrieveRowsByShipmentScheduleId_No_HUs_show_all()
	{
		final I_M_PickingSlot pickingSlot = newInstance(I_M_PickingSlot.class);
		save(pickingSlot);

		final PickingSlotRepoQuery query = PickingSlotRepoQuery.of(123);

		// @formatter:off
		// return an empty list
		new Expectations() {{ pickingHUsRepo.retrieveHUsIndexedByPickingSlotId(query); result = ImmutableListMultimap.of(); }};
		// @formatter:on

		final NullLookupDataSource nullDs = NullLookupDataSource.instance;

		final PickingSlotViewRepository pickingSlotViewRepository = new PickingSlotViewRepository(pickingHUsRepo, () -> nullDs, () -> nullDs, () -> nullDs);
		final List<PickingSlotRow> rowsByShipmentScheduleId = pickingSlotViewRepository.retrieveRowsByShipmentScheduleId(query);

		assertThat(rowsByShipmentScheduleId.size(), is(1)); // even if there are no HUs, there shall still be a row for our picking slot.
	}

	@Test
	public void testRetrieveRowsByShipmentScheduleId_No_HUs_show_none()
	{
		final I_M_PickingSlot pickingSlot = newInstance(I_M_PickingSlot.class);
		save(pickingSlot);

		final PickingSlotRepoQuery query = PickingSlotRepoQuery.builder().shipmentScheduleId(123).pickingCandidates(PickingCandidate.ONLY_PROCESSED).build();

		// @formatter:off return an empty list
		new Expectations() {{ pickingHUsRepo.retrieveHUsIndexedByPickingSlotId(query); result = ImmutableListMultimap.of(); }};
		// @formatter:on

		final NullLookupDataSource nullDs = NullLookupDataSource.instance;

		final PickingSlotViewRepository pickingSlotViewRepository = new PickingSlotViewRepository(pickingHUsRepo, () -> nullDs, () -> nullDs, () -> nullDs);
		final List<PickingSlotRow> rowsByShipmentScheduleId = pickingSlotViewRepository.retrieveRowsByShipmentScheduleId(query);

		assertThat(rowsByShipmentScheduleId.isEmpty(), is(true));
	}

	@Test
	public void testRetrieveRowsByShipmentScheduleId_One_TU_with_CU()
	{
		final I_M_PickingSlot pickingSlot = newInstance(I_M_PickingSlot.class);
		save(pickingSlot);

		final int shipmentScheduleId = 123;
		final boolean pickingSlotRowProcessed = false;

		final PickingSlotRepoQuery query = PickingSlotRepoQuery.of(shipmentScheduleId);

		final ListMultimap<Integer, PickingSlotHUEditorRow> husIndexedByPickingSlotId = ImmutableListMultimap.of(
				pickingSlot.getM_PickingSlot_ID(),
				new PickingSlotHUEditorRow(
						HUEditorRow
								.builder(WindowId.of(423))
								.setHUId(100)
								.setRowId(DocumentId.of(523))
								.setType(HUEditorRowType.TU)
								.setTopLevel(true)
								.addIncludedRow(HUEditorRow
										.builder(WindowId.of(423))
										.setHUId(101)
										.setRowId(DocumentId.of(523))
										.setType(HUEditorRowType.VHU)
										.setTopLevel(false)
										.build())
								.build(),
						pickingSlotRowProcessed));

		// @formatter:off return an empty list
		new Expectations() {{ pickingHUsRepo.retrieveHUsIndexedByPickingSlotId(query); result = husIndexedByPickingSlotId; }};
		// @formatter:on

		final NullLookupDataSource nullDs = NullLookupDataSource.instance;

		final PickingSlotViewRepository pickingSlotViewRepository = new PickingSlotViewRepository(pickingHUsRepo, () -> nullDs, () -> nullDs, () -> nullDs);
		final List<PickingSlotRow> rowsByShipmentScheduleId = pickingSlotViewRepository.retrieveRowsByShipmentScheduleId(query);

		assertThat(rowsByShipmentScheduleId.size(), is(1));
		final PickingSlotRow pickingSlotRow = rowsByShipmentScheduleId.get(0);
		assertThat(pickingSlotRow.isPickingSlotRow(), is(true));
		assertThat(pickingSlotRow.isHURow(), is(false));
		assertThat(pickingSlotRow.isProcessed(), is(pickingSlotRowProcessed));

		assertThat(pickingSlotRow.getIncludedRows().size(), is(1));
		final PickingSlotRow tuRow = pickingSlotRow.getIncludedRows().get(0);
		assertThat(tuRow.isPickingSlotRow(), is(false));
		assertThat(tuRow.isHURow(), is(true));
		assertThat(tuRow.getHuId(), is(100));

		assertThat(tuRow.getIncludedRows().size(), is(1));
		final PickingSlotRow whuRow = tuRow.getIncludedRows().get(0);
		assertThat(whuRow.isPickingSlotRow(), is(false));
		assertThat(whuRow.isHURow(), is(true));
		assertThat(whuRow.getHuId(), is(101));
	}
}
