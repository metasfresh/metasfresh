package de.metas.material.cockpit.stock.eventhandler;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.cockpit.stock.StockChangeSourceInfo;
import de.metas.material.cockpit.stock.StockDataRecordIdentifier;
import de.metas.material.cockpit.stock.StockDataUpdateRequest;
import de.metas.material.cockpit.stock.StockDataUpdateRequestHandler;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.transactions.AbstractTransactionEvent;
import de.metas.material.event.transactions.TransactionCreatedEvent;
import de.metas.material.event.transactions.TransactionDeletedEvent;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

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

/**
 * Updates {@link de.metas.material.cockpit.model.I_MD_Stock} when {@link AbstractTransactionEvent}s happen.
 * Note that this can cause {@link de.metas.material.event.stock.StockChangedEvent}s to be fired in turn.
 */
@Service
@Profile(Profiles.PROFILE_App) // it's important to have just *one* instance of this listener, because on each event needs to be handled exactly once.
public class TransactionEventHandlerForStockRecords
		implements MaterialEventHandler<AbstractTransactionEvent>
{
	private final StockDataUpdateRequestHandler dataUpdateRequestHandler;

	public TransactionEventHandlerForStockRecords(
			@NonNull final StockDataUpdateRequestHandler dataUpdateRequestHandler)
	{
		this.dataUpdateRequestHandler = dataUpdateRequestHandler;
	}

	@Override
	public Collection<Class<? extends AbstractTransactionEvent>> getHandledEventType()
	{
		return ImmutableList.of(TransactionCreatedEvent.class, TransactionDeletedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final AbstractTransactionEvent event)
	{
		final StockDataUpdateRequest dataUpdateRequest = createDataUpdateRequestForEvent(event);
		dataUpdateRequestHandler.handleDataUpdateRequest(dataUpdateRequest);
	}

	private StockDataUpdateRequest createDataUpdateRequestForEvent(
			@NonNull final AbstractTransactionEvent event)
	{
		final MaterialDescriptor materialDescriptor = event.getMaterialDescriptor();

		final StockDataRecordIdentifier identifier = StockDataRecordIdentifier.builder()
				.clientId(event.getClientId())
				.orgId(event.getOrgId())
				.warehouseId(materialDescriptor.getWarehouseId())
				.productId(ProductId.ofRepoId(materialDescriptor.getProductId()))
				.storageAttributesKey(materialDescriptor.getStorageAttributesKey())
				.build();

		final StockChangeSourceInfo stockChangeSourceInfo = StockChangeSourceInfo.ofTransactionId(event.getTransactionId());

		return StockDataUpdateRequest.builder()
				.identifier(identifier)
				.onHandQtyChange(event.getQuantityDelta())
				.sourceInfo(stockChangeSourceInfo)
				.build();
	}
}
