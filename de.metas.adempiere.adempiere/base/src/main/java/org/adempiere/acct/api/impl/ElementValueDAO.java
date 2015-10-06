package org.adempiere.acct.api.impl;

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


import java.util.Properties;

import org.adempiere.acct.api.IElementValueDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_ElementValue;

import de.metas.adempiere.util.CacheCtx;

public class ElementValueDAO implements IElementValueDAO
{
	@Cached(cacheName = I_C_ElementValue.Table_Name, expireMinutes = Cached.EXPIREMINUTES_Never)
	@Override
	public I_C_ElementValue retrieveById(@CacheCtx final Properties ctx, final int elementValueId)
	{
		if (elementValueId <= 0)
		{
			return null;
		}
		return InterfaceWrapperHelper.create(ctx, elementValueId, I_C_ElementValue.class, ITrx.TRXNAME_None);
	}
}
