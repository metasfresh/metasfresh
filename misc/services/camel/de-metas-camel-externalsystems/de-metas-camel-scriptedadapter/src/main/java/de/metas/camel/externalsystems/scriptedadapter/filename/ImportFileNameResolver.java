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
 * Resolves the attachment filename for an inbound (import-side) file, given an optional {@code pattern}
 * that may reference the incoming file's base name via {@code {filename}} (see {@link SftpFilenameResolver}
 * for the full placeholder syntax, e.g. {@code {timestamp}}).
 *
 * <p>A blank {@code pattern} leaves {@code incomingFileName} unchanged. Otherwise the incoming name is
 * split into base name and extension (on the last {@code '.'}; a leading dot, or no dot at all, means no
 * extension), the pattern is resolved with {@code {filename}} bound to the base name, and the source
 * extension is appended unless the resolved name already ends with it (compared case-insensitively).
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
		final String resolved = SftpFilenameResolver.resolve(pattern, variables);

		if (extension.isEmpty() || resolved.toLowerCase(Locale.ROOT).endsWith(extension.toLowerCase(Locale.ROOT)))
		{
			return resolved;
		}

		return resolved + extension;
	}
}
