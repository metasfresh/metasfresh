package de.metas.contracts.refund.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.contracts.refund.AssignableInvoiceCandidate;
import de.metas.contracts.refund.InvoiceCandidate;
import de.metas.contracts.refund.InvoiceCandidateAssignmentService;
import de.metas.contracts.refund.InvoiceCandidateRepository;
import de.metas.contracts.refund.RefundInvoiceCandidate;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component("de.metas.contracts.refund.interceptor.C_Invoice_Candidate")
@Interceptor(I_C_Invoice_Candidate.class)
public class C_Invoice_Candidate
{
	private final InvoiceCandidateRepository invoiceCandidateRepository;
	private final InvoiceCandidateAssignmentService invoiceCandidateAssignmentService;

	private C_Invoice_Candidate(
			@NonNull final InvoiceCandidateRepository invoiceCandidateRepository,
			@NonNull final InvoiceCandidateAssignmentService invoiceCandidateAssociationService)
	{
		this.invoiceCandidateRepository = invoiceCandidateRepository;
		this.invoiceCandidateAssignmentService = invoiceCandidateAssociationService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void associateDuringUpdateProcess(@NonNull final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		if (!Services.get(IInvoiceCandBL.class).isUpdateProcessInProgress())
		{
			return;
		}

		final AssignableInvoiceCandidate assignableInvoiceCandidate = invoiceCandidateRepository.ofRecord(invoiceCandidateRecord);
		final RefundInvoiceCandidate refundInvoiceCandidate = assignableInvoiceCandidate.getRefundInvoiceCandidate();

		if (refundInvoiceCandidate == null)
		{
			invoiceCandidateAssignmentService.createOrFindRefundCandidateAndAssignIfFeasible(assignableInvoiceCandidate);
			return;
		}

		invoiceCandidateRepository.save(refundInvoiceCandidate.withUpdatedMoneyAmout(assignableInvoiceCandidate));
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteAssignment(@NonNull final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		final InvoiceCandidate invoiceCandidate = invoiceCandidateRepository.ofRecord(invoiceCandidateRecord);
		if (invoiceCandidate instanceof RefundInvoiceCandidate)
		{
			invoiceCandidateAssignmentService.removeAllAssignments(RefundInvoiceCandidate.cast(invoiceCandidate));
		}
		else
		{
			invoiceCandidateAssignmentService.unassignCandidate(AssignableInvoiceCandidate.cast(invoiceCandidate));
		}
	}
}
