package de.metas.migration.impl;

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

import de.metas.migration.IScript;
import de.metas.migration.IScriptsRegistry;

/**
 * Null {@link IScriptsRegistry} which actually does nothing
 *
 * @author tsa
 *
 */
public final class NullScriptsRegistry implements IScriptsRegistry
{
	public static final NullScriptsRegistry instance = new NullScriptsRegistry();

	private NullScriptsRegistry()
	{
		super();
	}

	/**
	 * @return false
	 */
	@Override
	public boolean isApplied(final IScript script)
	{
		return false;
	}

	/**
	 * Does nothing
	 */
	@Override
	public void markApplied(final IScript script)
	{
		// nothing
	}

	/**
	 * Does nothing
	 */
	@Override
	public void markIgnored(final IScript script)
	{
		// nothing
	}
}
