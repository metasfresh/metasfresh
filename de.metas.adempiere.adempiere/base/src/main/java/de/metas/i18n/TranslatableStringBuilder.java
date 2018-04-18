package de.metas.i18n;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.Services;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class TranslatableStringBuilder
{
	public static final TranslatableStringBuilder newInstance()
	{
		return new TranslatableStringBuilder();
	}

	private TranslatableStringBuilder()
	{
	}

	private final List<ITranslatableString> parts = new ArrayList<>();

	public ITranslatableString build()
	{
		if (parts.isEmpty())
		{
			return ITranslatableString.empty();
		}
		return new CompositeTranslatableString(parts, "");
	}

	public TranslatableStringBuilder append(final ITranslatableString value)
	{
		if (value == null)
		{
			return this;
		}

		parts.add(value);
		return this;
	}

	public TranslatableStringBuilder append(final String value)
	{
		if (Check.isEmpty(value))
		{
			return this;
		}

		return append(ConstantTranslatableString.of(value));
	}

	public TranslatableStringBuilder append(final BigDecimal value, final int displayType)
	{
		return append(NumberTranslatableString.of(value, displayType));
	}

	public TranslatableStringBuilder appendDate(final Date value)
	{
		return append(DateTimeTranslatableString.ofDate(value));
	}

	public TranslatableStringBuilder appendDateTime(final Date value)
	{
		return append(DateTimeTranslatableString.ofDateTime(value));
	}

	public TranslatableStringBuilder appendADMessage(final String adMessage, final Object... msgParameters)
	{
		final ITranslatableString value = Services.get(IMsgBL.class).getTranslatableMsgText(adMessage, msgParameters);
		return append(value);
	}

	public TranslatableStringBuilder appendADElement(final String columnName)
	{
		final ITranslatableString value = Services.get(IMsgBL.class).translatable(columnName);
		return append(value);
	}

}
