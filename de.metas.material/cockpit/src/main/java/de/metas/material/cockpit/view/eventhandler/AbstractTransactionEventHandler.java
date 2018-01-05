package de.metas.material.cockpit.view.eventhandler;

import java.math.BigDecimal;
import java.util.Collection;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
import de.metas.material.cockpit.view.DataRecordIdentifier;
import de.metas.material.cockpit.view.DataUpdateRequest;
import de.metas.material.cockpit.view.DataUpdateRequestHandler;
import de.metas.material.cockpit.view.DataUpdateRequest.DataUpdateRequestBuilder;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.transactions.AbstractTransactionEvent;
import de.metas.material.event.transactions.TransactionCreatedEvent;
import de.metas.material.event.transactions.TransactionDeletedEvent;
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
public class AbstractTransactionEventHandler
		implements MaterialEventHandler<AbstractTransactionEvent>
{
	private final DataUpdateRequestHandler dataUpdateRequestHandler;

	public AbstractTransactionEventHandler(
			@NonNull final DataUpdateRequestHandler dataUpdateRequestHandler)
	{
		this.dataUpdateRequestHandler = dataUpdateRequestHandler;
	}

	@Override
	public Collection<Class<? extends AbstractTransactionEvent>> getHandeledEventType()
	{
		return ImmutableList.of(
				TransactionCreatedEvent.class,
				TransactionDeletedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final AbstractTransactionEvent event)
	{
		final DataUpdateRequest dataUpdateRequest = createDataUpdateRequestForEvent(event);
		dataUpdateRequestHandler.handleDataUpdateRequest(dataUpdateRequest);
	}

	private DataUpdateRequest createDataUpdateRequestForEvent(
			@NonNull final AbstractTransactionEvent transactionEvent)
	{
		final DataRecordIdentifier identifier = DataRecordIdentifier
				.createForMaterial(transactionEvent.getMaterialDescriptor());

		final BigDecimal eventQuantity = transactionEvent.getQuantityDelta();

		final DataUpdateRequestBuilder dataRequestBuilder = DataUpdateRequest.builder()
				.identifier(identifier)
				.onHandQtyChange(eventQuantity);

		if (transactionEvent.isDirectMovementWarehouse())
		{
			dataRequestBuilder.directMovementQty(eventQuantity);
		}
		return dataRequestBuilder.build();
	}

}
