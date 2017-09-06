package de.metas.ui.web.picking;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.adempiere.util.Services;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;

import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.picking.api.IPickingSlotDAO;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.picking.PickingHUsRepository.PickedHUEditorRow;
import de.metas.ui.web.picking.PickingSlotRepoQuery.PickingCandidate;
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
	private static final Logger logger = LogManager.getLogger(PickingSlotViewRepository.class);

	private final PickingHUsRepository pickedHUsRepo;

	private final Supplier<LookupDataSource> warehouseLookup;
	private final Supplier<LookupDataSource> bpartnerLookup;
	private final Supplier<LookupDataSource> bpartnerLocationLookup;

	/**
	 * @param pickingHUsRepo the "backend" repo to be used by this instance.
	 */
	@Autowired
	public PickingSlotViewRepository(@NonNull final PickingHUsRepository pickingHUsRepo)
	{
		// creating those LookupDataSources requires DB access. so to allow this component to be initialized early during startup
		// and also to allow it to be unit-tested (when the lookups are not part of the test), I use those suppliers

		this(
				pickingHUsRepo,
				// warehouseLookup
				Suppliers.memoize(() -> LookupDataSourceFactory.instance
						.getLookupDataSource(SqlLookupDescriptor.builder()
								.setColumnName(I_M_PickingSlot.COLUMNNAME_M_Warehouse_ID)
								.setDisplayType(DisplayType.Search)
								.setWidgetType(DocumentFieldWidgetType.Lookup)
								.buildProvider()
								.provideForScope(LookupScope.DocumentField))),
				// bpartnerLookup
				Suppliers.memoize(() -> LookupDataSourceFactory.instance
						.getLookupDataSource(SqlLookupDescriptor.builder()
								.setColumnName(I_M_PickingSlot.COLUMNNAME_C_BPartner_ID)
								.setDisplayType(DisplayType.Search)
								.setWidgetType(DocumentFieldWidgetType.Lookup)
								.buildProvider()
								.provideForScope(LookupScope.DocumentField))),
				// bpartnerLocationLookup
				Suppliers.memoize(() -> LookupDataSourceFactory.instance
						.getLookupDataSource(SqlLookupDescriptor.builder()
								.setColumnName(I_M_PickingSlot.COLUMNNAME_C_BPartner_Location_ID)
								.setDisplayType(DisplayType.Search)
								.setWidgetType(DocumentFieldWidgetType.Lookup)
								.buildProvider()
								.provideForScope(LookupScope.DocumentField))));
	}

	@VisibleForTesting
	/* package */ PickingSlotViewRepository(
			@NonNull final PickingHUsRepository pickingHUsRepo,
			@NonNull final Supplier<LookupDataSource> warehouseLookup,
			@NonNull final Supplier<LookupDataSource> bpartnerLookup,
			@NonNull final Supplier<LookupDataSource> bpartnerLocationLookup)
	{
		this.pickedHUsRepo = pickingHUsRepo;
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
	// ..ad least for checkPreconditionsApplicable()
	public List<PickingSlotRow> retrieveRows(@NonNull final PickingSlotRepoQuery query)
	{
		Check.errorIf(query.getShipmentScheduleIds().isEmpty(), "Given query has no shipmentScheduleIds; query={}", query);

		// get M_HU_Source records that reference active HUs with their locator in this WH and not on the picking location
		final List<HUEditorRow> sourceHuEditorRows = pickedHUsRepo.retrieveSourceHUs(query.getShipmentScheduleIds());
		final List<PickingSlotRow> sourceHUPickingSlotRows = sourceHuEditorRows.stream().map(sourceHuEditorRow -> createSourceHURow(sourceHuEditorRow)).collect(Collectors.toList());

		final ImmutableList<PickingSlotRow> pickingSlotRows = retrievePickingSlotRows(query);
		if (pickingSlotRows.isEmpty())
		{
			logger.warn("PickingSlotViewRepository returned empty list for query={}", query);
		}

		return ImmutableList.copyOf(Iterables.concat(
				sourceHUPickingSlotRows,
				pickingSlotRows));
	}

	@VisibleForTesting
	ImmutableList<PickingSlotRow> retrievePickingSlotRows(final PickingSlotRepoQuery query)
	{
		final I_M_ShipmentSchedule shipmentSchedule = loadOutOfTrx(query.getShipmentScheduleIds().get(0), I_M_ShipmentSchedule.class);

		// retrieve the M_PickingSlots that are available for the given shipmentSchedules' partner and location.
		// assume that all shipment schedules have the same partner and location (needs to be made sure) before starting all this stuff
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

		final int bpartnerId = shipmentScheduleEffectiveBL.getC_BPartner_ID(shipmentSchedule);
		final int bpartnerLocationId = shipmentScheduleEffectiveBL.getC_BP_Location_ID(shipmentSchedule);

		final IPickingSlotDAO pickingSlotDAO = Services.get(IPickingSlotDAO.class);

		final Set<Integer> pickingSlotIds = pickingSlotDAO
				.retrivePickingSlotsForBPartner(Env.getCtx(), bpartnerId, bpartnerLocationId)
				.stream()
				.filter(ps -> ps.getM_Warehouse_ID() == shipmentSchedule.getM_Warehouse_ID())
				.map(I_M_PickingSlot::getM_PickingSlot_ID)
				.collect(ImmutableSet.toImmutableSet());

		// retrieve picked HU rows (if any) to be displayed below there respective picking slots
		final ListMultimap<Integer, PickedHUEditorRow> huEditorRowsByPickingSlotId = pickedHUsRepo.retrievePickedHUsIndexedByPickingSlotId(query);

		final Predicate<? super I_M_PickingSlot> predicate = pickingSlotPO -> {
			if (query.getPickingCandidates() == PickingCandidate.DONT_CARE)
			{
				return true;
			}

			// For any other PickingCandidate enum value, huEditorRowsByPickingSlotId only contains items which match that value.
			// That's because we already invoked pickingHUsRepo with the same query.
			return huEditorRowsByPickingSlotId.containsKey(pickingSlotPO.getM_PickingSlot_ID());
		};

		final ImmutableList<PickingSlotRow> result = pickingSlotDAO
				.retrievePickingSlotsByIds(pickingSlotIds).stream() // get stream of I_M_PickingSlot
				.filter(predicate) // filter according to 'query'
				.map(pickingSlotPO -> createPickingSlotRow(pickingSlotPO, huEditorRowsByPickingSlotId)) // create the actual PickingSlotRows
				.collect(ImmutableList.toImmutableList());
		return result;
	}

	/**
	 * Creates a HU related picking slot row for the given HU editor row and the given {@code pickingSlotId}.
	 * 
	 * @param from the hu editor row to create a picking slot row for. If it has included HU editor rows, then the method creates an included picking slot line accordingly.
	 * @param pickingSlotId
	 * @return
	 */
	private final PickingSlotRow createPickedHURow(
			@NonNull final PickedHUEditorRow from,
			final int pickingSlotId)
	{
		final HUEditorRow huEditorRow = from.getHuEditor();

		final List<PickingSlotRow> includedHURows = huEditorRow.getIncludedRows()
				.stream()
				.map(includedHUEditorRow -> createPickedHURow(
						new PickedHUEditorRow(includedHUEditorRow, from.isProcessed()), // create PickingSlotRows for the included HU rows which shall inherit the parent's processed flag
						pickingSlotId))
				.collect(ImmutableList.toImmutableList());

		return PickingSlotRow.fromPickedHUBuilder()
				.pickingSlotId(pickingSlotId)
				.huId(huEditorRow.getM_HU_ID())

				//
				.type(huEditorRow.getType())

				// do *not* take the HUEditorRow's processed flag, because we don't care about that; we do care about PickingSlotHUEditorRow.isProcessed() because that's the value coming from the M_Picking_Candiate
				.processed(from.isProcessed())

				.huCode(huEditorRow.getValue())
				.product(huEditorRow.getProduct())
				.packingInfo(huEditorRow.getPackingInfo())
				.qtyCU(huEditorRow.getQtyCU())
				//
				.includedHURows(includedHURows)
				//
				.build();
	}

	@VisibleForTesting
	PickingSlotRow createSourceHURow(@NonNull final HUEditorRow sourceHuEditorRow)
	{
		final PickingSlotRow pickingSourceHuRow = PickingSlotRow.fromSourceHUBuilder()
				.huId(sourceHuEditorRow.getM_HU_ID())
				.type(sourceHuEditorRow.getType())
				.huCode(sourceHuEditorRow.getValue())
				.product(sourceHuEditorRow.getProduct())
				.packingInfo(sourceHuEditorRow.getPackingInfo())
				.qtyCU(sourceHuEditorRow.getQtyCU())
				.build();

		return pickingSourceHuRow;
	}

	private PickingSlotRow createPickingSlotRow(
			@NonNull final I_M_PickingSlot pickingSlotPO,
			@NonNull final ListMultimap<Integer, PickedHUEditorRow> huEditorRowsByPickingSlotId)
	{
		final int pickingSlotId = pickingSlotPO.getM_PickingSlot_ID();

		// create picking slot rows for included/picked HUs
		final List<PickingSlotRow> pickedHuRows = huEditorRowsByPickingSlotId.get(pickingSlotId)
				.stream()
				.map(pickingSlotHuEditorRow -> createPickedHURow(pickingSlotHuEditorRow, pickingSlotId))
				.collect(ImmutableList.toImmutableList());

		return PickingSlotRow.fromPickingSlotBuilder()
				.pickingSlotId(pickingSlotId)
				//
				.pickingSlotName(pickingSlotPO.getPickingSlot())
				.pickingSlotWarehouse(warehouseLookup.get().findById(pickingSlotPO.getM_Warehouse_ID()))
				.pickingSlotBPartner(bpartnerLookup.get().findById(pickingSlotPO.getC_BPartner_ID()))
				.pickingSlotBPLocation(bpartnerLocationLookup.get().findById(pickingSlotPO.getC_BPartner_Location_ID()))
				.includedHURows(pickedHuRows)
				//
				.build();
	}
}
