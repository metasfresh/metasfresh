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


import java.util.List;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.model.I_C_Invoice;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

/**
 * Abstract implementation of {@link IInvoiceCandidateListener} which does nothing.
 *
 * This class can be used as a template for implementing particular {@link IInvoiceCandidateListener}s.
 *
 * @author tsa
 *
 */
public abstract class InvoiceCandidateListenerAdapter implements IInvoiceCandidateListener
{

	/** does nothing */
	@Override
	public void onBeforeInvoiceLineCreated(I_C_InvoiceLine invoiceLine, IInvoiceLineRW fromInvoiceLine, List<I_C_Invoice_Candidate> fromCandidates)
	{
		// nothing
	}

	@Override
	public void onBeforeInvoiceComplete(I_C_Invoice invoice, List<I_C_Invoice_Candidate> fromCandidates)
	{
		// nothing
	}

	@Override
	public void onBeforeClosed(I_C_Invoice_Candidate candidate)
	{
		// nothing
	}
}
