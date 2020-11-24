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


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class FileUtil
{
	private FileUtil()
	{
	}

	public static final void copy(File from, OutputStream out) throws IOException
	{
		InputStream in = new FileInputStream(from);
		copy(in, out);
	}

	public static final void copy(InputStream in, File to) throws IOException
	{
		final FileOutputStream out = new FileOutputStream(to);
		copy(in, out);
	}

	public static final void copy(InputStream in, OutputStream out) throws IOException
	{
		byte[] buf = new byte[4096];
		int len = -1;
		while ((len = in.read(buf)) > 0)
		{
			out.write(buf, 0, len);
		}
		out.flush();
	}

	public static File createTempFile(final String fileExtension, final String title)
	{
		final String path = System.getProperty("java.io.tmpdir");
		final String prefix = makeFilePrefix(title);

		File file;
		try
		{
			file = File.createTempFile(prefix, "." + fileExtension, new File(path));
		}
		catch (IOException e)
		{
			throw new RuntimeException(e.getLocalizedMessage(), e);
		}
		return file;
	}

	private static String makeFilePrefix(String name)
	{
		if (name == null || name.trim().length() == 0)
		{
			return "Report_";
		}
		StringBuffer prefix = new StringBuffer();
		char[] nameArray = name.toCharArray();
		for (char ch : nameArray)
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

	public static File toTempFile(byte[] data, final String fileExtension, String title)
	{
		final File file = createTempFile(fileExtension, title);
		final InputStream in = new ByteArrayInputStream(data);
		try
		{
			copy(in, file);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e.getLocalizedMessage(), e);
		}
		return file;
	}

	/**
	 * Gets file extension (without the dot) or null if the file does not have an extension.
	 *
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
	public static String changeFileExtension(final String filename, final String extension)
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
		if (Check.isBlank(extension))
		{
			return sb.toString();
		}

		final String trimmedExtension = extension.trim();

		// Append the dot between basename and extension only if the extension does not already contain a dot
		if (!trimmedExtension.startsWith("."))
		{
			sb.append(".");
		}

		// Append the extension
		sb.append(trimmedExtension);

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
		catch (IOException e)
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
