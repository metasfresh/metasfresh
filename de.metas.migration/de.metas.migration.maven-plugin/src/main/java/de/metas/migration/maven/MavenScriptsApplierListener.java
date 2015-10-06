package de.metas.migration.maven;

/*
 * #%L
 * de.metas.migration.maven-plugin
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


import org.apache.maven.plugin.logging.Log;

import de.metas.migration.IScript;
import de.metas.migration.applier.IScriptsApplierListener;
import de.metas.migration.exception.ScriptExecutionException;

/**
 * {@link IScriptsApplierListener} implementation which fails {@link #onScriptFailed(IScript, ScriptExecutionException)} but logs an detailed message to maven logger
 * 
 * @author tsa
 * 
 */
public class MavenScriptsApplierListener implements IScriptsApplierListener
{
	private final Log log;

	public MavenScriptsApplierListener(final Log log)
	{
		super();

		if (log == null)
		{
			throw new IllegalArgumentException("log cannot be null");
		}
		this.log = log;
	}

	@Override
	public void onScriptApplied(IScript script)
	{
		// nothing
	}

	@Override
	public ScriptFailedResolution onScriptFailed(final IScript script, final ScriptExecutionException e)
	{
		//
		// Get detailed error info, but don't get the stack trace (that will be logged on higher level by maven if that is suitable)
		final boolean printStackTrace = false; // don't print the stack trace, let maven do it
		final String errmsg = e.toStringX(printStackTrace);

		//
		// Log exception details
		log.error(errmsg, e);

		// let it fail miserably
		throw e;
	}

}
