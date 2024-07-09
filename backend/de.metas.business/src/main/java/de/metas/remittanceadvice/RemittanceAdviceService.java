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

import ch.qos.logback.classic.Level;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.ICurrencyDAO;
import de.metas.document.IDocTypeDAO;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RemittanceAdviceService
{
	private static final Logger logger = LogManager.getLogger(RemittanceAdviceService.class);

	private final MoneyService moneyService;
	private final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);

	public RemittanceAdviceService(final MoneyService moneyService)
	{
		this.moneyService = moneyService;
	}

	public void resolveRemittanceAdviceLine(
			@NonNull final RemittanceAdvice remittanceAdvice,
			@NonNull final RemittanceAdviceLine remittanceAdviceLine)
	{
		final Optional<I_C_Invoice> invoiceOptional = resolveInvoice(remittanceAdviceLine);

		if (!invoiceOptional.isPresent())
		{
			Loggables.withLogger(logger, Level.WARN).addLog("*** WARN no invoice found for remittanceLine: {}", remittanceAdviceLine);
			return;
		}

		final I_C_Invoice invoice = invoiceOptional.get();

		final RemittanceAdviceLineInvoiceDetails remittanceAdviceLineInvoiceDetails =
				buildInvoiceDetailsForRemittanceLine(invoice, remittanceAdvice, remittanceAdviceLine);

		remittanceAdviceLine.syncWithInvoice(remittanceAdviceLineInvoiceDetails);
	}

	public void resolveRemittanceAdviceLines(final RemittanceAdvice remittanceAdvice)
	{
		remittanceAdvice.getLines().forEach(line -> resolveRemittanceAdviceLine(remittanceAdvice, line));
	}

	@NonNull
	private Amount getInvoiceAmountInRemAdvCurrency(@NonNull final CurrencyId remittanceCurrencyId, @NonNull final I_C_Invoice invoice)
	{
		final Instant conversionDate = SystemTime.asInstant();
		final CurrencyConversionContext currencyConversionContext =
				currencyConversionBL.createCurrencyConversionContext(conversionDate,
						ClientId.ofRepoId(invoice.getAD_Client_ID()),
						OrgId.ofRepoId(invoice.getAD_Org_ID()));

		final Money invoiceGrandTotal = Money.of(invoice.getGrandTotal(), CurrencyId.ofRepoId(invoice.getC_Currency_ID()));

		final Money invoiceAmtInREMADVCurrency = moneyService
				.convertMoneyToCurrency(invoiceGrandTotal, remittanceCurrencyId, currencyConversionContext);

		return invoiceAmtInREMADVCurrency.toAmount(moneyService::getCurrencyCodeByCurrencyId);
	}

	@NonNull
	private String getInvoiceDocumentNo(@NonNull final String invoiceIdentifier)
	{
		return invoiceIdentifier.substring(4).trim();
	}

	@NonNull
	private Optional<I_C_Invoice> resolveInvoice(@NonNull final RemittanceAdviceLine remittanceAdviceLine)
	{
		final InvoiceId invoiceId = remittanceAdviceLine.getInvoiceId();

		if (invoiceId != null)
		{
			return Optional.of(invoiceDAO.getByIdInTrx(invoiceId));
		}

		final String invoiceIdentifier = remittanceAdviceLine.getInvoiceIdentifier();

		if (Check.isEmpty(invoiceIdentifier))
		{
			return Optional.empty();
		}

		final String invoiceDocumentNo = getInvoiceDocumentNo(invoiceIdentifier);

		final List<I_C_Invoice> matchedInvoices = invoiceDAO.getByDocumentNo(invoiceDocumentNo, remittanceAdviceLine.getOrgId(), I_C_Invoice.class);

		if (Check.isEmpty(matchedInvoices))
		{
			return Optional.empty();
		}

		if (matchedInvoices.size() > 1)
		{
			final String matchedInvoiceIds = matchedInvoices.stream()
					.map(I_C_Invoice::getC_Invoice_ID)
					.map(String::valueOf)
					.collect(Collectors.joining(","));

			throw new AdempiereException("Multiple invoices found for the target doc number!")
					.appendParametersToMessage()
					.setParameter("TargetInvoiceDocNo", invoiceDocumentNo)
					.setParameter("OrgId", remittanceAdviceLine.getOrgId())
					.setParameter("MatchedInvoiceIds", matchedInvoiceIds);
		}

		return Optional.of(matchedInvoices.get(0));
	}

	private RemittanceAdviceLineInvoiceDetails buildInvoiceDetailsForRemittanceLine(
			@NonNull final I_C_Invoice invoice,
			@NonNull final RemittanceAdvice remittanceAdvice,
			@NonNull final RemittanceAdviceLine remittanceAdviceLine)
	{
		final CurrencyCode remittanceAdviceCurrencyCode = remittanceAdviceLine.getRemittedAmount().getCurrencyCode();
		final CurrencyCode invoiceCurrencyCode = moneyService.getCurrencyCodeByCurrencyId(CurrencyId.ofRepoId(invoice.getC_Currency_ID()));

		final Amount invoiceAmtInREMADVCurrency = remittanceAdviceCurrencyCode.equals(invoiceCurrencyCode)
				? Amount.of(invoice.getGrandTotal(), remittanceAdviceCurrencyCode)
				: getInvoiceAmountInRemAdvCurrency(remittanceAdvice.getRemittedAmountCurrencyId(), invoice);

		Amount overUnderAmt = remittanceAdviceLine.getRemittedAmount();

		final Amount serviceFeeInREMADVCurrency = getServiceFeeInREMADVCurrency(remittanceAdviceLine)
				.orElse(Amount.zero(remittanceAdviceCurrencyCode));

		overUnderAmt = overUnderAmt.add(serviceFeeInREMADVCurrency);

		if (remittanceAdviceLine.getPaymentDiscountAmount() != null)
		{
			overUnderAmt = overUnderAmt.add(remittanceAdviceLine.getPaymentDiscountAmount());
		}

		overUnderAmt = overUnderAmt.subtract(invoiceAmtInREMADVCurrency);

		final I_C_DocType invoiceDocType = docTypeDAO.getRecordById(invoice.getC_DocTypeTarget_ID());

		return RemittanceAdviceLineInvoiceDetails.builder()
				.invoiceId(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()))
				.billBPartnerId(BPartnerId.ofRepoId(invoice.getC_BPartner_ID()))
				.invoiceAmt(invoice.getGrandTotal())
				.invoiceCurrencyId(CurrencyId.ofRepoId(invoice.getC_Currency_ID()))
				.invoiceAmtInREMADVCurrency(invoiceAmtInREMADVCurrency)
				.overUnderAmtInREMADVCurrency(overUnderAmt)
				.invoiceDocType(invoiceDocType.getDocBaseType())
				.invoiceDate(TimeUtil.asInstant(invoice.getDateInvoiced()))
				.build();
	}

	@NonNull
	public Optional<Amount> getServiceFeeInREMADVCurrency(@NonNull final RemittanceAdviceLine remittanceAdviceLine)
	{
		if (remittanceAdviceLine.getServiceFeeAmount() == null
				|| remittanceAdviceLine.getServiceFeeAmount().isZero())
		{
			return Optional.empty();
		}

		final Amount serviceFeeAmount = remittanceAdviceLine.getServiceFeeAmount();

		if (serviceFeeAmount.getCurrencyCode().equals(remittanceAdviceLine.getRemittedAmount().getCurrencyCode()))
		{
			return Optional.of(serviceFeeAmount);
		}

		final Instant conversionDate = SystemTime.asInstant();
		final CurrencyConversionContext currencyConversionContext =
				currencyConversionBL.createCurrencyConversionContext(conversionDate,
						Env.getClientId(),
						remittanceAdviceLine.getOrgId());

		final Currency serviceFeeCurrency = currencyDAO.getByCurrencyCode(serviceFeeAmount.getCurrencyCode());
		final Currency remittedCurrency = currencyDAO.getByCurrencyCode(remittanceAdviceLine.getRemittedAmount().getCurrencyCode());

		final Money serviceFeeMoney = Money.of(serviceFeeAmount.toBigDecimal(), serviceFeeCurrency.getId());

		final Money serviceFeeInRemittedCurrency = moneyService
				.convertMoneyToCurrency(serviceFeeMoney, remittedCurrency.getId(), currencyConversionContext);

		final Amount serviceFeeAmountInRemadvCurr = Amount.of(serviceFeeInRemittedCurrency.toBigDecimal(), remittedCurrency.getCurrencyCode());

		return Optional.of(serviceFeeAmountInRemadvCurr);
	}
}
