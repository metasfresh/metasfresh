package org.adempiere.util.api.impl;

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


import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.util.api.IMsgDAO;
import org.compiere.util.DB;

public class MsgDAO implements IMsgDAO
{

	private transient Logger log = LogManager.getLogger(getClass());

	@Override
	public boolean isMessageExists(final String adMessage)
	{
		final int AD_Message_ID = DB.getSQLValue(null, "SELECT AD_Message_ID FROM AD_Message WHERE Value=?", adMessage);
		return AD_Message_ID > 0;
	}
	
	@Override
	public int retrieveMessageId(final String adMessage)
	{
		final int AD_Message_ID = DB.getSQLValue(null, "SELECT AD_Message_ID FROM AD_Message WHERE Value=?", adMessage);
		if (AD_Message_ID != -1)
			return AD_Message_ID;
		else
		{
			log.error("setAD_Message_ID - ID not found for '" + adMessage + "'");
			return 240;
		}
	}
}
