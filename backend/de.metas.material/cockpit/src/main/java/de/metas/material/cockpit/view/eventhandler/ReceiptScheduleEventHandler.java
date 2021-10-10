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
import de.metas.material.cockpit.view.detailrecord.RemoveDetailRequest.RemoveDetailRequestBuilder;
import de.metas.material.cockpit.view.detailrecord.UpdateDetailRequest;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.cockpit.view.mainrecord.UpdateMainDataRequest;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.material.event.receiptschedule.AbstractReceiptScheduleEvent;
import de.metas.material.event.receiptschedule.ReceiptScheduleCreatedEvent;
import de.metas.material.event.receiptschedule.ReceiptScheduleDeletedEvent;
import de.metas.material.event.receiptschedule.ReceiptScheduleUpdatedEvent;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
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
public class ReceiptScheduleEventHandler
		implements MaterialEventHandler<AbstractReceiptScheduleEvent>
{
	private static final Logger logger = LogManager.getLogger(ReceiptScheduleEventHandler.class);

	private final MainDataRequestHandler dataUpdateRequestHandler;
	private final DetailDataRequestHandler detailRequestHandler;
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	
	public ReceiptScheduleEventHandler(
			@NonNull final MainDataRequestHandler dataUpdateRequestHandler,
			@NonNull final DetailDataRequestHandler detailRequestHandler)
	{
		this.dataUpdateRequestHandler = dataUpdateRequestHandler;
		this.detailRequestHandler = detailRequestHandler;
	}

	@Override
	public Collection<Class<? extends AbstractReceiptScheduleEvent>> getHandledEventType()
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
		final OrgId orgId = event.getEventDescriptor().getOrgId();
		final ZoneId timeZone = orgDAO.getTimeZone(orgId);
		
		final MaterialDescriptor materialDescriptor = event.getMaterialDescriptor();
		final MainDataRecordIdentifier identifier = MainDataRecordIdentifier.createForMaterial(materialDescriptor, timeZone);

		createAndHandleMainDataEvent(event, identifier);

		createAndHandleDetailRequest(event, identifier);
	}

	private void createAndHandleMainDataEvent(
			@NonNull final AbstractReceiptScheduleEvent event,
			@NonNull final MainDataRecordIdentifier identifier)
	{
		if (event.getOrderedQuantityDelta().signum() == 0
				&& event.getReservedQuantityDelta().signum() == 0)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog(
					"Skipping this event because is has both orderedQuantityDelta and reservedQuantityDelta = zero");
			return;
		}
		final UpdateMainDataRequest request = UpdateMainDataRequest.builder()
				.identifier(identifier)
				.orderedPurchaseQty(event.getOrderedQuantityDelta())
				.qtySupplyPurchaseOrder(event.getReservedQuantityDelta())
				.build();

		dataUpdateRequestHandler.handleDataUpdateRequest(request);
	}

	private void createAndHandleDetailRequest(
			@NonNull final AbstractReceiptScheduleEvent receiptScheduleEvent,
			@NonNull final MainDataRecordIdentifier identifier)
	{
		final DetailDataRecordIdentifier detailIdentifier = DetailDataRecordIdentifier
				.createForReceiptSchedule(identifier, receiptScheduleEvent.getReceiptScheduleId());

		if (receiptScheduleEvent instanceof ReceiptScheduleCreatedEvent)
		{
			final ReceiptScheduleCreatedEvent receiptScheduleCreatedEvent = (ReceiptScheduleCreatedEvent)receiptScheduleEvent;
			createAndHandleAddDetailRequest(detailIdentifier, receiptScheduleCreatedEvent);
		}
		else if (receiptScheduleEvent instanceof ReceiptScheduleUpdatedEvent)
		{
			detailRequestHandler
					.handleUpdateDetailRequest(UpdateDetailRequest.builder()
							.detailDataRecordIdentifier(detailIdentifier)
							.qtyOrdered(receiptScheduleEvent.getMaterialDescriptor().getQuantity())
							.qtyReserved(receiptScheduleEvent.getReservedQuantity())
							.build());
		}
		else if (receiptScheduleEvent instanceof ReceiptScheduleDeletedEvent)
		{
			final RemoveDetailRequestBuilder removeDetailRequest = RemoveDetailRequest.builder()
					.detailDataRecordIdentifier(detailIdentifier);

			final int deletedCount = detailRequestHandler.handleRemoveDetailRequest(removeDetailRequest.build());
			Loggables.withLogger(logger, Level.DEBUG).addLog("Deleted {} detail records", deletedCount);
		}
	}

	private void createAndHandleAddDetailRequest(
			@NonNull final DetailDataRecordIdentifier identifier,
			@NonNull final ReceiptScheduleCreatedEvent receiptScheduleCreatedEvent)
	{
		final OrderLineDescriptor orderLineDescriptor = //
				receiptScheduleCreatedEvent.getOrderLineDescriptor();

		final InsertDetailRequestBuilder addDetailsRequest = InsertDetailRequest.builder()
				.detailDataRecordIdentifier(identifier);

		addDetailsRequest
				.qtyOrdered(receiptScheduleCreatedEvent.getMaterialDescriptor().getQuantity())
				.qtyReserved(receiptScheduleCreatedEvent.getReservedQuantity())
				.docTypeId(orderLineDescriptor.getDocTypeId())
				.orderId(orderLineDescriptor.getOrderId())
				.orderLineId(orderLineDescriptor.getOrderLineId())
				.bPartnerId(orderLineDescriptor.getOrderBPartnerId());

		detailRequestHandler.handleInsertDetailRequest(addDetailsRequest.build());
	}
}
