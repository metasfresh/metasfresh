package de.metas.migration.executor;

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

import java.util.Set;

import de.metas.migration.IDatabase;
import de.metas.migration.IScript;

public interface IScriptExecutorFactory
{
	String TYPE_ANY = "*";

	void registerScriptExecutorClass(String dbType, String scriptType, Class<? extends IScriptExecutor> executorClass);

	/**
	 * Clear all registered {@link IScriptExecutor} classes
	 */
	void removeAllScriptExecutorClasses();

	Set<String> getSupportedScriptTypes();

	IScriptExecutor createScriptExecutor(IDatabase targetDatabase, IScript script);

	void setDryRunMode(boolean dryRunMode);

	boolean isDryRunMode();

}
