/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.invoice.invoiceProcessingServiceCompany;

import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyPrecision;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.AdMessageKey;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.compiere.model.X_C_DocType.DOCBASETYPE_APInvoice;
import static org.compiere.model.X_C_DocType.DOCSUBTYPE_PaymentServiceProviderInvoice;

@Service
public class InvoiceProcessingServiceCompanyService
{
	private static final AdMessageKey MSG_INVOICE_HAS_SERVICE_INVOICE = AdMessageKey.of("AlreadyGeneratedServiceInvoice");
	private static final AdMessageKey MSG_PAYMENTS_OF_DIFFERENT_SERVICE_COMPANY_OR_DATE = AdMessageKey.of("InvoiceProcessingServiceCompany_PaymentsOfDifferentServiceCompanyOrDate");
	private static final AdMessageKey MSG_CUSTOMER_NOT_ASSIGNED_TO_SERVICE_COMPANY = AdMessageKey.of("InvoiceProcessingServiceCompany_CustomerNotAssignedToServiceCompany");

	@NonNull private final InvoiceProcessingServiceCompanyConfigRepository configRepository;
	@NonNull private final MoneyService moneyService;

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	@NonNull private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	@NonNull private final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);

	public InvoiceProcessingServiceCompanyService(
			@NonNull final InvoiceProcessingServiceCompanyConfigRepository configRepository,
			@NonNull final MoneyService moneyService)
	{
		this.configRepository = configRepository;
		this.moneyService = moneyService;
	}

	public Optional<InvoiceProcessingServiceCompanyConfig> getByCustomerId(@NonNull final BPartnerId customerId, @NonNull final ZonedDateTime evaluationDate)
	{
		return configRepository.getByCustomerId(customerId, evaluationDate);
	}

	/**
	 * @return the service fee calculation for the given payment's partner, or empty if that partner is not an invoice processing service company
	 * @throws AdempiereException (user validation error) if the invoice's customer is not assigned to that service company,
	 *                            because a service company may only retain a service fee on invoices of its own customers
	 */
	public Optional<InvoiceProcessingFeeCalculation> createFeeCalculationForPayment(@NonNull final InvoiceProcessingFeeWithPrecalculatedAmountRequest request)
	{
		final BPartnerId serviceCompanyBPartnerId = request.getServiceCompanyBPartnerId();
		final InvoiceId invoiceId = request.getInvoiceId();
		final Amount feeAmountIncludingTax = request.getFeeAmountIncludingTax();

		final ImmutableSet<InvoiceId> invoiceIdsWithGeneratedFees = retainIfServiceInvoiceWasAlreadyGenerated(ImmutableSet.of(invoiceId));

		if (!invoiceIdsWithGeneratedFees.isEmpty())
		{
			final String documentNo = invoiceDAO.getDocumentNosByInvoiceIds(ImmutableSet.of(invoiceId)).get(invoiceId);

			throw new AdempiereException(MSG_INVOICE_HAS_SERVICE_INVOICE, documentNo);
		}

		final InvoiceProcessingServiceCompanyConfig config = configRepository.getByPaymentBPartnerAndValidFromDate(serviceCompanyBPartnerId, request.getPaymentDate()).orElse(null);
		if (config == null)
		{
			return Optional.empty();
		}

		final BPartnerId customerId = request.getCustomerId();
		if (!config.isBPartnerDetailsActive(customerId))
		{
			throw new AdempiereException(
					MSG_CUSTOMER_NOT_ASSIGNED_TO_SERVICE_COMPANY,
					invoiceDAO.getDocumentNosByInvoiceIds(ImmutableSet.of(invoiceId)).get(invoiceId),
					bpartnerBL.getBPartnerName(serviceCompanyBPartnerId),
					bpartnerBL.getBPartnerName(customerId))
					.markAsUserValidationError()
					.setParameter("C_Invoice_ID", invoiceId.getRepoId())
					.setParameter("ServiceCompany_BPartner_ID", serviceCompanyBPartnerId.getRepoId())
					.setParameter("C_BPartner_ID", customerId.getRepoId());
		}

		return Optional.of(InvoiceProcessingFeeCalculation.builder()
				.orgId(request.getOrgId())
				.evaluationDate(request.getPaymentDate())
				//
				.customerId(customerId)
				.invoiceId(invoiceId)
				//
				.serviceCompanyBPartnerId(config.getServiceCompanyBPartnerId())
				.serviceInvoiceDocTypeId(config.getServiceInvoiceDocTypeId())
				.serviceFeeProductId(config.getServiceFeeProductId())
				.feeAmountIncludingTax(feeAmountIncludingTax)
				//
				.build());
	}

	/**
	 * Derives the context for the service fee of an invoice that is allocated against the given payments.
	 *
	 * @param paymentContexts one context per selected payment (payment partner = service company, payment date); may be empty
	 * @param noConfigError   the error to throw if there are no payments and the customer has no service company config
	 * @return with payments: their single common context; without payments: the customer's configured service company and now
	 */
	@NonNull
	public InvoiceProcessingContext extractInvoiceProcessingContext(
			@NonNull final BPartnerId customerId,
			@NonNull final Collection<InvoiceProcessingContext> paymentContexts,
			@NonNull final Supplier<AdempiereException> noConfigError)
	{
		if (paymentContexts.isEmpty())
		{
			final ZonedDateTime evaluationDate = SystemTime.asZonedDateTime();
			final InvoiceProcessingServiceCompanyConfig config = getByCustomerId(customerId, evaluationDate)
					.orElseThrow(noConfigError);
			return InvoiceProcessingContext.of(config.getServiceCompanyBPartnerId(), evaluationDate);
		}

		final ImmutableSet<InvoiceProcessingContext> distinctContexts = ImmutableSet.copyOf(paymentContexts);
		if (distinctContexts.size() != 1)
		{
			throw new AdempiereException(MSG_PAYMENTS_OF_DIFFERENT_SERVICE_COMPANY_OR_DATE).markAsUserValidationError();
		}
		return distinctContexts.iterator().next();
	}

	public Optional<InvoiceProcessingFeeCalculation> computeFee(@NonNull final InvoiceProcessingFeeComputeRequest request)
	{
		if (isServiceInvoiceAlreadyGenerated(request))
		{
			return Optional.empty();
		}

		final BPartnerId customerId = request.getCustomerId();
		final InvoiceProcessingServiceCompanyConfig config = getByCustomerId(customerId, request.getEvaluationDate()).orElse(null);
		if (config == null)
		{
			return Optional.empty();
		}

		final Percent feePercentageOfGrandTotal = config.getFeePercentageOfGrandTotalByBpartner(customerId, request.getDocTypeId()).orElse(null);
		if (feePercentageOfGrandTotal == null)
		{
			return Optional.empty();
		}

		final Amount invoiceGrandTotal = request.getInvoiceGrandTotal();
		final CurrencyPrecision precision = moneyService.getStdPrecision(invoiceGrandTotal.getCurrencyCode());
		final Amount feeAmountIncludingTax = invoiceGrandTotal
				.abs() // because no matter if the grand total is payed by customer or we have to pay it to customer, the processing company is retaining their fee
				.multiply(feePercentageOfGrandTotal, precision);

		return Optional.of(InvoiceProcessingFeeCalculation.builder()
				.orgId(request.getOrgId())
				.evaluationDate(request.getEvaluationDate())
				//
				.customerId(customerId)
				.invoiceId(request.getInvoiceId())
				//
				.serviceCompanyBPartnerId(config.getServiceCompanyBPartnerId())
				.serviceInvoiceDocTypeId(config.getServiceInvoiceDocTypeId())
				.serviceFeeProductId(config.getServiceFeeProductId())
				.feeAmountIncludingTax(feeAmountIncludingTax)
				//
				.build());
	}

	private boolean isServiceInvoiceAlreadyGenerated(@NonNull final InvoiceProcessingFeeComputeRequest request)
	{
		if (request.getServiceInvoiceWasAlreadyGenerated().isPresent())
		{
			return request.getServiceInvoiceWasAlreadyGenerated().isTrue();
		}
		else
		{
			return !retainIfServiceInvoiceWasAlreadyGenerated(ImmutableSet.of(request.getInvoiceId())).isEmpty();
		}
	}


	public InvoiceId generateServiceInvoice(
			@NonNull final InvoiceProcessingFeeCalculation calculation,
			@Nullable final Money invoiceProcessingFeeIncludingTaxOverride)
	{
		final Amount invoiceProcessingFeeIncludingTaxOverrideAmount = invoiceProcessingFeeIncludingTaxOverride != null
				? moneyService.toAmount(invoiceProcessingFeeIncludingTaxOverride)
				: null;

		return generateServiceInvoice(calculation, invoiceProcessingFeeIncludingTaxOverrideAmount);
	}

	public InvoiceId generateServiceInvoice(
			@NonNull final InvoiceProcessingFeeCalculation calculation,
			@Nullable final Amount invoiceProcessingFeeIncludingTaxOverride)
	{
		trxManager.assertThreadInheritedTrxExists();

		final OrgId orgId = calculation.getOrgId();
		final ZonedDateTime evaluationDate = calculation.getEvaluationDate();

		final Amount invoiceProcessingFeeIncludingTax = invoiceProcessingFeeIncludingTaxOverride != null
				? invoiceProcessingFeeIncludingTaxOverride
				: calculation.getFeeAmountIncludingTax();

		final BPartnerId serviceCompanyBPartnerId = calculation.getServiceCompanyBPartnerId();
		final DocTypeId serviceInvoiceDocTypeId = calculation.getServiceInvoiceDocTypeId();
		final ProductId serviceFeeProductId = calculation.getServiceFeeProductId();

		//
		// Invoice Header
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(orgId.getRepoId());
		invoice.setIsSOTrx(SOTrx.PURCHASE.toBoolean());
		invoice.setC_DocTypeTarget_ID(serviceInvoiceDocTypeId.getRepoId());
		invoice.setDateInvoiced(TimeUtil.asTimestamp(evaluationDate));
		invoice.setDateAcct(TimeUtil.asTimestamp(evaluationDate));
		invoice.setRef_Invoice_ID(calculation.getInvoiceId().getRepoId());

		invoice.setC_BPartner_ID(serviceCompanyBPartnerId.getRepoId());
		invoiceBL.updateFromBPartner(invoice);
		invoice.setIsTaxIncluded(true);

		final CurrencyId invoiceCurrencyId = CurrencyId.ofRepoId(invoice.getC_Currency_ID());
		final CurrencyCode invoiceCurrencyCode = moneyService.getCurrencyCodeByCurrencyId(invoiceCurrencyId);
		if (!invoiceCurrencyCode.equals(invoiceProcessingFeeIncludingTax.getCurrencyCode()))
		{
			throw new AdempiereException("Price List currency is not matching processing Fee currency")
					.setParameter("invoiceProcessingFee", invoiceProcessingFeeIncludingTax)
					.setParameter("invoiceCurrencyCode", invoiceCurrencyCode)
					.setParameter("M_PriceList_ID", invoice.getM_PriceList_ID())
					.appendParametersToMessage();
		}

		invoiceDAO.save(invoice);

		//
		// Invoice Line
		final I_C_InvoiceLine invoiceLine = newInstance(I_C_InvoiceLine.class);
		invoiceLine.setAD_Org_ID(orgId.getRepoId());
		invoiceLine.setC_Invoice_ID(invoice.getC_Invoice_ID());
		invoiceBL.setProductAndUOM(invoiceLine, serviceFeeProductId);
		invoiceLine.setQtyEntered(BigDecimal.ONE);
		invoiceLine.setQtyInvoiced(BigDecimal.ONE);
		invoiceLine.setIsManualPrice(true);
		invoiceLine.setPriceEntered(invoiceProcessingFeeIncludingTax.toBigDecimal());
		invoiceLine.setPriceActual(invoiceProcessingFeeIncludingTax.toBigDecimal());
		invoiceDAO.save(invoiceLine);

		documentBL.processEx(invoice, IDocument.ACTION_Complete, DocStatus.Completed.getCode());

		return InvoiceId.ofRepoId(invoice.getC_Invoice_ID());
	}

	@NonNull
	public ImmutableSet<InvoiceId> retainIfServiceInvoiceWasAlreadyGenerated(final @NonNull Collection<InvoiceId> invoiceIds)
	{
		final DocBaseAndSubType docBaseAndSubType = DocBaseAndSubType.of(DOCBASETYPE_APInvoice, DOCSUBTYPE_PaymentServiceProviderInvoice);

		return invoiceDAO.retainReferencingCompletedInvoices(invoiceIds, docBaseAndSubType);
	}
}
