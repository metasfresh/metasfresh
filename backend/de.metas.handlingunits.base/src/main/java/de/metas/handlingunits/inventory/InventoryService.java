package de.metas.handlingunits.inventory;

import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.global_qrcodes.service.GlobalQRCodeService;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.impl.SyncInventoryQtyToHUsCommand;
import de.metas.handlingunits.inventory.internaluse.HUInternalUseInventoryCreateRequest;
import de.metas.handlingunits.inventory.internaluse.HUInternalUseInventoryCreateResponse;
import de.metas.handlingunits.inventory.internaluse.HUInternalUseInventoryProducer;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.qrcodes.service.HUQRCodesRepository;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.i18n.AdMessageKey;
import de.metas.inventory.AggregationType;
import de.metas.inventory.HUAggregationType;
import de.metas.inventory.InventoryDocSubType;
import de.metas.inventory.InventoryId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.QuantitiesUOMNotMatchingExpection;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Inventory;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

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
@RequiredArgsConstructor
public class InventoryService
{
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull @Getter private final InventoryRepository inventoryRepository;
	@NonNull private final SourceHUsService sourceHUsService;
	@NonNull private final HUQRCodesService huQRCodesService;

	private static final AdMessageKey MSG_EXISTING_LINES_WITH_DIFFERENT_HU_AGGREGATION_TYPE = AdMessageKey.of("de.metas.handlingunits.inventory.ExistingLinesWithDifferentHUAggregationType");

