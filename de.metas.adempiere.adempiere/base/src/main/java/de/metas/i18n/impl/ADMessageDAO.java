package de.metas.i18n.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;
import java.util.function.Consumer;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Message;
import org.compiere.model.X_AD_Message;
import org.compiere.util.Env;

import de.metas.adempiere.util.CacheCtx;
import de.metas.i18n.IADMessageDAO;

public class ADMessageDAO implements IADMessageDAO
{
	@Override
	@Cached(cacheName = I_AD_Message.Table_Name + "#by#" + I_AD_Message.COLUMNNAME_Value)
	public I_AD_Message retrieveByValue(@CacheCtx final Properties ctx, final String value)
	{
		final IQueryBuilder<I_AD_Message> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Message.class, ctx, ITrx.TRXNAME_None);

		final ICompositeQueryFilter<I_AD_Message> filters = queryBuilder.getFilters();
		filters.addEqualsFilter(I_AD_Message.COLUMNNAME_Value, value);
		filters.addOnlyActiveRecordsFilter();

		return queryBuilder.create()
				.firstOnly(I_AD_Message.class);
	}

	@Override
	public int retrieveIdByValue(@CacheCtx final Properties ctx, final String value)
	{
		final I_AD_Message msg = retrieveByValue(ctx, value);
		if (msg == null)
			return 0;
		return msg.getAD_Message_ID();

	}

	@Override
	@Cached(cacheName = I_AD_Message.Table_Name + "#by#" + I_AD_Message.COLUMNNAME_AD_Message_ID)
	public I_AD_Message retrieveById(@CacheCtx final Properties ctx, final int adMessageId)
	{
		if (adMessageId <= 0)
		{
			return null;
		}

		return InterfaceWrapperHelper.create(ctx, adMessageId, I_AD_Message.class, ITrx.TRXNAME_None);
	}
	
	@Override
	public boolean isMessageExists(final String adMessage)
	{
		final int adMessageId = retrieveIdByValue(Env.getCtx(), adMessage);
		return adMessageId > 0;
	}

	@Override
	public void createUpdateMessage(final String adMessageKey, final Consumer<I_AD_Message> adMessageUpdater)
	{
		try
		{
			I_AD_Message adMessage = Services.get(IQueryBL.class)
					.createQueryBuilder(I_AD_Message.class)
					.addEqualsFilter(I_AD_Message.COLUMNNAME_Value, adMessageKey)
					.create()
					.firstOnly(I_AD_Message.class);

			if (adMessage == null)
			{
				adMessage = InterfaceWrapperHelper.newInstance(I_AD_Message.class);
				adMessage.setValue(adMessageKey);
				adMessage.setMsgType(X_AD_Message.MSGTYPE_Information);
				adMessage.setEntityType("U"); // User maintained
			}

			adMessageUpdater.accept(adMessage);

			InterfaceWrapperHelper.save(adMessage);
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex)
					.setParameter("AD_Message", adMessageKey);
		}
	}
}
