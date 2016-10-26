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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;

import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableFail;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableSuccess;
import org.adempiere.ad.trx.api.ITrxRunConfig.TrxPropagation;
import org.adempiere.ad.trx.exceptions.TrxException;
import org.adempiere.ad.trx.exceptions.TrxNotFoundException;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutor;
import org.adempiere.model.IContextAware;
import org.adempiere.util.ISingletonService;
import org.compiere.util.TrxRunnable;

/**
 * Transaction Manager
 *
 * @author tsa
 *
 */
public interface ITrxManager extends ISingletonService
{
	/**
	 *
	 * @return array of current active transactions
	 */
	ITrx[] getActiveTransactions();

	/**
	 *
	 * @return list of current active transactions
	 */
	List<ITrx> getActiveTransactionsList();

	/**
	 * Remove given transaction from active transactions list.
	 *
	 * NOTE: don't call it directly, it will be called by the framework.
	 *
	 * @param trx
	 * @return true if removed
	 */
	boolean remove(ITrx trx);

	/**
	 * Creates transaction runnable configuration
	 *
	 * @param trxMode
	 * @param onRunnableSuccess
	 * @param onRunnableFail
	 * @return
	 */
	ITrxRunConfig createTrxRunConfig(TrxPropagation trxMode, OnRunnableSuccess onRunnableSuccess, OnRunnableFail onRunnableFail);

	/**
	 * Get/Create actual transaction.
	 *
	 * @param trxName transaction name; it is assumed that trxName is not null
	 * @param createNew if false, null is returned if not found
	 * @param onTrxMissingPolicy what to do if transaction was not found
	 * @return Transaction or null
	 */
	ITrx get(String trxName, OnTrxMissingPolicy onTrxMissingPolicy);

	/**
	 * Get/Create actual transaction.
	 *
	 * @param trxName transaction name; it is assumed that trxName is not null
	 * @param createNew if false, null is returned if not found
	 * @return Transaction or null
	 */
	ITrx get(String trxName, boolean createNew);

	/**
	 * Gets existing transaction.
	 *
	 * @param trxName
	 * @return {@link ITrx} if transaction exists; null if transaction does not exist or trxName is null
	 */
	ITrx getTrxOrNull(String trxName);

	/**
	 * Get existing transaction.
	 *
	 * If trxName is null transaction then {@link ITrx#TRX_None} will be returned.
	 *
	 * @param trxName
	 * @return existing transaction.
	 * @throws TrxException is transaction was not found
	 */
	ITrx getTrx(String trxName);

	/**
	 * Create unique Transaction Name <b>and instantly create the new trx</b>.
	 *
	 * @param prefix optional prefix
	 * @return unique name
	 */
	String createTrxName(String prefix);

	/**
	 * Create unique Transaction Name
	 *
	 * @param prefix optional prefix
	 * @param createTrx if true, the transaction will also be created
	 *
	 * @return unique name
	 */
	String createTrxName(String prefix, boolean createTrx);

	<T> T call(Callable<T> callable);

	/**
	 * Same as calling {@link #run(String, TrxRunnable)} with trxName=null
	 *
	 * @see #run(String, TrxRunnable)
	 */
	void run(TrxRunnable r);

	/**
	 * Same as calling {@link #call(String, TrxRunnable)} with trxName=null
	 *
	 * @return callable's return value
	 * @see #call(String, TrxRunnable)
	 */
	<T> T call(TrxCallable<T> callable);

	/**
	 * Executes the runnable object. Same as calling {@link #run(String, boolean, TrxRunnable)} with manageTrx = false. This means that it uses the trx with the the given trxName, creates a savepoint
	 * and to roll back to in case of problems and doesn't commit in case of success.
	 *
	 * @param trxName transaction name
	 *
	 * @param r runnable object
	 * @see #run(String, boolean, TrxRunnable)
	 */
	void run(String trxName, TrxRunnable r);

	/**
	 * Executes the callable object. Same as calling {@link #call(String, boolean, TrxRunnable)} with manageTrx = false. This means that it uses the trx with the the given trxName, creates a savepoint
	 * and to roll back to in case of problems and doesn't commit in case of success.
	 *
	 * @param trxName transaction name
	 *
	 * @param r runnable object
	 * @return callable's return value
	 * @see #call(String, boolean, TrxRunnable)
	 */
	<T> T call(String trxName, TrxCallable<T> callable);

	/**
	 * @see #call(String, boolean, TrxCallable)
	 */
	void run(String trxName, boolean manageTrx, TrxRunnable r);

