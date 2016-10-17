package org.adempiere.ad.trx.api;

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


import org.compiere.util.TrxRunnable;
import org.compiere.util.TrxRunnable2;

/**
 * This config is used by {@link ITrxManager#run(String, ITrxRunConfig, org.compiere.util.TrxRunnable)} to decide the particular behavior.<br>
 * Use {@link ITrxManager#createTrxRunConfig(TrxPropagation, OnRunnableSuccess, OnRunnableFail)} to obtain an instance.
 *
 * @author ts
 *
 */
public interface ITrxRunConfig
{
	public enum TrxPropagation
	{
		/**
		 * The run method creates its own local transaction which is closed at the end.
		 *
		 * If a not-NULL <code>trxName</code> is given to the run method, then that trxName is used as prefix for the local transaction's name.
		 */
		REQUIRES_NEW,

		/**
		 * The run method doesn't create a transaction name of it's own, but uses the given <code>trxName</code>.
		 */
		NESTED,

		// TODO: implement REQUIRED; see http://static.springsource.org/spring/docs/3.0.0.M3/reference/html/ch11s05.html#tx-propagation
	}

	public enum OnRunnableSuccess
	{
		/**
		 * If the {@link TrxRunnable}'s run method succeeds, then the transaction is committed
		 */
		COMMIT,

		DONT_COMMIT
	}

	/**
	 * Decides what to do if the {@link TrxRunnable}'s run() method throws an Exception.
	 *
	 */
	public enum OnRunnableFail
	{
		/**
		 * The transaction is rolled back to the start it had before the run() method.
		 */
		ROLLBACK,

		/**
		 * The transaction is rolled back only if the given runnable is not an instance of <code>TrxRunnable2</code> or if {@link TrxRunnable2#doCatch(Throwable)} returns <code>true</code>.
		 */
		ASK_RUNNABLE,

		/**
		 * Don't rollback even if the runnable failed.
		 *
		 * If this option it's used the transaction won't be rolled back but also NO SAVEPOINT will be created (which can improve performances a lot).
		 */
		DONT_ROLLBACK
	}

	TrxPropagation getTrxMode();

	OnRunnableSuccess getOnRunnableSuccess();

	OnRunnableFail getOnRunnableFail();

}
