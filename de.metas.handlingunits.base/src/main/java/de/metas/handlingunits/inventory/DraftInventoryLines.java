package de.metas.handlingunits.inventory;

import java.util.Map;

import org.compiere.model.I_M_Inventory;

import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.inventory.IInventoryDAO;
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
 * Creates or updates inventory lines for
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Value
public class DraftInventoryLines
{
	IDocumentBL documentBL = Services.get(IDocumentBL.class);
	IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);

	final I_M_Inventory inventoryRecord;
	final HUsForInventoryStrategy strategy;

	Map<HuId, I_M_InventoryLine> inventoryLinesByHU;

	@Builder
	private DraftInventoryLines(
			@NonNull final I_M_Inventory inventoryRecord,
			@NonNull final HUsForInventoryStrategy strategy)
	{
		Check.errorUnless(
				documentBL.issDocumentDraftedOrInProgress(inventoryRecord),
				"the given inventory record needs to be in status 'DR' or 'IP', but is in status={}; inventoryRecord={}",
				inventoryRecord.getDocStatus(), inventoryRecord);
		this.inventoryRecord = inventoryRecord;
		this.strategy = strategy;

		// get existing lines' HuIds
		this.inventoryLinesByHU = inventoryDAO
				.retrieveLinesForInventoryId(inventoryRecord.getM_Inventory_ID(), I_M_InventoryLine.class)
				.stream()
				.filter(line -> line.getM_HU_ID() > 0)
				.collect(GuavaCollectors.toImmutableMapByKey(line -> HuId.ofRepoId(line.getM_HU_ID())));
	}
}
