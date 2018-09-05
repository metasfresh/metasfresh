package org.adempiere.util;

/*
 * #%L
 * de.metas.util
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
import java.io.IOException;

public final class FileUtils
{
	private FileUtils()
	{
	}

	/**
	 * Gets file extension (without the dot) or null if the file does not have an extension.
	 * 
	 * @param filename
	 * @return file extension or null
	 */
	public static String getFileExtension(final String filename)
	{
		if (filename == null)
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

	public static String getFileBaseName(final String filename)
	{
		if (filename == null)
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
	 * @param filename filename or URL
	 * @param extension file extension to be used (with or without dot); in case the extension is null then it won't be appended so only the basename will be returned
	 * @return filename with new file extension or same filename if the filename does not have an extension
	 */
	public static String changeFileExtension(final String filename, String extension)
	{
		final StringBuilder sb = new StringBuilder();

		//
		// Append basename
		final int extensionPos = filename.lastIndexOf(".");
		if (extensionPos > 0)
		{
			sb.append(filename.substring(0, extensionPos));
		}
		else
		{
			sb.append(filename);
		}

		// If no extension, return only the basename
		if (extension == null)
		{
			return sb.toString();
		}

		// If no extension, return only the basename
		extension = extension.trim();
		if (extension.length() == 0)
		{
			return sb.toString();
		}

		// Append the dot between basename and extension only if the extension does not already contain a dot
		if (!extension.startsWith("."))
		{
			sb.append(".");
		}

		// Append the extension
		sb.append(extension);

		return sb.toString();
	}

	/**
	 * Create temporary directory with given suffix
	 * 
	 * @param suffix
	 * @return temporary directory
	 */
	public static File createTempDirectory(final String suffix)
	{
		final File tempFile;
		try
		{
			tempFile = File.createTempFile(suffix, ".tmp");
		}
		catch (IOException e)
		{
			throw new RuntimeException("Cannot create temporary directory with suffix '" + suffix + "'", e);
		}

		final String tempDirName = FileUtils.changeFileExtension(tempFile.getAbsolutePath(), "");
		final File tempDir = new File(tempDirName);
		if (!tempDir.mkdirs())
		{
			throw new RuntimeException("Cannot create directories for " + tempDir);
		}

		return tempDir;
	}

	private static final String FILENAME_ILLEGAL_CHARACTERS = new String(new char[] { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' });

	public static final String stripIllegalCharacters(String filename)
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
}
