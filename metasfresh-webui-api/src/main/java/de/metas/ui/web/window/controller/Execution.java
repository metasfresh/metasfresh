package de.metas.ui.web.window.controller;

import java.util.concurrent.Callable;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Throwables;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.model.DocumentChangesCollector;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.NullDocumentChangesCollector;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class Execution implements IAutoCloseable
{
	public static final Execution getCurrent()
	{
		final Execution execution = currentExecutionHolder.get();
		if (execution == null)
		{
			throw new IllegalStateException("No current execution found for thread: " + Thread.currentThread().getName());
		}
		return execution;
	}

	private static Execution startExecution()
	{
		final Execution executionOld = currentExecutionHolder.get();
		if (executionOld != null)
		{
			throw new IllegalStateException("Cannot start execution on thread '" + Thread.currentThread().getName() + "' because there is another execution currently running: " + executionOld);
		}

		final Execution execution = new Execution();
		currentExecutionHolder.set(execution);
		return execution;
	}

	/**
	 * Calls the given caller running in:
	 * <ul>
	 * <li>in a new execution; assumes no other executions are currently running on this thread
	 * <li>in transaction
	 * </ul>
	 * 
	 * @param name execution name (for logging purposes only)
	 * @param callable
	 * @return callable's return value
	 */
	public static <T> T callInNewExecution(final String name, final Callable<T> callable)
	{
		Preconditions.checkNotNull(callable, "callable");

		final Stopwatch stopwatch = Stopwatch.createStarted();
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		boolean error = false;
		try (final Execution execution = startExecution())
		{
			return trxManager.call(callable);
		}
		catch (Exception e)
		{
			error = true;
			logger.info("Changes that will be discarded: {}", getCurrentDocumentChangesCollectorOrNull());
			throw Throwables.propagate(e);
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
	}

	public static IDocumentChangesCollector getCurrentDocumentChangesCollector()
	{
		return getCurrent().getDocumentChangesCollector();
	}

	/**
	 * @return actual {@link IDocumentChangesCollector} or {@link NullDocumentChangesCollector}
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

	private Execution()
	{
		super();
		this.threadName = Thread.currentThread().getName();
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
			throw new IllegalStateException("Cannot close the execution because current execution is not the one we expected."
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
}
