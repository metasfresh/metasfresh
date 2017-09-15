package de.metas.handlingunits.client.terminal.aggregate.model;

/*
 * #%L
 * de.metas.handlingunits.client
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.warehouse.model.I_M_Warehouse;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHUShipperTransportationBL;
import de.metas.handlingunits.IHUWarehouseDAO;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.inventory.model.InventoryHUSelectModel;
import de.metas.handlingunits.client.terminal.select.model.BPartnerLocationKeyLayout;
import de.metas.handlingunits.client.terminal.select.model.VendorKeyLayout;
import de.metas.handlingunits.client.terminal.select.model.WarehouseKeyLayout;
import de.metas.handlingunits.inout.IHUShipmentAssignmentBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.handlingunits.model.I_M_PickingSlot;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.IHUPickingSlotDAO;
import de.metas.interfaces.I_C_BPartner;

/**
 * Verdichtung (POS) HU Select Model (first window)
 *
 * @author tsa
 *
 */
public class AggregateHUSelectModel extends InventoryHUSelectModel
{
	// Services
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient IHUShipperTransportationBL huShipperTransportationBL = Services.get(IHUShipperTransportationBL.class);
	private final transient IHUShipmentAssignmentBL huShipmentAssignmentBL = Services.get(IHUShipmentAssignmentBL.class);
	private final transient IHUWarehouseDAO huWarehouseDAO = Services.get(IHUWarehouseDAO.class);
	private final transient IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
	private final transient IHUPickingSlotDAO huPickingSlotDAO = Services.get(IHUPickingSlotDAO.class);

	public AggregateHUSelectModel(final ITerminalContext terminalContext)
	{
		super(terminalContext);

		setAdditionalHUFilter(createAdditionalHUFilter());

		//
		// Warehouses Key Layout
		final WarehouseKeyLayout warehousesKeyLayout = getWarehouseKeyLayout();
		warehousesKeyLayout.setRows(2); // we don't have to many warehouses here

		//
		// BPartners Key Layout
		setDisplayVendorKeys(true); // make sure is displayed
		final VendorKeyLayout bpartersKeyLayout = getVendorKeyLayout();
		bpartersKeyLayout.setRows(5);

		//
		// BPartner Locations Key Layout
		final BPartnerLocationKeyLayout bpartnerLocationsKeyLayout = getBPartnerLocationKeyLayout();
		bpartnerLocationsKeyLayout.setRows(5); // because there are BPartners with a lot of locations
	}

	/**
	 * @see de.metas.handlingunits.client.terminal.inventory.model.InventoryHUSelectModel#createHUQueryBuilder()
	 * 
	 * Overrode the method here, so in case it is called from the Aggregation (Verdichtung) POS we have only the HUs from locator that are AfterPicking
	 */
	@Override
	public IHUQueryBuilder createHUQueryBuilder()
	{
		final boolean considerSelectedBPartner = true;
		final boolean considerSelectedBPartnerLocation = true;
		final boolean onlyAfterPickingLocator = true;

		IHUQueryBuilder huQueryBuilder = createHUQueryBuilder(considerSelectedBPartner, considerSelectedBPartnerLocation, onlyAfterPickingLocator);

		return huQueryBuilder;
	}

	/* (non-Javadoc)
	 * @see de.metas.handlingunits.client.terminal.inventory.model.InventoryHUSelectModel#refreshLines(boolean)
	 * 
	 * Overrode the method here so I can add the additional filter for excluding statuses A and E
	 */
	@Override
	public final void refreshLines(final boolean forceRefresh)
	{
		final ICompositeQueryFilter<I_M_HU> filter = queryBL.createCompositeQueryFilter(I_M_HU.class);

		final Set<String> huStatusesToExclude = new HashSet<>();
		huStatusesToExclude.add(X_M_HU.HUSTATUS_Active);
		huStatusesToExclude.add(X_M_HU.HUSTATUS_Shipped);

		filter.addNotInArrayFilter(I_M_HU.COLUMN_HUStatus, huStatusesToExclude);

		refreshBPartnerKeysIfNeeded(forceRefresh, filter);
	}

