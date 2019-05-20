package de.metas.migration.util;

/*
 * #%L
 * de.metas.migration.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import de.metas.migration.ScriptType;
import de.metas.migration.exception.ScriptException;
import de.metas.migration.scanner.IFileRef;
import lombok.NonNull;

public final class FileUtils
{
	private FileUtils()
	{
	}

	public static File createLocalFile(final String filename, final InputStream in)
	{
		try
		{
			return createLocalFile0(filename, in);
		}
		catch (final IOException e)
		{
			throw new ScriptException("Error creating local file " + filename, e);
		}
	}

	private static File createLocalFile0(final String filename, final InputStream in) throws IOException
	{
		final File file = createTempFile(filename);

		FileOutputStream out = null;
		try
		{
			out = new FileOutputStream(file);
			final byte[] buf = new byte[4096];
			int len = 0;
			while ((len = in.read(buf)) != -1)
			{
				out.write(buf, 0, len);
			}
		}
		finally
		{
			if (out != null)
			{
				out.flush();
				out.close();
				out = null;
			}
		}

		return file;
	}

	private static File createTempFile(final String filename)
	{
		final String fileExtension = getFileExtension(filename, true);
		final String fileBaseName = getFileBasename(filename);

		try
		{
			final File file = File.createTempFile(fileBaseName + ".", fileExtension);
			file.deleteOnExit();
			return file;
		}
		catch (final IOException ex)
		{
			throw new IllegalArgumentException("Cannot create temporary file", ex);
		}
	}

	private static final String getFileBasename(final String filename)
	{
		final int i = filename.lastIndexOf('.');
		if (i > 0)
		{
			return filename.substring(0, i);
		}
		else
		{
			return filename;
		}

	}

	public static final String getFileExtension(final String filename, final boolean includeDot)
	{
		String extension = "";
		final int i = filename.lastIndexOf('.');
		if (i > 0)
		{
			extension = filename.substring(i + 1);
			if (includeDot)
			{
				extension = "." + extension;
			}
		}

		return extension;
	}

	public static final ScriptType getScriptTypeByFilename(final String filename)
	{
		final String fileExtension = FileUtils.getFileExtension(filename, false);
		return ScriptType.ofFileExtension(fileExtension);
	}

	public static final IFileRef getFirstNonVirtual(final IFileRef fileRef)
	{
		if (fileRef == null)
		{
			return null;
		}

		for (IFileRef currentFile = fileRef; currentFile != null; currentFile = currentFile.getParent())
		{
			if (currentFile.isVirtual())
			{
				continue;
			}

			return currentFile;
		}

		return null;
	}

	public static File downloadUrl(@NonNull final URL url)
	{
		final String filename = extractFilename(url);
		final File tmpFile = createTempFile(filename);

		try (final FileOutputStream out = new FileOutputStream(tmpFile))
		{
			final ReadableByteChannel urlChannel = Channels.newChannel(url.openStream());

			out.getChannel()
					.transferFrom(urlChannel, 0, Long.MAX_VALUE);
		}
		catch (final Exception ex)
		{
			throw new RuntimeException("Failed downloading " + url + " to " + tmpFile, ex);
		}

		return tmpFile;
	}

	private static String extractFilename(final URL url)
	{
		final String path = url.getPath();
		final int idx = path.lastIndexOf("/");
		if (idx < 0)
		{
			throw new IllegalArgumentException("Cannot extract filename from " + url);
		}

		return path.substring(idx + 1);
	}

	public static File unzip(@NonNull final File zipFile)
	{
		try
		{
			final File unzipDir = Files.createTempDirectory(zipFile.getName() + "-")
					.toFile();
			unzipDir.deleteOnExit();

			final ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
			ZipEntry zipEntry = zis.getNextEntry();
			while (zipEntry != null)
			{
				unzipEntry(unzipDir, zipEntry, zis);

				zipEntry = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();

			return unzipDir;
		}
		catch (final Exception ex)
		{
			throw new RuntimeException("Cannot unzip " + zipFile, ex);
		}
	}

	public static File unzipEntry(
			final File destinationDir,
			final ZipEntry zipEntry,
			final ZipInputStream zipInputStream) throws IOException
	{
		final File destFile = new File(destinationDir, zipEntry.getName());
		if (zipEntry.isDirectory())
		{
			destFile.mkdirs();
		}
		else
		{
			final String destDirPath = destinationDir.getCanonicalPath();
			final String destFilePath = destFile.getCanonicalPath();
			if (!destFilePath.startsWith(destDirPath + File.separator))
			{
				throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
			}

			try (final FileOutputStream fos = new FileOutputStream(destFile))
			{
				final byte[] buffer = new byte[4096];
				int len;
				while ((len = zipInputStream.read(buffer)) > 0)
				{
					fos.write(buffer, 0, len);
				}
			}
		}
		return destFile;
	}

}
