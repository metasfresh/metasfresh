package de.metas.handlingunits.impl;

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
import java.util.Date;

import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHUCapacityBL;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.quantity.Capacity;
import de.metas.quantity.CapacityInterface;
import lombok.NonNull;

public class HUCapacityBL implements IHUCapacityBL
{

	@Override
	public Capacity getCapacity(final I_M_HU_PI_Item_Product itemDefProduct, final I_M_Product product, final I_C_UOM uom)
	{
		final I_M_Product productToUse;
		if (itemDefProduct.isAllowAnyProduct())
		{
			Check.assumeNotNull(product, "product not null");
			productToUse = product;
		}
		else
		{
			final I_M_Product piipProduct = itemDefProduct.getM_Product();
			if (product == null)
			{
				productToUse = piipProduct;
				if (productToUse == null || productToUse.getM_Product_ID() <= 0)
				{
					// Case: product was not found in PI_Item_Product nor was given was parameter
					throw new HUException("@NotFound@ @M_Product_ID@: " + itemDefProduct);
				}
			}
			else
			{
				final int itemDefProduct_ProductId = itemDefProduct.getM_Product_ID();
				if (itemDefProduct_ProductId > 0 && itemDefProduct_ProductId != product.getM_Product_ID())
				{
					throw new HUException("CU-TU assignment "
							+ "\n@M_HU_PI_Item_Product_ID@: " + itemDefProduct.getDescription() + " (" + I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID + "=" + itemDefProduct.getM_HU_PI_Item_Product_ID() + ") "
							+ "\n@M_HU_PI_Item_Product_ID@ - @M_Product_ID@: " + piipProduct.getValue() + " (" + piipProduct.getName() + ") "
							+ "\nis not compatible with required product @M_Product_ID@: " + product.getValue() + "_" + product.getName() + "( " + I_M_Product.COLUMNNAME_M_Product_ID + "=" + product.getM_Product_ID() + ")");
				}
				productToUse = product;
			}
		}

		Check.assumeNotNull(productToUse, "productToUse not null");

		final boolean infiniteCapacity = isInfiniteCapacity(itemDefProduct);
		if (infiniteCapacity)
		{
			return Capacity.createInfiniteCapacity(productToUse, uom);
		}

		final BigDecimal qty = itemDefProduct.getQty();
		final BigDecimal qtyConv = Services.get(IUOMConversionBL.class)
				.convertQty(productToUse, qty, itemDefProduct.getC_UOM(), uom);

		final boolean allowNegativeCapacity = false;

		return Capacity.createCapacity(qtyConv, productToUse, uom, allowNegativeCapacity);
	}

	@Override
	public CapacityInterface getCapacity(
			@NonNull final I_M_HU_Item huItem,
			final I_M_Product product,
			final I_C_UOM uom,
			final Date date)
	{
		final I_M_HU_PI_Item_Product itemDefProduct = Services.get(IHUPIItemProductDAO.class).retrievePIMaterialItemProduct(huItem, product, date);
		if (itemDefProduct == null)
		{
			final boolean allowNegativeCapacity = false;
			return Capacity.createZeroCapacity(product, uom, allowNegativeCapacity);
		}

		return getCapacity(itemDefProduct, product, uom);
	}

	@Override
	public boolean isInfiniteCapacity(final I_M_HU_PI_Item_Product itemDefProduct)
	{
		return itemDefProduct.isInfiniteCapacity();
	}

	@Override
	public boolean isValidItemProduct(final I_M_HU_PI_Item_Product itemDefProduct)
	{
		Check.assumeNotNull(itemDefProduct, "Error: Null record");
		// item which allows any product should have infinite capacity and no product assigned
		if (itemDefProduct.isAllowAnyProduct() && (itemDefProduct.getM_Product_ID() > 0 || !isInfiniteCapacity(itemDefProduct)))
		{
			return false;
		}

		if (isInfiniteCapacity(itemDefProduct))
		{
			// Infinite capacity item product : valid
			return true;
		}

		// If it's not infinite capacity, quantity must be > 0
		return itemDefProduct.getQty().signum() > 0;
	}
}
