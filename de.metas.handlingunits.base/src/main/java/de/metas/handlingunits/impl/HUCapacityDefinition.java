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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;

import org.adempiere.util.Check;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.EqualsBuilder;
import org.compiere.util.HashcodeBuilder;

import de.metas.handlingunits.IHUCapacityDefinition;

/**
 * Container used when making decisions between limited capacity Handling Units Packing Instructions Item Product definitions, and unlimited capacity ones.
 *
 * @author al
 */
/* package */class HUCapacityDefinition implements IHUCapacityDefinition
{
	private final I_M_Product product;
	private final I_C_UOM uom;
	private final BigDecimal capacity;

	private final boolean infiniteCapacity;
	private final boolean allowNegativeCapacity;

	/**
	 * Constructs a finite capacity definition
	 */
	public HUCapacityDefinition(final BigDecimal capacity,
			final I_M_Product product, final I_C_UOM uom,
			final boolean allowNegativeCapacity)
	{
		super();

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
	public HUCapacityDefinition(final I_M_Product product, final I_C_UOM uom)
	{
		super();

		this.product = product;
		this.uom = uom;

		capacity = null;

		infiniteCapacity = true;
		allowNegativeCapacity = true;
	}

	@Override
	public boolean isInfiniteCapacity()
	{
		return infiniteCapacity;
	}

	@Override
	public boolean isAllowNegativeCapacity()
	{
		Check.assume(!isInfiniteCapacity(), "Cannot retrieve if it's infinite for {0}", this);
		return allowNegativeCapacity;
	}

	@Override
	public BigDecimal getCapacity()
	{
		Check.assume(!isInfiniteCapacity(), "Cannot retrieve capacity Qty if it's infinite for {0}", this);
		return capacity;
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
