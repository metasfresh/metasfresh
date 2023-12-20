package de.metas.handlingunits.allocation.impl;

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

import de.metas.handlingunits.allocation.impl.TotalQtyCUBreakdownCalculator.LUQtys.LUQtysBuilder;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.adempiere.util.lang.ObjectUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link LUQtys} calculator which starts from a given total CUs quantity and exposes methods from subtracting that quantity by one LU.
 * 
 * @author tsa
 *
 */
public class TotalQtyCUBreakdownCalculator
{
	public static final TotalQtyCUBreakdownCalculator NULL = new TotalQtyCUBreakdownCalculator();

	public static final Builder builder()
	{
		return new Builder();
	}

	//
	// Initial/Available totals
	private final BigDecimal qtyCUsTotal;
	private BigDecimal _qtyCUsTotalAvailable;
	private final BigDecimal qtyTUsTotal;
	private BigDecimal _qtyTUsTotalAvailable;

	//
	// Standard configuration
	private final BigDecimal standardQtyCUsPerTU;
	private final BigDecimal standardQtyTUsPerLU;
	private final BigDecimal standardQtyCUsPerLU;
	private final boolean infiniteCapacity;

	private TotalQtyCUBreakdownCalculator(final Builder builder)
	{
		super();

		//
		// Initial/Available totals
		qtyCUsTotal = builder.getQtyCUsTotal();
		_qtyCUsTotalAvailable = qtyCUsTotal;

		qtyTUsTotal = builder.getQtyTUsTotal();
		_qtyTUsTotalAvailable = qtyTUsTotal;

		//
		// Standard configuration
		standardQtyCUsPerTU = builder.getStandardQtyCUsPerTU();
		standardQtyTUsPerLU = builder.getStandardQtyTUsPerLU();
		infiniteCapacity = (standardQtyCUsPerTU == null || standardQtyCUsPerTU.signum() <= 0)
				|| (standardQtyTUsPerLU == null || standardQtyTUsPerLU.signum() <= 0);
		standardQtyCUsPerLU = infiniteCapacity ? BigDecimal.ZERO : standardQtyCUsPerTU.multiply(standardQtyTUsPerLU);
	}

	/** Copy constructor */
	private TotalQtyCUBreakdownCalculator(final TotalQtyCUBreakdownCalculator calc)
	{
		super();
		qtyCUsTotal = calc.qtyCUsTotal;
		_qtyCUsTotalAvailable = calc._qtyCUsTotalAvailable;

		qtyTUsTotal = calc.qtyTUsTotal;
		_qtyTUsTotalAvailable = calc._qtyTUsTotalAvailable;

		//
		// Standard configuration
		standardQtyCUsPerTU = calc.standardQtyCUsPerTU;
		standardQtyTUsPerLU = calc.standardQtyTUsPerLU;
		infiniteCapacity = calc.infiniteCapacity;
		standardQtyCUsPerLU = calc.standardQtyCUsPerLU;
	}

