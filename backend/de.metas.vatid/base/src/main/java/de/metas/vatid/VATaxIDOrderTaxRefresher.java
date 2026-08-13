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
 * Reacts to a VAT-ID status change the check process just discovered: refreshes
 * {@code C_OrderLine.C_Tax_ID} on the partner's orders that are not yet completed.
 *
 * <p>The base half declares only this seam. Refreshing an order line's tax needs
 * {@code de.metas.order}'s {@code OrderLineBL} / {@code IOrderDAO}, both of which live in
 * {@code de.metas.business} — a module that already depends on this one (via
 * {@code de.metas.tax.api.impl.TaxDAO}, see {@code IBPartnerBL#getVATaxIDStatusCode}). {@code de.metas.vatid}
 * therefore cannot depend on {@code de.metas.business} without a cycle, so the implementation lives there
 * instead and is resolved through Spring — exactly the same base-seam / other-half-implementation split as
 * {@link VATaxIDOnlineChecker} (base half) / {@code VIESClient} (vies half).
 *
 * <p>Called only when a check just discovered a genuine status change — including the very first check
 * ({@link VATaxIDStatus#NotChecked} to any other status) — never on a re-check that only reconfirms the
 * previous status, since nothing about the partner's tax situation changed then.
 *
 * <p>Invoice candidates are deliberately out of scope here — this seam is order lines only. Invalidating a
 * partner's unprocessed invoice candidates on the same status change is a separate, not-yet-scheduled
 * concern with its own machinery elsewhere in the codebase; nothing here should be extended to cover it
 * without that being a deliberate, separately reviewed decision.
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
