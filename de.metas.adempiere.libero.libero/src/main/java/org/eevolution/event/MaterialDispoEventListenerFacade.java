package org.eevolution.event;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import de.metas.logging.LogManager;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventListener;
import de.metas.material.event.ddorder.DDOrderRequestedEvent;
import de.metas.material.event.pporder.PPOrderRequestedEvent;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-mrp
 * %%
 * Copyright (C) 2017 metas GmbH
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
/**
 * This listener is dedicated to handle {@link PPOrderRequestedEvent} and {@link DDOrderRequestedEvent}s.<br>
 * It ignores and other {@link MaterialEvent}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Service
public class MaterialDispoEventListenerFacade implements MaterialEventListener
{
	private static final transient Logger logger = LogManager.getLogger(MaterialDispoEventListenerFacade.class);

	private final PPOrderRequestedEventHandler ppOrderRequestedEventHandler;

	private final DDOrderRequestedEventHandler ddOrderRequestedEventHandler;

	public MaterialDispoEventListenerFacade(
			@NonNull final PPOrderRequestedEventHandler ppOrderRequestedEventHandler,
			@NonNull final DDOrderRequestedEventHandler ddOrderRequestedEventHandler)
	{
		this.ppOrderRequestedEventHandler = ppOrderRequestedEventHandler;
		this.ddOrderRequestedEventHandler = ddOrderRequestedEventHandler;
	}

	@Override
	public void onEvent(@NonNull final MaterialEvent event)
	{
		if (event instanceof PPOrderRequestedEvent)
		{
			logger.info("Received event {}", event);

			final PPOrderRequestedEvent ppOrderRequestedEvent = (PPOrderRequestedEvent)event;
			ppOrderRequestedEventHandler.createProductionOrderInTrx(ppOrderRequestedEvent);
		}
		else if (event instanceof DDOrderRequestedEvent)
		{
			logger.info("Received event {}", event);

			final DDOrderRequestedEvent distributionOrderEvent = (DDOrderRequestedEvent)event;
			ddOrderRequestedEventHandler.createDDOrderInTrx(distributionOrderEvent);
		}
	}
}
