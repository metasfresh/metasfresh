package de.metas.ui.web.picking.pickingslot;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.picking.PickingCandidatesQuery;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorRowId;
import de.metas.ui.web.handlingunits.HUEditorRowType;
import de.metas.ui.web.picking.pickingslot.PickingHURowsRepository.PickedHUEditorRow;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.NullLookupDataSource;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.function.Consumer;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;

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
	private static final WarehouseId WAREHOUSE_ID = WarehouseId.ofRepoId(2);

	private PickingHURowsRepository pickingHUsRepo;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		pickingHUsRepo = Mockito.mock(PickingHURowsRepository.class);
	}

	/**
	 * Verifies {@link PickingSlotViewRepository#retrieveRows(PickingSlotRepoQuery)}<br>
	 * when there are no HUs (no picking candidates) assigned to a picking slot, but still the picking slot itself shall be shown.
	 */
	@Test
	public void testRetrievePickingSlotRows_No_HUs_show_PickingSlotsOnly()
	{
		final ShipmentScheduleId shipmentScheduleId = createShipmentSchedule();

		final PickingSlotId pickingSlotId = createPickingSlot(pickingSlot -> {
			pickingSlot.setIsPickingRackSystem(true);
		});

		Mockito.when(pickingHUsRepo.retrievePickedHUsIndexedByPickingSlotId(
				PickingCandidatesQuery.builder()
						.shipmentScheduleId(shipmentScheduleId)
						.onlyNotClosedOrNotRackSystem(true)
						.onlyPickingSlotIds(ImmutableSet.of(pickingSlotId))
						.build()))
				.thenReturn(ImmutableListMultimap.of());

		final PickingSlotViewRepository pickingSlotViewRepository = createPickingSlotViewRepository();
		final PickingSlotRepoQuery query = PickingSlotRepoQuery.of(shipmentScheduleId);
		final List<PickingSlotRow> pickingSlotRows = pickingSlotViewRepository.retrievePickingSlotRows(query);

		assertThat(pickingSlotRows).hasSize(1); // even if there are no HUs, there shall still be a row for our picking slot.

		final PickingSlotRow pickingSlotRow = pickingSlotRows.get(0);
		assertThat(pickingSlotRow.getType()).isEqualTo(PickingSlotRowType.forPickingSlotRow());
	}

	@Test
	public void testRetrievePickingSlotRows_One_TU_with_CU()
	{
		final ShipmentScheduleId shipmentScheduleId = createShipmentSchedule();

		final PickingSlotId pickingSlotId = createPickingSlot();

		final boolean pickingSlotRowProcessed = false;

		// set up a picked TU with a CU to be returned by the pickingHUsRepo.
		{
			final ListMultimap<PickingSlotId, PickedHUEditorRow> husIndexedByPickingSlotId = ImmutableListMultimap.of(
					pickingSlotId,
					new PickedHUEditorRow(
							HUEditorRow
									.builder(WindowId.of(423))
									.setRowId(HUEditorRowId.ofTopLevelHU(HuId.ofRepoId(100)))
									.setType(HUEditorRowType.TU)
									.setTopLevel(true)
									.addIncludedRow(HUEditorRow
											.builder(WindowId.of(423))
											.setRowId(HUEditorRowId.ofHU(HuId.ofRepoId(101), HuId.ofRepoId(100)))
											.setType(HUEditorRowType.VHU)
											.setTopLevel(false)
											.build())
									.build(),
							pickingSlotRowProcessed));

			Mockito.when(pickingHUsRepo.retrievePickedHUsIndexedByPickingSlotId(
					PickingCandidatesQuery.builder()
							.shipmentScheduleId(shipmentScheduleId)
							.onlyNotClosedOrNotRackSystem(true)
							.onlyPickingSlotIds(ImmutableSet.of(pickingSlotId))
							.build()))
					.thenReturn(husIndexedByPickingSlotId);
		}

		final PickingSlotViewRepository pickingSlotViewRepository = createPickingSlotViewRepository();
		final PickingSlotRepoQuery query = PickingSlotRepoQuery.of(shipmentScheduleId);
		final List<PickingSlotRow> rowsByShipmentScheduleId = pickingSlotViewRepository.retrievePickingSlotRows(query);

		assertThat(rowsByShipmentScheduleId).hasSize(1);
		final PickingSlotRow pickingSlotRow = rowsByShipmentScheduleId.get(0);
		assertThat(pickingSlotRow.isPickingSlotRow()).isTrue();
		assertThat(pickingSlotRow.isPickedHURow()).isFalse();
		assertThat(pickingSlotRow.isProcessed()).isEqualTo(pickingSlotRowProcessed);

		assertThat(pickingSlotRow.getIncludedRows()).hasSize(1);
		final PickingSlotRow tuRow = pickingSlotRow.getIncludedRows().get(0);
		assertThat(tuRow.isPickingSlotRow()).isFalse();
		assertThat(tuRow.isPickedHURow()).isTrue();
		assertThat(tuRow.getHuId().getRepoId()).isEqualTo(100);

		assertThat(tuRow.getIncludedRows()).hasSize(1);
		final PickingSlotRow whuRow = tuRow.getIncludedRows().get(0);
		assertThat(whuRow.isPickingSlotRow()).isFalse();
		assertThat(whuRow.isPickedHURow()).isTrue();
		assertThat(whuRow.getHuId().getRepoId()).isEqualTo(101);
	}

	private ShipmentScheduleId createShipmentSchedule()
	{
		final I_M_ShipmentSchedule shipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
		shipmentSchedule.setC_BPartner_ID(1);
		shipmentSchedule.setC_BPartner_Location_ID(2);
		shipmentSchedule.setM_Warehouse_ID(WAREHOUSE_ID.getRepoId());
		save(shipmentSchedule);
		return ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());
	}

	private PickingSlotId createPickingSlot()
	{
		return createPickingSlot(pickingSlot -> {
		});
	}

	private PickingSlotId createPickingSlot(final Consumer<I_M_PickingSlot> customizer)
	{
		final I_M_PickingSlot pickingSlot = newInstance(I_M_PickingSlot.class);
		pickingSlot.setM_Warehouse_ID(WAREHOUSE_ID.getRepoId());
		customizer.accept(pickingSlot);
		save(pickingSlot);
		return PickingSlotId.ofRepoId(pickingSlot.getM_PickingSlot_ID());
	}

	private PickingSlotViewRepository createPickingSlotViewRepository()
	{
		final Supplier<LookupDataSource> nullDataSourceSupplier = () -> NullLookupDataSource.instance;

		final PickingSlotViewRepository pickingSlotViewRepository = new PickingSlotViewRepository(pickingHUsRepo, nullDataSourceSupplier, nullDataSourceSupplier, nullDataSourceSupplier);
		return pickingSlotViewRepository;
	}

	@Test
	public void testCreateSourceHURow()
	{
		final HUEditorRow huEditorRow = HUEditorRow
				.builder(WindowId.of(423))
				.setRowId(HUEditorRowId.ofTopLevelHU(HuId.ofRepoId(100)))
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
