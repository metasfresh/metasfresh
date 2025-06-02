/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.rest_api.v2.warehouse;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.RestUtils;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.warehouse.JsonOutOfStockNoticeRequest;
import de.metas.common.rest_api.v2.warehouse.JsonOutOfStockResponse;
import de.metas.common.rest_api.v2.warehouse.JsonOutOfStockResponseItem;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.InventoryHeaderCreateRequest;
import de.metas.handlingunits.inventory.InventoryLineCreateRequest;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.inventory.draftlinescreator.HUsForInventoryStrategies;
import de.metas.handlingunits.inventory.draftlinescreator.HuForInventoryLine;
import de.metas.handlingunits.inventory.draftlinescreator.HuForInventoryLineFactory;
import de.metas.handlingunits.inventory.draftlinescreator.LocatorAndProductStrategy;
import de.metas.handlingunits.inventory.draftlinescreator.ProductHUInventory;
import de.metas.i18n.TranslatableStrings;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.inoutcandidate.ShipmentScheduleRepository;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.invalidation.segments.ImmutableShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ShipmentScheduleSegments;
import de.metas.inventory.HUAggregationType;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.v2.attributes.JsonAttributeService;
import de.metas.rest_api.v2.product.ProductRestService;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Warehouse;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class WarehouseService
{
	private static final String ALL = "ALL";

	private final IUOMConversionBL conversionBL = Services.get(IUOMConversionBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	@NonNull
	private final ProductRestService productRestService;
	@NonNull
	private final HuForInventoryLineFactory huForInventoryLineFactory;
	@NonNull
	private final InventoryService inventoryService;
	@NonNull
	private final ShipmentScheduleRepository shipmentScheduleRepository;
	@NonNull
	private final JsonAttributeService jsonAttributeService;

	@NonNull
	private final WarehouseRestService warehouseRestService;

	public WarehouseService(
			@NonNull final ProductRestService productRestService,
			@NonNull final HuForInventoryLineFactory huForInventoryLineFactory,
			@NonNull final InventoryService inventoryService,
			@NonNull final ShipmentScheduleRepository shipmentScheduleRepository,
			@NonNull final JsonAttributeService jsonAttributeService,
			@NonNull final WarehouseRestService warehouseRestService)
	{
		this.productRestService = productRestService;
		this.huForInventoryLineFactory = huForInventoryLineFactory;
		this.inventoryService = inventoryService;
		this.shipmentScheduleRepository = shipmentScheduleRepository;
		this.jsonAttributeService = jsonAttributeService;
		this.warehouseRestService = warehouseRestService;
	}

	@NonNull
	public WarehouseId resolveWarehouseByIdentifier(@NonNull final OrgId orgId, @NonNull final String warehouseIdentifier)
	{
		return ExternalIdentifier.ofIdentifierCandidate(warehouseIdentifier)
				.flatMap(identifier -> warehouseRestService.resolveWarehouseExternalIdentifier(identifier, orgId))
				.orElseGet(() -> getWarehouseByIdentifier(orgId, warehouseIdentifier));
	}
	
	@NonNull
	public WarehouseId getWarehouseByIdentifier(@NonNull final OrgId orgId, @NonNull final String warehouseIdentifier)
	{
		final IdentifierString warehouseString = IdentifierString.of(warehouseIdentifier);

		final IWarehouseDAO.WarehouseQuery.WarehouseQueryBuilder builder = IWarehouseDAO.WarehouseQuery.builder().orgId(orgId);

		final WarehouseId result;
		if (warehouseString.getType().equals(IdentifierString.Type.METASFRESH_ID))
		{
			result = WarehouseId.ofRepoId(warehouseString.asMetasfreshId().getValue());
		}
		else
		{
			if (warehouseString.getType().equals(IdentifierString.Type.EXTERNAL_ID))
			{
				builder.externalId(warehouseString.asExternalId());
			}
			else if (warehouseString.getType().equals(IdentifierString.Type.VALUE))
			{
				builder.value(warehouseString.asValue());
			}
			else
			{
				throw new InvalidIdentifierException(warehouseIdentifier);
			}
			result = warehouseDAO.retrieveWarehouseIdBy(builder.build());
		}
		if (result == null)
		{
			throw MissingResourceException.builder()
					.resourceName("WarehouseId")
					.detail(TranslatableStrings.constant("Did not find warehouseId for AD_Org_ID=" + orgId.getRepoId() + " and identifier=" + warehouseIdentifier))
					.build();
		}

		return result;
	}

	public String getWarehouseName(@NonNull final WarehouseId warehouseId)
	{
		return warehouseDAO.getWarehouseName(warehouseId);
	}

	public JsonOutOfStockResponse handleOutOfStockRequest(
			@NonNull final String warehouseIdentifier,
			@NonNull final JsonOutOfStockNoticeRequest outOfStockInfoRequest)
	{
		if (!Boolean.TRUE.equals(outOfStockInfoRequest.getClosePendingShipmentSchedules())
				&& !Boolean.TRUE.equals(outOfStockInfoRequest.getCreateInventory()))
		{
			Loggables.addLog("WarehouseService.handleOutOfStockRequest: JsonOutOfStockNoticeRequest: closePendingShipmentSchedules and createInventory are both false! No action is performed!");

			return JsonOutOfStockResponse.builder()
					.affectedWarehouses(ImmutableList.of())
					.build();
		}

		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(outOfStockInfoRequest.getOrgCode());
		Loggables.addLog("WarehouseService.handleOutOfStockRequest: JsonOutOfStockNoticeRequest: orgCode resolved to AD_Org_ID {}!", OrgId.toRepoIdOrAny(orgId));

		final ExternalIdentifier productIdentifier = ExternalIdentifier.of(outOfStockInfoRequest.getProductIdentifier());

		final ProductId productId = productRestService.resolveProductExternalIdentifier(productIdentifier, orgId)
				.orElseThrow(() -> MissingResourceException.builder()
						.resourceIdentifier(productIdentifier.getRawValue())
						.resourceName("M_Product")
						.build());

		Loggables.addLog("WarehouseService.handleOutOfStockRequest: JsonOutOfStockNoticeRequest: productIdentifier resolved to M_Product_ID {}!", ProductId.toRepoId(productId));

		final AttributeSetInstanceId attributeSetInstanceId = jsonAttributeService.computeAttributeSetInstanceFromJson(outOfStockInfoRequest.getAttributeSetInstance())
				.orElse(null);

		Loggables.addLog("WarehouseService.handleOutOfStockRequest: JsonOutOfStockNoticeRequest: attributeSetInstance emerged to asiId: {}!", AttributeSetInstanceId.toRepoId(attributeSetInstanceId));

		final Optional<WarehouseId> targetWarehouseId = ALL.equals(warehouseIdentifier)
				? Optional.empty()
				: Optional.of(getWarehouseByIdentifier(orgId, warehouseIdentifier));

		final Map<WarehouseId, String> warehouseId2InventoryDocNo = outOfStockInfoRequest.getCreateInventory()
				? emptyWarehouse(productId, attributeSetInstanceId, targetWarehouseId.orElse(null))
				: ImmutableMap.of();

		final Map<WarehouseId, List<ShipmentScheduleId>> warehouseId2ClosedShipmentSchedules = outOfStockInfoRequest.getClosePendingShipmentSchedules()
				? handleCloseShipmentSchedulesRequest(targetWarehouseId, productId, attributeSetInstanceId)
				: ImmutableMap.of();

		return buildOutOfStockNoticeResponse(warehouseId2InventoryDocNo, warehouseId2ClosedShipmentSchedules);
	}

	@NonNull
	private Map<WarehouseId, List<ShipmentScheduleId>> handleCloseShipmentSchedulesRequest(
			@NonNull final Optional<WarehouseId> targetWarehouseId,
			@NonNull final ProductId productId,
			@Nullable final AttributeSetInstanceId attributeSetInstanceId)
	{
		final Set<WarehouseId> targetWarehouseIds = targetWarehouseId
				.map(ImmutableSet::of)
				.orElseGet(() -> ImmutableSet.copyOf(warehouseDAO.getAllWarehouseIds()));

		final ImmutableMap.Builder<WarehouseId, List<ShipmentScheduleId>> warehouseId2ClosedScheduleId = ImmutableMap.builder();

		targetWarehouseIds.forEach(targetId -> warehouseId2ClosedScheduleId.put(targetId, closeShipmentSchedules(attributeSetInstanceId, productId, targetId)));

		return warehouseId2ClosedScheduleId.build();
	}

	@NonNull
	private List<ShipmentScheduleId> closeShipmentSchedules(
			@Nullable final AttributeSetInstanceId attributeSetInstanceId,
			@NonNull final ProductId productId,
			@NonNull final WarehouseId warehouseId)
	{
		final int targetAsiId = attributeSetInstanceId != null
				? attributeSetInstanceId.getRepoId()
				: AttributeSetInstanceId.NONE.getRepoId();

		final ImmutableShipmentScheduleSegment shipmentScheduleSegment = ShipmentScheduleSegments.builder()
				.productId(productId.getRepoId())
				.warehouseId(warehouseId)
				.attributeSetInstanceId(targetAsiId)
				.build();

		final List<ShipmentScheduleId> closedShipmentScheduleIds = shipmentScheduleRepository.streamFromSegment(shipmentScheduleSegment)
				.filter(shipmentSchedule -> !shipmentSchedule.isProcessed())
				.map(ShipmentSchedule::getId)
				.peek(shipmentScheduleBL::closeShipmentSchedule)
				.collect(ImmutableList.toImmutableList());

		Loggables.addLog("WarehouseService.closeShipmentSchedules: Just closed from warehouse: {} the following shipmentSchedules: {}",
						 warehouseId, closedShipmentScheduleIds);

		return closedShipmentScheduleIds;
	}

	@NonNull
	private Map<WarehouseId, String> emptyWarehouse(
			@NonNull final ProductId productId,
			@Nullable final AttributeSetInstanceId attributeSetInstanceId,
			@Nullable final WarehouseId targetWarehouseId)
	{
		final List<HuForInventoryLine> huForInventoryLines = loadEligibleHUs(productId, attributeSetInstanceId, targetWarehouseId);

		if (huForInventoryLines.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ProductHUInventory productHUInventory = ProductHUInventory.of(productId, huForInventoryLines);

		return productHUInventory
				.mapByWarehouseId()
				.entrySet()
				.stream()
				.map(warehouse2StockEntry -> clearWarehouseStockViaInventory(warehouse2StockEntry.getKey(), warehouse2StockEntry.getValue(), attributeSetInstanceId))
				.collect(ImmutableMap.toImmutableMap(Inventory::getWarehouseId, Inventory::getDocumentNo));
	}

	@NonNull
	private Inventory clearWarehouseStockViaInventory(
			@NonNull final WarehouseId warehouseId,
			@NonNull final ProductHUInventory productHUInventory,
			@Nullable final AttributeSetInstanceId attributeSetInstanceId)
	{
		final I_M_Warehouse warehouse = warehouseDAO.getById(warehouseId);

		final OrgId orgId = OrgId.ofRepoId(warehouse.getAD_Org_ID());

		final I_C_UOM stockingUOM = productBL.getStockUOM(productHUInventory.getProductId().getRepoId());

		final InventoryHeaderCreateRequest inventoryHeaderCreateRequest = InventoryHeaderCreateRequest.builder()
				.orgId(orgId)
				.warehouseId(warehouseId)
				.docTypeId(inventoryService.getDocTypeIdByAggregationType(HUAggregationType.MULTI_HU, orgId))
				.movementDate(ZonedDateTime.now())
				.build();

		final Inventory inventory = inventoryService.createInventoryHeader(inventoryHeaderCreateRequest);

		productHUInventory.mapByLocatorId().forEach((locatorId, productHUInv) -> {

			final InventoryLineCreateRequest lineCreateRequest = InventoryLineCreateRequest.builder()
					.inventoryId(inventory.getId())
					.locatorId(locatorId)
					.aggregationType(HUAggregationType.MULTI_HU)
					.productId(productHUInv.getProductId())
					.attributeSetId(attributeSetInstanceId)
					.qtyBooked(productHUInv.getTotalQtyBooked(conversionBL, stockingUOM))
					.qtyCount(Quantity.zero(stockingUOM))
					.inventoryLineHUList(productHUInv.toInventoryLineHUs(conversionBL, UomId.ofRepoId(stockingUOM.getC_UOM_ID())))
					.build();

			inventoryService.createInventoryLine(lineCreateRequest);
		});

		inventoryService.completeDocument(inventory.getId());

		Loggables.addLog("WarehouseService.clearWarehouseStockViaInventory: Inventory (id: {}, docNo: {}) created for warehouse: {} to empty HUs: {}",
						 inventory.getId(), inventory.getDocumentNo(), inventory.getWarehouseId(), productHUInventory.getHuIds());

		return inventory;
	}

	@NonNull
	private List<HuForInventoryLine> loadEligibleHUs(
			@NonNull final ProductId productId,
			@Nullable final AttributeSetInstanceId attributeSetInstanceId,
			@Nullable final WarehouseId warehouseId)
	{
		final LocatorAndProductStrategy husFinder = HUsForInventoryStrategies.locatorAndProduct()
				.huForInventoryLineFactory(huForInventoryLineFactory)
				.productId(productId)
				.asiId(attributeSetInstanceId)
				.warehouseId(warehouseId)
				//
				.build();

		return husFinder.streamHus()
				.filter(hu -> hu.getProductId().equals(productId))
				.collect(ImmutableList.toImmutableList());
	}

	private JsonOutOfStockResponse buildOutOfStockNoticeResponse(
			@NonNull final Map<WarehouseId, String> warehouseId2InventoryDocNo,
			@NonNull final Map<WarehouseId, List<ShipmentScheduleId>> warehouseId2ClosedShipmentSchedules)
	{
		final JsonOutOfStockResponse.JsonOutOfStockResponseBuilder responseBuilder = JsonOutOfStockResponse.builder();

		final Set<WarehouseId> seenWarehouseIds = new HashSet<>();

		warehouseId2ClosedShipmentSchedules.keySet()
				.stream()
				.map(warehouseId -> {
					final List<JsonMetasfreshId> closedShipmentScheduleIds = warehouseId2ClosedShipmentSchedules.get(warehouseId)
							.stream()
							.map(ShipmentScheduleId::getRepoId)
							.map(JsonMetasfreshId::of)
							.collect(ImmutableList.toImmutableList());

					if (closedShipmentScheduleIds.isEmpty())
					{
						return null;
					}

					seenWarehouseIds.add(warehouseId);

					return JsonOutOfStockResponseItem.builder()
							.warehouseId(JsonMetasfreshId.of(warehouseId.getRepoId()))
							.closedShipmentSchedules(closedShipmentScheduleIds)
							.inventoryDocNo(warehouseId2InventoryDocNo.get(warehouseId))
							.build();
				})
				.filter(Objects::nonNull)
				.forEach(responseBuilder::affectedWarehouse);

		warehouseId2InventoryDocNo.entrySet()
				.stream()
				.filter(warehouseId2InvEntry -> !seenWarehouseIds.contains(warehouseId2InvEntry.getKey()))
				.map(warehouseId2InvDocNoEntry -> JsonOutOfStockResponseItem.builder()
						.inventoryDocNo(warehouseId2InvDocNoEntry.getValue())
						.warehouseId(JsonMetasfreshId.of(warehouseId2InvDocNoEntry.getKey().getRepoId()))
						.build())
				.forEach(responseBuilder::affectedWarehouse);

		return responseBuilder.build();
	}
}

