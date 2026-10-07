package de.metas.contracts.refund.interceptor;

import de.metas.aggregation.api.IAggregationFactory;
import de.metas.aggregation.model.X_C_Aggregation;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.refund.AssignableInvoiceCandidate;
import de.metas.contracts.refund.AssignableInvoiceCandidateFactory;
import de.metas.contracts.refund.AssignableInvoiceCandidateRepository;
import de.metas.contracts.refund.AssignmentToRefundCandidateRepository;
import de.metas.contracts.refund.CandidateAssignmentService;
import de.metas.contracts.refund.RefundInvoiceCandidateRepository;
import de.metas.contracts.refund.RefundInvoiceCandidateService;
import de.metas.contracts.refund.RefundTestTools;
import de.metas.contracts.refund.allqties.refundconfigchange.RefundConfigChangeService;
import de.metas.contracts.refund.packaging.RefundPackagingFilter;
import de.metas.currency.CurrencyRepository;
import de.metas.document.dimension.DimensionService;
import de.metas.invoicecandidate.agg.key.impl.ICHeaderAggregationKeyBuilder_OLD;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.document.dimension.InvoiceCandidateDimensionFactory;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.MoneyService;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Order_CompensationGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class C_Invoice_Candidate_Manage_Refund_CandidatesTest
{
	private RefundTestTools refundTestTools;
	private AssignableInvoiceCandidateRepository assignableInvoiceCandidateRepository;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		Services.get(IAggregationFactory.class).setDefaultAggregationKeyBuilder(I_C_Invoice_Candidate.class, X_C_Aggregation.AGGREGATIONUSAGELEVEL_Header, ICHeaderAggregationKeyBuilder_OLD.instance);
		SpringContextHolder.registerJUnitBean(new DimensionService(singletonList(new InvoiceCandidateDimensionFactory())));

		final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository = RefundInvoiceCandidateRepository.createInstanceForUnitTesting();
		final AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository = new AssignmentToRefundCandidateRepository(refundInvoiceCandidateRepository);
		final MoneyService moneyService = new MoneyService(new CurrencyRepository());
		assignableInvoiceCandidateRepository = new AssignableInvoiceCandidateRepository(AssignableInvoiceCandidateFactory.newForUnitTesting());
		final RefundInvoiceCandidateService refundInvoiceCandidateService = new RefundInvoiceCandidateService(refundInvoiceCandidateRepository, moneyService);
		final CandidateAssignmentService candidateAssignmentService = new CandidateAssignmentService(
				refundInvoiceCandidateRepository.getRefundContractRepository(),
				refundInvoiceCandidateService,
				assignableInvoiceCandidateRepository,
				assignmentToRefundCandidateRepository,
				refundInvoiceCandidateRepository,
				new RefundConfigChangeService(assignmentToRefundCandidateRepository, moneyService, refundInvoiceCandidateService),
				new RefundPackagingFilter(Optional.empty()));

		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new C_Invoice_Candidate_Manage_Refund_Candidates(
				refundInvoiceCandidateRepository,
				assignableInvoiceCandidateRepository,
				refundInvoiceCandidateService,
				candidateAssignmentService));

		refundTestTools = RefundTestTools.newInstance();
	}

	/**
	 * A candidate that becomes the discount line of a contract-created compensation group (e.g. the order is regrouped)
	 * leaves the refund base, even though none of its amounts or dates changed.
	 */
	@Test
	void candidateRegroupedIntoAContractGroup_isUnassigned()
	{
		final AssignableInvoiceCandidate assignedCandidate = refundTestTools.createAssignableCandidateWithAssignment();
		final I_C_Invoice_Candidate record = load(assignedCandidate.getId().getRepoId(), I_C_Invoice_Candidate.class);

		final I_C_Order order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(record.getBill_BPartner_ID());
		saveRecord(order);
		final I_C_Flatrate_Term compensationGroupContract = newInstance(I_C_Flatrate_Term.class);
		compensationGroupContract.setType_Conditions(TypeConditions.COMPENSATION_GROUP.getCode());
		saveRecord(compensationGroupContract);
		final I_C_Order_CompensationGroup group = newInstance(I_C_Order_CompensationGroup.class);
		group.setC_Order_ID(order.getC_Order_ID());
		group.setC_Flatrate_Term_ID(compensationGroupContract.getC_Flatrate_Term_ID());
		saveRecord(group);

		// invoke the interceptor: only the group columns change
		try (final IAutoCloseable ignored = Services.get(IInvoiceCandBL.class).setUpdateProcessInProgress())
		{
			record.setC_Order_ID(order.getC_Order_ID());
			record.setC_Order_CompensationGroup_ID(group.getC_Order_CompensationGroup_ID());
			record.setIsGroupCompensationLine(true);
			saveRecord(record);
		}

		assertThat(assignableInvoiceCandidateRepository.getById(assignedCandidate.getId()).getAssignmentsToRefundCandidates()).isEmpty();
	}
}
