package de.metas.pos;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Pins the contract of {@link POSTerminalRepository#runWithBoundedAcquire}: the pure "poll a bounded number
 * of times to acquire, then run-and-return-verbatim or throw" algorithm behind
 * {@link POSTerminalRepository#runWithCrossTransactionLock}, tested here directly with fake
 * acquire/release suppliers — no real DB connection needed or possible in this plain-JUnit context (see the
 * class Javadoc on {@code runWithBoundedAcquire}).
 * <p>
 * The central property under test: a timeout can NEVER be confused with the action's own (possibly null)
 * result — the design this replaced (an {@code Optional<T>} return, {@code empty()} meaning either) could not
 * make that guarantee, and briefly regressed into an {@code Optional.of(null)} NPE for exactly this reason.
 */
class POSTerminalRepositoryTest
{
	@Test
	void actionReturningNull_isReturnedVerbatim_notThrowing()
	{
		final Object result = POSTerminalRepository.runWithBoundedAcquire(
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

		final String result = POSTerminalRepository.runWithBoundedAcquire(
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

		assertThatThrownBy(() -> POSTerminalRepository.runWithBoundedAcquire(
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

		assertThatThrownBy(() -> POSTerminalRepository.runWithBoundedAcquire(
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
	void acquiresOnASubsequentPoll_runsActionOnce()
	{
		final AtomicInteger acquireAttempts = new AtomicInteger();

		final String result = POSTerminalRepository.runWithBoundedAcquire(
				() -> acquireAttempts.incrementAndGet() >= 3, // fails twice, then succeeds
				() -> { },
				5_000L,
				5L,
				() -> "acquired",
				() -> new RuntimeException("onTimeout must not be invoked — acquisition eventually succeeds"));

		assertThat(result).isEqualTo("acquired");
		assertThat(acquireAttempts).hasValue(3);
	}
}
