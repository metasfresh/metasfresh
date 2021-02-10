/*
 * #%L
 * de.metas.business
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

package de.metas.remittanceadvice;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.ConversionTypeMethod;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.ICurrencyBL;
import de.metas.document.IDocTypeDAO;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class RemittanceAdviceService
{
	final MoneyService moneyService;
	final RemittanceAdviceRepository remittanceAdviceRepo;
	final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	public RemittanceAdviceService(final MoneyService moneyService, final RemittanceAdviceRepository remittanceAdviceRepo)
	{
		this.moneyService = moneyService;
		this.remittanceAdviceRepo = remittanceAdviceRepo;
	}

	public void resolveRemittanceAdviceLine(final RemittanceAdvice remittanceAdvice, final RemittanceAdviceLine remittanceAdviceLine)
	{
		final I_C_Invoice invoice;
		final InvoiceId invoiceId = remittanceAdviceLine.getInvoiceId();

		if (invoiceId == null)
		{
			final String invoiceDocumentNo = getInvoiceDocumentNo(Objects.requireNonNull(remittanceAdviceLine.getInvoiceIdentifier()));
			invoice = getInvoiceByDocNumber(invoiceDocumentNo, remittanceAdvice.getOrgId());
		}
		else
		{
			invoice = getInvoice(invoiceId);
		}

		if (invoice != null)
		{
			final BigDecimal invoiceAmtInREMADVCurrency;
			if (remittanceAdvice.getRemittedAmountCurrencyId().getRepoId() != invoice.getC_Currency_ID())
			{
				invoiceAmtInREMADVCurrency = getInvoiceAmountInRemAdvCurrency(remittanceAdvice, invoice);
			}
			else
			{
				invoiceAmtInREMADVCurrency = invoice.getGrandTotal();
			}

			BigDecimal overUnderAmt = remittanceAdviceLine.getRemittedAmount().getAsBigDecimal();
			if (remittanceAdviceLine.getPaymentDiscountAmount() != null)
			{
				overUnderAmt = overUnderAmt.add(remittanceAdviceLine.getPaymentDiscountAmount().getAsBigDecimal());
			}
			overUnderAmt = overUnderAmt.subtract(invoiceAmtInREMADVCurrency);

			final I_C_DocType invoiceDocType = docTypeDAO.getById(invoice.getC_DocType_ID());

			final RemittanceAdviceLineInvoiceDetails remittanceAdviceLineInvoiceDetails = RemittanceAdviceLineInvoiceDetails.builder()
					.billBPartnerId(BPartnerId.ofRepoId(invoice.getC_BPartner_ID()))
					.invoiceAmt(invoice.getGrandTotal())
					.invoiceCurrencyId(CurrencyId.ofRepoId(invoice.getC_Currency_ID()))
					.invoiceAmtInREMADVCurrency(invoiceAmtInREMADVCurrency)
					.overUnderAmt(overUnderAmt)
					.invoiceDocType(invoiceDocType != null ? invoiceDocType.getDocBaseType() : null)
					.invoiceDate(invoice.getDateInvoiced())
					.build();

			remittanceAdviceLine.processIsBPartnerValid(remittanceAdviceLineInvoiceDetails, remittanceAdvice.getRemittanceAdviceBPartnerId());

			remittanceAdviceLine.syncWithInvoice(remittanceAdviceLineInvoiceDetails);
		}
	}

	public void resolveRemittanceAdviceLines(final RemittanceAdvice remittanceAdvice)
	{
		final List<RemittanceAdviceLine> remittanceAdviceLines = remittanceAdvice.getLines();

		for (final RemittanceAdviceLine remittanceAdviceLine : remittanceAdviceLines)
		{
			resolveRemittanceAdviceLine(remittanceAdvice, remittanceAdviceLine);
		}
	}

	private I_C_Invoice getInvoice(final InvoiceId invoiceId)
	{
		final I_C_Invoice invoice;
		invoice = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice.class)
				.addInArrayFilter(I_C_Invoice.COLUMNNAME_C_Invoice_ID, invoiceId)
				.create()
				.first();
		return invoice;
	}

	private BigDecimal getInvoiceAmountInRemAdvCurrency(final RemittanceAdvice remittanceAdvice, final I_C_Invoice invoice)
	{
		final LocalDate conversionDate = TimeUtil.asLocalDate(remittanceAdvice.getDocumentDate());
		final CurrencyConversionContext currencyConversionContext =
				currencyConversionBL.createCurrencyConversionContext(conversionDate,
																	 ConversionTypeMethod.Spot,
																	 remittanceAdvice.getClientId(),
																	 remittanceAdvice.getOrgId());

		final Money invoiceAmtInREMADVCurrency = moneyService.convertMoneyToCurrency(Money.of(invoice.getGrandTotal(), CurrencyId.ofRepoId(invoice.getC_Currency_ID())),
																					 remittanceAdvice.getRemittedAmountCurrencyId(), currencyConversionContext);

		return invoiceAmtInREMADVCurrency.toBigDecimal();
	}

	@Nullable
	private String getInvoiceDocumentNo(final String invoiceIdentifier)
	{
		return invoiceIdentifier != null ? invoiceIdentifier.substring(4).trim() : null;
	}

	@Nullable
	private I_C_Invoice getInvoiceByDocNumber(final String documentNo, final OrgId orgId)
	{
		if (documentNo != null)
		{
			return Services.get(IQueryBL.class)
					.createQueryBuilder(I_C_Invoice.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_C_Invoice.COLUMN_DocumentNo, documentNo)
					.addEqualsFilter(I_C_Invoice.COLUMNNAME_AD_Org_ID, orgId)
					.create()
					.firstOnly(I_C_Invoice.class);
		}

		return null;
	}
}
