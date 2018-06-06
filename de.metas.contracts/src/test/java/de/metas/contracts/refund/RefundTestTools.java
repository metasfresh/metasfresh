package de.metas.contracts.refund;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.bpartner.BPartnerId;
import org.adempiere.util.Services;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.X_C_InvoiceSchedule;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.model.I_C_Currency;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Invoice_Candidate_Assignment;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.refund.RefundConfig.RefundInvoiceType;
import de.metas.invoice.InvoiceSchedule;
import de.metas.invoice.InvoiceSchedule.Frequency;
import de.metas.invoice.InvoiceScheduleId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lang.Percent;
import de.metas.money.Currency;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.product.ProductId;
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
	private static final BPartnerId BPARTNER_ID = BPartnerId.ofRepoId(10);
	private static final BigDecimal TWENTY = new BigDecimal("20");
	private static final BigDecimal NINE = new BigDecimal("9");
	private static final BigDecimal TWO = new BigDecimal("2");
	private static final BigDecimal HUNDRED = new BigDecimal("100");

	private static final LocalDate ASSIGNABLE_CANDIDATE_INVOICE_DATE = LocalDate.now();
	public static final LocalDate CONTRACT_START_DATE = ASSIGNABLE_CANDIDATE_INVOICE_DATE.minusDays(2);
	public static final LocalDate CONTRACT_END_DATE = ASSIGNABLE_CANDIDATE_INVOICE_DATE.plusDays(2);

	private static final ProductId PRODUCT_ID = ProductId.ofRepoId(20);

	private final Currency currency;

	public RefundTestTools()
	{

		final I_C_Currency currencyRecord = newInstance(I_C_Currency.class);
		currencyRecord.setStdPrecision(2);
		saveRecord(currencyRecord);

		currency = Currency.builder()
				.id(CurrencyId.ofRepoId(currencyRecord.getC_Currency_ID()))
				.precision(2)
				.build();

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
		return createRefundCandidate(HUNDRED);
	}

	public RefundInvoiceCandidate createRefundCandidate(@NonNull final BigDecimal moneyValue)
	{
		final RefundContract refundContract = createRefundContract();

		final LocalDate invoiceableFromDate = ASSIGNABLE_CANDIDATE_INVOICE_DATE.plusDays(1);

		final I_C_Invoice_Candidate invoiceCandidateRecord = newInstance(I_C_Invoice_Candidate.class);
		invoiceCandidateRecord.setM_Product_ID(PRODUCT_ID.getRepoId());
		invoiceCandidateRecord.setPriceActual(moneyValue);
		invoiceCandidateRecord.setC_Currency_ID(currency.getId().getRepoId());
		invoiceCandidateRecord.setDateToInvoice(TimeUtil.asTimestamp(invoiceableFromDate));
		invoiceCandidateRecord.setRecord_ID(refundContract.getId().getRepoId());
		invoiceCandidateRecord.setAD_Table_ID(getTableId(I_C_Flatrate_Term.class));
		invoiceCandidateRecord.setBill_BPartner_ID(BPARTNER_ID.getRepoId());
		invoiceCandidateRecord.setProcessed(false);
		saveRecord(invoiceCandidateRecord);

		final Money money = Money.of(moneyValue, currency);

		return RefundInvoiceCandidate.builder()
				.id(InvoiceCandidateId.ofRepoId(invoiceCandidateRecord.getC_Invoice_Candidate_ID()))
				.bpartnerId(BPARTNER_ID)
				.refundContract(refundContract)
				.money(money)
				.invoiceableFrom(invoiceableFromDate)
				.build();
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
		refundConfigRecord.setM_Product_ID(PRODUCT_ID.getRepoId());
		refundConfigRecord.setRefundInvoiceType(X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Creditmemo);
		refundConfigRecord.setC_InvoiceSchedule(invoiceScheduleRecord);
		refundConfigRecord.setPercent(TWENTY);
		saveRecord(refundConfigRecord);

		final I_C_Flatrate_Term contractRecord = newInstance(I_C_Flatrate_Term.class);
		contractRecord.setC_Flatrate_Conditions(conditions);
		contractRecord.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Refund);
		contractRecord.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Completed);
		contractRecord.setBill_BPartner_ID(BPARTNER_ID.getRepoId());
		contractRecord.setM_Product_ID(PRODUCT_ID.getRepoId());
		contractRecord.setStartDate(TimeUtil.asTimestamp(CONTRACT_START_DATE));
		contractRecord.setEndDate(TimeUtil.asTimestamp(CONTRACT_END_DATE));
		saveRecord(contractRecord);

		final InvoiceSchedule invoiceSchedule = InvoiceSchedule
				.builder()
				.id(InvoiceScheduleId.ofRepoId(invoiceScheduleRecord.getC_InvoiceSchedule_ID()))
				.frequency(Frequency.DAILY)
				.build();

		final RefundConfig refundConfig = RefundConfig
				.builder()
				.productId(PRODUCT_ID)
				.percent(Percent.of(TWENTY))
				.conditionsId(ConditionsId.ofRepoId(contractRecord.getC_Flatrate_Conditions_ID()))
				.invoiceSchedule(invoiceSchedule)
				.refundInvoiceType(RefundInvoiceType.CREDITMEMO)
				.build();

		final RefundContract refundContract = RefundContract.builder()
				.id(FlatrateTermId.ofRepoId(contractRecord.getC_Flatrate_Term_ID()))
				.refundConfig(refundConfig)
				.startDate(CONTRACT_START_DATE)
				.endDate(CONTRACT_END_DATE)
				.build();
		return refundContract;
	}

	public AssignableInvoiceCandidate createAssignableCandidateStandlone()
	{
		final I_C_Invoice_Candidate invoiceCandidateRecord = createAssignableInvoiceCandidateRecord();

		final Money money = Money.of(TEN, currency);

		return AssignableInvoiceCandidate
				.builder()
				.id(InvoiceCandidateId.ofRepoId(invoiceCandidateRecord.getC_Invoice_Candidate_ID()))
				.bpartnerId(BPARTNER_ID)
				.productId(PRODUCT_ID)
				.money(money)
				.invoiceableFrom(ASSIGNABLE_CANDIDATE_INVOICE_DATE)
				.build();
	}

	public AssignableInvoiceCandidate createAssignableCandidateWithAssignment()
	{
		final AssignableInvoiceCandidate assignableInvoiceCandidate = createAssignableCandidateStandlone();
		final RefundInvoiceCandidate refundCandidate = createRefundCandidate(HUNDRED.add(TWO));

		final I_C_Invoice_Candidate_Assignment assignmentRecord = newInstance(I_C_Invoice_Candidate_Assignment.class);
		assignmentRecord.setC_Invoice_Candidate_Term_ID(refundCandidate.getId().getRepoId());
		assignmentRecord.setC_Invoice_Candidate_Assigned_ID(assignableInvoiceCandidate.getId().getRepoId());
		assignmentRecord.setC_Flatrate_Term_ID(refundCandidate.getRefundContract().getId().getRepoId());
		assignmentRecord.setAssignedAmount(TWO);
		saveRecord(assignmentRecord);

		final AssignmentToRefundCandidate assignementToRefundCandidate = new AssignmentToRefundCandidate(
				refundCandidate,
				Money.of(TWO, refundCandidate.getMoney().getCurrency()));
		return assignableInvoiceCandidate
				.toBuilder()
				.assignmentToRefundCandidate(assignementToRefundCandidate)
				.build();
	}

	public I_C_Invoice_Candidate createAssignableInvoiceCandidateRecord()
	{
		final I_C_Invoice_Candidate invoiceCandidateRecord = newInstance(I_C_Invoice_Candidate.class);
		invoiceCandidateRecord.setNetAmtInvoiced(ONE);
		invoiceCandidateRecord.setNetAmtToInvoice(NINE);
		invoiceCandidateRecord.setC_Currency_ID(currency.getId().getRepoId());
		invoiceCandidateRecord.setDateToInvoice(TimeUtil.asTimestamp(ASSIGNABLE_CANDIDATE_INVOICE_DATE));
		invoiceCandidateRecord.setBill_BPartner_ID(BPARTNER_ID.getRepoId());
		invoiceCandidateRecord.setM_Product_ID(PRODUCT_ID.getRepoId());
		saveRecord(invoiceCandidateRecord);
		return invoiceCandidateRecord;
	}
}
