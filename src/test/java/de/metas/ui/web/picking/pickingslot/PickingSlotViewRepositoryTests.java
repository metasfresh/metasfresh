package de.metas.ui.web.picking.pickingslot;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;

import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorRowId;
import de.metas.ui.web.handlingunits.HUEditorRowType;
import de.metas.ui.web.picking.pickingslot.PickingHURowsRepository.PickedHUEditorRow;
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
	private PickingHURowsRepository pickingHUsRepo;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * Verifies {@link PickingSlotViewRepository#retrieveRows(PickingSlotRepoQuery)}<br>
	 * when there are no HUs (no picking candidates) assigned to a picking slot, but still the picking slot itself shall be shown.
	 */
	@Test
	public void testRetrievePickingSlotRows_No_HUs_show_PickingSlotsOnly()
	{
		final I_M_ShipmentSchedule shipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
		save(shipmentSchedule);

		final I_M_PickingSlot pickingSlot = newInstance(I_M_PickingSlot.class);
		pickingSlot.setIsPickingRackSystem(true);
		save(pickingSlot);

		final PickingSlotRepoQuery query = PickingSlotRepoQuery.of(shipmentSchedule.getM_ShipmentSchedule_ID());

		// @formatter:off return an empty list
		new Expectations() {{ pickingHUsRepo.retrievePickedHUsIndexedByPickingSlotId(query); result = ImmutableListMultimap.of(); }};
		// @formatter:on

		final NullLookupDataSource nullDS = NullLookupDataSource.instance;

		final PickingSlotViewRepository pickingSlotViewRepository = new PickingSlotViewRepository(pickingHUsRepo, () -> nullDS, () -> nullDS, () -> nullDS);
		final List<PickingSlotRow> pickingSlotRows = pickingSlotViewRepository.retrievePickingSlotRows(query);

		assertThat(pickingSlotRows.size(), is(1)); // even if there are no HUs, there shall still be a row for our picking slot.

		final PickingSlotRow pickingSlotRow = pickingSlotRows.get(0);
		assertThat(pickingSlotRow.getType()).isEqualTo(PickingSlotRowType.forPickingSlotRow());
	}

	@Test
	public void testRetrievePickingSlotRows_One_TU_with_CU()
	{
		final I_M_ShipmentSchedule shipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
		save(shipmentSchedule);

		final I_M_PickingSlot pickingSlot = newInstance(I_M_PickingSlot.class);
		save(pickingSlot);

		final boolean pickingSlotRowProcessed = false;

		final PickingSlotRepoQuery query = PickingSlotRepoQuery.of(shipmentSchedule.getM_ShipmentSchedule_ID());

		// set up a picked TU with a CU to be returned by the pickingHUsRepo.
		{
			final ListMultimap<Integer, PickedHUEditorRow> husIndexedByPickingSlotId = ImmutableListMultimap.of(
					pickingSlot.getM_PickingSlot_ID(),
					new PickedHUEditorRow(
							HUEditorRow
									.builder(WindowId.of(423))
									.setRowId(HUEditorRowId.ofTopLevelHU(100))
									.setType(HUEditorRowType.TU)
									.setTopLevel(true)
									.addIncludedRow(HUEditorRow
											.builder(WindowId.of(423))
											.setRowId(HUEditorRowId.ofHU(101, 100))
											.setType(HUEditorRowType.VHU)
											.setTopLevel(false)
											.build())
									.build(),
							pickingSlotRowProcessed));

			// @formatter:off return an empty list
			new Expectations() {{ pickingHUsRepo.retrievePickedHUsIndexedByPickingSlotId(query); result = husIndexedByPickingSlotId; }};
			// @formatter:on
		}

		final PickingSlotViewRepository pickingSlotViewRepository = createPickingSllotViewRepository();
		final List<PickingSlotRow> rowsByShipmentScheduleId = pickingSlotViewRepository.retrievePickingSlotRows(query);

		assertThat(rowsByShipmentScheduleId.size(), is(1));
		final PickingSlotRow pickingSlotRow = rowsByShipmentScheduleId.get(0);
		assertThat(pickingSlotRow.isPickingSlotRow(), is(true));
		assertThat(pickingSlotRow.isPickedHURow(), is(false));
		assertThat(pickingSlotRow.isProcessed(), is(pickingSlotRowProcessed));

		assertThat(pickingSlotRow.getIncludedRows().size(), is(1));
		final PickingSlotRow tuRow = pickingSlotRow.getIncludedRows().get(0);
		assertThat(tuRow.isPickingSlotRow(), is(false));
		assertThat(tuRow.isPickedHURow(), is(true));
		assertThat(tuRow.getHuId(), is(100));

		assertThat(tuRow.getIncludedRows().size(), is(1));
		final PickingSlotRow whuRow = tuRow.getIncludedRows().get(0);
		assertThat(whuRow.isPickingSlotRow(), is(false));
		assertThat(whuRow.isPickedHURow(), is(true));
		assertThat(whuRow.getHuId(), is(101));
	}

	private PickingSlotViewRepository createPickingSllotViewRepository()
	{
		final NullLookupDataSource nullDs = NullLookupDataSource.instance;

		final PickingSlotViewRepository pickingSlotViewRepository = new PickingSlotViewRepository(pickingHUsRepo, () -> nullDs, () -> nullDs, () -> nullDs);
		return pickingSlotViewRepository;
	}

	@Test
	public void testCreateSourceHURow()
	{
		final HUEditorRow huEditorRow = HUEditorRow
				.builder(WindowId.of(423))
				.setRowId(HUEditorRowId.ofTopLevelHU(100))
				.setType(HUEditorRowType.TU)
				.setTopLevel(true)
				.build();

		final PickingSlotRow sourceHURow = PickingSlotViewRepository.createSourceHURow(huEditorRow);

		assertThat(sourceHURow).isNotNull();
		assertThat(sourceHURow.isPickingSlotRow()).isFalse();
		assertThat(sourceHURow.isPickedHURow()).isFalse();
		assertThat(sourceHURow.isPickingSourceHURow()).isTrue();
		assertThat(sourceHURow.getId()).isEqualTo(DocumentId.of("0-100"));
		assertThat(sourceHURow.isTopLevelHU()).isTrue();
	}
}
