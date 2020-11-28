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
import java.time.ZonedDateTime;

import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * It's an {@link IHUItemStorage} but on given product level.
 *
 * @author tsa
 *
 */
/* package */class HUItemProductStorage implements IProductStorage
{
	private final IHUItemStorage itemStorage;
	private final ProductId productId;
	private final I_C_UOM uom;
	private final ZonedDateTime date;

	public HUItemProductStorage(
			@NonNull final IHUItemStorage itemStorage,
			@NonNull final ProductId productId,
			@NonNull final I_C_UOM uom,
			@NonNull final ZonedDateTime date)
	{
		this.itemStorage = itemStorage;
		this.productId = productId;
		this.uom = uom;
		this.date = date;

	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "["
				+ "\nProduct: " + productId
				+ "\nQty: " + getQty()
				+ "\nCapacity: " + getTotalCapacity()
				+ "\nItem storage: " + itemStorage
				+ "]";

	}

	public Capacity getTotalCapacity()
	{
		return itemStorage.getCapacity(productId, uom, date);
	}

	@Override
	public ProductId getProductId()
	{
		return productId;
	}

	@Override
	public I_C_UOM getC_UOM()
	{
		return uom;
	}

	@Override
	public BigDecimal getQtyFree()
	{
		final Capacity capacityAvailable = itemStorage.getAvailableCapacity(getProductId(), getC_UOM(), date);
		if (capacityAvailable.isInfiniteCapacity())
		{
			return Quantity.QTY_INFINITE;
		}
		return capacityAvailable.toBigDecimal();
	}

	@Override
	public Quantity getQty()
	{
		return itemStorage.getQuantity(getProductId(), getC_UOM());
	}

	@Override
	public final Quantity getQty(final I_C_UOM uom)
	{
		final ProductId productId = getProductId();
		final Quantity qty = getQty();

		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final UOMConversionContext conversionCtx= UOMConversionContext.of(productId);
		return uomConversionBL.convertQuantityTo(qty, conversionCtx, uom);
	}

	@Override
	public BigDecimal getQtyCapacity()
	{
		final Capacity capacityTotal = getTotalCapacity();
		return capacityTotal.toBigDecimal();
	}

	@Override
	public IAllocationRequest addQty(final IAllocationRequest request)
	{
		return itemStorage.requestQtyToAllocate(request);
	}

	@Override
	public IAllocationRequest removeQty(final IAllocationRequest request)
	{
		return itemStorage.requestQtyToDeallocate(request);
	}

	@Override
	public void markStaled()
	{
		// nothing, so far, itemStorage is always database coupled, no in memory values
	}

	@Override
	public boolean isEmpty()
	{
		return itemStorage.isEmpty(getProductId());
	}

	@Override
	public boolean isAllowNegativeStorage()
	{
		return itemStorage.isAllowNegativeStorage();
	}
}
