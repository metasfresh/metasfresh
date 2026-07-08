package de.metas.material.cockpit.stock.eventhandler;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.cockpit.stock.StockChangeSourceInfo;
import de.metas.material.cockpit.stock.StockDataRecordIdentifier;
import de.metas.material.cockpit.stock.StockDataUpdateRequest;
import de.metas.material.cockpit.stock.StockDataUpdateRequestHandler;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.attributes.AttributesChangedEvent;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;

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

/**
 * Handles {@link AttributesChangedEvent} for MD_Stock records.
 * Re-keys the MD_Stock qty from the old AttributesKey bucket to the new one
 * by issuing a FROM leg (subtract) and a TO leg (add).
 *
 * <p>No MRP-exclusion guard: MD_Stock tracks real on-hand qty regardless of
 * warehouse exclusion, consistent with the transaction-driven MD_Stock handler.</p>
 */
@Service
@Profile(Profiles.PROFILE_App)
public class AttributesChangedEventHandlerForStockRecords
		implements MaterialEventHandler<AttributesChangedEvent>
{
	private final StockDataUpdateRequestHandler dataUpdateRequestHandler;

	public AttributesChangedEventHandlerForStockRecords(
			@NonNull final StockDataUpdateRequestHandler dataUpdateRequestHandler)
	{
		this.dataUpdateRequestHandler = dataUpdateRequestHandler;
	}

	@Override
	public Collection<Class<? extends AttributesChangedEvent>> getHandledEventType()
	{
		return ImmutableList.of(AttributesChangedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final AttributesChangedEvent event)
	{
		final AttributesKey oldAttributesKey = event.getOldStorageAttributes().getAttributesKey();
		final AttributesKey newAttributesKey = event.getNewStorageAttributes().getAttributesKey();

		// The event fires on ANY attribute change, including non-storage-relevant ones,
		// which leave the storage AttributesKey unchanged (old == new, often both NONE).
		// Nothing moves between buckets, so skip — otherwise we'd issue two offsetting
		// MD_Stock deltas and fire spurious StockChangedEvents on every attribute change.
		// Note: the dispo-service sibling handler for this same event deliberately does NOT
		// skip old==new (it always creates FROM/TO candidates); here skipping is safe because
		// an old==new re-key nets to zero on a single MD_Stock row and only wastes two saves.
		if (oldAttributesKey.equals(newAttributesKey))
		{
			return;
		}

		final ProductId productId = ProductId.ofRepoId(event.getProductId());
		final BigDecimal qtyBD = event.getQty();
		final StockChangeSourceInfo sourceInfo = StockChangeSourceInfo.ofHuAttributeChange();

		// FROM leg: subtract qty from old bucket
		final StockDataRecordIdentifier fromIdentifier = StockDataRecordIdentifier.builder()
				.clientId(event.getEventDescriptor().getClientId())
				.orgId(event.getEventDescriptor().getOrgId())
				.warehouseId(event.getWarehouseId())
				.productId(productId)
				.storageAttributesKey(oldAttributesKey)
				.build();

		dataUpdateRequestHandler.handleDataUpdateRequest(StockDataUpdateRequest.builder()
				.identifier(fromIdentifier)
				.onHandQtyChange(qtyBD.negate())
				.sourceInfo(sourceInfo)
				.build());

		// TO leg: add qty to new bucket
		final StockDataRecordIdentifier toIdentifier = StockDataRecordIdentifier.builder()
				.clientId(event.getEventDescriptor().getClientId())
				.orgId(event.getEventDescriptor().getOrgId())
				.warehouseId(event.getWarehouseId())
				.productId(productId)
				.storageAttributesKey(newAttributesKey)
				.build();

		dataUpdateRequestHandler.handleDataUpdateRequest(StockDataUpdateRequest.builder()
				.identifier(toIdentifier)
				.onHandQtyChange(qtyBD)
				.sourceInfo(sourceInfo)
				.build());
	}
}
