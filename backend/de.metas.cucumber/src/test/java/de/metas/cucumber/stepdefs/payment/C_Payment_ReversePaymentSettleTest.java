/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.payment;

import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.document.engine.DocStatus;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Guards the payment-reverse step's status check against a transient stale read of the
 * just-committed {@code DocStatus}.
 * <p>
 * A payment reversal commits {@code DocStatus=Reversed}, but a single immediate read of that status
 * can occasionally still observe {@code Completed}. Asserting from one read therefore fails
 * spuriously; a bounded refresh poll settles on the committed {@code Reversed}, while a payment that
 * never leaves {@code Completed} (a genuine reversal failure) still fails loud. These tests model
 * the status observation as {@link #statusReadSource(int)} and exercise the real
 * {@link StepDefUtil#tryAndWait(long, long, java.util.function.Supplier)} used by the step.
 */
class C_Payment_ReversePaymentSettleTest
{
	private static final String CO = DocStatus.Completed.getCode();
	private static final String RE = DocStatus.Reversed.getCode();

	/**
	 * A status source that returns {@link #CO} for the first {@code staleReads} reads (the transient
	 * stale reads of the just-committed status), then {@link #RE}. {@code staleReads = Integer.MAX_VALUE}
	 * models a payment that never reaches Reversed (a genuine reversal failure).
	 */
	private static Supplier<String> statusReadSource(final int staleReads)
	{
		final AtomicInteger reads = new AtomicInteger();
		return () -> reads.getAndIncrement() < staleReads ? CO : RE;
	}

	@Test
	void singleImmediateRead_throwsOnTransientStaleCompletedRead()
	{
		// A single immediate read of the just-committed status asserts Reversed but observes the
		// transient Completed => AssertionError ("expected RE, actual CO"), the spurious failure.
		final Supplier<String> statusSource = statusReadSource(1); // one transient stale read, then RE
		assertThatThrownBy(() ->
				assertThat(statusSource.get())
						.as("single read of just-committed DocStatus")
						.isEqualTo(RE))
				.isInstanceOf(AssertionError.class);
	}

	@Test
	void boundedPoll_settlesOnEventualReversedStatus() throws InterruptedException
	{
		// The bounded refresh poll tolerates the transient stale reads and settles on Reversed.
		final Supplier<String> statusSource = statusReadSource(3); // a few stale reads, then RE
		StepDefUtil.tryAndWait(10, 20, () -> RE.equals(statusSource.get()));
		// no exception => the poll observed the committed Reversed status
	}

	@Test
	void boundedPoll_failsLoudWhenStuckAtCompleted()
	{
		// Non-masking guarantee: a payment that never reaches Reversed (a real reversal failure)
		// still fails on timeout rather than being silently swallowed.
		final Supplier<String> statusSource = statusReadSource(Integer.MAX_VALUE); // always Completed
		assertThatThrownBy(() -> StepDefUtil.tryAndWait(1, 100, () -> RE.equals(statusSource.get())))
				.isInstanceOf(AssertionError.class);
	}
}
