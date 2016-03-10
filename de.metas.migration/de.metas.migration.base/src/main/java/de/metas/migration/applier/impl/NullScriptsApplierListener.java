package de.metas.migration.applier.impl;

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
import de.metas.migration.applier.IScriptsApplierListener;
import de.metas.migration.exception.ScriptExecutionException;

public final class NullScriptsApplierListener implements IScriptsApplierListener
{
	public static final transient NullScriptsApplierListener instance = new NullScriptsApplierListener();

	private NullScriptsApplierListener()
	{
		super();
	}

	@Override
	public void onScriptApplied(final IScript script)
	{
		// nothing
	}

	/**
	 * Throws <code>e</code>.
	 */
	@Override
	public ScriptFailedResolution onScriptFailed(final IScript script, final ScriptExecutionException e)
	{
		return ScriptFailedResolution.Fail;
	}

}
