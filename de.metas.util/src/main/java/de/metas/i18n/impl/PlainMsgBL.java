package de.metas.i18n.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.google.common.collect.ImmutableMap;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;

public class PlainMsgBL implements IMsgBL
{
	@Override
	public String getMsg(final String adLanguage, final String message)
	{
		return adLanguage + "_" + message;
	}

	@Override
	public String getMsg(final String adLanguage, final String message, final Object[] params)
	{
		return adLanguage + "_" + message + "_" + Arrays.toString(params);
	}

	@Override
	public String getMsg(final Properties ctx, final String adMessage)
	{
		return adMessage;
	}

	@Override
	public String getMsg(final Properties ctx, final String adMessage, final boolean text)
	{
		return adMessage + "_" + (text ? "Text" : "Tooltip");
	}

	@Override
	public String getMsg(final Properties ctx, final String adMessage, final Object[] params)
	{
		if (params == null || params.length == 0)
		{
			return adMessage;
		}
		return adMessage + "_" + Arrays.toString(params);
	}

	@Override
	public String getMsg(final String adMessage, final List<Object> params)
	{
		if (params == null || params.isEmpty())
		{
			return adMessage;
		}
		return adMessage + "_" + params;
	}

	@Override
	public Map<String, String> getMsgMap(final String adLanguage, final String prefix, final boolean removePrefix)
	{
		return ImmutableMap.of();
	}

	@Override
	public String parseTranslation(final Properties ctx, final String message)
	{
		return message;
	}

	@Override
	public String parseTranslation(final String adLanguage, final String message)
	{
		return message;
	}

	@Override
	public String translate(final Properties ctx, final String text)
	{
		return text;
	}

	@Override
	public String translate(final String adLanguage, final String text)
	{
		return text;
	}

	@Override
	public String translate(final Properties ctx, final String text, final boolean isSOTrx)
	{
		return text;
	}

	@Override
	public ITranslatableString translatable(final String text)
	{
		return ImmutableTranslatableString.constant(text);
	}

	@Override
	public ITranslatableString getTranslatableMsgText(final String adMessage, final Object... msgParameters)
	{
		return ImmutableTranslatableString.constant(adMessage);
	}

	@Override
	public void cacheReset()
	{
		// nothing
	}
}
