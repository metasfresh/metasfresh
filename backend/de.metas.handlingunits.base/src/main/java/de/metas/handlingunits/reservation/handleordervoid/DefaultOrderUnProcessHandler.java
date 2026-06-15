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

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Has no spring-annotation. Its "manually" instantiated and made sure to be invoked last. 
 */
public class DefaultOrderUnProcessHandler implements IUReservationAfterOrderUnProcessHandler
{
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	private final HUReservationService huReservationService;

	public DefaultOrderUnProcessHandler(@NotNull final HUReservationService huReservationService)
	{
		this.huReservationService = huReservationService;
	}

	@Override
	public HandlerResult onOrderVoid(@NonNull final OrderId orderId, @NonNull final DocTimingType timing)
	{
		if (!timing.isVoid() && !timing.isReverse())
		{
			return HandlerResult.NOT_HANDELED; // don't auto-unreserve on reactivate, because we can also reserve while the order is in progress
		}
		
		final ImmutableSet.Builder<HUReservationDocRef> linesToUnreserve = ImmutableSet.builder();
		final List<OrderAndLineId> orderAndLineIds = orderDAO.retrieveAllOrderLineIds(orderId);
		for (final OrderAndLineId orderAndLineId : orderAndLineIds)
		{
			final HUReservationDocRef huReservationDocRef = HUReservationDocRef.ofSalesOrderLineId(orderAndLineId);
			linesToUnreserve.add(huReservationDocRef);
		}

		huReservationService.deleteReservationsByDocumentRefs(linesToUnreserve.build());
		return HandlerResult.HANDELED_CONTINUE; // although we don't expect another handler to run after us, I don't see why we should prohibit it
	}
}
