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


import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * It's a wrapper which converts a value into a future value which is available right away.
 * 
 * @author tsa
 * 
 * @param <T>
 */
public class InstantFuture<T> implements Future<T>
{
	private final T value;

	public InstantFuture(final T value)
	{
		this.value = value;
	}

	/**
	 * @return already completed, always return false
	 */
	@Override
	public boolean cancel(boolean mayInterruptIfRunning)
	{
		// already completed, always return false
		return false;
	}

	@Override
	public boolean isCancelled()
	{
		return false;
	}

	@Override
	public boolean isDone()
	{
		return true;
	}

	@Override
	public T get()
	{
		return value;
	}

	@Override
	public T get(long timeout, TimeUnit unit)
	{
		return value;
	}
}
