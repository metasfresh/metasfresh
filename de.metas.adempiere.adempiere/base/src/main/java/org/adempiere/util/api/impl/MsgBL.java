package org.adempiere.util.api.impl;

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

import org.adempiere.util.api.IMsgBL;
import org.compiere.util.Msg;

/**
 * 
 * This implementation delegates to {@link Msg} and is therefore coupled with the database.
 * 
 */
@SuppressWarnings("deprecation")
public class MsgBL implements IMsgBL
{
	@Override
	public String getMsg(final String language, final String message, final Object[] params)
	{
		return Msg.getMsg(language, message, params);
	}
	
	@Override
	public String getMsg(final Properties ctx, final String adMessage)
	{
		return Msg.getMsg(ctx, adMessage);
	}
	
	@Override
	public String getMsg(Properties ctx, String adMessage, Object[] params)
	{
		return Msg.getMsg(ctx, adMessage, params);
	}

	@Override
	public String getMsg(Properties ctx, String adMessage, boolean text)
	{
		return Msg.getMsg(ctx, adMessage, text);
	}

	@Override
	public String translate(final Properties ctx, final String text)
	{
		return Msg.translate(ctx, text);
	}
	
	@Override
	public String translate(final Properties ctx, final String text, final boolean isSOTrx)
	{
		return Msg.translate(ctx, text, isSOTrx);
	}

	@Override
	public String parseTranslation(final Properties ctx, final String message)
	{
		return Msg.parseTranslation(ctx, message);
	}
}
