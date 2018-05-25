package de.metas.quantity;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.adempiere.util.Check;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.compiere.model.I_C_UOM;

import lombok.NonNull;

/**
 * Immutable Quantity.
 *
 * Besides quantity value ({@link #getQty()}) and it's uom ({@link #getUOM()} this object contains also source quantity/uom.
 *
 * The actual meaning of source quantity/uom depends on who constructs the {@link Quantity} object but the general meaning is: the quantity/uom in some source or internal UOM.
 *
 * e.g. when you ask a storage to allocate a quantity/uom that method could return a quantity object containing how much was allocated (in requested UOM), but the source quantity/uom is the quantity
 * in storage's UOM.
 *
 * @author tsa
 *
 */
public final class Quantity implements Comparable<Quantity>
{
	public static final Quantity of(final BigDecimal qty, final I_C_UOM uom)
	{
		return new Quantity(qty, uom);
	}

	public static boolean isInfinite(final BigDecimal qty)
	{
		return QTY_INFINITE.compareTo(qty) == 0;
	}

	public static final BigDecimal QTY_INFINITE = BigDecimal.valueOf(Long.MAX_VALUE); // NOTE: we need a new instance to make sure it's unique

	// NOTE to dev: all fields shall be final because this is a immutable object. Please keep that logic if u are adding more fields
	private final BigDecimal qty;
	private final I_C_UOM uom;

	private final BigDecimal sourceQty;
	private final I_C_UOM sourceUom;

	/**
	 * Constructs a quantity object without source quantity/uom. More preciselly, source quantity/uom will be set so same values as quantity/uom.
	 *
	 * @param qty
	 * @param uom
	 */
	public Quantity(final BigDecimal qty, final I_C_UOM uom)
	{
		this(qty, uom, qty, uom);
	}

	public Quantity(
			@NonNull final BigDecimal qty,
			@NonNull final I_C_UOM uom,
			@NonNull final BigDecimal sourceQty,
			@NonNull final I_C_UOM sourceUOM)
	{
		this.qty = qty;
		this.uom = uom;
		this.sourceQty = sourceQty;
		this.sourceUom = sourceUOM;
	}

	@Override
	public String toString()
	{
		return qty + " " + uom.getUOMSymbol() + " (source: " + sourceQty + " " + sourceUom.getUOMSymbol() + ")";
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(qty)
				.append(uom.getC_UOM_ID())
				.append(sourceQty)
				.append(sourceUom.getC_UOM_ID())
				.toHashcode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;

		final Quantity other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(this.qty, other.qty)
				.append(this.uom.getC_UOM_ID(), other.uom.getC_UOM_ID())
				.append(this.sourceQty, other.sourceQty)
				.append(this.sourceUom.getC_UOM_ID(), other.sourceUom.getC_UOM_ID())
				.isEqual();
	}

	/**
	 * Checks if <code>this</code> quantity equals with <code>other</code>, not considering the source (i.e. {@link #getSourceQty()}, {@link #getSourceUOM()}).
	 *
	 * @param other
	 * @return true if they are equal
	 */
	public boolean equalsIgnoreSource(final Quantity other)
	{
		if (this == other)
		{
			return true;
		}
		if (other == null)
		{
			return false;
		}
		return new EqualsBuilder()
				.append(this.qty, other.qty)
				.append(this.uom.getC_UOM_ID(), other.uom.getC_UOM_ID())
				.isEqual();
	}

	/**
	 * Checks if this quantity and given quantity are equal when comparing the current values (i.e. {@link #getQty()}, {@link #getUOM()}).
	 *
	 * NOTE: quantities will be compared by using {@link BigDecimal#compareTo(BigDecimal)} instead of {@link BigDecimal#equals(Object)}.
	 *
	 * @param quantity
	 * @return true if current Qty/UOM are comparable equal.
	 */
	public boolean qtyAndUomCompareToEquals(final Quantity quantity)
	{
		if (this == quantity)
		{
			return true;
		}
		if (quantity == null)
		{
			return false;
		}

		if (this.getQty().compareTo(quantity.getQty()) != 0)
		{
			return false;
		}

		if (this.getC_UOM_ID() != quantity.getC_UOM_ID())
		{
			return false;
		}

		return true;
	}

	/**
	 * @return Quantity value; never return null
	 */
	public BigDecimal getQty()
	{
		return qty;
	}

	/**
	 * @param qty
	 * @return a new {@link Quantity} object
	 */
	public Quantity setQty(final BigDecimal qty)
	{
		if (this.qty == qty)
		{
			return this;
		}
		return new Quantity(qty, this.uom, this.sourceQty, this.sourceUom);
	}

