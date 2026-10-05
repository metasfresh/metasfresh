package de.metas.contracts.refund.interceptor;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.refund.RefundContractRepository;
import de.metas.contracts.refund.RefundInvoiceCandidateRepository;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.I_C_UOM;
import org.compiere.model.X_C_InvoiceSchedule;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class C_Flatrate_Term_Test
{
	private static final BPartnerId BPARTNER_ID = BPartnerId.ofRepoId(30);

	private C_Flatrate_Term interceptor;
	private RefundInvoiceCandidateRepository refundInvoiceCandidateRepository;
	private RefundContractRepository refundContractRepository;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		saveRecord(newInstance(I_C_UOM.class));
		refundInvoiceCandidateRepository = RefundInvoiceCandidateRepository.createInstanceForUnitTesting();
		refundContractRepository = Mockito.spy(refundInvoiceCandidateRepository.getRefundContractRepository());
		interceptor = new C_Flatrate_Term(refundInvoiceCandidateRepository, refundContractRepository);
	}

	/**
	 * A back-dated term only picks up the invoice candidates of the current open period; the ones of past periods are not flagged for the assignment.
	 */
	@Test
	public void createInvoiceCandidatesToInvalidQuery_startsWithTheCurrentOpenPeriod()
	{
		final I_C_Flatrate_Term term = createRefundTerm(LocalDate.of(2026, 6, 1), LocalDate.of(2026, 12, 31));

		final I_C_Invoice_Candidate june = createInvoiceCandidate(LocalDate.of(2026, 6, 10), true);
		final I_C_Invoice_Candidate julyProcessed = createInvoiceCandidate(LocalDate.of(2026, 7, 5), true);
		final I_C_Invoice_Candidate julyOpen = createInvoiceCandidate(LocalDate.of(2026, 7, 20), false);
		final I_C_Invoice_Candidate august = createInvoiceCandidate(LocalDate.of(2026, 8, 3), false);
		createInvoiceCandidate(LocalDate.of(2027, 1, 3), false); // after the term

		// invoke the method under test
		final java.util.List<I_C_Invoice_Candidate> result = interceptor.createInvoiceCandidatesToInvalidQuery(term, LocalDate.of(2026, 7, 15)).list();

		assertThat(result)
				.extracting(I_C_Invoice_Candidate::getC_Invoice_Candidate_ID)
				.containsExactlyInAnyOrder(julyProcessed.getC_Invoice_Candidate_ID(), julyOpen.getC_Invoice_Candidate_ID(), august.getC_Invoice_Candidate_ID())
				.doesNotContain(june.getC_Invoice_Candidate_ID());
	}

	/** a term that starts in the current period is bound by its start date */
	@Test
	public void createInvoiceCandidatesToInvalidQuery_termStartingInTheCurrentPeriod()
	{
		final I_C_Flatrate_Term term = createRefundTerm(LocalDate.of(2026, 7, 10), LocalDate.of(2026, 12, 31));
		createInvoiceCandidate(LocalDate.of(2026, 7, 5), true); // before the term
		final I_C_Invoice_Candidate julyAfterStart = createInvoiceCandidate(LocalDate.of(2026, 7, 12), true);

		assertThat(interceptor.createInvoiceCandidatesToInvalidQuery(term, LocalDate.of(2026, 7, 15)).list())
				.extracting(I_C_Invoice_Candidate::getC_Invoice_Candidate_ID)
				.containsExactly(julyAfterStart.getC_Invoice_Candidate_ID());
	}

	/**
	 * What the refund contracts repository cached before the term was committed must not survive the commit,
	 * or the flagged invoice candidates would find no contract and stay unassigned.
	 */
	@Test
	public void flagInvoiceCandidates_resetsTheCachesOfTheRefundContracts()
	{
		final I_C_Flatrate_Term term = createRefundTerm(LocalDate.of(2026, 6, 1), LocalDate.of(2026, 12, 31));

		// invoke the method under test
		interceptor.flagInvoiceCandidates(interceptor.createInvoiceCandidatesToInvalidQuery(term, LocalDate.of(2026, 7, 15)));

		Mockito.verify(refundContractRepository).resetCaches();
	}

	private I_C_Flatrate_Term createRefundTerm(@NonNull final LocalDate startDate, @NonNull final LocalDate endDate)
	{
		final I_C_InvoiceSchedule schedule = newInstance(I_C_InvoiceSchedule.class);
		schedule.setInvoiceFrequency(X_C_InvoiceSchedule.INVOICEFREQUENCY_Monthly);
		schedule.setInvoiceDay(31);
		schedule.setInvoiceDistance(1);
		saveRecord(schedule);

		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refund);
		saveRecord(conditions);

		final I_C_Flatrate_RefundConfig config = newInstance(I_C_Flatrate_RefundConfig.class);
		config.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
		config.setC_InvoiceSchedule_ID(schedule.getC_InvoiceSchedule_ID());
		config.setRefundInvoiceType(X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Invoice);
		config.setRefundBase(X_C_Flatrate_RefundConfig.REFUNDBASE_Percentage);
		config.setRefundPercent(BigDecimal.TEN);
		config.setRefundMode(X_C_Flatrate_RefundConfig.REFUNDMODE_Accumulated);
		config.setMinQty(BigDecimal.ZERO);
		saveRecord(config);

		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		term.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Refund);
		term.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Completed);
		term.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
		term.setBill_BPartner_ID(BPARTNER_ID.getRepoId());
		term.setStartDate(TimeUtil.asTimestamp(startDate));
		term.setEndDate(TimeUtil.asTimestamp(endDate));
		saveRecord(term);
		return term;
	}

	private I_C_Invoice_Candidate createInvoiceCandidate(@NonNull final LocalDate dateToInvoice, final boolean processed)
	{
		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setBill_BPartner_ID(BPARTNER_ID.getRepoId());
		ic.setDateToInvoice(TimeUtil.asTimestamp(dateToInvoice));
		ic.setProcessed(processed);
		saveRecord(ic);
		return ic;
	}
}
