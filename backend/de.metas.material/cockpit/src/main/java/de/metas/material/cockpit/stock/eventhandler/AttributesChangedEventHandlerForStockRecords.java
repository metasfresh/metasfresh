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
import org.compiere.SpringContextHolder;
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
 * warehouse exclusion (see cockpit/CLAUDE.md "Warehouse exclusion").</p>
 */
@Service
@Profile(Profiles.PROFILE_App)
public class AttributesChangedEventHandlerForStockRecords
		implements MaterialEventHandler<AttributesChangedEvent>
{
	private final StockDataUpdateRequestHandler dataUpdateRequestHandler;

	/** Spring-managed constructor (production). */
	public AttributesChangedEventHandlerForStockRecords(
			@NonNull final StockDataUpdateRequestHandler dataUpdateRequestHandler)
	{
		this.dataUpdateRequestHandler = dataUpdateRequestHandler;
	}

	/** Test constructor: obtains the handler from the SpringContextHolder JUnit bean registry. */
	AttributesChangedEventHandlerForStockRecords()
	{
		this(SpringContextHolder.instance.getBean(StockDataUpdateRequestHandler.class));
	}

	@Override
	public Collection<Class<? extends AttributesChangedEvent>> getHandledEventType()
	{
		return ImmutableList.of(AttributesChangedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final AttributesChangedEvent event)
	{
		final ProductId productId = ProductId.ofRepoId(event.getProductId());
		final BigDecimal qty = event.getQty();
		final StockChangeSourceInfo sourceInfo = StockChangeSourceInfo.ofHuAttributeChange(event.getHuId());

		final AttributesKey oldAttributesKey = event.getOldStorageAttributes().getAttributesKey();
		final AttributesKey newAttributesKey = event.getNewStorageAttributes().getAttributesKey();

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
				.onHandQtyChange(qty.negate())
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
				.onHandQtyChange(qty)
				.sourceInfo(sourceInfo)
				.build());
	}
}
