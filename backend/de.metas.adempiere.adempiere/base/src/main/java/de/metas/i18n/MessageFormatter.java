/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.i18n;

import de.metas.currency.Amount;
import de.metas.logging.LogManager;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.text.MessageFormat;

@UtilityClass
class MessageFormatter
{
	private static final Logger s_log = LogManager.getLogger(MessageFormatter.class);

	static String format(@NonNull final String adLanguage, @NonNull final Message message, @Nullable final Object[] args)
	{
		final String messageStr = message.getMsgTextAndTip(adLanguage);
		if (args == null || args.length == 0)
		{
			return messageStr;
		}

		String retStr = messageStr;
		try
		{
			normalizeArgsBeforeFormat(args, adLanguage);
			retStr = MessageFormat.format(messageStr, args);    // format string
		}
		catch (final Exception e)
		{
			s_log.error(messageStr, e);
		}
		return retStr;
	}

	private static void normalizeArgsBeforeFormat(final Object[] args, final String adLanguage)
	{
		if (args == null || args.length == 0)
		{
			return;
		}

		for (int i = 0; i < args.length; i++)
		{
			final Object arg = args[i];
			final Object argNorm = normalizeSingleArgumentBeforeFormat(arg, adLanguage);
			args[i] = argNorm;
		}
	}

	private static Object normalizeSingleArgumentBeforeFormat(@Nullable final Object arg, final String adLanguage)
	{
		if (arg == null)
		{
			return null;
		}
		else if (arg instanceof ITranslatableString)
		{
			return ((ITranslatableString)arg).translate(adLanguage);
		}
		else if (arg instanceof Amount)
		{
			return TranslatableStrings.amount((Amount)arg).translate(adLanguage);
		}
		else if (arg instanceof ReferenceListAwareEnum)
		{
			return normalizeSingleArgumentBeforeFormat_ReferenceListAwareEnum((ReferenceListAwareEnum)arg, adLanguage);
		}
		else if (arg instanceof Iterable)
		{
			@SuppressWarnings("unchecked") final Iterable<Object> iterable = (Iterable<Object>)arg;
			return normalizeSingleArgumentBeforeFormat_Iterable(iterable, adLanguage);
		}
		else
		{
			return arg;
		}
	}

	private static Object normalizeSingleArgumentBeforeFormat_ReferenceListAwareEnum(
			@NonNull final ReferenceListAwareEnum referenceListAwareEnum,
			final String adLanguage)
	{
		return referenceListAwareEnum.toString();
	}

	private static String normalizeSingleArgumentBeforeFormat_Iterable(
			@NonNull final Iterable<Object> iterable,
			final String adLanguage)
	{
		final StringBuilder result = new StringBuilder();

		for (final Object item : iterable)
		{
			String itemNormStr;
			try
			{
				final Object itemNormObj = normalizeSingleArgumentBeforeFormat(item, adLanguage);
				itemNormStr = itemNormObj != null ? itemNormObj.toString() : "-";
			}
			catch (Exception ex)
			{
				s_log.warn("Failed normalizing argument `{}`. Using toString().", item, ex);
				itemNormStr = item.toString();
			}

			if (!(result.length() == 0))
			{
				result.append(", ");
			}
			result.append(itemNormStr);
		}

		return result.toString();

	}
}
