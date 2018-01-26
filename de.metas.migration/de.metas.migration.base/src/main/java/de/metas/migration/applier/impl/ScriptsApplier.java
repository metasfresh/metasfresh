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

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.metas.migration.IDatabase;
import de.metas.migration.IScript;
import de.metas.migration.IScriptsRegistry;
import de.metas.migration.applier.IScriptsApplier;
import de.metas.migration.applier.IScriptsApplierListener;
import de.metas.migration.applier.IScriptsApplierListener.ScriptFailedResolution;
import de.metas.migration.applier.IScriptsProvider;
import de.metas.migration.exception.ScriptExecutionException;
import de.metas.migration.executor.IScriptExecutor;
import de.metas.migration.executor.IScriptExecutorFactory;
import de.metas.migration.executor.impl.DefaultScriptExecutorFactory;

public class ScriptsApplier implements IScriptsApplier
{
	private static final transient Logger logger = LoggerFactory.getLogger(ScriptsApplier.class);

	private final IDatabase targetDatabase;

	private IScriptsApplierListener listener = NullScriptsApplierListener.instance;
	private IScriptExecutorFactory scriptExecutorFactory = new DefaultScriptExecutorFactory();

	private int countAll = 0;
	private int countApplied = 0;
	private int countIgnored = 0;

	private static enum ScriptApplyResult
	{
		Applied,
		Ignored,
	}

	public ScriptsApplier(final IDatabase targetDatabase)
	{
		super();

		if (targetDatabase == null)
		{
			throw new IllegalArgumentException("targetDatabase shall not be null");
		}
		this.targetDatabase = targetDatabase;
	}

	@Override
	public void setListener(final IScriptsApplierListener listener)
	{
		if (listener == null)
		{
			throw new IllegalArgumentException("listener shall not be null");
		}
		this.listener = listener;
	}

	public IScriptsApplierListener getListener()
	{
		return listener;
	}

	public IScriptExecutorFactory getScriptExecutorFactory()
	{
		return scriptExecutorFactory;
	}

	@Override
	public void setScriptExecutorFactory(final IScriptExecutorFactory scriptExecutorFactory)
	{
		if (scriptExecutorFactory == null)
		{
			throw new IllegalArgumentException("scriptExecutorFactory shall not be null");
		}
		this.scriptExecutorFactory = scriptExecutorFactory;
	}

	@Override
	public void apply(final IScriptsProvider scriptsProvider)
	{
		if (scriptsProvider == null)
		{
			throw new IllegalArgumentException("scriptsProvider shall not be null");
		}

		logger.info("Migrating {} using {}", targetDatabase, scriptsProvider);

		final IScriptsRegistry scriptsRegistry = getScriptsRegistry();

		final Iterator<IScript> scripts = scriptsProvider.getScripts();
		int countSkippedFromLastAction = 0;
		while (scripts.hasNext())
		{
			countAll++;

			final IScript script = scripts.next();

			if (scriptsRegistry.isApplied(script))
			{
				logger.debug("Script already applied: {}", script);

				countSkippedFromLastAction++;
				if (countSkippedFromLastAction % 50 == 0)
				{
					logger.info("Skipped {} scripts that were already applied", countSkippedFromLastAction);
				}
				continue;
			}

			countSkippedFromLastAction = 0;
			final ScriptApplyResult result = apply(script);
			if (result == ScriptApplyResult.Applied)
			{
				countApplied++;
				scriptsRegistry.markApplied(script);
			}
			else if (result == ScriptApplyResult.Ignored)
			{
				countIgnored++;
				scriptsRegistry.markIgnored(script);
			}
			else
			{
				throw new ScriptExecutionException("Invalid ScriptApplyResult: " + result);
			}
		}
	}

	/**
	 *
	 * @param script
	 * @return
	 */
	private ScriptApplyResult apply(final IScript script)
	{
		final IScriptsApplierListener listener = getListener();
		final IScriptExecutor executor = getExecutor(script);

		logger.info("Applying {}", script);

		boolean needExecute = true;
		while (needExecute)
		{
			needExecute = false;

			final long startTS = System.currentTimeMillis();
			ScriptExecutionException error = null;
			try
			{
				executor.execute(script);
			}
			catch (final ScriptExecutionException e)
			{
				error = e;
			}
			catch (final Exception e)
			{
				error = new ScriptExecutionException("Error running script", e)
						.setScript(script)
						.setDatabase(targetDatabase)
						.setExecutor(executor);
			}
			finally
			{
				final long durationMillis = System.currentTimeMillis() - startTS;
				if (error == null)
				{
					logger.info("... Applied in {}ms", durationMillis);
					script.setLastDurationMillis(durationMillis);
					listener.onScriptApplied(script);
					return ScriptApplyResult.Applied;
				}
				else
				{
					final ScriptFailedResolution scriptFailedResolution = listener.onScriptFailed(script, error);
					if (scriptFailedResolution == ScriptFailedResolution.Fail)
					{
						throw error;
					}
					else if (scriptFailedResolution == ScriptFailedResolution.Ignore)
					{
						logger.info("... Ignored");
						return ScriptApplyResult.Ignored;
					}
					else if (scriptFailedResolution == ScriptFailedResolution.Retry)
					{
						logger.info("... Retry");
						needExecute = true;
					}
					else
					{
						throw new ScriptExecutionException("Invalid ScriptFailedResolution: " + scriptFailedResolution, error);
					}
				}
			}
		}

		throw new IllegalStateException("Internal error: Shall never reach this point");
	}

	private IScriptsRegistry getScriptsRegistry()
	{
		return targetDatabase.getScriptsRegistry();
	}

	private IScriptExecutor getExecutor(final IScript script)
	{
		return scriptExecutorFactory.createScriptExecutor(targetDatabase, script);
	}

	@Override
	public int getCountAll()
	{
		return countAll;
	}

	@Override
	public int getCountApplied()
	{
		return countApplied;
	}

	@Override
	public int getCountIgnored()
	{
		return countIgnored;
	}
}
