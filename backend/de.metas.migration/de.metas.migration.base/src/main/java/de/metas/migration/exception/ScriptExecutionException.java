package de.metas.migration.exception;

import java.io.Serial;
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
	@Serial
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
