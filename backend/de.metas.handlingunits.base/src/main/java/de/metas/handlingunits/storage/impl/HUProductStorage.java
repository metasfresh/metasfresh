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

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Read-only HU Product Storage based on {@link IHUStorage} and a particular product.
 *
 * @author tsa
 *
 */
/* package */class HUProductStorage implements IHUProductStorage
{
	private final IHUStorage huStorage;
	private final ProductId productId;
	private final I_C_UOM uom;
	private final Capacity capacityTotal;

	public HUProductStorage(
			@NonNull final IHUStorage huStorage,
			@NonNull final ProductId productId,
			@NonNull final I_C_UOM uom)
	{
		this.huStorage = huStorage;
		this.productId = productId;
		this.uom = uom;

		// NOTE: we are creating infinite capacity because we cannot determine the capacity at HU Storage Level
		// Capacity is defined only on HU_Item_Storage level.
		capacityTotal = Capacity.createInfiniteCapacity(productId, uom);
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "["
				+ "\nProduct: " + productId
				+ "\nQty: " + getQty()
				+ "\nCapacity: " + capacityTotal
				+ "\nItem storage: " + huStorage
				+ "]";

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
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final Capacity capacityAvailable = capacityTotal.subtractQuantity(getQty(), uomConversionBL);
		if (capacityAvailable.isInfiniteCapacity())
		{
			return Quantity.QTY_INFINITE;
		}
		return capacityAvailable.toBigDecimal();
	}

	@Override
	public Quantity getQty()
	{
		return huStorage.getQuantity(getProductId(), getC_UOM());
	}

	@Override
	public final Quantity getQty(final I_C_UOM uom)
	{
		final UOMConversionContext conversionCtx = UOMConversionContext.of(getProductId());

		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		return uomConversionBL.convertQuantityTo(getQty(), conversionCtx, uom);
	}

	@Override
	public final Quantity getQtyInStockingUOM()
	{
		final I_C_UOM productUOM = Services.get(IProductBL.class).getStockUOM(getProductId());
		return getQty(productUOM);
	}

	@Override
	public BigDecimal getQtyCapacity()
	{
		return capacityTotal.toBigDecimal();
	}

	@Override
	public IAllocationRequest addQty(final IAllocationRequest request)
	{
		throw new AdempiereException("Adding Qty is not supported on this level");
	}

	@Override
	public IAllocationRequest removeQty(final IAllocationRequest request)
	{
		throw new AdempiereException("Removing Qty is not supported on this level");
	}

	/**
	 * Returns always false because negative storages are not supported (see {@link #removeQty(IAllocationRequest)})
	 *
	 * @return false
	 */
	@Override
	public boolean isAllowNegativeStorage()
	{
		return false;
	}

	@Override
	public void markStaled()
	{
		// nothing, so far, itemStorage is always database coupled, no in memory values
	}

	@Override
	public boolean isEmpty()
	{
		return huStorage.isEmpty(getProductId());
	}

	@Override
	public I_M_HU getM_HU()
	{
		return huStorage.getM_HU();
	}
}
