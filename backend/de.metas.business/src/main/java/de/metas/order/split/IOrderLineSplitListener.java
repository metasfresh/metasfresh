/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.order.split;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.OrderLineId;
import lombok.NonNull;

/**
 * SPI for cross-module side-effects that must fire after an order line split.
 * <p>
 * Defined here (de.metas.business) so that {@link OrderLineSplitCommand} can call it without
 * creating a circular dependency on de.metas.swat.base.
 * <p>
 * de.metas.swat.base registers the real implementation that creates shipment schedules,
 * invalidates invoice candidates, and shrinks qty-reservations.
 */
public interface IOrderLineSplitListener
{
	/**
	 * Schedules creation of the missing M_ShipmentSchedule row for the newly-cloned order line.
	 */
	void onNewLineSaved(@NonNull I_C_OrderLine newLine);

	/**
	 * Shrinks M_QtyReservation rows on the original (now-reduced) order line so that
	 * Σ reserved-qty ≤ (QtyOrdered − QtyDelivered).
	 */
	void onOriginalLineReduced(@NonNull OrderLineId originalOrderLineId);
}
