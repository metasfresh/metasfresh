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

import org.compiere.model.I_C_UOM;

import de.metas.uom.IUOMConversionBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * This is {@link Capacity} that also has "level", i.e. it contains a quantity that already occupates a part of it.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@EqualsAndHashCode
public class Bucket
{
	public static Bucket createEmptyBucketWithCapacity(@NonNull final Capacity capacity)
	{
		return new Bucket(capacity, BigDecimal.ZERO);
	}

	public static Bucket createBucketWithCapacityAndQty(
			@NonNull final Capacity capacity,
			@NonNull final BigDecimal qty)
	{
		return new Bucket(capacity, qty);
	}

	private final Capacity capacity;
	private BigDecimal _qty = BigDecimal.ZERO;
	private BigDecimal _qtyFree = BigDecimal.ZERO;

	private Bucket(@NonNull final Capacity capacity, @NonNull final BigDecimal qtyInitial)
	{
		this.capacity = capacity;
		adjustQty(qtyInitial);
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + " ["
				+ "qty=" + _qty
				+ ", qtyFree=" + _qtyFree
				+ ", capacity=" + capacity
				+ "]";
	}

	public final boolean isInfiniteCapacity()
	{
		return capacity.isInfiniteCapacity();
	}

	public final boolean isAllowNegativeCapacity()
	{
		return capacity.isAllowNegativeCapacity();
	}

	private final boolean isAllowNegativeCapacity(final Boolean allowNegativeCapacityOverride)
	{
		if (allowNegativeCapacityOverride == null)
		{
			return capacity.isAllowNegativeCapacity();
		}
		else
		{
			return allowNegativeCapacityOverride;
		}
	}

	private final boolean isAllowCapacityOverload(final Boolean allowCapacityOverload)
	{
		if (allowCapacityOverload == null)
		{
			// By default, we don't allow capacity overload (backward compatibility)
			return false;
		}
		else
		{
			return allowCapacityOverload;
		}
	}

	public final BigDecimal getCapacity()
	{
		return capacity.toBigDecimal();
	}

	public final I_C_UOM getC_UOM()
	{
		return capacity.getC_UOM();
	}

	public final BigDecimal getQty()
	{
		return _qty;
	}

	private void adjustQty(final BigDecimal qtyDiff)
	{
		final BigDecimal qtyOld = _qty;
		final BigDecimal qtyNew = qtyOld.add(qtyDiff);

		_qty = qtyNew;

		//
		// Update Qty Free
		if (isInfiniteCapacity())
		{
			_qtyFree = Quantity.QTY_INFINITE;
		}
		else
		{
			final BigDecimal qtyCapacity = getCapacity();
			_qtyFree = qtyCapacity.subtract(_qty);
		}
	}

	public final BigDecimal getQtyFree()
	{
		return _qtyFree;
	}

	public Quantity addQty(final Quantity qtyToAdd)
	{
		final Boolean allowCapacityOverload = null; // default
		return addQty(qtyToAdd, allowCapacityOverload);
	}

	public Quantity addQty(
			@NonNull final Quantity qtyToAdd,
			final Boolean allowCapacityOverload)
	{
		Check.assume(qtyToAdd.signum() >= 0, "qtyToAdd({}) >= 0", qtyToAdd);

		final BigDecimal qtyToAdd_Qty = qtyToAdd.toBigDecimal();
		final I_C_UOM qtyToAdd_UOM = qtyToAdd.getUOM();
		final I_C_UOM baseUOM = getC_UOM();

		if (qtyToAdd.signum() == 0)
		{
			return new Quantity(BigDecimal.ZERO, qtyToAdd_UOM, BigDecimal.ZERO, baseUOM);
		}

		final BigDecimal qtyToAddBaseUom = convertToBaseUOM(qtyToAdd_Qty, qtyToAdd_UOM);
		// NOTE: we assume "qtyToAddBaseUom > 0" because we assumed "qtyToAdd > 0"

		// Case: we have infinite capacity
		// => add full quantity
		if (isInfiniteCapacity())
		{
			adjustQty(qtyToAddBaseUom);
			return new Quantity(qtyToAdd_Qty, qtyToAdd_UOM, qtyToAddBaseUom, baseUOM);
		}

		// Case: we are asked to overload the capacity
		// => add full quantity, no matter what
		if (isAllowCapacityOverload(allowCapacityOverload))
		{
			adjustQty(qtyToAddBaseUom);
			return new Quantity(qtyToAdd_Qty, qtyToAdd_UOM, qtyToAddBaseUom, baseUOM);
		}

		//
		// Enforce capacity
		final BigDecimal qtyFree = getQtyFree();
		final BigDecimal qtyToAddActualBaseUom;
		final BigDecimal qtyToAddActual; // QtyToAdd (actual), in qtyToAddUOM
		if (qtyFree.compareTo(qtyToAddBaseUom) >= 0)
		{
			// we have enough available qty
			qtyToAddActualBaseUom = qtyToAddBaseUom;
			qtyToAddActual = qtyToAdd_Qty;
		}
		else if (qtyFree.signum() <= 0)
		{
			// we have a negative (or zero) free quantity
			// => there is nothing to add
			qtyToAddActualBaseUom = BigDecimal.ZERO;
			qtyToAddActual = BigDecimal.ZERO;
		}
		else
		{
			// we don't have enough available qty, truncate to max available
			qtyToAddActualBaseUom = qtyFree;
			qtyToAddActual = convertFromBaseUOM(qtyToAddActualBaseUom, qtyToAdd_UOM);
		}

		//
		// Adjust Qty Used
		adjustQty(qtyToAddActualBaseUom);

		return new Quantity(qtyToAddActual, qtyToAdd_UOM, qtyToAddActualBaseUom, baseUOM);
	}

