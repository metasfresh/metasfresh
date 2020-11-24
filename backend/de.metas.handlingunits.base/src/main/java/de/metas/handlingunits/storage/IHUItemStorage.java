package de.metas.handlingunits.storage;

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
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Item_Storage;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;

/**
 * HU Item Storage
 *
 * NOTE:
 * <ul>
 * <li>{@link #requestQtyToAllocate(IAllocationRequest)} and {@link #requestQtyToDeallocate(IAllocationRequest)} are allowed only on virtual HU items because only on those we allocate/deallocate
 * </ul>
 *
 * @author tsa
 *
 */
public interface IHUItemStorage extends IGenericHUStorage
{
	@Override
	IHUStorage getParentStorage();

	/**
	 * Retrieve or create the underlying {@link I_M_HU_Item_Storage} and add the given quantity to it.
	 */
	@Override
	void addQty(ProductId productId, BigDecimal qty, I_C_UOM uom);

	/**
	 * Retrieve the underlying {@link I_M_HU_Item_Storage} (if there is any) and return its quantity (or zero).
	 */
	@Override
	BigDecimal getQty(ProductId productId, I_C_UOM uom);

	@Override
	boolean isEmpty();

	@Override
	boolean isEmpty(ProductId productId);

	I_M_HU_Item getM_HU_Item();

	/**
	 * Gets total capacity.
	 *
	 * If a custom capacity is set (see {@link #setCustomCapacity(Capacity)}), that one will be considered first.
	 */
	Capacity getCapacity(ProductId productId, I_C_UOM uom, ZonedDateTime date);

	/**
	 * Override current total capacity settings
	 *
	 * @see #getCapacity(ProductId, I_C_UOM, Date)
	 */
	void setCustomCapacity(Capacity capacity);

	/**
	 * @return available capacity (i.e. how much is free)
	 */
	Capacity getAvailableCapacity(ProductId productId, I_C_UOM uom, ZonedDateTime date);

	/**
	 * @return the given <code>request</code>, if <code>this</code> storage instance is big enough for it.
	 *         IF the requested quantity exceeds this storage's capacity, then return a new "smaller" request.
	 * 
	 * @see #getAvailableCapacity(ProductId, I_C_UOM, Date)
	 */
	IAllocationRequest requestQtyToAllocate(IAllocationRequest request);

	/**
	 * Similar to {@link #requestQtyToAllocate(IAllocationRequest)},
	 * but the returned request's quantity does not depend on the capacity of a destination storage,
	 * but one the actual contend of a source storage.
	 * 
	 * @see #getQty(ProductId, I_C_UOM)
	 */
	IAllocationRequest requestQtyToDeallocate(IAllocationRequest request);

	/**
	 * Decides if a new child-HU can be created and attached to this instance's {@link I_M_HU_Item}. This usually depends on the capacity of the items own "parent" HU.
	 * If another HU can be created, then also this instance's {@code huCount} as returned by {@link #getHUCount()} is increased.
	 * 
	 * @return {@code true} if a new HU can be created.
	 */
	boolean requestNewHU();

	boolean releaseHU(I_M_HU hu);

	int getHUCount();

	int getHUCapacity();

	IProductStorage getProductStorage(ProductId productId, I_C_UOM uom, ZonedDateTime date);

	List<IProductStorage> getProductStorages(ZonedDateTime date);

	/**
	 * @return true if this storage allows negative storages
	 */
	boolean isAllowNegativeStorage();

	/**
	 * @return true if the item is part of a virtual HU.
	 */
	@Override
	boolean isVirtual();

	/**
	 * @return true if the item is pure virtual (i.e. {@link #isVirtual()} and the HU is linked to a material item)
	 * @see IHandlingUnitsBL#isPureVirtual(I_M_HU_Item)
	 */
	boolean isPureVirtual();
}
