package de.metas.material.cockpit.view.eventhandler;

import java.util.Collection;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
import de.metas.material.cockpit.view.DataRecordIdentifier;
import de.metas.material.cockpit.view.DataUpdateRequest;
import de.metas.material.cockpit.view.DataUpdateRequestHandler;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.shipmentschedule.AbstractShipmentScheduleEvent;
import de.metas.material.event.shipmentschedule.ShipmentScheduleCreatedEvent;
import de.metas.material.event.shipmentschedule.ShipmentScheduleDeletedEvent;
import de.metas.material.event.shipmentschedule.ShipmentScheduleUpdatedEvent;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-cockpit
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
@Profile(Profiles.PROFILE_App) // it's important to have just *one* instance of this listener, because on each event needs to be handled exactly once.
public class AbstractShipmentScheduleEventHandler
		implements MaterialEventHandler<AbstractShipmentScheduleEvent>
{
	private final DataUpdateRequestHandler dataUpdateRequestHandler;

	public AbstractShipmentScheduleEventHandler(
			@NonNull final DataUpdateRequestHandler dataUpdateRequestHandler)
	{
		this.dataUpdateRequestHandler = dataUpdateRequestHandler;
	}

	@Override
	public Collection<Class<? extends AbstractShipmentScheduleEvent>> getHandeledEventType()
	{
		return ImmutableList.of(ShipmentScheduleCreatedEvent.class,
				ShipmentScheduleUpdatedEvent.class,
				ShipmentScheduleDeletedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final AbstractShipmentScheduleEvent event)
	{
		final DataUpdateRequest dataUpdateRequest= createDataUpdateRequestForEvent(event);
		dataUpdateRequestHandler.handleDataUpdateRequest(dataUpdateRequest);
	}

	private DataUpdateRequest createDataUpdateRequestForEvent(
			@NonNull final AbstractShipmentScheduleEvent shipmentScheduleEvent)
	{
		final MaterialDescriptor orderedMaterial = shipmentScheduleEvent.getOrderedMaterial();

		final DataRecordIdentifier identifier = DataRecordIdentifier.createForMaterial(orderedMaterial);

		final DataUpdateRequest request = DataUpdateRequest.builder()
				.identifier(identifier)
				.orderedSalesQty(shipmentScheduleEvent.getOrderedQuantityDelta())
				.reservedSalesQty(shipmentScheduleEvent.getReservedQuantityDelta())
				.build();
		return request;
	}
}
