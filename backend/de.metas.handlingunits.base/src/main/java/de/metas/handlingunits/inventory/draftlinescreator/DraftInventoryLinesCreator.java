package de.metas.handlingunits.inventory.draftlinescreator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import org.adempiere.warehouse.LocatorId;

import com.google.common.collect.ImmutableMap;

import de.metas.handlingunits.inventory.InventoryLine;
import de.metas.handlingunits.inventory.InventoryLine.InventoryLineBuilder;
import de.metas.handlingunits.inventory.InventoryLineHU;
import de.metas.handlingunits.inventory.InventoryRepository;
import de.metas.inventory.HUAggregationType;
import de.metas.inventory.InventoryId;
import de.metas.quantity.Quantity;
import de.metas.util.lang.CoalesceUtil;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.NonFinal;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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
 * Creates or updates inventory lines for
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class DraftInventoryLinesCreator
{
	@NonNull
	private final InventoryLinesCreationCtx inventoryLinesCreationCtx;

	private final Set<LocatorId> seenLocatorIds = new HashSet<>();

	private final LinkedHashMap<InventoryLineAggregationKey, InventoryLine> createdOrUpdatedLines = new LinkedHashMap<>();

	@NonFinal
	@Getter
	private long countInventoryLines = 0;

	public DraftInventoryLinesCreator(@NonNull final InventoryLinesCreationCtx inventoryLinesCreationCtx)
	{
		this.inventoryLinesCreationCtx = inventoryLinesCreationCtx;
	}

	/** Create/Update new lines using the instance's {@link HUsForInventoryStrategy} */
	public void execute()
	{
		final HUsForInventoryStrategy strategy = inventoryLinesCreationCtx.getStrategy();

		final Iterator<HuForInventoryLine> hus = strategy.streamHus().iterator();
		while (hus.hasNext())
		{
			final HuForInventoryLine hu = hus.next();
			seenLocatorIds.add(hu.getLocatorId());

			if (strategy.getMaxLocatorsAllowed() > 0 && strategy.getMaxLocatorsAllowed() < seenLocatorIds.size())
			{
				break;
			}

			createOrUpdateInventoryLine(hu);
			countInventoryLines++;
		}

		final InventoryRepository inventoryLineRepository = inventoryLinesCreationCtx.getInventoryRepo();
		final InventoryId inventoryId = inventoryLinesCreationCtx.getInventoryId();
		createdOrUpdatedLines
				.values()
				.forEach(line -> inventoryLineRepository.saveInventoryLine(line, inventoryId));

	}

	private void createOrUpdateInventoryLine(@NonNull final HuForInventoryLine huForInventoryLine)
	{
		final InventoryLineBuilder inventoryLineBuilder;

		final InventoryLineAggregationKey aggregationKey = inventoryLinesCreationCtx
				.getInventoryLineAggregator()
				.createAggregationKey(huForInventoryLine);

		if (createdOrUpdatedLines.containsKey(aggregationKey))
		{
			inventoryLineBuilder = createdOrUpdatedLines
					.get(aggregationKey)
					.toBuilder();
		}
		else
		{
			final ImmutableMap<InventoryLineAggregationKey, InventoryLine> //
			preExistingInventoryLines = inventoryLinesCreationCtx.getPreExistingInventoryLines();

			if (preExistingInventoryLines.containsKey(aggregationKey))
			{
				// update line
				inventoryLineBuilder = preExistingInventoryLines
						.get(aggregationKey)
						.toBuilder();

			}
			else
			{
				// create line
				inventoryLineBuilder = InventoryLine.builder()
						.orgId(huForInventoryLine.getOrgId())
						.counted(huForInventoryLine.isMarkAsCounted());
			}
		}

		final InventoryLineHU inventoryLineHU = toInventoryLineHU(huForInventoryLine);

		inventoryLineBuilder
				.huAggregationType(getHuAggregationType())
				.storageAttributesKey(huForInventoryLine.getStorageAttributesKey())
				.inventoryLineHU(inventoryLineHU)
				.locatorId(huForInventoryLine.getLocatorId())
				.productId(huForInventoryLine.getProductId());

		createdOrUpdatedLines.put(aggregationKey, inventoryLineBuilder.build());
	}

	private static InventoryLineHU toInventoryLineHU(final HuForInventoryLine huForInventoryLine)
	{
		final Quantity quantityBooked = huForInventoryLine.getQuantityBooked();
		final Quantity quantityCount = CoalesceUtil.coalesce(huForInventoryLine.getQuantityCount(), quantityBooked);

		return InventoryLineHU.builder()
				.huId(huForInventoryLine.getHuId())
				.qtyBook(quantityBooked)
				.qtyCount(quantityCount)
				.build();
	}

	private HUAggregationType getHuAggregationType()
	{
		return inventoryLinesCreationCtx
				.getInventoryLineAggregator()
				.getAggregationType()
				.getHuAggregationType();
	}
}
