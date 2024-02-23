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

import com.google.common.collect.ImmutableList;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableFail;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableSuccess;
import org.adempiere.ad.trx.api.ITrxRunConfig.TrxPropagation;
import org.adempiere.ad.trx.exceptions.TrxException;
import org.adempiere.ad.trx.exceptions.TrxNotFoundException;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutor;
import org.adempiere.util.lang.IContextAware;
import org.compiere.util.TrxRunnable;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * Transaction Manager
 *
 * @author tsa
 */
public interface ITrxManager extends ISingletonService
{
	/**
	 * @return list of current active transactions
	 */
	List<ITrx> getActiveTransactionsList();

	/**
	 * Remove given transaction from active transactions list.
	 * <p>
	 * NOTE: don't call it directly, it will be called by the framework.
	 *
	 * @return true if removed
	 */
	boolean remove(ITrx trx);

	/**
	 * Creates a builder for a transaction runnable configuration.
	 */
	ITrxRunConfigBuilder newTrxRunConfigBuilder();

	interface ITrxRunConfigBuilder
	{
		/**
		 * Decide if the connection should perform an auto-commit after each statement.
		 * Makes e.g. sense with long-running transactions that only do selects (yes, also a select acquires a lock).
		 * <p>
		 * The default is <code>false</code>.
		 */
		ITrxRunConfigBuilder setAutoCommit(boolean autoCommit);

		/**
		 * The default is {@link TrxPropagation#REQUIRES_NEW}.
		 */
		ITrxRunConfigBuilder setTrxPropagation(TrxPropagation trxPropagation);

		/**
		 * What to do if a runnable succeeds. Ignored if autoCommit is <code>true</code>.
		 * <p>
		 * The default is {@link OnRunnableSuccess#COMMIT}
		 */
		ITrxRunConfigBuilder setOnRunnableSuccess(OnRunnableSuccess onRunnableSuccess);

		/**
		 * Specify what to do if a runnable fails. Ignored if autoCommit is <code>true</code>.
		 * <p>
		 * the default is {@link OnRunnableFail#ASK_RUNNABLE}.
		 */
		ITrxRunConfigBuilder setOnRunnableFail(OnRunnableFail onRunnableFail);

		ITrxRunConfig build();
	}

	/**
	 * Creates transaction runnable configuration.
	 *
	 * @deprecated please use {@link #newTrxRunConfigBuilder()} instead.
	 */
	@Deprecated
	ITrxRunConfig createTrxRunConfig(TrxPropagation trxPropagation, OnRunnableSuccess onRunnableSuccess, OnRunnableFail onRunnableFail);

	/**
	 * Get/Create actual transaction.
	 *
	 * @param trxName            transaction name; it is assumed that trxName is not null
	 * @param onTrxMissingPolicy what to do if transaction was not found
	 * @return Transaction or null
	 */
	ITrx get(String trxName, OnTrxMissingPolicy onTrxMissingPolicy);

	/**
	 * Get/Create actual transaction.
	 *
	 * @param trxName   transaction name; it is assumed that trxName is not null
	 * @param createNew if false, null is returned if not found
	 * @return Transaction or null
	 */
	ITrx get(String trxName, boolean createNew);

	/**
	 * Gets existing transaction.
	 *
	 * @return {@link ITrx} if transaction exists; null if transaction does not exist or trxName is null
	 */
	ITrx getTrxOrNull(String trxName);

	/**
	 * Get existing transaction.
	 * <p>
	 * If trxName is null transaction then {@link ITrx#TRX_None} will be returned.
	 *
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
	 * @param prefix    optional prefix
	 * @param createTrx if true, the transaction will also be created
	 * @return unique name
	 */
	String createTrxName(String prefix, boolean createTrx);

	<T> T callInNewTrx(Callable<T> callable);

	void runInNewTrx(Runnable runnable);

	/**
	 * Same as calling {@link #run(String, TrxRunnable)} with trxName=null
	 *
	 * @see #run(String, TrxRunnable)
	 */
	void runInNewTrx(TrxRunnable runnable);

	/**
	 * Same as calling {@link #call(String, TrxCallable)} with trxName=null
	 *
	 * @return callable's return value
	 * @see #call(String, TrxCallable)
	 */
	<T> T callInNewTrx(TrxCallable<T> callable);

	/**
	 * Executes the runnable object. Same as calling {@link #run(String, boolean, TrxRunnable)} with manageTrx = false. This means that it uses the trx with the the given trxName, creates a savepoint
	 * to roll back to in case of problems and doesn't commit in case of success.
	 *
	 * @param trxName transaction name
	 * @param r       runnable object
	 * @see #run(String, boolean, TrxRunnable)
	 */
	void run(String trxName, TrxRunnable r);

	void run(String trxName, Runnable runnable);

	default void runInThreadInheritedTrx(final TrxRunnable runnable)
	{
		run(ITrx.TRXNAME_ThreadInherited, runnable);
	}

	default void runInThreadInheritedTrx(final Runnable runnable)
	{
		run(ITrx.TRXNAME_ThreadInherited, runnable);
	}