	public Quantity removeQty(final Quantity qtyToRemove)
	{
		final Boolean allowNegativeCapacityOverride = null; // default
		return removeQty(qtyToRemove, allowNegativeCapacityOverride);
	}

	public Quantity removeQty(final Quantity qtyToRemove, final Boolean allowNegativeCapacityOverride)
	{
		Check.assumeNotNull(qtyToRemove, "qtyToRemove not null");
		Check.assume(qtyToRemove.signum() >= 0, "qtyToRemove({}) >= 0", qtyToRemove);

		final BigDecimal qtyToRemove_Qty = qtyToRemove.toBigDecimal();
		final I_C_UOM qtyToRemove_UOM = qtyToRemove.getUOM();
		final I_C_UOM baseUOM = getC_UOM();

		if (qtyToRemove_Qty.signum() == 0)
		{
			return new Quantity(BigDecimal.ZERO, qtyToRemove_UOM, BigDecimal.ZERO, baseUOM);
		}

		final BigDecimal qtyToRemoveBaseUom = convertToBaseUOM(qtyToRemove_Qty, qtyToRemove_UOM);

		if (isInfiniteCapacity())
		{
			adjustQty(qtyToRemoveBaseUom.negate());
			return new Quantity(qtyToRemove_Qty, qtyToRemove_UOM, qtyToRemoveBaseUom, baseUOM);
		}

		//
		// Enforce capacity
		final BigDecimal qty = getQty();
		final BigDecimal qtyToRemoveActualBaseUom;
		final BigDecimal qtyToRemoveActual;
		//
		// Case: current storage quantity is negative
		if (qty.signum() < 0)
		{
			if (isAllowNegativeCapacity(allowNegativeCapacityOverride))
			{
				// we allow negative capacity, so we can remove everything
				qtyToRemoveActualBaseUom = qtyToRemoveBaseUom;
				qtyToRemoveActual = qtyToRemove_Qty;
			}
			else
			{
				return new Quantity(BigDecimal.ZERO, qtyToRemove_UOM, BigDecimal.ZERO, baseUOM);
			}
		}
		//
		// Case: in our storage there is more than required
		// => we can remove everything that was required
		else if (qty.compareTo(qtyToRemoveBaseUom) >= 0)
		{
			// we have enough qty used
			qtyToRemoveActualBaseUom = qtyToRemoveBaseUom;
			qtyToRemoveActual = qtyToRemove_Qty;
		}
		//
		// Case: in our storage there is not enough quantity to remove
		else
		{
			// we don't have enough qty used
			if (isAllowNegativeCapacity(allowNegativeCapacityOverride))
			{
				// we allow negative capacity, so we can remove everything
				qtyToRemoveActualBaseUom = qtyToRemoveBaseUom;
				qtyToRemoveActual = qtyToRemove_Qty;
			}
			else
			{
				// we don't allow negative capacity, so we remove as much as we can
				qtyToRemoveActualBaseUom = qty;
				qtyToRemoveActual = convertFromBaseUOM(qtyToRemoveActualBaseUom, qtyToRemove_UOM);
			}
		}

		//
		// Adjust Qty Used
		adjustQty(qtyToRemoveActualBaseUom.negate());

		return new Quantity(qtyToRemoveActual, qtyToRemove_UOM, qtyToRemoveActualBaseUom, baseUOM);
	}

	private BigDecimal convertToBaseUOM(final BigDecimal qty, final I_C_UOM qtyUOM)
	{
		final I_C_UOM baseUOM = getC_UOM();

		final BigDecimal qtyConv = Services.get(IUOMConversionBL.class).convertQty(
				capacity.getProductId(),
				qty,
				qtyUOM, // uomFrom,
				baseUOM// uomTo
		);
		return qtyConv;
	}

	private BigDecimal convertFromBaseUOM(final BigDecimal qty, final I_C_UOM uomTo)
	{
		final I_C_UOM baseUOM = getC_UOM();
		final BigDecimal qtyConv = Services.get(IUOMConversionBL.class).convertQty(
				capacity.getProductId(),
				qty,
				baseUOM, // uomFrom,
				uomTo// uomTo
		);
		return qtyConv;
	}
}
