package de.metas.i18n.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class PlainADMessageDAO extends ADMessageDAO
{
	private Map<String, Boolean> adMessage2MsgExists = new HashMap<>();
	private Map<String, Integer> adMessage2MsgID = new HashMap<>();
	
	@Override
	public int retrieveIdByValue(Properties ctx, String adMessage)
	{
		final Integer messageId = adMessage2MsgID.get(adMessage);
		return messageId != null ? messageId : 240; // 240 is currently what the real implementation returns on missing msg.
	}
	
	@Override
	public boolean isMessageExists(final String adMessage)
	{
		final Boolean messageExists = adMessage2MsgExists.get(adMessage);
		return messageExists != null ? messageExists : false;
	}

}
