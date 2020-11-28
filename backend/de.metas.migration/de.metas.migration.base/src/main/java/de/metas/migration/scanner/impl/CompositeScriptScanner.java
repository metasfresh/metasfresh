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

import java.util.ArrayList;
import java.util.List;

import de.metas.migration.scanner.IScriptScanner;

public class CompositeScriptScanner extends AbstractRecursiveScriptScanner
{
	private final List<IScriptScanner> children = new ArrayList<IScriptScanner>();
	private int currentIndex = -1;

	public CompositeScriptScanner(final IScriptScanner parentScanner)
	{
		super(parentScanner);
	}

	public CompositeScriptScanner()
	{
		this(null);
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "["
				+ children
				+ "]";
	}

	public CompositeScriptScanner addScriptScanner(final IScriptScanner scriptScanner)
	{
		if (scriptScanner == null)
		{
			throw new IllegalArgumentException("scriptScanner is null");
		}

		if (children.contains(scriptScanner))
		{
			return this;
		}

		children.add(scriptScanner);
		return this;
	}

	@Override
	protected IScriptScanner retrieveNextChildScriptScanner()
	{
		final int nextIndex = currentIndex + 1;

		if (nextIndex >= children.size())
		{
			return null;
		}

		final IScriptScanner nextScanner = children.get(nextIndex);
		currentIndex = nextIndex;

		return nextScanner;
	}

}
