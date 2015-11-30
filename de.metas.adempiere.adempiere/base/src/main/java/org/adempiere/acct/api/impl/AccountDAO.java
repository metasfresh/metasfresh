package org.adempiere.acct.api.impl;

import java.util.Properties;

import org.adempiere.acct.api.IAccountDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.MAccount;

import de.metas.adempiere.util.CacheCtx;

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

public class AccountDAO implements IAccountDAO
{
	@Override
	@Cached(cacheName = MAccount.Table_Name)
	public MAccount retrieveAccountById(@CacheCtx final Properties ctx, final int validCombinationId)
	{
		if (validCombinationId <= 0)
		{
			return null;
		}
		return new MAccount(ctx, validCombinationId, ITrx.TRXNAME_None);
	}
}
