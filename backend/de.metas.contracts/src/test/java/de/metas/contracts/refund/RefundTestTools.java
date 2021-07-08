package de.metas.contracts.refund;

import static de.metas.util.collections.CollectionUtils.singleElement;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.Nullable;

import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_C_InvoiceSchedule;
import org.compiere.util.TimeUtil;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
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
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.invoice.InvoiceSchedule;
import de.metas.invoice.service.InvoiceScheduleRepository;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_BPartner;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.Builder;
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
	public static RefundTestTools newInstance()
	{
		return builder().build();
	}

	public static final BPartnerId BPARTNER_ID = BPartnerId.ofRepoId(10);
	public static final BPartnerLocationId BPARTNERLOCATION_ID = BPartnerLocationId.ofRepoId(BPARTNER_ID, 20);
	private static final BigDecimal TWENTY = new BigDecimal("20");
	private static final BigDecimal NINE = new BigDecimal("9");
	private static final BigDecimal TWO = new BigDecimal("2");
	private static final BigDecimal HUNDRED = new BigDecimal("100");

	private static final LocalDate ASSIGNABLE_CANDIDATE_INVOICE_DATE = computeAssignableCandidateInvoiceDate();

	private static final LocalDate REFUND_CANDIDATE_INVOICE_DATE = computeRefundCandidateInvoiceDate(ASSIGNABLE_CANDIDATE_INVOICE_DATE);

	public static final LocalDate CONTRACT_START_DATE = ASSIGNABLE_CANDIDATE_INVOICE_DATE.minusDays(2);
	public static final LocalDate CONTRACT_END_DATE = ASSIGNABLE_CANDIDATE_INVOICE_DATE.plusDays(10);

	// we want this day of month to be after REFUND_CANDIDATE_INVOICE_DATE,
	// because otherwise the refund candidate we create in here is not found to be a match when searched for via ASSIGNABLE_CANDIDATE_INVOICE_DATE
	// note that we also need to make sure to have 1 as the min number
	@VisibleForTesting
	static final int INVOICE_SCHEDULE_DAY_OF_MONTH = computeInvoiceScheduleDayOfMonth();

	private static LocalDate computeAssignableCandidateInvoiceDate()
	{
		return SystemTime.asLocalDate();
	}

	@VisibleForTesting
	static int computeInvoiceScheduleDayOfMonth()
	{
		final LocalDate assignableCandidateInvoiceDate = computeAssignableCandidateInvoiceDate();
		final LocalDate refundCandidateInvoiceDate = computeRefundCandidateInvoiceDate(assignableCandidateInvoiceDate);

		return ((refundCandidateInvoiceDate.getDayOfMonth() + 4) % 28) + 1;
	}

	private static LocalDate computeRefundCandidateInvoiceDate(final LocalDate assignableCandidateInvoiceDate)
	{
		return assignableCandidateInvoiceDate.plusDays(1);
	}

	private final Currency currency;

	@Getter
	private final I_C_UOM uomRecord;

	@Getter
	private I_M_Product productRecord;

	private RefundInvoiceCandidateRepository refundInvoiceCandidateRepository;

	// used when creating refund configs
	private InvoiceSchedule invoiceSchedule;

	public BPartnerLocationId billBPartnerLocationId;

	@Builder
	private RefundTestTools(
			@Nullable final Currency currency)
	{
		final I_C_ILCandHandler icHandlerRecord = InterfaceWrapperHelper.newInstance(I_C_ILCandHandler.class);
		icHandlerRecord.setClassname(FlatrateTerm_Handler.class.getName());
		saveRecord(icHandlerRecord);

		final I_C_DocType docTypeRecord = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docTypeRecord.setIsSOTrx(true); // needs to match the C_Invoice_Candidate record we will test with
		docTypeRecord.setDocBaseType(X_C_DocType.DOCBASETYPE_ARCreditMemo);
		docTypeRecord.setDocSubType(X_C_DocType.DOCSUBTYPE_Rueckverguetungsrechnung);
		saveRecord(docTypeRecord);

		if (currency != null)
		{
			this.currency = currency;
		}
		else
		{
			this.currency = PlainCurrencyDAO.createCurrency(CurrencyCode.EUR);
		}

		uomRecord = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		saveRecord(uomRecord);

		productRecord = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		productRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		saveRecord(productRecord);

		final I_C_InvoiceSchedule invoiceScheduleRecord = InterfaceWrapperHelper.newInstance(I_C_InvoiceSchedule.class);
		invoiceScheduleRecord.setInvoiceFrequency(X_C_InvoiceSchedule.INVOICEFREQUENCY_Monthly);
		invoiceScheduleRecord.setInvoiceDay(INVOICE_SCHEDULE_DAY_OF_MONTH);
		invoiceScheduleRecord.setInvoiceDistance(1);
		saveRecord(invoiceScheduleRecord);

		final InvoiceScheduleRepository invoiceScheduleRepository = new InvoiceScheduleRepository();
		invoiceSchedule = invoiceScheduleRepository.ofRecord(invoiceScheduleRecord);

		final RefundConfigRepository refundConfigRepository = new RefundConfigRepository(invoiceScheduleRepository);

		final RefundContractRepository refundContractRepository = new RefundContractRepository(refundConfigRepository);

		final AssignmentAggregateService assignmentAggregateService = new AssignmentAggregateService(refundConfigRepository);

		final RefundInvoiceCandidateFactory refundInvoiceCandidateFactory = new RefundInvoiceCandidateFactory(refundContractRepository, assignmentAggregateService);

		refundInvoiceCandidateRepository = new RefundInvoiceCandidateRepository(
				refundContractRepository, refundInvoiceCandidateFactory);

		final I_C_Country country_DE = InterfaceWrapperHelper.newInstance(I_C_Country.class);
		country_DE.setAD_Language("de");
		save(country_DE);

		final I_C_Location loc = InterfaceWrapperHelper.newInstance(I_C_Location.class);
		loc.setC_Country_ID(country_DE.getC_Country_ID());
		save(loc);

		final I_C_BPartner partner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		partner.setC_BPartner_ID(BPARTNER_ID.getRepoId());
		save(partner);

		final I_C_BPartner_Location bpLoc = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class);
		bpLoc.setC_Location_ID(loc.getC_Location_ID());
		bpLoc.setC_BPartner_ID(BPARTNER_ID.getRepoId());
		if (billBPartnerLocationId != null)
		{
			Check.assumeEquals(billBPartnerLocationId.getBpartnerId(), BPARTNER_ID, "BP Location shall have the same BP: {}, {}", billBPartnerLocationId, BPARTNER_ID);
			bpLoc.setC_BPartner_Location_ID(BPARTNERLOCATION_ID.getRepoId());
		}
		save(bpLoc);

		billBPartnerLocationId = BPartnerLocationId.ofRepoId(bpLoc.getC_BPartner_ID(), bpLoc.getC_BPartner_Location_ID());
	}

	public CurrencyId getCurrencyId()
	{
		return currency.getId();
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
		final RefundContract refundContract = createRefundContract_APPLY_TO_ALL_QTIES();

		return createRefundCandidate(refundContract);
	}

	public RefundInvoiceCandidate createRefundCandidate(@NonNull final RefundContract refundContract)
	{

		Check.assumeNotNull(refundContract.getId(),
				"The given refundContract has to be persisted (i.e. id!=null); refundContract={}", refundContract);

		final I_C_Invoice_Candidate invoiceCandidateRecord = InterfaceWrapperHelper.newInstance(I_C_Invoice_Candidate.class);
		invoiceCandidateRecord.setIsSOTrx(true); // pls keep in sync with C_DocType that we create in this classe's constructor
		invoiceCandidateRecord.setM_Product_ID(productRecord.getM_Product_ID());
		invoiceCandidateRecord.setPriceActual(HUNDRED);
		invoiceCandidateRecord.setC_Currency_ID(getCurrencyId().getRepoId());
		invoiceCandidateRecord.setDateToInvoice(TimeUtil.asTimestamp(REFUND_CANDIDATE_INVOICE_DATE));
		invoiceCandidateRecord.setRecord_ID(refundContract.getId().getRepoId());
		invoiceCandidateRecord.setAD_Table_ID(getTableId(I_C_Flatrate_Term.class));
		invoiceCandidateRecord.setBill_BPartner_ID(billBPartnerLocationId.getBpartnerId().getRepoId());
		invoiceCandidateRecord.setBill_Location_ID(billBPartnerLocationId.getRepoId());
		invoiceCandidateRecord.setQtyToInvoice(ONE);
		invoiceCandidateRecord.setProcessed(false);
		saveRecord(invoiceCandidateRecord);

		return refundInvoiceCandidateRepository
				.ofNullableRefundRecord(invoiceCandidateRecord)
				.get();
	}

	/**
	 * Creates a refund contract with a single config that has {@link RefundMode#APPLY_TO_ALL_QTIES}, i.e. {@link X_C_Flatrate_RefundConfig#REFUNDMODE_Accumulated}.
	 */
	public RefundContract createRefundContract_APPLY_TO_ALL_QTIES()
	{
		// final I_C_InvoiceSchedule invoiceScheduleRecord = InterfaceWrapperHelper.newInstance(I_C_InvoiceSchedule.class);
		// invoiceScheduleRecord.setInvoiceFrequency(X_C_InvoiceSchedule.INVOICEFREQUENCY_Daily);
		// saveRecord(invoiceScheduleRecord);

		final I_C_Flatrate_Conditions conditions = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Conditions.class);
		conditions.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refund);
		saveRecord(conditions);

		final I_C_Flatrate_RefundConfig refundConfigRecord = InterfaceWrapperHelper.newInstance(I_C_Flatrate_RefundConfig.class);
		refundConfigRecord.setC_Flatrate_Conditions(conditions);
		refundConfigRecord.setM_Product(productRecord);
		refundConfigRecord.setRefundInvoiceType(X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Invoice); // keep in sync with the C_DocType's subType that we set up in the constructor.
		refundConfigRecord.setC_InvoiceSchedule_ID(invoiceSchedule.getId().getRepoId());
		refundConfigRecord.setRefundBase(X_C_Flatrate_RefundConfig.REFUNDBASE_Percentage);
		refundConfigRecord.setRefundPercent(TWENTY);
		refundConfigRecord.setRefundMode(X_C_Flatrate_RefundConfig.REFUNDMODE_Accumulated);
		saveRecord(refundConfigRecord);

		final I_C_Flatrate_Term contractRecord = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Term.class);
		contractRecord.setC_Flatrate_Conditions(conditions);
		contractRecord.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Refund);
		contractRecord.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Completed);
		contractRecord.setBill_BPartner_ID(billBPartnerLocationId.getBpartnerId().getRepoId());
		contractRecord.setBill_Location_ID(billBPartnerLocationId.getRepoId());
		contractRecord.setM_Product_ID(productRecord.getM_Product_ID());
		contractRecord.setStartDate(TimeUtil.asTimestamp(CONTRACT_START_DATE));
		contractRecord.setEndDate(TimeUtil.asTimestamp(CONTRACT_END_DATE));
		saveRecord(contractRecord);

		final RefundContractRepository refundContractRepository = new RefundContractRepository(new RefundConfigRepository(new InvoiceScheduleRepository()));
		return refundContractRepository.getById(FlatrateTermId.ofRepoId(contractRecord.getC_Flatrate_Term_ID()));
	}

	public AssignableInvoiceCandidate createAssignableCandidateStandlone()
	{
		return createAssignableCandidateStandlone(ONE);
	}

	public AssignableInvoiceCandidate createAssignableCandidateStandlone(@NonNull final BigDecimal quantityAsBigDecimal)
	{
		final I_C_Invoice_Candidate invoiceCandidateRecord = createAssignableInvoiceCandidateRecord(quantityAsBigDecimal);

		final Money money = Money.of(TEN, getCurrencyId());

		return AssignableInvoiceCandidate
				.builder()
				.id(InvoiceCandidateId.ofRepoId(invoiceCandidateRecord.getC_Invoice_Candidate_ID()))
				.bpartnerLocationId(billBPartnerLocationId)
				.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.money(money)
				.precision(currency.getPrecision().toInt())
				.invoiceableFrom(ASSIGNABLE_CANDIDATE_INVOICE_DATE)
				.quantity(Quantity.of(quantityAsBigDecimal, uomRecord))
				.build();
	}

	public AssignableInvoiceCandidate createAssignableCandidateWithAssignment()
	{
		final AssignableInvoiceCandidate assignableInvoiceCandidate = createAssignableCandidateStandlone();
		final RefundInvoiceCandidate refundCandidate = createRefundCandidate();

		final RefundConfig refundConfig = singleElement(refundCandidate.getRefundConfigs());

		final I_C_Invoice_Candidate_Assignment assignmentRecord = InterfaceWrapperHelper.newInstance(I_C_Invoice_Candidate_Assignment.class);
		assignmentRecord.setC_Invoice_Candidate_Term_ID(refundCandidate.getId().getRepoId());
		assignmentRecord.setC_Invoice_Candidate_Assigned_ID(assignableInvoiceCandidate.getId().getRepoId());
		assignmentRecord.setC_Flatrate_Term_ID(refundCandidate.getRefundContract().getId().getRepoId());
		assignmentRecord.setC_Flatrate_RefundConfig_ID(refundConfig.getId().getRepoId());
		assignmentRecord.setIsAssignedQuantityIncludedInSum(true);
		assignmentRecord.setAssignedMoneyAmount(TWO);
		assignmentRecord.setAssignedQuantity(ONE);
		saveRecord(assignmentRecord);

		final RefundInvoiceCandidate reloadedRefundCandidate = refundInvoiceCandidateRepository.getById(refundCandidate.getId());

		final AssignmentToRefundCandidate assignementToRefundCandidate = new AssignmentToRefundCandidate(
				refundConfig.getId(),
				assignableInvoiceCandidate.getId(),
				reloadedRefundCandidate,
				assignableInvoiceCandidate.getMoney(),
				Money.of(TWO, reloadedRefundCandidate.getMoney().getCurrencyId()),
				Quantity.of(ONE, uomRecord),
				true /* useAssignedQtyInSum */);
		return assignableInvoiceCandidate
				.toBuilder()
				.assignmentToRefundCandidate(assignementToRefundCandidate)
				.build();
	}

	public I_C_Invoice_Candidate createAssignableInvoiceCandidateRecord(@NonNull final BigDecimal quantityToInvoice)
	{

		final I_C_Invoice_Candidate invoiceCandidateRecord = InterfaceWrapperHelper.newInstance(I_C_Invoice_Candidate.class);
		invoiceCandidateRecord.setIsSOTrx(true); // pls keep in sync with C_DocType that we create in this class's constructor
		invoiceCandidateRecord.setNetAmtInvoiced(ONE);
		invoiceCandidateRecord.setNetAmtToInvoice(NINE);
		invoiceCandidateRecord.setC_Currency_ID(getCurrencyId().getRepoId());
		invoiceCandidateRecord.setQtyToInvoice(quantityToInvoice);
		invoiceCandidateRecord.setDateToInvoice(TimeUtil.asTimestamp(ASSIGNABLE_CANDIDATE_INVOICE_DATE));
		invoiceCandidateRecord.setBill_BPartner_ID(billBPartnerLocationId.getBpartnerId().getRepoId());
		invoiceCandidateRecord.setBill_Location_ID(billBPartnerLocationId.getRepoId());

		invoiceCandidateRecord.setM_Product_ID(productRecord.getM_Product_ID());
		saveRecord(invoiceCandidateRecord);
		return invoiceCandidateRecord;
	}

	public RefundConfigBuilder createAndInitConfigBuilder()
	{
		final RefundConfigBuilder refundConfigBuilder = RefundConfig
				.builder()
				.minQty(ZERO)
				.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
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

	public static ImmutableList<AssignmentToRefundCandidate> retrieveAllAssignmentsToRefundCandidates(
			@NonNull final AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository)
	{
		final List<I_C_Invoice_Candidate_Assignment> assignmentRecords = POJOLookupMap.get().getRecords(I_C_Invoice_Candidate_Assignment.class);
		final ImmutableList.Builder<AssignmentToRefundCandidate> result = ImmutableList.builder();
		for (final I_C_Invoice_Candidate_Assignment assignmentRecord : assignmentRecords)
		{
			result.add(assignmentToRefundCandidateRepository.ofRecordOrNull(assignmentRecord));
		}
		return result.build();
	}
}
