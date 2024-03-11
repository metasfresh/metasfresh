/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.banking.remittanceadvice.process;

import ch.qos.logback.classic.Level;
import com.google.common.annotations.VisibleForTesting;
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
import de.metas.currency.Amount;
import de.metas.invoice.InvoiceAmtMultiplier;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingFeeCalculation;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.money.MoneyService;
import de.metas.organization.IOrgDAO;
import de.metas.payment.PaymentId;
import de.metas.payment.TenderType;
import de.metas.payment.api.DefaultPaymentBuilder;
import de.metas.payment.api.IPaymentBL;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import de.metas.product.ProductId;
import de.metas.remittanceadvice.RemittanceAdvice;
import de.metas.remittanceadvice.RemittanceAdviceId;
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
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_C_RemittanceAdvice;
import org.compiere.model.X_C_RemittanceAdvice;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class C_RemittanceAdvice_CreateAndAllocatePayment extends JavaProcess
{
	private static final Logger logger = LogManager.getLogger(C_RemittanceAdvice_CreateAndAllocatePayment.class);

	private final RemittanceAdviceRepository remittanceAdviceRepo = SpringContextHolder.instance.getBean(RemittanceAdviceRepository.class);
	private final RemittanceAdviceService remittanceAdviceService = SpringContextHolder.instance.getBean(RemittanceAdviceService.class);
	private final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);

	private final PaymentAllocationService paymentAllocationService = SpringContextHolder.instance.getBean(PaymentAllocationService.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_C_RemittanceAdvice> processFilter = getProcessInfo().getQueryFilterOrElse(null);
		if (processFilter == null)
		{
			throw new AdempiereException("@NoSelection@");
		}
		final List<RemittanceAdvice> remittanceAdvices = remittanceAdviceRepo.getRemittanceAdvices(processFilter);

		for (final RemittanceAdvice remittanceAdvice : remittanceAdvices)
		{
			try
			{
				trxManager.runInNewTrx(() -> runForRemittanceAdvice(remittanceAdvice));
			}
			catch (final Exception e)
			{
				Loggables.withLogger(logger, Level.WARN).addLog("*** ERROR: failed to create and allocate payments for C_RemittanceAdvice_ID={} ", RemittanceAdviceId.toRepoId(remittanceAdvice.getRemittanceAdviceId()));
				throw AdempiereException.wrapIfNeeded(e)
						.appendParametersToMessage().setParameter("C_RemittanceAdvice_ID", RemittanceAdviceId.toRepoId(remittanceAdvice.getRemittanceAdviceId()));
			}
		}

		return MSG_OK;
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
				.dateTrx(remittanceAdvice.getSendDate() != null ? remittanceAdvice.getSendDate() : Instant.now())
				.paymentAllocationPayableItems(paymentAllocationPayableItems)
				.allowPartialAllocations(true)
				.build();
	}

	private void populateRemittanceWithAllocationData(
			@NonNull final Map<InvoiceId, RemittanceAdviceLine> remittanceAdviceLineMap,
			@NonNull final PaymentAllocationResult paymentAllocationResult)
	{
		final Map<Integer, InvoiceId> serviceFeeInvoiceIdsByAssignedInvoiceId =
				paymentAllocationResult
						.getPaymentAllocationIds()
						.values()
						.stream()
						.filter(paymentAllocationResultItem -> AllocationLineCandidate.AllocationLineCandidateType.SalesInvoiceToPurchaseInvoice.equals(paymentAllocationResultItem.getType()))
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

		if (!CollectionUtils.isEmpty(serviceFeeInvoiceLines))
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
	private I_C_Payment createPayment(@NonNull final RemittanceAdvice remittanceAdvice)
	{
		final DefaultPaymentBuilder paymentBuilder = remittanceAdvice.isSOTrx()
				? paymentBL.newInboundReceiptBuilder()
				: paymentBL.newOutboundPaymentBuilder();

		final BPartnerBankAccountId bPartnerBankAccountId = remittanceAdvice.isSOTrx() ? remittanceAdvice.getSourceBPartnerBankAccountId()
				: remittanceAdvice.getDestinationBPartnerBankAccountId();

		return paymentBuilder
				.adOrgId(remittanceAdvice.getOrgId())
				.bpartnerId(remittanceAdvice.isSOTrx() ? remittanceAdvice.getSourceBPartnerId() : remittanceAdvice.getDestinationBPartnerId())
				.orgBankAccountId(BankAccountId.ofRepoId(bPartnerBankAccountId.getRepoId()))
				.currencyId(remittanceAdvice.getRemittedAmountCurrencyId())
				.payAmt(remittanceAdvice.getRemittedAmountSum())
				.dateAcct(remittanceAdvice.getPaymentDate() != null 
								  ? TimeUtil.asLocalDate(remittanceAdvice.getPaymentDate())
								  : TimeUtil.asLocalDate(remittanceAdvice.getDocumentDate()))
				.dateTrx(TimeUtil.asLocalDate(remittanceAdvice.getDocumentDate()))
				.tenderType(TenderType.DirectDeposit)
				.createAndProcess();

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

		final SOTrx soTrx = SOTrx.ofBooleanNotNull(remittanceAdvice.isSOTrx());
		final boolean isCreditMemo = invoiceBL.isCreditMemo(invoice);
		final InvoiceAmtMultiplier amtMultiplier = toInvoiceAmtMultiplier(soTrx, isCreditMemo);

		final Amount paymentDiscountAmt = remittanceAdviceLine.getPaymentDiscountAmount() != null
				? remittanceAdviceLine.getPaymentDiscountAmount()
				: Amount.zero(moneyService.getCurrencyCodeByCurrencyId(remittanceAdvice.getRemittedAmountCurrencyId()));

		final Amount serviceFeeInREMADVCurrency = remittanceAdviceService.getServiceFeeInREMADVCurrency(remittanceAdviceLine).orElse(null);

		if (remittanceAdviceLine.getInvoiceAmtInREMADVCurrency() == null)
		{
			throw new AdempiereException("Amount cannot be null if the invoice is resolved!")
					.appendParametersToMessage()
					.setParameter("C_RemittanceAdvice_Line_ID", RemittanceAdviceLineId.toRepoId(remittanceAdviceLine.getRemittanceAdviceLineId()))
					.setParameter("remittanceAdviceLine", remittanceAdviceLine);
		}

		final ZoneId timeZone = orgDAO.getTimeZone(remittanceAdvice.getOrgId());

		return PaymentAllocationPayableItem.builder()
				.amtMultiplier(amtMultiplier)
				.payAmt(remittanceAdviceLine.getRemittedAmount())
				.openAmt(remittanceAdviceLine.getInvoiceAmtInREMADVCurrency())
				.serviceFeeAmt(serviceFeeInREMADVCurrency)
				.discountAmt(paymentDiscountAmt)
				.invoiceId(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()))
				.invoiceIsCreditMemo(invoiceBL.isCreditMemo(invoice))
				.invoiceBPartnerId(BPartnerId.ofRepoId(invoice.getC_BPartner_ID()))
				.orgId(remittanceAdvice.getOrgId())
				.clientId(remittanceAdvice.getClientId())
				.bPartnerId(soTrx.isSales() ? remittanceAdvice.getSourceBPartnerId() : remittanceAdvice.getDestinationBPartnerId())
				.documentNo(invoice.getDocumentNo())
				.soTrx(soTrx)
				.dateInvoiced(TimeUtil.asLocalDate(invoice.getDateInvoiced(), timeZone))
				.build();
	}

	@VisibleForTesting
	public static InvoiceAmtMultiplier toInvoiceAmtMultiplier(@NonNull final SOTrx soTrx, final boolean isCreditMemo)
	{
		return InvoiceAmtMultiplier.builder()
				.soTrx(soTrx)
				.isSOTrxAdjusted(false)
				.isCreditMemo(isCreditMemo)
				.isCreditMemoAdjusted(false)
				.build()
				.intern();
	}

	@NonNull
	private List<I_C_InvoiceLine> getInvoiceLines(final InvoiceId invoiceId)
	{
		return invoiceDAO.retrieveLines(invoiceId)
				.stream()
				.map(invoiceLine -> InterfaceWrapperHelper.create(invoiceLine, I_C_InvoiceLine.class))
				.collect(ImmutableList.toImmutableList());
	}

	private void runForRemittanceAdvice(@NonNull final RemittanceAdvice remittanceAdvice)
	{
		if (remittanceAdvice.getPaymentId() == null && X_C_RemittanceAdvice.DOCSTATUS_Completed.equals(remittanceAdvice.getDocStatus()))
		{
			final I_C_Payment payment = createPayment(remittanceAdvice);

			final PaymentAllocationCriteria paymentAllocationCriteria = getPaymentAllocationCriteria(remittanceAdvice, payment);

			final PaymentAllocationResult paymentAllocationResult = paymentAllocationService.allocatePayment(paymentAllocationCriteria);

			final Map<InvoiceId, RemittanceAdviceLine> remittanceAdviceLineMap = getRemittanceAdviceLinesByInvoiceId(remittanceAdvice.getLines());
			populateRemittanceWithAllocationData(remittanceAdviceLineMap, paymentAllocationResult);

			remittanceAdvice.setPaymentId(PaymentId.ofRepoId(payment.getC_Payment_ID()));

			remittanceAdviceRepo.updateRemittanceAdvice(remittanceAdvice);
		}
		else
		{
			Loggables.withLogger(logger, Level.INFO).addLog("Skipping Remittance Advice, RemittanceAdviceId={}, DocStatus={}, PaymentId={}",
					remittanceAdvice.getRemittanceAdviceId(), remittanceAdvice.getDocStatus(), remittanceAdvice.getPaymentId());
		}
	}
}
