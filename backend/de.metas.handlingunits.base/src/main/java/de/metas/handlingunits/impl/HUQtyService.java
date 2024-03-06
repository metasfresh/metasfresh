/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.handlingunits.impl;

import de.metas.common.util.time.SystemTime;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.UpdateHUQtyRequest;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.InventoryHeaderCreateRequest;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.inventory.draftlinescreator.DraftInventoryLinesCreator;
import de.metas.handlingunits.inventory.draftlinescreator.HUsForInventoryStrategies;
import de.metas.handlingunits.inventory.draftlinescreator.HuForInventoryLine;
import de.metas.handlingunits.inventory.draftlinescreator.InventoryLinesCreationCtx;
import de.metas.handlingunits.inventory.draftlinescreator.aggregator.InventoryLineAggregatorFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.inventory.AggregationType;
import de.metas.inventory.InventoryDocSubType;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HUQtyService
{
	@NonNull
	private final InventoryService inventoryService;

	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	@Nullable
	public Inventory updateQty(@NonNull final UpdateHUQtyRequest request)
	{
		final I_M_HU hu = handlingUnitsBL.getById(request.getHuId());
		final ClientId clientId = ClientId.ofRepoId(hu.getAD_Client_ID());
		final IHUProductStorage huProductStorage = getSingleStorage(hu);
		final HuForInventoryLine inventoryLineCandidate = toHuForInventoryLine(hu, huProductStorage, request.getQty());
		if (inventoryLineCandidate == null)
		{
			return null;
		}

		final Inventory inventoryHeader = inventoryService.createInventoryHeader(InventoryHeaderCreateRequest.builder()
																						 .orgId(inventoryLineCandidate.getOrgId())
																						 .docTypeId(getInventoryDocTypeId(clientId, inventoryLineCandidate.getOrgId()))
																						 .movementDate(SystemTime.asZonedDateTime())
																						 .warehouseId(inventoryLineCandidate.getLocatorId().getWarehouseId())
																						 .description(request.getDescription())
																						 .build());

		final InventoryLinesCreationCtx inventoryLinesCreationCtx = InventoryLinesCreationCtx.builder()
				.inventoryRepo(inventoryService.getInventoryRepository())
				.inventoryLineAggregator(InventoryLineAggregatorFactory.getForAggregationMode(AggregationType.SINGLE_HU))
				.inventory(inventoryHeader)
				.strategy(HUsForInventoryStrategies.of(inventoryLineCandidate))
				.build();

		new DraftInventoryLinesCreator(inventoryLinesCreationCtx).execute();

		inventoryService.completeDocument(inventoryHeader.getId());
		return inventoryService.getById(inventoryHeader.getId());
	}

	@Nullable
	public HuForInventoryLine toHuForInventoryLine(
			final I_M_HU hu,
			final IHUProductStorage huProductStorage,
			final Quantity newQty)
	{
		final Quantity huQty = huProductStorage.getQty();
		if (huQty.compareTo(newQty) == 0)
		{
			return null;
		}

		return HuForInventoryLine.builder()
				.orgId(OrgId.ofRepoId(hu.getAD_Org_ID()))
				.huId(huProductStorage.getHuId())
				.quantityBooked(huQty)
				.quantityCount(newQty)
				.productId(huProductStorage.getProductId())
				.storageAttributesKey(handlingUnitsBL.getAttributesKeyForInventory(hu))
				.locatorId(IHandlingUnitsBL.extractLocatorId(hu))
				.markAsCounted(true)
				.build();
	}

	@NonNull
	private IHUProductStorage getSingleStorage(final I_M_HU hu)
	{
		final IHUStorage huStorage = handlingUnitsBL
				.getStorageFactory()
				.getStorage(hu);

		final List<IHUProductStorage> huProductStorages = huStorage.getProductStorages();
		if (huProductStorages.isEmpty())
		{
			throw new AdempiereException("Empty HU is not handled");
		}
		else if (huProductStorages.size() == 1)
		{
			return huProductStorages.get(0);
		}
		else
		{
			throw new AdempiereException("Multi product storage is not handled");
		}
	}

	private DocTypeId getInventoryDocTypeId(
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		return docTypeDAO.getDocTypeId(DocTypeQuery.builder()
											   .docBaseType(DocBaseType.MaterialPhysicalInventory)
											   .docSubType(InventoryDocSubType.SingleHUInventory.getCode())
											   .adClientId(clientId.getRepoId())
											   .adOrgId(orgId.getRepoId())
											   .build());
	}
}
