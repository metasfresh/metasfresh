package org.adempiere.util.concurrent;

/*
 * #%L
 * de.metas.util
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


import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import de.metas.util.Check;

/**
 * A value which will be set in future.
 * 
 * Invocations to {@link #get()} and {@link #get(long, TimeUnit)} will block until some thread will set a value by using {@link #set(Object)} or it will set an exception by using
 * {@link #setError(Exception)}.
 * 
 * @author tsa
 * 
 * @param <T>
 */
public class FutureValue<T> implements Future<T>
{
	private T value = null;
	private Exception exception = null;

	private boolean done = false;
	private boolean canceled = false;

	private final CountDownLatch latch = new CountDownLatch(1);

	public void set(final T value)
	{
		if (done)
		{
			throw new IllegalStateException("Value was already set");
		}
		this.value = value;
		this.exception = null;
		this.done = true;
		this.canceled = false;
		latch.countDown();
	}

	public void setError(final Exception e)
	{
		if (done)
		{
			throw new IllegalStateException("Value was already set");
		}

		Check.assumeNotNull(e, "exception not null");

		this.value = null;
		this.exception = e;
		this.done = true;
		this.canceled = false;
		latch.countDown();
	}

	public void setCanceled(final Exception e)
	{
		if (done)
		{
			throw new IllegalStateException("Value was already set");
		}

		Check.assumeNotNull(e, "exception not null");

		this.value = null;
		this.exception = e;
		this.done = true;
		this.canceled = true;
		latch.countDown();
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCancelled()
	{
		return canceled;
	}

	@Override
	public boolean isDone()
	{
		return done;
	}

	@Override
	public T get() throws InterruptedException, ExecutionException
	{
		latch.await();
		return get0();
	}

	@Override
	public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException
	{
		latch.await(timeout, unit);
		return get0();
	}

	private T get0() throws ExecutionException
	{
		if (!done)
		{
			throw new IllegalStateException("Value not available");
		}

		//
		// We got a cancellation
		if (canceled)
		{
			final CancellationException cancelException = new CancellationException(exception.getLocalizedMessage());
			cancelException.initCause(exception);
			throw cancelException;
		}
		
		//
		// We got an error
		if (exception != null)
		{
			throw new ExecutionException(exception);
		}
		return value;
	}
}
