package de.metas.contracts.refund;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.adempiere.util.Check;

import de.metas.contracts.ConditionsId;
import de.metas.invoice.InvoiceSchedule;
import de.metas.lang.Percent;
import de.metas.money.Money;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
public class RefundConfig
{
	public enum RefundInvoiceType
	{
		INVOICE, CREDITMEMO;
	}

	public enum RefundBase
	{
		PERCENTAGE, AMOUNT_PER_UNIT;
	}

	public enum RefundMode
	{
		PER_INDIVIDUAL_SCALE, ALL_MAX_SCALE;
	}

	RefundInvoiceType refundInvoiceType;

	RefundBase refundBase;

	Percent percent;

	Money amount;

	/** {@code null} means that every product is matched. */
	ProductId productId;

	InvoiceSchedule invoiceSchedule;

	ConditionsId conditionsId;

	boolean useInProfitCalculation;

	/** This config might apply to "any" product (if productId == null). The quantity's UOM is always the uom of the respective product. */
	BigDecimal minQty;

	RefundMode refundMode;

	@Builder(toBuilder = true)
	public RefundConfig(
			@NonNull final RefundInvoiceType refundInvoiceType,
			@NonNull final RefundBase refundBase,
			@Nullable final Percent percent,
			@Nullable final Money amount,
			@Nullable final ProductId productId,
			@NonNull final InvoiceSchedule invoiceSchedule,
			@NonNull final ConditionsId conditionsId,
			boolean useInProfitCalculation,
			@NonNull final BigDecimal minQty,
			@NonNull final RefundMode refundMode)
	{
		this.refundInvoiceType = refundInvoiceType;
		this.refundBase = refundBase;
		this.productId = productId;
		this.invoiceSchedule = invoiceSchedule;
		this.conditionsId = conditionsId;
		this.useInProfitCalculation = useInProfitCalculation;

		if (RefundBase.PERCENTAGE.equals(refundBase))
		{
			this.percent = Check.assumeNotNull(percent, "If parameter 'refundBase'={}, then parameter 'percent' may not be null", RefundBase.PERCENTAGE);
			this.amount = null;
		}
		else
		{
			this.amount = Check.assumeNotNull(amount, "If parameter 'refundBase'={}, then parameter 'amount' may not be null", RefundBase.AMOUNT_PER_UNIT);
			this.percent = null;
		}

		Check.errorIf(minQty.signum() < 0, "Parameter 'minQty' may not be negative; minQty={}", minQty);
		this.minQty = minQty;

		this.refundMode = refundMode;
	}

}
