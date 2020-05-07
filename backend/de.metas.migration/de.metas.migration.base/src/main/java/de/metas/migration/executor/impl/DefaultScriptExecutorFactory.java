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

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;

import de.metas.migration.IDatabase;
import de.metas.migration.IScript;
import de.metas.migration.ScriptType;
import de.metas.migration.exception.ScriptException;
import de.metas.migration.executor.IScriptExecutor;
import de.metas.migration.executor.IScriptExecutorFactory;
import lombok.NonNull;
import lombok.Value;

public class DefaultScriptExecutorFactory implements IScriptExecutorFactory
{
	private static final transient Logger logger = LoggerFactory.getLogger(DefaultScriptExecutorFactory.class.getName());

	private final Map<ScriptExecutorKey, Class<? extends IScriptExecutor>> scriptExecutorClasses = new HashMap<>();
	private final Set<ScriptType> supportedScriptTypes = new HashSet<>();

	private boolean dryRunMode = false;

	public DefaultScriptExecutorFactory()
	{
		initDefaults();
	}

	protected void initDefaults()
	{
		registerScriptExecutorClass("postgresql", ScriptType.SQL, PostgresqlNativeExecutor.class);
	}

	@Override
	public void removeAllScriptExecutorClasses()
	{
		scriptExecutorClasses.clear();
		supportedScriptTypes.clear();
	}

	@Override
	public void registerScriptExecutorClass(
			final String dbType,
			final ScriptType scriptType,
			@NonNull final Class<? extends IScriptExecutor> executorClass)
	{
		if (executorClass == null)
		{
			throw new IllegalArgumentException("executorClass is null");
		}

		final ScriptExecutorKey key = ScriptExecutorKey.of(dbType, scriptType);

		final Class<? extends IScriptExecutor> executorClassOld = scriptExecutorClasses.remove(key);
		if (executorClassOld != null)
		{
			logger.info("Unregistering executor " + executorClassOld + " for dbType=" + dbType + ", scriptType=" + scriptType);
		}

		scriptExecutorClasses.put(key, executorClass);

		if (scriptType != null)
		{
			supportedScriptTypes.add(scriptType);
			logger.info("Registered executor " + executorClass + " for dbType=" + dbType + ", scriptType=" + scriptType);
		}
	}

	private Class<? extends IScriptExecutor> getScriptExecutorClass(final String dbType, final ScriptType scriptType)
	{
		for (final String currDbType : Arrays.asList(dbType, TYPE_ANY))
		{
			for (final ScriptType currScriptType : Arrays.asList(scriptType, null))
			{
				final ScriptExecutorKey key = ScriptExecutorKey.of(currDbType, currScriptType);
				final Class<? extends IScriptExecutor> scriptExecutorClass = scriptExecutorClasses.get(key);
				if (scriptExecutorClass != null)
				{
					return scriptExecutorClass;
				}
			}
		}

		return null;
	}

	@Override
	public Set<ScriptType> getSupportedScriptTypes()
	{
		return ImmutableSet.copyOf(supportedScriptTypes);
	}

	@Override
	public IScriptExecutor createScriptExecutor(final IDatabase targetDatabase, final IScript script)
	{
		return createScriptExecutor(targetDatabase, script.getType());
	}

	@Override
	public IScriptExecutor createScriptExecutor(final IDatabase targetDatabase)
	{
		return createScriptExecutor(targetDatabase, ScriptType.SQL);
	}

	public IScriptExecutor createScriptExecutor(final IDatabase targetDatabase, final ScriptType scriptType)
	{
		final Class<? extends IScriptExecutor> scriptExecutorClass = getScriptExecutorClass(targetDatabase.getDbType(), scriptType);
		if (scriptExecutorClass == null)
		{
			throw new ScriptException("No script executors found for " + scriptType)
					.addParameter("Database", targetDatabase);
		}

		if (dryRunMode)
		{
			return new NullScriptExecutor(targetDatabase);
		}

		try
		{
			final IScriptExecutor executor = scriptExecutorClass.getConstructor(IDatabase.class).newInstance(targetDatabase);
			return executor;
		}
		catch (final Exception e)
		{
			throw new ScriptException("Cannot instantiate executor class: " + scriptExecutorClass, e);
		}
	}

	/**
	 * Enable/Disable dry run mode.
	 *
	 * If dry run mode is enabled then scripts won't be actually executed (i.e. {@link NullScriptExecutor} will be used)
	 *
	 * @param dryRunMode
	 */
	@Override
	public void setDryRunMode(final boolean dryRunMode)
	{
		this.dryRunMode = dryRunMode;
	}

	/**
	 * @return true if dry run mode is enabled
	 * @see #setDryRunMode(boolean)
	 */
	@Override
	public boolean isDryRunMode()
	{
		return dryRunMode;
	}

	@Value(staticConstructor = "of")
	private static class ScriptExecutorKey
	{
		final String dbType;
		final ScriptType scriptType;
	}
}