	/**
	 * Executes the callable object. Same as calling {@link #call(String, boolean, TrxCallable)} with manageTrx = false. This means that it uses the trx with the the given trxName, creates a savepoint
	 * and to roll back to in case of problems and doesn't commit in case of success.
	 *
	 * @param trxName transaction name
	 * @return callable's return value
	 * @see #call(String, TrxCallable)
	 */
	<T> T call(String trxName, TrxCallable<T> callable);

	default <T> T callInThreadInheritedTrx(@NonNull final TrxCallable<T> callable)
	{
		return call(ITrx.TRXNAME_ThreadInherited, callable);
	}

	/**
	 * @see #call(String, boolean, TrxCallable)
	 */
	@Deprecated
	void run(String trxName, boolean manageTrx, TrxRunnable r);

	/**
	 * Execute the callable object using either the provided transaction or create a new one, depending on the {@code manageTrx} parameter.
	 * If execution fails, database operations will be rolled back.
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * Trx.call("myTrxNamePrefix", true, new {@link TrxCallable}() {
	 *     public SomeResult call() {
	 *         // do something using in transaction
	 *     }
	 * )};
	 * </pre>
	 * <p>
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
	 * @param trxName   transaction name (if {@link ITrx#TRXNAME_None}, or <code>mangeTrx</code> is true a new transaction will be created)
	 * @param manageTrx if <code>true</code>, or <code>trxName</code> is {@link #isNull(String)}, the transaction will be managed by this method. Also, in case transaction is managed, a trxName will
	 *                  be created using given "trxName" as name prefix. If trxName is null a new transaction name will be created with prefix "TrxRun". If trxName is null, the transaction will be
	 *                  automatically managed, even if the manageTrx parameter is false.
	 * @return callable's return value
	 */
	@Deprecated
	<T> T call(String trxName, boolean manageTrx, TrxCallable<T> callable);

	/**
	 * Execute the given <code>runnable</code> config.
	 */
	@Deprecated
	void run(String trxName, ITrxRunConfig cfg, TrxRunnable runnable);

	/**
	 * Execute the given <code>callable</code> in given transation using given transaction options.
	 *
	 * @return callable's return value
	 */
	@Deprecated
	<T> T call(String trxName, ITrxRunConfig cfg, TrxCallable<T> callable);

	/**
	 * Convenient method to execute the given runnable, but out of transaction (i.e. NO transaction management will be involved).
	 *
	 * @param r runnable
	 */
	void runOutOfTransaction(TrxRunnable r);

	/**
	 * Run after current transaction is committed. If no transaction, the code is executed right away.
	 */
	void runAfterCommit(final Runnable runnable);

	/**
	 * Commit transaction for given <code>trxName</code>.
	 *
	 * @throws TrxException if transaction was not found or is null
	 */
	void commit(String trxName);

	/**
	 * Gets {@link ITrxListenerManager} associated with given transaction, identified by <code>trxName</code>.
	 *
	 * @return {@link ITrxListenerManager}; never returns null
	 * @throws TrxNotFoundException if transaction was not found
	 */
	ITrxListenerManager getTrxListenerManager(String trxName);

	/**
	 * Same as {@link #getTrxListenerManager(String)}.
	 * <p>
	 * But in case
	 * <ul>
	 * <li>the transaction {@link #isNull(String)}
	 * <li>or it's an inherited transaction but the actual transaction was not found
	 * <li>or transaction no longer exists or was closed
	 * </ul>
	 * this method will return a pseudo- {@link ITrxListenerManager} which automatically commits when a listener is registered.
	 *
	 * @return auto-commit {@link ITrxListenerManager}; never returns null
	 */
	ITrxListenerManager getTrxListenerManagerOrAutoCommit(String trxName);

	/**
	 * Same as {@link #getTrxListenerManagerOrAutoCommit(String)} but it will use {@link ITrx#TRXNAME_ThreadInherited}.
	 */
	ITrxListenerManager getCurrentTrxListenerManagerOrAutoCommit();

	/**
	 * @return true if given transaction is "null" (i.e. no transaction)
	 */
	boolean isNull(ITrx trx);

	/**
	 * Checks if given <code>trxName</code> is a "null"/no transaction.
	 * <p>
	 * Mainly, null/no transaction names are <code>null</code>, empty string, {@link ITrx#TRXNAME_None}, {@link ITrx#TRXNAME_NoneNotNull}.
	 *
	 * @return true if given transaction is "null" (i.e. no transaction)
	 */
	boolean isNull(@Nullable String trxName);

	/**
	 * @return true if transaction is not null and it's active (e.g. not already committed/closed)
	 */
	default boolean isActive(@Nullable final ITrx trx)
	{
		return trx != null && !isNull(trx) && trx.isActive();
	}

	default boolean isActive(@Nullable final String trxName)
	{
		if (isNull(trxName))
		{
			return false;
		}
		final ITrx trx = get(trxName, OnTrxMissingPolicy.ReturnTrxNone);
		return isActive(trx);
	}

