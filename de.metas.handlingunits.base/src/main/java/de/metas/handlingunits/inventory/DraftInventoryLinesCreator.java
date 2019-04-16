package de.metas.handlingunits.inventory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.adempiere.warehouse.LocatorId;

import com.google.common.collect.ImmutableMap;

import de.metas.handlingunits.inventory.InventoryLine.InventoryLineBuilder;
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
	private final DraftInventoryLines draftInventoryLines;

	private final Set<LocatorId> seenLocatorIds = new HashSet<>();

	@NonFinal
	@Getter
	private long countInventoryLines = 0;

	public DraftInventoryLinesCreator(@NonNull final DraftInventoryLines draftInventoryLines)
	{
		this.draftInventoryLines = draftInventoryLines;
	}

	/** Create/Update new lines using the instance's {@link HUsForInventoryStrategy} */
	public void execute()
	{
		final HUsForInventoryStrategy strategy = draftInventoryLines.getStrategy();

		final Iterator<HuForInventoryLine> hus = strategy.streamHus().iterator();
		while (hus.hasNext())
		{
			final HuForInventoryLine hu = hus.next();
			seenLocatorIds.add(hu.getLocatorId());

			if (strategy.getMaxLocatorsAllowed() > 0 && strategy.getMaxLocatorsAllowed() < seenLocatorIds.size())
			{
				return;
			}

			createOrUpdateInventoryLine(hu);
			countInventoryLines++;
		}
	}

	private void createOrUpdateInventoryLine(@NonNull final HuForInventoryLine huForInventoryLine)
	{
		final InventoryLineBuilder inventoryLineBuilder;

		final InventoryLineAggregationKey aggregationKey = draftInventoryLines
				.getInventoryLineAggregator()
				.createAggregationKey(huForInventoryLine);


		final ImmutableMap<InventoryLineAggregationKey, InventoryLine> preExistingInventoryLines = draftInventoryLines.getPreExistingInventoryLines();
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
			inventoryLineBuilder = InventoryLine
					.builder()
					.inventoryId(draftInventoryLines.getInventoryId());
		}

		inventoryLineBuilder.storageAttributesKey(huForInventoryLine.getStorageAttributesKey())
				.inventoryLineHU(InventoryLineHU.builder()
						.huId(huForInventoryLine.getHuId())
						.bookQty(huForInventoryLine.getQuantity())
						.countQty(huForInventoryLine.getQuantity())
						.build())
				.locatorId(huForInventoryLine.getLocatorId())
				.productId(huForInventoryLine.getProductId());

		draftInventoryLines
				.getInventoryLineRepository()
				.save(inventoryLineBuilder.build());
	}
}