	/**
	 *
	 * @return true if quantity value is infinite
	 */
	public boolean isInfinite()
	{
		return isInfinite(qty);
	}

	/**
	 *
	 * @return quantity's UOM; never return null
	 */
	public I_C_UOM getUOM()
	{
		return uom;
	}

	/**
	 * @return quantity's C_UOM_ID
	 */
	private int getC_UOM_ID()
	{
		return uom.getC_UOM_ID();
	}

	public String getUOMSymbol()
	{
		return uom.getUOMSymbol();
	}

	/**
	 *
	 * @param uom
	 * @return a new {@link Quantity} object
	 */
	public Quantity setUOM(final I_C_UOM uom)
	{
		if (this.uom == uom)
		{
			return this;
		}
		return new Quantity(this.qty, uom, this.sourceQty, this.sourceUom);
	}

	/**
	 * @return source quantity; never null
	 */
	public BigDecimal getSourceQty()
	{
		return sourceQty;
	}

	/**
	 *
	 * @param sourceQty
	 * @return a new {@link Quantity} object
	 */
	public Quantity setSourceQty(final BigDecimal sourceQty)
	{
		if (this.sourceQty == sourceQty)
		{
			return this;
		}
		return new Quantity(this.qty, this.uom, sourceQty, this.sourceUom);
	}

	/**
	 *
	 * @return source quatity's UOM
	 */
	public I_C_UOM getSourceUOM()
	{
		return sourceUom;
	}

	/**
	 * @return source quatity's C_UOM_ID
	 */
	private final int getSource_UOM_ID()
	{
		return sourceUom.getC_UOM_ID();
	}

	/**
	 *
	 * @param sourceUom
	 * @return a new {@link Quantity} object
	 */
	public Quantity setSourceUOM(final I_C_UOM sourceUom)
	{
		if (this.sourceUom == sourceUom)
		{
			return this;
		}
		return new Quantity(this.qty, this.uom, this.sourceQty, sourceUom);
	}

	/**
	 * @param uom
	 * @return ZERO quantity (using given UOM)
	 */
	public static final Quantity zero(final I_C_UOM uom)
	{
		return new Quantity(BigDecimal.ZERO, uom, BigDecimal.ZERO, uom);
	}

	/**
	 *
	 * @return ZERO quantity (but preserve UOMs)
	 */
	public Quantity toZero()
	{
		if (qty.signum() == 0 && sourceQty.signum() == 0)
		{
			return this;
		}
		return new Quantity(BigDecimal.ZERO, uom, BigDecimal.ZERO, sourceUom);
	}

	/**
	 * @param uom
	 * @return infinite quantity (using given UOM)
	 */
	public static final Quantity infinite(final I_C_UOM uom)
	{
		return new Quantity(QTY_INFINITE, uom, QTY_INFINITE, uom);
	}

	/**
	 * @return infinite quantity (but preserve UOMs)
	 */
	public Quantity toInfinite()
	{
		if (isInfinite())
		{
			return this;
		}
		return new Quantity(QTY_INFINITE, uom, QTY_INFINITE, sourceUom);
	}

	public Quantity negate()
	{
		return setQty(getQty().negate())
				.setSourceQty(getSourceQty().negate());
	}

	/**
	 *
	 * @param condition
	 * @return negated quantity if <code>condition</code> is true; else return this
	 */
	public Quantity negateIf(final boolean condition)
	{
		if (!condition)
		{
			return this;
		}

		return negate();
	}

	/**
	 * Calculates the Weighted Average between this Quantity and a previous given average.
	 *
	 * i.e. Current Weighted Avg = (<code>previousAverage</code> * <code>previousAverageWeight</code> + this quantity) / (<code>previousAverageWeight</code> + 1)
	 *
	 * @param previousAverage
	 * @param previousAverageWeight
	 * @return weighted average
	 */
	public Quantity weightedAverage(final BigDecimal previousAverage, final int previousAverageWeight)
	{
		Check.assumeNotNull(previousAverage, "previousAverage not null");
		Check.assume(previousAverageWeight >= 0, "previousAverageWeight >= 0");

		final BigDecimal previousAverageWeightBD = BigDecimal.valueOf(previousAverageWeight);
		final BigDecimal count = BigDecimal.valueOf(previousAverageWeight + 1);
		final int precision = getUOM().getStdPrecision();

		final BigDecimal currentQty = getQty();
		final BigDecimal currentWeightAverage = previousAverage
				.multiply(previousAverageWeightBD)
				.add(currentQty)
				.divide(count, precision, RoundingMode.HALF_UP);

		return new Quantity(currentWeightAverage, getUOM());
	}

