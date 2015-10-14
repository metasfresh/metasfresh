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


import java.util.HashMap;
import java.util.Map;

import org.adempiere.util.api.IMsgDAO;

public class PlainMsgDAO implements IMsgDAO
{

	private Map<String, Boolean> adMessage2MsgExists = new HashMap<String, Boolean>();

	private Map<String, Integer> adMessage2MsgID = new HashMap<String, Integer>();

	@Override
	public int retrieveMessageId(String adMessage)
	{
		final Integer messageId = adMessage2MsgID.get(adMessage);
		return messageId != null ? messageId : 240; // 240 is currently what the real implementation returns on missing msg.
	}

	@Override
	public boolean isMessageExists(String adMessage)
	{
		final Boolean messageExists = adMessage2MsgExists.get(adMessage);
		return messageExists != null ? messageExists : false;
	}

	/**
	 * Method can be used to set up the desired behavior for tests.
	 * 
	 * @param adMessage
	 * @param messageId
	 */
	public void putMessageId(final String adMessage, final int messageId)
	{
		adMessage2MsgID.put(adMessage, messageId);
	}

	/**
	 * Method can be used to set up the desired behavior for tests.
	 * 
	 * @param adMessage
	 * @param messageExists
	 */
	public void putMessageExists(final String adMessage, final boolean messageExists)
	{
		adMessage2MsgExists.put(adMessage, messageExists);
	}
}
