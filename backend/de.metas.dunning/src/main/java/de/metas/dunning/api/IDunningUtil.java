package de.metas.dunning.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.util.ISingletonService;

/**
 * Misc Dunning Utils which are referencing other projects.
 * 
 * We use this as a border interface for integration with other modules. Also this is useful, because we can switch the implementation when we do isolate testings.
 * 
 * @author tsa
 * 
 */
public interface IDunningUtil extends ISingletonService
{
	/**
	 * Delegate to {@link ICurrencyBL#convert(Properties, BigDecimal, int, int, Timestamp, int, int, int)}.
	 * 
	 * @param ctx
	 * @param Amt
	 * @param CurFrom_ID
	 * @param CurTo_ID
	 * @param ConvDate
	 * @param C_ConversionType_ID
	 * @param AD_Client_ID
	 * @param AD_Org_ID
	 * @return
	 */
	BigDecimal currencyConvert(BigDecimal Amt, int CurFrom_ID, int CurTo_ID, Timestamp ConvDate, CurrencyConversionTypeId conversionTypeId, int AD_Client_ID, int AD_Org_ID);

	/**
	 * Returns a placeholder value for C_ConversionType_ID.
	 * 
	 * NOTE: is not guaranteed that a valid ID will be returned
	 * 
	 * @return C_ConversionType_ID placeholder
	 */
	CurrencyConversionTypeId getDefaultCurrencyConvertionTypeId();

}
