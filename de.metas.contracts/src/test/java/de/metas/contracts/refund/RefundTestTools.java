package de.metas.contracts.refund;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.adempiere.util.collections.CollectionUtils.singleElement;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_C_InvoiceSchedule;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.model.I_C_Currency;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.invoicecandidate.FlatrateTerm_Handler;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Invoice_Candidate_Assignment;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.refund.RefundConfig.RefundBase;
import de.metas.contracts.refund.RefundConfig.RefundConfigBuilder;
import de.metas.contracts.refund.RefundConfig.RefundInvoiceType;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.invoice.InvoiceSchedule;
import de.metas.invoice.InvoiceScheduleRepository;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lang.Percent;
import de.metas.money.Currency;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Getter;
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

public class RefundTestTools
{
	public static final BPartnerId BPARTNER_ID = BPartnerId.ofRepoId(10);
	private static final BigDecimal TWENTY = new BigDecimal("20");
	private static final BigDecimal NINE = new BigDecimal("9");
	private static final BigDecimal TWO = new BigDecimal("2");
	private static final BigDecimal HUNDRED = new BigDecimal("100");

	private static final LocalDate ASSIGNABLE_CANDIDATE_INVOICE_DATE = LocalDate.now();
	public static final LocalDate CONTRACT_START_DATE = ASSIGNABLE_CANDIDATE_INVOICE_DATE.minusDays(2);
	public static final LocalDate CONTRACT_END_DATE = ASSIGNABLE_CANDIDATE_INVOICE_DATE.plusDays(2);

	// private static final ProductId PRODUCT_ID = ProductId.ofRepoId(20);

	@Getter
	private final Currency currency;

	@Getter
	private final I_C_UOM uomRecord;

	@Getter
	private I_M_Product productRecord;

	@Getter
	private I_C_Currency currencyRecord;

	private RefundInvoiceCandidateRepository refundInvoiceCandidateRepository;

	// used when creating refund configs
	private InvoiceSchedule invoiceSchedule;

	public RefundTestTools()
	{
		final I_C_ILCandHandler icHandlerRecord = newInstance(I_C_ILCandHandler.class);
		icHandlerRecord.setClassname(FlatrateTerm_Handler.class.getName());
		saveRecord(icHandlerRecord);

		final I_C_DocType docTypeRecord = newInstance(I_C_DocType.class);
		docTypeRecord.setIsSOTrx(true); // needs to match the C_Invoice_Candidate record we will test with
		docTypeRecord.setDocBaseType(X_C_DocType.DOCBASETYPE_ARCreditMemo);
		docTypeRecord.setDocSubType(X_C_DocType.DOCSUBTYPE_Rueckverguetungsrechnung);
		saveRecord(docTypeRecord);

		currencyRecord = newInstance(I_C_Currency.class);
		currencyRecord.setStdPrecision(2);
		saveRecord(currencyRecord);

		currency = Currency.builder()
				.id(CurrencyId.ofRepoId(currencyRecord.getC_Currency_ID()))
				.precision(currencyRecord.getStdPrecision())
				.build();

		uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);

		productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM(uomRecord);
		saveRecord(productRecord);

		final I_C_InvoiceSchedule invoiceScheduleRecord = newInstance(I_C_InvoiceSchedule.class);
		invoiceScheduleRecord.setInvoiceFrequency(X_C_InvoiceSchedule.INVOICEFREQUENCY_Daily);
		saveRecord(invoiceScheduleRecord);

		final InvoiceScheduleRepository invoiceScheduleRepository = new InvoiceScheduleRepository();
		invoiceSchedule = invoiceScheduleRepository.ofRecord(invoiceScheduleRecord);

		final RefundConfigRepository refundConfigRepository = new RefundConfigRepository(invoiceScheduleRepository);

		final RefundContractRepository refundContractRepository = new RefundContractRepository(refundConfigRepository);

		final AssignmentAggregateService assignmentAggregateService = new AssignmentAggregateService(refundConfigRepository);

		final RefundInvoiceCandidateFactory refundInvoiceCandidateFactory = new RefundInvoiceCandidateFactory(refundContractRepository, assignmentAggregateService);

