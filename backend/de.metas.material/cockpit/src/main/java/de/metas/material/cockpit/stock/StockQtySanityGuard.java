package de.metas.material.cockpit.stock;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

/*
 * #%L
 * metasfresh-material-cockpit
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

@UtilityClass
public class StockQtySanityGuard
{
	/**
	 * Magnitude above which a {@code QtyOnHand} is treated as non-physical and rejected.
	 * <p>
	 * 1e12 (a trillion stocking units) is far above any plausible real on-hand quantity (the largest
	 * HU-derived truth in the me03 #30569 incident was ~28430), yet far below the escalated 50–1267-digit
	 * values the runaway reset loop produced — so it catches the escalation without risking false
	 * positives on legitimate stock. Adjust only with evidence of a larger legitimate quantity.
	 */
	static final BigDecimal MAX_PLAUSIBLE_QTY_ON_HAND = new BigDecimal("1E12");

	/** @return {@code false} when {@code qtyOnHand} is non-physically large (the #30569 escalation). */
	public boolean isPlausibleQtyOnHand(final BigDecimal qtyOnHand)
	{
		return qtyOnHand.abs().compareTo(MAX_PLAUSIBLE_QTY_ON_HAND) <= 0;
	}
}
