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

package de.metas.order.payschedule.delivery;

import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/**
 * Computes the with-tax value of a completed goods receipt ({@code M_InOut}).
 *
 * <p>Tax is applied <em>per order line</em> (AC #21 / REQUIREMENTS.md §3.1.3):
 * for each receipt line, the tax rate is taken from the matched order line's
 * {@code C_Tax_ID}. This correctly handles mixed-tax orders where different
 * order lines carry different tax rates.
 *
 * <p>Receipt lines without a matching order line (dropship lines, i.e.
 * {@code C_OrderLine_ID = 0}) contribute zero — they are skipped.
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3</a>
 */
@Component
public class ReceiptTaxCalculator
{
	/** Scale for intermediate tax-amount calculations (round at the end). */
	private static final int INTERMEDIATE_SCALE = 12;

	/** Final scale for {@code withTaxValue}. */
	private static final int FINAL_SCALE = 2;

	private final ITaxBL taxBL = Services.get(ITaxBL.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

	/**
	 * Computes the gross (with-tax) value of the given receipt.
	 *
	 * <p>Formula per line:
	 * <pre>
	 *   lineNet   = movementQty × orderLine.priceActual
	 *   lineGross = lineNet × (1 + orderLine.taxRate / 100)   (via Tax.calculateGross)
	 * </pre>
	 * The result is summed across all lines and rounded to 2 decimal places (HALF_UP).
	 *
	 * <p>Currency is taken from the first order line that has a non-zero
	 * {@code C_Currency_ID}. All order lines of the same PO share the same currency.
	 *
	 * @param inOutId the receipt to compute
	 * @return the total gross value of the receipt in the order's currency
	 */
	@NonNull
	public Money computeWithTaxValue(@NonNull final InOutId inOutId)
	{
		final I_M_InOut receipt = loadOutOfTrx(inOutId, I_M_InOut.class);
		final List<I_M_InOutLine> lines = inOutDAO.retrieveLines(receipt);

		BigDecimal grossTotal = BigDecimal.ZERO;
		CurrencyId currencyId = null;

		for (final I_M_InOutLine line : lines)
		{
			final int orderLineId = line.getC_OrderLine_ID();
			if (orderLineId <= 0)
			{
				// Dropship line — no order line → skip (contributes 0)
				continue;
			}

			final I_C_OrderLine orderLine = loadOutOfTrx(orderLineId, I_C_OrderLine.class);

			// Capture currency from the first qualifying order line
			if (currencyId == null && orderLine.getC_Currency_ID() > 0)
			{
				currencyId = CurrencyId.ofRepoId(orderLine.getC_Currency_ID());
			}

			final BigDecimal qty = line.getMovementQty();
			final BigDecimal priceActual = orderLine.getPriceActual();
			final BigDecimal lineNet = qty.multiply(priceActual);

			final int taxRepoId = orderLine.getC_Tax_ID();
			final BigDecimal lineGross;
			if (taxRepoId <= 0)
			{
				// No tax configured on order line → gross = net
				lineGross = lineNet;
			}
			else
			{
				final Tax tax = taxBL.getTaxById(TaxId.ofRepoId(taxRepoId));
				lineGross = tax.calculateGross(lineNet, INTERMEDIATE_SCALE);
			}

			grossTotal = grossTotal.add(lineGross);
		}

		if (currencyId == null)
		{
			// All lines were dropship — use order-level currency if available, else default to a sentinel
			final int orderId = receipt.getC_Order_ID();
			if (orderId > 0)
			{
				final org.compiere.model.I_C_Order order = loadOutOfTrx(orderId, org.compiere.model.I_C_Order.class);
				currencyId = CurrencyId.ofRepoId(order.getC_Currency_ID());
			}
			else
			{
				currencyId = CurrencyId.EUR; // fallback — should not occur in production
			}
		}

		// Round the total to 2 decimal places (HALF_UP) at the end
		final BigDecimal rounded = grossTotal.setScale(FINAL_SCALE, RoundingMode.HALF_UP);
		return Money.of(rounded, currencyId);
	}
}