	/**
	 * Sets TrxName generator to be used for generating new transaction names.
	 */
	void setTrxNameGenerator(ITrxNameGenerator trxNameGenerator);

	/**
	 * Checks if given <code>trxName</code> is a valid and concrete transaction name.
	 * <p>
	 * If there is a transaction placeholder like {@link ITrx#TRXNAME_None}, {@link ITrx#TRXNAME_ThreadInherited} etc, then it will return <code>false</code>.
	 *
	 * @return <code>true</code> if it's a valid transaction name
	 */
	boolean isValidTrxName(String trxName);

	/**
	 * @return <code>true</code> if given transactions are the same
	 */
	boolean isSameTrxName(String trxName1, String trxName2);

	/**
	 * @return <code>true</code> if given transactions are the same
	 */
	boolean isSameTrxName(ITrx trx, String trxName);

	/**
	 * Assumes that given transaction name is not null.
	 * <p>
	 * If it's null an exception will be thrown
	 */
	void assertTrxNameNotNull(String trxName);

	/**
	 * Assumes that given transaction name is null.
	 * <p>
	 * If it's NOT null an exception will be thrown
	 */
	void assertTrxNameNull(String trxName);

	/**
	 * Assumes that transaction <b>is not</b> null for given <code>contextProvider</code>
	 * <p>
	 * If it's null a {@link TrxException} will be thrown.
	 */
	void assertTrxNotNull(IContextAware contextProvider);

	/**
	 * Assumes that transaction <b>is</b> null for given <code>contextProvider</code>
	 * <p>
	 * If <code>contextProvider</code> is NULL or it's transaction is NOT null an exception will be thrown.
	 */
	void assertTrxNull(IContextAware contextProvider);

	/**
	 * Assumes that all models have <code>trxNameExpected</code>
	 */
	<T> void assertModelsTrxName(String trxNameExpected, Collection<T> models);

	/**
	 * Assumes that given <code>model</code> have <code>trxNameExpected</code>
	 */
	<T> void assertModelTrxName(String trxNameExpected, T model);

	/**
	 * Assumes that the transaction name of given <code>model</code> is not null.
	 *
	 * @see #isNull(String)
	 */
	<T> void assertModelTrxNameNotNull(T model);

	/**
	 * @return true if current thread has thread inherited transaction set
	 */
	boolean hasThreadInheritedTrx();

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
	 * <p>
	 * NOTE: please don't use this method directly. It is exposed in interface only for those who are implementing low level transaction management functionalities (e.g.
	 * {@link ITrxItemProcessorExecutor}).
	 *
	 * @return previous trxName (before setting this one)
	 */
	String setThreadInheritedTrxName(@Nullable String trxName);

	/**
	 * Gets current Thread's transaction name.
	 *
	 * @return current thread's trxName; if there is no thread transaction then <code>null</code> will be returned
	 */
	String getThreadInheritedTrxName();

	/**
	 * Gets current Thread's transaction name.
	 *
	 * @return current thread's trx
	 */
	String getThreadInheritedTrxName(OnTrxMissingPolicy onTrxMissingPolicy);

	/**
	 * Gets current Thread's transaction.
	 *
	 * @return current thread's trx
	 */
	ITrx getThreadInheritedTrx(OnTrxMissingPolicy onTrxMissingPolicy);

	/**
	 * Creates a context aware object which will use given context and for transaction will return current thread transaction (see {@link #getThreadInheritedTrxName()}).
	 * <p>
	 * If thread transaction {@link #isNull(String)} or was not set then {@link IContextAware#getTrxName()} will throw an exception.
	 *
	 * @return context aware (i.e. context provider)
	 */
	IContextAware createThreadContextAware(Properties ctx);

	/**
	 * @param contextProvider used to get the underlying "Properties ctx".
	 * @return context aware (i.e. context provider)
	 * @see #createThreadContextAware(Properties)
	 */
	IContextAware createThreadContextAware(Object contextProvider);

	/**
	 * Sets the default {@link OnRunnableFail} to be used when a new {@link #run(String, TrxRunnable)} is executed.
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

	default <T> void accumulateAndProcessAfterCommit(
			@NonNull final String propertyName,
			@NonNull final Collection<T> itemsToAccumulate,
			@NonNull final Consumer<ImmutableList<T>> afterCommitListProcessor)
	{
		final ITrx trx = getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		if (isActive(trx))
		{
			trx.accumulateAndProcessAfterCommit(propertyName, itemsToAccumulate, afterCommitListProcessor);
		}
		else
		{
			afterCommitListProcessor.accept(ImmutableList.copyOf(itemsToAccumulate));
		}
	}

	default <T> void accumulateAndProcessAfterRollback(
			@NonNull final String propertyName,
			@NonNull final Collection<T> itemsToAccumulate,
			@NonNull final Consumer<ImmutableList<T>> processor)
	{
		final ITrx trx = getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		if (isActive(trx))
		{
			trx.accumulateAndProcessAfterRollback(propertyName, itemsToAccumulate, processor);
		}
	}

}
