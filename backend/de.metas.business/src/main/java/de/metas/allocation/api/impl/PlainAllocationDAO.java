package de.metas.allocation.api.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.allocation.api.InvoiceOpenRequest;
import de.metas.allocation.api.InvoiceOpenResult;
import de.metas.allocation.api.MoneyWithInvoiceFlags;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.ICurrencyBL;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.util.Services;
import de.metas.util.TypedAccessor;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

public class PlainAllocationDAO extends AllocationDAO
{

	@Override
	public InvoiceOpenResult retrieveInvoiceOpen(final @NonNull InvoiceOpenRequest request)
	{
		final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

		final Set<PaymentId> paymentIDsToIgnore = request.getExcludePaymentIds() != null && !request.getExcludePaymentIds().isEmpty()
				? request.getExcludePaymentIds()
				: null;

		final I_C_Invoice invoice = InterfaceWrapperHelper.load(request.getInvoiceId(), I_C_Invoice.class);
		final InvoiceDocBaseType docBaseType = Services.get(IInvoiceBL.class).getInvoiceDocBaseType(invoice);

		final CurrencyConversionTypeId conversionTypeId = CurrencyConversionTypeId.ofRepoIdOrNull(invoice.getC_ConversionType_ID());
		final CurrencyId returnInCurrencyId = request.getReturnInCurrencyId() != null
				? request.getReturnInCurrencyId()
				: CurrencyId.ofRepoId(invoice.getC_Invoice_ID());

		Money allocatedAmt = Money.zero(returnInCurrencyId);
		for (final I_C_AllocationLine line : retrieveAllocationLines(invoice))
		{
			if (paymentIDsToIgnore != null && paymentIDsToIgnore.contains(PaymentId.ofRepoIdOrNull(line.getC_Payment_ID())))
			{
				continue;
			}

			final I_C_AllocationHdr ah = line.getC_AllocationHdr();
			final CurrencyId allocationCurrencyId = CurrencyId.ofRepoId(ah.getC_Currency_ID());
			Money lineAmt = Money.of(line.getAmount().add(line.getDiscountAmt()).add(line.getWriteOffAmt()), allocationCurrencyId)
					.negateIf(docBaseType.isAP());
			if (!CurrencyId.equals(lineAmt.getCurrencyId(), returnInCurrencyId))
			{
				final CurrencyConversionContext conversionCtx = currencyBL.createCurrencyConversionContext(
						extractAllocationDate(ah, request.getDateColumn()),
						conversionTypeId,
						ClientId.ofRepoId(line.getAD_Client_ID()),
						OrgId.ofRepoId(line.getAD_Org_ID()));
				lineAmt = currencyBL.convert(conversionCtx, lineAmt, returnInCurrencyId).getAmountAsMoney();
			}

			allocatedAmt = allocatedAmt.add(lineAmt);
		}

		//
		Money invoiceGrandTotal = Money.of(invoice.getGrandTotal(), CurrencyId.ofRepoId(invoice.getC_Currency_ID()))
				.negateIf(docBaseType.isCreditMemo());

		if (!CurrencyId.equals(invoiceGrandTotal.getCurrencyId(), returnInCurrencyId))
		{
			final CurrencyConversionContext conversionCtx = currencyBL.createCurrencyConversionContext(
					extractInvoiceDate(invoice, request.getDateColumn()),
					conversionTypeId,
					ClientId.ofRepoId(invoice.getAD_Client_ID()),
					OrgId.ofRepoId(invoice.getAD_Org_ID()));
			invoiceGrandTotal = currencyBL.convert(conversionCtx, invoiceGrandTotal, returnInCurrencyId).getAmountAsMoney();
		}

		final Money openAmt = invoiceGrandTotal.subtract(allocatedAmt);

		return InvoiceOpenResult.builder()
				.invoiceDocBaseType(docBaseType)
				.invoiceGrandTotal(MoneyWithInvoiceFlags.builder()
						.docBaseType(docBaseType)
						.value(invoiceGrandTotal)
						.isAPAdjusted(false)
						.isCMAjusted(true)
						.build())
				.allocatedAmt(MoneyWithInvoiceFlags.builder()
						.docBaseType(docBaseType)
						.value(allocatedAmt)
						.isAPAdjusted(true)
						.isCMAjusted(false)
						.build())
				.openAmt(MoneyWithInvoiceFlags.builder()
						.docBaseType(docBaseType)
						.value(openAmt)
						.isAPAdjusted(true)
						.isCMAjusted(true)
						.build())
				.build();
	}

	private static Instant extractAllocationDate(final I_C_AllocationHdr ah, final @NonNull InvoiceOpenRequest.DateColumn dateColumn)
	{
		switch (dateColumn)
		{
			case DateAcct:
				return ah.getDateAcct().toInstant();
			case DateTrx:
			default:
				return ah.getDateTrx().toInstant();
		}
	}

	private static Instant extractInvoiceDate(final I_C_Invoice invoice, final @NonNull InvoiceOpenRequest.DateColumn dateColumn)
	{
		switch (dateColumn)
		{
			case DateAcct:
				return invoice.getDateAcct().toInstant();
			case DateTrx:
			default:
				return invoice.getDateInvoiced().toInstant();
		}
	}

	@Override
	public BigDecimal retrieveWriteoffAmt(final org.compiere.model.I_C_Invoice invoice)
	{
		return retrieveWriteoffAmt(invoice, o -> {
			final I_C_AllocationLine line = (I_C_AllocationLine)o;
			return line.getWriteOffAmt();
		});

	}

	private BigDecimal retrieveWriteoffAmt(final org.compiere.model.I_C_Invoice invoice, final TypedAccessor<BigDecimal> amountAccessor)
	{
		BigDecimal sum = BigDecimal.ZERO;
		for (final I_C_AllocationLine line : retrieveAllocationLines(invoice))
		{
			final I_C_AllocationHdr ah = line.getC_AllocationHdr();
			final BigDecimal lineWriteOff = amountAccessor.getValue(line);

			if (null != ah && ah.getC_Currency_ID() != invoice.getC_Currency_ID())
			{
				final BigDecimal lineWriteOffConv = Services.get(ICurrencyBL.class).convert(
						lineWriteOff, // Amt
						CurrencyId.ofRepoId(ah.getC_Currency_ID()), // CurFrom_ID
						CurrencyId.ofRepoId(invoice.getC_Currency_ID()), // CurTo_ID
						ah.getDateTrx().toInstant(), // ConvDate
						CurrencyConversionTypeId.ofRepoIdOrNull(invoice.getC_ConversionType_ID()),
						ClientId.ofRepoId(line.getAD_Client_ID()),
						OrgId.ofRepoId(line.getAD_Org_ID()));

				sum = sum.add(lineWriteOffConv);
			}
			else
			{
				sum = sum.add(lineWriteOff);
			}
		}

		return sum;
	}

}
