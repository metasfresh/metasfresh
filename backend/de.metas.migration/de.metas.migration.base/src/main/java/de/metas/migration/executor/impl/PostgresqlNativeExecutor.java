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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.migration.IDatabase;
import de.metas.migration.IScript;
import de.metas.migration.exception.ScriptExecutionException;
import de.metas.migration.executor.IScriptExecutor;
import de.metas.migration.impl.AnonymousScript;
import de.metas.migration.impl.SQLDatabase;
import de.metas.migration.impl.SQLHelper;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

public class PostgresqlNativeExecutor implements IScriptExecutor
{
	private static final transient Logger logger = LoggerFactory.getLogger(PostgresqlNativeExecutor.class);

	private static final String ENV_PG_HOME = "PG_HOME";
	private static final String ENV_PGPASSWORD = "PGPASSWORD";

	private static final String AFTER_MIGRATION_FUNC_PATTERN = "after_migration%";

	private final IDatabase database;
	private final SQLHelper sqlHelper;

	private final String command;
	private final List<String> args;
	private final Map<String, String> environment;

	public PostgresqlNativeExecutor(@NonNull final IDatabase database)
	{
		this.database = database;
		this.sqlHelper = new SQLHelper(SQLDatabase.cast(database));

		//
		// Configure: psql command
		final String pgHome = getEnv(ENV_PG_HOME);
		if (pgHome == null || pgHome.trim().length() == 0)
		{
			command = "psql";
		}
		else
		{
			command = pgHome + File.separator + "bin" + File.separator + "psql";
		}

		//
		// Configure: psql command arguments
		{
			args = new ArrayList<String>();
			addParameter(args, "-h", database.getDbHostname());
			addParameter(args, "-p", database.getDbPort());
			addParameter(args, "-d", database.getDbName());
			addParameter(args, "-U", database.getDbUser());

			// Execute the script in one single transaction
			// see http://petereisentraut.blogspot.ro/2010/03/running-sql-scripts-with-psql.html
			args.add("--single-transaction");

			// Stop script execution on first error.
			// If this value is not set or is ZERO the script execution will continue and the exit code will be 0, no matter if one command failed
			// see http://petereisentraut.blogspot.ro/2010/03/running-sql-scripts-with-psql.html
			args.add("--set");
			args.add("ON_ERROR_STOP=1");
		}

		//
		// Configure: psql running environment
		{
			environment = new HashMap<String, String>();

			if (database.getDbPassword() != null)
			{
				// NOTE: we are setting the ENV_PGPASSWORD only if DbPassword is set
				// else we rely on other authentication methods
				// e.g. "pgpass" file
				environment.put(ENV_PGPASSWORD, database.getDbPassword());
			}
		}
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + " ["
				+ "command=" + command
				+ ", args=" + args
				+ ", environment=" + environment
				+ "]";
	}

	private String getEnv(final String name)
	{
		final String value = System.getenv(name);
		return value;
	}

	private static void addParameter(final List<String> args, final String paramName, final String value)
	{
		if (value == null || value.trim().length() == 0)
		{
			return;
		}

		args.add(paramName);
		args.add(value.trim());
	}

	@Override
	public void execute(final IScript script)
	{
		executeAndReturnResult(script, 100);
	}

	private ScriptExecutionResult executeAndReturnResult(final IScript script, final int logTailSize)
	{
		final Process proc = startProcess(script);

		final ImmutableList<String> logTail = readOutput(proc.getInputStream(), logTailSize);
		final int exitValue;
		try
		{
			exitValue = proc.waitFor();
		}
		catch (final InterruptedException e)
		{
			throw new ScriptExecutionException("Process executor interrupted", e)
					.setDatabase(database)
					.setScript(script)
					.setExecutor(this)
					.setLog(logTail);
		}

		if (exitValue != 0)
		{
			throw new ScriptExecutionException("Script execution failed")
					.setDatabase(database)
					.setScript(script)
					.setExecutor(this)
					.addParameter("ProcessExitCode", exitValue)
					.setLog(logTail);
		}

		return ScriptExecutionResult.builder()
				.logTail(logTail)
				.build();
	}

	private Process startProcess(final IScript script)
	{
		final List<String> cmdarrayList = new ArrayList<String>();
		cmdarrayList.add(command);
		cmdarrayList.addAll(args);
		addParameter(cmdarrayList, "-f", script.getLocalFile().getAbsolutePath());

		final ProcessBuilder processBuilder = new ProcessBuilder(cmdarrayList);
		final Map<String, String> env = processBuilder.environment();
		env.putAll(environment);

		processBuilder.redirectErrorStream(true); // forward STDERR to STDOUT

		final Process proc;
		try
		{
			proc = processBuilder.start();
		}
		catch (final IOException e)
		{
			throw new ScriptExecutionException("Error creating executor process."
					+ "If 'psql' command was not found, try setting " + ENV_PG_HOME + " environment variable.", e)
							.setDatabase(database)
							.setScript(script)
							.setExecutor(this);
		}

		return proc;
	}

	private ImmutableList<String> readOutput(final InputStream is, final int tailSize)
	{

		final BufferedReader in = new BufferedReader(new InputStreamReader(is), 10240);
		try
		{
			final List<String> tail = tailSize > 0 ? new ArrayList<>(tailSize + 1) : new ArrayList<>();
			int truncatedLines = 0;

			String line;
			while ((line = in.readLine()) != null)
			{
				if (tailSize > 0 && tail.size() >= tailSize)
				{
					tail.remove(0);
					truncatedLines++;
				}
				tail.add(line);
			}

			if (truncatedLines > 0)
			{
				tail.add(0, "(Truncated " + truncatedLines + " lines. Preserved last " + tail.size() + " lines)");
			}

			return ImmutableList.copyOf(tail);
		}
		catch (final IOException e)
		{
			throw new ScriptExecutionException("Error while reading process output", e)
					.setDatabase(database)
					.setExecutor(this);
		}

	}

	@Override
	public void executeAfterScripts()
	{
		final Set<String> functionNames = sqlHelper.getDBFunctionsMatchingPattern(AFTER_MIGRATION_FUNC_PATTERN)
				.stream()
				.sorted()
				.collect(ImmutableSet.toImmutableSet());

		if (functionNames.isEmpty())
		{
			logger.warn("Skip executing after migration scripts because no function matching pattern '{}' was found in {}", AFTER_MIGRATION_FUNC_PATTERN, database);
			return;
		}

		final AnonymousScript script = AnonymousScript.builder()
				.fileName("after_migration.sql")
				.scriptContent(functionNames.stream()
						.map(functionName -> "select " + functionName + "();\n")
						.collect(Collectors.joining()))
				.build();

		final Stopwatch stopwatch = Stopwatch.createStarted();
		final int logTailSize = -1; // full log
		final ScriptExecutionResult result = executeAndReturnResult(script, logTailSize);
		stopwatch.stop();

		logger.info("Executed {} in {}ms and got following result:\n{}",
				functionNames,
				stopwatch,
				Joiner.on("\n").join(result.getLogTail()));
	}

	@Builder
	@Value
	private static class ScriptExecutionResult
	{
		@NonNull
		final ImmutableList<String> logTail;
	}
}
