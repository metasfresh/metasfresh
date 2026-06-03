package org.adempiere.ad.trx.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

import com.google.common.annotations.VisibleForTesting;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.DBDeadLockDetectedException;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.function.Supplier;

/**
 * Retries an action up to {@code maxAttempts} times on a DB deadlock, then re-throws.
 * A fixed backoff sleep separates consecutive attempts.
 */
public class DeadlockRetryPolicy
{
	private static final Logger logger = LogManager.getLogger(DeadlockRetryPolicy.class);

	/** PostgreSQL SQLSTATE for "deadlock detected". */
	static final String PG_SQLSTATE_DEADLOCK_DETECTED = "40P01";

	/** Maximum number of attempts (first attempt + retries). */
	@VisibleForTesting
	public static final int DEFAULT_MAX_ATTEMPTS = 3;

	/** Milliseconds to sleep between attempts. */
	@VisibleForTesting
	public static final long DEFAULT_BACKOFF_MILLIS = 5_000L;

	/** Cached default instance (immutable — all fields final, no mutators). */
	public static final DeadlockRetryPolicy DEFAULT = DeadlockRetryPolicy.builder()
			.maxAttempts(DEFAULT_MAX_ATTEMPTS)
			.backoffMillis(DEFAULT_BACKOFF_MILLIS)
			.build();

	private final int maxAttempts;
	private final long backoffMillis;

	@Builder
	private DeadlockRetryPolicy(final int maxAttempts, final long backoffMillis)
	{
		this.maxAttempts = maxAttempts > 0 ? maxAttempts : DEFAULT_MAX_ATTEMPTS;
		this.backoffMillis = backoffMillis >= 0 ? backoffMillis : DEFAULT_BACKOFF_MILLIS; // 0 = no sleep (tests)
	}

	/** Returns the cached default policy (3 attempts, 5 s backoff). */
	public static DeadlockRetryPolicy defaults()
	{
		return DEFAULT;
	}

	/** Runs {@code action}; retries on deadlock up to {@code maxAttempts - 1} times. */
	public void run(@NonNull final Runnable action, final Object... context)
	{
		call(() -> {
			action.run();
			return null;
		}, context);
	}

	/** Calls {@code action}; retries on deadlock up to {@code maxAttempts - 1} times. */
	public <T> T call(@NonNull final Supplier<T> action, final Object... context)
	{
		RuntimeException lastException = null;

		for (int attempt = 1; attempt <= maxAttempts; attempt++)
		{
			try
			{
				return action.get();
			}
			catch (final RuntimeException ex)
			{
				if (!isDeadlock(ex))
				{
					throw ex;
				}

				lastException = ex;
				logger.warn(
						"Deadlock detected on attempt {}/{} for {}; will retry after {} ms",
						attempt, maxAttempts, contextToString(context), backoffMillis, ex);

				if (attempt < maxAttempts)
				{
					sleepQuietly(backoffMillis);
				}
			}
		}

		// All attempts exhausted — rethrow so the error path records it
		throw lastException;
	}

	private static String contextToString(final Object... context)
	{
		return context == null || context.length == 0 ? "" : Arrays.deepToString(context);
	}

	/**
	 * Returns a {@link TrxCallable} that applies the deadlock-retry policy when {@code condition} is true,
	 * or returns {@code callable} unchanged when false.
	 *
	 * <p>When {@code condition} is true the returned wrapper runs each attempt in a <em>fresh</em> transaction
	 * via {@link ITrxManager#callInNewTrx(TrxCallable)}.  This is the legitimate exception to the
	 * "prefer runInThreadInheritedTrx" rule: a deadlock <em>aborts</em> the current transaction, so any
	 * subsequent statement in it would fail with "current transaction is aborted"; each retry therefore
	 * <strong>must</strong> run in an isolated, newly-opened transaction.
	 *
	 * @param condition if {@code true} the deadlock-retry wrapper is applied; if {@code false} {@code callable} is returned as-is
	 * @param callable  the work to protect
	 * @param context   diagnostic context passed to the retry logger
	 * @return a {@link TrxCallable} — either the original or a retry-wrapped one
	 */
	public <T> TrxCallable<T> wrapIf(final boolean condition, @NonNull final TrxCallable<T> callable, final Object... context)
	{
		if (!condition)
		{
			return callable;
		}
		return () -> call(() -> Services.get(ITrxManager.class).callInNewTrx(callable), context);
	}

	/**
	 * Returns {@code true} when the throwable (or any cause) is a DB deadlock:
	 * a {@link DBDeadLockDetectedException} or {@link SQLException} with SQLSTATE {@code 40P01}.
	 * Self-contained to avoid a DB.isPostgreSQL() dependency that fails in unit-test mode.
	 */
	@VisibleForTesting
	public static boolean isDeadlock(@Nullable final Throwable ex)
	{
		for (Throwable cause = ex; cause != null; cause = cause.getCause())
		{
			if (cause instanceof DBDeadLockDetectedException)
			{
				return true;
			}
			if (cause instanceof SQLException && PG_SQLSTATE_DEADLOCK_DETECTED.equals(((SQLException)cause).getSQLState()))
			{
				return true;
			}
			if (cause.getCause() == cause)
			{
				break;
			}
		}
		return false;
	}

	/** Sleeps for {@code millis} ms; restores the interrupt flag on {@link InterruptedException}. */
	private static void sleepQuietly(final long millis)
	{
		if (millis <= 0)
		{
			return;
		}
		try
		{
			Thread.sleep(millis);
		}
		catch (final InterruptedException ie)
		{
			Thread.currentThread().interrupt();
		}
	}
}
