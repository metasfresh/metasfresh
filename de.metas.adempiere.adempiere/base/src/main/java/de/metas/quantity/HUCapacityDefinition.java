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

import org.adempiere.util.Check;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHUCapacityDefinition;

/**
 * Container used when making decisions between limited capacity Handling Units Packing Instructions Item Product definitions, and unlimited capacity ones.
 *
 * @author al
 */
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
	BigDecimal DEFAULT = new BigDecimal(Long.MAX_VALUE);

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
	HUCapacityDefinition convertToUOM(public I_C_UOM uomTo)
	{
		
	}

	/**
	 * Evaluate the given {@code capacityDefinition} and create a new one based how much the given {@code qtyUsed} and {@code qtyUsedUOM} would take away.
	 * 
	 * @param qtyUsed
	 * @param qtyUsedUOM
	 * @param capacityDefinition
	 * @return
	 */
	HUCapacityDefinition getAvailableCapacity(BigDecimal qtyUsed, I_C_UOM qtyUsedUOM, HUCapacityDefinition capacityDefinition)
	{
		
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
	Integer calculateQtyPacks(BigDecimal qty, I_C_UOM uom, HUCapacityDefinition capacityDef)
	{
		
	}

	HUCapacityDefinition multiply(final HUCapacityDefinition capacityDef, int multiplier)
	{
		
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
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(product)
				.append(uom)
				.append(capacity)
				.append(infiniteCapacity)
				.append(allowNegativeCapacity)
				.toHashcode();
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final HUCapacityDefinition other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(product, other.product)
				.append(uom, other.uom)
				.append(capacity, other.capacity)
				.append(infiniteCapacity, other.infiniteCapacity)
				.append(allowNegativeCapacity, other.allowNegativeCapacity)
				.isEqual();
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
