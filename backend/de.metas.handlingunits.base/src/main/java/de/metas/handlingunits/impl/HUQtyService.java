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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.handlingunits.CreateHUFromInventoryRequest;
import de.metas.handlingunits.CreatedHU;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.UpdateHUQtyRequest;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.InventoryHeaderCreateRequest;
import de.metas.handlingunits.inventory.InventoryLineCreateRequest;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.inventory.draftlinescreator.DraftInventoryLinesCreateRequest;
import de.metas.handlingunits.inventory.draftlinescreator.HUsForInventoryStrategies;
import de.metas.handlingunits.inventory.draftlinescreator.HuForInventoryLine;
import de.metas.handlingunits.inventory.draftlinescreator.aggregator.InventoryLineAggregatorFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.candidate.commands.PackedHUWeightNetUpdater;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodeAttribute;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.inventory.AggregationType;
import de.metas.inventory.HUAggregationType;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HUQtyService
{
	@NonNull
	private final InventoryService inventoryService;
	@NonNull
	private final HUQRCodesService huQRCodesService;

	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	@VisibleForTesting
	public static HUQtyService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(
				HUQtyService.class,
				() -> new HUQtyService(
						InventoryService.newInstanceForUnitTesting(),
						HUQRCodesService.newInstanceForUnitTesting()));
	}

	@Nullable
	public Inventory updateQty(@NonNull final UpdateHUQtyRequest request)
	{
		final Quantity newQty = request.getQty();

		final LocatorId locatorId;
		final ClientAndOrgId clientAndOrgId;
		final ProductId productId;
		final AttributesKey attributesKey;
		final Quantity huQty;
		final HuId huId = request.getHuId();
		final HUQRCode huQRCode;
		if (huId == null)
		{
			if (request.getLocatorId() == null)
			{
				throw new AdempiereException("locatorId must be set: " + request);
			}
			locatorId = request.getLocatorId();

			clientAndOrgId = warehouseDAO.getClientAndOrgIdByLocatorId(locatorId);

			huQRCode = request.getHuQRCode();
			if (huQRCode == null)
			{
				throw new AdempiereException("huId or huQRCode must be set: " + request);
			}

			productId = huQRCode.getProductIdNotNull();
			final ImmutableAttributeSet attributes = extractAttributeSet(huQRCode);
			attributesKey = handlingUnitsBL.getAttributesKeyForInventory(attributes);

			huQty = newQty.toZero();
		}
		else
		{
			huQRCode = null;
			final I_M_HU hu = handlingUnitsBL.getById(huId);
			final IHUProductStorage singleProductStorage = getSingleStorage(hu);

			locatorId = IHandlingUnitsBL.extractLocatorId(hu);
			if (request.getLocatorId() != null && !LocatorId.equals(request.getLocatorId(), locatorId))
			{
				throw new AdempiereException("request locatorId is not matching the HU: " + request + ", " + hu);
			}

			clientAndOrgId = ClientAndOrgId.ofClientAndOrg(hu.getAD_Client_ID(), hu.getAD_Org_ID());

			productId = singleProductStorage.getProductId();
			attributesKey = handlingUnitsBL.getAttributesKeyForInventory(hu);
			huQty = singleProductStorage.getQty();
		}

		//
		// No qty change
		if (huQty.compareTo(newQty) == 0)
		{
			return null;
		}

		final HuForInventoryLine inventoryLineCandidate = HuForInventoryLine.builder()
				.orgId(clientAndOrgId.getOrgId())
				.huId(huId)
				.huQRCode(huQRCode)
				.quantityBooked(huQty)
				.quantityCount(newQty)
				.productId(productId)
				.storageAttributesKey(attributesKey)
				.locatorId(locatorId)
				.markAsCounted(true)
				.build();

		final Inventory inventoryHeader = createInventoryHeader(clientAndOrgId, locatorId.getWarehouseId(), request.getDate(), request.getDescription());

		inventoryService.createDraftLines(
				DraftInventoryLinesCreateRequest.builder()
						.lineAggregator(InventoryLineAggregatorFactory.getForAggregationMode(AggregationType.SINGLE_HU))
						.inventory(inventoryHeader)
						.strategy(HUsForInventoryStrategies.of(inventoryLineCandidate))
						.build()
		);

		inventoryService.completeDocument(inventoryHeader.getId());
		return inventoryService.getById(inventoryHeader.getId());
	}

	/**
	 * Creates ONE new HU for the given product, with the given quantity and attributes, at the given locator, by booking and
	 * completing a single-HU inventory with a count of the quantity and a book quantity of zero.
	 * The line is marked {@link HUAggregationType#SINGLE_HU}, like the draft lines of {@link #updateQty(UpdateHUQtyRequest)}.
	 * The HU is a virtual HU (a CU): the inventory line carries no packing instructions.
	 * A given weight is set on the CU after the inventory completed (the inventory's attribute key drops weight attributes).
	 *
	 * Runs in the caller's transaction: it opens none, so the inventory, the weight and the HU QR code commit or roll back together with the caller.
	 *
	 * @return the created HU with its HU QR code; the QR code is generated if the HU has none only when HU QR code generation is enabled
	 * ({@code HUQRCodesService#isGenerateQRCodesIfMissing()}), otherwise this fails after the inventory was booked
	 */
	@NonNull
	public CreatedHU createHUFromInventory(@NonNull final CreateHUFromInventoryRequest request)
	{
		final LocatorId locatorId = request.getLocatorId();
		final ClientAndOrgId clientAndOrgId = warehouseDAO.getClientAndOrgIdByLocatorId(locatorId);

		final Inventory inventoryHeader = createInventoryHeader(clientAndOrgId, locatorId.getWarehouseId(), request.getDate(), request.getDescription());

		// A new HU has neither an HU id nor an HU QR code to key a draft line on (SingleHUInventoryLineAggregator needs one of them),
		// so the line is created directly with a "no HU yet" placeholder; completing the inventory creates the HU
		// (same as InventoryService#createInventoryForMissingQty).
		inventoryService.createInventoryLine(InventoryLineCreateRequest.builder()
				.inventoryId(inventoryHeader.getId())
				.productId(request.getProductId())
				.qtyBooked(request.getQty().toZero())
				.qtyCount(request.getQty())
				.attributeSetId(AttributesKeys.createAttributeSetInstanceFromAttributesKey(handlingUnitsBL.getAttributesKeyForInventory(request.getAttributes())))
				.locatorId(locatorId)
				.aggregationType(AggregationType.SINGLE_HU.getHuAggregationType()) // same as the doc type (SingleHUInventory) and the draft-lines path
				.build());

		inventoryService.completeDocument(inventoryHeader.getId());
		final HuId huId = CollectionUtils.singleElement(inventoryService.getById(inventoryHeader.getId()).getHuIds());

		setWeightNet(huId, request.getProductId(), request.getWeightInKg());
		return new CreatedHU(huId, huQRCodesService.getQRCodeByHuId(huId));
	}

	private void setWeightNet(@NonNull final HuId cuId, @NonNull final ProductId productId, @Nullable final Quantity weightInKg)
	{
		if (weightInKg == null)
		{
			return;
		}

		new PackedHUWeightNetUpdater(uomConversionBL, handlingUnitsBL.createMutableHUContextForProcessing(), productId, weightInKg)
				.updatePackToHU(handlingUnitsBL.getById(cuId));
	}

	@NonNull
	private Inventory createInventoryHeader(
			@NonNull final ClientAndOrgId clientAndOrgId,
			@NonNull final WarehouseId warehouseId,
			@NonNull final ZonedDateTime date,
			@Nullable final String description)
	{
		return inventoryService.createInventoryHeader(InventoryHeaderCreateRequest.builder()
				.orgId(clientAndOrgId.getOrgId())
				.docTypeId(getInventoryDocTypeId(clientAndOrgId))
				.movementDate(date)
				.warehouseId(warehouseId)
				.description(description)
				.build());
	}

	private static ImmutableAttributeSet extractAttributeSet(@NonNull HUQRCode huQRCode)
	{
		final ImmutableList<HUQRCodeAttribute> huQRCodeAttributes = huQRCode.getAttributes();
		if (huQRCodeAttributes.isEmpty())
		{
			return ImmutableAttributeSet.EMPTY;
		}

		final ImmutableAttributeSet.Builder builder = ImmutableAttributeSet.builder();
		for (final HUQRCodeAttribute huQRCodeAttribute : huQRCodeAttributes)
		{
			builder.attributeValue(huQRCodeAttribute.getCode(), huQRCodeAttribute.getValue());
		}
		return builder.build();
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

	private DocTypeId getInventoryDocTypeId(@NonNull final ClientAndOrgId clientAndOrgId)
	{
		return docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(DocBaseType.MaterialPhysicalInventory)
				.docSubType(DocSubType.SingleHUInventory)
				.adClientId(clientAndOrgId.getClientId().getRepoId())
				.adOrgId(clientAndOrgId.getOrgId().getRepoId())
				.build());
	}
}
