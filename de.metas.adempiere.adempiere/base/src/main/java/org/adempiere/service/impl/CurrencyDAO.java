/**
 *
 */
package org.adempiere.service.impl;

import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ICurrencyDAO;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Currency;
import org.compiere.util.CCache;

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
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public class CurrencyDAO implements ICurrencyDAO
{
	/** Store System Currencies **/
	private static CCache<Integer, I_C_Currency> s_currencies = new CCache<Integer, I_C_Currency>("C_Currency", 50);
	/** Cache System Currencies by using ISO code as key **/
	private static CCache<String, I_C_Currency> s_currenciesISO = new CCache<String, I_C_Currency>("C_CurrencyISO", 50);

	@Override
	public I_C_Currency retrieveCurrency(Properties ctx, int currencyId)
	{
		if (currencyId <= 0)
		{
			return null;
		}

		// Try Cache
		Integer key = new Integer(currencyId);
		I_C_Currency retValue = s_currencies.get(key);
		if (retValue != null)
		{
			return retValue;
		}

		// Create it
		retValue = InterfaceWrapperHelper.create(ctx, currencyId, I_C_Currency.class, ITrx.TRXNAME_None);
		// Save in System
		if (retValue.getAD_Client_ID() == 0)
		{
			s_currencies.put(key, retValue);
		}
		return retValue;
	}

	@Override
	public I_C_Currency retrieveCurrencyByISOCode(Properties ctx, String ISOCode)
	{
		// Try Cache

		I_C_Currency retValue = s_currenciesISO.get(ISOCode);
		if (retValue != null)
		{
			return retValue;
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_C_Currency> queryBuilder = queryBL.createQueryBuilder(I_C_Currency.class, ctx, ITrx.TRXNAME_None);

		// ordering by value to make it easier for the user to browse the logging.
		final IQueryOrderBy orderBy = queryBuilder.orderBy().addColumn(I_C_Currency.COLUMNNAME_ISO_Code).createQueryOrderBy();

		retValue = queryBuilder
				.filter(new EqualsQueryFilter<I_C_Currency>(I_C_Currency.COLUMNNAME_ISO_Code, ISOCode))
				.addOnlyActiveRecordsFilter()
				.create()
				.setOrderBy(orderBy)
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				.setOption(IQuery.OPTION_IteratorBufferSize, 500)
				.firstOnly(I_C_Currency.class);


		// Save
		if (retValue != null)
		{
			s_currenciesISO.put(ISOCode, retValue);
		}
		return retValue;
	}

	@Override
	public String getISO_Code (Properties ctx, int C_Currency_ID)
	{
		String contextKey = "C_Currency_" + C_Currency_ID;
		String retValue = ctx.getProperty(contextKey);
		if (retValue != null)
			return retValue;

		//	Create it
		final I_C_Currency c = retrieveCurrency(ctx, C_Currency_ID);
		retValue = c.getISO_Code();
		ctx.setProperty(contextKey, retValue);
		return retValue;
	}

	@Override
	public int getStdPrecision(Properties ctx, int C_Currency_ID)
	{
		final I_C_Currency c = retrieveCurrency(ctx, C_Currency_ID);
		if (c == null || c.getC_Currency_ID() <= 0)
		{
			return 2; // default
		}
		return c.getStdPrecision();
	}
}
