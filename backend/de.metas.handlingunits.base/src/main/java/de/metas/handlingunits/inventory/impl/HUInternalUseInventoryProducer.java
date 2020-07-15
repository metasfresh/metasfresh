package de.metas.handlingunits.inventory.impl;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;

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
import de.metas.product.acct.api.ActivityId;
import de.metas.util.Check;
import de.metas.util.Services;
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
	private ZonedDateTime _movementDate;
	private String _docSubType = X_C_DocType.DOCSUBTYPE_InternalUseInventory;
	private final List<I_M_HU> _hus = new ArrayList<>();

	private ActivityId activityId;
	private String description;
	private boolean isCompleteInventory;

	private boolean isCreateMovement;

	private HUInternalUseInventoryProducer()
	{
	}

	public List<I_M_Inventory> createInventories()
	{
		final Map<WarehouseId, List<I_M_HU>> topLevelHUsByWarehouseId = getTopLevelHUs()
				.stream()
				.collect(Collectors.groupingBy(this::extractWarehouseId)); // we asserted earlier that each HU has a locator

		final List<I_M_Inventory> result = new ArrayList<>();
		for (final Map.Entry<WarehouseId, List<I_M_HU>> warehouseIdAndHUs : topLevelHUsByWarehouseId.entrySet())
		{
			final WarehouseId warehouseId = warehouseIdAndHUs.getKey();
			final List<I_M_HU> hus = warehouseIdAndHUs.getValue();
			final List<I_M_Inventory> inventories = createInventories(warehouseId, hus, activityId, description, isCompleteInventory, isCreateMovement);
			result.addAll(inventories);
		}

		return result;
	}
	
	private WarehouseId extractWarehouseId(final I_M_HU hu)
	{
		final int locatorRepoId = hu.getM_Locator_ID();
		return Services.get(IWarehouseDAO.class).getWarehouseIdByLocatorRepoId(locatorRepoId);
	}

	private final List<I_M_Inventory> createInventories(
			final WarehouseId warehouseId,
			final List<I_M_HU> hus,
			final ActivityId activityId,
			final String description,
			final boolean isCompleteInventory,
			final boolean isCreateMovement)
	{
		final I_M_Warehouse warehouse = Services.get(IWarehouseDAO.class).getById(warehouseId);

		// Make sure all HUs have ThreadInherited transaction (in order to use caching)
		InterfaceWrapperHelper.setThreadInheritedTrxName(hus);

		//
		// Allocation Source: our HUs
		final HUListAllocationSourceDestination husSource = HUListAllocationSourceDestination.of(hus);

		husSource.setCreateHUSnapshots(true);

		//
		// Create and setup context
		final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
		final IMutableHUContext huContext = huContextFactory.createMutableHUContextForProcessing(PlainContextAware.newWithThreadInheritedTrx());

		huContext.setDate(getMovementDate());
		huContext.getHUPackingMaterialsCollector()
				.disable(); // we assume the inventory destination will do that

		// Inventory allocation destination
		final DocTypeId materialDisposalDocTypeId = getInventoryDocTypeId(warehouse);
		final InventoryAllocationDestination inventoryAllocationDestination = new InventoryAllocationDestination(
				warehouseId,
				materialDisposalDocTypeId,
				activityId,
				description);

		//
		// Create and configure Loader
		final HULoader loader = HULoader
				.of(husSource, inventoryAllocationDestination)
				.setAllowPartialLoads(true);

		//
		// Unload everything from source (our HUs)
		loader.unloadAllFromSource(huContext);

		//
		final List<I_M_Inventory> inventories = inventoryAllocationDestination.processInventories(isCompleteInventory);

		if (isCreateMovement)
		{
			inventoryAllocationDestination.createMovementsForInventories();
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

	public HUInternalUseInventoryProducer setMovementDate(@NonNull final ZonedDateTime movementDate)
	{
		_movementDate = movementDate;
		return this;
	}

	private ZonedDateTime getMovementDate()
	{
		if (_movementDate == null)
		{
			_movementDate = Env.getZonedDateTime();
		}
		return _movementDate;
	}

	public ActivityId getActivityId()
	{
		return activityId;
	}

	public HUInternalUseInventoryProducer setActivityId(ActivityId activityId)
	{
		this.activityId = activityId;

		return this;
	}

	public boolean isCompleteInventory()
	{
		return isCompleteInventory;
	}

	public HUInternalUseInventoryProducer setIsCompleteInventory(boolean isCompleteInventory)
	{
		this.isCompleteInventory = isCompleteInventory;

		return this;
	}

	public boolean isCreateMovement()
	{
		return isCreateMovement;
	}

	public HUInternalUseInventoryProducer setIsCreateMovement(boolean isCreateMovement)
	{
		this.isCreateMovement = isCreateMovement;

		return this;
	}

	public String getDescription()
	{
		return description;
	}

	public HUInternalUseInventoryProducer setDescription(String description)
	{
		this.description = description;

		return this;
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

	private DocTypeId getInventoryDocTypeId(@NonNull final I_M_Warehouse warehouse)
	{
		final DocTypeQuery query = DocTypeQuery.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory)
				.docSubType(getDocSubType())
				.adClientId(warehouse.getAD_Client_ID())
				.adOrgId(warehouse.getAD_Org_ID())
				.build();
		return docTypeDAO.getDocTypeId(query);
	}

	/**
	 * Add the HUs to be disposed.
	 *
	 * @param hus may be empty but not null.
	 *            This class takes care of making sure that only the top level HUs are processed to avoid issue <a href="https://github.com/metasfresh/metasfresh-webui-api/issues/578">metasfresh/metasfresh-webui-api#578</a>.
	 *            Included lower-level HUs are processed recursively.
	 * @return
	 */
	public HUInternalUseInventoryProducer addHUs(@NonNull final Collection<I_M_HU> hus)
	{
		hus.forEach(this::addHU);
		return this;
	}

	public HUInternalUseInventoryProducer addHU(@NonNull final I_M_HU hu)
	{
		Check.assume(hu.getM_Locator_ID() > 0, "HU needs to have a locator: {}", hu);

		_hus.add(hu);
		return this;
	}

	private List<I_M_HU> getTopLevelHUs()
	{
		if (_hus.isEmpty())
		{
			throw new AdempiereException("No HUs for internal use inventory");
		}
		final TopLevelHusQuery query = TopLevelHusQuery.builder()
				.hus(_hus)
				.includeAll(false)
				.build();
		return handlingUnitsBL.getTopLevelHUs(query);
	}

}
