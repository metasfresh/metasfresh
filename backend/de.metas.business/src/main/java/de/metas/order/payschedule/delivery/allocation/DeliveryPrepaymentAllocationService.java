/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.order.payschedule.delivery.allocation;

import de.metas.money.Money;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;

/**
 * Computes the prepayment allocation amount for a completed financial purchase invoice
 * (iter-3, AC #4, #6, #8, #10, #12, #15).
 *
 * <h2>Allocation rule (REQUIREMENTS.md §3.3)</h2>
 * <table>
 *   <tr><th>{@code IsPartialInvoice}</th><th>Allocation amount</th></tr>
 *   <tr><td>PARTIAL</td><td>{@code MIN(receipt × LC%, remainingPrepay)}</td></tr>
 *   <tr><td>FINAL</td><td>{@code remainingPrepay} (consumes all remaining prepay)</td></tr>
 * </table>
 *
 * <p>Edge: {@code remainingPrepay <= 0} → no allocation, no error (AC #15).
 *
 * <p><strong>CRITICAL (R12 / AC #15):</strong> never delegate to
 * {@code IAllocationBL.autoAllocateAvailablePayments(invoice)} — it is greedy and
 * would consume the full prepay on the first invoice, violating AC #6.
 * Always use {@code IAllocationBL.newBuilder()} with an explicit amount.
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3</a>
 */
@Service
public class DeliveryPrepaymentAllocationService
{
	// Tasks 27a, 27b, 27c will inject services here.
	// Pure function computeAllocation is deliberately free of injected dependencies.

	// -----------------------------------------------------------------------
	// Task 26 — pure computation function
	// -----------------------------------------------------------------------

	/**
	 * Pure function: computes the allocation amount to create between the proforma-prepayment
	 * and the given invoice, based on the matched receipt value and the partial-invoice flag.
	 *
	 * <p>Public for unit testability (no Spring context needed).
	 *
	 * @param receiptWithTax  value of the receipt(s) matched to this invoice, including tax
	 * @param flag            {@link IsPartialInvoiceFlag#PARTIAL} or {@link IsPartialInvoiceFlag#FINAL}
	 * @param remainingPrepay remaining available amount on the proforma-prepayment payment
	 * @param lcPercent       LC percentage (e.g. {@code Percent.of(30)} for a 30/70 payment term)
	 * @return the allocation amount; zero when {@code remainingPrepay <= 0}; never negative
	 */
	public Money computeAllocation(
			@NonNull final Money receiptWithTax,
			@NonNull final IsPartialInvoiceFlag flag,
			@NonNull final Money remainingPrepay,
			@NonNull final Percent lcPercent)
	{
		if (remainingPrepay.signum() <= 0)
		{
			return Money.zero(remainingPrepay.getCurrencyId());
		}

		switch (flag)
		{
			case PARTIAL:
			{
				// proportional = receipt × LC%, rounded to 2 decimal places HALF_UP
				final java.math.BigDecimal proportionalAmt =
						lcPercent.computePercentageOf(receiptWithTax.toBigDecimal(), 2, RoundingMode.HALF_UP);
				final Money proportional = Money.of(proportionalAmt, receiptWithTax.getCurrencyId());
				return proportional.min(remainingPrepay);
			}
			case FINAL:
			{
				// Final invoice consumes all remaining prepay
				return remainingPrepay;
			}
			default:
				throw new IllegalStateException("Unhandled IsPartialInvoiceFlag: " + flag);
		}
	}
}
