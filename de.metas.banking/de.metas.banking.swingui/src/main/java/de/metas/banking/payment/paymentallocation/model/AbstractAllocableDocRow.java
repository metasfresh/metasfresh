package de.metas.banking.payment.paymentallocation.model;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;

import de.metas.payment.PaymentDirection;

/**
 * Abstract {@link IAllocableDocRow} which contains the "default methods" (as in java8), which are common on all {@link IAllocableDocRow} implementations.
 * 
 * @author tsa
 *
 */
abstract class AbstractAllocableDocRow implements IAllocableDocRow
{
	@Override
	public final BigDecimal getOpenAmtConv_APAdjusted()
	{
		return adjustAP(getOpenAmtConv());
	}

	/**
	 * @return not applied amount (i.e. {@link #getOpenAmtConv()} minus {@link #getAppliedAmt()})
	 */
	public final BigDecimal getNotAppliedAmt()
	{
		final BigDecimal openAmt = getOpenAmtConv();
		final BigDecimal appliedAmt = getAppliedAmt();
		final BigDecimal notAppliedAmt = openAmt.subtract(appliedAmt);
		return notAppliedAmt;
	}

	@Override
	public final BigDecimal getAppliedAmt_APAdjusted()
	{
		return adjustAP(getAppliedAmt());
	}

	protected static final BigDecimal notNullOrZero(final BigDecimal value)
	{
		return value == null ? BigDecimal.ZERO : value;
	}

	/**
	 * AP Adjust given amount (by using {@link #getMultiplierAP()}).
	 * 
	 * @param amount
	 * @return AP adjusted amount
	 */
	protected final BigDecimal adjustAP(final BigDecimal amount)
	{
		final BigDecimal multiplierAP = getMultiplierAP();
		final BigDecimal amount_APAdjusted = amount.multiply(multiplierAP);
		return amount_APAdjusted;

	}

	public final PaymentDirection getPaymentDirection()
	{
		return PaymentDirection.ofInboundPaymentFlag(isCustomerDocument());
	}

	@Override
	public final boolean isVendorDocument()
	{
		return getMultiplierAP().signum() < 0;
	}

	@Override
	public final boolean isCustomerDocument()
	{
		return getMultiplierAP().signum() > 0;
	}
}
