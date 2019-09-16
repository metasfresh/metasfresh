package de.metas.contracts.refund.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.getValueOverrideOrValue;

import java.sql.Timestamp;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import ch.qos.logback.classic.Level;
import de.metas.contracts.refund.AssignableInvoiceCandidate;
import de.metas.contracts.refund.AssignableInvoiceCandidateRepository;
import de.metas.contracts.refund.CandidateAssignmentService;
import de.metas.contracts.refund.RefundInvoiceCandidate;
import de.metas.contracts.refund.RefundInvoiceCandidateRepository;
import de.metas.contracts.refund.RefundInvoiceCandidateService;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import de.metas.util.Services;
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

@Component
@Interceptor(I_C_Invoice_Candidate.class)
public class C_Invoice_Candidate_Manage_Refund_Candidates
{

	private static final Logger logger = LogManager.getLogger(C_Invoice_Candidate_Manage_Refund_Candidates.class);

	private final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository;
	private final CandidateAssignmentService invoiceCandidateAssignmentService;

	private RefundInvoiceCandidateService refundInvoiceCandidateService;

	private AssignableInvoiceCandidateRepository assignableInvoiceCandidateRepository;

	private C_Invoice_Candidate_Manage_Refund_Candidates(
			@NonNull final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository,
			@NonNull final AssignableInvoiceCandidateRepository assignableInvoiceCandidateRepository,
			@NonNull final RefundInvoiceCandidateService refundInvoiceCandidateService,
			@NonNull final CandidateAssignmentService invoiceCandidateAssociationService)
	{
		this.refundInvoiceCandidateRepository = refundInvoiceCandidateRepository;
		this.assignableInvoiceCandidateRepository = assignableInvoiceCandidateRepository;
		this.invoiceCandidateAssignmentService = invoiceCandidateAssociationService;
		this.refundInvoiceCandidateService = refundInvoiceCandidateService;
	}

	@ModelChange(//
			timings = ModelValidator.TYPE_AFTER_CHANGE, //
			ifColumnsChanged = {
					I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice,
					I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice_Override,
					I_C_Invoice_Candidate.COLUMNNAME_NetAmtToInvoice,
					I_C_Invoice_Candidate.COLUMNNAME_NetAmtInvoiced })
	public void associateWithRefundCandidate(@NonNull final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		if (!Services.get(IInvoiceCandBL.class).isUpdateProcessInProgress())
		{
			return; // only the update process shall manage refund invoice candidates, to avoid locking problems and other race conditions
		}

		final Timestamp invoicableFromDate = getValueOverrideOrValue(invoiceCandidateRecord, I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice);
		if (invoicableFromDate == null)
		{
			return; // it's not yet ready
		}
		if (refundInvoiceCandidateService.isRefundInvoiceCandidateRecord(invoiceCandidateRecord))
		{
			return; // it's already associated
		}
		associateDuringUpdateProcess0(invoiceCandidateRecord);
	}

	private void associateDuringUpdateProcess0(@NonNull final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		try
		{
			final AssignableInvoiceCandidate assignableInvoiceCandidate = assignableInvoiceCandidateRepository.ofRecord(invoiceCandidateRecord);
			invoiceCandidateAssignmentService.updateAssignment(assignableInvoiceCandidate);
		}
		catch (final RuntimeException e)
		{
			// allow the "normal ICs" to be updated, even if something is wrong with the "refund-ICs"
			final AdIssueId issueId = Services.get(IErrorManager.class).createIssue(e);
			Loggables.withLogger(logger, Level.WARN)
					.addLog("associateDuringUpdateProcess0 - Caught an exception withe processing C_Invoice_Candidate_ID={}; "
							+ "please check the async workpackage log; AD_Issue_ID={}; e={}",
							invoiceCandidateRecord.getC_Invoice_Candidate_ID(), issueId, e.toString());

		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteAssignment(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		if (refundInvoiceCandidateService.isRefundInvoiceCandidateRecord(icRecord))
		{
			final RefundInvoiceCandidate refundCandidate = refundInvoiceCandidateRepository.ofRecord(icRecord);
			invoiceCandidateAssignmentService.removeAllAssignments(refundCandidate);
		}
		else
		{
			final Timestamp invoicableFromDate = getValueOverrideOrValue(icRecord, I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice);
			if (invoicableFromDate == null)
			{
				return; // this IC was not yet once validated; it's certainly not assigned, and we can't create an AssignableInvoiceCandidate from it, because invoicableFromDate may not be null
			}

			final AssignableInvoiceCandidate assignableCandidate = assignableInvoiceCandidateRepository.ofRecord(icRecord);
			if (assignableCandidate.isAssigned())
			{
				invoiceCandidateAssignmentService.unassignCandidate(assignableCandidate);
			}
		}
	}
}
