/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.cucumber.stepdefs.allocation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.allocation.api.IAllocationBL;
import de.metas.banking.payment.paymentallocation.InvoiceToAllocate;
import de.metas.banking.payment.paymentallocation.InvoiceToAllocateQuery;
import de.metas.banking.payment.paymentallocation.PaymentAllocationRepository;
import de.metas.banking.payment.paymentallocation.PaymentToAllocate;
import de.metas.banking.payment.paymentallocation.PaymentToAllocateQuery;
import de.metas.banking.payment.paymentallocation.service.AllocationAmounts;
import de.metas.banking.payment.paymentallocation.service.AllocationLineCandidate;
import de.metas.banking.payment.paymentallocation.service.PayableDocument;
import de.metas.banking.payment.paymentallocation.service.PayableDocument.PayableDocumentBuilder;
import de.metas.banking.payment.paymentallocation.service.PaymentAllocationBuilder;
import de.metas.banking.payment.paymentallocation.service.PaymentAllocationResult;
import de.metas.banking.payment.paymentallocation.service.PaymentDocument;
import de.metas.bpartner.BPartnerId;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.allocation.C_AllocationHdr_StepDefData;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.payment.C_Payment_StepDefData;
import de.metas.invoice.InvoiceAmtMultiplier;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingContext;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingFeeCalculation;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingFeeComputeRequest;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingFeeWithPrecalculatedAmountRequest;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingServiceCompanyService;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentAmtMultiplier;
import de.metas.payment.PaymentId;
import de.metas.common.util.time.SystemTime;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_Invoice_ID;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_Payment_ID;

/**
 * Step definitions for allocating payments to invoices.
 * <p>
 * The step {@code allocate payments to invoices} derives the invoice-processing service fee automatically for every sales invoice
 * that has a service-company config; no fee column is needed in the data table.
 * The optional column {@code InvoiceProcessing.C_BPartner_ID} only asserts the expected service company.
 */
@RequiredArgsConstructor
public class AllocatePayments_StepDef
{
	private static final String WRITE_OFF_PROCESS = "WRITEOFF";
	private static final String DISCOUNT_PROCESS = "DISCOUNT";

