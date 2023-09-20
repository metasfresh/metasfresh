package de.metas.handlingunits.weighting;

import de.metas.common.util.time.SystemTime;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.weightable.IWeightable;
import de.metas.handlingunits.attribute.weightable.PlainWeightable;
import de.metas.handlingunits.attribute.weightable.Weightables;
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
import de.metas.inventory.InventoryId;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.model.X_C_DocType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class WeightHUCommand
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final InventoryService inventoryService;

	private final HuId huId;
	private final PlainWeightable targetWeight;

	@Builder
	private WeightHUCommand(
			@NonNull final InventoryService inventoryService,
			//
			@NonNull final HuId huId,
			@NonNull final IWeightable targetWeight)
	{
		this.inventoryService = inventoryService;

		this.huId = huId;
		this.targetWeight = PlainWeightable.copyOf(targetWeight);
	}

	public Optional<InventoryId> execute()
	{
		final Inventory inventoryHeader = createAndCompleteInventory();
		if (inventoryHeader == null)
		{
			return Optional.empty();
		}

		updateHUWeights();

		return Optional.of(inventoryHeader.getId());
	}

	@Nullable
	private Inventory createAndCompleteInventory()
	{
		final Quantity targetWeightNet = Quantity.of(targetWeight.getWeightNet(), targetWeight.getWeightNetUOM());

		final I_M_HU hu = handlingUnitsBL.getById(huId);
		final ClientId clientId = ClientId.ofRepoId(hu.getAD_Client_ID());
		final IHUProductStorage huProductStorage = getSingleStorage(hu);
		final HuForInventoryLine inventoryLineCandidate = toHuForInventoryLine(hu, huProductStorage, targetWeightNet);
		if (inventoryLineCandidate == null)
		{
			return null;
		}

		final Inventory inventoryHeader = inventoryService.createInventoryHeader(InventoryHeaderCreateRequest.builder()
				.orgId(inventoryLineCandidate.getOrgId())
				.docTypeId(getInventoryDocTypeId(clientId, inventoryLineCandidate.getOrgId()))
				.movementDate(SystemTime.asZonedDateTime())
				.warehouseId(inventoryLineCandidate.getLocatorId().getWarehouseId())
				.build());

		final InventoryLinesCreationCtx inventoryLinesCreationCtx = InventoryLinesCreationCtx.builder()
				.inventoryRepo(inventoryService.getInventoryRepository())
				.inventoryLineAggregator(InventoryLineAggregatorFactory.getForAggregationMode(AggregationType.SINGLE_HU))
				.inventory(inventoryHeader)
				.strategy(HUsForInventoryStrategies.of(inventoryLineCandidate))
				.build();

		new DraftInventoryLinesCreator(inventoryLinesCreationCtx).execute();

		inventoryService.completeDocument(inventoryHeader.getId());
		return inventoryHeader;
	}

	private DocTypeId getInventoryDocTypeId(
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		return docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory)
				.docSubType(InventoryDocSubType.SingleHUInventory.getCode())
				.adClientId(clientId.getRepoId())
				.adOrgId(orgId.getRepoId())
				.build());
	}

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

	@Nullable
	private HuForInventoryLine toHuForInventoryLine(
			final I_M_HU hu,
			final IHUProductStorage huProductStorage,
			final Quantity weightNet)
	{
		// TODO: convert weightNet to huProductStorage.getQty()'s UOM
		final Quantity huQty = huProductStorage.getQty();
		if (huQty.compareTo(weightNet) == 0)
		{
			return null;
		}

		return HuForInventoryLine.builder()
				.orgId(OrgId.ofRepoId(hu.getAD_Org_ID()))
				.huId(huProductStorage.getHuId())
				.quantityBooked(huQty)
				.quantityCount(weightNet)
				.productId(huProductStorage.getProductId())
				.storageAttributesKey(handlingUnitsBL.getStorageRelevantAttributesKey(hu))
				.locatorId(IHandlingUnitsBL.extractLocatorId(hu))
				.markAsCounted(true)
				.build();
	}

	private void updateHUWeights()
	{
		final I_M_HU hu = handlingUnitsBL.getById(huId);
		final IWeightable huAttributes = getHUAttributes(hu);

		huAttributes.setWeightTareAdjust(targetWeight.getWeightTareAdjust());
		huAttributes.setWeightGross(targetWeight.getWeightGross());
		huAttributes.setWeightNet(targetWeight.getWeightNet());
		huAttributes.setWeightNetNoPropagate(targetWeight.getWeightNet());
	}

	private IWeightable getHUAttributes(final I_M_HU hu)
	{
		final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
		final IAttributeStorage huAttributes = huContextFactory
				.createMutableHUContext()
				.getHUAttributeStorageFactory()
				.getAttributeStorage(hu);
		huAttributes.setSaveOnChange(true);

		return Weightables.wrap(huAttributes);
	}

}
