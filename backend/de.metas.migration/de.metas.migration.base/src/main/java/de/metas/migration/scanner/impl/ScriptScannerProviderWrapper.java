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

import de.metas.migration.IScript;
import de.metas.migration.applier.IScriptsProvider;
import de.metas.migration.scanner.IScriptScanner;

/**
 * Wraps a given {@link IScriptScanner} to an {@link IScriptsProvider}
 *
 * @author tsa
 *
 */
public class ScriptScannerProviderWrapper implements IScriptsProvider
{
	private final IScriptScanner scriptScanner;

	public ScriptScannerProviderWrapper(final IScriptScanner scriptScanner)
	{
		if (scriptScanner == null)
		{
			throw new IllegalArgumentException("scriptScanner is null");
		}
		this.scriptScanner = scriptScanner;
	}

	@Override
	public Iterator<IScript> getScripts()
	{
		final Iterator<IScript> it = scriptScanner;
		return it;
	}

	@Override
	public String toString()
	{
		return "ScriptScannerProviderWrapper [scriptScanner=" + scriptScanner + "]";
	}
}
