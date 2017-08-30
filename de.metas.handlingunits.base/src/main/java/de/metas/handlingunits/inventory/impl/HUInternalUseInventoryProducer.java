package de.metas.handlingunits.inventory.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_M_Inventory;
import org.compiere.util.Env;

import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocActionBL;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.InventoryAllocationDestination;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.inventory.event.InventoryProcessedEventBus;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * Creates and processes Internal Use Inventory documents to destroy given HUs.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class HUInternalUseInventoryProducer
{
	public static HUInternalUseInventoryProducer newInstance()
	{
		return new HUInternalUseInventoryProducer();
	}

	// services
	private final transient IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	//
	// Parameters
	private Timestamp _movementDate;
	private String _docSubType = X_C_DocType.DOCSUBTYPE_MaterialDisposal;
	private final List<I_M_HU> _hus = new ArrayList<>();

	private HUInternalUseInventoryProducer()
	{
	}

	public List<I_M_Inventory> create()
	{
		final Map<Integer, List<I_M_HU>> husByWarehouseId = getHUs()
				.stream()
				.collect(Collectors.groupingBy(hu -> hu.getM_Locator().getM_Warehouse_ID()));

		final List<I_M_Inventory> result = new ArrayList<>();
		for (final Map.Entry<Integer, List<I_M_HU>> warehouseIdAndHUs : husByWarehouseId.entrySet())
		{
			final int warehouseId = warehouseIdAndHUs.getKey();
			final List<I_M_HU> hus = warehouseIdAndHUs.getValue();
			final List<I_M_Inventory> inventories = create(warehouseId, hus);
			result.addAll(inventories);
		}

		return result;
	}

	private final List<I_M_Inventory> create(final int warehouseId, final List<I_M_HU> hus)
	{
		final I_M_Warehouse warehouse = InterfaceWrapperHelper.loadOutOfTrx(warehouseId, I_M_Warehouse.class);

		// Make sure all HUs have ThreadInherited transaction (in order to use caching)
		InterfaceWrapperHelper.setThreadInheritedTrxName(hus);

		//
		// Allocation Source: our HUs
		final HUListAllocationSourceDestination husSource = HUListAllocationSourceDestination.of(hus);

		husSource.setCreateHUSnapshots(true);
		 husSource.setDestroyEmptyHUs(true); // get rid of those HUs which got empty

		//
		// Create and setup context
		final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
		final IMutableHUContext huContext = huContextFactory.createMutableHUContextForProcessing(PlainContextAware.newWithThreadInheritedTrx());
		huContext.setDate(getMovementDate());

		final I_C_DocType materialDisposalDocType = getInventoryDocType(warehouse);

		// Inventory allocation destination
		final InventoryAllocationDestination inventoryAllocationDestination = new InventoryAllocationDestination(warehouse, materialDisposalDocType);

		//
		// Create and configure Loader
		final HULoader loader = HULoader.of(husSource, inventoryAllocationDestination);
		loader.setAllowPartialLoads(true);

		//
		// Unload everything from source (our HUs)
		loader.unloadAllFromSource(huContext);

		// destroy empty hus

		{
			for (final I_M_HU hu : hus)
			{
				// Skip it if already destroyed
				if (handlingUnitsBL.isDestroyed(hu))
				{
					continue;
				}

				handlingUnitsBL.destroyIfEmptyStorage(huContext, hu);
			}
		}
		final List<I_M_Inventory> inventories = inventoryAllocationDestination.getInventories();

		for (final I_M_Inventory inventory : inventories)
		{
			Services.get(IDocActionBL.class).processEx(inventory, X_M_Inventory.DOCACTION_Complete, X_M_Inventory.DOCSTATUS_Completed);
		}
		//
		// Send notifications
		InventoryProcessedEventBus.newInstance()
				.queueEventsUntilCurrentTrxCommit()
				.notify(inventories);

		return inventories;
	}

	public HUInternalUseInventoryProducer setMovementDate(@NonNull final Timestamp movementDate)
	{
		_movementDate = movementDate;
		return this;
	}

	private Timestamp getMovementDate()
	{
		if (_movementDate == null)
		{
			_movementDate = Env.getDate(Env.getCtx());
		}
		return _movementDate;
	}

	public HUInternalUseInventoryProducer setDocSubType(@NonNull final String docSubType)
	{
		_docSubType = docSubType;
		return this;
	}

	@NonNull
	private String getDocSubType()
	{
		return _docSubType;
	}

	private I_C_DocType getInventoryDocType(final I_M_Warehouse warehouse)
	{
		final I_C_DocType docType = docTypeDAO.getDocType(
				X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory, // doc basetype
				getDocSubType(), // doc subtype
				warehouse.getAD_Client_ID(), // client
				warehouse.getAD_Org_ID() // org
		);
		return docType;
	}

	public HUInternalUseInventoryProducer addHUs(final Collection<I_M_HU> hus)
	{
		_hus.addAll(hus);
		return this;
	}

	private List<I_M_HU> getHUs()
	{
		if (_hus.isEmpty())
		{
			throw new AdempiereException("No HUs for internal use inventory");
		}
		return _hus;
	}
}
