package de.metas.material.cockpit.view.eventhandler;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.material.cockpit.view.DetailDataRecordIdentifier;
import de.metas.material.cockpit.view.MainDataRecordIdentifier;
import de.metas.material.cockpit.view.detailrecord.DetailDataRequestHandler;
import de.metas.material.cockpit.view.detailrecord.InsertDetailRequest;
import de.metas.material.cockpit.view.detailrecord.InsertDetailRequest.InsertDetailRequestBuilder;
import de.metas.material.cockpit.view.detailrecord.RemoveDetailRequest;
import de.metas.material.cockpit.view.detailrecord.UpdateDetailRequest;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.cockpit.view.mainrecord.UpdateMainDataRequest;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.commons.DocumentLineDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.material.event.commons.SubscriptionLineDescriptor;
import de.metas.material.event.shipmentschedule.AbstractShipmentScheduleEvent;
import de.metas.material.event.shipmentschedule.ShipmentScheduleCreatedEvent;
import de.metas.material.event.shipmentschedule.ShipmentScheduleDeletedEvent;
import de.metas.material.event.shipmentschedule.ShipmentScheduleUpdatedEvent;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Collection;

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
public class ShipmentScheduleEventHandler
		implements MaterialEventHandler<AbstractShipmentScheduleEvent>
{
	private static final Logger logger = LogManager.getLogger(ShipmentScheduleEventHandler.class);

	private final MainDataRequestHandler dataUpdateRequestHandler;
	private final DetailDataRequestHandler detailRequestHandler;
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	
	public ShipmentScheduleEventHandler(
			@NonNull final MainDataRequestHandler dataUpdateRequestHandler,
			@NonNull final DetailDataRequestHandler detailRequestHandler)
	{
		this.detailRequestHandler = detailRequestHandler;
		this.dataUpdateRequestHandler = dataUpdateRequestHandler;
	}

	@Override
	public Collection<Class<? extends AbstractShipmentScheduleEvent>> getHandledEventType()
	{
		return ImmutableList.of(
				ShipmentScheduleCreatedEvent.class,
				ShipmentScheduleUpdatedEvent.class,
				ShipmentScheduleDeletedEvent.class);
	}

	@Override
	public void validateEvent(@NonNull final AbstractShipmentScheduleEvent event)
	{
		event.validate();
	}

	@Override
	public void handleEvent(@NonNull final AbstractShipmentScheduleEvent event)
	{
		final OrgId orgId = event.getEventDescriptor().getOrgId();
		final ZoneId timeZone = orgDAO.getTimeZone(orgId);
		
		final MaterialDescriptor materialDescriptor = event.getMaterialDescriptor();
		final MainDataRecordIdentifier identifier = MainDataRecordIdentifier.createForMaterial(materialDescriptor, timeZone);

		createAndHandleMainDataRequest(event, identifier);
		createAndHandleDetailRequest(event, identifier);
	}

	private void createAndHandleMainDataRequest(
			@NonNull final AbstractShipmentScheduleEvent shipmentScheduleEvent,
			@NonNull final MainDataRecordIdentifier identifier)
	{
		if (shipmentScheduleEvent.getOrderedQuantityDelta().signum() == 0
				&& shipmentScheduleEvent.getReservedQuantityDelta().signum() == 0)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("Skipping this event because is has both orderedQuantityDelta and reservedQuantityDelta = zero");
			return;
		}

		final UpdateMainDataRequest request = UpdateMainDataRequest.builder()
				.identifier(identifier)
				.orderedSalesQty(shipmentScheduleEvent.getOrderedQuantityDelta())
				.qtyDemandSalesOrder(shipmentScheduleEvent.getReservedQuantityDelta())
				.build();
		dataUpdateRequestHandler.handleDataUpdateRequest(request);
	}

	private void createAndHandleDetailRequest(
			@NonNull final AbstractShipmentScheduleEvent shipmentScheduleEvent,
			@NonNull final MainDataRecordIdentifier identifier)
	{
		final DetailDataRecordIdentifier detailIdentifier = DetailDataRecordIdentifier.createForShipmentSchedule(
				identifier,
				shipmentScheduleEvent.getShipmentScheduleId());

		if (shipmentScheduleEvent instanceof ShipmentScheduleCreatedEvent)
		{
			final ShipmentScheduleCreatedEvent shipmentScheduleCreatedEvent = (ShipmentScheduleCreatedEvent)shipmentScheduleEvent;
			createAndHandleAddDetailRequest(detailIdentifier, shipmentScheduleCreatedEvent);
		}
		else if (shipmentScheduleEvent instanceof ShipmentScheduleUpdatedEvent)
		{
			detailRequestHandler
					.handleUpdateDetailRequest(UpdateDetailRequest.builder()
							.detailDataRecordIdentifier(detailIdentifier)
							.qtyOrdered(shipmentScheduleEvent.getMaterialDescriptor().getQuantity())
							.qtyReserved(shipmentScheduleEvent.getReservedQuantity())
							.build());
		}
		else if (shipmentScheduleEvent instanceof ShipmentScheduleDeletedEvent)
		{
			final int deletedCount = detailRequestHandler
					.handleRemoveDetailRequest(RemoveDetailRequest.builder()
							.detailDataRecordIdentifier(detailIdentifier)
							.build());
			Loggables.withLogger(logger, Level.DEBUG).addLog("Deleted {} detail records", deletedCount);
		}
	}

	private void createAndHandleAddDetailRequest(
			@NonNull final DetailDataRecordIdentifier identifier,
			@NonNull final ShipmentScheduleCreatedEvent shipmentScheduleCreatedEvent)
	{
		final DocumentLineDescriptor documentLineDescriptor = //
				shipmentScheduleCreatedEvent
						.getDocumentLineDescriptor();

		final InsertDetailRequestBuilder addDetailsRequest = InsertDetailRequest.builder()
				.detailDataRecordIdentifier(identifier)
				.qtyOrdered(shipmentScheduleCreatedEvent.getMaterialDescriptor().getQuantity())
				.qtyReserved(shipmentScheduleCreatedEvent.getReservedQuantity());

		if (documentLineDescriptor instanceof OrderLineDescriptor)
		{
			final OrderLineDescriptor orderLineDescriptor = //
					(OrderLineDescriptor)documentLineDescriptor;
			addDetailsRequest
					.orderId(orderLineDescriptor.getOrderId())
					.orderLineId(orderLineDescriptor.getOrderLineId())
					.bPartnerId(orderLineDescriptor.getOrderBPartnerId())
					.docTypeId(orderLineDescriptor.getDocTypeId());
		}
		else if (documentLineDescriptor instanceof SubscriptionLineDescriptor)
		{
			final SubscriptionLineDescriptor subscriptionLineDescriptor = //
					(SubscriptionLineDescriptor)documentLineDescriptor;
			addDetailsRequest
					.subscriptionId(subscriptionLineDescriptor.getFlatrateTermId())
					.subscriptionLineId(subscriptionLineDescriptor.getSubscriptionProgressId())
					.bPartnerId(subscriptionLineDescriptor.getSubscriptionBillBPartnerId());
		}
		else
		{
			Check.errorIf(true,
					"The DocumentLineDescriptor has an unexpected type; documentLineDescriptor={}", documentLineDescriptor);
		}

		detailRequestHandler.handleInsertDetailRequest(addDetailsRequest.build());
	}
}
