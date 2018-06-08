package de.metas.handlingunits.storage.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHUCapacityBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.exceptions.HUInfiniteQtyAllocationException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Item_Storage;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.storage.IGenericHUStorage;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageDAO;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.quantity.Capacity;
import de.metas.quantity.CapacityInterface;
import de.metas.quantity.Quantity;
import lombok.NonNull;

public class HUItemStorage implements IHUItemStorage
{
	// services
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IHUCapacityBL capacityBL = Services.get(IHUCapacityBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	private final IHUStorageFactory storageFactory;
	private final IHUStorageDAO dao;

	private final I_M_HU_Item item;
	private final boolean virtualHUItem;
	private final boolean pureVirtualHUItem;
	private final boolean allowAddRemoveQty;

	/**
	 * Tells if invocations to this instance's {@link #requestNewHU()} and {@link #releaseHU(I_M_HU)} should be legal. This depends on the type of the wrapped {@link I_M_HU_Item}.
	 */
	private final boolean allowRequestReleaseIncludedHU;

	private final Map<Integer, Capacity> productId2customCapacity = new HashMap<>();

	/**
	 * Creates a new instance. Actual {@link I_M_HU_Item_Storage} records will be loaded and saved only when needed.
	 *
	 * @param storageFactory
	 * @param item
	 */
	public HUItemStorage(final IHUStorageFactory storageFactory, final I_M_HU_Item item)
	{
		Check.assumeNotNull(storageFactory, "storageFactory not null");
		this.storageFactory = storageFactory;

		dao = storageFactory.getHUStorageDAO();
		Check.assumeNotNull(dao, "dao not null");

		Check.assumeNotNull(item, "item not null");
		Check.assumeNotNull(item.getM_HU_Item_ID() > 0, "item is saved: {}", item);
		this.item = item;
		virtualHUItem = handlingUnitsBL.isVirtual(item);
		pureVirtualHUItem = handlingUnitsBL.isPureVirtual(item);

		final String itemType = handlingUnitsBL.getItemType(item);
		allowAddRemoveQty = X_M_HU_PI_Item.ITEMTYPE_Material.equals(itemType);
		allowRequestReleaseIncludedHU = X_M_HU_Item.ITEMTYPE_HandlingUnit.equals(itemType) || X_M_HU_Item.ITEMTYPE_HUAggregate.equals(itemType);
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "["
				+ "virtual=" + virtualHUItem
				+ ", item=" + item
				+ "]";
	}

	@Override
	public I_M_HU_Item getM_HU_Item()
	{
		return item;
	}

	@Override
	public IHUStorageFactory getHUStorageFactory()
	{
		return storageFactory;
	}

	private I_M_HU_Item_Storage getCreateStorageLine(final I_M_Product product, final I_C_UOM uom)
	{
		I_M_HU_Item_Storage storage = dao.retrieveItemStorage(item, product);
		if (storage == null)
		{
			storage = dao.newInstance(I_M_HU_Item_Storage.class, item);
			storage.setM_HU_Item_ID(item.getM_HU_Item_ID());
			storage.setM_Product(product);
			storage.setQty(BigDecimal.ZERO);
			storage.setC_UOM(uom);

			// don't save it; it will be saved after qty update
			// dao.saveStorageLine(storage);
		}
		return storage;
	}

	@Override
	public IHUStorage getParentStorage()
	{
		final I_M_HU hu = item.getM_HU();
		final IHUStorage parentStorage = storageFactory.getStorage(hu);
		return parentStorage;
	}

	@Override
	public void addQty(final I_M_Product product, final BigDecimal qty, final I_C_UOM uom)
	{
		// NOTE: we allow to add/remove qty even if HU Item is not of type Material
		// because rollupIncremental is updating the storage on all levels

		if (qty.signum() == 0)
		{
			return;
		}

		final I_M_HU_Item_Storage storageLine = getCreateStorageLine(product, uom);

		final I_C_UOM uomStorage = storageLine.getC_UOM();
		final BigDecimal qtyConv = uomConversionBL.convertQty(product, qty, uom, uomStorage);
		//
		// Update storage line
		final BigDecimal qtyOld = storageLine.getQty();
		final BigDecimal qtyNew = qtyOld.add(qtyConv);

		Check.errorIf(qtyNew.signum() < 0, "Attempt to set negative qty on storageLine; qty={}; qtyNew={}; qtyOld={}; this={}; storageLine={}", qty, qtyNew, qtyOld, this, storageLine);

		storageLine.setQty(qtyNew);
		dao.save(storageLine);

		//
		// Roll-up
		rollupIncremental(product, qtyConv, uomStorage);
	}

	private void rollupIncremental(final I_M_Product product, final BigDecimal qtyDelta, final I_C_UOM uom)
	{
		final IGenericHUStorage parentStorage = getParentStorage();
		if (parentStorage != null)
		{
			parentStorage.addQty(product, qtyDelta, uom);
		}
	}

	@Override
	public BigDecimal getQty(final I_M_Product product, final I_C_UOM uom)
	{
		final I_M_HU_Item_Storage storageLine = getCreateStorageLine(product, uom);

		final BigDecimal qty = storageLine.getQty();
		final BigDecimal qtyConv = uomConversionBL.convertQty(product, qty, storageLine.getC_UOM(), uom);

		return qtyConv;
	}

	@Override
	public boolean isVirtual()
	{
		return virtualHUItem;
	}

	@Override
	public boolean isPureVirtual()
	{
		return pureVirtualHUItem;
	}

	@Override
	public CapacityInterface getCapacity(
			@NonNull final I_M_Product product,
			@NonNull final I_C_UOM uom,
			@NonNull final Date date)
	{
		//
		// In case there is a custom capacity set, we use that right away
		final CapacityInterface customCapacity = getCustomCapacityOrNull(product, uom);
		if (customCapacity != null)
		{
			return customCapacity;
		}

		//
		// Get directly
		final CapacityInterface capacity = capacityBL.getCapacity(item, product, uom, date);
		return capacity;
	}

	private final CapacityInterface getCustomCapacityOrNull(final I_M_Product product, final I_C_UOM uom)
	{
		if (product == null)
		{
			return null;
		}

		final CapacityInterface customCapacity = productId2customCapacity.get(product.getM_Product_ID());
		if (customCapacity == null)
		{
			return null;
		}

		final CapacityInterface customCapacityConv = customCapacity.convertToUOM(uom);
		return customCapacityConv;
	}

	private final boolean hasCustomCapacityDefined(final I_M_Product product)
	{
		if (product == null)
		{
			return false;
		}

		final CapacityInterface customCapacity = productId2customCapacity.get(product.getM_Product_ID());
		return customCapacity != null;
	}

	@Override
	public void setCustomCapacity(final Capacity capacity)
	{
		final I_M_Product product = capacity.getM_Product();
		Check.assumeNotNull(product, "product not null");

		final int productId = product.getM_Product_ID();
		productId2customCapacity.put(productId, capacity);
	}

	@Override
	public CapacityInterface getAvailableCapacity(
			@NonNull final I_M_Product product,
			@NonNull final I_C_UOM uom,
			@NonNull final Date date)
	{
		final CapacityInterface capacity = getCapacity(product, uom, date);
		if (handlingUnitsBL.isAggregateHU(getM_HU_Item().getM_HU()))
		{
			// if this is an aggregate HU's item, then ignore the qty that was already added and ignore the item's full capacity
			// otherwise the allocation strategy might refuse to allocate more to this aggregate HU
			// fixes gh #1162!
			return capacity;
		}
		final BigDecimal qty = getQty(product, uom);

		final CapacityInterface capacityAvailable = capacity.subtractQuantity(Quantity.of(qty, uom));
		return capacityAvailable;
	}

	@Override
	public IAllocationRequest requestQtyToAllocate(final IAllocationRequest request)
	{
		assertQtyAllocationDeallocationAllowed();

		// Check: Qty shall be positive or zero
		final BigDecimal qtyRequired = request.getQty();
		Check.assume(qtyRequired.signum() >= 0, "qtyRequired({}) >= 0", qtyRequired);

		//
		// Zero Qty Request: return the request right away, there is nothing to do (optimization)
		if (qtyRequired.signum() == 0)
		{
			return request;
		}

		//
		// Force allocation: don't check if we have enough qty
		if (checkForceQtyAllocation(request))
		{
			return request;
		}

		final I_M_Product product = request.getProduct();
		final I_C_UOM uom = request.getC_UOM();
		final CapacityInterface availableCapacityDefinition = getAvailableCapacity(product, uom, request.getDate());

		//
		// Infinite capacity check
		if (availableCapacityDefinition.isInfiniteCapacity())
		{
			// Corner case: qty to allocate is infinite and capacity is also infinite => not allowed
			if (request.isInfiniteQty())
			{
				throw new HUInfiniteQtyAllocationException(request, this);
			}

			return request;
		}

		final BigDecimal capacityAvailable = availableCapacityDefinition.getCapacityQty();
		if (capacityAvailable.signum() == 0)
		{
			return AllocationUtils.createZeroQtyRequest(request);
		}

		if (capacityAvailable.compareTo(qtyRequired) >= 0)
		{
			return request;
		}
		else
		{
			return AllocationUtils.createQtyRequest(request, capacityAvailable);
		}
	}

	/**
	 * Checks if we shall do a force allocation (i.e. allocate as much as required)
	 *
	 * @param request
	 * @return true if force allocation shall be performed
	 */
	private final boolean checkForceQtyAllocation(final IAllocationRequest request)
	{
		// If not required, don't do it
		if (!request.isForceQtyAllocation())
		{
			return false;
		}

		// Corner case: we are asked to allocate the whole quantity, but whole qty is infinite => not allowed
		// (if happens, we have a bug somewhere)
		if (request.isInfiniteQty())
		{
			throw new HUInfiniteQtyAllocationException(request, this);
		}

		// If Custom capacity is enforced, then that rule is stronger than force allocation
		// e.g. use-case where is used: LU/TU producer is creating TUs enforcing this custom max capacity for CUs/TU... and we want to respect that rule.
		final I_M_Product product = request.getProduct();
		if (hasCustomCapacityDefined(product))
		{
			return false;
		}

		//
		// If we reach this point we are accepting force allocation
		return true;
	}

	@Override
	public IAllocationRequest requestQtyToDeallocate(final IAllocationRequest request)
	{
		assertQtyAllocationDeallocationAllowed();

		final BigDecimal qtyRequired = request.getQty();
		if (qtyRequired.signum() == 0)
		{
			return request;
		}

		// TODO: handle the case of allowing negative storage
		// TODO: handle the case then request has infinite Qty and the storage has infinite capacity
		// final boolean allowNegativeStorage = true;
		// if (allowNegativeStorage)
		// {
		// return request;
		// }

		Check.assume(qtyRequired.signum() > 0, "qtyRequired({}) > 0", qtyRequired);

		final BigDecimal qtyAvailable = getQty(request.getProduct(), request.getC_UOM());

		if (qtyAvailable.compareTo(qtyRequired) >= 0)
		{
			return request;
		}
		else
		{
			return AllocationUtils.createQtyRequest(request, qtyAvailable);
		}
	}

	/**
	 * Makes sure that we are allowed to allocate/deallocate quantity on this storage
	 */
	private final void assertQtyAllocationDeallocationAllowed()
	{
		// NOTE: we need to allow the BL to compute actual qty to allocate on a regular non-virtual HU Item
		// Check.assume(isVirtual(), "M_HU_Item needs to be virtual in order to be able to allocate/deallocate");
		Check.assume(allowAddRemoveQty, "Add/Remove Qty shall be allowed for {}", this);
	}

	/**
	 * Returns always false because negative storages are not supported (see {@link #requestQtyToDeallocate(IAllocationRequest)}).
	 *
	 * @return false
	 */
	@Override
	public final boolean isAllowNegativeStorage()
	{
		return false;
	}

	@Override
	public boolean requestNewHU()
	{
		Check.assume(allowRequestReleaseIncludedHU, "Requesting/Releasing new HU shall be allowed for {}", this);

		if (X_M_HU_Item.ITEMTYPE_HUAggregate.equals(item.getItemType()))
		{
			// a "HUAggregate" item can have one (virtual) child HU
			return getHUCount() < 1;
		}

		// for other items, check out the available capacity to decide
		final int count = getHUCount();
		final int max = getHUCapacity();

		if (count >= max)
		{
			return false;
		}

		incrementHUCount();
		return true;
	}

	@Override
	public boolean releaseHU(final I_M_HU hu)
	{
		Check.assume(allowRequestReleaseIncludedHU, "Requesting/Releasing new HU shall be allowed for {}", this);

		final int count = getHUCount();

		if (count <= 0)
		{
			return false;
		}

		decrementHUCount();
		return true;
	}

	private void incrementHUCount()
	{
		final BigDecimal count = item.getQty();
		final BigDecimal countNew = count.add(BigDecimal.ONE);
		item.setQty(countNew);
		dao.save(item);
	}

	private void decrementHUCount()
	{
		final BigDecimal count = item.getQty();
		final BigDecimal countNew = count.subtract(BigDecimal.ONE);
		item.setQty(countNew);
		dao.save(item);
	}

	@Override
	public int getHUCount()
	{
		final int count = item.getQty().intValueExact();
		return count;
	}

	@Override
	public int getHUCapacity()
	{
		if (X_M_HU_Item.ITEMTYPE_HUAggregate.equals(item.getItemType()))
		{
			return Quantity.QTY_INFINITE.intValue();
		}
		return Services.get(IHandlingUnitsBL.class).getPIItem(item).getQty().intValueExact();
	}

	@Override
	public IProductStorage getProductStorage(final I_M_Product product, final I_C_UOM uom, final Date date)
	{
		return new HUItemProductStorage(this, product, uom, date);
	}

	@Override
	public List<IProductStorage> getProductStorages(final Date date)
	{
		final List<I_M_HU_Item_Storage> storages = dao.retrieveItemStorages(item);
		final List<IProductStorage> result = new ArrayList<>(storages.size());
		for (final I_M_HU_Item_Storage storage : storages)
		{
			final I_M_Product product = storage.getM_Product();
			final I_C_UOM uom = storage.getC_UOM();
			final HUItemProductStorage productStorage = new HUItemProductStorage(this, product, uom, date);
			result.add(productStorage);
		}

		return result;
	}

	@Override
	public boolean isEmpty()
	{
		final List<I_M_HU_Item_Storage> storages = dao.retrieveItemStorages(item);
		for (final I_M_HU_Item_Storage storage : storages)
		{
			if (!isEmpty(storage))
			{
				return false;
			}
		}

		return true;
	}

	private boolean isEmpty(final I_M_HU_Item_Storage storage)
	{
		final BigDecimal qty = storage.getQty();
		if (qty.signum() != 0)
		{
			return false;
		}

		return true;
	}

	@Override
	public boolean isEmpty(final I_M_Product product)
	{
		final I_M_HU_Item_Storage storage = dao.retrieveItemStorage(item, product);
		if (storage == null)
		{
			return true;
		}

		return isEmpty(storage);
	}
}
