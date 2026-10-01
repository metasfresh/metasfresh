package de.metas.pos;

import de.metas.common.util.time.SystemTime;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

/**
 * Pins the contract of {@link POSTerminalService#runWithBoundedAcquire}: the pure "poll a bounded number
 * of times to acquire, then run-and-return-verbatim or throw" algorithm behind
 * {@link POSTerminalService#runWithCrossTransactionLock}, tested here directly with fake
 * acquire/release suppliers — no real DB connection needed or possible in this plain-JUnit context (the DB
 * primitives + connection lifecycle live in {@link POSTerminalRepository}; only the timing algorithm lives in
 * the service, so only it is unit-tested here).
 * <p>
 * The central property under test: a timeout can NEVER be confused with the action's own (possibly null)
 * result — the design this replaced (an {@code Optional<T>} return, {@code empty()} meaning either) could not
 * make that distinction.
 */
class POSTerminalServiceTest
{
	@Test
	void actionReturningNull_isReturnedVerbatim_notThrowing()
	{
		final Object result = POSTerminalService.runWithBoundedAcquire(
				() -> true, // acquires immediately
				() -> { },  // release: no-op
				1_000L,
				10L,
				() -> null, // the action legitimately returns null (e.g. a Void/Runnable-shaped action)
				() -> new RuntimeException("onTimeout must not be invoked — the lock was acquired immediately"));

		assertThat(result).isNull();
	}

	@Test
	void actionSucceeds_releaseRuns()
	{
		final AtomicBoolean released = new AtomicBoolean(false);

		final String result = POSTerminalService.runWithBoundedAcquire(
				() -> true,
				() -> released.set(true),
				1_000L,
				10L,
				() -> "acquired",
				() -> new RuntimeException("onTimeout must not be invoked — the lock was acquired immediately"));

		assertThat(result).isEqualTo("acquired");
		assertThat(released).as("release must run on the plain success path, same as on the exception path").isTrue();
	}

	@Test
	void neverAcquires_throwsTheSuppliedTimeoutException_releaseNeverCalled()
	{
		final RuntimeException timeoutException = new RuntimeException("timed out");
		final AtomicBoolean released = new AtomicBoolean(false);

		assertThatThrownBy(() -> POSTerminalService.runWithBoundedAcquire(
				() -> false, // never acquires
				() -> released.set(true),
				50L,
				10L,
				() -> "action must never run if the lock is never acquired",
				() -> timeoutException))
				.isSameAs(timeoutException);

		assertThat(released).as("release must not run — the lock was never acquired").isFalse();
	}

	@Test
	void actionThrows_releaseStillRuns()
	{
		final AtomicBoolean released = new AtomicBoolean(false);
		final RuntimeException actionException = new RuntimeException("boom");

		assertThatThrownBy(() -> POSTerminalService.runWithBoundedAcquire(
				() -> true,
				() -> released.set(true),
				1_000L,
				10L,
				() -> { throw actionException; },
				() -> new RuntimeException("onTimeout must not be invoked — the lock was acquired")))
				.isSameAs(actionException);

		assertThat(released).as("release must still run when the action throws").isTrue();
	}

	@Test
	void actionThrowsAndReleaseThrows_releaseFailureIsSuppressed_notMaskingTheBusinessError()
	{
		// The cashier must see the business error (the action's), not a lock-release plumbing failure. A failing
		// release is attached as a suppressed throwable, never allowed to replace the in-flight error.
		final RuntimeException actionException = new RuntimeException("business error the cashier needs to see");
		final RuntimeException releaseException = new RuntimeException("pg_advisory_unlock failed");

		assertThatThrownBy(() -> POSTerminalService.runWithBoundedAcquire(
				() -> true,
				() -> { throw releaseException; },
				1_000L,
				10L,
				() -> { throw actionException; },
				() -> new RuntimeException("onTimeout must not be invoked — the lock was acquired")))
				.isSameAs(actionException)
				.satisfies(thrown -> assertThat(thrown.getSuppressed()).containsExactly(releaseException));
	}

	@Test
	void actionSucceedsButReleaseThrows_releaseFailurePropagates()
	{
		// No business error is in flight, so a failing release must NOT be swallowed — otherwise the advisory lock
		// would leak on the pooled connection and wedge the terminal as permanently "till busy".
		final RuntimeException releaseException = new RuntimeException("pg_advisory_unlock failed");

		assertThatThrownBy(() -> POSTerminalService.runWithBoundedAcquire(
				() -> true,
				() -> { throw releaseException; },
				1_000L,
				10L,
				() -> "acquired",
				() -> new RuntimeException("onTimeout must not be invoked — the lock was acquired")))
				.isSameAs(releaseException);
	}

	@Test
	void acquiresOnASubsequentPoll_runsActionOnce()
	{
		final AtomicInteger acquireAttempts = new AtomicInteger();

		final String result = POSTerminalService.runWithBoundedAcquire(
				() -> acquireAttempts.incrementAndGet() >= 3, // fails twice, then succeeds
				() -> { },
				5_000L,
				5L,
				() -> "acquired",
				() -> new RuntimeException("onTimeout must not be invoked — acquisition eventually succeeds"));

		assertThat(result).isEqualTo("acquired");
		assertThat(acquireAttempts).hasValue(3);
	}

	@Test
	void timesOut_evenWhenSystemTimeIsFrozen()
	{
		// A cucumber scenario freezes SystemTime ("metasfresh has date and time ..."), so the lock-acquire
		// timeout must measure REAL elapsed time (a monotonic clock), NOT the business clock (SystemTime):
		// with a frozen SystemTime the deadline is never reached and the loop never times out, so the
		// till-busy guard never fires. Regression guard for cucumber S28210_TC20 (POS return till-busy),
		// which failed when the timeout was computed from SystemTime.millis(). The assertTimeoutPreemptively
		// turns a reverted (frozen-clock-dependent) implementation's infinite poll loop into a clean failure
		// instead of a hang.
		final RuntimeException timeoutException = new RuntimeException("timed out");
		SystemTime.setFixedTimeSource(ZonedDateTime.parse("2026-09-24T08:00:00+02:00[Europe/Berlin]"));
		try
		{
			assertTimeoutPreemptively(Duration.ofSeconds(5), () ->
					assertThatThrownBy(() -> POSTerminalService.runWithBoundedAcquire(
							() -> false, // never acquires
							() -> { },
							50L,
							10L,
							() -> "action must never run if the lock is never acquired",
							() -> timeoutException))
							.isSameAs(timeoutException));
		}
		finally
		{
			SystemTime.resetTimeSource();
		}
	}
}