	/**
	 * Create additional HUs filter
	 *
	 * @return filter
	 */
	private IQueryFilter<I_M_HU> createAdditionalHUFilter()
	{
		final ITerminalContext terminalContext = getTerminalContext();

		final ICompositeQueryFilter<I_M_HU> filters = queryBL.createCompositeQueryFilter(I_M_HU.class);
		filters.setJoinAnd();

		//
		// Accept only those HUs which are not assigned to a Shipment already
		filters.addFilter(huShipmentAssignmentBL.createHUsNotAssignedToShipmentsFilter(terminalContext));

		return filters;
	}

	@Override
	protected HUEditorModel createHUEditorModelInstance()
	{
		final ITerminalContext terminalContext = getTerminalContext();
		final AggregateHUEditorModel huEditorModel = new AggregateHUEditorModel(terminalContext);
		return huEditorModel;
	}

	/**
	 * Generate shipments from HUs which are displayed in this panel and which were enqueued to shipper transportation.
	 *
	 * @throws AdempiereException is there are no unlocked, (i.e. NOT added-to-shipper-transportation) HUs.
	 */
	public final void doCreateShipment()
	{
		//
		// Create the HU query which will be used to select the right HUs for shipment generation.
		final boolean considerSelectedBPartner = true; // always consider the selected BPartner
		final boolean considerSelectedBPartnerLocation = getC_BPartner_Location_ID() > 0; // consider the BPartner Location only if selected
		final boolean onlyAfterPickingLocator = true;
		final IHUQueryBuilder husQueryBuilder = createHUQueryBuilder(considerSelectedBPartner, considerSelectedBPartnerLocation, onlyAfterPickingLocator);

		//
		// Generate shipments using our HUs selection query
		final Properties ctx = getCtx();
		huShipperTransportationBL.generateShipments(ctx, husQueryBuilder);

		// Refresh all (bpartner and bpartner location keys) in case some of them shall not be displayed anymore because there are no HUs on them
		// NOTE: because Shipment generation is done asynchronously, it's a big chance to have the BP and BPLocation keys displayed after following refresh,
		// because at the moment of refresh the HUs where not already processed by ASYNC.
		refreshLines(true);
	}

	/**
	 * Close open picked HUs
	 *
	 * @param pickingSlotsToFree
	 * @task 07834
	 */
	public void doCloseOpenPickedHUs(final List<I_M_PickingSlot> pickingSlotsToFree)
	{
		for (final I_M_PickingSlot pickingSlot : pickingSlotsToFree)
		{
			huPickingSlotBL.closeCurrentHU(pickingSlot);
		}

		// Refresh all (BPartner and BPartner Location keys)
		refreshLines(true);
	}

	/**
	 * @return picking slots which have open HUs on them and are for currently selected Warehouse and BPartner.
	 */
	public List<I_M_PickingSlot> getPickingSlotsToFree()
	{
		final I_M_Warehouse warehouse = getM_Warehouse();

		//
		// Get after-picking locator
		final I_M_Locator afterPickingLocator = huWarehouseDAO.suggestAfterPickingLocator(warehouse);
		final I_C_BPartner partner = getC_BPartner();

		//
		// Get all picking slots for selected partner and after-picking locator of the selected warehouse
		final List<I_M_PickingSlot> pickingSlots = huPickingSlotDAO.retrievePickingSlots(partner, afterPickingLocator);

		//
		// Count HUs to close and display warning message to the user
		final List<I_M_PickingSlot> pickingSlotsToFree = new ArrayList<I_M_PickingSlot>();
		for (final I_M_PickingSlot pickingSlot : pickingSlots)
		{
			final I_M_HU currentHU = pickingSlot.getM_HU();
			if (currentHU == null || currentHU.getM_HU_ID() <= 0)
			{
				continue; // not open
			}
			pickingSlotsToFree.add(pickingSlot);
		}

		return pickingSlotsToFree;
	}
}
