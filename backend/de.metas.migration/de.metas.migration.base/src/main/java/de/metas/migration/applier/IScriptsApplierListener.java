package de.metas.migration.applier;

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
import de.metas.migration.exception.ScriptExecutionException;

public interface IScriptsApplierListener
{
	enum ScriptFailedResolution
	{
		/** Skip current script */
		Ignore,
		/** Consider as a failure. In this case the API will throw an exception */
		Fail,
		/** Retry executing the same script again */
		Retry,
	};

	void onScriptApplied(IScript script);

	/**
	 * Method called when a script execution failed.
     *
	 * @return script failed resolution
	 * @throws RuntimeException in case the given exception shall fail the entire run. An alternative would be to return {@link ScriptFailedResolution#Ignore}.
	 */
	ScriptFailedResolution onScriptFailed(IScript script, ScriptExecutionException e) throws RuntimeException;
}
