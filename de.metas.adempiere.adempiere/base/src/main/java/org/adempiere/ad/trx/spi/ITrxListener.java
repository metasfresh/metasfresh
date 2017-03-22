package org.adempiere.ad.trx.spi;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.ad.trx.api.ITrx;

/**
 * Transaction listener.
 * <p>
 * Hint: you will probably want to subclass {@link TrxListenerAdapter} instead of directly implementing this interface.
 *
 * @author tsa
 * @see org.adempiere.ad.trx.api.ITrxListenerManager
 *
 */
public interface ITrxListener
{
	/**
	 * Method called before a transaction will be committed.
	 *
	 * If an exception is thrown by this method, it will be propagated and the execution/transaction will fail.
	 *
	 * @param trx
	 */
	void beforeCommit(ITrx trx) throws Exception;

	/**
	 * Method called after a transaction was successfully committed.
	 *
	 * If an exception is thrown from this method, the exception will be JUST logged but it will not fail or stop the execution.
	 *
	 * @param trx the transaction that was committed. <b>This transaction is already closed</b>
	 */
	void afterCommit(ITrx trx);

	/**
	 * Method called after a transaction was <b>successfully</b> rollback
	 *
	 * @param trx
	 */
	void afterRollback(ITrx trx);

	/**
	 * Method called after a transaction was closed (sucessfully or not).
	 *
	 * @param trx
	 */
	void afterClose(ITrx trx);

	/**
	 * Deactivate this listener, so that it won't be invoked any further.
	 *
	 * Method can be called when this listener shall be ignored from now on.<br>
	 * Useful for example if the after-commit code shall be invoked only <b>once</b>, even if there are multiple commits.
	 */
	void deactivate();

	/**
	 *
	 * @return <code>true</code>, unless {@link #deactivate()} has been called at least once. from there on, it always returns <code>false</code>.
	 */
	boolean isActive();
}
