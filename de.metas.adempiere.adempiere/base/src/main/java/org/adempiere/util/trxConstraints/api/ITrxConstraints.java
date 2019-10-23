package org.adempiere.util.trxConstraints.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Set;

/**
 * Transaction constraints can be defined on a per-thread basis and are enforced by the system. A user can obtain her
 * constraints instance by calling {@link org.compiere.util.DB#getConstraints()} and can then customize that instance
 * for the particular thread's needs.
 * <p>
 * <b>Motivation:</b>
 * <p>
 * Within ADempiere, we should have a way to enforce trx-Constraints to guard against misbehaving code which might
 * otherwise affect the overall runtime stability of the system.<br>
 * <p>
 * Examples:
 * <ul>
 * <li>When implementing a new process, we want to make sure that there is no unexpected stuff taking place outside of
 * the "main" transaction (e.g. in custom model validators).
 * <li>When working on complex issues (e.g. GUIs with a lot of callouts), we want to make sure that no open transactions
 * are left behind to block further database access
 * </ul>
 * <p>
 * Therefore we need to specify (e.g.) that there may only be a limited number of transactions opened from the current
 * thread at the same time. Or a limited number of save points per transaction. Or, that all transactions opened by a
 * given thread have to be finished (committed or rolled back) within a given timeout.
 * 
 * @see org.compiere.util.DB#getConstraints()
 */
public interface ITrxConstraints
{
	/**
	 * Activates or deactivates this constraints. Deactivated constrains will be ignored.
	 * 
	 */
	ITrxConstraints setActive(boolean active);

	/**
	 * 
	 * @see #setActive(boolean)
	 */
	boolean isActive();

	/**
	 * Sets if the constraints will allow new transactions only if their trxName starts with certain prefixes.
	 * 
	 * If this is set to <code>true</code>, the user needs to specify those prefixes using
	 * {@link #addAllowedTrxNamePrefix(String)}.
	 * 
	 */
	ITrxConstraints setOnlyAllowedTrxNamePrefixes(boolean onlyAllowedTrxNamePrefixes);

	/**
	 * 
	 * @see #setOnlyAllowedTrxNamePrefixes(boolean)
	 */
	boolean isOnlyAllowedTrxNamePrefixes();

	/**
	 * Adds another trxName prefix to the list of allowed prefixes
	 * 
	 * Note:
	 * <ul>
	 * <li>The given prefex doesn't have to be a real prefix. I.e. it may also be a complete trxName.
	 * <li> <code>null</code> is also a legal parameter. However, a null> prefix will only math a null trxName.
	 * </ul>
	 * 
	 * @see #setOnlyAllowedTrxNamePrefixes(boolean)
	 */
	ITrxConstraints addAllowedTrxNamePrefix(String trxNamePrefix);

	/**
	 * Removes the given string from the list of allowed trxName prefixes.
	 * 
	 * 
	 * @param trxNamePrefix
	 * @return this
	 * @see #setOnlyAllowedTrxNamePrefixes(boolean)
	 */
	ITrxConstraints removeAllowedTrxNamePrefix(String trxNamePrefix);

	/**
	 * 
	 * @see #setOnlyAllowedTrxNamePrefixes(boolean)
	 */
	Set<String> getAllowedTrxNamePrefixes();

	/**
	 * Every new transaction must commit, close or rollback within the given number of seconds. If the timeout is
	 * exceeded, the trx is closed by force and a diagnostic message is written to the log. The message contains both
	 * the stacktrace as it was when the transaction was created and the <i>current</i> stacktrace of the thread that
	 * created the transaction. The latter might help to find out if the thread is still working on something reasonable
	 * (and therefore the timeout was too short) or if the thread just forgot to close the transaction.
	 * 
	 * Note a value <=0 means "no timeout"
	 * 
	 * @param secs
	 * @param logOnly
	 *            if <code>true</code>, then the transactions are not closed, but only the diagnostic message is logged
	 *            and the timer is started again.
	 */
	ITrxConstraints setTrxTimeoutSecs(int secs, boolean logOnly);

	/**
	 * 
	 * @see #setTrxTimeoutSecs(int, boolean)
	 */
	int getTrxTimeoutSecs();

	/**
	 * 
	 * @see #setTrxTimeoutSecs(int, boolean)
	 */
	boolean isTrxTimeoutLogOnly();

	/**
	 * Sets the maximum number of concurrent transactions that a thread may open
	 * 
	 */
	public ITrxConstraints setMaxTrx(int max);

	/**
	 * Increased the maximum number of concurrent transactions that a thread may open by the given number
	 * 
	 * @param i
	 * @return
	 */
	public ITrxConstraints incMaxTrx(int num);

	/**
	 * 
	 * @see #setMaxTrx(int)
	 */
	public int getMaxTrx();

	/**
	 * 
	 * @see #setMaxSavepoints(int)
	 */
	public int getMaxSavepoints();

	/**
	 * Sets how many savepoints a transaction may set before it has to release one.
	 * 
	 */
	public ITrxConstraints setMaxSavepoints(int maxSavePoints);

	/**
	 * 
	 * @see #setAllowTrxAfterThreadEnd(boolean)
	 */
	boolean isAllowTrxAfterThreadEnd();

	/**
	 * Sets whether the current thread is allowed to end without having closed all transactions that it previously
	 * opened. If false (which is the default) and the thread ends without having closed all transactions opened, those
	 * transactions are closed by force and an error message is logged.
	 */
	ITrxConstraints setAllowTrxAfterThreadEnd(boolean allow);

	/**
	 * Resets this instance back to its default values.
	 */
	void reset();
}
