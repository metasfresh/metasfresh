package de.metas.handlingunits.client.terminal.inventory.model;

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


import java.util.Collection;
import java.util.List;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.ad.dao.IQueryAggregateBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.Predicate;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;

import de.metas.adempiere.form.terminal.IKeyLayoutSelectionModel;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.TerminalKeyListenerAdapter;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.select.api.IPOSTableRow;
import de.metas.handlingunits.client.terminal.select.model.AbstractHUSelectModel;
import de.metas.handlingunits.client.terminal.select.model.BPartnerLocationKey;
import de.metas.handlingunits.client.terminal.select.model.BPartnerLocationKeyLayout;
import de.metas.handlingunits.client.terminal.select.model.IHUEditorCallback;
import de.metas.handlingunits.client.terminal.select.model.VendorKey;
import de.metas.handlingunits.client.terminal.select.model.VendorKeyLayout;
import de.metas.handlingunits.client.terminal.select.model.WarehouseKey;
import de.metas.handlingunits.client.terminal.select.model.WarehouseKeyLayout;
import de.metas.handlingunits.document.IHUDocumentLineFinder;
import de.metas.handlingunits.document.impl.NullHUDocumentLineFinder;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;

/**
 * Model for
 * <ul>
 * <li>Verdichtung (POS)
 * <li>Eigenverbrauch (POS)
 * </ul>
 */

public class InventoryHUSelectModel extends AbstractHUSelectModel
{
	/**
	 * {@link I_C_BPartner_Location}'s dynamic property which is containing how many locked HUs are on that location.
	 *
	 * It is used by renderes when decide what color the BPartnerLocation Key shall have.
	 */
	public static final ModelDynAttributeAccessor<I_C_BPartner_Location, Integer> DYNATTR_CountLockedHUs = new ModelDynAttributeAccessor<>("CountLockedHUs", Integer.class);

	protected static final String MSG_ErrorNoBPartnerLocationSelected = "de.metas.handlingunits.client.ErrorNoBPartnerLocationSelected";

	// services
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	private IHUDocumentLineFinder huDocumentLineFinder = NullHUDocumentLineFinder.instance; // no document line available by default

	private final BPartnerLocationKeyLayout _bpartnerLocationsKeyLayout;

	public InventoryHUSelectModel(final ITerminalContext terminalContext)
	{
		// We don't have any other tables.
		super(terminalContext);

		//
		// Warehouses Key Kayout
		{
			final WarehouseKeyLayout warehouseKeyLayout = getWarehouseKeyLayout();
			warehouseKeyLayout.setRows(4);
		}

		//
		// BPartners Key Layout
		final VendorKeyLayout bpartnersKeyLayout = getVendorKeyLayout();
		{
			setDisplayVendorKeys(false); // hide them at this level

			// Preconfigure the Vendor Keys layout, just in case somebody wants to use it
			bpartnersKeyLayout.setColumns(3);
			bpartnersKeyLayout.setRows(4);
		}

		//
		// BPartner Locations Key Layout
		{
			//
			// Configure vendorKeyLayout selectionModel
			_bpartnerLocationsKeyLayout = new BPartnerLocationKeyLayout(terminalContext);
			{
				_bpartnerLocationsKeyLayout.setColumns(bpartnersKeyLayout.getColumns()); // make sure it has the same number of columns as BPartners key layout
				_bpartnerLocationsKeyLayout.setRows(4);

				final IKeyLayoutSelectionModel bpartnerLocationsKeyLayoutSelectionModel = _bpartnerLocationsKeyLayout.getKeyLayoutSelectionModel();
				bpartnerLocationsKeyLayoutSelectionModel.setAllowKeySelection(true);
				bpartnerLocationsKeyLayoutSelectionModel.setToggleableSelection(true);
			}
			_bpartnerLocationsKeyLayout.addTerminalKeyListener(new TerminalKeyListenerAdapter()
			{
				@Override
				public void keyReturned(final ITerminalKey key)
				{
					final BPartnerLocationKey bpartnerLocationKey = (BPartnerLocationKey)key;
					onBPartnerLocationKeyPressed(bpartnerLocationKey);
				}
			});

		}

		//
		// Rows/Lines table
		{
			// This model ignores rows, as the POS does not display/need them.
			setAllowNoRowsSelection(true);
		}

		//
		// Load data
		load();
	}

	public void setHUDocumentLineFinder(final IHUDocumentLineFinder huDocumentLineFinder)
	{
		Check.assumeNotNull(huDocumentLineFinder, "huDocumentLineFinder not null");
		this.huDocumentLineFinder = huDocumentLineFinder;
	}

	@Override
	protected Predicate<IPOSTableRow> getRowsFilter()
	{
		// Returns null, as we don't have rows.
		return null;
	}

	@Override
	protected void onWarehouseKeyPressed(final WarehouseKey key)
	{
		getVendorKeyLayout().getKeyLayoutSelectionModel().onKeySelected(null);
		getBPartnerLocationKeyLayout().createAndSetKeysFromBPartnerLocations(null); // make sure BParter Location Keys are cleared
		refreshBPartnerKeysIfNeeded(true, null); // no extra filter needed
	}

