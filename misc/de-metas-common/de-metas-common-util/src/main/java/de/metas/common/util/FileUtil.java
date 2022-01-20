/*
 * #%L
 * de-metas-common-util
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.common.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@UtilityClass
public class FileUtil
{
	private static final String FILENAME_ILLEGAL_CHARACTERS = new String(new char[] { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' });

	@Nullable
	public String stripIllegalCharacters(@Nullable String filename)
	{
		if (filename == null)
		{
			return "";
		}

		if (filename.length() == 0)
		{
			return filename;
		}

		// Strip white spaces from filename
		filename = filename.trim();
		if (filename.length() == 0)
		{
			return filename;
		}

		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < filename.length(); i++)
		{
			final char ch = filename.charAt(i);
			if (FILENAME_ILLEGAL_CHARACTERS.indexOf(ch) >= 0)
			{
				continue;
			}
			sb.append(ch);
		}

		return sb.toString();
	}

	public boolean isAccessible(@NonNull final URL url) throws IOException
	{
		final Path filePath = getFilePath(url);

		if (filePath == null)
		{
			throw new RuntimeException("Couldn't parse path from:" + url);
		}

		return filePath.toFile().isFile();
	}

	@Nullable
	public Path getFilePath(@NonNull final URL url) throws MalformedURLException
	{
		return Optional.ofNullable(parseNetworkFileURLOrNull(url))
				.orElseGet(() -> parseLocalFileURLOrNull(url));
	}

	@Nullable
	private Path parseLocalFileURLOrNull(@NonNull final URL url)
	{
		try
		{
			final String normalizedPath = url.getAuthority() + "\\" + Arrays.stream(url.getPath().split("/"))
					.filter(string -> !string.isEmpty())
					.collect(Collectors.joining("\\"));

			return Paths.get(normalizedPath);
		}
		catch (final Throwable throwable)
		{
			return null;
		}
	}

	@Nullable
	private Path parseNetworkFileURLOrNull(@NonNull final URL url)
	{
		try
		{
			final String normalizedPath = "\\\\" + url.getAuthority() + "\\" + Arrays.stream(url.getPath().split("/"))
					.filter(string -> !string.isEmpty())
					.collect(Collectors.joining("\\"));
			return Paths.get(normalizedPath);
		}
		catch (final Throwable throwable)
		{
			return null;
		}
	}
}
