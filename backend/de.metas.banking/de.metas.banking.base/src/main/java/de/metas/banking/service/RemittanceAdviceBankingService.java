/*
 * #%L
 * de.metas.banking.base
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

package de.metas.banking.service;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.banking.BankAccountId;
import de.metas.banking.payment.paymentallocation.PaymentAllocationCriteria;
import de.metas.banking.payment.paymentallocation.PaymentAllocationPayableItem;
import de.metas.banking.payment.paymentallocation.service.AllocationLineCandidate;
import de.metas.banking.payment.paymentallocation.service.PaymentAllocationResult;
import de.metas.banking.payment.paymentallocation.service.PaymentAllocationService;
import de.metas.bpartner.BPartnerBankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.InvoiceAmtMultiplier;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingFeeCalculation;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.money.MoneyService;
import de.metas.organization.IOrgDAO;
import de.metas.payment.PaymentId;
import de.metas.payment.TenderType;
import de.metas.payment.api.DefaultPaymentBuilder;
import de.metas.payment.api.IPaymentBL;
import de.metas.product.ProductId;
import de.metas.remittanceadvice.RemittanceAdvice;
import de.metas.remittanceadvice.RemittanceAdviceLine;
import de.metas.remittanceadvice.RemittanceAdviceLineId;
import de.metas.remittanceadvice.RemittanceAdviceLineServiceFee;
import de.metas.remittanceadvice.RemittanceAdviceRepository;
import de.metas.remittanceadvice.RemittanceAdviceService;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RemittanceAdviceBankingService
{
	private static final Logger logger = LogManager.getLogger(RemittanceAdviceBankingService.class);

	private final PaymentAllocationService paymentAllocationService;
	private final RemittanceAdviceRepository remittanceAdviceRepo;
	private final RemittanceAdviceService remittanceAdviceService;
	private final MoneyService moneyService;
;
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);

	/**
	 * Proceses the given remittance-advice by
	 * <ul>
	 *     <li>creating and linking a payment</li>
	 *     <li>creating a service-fee-invoice for each remittance-advice line</li>
	 *     <li>allocating the payment, service-fee-invoices and remittance-advice lines' invoices against each other</li>
	 * </ul>
	 */
	public void createPaymentAndAllocations(@NonNull final RemittanceAdvice remittanceAdvice)
	{
		final boolean canBeProcessed = remittanceAdvice.getPaymentId() == null && DocStatus.ofCode(remittanceAdvice.getDocStatus()).isCompleted();
		if (!canBeProcessed)
		{
			Loggables.withLogger(logger, Level.INFO).addLog("Skipping Remittance Advice, RemittanceAdviceId={}, DocStatus={}, PaymentId={}",
					remittanceAdvice.getRemittanceAdviceId().getRepoId(), remittanceAdvice.getDocStatus(), remittanceAdvice.getPaymentId());
			return;
		}
		
		final I_C_Payment payment = createPayment(remittanceAdvice);

		final PaymentAllocationCriteria paymentAllocationCriteria = getPaymentAllocationCriteria(remittanceAdvice, payment);

		final PaymentAllocationResult paymentAllocationResult = paymentAllocationService.allocatePaymentForRemittanceAdvise(paymentAllocationCriteria);

		final Map<InvoiceId, RemittanceAdviceLine> remittanceAdviceLineMap = getRemittanceAdviceLinesByInvoiceId(remittanceAdvice.getLines());
		populateRemittanceWithAllocationData(remittanceAdviceLineMap, paymentAllocationResult);

		remittanceAdvice.setPaymentId(PaymentId.ofRepoId(payment.getC_Payment_ID()));

		remittanceAdviceRepo.updateRemittanceAdvice(remittanceAdvice);
	}

	@NonNull
	private I_C_Payment createPayment(@NonNull final RemittanceAdvice remittanceAdvice)
	{
		final DefaultPaymentBuilder paymentBuilder = remittanceAdvice.isSOTrx()
				? paymentBL.newInboundReceiptBuilder()
				: paymentBL.newOutboundPaymentBuilder();

		final BPartnerBankAccountId bPartnerBankAccountId = remittanceAdvice.isSOTrx() ? remittanceAdvice.getSourceBPartnerBankAccountId()
				: remittanceAdvice.getDestinationBPartnerBankAccountId();

		final ZoneId tz = orgDAO.getTimeZone(remittanceAdvice.getOrgId());

		return paymentBuilder
				.adOrgId(remittanceAdvice.getOrgId())
				.bpartnerId(remittanceAdvice.isSOTrx() ? remittanceAdvice.getSourceBPartnerId() : remittanceAdvice.getDestinationBPartnerId())
				.orgBankAccountId(BankAccountId.ofRepoId(bPartnerBankAccountId.getRepoId()))
				.currencyId(remittanceAdvice.getRemittedAmountCurrencyId())
				.payAmt(remittanceAdvice.getRemittedAmountSum())
				.dateAcct(CoalesceUtil.coalesce(TimeUtil.asLocalDate(remittanceAdvice.getPaymentDate(), tz),
						TimeUtil.asLocalDate(remittanceAdvice.getDocumentDate(), tz)))
				.dateTrx(TimeUtil.asLocalDate(remittanceAdvice.getDocumentDate(), tz))
				.tenderType(TenderType.DirectDeposit)
				.createAndProcess();
	}

	@NonNull
	private PaymentAllocationCriteria getPaymentAllocationCriteria(
			@NonNull final RemittanceAdvice remittanceAdvice,
			@NonNull final I_C_Payment payment)
	{
		final Map<InvoiceId, I_C_Invoice> invoiceMapById = getInvoiceMapById(remittanceAdvice.getLines());

		final List<PaymentAllocationPayableItem> paymentAllocationPayableItems = remittanceAdvice.getLines()
				.stream()
				.map(line -> createPaymentAllocationPayableItem(line, remittanceAdvice, invoiceMapById.get(line.getInvoiceId())))
				.collect(Collectors.toList());

		return PaymentAllocationCriteria.builder()
				.payment(payment)
				.dateTrx(remittanceAdvice.getSendDate() != null ? remittanceAdvice.getSendDate() : SystemTime.asInstant())
				.paymentAllocationPayableItems(paymentAllocationPayableItems)
				.allowPartialAllocations(true)
				.allowInvoiceToCreditMemoAllocation(false) // invoices and creditmemos of the remadv must not be allocated against each other!
				.build();
	}

	private void populateRemittanceWithAllocationData(
			@NonNull final Map<InvoiceId, RemittanceAdviceLine> remittanceAdviceLineMap,
			@NonNull final PaymentAllocationResult paymentAllocationResult)
	{
		final ImmutableSet<AllocationLineCandidate.AllocationLineCandidateType> validTypes =
				ImmutableSet.of(AllocationLineCandidate.AllocationLineCandidateType.SalesInvoiceToPurchaseInvoice,
						AllocationLineCandidate.AllocationLineCandidateType.SalesCreditMemoToPurchaseInvoice);

		final Map<Integer, InvoiceId> serviceFeeInvoiceIdsByAssignedInvoiceId =
				paymentAllocationResult
						.getPaymentAllocationIds()
						.values()
						.stream()
						.filter(paymentAllocationResultItem -> validTypes.contains(paymentAllocationResultItem.getType()))
						.collect(Collectors.toMap(paymentAllocationResultItem -> paymentAllocationResultItem.getPayableDocumentRef().getRecord_ID(),
								paymentAllocationResultItem -> InvoiceId.ofRepoId(paymentAllocationResultItem.getPaymentDocumentRef().getRecord_ID())));

		paymentAllocationResult.getCandidates()
				.stream()
				.filter(allocationLineCandidate -> AllocationLineCandidate.AllocationLineCandidateType.InvoiceProcessingFee.equals(allocationLineCandidate.getType()))
				.forEach(allocationLineCandidate -> {
					final InvoiceProcessingFeeCalculation invoiceProcessingFeeCalculation = allocationLineCandidate.getInvoiceProcessingFeeCalculation();
					if (invoiceProcessingFeeCalculation != null)
					{
						final RemittanceAdviceLine remittanceAdviceLine = remittanceAdviceLineMap.get(invoiceProcessingFeeCalculation.getInvoiceId());
						if (remittanceAdviceLine != null)
						{
							final InvoiceId serviceFeeInvoiceId = serviceFeeInvoiceIdsByAssignedInvoiceId.get(invoiceProcessingFeeCalculation.getInvoiceId().getRepoId());

							final RemittanceAdviceLineServiceFee remittanceAdviceLineServiceFee = getRemittanceAdviceLineServiceFee(serviceFeeInvoiceId);

							remittanceAdviceLine.setServiceFeeDetails(remittanceAdviceLineServiceFee);
						}
					}
				});
	}

	@NonNull
	private RemittanceAdviceLineServiceFee getRemittanceAdviceLineServiceFee(@NonNull final InvoiceId serviceFeeInvoiceId)
	{
		TaxId serviceFeeTaxId = null;
		ProductId serviceFeeProductId = null;
		BigDecimal serviceFeeTaxVATRate = null;
		final List<I_C_InvoiceLine> serviceFeeInvoiceLines = getInvoiceLines(serviceFeeInvoiceId);

		if (!serviceFeeInvoiceLines.isEmpty())
		{
			final I_C_InvoiceLine firstInvoiceLine = serviceFeeInvoiceLines.get(0);
			serviceFeeTaxId = TaxId.ofRepoId(firstInvoiceLine.getC_Tax_ID());
			serviceFeeProductId = ProductId.ofRepoIdOrNull(firstInvoiceLine.getM_Product_ID());

			final Tax serviceFeeTax = taxDAO.getTaxById(serviceFeeTaxId.getRepoId());
			serviceFeeTaxVATRate = serviceFeeTax.getRate();
		}

		final I_C_Invoice serviceFeeInvoice = invoiceDAO.getByIdInTrx(serviceFeeInvoiceId);

		return RemittanceAdviceLineServiceFee.builder()
				.serviceFeeInvoiceId(serviceFeeInvoiceId)
				.serviceProductId(serviceFeeProductId)
				.serviceBPartnerId(BPartnerId.ofRepoId(serviceFeeInvoice.getC_BPartner_ID()))
				.serviceFeeTaxId(serviceFeeTaxId)
				.serviceVatRate(serviceFeeTaxVATRate)
				.build();
	}

	private Map<InvoiceId, RemittanceAdviceLine> getRemittanceAdviceLinesByInvoiceId(final List<RemittanceAdviceLine> remittanceAdviceLines)
	{
		return remittanceAdviceLines
				.stream()
				.filter(remittanceAdviceLine -> remittanceAdviceLine.getInvoiceId() != null)
				.collect(Collectors.toMap(RemittanceAdviceLine::getInvoiceId, remittanceAdviceLine -> remittanceAdviceLine));
	}

	@NonNull
	private Map<InvoiceId, I_C_Invoice> getInvoiceMapById(@NonNull final List<RemittanceAdviceLine> remittanceAdviceLines)
	{
		final ImmutableSet<InvoiceId> invoiceIds = remittanceAdviceLines
				.stream()
				.map(RemittanceAdviceLine::getInvoiceId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		return getInvoicesById(invoiceIds);
	}

	@NonNull
	private Map<InvoiceId, I_C_Invoice> getInvoicesById(final Set<InvoiceId> invoiceIds)
	{
		return invoiceDAO.getByIdsInTrx(invoiceIds)
				.stream()
				.collect(Collectors.toMap(invoice -> InvoiceId.ofRepoId(invoice.getC_Invoice_ID()), invoice -> invoice));
	}

	@NonNull
	private PaymentAllocationPayableItem createPaymentAllocationPayableItem(
			@NonNull final RemittanceAdviceLine remittanceAdviceLine,
			@NonNull final RemittanceAdvice remittanceAdvice,
			@Nullable final I_C_Invoice invoice)
	{
		if (invoice == null)
		{
			throw new AdempiereException("Missing invoice for remittance line!")
					.appendParametersToMessage()
					.setParameter("C_RemittanceAdvice_Line_ID", RemittanceAdviceLineId.toRepoId(remittanceAdviceLine.getRemittanceAdviceLineId()))
					.setParameter("remittanceAdviceLine", remittanceAdviceLine);
		}

		final Amount paymentDiscountAmt = remittanceAdviceLine.getPaymentDiscountAmount() != null
				? remittanceAdviceLine.getPaymentDiscountAmount()
				: Amount.zero(moneyService.getCurrencyCodeByCurrencyId(remittanceAdvice.getRemittedAmountCurrencyId()));

		final Amount serviceFeeInREMADVCurrency = remittanceAdviceService.getServiceFeeInREMADVCurrency(remittanceAdviceLine).orElse(null);

		if (remittanceAdviceLine.getInvoiceAmtInREMADVCurrencyAdjusted() == null)
		{
			throw new AdempiereException("Amount cannot be null if the invoice is resolved!")
					.appendParametersToMessage()
					.setParameter("C_RemittanceAdvice_Line_ID", RemittanceAdviceLineId.toRepoId(remittanceAdviceLine.getRemittanceAdviceLineId()))
					.setParameter("remittanceAdviceLine", remittanceAdviceLine);
		}

		// create a "noop" multiplier that won't mess with the +/- signs
		final InvoiceAmtMultiplier multiplier =  InvoiceAmtMultiplier.adjustedFor(remittanceAdviceLine.getExternalInvoiceDocBaseType());
		
		final SOTrx remittanceAdviceSOTrx = SOTrx.ofBooleanNotNull(remittanceAdvice.isSOTrx());
		final ZoneId timeZone = orgDAO.getTimeZone(remittanceAdvice.getOrgId());

		return PaymentAllocationPayableItem.builder()
				.amtMultiplier(multiplier)
				.payAmt(remittanceAdviceLine.getRemittedAmountAdjusted())
				.openAmt(remittanceAdviceLine.getInvoiceAmtInREMADVCurrencyAdjusted())
				.serviceFeeAmt(serviceFeeInREMADVCurrency)
				.discountAmt(paymentDiscountAmt)
				.invoiceId(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()))
				.invoiceBPartnerId(BPartnerId.ofRepoId(invoice.getC_BPartner_ID()))
				.orgId(remittanceAdvice.getOrgId())
				.clientId(remittanceAdvice.getClientId())
				.bPartnerId(remittanceAdviceSOTrx.isSales() ? remittanceAdvice.getSourceBPartnerId() : remittanceAdvice.getDestinationBPartnerId())
				.documentNo(invoice.getDocumentNo())
				.soTrx(remittanceAdviceSOTrx)
				.dateInvoiced(TimeUtil.asLocalDate(invoice.getDateInvoiced(), timeZone))
				.build();
	}

	@NonNull
	private List<I_C_InvoiceLine> getInvoiceLines(final InvoiceId invoiceId)
	{
		return invoiceDAO.retrieveLines(invoiceId)
				.stream()
				.map(invoiceLine -> InterfaceWrapperHelper.create(invoiceLine, I_C_InvoiceLine.class))
				.collect(ImmutableList.toImmutableList());
	}
}
