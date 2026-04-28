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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class FileUtil
{
	private static final String FILENAME_ILLEGAL_CHARACTERS = new String(new char[] { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' });

	@NonNull
	public String stripIllegalCharacters(@Nullable final String filename)
	{
		if (Check.isBlank(filename))
		{
			return "";
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

	@NonNull
	public Path getFilePath(@NonNull final URL url)
	{
		final boolean isWindowsLocalPath = Check.isEmpty(url.getHost())
				? url.getPath().contains(":")
				: url.getAuthority().contains(":");

		final Path path = isWindowsLocalPath
				? parseWindowsLocalPath(url)
				: parseNonLocalWindowsFileURL(url);

		if (path == null)
		{
			throw new RuntimeException("Couldn't parse path from:" + url);
		}

		return path;
	}

	@NonNull
	public String normalizeAndValidateFilePath(@NonNull final String filePath)
	{
		final File file = new File(filePath);

		if (file.isAbsolute())
		{
			throw new RuntimeException("Absolute path not allowed! filePath:" + filePath);
		}

		try
		{
			final String pathUsingCanonical = file.getCanonicalPath();
			final String pathUsingAbsolute = file.getAbsolutePath();

			// avoid attacks, e.g. "1/../2/"
			if (!pathUsingCanonical.equals(pathUsingAbsolute))
			{
				throw new RuntimeException("Absolute path and canonical path for filePath must be equal! filePath: " + filePath);
			}

			return Paths.get(file.getPath())
					.normalize()
					.toString();

		}
		catch (final Exception e)
		{
			throw new RuntimeException("Error caught: ", e);
		}
	}

	@Nullable
	private Path parseWindowsLocalPath(@NonNull final URL url)
	{
		try
		{
			final String normalizedPath = Stream.concat(Stream.of(url.getAuthority()), Arrays.stream(url.getPath().split("/")))
					.filter(Check::isNotBlank)
					.collect(Collectors.joining("/"));

			return Paths.get(normalizedPath);
		}
		catch (final Throwable throwable)
		{
			return null;
		}
	}

	@Nullable
	private Path parseNonLocalWindowsFileURL(@NonNull final URL url)
	{
		try
		{
			final String normalizedPath = Stream.of(url.getAuthority(), url.getPath())
					.filter(Check::isNotBlank)
					.collect(Collectors.joining());

			return Paths.get("//" + normalizedPath);
		}
		catch (final Throwable throwable)
		{
			return null;
		}
	}

	/**
	 * Creates a file path for the given {@code baseDirectory} and {@code subdirectory}.
	 *
	 * @throws RuntimeException if the resulting file path is not within the given {@code baseDirectory}.
	 */
	@NonNull
	public static Path createAndValidatePath(@NonNull final Path baseDirectory,
											  @NonNull final Path subdirectory)
	{
		final Path baseDirectoryAbs = baseDirectory.normalize().toAbsolutePath();
		final Path outputFilePath = baseDirectoryAbs.resolve(
				subdirectory).normalize().toAbsolutePath();

		// Validate that the outputFilePath is within the base directory
		if (!outputFilePath.startsWith(baseDirectoryAbs))
		{
			throw new RuntimeException("Invalid path " + outputFilePath + "! The result of baseDirectory=" + baseDirectory + " and subdirectory=" + subdirectory + " needs to be within baseDirectory");
		}
		return outputFilePath;
	}

	public static void copy(@NonNull final File from, @NonNull final OutputStream out) throws IOException
	{
		try (final InputStream in = Files.newInputStream(from.toPath()))
		{
			copy(in, out);
		}
	}

	/**
	 * Copies the content from a given {@link InputStream} to the specified {@link File}.
	 * The method writes the data to a temporary file first, and then renames it to the target file.
	 * This ensures that we don'T end up with a partially written file that's then already processed by some other component.
	 *
	 * @throws IOException if an I/O error occurs during reading, writing, or renaming the file
	 */
	public static void copy(@NonNull final InputStream in, @NonNull final File to) throws IOException
	{
		final File tempFile = new File(to.getAbsolutePath() + ".part");
		try (final FileOutputStream out = new FileOutputStream(tempFile))
		{
			copy(in, out);
		}

		// rename the temporary file to the final destination
		if (!tempFile.renameTo(to))
		{
			throw new IOException("Failed to rename the temporary file "
					+ tempFile.getAbsolutePath() + " to " + to.getAbsolutePath());
		}
	}


	public static void copy(@NonNull final InputStream in, @NonNull final OutputStream out) throws IOException
	{
		final byte[] buf = new byte[4096];
		int len;
		while ((len = in.read(buf)) > 0)
		{
			out.write(buf, 0, len);
		}
		out.flush();
	}
}