		refundInvoiceCandidateRepository = new RefundInvoiceCandidateRepository(
				refundContractRepository, refundInvoiceCandidateFactory);
	}

	public static I_C_Invoice_Candidate retrieveRecord(@NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		final I_C_Invoice_Candidate invoiceCandidateRecord = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(
						I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID,
						invoiceCandidateId.getRepoId())
				.create()
				.firstOnly(I_C_Invoice_Candidate.class);

		return invoiceCandidateRecord;
	}

	/**
	 * Creates an instance complete with contract and all backing records.
	 * The contract's begin and end dates are three days before
	 * resp. one days after the candidate's invoiceableFrom date.
	 */
	public RefundInvoiceCandidate createRefundCandidate()
	{
		final RefundContract refundContract = createRefundContract();

		return createRefundCandidate(refundContract);
	}

	public RefundInvoiceCandidate createRefundCandidate(@NonNull final RefundContract refundContract)
	{
		Check.assumeNotNull(refundContract.getId(),
				"The given refundContract has to be persisted (i.e. id!=null); refundContract={}", refundContract);

		final LocalDate invoiceableFromDate = ASSIGNABLE_CANDIDATE_INVOICE_DATE.plusDays(1);

		final I_C_Invoice_Candidate invoiceCandidateRecord = newInstance(I_C_Invoice_Candidate.class);
		invoiceCandidateRecord.setIsSOTrx(true); // pls keep in sync with C_DocType that we create in this classe's constructor
		invoiceCandidateRecord.setM_Product(productRecord);
		invoiceCandidateRecord.setPriceActual(HUNDRED);
		invoiceCandidateRecord.setC_Currency_ID(currency.getId().getRepoId());
		invoiceCandidateRecord.setDateToInvoice(TimeUtil.asTimestamp(invoiceableFromDate));
		invoiceCandidateRecord.setRecord_ID(refundContract.getId().getRepoId());
		invoiceCandidateRecord.setAD_Table_ID(getTableId(I_C_Flatrate_Term.class));
		invoiceCandidateRecord.setBill_BPartner_ID(BPARTNER_ID.getRepoId());
		invoiceCandidateRecord.setQtyToInvoice(ONE);
		invoiceCandidateRecord.setProcessed(false);
		saveRecord(invoiceCandidateRecord);

		return refundInvoiceCandidateRepository
				.ofNullableRefundRecord(invoiceCandidateRecord)
				.get();
	}

	public RefundContract createRefundContract()
	{
		final I_C_InvoiceSchedule invoiceScheduleRecord = newInstance(I_C_InvoiceSchedule.class);
		invoiceScheduleRecord.setInvoiceFrequency(X_C_InvoiceSchedule.INVOICEFREQUENCY_Daily);
		saveRecord(invoiceScheduleRecord);

		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refund);
		saveRecord(conditions);

		final I_C_Flatrate_RefundConfig refundConfigRecord = newInstance(I_C_Flatrate_RefundConfig.class);
		refundConfigRecord.setC_Flatrate_Conditions(conditions);
		refundConfigRecord.setM_Product(productRecord);
		refundConfigRecord.setRefundInvoiceType(X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Invoice); // keep in sync with the C_DocType's subType that we set up in the constructor.
		refundConfigRecord.setC_InvoiceSchedule(invoiceScheduleRecord);
		refundConfigRecord.setRefundBase(X_C_Flatrate_RefundConfig.REFUNDBASE_Percentage);
		refundConfigRecord.setRefundPercent(TWENTY);
		refundConfigRecord.setRefundMode(X_C_Flatrate_RefundConfig.REFUNDMODE_Accumulated);
		saveRecord(refundConfigRecord);

		final I_C_Flatrate_Term contractRecord = newInstance(I_C_Flatrate_Term.class);
		contractRecord.setC_Flatrate_Conditions(conditions);
		contractRecord.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Refund);
		contractRecord.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Completed);
		contractRecord.setBill_BPartner_ID(BPARTNER_ID.getRepoId());
		contractRecord.setM_Product(productRecord);
		contractRecord.setStartDate(TimeUtil.asTimestamp(CONTRACT_START_DATE));
		contractRecord.setEndDate(TimeUtil.asTimestamp(CONTRACT_END_DATE));
		saveRecord(contractRecord);

		final RefundContractRepository refundContractRepository = new RefundContractRepository(new RefundConfigRepository(new InvoiceScheduleRepository()));
		return refundContractRepository.getById(FlatrateTermId.ofRepoId(contractRecord.getC_Flatrate_Term_ID()));
		//
		// final InvoiceSchedule invoiceSchedule = InvoiceSchedule
		// .builder()
		// .id(InvoiceScheduleId.ofRepoId(invoiceScheduleRecord.getC_InvoiceSchedule_ID()))
		// .frequency(Frequency.DAILY)
		// .build();
		//
		// final RefundConfig refundConfig = RefundConfig
		// .builder()
		// .id(RefundConfigId.ofRepoId(refundConfigRecord.getC_Flatrate_RefundConfig_ID()))
		// .productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
		// .minQty(ZERO)
		// .refundBase(RefundBase.PERCENTAGE)
		// .percent(Percent.of(TWENTY))
		// .conditionsId(ConditionsId.ofRepoId(contractRecord.getC_Flatrate_Conditions_ID()))
		// .invoiceSchedule(invoiceSchedule)
		// .refundInvoiceType(RefundInvoiceType.INVOICE) // keep in sync with the C_DocType's subType that we set up in the constructor.
		// .refundMode(RefundMode.ALL_MAX_SCALE)
		// .build();
		//
		// final RefundContract refundContract = RefundContract.builder()
		// .id(FlatrateTermId.ofRepoId(contractRecord.getC_Flatrate_Term_ID()))
		// .refundConfig(refundConfig)
		// .startDate(CONTRACT_START_DATE)
		// .endDate(CONTRACT_END_DATE)
		// .build();
		// return refundContract;
	}

	public AssignableInvoiceCandidate createAssignableCandidateStandlone()
	{
		return createAssignableCandidateStandlone(ONE);
	}

	public AssignableInvoiceCandidate createAssignableCandidateStandlone(@NonNull final BigDecimal quantityAsBigDecimal)
	{
		final I_C_Invoice_Candidate invoiceCandidateRecord = createAssignableInvoiceCandidateRecord(quantityAsBigDecimal);

		final Money money = Money.of(TEN, currency.getId());

		return AssignableInvoiceCandidate
				.builder()
				.repoId(InvoiceCandidateId.ofRepoId(invoiceCandidateRecord.getC_Invoice_Candidate_ID()))
				.bpartnerId(BPARTNER_ID)
				.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.money(money)
				.precision(currency.getPrecision())
				.invoiceableFrom(ASSIGNABLE_CANDIDATE_INVOICE_DATE)
				.quantity(Quantity.of(quantityAsBigDecimal, uomRecord))
				.build();
	}

	public AssignableInvoiceCandidate createAssignableCandidateWithAssignment()
	{
		final AssignableInvoiceCandidate assignableInvoiceCandidate = createAssignableCandidateStandlone();
		final RefundInvoiceCandidate refundCandidate = createRefundCandidate();

		final RefundConfig refundConfig = singleElement(refundCandidate.getRefundConfigs());

		final I_C_Invoice_Candidate_Assignment assignmentRecord = newInstance(I_C_Invoice_Candidate_Assignment.class);
		assignmentRecord.setC_Invoice_Candidate_Term_ID(refundCandidate.getId().getRepoId());
		assignmentRecord.setC_Invoice_Candidate_Assigned_ID(assignableInvoiceCandidate.getRepoId().getRepoId());
		assignmentRecord.setC_Flatrate_Term_ID(refundCandidate.getRefundContract().getId().getRepoId());
		assignmentRecord.setC_Flatrate_RefundConfig_ID(refundConfig.getId().getRepoId());
		assignmentRecord.setIsAssignedQuantityIncludedInSum(true);
		assignmentRecord.setAssignedMoneyAmount(TWO);
		assignmentRecord.setAssignedQuantity(ONE);
		saveRecord(assignmentRecord);

		final RefundInvoiceCandidate reloadedRefundCandidate = refundInvoiceCandidateRepository.getById(refundCandidate.getId());

		final AssignmentToRefundCandidate assignementToRefundCandidate = new AssignmentToRefundCandidate(
				refundConfig.getId(),
				assignableInvoiceCandidate.getRepoId(),
				reloadedRefundCandidate,
				assignableInvoiceCandidate.getMoney(),
				Money.of(TWO, reloadedRefundCandidate.getMoney().getCurrencyId()),
				Quantity.of(ONE, uomRecord),
				true /*useAssignedQtyInSum*/);
		return assignableInvoiceCandidate
				.toBuilder()
				.assignmentToRefundCandidate(assignementToRefundCandidate)
				.build();
	}

	public I_C_Invoice_Candidate createAssignableInvoiceCandidateRecord(@NonNull final BigDecimal quantityToInvoice)
	{
		final I_C_Invoice_Candidate invoiceCandidateRecord = newInstance(I_C_Invoice_Candidate.class);
		invoiceCandidateRecord.setIsSOTrx(true); // pls keep in sync with C_DocType that we create in this class's constructor
		invoiceCandidateRecord.setNetAmtInvoiced(ONE);
		invoiceCandidateRecord.setNetAmtToInvoice(NINE);
		invoiceCandidateRecord.setC_Currency_ID(currency.getId().getRepoId());
		invoiceCandidateRecord.setQtyToInvoice(quantityToInvoice);
		invoiceCandidateRecord.setDateToInvoice(TimeUtil.asTimestamp(ASSIGNABLE_CANDIDATE_INVOICE_DATE));
		invoiceCandidateRecord.setBill_BPartner_ID(BPARTNER_ID.getRepoId());

		invoiceCandidateRecord.setM_Product(productRecord);
		saveRecord(invoiceCandidateRecord);
		return invoiceCandidateRecord;
	}

	public RefundConfigBuilder createAndInitConfigBuilder()
	{
		final RefundConfigBuilder refundConfigBuilder = RefundConfig
				.builder()
				.minQty(ZERO)
				.refundBase(RefundBase.PERCENTAGE)
				.percent(Percent.of(TWENTY))
				.conditionsId(ConditionsId.ofRepoId(20))
				.invoiceSchedule(invoiceSchedule)
				.refundInvoiceType(RefundInvoiceType.INVOICE) // keep in sync with the C_DocType's subType that we set up in the constructor.
				.refundMode(RefundMode.APPLY_TO_ALL_QTIES);

		return refundConfigBuilder;
	}

	public static RefundConfig extractSingleConfig(@NonNull final RefundInvoiceCandidate refundCandidate)
	{
		final List<RefundConfig> resultConfigs = refundCandidate.getRefundConfigs();
		assertThat(resultConfigs).hasSize(1);
		return resultConfigs.get(0);
	}
}
