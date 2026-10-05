package de.metas.contracts.refund;

import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateListener;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

/**
 * Lets the invoice candidate update run assign an invoice candidate to the refund terms that match it but that it is not assigned to yet,
 * e.g. because the term was completed after the invoice candidate was invoiced.
 * Like every other refund assignment, this is done by the update run only, never by the thread that completes the term.
 */
public final class RefundInvoiceCandidateListener implements IInvoiceCandidateListener
{
	public static final RefundInvoiceCandidateListener instance = new RefundInvoiceCandidateListener();

	private RefundInvoiceCandidateListener()
	{
	}

	@Override
	public void onAfterUpdated(@NonNull final I_C_Invoice_Candidate candidate)
	{
		final RefundInvoiceCandidateService refundInvoiceCandidateService = SpringContextHolder.instance.getBean(RefundInvoiceCandidateService.class);
		if (refundInvoiceCandidateService.isRefundInvoiceCandidateRecord(candidate))
		{
			return;
		}
		if (candidate.getC_Currency_ID() <= 0 || candidate.getDateToInvoice() == null)
		{
			return; // not yet computed, there is nothing to assign
		}

		try
		{
			final AssignableInvoiceCandidate assignableCandidate = SpringContextHolder.instance.getBean(AssignableInvoiceCandidateRepository.class).ofRecord(candidate);
			SpringContextHolder.instance.getBean(CandidateAssignmentService.class).assignToNewlyMatchingContracts(assignableCandidate);
		}
		catch (final RuntimeException e)
		{
			// the invoice candidate itself is updated, even if something is wrong with its refund
			final AdIssueId issueId = Services.get(IErrorManager.class).createIssue(e);
			Loggables.addLog("Caught an exception while assigning C_Invoice_Candidate_ID={} to refund terms; AD_Issue_ID={}; e={}", candidate.getC_Invoice_Candidate_ID(), issueId, e.toString());
		}
	}
}