	/**
	 * Interchange current Qty/UOM with source Qty/UOM.
	 *
	 * e.g. If your quantity is "5kg (source: 5000g)" then this method will return "5000g (source: 5kg)"
	 *
	 * @return a new instance with current Qty/UOM and source Qty/UOM interchanged.
	 */
	public Quantity switchToSource()
	{
		return new Quantity(getSourceQty(), getSourceUOM(), getQty(), getUOM());
	}

	/**
	 * Returns the signum function of current quantity (i.e. {@link #getQty()} ).
	 *
	 * @return -1, 0, or 1 as the value of current quantity is negative, zero, or positive.
	 */
	public int signum()
	{
		return getQty().signum();
	}

	/**
	 * Checks if current quantity zero.
	 *
	 * This is just a convenient method for checking if the {@link #signum()} is zero.
	 *
	 * @return true if {@link #signum()} is zero.
	 */
	public boolean isZero()
	{
		return signum() == 0;
	}

	/**
	 * Adds given quantity and returns the result.
	 *
	 * @param qtyToAdd
	 * @return new {@link Quantity}
	 * @throws QuantitiesUOMNotMatchingExpection if this quantity and qtyToAdd are not UOM compatible
	 */
	public Quantity add(@NonNull final Quantity qtyToAdd)
	{
		if (qtyToAdd.isZero())
		{
			return this;
		}

		// Get QtyToAdd value (mandatory)
		final BigDecimal qtyToAdd_Value;
		final int uomId = this.getC_UOM_ID();
		final int qtyToAdd_uomId = qtyToAdd.getC_UOM_ID();
		final int qtyToAdd_sourceUomId = qtyToAdd.getSource_UOM_ID();
		if (uomId == qtyToAdd_uomId)
		{
			qtyToAdd_Value = qtyToAdd.getQty();
		}
		else if (uomId == qtyToAdd_sourceUomId)
		{
			qtyToAdd_Value = qtyToAdd.getSourceQty();
		}
		else
		{
			throw new QuantitiesUOMNotMatchingExpection("Cannot add " + qtyToAdd + " to " + this + " because UOMs are not compatible");
		}

		//
		// Get QtyToAdd source value
		final BigDecimal qtyToAdd_SourceValue;
		final int sourceUomId = this.getSource_UOM_ID();
		if (sourceUomId == qtyToAdd_sourceUomId)
		{
			qtyToAdd_SourceValue = qtyToAdd.getSourceQty();
		}
		else if (sourceUomId == qtyToAdd_uomId)
		{
			qtyToAdd_SourceValue = qtyToAdd.getQty();
		}
		else
		{
			// Source UOM does not match. We can skip it
			qtyToAdd_SourceValue = null;
		}

		//
		// Compute new Quantity's values
		final BigDecimal qtyNew_Value = this.getQty().add(qtyToAdd_Value);
		final I_C_UOM qtyNew_UOM = this.getUOM();
		final BigDecimal qtyNew_SourceValue;
		final I_C_UOM qtyNew_SourceUOM;
		if (qtyToAdd_SourceValue == null)
		{
			qtyNew_SourceValue = qtyNew_Value;
			qtyNew_SourceUOM = qtyNew_UOM;
		}
		else
		{
			qtyNew_SourceValue = this.getSourceQty().add(qtyToAdd_SourceValue);
			qtyNew_SourceUOM = this.getSourceUOM();
		}

		//
		// Create the new quantity and return it
		return new Quantity(qtyNew_Value, qtyNew_UOM, qtyNew_SourceValue, qtyNew_SourceUOM);
	}

	public Quantity subtract(@NonNull final Quantity qtyToRemove)
	{
		final Quantity qtyToAdd = qtyToRemove.negate();
		return add(qtyToAdd);
	}

	/**
	 *
	 * @param qtyToCompare
	 * @return minimum of <code>this</code> and <code>qtyToCompare</code>
	 */
	public Quantity min(@NonNull final Quantity qtyToCompare)
	{
		final Quantity diff = this.subtract(qtyToCompare);
		if (diff.signum() >= 0)
		{
			return this;
		}
		else
		{
			return qtyToCompare;
		}
	}

	@Override
	public int compareTo(@NonNull final Quantity quantity)
	{
		final Quantity diff = this.subtract(quantity);
		return diff.signum();
	}

	public Quantity multiply(final int multiplicand)
	{
		return multiply(BigDecimal.valueOf(multiplicand));
	}

	public Quantity multiply(final BigDecimal multiplicand)
	{
		if (multiplicand.compareTo(BigDecimal.ONE) == 0)
		{
			return this;
		}

		return new Quantity(qty.multiply(multiplicand), uom, sourceQty.multiply(multiplicand), sourceUom);
	}
}
