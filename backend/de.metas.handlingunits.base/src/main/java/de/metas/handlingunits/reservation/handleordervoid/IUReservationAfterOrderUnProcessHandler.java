/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.handlingunits.reservation.handleordervoid;

import de.metas.order.OrderId;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.DocTimingType;

/**
 * To be implemented everywhere in metasfresh where any handling of HU-reservations is needed as an order is reactivated, reversed, or voided. 
 */
public interface IUReservationAfterOrderUnProcessHandler
{
	HandlerResult onOrderVoid(@NonNull final OrderId orderId, @NonNull final DocTimingType timing);
}
