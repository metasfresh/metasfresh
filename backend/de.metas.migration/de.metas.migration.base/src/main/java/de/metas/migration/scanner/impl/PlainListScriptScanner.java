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

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import de.metas.migration.IScript;
import de.metas.migration.scanner.IFileRef;
import de.metas.migration.scanner.IScriptFactory;

/**
 * This scanner returns a list of {@link IScript}s.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PlainListScriptScanner extends AbstractScriptScanner
{
	private final Iterator<IFileRef> fileRefs;
	private final Iterator<IScript> scripts;

	public static PlainListScriptScanner fromScripts(final List<IScript> scripts)
	{
		return new PlainListScriptScanner(null, scripts);
	}

	public static PlainListScriptScanner fromFileRefs(final List<IFileRef> fileRefs)
	{
		return new PlainListScriptScanner(fileRefs, null);
	}

	private PlainListScriptScanner(final List<IFileRef> fileRefs, final List<IScript> scripts)
	{
		super(null);
		this.fileRefs = fileRefs == null ? null : fileRefs.iterator();
		this.scripts = scripts == null ? null : scripts.iterator();
	}

	@Override
	public boolean hasNext()
	{
		if (fileRefs != null)
		{
			return fileRefs.hasNext();
		}
		return scripts.hasNext();
	}

	@Override
	public IScript next()
	{
		if (!hasNext())
		{
			throw new NoSuchElementException();
		}

		if (fileRefs != null)
		{
			return getScript(fileRefs.next());
		}
		return scripts.next();
	}

	private IScript getScript(final IFileRef fileRef)
	{

		final IScriptFactory scriptFactory = getScriptFactoryToUse();
		return scriptFactory.createScript(fileRef);
	}

	@Override
	public String toString()
	{
		return "PlainListScriptScanner [fileRef=" + fileRefs + ", scripts= " + scripts + "]";
	}

}
