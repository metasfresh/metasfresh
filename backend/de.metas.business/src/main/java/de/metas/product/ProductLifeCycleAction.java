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

package de.metas.product;

/**
 * Business actions gated by {@link BBSStatus} (product life-cycle status).
 *
 * @see BBSStatus#isAllowed(ProductLifeCycleAction)
 */
public enum ProductLifeCycleAction
{
	/** Purchase candidate creation / PO line. */
	PURCHASE,
	/** Sales order line. */
	SELL,
	/** Picking (mobile or desktop). */
	PICK,
	/** Manufacturing order (PP_Order) for this product. */
	MANUFACTURE,
	/** Shipment completion (M_InOut BEFORE_COMPLETE, exempting reversals). */
	SHIP
}
