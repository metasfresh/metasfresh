package org.adempiere.util.api.impl;

/*
 * #%L
 * de.metas.util
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


import java.util.Arrays;
import java.util.Properties;

import org.adempiere.util.api.IMsgBL;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;

public class PlainMsgBL implements IMsgBL
{
	@Override
	public String getMsg(final String language, final String message, final Object[] params)
	{
		return language + "_" + message + "_" + Arrays.toString(params);
	}

	@Override
	public String getMsg(Properties ctx, String adMessage)
	{
		return adMessage;
	}

	@Override
	public String getMsg(Properties ctx, String adMessage, boolean text)
	{
		return adMessage + "_" + (text ? "Text" : "Tooltip");
	}

	@Override
	public String getMsg(Properties ctx, String adMessage, Object[] params)
	{
		return adMessage + "_" + Arrays.toString(params);
	}

	@Override
	public String parseTranslation(final Properties ctx, final String message)
	{
		return message;
	}

	@Override
	public String translate(final Properties ctx, final String text)
	{
		return text;
	}

	@Override
	public String translate(Properties ctx, String text, boolean isSOTrx)
	{
		return text;
	}

	@Override
	public ITranslatableString getTranslatableMsgText(String adMessage, Object... msgParameters)
	{
		return ImmutableTranslatableString.constant(adMessage);
	}
}
