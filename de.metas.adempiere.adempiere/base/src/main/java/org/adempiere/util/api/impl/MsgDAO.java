package org.adempiere.util.api.impl;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgDAO;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Message;

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

import org.slf4j.Logger;

import de.metas.logging.LogManager;
import lombok.NonNull;

public class MsgDAO implements IMsgDAO
{

	private final static transient Logger log = LogManager.getLogger(MsgDAO.class);

	@Override
	public boolean isMessageExists(@NonNull final String adMessage)
	{
		return mkMsgQuery(adMessage).match();
	}

	private IQuery<I_AD_Message> mkMsgQuery(@NonNull final String adMessage)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_Message.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Message.COLUMNNAME_Value, adMessage)
				.create();
	}

	@Override
	public int retrieveMessageId(@NonNull final String adMessage)
	{
		final int AD_Message_ID = mkMsgQuery(adMessage).firstIdOnly();
		if (AD_Message_ID != -1)
		{
			return AD_Message_ID;
		}
		else
		{
			log.error("setAD_Message_ID - ID not found for '" + adMessage + "'");
			return 240;
		}
	}

	@Override
	public I_AD_Message retrieveMessage(@NonNull final String adMessage)
	{
		return mkMsgQuery(adMessage).firstOnly(I_AD_Message.class);
	}
}
