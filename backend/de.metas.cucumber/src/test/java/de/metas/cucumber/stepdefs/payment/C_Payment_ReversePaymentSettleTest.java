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
 * Deterministic seam reproduction for flaky registry case 17
 * ({@code invoicePaymentAllocation.feature:1569}).
 * <p>
 * Symptom (observed in CI, run 29507058082 / job 87657009594, profile5): the step
 * {@code C_Payment_StepDef.reversePayment} calls
 * {@code documentBL.processEx(payment, ACTION_Reverse_Correct, STATUS_Reversed)}, whose internal
 * status check reads the payment's {@code DocStatus} exactly once (a single immediate refresh) and
 * throws {@code DocumentProcessingException: Document does not have expected status (Expected=RE,
 * actual=CO)}. The reversal doc-action itself succeeds and commits {@code DocStatus=Reversed}; the
 * just-committed status is only intermittently read back as {@code Completed} on that first read.
 * <p>
 * The stack is configured synchronously in this feature ({@code SKIP_WP_PROCESSOR_FOR_AUTOMATION=true},
 * "documents are accounted immediately"), so the fix does NOT add an async settle for a queue: it
 * tolerates a transient stale read of the just-committed status via a bounded refresh poll, while
 * still failing loud if the payment is genuinely stuck at {@code Completed} (a real reversal failure).
 * <p>
 * This test models the post-reverse status observation as {@link StatusReadSource}: it returns
 * {@code Completed} for the first {@code staleReads} reads (mirroring the transient stale refresh),
 * then {@code Reversed}. It exercises the real {@link StepDefUtil#tryAndWait} that the fix uses.
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
	void preFix_singleImmediateRead_throwsWhenJustCommittedStatusIsReadStale()
	{
		// Reproduces the pre-fix behaviour: processEx asserts the target status from ONE immediate read.
		final Supplier<String> statusSource = statusReadSource(1); // one transient stale read, then RE
		assertThatThrownBy(() ->
				assertThat(statusSource.get()) // the single immediate read the pre-fix code relied on
						.as("pre-fix single read of just-committed DocStatus")
						.isEqualTo(RE))
				.isInstanceOf(AssertionError.class); // == the CI "Expected=RE, actual=CO" failure
	}

	@Test
	void fix_boundedPoll_settlesToReversed() throws InterruptedException
	{
		// The fix: bounded refresh poll on the just-committed status. Tolerates the transient stale read.
		final Supplier<String> statusSource = statusReadSource(3); // a few stale reads, then RE
		StepDefUtil.tryAndWait(10, 20, () -> RE.equals(statusSource.get()));
		// no exception => the poll observed the committed Reversed status
	}

	@Test
	void fix_boundedPoll_stillFailsLoudWhenPaymentIsGenuinelyStuckCompleted()
	{
		// Non-masking guarantee: a payment that never reaches Reversed (a real reversal failure)
		// still fails the step on timeout rather than being silently swallowed.
		final Supplier<String> statusSource = statusReadSource(Integer.MAX_VALUE); // always Completed
		assertThatThrownBy(() -> StepDefUtil.tryAndWait(1, 100, () -> RE.equals(statusSource.get())))
				.isInstanceOf(AssertionError.class);
	}
}
