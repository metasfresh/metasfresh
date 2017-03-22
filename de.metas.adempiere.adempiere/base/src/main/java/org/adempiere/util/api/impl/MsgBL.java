package org.adempiere.util.api.impl;

import java.util.List;

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

import java.util.Properties;
import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.util.api.IMsgBL;
import org.compiere.util.Msg;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;

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
	public String getMsg(final Properties ctx, final String adMessage, final Object[] params)
	{
		return Msg.getMsg(ctx, adMessage, params);
	}

	@Override
	public String getMsg(final Properties ctx, final String adMessage, final boolean text)
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
	public ITranslatableString translatable(final String text)
	{
		if(Check.isEmpty(text, true))
		{
			return ImmutableTranslatableString.constant(text);
		}
		return new ADElementOrADMessageTranslatableString(text);
	}

	@Override
	public String parseTranslation(final Properties ctx, final String message)
	{
		return Msg.parseTranslation(ctx, message);
	}

	@Override
	public ITranslatableString getTranslatableMsgText(final String adMessage, final Object... msgParameters)
	{
		return new ADMessageTranslatableString(adMessage, msgParameters);
	}

	private static final class ADMessageTranslatableString implements ITranslatableString
	{
		private final String adMessage;
		private final List<Object> msgParameters;

		private ADMessageTranslatableString(final String adMessage, final Object... msgParameters)
		{
			super();
			this.adMessage = adMessage;
			if (msgParameters == null || msgParameters.length == 0)
			{
				this.msgParameters = ImmutableList.of();
			}
			else
			{
				this.msgParameters = ImmutableList.copyOf(msgParameters);
			}
		}

		@Override
		public String toString()
		{
			return adMessage;
		}

		@Override
		public String translate(final String adLanguage)
		{
			return Msg.getMsg(adLanguage, adMessage, msgParameters.toArray());
		}

		@Override
		public String getDefaultValue()
		{
			return adMessage;
		}

		@Override
		public Set<String> getAD_Languages()
		{
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * Wraps a given <code>text</code> and will call {@link Msg#translate(Properties, String, boolean)}.
	 * 
	 * @author metas-dev <dev@metasfresh.com>
	 */
	private static final class ADElementOrADMessageTranslatableString implements ITranslatableString
	{
		private final String text;

		private ADElementOrADMessageTranslatableString(final String text)
		{
			super();
			this.text = text;
		}

		@Override
		public String toString()
		{
			return text;
		}

		@Override
		public String translate(final String adLanguage)
		{
			final boolean isSOTrx = true;
			return Msg.translate(adLanguage, isSOTrx, text);
		}

		@Override
		public String getDefaultValue()
		{
			return text;
		}

		@Override
		public Set<String> getAD_Languages()
		{
			throw new UnsupportedOperationException();
		}
	}

}
