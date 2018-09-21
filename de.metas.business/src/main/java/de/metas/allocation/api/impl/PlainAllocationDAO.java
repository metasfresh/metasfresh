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

import java.math.BigDecimal;
import java.util.Properties;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.TypedAccessor;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;

import de.metas.currency.ICurrencyBL;

public class PlainAllocationDAO extends AllocationDAO
{
	@Override
	public BigDecimal retrieveAllocatedAmtIgnoreGivenPaymentIDs(
			final org.compiere.model.I_C_Invoice invoice,
			final Set<Integer> paymentIDsToIgnore)
	{
		return retrieveAllocatedAmt(invoice, paymentIDsToIgnore, new TypedAccessor<BigDecimal>()
		{
			@Override
			public BigDecimal getValue(final Object o)
			{
				final I_C_AllocationLine line = (I_C_AllocationLine)o;
				final BigDecimal lineAmt = line.getAmount()
						.add(line.getDiscountAmt())
						.add(line.getWriteOffAmt());
				return lineAmt;
			}
		});
	}

	private BigDecimal retrieveAllocatedAmt(
			final org.compiere.model.I_C_Invoice invoice,
			final Set<Integer> paymentIDsToIgnore,
			final TypedAccessor<BigDecimal> amountAccessor)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);

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
				final BigDecimal lineAmtConv = Services.get(ICurrencyBL.class).convert(ctx,
						lineAmt, // Amt
						ah.getC_Currency_ID(), // CurFrom_ID
						invoice.getC_Currency_ID(), // CurTo_ID
						ah.getDateTrx(), // ConvDate
						invoice.getC_ConversionType_ID(),
						line.getAD_Client_ID(), line.getAD_Org_ID());

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
	public BigDecimal retrieveAllocatedAmt(final org.compiere.model.I_C_Invoice invoice)
	{
		return retrieveAllocatedAmt(invoice, new TypedAccessor<BigDecimal>()
		{

			@Override
			public BigDecimal getValue(final Object o)
			{
				final I_C_AllocationLine line = (I_C_AllocationLine)o;
				final BigDecimal lineAmt = line.getAmount()
						.add(line.getDiscountAmt())
						.add(line.getWriteOffAmt());
				return lineAmt;
			}
		});
	}

	private BigDecimal retrieveAllocatedAmt(final org.compiere.model.I_C_Invoice invoice, final TypedAccessor<BigDecimal> amountAccessor)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);

		BigDecimal sum = BigDecimal.ZERO;
		for (final I_C_AllocationLine line : retrieveAllocationLines(invoice))
		{
			final I_C_AllocationHdr ah = line.getC_AllocationHdr();
			final BigDecimal lineAmt = amountAccessor.getValue(line);

			if (null != ah && ah.getC_Currency_ID() != invoice.getC_Currency_ID())
			{
				final BigDecimal lineAmtConv = Services.get(ICurrencyBL.class).convert(ctx,
						lineAmt, // Amt
						ah.getC_Currency_ID(), // CurFrom_ID
						invoice.getC_Currency_ID(), // CurTo_ID
						ah.getDateTrx(), // ConvDate
						invoice.getC_ConversionType_ID(),
						line.getAD_Client_ID(), line.getAD_Org_ID());

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

		return retrieveWriteoffAmt(invoice,  new TypedAccessor<BigDecimal>()
		{

			@Override
			public BigDecimal getValue(final Object o)
			{
				final I_C_AllocationLine line = (I_C_AllocationLine)o;
				final BigDecimal lineWriteOff = line.getWriteOffAmt();
				return lineWriteOff;
			}
		});

	}

	private BigDecimal retrieveWriteoffAmt(final org.compiere.model.I_C_Invoice invoice, final TypedAccessor<BigDecimal> amountAccessor)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);

		BigDecimal sum = BigDecimal.ZERO;
		for (final I_C_AllocationLine line : retrieveAllocationLines(invoice))
		{
			final I_C_AllocationHdr ah = line.getC_AllocationHdr();
			final BigDecimal lineWriteOff = amountAccessor.getValue(line);

			if (null != ah && ah.getC_Currency_ID() != invoice.getC_Currency_ID())
			{
				final BigDecimal lineWriteOffConv = Services.get(ICurrencyBL.class).convert(ctx,
						lineWriteOff, // Amt
						ah.getC_Currency_ID(), // CurFrom_ID
						invoice.getC_Currency_ID(), // CurTo_ID
						ah.getDateTrx(), // ConvDate
						invoice.getC_ConversionType_ID(),
						line.getAD_Client_ID(), line.getAD_Org_ID());

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
