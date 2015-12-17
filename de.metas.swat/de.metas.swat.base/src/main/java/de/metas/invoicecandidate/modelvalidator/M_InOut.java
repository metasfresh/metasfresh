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


import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.util.Services;
import org.compiere.model.I_M_InOut;
import org.compiere.model.ModelValidator;

import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;

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
		// NOTE: we are invalidating for shipments and receipts because atm both of them can be reactivated 
		// (and the actual need for this is for re-activated shipments which were completed again)
		final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
		invoiceCandidateHandlerBL.invalidateCandidatesFor(inout);

		//
		// Create missing invoice candidates
		// NOTE: The invoice candidates will be created immediate, synchronously. The need of doing this so eager is described in 08491 task's concept.
		invoiceCandidateHandlerBL.createMissingCandidatesFor(inout);
	}

	/**
	 * @param doc
	 */
	// Moved here from {@link de.metas.inout.model.validator.M_InOut} 
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSECORRECT
			, ModelValidator.TIMING_AFTER_REVERSEACCRUAL
			, ModelValidator.TIMING_AFTER_REACTIVATE })
	public void invalidateInvoiceCandidatesOnReversal(final I_M_InOut inout)
	{
		final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
		invoiceCandidateHandlerBL.invalidateCandidatesFor(inout);
	}
}
