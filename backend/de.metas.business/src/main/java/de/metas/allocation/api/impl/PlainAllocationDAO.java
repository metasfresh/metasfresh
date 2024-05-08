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
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import de.metas.util.TypedAccessor;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
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
						TimeUtil.asLocalDate(ah.getDateTrx()), // ConvDate
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
	public BigDecimal retrieveAllocatedAmt(final org.compiere.model.@NonNull I_C_Invoice invoice)
	{
		return retrieveAllocatedAmt(invoice, o -> {
			final I_C_AllocationLine line = (I_C_AllocationLine)o;
			return line.getAmount()
					.add(line.getDiscountAmt())
					.add(line.getWriteOffAmt());
		});
	}

	private BigDecimal retrieveAllocatedAmt(final org.compiere.model.I_C_Invoice invoice, final TypedAccessor<BigDecimal> amountAccessor)
	{
		BigDecimal sum = BigDecimal.ZERO;
		for (final I_C_AllocationLine line : retrieveAllocationLines(invoice))
		{
			final I_C_AllocationHdr ah = line.getC_AllocationHdr();
			final BigDecimal lineAmt = amountAccessor.getValue(line);

			if (null != ah && ah.getC_Currency_ID() != invoice.getC_Currency_ID())
			{
				final BigDecimal lineAmtConv = Services.get(ICurrencyBL.class).convert(
						lineAmt, // Amt
						CurrencyId.ofRepoId(ah.getC_Currency_ID()), // CurFrom_ID
						CurrencyId.ofRepoId(invoice.getC_Currency_ID()), // CurTo_ID
						TimeUtil.asLocalDate(ah.getDateTrx()), // ConvDate
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
						TimeUtil.asLocalDate(ah.getDateTrx()), // ConvDate
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