	/**
	 * Execute callable object using provided transaction. If execution fails, database operations will be rolled back.
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * Trx.call(null, new {@link TrxCallable}() {
	 *     public SomeResult call() {
	 *         // do something using in transaction
	 *     }
	 * )};
	 * </pre>
	 *
	 * Parameters and values:
	 * <ul>
	 * <li>trxName=null, manageTrx in {true, false} => create a new trxName (and thus a new local trx) with prefix <code>"TrxRun"</code>. Commit in case of success, undo all changes in case of
	 * problems.
	 * <li>trxName!=null, manageTrx=true => create a new trxName (and thus a new local trx) with prefix being the given <code>trxName</code>. Commit in case of success, undo all changes in case of
	 * problems.
	 * <li>trxName!=null, manageTrx=false => use the trx with the the given trxName; create a savepoint and to roll back to in case of problems. Don't commit in case of success.
	 * </ul>
	 * *
	 *
	 * @param trxName transaction name (if {@link ITrx#TRXNAME_None}, or <code>mangeTrx</code> is true a new transaction will be created)
	 * @param manageTrx if <code>true</code>, or <code>trxName</code> is {@link #isNull(String)}, the transaction will be managed by this method. Also, in case transaction is managed, a trxName will
	 *            be created using given "trxName" as name prefix. If trxName is null a new transaction name will be created with prefix "TrxRun". If trxName is null, the transaction will be
	 *            automatically managed, even if the manageTrx parameter is false.
	 * @param callable
	 * @return callable's return value
	 */
	<T> T call(String trxName, boolean manageTrx, TrxCallable<T> callable);

	/**
	 * Execute the given <code>runnable</code> config.
	 *
	 * @param trxName
	 * @param cfg
	 * @param runnable
	 *
	 */
	void run(String trxName, ITrxRunConfig cfg, TrxRunnable runnable);

	/**
	 * Execute the given <code>callable</code> in given transation using given transaction options.
	 *
	 * @param trxName
	 * @param cfg
	 * @param runnable
	 * @return callable's return value
	 */
	<T> T call(String trxName, ITrxRunConfig cfg, TrxCallable<T> callable);

	/**
	 * Convenient method to execute the given runnable, but out of transaction (i.e. NO transaction management will be involved).
	 *
	 * @param r runnable
	 */
	void runOutOfTransaction(TrxRunnable r);

	/**
	 * Commit transaction for given <code>trxName</code>.
	 *
	 * @param trxName
	 * @throws TrxException if transaction was not found or is null
	 */
	void commit(String trxName);

	/**
	 * Gets {@link ITrxListenerManager} associated with given transaction, identified by <code>trxName</code>.
	 *
	 * @param trxName
	 * @return {@link ITrxListenerManager}; never returns null
	 * @throws TrxNotFoundException if transaction was not found
	 */
	ITrxListenerManager getTrxListenerManager(String trxName);

	/**
	 * Same as {@link #getTrxListenerManager(String)}.
	 *
	 * But in case
	 * <ul>
	 * <li>the transaction {@link #isNull(String)}
	 * <li>or it's an inherited transaction but the actual transaction was not found
	 * <li>or transaction no longer exists or was closed
	 * </ul>
	 * this method will return a pseudo- {@link ITrxListenerManager} which automatically commits when a listener is registered.
	 *
	 *
	 * @param trxName
	 * @return auto-commit {@link ITrxListenerManager}; never returns null
	 */
	ITrxListenerManager getTrxListenerManagerOrAutoCommit(String trxName);

	/**
	 *
	 * @param trx
	 * @return true if given transaction is "null" (i.e. no transaction)
	 */
	boolean isNull(ITrx trx);

	/**
	 * Checks if given <code>trxName</code> is a "null"/no transaction.
	 *
	 * Mainly, null/no transaction names are <code>null</code>, empty string, {@link ITrx#TRXNAME_None}, {@link ITrx#TRXNAME_NoneNotNull}.
	 *
	 * @param trxName
	 * @return true if given transaction is "null" (i.e. no transaction)
	 */
	boolean isNull(String trxName);

	/**
	 * Sets TrxName generator to be used for generating new transaction names.
	 *
	 * @param trxNameGenerator
	 */
	void setTrxNameGenerator(ITrxNameGenerator trxNameGenerator);

	/**
	 * Checks if given <code>trxName</code> is a valid and concrete transaction name.
	 *
	 * If there is a transaction placeholder like {@link ITrx#TRXNAME_None}, {@link ITrx#TRXNAME_ThreadInherited} etc, then it will return <code>false</code>.
	 *
	 * @param trxName
	 * @return <code>true</code> if it's a valid transaction name
	 */
	boolean isValidTrxName(String trxName);

	/**
	 *
	 * @param trxName1
	 * @param trxName2
	 * @return <code>true</code> if given transactions are the same
	 */
	boolean isSameTrxName(String trxName1, String trxName2);

