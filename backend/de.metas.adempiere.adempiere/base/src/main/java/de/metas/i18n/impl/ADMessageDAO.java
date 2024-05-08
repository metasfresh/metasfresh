package de.metas.i18n.impl;

import java.util.Optional;

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
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Message;
import org.compiere.model.X_AD_Message;
import org.compiere.util.Env;

import de.metas.cache.annotation.CacheCtx;
import de.metas.i18n.AdMessageId;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IADMessageDAO;
import de.metas.util.Services;

public class ADMessageDAO implements IADMessageDAO
{
	@Override
	@Cached(cacheName = I_AD_Message.Table_Name + "#by#" + I_AD_Message.COLUMNNAME_Value)
	public Optional<I_AD_Message> retrieveByValue(@CacheCtx final Properties ctx, final AdMessageKey value)
	{
		final IQueryBuilder<I_AD_Message> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Message.class, ctx, ITrx.TRXNAME_None);

		final ICompositeQueryFilter<I_AD_Message> filters = queryBuilder.getCompositeFilter();
		filters.addEqualsFilter(I_AD_Message.COLUMNNAME_Value, value.toAD_Message());
		filters.addOnlyActiveRecordsFilter();

		return Optional.ofNullable(queryBuilder.create()
				.firstOnly(I_AD_Message.class));
	}

	@Override
	public Optional<AdMessageId> retrieveIdByValue(final Properties ctx, final AdMessageKey value)
	{
		return retrieveByValue(ctx, value)
				.map(message -> AdMessageId.ofRepoId(message.getAD_Message_ID()));

	}

	@Override
	@Cached(cacheName = I_AD_Message.Table_Name + "#by#" + I_AD_Message.COLUMNNAME_AD_Message_ID)
	public Optional<I_AD_Message> retrieveById(@CacheCtx final Properties ctx, final AdMessageId adMessageId)
	{
		if (adMessageId == null)
		{
			return null;
		}

		return Optional.ofNullable(InterfaceWrapperHelper.loadOutOfTrx(adMessageId, I_AD_Message.class));
	}

	@Override
	public boolean isMessageExists(final AdMessageKey adMessage)
	{
		return retrieveIdByValue(Env.getCtx(), adMessage).isPresent();
	}

	@Override
	public void createUpdateMessage(final AdMessageKey adMessageKey, final Consumer<I_AD_Message> adMessageUpdater)
	{
		try
		{
			I_AD_Message adMessage = Services.get(IQueryBL.class)
					.createQueryBuilder(I_AD_Message.class)
					.addEqualsFilter(I_AD_Message.COLUMNNAME_Value, adMessageKey.toAD_Message())
					.create()
					.firstOnly(I_AD_Message.class);

			if (adMessage == null)
			{
				adMessage = InterfaceWrapperHelper.newInstance(I_AD_Message.class);
				adMessage.setValue(adMessageKey.toAD_Message());
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