	@Override
	protected void onVendorKeyPressed(final VendorKey key)
	{
		getBPartnerLocationKeyLayout().getKeyLayoutSelectionModel().onKeySelected(null);
		refreshBPartnerLocationKeysIfNeeded();
	}

	protected void onBPartnerLocationKeyPressed(final BPartnerLocationKey bpartnerLocationKey)
	{
		// nothing on this level
	}

	@Override
	protected void loadKeysFromLines(final List<IPOSTableRow> lines)
	{
		// Nothing to do on this level.

	}

	@Override
	protected final HUEditorModel createHUEditorModel(final Collection<IPOSTableRow> rows, final IHUEditorCallback<HUEditorModel> editorCallback_NOTUSED)
	{
		final IHUQueryBuilder husQuery = createHUQueryBuilder();

		final ITerminalContext terminalContext = getTerminalContext();
		final IHUKeyFactory keyFactory = terminalContext.getService(IHUKeyFactory.class);

		//
		// Clear (attribute) cache before opening editor
		// keyFactory.clearCache(); // NOTE: already done on HUEditor ctor

		//
		// Create HU Editor Model
		final HUEditorModel huEditorModel = createHUEditorModelInstance();

		//
		// Create a Root HU Key from HUs
		final IHUKey rootKey = keyFactory.createRootKey(husQuery, huDocumentLineFinder);
		huEditorModel.setRootHUKey(rootKey);

		// If there is no huDocumentLineFinder, there is no point to check and update HU Allocations
		huEditorModel.setUpdateHUAllocationsOnSave(!NullHUDocumentLineFinder.isNull(huDocumentLineFinder));

		return huEditorModel;
	}

	/**
	 * Creates {@link IHUQueryBuilder} used for retriving HUs in this POS.
	 *
	 * @return
	 */
	// public for testing
	// no longer final because in AggregationHUSelectModel it shall behave differently
	public IHUQueryBuilder createHUQueryBuilder()
	{
		final boolean considerSelectedBPartner = true;
		final boolean considerSelectedBPartnerLocation = true;
		final boolean onlyAfterPickingLocator = false;
		return createHUQueryBuilder(considerSelectedBPartner, considerSelectedBPartnerLocation, onlyAfterPickingLocator);
	}

	public final IHUQueryBuilder createHUQueryBuilder(final boolean considerSelectedBPartner, final boolean considerSelectedBPartnerLocation, final boolean onlyAfterPickingLocator)
	{
		final ITerminalContext terminalContext = getTerminalContext();

		// query builder for top level hus
		final IHUQueryBuilder huQueryBuilder = handlingUnitsDAO.createHUQueryBuilder()
				.setContext(terminalContext.getCtx(), ITrx.TRXNAME_None)
				.setOnlyTopLevelHUs()
				.setErrorIfNoHUs(MSG_ErrorNoHandlingUnitFoundForSelection)
		//
		;

		//
		// Filter by Warehouse
		final int warehouseId = getM_Warehouse_ID(true); // failIfNotSelected=true
		huQueryBuilder.addOnlyInWarehouseId(warehouseId);

		//
		// Filter by BPartner/Location
		if (considerSelectedBPartner && isDisplayVendorKeys())
		{
			// BPartner
			final int bPartnerId = getC_BPartner_ID();
			if (bPartnerId <= 0)
			{
				throw new AdempiereException("@" + MSG_ErrorNoBPartnerSelected + "@");
			}
			huQueryBuilder.addOnlyInBPartnerId(bPartnerId);

			// BPartner Location
			if (considerSelectedBPartnerLocation)
			{
				final int bpartnerLocationId = getC_BPartner_Location_ID();
				if (bpartnerLocationId <= 0)
				{
					throw new AdempiereException("@" + MSG_ErrorNoBPartnerLocationSelected + "@");
				}
				huQueryBuilder.addOnlyWithBPartnerLocationId(bpartnerLocationId);
			}
		}

		//
		// Exclude Planning HUs (07732)
		huQueryBuilder.addHUStatusToExclude(X_M_HU.HUSTATUS_Planning);

		//
		// Exclude Planning HUs (08544)
		huQueryBuilder.addHUStatusToExclude(X_M_HU.HUSTATUS_Destroyed);

		//
		// We check for additional filters (set from the frame).
		if (onlyAfterPickingLocator)
		{
			// Make sure only the HUs from an AfterPicking Locator are displayed
			huQueryBuilder.setIncludeAfterPickingLocator(true);
		}

		final IQueryFilter<I_M_HU> additionalFilter = getAdditionalHUFilter();

		if (null != additionalFilter)
		{
			huQueryBuilder.addFilter(additionalFilter);
		}

		return huQueryBuilder;
	}

	protected HUEditorModel createHUEditorModelInstance()
	{
		final ITerminalContext terminalContext = getTerminalContext();
		final InventoryHUEditorModel huEditorModel = new InventoryHUEditorModel(terminalContext, getM_Warehouse_ID());
		return huEditorModel;
	}

