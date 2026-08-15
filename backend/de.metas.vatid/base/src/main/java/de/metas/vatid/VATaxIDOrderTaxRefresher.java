/*
 * #%L
 * de.metas.vatid
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

package de.metas.vatid;

import de.metas.bpartner.BPartnerId;
import lombok.NonNull;

/**
 * Reacts to a VAT-ID status change by refreshing {@code C_OrderLine.C_Tax_ID} on the partner's not-yet-
 * completed orders.
 *
 * <p>The base half declares only this seam. The implementation needs {@code de.metas.order}'s
 * {@code OrderLineBL} / {@code IOrderDAO} from {@code de.metas.business}, which already depends on this
 * module, so it lives there and is resolved through Spring — the same split as
 * {@link VATaxIDOnlineChecker} (base) / {@code VIESClient} (vies).
 *
 * <p>Called only on a genuine status change, including the first check
 * ({@link VATaxIDStatus#NotChecked} to anything else), never on a re-check that reconfirms the previous
 * status.
 *
 * <p>Invoice candidates are deliberately out of scope — that is a separate, not-yet-scheduled concern with
 * its own machinery, and extending this seam to cover it needs its own review.
 */
public interface VATaxIDOrderTaxRefresher
{
	/**
	 * Refreshes {@code C_OrderLine.C_Tax_ID} for every line of every order of {@code bpartnerId} that is
	 * not yet {@code Completed} or {@code Closed}. A completed or closed order is never touched — rewriting
	 * the tax of an already-final document would rewrite history.
	 */
	void refreshOrderLinesTaxForBPartner(@NonNull BPartnerId bpartnerId);
}
