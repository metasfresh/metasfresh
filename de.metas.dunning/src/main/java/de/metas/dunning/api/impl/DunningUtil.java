package de.metas.dunning.api.impl;

/*
 * #%L
 * de.metas.dunning
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
import java.sql.Timestamp;
import java.util.Properties;

import de.metas.currency.ICurrencyBL;
import de.metas.dunning.api.IDunningUtil;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.util.Services;

public class DunningUtil implements IDunningUtil
{
	@Override
	public BigDecimal currencyConvert(
			Properties ctx,
			BigDecimal Amt,
			int CurFrom_ID,
			int CurTo_ID,
			Timestamp ConvDate,
			CurrencyConversionTypeId conversionTypeId,
			int AD_Client_ID,
			int AD_Org_ID)
	{
		return Services.get(ICurrencyBL.class).convert(
				ctx,
				Amt,
				CurFrom_ID,
				CurTo_ID,
				ConvDate,
				CurrencyConversionTypeId.toRepoId(conversionTypeId),
				AD_Client_ID,
				AD_Org_ID);
	}

	@Override
	public CurrencyConversionTypeId getDefaultCurrencyConvertionTypeId()
	{
		return null;
	}
}
