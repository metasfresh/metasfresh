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
import de.metas.migration.scanner.IScriptScanner;

public abstract class AbstractRecursiveScriptScanner extends AbstractScriptScanner
{
	public AbstractRecursiveScriptScanner(final IScriptScanner parentScanner)
	{
		super(parentScanner);
	}

	/**
	 *
	 * @return next script scanner or null
	 */
	protected abstract IScriptScanner retrieveNextChildScriptScanner();

	private boolean closed = false;
	private IScriptScanner currentChildScanner = null;

	@Override
	public boolean hasNext()
	{
		while (!closed)
		{
			if (currentChildScanner == null)
			{
				currentChildScanner = retrieveNextChildScriptScanner();
			}
			if (currentChildScanner == null)
			{
				closed = true;
				return false;
			}

			if (currentChildScanner.hasNext())
			{
				return true;
			}
			else
			{
				currentChildScanner = null;
			}
		}

		return false;
	}

	@Override
	public IScript next()
	{
		if (!hasNext())
		{
			throw new NoSuchElementException();
		}
		return currentChildScanner.next();
	}
}
