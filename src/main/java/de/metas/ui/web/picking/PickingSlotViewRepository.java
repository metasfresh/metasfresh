package de.metas.ui.web.picking;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Product;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;

import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.shipmentschedule.api.impl.ShipmentScheduleQtyPickedProductStorage;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.picking.api.IPickingSlotDAO;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.picking.model.I_M_Picking_Candidate;
import de.metas.quantity.Quantity;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider.LookupScope;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;

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

@Component
public class PickingSlotViewRepository
{
	private final transient IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

	@Autowired
	private PickingHUsRepository pickingHUsRepo;

	private final LookupDataSource warehouseLookup;
	private final LookupDataSource bpartnerLookup;
	private final LookupDataSource bpartnerLocationLookup;

	@Autowired
	public PickingSlotViewRepository(final Adempiere databaseAccess)
	{
		warehouseLookup = LookupDataSourceFactory.instance.getLookupDataSource(SqlLookupDescriptor.builder()
				.setColumnName(I_M_PickingSlot.COLUMNNAME_M_Warehouse_ID)
				.setDisplayType(DisplayType.Search)
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.buildProvider()
				.provideForScope(LookupScope.DocumentField));
		bpartnerLookup = LookupDataSourceFactory.instance.getLookupDataSource(SqlLookupDescriptor.builder()
				.setColumnName(I_M_PickingSlot.COLUMNNAME_C_BPartner_ID)
				.setDisplayType(DisplayType.Search)
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.buildProvider()
				.provideForScope(LookupScope.DocumentField));
		bpartnerLocationLookup = LookupDataSourceFactory.instance.getLookupDataSource(SqlLookupDescriptor.builder()
				.setColumnName(I_M_PickingSlot.COLUMNNAME_C_BPartner_Location_ID)
				.setDisplayType(DisplayType.Search)
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.buildProvider()
				.provideForScope(LookupScope.DocumentField));
	}

	public List<PickingSlotRow> retrieveRowsByShipmentScheduleId(final int shipmentScheduleId)
	{
		final ListMultimap<Integer, HUEditorRow> huEditorRowsByPickingSlotId = pickingHUsRepo.retrieveHUsIndexedByPickingSlotId(shipmentScheduleId);
		// final Set<Integer> pickingSlotIds = huEditorRowsByPickingSlotId.keySet();
		// FIXME: debugging!!!!
		final Set<Integer> pickingSlotIds = Services.get(IPickingSlotDAO.class).retrievePickingSlots(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.stream()
				.map(I_M_PickingSlot::getM_PickingSlot_ID)
				.collect(ImmutableSet.toImmutableSet());

		return Services.get(IPickingSlotDAO.class).retrievePickingSlotsByIds(pickingSlotIds)
				.stream()
				.map(pickingSlotPO -> createPickingSlotRow(pickingSlotPO, huEditorRowsByPickingSlotId))
				.collect(ImmutableList.toImmutableList());
	}

	private final PickingSlotRow createHURow(final HUEditorRow from, final int pickingSlotId)
	{
		final List<PickingSlotRow> includedHURows = from.getIncludedRows()
				.stream()
				.map(includedHUEditorRow -> createHURow(includedHUEditorRow, pickingSlotId))
				.collect(ImmutableList.toImmutableList());

		return PickingSlotRow.fromHUBuilder()
				.pickingSlotId(pickingSlotId)
				.huId(from.getM_HU_ID())
				//
				.type(from.getType())
				.processed(true)
				//
				.code(from.getValue())
				.product(from.getProduct())
				.packingInfo(from.getPackingInfo())
				.qtyCU(from.getQtyCU())
				//
				.includedHURows(includedHURows)
				//
				.build();
	}

	public Set<Integer> retrieveAllRowIds()
	{
		return Services.get(IPickingSlotDAO.class).retrievePickingSlots(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.stream()
				.map(I_M_PickingSlot::getM_PickingSlot_ID)
				.collect(ImmutableSet.toImmutableSet());
	}

	private PickingSlotRow createPickingSlotRow(final I_M_PickingSlot pickingSlotPO, final ListMultimap<Integer, HUEditorRow> huEditorRowsByPickingSlotId)
	{
		final int pickingSlotId = pickingSlotPO.getM_PickingSlot_ID();
		final List<PickingSlotRow> huRows = huEditorRowsByPickingSlotId.get(pickingSlotId)
				.stream()
				.map(huEditorRow -> createHURow(huEditorRow, pickingSlotId))
				.collect(ImmutableList.toImmutableList());

		return PickingSlotRow.fromPickingSlotBuilder()
				.pickingSlotId(pickingSlotId)
				//
				.pickingSlotName(pickingSlotPO.getPickingSlot())
				.pickingSlotWarehouse(warehouseLookup.findById(pickingSlotPO.getM_Warehouse_ID()))
				.pickingSlotBPartner(bpartnerLookup.findById(pickingSlotPO.getC_BPartner_ID()))
				.pickingSlotBPLocation(bpartnerLocationLookup.findById(pickingSlotPO.getC_BPartner_Location_ID()))
				.includedHURows(huRows)
				//
				.build();
	}

	public void addHUToPickingSlot(final int huId, final int pickingSlotId, final int shipmentScheduleId)
	{
		pickingHUsRepo.addHUToPickingSlot(huId, pickingSlotId, shipmentScheduleId);
	}

	public void removeHUFromPickingSlot(final int huId, final int pickingSlotId)
	{
		pickingHUsRepo.removeHUFromPickingSlot(huId, pickingSlotId);
	}

	public void addQtyToHU(final BigDecimal qtyCU, final int huId, final int pickingSlotId, final int shipmentScheduleId)
	{
		if(qtyCU.signum() <= 0)
		{
			throw new AdempiereException("@Invalid@ @QtyCU@");
		}
		
		final I_M_ShipmentSchedule shipmentSchedule = InterfaceWrapperHelper.load(shipmentScheduleId, I_M_ShipmentSchedule.class);
		I_M_Product product = shipmentSchedule.getM_Product();

		//
		// Source
		final IAllocationSource source;
		{
			final I_M_Picking_Candidate candidate = pickingHUsRepo.getCreateCandidate(huId, pickingSlotId, shipmentScheduleId);
			
			final IProductStorage storage = new ShipmentScheduleQtyPickedProductStorage(shipmentSchedule);
			source = new GenericAllocationSourceDestination(storage, candidate);
		}

		//
		// Destination: HU
		final IAllocationDestination destination;
		{
			final I_M_HU hu = InterfaceWrapperHelper.load(huId, I_M_HU.class);
			if (!X_M_HU.HUSTATUS_Planning.equals(hu.getHUStatus()))
			{
				throw new AdempiereException("not a planning HU").setParameter("hu", hu);
			}
			destination = HUListAllocationSourceDestination.of(hu);
		}

		//
		// Request
		final IMutableHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContextForProcessing(Env.getCtx());
		final IAllocationRequest request = AllocationUtils.createAllocationRequestBuilder()
				.setHUContext(huContext)
				.setProduct(product)
				.setQuantity(Quantity.of(qtyCU, shipmentScheduleBL.getC_UOM(shipmentSchedule)))
				.setDateAsToday()
				.setFromReferencedModel(shipmentSchedule)
				.setForceQtyAllocation(true)
				.create();

		//
		// Load QtyCU to HU(destination)
		HULoader.of(source, destination)
				.setAllowPartialLoads(false)
				.setAllowPartialUnloads(false)
				.load(request);
	}

}
