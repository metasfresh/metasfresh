package de.metas.migration.exception;

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

import java.util.Collections;
import java.util.List;

import de.metas.migration.IDatabase;
import de.metas.migration.IScript;
import de.metas.migration.executor.IScriptExecutor;

public class ScriptExecutionException extends ScriptException
{
	/**
	 *
	 */
	private static final long serialVersionUID = -1165299685098998072L;

	private IDatabase database;
	private IScript script;
	private IScriptExecutor executor;
	private List<String> log;

	public ScriptExecutionException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public ScriptExecutionException(final String message)
	{
		super(message);
	}

	@Override
	public ScriptExecutionException addParameter(final String name, final Object value)
	{
		super.addParameter(name, value);
		return this;
	}

	public IDatabase getDatabase()
	{
		return database;
	}

	public ScriptExecutionException setDatabase(final IDatabase database)
	{
		addParameter("database", database);
		this.database = database;
		return this;
	}

	public IScript getScript()
	{
		return script;
	}

	public ScriptExecutionException setScript(final IScript script)
	{
		addParameter("script", script);
		this.script = script;
		return this;
	}

	public IScriptExecutor getExecutor()
	{
		return executor;
	}

	public ScriptExecutionException setExecutor(final IScriptExecutor executor)
	{
		addParameter("executor", executor);
		this.executor = executor;
		return this;
	}

	public ScriptExecutionException setLog(final List<String> log)
	{
		addParameter("log", log);
		this.log = log;
		return this;
	}

	public List<String> getLog()
	{
		if (log == null)
		{
			return Collections.emptyList();
		}
		return log;
	}

}
