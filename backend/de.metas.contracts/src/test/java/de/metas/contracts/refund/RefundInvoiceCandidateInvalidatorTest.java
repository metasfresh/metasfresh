package de.metas.contracts.refund;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.ConditionsId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;
import org.mockito.ArgumentCaptor;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.common.util.time.SystemTime;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.compiere.model.X_C_InvoiceSchedule;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class RefundInvoiceCandidateInvalidatorTest
{
	private static final BPartnerId BPARTNER_ID = BPartnerId.ofRepoId(30);
	private static final BPartnerId OTHER_BPARTNER_ID = BPartnerId.ofRepoId(31);

	private RefundInvoiceCandidateInvalidator invalidator;
	private RefundInvoiceCandidateRepository refundInvoiceCandidateRepository;
	private RefundContractRepository refundContractRepository;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		saveRecord(newInstance(I_C_UOM.class));
		refundInvoiceCandidateRepository = RefundInvoiceCandidateRepository.createInstanceForUnitTesting();
		refundContractRepository = Mockito.spy(refundInvoiceCandidateRepository.getRefundContractRepository());
		invalidator = new RefundInvoiceCandidateInvalidator(refundInvoiceCandidateRepository, refundContractRepository);
	}

	/**
	 * A back-dated term only picks up the invoice candidates of the current open period; the ones of past periods are not flagged for the assignment.
	 */
	@Test
	public void createInvoiceCandidatesOfCurrentPeriodQuery_startsWithTheCurrentOpenPeriod()
	{
		final I_C_Flatrate_Term term = createRefundTerm(LocalDate.of(2026, 6, 1), LocalDate.of(2026, 12, 31));

		final I_C_Invoice_Candidate june = createInvoiceCandidate(LocalDate.of(2026, 6, 10), true);
		final I_C_Invoice_Candidate julyProcessed = createInvoiceCandidate(LocalDate.of(2026, 7, 5), true);
		final I_C_Invoice_Candidate julyOpen = createInvoiceCandidate(LocalDate.of(2026, 7, 20), false);
		final I_C_Invoice_Candidate august = createInvoiceCandidate(LocalDate.of(2026, 8, 3), false);
		createInvoiceCandidate(LocalDate.of(2027, 1, 3), false); // after the term

		// invoke the method under test
		final List<I_C_Invoice_Candidate> result = refundInvoiceCandidateRepository.createInvoiceCandidatesOfCurrentPeriodQuery(refundContractRepository.ofRecord(term), LocalDate.of(2026, 7, 15)).list();

		assertThat(result)
				.extracting(I_C_Invoice_Candidate::getC_Invoice_Candidate_ID)
				.containsExactlyInAnyOrder(julyProcessed.getC_Invoice_Candidate_ID(), julyOpen.getC_Invoice_Candidate_ID(), august.getC_Invoice_Candidate_ID())
				.doesNotContain(june.getC_Invoice_Candidate_ID());
	}

	/** a term that starts in the current period is bound by its start date */
	@Test
	public void createInvoiceCandidatesOfCurrentPeriodQuery_termStartingInTheCurrentPeriod()
	{
		final I_C_Flatrate_Term term = createRefundTerm(LocalDate.of(2026, 7, 10), LocalDate.of(2026, 12, 31));
		createInvoiceCandidate(LocalDate.of(2026, 7, 5), true); // before the term
		final I_C_Invoice_Candidate julyAfterStart = createInvoiceCandidate(LocalDate.of(2026, 7, 12), true);

		assertThat(refundInvoiceCandidateRepository.createInvoiceCandidatesOfCurrentPeriodQuery(refundContractRepository.ofRecord(term), LocalDate.of(2026, 7, 15)).list())
				.extracting(I_C_Invoice_Candidate::getC_Invoice_Candidate_ID)
				.containsExactly(julyAfterStart.getC_Invoice_Candidate_ID());
	}

	/** the refund always goes to the invoice partner: a candidate invoiced to another partner is not flagged, even if the term's partner ordered (or receives) the goods */
	@Test
	public void createInvoiceCandidatesOfCurrentPeriodQuery_onlyTheCandidatesInvoicedToTheTermsPartner()
	{
		final I_C_Flatrate_Term term = createRefundTerm(LocalDate.of(2026, 6, 1), LocalDate.of(2026, 12, 31));
		final I_C_Invoice_Candidate invoicedToThePartner = createInvoiceCandidate(LocalDate.of(2026, 7, 20), false);

		final I_C_Order orderOfThePartner = newInstance(I_C_Order.class);
		orderOfThePartner.setC_BPartner_ID(BPARTNER_ID.getRepoId());
		saveRecord(orderOfThePartner);
		final I_C_Invoice_Candidate invoicedToAnotherPartner = createInvoiceCandidate(LocalDate.of(2026, 7, 20), false);
		invoicedToAnotherPartner.setBill_BPartner_ID(OTHER_BPARTNER_ID.getRepoId());
		invoicedToAnotherPartner.setC_Order_ID(orderOfThePartner.getC_Order_ID());
		saveRecord(invoicedToAnotherPartner);

		assertThat(refundInvoiceCandidateRepository.createInvoiceCandidatesOfCurrentPeriodQuery(refundContractRepository.ofRecord(term), LocalDate.of(2026, 7, 15)).list())
				.extracting(I_C_Invoice_Candidate::getC_Invoice_Candidate_ID)
				.containsExactly(invoicedToThePartner.getC_Invoice_Candidate_ID());
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
		invalidator.flagInvoiceCandidates(refundInvoiceCandidateRepository.createInvoiceCandidatesOfCurrentPeriodQuery(refundContractRepository.ofRecord(term), LocalDate.of(2026, 7, 15)));

		Mockito.verify(refundContractRepository).resetCaches();
	}

	/**
	 * When the amount of a refund config changes (e.g. its currency is corrected), the refund candidates of the completed terms with these conditions are checked again,
	 * and the invoice candidates of each term's partner are flagged, so that those that could not be assigned before get assigned now.
	 */
	@Test
	public void invalidateCandidatesOfConditionsAfterCommit_flagsTheRefundCandidatesAndTheCandidatesOfThePartner()
	{
		final IInvoiceCandDAO invoiceCandDAO = Mockito.mock(IInvoiceCandDAO.class);
		Services.registerService(IInvoiceCandDAO.class, invoiceCandDAO);
		invalidator = new RefundInvoiceCandidateInvalidator(refundInvoiceCandidateRepository, refundContractRepository); // after the mock is registered, because it holds the DAO as field

		final I_C_Flatrate_Term term = createRefundTerm(LocalDate.of(2026, 6, 1), LocalDate.of(2026, 12, 31));
		final I_C_Invoice_Candidate july = createInvoiceCandidate(LocalDate.of(2026, 7, 20), false);
		final I_C_Flatrate_Term draftTerm = createRefundTerm(LocalDate.of(2026, 6, 1), LocalDate.of(2026, 12, 31));
		draftTerm.setC_Flatrate_Conditions_ID(term.getC_Flatrate_Conditions_ID());
		draftTerm.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Drafted);
		saveRecord(draftTerm);

		SystemTime.setFixedTimeSource(LocalDate.of(2026, 7, 15).atStartOfDay(ZoneId.systemDefault()));
		try
		{
			// invoke the method under test; there is no transaction, so the after-commit listener runs right away
			invalidator.invalidateCandidatesOfConditionsAfterCommit(ConditionsId.ofRepoId(term.getC_Flatrate_Conditions_ID()));
		}
		finally
		{
			SystemTime.resetTimeSource();
		}

		Mockito.verify(invoiceCandDAO).invalidateCandsThatReference(TableRecordReference.of(term));
		Mockito.verify(invoiceCandDAO, Mockito.never()).invalidateCandsThatReference(TableRecordReference.of(draftTerm));

		@SuppressWarnings("unchecked") final ArgumentCaptor<IQuery<I_C_Invoice_Candidate>> queryCaptor = ArgumentCaptor.forClass(IQuery.class);
		Mockito.verify(invoiceCandDAO).invalidateCandsFor(queryCaptor.capture());
		assertThat(queryCaptor.getValue().list())
				.extracting(I_C_Invoice_Candidate::getC_Invoice_Candidate_ID)
				.containsExactly(july.getC_Invoice_Candidate_ID());
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
