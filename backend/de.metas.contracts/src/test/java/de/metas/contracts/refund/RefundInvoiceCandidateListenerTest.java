package de.metas.contracts.refund;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoice.service.InvoiceScheduleRepository;
import de.metas.order.IOrderDAO;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.ad.trx.api.impl.PlainTrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

public class RefundInvoiceCandidateListenerTest
{
	private static final LocalDate DATE = LocalDate.of(2026, 7, 15);

	private RefundInvoiceCandidateService refundInvoiceCandidateService;
	private AssignableInvoiceCandidateRepository assignableInvoiceCandidateRepository;
	private CandidateAssignmentService candidateAssignmentService;
	private IOrderDAO orderDAO;
	private RefundInvoiceCandidateListener listener;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		orderDAO = Mockito.mock(IOrderDAO.class);
		Services.registerService(IOrderDAO.class, orderDAO);

		refundInvoiceCandidateService = Mockito.mock(RefundInvoiceCandidateService.class);
		assignableInvoiceCandidateRepository = Mockito.mock(AssignableInvoiceCandidateRepository.class);
		candidateAssignmentService = Mockito.mock(CandidateAssignmentService.class);
		final RefundContractRepository refundContractRepository = new RefundContractRepository(new RefundConfigRepository(new InvoiceScheduleRepository()));

		listener = new RefundInvoiceCandidateListener(
				() -> refundContractRepository,
				() -> refundInvoiceCandidateService,
				() -> assignableInvoiceCandidateRepository,
				() -> candidateAssignmentService);
	}

	/**
	 * Without any refund contract, nothing is loaded for the candidate: not the order, and no assignable candidate with its assignments.
	 */
	@Test
	public void onAfterUpdated_withoutRefundContracts_doesNothing()
	{
		listener.onAfterUpdated(createInvoiceCandidate());

		Mockito.verifyNoInteractions(orderDAO, refundInvoiceCandidateService, assignableInvoiceCandidateRepository, candidateAssignmentService);
	}

	/**
	 * Refund contracts exist, but none of the candidate's partner: still nothing is loaded for the candidate.
	 */
	@Test
	public void onAfterUpdated_withRefundContractsOfOtherPartners_doesNothing()
	{
		createRefundTerm(BPartnerId.ofRepoId(99));

		listener.onAfterUpdated(createInvoiceCandidate());

		Mockito.verifyNoInteractions(refundInvoiceCandidateService, assignableInvoiceCandidateRepository, candidateAssignmentService);
	}

	/**
	 * The update run processes a chunk of candidates in one transaction without savepoints, and the candidate is already saved when the listener is called.
	 * So the refund assignment of a candidate is done within a savepoint of that transaction: if it fails, e.g. for the second of two matching terms,
	 * then what was already written for the first term is rolled back (and an aborted database transaction is usable again), instead of being committed with the chunk.
	 */
	@Test
	public void onAfterUpdated_failingAssignment_isRolledBackToASavepoint()
	{
		createRefundTerm(BPartnerId.ofRepoId(30));
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final AtomicBoolean savepointActiveDuringAssignment = new AtomicBoolean(false);
		Mockito.doAnswer(invocation -> {
					savepointActiveDuringAssignment.set(currentTrx(trxManager).hasActiveSavepoints());
					throw new AdempiereException("the assignment to the second term fails");
				})
				.when(candidateAssignmentService).assignToNewlyMatchingContracts(any());
		final I_C_Invoice_Candidate candidate = createInvoiceCandidate();

		trxManager.runInNewTrx(() -> {
			// invoke the method under test; the exception doesn't get out, so the candidate itself is still updated
			listener.onAfterUpdated(candidate);

			assertThat(currentTrx(trxManager).hasActiveSavepoints()).as("the savepoint is gone after the rollback").isFalse();
		});

		assertThat(savepointActiveDuringAssignment).as("the assignment ran within a savepoint").isTrue();
	}

	private static PlainTrx currentTrx(final ITrxManager trxManager)
	{
		return (PlainTrx)trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.Fail);
	}

	private static void createRefundTerm(final BPartnerId billBPartnerId)
	{
		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		term.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Refund);
		term.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Completed);
		term.setBill_BPartner_ID(billBPartnerId.getRepoId());
		term.setStartDate(TimeUtil.asTimestamp(DATE.minusDays(5)));
		term.setEndDate(TimeUtil.asTimestamp(DATE.plusDays(5)));
		saveRecord(term);
	}

	private static I_C_Invoice_Candidate createInvoiceCandidate()
	{
		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setBill_BPartner_ID(30);
		ic.setM_Product_ID(31);
		ic.setC_Currency_ID(32);
		ic.setDateToInvoice(TimeUtil.asTimestamp(DATE));
		saveRecord(ic);
		return ic;
	}
}
