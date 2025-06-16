package de.metas.invoicecandidate.spi;

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

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.model.I_C_Invoice;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

import java.util.List;

/**
 * Invoice Candidates module listener.
 *
 * @author tsa
 *
 */
public interface IInvoiceCandidateListener
{
	/**
	 * Method called when invoice line was created from {@link IInvoiceLineRW} and {@link I_C_Invoice_Candidate}s.
	 * <p>
	 * The method will be called after invoice line is created but before it will be saved. The listener has the opportunity to change things there.
	 */
	default void onBeforeInvoiceLineCreated(final I_C_InvoiceLine invoiceLine, final IInvoiceLineRW fromInvoiceLine, final List<I_C_Invoice_Candidate> fromCandidates)
	{
		// nothing
	}

	/**
	 * Method called before an invoice is completed. It is needed for particular details to be set, no matter from what project they come from
	 */
	default void onBeforeInvoiceComplete(final I_C_Invoice invoice, final List<I_C_Invoice_Candidate> fromCandidates)
	{
		// nothing
	}

	/**
	 * Method called before an invoice candidate is closed
	 */
	default void onBeforeClosed(final I_C_Invoice_Candidate candidate)
	{
		// nothing
	}
}
