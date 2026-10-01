package de.metas.invoicecandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Properties;
import java.util.stream.Collectors;

import org.adempiere.util.lang.ObjectUtils;

import de.metas.i18n.IMsgBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueueResult;
import de.metas.lock.api.ILock;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * @author al
 */
/* package */final class InvoiceCandidateEnqueueResult implements IInvoiceCandidateEnqueueResult
{
	private static final String MSG_INVOICE_CANDIDATE_ENQUEUE = "InvoiceCandidateEnqueue";
	private static final String MSG_INVOICE_CANDIDATE_ENQUEUE_SKIPPED = "InvoiceCandidateEnqueue_Skipped";
	/** A selection can hold thousands of candidates; the process log has every reason. */
	private static final int MAX_LISTED_SKIP_REASONS = 5;

	private final int invoiceCandidateEnqueuedCount;
	private final int workpackageEnqueuedCount;
	private final int workpackageQueueSizeBeforeEnqueueing;
	private final BigDecimal totalNetAmtToInvoiceChecksum;
	private final ImmutableList<String> skipReasons;
	private final ILock lock;

	/* package */ InvoiceCandidateEnqueueResult(final int invoiceCandidateEnqueuedCount,
			final int enqueuedWorkpackageCount,
			final int workpackageQueueSizeBeforeEnqueueing,
			final BigDecimal totalNetAmtToInvoiceChecksum,
			@NonNull final ImmutableList<String> skipReasons,
			final ILock lock)
	{
		Check.assume(invoiceCandidateEnqueuedCount >= 0, "invoiceCandidateEnqueuedCount > 0");
		this.invoiceCandidateEnqueuedCount = invoiceCandidateEnqueuedCount;

		Check.assume(enqueuedWorkpackageCount >= 0, "Expected positive amount of enqueuedWorkpackageCount, but got {}", enqueuedWorkpackageCount);
		this.workpackageEnqueuedCount = enqueuedWorkpackageCount;

		this.workpackageQueueSizeBeforeEnqueueing = workpackageQueueSizeBeforeEnqueueing;

		this.totalNetAmtToInvoiceChecksum = totalNetAmtToInvoiceChecksum;

		this.skipReasons = skipReasons;

		Check.assumeNotNull(lock, "lock not null");
		this.lock = lock;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public String getSummaryTranslated(final Properties ctx)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final int countWorkpackages = getWorkpackageEnqueuedCount();
		final int countUnprocessedWorkPackages = getWorkpackageQueueSizeBeforeEnqueueing();
		final String enqueuedSummary = msgBL.getMsg(ctx, MSG_INVOICE_CANDIDATE_ENQUEUE, new Object[] { countWorkpackages, countUnprocessedWorkPackages });

		final int skippedCount = getInvoiceCandidateSkippedCount();
		if (skippedCount <= 0)
		{
			return enqueuedSummary;
		}

		final int selectedCount = getInvoiceCandidateEnqueuedCount() + skippedCount;

		return enqueuedSummary + " " + buildSkippedSummary(ctx, skipReasons, selectedCount);
	}

	/**
	 * "N of M selected invoice candidate(s) were not invoiced: ...".
	 * <p>
	 * Static because {@link InvoiceCandidateEnqueuer} needs the same sentence on the all-skipped path,
	 * where it throws instead of building a result.
	 */
	/* package */
	static String buildSkippedSummary(
			@NonNull final Properties ctx,
			@NonNull final ImmutableList<String> skipReasons,
			final int selectedCount)
	{
		return Services.get(IMsgBL.class).getMsg(ctx, MSG_INVOICE_CANDIDATE_ENQUEUE_SKIPPED,
				new Object[] { skipReasons.size(), selectedCount, buildSkipReasonsText(skipReasons) });
	}

	private static String buildSkipReasonsText(@NonNull final ImmutableList<String> skipReasons)
	{
		final String listed = skipReasons.stream()
				.limit(MAX_LISTED_SKIP_REASONS)
				.collect(Collectors.joining("; "));

		final int notListed = skipReasons.size() - Math.min(skipReasons.size(), MAX_LISTED_SKIP_REASONS);
		return notListed <= 0
				? listed
				: listed + "; ... (+" + notListed + ")";
	}

	@Override
	public int getInvoiceCandidateEnqueuedCount()
	{
		return invoiceCandidateEnqueuedCount;
	}

	@Override
	public int getInvoiceCandidateSkippedCount()
	{
		return skipReasons.size();
	}

	@Override
	public ImmutableList<String> getSkipReasons()
	{
		return skipReasons;
	}

	@Override
	public int getWorkpackageEnqueuedCount()
	{
		return workpackageEnqueuedCount;
	}

	@Override
	public int getWorkpackageQueueSizeBeforeEnqueueing()
	{
		return workpackageQueueSizeBeforeEnqueueing;
	}

	@Override
	public BigDecimal getTotalNetAmtToInvoiceChecksum()
	{
		return totalNetAmtToInvoiceChecksum;
	}

	@Override
	public ILock getLock()
	{
		return lock;
	}
}
