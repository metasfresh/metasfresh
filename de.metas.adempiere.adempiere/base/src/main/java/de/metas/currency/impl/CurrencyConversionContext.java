package de.metas.currency.impl;

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

import java.text.DateFormat;
import java.util.Date;

import org.compiere.util.DisplayType;

import com.google.common.base.MoreObjects;

import de.metas.currency.ICurrencyConversionContext;
import de.metas.util.Check;

public final class CurrencyConversionContext implements ICurrencyConversionContext
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final Date conversionDate;
	private final int conversionTypeId;
	private final int adClientId;
	private final int adOrgId;

	private CurrencyConversionContext(final Builder builder)
	{
		super();

		Check.assumeNotNull(builder.conversionDate, "builder.conversionDate not null");
		this.conversionDate = (Date)builder.conversionDate.clone();
		
		Check.assume(builder.conversionTypeId != null && builder.conversionTypeId > 0, "valid conversionTypeId but it was {}", builder.conversionTypeId);
		this.conversionTypeId = builder.conversionTypeId;

		Check.assumeNotNull(builder.adClientId, "builder.adClientId not null");
		this.adClientId = builder.adClientId;
		
		Check.assumeNotNull(builder.adOrgId, "builder.adOrgId not null");
		this.adOrgId = builder.adOrgId;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("date", conversionDate)
				.add("conversionTypeId", conversionTypeId)
				.add("AD_Client_ID", adClientId)
				.add("AD_Org_ID", adOrgId)
				.toString();
	}
	
	@Override
	public String getSummary()
	{
		// NOTE: keep it short because we want to append it to Fact_Acct.Description
		
		final DateFormat dateFormat = DisplayType.getDateFormat(DisplayType.Date);
		return MoreObjects.toStringHelper(this)
				.add("Date", dateFormat.format(conversionDate))
				.add("C_ConversionType_ID", conversionTypeId)
				.toString();
	}

	@Override
	public Date getConversionDate()
	{
		return (Date)conversionDate.clone();
	}

	@Override
	public int getC_ConversionType_ID()
	{
		return conversionTypeId;
	}

	@Override
	public int getAD_Client_ID()
	{
		return adClientId;
	}

	@Override
	public int getAD_Org_ID()
	{
		return adOrgId;
	}

	public static final class Builder
	{
		private Date conversionDate;
		private Integer conversionTypeId;
		private Integer adClientId;
		private Integer adOrgId;

		private Builder()
		{
			super();
		}
		
		public CurrencyConversionContext build()
		{
			return new CurrencyConversionContext(this);
		}

		public Builder setConversionDate(Date conversionDate)
		{
			this.conversionDate = conversionDate;
			return this;
		}

		public Builder setC_ConversionType_ID(int conversionTypeId)
		{
			this.conversionTypeId = conversionTypeId;
			return this;
		}

		public Builder setAD_Client_ID(int adClientId)
		{
			this.adClientId = adClientId;
			return this;
		}

		public Builder setAD_Org_ID(int adOrgId)
		{
			this.adOrgId = adOrgId;
			return this;
		}
	}
}
