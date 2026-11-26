package de.metas.contracts.refund;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateListener;
import de.metas.product.ProductId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.SpringContextHolder;
import org.compiere.util.TimeUtil;

import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * Lets the invoice candidate update run assign an invoice candidate to the refund terms that match it but that it is not assigned to yet,
 * e.g. because the term was completed after the invoice candidate was invoiced.
 * Like every other refund assignment, this is done by the update run only, never by the thread that completes the term.
 * <p>
 * Transaction contract: the update run ({@code InvoiceCandInvalidUpdater}) calls this listener right after it saved the candidate,
 * within the transaction of the current chunk of candidates; that chunk is committed even if single candidates fail, and without savepoints per candidate.
 * Therefore the assignment of one candidate to all its matching terms is done in a savepoint of that transaction:
 * if it fails (e.g. for the second of two terms), it is rolled back as a whole, so no half-assigned state is committed with the chunk,
 * and a database transaction that was aborted by an SQL error is usable again for the rest of the chunk.
 * The error is recorded as an {@code AD_Issue}; the candidate itself stays updated, and its refund assignment is retried the next time it is updated.
 */
public final class RefundInvoiceCandidateListener implements IInvoiceCandidateListener
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IErrorManager errorManager = Services.get(IErrorManager.class);

	// the beans are looked up when they are needed, because this listener is registered before the application context is complete
	private final SpringContextHolder.Lazy<RefundContractRepository> refundContractRepository = SpringContextHolder.lazyBean(RefundContractRepository.class);
	private final SpringContextHolder.Lazy<RefundInvoiceCandidateService> refundInvoiceCandidateService = SpringContextHolder.lazyBean(RefundInvoiceCandidateService.class);
	private final SpringContextHolder.Lazy<AssignableInvoiceCandidateRepository> assignableInvoiceCandidateRepository = SpringContextHolder.lazyBean(AssignableInvoiceCandidateRepository.class);
	private final SpringContextHolder.Lazy<CandidateAssignmentService> candidateAssignmentService = SpringContextHolder.lazyBean(CandidateAssignmentService.class);

	@Override
	public void onAfterUpdated(@NonNull final I_C_Invoice_Candidate candidate)
	{
		if (candidate.getC_Currency_ID() <= 0 || candidate.getM_Product_ID() <= 0)
		{
			return; // not yet computed, there is nothing to assign
		}
		final Timestamp dateToInvoice = CoalesceUtil.coalesce(candidate.getDateToInvoice_Override(), candidate.getDateToInvoice());
		if (dateToInvoice == null)
		{
			return;
		}

		try
		{
			if (!mayMatchARefundContract(candidate, TimeUtil.asLocalDate(dateToInvoice)))
			{
				return; // the usual case: no refund contracts, so no further work for the candidate
			}
			if (refundInvoiceCandidateService.get().isRefundInvoiceCandidateRecord(candidate))
			{
				return;
			}

			// all or nothing: on failure, the savepoint is rolled back and the exception rethrown
			trxManager.runInThreadInheritedTrx(() -> {
				final AssignableInvoiceCandidate assignableCandidate = assignableInvoiceCandidateRepository.get().ofRecord(candidate);
				candidateAssignmentService.get().assignToNewlyMatchingContracts(assignableCandidate);
			});
		}
		catch (final RuntimeException e)
		{
			// the invoice candidate itself is updated, even if something is wrong with its refund
			final AdIssueId issueId = errorManager.createIssue(e);
			Loggables.addLog("Caught an exception while assigning C_Invoice_Candidate_ID={} to refund terms; AD_Issue_ID={}; e={}", candidate.getC_Invoice_Candidate_ID(), issueId, e.toString());
		}
	}

	/** Cheap checks with cached queries, so that a candidate without any refund contract costs next to nothing. */
	private boolean mayMatchARefundContract(@NonNull final I_C_Invoice_Candidate candidate, @NonNull final LocalDate date)
	{
		final RefundContractRepository contractRepository = refundContractRepository.get();
		if (!contractRepository.hasAnyRefundContract(date))
		{
			return false;
		}

		final BPartnerId billBPartnerId = BPartnerId.ofRepoIdOrNull(candidate.getBill_BPartner_ID());
		if (billBPartnerId == null)
		{
			return false;
		}
		final RefundContractQuery query = new RefundContractQuery(
				billBPartnerId,
				ProductId.ofRepoId(candidate.getM_Product_ID()),
				date);
		return !contractRepository.getIdsByQuery(query).isEmpty();
	}
}
