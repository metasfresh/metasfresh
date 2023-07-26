/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.handlingunits.inventory.internaluse;

import com.google.common.collect.ImmutableListMultimap;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsBL.TopLevelHusQuery;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Inventory;
import de.metas.inventory.InventoryDocSubType;
import de.metas.inventory.event.InventoryUserNotificationsProducer;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates and processes Internal Use Inventory documents to destroy given HUs.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class HUInternalUseInventoryProducer
{
	// services
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);

	//
	// Parameters
	@NonNull
	private final HUInternalUseInventoryCreateRequest request;

	public HUInternalUseInventoryProducer(@NonNull final HUInternalUseInventoryCreateRequest request)
	{
		this.request = request;
	}

	public HUInternalUseInventoryCreateResponse execute()
	{
		final ImmutableListMultimap<WarehouseId, I_M_HU> topLevelHUsByWarehouseId = getTopLevelHUs()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						IHandlingUnitsBL::extractWarehouseId, // we asserted earlier that each HU has a locator
						hu -> hu));
		if (topLevelHUsByWarehouseId.isEmpty())
		{
			throw new AdempiereException("No top level HUs to consume");
		}

		final List<I_M_Inventory> allCreatedInventories = new ArrayList<>();
		for (final WarehouseId warehouseId : topLevelHUsByWarehouseId.keySet())
		{
			final List<I_M_HU> hus = topLevelHUsByWarehouseId.get(warehouseId);
			final List<I_M_Inventory> inventories = createInventories(warehouseId, hus);
			allCreatedInventories.addAll(inventories);
		}

		//
		// Send notifications
		if (request.isSendNotifications())
		{
			InventoryUserNotificationsProducer.newInstance()
					.notifyGenerated(allCreatedInventories);
		}

		return HUInternalUseInventoryCreateResponse.builder()
				.inventories(allCreatedInventories)
				.build();
	}

	private List<I_M_Inventory> createInventories(
			final WarehouseId warehouseId,
			final List<I_M_HU> hus)
	{
		final I_M_Warehouse warehouse = warehouseDAO.getById(warehouseId);

		// Make sure all HUs have ThreadInherited transaction (in order to use caching)
		InterfaceWrapperHelper.setThreadInheritedTrxName(hus);

		//
		// Allocation Source: our HUs
		final HUListAllocationSourceDestination husSource = HUListAllocationSourceDestination.of(hus);

		husSource.setCreateHUSnapshots(true);

		//
		// Create and setup context
		final IMutableHUContext huContext = huContextFactory.createMutableHUContextForProcessing(PlainContextAware.newWithThreadInheritedTrx());

		huContext.setDate(request.getMovementDate());
		huContext.getHUPackingMaterialsCollector()
				.disable(); // we assume the inventory destination will do that

		// Inventory allocation destination
		final DocTypeId materialDisposalDocTypeId = getInventoryDocTypeId(warehouse);
		final InventoryAllocationDestination inventoryAllocationDestination = new InventoryAllocationDestination(
				warehouseId,
				materialDisposalDocTypeId,
				request.getActivityId(),
				request.getDescription());

		//
		// Create and configure Loader
		final HULoader loader = HULoader
				.of(husSource, inventoryAllocationDestination)
				.setAllowPartialLoads(true);

		//
		// Unload everything from source (our HUs)
		loader.unloadAllFromSource(huContext);

		//
		final List<I_M_Inventory> inventories = inventoryAllocationDestination.processInventories(request.isCompleteInventory());

		if (request.isMoveEmptiesToEmptiesWarehouse())
		{
			inventoryAllocationDestination.createEmptiesMovementForInventories();
		}

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

		return inventories;
	}

	private DocTypeId getInventoryDocTypeId(@NonNull final I_M_Warehouse warehouse)
	{
		if (request.getInternalUseInventoryDocTypeId() != null)
		{
			return request.getInternalUseInventoryDocTypeId();
		}

		return docTypeDAO.getDocTypeId(
				DocTypeQuery.builder()
						.docBaseType(X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory)
						.docSubType(InventoryDocSubType.InternalUseInventory.toDocSubTypeString())
						.adClientId(warehouse.getAD_Client_ID())
						.adOrgId(warehouse.getAD_Org_ID())
						.build());
	}

	private List<I_M_HU> getTopLevelHUs()
	{
		final List<I_M_HU> hus = request.getHus();
		if (hus.isEmpty())
		{
			throw new AdempiereException("No HUs for internal use inventory");
		}

		return handlingUnitsBL.getTopLevelHUs(TopLevelHusQuery.builder()
													  .hus(hus)
													  .includeAll(false)
													  .build());
	}
}