	/** Null constructor */
	private TotalQtyCUBreakdownCalculator()
	{
		super();
		qtyCUsTotal = BigDecimal.ZERO;
		_qtyCUsTotalAvailable = BigDecimal.ZERO;

		qtyTUsTotal = null;
		_qtyTUsTotalAvailable = null;

		//
		// Standard configuration
		standardQtyCUsPerTU = BigDecimal.ZERO;
		standardQtyTUsPerLU = BigDecimal.ZERO;
		infiniteCapacity = true;
		standardQtyCUsPerLU = BigDecimal.ZERO;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public TotalQtyCUBreakdownCalculator copy()
	{
		return new TotalQtyCUBreakdownCalculator(this);
	}

	public BigDecimal getQtyCUsTotalAvailable()
	{
		return _qtyCUsTotalAvailable;
	}

	/**
	 * @return qty TUs available or <code>null</code> if this info was not configured
	 */
	public BigDecimal getQtyTUsTotalAvailable()
	{
		return _qtyTUsTotalAvailable;
	}

	public LUQtys subtractOneLU()
	{
		if (isInfiniteCapacity())
		{
			return LUQtys.NULL;
		}
		if (!hasAvailableQty())
		{
			return LUQtys.NULL;
		}

		//
		// Check available qty CUs
		final BigDecimal qtyCUsTotalAvailable = getQtyCUsTotalAvailable();
		BigDecimal qtyCUsPerTU = standardQtyCUsPerTU;
		BigDecimal qtyCUsPerLU;
		BigDecimal qtyTUsPerLU;
		// Case: we don't have CU quantity available at all
		if (qtyCUsTotalAvailable.signum() <= 0)
		{
			return LUQtys.NULL;
		}
		// Case: we have enough qty to fullfill one palet
		else if (qtyCUsTotalAvailable.compareTo(standardQtyCUsPerLU) >= 0)
		{
			qtyCUsPerLU = standardQtyCUsPerLU;
			qtyTUsPerLU = standardQtyTUsPerLU;
		}
		// Case: we DON'T have enough qty to fullfill one palet => take as much as we have
		else
		{
			// Make sure the case when we have an LU with an uncompleted TU is covered
			qtyCUsPerTU = qtyCUsPerTU.min(qtyCUsTotalAvailable);
			
			qtyCUsPerLU = qtyCUsTotalAvailable;
			qtyTUsPerLU = qtyCUsPerLU.divide(qtyCUsPerTU, 0, RoundingMode.UP);
		}

		//
		// Check the available TUs enforcement (if any)
		final BigDecimal qtyTUsTotalAvailable = getQtyTUsTotalAvailable();
		if (qtyTUsTotalAvailable != null)
		{
			// Case: we don't have enough TUs at all
			if (qtyTUsTotalAvailable.signum() <= 0)
			{
				return LUQtys.NULL;
			}
			// Case: we have enough TUs
			else if (qtyTUsTotalAvailable.compareTo(qtyTUsPerLU) >= 0)
			{
				// do nothing
			}
			// Case: we don't have enough palets => take only the available TUs
			else
			{

				qtyTUsPerLU = qtyTUsTotalAvailable;
				qtyCUsPerLU = qtyTUsPerLU.multiply(qtyCUsPerTU);
				qtyCUsPerLU = qtyCUsPerLU.min(qtyCUsTotalAvailable); // make sure is not greater than currently available CUs
			}
		}

		//
		// Create our LU
		final LUQtys lu = LUQtys.builder()
				.setQtyTUsPerLU(qtyTUsPerLU)
				.setQtyCUsPerTU(qtyCUsPerTU)
				.setQtyCUsPerLU_IfGreaterThanZero(qtyCUsPerLU)
				.build();

		// Adjust available quantity CUs and TUs
		subtractLU(lu);

		return lu;
	}

	public void subtractLU(final LUQtys lu)
	{
		Check.assumeNotNull(lu, "lu not null");
		if (lu == LUQtys.NULL)
		{
			return;
		}

		// Make sure we are not changing the "NULL" instance
		if (this == NULL)
		{
			return;
		}

		_qtyCUsTotalAvailable = _qtyCUsTotalAvailable.subtract(lu.getQtyCUsPerLU());
		if (_qtyTUsTotalAvailable != null)
		{
			_qtyTUsTotalAvailable = _qtyTUsTotalAvailable.subtract(lu.getQtyTUsPerLU());
		}
	}

	/**
	 * Starts building a new {@link LUQtys} and when {@link LUQtysBuilder#build()} is called the build {@link LUQtys} will be automatically subtracted.
	 * 
	 * @return {@link LUQtysBuilder}.
	 */
	public LUQtysBuilder subtractLU()
	{
		return new LUQtysBuilder()
		{
			@Override
			public LUQtys build()
			{
				final LUQtys lu = super.build();
				subtractLU(lu);
				return lu;
			}
		};
	}

	public List<LUQtys> subtractAllLUs()
	{
		// NOTE: this method is not performant at all because it's complexity grows based on how much qty available we have and which is the LU/TU capacity.
		// At the moment this is enough, but if we need performance here we need to: estimate how many complete LUs do we have, take them out and apply the one-by-one calculation only for remaining.

		final List<LUQtys> luQtys = new ArrayList<>();
		if (isInfiniteCapacity())
		{
			return luQtys;
		}

		while (hasAvailableQty())
		{
			final LUQtys luQty = subtractOneLU();
			if (luQty == LUQtys.NULL)
			{
				break;
			}

			luQtys.add(luQty);
		}

		return luQtys;
	}

	public boolean hasAvailableQty()
	{
		if (this == NULL)
		{
			return false;
		}

		final BigDecimal qtyCUsTotalAvailable = getQtyCUsTotalAvailable();
		if (qtyCUsTotalAvailable.signum() <= 0)
		{
			return false;
		}

		final BigDecimal qtyTUsTotalAvailable = getQtyTUsTotalAvailable();
		if (qtyTUsTotalAvailable != null && qtyTUsTotalAvailable.signum() <= 0)
		{
			return false;
		}

		return true;
	}

	public boolean isInfiniteCapacity()
	{
		return infiniteCapacity;
	}

	public int getAvailableLUs()
	{
		if (!hasAvailableQty())
		{
			return 0;
		}
		if (isInfiniteCapacity())
		{
			return 0;
		}

		final int qtyLUs = _qtyCUsTotalAvailable.divide(standardQtyCUsPerLU, 0, RoundingMode.UP).intValueExact();
		return qtyLUs;
	}

	/**
	 * Simplified LU structure from quantities perspective: Qty TUs/LU, Qty CUs/TU and Qtys CUs/LU (usefull in case of incomplete TUs).
	 * 
	 * @author tsa
	 *
	 */
	public static final class LUQtys
	{
		public static final LUQtys NULL = new LUQtys();

		public static LUQtysBuilder builder()
		{
			return new LUQtysBuilder();
		}

		private final BigDecimal qtyTUsPerLU;
		private final BigDecimal qtyCUsPerTU;
		private final BigDecimal qtyCUsPerLU;

		public LUQtys(final LUQtysBuilder builder)
		{
			qtyTUsPerLU = builder.getQtyTUsPerLU();
			qtyCUsPerTU = builder.getQtyCUsPerTU();
			qtyCUsPerLU = builder.getQtyCUsPerLU();
		}

		/** Null constructor */
		private LUQtys()
		{
			qtyTUsPerLU = BigDecimal.ZERO;
			qtyCUsPerTU = BigDecimal.ZERO;
			qtyCUsPerLU = BigDecimal.ZERO;
		}

		@Override
		public String toString()
		{
			return ObjectUtils.toString(this);
		}

		@Override
		public int hashCode()
		{
			return new HashcodeBuilder()
					.append(qtyTUsPerLU)
					.append(qtyCUsPerTU)
					.append(qtyCUsPerLU)
					.toHashcode();
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
			{
				return true;
			}

			final LUQtys other = EqualsBuilder.getOther(this, obj);
			if (other == null)
			{
				return false;
			}
			return new EqualsBuilder()
					.append(this.qtyCUsPerLU, other.qtyCUsPerLU)
					.append(this.qtyCUsPerTU, other.qtyCUsPerTU)
					.append(this.qtyTUsPerLU, other.qtyTUsPerLU)
					.isEqual();
		}

		public boolean isNull()
		{
			return this == NULL;
		}

		public BigDecimal getQtyTUsPerLU()
		{
			return qtyTUsPerLU;
		}

		public BigDecimal getQtyCUsPerTU()
		{
			return qtyCUsPerTU;
		}

		public BigDecimal getQtyCUsPerLU()
		{
			return qtyCUsPerLU;
		}

		public static class LUQtysBuilder
		{
			private BigDecimal qtyTUsPerLU;
			private BigDecimal qtyCUsPerTU;
			private BigDecimal qtyCUsPerLU;

			private LUQtysBuilder()
			{
				super();
			}

			public LUQtys build()
			{
				if (getQtyTUsPerLU().signum() <= 0 || getQtyCUsPerTU().signum() <= 0 || getQtyCUsPerLU().signum() <= 0)
				{
					return LUQtys.NULL;
				}

				return new LUQtys(this);
			}

			private BigDecimal getQtyTUsPerLU()
			{
				Check.assumeNotNull(qtyTUsPerLU, "qtyTUsPerLU not null");
				return qtyTUsPerLU;
			}

			public LUQtysBuilder setQtyTUsPerLU(final BigDecimal qtyTUsPerLU)
			{
				this.qtyTUsPerLU = NumberUtils.stripTrailingDecimalZeros(qtyTUsPerLU);
				return this;
			}

			public LUQtysBuilder setQtyTUsPerLU(final int qtyTUs)
			{
				return setQtyTUsPerLU(BigDecimal.valueOf(qtyTUs));
			}

			private BigDecimal getQtyCUsPerTU()
			{
				Check.assumeNotNull(qtyCUsPerTU, "qtyCUsPerTU not null");
				return qtyCUsPerTU;
			}

			public LUQtysBuilder setQtyCUsPerTU(final BigDecimal qtyCUsPerTU)
			{
				this.qtyCUsPerTU = NumberUtils.stripTrailingDecimalZeros(qtyCUsPerTU);
				return this;
			}

			public LUQtysBuilder setQtyCUsPerTU(final int qtyCUsPerTU)
			{
				return setQtyCUsPerTU(BigDecimal.valueOf(qtyCUsPerTU));
			}

			/**
			 * Sets quantity CUs/LU.
			 * 
			 * Use it only if you have incomplete TUs, else it would be calculated as QtyCUsPerLU = QtyCUsPerTU * QtyTUs.
			 * 
			 * @param qtyCUsPerLU
			 */
			public LUQtysBuilder setQtyCUsPerLU_IfGreaterThanZero(BigDecimal qtyCUsPerLU)
			{
				if (qtyCUsPerLU == null || qtyCUsPerLU.signum() <= 0)
				{
					return this;
				}

				this.qtyCUsPerLU = NumberUtils.stripTrailingDecimalZeros(qtyCUsPerLU);
				return this;
			}

			private BigDecimal getQtyCUsPerLU()
			{
				if (qtyCUsPerLU == null)
				{
					return getQtyCUsPerTU().multiply(getQtyTUsPerLU());
				}
				return qtyCUsPerLU;
			}

		}
	}

	/** {@link TotalQtyCUBreakdownCalculator} builder */
	public static final class Builder
	{
		private BigDecimal qtyCUsTotal;
		private BigDecimal qtyTUsTotal;
		private BigDecimal qtyCUsPerTU;
		private BigDecimal qtyTUsPerLU;

		private Builder()
		{
			super();
		}

		public TotalQtyCUBreakdownCalculator build()
		{
			return new TotalQtyCUBreakdownCalculator(this);
		}

		public Builder setQtyCUsTotal(final BigDecimal qtyCUsTotal)
		{
			this.qtyCUsTotal = qtyCUsTotal;
			return this;
		}

		public BigDecimal getQtyCUsTotal()
		{
			Check.assumeNotNull(qtyCUsTotal, "qtyCUsTotal not null");
			return qtyCUsTotal;
		}

		public Builder setQtyTUsTotal(final BigDecimal qtyTUsTotal)
		{
			this.qtyTUsTotal = qtyTUsTotal;
			return this;
		}

		public BigDecimal getQtyTUsTotal()
		{
			if (qtyTUsTotal == null)
			{
				return Quantity.QTY_INFINITE;
			}
			return qtyTUsTotal;
		}

		public Builder setStandardQtyCUsPerTU(final BigDecimal qtyCUsPerTU)
		{
			this.qtyCUsPerTU = qtyCUsPerTU;
			return this;
		}

		public BigDecimal getStandardQtyCUsPerTU()
		{
			return qtyCUsPerTU;
		}

		public Builder setStandardQtyTUsPerLU(final BigDecimal qtyTUsPerLU)
		{
			this.qtyTUsPerLU = qtyTUsPerLU;
			return this;
		}

		public Builder setStandardQtyTUsPerLU(final int qtyTUsPerLU)
		{
			return setStandardQtyTUsPerLU(BigDecimal.valueOf(qtyTUsPerLU));
		}

		public BigDecimal getStandardQtyTUsPerLU()
		{
			return qtyTUsPerLU;
		}

		public Builder setStandardQtysAsInfinite()
		{
			setStandardQtyCUsPerTU(BigDecimal.ZERO);
			setStandardQtyTUsPerLU(BigDecimal.ZERO);
			return this;
		}
	}
}
