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

import de.metas.common.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Resolves the attachment filename for an inbound (import-side) file: {@code pattern} is resolved with
 * {@code {filename}} bound to the incoming file's base name (see {@link FilenamePatternResolver} for the
 * placeholder syntax), and the source extension re-appended unless the result already ends with it. A
 * blank {@code pattern} leaves {@code incomingFileName} unchanged.
 */
@UtilityClass
public class ImportFileNameResolver
{
	private static final String FILENAME_PLACEHOLDER = "filename";

	@NonNull
	public static String resolve(@Nullable final String pattern, @NonNull final String incomingFileName)
	{
		if (Check.isBlank(pattern))
		{
			return incomingFileName;
		}

		final int extensionSeparatorIndex = incomingFileName.lastIndexOf('.');
		final boolean hasExtension = extensionSeparatorIndex > 0;
		final String baseName = hasExtension ? incomingFileName.substring(0, extensionSeparatorIndex) : incomingFileName;
		final String extension = hasExtension ? incomingFileName.substring(extensionSeparatorIndex) : "";

		final Map<String, String> variables = new HashMap<>();
		variables.put(FILENAME_PLACEHOLDER, baseName);
		final String resolved = FilenamePatternResolver.resolve(pattern, variables);

		if (extension.isEmpty() || resolved.toLowerCase(Locale.ROOT).endsWith(extension.toLowerCase(Locale.ROOT)))
		{
			return resolved;
		}

		return resolved + extension;
	}
}
