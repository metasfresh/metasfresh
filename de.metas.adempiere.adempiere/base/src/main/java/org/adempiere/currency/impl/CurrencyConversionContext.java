package org.adempiere.currency.impl;

/*
 * #%L
 * ADempiere ERP - Base
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


import java.util.Date;

import org.adempiere.currency.ICurrencyConversionContext;
import org.adempiere.util.lang.ObjectUtils;

public class CurrencyConversionContext implements ICurrencyConversionContext
{
	private Date conversionDate;
	private int conversionTypeId = -1;
	private int adClientId = -1;
	private int adOrgId;

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public Date getConversionDate()
	{
		return conversionDate;
	}

	// @Override
	public void setConversionDate(Date conversionDate)
	{
		this.conversionDate = conversionDate;
	}

	@Override
	public int getC_ConversionType_ID()
	{
		return conversionTypeId;
	}

	// @Override
	public void setC_ConversionType_ID(int conversionTypeId)
	{
		this.conversionTypeId = conversionTypeId;
	}

	@Override
	public int getAD_Client_ID()
	{
		return adClientId;
	}

	// @Override
	public void setAD_Client_ID(int adClientId)
	{
		this.adClientId = adClientId;
	}

	@Override
	public int getAD_Org_ID()
	{
		return adOrgId;
	}

	// @Override
	public void setAD_Org_ID(int adOrgId)
	{
		this.adOrgId = adOrgId;
	}

}
