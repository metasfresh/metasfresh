/**
 *
 */
package de.metas.util;

/*
 * #%L
 * de.metas.report.jasper.commons
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

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@UtilityClass
public final class FileUtil
{
	public static void copy(@NonNull final File from, @NonNull final OutputStream out) throws IOException
	{
		try (final InputStream in = Files.newInputStream(from.toPath()))
		{
			copy(in, out);
		}
	}

	public static void copy(@NonNull final InputStream in, @NonNull final File to) throws IOException
	{
		try (final FileOutputStream out = new FileOutputStream(to))
		{
			copy(in, out);
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
	
	public static String getTempDir()
	{
		return System.getProperty("java.io.tmpdir");
	}

	public static File createTempFile(@NonNull final String fileExtension, @NonNull final String title)
	{
		final String path = getTempDir();
		final String prefix = makeFilePrefix(title);

		final File file;
		try
		{
			file = File.createTempFile(prefix, "." + fileExtension, new File(path));
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e.getLocalizedMessage(), e);
		}
		return file;
	}

	private static String makeFilePrefix(@Nullable final String name)
	{
		if (Check.isBlank(name))
		{
			return "Report_";
		}
		final StringBuilder prefix = new StringBuilder();
		final char[] nameArray = name.toCharArray();
		for (final char ch : nameArray)
		{
			if (Character.isLetterOrDigit(ch))
			{
				prefix.append(ch);
			}
			else
			{
				prefix.append("_");
			}
		}

		if (prefix.length() < 3)
		{
			// prefix shall be at least 3chars long, else File.createTempFile will throw exception
			prefix.append("_Report");
		}

		return prefix.toString();
	}

	/**
	 * @return file extension (without the dot) or null if the file does not have an extension.
	 */
	@Contract("null -> null")
	@Nullable
	public static String getFileExtension(@Nullable final String filename)
	{
		if (Check.isBlank(filename))
		{
			return null;
		}

		final int idx = filename.lastIndexOf(".");
		if (idx > 0)
		{
			return filename.substring(idx + 1);
		}

		return null;
	}

	@Contract("null -> null")
	@Nullable
	public static String getFileBaseName(@Nullable final String filename)
	{
		if (Check.isBlank(filename))
		{
			return null;
		}

		final int idx = filename.lastIndexOf(".");
		if (idx > 0)
		{
			return filename.substring(0, idx);
		}
		else
		{
			return filename;
		}
	}

	/**
	 * Change file's extension.
	 *
	 * @param filename  filename or URL
	 * @param extension file extension to be used (with or without dot); in case the extension is null then it won't be appended so only the basename will be returned
	 * @return filename with new file extension or same filename if the filename does not have an extension
	 */
	public static String changeFileExtension(@NonNull final String filename, @Nullable final String extension)
	{
		final StringBuilder sb = new StringBuilder();

		//
		// Append basename
		final int extensionPos = filename.lastIndexOf(".");
		if (extensionPos > 0)
		{
			sb.append(filename, 0, extensionPos);
		}
		else
		{
			sb.append(filename);
		}

		// If no extension, return only the basename
		final String extensionNorm = StringUtils.trimBlankToNull(extension);
		if (extensionNorm == null)
		{
			return sb.toString();
		}

		// Append the dot between basename and extension only if the extension does not already contain a dot
		if (!extensionNorm.startsWith("."))
		{
			sb.append(".");
		}

		// Append the extension
		sb.append(extensionNorm);

		return sb.toString();
	}

	/**
	 * Create temporary directory with given suffix
	 *
	 * @return temporary directory
	 */
	public static File createTempDirectory(final String suffix)
	{
		final File tempFile;
		try
		{
			tempFile = File.createTempFile(suffix, ".tmp");
		}
		catch (final IOException e)
		{
			throw new RuntimeException("Cannot create temporary directory with suffix '" + suffix + "'", e);
		}

		final String tempDirName = changeFileExtension(tempFile.getAbsolutePath(), "");
		final File tempDir = new File(tempDirName);
		if (!tempDir.mkdirs())
		{
			throw new RuntimeException("Cannot create directories for " + tempDir);
		}

		return tempDir;
	}

	private static final String FILENAME_ILLEGAL_CHARACTERS = new String(new char[] { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' });

	@NonNull
	public static String stripIllegalCharacters(@Nullable final String filename)
	{
		return de.metas.common.util.FileUtil.stripIllegalCharacters(filename);
	}

	public static Optional<Path> findNotExistingFile(
			@NonNull final Path directory, 
			@NonNull final String desiredFilename, 
			final int tries)
	{
		final String fileBaseNameInitial = getFileBaseName(desiredFilename);
		final String ext = StringUtils.trimBlankToNull(getFileExtension(desiredFilename));
		Path file = directory.resolve(desiredFilename);
		for (int i = 1; Files.exists(file) && i <= tries - 1; i++)
		{
			final String newFilename = fileBaseNameInitial
					+ "_" + (i + 1)
					+ (ext != null ? "." + ext : "");
			file = directory.resolve(newFilename);
		}

		return !Files.exists(file) ? Optional.of(file) : Optional.empty();
	}

	public static File zip(@NonNull final List<File> files) throws IOException
	{
		final File zipFile = File.createTempFile("ZIP", ".zip");
		zipFile.deleteOnExit();

		try (final FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
				final ZipOutputStream zip = new ZipOutputStream(fileOutputStream))
		{
			zip.setMethod(ZipOutputStream.DEFLATED);
			zip.setLevel(Deflater.BEST_COMPRESSION);

			for (final File file : files)
			{
				addToZipFile(file, zip);
			}
		}

		return zipFile;
	}

	private static void addToZipFile(@NonNull final File file, @NonNull final ZipOutputStream zos) throws IOException
	{
		try (final FileInputStream fis = new FileInputStream(file))
		{
			final ZipEntry zipEntry = new ZipEntry(file.getName());
			zos.putNextEntry(zipEntry);

			final byte[] bytes = new byte[1024];
			int length;
			while ((length = fis.read(bytes)) >= 0)
			{
				zos.write(bytes, 0, length);
			}

			zos.closeEntry();
		}
	}

	/**
	 * Java 8 implemention of Files.writeString
	 */
	public static void writeString(final Path path, final String content, final Charset charset, final OpenOption... options) throws IOException
	{
		final byte[] bytes = content.getBytes(charset);
		Files.write(path, bytes, options);
	}
}
