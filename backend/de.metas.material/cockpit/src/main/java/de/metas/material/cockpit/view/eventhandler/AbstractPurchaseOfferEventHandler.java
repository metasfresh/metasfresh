package de.metas.material.cockpit.view.eventhandler;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.cockpit.view.MainDataRecordIdentifier;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.cockpit.view.mainrecord.UpdateMainDataRequest;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.procurement.AbstractPurchaseOfferEvent;
import de.metas.material.event.procurement.PurchaseOfferCreatedEvent;
import de.metas.material.event.procurement.PurchaseOfferDeletedEvent;
import de.metas.material.event.procurement.PurchaseOfferUpdatedEvent;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.util.TimeUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
public class AbstractPurchaseOfferEventHandler
		implements MaterialEventHandler<AbstractPurchaseOfferEvent>
{
	private final MainDataRequestHandler dataUpdateRequestHandler;
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	
	public AbstractPurchaseOfferEventHandler(
			@NonNull final MainDataRequestHandler dataUpdateRequestHandler)
	{
		this.dataUpdateRequestHandler = dataUpdateRequestHandler;
	}

	@Override
	public Collection<Class<? extends AbstractPurchaseOfferEvent>> getHandledEventType()
	{
		return ImmutableList.of(
				PurchaseOfferCreatedEvent.class,
				PurchaseOfferUpdatedEvent.class,
				PurchaseOfferDeletedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final AbstractPurchaseOfferEvent event)
	{
		final UpdateMainDataRequest dataUpdateRequest = createDataUpdateRequestForEvent(event, false);
		dataUpdateRequestHandler.handleDataUpdateRequest(dataUpdateRequest);

		final UpdateMainDataRequest dataUpdateRequestForNextDayQty = createDataUpdateRequestForEvent(event, true);
		dataUpdateRequestHandler.handleDataUpdateRequest(dataUpdateRequestForNextDayQty);
	}

	private UpdateMainDataRequest createDataUpdateRequestForEvent(
			@NonNull final AbstractPurchaseOfferEvent purchaseOfferedEvent,
			final boolean forNextDayQty)
	{
		final OrgId orgId = purchaseOfferedEvent.getEventDescriptor().getOrgId();
		final ZoneId timeZone = orgDAO.getTimeZone(orgId);

		final Instant date = TimeUtil.getDay(purchaseOfferedEvent.getDate(), timeZone);
		final Instant dateToUse = forNextDayQty ? date.minus(1, ChronoUnit.DAYS) : date;
		final MainDataRecordIdentifier identifier = MainDataRecordIdentifier.builder()
				.productDescriptor(purchaseOfferedEvent.getProductDescriptor())
				.date(dateToUse)
				.build();

		final UpdateMainDataRequest.UpdateMainDataRequestBuilder request = UpdateMainDataRequest.builder()
				.identifier(identifier);

		if (forNextDayQty)
		{
			request.offeredQtyNextDay(purchaseOfferedEvent.getQtyDelta());
		}
		else
		{
			request.offeredQty(purchaseOfferedEvent.getQtyDelta());
		}
		return request.build();
	}
}
