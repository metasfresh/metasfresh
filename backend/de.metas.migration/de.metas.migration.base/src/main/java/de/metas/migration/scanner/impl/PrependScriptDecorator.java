package de.metas.migration.scanner.impl;

import java.util.Iterator;
import java.util.List;

import de.metas.migration.IScript;
import de.metas.migration.scanner.IScriptScanner;

/*
 * #%L
 * de.metas.migration.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Adds a list to {@link IScript}s to be executed before the actual scanner.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PrependScriptDecorator extends AbstractScriptDecoratorAdapter
{

	private final Iterator<IScript> scriptsToPrepend;

	private boolean removeFromHere = false;

	public PrependScriptDecorator(IScriptScanner scanner, List<IScript> scriptsToPrepend)
	{
		super(scanner);
		this.scriptsToPrepend = scriptsToPrepend.iterator();
	}

	@Override
	public void remove()
	{
		if (removeFromHere)
		{
			scriptsToPrepend.remove();
		}
		else
		{
			super.remove();
		}
	}

	@Override
	public boolean hasNext()
	{
		return scriptsToPrepend.hasNext() || super.hasNext();
	}

	@Override
	public IScript next()
	{
		final boolean hasNext = scriptsToPrepend.hasNext();
		if (hasNext)
		{
			removeFromHere = true;
			return scriptsToPrepend.next();
		}
		removeFromHere = false;
		return super.next();
	}

}
