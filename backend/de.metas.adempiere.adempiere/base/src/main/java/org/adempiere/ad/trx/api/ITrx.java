package org.adempiere.ad.trx.api;

import com.google.common.collect.ImmutableList;
import de.metas.util.collections.ListAccumulator;
import lombok.NonNull;
import org.adempiere.exceptions.DBException;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Transaction interface
 *
 * @author tsa
 */
public interface ITrx
{
	/**
	 * Old No Transaction Marker.
	 * <p>
	 * NOTE: we keep this until we get rid of comparations with "null"
	 */
	String TRXNAME_None = null;

	/**
	 * No Transaction Marker
	 */
	String TRXNAME_NoneNotNull = "<<None>>";

	/**
	 * Transaction Name marker for Thread Local inherited transactions
	 */
	String TRXNAME_ThreadInherited = "<<ThreadInherited>>";

	/**
	 * Prefix used for local transactions
	 */
	String TRXNAME_PREFIX_LOCAL = "POSave";

	/**
	 * No Transaction marker
	 */
	ITrx TRX_None = null;

	/**
	 * Get Name
	 *
	 * @return name
	 */
	String getTrxName();

	/**
	 * Start Trx
	 *
	 * @return true if trx started
	 */
	boolean start();

	/**
	 * End Transaction and Close Connection
	 *
	 * @return true if success
	 */
	boolean close();

	/**
	 * Transaction is Active
	 *
	 * @return true if transaction active (e.g. not already committed/closed)
	 */
	boolean isActive();

	/**
	 * Then this trx obtains a connection, it will check and invoke {@link Connection#setAutoCommit(boolean)} if necessary.
	 */
	boolean isAutoCommit();

	/**
	 * @return The start time of this transaction
	 */
	Date getStartTime();

	/**
	 * Commit
	 *
	 * @param throwException if true, re-throws exception
	 * @return true if success
	 */
	boolean commit(boolean throwException) throws SQLException;

	/**
	 * Rollback.
	 * <p>
	 * NOTE: this method NEVER EVER throws an exception.
	 *
	 * @return <code>true</code> if success, <code>false</code> if failed or the transaction is already rolled back, or if {@link #isAutoCommit()} is <code>true</code>.
	 */
	boolean rollback();

	/**
	 * Rollback
	 *
	 * @param throwException if true, re-throws exception
	 * @return same as {@link #rollback()}
	 */
	boolean rollback(boolean throwException) throws SQLException;

	/**
	 * Rollback to given savepoint
	 *
	 * @return true if success
	 * @throws DBException on fail
	 */
	boolean rollback(ITrxSavepoint savepoint) throws DBException;

	/**
	 * Create transaction savepoint
	 */
	ITrxSavepoint createTrxSavepoint(String name) throws DBException;

	/**
	 * Release given savepoint
	 */
	void releaseSavepoint(ITrxSavepoint savepoint);

	/**
	 * Gets the {@link ITrxListenerManager} associated with this transaction
	 *
	 * @return {@link ITrxListenerManager}; never returns null
	 * <p>
	 * Task 04265
	 */
	ITrxListenerManager getTrxListenerManager();

	/**
	 * Gets the {@link ITrxManager} which created this transaction and which is responsible for its lifecycle
	 */
	ITrxManager getTrxManager();

	/**
	 * Sets custom transaction property.
	 * <p>
	 * NOTE: developer is free to use it as she/he wants.
	 *
	 * @return old value or null
	 */
	@Nullable
	<T> T setProperty(final String name, @Nullable Object value);

	/**
	 * Gets custom transaction property.
	 * <p>
	 * NOTE: developer is free to use it as she/he wants.
	 *
	 * @return property's value or null
	 */
	<T> T getProperty(final String name);

	/**
	 * Gets custom transaction property.
	 * <p>
	 * NOTE: developer is free to use it as she/he wants.
	 *
	 * @param valueInitializer used to create the new value in case it does not already exist
	 * @return property's value or null
	 */
	<T> T getProperty(String name, Supplier<T> valueInitializer);

	/**
	 * @see #getProperty(String, Supplier)
	 */
	<T> T getProperty(String name, Function<ITrx, T> valueInitializer);

	/**
	 * Gets custom transaction property.
	 * If the property does not exist it will call <code>valueInitializer</code>.
	 * After transaction is committed the <code>afterCommitValueProcessor</code> will be called.
	 *
	 * @param valueInitializer          used to create the new value in case it does not already exist.
	 * @param afterCommitValueProcessor called after transaction is committed
	 * @return property's value or null
	 */
	default <T> T getPropertyAndProcessAfterCommit(
			@NonNull final String propertyName,
			@NonNull final Supplier<T> valueInitializer,
			@NonNull final Consumer<T> afterCommitValueProcessor)
	{
		return getProperty(propertyName, () -> {
			final T value = valueInitializer.get();
			runAfterCommit(() -> afterCommitValueProcessor.accept(value));
			return value;
		});
	}

	default <T> void accumulateAndProcessAfterCommit(
			@NonNull final String propertyName,
			@NonNull final Collection<T> itemsToAccumulate,
			@NonNull final Consumer<ImmutableList<T>> afterCommitListProcessor)
	{
		getProperty(propertyName, () -> {
			final ListAccumulator<T> accum = new ListAccumulator<>();
			runAfterCommit(() -> accum.flush(afterCommitListProcessor));
			return accum;
		})
				.addAll(itemsToAccumulate);
	}

	default <T> void accumulateAndProcessAfterRollback(
			@NonNull final String propertyName,
			@NonNull final Collection<T> itemsToAccumulate,
			@NonNull final Consumer<ImmutableList<T>> processor)
	{
		getProperty(propertyName, () -> {
			final ListAccumulator<T> accum = new ListAccumulator<>();
			runAfterRollback(() -> accum.flush(processor));
			return accum;
		})
				.addAll(itemsToAccumulate);
	}

	/**
	 * Change property using the value remapping function and then returns it.
	 * If the remapping function returns null then the property will be removed.
	 *
	 * @param name                   property name
	 * @param valueRemappingFunction function which takes as input the old value and returns the new value
	 * @return new value or null in case the remapping function returned null
	 */
	<T> T setAndGetProperty(String name, Function<T, T> valueRemappingFunction);

	default void runAfterCommit(@NonNull final Runnable runnable)
	{
		getTrxListenerManager().runAfterCommit(runnable);
	}

	default void runAfterRollback(@NonNull final Runnable runnable)
	{
		getTrxListenerManager().runAfterRollback(runnable);
	}
}
