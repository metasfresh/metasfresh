package de.metas.ui.web.picking.pickingslot;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.util.List;
import java.util.stream.Collectors;

import org.adempiere.util.Services;
import org.compiere.util.DisplayType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;

import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.api.IPickingSlotDAO;
import de.metas.picking.api.IPickingSlotDAO.PickingSlotQuery;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.picking.pickingslot.PickingHURowsRepository.PickedHUEditorRow;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider.LookupScope;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import lombok.NonNull;

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

/**
 * Class to retrieve {@link PickingSlotRow}s that are displayed in the {@link PickingSlotView}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Component
public class PickingSlotViewRepository
{
	private final PickingHURowsRepository pickingHUsRepo;

	private final Supplier<LookupDataSource> warehouseLookup;
	private final Supplier<LookupDataSource> bpartnerLookup;
	private final Supplier<LookupDataSource> bpartnerLocationLookup;

	/**
	 * @param pickingHUsRepo the "backend" repo to be used by this instance.
	 */
	@Autowired
	public PickingSlotViewRepository(@NonNull final PickingHURowsRepository pickingHUsRepo)
	{
		// creating those LookupDataSources requires DB access. so to allow this component to be initialized early during startup
		// and also to allow it to be unit-tested (when the lookups are not part of the test), I use those suppliers
		this(
				pickingHUsRepo,
				createWarehouseLookup(),
				createBPartnerLookup(),
				createBPartnerLocationLookup());
	}

	private static Supplier<LookupDataSource> createWarehouseLookup()
	{
		return Suppliers.memoize(() -> LookupDataSourceFactory.instance
				.getLookupDataSource(SqlLookupDescriptor.builder()
						.setCtxTableName(null)
						.setCtxColumnName(I_M_PickingSlot.COLUMNNAME_M_Warehouse_ID)
						.setDisplayType(DisplayType.Search)
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.buildProvider()
						.provideForScope(LookupScope.DocumentField)));
	}

	private static Supplier<LookupDataSource> createBPartnerLookup()
	{
		return Suppliers.memoize(() -> LookupDataSourceFactory.instance
				.getLookupDataSource(SqlLookupDescriptor.builder()
						.setCtxTableName(null)
						.setCtxColumnName(I_M_PickingSlot.COLUMNNAME_C_BPartner_ID)
						.setDisplayType(DisplayType.Search)
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.buildProvider()
						.provideForScope(LookupScope.DocumentField)));
	}

	private static Supplier<LookupDataSource> createBPartnerLocationLookup()
	{
		return Suppliers.memoize(() -> LookupDataSourceFactory.instance
				.getLookupDataSource(SqlLookupDescriptor.builder()
						.setCtxTableName(null)
						.setCtxColumnName(I_M_PickingSlot.COLUMNNAME_C_BPartner_Location_ID)
						.setDisplayType(DisplayType.Search)
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.buildProvider()
						.provideForScope(LookupScope.DocumentField)));
	}

	@VisibleForTesting
	/* package */ PickingSlotViewRepository(
			@NonNull final PickingHURowsRepository pickingHUsRepo,
			@NonNull final Supplier<LookupDataSource> warehouseLookup,
			@NonNull final Supplier<LookupDataSource> bpartnerLookup,
			@NonNull final Supplier<LookupDataSource> bpartnerLocationLookup)
	{
		this.pickingHUsRepo = pickingHUsRepo;
		this.warehouseLookup = warehouseLookup;
		this.bpartnerLookup = bpartnerLookup;
		this.bpartnerLocationLookup = bpartnerLocationLookup;
	}

	/**
	 * Returns "top level" source HU and picking slot rows, according to the given {@code query}.<br>
	 * If there are HUs assigned, they are included and can be accessed via {@link PickingSlotRow#getIncludedRows()}.
	 * 
	 * @param query
	 * @return
	 */
	// when https://github.com/metasfresh/metasfresh-webui-api/issues/509 is done,
	// we shall re-implement this method to use the view's streamByIds(DocumentIdsSelection.ALL) to avoid the DB access
	// ..at least for checkPreconditionsApplicable()
	public List<PickingSlotRow> retrieveRows(@NonNull final PickingSlotRepoQuery query)
	{
		Check.errorIf(query.getShipmentScheduleIds().isEmpty(), "Given query has no shipmentScheduleIds; query={}", query);

		// get M_HU_Source records that reference active HUs with their locator in this WH and not on the picking location
		final List<HUEditorRow> sourceHUEditorRows = pickingHUsRepo.retrieveSourceHUs(query);
		final List<PickingSlotRow> sourceHUPickingSlotRows = sourceHUEditorRows.stream()
				.map(sourceHuEditorRow -> createSourceHURow(sourceHuEditorRow))
				.collect(Collectors.toList());

		// get the picking slot rows, including the rows the represent picked HUs
		final ImmutableList<PickingSlotRow> pickingSlotRows = retrievePickingSlotRows(query);

		return ImmutableList.copyOf(Iterables.concat(
				pickingSlotRows,
				sourceHUPickingSlotRows));
	}

	@VisibleForTesting
	ImmutableList<PickingSlotRow> retrievePickingSlotRows(@NonNull final PickingSlotRepoQuery query)
	{
		final List<I_M_PickingSlot> pickingSlots = retrievePickingSlotsForShipmentSchedule(query);

		// retrieve picked HU rows (if any) to be displayed below there respective picking slots
		final ListMultimap<Integer, PickedHUEditorRow> huEditorRowsByPickingSlotId = pickingHUsRepo.retrievePickedHUsIndexedByPickingSlotId(query);

		final ImmutableList<PickingSlotRow> result = pickingSlots.stream() // get stream of I_M_PickingSlot
				.map(pickingSlot -> createPickingSlotRow(pickingSlot, huEditorRowsByPickingSlotId)) // create the actual PickingSlotRows
				.collect(ImmutableList.toImmutableList());
		return result;
	}

	/**
	 * Retrieves the M_PickingSlots that are available for the given shipmentSchedules' partner and location.
	 * Assumes that all shipment schedules have the same partner and location (needs to be made sure) before starting all this stuff
	 * 
	 * @param shipmentSchedule
	 * @return
	 */
	private static List<I_M_PickingSlot> retrievePickingSlotsForShipmentSchedule(@NonNull final PickingSlotRepoQuery repoQuery)
	{
		final I_M_ShipmentSchedule shipmentSchedule = loadOutOfTrx(repoQuery.getCurrentShipmentScheduleId(), I_M_ShipmentSchedule.class);

		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

		final PickingSlotQuery pickingSlotQuery = PickingSlotQuery.builder()
				.availableForBPartnerId(shipmentScheduleEffectiveBL.getC_BPartner_ID(shipmentSchedule))
				.availableForBPartnerLocationId(shipmentScheduleEffectiveBL.getC_BP_Location_ID(shipmentSchedule))
				.warehouseId(shipmentScheduleEffectiveBL.getWarehouseId(shipmentSchedule))
				.barcode(repoQuery.getPickingSlotBarcode())
				.build();

		final IPickingSlotDAO pickingSlotDAO = Services.get(IPickingSlotDAO.class);
		final List<I_M_PickingSlot> pickingSlots = pickingSlotDAO.retrievePickingSlots(pickingSlotQuery);
		return pickingSlots;
	}

	@VisibleForTesting
	static PickingSlotRow createSourceHURow(@NonNull final HUEditorRow sourceHuEditorRow)
	{
		final PickingSlotRow pickingSourceHuRow = PickingSlotRow.fromSourceHUBuilder()
				.huId(sourceHuEditorRow.getM_HU_ID())
				.huEditorRowType(sourceHuEditorRow.getType())
				.huCode(sourceHuEditorRow.getValue())
				.product(sourceHuEditorRow.getProduct())
				.packingInfo(sourceHuEditorRow.getPackingInfo())
				.qtyCU(sourceHuEditorRow.getQtyCU())
				.topLevelHU(sourceHuEditorRow.isTopLevel())
				.build();

		return pickingSourceHuRow;
	}

	private PickingSlotRow createPickingSlotRow(
			@NonNull final I_M_PickingSlot pickingSlot,
			@NonNull final ListMultimap<Integer, PickedHUEditorRow> huEditorRowsByPickingSlotId)
	{
		final List<PickingSlotRow> pickedHuRows = retrieveHuRowsToIncludeInPickingSlotRow(pickingSlot, huEditorRowsByPickingSlotId);
		return createPickingSlotRowWithIncludedRows(pickingSlot, pickedHuRows);
	}

	private static List<PickingSlotRow> retrieveHuRowsToIncludeInPickingSlotRow(
			@NonNull final I_M_PickingSlot pickingSlot,
			@NonNull final ListMultimap<Integer, PickedHUEditorRow> huEditorRowsByPickingSlotId)
	{
		final int pickingSlotId = pickingSlot.getM_PickingSlot_ID();

		// create picking slot rows for included/picked HUs
		final List<PickingSlotRow> pickedHuRows = huEditorRowsByPickingSlotId.get(pickingSlotId)
				.stream()
				.map(pickingSlotHuEditorRow -> createPickedHURow(pickingSlotHuEditorRow, pickingSlotId))
				.collect(ImmutableList.toImmutableList());
		return pickedHuRows;
	}

	/**
	 * Creates a HU related picking slot row for the given HU editor row and the given {@code pickingSlotId}.
	 * 
	 * @param from the hu editor row to create a picking slot row for. If it has included HU editor rows, then the method creates an included picking slot line accordingly.
	 * @param pickingSlotId
	 * @return
	 */
	private static final PickingSlotRow createPickedHURow(@NonNull final PickedHUEditorRow from, final int pickingSlotId)
	{
		final HUEditorRow huEditorRow = from.getHuEditorRow();

		final List<PickingSlotRow> includedHURows = huEditorRow.getIncludedRows()
				.stream()
				.map(includedHUEditorRow -> createPickedHURow(
						new PickedHUEditorRow(includedHUEditorRow, from.isProcessed()), // create PickingSlotRows for the included HU rows which shall inherit the parent's processed flag
						pickingSlotId))
				.collect(ImmutableList.toImmutableList());

		return PickingSlotRow.fromPickedHUBuilder()
				.pickingSlotId(pickingSlotId)
				.huId(huEditorRow.getHURowId().getHuId())
				.huStorageProductId(huEditorRow.getHURowId().getStorageProductId())

				.huEditorRowType(huEditorRow.getType())

				// do *not* take the HUEditorRow's processed flag, because we don't care about that; we do care about PickingSlotHUEditorRow.isProcessed() because that's the value coming from the M_Picking_Candiate
				.processed(from.isProcessed())

				.huCode(huEditorRow.getValue())
				.product(huEditorRow.getProduct())
				.packingInfo(huEditorRow.getPackingInfo())
				.qtyCU(huEditorRow.getQtyCU())
				.topLevelHU(huEditorRow.isTopLevel())
				//
				.includedHURows(includedHURows)
				//
				.build();
	}

	private PickingSlotRow createPickingSlotRowWithIncludedRows(
			@NonNull final I_M_PickingSlot pickingSlot,
			@NonNull final List<PickingSlotRow> pickedHuRows)
	{
		return PickingSlotRow.fromPickingSlotBuilder()
				.pickingSlotId(pickingSlot.getM_PickingSlot_ID())
				//
				.pickingSlotName(pickingSlot.getPickingSlot())
				.pickingSlotWarehouse(warehouseLookup.get().findById(pickingSlot.getM_Warehouse_ID()))
				.pickingSlotLocatorId(pickingSlot.getM_Locator_ID())
				.pickingSlotBPartner(bpartnerLookup.get().findById(pickingSlot.getC_BPartner_ID()))
				.pickingSlotBPLocation(bpartnerLocationLookup.get().findById(pickingSlot.getC_BPartner_Location_ID()))
				.includedHURows(pickedHuRows)
				//
				.build();
	}

	public List<PickingSlotRow> retrievePickingSlotsRows(@NonNull final PickingSlotQuery query)
	{
		final List<I_M_PickingSlot> pickingSlots = Services.get(IPickingSlotDAO.class).retrievePickingSlots(query);

		final ListMultimap<Integer, PickedHUEditorRow> huEditorRowsByPickingSlotId = pickingHUsRepo.retrieveAllPickedHUsIndexedByPickingSlotId(pickingSlots);

		return pickingSlots.stream() // get stream of I_M_PickingSlot
				.map(pickingSlot -> createPickingSlotRow(pickingSlot, huEditorRowsByPickingSlotId)) // create the actual PickingSlotRows
				.collect(ImmutableList.toImmutableList());
	}
}
