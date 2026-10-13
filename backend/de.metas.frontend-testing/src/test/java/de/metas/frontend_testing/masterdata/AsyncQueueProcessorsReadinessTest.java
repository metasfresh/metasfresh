package de.metas.frontend_testing.masterdata;

import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AsyncQueueProcessorsReadinessTest
{
	private static final Duration POLL_INTERVAL = Duration.ofMillis(5);

	@Test
	public void alreadyStarted_returnsWithoutPolling()
	{
		final AtomicInteger checks = new AtomicInteger();

		AsyncQueueProcessorsReadiness.awaitStarted(() -> {
			checks.incrementAndGet();
			return true;
		}, Duration.ofSeconds(5), POLL_INTERVAL);

		assertThat(checks).hasValue(1);
	}

	@Test
	public void startedLater_waitsUntilStarted()
	{
		final AtomicInteger checks = new AtomicInteger();

		AsyncQueueProcessorsReadiness.awaitStarted(() -> checks.incrementAndGet() >= 4, Duration.ofSeconds(5), POLL_INTERVAL);

		assertThat(checks).hasValue(4);
	}

	@Test
	public void neverStarted_failsAfterTimeout_withExplicitMessage()
	{
		assertThatThrownBy(() -> AsyncQueueProcessorsReadiness.awaitStarted(() -> false, Duration.ofMillis(50), POLL_INTERVAL))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("async queue processors were not started");
	}

	@Test
	public void interruptedWhileWaiting_failsAndKeepsInterruptFlag()
	{
		Thread.currentThread().interrupt();
		try
		{
			assertThatThrownBy(() -> AsyncQueueProcessorsReadiness.awaitStarted(() -> false, Duration.ofSeconds(5), POLL_INTERVAL))
					.isInstanceOf(AdempiereException.class);
			assertThat(Thread.currentThread().isInterrupted()).isTrue();
		}
		finally
		{
			//noinspection ResultOfMethodCallIgnored
			Thread.interrupted(); // clear the flag for the following tests
		}
	}
}
