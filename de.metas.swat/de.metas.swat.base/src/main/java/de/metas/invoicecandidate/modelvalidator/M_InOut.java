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
