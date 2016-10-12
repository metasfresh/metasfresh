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


import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.util.ILoggable;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

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

	String MSG_INVOICE_GENERATE_NO_CANDIDATES_SELECTED_0P = "InvoiceGenerate_No_Candidates_Selected";

	/**
	 * Enqueue {@link I_C_Invoice_Candidate}s in given selection.
	 * 
	 * @param adPInstanceId
	 * @return enqueueing result
	 */
	IInvoiceCandidateEnqueueResult enqueueSelection(final int adPInstanceId);

	/**
	 * Context/transaction name to be used when enqueueing.
	 * 
	 * @param ctx
	 * @param trxName
	 */
	IInvoiceCandidateEnqueuer setContext(final Properties ctx, final String trxName);

	/**
	 * Sets logger to be used to log warnings.
	 */
	IInvoiceCandidateEnqueuer setLoggable(final ILoggable loggable);

	/**
	 * @param failIfNothingEnqueued true if enqueueing shall fail if nothing was enqueued
	 */
	IInvoiceCandidateEnqueuer setFailIfNothingEnqueued(boolean failIfNothingEnqueued);

	/**
	 * Set to <code>true</code> if you want the enqueuer to make sure that the invoice candidates that will be enqueued shall not be changed.
	 * 
	 * By default, if you are not setting a particular value the {@link #SYSCONFIG_FailOnChanges} (default {@link #DEFAULT_FailOnChanges}) will be used.
	 * 
	 * @param checkNoChanges
	 */
	IInvoiceCandidateEnqueuer setFailOnChanges(boolean failOnChanges);

	/**
	 * Sets invoicing parameters to be used.
	 * 
	 * @param invoicingParams
	 */
	IInvoiceCandidateEnqueuer setInvoicingParams(IInvoicingParams invoicingParams);

	/**
	 * Sets the total net amount to invoice checksum.
	 * 
	 * If the amount is not null and "FailOnChanges" is set then this checksum will be enforced on enqueued invoice candidates.
	 * 
	 * @param totalNetAmtToInvoiceChecksum
	 */
	IInvoiceCandidateEnqueuer setTotalNetAmtToInvoiceChecksum(BigDecimal totalNetAmtToInvoiceChecksum);
}
