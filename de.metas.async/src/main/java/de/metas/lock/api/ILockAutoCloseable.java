package de.metas.lock.api;

/*
 * #%L
 * de.metas.async
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

import java.io.Closeable;

/**
 * Implementations of this interface can be used in "try-with-resources" block to automatically call {@link ILock#close()}.<br>
 * Use {@link ILock#asAutoCloseable()} or {@link ILock#asAutocloseableOnTrxClose(String)} to obtain an instance.
 * 
 * @author tsa
 *
 */
public interface ILockAutoCloseable extends AutoCloseable, Closeable
{
	/**
	 * Ask to close the underlying lock.
	 * 
	 * There is <b>NO GUARANTEE</b> that the lock will be closed now or on a later time.
	 * This depends on implementation.
	 */
	@Override
	void close();

	/** @return underlying lock; never returns null */
	ILock getLock();
}
