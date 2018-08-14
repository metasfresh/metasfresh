package de.metas.handlingunits.inventory;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Stream;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Inventory;

import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.inventory.IInventoryDAO;
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
public class DraftInventoryLinesCreator
{
	IDocumentBL documentBL = Services.get(IDocumentBL.class);
	IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);
	IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	I_M_Inventory inventoryRecord;

	Map<HuId, I_M_InventoryLine> inventoryLinesByHU;

	long countInventoryLines;

	@Builder
	private DraftInventoryLinesCreator(
			@NonNull final I_M_Inventory inventoryRecord,
			@NonNull final HUsForInventoryStrategy strategy)
	{
		Check.errorUnless(
				documentBL.issDocumentDraftedOrInProgress(inventoryRecord),
				"the given inventory record needs to be in status 'DR' or 'IP', but is in status={}; inventoryRecord={}",
				inventoryRecord.getDocStatus(), inventoryRecord);
		this.inventoryRecord = inventoryRecord;

		// get existing lines' HuIds
		this.inventoryLinesByHU = inventoryDAO
				.retrieveLinesForInventoryId(inventoryRecord.getM_Inventory_ID(), I_M_InventoryLine.class)
				.stream()
				.filter(line -> line.getM_HU_ID() > 0)
				.collect(GuavaCollectors.toImmutableMapByKey(line -> HuId.ofRepoId(line.getM_HU_ID())));

		// create/update new lines
		countInventoryLines = strategy
				.streamHus()
				.flatMap(this::createUpdateInventoryLines)
				.count();
	}

	private Stream<I_M_InventoryLine> createUpdateInventoryLines(@NonNull final I_M_HU hu)
	{
		return handlingUnitsBL
				.getStorageFactory()
				.streamHUProductStorages(hu)
				.filter(huProductStorage -> !huProductStorage.isEmpty())
				.map(this::createOrUpdateInventoryLine);
	}

	private I_M_InventoryLine createOrUpdateInventoryLine(@NonNull final IHUProductStorage huProductStorage)
	{
		final I_M_InventoryLine inventoryLine;
		final I_M_HU hu = huProductStorage.getM_HU();

		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());

		if (inventoryLinesByHU.containsKey(huId))
		{
			// update line
			inventoryLine = inventoryLinesByHU.get(huId);
		}
		else
		{
			// create line
			inventoryLine = InterfaceWrapperHelper.newInstance(I_M_InventoryLine.class);
			inventoryLine.setM_Inventory(inventoryRecord);
			inventoryLine.setAD_Org_ID(inventoryRecord.getAD_Org_ID());
		}

		inventoryLine.setM_AttributeSetInstance_ID(0);
		inventoryLine.setM_HU_ID(hu.getM_HU_ID());
		inventoryLine.setM_HU_PI_Item_Product(null); // TODO
		inventoryLine.setQtyTU(BigDecimal.ZERO); // TODO

		inventoryLine.setM_Locator_ID(hu.getM_Locator_ID());
		inventoryLine.setM_Product_ID(huProductStorage.getM_Product_ID());
		inventoryLine.setC_UOM(huProductStorage.getC_UOM());

		inventoryLine.setQtyBook(huProductStorage.getQty());
		inventoryLine.setQtyCount(huProductStorage.getQty());

		saveRecord(inventoryLine);

		return inventoryLine;
	}
}