	public static InventoryService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new InventoryService(
				new InventoryRepository(),
				SourceHUsService.get(),
				new HUQRCodesService(new HUQRCodesRepository(), new GlobalQRCodeService())
		);
	}

	public Inventory getById(@NonNull final InventoryId inventoryId)
	{
		return inventoryRepository.getById(inventoryId);
	}

	public Inventory toInventory(@NonNull final I_M_Inventory inventoryRecord) {return inventoryRepository.toInventory(inventoryRecord);}

	public DocBaseAndSubType extractDocBaseAndSubTypeOrNull(final I_M_Inventory inventoryRecord)
	{
		return inventoryRepository.extractDocBaseAndSubTypeOrNull(inventoryRecord);
	}

	public boolean isMaterialDisposal(final I_M_Inventory inventory)
	{
		final DocTypeId inventoryDocTypeId = DocTypeId.ofRepoIdOrNull(inventory.getC_DocType_ID());
		if (inventoryDocTypeId == null)
		{
			return false;
		}

		final DocBaseAndSubType inventoryDocBaseAndSubType = docTypeDAO.getDocBaseAndSubTypeById(inventoryDocTypeId);
		return InventoryDocSubType.InternalUseInventory.toDocBaseAndSubType().equals(inventoryDocBaseAndSubType);
	}

	public static HUAggregationType computeHUAggregationType(@NonNull final DocBaseAndSubType baseAndSubType)
	{
		return computeHUAggregationType(null, baseAndSubType);
	}

	public void updateHUAggregationTypeIfAllowed(@NonNull final I_M_Inventory inventoryRecord)
	{
		final Inventory inventory = inventoryRepository.toInventory(inventoryRecord);
		for (final InventoryLine inventoryLine : inventory.getLines())
		{
			final HUAggregationType huAggregationType = computeHUAggregationType(inventoryLine, inventory.getDocBaseAndSubType());
			inventoryLine.setHuAggregationType(huAggregationType);
		}

		inventoryRepository.saveInventoryLines(inventory);
	}

	@NonNull
	public DocTypeId getDocTypeIdByAggregationType(
			@Nullable final HUAggregationType huAggregationType,
			@NonNull final OrgId orgId)
	{
		final DocBaseAndSubType docBaseAndSubType = getDocBaseAndSubType(huAggregationType);

		return docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(docBaseAndSubType.getDocBaseType())
				.docSubType(docBaseAndSubType.getDocSubType())
				.adClientId(Env.getAD_Client_ID())
				.adOrgId(orgId.getRepoId())
				.build());
	}

	private static HUAggregationType computeHUAggregationType(
			@Nullable final InventoryLine inventoryLine,
			@NonNull final DocBaseAndSubType baseAndSubType)
	{
		final HUAggregationType huAggregationTypeToUse = Optional
				.ofNullable(AggregationType.getByDocTypeOrNull(baseAndSubType))
				.map(AggregationType::getHuAggregationType)
				.orElse(HUAggregationType.SINGLE_HU); // the default

		if (inventoryLine == null)
		{
			return huAggregationTypeToUse; // nothing more to check
		}

		final HUAggregationType huAggregationTypeCurrent = inventoryLine.getHuAggregationType();
		if (huAggregationTypeCurrent == null)
		{
			return huAggregationTypeToUse;
		}
		else if (huAggregationTypeCurrent.equals(huAggregationTypeToUse))
		{
			return huAggregationTypeToUse;
		}
		else if (inventoryLine.getId() == null)
		{
			return huAggregationTypeToUse;
		}
		else
		{
			// this line already has a different aggregation type
			throw new AdempiereException(MSG_EXISTING_LINES_WITH_DIFFERENT_HU_AGGREGATION_TYPE)
					.markAsUserValidationError();
		}
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
				.sourceHUsService(sourceHUsService)
				.huQRCodesService(huQRCodesService)
				.inventory(inventory)
				.build()
				//
				.execute();
	}

	/**
	 * Move products from the warehouse to garbage (waste disposal)
	 * After this process an internal use inventory is created.
	 */
	public HUInternalUseInventoryCreateResponse moveToGarbage(@NonNull final HUInternalUseInventoryCreateRequest request)
	{
		return new HUInternalUseInventoryProducer(request).execute();
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

	public void createInventoryLine(@NonNull final InventoryLineCreateRequest request)
	{
		inventoryRepository.createInventoryLine(request);
	}

	@NonNull
	public HuId createInventoryForMissingQty(@NonNull final CreateVirtualInventoryWithQtyReq req)
	{
		final LocatorId locatorId = warehouseBL.getDefaultLocatorId(req.getWarehouseId());

		final InventoryHeaderCreateRequest createHeaderRequest = InventoryHeaderCreateRequest
				.builder()
				.orgId(req.getOrgId())
				.docTypeId(getVirtualInventoryDocTypeId(req.getClientId(), req.getOrgId()))
				.movementDate(req.getMovementDate())
				.warehouseId(req.getWarehouseId())
				.pickingJobId(req.getPickingJobId())
				.build();

		final InventoryId inventoryId = createInventoryHeader(createHeaderRequest).getId();

		final InventoryLineCreateRequest createLineRequest = InventoryLineCreateRequest
				.builder()
				.inventoryId(inventoryId)
				.productId(req.getProductId())
				.qtyBooked(req.getQty().toZero())
				.qtyCount(req.getQty())
				.attributeSetId(req.getAttributeSetInstanceId())
				.locatorId(locatorId)
				.build();

		createInventoryLine(createLineRequest);

		completeDocument(inventoryId);

		final Inventory inventory = getById(inventoryId);

		return CollectionUtils.singleElement(inventory.getHuIds());
	}

	private DocTypeId getVirtualInventoryDocTypeId(@NonNull final ClientId clientId, @NonNull final OrgId orgId)
	{
		return docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(InventoryDocSubType.VirtualInventory.getDocBaseType())
				.docSubType(InventoryDocSubType.VirtualInventory.getCode())
				.adClientId(clientId.getRepoId())
				.adOrgId(orgId.getRepoId())
				.build());
	}

	private static DocBaseAndSubType getDocBaseAndSubType(@Nullable final HUAggregationType huAggregationType)
	{
		if (huAggregationType == null)
		{
			// #10656 There is no inventory doctype without a subtype. Consider the AggregatedHUInventory as a default
			return AggregationType.MULTIPLE_HUS.getDocBaseAndSubType();
		}
		else
		{
			return AggregationType.getByHUAggregationType(huAggregationType).getDocBaseAndSubType();
		}
	}
}
