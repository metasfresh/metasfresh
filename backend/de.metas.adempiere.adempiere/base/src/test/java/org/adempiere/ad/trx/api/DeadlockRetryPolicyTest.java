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

import org.adempiere.exceptions.DBDeadLockDetectedException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.adempiere.ad.trx.api.DeadlockRetryPolicy.PG_SQLSTATE_DEADLOCK_DETECTED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DeadlockRetryPolicyTest
{
	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
	}

	// -----------------------------------------------------------------------
	// isDeadlock detection
	// -----------------------------------------------------------------------

	@Nested
	class IsDeadlock
	{
		@Test
		void directDBDeadLockDetectedException()
		{
			final DBDeadLockDetectedException ex = new DBDeadLockDetectedException(
					new SQLException("deadlock detected", PG_SQLSTATE_DEADLOCK_DETECTED), /*connection=*/null);
			assertThat(DeadlockRetryPolicy.isDeadlock(ex)).isTrue();
		}

		@Test
		void wrappedInsideRuntimeException()
		{
			final DBDeadLockDetectedException deadlock = new DBDeadLockDetectedException(
					new SQLException("deadlock detected", PG_SQLSTATE_DEADLOCK_DETECTED), null);
			final RuntimeException wrapper = new RuntimeException("wrapper", deadlock);
			assertThat(DeadlockRetryPolicy.isDeadlock(wrapper)).isTrue();
		}

		@Test
		void sqlExceptionWithDeadlockSQLState()
		{
			final SQLException sqlEx = new SQLException("deadlock detected", PG_SQLSTATE_DEADLOCK_DETECTED);
			assertThat(DeadlockRetryPolicy.isDeadlock(sqlEx)).isTrue();
		}

		@Test
		void nonDeadlockRuntimeException()
		{
			assertThat(DeadlockRetryPolicy.isDeadlock(new RuntimeException("some other error"))).isFalse();
		}

		@Test
		void nonDeadlockSQLException()
		{
			final SQLException sqlEx = new SQLException("unique violation", "23505");
			assertThat(DeadlockRetryPolicy.isDeadlock(sqlEx)).isFalse();
		}
	}

	// -----------------------------------------------------------------------
	// run() — retry logic
	// -----------------------------------------------------------------------

	/** Zero-sleep policy to keep tests fast. */
	private static DeadlockRetryPolicy fastPolicy(final int maxAttempts)
	{
		return DeadlockRetryPolicy.builder()
				.maxAttempts(maxAttempts)
				.backoffMillis(0L)
				.build();
	}

	private static DBDeadLockDetectedException deadlock()
	{
		return new DBDeadLockDetectedException(new SQLException("deadlock detected", PG_SQLSTATE_DEADLOCK_DETECTED), null);
	}

	@Nested
	class Run
	{
		@Test
		void successOnFirstAttempt()
		{
			final AtomicInteger callCount = new AtomicInteger();
			fastPolicy(3).run(callCount::incrementAndGet, "ctx");
			assertThat(callCount).hasValue(1);
		}

		@Test
		void retryOnDeadlock_succeedsOnSecondAttempt()
		{
			final AtomicInteger callCount = new AtomicInteger();
			fastPolicy(3).run(() -> {
				if (callCount.incrementAndGet() < 2)
				{
					throw deadlock();
				}
			}, "ctx");
			assertThat(callCount).hasValue(2);
		}

		@Test
		void retryOnDeadlock_exhaustsAllAttempts_thenRethrows()
		{
			final AtomicInteger callCount = new AtomicInteger();
			final DBDeadLockDetectedException ex = deadlock();

			assertThatThrownBy(() -> fastPolicy(3).run(() -> {
				callCount.incrementAndGet();
				throw ex;
			}, "ctx"))
					.isSameAs(ex);

			assertThat(callCount).hasValue(3);
		}

		@Test
		void nonDeadlockException_notRetried_propagatesImmediately()
		{
			final AtomicInteger callCount = new AtomicInteger();
			final RuntimeException nonDeadlock = new RuntimeException("unique violation");

			assertThatThrownBy(() -> fastPolicy(3).run(() -> {
				callCount.incrementAndGet();
				throw nonDeadlock;
			}, "ctx"))
					.isSameAs(nonDeadlock);

			// must not retry on a non-deadlock exception
			assertThat(callCount).hasValue(1);
		}

		@Test
		void maxAttempts_one_noRetry()
		{
			final AtomicInteger callCount = new AtomicInteger();

			assertThatThrownBy(() -> fastPolicy(1).run(() -> {
				callCount.incrementAndGet();
				throw deadlock();
			}, "ctx"))
					.isInstanceOf(DBDeadLockDetectedException.class);

			assertThat(callCount).hasValue(1);
		}

		@Test
		void defaultConstants_matchExpectedValues()
		{
			assertThat(DeadlockRetryPolicy.DEFAULT_MAX_ATTEMPTS).isEqualTo(3);
			assertThat(DeadlockRetryPolicy.DEFAULT_BACKOFF_MILLIS).isEqualTo(5_000L);
		}
	}

	// -----------------------------------------------------------------------
	// call() — typed return-value overload
	// -----------------------------------------------------------------------

	@Nested
	class Call
	{
		@Test
		void returnsValueOnSuccess()
		{
			final String result = fastPolicy(3).call(() -> "hello", "ctx");
			assertThat(result).isEqualTo("hello");
		}

		@Test
		void nullContext_retriesWithoutNPE()
		{
			final AtomicInteger callCount = new AtomicInteger();
			final String result = fastPolicy(3).call(() -> {
				if (callCount.incrementAndGet() < 2)
				{
					throw deadlock();
				}
				return "ok";
			}, (Object[])null);
			assertThat(result).isEqualTo("ok");
			assertThat(callCount).hasValue(2);
		}

		@Test
		void returnsValueAfterOneDeadlockRetry()
		{
			final AtomicInteger callCount = new AtomicInteger();
			final String result = fastPolicy(3).call(() -> {
				if (callCount.incrementAndGet() < 2)
				{
					throw deadlock();
				}
				return "ok";
			}, "ctx");
			assertThat(result).isEqualTo("ok");
			assertThat(callCount).hasValue(2);
		}

		@Test
		void exhaustsAllAttempts_thenRethrows()
		{
			final AtomicInteger callCount = new AtomicInteger();
			final DBDeadLockDetectedException ex = deadlock();

			assertThatThrownBy(() -> fastPolicy(3).call(() -> {
				callCount.incrementAndGet();
				throw ex;
			}, "ctx"))
					.isSameAs(ex);

			assertThat(callCount).hasValue(3);
		}

		@Test
		void nonDeadlock_propagatesImmediately()
		{
			final RuntimeException nonDeadlock = new RuntimeException("other error");
			assertThatThrownBy(() -> fastPolicy(3).call(() -> {
				throw nonDeadlock;
			}, "ctx"))
					.isSameAs(nonDeadlock);
		}
	}

}
