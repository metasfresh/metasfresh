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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.metas.migration.exception.ScriptException;
import de.metas.migration.scanner.IFileRef;

public final class FileUtils
{
	private FileUtils()
	{
		super();
	}

	public static File createLocalFile(final String filename, InputStream in)
	{
		try
		{
			return createLocalFile0(filename, in);
		}
		catch (IOException e)
		{
			throw new ScriptException("Error creating local file " + filename, e);
		}
	}

	private static File createLocalFile0(final String filename, final InputStream in) throws IOException
	{
		final String fileExtension = getFileExtension(filename, true);

		final File file = File.createTempFile(filename, fileExtension);
		file.deleteOnExit();

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

	public static final String getFileExtension(String filename, boolean includeDot)
	{
		String extension = "";
		int i = filename.lastIndexOf('.');
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

}
