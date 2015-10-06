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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
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

public class FileRef implements IFileRef
{
	private final IFileRef parent;
	private final File file;
	private final String fileName;
	private InputStream inputStream;
	private final boolean virtual;
	private final IScriptScanner scriptScanner;

	public FileRef(final File file)
	{
		this(IScriptScanner.NULL, null, file);
	}

	protected FileRef(final IScriptScanner scriptScanner, final File file)
	{
		this(scriptScanner, null, file);
	}

	protected FileRef(final IScriptScanner scriptScanner, final IFileRef parent, final File file)
	{
		this(scriptScanner, parent, file, true);
	}

	protected FileRef(
			final IScriptScanner scriptScanner,
			final IFileRef parent,
			final File file,
			final boolean createParentIfNull)
	{
		if (parent == null && createParentIfNull)
		{
			this.parent = new FileRef(scriptScanner, parent, file.getParentFile(), false);
		}
		else
		{
			this.parent = parent;
		}

		this.file = file;
		this.fileName = file.getName();
		this.inputStream = null;
		this.virtual = false;
		this.scriptScanner = scriptScanner;
	}

	protected FileRef(final IScriptScanner scriptScanner, final IFileRef parent, final String filename, final InputStream in)
	{
		super();
		this.parent = parent;
		this.file = null;
		this.fileName = filename;
		this.inputStream = in;
		this.virtual = true;
		this.scriptScanner = scriptScanner;
	}

	@Override
	public IFileRef getParent()
	{
		return parent;
	}

	@Override
	public String getFileName()
	{
		return fileName;
	}

	@Override
	public File getFile()
	{
		return file;
	}

	@Override
	public InputStream getInputStream()
	{
		if (inputStream == null)
		{
			InputStream in;
			try
			{
				in = new FileInputStream(file);
			}
			catch (FileNotFoundException e)
			{
				throw new ScriptException("File not found: " + file, e);
			}
			inputStream = in;
		}
		return inputStream;
	}

	@Override
	public boolean isVirtual()
	{
		return virtual;
	}

	@Override
	public IScriptScanner getScriptScanner()
	{
		return scriptScanner;
	}

	@Override
	public String toString()
	{
		return "FileRef [fileName=" + fileName + ", file=" + file + ", virtual=" + virtual + ", scriptScanner=" + scriptScanner + "]";
	}

}
