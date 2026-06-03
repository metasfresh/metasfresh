package org.adempiere.ad.trx.api.impl;

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
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.DBDeadLockDetectedException;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.sql.SQLException;
import java.util.function.Supplier;

/**
 * Retry policy that detects DB deadlocks and retries the given action up to {@code maxAttempts} times
 * before re-throwing the last exception. A fixed backoff sleep separates consecutive attempts.
 *
 * <p>Deadlock detection walks the cause chain looking for a {@link DBDeadLockDetectedException}
 * or an {@link SQLException} with PostgreSQL SQLSTATE {@code 40P01}.</p>
 */
public class DeadlockRetryPolicy
{
	private static final Logger logger = LogManager.getLogger(DeadlockRetryPolicy.class);

	/** PostgreSQL SQLSTATE for "deadlock detected". */
	private static final String PG_SQLSTATE_DEADLOCK_DETECTED = "40P01";

	/** Maximum number of attempts (first attempt + retries). */
	@VisibleForTesting
	public static final int DEFAULT_MAX_ATTEMPTS = 3;

	/** Milliseconds to sleep between attempts, mirroring WorkpackageProcessorTask's retry timeout. */
	@VisibleForTesting
	public static final long DEFAULT_BACKOFF_MILLIS = 5_000L;

	private final int maxAttempts;
	private final long backoffMillis;

	@Builder
	private DeadlockRetryPolicy(final int maxAttempts, final long backoffMillis)
	{
		this.maxAttempts = maxAttempts > 0 ? maxAttempts : DEFAULT_MAX_ATTEMPTS;
		this.backoffMillis = backoffMillis >= 0 ? backoffMillis : DEFAULT_BACKOFF_MILLIS; // 0 = no sleep (tests)
	}

	/** Creates the default policy (3 attempts, 5 s backoff). */
	public static DeadlockRetryPolicy defaults()
	{
		return DeadlockRetryPolicy.builder()
				.maxAttempts(DEFAULT_MAX_ATTEMPTS)
				.backoffMillis(DEFAULT_BACKOFF_MILLIS)
				.build();
	}

	/**
	 * Runs {@code action}; on {@link #isDeadlock(Throwable)} retries up to {@code maxAttempts - 1}
	 * times after sleeping {@code backoffMillis}. After exhausting retries the last exception is
	 * re-thrown as-is (preserving the original type and stack).
	 *
	 * @param action  the idempotent operation to execute
	 * @param context an optional description used in log messages (e.g. the posting request)
	 */
	public void run(@NonNull final Runnable action, @Nullable final Object context)
	{
		call(() -> {
			action.run();
			return null;
		}, context);
	}

	/**
	 * Calls {@code action}; on {@link #isDeadlock(Throwable)} retries up to {@code maxAttempts - 1}
	 * times after sleeping {@code backoffMillis}. After exhausting retries the last exception is
	 * re-thrown as-is (preserving the original type and stack).
	 *
	 * @param <T>     return type of the supplier
	 * @param action  the idempotent operation to execute
	 * @param context an optional description used in log messages (e.g. the posting request);
	 *                may be null (unit-test documents have no document info) — log-only, never dereferenced
	 * @return the value returned by {@code action} on a successful attempt
	 */
	public <T> T call(@NonNull final Supplier<T> action, @Nullable final Object context)
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
						"Deadlock detected on attempt {}/{} for {}; will retry after {} ms. Deadlock: {}",
						attempt, maxAttempts, context, backoffMillis, ex.getMessage());

				if (attempt < maxAttempts)
				{
					sleepQuietly(backoffMillis);
				}
			}
		}

		// All attempts exhausted — rethrow so the error path records it
		throw lastException;
	}

	/**
	 * Returns {@code true} when the given throwable (or any of its causes) represents a
	 * DB deadlock: a {@link DBDeadLockDetectedException} or an {@link SQLException} with
	 * PostgreSQL SQLSTATE {@code 40P01} anywhere in the cause chain.
	 */
	@VisibleForTesting
	public static boolean isDeadlock(@Nullable final Throwable ex)
	{
		// Deliberately self-contained (no DB.isPostgreSQL() dependency): that check requires a configured
		// DB connection and is false in unit-test mode, where DBException.isDeadLockDetected would
		// silently never match.
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

	/** Sleeps for {@code millis} ms; swallows {@link InterruptedException} and restores the flag. */
	private static void sleepQuietly(final long millis)
	{
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
