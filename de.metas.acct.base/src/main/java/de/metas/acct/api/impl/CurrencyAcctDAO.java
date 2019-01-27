package de.metas.acct.api.impl;

import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_Currency_Acct;

import de.metas.acct.api.ICurrencyAcctDAO;
import de.metas.cache.annotation.CacheCtx;
import de.metas.util.Services;

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

public class CurrencyAcctDAO implements ICurrencyAcctDAO
{
	@Override
	@Cached(cacheName = I_C_Currency_Acct.Table_Name + "#by#C_Currency_ID#C_AcctSchema_ID")
	public I_C_Currency_Acct get(@CacheCtx final Properties ctx, final int C_Currency_ID, final int C_AcctSchema_ID)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Currency_Acct.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_Currency_Acct.COLUMN_C_Currency_ID, C_Currency_ID)
				.addEqualsFilter(I_C_Currency_Acct.COLUMN_C_AcctSchema_ID, C_AcctSchema_ID)
				.create()
				.firstOnly(I_C_Currency_Acct.class);
	}	// get

}
