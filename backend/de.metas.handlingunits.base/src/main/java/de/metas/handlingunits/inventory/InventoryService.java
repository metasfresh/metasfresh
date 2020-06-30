package de.metas.handlingunits.inventory;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.compiere.model.I_M_Inventory;
import org.compiere.model.X_C_DocType;
import org.springframework.stereotype.Service;

import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.impl.HUInternalUseInventoryProducer;
import de.metas.handlingunits.inventory.impl.SyncInventoryQtyToHUsCommand;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.inventory.HUAggregationType;
import de.metas.inventory.InventoryId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.quantity.QuantitiesUOMNotMatchingExpection;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class InventoryService
{
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	@Getter
	private final InventoryRepository inventoryRepository;

	public InventoryService(@NonNull final InventoryRepository inventoryRepository)
	{
		this.inventoryRepository = inventoryRepository;
	}

	public Inventory getById(@NonNull final InventoryId inventoryId)
	{
		return inventoryRepository.getById(inventoryId);
	}

	public DocBaseAndSubType extractDocBaseAndSubTypeOrNull(I_M_Inventory inventoryRecord)
	{
		return inventoryRepository.extractDocBaseAndSubTypeOrNull(inventoryRecord);
	}

	public boolean isMaterialDisposal(final I_M_Inventory inventory)
	{
		final DocTypeQuery query = DocTypeQuery.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory)
				.docSubType(X_C_DocType.DOCSUBTYPE_InternalUseInventory)
				.adClientId(inventory.getAD_Client_ID())
				.adOrgId(inventory.getAD_Org_ID())
				.build();

		final DocTypeId disposalDocTypeId = docTypeDAO.getDocTypeIdOrNull(query);

		return disposalDocTypeId != null && disposalDocTypeId.getRepoId() == inventory.getC_DocType_ID();
	}

	public static HUAggregationType computeHUAggregationType(@NonNull final DocBaseAndSubType baseAndSubType)
	{
		final InventoryLine inventoryLine = null;
		return SyncInventoryQtyToHUsCommand.computeHUAggregationType(inventoryLine, baseAndSubType);
	}

	public void updateHUAggregationTypeIfAllowed(@NonNull final I_M_Inventory inventoryRecord)
	{
		final Inventory inventory = inventoryRepository.toInventory(inventoryRecord);
		for (final InventoryLine inventoryLine : inventory.getLines())
		{
			final HUAggregationType huAggregationType = SyncInventoryQtyToHUsCommand.computeHUAggregationType(inventoryLine, inventory.getDocBaseAndSubType());
			inventoryLine.setHuAggregationType(huAggregationType);
		}

		inventoryRepository.saveInventoryLines(inventory);
	}

	public void setQtyBookedFromStorage(@NonNull final I_M_InventoryLine inventoryLine)
	{
		inventoryLine.setQtyBook(BigDecimal.ZERO);

		// mandatory ids might be missing as the I_M_InventoryLine might not be persisted yet
		final ProductId productId = ProductId.ofRepoIdOrNull(inventoryLine.getM_Product_ID());
		final HuId huId = HuId.ofRepoIdOrNull(inventoryLine.getM_HU_ID());
		final UomId uomId = UomId.ofRepoIdOrNull(inventoryLine.getC_UOM_ID());

		final boolean idsAreMissing = Stream.of(productId, huId, uomId)
				.anyMatch(Objects::isNull);

		if (idsAreMissing)
		{
			return;
		}

		final Optional<Quantity> bookedQty = inventoryRepository.getFreshBookedQtyFromStorage(productId, uomId, huId);

		if (bookedQty.isPresent())
		{
			if (bookedQty.get().getUomId().getRepoId() != inventoryLine.getC_UOM_ID())
			{
				// this should never happen as InventoryRepository#getFreshBookedQtyFromStorage() returns the qty in the inventory line's uom.
				throw new QuantitiesUOMNotMatchingExpection("Booked and counted quantities don't have the same UOM!")
						.appendParametersToMessage()
						.setParameter("InventoryLineUOMID", inventoryLine.getC_UOM_ID())
						.setParameter("BookedQtyUOMID", bookedQty.get().getUomId());
			}

			inventoryLine.setQtyBook(bookedQty.get().toBigDecimal());
		}
	}

	public void syncToHUs(@NonNull final I_M_Inventory inventoryRecord)
	{
		final Inventory inventory = inventoryRepository.toInventory(inventoryRecord);

		SyncInventoryQtyToHUsCommand.builder()
				.inventoryRepository(inventoryRepository)
				.inventory(inventory)
				.build()
				//
				.execute();
	}

	/**
	 * Move products from the warehouse to garbage (waste disposal)
	 * After this process an internal use inventory is created.
	 */
	public List<de.metas.handlingunits.model.I_M_Inventory> moveToGarbage(
			final Collection<I_M_HU> husToDestroy,
			final ZonedDateTime movementDate,
			final ActivityId activityId,
			final String description,
			final boolean isCompleteInventory,
			final boolean isCreateMovement)
	{
		return HUInternalUseInventoryProducer.newInstance()
				.setMovementDate(movementDate)
				.setDocSubType(X_C_DocType.DOCSUBTYPE_InternalUseInventory)
				.addHUs(husToDestroy)
				.setActivityId(activityId)
				.setDescription(description)
				.setIsCompleteInventory(isCompleteInventory)
				.setIsCreateMovement(isCreateMovement)
				.createInventories();
	}

	public void completeDocument(@NonNull final InventoryId inventoryId)
	{
		final I_M_Inventory inventory = inventoryRepository.getRecordById(inventoryId);
		documentBL.processEx(inventory, IDocument.ACTION_Complete);
	}

	public Inventory createInventoryHeader(@NonNull final InventoryHeaderCreateRequest request)
	{
		return inventoryRepository.createInventoryHeader(request);
	}

	public Inventory createInventoryLine(@NonNull final InventoryLineCreateRequest request)
	{
		return inventoryRepository.createInventoryLine(request);
	}
}
