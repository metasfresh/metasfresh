package de.metas.material.cockpit.view.eventhandler;

import java.util.Collection;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
import de.metas.event.log.EventLogUserService;
import de.metas.material.cockpit.view.DataRecordIdentifier;
import de.metas.material.cockpit.view.DataUpdateRequest;
import de.metas.material.cockpit.view.DataUpdateRequestHandler;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.receiptschedule.AbstractReceiptScheduleEvent;
import de.metas.material.event.receiptschedule.ReceiptScheduleCreatedEvent;
import de.metas.material.event.receiptschedule.ReceiptScheduleDeletedEvent;
import de.metas.material.event.receiptschedule.ReceiptScheduleUpdatedEvent;
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
public class ReceiptScheduleEventHandler
		implements MaterialEventHandler<AbstractReceiptScheduleEvent>
{

	private final DataUpdateRequestHandler dataUpdateRequestHandler;
	private final EventLogUserService eventLogUserService;

	public ReceiptScheduleEventHandler(
			@NonNull final DataUpdateRequestHandler dataUpdateRequestHandler,
			@NonNull final EventLogUserService eventLogUserService)
	{
		this.dataUpdateRequestHandler = dataUpdateRequestHandler;
		this.eventLogUserService = eventLogUserService;

	}

	@Override
	public Collection<Class<? extends AbstractReceiptScheduleEvent>> getHandeledEventType()
	{
		return ImmutableList.of(
				ReceiptScheduleCreatedEvent.class,
				ReceiptScheduleUpdatedEvent.class,
				ReceiptScheduleDeletedEvent.class);
	}

	@Override
	public void validateEvent(@NonNull final AbstractReceiptScheduleEvent event)
	{
		event.validate();
	}

	@Override
	public void handleEvent(@NonNull final AbstractReceiptScheduleEvent event)
	{
		final MaterialDescriptor orderedMaterial = event.getMaterialDescriptor();
		final DataRecordIdentifier identifier = DataRecordIdentifier.createForMaterial(orderedMaterial);

		if (event.getOrderedQuantityDelta().signum() == 0
				&& event.getReservedQuantityDelta().signum() == 0)
		{
			eventLogUserService.newLogEntry(this.getClass())
					.message("Skipping this event because is has both orderedQuantityDelta and reservedQuantityDelta = zero")
					.storeEntry();
			return;
		}

		final DataUpdateRequest request = DataUpdateRequest.builder()
				.identifier(identifier)
				.orderedPurchaseQty(event.getOrderedQuantityDelta())
				.reservedPurchaseQty(event.getReservedQuantityDelta())
				.build();

		dataUpdateRequestHandler.handleDataUpdateRequest(request);
	}

}