	private final PaymentAllocationRepository paymentAllocationRepository = SpringContextHolder.instance.getBean(PaymentAllocationRepository.class);
	private final InvoiceProcessingServiceCompanyService invoiceProcessingServiceCompanyService = SpringContextHolder.instance.getBean(InvoiceProcessingServiceCompanyService.class);
	private final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);

	private final IAllocationBL allocationBL = Services.get(IAllocationBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

	@NonNull private final C_Payment_StepDefData paymentTable;
	@NonNull private final C_Invoice_StepDefData invoiceTable;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;
	@NonNull private final C_AllocationHdr_StepDefData allocationHdrTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@And("^apply (.*) to invoices$")
	public void apply_write_off_or_discount_to_invoice(final String processToApply, @NonNull final DataTable table)
	{
		DataTableRows.of(table).forEach(row -> {
			final I_C_Invoice invoice = row.getAsIdentifier(COLUMNNAME_C_Invoice_ID).lookupNotNullIn(invoiceTable);

			final InvoiceToAllocate invoiceToAllocate = getInvoiceToAllocate(invoice);

			final InvoiceAmtMultiplier amtMultiplier = invoiceToAllocate.getMultiplier();
			final Money amountToDiscountOrWriteOff = amtMultiplier.convertToRealValue(invoiceToAllocate.getOpenAmountConverted())
					.toMoney(moneyService::getCurrencyIdByCurrencyCode);

			trxManager.runInThreadInheritedTrx(() -> allocationBL.invoiceDiscountAndWriteOff(
					IAllocationBL.InvoiceDiscountAndWriteOffRequest.builder()
							.invoice(invoice)
							.dateTrx(Instant.now())
							.discountAmt(processToApply.equals(DISCOUNT_PROCESS) ? amountToDiscountOrWriteOff : null)
							.writeOffAmt(processToApply.equals(WRITE_OFF_PROCESS) ? amountToDiscountOrWriteOff : null)
							.build()));
		});
	}

	/**
	 * Allocates the payments to the invoices of the data table in one {@link PaymentAllocationBuilder} run.
	 * <p>
	 * Required columns: {@code C_Payment_ID} (payment identifier), {@code C_Invoice_ID} (invoice identifier).
	 * Optional: {@code InvoiceProcessing.C_Invoice_ID} registers the generated service invoice under that identifier;
	 * {@code InvoiceProcessing.C_BPartner_ID} asserts the expected service company.
	 * <p>
	 * Payments are read first, because the service company and the fee date are derived from them, like in the WebUI payment allocation.
	 * For every sales invoice with a service-company config the service fee is computed automatically.
	 * <pre>
	 * And allocate payments to invoices
	 *   | C_Payment_ID | C_Invoice_ID | InvoiceProcessing.C_Invoice_ID |
	 *   | pay1         | inv1         | svcInv1                        |
	 * </pre>
	 * @see PaymentAllocationBuilder
	 */
	@And("allocate payments to invoices")
	public void allocate_payment_to_invoice(@NonNull final DataTable table)
	{
		final ArrayList<PayableDocument> payableDocuments = new ArrayList<>();
		final ArrayList<PaymentDocument> paymentDocuments = new ArrayList<>();

		// payments first: the service company (and the fee date) is derived from the payment, like in the WebUI payment allocation
		DataTableRows.of(table).forEach(row -> row.getAsOptionalIdentifier(COLUMNNAME_C_Payment_ID)
				.map(this::buildPaymentDocument)
				.ifPresent(paymentDocuments::add));

		DataTableRows.of(table).forEach(row -> row.getAsOptionalIdentifier(COLUMNNAME_C_Invoice_ID)
				.map(invoiceIdentifier -> buildPayableDocument(invoiceIdentifier, row, paymentDocuments))
				.ifPresent(payableDocuments::add));

		final PaymentAllocationBuilder paymentAllocationBuilder = PaymentAllocationBuilder.newBuilder()
				.invoiceProcessingServiceCompanyService(invoiceProcessingServiceCompanyService)
				.defaultDateTrx(LocalDate.now())
				.paymentDocuments(paymentDocuments)
				.payableDocuments(payableDocuments)
				.allowPartialAllocations(true)
				.payableRemainingOpenAmtPolicy(PaymentAllocationBuilder.PayableRemainingOpenAmtPolicy.DO_NOTHING)
				.allowPurchaseSalesInvoiceCompensation(paymentDocuments.isEmpty() && payableDocuments.size() > 1);

		final boolean isInvoiceProcessingFee = payableDocuments.stream().anyMatch(payableDocument -> payableDocument.getInvoiceProcessingFeeCalculation() != null);
		if (isInvoiceProcessingFee)
		{
			paymentAllocationBuilder.allowInvoiceToCreditMemoAllocation(false); // invoices and creditmemos of the remadv must not be allocated against each other!
			paymentAllocationBuilder.allocatePayableAmountsAsIs(true); // no min/max computations! the sums will match in the end
		}

		final PaymentAllocationResult result = paymentAllocationBuilder.build();

		DataTableRows.of(table).forEach(row -> updateServiceInvoiceIdentifier(row, result));
	}

	/**
	 * Registers the {@link I_C_AllocationHdr} generated for a completed {@link I_C_Payment}
	 * into {@link C_AllocationHdr_StepDefData} so later steps can reference it by identifier.
	 * Intended for scenarios where the allocation is built indirectly via
	 * {@link PaymentAllocationBuilder} (path P) and no allocation identifier is otherwise
	 * known. Looks up the C_AllocationHdr via the C_AllocationLine that carries this payment.
	 *
	 * <p><b>Required columns</b>:
	 * <ul>
	 *     <li>{@code C_Payment_ID} — identifier of a previously registered payment</li>
	 *     <li>{@code C_AllocationHdr_ID} — identifier under which the looked-up allocation hdr is registered</li>
	 * </ul>
	 *
	 * <p><b>Gherkin usage example</b>:
	 * <pre>{@code
	 * And register C_AllocationHdr from C_Payment:
	 *   | C_Payment_ID | C_AllocationHdr_ID |
	 *   | payment      | alloc              |
	 * }</pre>
	 */
	@And("register C_AllocationHdr from C_Payment:")
	public void registerAllocationHdrFromPayment(@NonNull final DataTable table)
	{
		DataTableRows.of(table).forEach(row -> {
			final StepDefDataIdentifier allocIdentifier = row.getAsIdentifier("C_AllocationHdr_ID");
			final I_C_Payment payment = paymentTable.get(row.getAsIdentifier(COLUMNNAME_C_Payment_ID));

			final List<I_C_AllocationLine> allocLines = queryBL.createQueryBuilder(I_C_AllocationLine.class)
					.addEqualsFilter(I_C_AllocationLine.COLUMNNAME_C_Payment_ID, payment.getC_Payment_ID())
					.addOnlyActiveRecordsFilter()
					.orderBy(I_C_AllocationLine.COLUMNNAME_C_AllocationLine_ID)
					.create()
					.list();

			final ImmutableSet<Integer> distinctHdrIds = allocLines.stream()
					.map(I_C_AllocationLine::getC_AllocationHdr_ID)
					.collect(ImmutableSet.toImmutableSet());

			assertThat(distinctHdrIds)
					.as("Expected exactly one C_AllocationHdr linked to payment C_Payment_ID=%s via its C_AllocationLine(s). "
							+ "Found %s distinct hdr id(s) across %s allocation line(s): %s",
							payment.getC_Payment_ID(), distinctHdrIds.size(), allocLines.size(), distinctHdrIds)
					.hasSize(1);

			final I_C_AllocationHdr hdr = InterfaceWrapperHelper.load(distinctHdrIds.iterator().next(), I_C_AllocationHdr.class);
			allocationHdrTable.putOrReplace(allocIdentifier, hdr);
		});
	}

	private void updateServiceInvoiceIdentifier(final DataTableRow row, final PaymentAllocationResult result)
	{
		final StepDefDataIdentifier serviceInvoiceIdentifier = row.getAsOptionalIdentifier("InvoiceProcessing.C_Invoice_ID").orElse(null);
		if (serviceInvoiceIdentifier == null)
		{
			return;
		}

		final InvoiceId invoiceId = row.getAsIdentifier(COLUMNNAME_C_Invoice_ID).lookupNotNullIdIn(invoiceTable);
		final BPartnerId serviceCompanyBPartnerId = getServiceCompanyBPartnerId(row);
		final I_C_Invoice serviceInvoice = getProcessingFeeServiceInvoiceId(result, invoiceId, serviceCompanyBPartnerId)
				.orElseThrow(() -> new AdempiereException("No processing fee service invoice found for invoiceId=" + invoiceId)
						.setParameter("allocationResult", result)
						.appendParametersToMessage());

		invoiceTable.putOrReplaceIfSameId(serviceInvoiceIdentifier, serviceInvoice);
	}

	private Optional<I_C_Invoice> getProcessingFeeServiceInvoiceId(
			@NonNull final PaymentAllocationResult result,
			@NonNull final InvoiceId invoiceId,
			@NonNull final BPartnerId serviceBPartnerId)
	{
		for (final AllocationLineCandidate candidate : result.getPaymentAllocationIds().values())
		{
			final TableRecordReference payableDocumentRef = candidate.getPayableDocumentRef();
			if (!payableDocumentRef.tableNameEqualsTo(org.adempiere.banking.model.I_C_Invoice.Table_Name)
					|| payableDocumentRef.getRecord_ID() != invoiceId.getRepoId())
			{
				continue;
			}

			final TableRecordReference paymentDocumentRef = candidate.getPaymentDocumentRef();
			if (paymentDocumentRef == null
					|| !paymentDocumentRef.tableNameEqualsTo(I_C_Invoice.Table_Name))
			{
				continue;
			}

			final InvoiceId serviceInvoiceId = paymentDocumentRef.getIdAssumingTableName(I_C_Invoice.Table_Name, InvoiceId::ofRepoId);
			final I_C_Invoice serviceInvoice = invoiceBL.getById(serviceInvoiceId);
			if (!BPartnerId.equals(BPartnerId.ofRepoId(serviceInvoice.getC_BPartner_ID()), serviceBPartnerId))
			{
				continue;
			}

			return Optional.of(serviceInvoice);
		}

		return Optional.empty();
	}


	/**
	 * The service company is the business partner of the payment (it pays on behalf of its customers).
	 * The optional column {@code InvoiceProcessing.C_BPartner_ID} only asserts that expectation.
	 */
	@NonNull
	private BPartnerId getServiceCompanyBPartnerId(final @NonNull DataTableRow row)
	{
		final I_C_Payment payment = paymentTable.get(row.getAsIdentifier(COLUMNNAME_C_Payment_ID));
		final BPartnerId serviceCompanyBPartnerId = BPartnerId.ofRepoId(payment.getC_BPartner_ID());

		row.getAsOptionalIdentifier("InvoiceProcessing.C_BPartner_ID")
				.map(identifier -> identifier.lookupNotNullIdIn(bpartnerTable))
				.ifPresent(expected -> assertThat(serviceCompanyBPartnerId)
						.as("service company derived from the payment")
						.isEqualTo(expected));

		return serviceCompanyBPartnerId;
	}

	@And("^allocate invoices \\(credit memo/purchase\\) to invoices$")
	public void allocate_credit_memo_to_invoice(@NonNull final DataTable table)
	{
		final ArrayList<PayableDocument> payableDocuments = new ArrayList<>();

		DataTableRows.of(table).forEach(row -> {
			row.getAsOptionalIdentifier("C_Invoice_ID")
					.map(invoiceIdentifier -> buildPayableDocument(invoiceIdentifier, row, null))
					.ifPresent(payableDocuments::add);
			row.getAsOptionalIdentifier("CreditMemo.C_Invoice_ID")
					.map(invoiceIdentifier -> buildPayableDocument(invoiceIdentifier, row, null))
					.ifPresent(payableDocuments::add);
			row.getAsOptionalIdentifier("Purchase.C_Invoice_ID")
					.map(invoiceIdentifier -> buildPayableDocument(invoiceIdentifier, row, null))
					.ifPresent(payableDocuments::add);
		});

		PaymentAllocationBuilder.newBuilder()
				.invoiceProcessingServiceCompanyService(invoiceProcessingServiceCompanyService)
				.defaultDateTrx(LocalDate.now())
				.payableDocuments(payableDocuments)
				.allowPartialAllocations(true)
				.allowPurchaseSalesInvoiceCompensation(true)
				.payableRemainingOpenAmtPolicy(PaymentAllocationBuilder.PayableRemainingOpenAmtPolicy.DO_NOTHING)
				.build();
	}

	@NonNull
	private PayableDocument buildPayableDocument(@NonNull final StepDefDataIdentifier invoiceIdentifier,
												 @NonNull final DataTableRow row,
												 @Nullable final List<PaymentDocument> paymentDocumentsForServiceFee)
	{
		return preparePayableDocument(invoiceIdentifier, row, paymentDocumentsForServiceFee).build();
	}

	@NonNull
	private PayableDocumentBuilder preparePayableDocument(@NonNull final StepDefDataIdentifier invoiceIdentifier,
														  @NonNull final DataTableRow row,
														  @Nullable final List<PaymentDocument> paymentDocumentsForServiceFee)
	{
		final I_C_Invoice invoice = invoiceTable.get(invoiceIdentifier);

		final InvoiceToAllocate invoiceToAllocate = getInvoiceToAllocate(invoice);
		final Money invoiceOpenMoneyAmt = moneyService.toMoney(invoiceToAllocate.getOpenAmountConverted());
		Money payAmt = invoiceOpenMoneyAmt;
		//
		// Discount
		Money discountAmt = row.getAsOptionalMoney("DiscountAmt", moneyService::getCurrencyIdByCurrencyCode).orElse(null);
		if (discountAmt == null)
		{
			discountAmt = moneyService.toMoney(invoiceToAllocate.getDiscountAmountConverted());
		}
		if (discountAmt != null)
		{
			payAmt = payAmt.subtract(discountAmt);
		}


		//
		// Service company fee
		final InvoiceProcessingFeeCalculation invoiceProcessingFeeCalculation = paymentDocumentsForServiceFee != null
				? computeInvoiceProcessingFee(invoiceToAllocate, paymentDocumentsForServiceFee).orElse(null)
				: null;
		Money invoiceProcessingFee = null;
		if (invoiceProcessingFeeCalculation != null)
		{
			invoiceProcessingFee = moneyService.toMoney(invoiceProcessingFeeCalculation.getFeeAmountIncludingTax());
			payAmt = payAmt.subtract(invoiceProcessingFee);
		}

		final AllocationAmounts amounts = AllocationAmounts.builder()
				.payAmt(payAmt)
				.discountAmt(discountAmt)
				.invoiceProcessingFee(invoiceProcessingFee)
				.build();

		return PayableDocument.builder()
				.invoiceProcessingFeeCalculation(invoiceProcessingFeeCalculation)
				.invoiceId(invoiceToAllocate.getInvoiceId())
				.bpartnerId(invoiceToAllocate.getBpartnerId())
				.documentNo(invoiceToAllocate.getDocumentNo())
				.soTrx(invoiceToAllocate.getDocBaseType().getSoTrx())
				.creditMemo(invoiceToAllocate.getDocBaseType().isCreditMemo())
				.openAmt(invoiceOpenMoneyAmt.negateIf(!invoice.isSOTrx()))
				.date(invoiceToAllocate.getDateInvoiced())
				.dateAcct(invoiceToAllocate.getDateAcct())
				.clientAndOrgId(invoiceToAllocate.getClientAndOrgId())
				.currencyConversionTypeId(invoiceToAllocate.getCurrencyConversionTypeId())
				.amountsToAllocate(amounts.convertToRealAmounts(invoiceToAllocate.getMultiplier()));
	}

	private Optional<InvoiceProcessingFeeCalculation> computeInvoiceProcessingFee(
			@NonNull final InvoiceToAllocate invoiceToAllocate,
			@NonNull final List<PaymentDocument> paymentDocuments)
	{
		if (!invoiceToAllocate.getDocBaseType().isSales())
		{
			return Optional.empty();
		}

		final ZonedDateTime evaluationDate = SystemTime.asZonedDateTime();
		final Optional<InvoiceProcessingFeeCalculation> computedFee = invoiceProcessingServiceCompanyService.computeFee(InvoiceProcessingFeeComputeRequest.builder()
				.orgId(invoiceToAllocate.getClientAndOrgId().getOrgId())
				.evaluationDate(evaluationDate)
				.customerId(invoiceToAllocate.getBpartnerId())
				.docTypeId(invoiceToAllocate.getDocTypeId())
				.invoiceId(invoiceToAllocate.getInvoiceId())
				.invoiceGrandTotal(invoiceToAllocate.getGrandTotal())
				.serviceInvoiceWasAlreadyGenerated(OptionalBoolean.UNKNOWN) // like production: let computeFee check for an existing service invoice
				.build());
		if (!computedFee.isPresent() || computedFee.get().getFeeAmountIncludingTax().isZero())
		{
			return Optional.empty();
		}

		final BPartnerId customerId = invoiceToAllocate.getBpartnerId();
		final InvoiceProcessingContext context = invoiceProcessingServiceCompanyService.extractInvoiceProcessingContext(
				customerId,
				paymentDocuments.stream()
						.map(payment -> InvoiceProcessingContext.of(payment.getBpartnerId(), TimeUtil.asZonedDateTime(payment.getDateTrx())))
						.collect(ImmutableList.toImmutableList()),
				() -> new AdempiereException("No service company config found for customer " + customerId));
		final BPartnerId serviceCompanyBPartnerId = context.getServiceCompanyId();

		return Optional.of(invoiceProcessingServiceCompanyService.createFeeCalculationForPayment(InvoiceProcessingFeeWithPrecalculatedAmountRequest.builder()
						.orgId(invoiceToAllocate.getClientAndOrgId().getOrgId())
						.paymentDate(context.getPaymentDate())
						.customerId(invoiceToAllocate.getBpartnerId())
						.invoiceId(invoiceToAllocate.getInvoiceId())
						.feeAmountIncludingTax(computedFee.get().getFeeAmountIncludingTax())
						.serviceCompanyBPartnerId(serviceCompanyBPartnerId)
						.build())
				.orElseThrow(() -> new AdempiereException("Cannot find service company " + serviceCompanyBPartnerId + " for customer " + invoiceToAllocate.getBpartnerId())));
	}

	@NonNull
	private InvoiceToAllocate getInvoiceToAllocate(@NonNull final I_C_Invoice invoice)
	{
		final ZoneId timeZone = orgDAO.getTimeZone(OrgId.ofRepoId(invoice.getAD_Org_ID()));
		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());
		final List<InvoiceToAllocate> invoiceToAllocateList = paymentAllocationRepository.retrieveInvoicesToAllocate(
				InvoiceToAllocateQuery.builder()
						.evaluationDate(invoice.getDateInvoiced().toLocalDateTime().atZone(timeZone))
						.onlyInvoiceId(invoiceId)
						.build()
		);

		assertThat(invoiceToAllocateList)
				.as("There should be just one 'InvoiceToAllocate' for a given C_Invoice_ID")
				.hasSize(1);

		return invoiceToAllocateList.get(0);
	}

	@NonNull
	private PaymentDocument buildPaymentDocument(@NonNull final StepDefDataIdentifier paymentIdentifier)
	{
		final I_C_Payment payment = paymentTable.get(paymentIdentifier);

		assertThat(payment).isNotNull();

		final PaymentToAllocate paymentToAllocate = getPaymentToAllocate(payment);

		final PaymentAmtMultiplier amtMultiplier = paymentToAllocate.getPaymentAmtMultiplier();

		final Money openAmt = amtMultiplier.convertToRealValue(paymentToAllocate.getOpenAmt())
				.toMoney(moneyService::getCurrencyIdByCurrencyCode);

		return PaymentDocument.builder()
				.paymentId(paymentToAllocate.getPaymentId())
				.bpartnerId(paymentToAllocate.getBpartnerId())
				.documentNo(paymentToAllocate.getDocumentNo())
				.paymentDirection(paymentToAllocate.getPaymentDirection())
				.openAmt(openAmt)
				.amountToAllocate(openAmt)
				.dateTrx(paymentToAllocate.getDateTrx())
				.dateAcct(paymentToAllocate.getDateAcct())
				.clientAndOrgId(paymentToAllocate.getClientAndOrgId())
				.paymentCurrencyContext(paymentToAllocate.getPaymentCurrencyContext())
				.build();
	}

	@NonNull
	private PaymentToAllocate getPaymentToAllocate(@NonNull final I_C_Payment payment)
	{
		final PaymentToAllocateQuery query = PaymentToAllocateQuery.builder()
				.evaluationDate(ZonedDateTime.now())
				.additionalPaymentIdToInclude(PaymentId.ofRepoId(payment.getC_Payment_ID()))
				.build();

		final List<PaymentToAllocate> paymentToAllocateList = paymentAllocationRepository.retrievePaymentsToAllocate(query);

		assertThat(paymentToAllocateList)
				.as("There should be just one 'PaymentToAllocate' for a given C_Payment_ID")
				.hasSize(1);

		return paymentToAllocateList.get(0);
	}
}
