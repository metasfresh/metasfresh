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

import java.util.NoSuchElementException;

import de.metas.migration.IScript;
import de.metas.migration.exception.ScriptException;
import de.metas.migration.scanner.IFileRef;
import de.metas.migration.scanner.IScriptFactory;

public class SingletonScriptScanner extends AbstractScriptScanner
{
	private final IFileRef fileRef;
	private IScript script;
	private boolean consumed = false;

	/**
	 *
	 * @param fileRef
	 * @throws ScriptException if the given <code>fileRef</code> is not a regular file.
	 */
	public SingletonScriptScanner(final IFileRef fileRef)
	{
		super(fileRef.getScriptScanner());
		this.fileRef = fileRef;

		// make sure the file exists and is a regular file
		if (!fileRef.getFile().exists())
		{
			throw new ScriptException("Script file doesn't exist: " + fileRef.getFileName());
		}
		if (!fileRef.getFile().isFile())
		{
			throw new ScriptException("Script file is not a regular file: " + fileRef.getFileName());
		}
	}

	public SingletonScriptScanner(final IScript script)
	{
		super(null);
		this.fileRef = null;
		this.script = script;
	}

	@Override
	public boolean hasNext()
	{
		return !consumed;
	}

	@Override
	public IScript next()
	{
		if (consumed)
		{
			throw new NoSuchElementException();
		}

		consumed = true;
		return getScript();
	}

	private IScript getScript()
	{
		if (script == null)
		{
			final IScriptFactory scriptFactory = getScriptFactoryToUse();
			script = scriptFactory.createScript(fileRef);
		}

		return script;
	}

	@Override
	public String toString()
	{
		return "SingletonScriptScanner [fileRef=" + fileRef + "]";
	}

}
