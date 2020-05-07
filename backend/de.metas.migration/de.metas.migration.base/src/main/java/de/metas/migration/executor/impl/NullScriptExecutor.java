package de.metas.migration.executor.impl;

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

import de.metas.migration.IDatabase;
import de.metas.migration.IScript;
import de.metas.migration.executor.IScriptExecutor;

/**
 * A script executor which does nothing
 *
 * @author tsa
 *
 */
public class NullScriptExecutor implements IScriptExecutor
{
	// NOTE: let the constructor signature as is now
	// because de.metas.migration.executor.impl.DefaultScriptExecutorFactory.createScriptExecutor(IDatabase, IScript) relies on this
	public NullScriptExecutor(final IDatabase database)
	{
		// nothing
	}

	@Override
	public void execute(final IScript script)
	{
		// do nothing
	}

}
