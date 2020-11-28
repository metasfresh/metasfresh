package de.metas.contracts.refund;

import static de.metas.util.Check.fail;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import de.metas.contracts.ConditionsId;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.invoice.InvoiceSchedule;
import de.metas.money.Money;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.lang.Percent;
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
		/**
		 * The config matching the respective minQty is applied only to the part that exceeds that quantity.
		 * A.k.a {@link X_C_Flatrate_RefundConfig#REFUNDMODE_Tiered}
		 */
		APPLY_TO_EXCEEDING_QTY,

		/**
		 * The config matching the respective minQty is applied to all assignments.
		 * A.k.a {@link X_C_Flatrate_RefundConfig#REFUNDMODE_Accumulated}
		 */
		APPLY_TO_ALL_QTIES;
	}

	RefundConfigId id;

	/**
	 * Why BigDecimal and not Quantity: this config might apply to "any" product (if productId == null). The quantity's UOM is always the uom of the respective product.
	 */
	BigDecimal minQty;

	RefundInvoiceType refundInvoiceType;

	RefundBase refundBase;

	Percent percent;

	Money amount;

	/** {@code null} means that every product is matched. */
	ProductId productId;

	InvoiceSchedule invoiceSchedule;

	ConditionsId conditionsId;

	boolean useInProfitCalculation;

	RefundMode refundMode;

	@Builder(toBuilder = true)
	public RefundConfig(
			@Nullable final RefundConfigId id, // may be null if not yet persisted
			@NonNull final RefundInvoiceType refundInvoiceType,
			@NonNull final RefundBase refundBase,
			@Nullable final Percent percent,
			@Nullable final Money amount,
			@Nullable final ProductId productId,
			@Nullable final InvoiceSchedule invoiceSchedule,
			@NonNull final ConditionsId conditionsId,
			boolean useInProfitCalculation,
			@NonNull final BigDecimal minQty,
			@NonNull final RefundMode refundMode)
	{
		this.id = id;
		this.refundInvoiceType = refundInvoiceType;
		this.refundBase = refundBase;
		this.productId = productId;
		this.invoiceSchedule = invoiceSchedule;
		this.conditionsId = conditionsId;
		this.useInProfitCalculation = useInProfitCalculation;
		this.refundMode = refundMode;

		switch (refundBase)
		{
			case PERCENTAGE:
				this.amount = null;
				this.percent = Check.assumeNotNull(percent, "If parameter 'refundBase'={}, then parameter 'percent' may not be null; this={}", RefundBase.PERCENTAGE, this);
				break;
			case AMOUNT_PER_UNIT:
				this.amount = Check.assumeNotNull(amount, "If parameter 'refundBase'={}, then parameter 'amount' may not be null; this={}", RefundBase.AMOUNT_PER_UNIT, this);
				this.percent = null;
				break;
			default:
				fail("Unexpected refundBase={}", refundBase);
				this.amount = null;
				this.percent = null;
				break;
		}

		Check.errorIf(minQty.signum() < 0, "Parameter 'minQty' may not be negative; minQty={}", minQty);
		this.minQty = minQty;

		Check.errorIf(invoiceSchedule == null && !isZeroConfig(),
				"Parameter invoiceSchedule may not be null, unless both amount, percent and minQty are null/zero");
	}

	public boolean isZeroConfig()
	{
		return minQty.signum() <= 0
				&& (amount == null || amount.isZero())
				&& (percent == null || percent.isZero());
	}

	/**
	 * If an {@link AssignmentToRefundCandidate} is created using this config,
	 * then this method decides if that assignement's quantity shall be part of the sum
	 * when computing the respective {@link RefundInvoiceCandidate}'s assigned quantity.
	 *
	 */
	public boolean isIncludeAssignmentsWithThisConfigInSum()
	{
		if (RefundMode.APPLY_TO_EXCEEDING_QTY.equals(refundMode))
		{
			return true;
		}

		return minQty.signum() <= 0;
	}
}
