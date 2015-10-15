package de.metas.invoicecandidate.modelvalidator;

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


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.inout.service.IInOutDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_InvoiceCandidate_InOutLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.ModelValidator;

import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;

@Interceptor(I_M_InOut.class)
public class M_InOut
{
	/**
	 * Invalidate existing invoice candidates and create missing ones for given shipment.
	 * <p>
	 * Note1: If it is a receipt, then we don't need to create the IC directly. Instead we leave it to the process which runs frequently.<br>
	 * Note2: Also "Leergutrücknahme" is a shipment in that sense (i.e. it has shipment.isSOTrx() == true), so we are fine to directly create the IC there as well
	 * 
	 * @param inout
	 * @task http://dewiki908/mediawiki/index.php/08491_Barverkauf_Timing_%28102342055782%29
	 * @task http://dewiki908/mediawiki/index.php/08641_Invalidate_invoice_candidates_when_an_reactivated_shipment_is_completed_again_%28100467724190%29
	 */
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void invalidateExistingAndCreateMissingInvoiceCandidates(final I_M_InOut inout)
	{
		//
		// Invalidate existing candidates which are directly or indirectly linked to this inout
		// NOTE: we are invalidating only for shipments because atm the receipts cannot be re-activated (and the actual need for this is for re-activated shipments which were completed again)
		if (inout.isSOTrx())
		{
			invalidateInvoiceCandidates(inout);
		}

		//
		// Create missing invoice candidates
		// NOTE: The invoice candidates will be created immediate, synchronously. The need of doing this so eager is described in 08491 task's concept.
		final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
		invoiceCandidateHandlerBL.createMissingCandidatesFor(inout);
	}

	/**
	 * @param doc
	 */
	// Moved here from {@link de.metas.inout.model.validator.M_InOut} 
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSECORRECT
			, ModelValidator.TIMING_AFTER_REVERSEACCRUAL
			, ModelValidator.TIMING_AFTER_REACTIVATE })
	public void invalidateInvoiceCandidatesOnReversal(final I_M_InOut doc)
	{
		final IInvoiceCandidateHandlerBL creatorBL = Services.get(IInvoiceCandidateHandlerBL.class);
		final IInOutDAO inoutDAO = Services.get(IInOutDAO.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(doc);
		final List<IInvoiceCandidateHandler> handlers = creatorBL.retrieveImplementationsForTable(ctx, I_M_InOutLine.Table_Name);
		for (final IInvoiceCandidateHandler handler : handlers)
		{
			for (final I_M_InOutLine line : inoutDAO.retrieveLines(doc))
			{
				handler.invalidateCandidatesFor(line);
			}
		}
	}
	
	/**
	 * Invalidate all invoice candidates which are directly or indirectly linked to given {@link I_M_InOut}.
	 * 
	 * @param inout
	 */
	private void invalidateInvoiceCandidates(final I_M_InOut inout)
	{
		// services
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		final List<I_C_Invoice_Candidate> invoiceCandidates = retrieveInvoiceCandidatesForInOut(inout);
		final String trxName = InterfaceWrapperHelper.getTrxName(inout);
		invoiceCandDAO.invalidateCands(invoiceCandidates, trxName);
	}

	/**
	 * Retrieve all {@link I_C_Invoice_Candidate}s which are directly or indirectly linked to {@link I_M_InOutLine}s.
	 * 
	 * @param inout
	 * @return invoice candidates
	 */
	private final List<I_C_Invoice_Candidate> retrieveInvoiceCandidatesForInOut(final I_M_InOut inout)
	{
		// services
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

		final Set<Integer> seenInvoiceCandidateIds = new HashSet<>();
		final List<I_C_Invoice_Candidate> invoiceCandidates = new ArrayList<>();

		//
		// Iterate each inout line
		for (final I_M_InOutLine inoutLine : inOutDAO.retrieveLines(inout))
		{
			//
			// Collect candidates which are indirectly linked with this inout line
			final List<I_C_InvoiceCandidate_InOutLine> iciols = invoiceCandDAO.retrieveICIOLAssociationsForInOutLineInclInactive(inoutLine);
			for (final I_C_InvoiceCandidate_InOutLine iciol : iciols)
			{
				final int invoiceCandidateId = iciol.getC_Invoice_Candidate_ID();

				// Skip if already added
				if (!seenInvoiceCandidateIds.add(invoiceCandidateId))
				{
					continue;
				}

				final I_C_Invoice_Candidate ic = iciol.getC_Invoice_Candidate();
				invoiceCandidates.add(ic);
			}

			//
			// Collect candidates which are directly created for this inout line
			final List<I_C_Invoice_Candidate> referencingICs = invoiceCandDAO.retrieveReferencing(inoutLine);
			for (final I_C_Invoice_Candidate ic : referencingICs)
			{
				final int invoiceCandidateId = ic.getC_Invoice_Candidate_ID();

				// Skip if already added
				if (!seenInvoiceCandidateIds.add(invoiceCandidateId))
				{
					continue;
				}
				invoiceCandidates.add(ic);
			}
		}

		return invoiceCandidates;
	}
}
