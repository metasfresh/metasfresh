package de.metas.invoicecandidate.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.spi.IWorkpackagePrioStrategy;
import de.metas.i18n.AdMessageKey;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.process.params.InvoicingParams;
import de.metas.process.PInstanceId;
import lombok.NonNull;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Properties;
import java.util.Set;

/**
 * Helper interface to mass-enqueue {@link I_C_Invoice_Candidate}s to be invoiced.
 *
 * @author tsa
 *
 */
public interface IInvoiceCandidateEnqueuer
{
	String SYSCONFIG_FailOnChanges = "de.metas.invoicecandidate.api.impl.InvoiceCandidateEnqueuer.FailOnChanges";
	boolean DEFAULT_FailOnChanges = true;

	AdMessageKey MSG_INVOICE_GENERATE_NO_CANDIDATES_SELECTED_0P = AdMessageKey.of("InvoiceGenerate_No_Candidates_Selected");

	/**
	 * Prepare the selection while the ICs are not yet locked, because we want them to be updated by the regular
	 * {@link de.metas.invoicecandidate.async.spi.impl.UpdateInvalidInvoiceCandidatesWorkpackageProcessor}.
	 */
	void prepareSelection(@NonNull PInstanceId pInstanceId);

	/**
	 * Enqueue {@link I_C_Invoice_Candidate}s in given selection.
     */
	IInvoiceCandidateEnqueueResult enqueueSelection(final PInstanceId pinstanceId);

	default IInvoiceCandidateEnqueueResult prepareAndEnqueueSelection(@NonNull final PInstanceId pinstanceId)
	{
		prepareSelection(pinstanceId);
		return enqueueSelection(pinstanceId);
	}

	IInvoiceCandidateEnqueueResult enqueueInvoiceCandidateIds(Set<InvoiceCandidateId> invoiceCandidateIds);

	/**
	 * Context/transaction name to be used when enqueueing.
	 */
	IInvoiceCandidateEnqueuer setContext(final Properties ctx);

	/**
	 * @param failIfNothingEnqueued true if enqueueing shall fail if nothing was enqueued
	 */
	IInvoiceCandidateEnqueuer setFailIfNothingEnqueued(boolean failIfNothingEnqueued);

	/**
	 * Set to <code>true</code> if you want the enqueuer to make sure that the invoice candidates that will be enqueued shall not be changed.
	 * <p>
	 * By default, if you are not setting a particular value the {@link #SYSCONFIG_FailOnChanges} (default {@link #DEFAULT_FailOnChanges}) will be used.
	 */
	IInvoiceCandidateEnqueuer setFailOnChanges(boolean failOnChanges);

	/**
	 * Sets invoicing parameters to be used.
	 */
	IInvoiceCandidateEnqueuer setInvoicingParams(InvoicingParams invoicingParams);

	/**
	 * Sets the total net amount to invoice checksum.
	 * <p>
	 * If the amount is not null and "FailOnChanges" is set then this checksum will be enforced on enqueued invoice candidates.
	 */
	IInvoiceCandidateEnqueuer setTotalNetAmtToInvoiceChecksum(BigDecimal totalNetAmtToInvoiceChecksum);

	/**
	 * Sets the asyncBatch that will be used for grouping
	 */
	IInvoiceCandidateEnqueuer setC_Async_Batch(I_C_Async_Batch asyncBatch);

	/**
	 * Sets the priority to be used when processing the WPs
	 */
	IInvoiceCandidateEnqueuer setPriority(IWorkpackagePrioStrategy priority);
}
