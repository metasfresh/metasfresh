package de.metas.material.dispo.service.event;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import de.metas.material.dispo.service.event.handler.DistributionPlanEventHandler;
import de.metas.material.dispo.service.event.handler.ForecastEventHandler;
import de.metas.material.dispo.service.event.handler.ProductionPlanEventHandler;
import de.metas.material.dispo.service.event.handler.ReceiptScheduleEventHandler;
import de.metas.material.dispo.service.event.handler.ShipmentScheduleEventHandler;
import de.metas.material.dispo.service.event.handler.TransactionEventHandler;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventListener;
import de.metas.material.event.ReceiptScheduleEvent;
import de.metas.material.event.ShipmentScheduleEvent;
import de.metas.material.event.TransactionEvent;
import de.metas.material.event.ddorder.DistributionPlanEvent;
import de.metas.material.event.forecast.ForecastEvent;
import de.metas.material.event.pporder.ProductionPlanEvent;
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

	private final ProductionPlanEventHandler productionPlanEventHandler;

	private final DistributionPlanEventHandler distributionPlanEventHandler;

	private final ForecastEventHandler forecastEventHandler;

	private final TransactionEventHandler transactionEventHandler;

	private final ReceiptScheduleEventHandler receiptScheduleEventHandler;

	private final ShipmentScheduleEventHandler shipmentScheduleEventHandler;

	public MaterialDispoEventListenerFacade(
			@NonNull final DistributionPlanEventHandler distributionPlanEventHandler,
			@NonNull final ProductionPlanEventHandler productionPlanEventHandler,
			@NonNull final ForecastEventHandler forecastEventHandler,
			@NonNull final TransactionEventHandler transactionEventHandler,
			@NonNull final ReceiptScheduleEventHandler receiptScheduleEventHandler,
			@NonNull final ShipmentScheduleEventHandler shipmentScheduleEventHandler)
	{
		this.shipmentScheduleEventHandler = shipmentScheduleEventHandler;
		this.receiptScheduleEventHandler = receiptScheduleEventHandler;
		this.distributionPlanEventHandler = distributionPlanEventHandler;
		this.productionPlanEventHandler = productionPlanEventHandler;
		this.forecastEventHandler = forecastEventHandler;
		this.transactionEventHandler = transactionEventHandler;
	}

	@Override
	public void onEvent(@NonNull final MaterialEvent event)
	{
		if (event instanceof TransactionEvent)
		{
			transactionEventHandler.handleTransactionEvent((TransactionEvent)event);
		}
		else if (event instanceof ReceiptScheduleEvent)
		{
			receiptScheduleEventHandler.handleReceiptScheduleEvent((ReceiptScheduleEvent)event);
		}
		else if (event instanceof ShipmentScheduleEvent)
		{
			shipmentScheduleEventHandler.handleShipmentScheduleEvent((ShipmentScheduleEvent)event);
		}
		else if (event instanceof ForecastEvent)
		{
			forecastEventHandler.handleForecastEvent((ForecastEvent)event);
		}
		else if (event instanceof DistributionPlanEvent)
		{
			distributionPlanEventHandler.handleDistributionPlanEvent((DistributionPlanEvent)event);
		}
		else if (event instanceof ProductionPlanEvent)
		{
			productionPlanEventHandler.handleProductionPlanEvent((ProductionPlanEvent)event);
		}
	}
}
