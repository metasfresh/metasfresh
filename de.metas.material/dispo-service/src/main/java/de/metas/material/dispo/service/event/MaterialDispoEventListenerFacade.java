package de.metas.material.dispo.service.event;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import de.metas.material.dispo.service.event.handler.DistributionAdvisedHandler;
import de.metas.material.dispo.service.event.handler.ForecastCreatedHandler;
import de.metas.material.dispo.service.event.handler.ProductionAdvisedHandler;
import de.metas.material.dispo.service.event.handler.ShipmentScheduleCreatedHandler;
import de.metas.material.dispo.service.event.handler.TransactionCreatedHandler;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventListener;
import de.metas.material.event.ddorder.DistributionAdvisedEvent;
import de.metas.material.event.forecast.ForecastCreatedEvent;
import de.metas.material.event.pporder.ProductionAdvisedEvent;
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

	private final ProductionAdvisedHandler productionPlanEventHandler;

	private final DistributionAdvisedHandler distributionPlanEventHandler;

	private final ForecastCreatedHandler forecastEventHandler;

	private final TransactionCreatedHandler transactionEventHandler;

	private final ShipmentScheduleCreatedHandler shipmentScheduleEventHandler;

	public MaterialDispoEventListenerFacade(
			@NonNull final DistributionAdvisedHandler distributionPlanEventHandler,
			@NonNull final ProductionAdvisedHandler productionPlanEventHandler,
			@NonNull final ForecastCreatedHandler forecastEventHandler,
			@NonNull final TransactionCreatedHandler transactionEventHandler,
			@NonNull final ShipmentScheduleCreatedHandler shipmentScheduleEventHandler)
	{
		this.shipmentScheduleEventHandler = shipmentScheduleEventHandler;
		this.distributionPlanEventHandler = distributionPlanEventHandler;
		this.productionPlanEventHandler = productionPlanEventHandler;
		this.forecastEventHandler = forecastEventHandler;
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
			shipmentScheduleEventHandler.handleShipmentScheduleCreatedEvent((ShipmentScheduleCreatedEvent)event);
		}
		else if (event instanceof ForecastCreatedEvent)
		{
			forecastEventHandler.handleForecastCreatedEvent((ForecastCreatedEvent)event);
		}
		else if (event instanceof DistributionAdvisedEvent)
		{
			distributionPlanEventHandler.handleDistributionAdvisedEvent((DistributionAdvisedEvent)event);
		}
		else if (event instanceof ProductionAdvisedEvent)
		{
			productionPlanEventHandler.handleProductionAdvisedEvent((ProductionAdvisedEvent)event);
		}
	}
}
