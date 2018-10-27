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
}