	/**
	 * i.e. refresh all Key panels (excluding the Warehouse)
	 */
	@Override
	public void refreshLines(final boolean forceRefresh)
	{
		// no extra filter needed
		refreshBPartnerKeysIfNeeded(forceRefresh, null);
	}

	/**
	 * @param forceRefresh
	 * @param filter : additional filter for details that are not common for all the models (e.g. for AggregateHUSelectModel)
	 */
	public final void refreshBPartnerKeysIfNeeded(final boolean forceRefresh, final IQueryFilter<I_M_HU> filter)
	{
		final VendorKeyLayout bpartnerKeyLayout = getVendorKeyLayout();

		// the vendor lines are refreshed only if they are displayed
		if (!isDisplayVendorKeys())
		{
			bpartnerKeyLayout.createAndSetKeysFromBPartners(null);
			return;
		}

		final boolean bpartnersLoaded = !bpartnerKeyLayout.isEmpty();

		// Refresh BPartners only if we if they are not already loaded, or we do a force refresh
		if (bpartnersLoaded && !forceRefresh)
		{
			return;
		}

		//
		// Load BPartner Keys
		final boolean considerSelectedBPartner = false; // ofc not
		final boolean considerSelectedBPartnerLocation = false; // ofc not, does not matter
		final boolean onlyAfterPickingLocator = false;
		IHUQueryBuilder huQueryBuilder = createHUQueryBuilder(considerSelectedBPartner, considerSelectedBPartnerLocation, onlyAfterPickingLocator);

		if (null != filter)
		{
			huQueryBuilder.addFilter(filter);
		}

		final List<I_C_BPartner> bPartners = huQueryBuilder.collect(I_M_HU.COLUMN_C_BPartner_ID);
		bpartnerKeyLayout.createAndSetKeysFromBPartners(bPartners);

		//
		// Refresh current BPartnerLocationKeys, if any
		refreshBPartnerLocationKeysIfNeeded();
	}

	// public for testing purposes
	public final void refreshBPartnerLocationKeysIfNeeded()
	{
		final BPartnerLocationKeyLayout bpartnerLocationsKeyLayout = getBPartnerLocationKeyLayout();

		if (!isDisplayVendorKeys())
		{
			bpartnerLocationsKeyLayout.createAndSetKeysFromBPartnerLocations(null);
			return;
		}

		//
		// If there is no BPartner Key selected
		// => empty the locations
		final int bpartnerId = getC_BPartner_ID();
		if (bpartnerId <= 0)
		{
			bpartnerLocationsKeyLayout.createAndSetKeysFromBPartnerLocations(null);
			return;
		}

		//
		// Created M_HU.C_BPartner_Location_ID aggregation
		final boolean considerSelectedBPartner = true; // yes, we want to consider the current selected BPartner
		final boolean considerSelectedBPartnerLocation = false; // ofcourse not
		final boolean onlyAfterPickingLocator = true;
		final IQueryAggregateBuilder<I_M_HU, I_C_BPartner_Location> bpLocationsAggregate = createHUQueryBuilder(considerSelectedBPartner, considerSelectedBPartnerLocation, onlyAfterPickingLocator)
				.createQueryBuilder()
				.aggregateOnColumn(I_M_HU.COLUMN_C_BPartner_Location_ID);

		// Define CountLockedHUs aggregator:
		// * counts how many Locked HUs are for each BPartner Location
		// * set the result as a dynamic property on target I_C_BPartner_Location models
		bpLocationsAggregate.countIf(DYNATTR_CountLockedHUs)
				.filter()
				.addEqualsFilter(I_M_HU.COLUMN_Locked, true);

		//
		// Aggregate the HUs to their M_HU.C_BPartner_Location
		// NOTE: each loaded BPartner Location will have our aggregated values set as dynamic properties
		final List<I_C_BPartner_Location> bpartnerLocations = bpLocationsAggregate.aggregate();

		// Set loaded BPartner Locations to our BPartner Location Key Layout
		bpartnerLocationsKeyLayout.createAndSetKeysFromBPartnerLocations(bpartnerLocations);
	}

	public final BPartnerLocationKeyLayout getBPartnerLocationKeyLayout()
	{
		return _bpartnerLocationsKeyLayout;
	}

	/**
	 * @return C_BPartner_Location_ID of BPartner Location Key that is currently pressed or <code>-1</code>
	 */
	@OverridingMethodsMustInvokeSuper
	// NOTE: allow override only for testing purposes
	public int getC_BPartner_Location_ID()
	{
		final BPartnerLocationKey bpartnerLocationKey = getBPartnerLocationKeyLayout()
				.getKeyLayoutSelectionModel()
				.getSelectedKeyOrNull(BPartnerLocationKey.class);
		if (bpartnerLocationKey == null)
		{
			return -1;
		}
		return bpartnerLocationKey.getC_BPartner_Location_ID();
	}

}
