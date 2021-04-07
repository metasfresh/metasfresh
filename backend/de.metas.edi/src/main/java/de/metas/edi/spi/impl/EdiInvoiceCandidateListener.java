package de.metas.edi.spi.impl;

/*
 * #%L
 * de.metas.edi
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

import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.invoicecandidate.model.I_C_Invoice;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateListener;

public class EdiInvoiceCandidateListener implements IInvoiceCandidateListener
{
	public static final EdiInvoiceCandidateListener instance = new EdiInvoiceCandidateListener();

	private EdiInvoiceCandidateListener()
	{
	}


	@Override
	public void onBeforeInvoiceComplete(final I_C_Invoice invoice, final List<I_C_Invoice_Candidate> fromCandidates)
	{

		final boolean isEdiEnabled = calculateEdiEnabled(fromCandidates);

		final de.metas.edi.model.I_C_Invoice ediInvoice = InterfaceWrapperHelper.create(invoice, de.metas.edi.model.I_C_Invoice.class);

		// make sure the EdiEnabled flag is set based on the invoice candidates of the invoice to be completed
		ediInvoice.setIsEdiEnabled(isEdiEnabled);

	}

	private boolean calculateEdiEnabled(@NonNull final List<I_C_Invoice_Candidate> fromCandidates)
	{
		if (fromCandidates.isEmpty())
		{
			return false;
		}
		final boolean isEdiEnabled = InterfaceWrapperHelper.create(fromCandidates.get(0), de.metas.edi.model.I_C_Invoice_Candidate.class).isEdiEnabled();

		for (int i = 0; i < fromCandidates.size(); i++)
		{
			final de.metas.edi.model.I_C_Invoice_Candidate candidate = InterfaceWrapperHelper.create(fromCandidates.get(i), de.metas.edi.model.I_C_Invoice_Candidate.class);

			if (isEdiEnabled != candidate.isEdiEnabled())
			{
				throw new AdempiereException("IsEdiEnabled not consistent in candidates: " + fromCandidates);
			}
		}
		return isEdiEnabled;
	}
}
