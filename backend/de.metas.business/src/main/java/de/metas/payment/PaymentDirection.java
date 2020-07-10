package de.metas.payment;

import de.metas.currency.Amount;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
import lombok.NonNull;

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

public enum PaymentDirection
{
	/** Inbound payment - aka Receipt */
	INBOUND,

	/** Outbound payment - aka payment */
	OUTBOUND;

	public static PaymentDirection ofReceiptFlag(final boolean isReceipt)
	{
		return isReceipt ? INBOUND : OUTBOUND;
	}

	public static PaymentDirection ofInboundPaymentFlag(final boolean inboundPayment)
	{
		return inboundPayment ? INBOUND : OUTBOUND;
	}

	public static PaymentDirection ofBankStatementAmount(@NonNull final Money statementAmt)
	{
		return statementAmt.signum() >= 0 ? INBOUND : OUTBOUND;
	}

	public static PaymentDirection ofSOTrx(@NonNull final SOTrx soTrx)
	{
		final boolean creditMemo = false;
		return ofSOTrxAndCreditMemo(soTrx, creditMemo);
	}

	public static PaymentDirection ofSOTrxAndCreditMemo(@NonNull final SOTrx soTrx, final boolean creditMemo)
	{
		if (soTrx.isPurchase())
		{
			return !creditMemo ? PaymentDirection.OUTBOUND : PaymentDirection.INBOUND;
		}
		else // sales
		{
			return !creditMemo ? PaymentDirection.INBOUND : PaymentDirection.OUTBOUND;
		}
	}

	public boolean isReceipt()
	{
		return isInboundPayment();
	}

	public boolean isInboundPayment()
	{
		return this == INBOUND;
	}

	public boolean isOutboundPayment()
	{
		return this == OUTBOUND;
	}

	public Amount convertPayAmtToStatementAmt(@NonNull final Amount payAmt)
	{
		return payAmt.negateIf(isOutboundPayment());
	}

	public Money convertPayAmtToStatementAmt(@NonNull final Money payAmt)
	{
		return payAmt.negateIf(isOutboundPayment());
	}

	public Money convertStatementAmtToPayAmt(@NonNull final Money bankStatementAmt)
	{
		return bankStatementAmt.negateIf(isOutboundPayment());
	}
}
