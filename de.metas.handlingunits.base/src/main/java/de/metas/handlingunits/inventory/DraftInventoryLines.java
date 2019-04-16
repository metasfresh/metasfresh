package de.metas.handlingunits.inventory;

import org.compiere.model.I_M_Inventory;

import com.google.common.collect.ImmutableMap;

import de.metas.document.engine.IDocumentBL;
import de.metas.inventory.IInventoryDAO;
import de.metas.inventory.InventoryId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
 * Creates or updates unprocessed inventory lines for a given {@link I_M_Inventory} record.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Value
public class DraftInventoryLines
{
	transient IDocumentBL documentBL = Services.get(IDocumentBL.class);
	transient IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);


	InventoryId inventoryId;
	HUsForInventoryStrategy strategy;

	InventoryLineAggregator inventoryLineAggregator;

	ImmutableMap<InventoryLineAggregationKey, InventoryLine> preExistingInventoryLines;
	InventoryLineRepository inventoryLineRepository;


	@Builder
	private DraftInventoryLines(
			@NonNull final InventoryLineAggregator inventoryLineAggregator,
			@NonNull final InventoryLineRepository inventoryLineRepository,
			@NonNull final I_M_Inventory inventoryRecord,
			@NonNull final HUsForInventoryStrategy strategy)
	{
		this.inventoryLineRepository = inventoryLineRepository;
		this.inventoryLineAggregator = inventoryLineAggregator;
		Check.errorUnless(
				documentBL.issDocumentDraftedOrInProgress(inventoryRecord),
				"the given inventory record needs to be in status 'DR' or 'IP', but is in status={}; inventoryRecord={}",
				inventoryRecord.getDocStatus(), inventoryRecord);

		this.inventoryId = InventoryId.ofRepoId(inventoryRecord.getM_Inventory_ID());
		this.strategy = strategy;

		preExistingInventoryLines = inventoryLineRepository
				.getByInventoryId(InventoryId.ofRepoId(inventoryRecord.getM_Inventory_ID()))

				.stream()
				.filter(il -> !il.getInventoryLineHUs().isEmpty())
				.collect(GuavaCollectors.toImmutableMapByKey(inventoryLineAggregator::createAggregationKey));
	}
}
