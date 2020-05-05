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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.lock.exceptions.LockAlreadyClosedException;

/**
 * Lock model.<br>
 *
 * A lock instance could be used to lock/unlock one or more models.<br>
 * Use {@link ILockManager} to obaint an instance.
 *
 * @author tsa
 *
 */
public interface ILock
{
	/** Null lock marker */
	ILock NULL = null;

	/**
	 * Also see {@link ILockCommand#setOwner(LockOwner)}
	 *
	 * @return lock owner or {@link LockOwner#NONE}; never returns <code>null</code>
	 */
	LockOwner getOwner();

	/**
	 * @return true if the lock shall be automatically cleaned up when server starts
	 */
	boolean isAutoCleanup();

	/**
	 * @return how many records are locked by this lock
	 */
	int getCountLocked();

	/**
	 * Start splitting some records off this lock and move them to another lock.
	 * <p>
	 * <b>IMPORTANT:</b> it is mandatory to provide an owner for the new lock with {@link ILockCommand#setOwner(LockOwner)}.
	 **/
	ILockCommand split();

	/**
	 * Unlock all records which were locked by this lock.
	 */
	void unlockAll() throws LockAlreadyClosedException;

	/**
	 * @return true if lock is closed
	 */
	boolean isClosed();

	/**
	 * Same as {@link #unlockAll()}. If this method was already called, then it does nothing.
	 */
	void close();

	/**
	 * This method is to {@link #close()} what {@link #asAutocloseableOnTrxClose(String)} is to {@link #asAutoCloseable()}.
	 *
	 * @param localTrxName
	 */
	void closeOnTrxClose(String localTrxName);

	/**
	 * Gets a lock auto-closeable.
	 *
	 * @return auto-closeable
	 */
	ILockAutoCloseable asAutoCloseable();

	/**
	 * Gets a lock auto-closeable which when called, it will
	 * <ul>
	 * <li>if transaction is null or no longer exist, it will close the lock immediatelly
	 * <li>if transaction exists, it will close the lock asynchronously when the transaction is commited or rolled back.
	 * </ul>
	 *
	 * @param trxName
	 * @return auto closable
	 */
	ILockAutoCloseable asAutocloseableOnTrxClose(String trxName);

	boolean isLocked(final Object model);
}
