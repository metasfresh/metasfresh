package de.metas.material.dispo.service.event;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import de.metas.material.dispo.service.event.handler.DDOrderAdvisedOrCreatedHandler;
import de.metas.material.dispo.service.event.handler.ForecastCreatedHandler;
import de.metas.material.dispo.service.event.handler.PPOrderAdvisedOrCreatedHandler;
import de.metas.material.dispo.service.event.handler.ShipmentScheduleCreatedHandler;
import de.metas.material.dispo.service.event.handler.TransactionCreatedHandler;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventListener;
import de.metas.material.event.ddorder.DDOrderAdvisedOrCreatedEvent;
import de.metas.material.event.forecast.ForecastCreatedEvent;
import de.metas.material.event.pporder.PPOrderAdvisedOrCreatedEvent;
import de.metas.material.event.shipmentschedule.ShipmentScheduleCreatedEvent;
import de.metas.material.event.transactions.TransactionCreatedEvent;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-manufacturing-dispo
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
@Service
@Lazy
public class MaterialDispoEventListenerFacade implements MaterialEventListener
{

	private final PPOrderAdvisedOrCreatedHandler productionAdvisedHandler;

	private final DDOrderAdvisedOrCreatedHandler distributionAdvisedHandler;

	private final ForecastCreatedHandler forecastCreatedHandler;

	private final TransactionCreatedHandler transactionEventHandler;

	private final ShipmentScheduleCreatedHandler shipmentScheduleCreatedHandler;

	public MaterialDispoEventListenerFacade(
			@NonNull final DDOrderAdvisedOrCreatedHandler distributionAdvisedHandler,
			@NonNull final PPOrderAdvisedOrCreatedHandler productionAdvisedHandler,
			@NonNull final ForecastCreatedHandler forecastCreatedHandler,
			@NonNull final TransactionCreatedHandler transactionEventHandler,
			@NonNull final ShipmentScheduleCreatedHandler shipmentScheduleCreatedHandler)
	{
		this.shipmentScheduleCreatedHandler = shipmentScheduleCreatedHandler;
		this.distributionAdvisedHandler = distributionAdvisedHandler;
		this.productionAdvisedHandler = productionAdvisedHandler;
		this.forecastCreatedHandler = forecastCreatedHandler;
		this.transactionEventHandler = transactionEventHandler;
	}

	@Override
	public void onEvent(@NonNull final MaterialEvent event)
	{
		if (event instanceof TransactionCreatedEvent)
		{
			transactionEventHandler.handleTransactionCreatedEvent((TransactionCreatedEvent)event);
		}
		else if (event instanceof ShipmentScheduleCreatedEvent)
		{
			shipmentScheduleCreatedHandler.handleShipmentScheduleCreatedEvent((ShipmentScheduleCreatedEvent)event);
		}
		else if (event instanceof ForecastCreatedEvent)
		{
			forecastCreatedHandler.handleForecastCreatedEvent((ForecastCreatedEvent)event);
		}
		else if (event instanceof DDOrderAdvisedOrCreatedEvent)
		{
			distributionAdvisedHandler.handleDDOrderAdvisedOrCreatedEvent((DDOrderAdvisedOrCreatedEvent)event);
		}
		else if (event instanceof PPOrderAdvisedOrCreatedEvent)
		{
			productionAdvisedHandler.handlePPOrderAdvisedOrCreatedEvent((PPOrderAdvisedOrCreatedEvent)event);
		}
	}
}
