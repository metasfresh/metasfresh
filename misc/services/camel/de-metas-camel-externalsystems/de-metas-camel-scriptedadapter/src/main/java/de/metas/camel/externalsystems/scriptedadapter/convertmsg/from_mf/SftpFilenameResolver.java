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

package de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Resolves filename patterns by replacing {@code {placeholder}} tokens with actual values.
 *
 * <p>Built-in placeholders:
 * <ul>
 *   <li>{@code {timestamp}} — current local date/time in {@code yyyyMMdd_HHmmss} format (e.g. {@code 20260328_143022})</li>
 * </ul>
 *
 * <p>Context-dependent placeholders (populated by {@link SftpDeliveryProcessor}):
 * <ul>
 *   <li>{@code {documentno}} — document number of the exported record (e.g. shipment number)</li>
 *   <li>{@code {table}} — table name of the exported record (e.g. {@code M_InOut})</li>
 *   <li>{@code {recordid}} — database record ID of the exported record</li>
 * </ul>
 *
 * <p>Unknown placeholders are left unchanged.
 *
 * <p>Example:
 * <pre>
 *   resolve("DESADV_{documentno}_{timestamp}.json", Map.of("documentno", "12345"))
 *   // → "DESADV_12345_20260328_143022.json"
 * </pre>
 */
@UtilityClass
public class SftpFilenameResolver
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

		// Replace variable placeholders first
		for (final Map.Entry<String, String> entry : variables.entrySet())
		{
			if (entry.getValue() != null)
			{
				result = result.replace("{" + entry.getKey() + "}", entry.getValue());
			}
		}

		// Replace the special {timestamp} placeholder
		if (result.contains(TIMESTAMP_PLACEHOLDER))
		{
			final String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
			result = result.replace(TIMESTAMP_PLACEHOLDER, timestamp);
		}

		return result;
	}
}
