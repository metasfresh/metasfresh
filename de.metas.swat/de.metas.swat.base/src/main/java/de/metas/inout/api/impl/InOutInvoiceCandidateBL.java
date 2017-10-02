package de.metas.inout.api.impl;

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

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_InOutLine;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.api.IInOutInvoiceCandidateBL;
import de.metas.inout.model.I_M_InOut;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

public class InOutInvoiceCandidateBL implements IInOutInvoiceCandidateBL
{

	@Override
	public boolean isApproveInOutForInvoicing(I_M_InOut inOut)
	{
		Check.assumeNotNull(inOut, "Inout shall not be null");

		boolean isAllowToInvoice = false;

		final IDocumentBL docActionBL = Services.get(IDocumentBL.class);

		if (inOut.isInOutApprovedForInvoicing())
		{
			isAllowToInvoice = true;
		}

		if (docActionBL.isStatusStrOneOf(inOut.getDocStatus(),
				IDocument.STATUS_Completed, IDocument.STATUS_Closed))
		{
			isAllowToInvoice = true;
		}

		if (!inOut.isActive())
		{
			isAllowToInvoice = false;
		}

		return isAllowToInvoice;
	}

	@Override
	public void approveForInvoicingLinkedInvoiceCandidates(final I_M_InOut inOut)
	{
		Check.assumeNotNull(inOut, "InOut not null");

		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		final boolean isApprovedForInvoicing = inOut.isInOutApprovedForInvoicing();

		if (!isApprovedForInvoicing)
		{
			// nothing to do
			return;
		}

		// get all the inout lines of the given inout
		final List<I_M_InOutLine> inoutLines = Services.get(IInOutDAO.class).retrieveLines(inOut);

		for (final I_M_InOutLine line : inoutLines)
		{
			// for each line, get all the linked invoice candidates
			final List<I_C_Invoice_Candidate> candidates = invoiceCandDAO.retrieveInvoiceCandidatesForInOutLine(line);

			for (final I_C_Invoice_Candidate candidate : candidates)
			{
				// because we are not allowed to set an invoice candidate as approved for invoicing if not all the valid inoutlines linked to it are approved for invoicing,
				// we have to get all the linked lines and check them one by one
				final List<I_M_InOutLine> inOutLinesForCandidate = invoiceCandDAO.retrieveInOutLinesForCandidate(candidate, I_M_InOutLine.class);

				boolean isAllowInvoicing = true;

				for (final I_M_InOutLine inOutLineForCandidate : inOutLinesForCandidate)
				{
					if(inOutLineForCandidate.getM_InOut_ID() == inOut.getM_InOut_ID())
					{
						// skip the current inout
						continue;
					}
					
					final I_M_InOut inoutForCandidate = InterfaceWrapperHelper.create(inOutLineForCandidate.getM_InOut(), I_M_InOut.class);
							
					// in case the inout entry is active, completed or closed but it doesn't have the flag isInOutApprovedForInvoicing set on true
					// we shall not approve the candidate for invoicing
					if (Services.get(IInOutInvoiceCandidateBL.class).isApproveInOutForInvoicing(inoutForCandidate) && !inoutForCandidate.isInOutApprovedForInvoicing())
					{
						isAllowInvoicing = false;
						break;
					}
				}

				candidate.setIsInOutApprovedForInvoicing(isAllowInvoicing);

				// also set the ApprovalForInvoicing flag with the calculated value
				// note that this flag is editable so it can be modifyied by the user anytime
				candidate.setApprovalForInvoicing(isAllowInvoicing);

				InterfaceWrapperHelper.save(candidate);
			}
		}
	}

}
