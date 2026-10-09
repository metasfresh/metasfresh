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

import de.metas.currency.ICurrencyBL;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import de.metas.util.TypedAccessor;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PlainAllocationDAO extends AllocationDAO
{
	@Override
	public BigDecimal retrieveAllocatedAmtIgnoreGivenPaymentIDs(
			final org.compiere.model.I_C_Invoice invoice,
			final Set<Integer> paymentIDsToIgnore)
	{
		return retrieveAllocatedAmt(invoice, paymentIDsToIgnore, o -> {
			final I_C_AllocationLine line = (I_C_AllocationLine)o;
			return line.getAmount()
					.add(line.getDiscountAmt())
					.add(line.getWriteOffAmt());
		});
	}

	private BigDecimal retrieveAllocatedAmt(
			final org.compiere.model.I_C_Invoice invoice,
			final Set<Integer> paymentIDsToIgnore,
			final TypedAccessor<BigDecimal> amountAccessor)
	{
		BigDecimal sum = BigDecimal.ZERO;
		for (final I_C_AllocationLine line : retrieveAllocationLines(invoice))
		{
			if (paymentIDsToIgnore != null && paymentIDsToIgnore.contains(line.getC_Payment_ID()))
			{
				continue;
			}

			final I_C_AllocationHdr ah = line.getC_AllocationHdr();
			final BigDecimal lineAmt = amountAccessor.getValue(line);

			if (null != ah && ah.getC_Currency_ID() != invoice.getC_Currency_ID())
			{
				final BigDecimal lineAmtConv = Services.get(ICurrencyBL.class).convert(
						lineAmt, // Amt
						CurrencyId.ofRepoId(ah.getC_Currency_ID()), // CurFrom_ID
						CurrencyId.ofRepoId(invoice.getC_Currency_ID()), // CurTo_ID
						ah.getDateTrx().toInstant(), // ConvDate
						CurrencyConversionTypeId.ofRepoIdOrNull(invoice.getC_ConversionType_ID()),
						ClientId.ofRepoId(line.getAD_Client_ID()), 
						OrgId.ofRepoId(line.getAD_Org_ID()));

				sum = sum.add(lineAmtConv);
			}
			else
			{
				sum = sum.add(lineAmt);
			}
		}

		return sum;
	}

	@Override
	protected Optional<Money> retrieveAllocatedAmt(@NonNull final InvoiceId invoiceId, final String trxName)
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.create(Env.getCtx(), invoiceId.getRepoId(), I_C_Invoice.class, trxName);
		final List<I_C_AllocationLine> allocationLines = retrieveAllocationLines(invoice);
		if(allocationLines.isEmpty())
		{
			return Optional.empty();
		}
		
		final CurrencyId invoiceCurrencyId = CurrencyId.ofRepoId(invoice.getC_Currency_ID());
		Money allocatedAmt = Money.zero(invoiceCurrencyId);

		for (final I_C_AllocationLine line : allocationLines)
		{
			final I_C_AllocationHdr ah = line.getC_AllocationHdr();
			final CurrencyId allocationCurrencyId = CurrencyId.ofRepoId(ah.getC_Currency_ID());
			final Money lineAmt = Money.of(line.getAmount().add(line.getDiscountAmt()).add(line.getWriteOffAmt()), allocationCurrencyId);
			final Money lineAmtConv;
			if (ah.getC_Currency_ID() != invoice.getC_Currency_ID())
			{
				final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
				lineAmtConv = currencyBL.convert(
						lineAmt, 
						invoiceCurrencyId, 
						TimeUtil.asLocalDate(ah.getDateTrx()), 
						ClientAndOrgId.ofClientAndOrg(line.getAD_Client_ID(), line.getAD_Org_ID()));
			}
			else
			{
				lineAmtConv = lineAmt;
			}
			
			allocatedAmt = allocatedAmt.add(lineAmtConv);
		}

		return Optional.of(allocatedAmt);
	}

	@Override
	public BigDecimal retrieveWriteoffAmt(final org.compiere.model.I_C_Invoice invoice)
	{

		return retrieveWriteoffAmt(invoice,  o -> {
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
