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
import de.metas.payment.PaymentId;
import de.metas.util.Services;
import org.adempiere.service.ClientId;
import org.compiere.Adempiere;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Payment;

import java.math.BigDecimal;

public class PlainPaymentDAO extends AbstractPaymentDAO
{
	public PlainPaymentDAO()
	{
		Adempiere.assertUnitTestMode();
	}

	@Override
	public BigDecimal getAvailableAmount(final PaymentId paymentId)
	{
		Adempiere.assertUnitTestMode();

		final I_C_Payment payment = getById(paymentId);
		return payment.getPayAmt();
	}

	@Override
	public BigDecimal getAllocatedAmt(final PaymentId paymentId)
	{
		final I_C_Payment payment = getById(paymentId);
		return getAllocatedAmt(payment);
	}

	@Override
	public BigDecimal getAllocatedAmt(final I_C_Payment payment)
	{
		Adempiere.assertUnitTestMode();

		BigDecimal sum = BigDecimal.ZERO;
		for (final I_C_AllocationLine line : retrieveAllocationLines(payment))
		{

			final I_C_AllocationHdr ah = line.getC_AllocationHdr();
			final BigDecimal lineAmt = line.getAmount();

			if (null != ah && ah.getC_Currency_ID() != payment.getC_Currency_ID())
			{
				final BigDecimal lineAmtConv = Services.get(ICurrencyBL.class).convert(
						lineAmt, // Amt
						CurrencyId.ofRepoId(ah.getC_Currency_ID()), // CurFrom_ID
						CurrencyId.ofRepoId(payment.getC_Currency_ID()), // CurTo_ID
						ah.getDateTrx().toInstant(), // ConvDate
						CurrencyConversionTypeId.ofRepoIdOrNull(payment.getC_ConversionType_ID()),
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
	public void updateDiscountAndPayment(final I_C_Payment payment, final int c_Invoice_ID, final I_C_DocType c_DocType)
	{
		Adempiere.assertUnitTestMode();

		throw new UnsupportedOperationException();
	}

}
