package de.metas.ui.web.window.controller;

import org.adempiere.util.lang.IAutoCloseable;

import com.google.common.base.MoreObjects;

import de.metas.ui.web.window.model.FieldChangedEventCollector;
import de.metas.ui.web.window.model.IDocumentFieldChangedEventCollector;

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
			throw new IllegalStateException("No current execution found for " + Thread.currentThread().getName());
		}
		return execution;
	}

	public static Execution startExecution()
	{
		final Execution executionOld = currentExecutionHolder.get();
		if (executionOld != null)
		{
			throw new IllegalStateException("Cannot start execution on " + Thread.currentThread().getName() + " because there is another execution currently running: " + executionOld);
		}

		final Execution execution = new Execution();
		currentExecutionHolder.set(execution);
		return execution;
	}

	public static IDocumentFieldChangedEventCollector getCurrentFieldChangedEventsCollector()
	{
		return getCurrent().getFieldChangedEventsCollector();
	}

	private static final ThreadLocal<Execution> currentExecutionHolder = new ThreadLocal<>();

	//
	private final String threadName;
	private volatile boolean closed = false;
	private volatile IDocumentFieldChangedEventCollector fieldChangedEventsCollector;

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

	public IDocumentFieldChangedEventCollector getFieldChangedEventsCollector()
	{
		if (fieldChangedEventsCollector == null)
		{
			synchronized (this)
			{
				if (fieldChangedEventsCollector == null)
				{
					fieldChangedEventsCollector = FieldChangedEventCollector.newInstance();
				}
			}
		}
		return fieldChangedEventsCollector;
	}
}
