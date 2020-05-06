package de.metas.banking.bankstatement.match.model;

import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Payment;

import de.metas.banking.bankstatement.match.spi.IPaymentBatch;
import de.metas.util.Check;

/*
 * #%L
 * de.metas.banking.swingui
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

public class Payment implements IPayment
{
	public static IPayment of(final I_C_Payment paymentPO, final IPaymentBatch paymentBatch)
	{
		return new Payment(paymentPO, paymentBatch);
	}

	private final int paymentId;
	private final String documentNo;
	private final Date dateTrx;
	private final int currencyId;
	private final BigDecimal payAmt;
	private final int bpartnerId;
	private final String bpartnerName;
	private final BankAccount bankAccount;
	private final int invoiceId;
	private final IPaymentBatch paymentBatch;

	//

	private Payment(final I_C_Payment paymentPO, final IPaymentBatch paymentBatch)
	{
		super();

		Check.assumeNotNull(paymentPO, "paymentPO not null");

		paymentId = paymentPO.getC_Payment_ID();
		documentNo = paymentPO.getDocumentNo();
		dateTrx = paymentPO.getDateTrx();
		currencyId = paymentPO.getC_Currency_ID();

		if (paymentPO.isReceipt())
		{
			payAmt = paymentPO.getPayAmt();
		}
		else
		{
			payAmt = paymentPO.getPayAmt().negate();
		}

		final I_C_BPartner bpartner = InterfaceWrapperHelper.load(paymentPO.getC_BPartner_ID(), I_C_BPartner.class);
		bpartnerId = bpartner != null ? bpartner.getC_BPartner_ID() : -1;
		bpartnerName = bpartner.getName();

		bankAccount = BankAccount.of(paymentPO.getC_BP_BankAccount());

		invoiceId = paymentPO.getC_Invoice_ID();

		this.paymentBatch = paymentBatch;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public String getDocumentNo()
	{
		return documentNo;
	}

	@Override
	public Date getDateTrx()
	{
		return dateTrx;
	}

	@Override
	public BigDecimal getPayAmt()
	{
		return payAmt;
	}

	@Override
	public String getBPartnerName()
	{
		return bpartnerName;
	}

	@Override
	public int getC_BPartner_ID()
	{
		return bpartnerId;
	}

	@Override
	public BankAccount getBankAccount()
	{
		return bankAccount;
	}

	@Override
	public int getC_Payment_ID()
	{
		return paymentId;
	}

	@Override
	public int getC_Currency_ID()
	{
		return currencyId;
	}

	@Override
	public int getC_Invoice_ID()
	{
		return invoiceId;
	}

	@Override
	public IPaymentBatch getPaymentBatch()
	{
		return paymentBatch;
	}
}
