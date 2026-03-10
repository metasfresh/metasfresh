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

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.order.OrderId;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Invokes all handlers, making sure to invoke {@link DefaultOrderUnProcessHandler} last.
 */
@Service
public class HUReservationAfterOrderUnProcessService
{
	private final ImmutableList<IUReservationAfterOrderUnProcessHandler> injectedHandlers;
	private final DefaultOrderUnProcessHandler defaultHandler;

	public HUReservationAfterOrderUnProcessService(
			@NonNull final List<IUReservationAfterOrderUnProcessHandler> injectedHandlers,
			@NonNull final HUReservationService huReservationService)
	{
		this.injectedHandlers = ImmutableList.copyOf(injectedHandlers);
		this.defaultHandler = new DefaultOrderUnProcessHandler(huReservationService);
	}

	/**
	 * Supposed to be called from a model interceptor
	 */
	public void handleHUReservationsAfterOrderUnprocess(@NonNull final OrderId orderId, @NonNull final DocTimingType timing)
	{
		HandlerResult lastInjectedHandlerResult = null;
		for (final IUReservationAfterOrderUnProcessHandler handler : injectedHandlers)
		{
			final HandlerResult result = handler.onOrderVoid(orderId, timing);
			lastInjectedHandlerResult = result;
			
			if (result == HandlerResult.HANDELED_DONE)
			{
				break;
			}
		}
		if (lastInjectedHandlerResult != HandlerResult.HANDELED_DONE) 
		{ // note that null is also !=HANDELED_DONE
			defaultHandler.onOrderVoid(orderId, timing);
		}
	}
}
