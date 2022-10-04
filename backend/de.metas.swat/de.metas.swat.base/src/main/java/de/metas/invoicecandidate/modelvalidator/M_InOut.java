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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.document.exception.DocumentActionException;
import de.metas.i18n.AdMessageKey;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.approvedforinvoice.ApprovedForInvoicingService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOut;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import javax.naming.OperationNotSupportedException;

@Interceptor(I_M_InOut.class)
@Component
public class M_InOut
{
	private static final AdMessageKey OPERATION_NOT_SUPPORTED_APPROVED_FOR_INVOICE = AdMessageKey.of("Operation_Not_Supported_Approved_For_Invoice");

	private final ApprovedForInvoicingService approvedForInvoicingService;

	public M_InOut(@NonNull final ApprovedForInvoicingService approvedForInvoicingService)
	{
		this.approvedForInvoicingService = approvedForInvoicingService;
	}

	// Moved here from {@link de.metas.inout.model.validator.M_InOut}
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSECORRECT, //
			ModelValidator.TIMING_AFTER_REVERSEACCRUAL, //
			ModelValidator.TIMING_AFTER_REACTIVATE,
			ModelValidator.TIMING_AFTER_COMPLETE,// needed in case we complete an inout that was previously reactivated
			ModelValidator.TIMING_AFTER_CLOSE,
			ModelValidator.TIMING_AFTER_VOID,
			})

	public void invalidateInvoiceCandidatesOnReversal(final I_M_InOut inout)
	{
		final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
		invoiceCandidateHandlerBL.invalidateCandidatesFor(inout);
	}

	@DocValidate(timings = {
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_REACTIVATE,
			ModelValidator.TIMING_BEFORE_VOID,
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL })
	public void checkAnyAssociatedInvoiceCandidateClearedForInvoice(@NonNull final I_M_InOut inout) throws OperationNotSupportedException
	{
		final TableRecordReference recordReference = TableRecordReference.of(I_M_InOut.Table_Name, inout.getM_InOut_ID());

		if (approvedForInvoicingService.areAnyCandidatesApprovedForInvoice(recordReference))
		{
			throw new DocumentActionException(OPERATION_NOT_SUPPORTED_APPROVED_FOR_INVOICE);
		}
	}
}
