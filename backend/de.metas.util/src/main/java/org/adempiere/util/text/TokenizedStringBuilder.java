package org.adempiere.util.text;

import de.metas.util.Check;

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
