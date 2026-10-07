package de.metas.contracts.refund;

import com.google.common.annotations.VisibleForTesting;
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
import org.compiere.SpringContextHolder;
import org.compiere.util.TimeUtil;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.function.Supplier;

/**
 * Lets the invoice candidate update run assign an invoice candidate to the refund terms that match it but that it is not assigned to yet,
 * e.g. because the term was completed after the invoice candidate was invoiced.
 * Like every other refund assignment, this is done by the update run only, never by the thread that completes the term.
 */
public final class RefundInvoiceCandidateListener implements IInvoiceCandidateListener
{
	public static final RefundInvoiceCandidateListener instance = new RefundInvoiceCandidateListener(
			() -> SpringContextHolder.instance.getBean(RefundContractRepository.class),
			() -> SpringContextHolder.instance.getBean(RefundInvoiceCandidateService.class),
			() -> SpringContextHolder.instance.getBean(AssignableInvoiceCandidateRepository.class),
			() -> SpringContextHolder.instance.getBean(CandidateAssignmentService.class));

	// the beans are looked up when they are needed, because this listener is registered before the application context is complete
	private final Supplier<RefundContractRepository> refundContractRepository;
	private final Supplier<RefundInvoiceCandidateService> refundInvoiceCandidateService;
	private final Supplier<AssignableInvoiceCandidateRepository> assignableInvoiceCandidateRepository;
	private final Supplier<CandidateAssignmentService> candidateAssignmentService;

	@VisibleForTesting
	RefundInvoiceCandidateListener(
			@NonNull final Supplier<RefundContractRepository> refundContractRepository,
			@NonNull final Supplier<RefundInvoiceCandidateService> refundInvoiceCandidateService,
			@NonNull final Supplier<AssignableInvoiceCandidateRepository> assignableInvoiceCandidateRepository,
			@NonNull final Supplier<CandidateAssignmentService> candidateAssignmentService)
	{
		this.refundContractRepository = refundContractRepository;
		this.refundInvoiceCandidateService = refundInvoiceCandidateService;
		this.assignableInvoiceCandidateRepository = assignableInvoiceCandidateRepository;
		this.candidateAssignmentService = candidateAssignmentService;
	}

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

			final AssignableInvoiceCandidate assignableCandidate = assignableInvoiceCandidateRepository.get().ofRecord(candidate);
			candidateAssignmentService.get().assignToNewlyMatchingContracts(assignableCandidate);
		}
		catch (final RuntimeException e)
		{
			// the invoice candidate itself is updated, even if something is wrong with its refund
			final AdIssueId issueId = Services.get(IErrorManager.class).createIssue(e);
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
