package de.metas.acct.vatcode;

import java.util.Date;

import com.google.common.base.MoreObjects;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public final class VATCodeMatchingRequest
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final int C_AcctSchema_ID;
	private final int C_Tax_ID;
	private final boolean isSOTrx;
	private final Date date;

	private VATCodeMatchingRequest(final Builder builder)
	{
		super();

		C_AcctSchema_ID = builder.C_AcctSchema_ID;
		C_Tax_ID = builder.C_Tax_ID;
		isSOTrx = builder.isSOTrx;
		date = (Date)builder.date.clone();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("C_AcctSchema_ID", C_AcctSchema_ID)
				.add("C_Tax_ID", C_Tax_ID)
				.add("IsSOTrx", isSOTrx)
				.add("Date", date)
				.toString();
	}

	public int getC_AcctSchema_ID()
	{
		return C_AcctSchema_ID;
	}

	public int getC_Tax_ID()
	{
		return C_Tax_ID;
	}

	public boolean isSOTrx()
	{
		return isSOTrx;
	}

	public Date getDate()
	{
		return (Date)date.clone();
	}

	public static final class Builder
	{
		private Integer C_AcctSchema_ID;
		private Integer C_Tax_ID;
		private Boolean isSOTrx;
		private Date date;

		private Builder()
		{
			super();
		}

		public VATCodeMatchingRequest build()
		{
			return new VATCodeMatchingRequest(this);
		}

		public Builder setC_AcctSchema_ID(final int C_AcctSchema_ID)
		{
			this.C_AcctSchema_ID = C_AcctSchema_ID;
			return this;
		}

		public Builder setC_Tax_ID(final int C_Tax_ID)
		{
			this.C_Tax_ID = C_Tax_ID;
			return this;
		}

		public Builder setIsSOTrx(final boolean isSOTrx)
		{
			this.isSOTrx = isSOTrx;
			return this;
		}

		public Builder setDate(final Date date)
		{
			this.date = date;
			return this;
		}
	}
}
