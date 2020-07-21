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

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyPrecision;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
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
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

@Service
public class InvoiceProcessingServiceCompanyService
{
	private final InvoiceProcessingServiceCompanyConfigRepository configRepository;
	private final MoneyService moneyService;

	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

	public InvoiceProcessingServiceCompanyService(
			@NonNull final InvoiceProcessingServiceCompanyConfigRepository configRepository,
			@NonNull final MoneyService moneyService)
	{
		this.configRepository = configRepository;
		this.moneyService = moneyService;
	}

	public Optional<InvoiceProcessingFeeCalculation> createFeeCalculationForPayment(@NonNull final InvoiceProcessingFeeWithPrecalculatedAmountRequest request)
	{
		final BPartnerId serviceCompanyBPartnerId = request.getServiceCompanyBPartnerId();
		final InvoiceId invoiceId = request.getInvoiceId();
		final Amount feeAmountIncludingTax = request.getFeeAmountIncludingTax();

		if (invoiceDAO.hasCompletedInvoicesReferencing(invoiceId))
		{
			return Optional.empty();
		}

		final InvoiceProcessingServiceCompanyConfig config = configRepository.getByPaymentBPartnerAndValidFromDate(serviceCompanyBPartnerId, request.getPaymentDate()).orElse(null);
		if (config == null)
		{
			return Optional.empty();
		}


		return Optional.of(InvoiceProcessingFeeCalculation.builder()
				.orgId(request.getOrgId())
				.evaluationDate(request.getPaymentDate())
				//
				.customerId(request.getCustomerId())
				.invoiceId(invoiceId)
				//
				.serviceCompanyBPartnerId(config.getServiceCompanyBPartnerId())
				.serviceInvoiceDocTypeId(config.getServiceInvoiceDocTypeId())
				.serviceFeeProductId(config.getServiceFeeProductId())
				.feeAmountIncludingTax(feeAmountIncludingTax)
				//
				.build());
	}

	public Optional<InvoiceProcessingFeeCalculation> computeFee(@NonNull final InvoiceProcessingFeeComputeRequest request)
	{
		final BPartnerId customerId = request.getCustomerId();
		final InvoiceId invoiceId = request.getInvoiceId();
		final Amount invoiceGrandTotal = request.getInvoiceGrandTotal();

		if (invoiceDAO.hasCompletedInvoicesReferencing(invoiceId))
		{
			return Optional.empty();
		}

		final InvoiceProcessingServiceCompanyConfig config = configRepository.getByCustomerId(customerId, request.getEvaluationDate()).orElse(null);
		if (config == null)
		{
			return Optional.empty();
		}

		final Percent feePercentageOfGrandTotal = config.getFeePercentageOfGrandTotalByBpartner(customerId).orElse(null);
		if (feePercentageOfGrandTotal == null)
		{
			return Optional.empty();
		}

		final CurrencyPrecision precision = moneyService.getStdPrecision(invoiceGrandTotal.getCurrencyCode());
		final Amount feeAmountIncludingTax = invoiceGrandTotal.multiply(feePercentageOfGrandTotal, precision);

		return Optional.of(InvoiceProcessingFeeCalculation.builder()
				.orgId(request.getOrgId())
				.evaluationDate(request.getEvaluationDate())
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
}
