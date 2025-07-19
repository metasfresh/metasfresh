/*
 * #%L
 * de-metas-camel-scripting
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

package de.metas.camel.externalsystems.scripting;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
public class JavaScriptRepo
{
	private final String baseDir;

	@NonNull
	public String get(@NonNull final String identifier)
	{
		final Path path = Paths.get(baseDir, identifier + ".js");
		try
		{
			return Files.readString(path);
		}
		catch (final IOException e)
		{
			throw new JavaScriptRepoException(identifier,
					"Unable to load script with identifier " + identifier,
					e);
		}
	}

	public void save(@NonNull final String identifier,
					 @NonNull final String javascript)
	{
		final Path path = Paths.get(baseDir, identifier + ".js");
		try
		{
			Files.writeString(path, javascript);
		}
		catch (final IOException e)
		{
			throw new JavaScriptRepoException(
					identifier,
					"Unable to store script with identifier " + identifier,
					e);
		}
	}
}
