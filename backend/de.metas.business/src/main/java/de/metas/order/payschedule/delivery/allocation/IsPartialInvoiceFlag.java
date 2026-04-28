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

/**
 * Typed representation of the {@code C_Invoice.IsPartialInvoice} boolean column.
 *
 * <ul>
 *   <li>{@link #PARTIAL} ({@code IsPartialInvoice = true}) — a partial (non-final) invoice;
 *       allocation is capped at {@code receipt × LC%}.
 *   <li>{@link #FINAL} ({@code IsPartialInvoice = false}) — the final invoice for a receipt;
 *       allocation consumes the entire remaining prepayment.
 * </ul>
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3 §3.3</a>
 */
public enum IsPartialInvoiceFlag
{
	/** Invoice is partial (non-final); allocate MIN(receipt × LC%, remainingPrepay). */
	PARTIAL,

	/** Invoice is final; allocate the entire remainingPrepay. */
	FINAL;

	/**
	 * Converts the {@code I_C_Invoice.isPartialInvoice()} boolean getter result to this enum.
	 *
	 * @param isPartialInvoice value of {@code invoice.isPartialInvoice()}
	 * @return {@link #PARTIAL} if {@code true}, {@link #FINAL} if {@code false}
	 */
	public static IsPartialInvoiceFlag fromBoolean(final boolean isPartialInvoice)
	{
		return isPartialInvoice ? PARTIAL : FINAL;
	}
}
