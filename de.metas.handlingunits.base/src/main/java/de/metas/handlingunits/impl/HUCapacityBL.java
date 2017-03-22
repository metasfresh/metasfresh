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
import java.math.RoundingMode;
import java.util.Date;

import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHUCapacityBL;
import de.metas.handlingunits.IHUCapacityDefinition;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IStatefulHUCapacityDefinition;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

public class HUCapacityBL implements IHUCapacityBL
{
	@Override
	public IHUCapacityDefinition createInfiniteCapacity(final I_M_Product product, final I_C_UOM uom)
	{
		return new HUCapacityDefinition(product, uom);
	}

	private IHUCapacityDefinition createZeroCapacity(final I_M_Product product, final I_C_UOM uom, final boolean allowNegativeCapacity)
	{
		return new HUCapacityDefinition(BigDecimal.ZERO, product, uom, allowNegativeCapacity);
	}

	@Override
	public IHUCapacityDefinition createCapacity(final BigDecimal qty, final I_M_Product product, final I_C_UOM uom, final boolean allowNegativeCapacity)
	{
		return new HUCapacityDefinition(qty, product, uom, allowNegativeCapacity);

	}

	@Override
	public IStatefulHUCapacityDefinition createStatefulCapacity(final IHUCapacityDefinition capacity, final BigDecimal qtyUsed)
	{
		return new StatefulHUCapacityDefinition(capacity, qtyUsed);
	}

