package de.metas.procurement.base.impl;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;

import de.metas.procurement.base.IPMMMessageDAO;
import de.metas.procurement.base.model.I_PMM_Message;
import de.metas.util.Check;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.procurement.base
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

public class PMMMessageDAO implements IPMMMessageDAO
{
	public List<I_PMM_Message> retrieveMessages(final Properties ctx)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_PMM_Message.class, ctx, ITrx.TRXNAME_ThreadInherited)
				.addOnlyActiveRecordsFilter()
				//
				.orderBy()
				.addColumn(I_PMM_Message.COLUMNNAME_PMM_Message_ID)
				.endOrderBy()
				//
				.create()
				.list();
	}

	@Override
	public String retrieveMessagesAsString(final Properties ctx)
	{
		final StringBuilder messages = new StringBuilder();
		for (final I_PMM_Message pmmMessage : retrieveMessages(ctx))
		{
			final String message = pmmMessage.getMsgText();
			if (Check.isEmpty(message, true))
			{
				continue;
			}

			if (messages.length() > 0)
			{
				messages.append("\n");
			}
			messages.append(message.trim());
		}

		return messages.toString();
	}
}
