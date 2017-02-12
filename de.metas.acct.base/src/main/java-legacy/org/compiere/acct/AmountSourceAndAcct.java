package org.compiere.acct;

import java.math.BigDecimal;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Immutable helper class which stores the a pair of amounts in source currency and in accounted currency.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
final class AmountSourceAndAcct
{
	public static final Builder builder()
	{
		return new Builder();
	}

	public static final AmountSourceAndAcct of(final BigDecimal amtSource, final BigDecimal amtAcct)
	{
		return builder()
				.setAmtSource(amtSource)
				.setAmtAcct(amtAcct)
				.build();
	}

	public static final AmountSourceAndAcct ZERO = of(BigDecimal.ZERO, BigDecimal.ZERO);

	private final BigDecimal amtSource;
	private final BigDecimal amtAcct;

	private AmountSourceAndAcct(final Builder builder)
	{
		super();
		amtSource = builder.amtSource;
		Check.assumeNotNull(amtSource, "amtSource not null");
		amtAcct = builder.amtAcct;
		Check.assumeNotNull(amtAcct, "amtAcct not null");
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("source", amtSource)
				.add("acct", amtAcct)
				.toString();
	}

	public BigDecimal getAmtSource()
	{
		return amtSource;
	}

	public BigDecimal getAmtAcct()
	{
		return amtAcct;
	}

	public static final class Builder
	{
		private BigDecimal amtSource = BigDecimal.ZERO;
		private BigDecimal amtAcct = BigDecimal.ZERO;

		private Builder()
		{
			super();
		}

		public final AmountSourceAndAcct build()
		{
			return new AmountSourceAndAcct(this);
		}

		public Builder setAmtSource(final BigDecimal amtSource)
		{
			this.amtSource = amtSource;
			return this;
		}

		public Builder setAmtAcct(final BigDecimal amtAcct)
		{
			this.amtAcct = amtAcct;
			return this;
		}

		public Builder addAmtSource(final BigDecimal amtSourceToAdd)
		{
			amtSource = amtSource.add(amtSourceToAdd);
			return this;
		}

		public Builder addAmtAcct(final BigDecimal amtAcctToAdd)
		{
			amtAcct = amtAcct.add(amtAcctToAdd);
			return this;
		}

		public Builder add(final AmountSourceAndAcct amtSourceAndAcctToAdd)
		{
			// Optimization: do nothing if zero
			if (amtSourceAndAcctToAdd == ZERO)
			{
				return this;
			}
			
			addAmtSource(amtSourceAndAcctToAdd.getAmtSource());
			addAmtAcct(amtSourceAndAcctToAdd.getAmtAcct());
			return this;
		}
	}
}