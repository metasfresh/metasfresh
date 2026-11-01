package de.metas.contracts.refund.interceptor;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.refund.CandidateAssignmentService;
import de.metas.contracts.refund.RefundContractRepository;
import de.metas.contracts.refund.RefundInvoiceCandidateInvalidator;
import de.metas.contracts.refund.RefundInvoiceCandidateRepository;
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
		interceptor = new C_Flatrate_Term(refundContractRepository, new RefundInvoiceCandidateInvalidator(refundInvoiceCandidateRepository, refundContractRepository, Mockito.mock(CandidateAssignmentService.class)));
	}

	/**
	 * Like after completing a term, the caches must be reset after reactivating it (void and close are prohibited for all terms): a contract id that was cached before the commit
	 * would keep matching, and the update run would assign invoice candidates to a term that is no longer completed.
	 */
	@Test
	public void reactivate_resetsTheCachesOfTheRefundContractsAfterCommit()
	{
		final I_C_Flatrate_Term term = createRefundTerm(LocalDate.of(2026, 6, 1), LocalDate.of(2026, 12, 31));

		// invoke the method under test; there is no transaction, so the after-commit listener runs right away
		interceptor.deleteRefundInvoiceCandidates(term);

		Mockito.verify(refundContractRepository).resetCaches();
	}

	/** a completed refund term lets the invalidator flag the invoice candidates of its partner */
	@Test
	public void complete_invalidatesTheCandidatesOfTheContract()
	{
		final RefundInvoiceCandidateInvalidator invalidator = Mockito.mock(RefundInvoiceCandidateInvalidator.class);
		final C_Flatrate_Term interceptorWithMock = new C_Flatrate_Term(refundContractRepository, invalidator);
		final I_C_Flatrate_Term term = createRefundTerm(LocalDate.of(2026, 6, 1), LocalDate.of(2026, 12, 31));

		// invoke the method under test
		interceptorWithMock.invalidateMatchingInvoiceCandidatesAfterCommit(term);

		Mockito.verify(invalidator).invalidateCandidatesOfContractAfterCommit(refundContractRepository.ofRecord(term));
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
}
