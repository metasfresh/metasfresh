/*
 * #%L
 * de-metas-camel-scriptedadapter
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.camel.externalsystems.scriptedadapter.filename;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Replaces {@code {placeholder}} tokens in a filename pattern; unknown ones are left unchanged.
 *
 * <p>{@code {timestamp}} is built in, the rest are supplied by the caller: export-side
 * {@code {documentno}}, {@code {table}} and {@code {recordid}}, import-side {@code {filename}}
 * (see {@link ImportFileNameResolver}).
 */
@UtilityClass
public class FilenamePatternResolver
{
	private static final String TIMESTAMP_PLACEHOLDER = "{timestamp}";
	private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

	/**
	 * Resolves all {@code {placeholder}} tokens in {@code pattern}.
	 *
	 * @param pattern   the filename pattern, e.g. {@code "DESADV_{documentno}_{timestamp}.json"}
	 * @param variables variable values to substitute (must not be {@code null})
	 * @return resolved filename
	 */
	@NonNull
	public static String resolve(@NonNull final String pattern, @NonNull final Map<String, String> variables)
	{
		String result = pattern;

		for (final Map.Entry<String, String> entry : variables.entrySet())
		{
			if (entry.getValue() != null)
			{
				result = result.replace("{" + entry.getKey() + "}", entry.getValue());
			}
		}

		if (result.contains(TIMESTAMP_PLACEHOLDER))
		{
			final String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
			result = result.replace(TIMESTAMP_PLACEHOLDER, timestamp);
		}

		return result;
	}
}
