package de.metas.quantity;

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

import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * Container used when making decisions between limited capacity Handling Units Packing Instructions Item Product definitions, and unlimited capacity ones.
 *
 * @author al
 */
@EqualsAndHashCode
public class HUCapacityDefinition
{

	public static HUCapacityDefinition createInfiniteCapacity(final I_M_Product product, final I_C_UOM uom)
	{
		return new HUCapacityDefinition(product, uom);
	}

	public static HUCapacityDefinition createZeroCapacity(final I_M_Product product, final I_C_UOM uom, final boolean allowNegativeCapacity)
	{
		return new HUCapacityDefinition(BigDecimal.ZERO, product, uom, allowNegativeCapacity);
	}

	public static HUCapacityDefinition createCapacity(final BigDecimal qty, final I_M_Product product, final I_C_UOM uom, final boolean allowNegativeCapacity)
	{
		return new HUCapacityDefinition(qty, product, uom, allowNegativeCapacity);

	}

	/**
	 * Signals the logic to use the default capacity.
	 */
	public static BigDecimal DEFAULT = new BigDecimal(Long.MAX_VALUE);

	private final I_M_Product product;
	private final I_C_UOM uom;
	private final BigDecimal capacity;

	private final boolean infiniteCapacity;
	private final boolean allowNegativeCapacity;

	/**
	 * Constructs a finite capacity definition
	 */
	private HUCapacityDefinition(
			final BigDecimal capacity,
			final I_M_Product product, final I_C_UOM uom,
			final boolean allowNegativeCapacity)
	{
		this.product = product;
		this.uom = uom;

		Check.assumeNotNull(capacity, "capacity not null");
		this.capacity = capacity;

		infiniteCapacity = false;
		this.allowNegativeCapacity = allowNegativeCapacity;
	}

	/**
	 * Constructs an infinite capacity definition
	 */
	private HUCapacityDefinition(final I_M_Product product, final I_C_UOM uom)
	{
		this.product = product;
		this.uom = uom;

		capacity = null;

		infiniteCapacity = true;
		allowNegativeCapacity = true;
	}

	public boolean isInfiniteCapacity()
	{
		return infiniteCapacity;
	}

	public boolean isAllowNegativeCapacity()
	{
		Check.assume(!isInfiniteCapacity(), "Cannot retrieve if it's infinite for {}", this);
		return allowNegativeCapacity;
	}

	public BigDecimal getCapacity()
	{
		Check.assume(!isInfiniteCapacity(), "Cannot retrieve capacity Qty if it's infinite for {}", this);
		return capacity;
	}

	/**
	 * Convert given capacity to <code>uomTo</code>
	 *
	 * @param customCapacity
	 * @param uom
	 * @return capacity converted to <code>uomTo</code>
	 */
	public HUCapacityDefinition convertToUOM(@NonNull final I_C_UOM uomTo)
	{
		if (uom.getC_UOM_ID() == uomTo.getC_UOM_ID())
		{
			return this;
		}
		if (isInfiniteCapacity())
		{
			return createInfiniteCapacity(product, uomTo);
		}

		final BigDecimal qtyCapacityTo = Services.get(IUOMConversionBL.class).convertQty(product,
				capacity,
				uom,
				uomTo);

		return createCapacity(qtyCapacityTo, product, uomTo, allowNegativeCapacity);
	}

	/**
	 * Evaluate the given {@code capacityDefinition} and create a new one based how much the given {@code qtyUsed} and {@code qtyUsedUOM} would take away.
	 * 
	 * @param qtyUsed
	 * @param qtyUsedUOM
	 * @param capacityDefinition
	 * @return
	 */
	public HUCapacityDefinition getAvailableCapacity(BigDecimal qtyUsed, I_C_UOM qtyUsedUOM)
	{
		// If it's infinite capacity, there is nothing to adjust
		if (infiniteCapacity)
		{
			return this;
		}

		// Qty used is ZERO so there is nothing to adjust
		if (qtyUsed.signum() == 0)
		{
			return this;
		}

		final BigDecimal qtyUsedConv = Services.get(IUOMConversionBL.class)
				.convertQty(product, qtyUsed, qtyUsedUOM, uom);

		final BigDecimal capacityAvailable = capacity.subtract(qtyUsedConv);

		// We got negative capacity
		if (capacityAvailable.signum() <= 0)
		{
			final boolean mustCreateZeroCapacity =
					// Yes, if capacity definition does not allow negative capacity
					!allowNegativeCapacity
							// note: if *both* capacityAvailable and qtyUsed/qtyOsedConf we negative, it would not influence the sign of the resulting capacity
							&& qtyUsedConv.signum() > 0;
			if (!mustCreateZeroCapacity)
			{
				return createZeroCapacity(product,
						uom,
						allowNegativeCapacity);
			}
		}

		return createCapacity(capacityAvailable,
				product,
				uom,
				allowNegativeCapacity);
	}

	/**
	 * Calculates how many capacities like <code>capacityDef</code> do we need to cover given <code>qty</code>
	 *
	 * <p>
	 * e.g. if Qty=13 and Capacity=10 then QtyPacks=2 (13/10 rounded up).
	 *
	 * @param qty
	 * @param uom quantity's unit of measure
	 * @param capacityDef
	 * @return how many capacities are required or NULL if capacity is not available
	 */
	public Integer calculateQtyPacks(BigDecimal qty, I_C_UOM uom)
	{
		// Infinite capacity => one pack would be sufficient
		if (infiniteCapacity)
		{
			return 1;
		}

		// Qty is zero => zero packs
		if (qty.signum() == 0)
		{
			return 0;
		}

		// Capacity is ZERO => N/A
		if (capacity.signum() <= 0)
		{
			return null;
		}

		// Convert Qty to Capacity's UOM
		final BigDecimal qtyConv = Services.get(IUOMConversionBL.class).convertQty(product, qty, uom, uom);

		final BigDecimal qtyPacks = qtyConv.divide(capacity, 0, RoundingMode.UP);
		return qtyPacks.intValueExact();
	}

	public HUCapacityDefinition multiply(final int multiplier)
	{
		Check.assume(multiplier >= 0, "multiplier = {} needs to be 0", multiplier);

		// If capacity is infinite, there is no point to multiply it
		if (infiniteCapacity)
		{
			return this;
		}

		final BigDecimal capacityNew = capacity.multiply(BigDecimal.valueOf(multiplier));

		return createCapacity(
				capacityNew,
				product,
				uom,
				allowNegativeCapacity);
	}

	public I_M_Product getM_Product()
	{
		return product;
	}

	public I_C_UOM getC_UOM()
	{
		return uom;
	}

	@Override
	public String toString()
	{
		return "HUCapacityDefinition ["
				+ "infiniteCapacity=" + infiniteCapacity
				+ ", capacity(qty)=" + capacity
				+ ", product=" + (product == null ? "null" : product.getValue())
				+ ", uom=" + (uom == null ? "null" : uom.getUOMSymbol())
				+ ", allowNegativeCapacity=" + allowNegativeCapacity
				+ "]";
	}
}
