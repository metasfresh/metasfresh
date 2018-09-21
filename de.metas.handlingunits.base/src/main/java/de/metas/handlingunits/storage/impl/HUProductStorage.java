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
import org.adempiere.uom.api.IUOMConversionBL;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.quantity.Capacity;
import de.metas.quantity.CapacityInterface;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Read-only HU Product Storage based on {@link IHUStorage} and a particular product.
 *
 * @author tsa
 *
 */
/* package */class HUProductStorage implements IHUProductStorage
{
	private final IHUStorage huStorage;
	private final I_M_Product product;
	private final I_C_UOM uom;
	private final CapacityInterface capacityTotal;

	public HUProductStorage(final IHUStorage huStorage,
			final I_M_Product product,
			final I_C_UOM uom)
	{
		super();

		Check.assumeNotNull(huStorage, "huStorage not null");
		this.huStorage = huStorage;

		Check.assumeNotNull(product, "product not null");
		this.product = product;

		Check.assumeNotNull(uom, "uom not null");
		this.uom = uom;

		// NOTE: we are creating infinite capacity because we cannot determine the capacity at HU Storage Level
		// Capacity is defined only on HU_Item_Storage level.
		capacityTotal = Capacity.createInfiniteCapacity(product, uom);
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "["
				+ "\nProduct: " + product.getName()
				+ "\nQty: " + getQty()
				+ "\nCapacity: " + capacityTotal
				+ "\nItem storage: " + huStorage
				+ "]";

	}

	@Override
	public I_M_Product getM_Product()
	{
		return product;
	}

	@Override
	public I_C_UOM getC_UOM()
	{
		return uom;
	}

	@Override
	public BigDecimal getQtyFree()
	{
		final CapacityInterface capacityAvailable = capacityTotal.subtractQuantity(Quantity.of(getQty(), getC_UOM()));
		if (capacityAvailable.isInfiniteCapacity())
		{
			return Quantity.QTY_INFINITE;
		}
		return capacityAvailable.getCapacityQty();
	}

	@Override
	public BigDecimal getQty()
	{
		final BigDecimal qty = huStorage.getQty(getM_Product(), getC_UOM());
		return qty;
	}

	@Override
	public final Quantity getQty(final I_C_UOM uom)
	{
		final I_M_Product product = getM_Product();
		final BigDecimal qty = getQty();
		final I_C_UOM uomFrom = getC_UOM();

		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final BigDecimal convertQty = uomConversionBL.convertQty(product.getM_Product_ID(), qty, uomFrom, uom);
		return Quantity.of(convertQty, uom);
	}

	@Override
	public final BigDecimal getQtyInStockingUOM()
	{
		final I_M_Product product = getM_Product();
		final I_C_UOM uom = product.getC_UOM();
		return getQty(uom).getQty();
	}

	@Override
	public BigDecimal getQtyCapacity()
	{
		return capacityTotal.getCapacityQty();
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
		return huStorage.isEmpty(getM_Product());
	}

	@Override
	public I_M_HU getM_HU()
	{
		return huStorage.getM_HU();
	}
}
