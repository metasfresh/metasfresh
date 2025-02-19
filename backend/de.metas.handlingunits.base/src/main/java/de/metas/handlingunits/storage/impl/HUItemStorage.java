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
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMPrecision;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_UOM;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

	private final Map<ProductId, Capacity> productId2customCapacity = new HashMap<>();

	/**
	 * Creates a new instance. Actual {@link I_M_HU_Item_Storage} records will be loaded and saved only when needed.
	 *
	 * @param storageFactory
	 * @param item
	 */
	public HUItemStorage(
			@NonNull final IHUStorageFactory storageFactory,
			@NonNull final I_M_HU_Item item)
	{
		this.storageFactory = storageFactory;

		dao = storageFactory.getHUStorageDAO();
		Check.assumeNotNull(dao, "dao not null");

		Check.assume(item.getM_HU_Item_ID() > 0, "item is saved: {}", item);
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

	private I_M_HU_Item_Storage getCreateStorageLine(@NonNull final ProductId productId, @NonNull final I_C_UOM uom)
	{
		I_M_HU_Item_Storage storage = dao.retrieveItemStorage(item, productId);
		if (storage == null)
		{
			storage = dao.newInstance(I_M_HU_Item_Storage.class, item);
			storage.setM_HU_Item_ID(item.getM_HU_Item_ID());
			storage.setM_Product_ID(productId.getRepoId());
			storage.setQty(BigDecimal.ZERO);
			storage.setC_UOM_ID(uom.getC_UOM_ID());

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
	public void addQty(@NonNull final ProductId productId, @NonNull final BigDecimal qtyToAdd, @NonNull final I_C_UOM uom)
	{
		// NOTE: we allow to add/remove qty even if HU Item is not of type Material
		// because rollupIncremental is updating the storage on all levels

		if (qtyToAdd.signum() == 0)
		{
			return;
		}

		final I_M_HU_Item_Storage storageLine = getCreateStorageLine(productId, uom);

		final I_C_UOM uomStorage = extractUOM(storageLine);
		final BigDecimal qtyConv = uomConversionBL.convertQty(productId, qtyToAdd, uom, uomStorage);
		
		// Avoid failing if we have qtyOld=15.3035 (despite uomStorage-precision=3) and qtyConv=15.304
		final UOMPrecision uomPrecision = UOMPrecision.ofInt(uomStorage.getStdPrecision());
		final BigDecimal qtyOld = storageLine.getQty().setScale(uomPrecision.toInt(), uomPrecision.getRoundingMode());
		
		// Update storage line
		BigDecimal qtyNew = qtyOld.add(qtyConv);

		final BigDecimal qtyOnParent = getParentStorage().getQty(productId, uom);

		if (qtyNew.signum() < 0 && !qtyOld.equals(qtyOnParent))
		{
			Loggables.addLog("Warning! M_HU_Item_Storage.Qty out of sync with M_HU_Storage! "
									 + "M_HU_Item_Id: {}, M_HU_Item_Storage.Qty: {}, M_HU_Item_Storage.Product: {}, M_HU_Item_Storage.UOM: {}"
									 + "M_HU_ID: {}, M_HU_Storage.Qty: {}, qtyToAdd: {} same UOM",
							 storageLine.getM_HU_Item_ID(), storageLine.getQty(), storageLine.getM_Product_ID(), storageLine.getC_UOM_ID(),
							 item.getM_HU_ID(), qtyOnParent, qtyConv);

			qtyNew = qtyOnParent.add(qtyConv);
		}

		Check.errorIf(qtyNew.signum() < 0, "Attempt to set negative qty on storageLine; qtyOld={}; qtyToAdd={}; qtyNew={}; this={}; storageLine={}", qtyOld, qtyToAdd, qtyNew, this, storageLine);

		storageLine.setQty(qtyNew);
		dao.save(storageLine);

		//
		// Roll-up
		rollupIncremental(productId, qtyConv, uomStorage);
	}

	private void rollupIncremental(final ProductId productId, final BigDecimal qtyDelta, final I_C_UOM uom)
	{
		final IGenericHUStorage parentStorage = getParentStorage();
		if (parentStorage != null)
		{
			parentStorage.addQty(productId, qtyDelta, uom);
		}
	}

	@Override
	public BigDecimal getQty(final ProductId productId, final I_C_UOM uom)
	{
		final I_M_HU_Item_Storage storageLine = getCreateStorageLine(productId, uom);

		final BigDecimal qty = storageLine.getQty();
		final BigDecimal qtyConv = uomConversionBL.convertQty(productId, qty, extractUOM(storageLine), uom);

		return qtyConv;
	}

	@Override
	public Optional<Quantity> getQuantity(final ProductId productId)
	{
		final I_M_HU_Item_Storage storage = dao.retrieveItemStorage(item, productId);
		return storage != null
				? Optional.of(Quantitys.of(storage.getQty(), UomId.ofRepoId(storage.getC_UOM_ID())))
				: Optional.empty();
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
	public Capacity getCapacity(
			@NonNull final ProductId productId,
			@NonNull final I_C_UOM uom,
			@NonNull final ZonedDateTime date)
	{
		//
		// In case there is a custom capacity set, we use that right away
		final Capacity customCapacity = getCustomCapacityOrNull(productId, uom);
		if (customCapacity != null)
		{
			return customCapacity;
		}

		//
		// Get directly
		final Capacity capacity = capacityBL.getCapacity(item, productId, uom, date);
		return capacity;
	}

	private final Capacity getCustomCapacityOrNull(final ProductId productId, final I_C_UOM uom)
	{
		if (productId == null)
		{
			return null;
		}

		final Capacity customCapacity = productId2customCapacity.get(productId);
		if (customCapacity == null)
		{
			return null;
		}

		return customCapacity.convertToUOM(uom, uomConversionBL);
	}

	private final boolean hasCustomCapacityDefined(final ProductId productId)
	{
		if (productId == null)
		{
			return false;
		}

		final Capacity customCapacity = productId2customCapacity.get(productId);
		return customCapacity != null;
	}

	@Override
	public void setCustomCapacity(final Capacity capacity)
	{
		final ProductId productId = capacity.getProductId();
		Check.assumeNotNull(productId, "productId not null");

		productId2customCapacity.put(productId, capacity);
	}

	@Override
	public Capacity getAvailableCapacity(
			@NonNull final ProductId productId,
			@NonNull final I_C_UOM uom,
			@NonNull final ZonedDateTime date)
	{
		final Capacity capacity = getCapacity(productId, uom, date);
		if (handlingUnitsBL.isAggregateHU(getM_HU_Item().getM_HU()))
		{
			// if this is an aggregate HU's item, then ignore the qty that was already added and ignore the item's full capacity
			// otherwise the allocation strategy might refuse to allocate more to this aggregate HU
			// fixes gh #1162!
			return capacity;
		}
		final BigDecimal qty = getQty(productId, uom);

		final Capacity capacityAvailable = capacity.subtractQuantity(Quantity.of(qty, uom), uomConversionBL);
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

		final ProductId productId = request.getProductId();
		final I_C_UOM uom = request.getC_UOM();
		final Capacity availableCapacityDefinition = getAvailableCapacity(productId, uom, request.getDate());

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

		final BigDecimal capacityAvailable = availableCapacityDefinition.toBigDecimal();
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
		final ProductId productId = request.getProductId();
		if (hasCustomCapacityDefined(productId))
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

		final BigDecimal qtyAvailable = getQty(request.getProductId(), request.getC_UOM());

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
	public IProductStorage getProductStorage(final ProductId productId, final I_C_UOM uom, final ZonedDateTime date)
	{
		return new HUItemProductStorage(this, productId, uom, date);
	}

	@Override
	public List<IProductStorage> getProductStorages(final ZonedDateTime date)
	{
		final List<I_M_HU_Item_Storage> storages = dao.retrieveItemStorages(item);
		final List<IProductStorage> result = new ArrayList<>(storages.size());
		for (final I_M_HU_Item_Storage storage : storages)
		{
			final ProductId productId = ProductId.ofRepoId(storage.getM_Product_ID());
			final I_C_UOM uom = extractUOM(storage);
			final HUItemProductStorage productStorage = new HUItemProductStorage(this, productId, uom, date);
			result.add(productStorage);
		}

		return result;
	}

	private I_C_UOM extractUOM(final I_M_HU_Item_Storage storage)
	{
		return Services.get(IUOMDAO.class).getById(storage.getC_UOM_ID());
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
	public boolean isEmpty(final ProductId productId)
	{
		final I_M_HU_Item_Storage storage = dao.retrieveItemStorage(item, productId);
		if (storage == null)
		{
			return true;
		}

		return isEmpty(storage);
	}
}
