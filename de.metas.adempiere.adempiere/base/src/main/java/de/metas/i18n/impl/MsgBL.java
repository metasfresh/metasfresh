package de.metas.i18n.impl;

import java.util.List;
import java.util.Map;

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
import java.util.Set;

import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.ILanguageBL;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.i18n.Msg;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 *
 * This implementation delegates to {@link Msg} and is therefore coupled with the database.
 *
 */
@SuppressWarnings("deprecation")
public class MsgBL implements IMsgBL
{
	@Override
	public String getMsg(final String adLanguage, final String message)
	{
		return Msg.getMsg(adLanguage, message);
	}

	@Override
	public String getMsg(final String adLanguage, final String message, final Object[] params)
	{
		return Msg.getMsg(adLanguage, message, params);
	}

	@Override
	public String getMsg(final String adLanguage, final String message, final List<Object> params)
	{
		return Msg.getMsg(adLanguage, message, params != null && !params.isEmpty() ? params.toArray() : null);
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
	public String getMsg(final String adMessage, final List<Object> params)
	{
		return getMsg(Env.getCtx(), adMessage, params != null && !params.isEmpty() ? params.toArray() : null);
	}

	@Override
	public String getMsg(final Properties ctx, final String adMessage, final boolean text)
	{
		return Msg.getMsg(ctx, adMessage, text);
	}

	@Override
	public Map<String, String> getMsgMap(final String adLanguage, final String prefix, final boolean removePrefix)
	{
		return Msg.getMsgMap(adLanguage, prefix, removePrefix);
	}

	@Override
	public String translate(final Properties ctx, final String text)
	{
		return Msg.translate(ctx, text);
	}

	@Override
	public String translate(final String adLanguage, final String text)
	{
		return Msg.translate(adLanguage, text);
	}

	@Override
	public String translate(final Properties ctx, final String text, final boolean isSOTrx)
	{
		return Msg.translate(ctx, text, isSOTrx);
	}

	@Override
	public ITranslatableString translatable(final String text)
	{
		if (Check.isEmpty(text, true))
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
	public String parseTranslation(final String adLanguage, final String message)
	{
		return Msg.parseTranslation(adLanguage, message);
	}

	@Override
	public ITranslatableString getTranslatableMsgText(final String adMessage, final Object... msgParameters)
	{
		return new ADMessageTranslatableString(adMessage, msgParameters);
	}

	@Override
	public ITranslatableString parseTranslatableString(final String text)
	{
		if (text == null || text.isEmpty())
		{
			return ImmutableTranslatableString.empty();
		}

		final TranslatableStringBuilder builder = TranslatableStringBuilder.newInstance();

		String inStr = text;
		int idx = inStr.indexOf('@');
		while (idx != -1)
		{
			builder.append(inStr.substring(0, idx)); // up to @
			inStr = inStr.substring(idx + 1, inStr.length()); // from first @

			final int j = inStr.indexOf('@'); // next @
			if (j < 0) // no second tag
			{
				inStr = "@" + inStr;
				break;
			}

			final String token = inStr.substring(0, j);
			if (token.isEmpty())
			{
				builder.append("@");
			}
			else
			{
				builder.append(translatable(token)); // replace context
			}

			inStr = inStr.substring(j + 1, inStr.length());	// from second @
			idx = inStr.indexOf('@');
		}

		// add remainder
		if (inStr != null && inStr.length() > 0)
		{
			builder.append(inStr);
		}

		return builder.build();
	}

	@Override
	public void cacheReset()
	{
		Msg.cacheReset();
	}

	@lombok.EqualsAndHashCode
	private static final class ADMessageTranslatableString implements ITranslatableString
	{
		private final String adMessage;
		private final List<Object> msgParameters;

		private ADMessageTranslatableString(final String adMessage, final Object... msgParameters)
		{
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
			return "@" + adMessage + "@";
		}

		@Override
		public Set<String> getAD_Languages()
		{
			return Services.get(ILanguageBL.class).getAvailableLanguages().getAD_Languages();
		}
	}

	/**
	 * Wraps a given <code>text</code> and will call {@link Msg#translate(Properties, String, boolean)}.
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 */
	@lombok.EqualsAndHashCode
	private static final class ADElementOrADMessageTranslatableString implements ITranslatableString
	{
		private final String text;

		private ADElementOrADMessageTranslatableString(final String text)
		{
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
			return "@" + text + "@";
		}

		@Override
		public Set<String> getAD_Languages()
		{
			return Services.get(ILanguageBL.class).getAvailableLanguages().getAD_Languages();
		}
	}

}
