package de.metas.migration.scanner.impl;

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
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.metas.migration.exception.ScriptException;
import de.metas.migration.scanner.IFileRef;
import de.metas.migration.scanner.IScriptScanner;
import lombok.Getter;
import lombok.ToString;

@ToString(of = { "fileName", "virtual", "localFile" })
public class FileRef implements IFileRef
{
	@Getter
	private final IScriptScanner scriptScanner;

	@Getter
	private final IFileRef parent;

	@Getter
	private final String fileName;

	@Getter
	private final boolean virtual;

	private final File localFile;
	private InputStream inputStream;

	public FileRef(final File file)
	{
		this(IScriptScanner.NULL, /* parent */null, file);
	}

	protected FileRef(final IScriptScanner scriptScanner, final IFileRef parent, final File file)
	{
		this(scriptScanner, parent, file, true);
	}

	private FileRef(
			final IScriptScanner scriptScanner,
			final IFileRef parent,
			final File file,
			final boolean createParentIfNull)
	{
		if (parent == null && createParentIfNull)
		{
			this.parent = new FileRef(scriptScanner, null, file.getParentFile(), false);
		}
		else
		{
			this.parent = parent;
		}

		this.localFile = file;
		fileName = file.getName();
		inputStream = null;
		virtual = false;
		this.scriptScanner = scriptScanner;
	}

	protected FileRef(
			final IScriptScanner scriptScanner,
			final IFileRef parent,
			final String filename,
			final InputStream in)
	{
		this.parent = parent;
		localFile = null;
		fileName = filename;
		inputStream = in;
		virtual = true;
		this.scriptScanner = scriptScanner;
	}

	@Override
	public File getFile()
	{
		return localFile;
	}

	@Override
	public InputStream getInputStream()
	{
		if (inputStream == null)
		{
			InputStream in;
			try
			{
				in = new FileInputStream(localFile);
			}
			catch (final FileNotFoundException e)
			{
				throw new ScriptException("File not found: " + localFile, e);
			}
			inputStream = in;
		}
		return inputStream;
	}
}
