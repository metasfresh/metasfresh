/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.material.cockpit.view.eventhandler;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.material.cockpit.view.MainDataRecordIdentifier;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.cockpit.view.mainrecord.UpdateMainDataRequest;
import de.metas.material.cockpit.view.mainrecord.UpdateMainDataRequest.UpdateMainDataRequestBuilder;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.transactions.AbstractTransactionEvent;
import de.metas.material.event.transactions.TransactionCreatedEvent;
import de.metas.material.event.transactions.TransactionDeletedEvent;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Collection;

@Service
@Profile(Profiles.PROFILE_App) // it's important to have just *one* instance of this listener, because on each event needs to be handled exactly once.
public class TransactionEventHandlerForCockpitRecords
		implements MaterialEventHandler<AbstractTransactionEvent>
{
	private static final Logger logger = LogManager.getLogger(TransactionEventHandlerForCockpitRecords.class);
	
	private final MainDataRequestHandler dataUpdateRequestHandler;
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	public TransactionEventHandlerForCockpitRecords(
			@NonNull final MainDataRequestHandler dataUpdateRequestHandler)
	{
		this.dataUpdateRequestHandler = dataUpdateRequestHandler;
	}

	@Override
	public Collection<Class<? extends AbstractTransactionEvent>> getHandledEventType()
	{
		return ImmutableList.of(
				TransactionCreatedEvent.class,
				TransactionDeletedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final AbstractTransactionEvent event)
	{
		// dropship-warehouse transactions bypass cockpit entirely —
		// the goods are shipped supplier → customer and never reach our warehouse.
		if (event.isIgnoreInMaterialDispo())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("Ignoring event with isIgnoreInMaterialDispo=true");
			return;
		}

		final UpdateMainDataRequest dataUpdateRequest = createDataUpdateRequestForEvent(event);
		dataUpdateRequestHandler.handleDataUpdateRequest(dataUpdateRequest);
	}

	private UpdateMainDataRequest createDataUpdateRequestForEvent(@NonNull final AbstractTransactionEvent event)
	{
		final OrgId orgId = event.getOrgId();
		final ZoneId timeZone = orgDAO.getTimeZone(orgId);

		final MainDataRecordIdentifier identifier = MainDataRecordIdentifier
				.createForMaterial(event.getMaterialDescriptor(), timeZone);

		final BigDecimal eventQuantity = event.getQuantityDelta();

		final UpdateMainDataRequestBuilder dataRequestBuilder = UpdateMainDataRequest.builder()
				.identifier(identifier)
				.onHandQtyChange(eventQuantity);

		if (event.isDirectMovementWarehouse())
		{
			dataRequestBuilder.directMovementQty(eventQuantity);
		}

		if (event.getInventoryLineId() > 0)
		{
			dataRequestBuilder.qtyInventoryCount(eventQuantity);
			dataRequestBuilder.qtyInventoryTime(event.getMaterialDescriptor().getDate());
		}

		return dataRequestBuilder.build();
	}
}
