package de.metas.payment.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.wrapper.IPOJOFilter;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Payment;

import de.metas.currency.ICurrencyBL;
import de.metas.util.Services;

public class PlainPaymentDAO extends AbstractPaymentDAO
{
	private final POJOLookupMap db = POJOLookupMap.get();

	public POJOLookupMap getDB()
	{
		return db;
	}

	@Override
	public BigDecimal getAvailableAmount(I_C_Payment payment)
	{
		return payment.getPayAmt();
	}
	
	@Override
	public List<I_C_AllocationLine> retrieveAllocationLines(final I_C_Payment payment)
	{
		return db.getRecords(I_C_AllocationLine.class, new IPOJOFilter<I_C_AllocationLine>()
		{
			@Override
			public boolean accept(I_C_AllocationLine pojo)
			{
				return pojo.getC_Payment_ID() == payment.getC_Payment_ID();
			}
		});
	}
	
	@Override
	public BigDecimal getAllocatedAmt(I_C_Payment payment)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(payment);

		BigDecimal sum = BigDecimal.ZERO;
		for (final I_C_AllocationLine line : retrieveAllocationLines(payment))
		{

			final I_C_AllocationHdr ah = line.getC_AllocationHdr();
			final BigDecimal lineAmt = line.getAmount();

			if ((null != ah) && (ah.getC_Currency_ID() != payment.getC_Currency_ID()))
			{
				final BigDecimal lineAmtConv = Services.get(ICurrencyBL.class).convert(ctx,
						lineAmt, // Amt
						ah.getC_Currency_ID(), // CurFrom_ID
						payment.getC_Currency_ID(), // CurTo_ID
						ah.getDateTrx(), // ConvDate
						payment.getC_ConversionType_ID(),
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
	

}
