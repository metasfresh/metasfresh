/**
 *
 */
package de.metas.currency;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_ConversionType;
import org.compiere.model.I_C_Currency;
import org.compiere.util.Env;

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
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface ICurrencyDAO extends ISingletonService
{
	/**
	 * retrieves Currency by ID
	 *
	 * @param ctx
	 * @param currencyId
	 * @return
	 */
	I_C_Currency retrieveCurrency(Properties ctx, int currencyId);

	/**
	 * retrieves currency by ISO code
	 *
	 * @param ctx
	 * @param ISOCode
	 * @return currency or <code>null</code>
	 */
	I_C_Currency retrieveCurrencyByISOCode(Properties ctx, String ISOCode);

	/**
	 * Get Currency Iso Code.
	 *
	 * @param ctx Context
	 * @param C_Currency_ID currency
	 * @return ISO Code or <code>null</code>
	 */
	String getISO_Code(Properties ctx, int C_Currency_ID);

	/**
	 * Get Standard Precision.
	 *
	 * @param ctx Context
	 * @param C_Currency_ID currency
	 * @return Standard Precision
	 */
	int getStdPrecision(Properties ctx, int C_Currency_ID);

	default int getStdPrecision(int C_Currency_ID)
	{
		return getStdPrecision(Env.getCtx(), C_Currency_ID);
	}

	/**
	 * @param ctx
	 * @param adClientId
	 * @param adOrgId
	 * @param date
	 * @return default {@link I_C_ConversionType}; never returns null
	 */
	I_C_ConversionType retrieveDefaultConversionType(Properties ctx, int adClientId, int adOrgId, Date date);

	/**
	 * @return conversion type of given {@link ConversionType}; never returns null
	 */
	I_C_ConversionType retrieveConversionType(Properties ctx, ConversionType type);

	BigDecimal retrieveRateOrNull(ICurrencyConversionContext conversionCtx, int CurFrom_ID, int CurTo_ID);
}