	@Override
	public IHUCapacityDefinition getCapacity(final I_M_HU_PI_Item_Product itemDefProduct, final I_M_Product product, final I_C_UOM uom)
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
			return createInfiniteCapacity(productToUse, uom);
		}

		final BigDecimal qty = itemDefProduct.getQty();
		final BigDecimal qtyConv = Services.get(IUOMConversionBL.class)
				.convertQty(productToUse, qty, itemDefProduct.getC_UOM(), uom);

		final boolean allowNegativeCapacity = false;

		return createCapacity(qtyConv, productToUse, uom, allowNegativeCapacity);
	}

	@Override
	public IHUCapacityDefinition getCapacity(final I_M_HU_Item huItem, final I_M_Product product, final I_C_UOM uom, final Date date)
	{
		Check.assumeNotNull(huItem, "huItem not null");

		final I_M_HU_PI_Item_Product itemDefProduct = Services.get(IHUPIItemProductDAO.class).retrievePIMaterialItemProduct(huItem, product, date);
		if (itemDefProduct == null)
		{
			final boolean allowNegativeCapacity = false;
			return createZeroCapacity(product, uom, allowNegativeCapacity);
		}

		return getCapacity(itemDefProduct, product, uom);
	}

	@Override
	public boolean isInfiniteCapacity(final I_M_HU_PI_Item_Product itemDefProduct)
	{
		return itemDefProduct.isInfiniteCapacity();
	}

	@Override
	public IHUCapacityDefinition getAvailableCapacity(final BigDecimal qtyUsed, final I_C_UOM qtyUsedUOM,
			final IHUCapacityDefinition capacityDefinition)
	{
		final boolean allowNegativeCapacityThisTime = false;
		return getAvailableCapacity(qtyUsed, qtyUsedUOM, capacityDefinition, allowNegativeCapacityThisTime);
	}

	private IHUCapacityDefinition getAvailableCapacity(
			final BigDecimal qtyUsed, 
			final I_C_UOM qtyUsedUOM,
			final IHUCapacityDefinition capacityDefinition,
			final boolean allowNegativeCapacityThisTime)
	{
		// If it's infinite capacity, there is nothing to adjust
		if (capacityDefinition.isInfiniteCapacity())
		{
			return capacityDefinition;
		}

		// Qty used is ZERO so there is nothing to adjust
		if (qtyUsed.signum() == 0)
		{
			return capacityDefinition;
		}

		final BigDecimal capacity = capacityDefinition.getCapacity();

		final BigDecimal qtyUsedConv = Services.get(IUOMConversionBL.class)
				.convertQty(capacityDefinition.getM_Product(),
						qtyUsed,
						qtyUsedUOM, capacityDefinition.getC_UOM());

		final BigDecimal capacityAvailable = capacity.subtract(qtyUsedConv);

		// We got negative capacity
		if (capacityAvailable.signum() <= 0)
		{
			// Do we allow negative capacity?
			final boolean allowNegativeCapacity = false
					// Yes, if allow this time only is set
					|| allowNegativeCapacityThisTime
					// Yes, if capacity definition allows negative capacity
					|| capacityDefinition.isAllowNegativeCapacity()
					// Yes, if adjustment qty was negative so it did not influenced the sign of resulting capacity
					|| qtyUsedConv.signum() <= 0;

			if (!allowNegativeCapacity)
			{
				return createZeroCapacity(capacityDefinition.getM_Product(),
						capacityDefinition.getC_UOM(),
						capacityDefinition.isAllowNegativeCapacity());
			}
		}

		return createCapacity(capacityAvailable,
				capacityDefinition.getM_Product(),
				capacityDefinition.getC_UOM(),
				capacityDefinition.isAllowNegativeCapacity());
	}

	@Override
	public Integer calculateQtyPacks(final BigDecimal qty, final I_C_UOM uom, final IHUCapacityDefinition capacityDef)
	{
		// Infinite capacity => one pack would be sufficient
		if (capacityDef.isInfiniteCapacity())
		{
			return 1;
		}

		// Qty is zero => zero packs
		if (qty.signum() == 0)
		{
			return 0;
		}

		// Capacity is ZERO => N/A
		final BigDecimal capacity = capacityDef.getCapacity();
		if (capacity.signum() <= 0)
		{
			return null;
		}

		// Convert Qty to Capacity's UOM
		final BigDecimal qtyConv = Services.get(IUOMConversionBL.class).convertQty(capacityDef.getM_Product(), qty, uom, capacityDef.getC_UOM());

		final BigDecimal qtyPacks = qtyConv.divide(capacity, 0, RoundingMode.UP);
		return qtyPacks.intValueExact();
	}

	@Override
	public IHUCapacityDefinition multiply(final IHUCapacityDefinition capacityDef, final int multiplier)
	{
		Check.assumeNotNull(capacityDef, "capacityDef not null");
		Check.assume(multiplier >= 0, "multiplier >= 0");

		// If capacity is infinite, there is no point to multiply it
		if (capacityDef.isInfiniteCapacity())
		{
			return capacityDef;
		}

		final BigDecimal multiplierBD = BigDecimal.valueOf(multiplier);
		final BigDecimal capacity = capacityDef.getCapacity();
		final BigDecimal capacityNew = capacity.multiply(multiplierBD);

		return createCapacity(
				capacityNew,
				capacityDef.getM_Product(),
				capacityDef.getC_UOM(),
				capacityDef.isAllowNegativeCapacity());
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

	@Override
	public IHUCapacityDefinition convertToUOM(final IHUCapacityDefinition capacity, final I_C_UOM uomTo)
	{
		Check.assumeNotNull(capacity, "capacity not null");
		Check.assumeNotNull(uomTo, "uomTo not null");

		final I_C_UOM uomFrom = capacity.getC_UOM();
		if (uomFrom.getC_UOM_ID() == uomTo.getC_UOM_ID())
		{
			return capacity;
		}

		final I_M_Product product = capacity.getM_Product();
		if (capacity.isInfiniteCapacity())
		{
			return createInfiniteCapacity(product, uomTo);
		}

		final BigDecimal qtyCapacityFrom = capacity.getCapacity();
		final BigDecimal qtyCapacityTo = Services.get(IUOMConversionBL.class).convertQty(product,
				qtyCapacityFrom,
				uomFrom,
				uomTo);

		final boolean allowNegativeCapacity = capacity.isAllowNegativeCapacity();
		return createCapacity(qtyCapacityTo, product, uomTo, allowNegativeCapacity);
	}
}