	/**
	 *
	 * @param trx1
	 * @param trxName2
	 * @return <code>true</code> if given transactions are the same
	 */
	boolean isSameTrxName(ITrx trx1, String trxName2);

	/**
	 * Assumes that given transaction name is not null.
	 *
	 * If it's null an exception will be thrown
	 *
	 * @param trxName
	 */
	void assertTrxNameNotNull(String trxName);

	/**
	 * Assumes that given transaction name is null.
	 *
	 * If it's NOT null an exception will be thrown
	 *
	 * @param trxName
	 */
	void assertTrxNameNull(String trxName);

	/**
	 * Assumes that transaction <b>is not</b> null for given <code>contextProvider</code>
	 *
	 * If it's null a {@link TrxException} will be thrown.
	 *
	 * @param contextProvider
	 */
	void assertTrxNotNull(IContextAware contextProvider);

	/**
	 * Assumes that transaction <b>is</b> null for given <code>contextProvider</code>
	 *
	 * If <code>contextProvider</code> is NULL or it's transaction is NOT null an exception will be thrown.
	 *
	 * @param contextProvider
	 */
	void assertTrxNull(IContextAware contextProvider);

	/**
	 * Assumes that all models have <code>trxNameExpected</code>
	 *
	 * @param trxNameExpected
	 * @param models
	 */
	<T> void assertModelsTrxName(String trxNameExpected, Collection<T> models);

	/**
	 * Assumes that given <code>model</code> have <code>trxNameExpected</code>
	 *
	 * @param trxNameExpected
	 * @param model
	 */
	<T> void assertModelTrxName(String trxNameExpected, T model);

	/**
	 * Assumes that the transaction name of given <code>model</code> is not null.
	 *
	 * @param model
	 * @see #isNull(String)
	 */
	<T> void assertModelTrxNameNotNull(T model);

	/**
	 * Assumes current thread has thread inherited transaction set.
	 */
	void assertThreadInheritedTrxExists();

	/**
	 * Assumes current thread does not have thread inherited transaction set.
	 */
	void assertThreadInheritedTrxNotExists();

	/**
	 * Sets current Thread's transaction.
	 *
	 * NOTE: please don't use this method directly. It is exposed in interface only for those who are implementing low level transaction management functionalities (e.g.
	 * {@link ITrxItemProcessorExecutor}).
	 *
	 * @param trxName
	 * @return previous trxName (before setting this one)
	 */
	String setThreadInheritedTrxName(String trxName);

	/**
	 * Gets current Thread's transaction name.
	 *
	 * @return current thread's trxName; if there is no thread transaction then <code>null</code> will be returned
	 */
	String getThreadInheritedTrxName();

	/**
	 * Gets current Thread's transaction name.
	 *
	 * @param onTrxMissingPolicy
	 * @return current thread's trx
	 */
	public String getThreadInheritedTrxName(final OnTrxMissingPolicy onTrxMissingPolicy);

	/**
	 * Gets current Thread's transaction.
	 *
	 * @param onTrxMissingPolicy
	 * @return current thread's trx
	 */
	ITrx getThreadInheritedTrx(final OnTrxMissingPolicy onTrxMissingPolicy);

	/**
	 * Creates a context aware object which will use given context and for transaction will return current thread transaction (see {@link #getThreadInheritedTrxName()}).
	 *
	 * If thread transaction {@link #isNull(String)} or was not set then {@link IContextAware#getTrxName()} will throw an exception.
	 *
	 * @param ctx
	 * @return context aware (i.e. context provider)
	 */
	IContextAware createThreadContextAware(Properties ctx);

	/**
	 *
	 * @param contextProvider used to get the underlying "Properties ctx".
	 * @return context aware (i.e. context provider)
	 * @see #createThreadContextAware(Properties)
	 */
	IContextAware createThreadContextAware(Object contextProvider);

	/**
	 * Sets the default {@link OnRunnableFail} to be used when a new {@link #run(TrxRunnable)} is executed.
	 *
	 * @param onRunnableFail
	 */
	void setThreadInheritedOnRunnableFail(OnRunnableFail onRunnableFail);

	void setDebugTrxCloseStacktrace(boolean debugTrxCloseStacktrace);

	boolean isDebugTrxCloseStacktrace();

	void setDebugTrxCreateStacktrace(boolean debugTrxCreateStacktrace);

	boolean isDebugTrxCreateStacktrace();

	List<ITrx> getDebugClosedTransactions();

	void setDebugClosedTransactions(boolean enabled);

	boolean isDebugClosedTransactions();

	void setDebugConnectionBackendId(boolean debugConnectionBackendId);

	boolean isDebugConnectionBackendId();
}
