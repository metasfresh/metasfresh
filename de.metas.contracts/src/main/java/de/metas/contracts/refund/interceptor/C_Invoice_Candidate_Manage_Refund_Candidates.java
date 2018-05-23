package de.metas.contracts.refund.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.getValueOverrideOrValue;

import java.sql.Timestamp;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import ch.qos.logback.classic.Level;
import de.metas.contracts.refund.AssignableInvoiceCandidate;
import de.metas.contracts.refund.InvoiceCandidate;
import de.metas.contracts.refund.InvoiceCandidateAssignmentService;
import de.metas.contracts.refund.InvoiceCandidateRepository;
import de.metas.contracts.refund.RefundInvoiceCandidate;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
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
public class C_Invoice_Candidate_Manage_Refund_Candidates
{

	private static final Logger logger = LogManager.getLogger(C_Invoice_Candidate_Manage_Refund_Candidates.class);

	private final InvoiceCandidateRepository invoiceCandidateRepository;
	private final InvoiceCandidateAssignmentService invoiceCandidateAssignmentService;

	private C_Invoice_Candidate_Manage_Refund_Candidates(
			@NonNull final InvoiceCandidateRepository invoiceCandidateRepository,
			@NonNull final InvoiceCandidateAssignmentService invoiceCandidateAssociationService)
	{
		this.invoiceCandidateRepository = invoiceCandidateRepository;
		this.invoiceCandidateAssignmentService = invoiceCandidateAssociationService;
	}

	@ModelChange(//
			timings = ModelValidator.TYPE_AFTER_CHANGE, //
			ifColumnsChanged = {
					I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice,
					I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice_Effective,
					I_C_Invoice_Candidate.COLUMNNAME_NetAmtToInvoice,
					I_C_Invoice_Candidate.COLUMNNAME_NetAmtInvoiced })
	public void associateDuringUpdateProcess(@NonNull final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		if (!Services.get(IInvoiceCandBL.class).isUpdateProcessInProgress())
		{
			return; // we one want one part to manage refund invoice candidate to avoid locking problems and other race conditions
		}

		final Timestamp invoicableFromDate = getValueOverrideOrValue(invoiceCandidateRecord, I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice);
		if (invoicableFromDate == null)
		{
			return; // it's not yet ready
		}
		if (invoiceCandidateRepository.isRefundInvoiceCandidateRecord(invoiceCandidateRecord))
		{
			return;
		}

		associateDuringUpdateProcess0(invoiceCandidateRecord);
	}

	private void associateDuringUpdateProcess0(final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		try
		{
			final AssignableInvoiceCandidate assignableInvoiceCandidate = invoiceCandidateRepository.ofRecord(invoiceCandidateRecord);
			final RefundInvoiceCandidate refundInvoiceCandidate = assignableInvoiceCandidate.getRefundInvoiceCandidate();

			if (refundInvoiceCandidate == null)
			{
				invoiceCandidateAssignmentService.createOrFindRefundCandidateAndAssignIfFeasible(assignableInvoiceCandidate);
				return;
			}

			invoiceCandidateRepository.save(refundInvoiceCandidate.withUpdatedMoneyAmout(assignableInvoiceCandidate));
		}
		catch (final RuntimeException e)
		{
			// allow the "normal ICs" to be updated, even if something is wrong with the "refund-ICs"
			Loggables
					.get()
					.withLogger(logger, Level.WARN)
					.addLog("associateDuringUpdateProcess0 - Caught an exception; please check the async workpackage log; e={}", e.toString());
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteAssignment(@NonNull final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		final InvoiceCandidate invoiceCandidate = invoiceCandidateRepository.ofRecord(invoiceCandidateRecord);
		if (invoiceCandidate instanceof RefundInvoiceCandidate)
		{
			final RefundInvoiceCandidate refundCandidate = RefundInvoiceCandidate.cast(invoiceCandidate);
			invoiceCandidateAssignmentService.removeAllAssignments(refundCandidate);
		}
		else
		{
			final AssignableInvoiceCandidate assignableCandidate = AssignableInvoiceCandidate.cast(invoiceCandidate);
			if (assignableCandidate.isAssigned())
			{
				invoiceCandidateAssignmentService.unassignCandidate(assignableCandidate);
			}
		}
	}
}
