/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber;

import de.metas.util.ILoggable;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.slf4j.MDC;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

class CucumberLoggable implements ILoggable
{
	public static final CucumberLoggable instance = new CucumberLoggable();

	private static final int LINE_MAX_LENGTH = 200;
	public static final String NL = "\n";
	@NonNull private final PrintStream out = System.out;

	private CucumberLoggable() {}

	@Override
	public ILoggable addLog(final String msg, final Object... msgParameter)
	{
		out.println(
				paginate(
						StringUtils.formatMessage(msg, msgParameter),
						prefix(),
						LINE_MAX_LENGTH,
						lastLineSuffix()));

		return this;
	}

	private String prefix()
	{
		return ">>> " + Thread.currentThread().getName() + " >>> ";
	}

	private static String lastLineSuffix()
	{
		final CharSequence mdcAsString = getMDCAsString();
		if (mdcAsString.length() > 0)
		{
			return "[Context: " + mdcAsString + "]";
		}
		else
		{
			return "";
		}
	}

	private static CharSequence getMDCAsString()
	{
		final Map<String, String> mdcMap = MDC.getCopyOfContextMap();
		if (mdcMap == null || mdcMap.isEmpty())
		{
			return "";
		}

		final TreeSet<String> mdcKeys = new TreeSet<>(mdcMap.keySet()); // make sure they are sorted
		final StringBuilder sb = new StringBuilder();
		for (final String key : mdcKeys)
		{
			final String value = mdcMap.get(key);
			if (sb.length() > 0)
			{
				sb.append(", ");
			}
			sb.append(key).append("=").append(value);
		}

		return sb;
	}

	@SuppressWarnings("SameParameterValue")
	private static CharSequence paginate(final String message, @NonNull final String prefixFirstLine, final int lineMaxLength, final String lastLineSuffix)
	{
		final String messageNorm = StringUtils.trimBlankToNull(message);
		if (messageNorm == null)
		{
			return "";
		}

		String prefixNextLines = null; // to be calculated if needed

		final StringBuilder result = new StringBuilder();
		int lastLineLength = -1;
		for (final String rawLine : messageNorm.split("\\n"))
		{
			for (String lineAfterBreak : breakLineByMaxLength(rawLine.trim(), lineMaxLength))
			{
				final boolean isFirstLine = result.length() == 0;
				if (isFirstLine)
				{
					result.append(prefixFirstLine);
				}
				else
				{
					prefixNextLines = prefixNextLines != null ? prefixNextLines : repeat(" ", prefixFirstLine.length());
					result.append(NL).append(prefixNextLines);
				}

				result.append(lineAfterBreak);

				lastLineLength = lineAfterBreak.length();
			}
		}

		//
		// Last line suffix
		{
			final int lastLineSuffixStart = lineMaxLength + 10;
			if (lastLineLength >= 0 && lastLineLength < lastLineSuffixStart)
			{
				result.append(repeat(" ", lastLineSuffixStart - lastLineLength));
			}
			result.append(lastLineSuffix);
		}

		return result;
	}

	@SuppressWarnings("SameParameterValue")
	private static String repeat(String what, int count)
	{
		if (count <= 0)
		{
			return "";
		}

		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++)
		{
			sb.append(what);
		}
		return sb.toString();
	}

	private static List<String> breakLineByMaxLength(final String string, final int lineMaxLength)
	{
		if (string.length() <= lineMaxLength)
		{
			return ImmutableList.of(string);
		}

		final ArrayList<String> result = new ArrayList<>();
		String remaining = string;
		while (!remaining.isEmpty())
		{
			final int remainingLength = remaining.length();
			if (remainingLength <= lineMaxLength)
			{
				result.add(remaining.trim());
				//remaining = "";
				break;
			}
			else
			{
				final String line = remaining.substring(0, lineMaxLength);
				result.add(line.trim());
				remaining = remaining.substring(lineMaxLength);
			}
		}

		return result;
	}

	@Override
	public void flush()
	{
		out.flush();
	}
}
