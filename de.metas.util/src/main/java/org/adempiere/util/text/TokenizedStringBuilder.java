package org.adempiere.util.text;

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


import java.util.Properties;

import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.i18n.IMsgBL;

/**
 * Simple {@link StringBuilder} like class which is appending a given separator between each {@link #append(Object)} method calls.
 * 
 * @author tsa
 *
 */
public class TokenizedStringBuilder
{
	// Parameters
	private final StringBuilder sb;
	private final String separator;
	private boolean autoAppendSeparator = true;

	// Status
	private boolean lastAppendedIsSeparator = false;

	public TokenizedStringBuilder(final StringBuilder sb, final String separator)
	{
		super();
		Check.assumeNotNull(sb, "sb not null");
		this.sb = sb;

		// NOTE: we allow any white chars as separator, but the separator shall not be an empty string because that makes no sense
		Check.assume(separator != null && separator.length() > 0, "separator not empty");
		this.separator = separator;
	}

	public TokenizedStringBuilder(final String separator)
	{
		this(new StringBuilder(), separator);
	}

	@Override
	public String toString()
	{
		return sb.toString();
	}

	/**
	 * @return underlying {@link StringBuilder}.
	 */
	public StringBuilder asStringBuilder()
	{
		return sb;
	}

	public boolean isAutoAppendSeparator()
	{
		return autoAppendSeparator;
	}

	public TokenizedStringBuilder setAutoAppendSeparator(boolean autoAppendSeparator)
	{
		this.autoAppendSeparator = autoAppendSeparator;
		return this;
	}

	public boolean isLastAppendedIsSeparator()
	{
		return lastAppendedIsSeparator;
	}

	public String getSeparator()
	{
		return separator;
	}

	public TokenizedStringBuilder append(final Object obj)
	{
		if (autoAppendSeparator)
		{
			appendSeparatorIfNeeded();
		}

		sb.append(obj);
		lastAppendedIsSeparator = false;

		return this;
	}

	public TokenizedStringBuilder parseTranslationAndAppend(final Properties ctx, final String str)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final String strTranslated = msgBL.parseTranslation(ctx, str);

		return append(strTranslated);
	}

	public TokenizedStringBuilder appendSeparatorIfNeeded()
	{
		if (lastAppendedIsSeparator)
		{
			return this;
		}
		if (sb.length() <= 0)
		{
			return this;
		}

		sb.append(separator);
		lastAppendedIsSeparator = true;

		return this;
	}
}
