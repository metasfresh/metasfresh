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


import java.util.concurrent.locks.ReentrantLock;

/**
 * A {@link ReentrantLock} which also implements {@link AutoCloseable} to be easily used in try-with-resource blocks.
 * 
 * @author tsa
 * Also see https://stackoverflow.com/a/11000458
 */
public final class CloseableReentrantLock extends ReentrantLock implements AutoCloseable
{
	private static final long serialVersionUID = 5256938961821141771L;

	/**
	 * Calls {@link #lock()}.
	 */
	public CloseableReentrantLock open()
	{
		this.lock();
		return this;
	}

	/**
	 * Calls {@link #unlock()}.
	 */
	@Override
	public void close()
	{
		this.unlock();
	}
}
