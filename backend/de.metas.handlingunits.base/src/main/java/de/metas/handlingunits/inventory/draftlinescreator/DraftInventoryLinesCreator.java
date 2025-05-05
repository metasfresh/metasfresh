package de.metas.handlingunits.inventory.draftlinescreator;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.InventoryLine;
import de.metas.handlingunits.inventory.InventoryLine.InventoryLineBuilder;
import de.metas.handlingunits.inventory.InventoryLineHU;
import de.metas.handlingunits.inventory.InventoryRepository;
import de.metas.handlingunits.inventory.draftlinescreator.aggregator.InventoryLineAggregationKey;
import de.metas.inventory.HUAggregationType;
import de.metas.inventory.InventoryId;
import de.metas.logging.LogManager;
import de.metas.quantity.Quantity;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.NonFinal;
import org.adempiere.warehouse.LocatorId;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

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
 */
public class DraftInventoryLinesCreator
{
	private final static Logger logger = LogManager.getLogger(DraftInventoryLinesCreator.class);

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

	/**
	 * Create/Update new lines using the instance's {@link HUsForInventoryStrategy}
	 */
	public void execute()
	{
		final HUsForInventoryStrategy strategy = inventoryLinesCreationCtx.getStrategy();

		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);
		final InventoryRepository inventoryLineRepository = inventoryLinesCreationCtx.getInventoryRepo();

		final InventoryId inventoryId = inventoryLinesCreationCtx.getInventoryId();
		final Inventory inventory = inventoryLineRepository.getById(inventoryId);
		final ImmutableSet<HuId> preAddedHuIds = inventory.getHuIds(); // needed in case we want to add further lines when some already pre-exist

		final Iterator<HuForInventoryLine> hus = strategy.streamHus().iterator();
		while (hus.hasNext())
		{
			final HuForInventoryLine hu = hus.next();
			seenLocatorIds.add(hu.getLocatorId());

			if (strategy.getMaxLocatorsAllowed() > 0 && strategy.getMaxLocatorsAllowed() < seenLocatorIds.size())
			{
				loggable.addLog("Strategy {} has maxLocatorsAllowed={}; -> not adding further HUs",
						strategy.getClass().getSimpleName(), strategy.getMaxLocatorsAllowed());
				break;
			}

			if (hu.getHuId() != null && preAddedHuIds.contains(hu.getHuId()))
			{
				loggable.addLog("M_HU_ID={} is already assigned to M_Inventory_ID={}; -> not trying to add it again",
						HuId.toRepoId(hu.getHuId()), InventoryId.toRepoId(inventoryId));
				continue;
			}

			createOrUpdateInventoryLine(hu);
			countInventoryLines++;
		}

		createdOrUpdatedLines
				.values()
				.forEach(line -> inventoryLineRepository.saveInventoryLine(line, inventoryId));

	}

	/**
	 * Stores the result in {@link #createdOrUpdatedLines}
	 */
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

	public static InventoryLineHU toInventoryLineHU(final HuForInventoryLine huForInventoryLine)
	{
		final Quantity quantityBooked = huForInventoryLine.getQuantityBooked();
		final Quantity quantityCount = CoalesceUtil.coalesce(huForInventoryLine.getQuantityCount(), quantityBooked);

		return InventoryLineHU.builder()
				.huId(huForInventoryLine.getHuId())
				.huQRCode(huForInventoryLine.getHuQRCode())
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
