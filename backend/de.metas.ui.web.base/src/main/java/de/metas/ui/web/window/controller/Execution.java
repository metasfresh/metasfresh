package de.metas.ui.web.window.controller;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import de.metas.ui.web.exceptions.InvalidDocumentVersionException;
import de.metas.ui.web.window.datatypes.json.JSONTriggerAction;
import de.metas.ui.web.window.model.DocumentChangesCollector;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.NullDocumentChangesCollector;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class Execution implements IAutoCloseable
{
	public static Execution getCurrent()
	{
		final Execution execution = currentExecutionHolder.get();
		if (execution == null)
		{
			throw new AdempiereException("No current execution found for thread: " + Thread.currentThread().getName());
		}
		return execution;
	}

	public static boolean isCurrentExecutionAvailable()
	{
		return currentExecutionHolder.get() != null;
	}

	private static Execution startExecution()
	{
		final Execution executionOld = currentExecutionHolder.get();
		if (executionOld != null)
		{
			throw new AdempiereException("Cannot start execution on thread '" + Thread.currentThread().getName() + "' because there is another execution currently running: " + executionOld);
		}

		final Execution execution = new Execution();
		currentExecutionHolder.set(execution);
		return execution;
	}

	public static ExecutionBuilder prepareNewExecution()
	{
		return new ExecutionBuilder();
	}

	/**
	 * Calls the given caller running in:
	 * <ul>
	 * <li>in a new execution; assumes no other executions are currently running on this thread
	 * <li>in transaction
	 * </ul>
	 *
	 * @param name execution name (for logging purposes only)
	 * @return callable's return value
	 */
	public static <T> T callInNewExecution(final String name, final Callable<T> callable)
	{
		return new ExecutionBuilder()
				.name(name)
				.execute(callable);
	}

	public static IDocumentChangesCollector getCurrentDocumentChangesCollector()
	{
		return getCurrent().getDocumentChangesCollector();
	}

	/**
	 * @return actual {@link IDocumentChangesCollector} or {@link NullDocumentChangesCollector}. Never returns {@code null}.
	 */
	public static IDocumentChangesCollector getCurrentDocumentChangesCollectorOrNull()
	{
		final Execution execution = currentExecutionHolder.get();
		if (execution == null)
		{
			return NullDocumentChangesCollector.instance;
		}
		return execution.getDocumentChangesCollector();
	}

	private static final Logger logger = LogManager.getLogger(Execution.class);

	private static final ThreadLocal<Execution> currentExecutionHolder = new ThreadLocal<>();

	//
	private final String threadName;
	private volatile boolean closed = false;
	private volatile IDocumentChangesCollector documentChangesCollector;
	private final ArrayList<JSONTriggerAction> frontendTriggerActions = new ArrayList<>();

	private Execution()
	{
		threadName = Thread.currentThread().getName();
	}

	@Override
	public String toString()

	{
		return MoreObjects.toStringHelper(this)
				.add("threadName", threadName)
				.toString();
	}

	@Override
	public synchronized void close()
	{
		if (closed)
		{
			return;
		}

		final Execution executionNow = currentExecutionHolder.get();
		if (this != executionNow)
		{
			throw new AdempiereException("Cannot close the execution because current execution is not the one we expected."
					+ "\n Expected: " + this
					+ "\n Current: " + executionNow
					+ "\n Current thread: " + Thread.currentThread().getName()
					+ "\n HINT: make sure you are closing the execution on the same thread where you started it.");
		}

		currentExecutionHolder.set(null);
		closed = true;
	}

	public IDocumentChangesCollector getDocumentChangesCollector()
	{
		if (documentChangesCollector == null)
		{
			synchronized (this)
			{
				if (documentChangesCollector == null)
				{
					documentChangesCollector = DocumentChangesCollector.newInstance();
				}
			}
		}
		return documentChangesCollector;
	}

	public void requestFrontendToTriggerAction(@NonNull final JSONTriggerAction action)
	{
		frontendTriggerActions.add(action);
	}

	public ImmutableList<JSONTriggerAction> getFrontendTriggerActions()
	{
		return ImmutableList.copyOf(frontendTriggerActions);
	}

	public static ImmutableList<JSONTriggerAction> getCurrentFrontendTriggerActionsOrEmpty()
	{
		final Execution execution = currentExecutionHolder.get();
		return execution != null
				? execution.getFrontendTriggerActions()
				: ImmutableList.of();
	}

	public static final class ExecutionBuilder
	{
		private String name = "";
		private int versionErrorRetryCount;
		private boolean inTransaction = true;

		private ExecutionBuilder()
		{
		}

		public <T> T execute(final Callable<T> callable)
		{
			Callable<T> callableEffective = callable;

			//
			// First(important). Run in transaction
			if (inTransaction)
			{
				final Callable<T> beforeCall = callableEffective;
				callableEffective = () -> {
					final ITrxManager trxManager = Services.get(ITrxManager.class);

					try
					{
						return trxManager.callInNewTrx(beforeCall);
					}
					catch (final Exception ex)
					{
						logger.info("Changes that will be discarded: {}", getCurrentDocumentChangesCollectorOrNull());
						throw AdempiereException.wrapIfNeeded(ex);
					}
				};
			}

			//
			// Retry on version error
			if (versionErrorRetryCount > 0)
			{
				final Callable<T> beforeCall = callableEffective;
				callableEffective = () -> {
					InvalidDocumentVersionException versionException = null;
					for (int retry = 0; retry < versionErrorRetryCount; retry++)
					{
						try
						{
							return beforeCall.call();
						}
						catch (final InvalidDocumentVersionException ex)
						{
							versionException = ex;
							logger.info("Version error on run {}/{}", retry + 1, versionErrorRetryCount, versionException);
						}
					}

					Check.assumeNotNull(versionException, "VersionException must not be null");

					throw versionException;
				};
			}

			//
			// Last, measure and log
			{
				final Callable<T> beforeCall = callableEffective;
				callableEffective = () -> {
					boolean error = true;
					final Stopwatch stopwatch = Stopwatch.createStarted();
					try
					{
						final T result = beforeCall.call();
						error = false;
						return result;
					}
					finally
					{
						if (!error)
						{
							logger.debug("Executed {} in {} ({})", name, stopwatch, callable);
						}
						else
						{
							logger.warn("Failed executing {} (took {}) ({})", name, stopwatch, callable);
						}

					}
				};
			}

			//
			// Run the effective callable in a new execution
			try (final Execution ignored = startExecution())
			{
				return callableEffective.call();
			}
			catch (final Exception ex)
			{
				throw AdempiereException.wrapIfNeeded(ex);
			}
		}

		public ExecutionBuilder name(final String name)
		{
			this.name = name;
			return this;
		}

		public ExecutionBuilder retryOnVersionError(final int retryCount)
		{
			Preconditions.checkArgument(retryCount > 0);
			versionErrorRetryCount = retryCount;
			return this;
		}

		public ExecutionBuilder outOfTransaction()
		{
			inTransaction = false;
			return this;
		}
	}
}
