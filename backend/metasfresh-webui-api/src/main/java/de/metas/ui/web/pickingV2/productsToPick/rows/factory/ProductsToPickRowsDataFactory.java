package de.metas.ui.web.pickingV2.productsToPick.rows.factory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.mm.attributes.api.impl.LotNumberDateAttributeDAO;
import org.eevolution.api.IPPOrderBL;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateIssueToBOMLine;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.PickingCandidateStatus;
import de.metas.handlingunits.reservation.HUReservation;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.inoutcandidate.api.Packageable;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.material.planning.pporder.PPOrderBOMLineId;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.material.planning.pporder.impl.QtyCalculationsBOM;
import de.metas.material.planning.pporder.impl.QtyCalculationsBOMLine;
import de.metas.order.OrderLineId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperId;
import de.metas.ui.web.pickingV2.packageable.PackageableRow;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductInfo;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRow;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRowId;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRowType;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRowsData;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.lookup.LookupValueByIdSupplier;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
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

public final class ProductsToPickRowsDataFactory
{
	// services
	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);
	private final IDeveloperModeBL developerModeBL = Services.get(IDeveloperModeBL.class);
	private final IPPOrderBL ppOrdersBL = Services.get(IPPOrderBL.class);
	private final IBPartnerBL bpartnersService;
	private final HUReservationService huReservationService;
	private final PickingCandidateService pickingCandidateService;
	private final ProductInfoSupplier productInfos;

	private final LookupValueByIdSupplier locatorLookup;
	private final IAttributeStorageFactory attributesFactory;

	private final ProductsToPickSourceStorage storages = new ProductsToPickSourceStorage();
	private final Map<HuId, ImmutableAttributeSet> huAttributesCache = new HashMap<>();

	private final boolean considerAttributes;

	public static final String ATTR_LotNumber_String = LotNumberDateAttributeDAO.ATTR_LotNumber_String;
	public static final AttributeCode ATTR_LotNumber = LotNumberDateAttributeDAO.ATTR_LotNumber;

	public static final String ATTR_BestBeforeDate_String = AttributeConstants.ATTR_BestBeforeDate_String;
	public static final AttributeCode ATTR_BestBeforeDate = AttributeConstants.ATTR_BestBeforeDate;

	public static final String ATTR_RepackNumber_String = "RepackNumber"; // TODO use it as constant, see RepackNumberUtils
	public static final AttributeCode ATTR_RepackNumber = AttributeCode.ofString(ATTR_RepackNumber_String);

	private static final ImmutableSet<AttributeCode> ATTRIBUTES = ImmutableSet.of(
			ATTR_LotNumber,
			ATTR_BestBeforeDate,
			ATTR_RepackNumber);

	@Builder
	private ProductsToPickRowsDataFactory(
			@NonNull final IBPartnerBL bpartnersService,
			@NonNull final HUReservationService huReservationService,
			@NonNull final PickingCandidateService pickingCandidateService,
			//
			@NonNull final LookupValueByIdSupplier locatorLookup,
			//
			final boolean considerAttributes)
	{
		this.bpartnersService = bpartnersService;
		this.huReservationService = huReservationService;
		this.pickingCandidateService = pickingCandidateService;
		productInfos = ProductInfoSupplier.builder()
				.productsRepo(productsRepo)
				.uomsRepo(uomsRepo)
				.build();

		this.locatorLookup = locatorLookup;

		final IAttributeStorageFactoryService attributeStorageFactoryService = Services.get(IAttributeStorageFactoryService.class);
		attributesFactory = attributeStorageFactoryService.createHUAttributeStorageFactory();

		this.considerAttributes = considerAttributes;
	}

	public ProductsToPickRowsData create(final PackageableRow packageableRow)
	{
		final ImmutableList<ProductsToPickRow> rows = packageableRow.getPackageables()
				.stream()
				.map(this::toAllocablePackageable)
				.flatMap(this::createRowsAndStream)
				.collect(ImmutableList.toImmutableList());

		return ProductsToPickRowsData.builder()
				.pickingCandidateService(pickingCandidateService)
				.rows(rows)
				.orderBy(DocumentQueryOrderBy.byFieldName(ProductsToPickRow.FIELD_Locator))
				.build();
	}

	private AllocablePackageable toAllocablePackageable(@NonNull final Packageable packageable)
	{
		final Quantity qtyToAllocateTarget = packageable.getQtyToDeliver()
				.subtract(packageable.getQtyPickedNotDelivered())
				// IMPORTANT: don't subtract the Qty PickedPlanned
				// because we will also allocate existing DRAFT picking candidates
				// .subtract(packageable.getQtyPickedPlanned())
				.toZeroIfNegative();

		return AllocablePackageable.builder()
				.customerId(packageable.getCustomerId())
				.productId(packageable.getProductId())
				.asiId(packageable.getAsiId())
				.shipmentScheduleId(packageable.getShipmentScheduleId())
				.bestBeforePolicy(packageable.getBestBeforePolicy())
				.warehouseId(packageable.getWarehouseId())
				.salesOrderLineIdOrNull(packageable.getSalesOrderLineIdOrNull())
				.shipperId(packageable.getShipperId())
				.pickFromOrderId(packageable.getPickFromOrderId())
				.qtyToAllocateTarget(qtyToAllocateTarget)
				.build();
	}

	private Stream<ProductsToPickRow> createRowsAndStream(final AllocablePackageable packageable)
	{
		final ArrayList<ProductsToPickRow> rows = new ArrayList<>();
		rows.addAll(createRowsFromExistingPickingCandidates(packageable));
		rows.addAll(createRowsFromHUs(packageable));

		if (!packageable.isAllocated())
		{
			final QtyCalculationsBOM pickingOrderBOM = getPickingOrderBOM(packageable).orElse(null);
			if (pickingOrderBOM != null)
			{
				rows.add(createRowsFromPickingOrder(pickingOrderBOM, packageable));
			}

		}

		if (!packageable.isAllocated())
		{
			rows.add(createQtyNotAvailableRowForRemainingQtyToAllocate(packageable));
		}

		return rows.stream();
	}

	private Optional<QtyCalculationsBOM> getPickingOrderBOM(final AllocablePackageable packageable)
	{
		final PPOrderId pickFromOrderId = packageable.getPickFromOrderId();
		return pickFromOrderId != null
				? ppOrdersBL.getOpenPickingOrderBOM(pickFromOrderId)
				: Optional.empty();
	}

	private List<ProductsToPickRow> createRowsFromExistingPickingCandidates(final AllocablePackageable packageable)
	{
		final List<PickingCandidate> pickingCandidates = pickingCandidateService.getByShipmentScheduleIdAndStatus(packageable.getShipmentScheduleId(), PickingCandidateStatus.Draft);

		return pickingCandidates
				.stream()
				.map(pickingCandidate -> createRowFromExistingPickingCandidate(packageable, pickingCandidate))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

	private ProductsToPickRow createRowFromExistingPickingCandidate(
			@NonNull final AllocablePackageable packageable,
			@NonNull final PickingCandidate existingPickingCandidate)
	{
		final PickFrom pickFrom = existingPickingCandidate.getPickFrom();
		if (pickFrom.isPickFromHU())
		{
			final Quantity qty;

			final HuId pickFromHUId = pickFrom.getHuId();
			if (pickFromHUId != null)
			{
				final ProductId productId = packageable.getProductId();
				final ReservableStorage storage = storages.getStorage(pickFromHUId, productId);
				qty = storage.reserve(packageable, existingPickingCandidate.getQtyPicked());
			}
			else
			{
				qty = existingPickingCandidate.getQtyPicked();
			}

			return prepareRow_PickFromHU(packageable)
					.pickFromHUId(pickFromHUId)
					.qty(qty)
					.existingPickingCandidate(existingPickingCandidate)
					.build();
		}
		else if (pickFrom.isPickFromPickingOrder())
		{
			final ImmutableList<ProductsToPickRow> includedRows = existingPickingCandidate.getIssuesToPickingOrder()
					.stream()
					.map(issueToBOMLine -> prepareRow_IssueComponentsToPickingOrder(toBOMLineAllocablePackageable(issueToBOMLine, packageable))
							.pickFromHUId(issueToBOMLine.getIssueFromHUId())
							.qty(issueToBOMLine.getQtyToIssue())
							.build())
					.collect(ImmutableList.toImmutableList());

			return prepareRow_PickFromPickingOrder(packageable)
					.pickFromPickingOrderId(pickFrom.getPickingOrderId())
					.qty(existingPickingCandidate.getQtyPicked())
					.existingPickingCandidate(existingPickingCandidate)
					.includedRows(includedRows)
					.build();
		}
		else
		{
			throw new AdempiereException("Unknown " + pickFrom);
		}
	}

	private List<ProductsToPickRow> createRowsFromHUs(final AllocablePackageable packageable)
	{
		if (packageable.isAllocated())
		{
			return ImmutableList.of();
		}

		final ImmutableSet<HuId> huIdsAvailableToPick = ImmutableSet.<HuId> builder()
				.addAll(getHuIdsReservedForSalesOrderLine(packageable)) // reserved HUs first
				.addAll(getHuIdsAvailableToAllocate(packageable))
				.build();

		final List<ProductsToPickRow> rowsWithZeroQty = huIdsAvailableToPick.stream()
				.map(pickFromHUId -> createZeroQtyRowFromHU(packageable, pickFromHUId))
				.collect(ImmutableList.toImmutableList());

		final ShipmentAllocationBestBeforePolicy bestBeforePolicy = getBestBeforePolicy(packageable);

		return rowsWithZeroQty.stream()
				.sorted(Comparator
						.<ProductsToPickRow> comparingInt(row -> row.isHuReservedForThisRow() ? 0 : 1) // consider reserved HU first
						.thenComparing(bestBeforePolicy.comparator(ProductsToPickRow::getExpiringDate))) // then first/last expiring HU
				.map(row -> allocateRowFromHU(row, packageable))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

	private ShipmentAllocationBestBeforePolicy getBestBeforePolicy(final AllocablePackageable packageable)
	{
		final Optional<ShipmentAllocationBestBeforePolicy> bestBeforePolicy = packageable.getBestBeforePolicy();
		if (bestBeforePolicy.isPresent())
		{
			return bestBeforePolicy.get();
		}

		final BPartnerId bpartnerId = packageable.getCustomerId();
		return bpartnersService.getBestBeforePolicy(bpartnerId);
	}

	private Set<HuId> getHuIdsReservedForSalesOrderLine(final AllocablePackageable packageable)
	{
		final OrderLineId salesOrderLineId = packageable.getSalesOrderLineIdOrNull();
		if (salesOrderLineId == null)
		{
			return ImmutableSet.of();
		}

		final HUReservation huReservation = huReservationService.getBySalesOrderLineId(salesOrderLineId).orElse(null);
		if (huReservation == null)
		{
			return ImmutableSet.of();
		}

		return huReservation.getVhuIds();
	}

	private Set<HuId> getHuIdsAvailableToAllocate(final AllocablePackageable packageable)
	{
		final OrderLineId salesOrderLine = packageable.getSalesOrderLineIdOrNull();

		final Set<HuId> huIds = huReservationService.prepareHUQuery()
				.warehouseId(packageable.getWarehouseId())
				.productId(packageable.getProductId())
				.asiId(considerAttributes ? packageable.getAsiId() : null)
				.reservedToSalesOrderLineIdOrNotReservedAtAll(salesOrderLine)
				.build()
				.listIds();

		warmUpCacheForHuIds(huIds);

		return huIds;
	}

	private void warmUpCacheForHuIds(final Collection<HuId> huIds)
	{
		storages.warmUpCacheForHuIds(huIds); // pre-load all HUs
		huReservationService.warmup(huIds);
	}

	@Nullable
	private ProductsToPickRow allocateRowFromHU(final ProductsToPickRow row, final AllocablePackageable packageable)
	{
		if (packageable.isAllocated())
		{
			return null;
		}

		final HuId pickFromHUId = row.getPickFromHUId();
		if (pickFromHUId == null)
		{
			throw new AdempiereException("No pickFromHUId set for " + row);
		}

		final ProductId productId = packageable.getProductId();
		final ReservableStorage storage = storages.getStorage(pickFromHUId, productId);
		final Quantity qty = storage.reserve(packageable);
		if (qty.isZero())
		{
			return null;
		}

		return row.withQty(qty);
	}

	private ProductsToPickRow createZeroQtyRowFromHU(@NonNull final AllocablePackageable packageable, @NonNull final HuId pickFromHUId)
	{
		final boolean isPickFromHU = packageable.getIssueToOrderBOMLineId() == null;
		if (isPickFromHU)
		{
			return prepareRow_PickFromHU(packageable)
					.pickFromHUId(pickFromHUId)
					.qty(packageable.getQtyToAllocate().toZero())
					.build();
		}
		else
		{
			return prepareRow_IssueComponentsToPickingOrder(packageable)
					.pickFromHUId(pickFromHUId)
					.qty(packageable.getQtyToAllocate().toZero())
					.build();
		}
	}

	private ProductsToPickRow createQtyNotAvailableRowForRemainingQtyToAllocate(@NonNull final AllocablePackageable packageable)
	{
		final Quantity qty = packageable.getQtyToAllocate();
		packageable.allocateQty(qty);

		return prepareRow()
				.rowType(ProductsToPickRowType.UNALLOCABLE)
				.productId(packageable.getProductId())
				.shipmentScheduleId(packageable.getShipmentScheduleId())
				.salesOrderLineId(packageable.getSalesOrderLineIdOrNull())
				.shipperId(packageable.getShipperId())
				.issueToOrderBOMLineId(packageable.getIssueToOrderBOMLineId())
				.qty(qty)
				.build();
	}

	private ProductsToPickRowBuilder prepareRow_PickFromHU(@NonNull final AllocablePackageable packageable)
	{
		return prepareRow()
				.rowType(ProductsToPickRowType.PICK_FROM_HU)
				.productId(packageable.getProductId())
				.shipmentScheduleId(packageable.getShipmentScheduleId())
				.salesOrderLineId(packageable.getSalesOrderLineIdOrNull())
				.shipperId(packageable.getShipperId());
	}

	private ProductsToPickRowBuilder prepareRow_PickFromPickingOrder(@NonNull final AllocablePackageable finishedGoodPackageable)
	{
		return prepareRow()
				.rowType(ProductsToPickRowType.PICK_FROM_PICKING_ORDER)
				.productId(finishedGoodPackageable.getProductId())
				.shipmentScheduleId(finishedGoodPackageable.getShipmentScheduleId())
				.salesOrderLineId(finishedGoodPackageable.getSalesOrderLineIdOrNull())
				.shipperId(finishedGoodPackageable.getShipperId());
	}

	private ProductsToPickRowBuilder prepareRow_IssueComponentsToPickingOrder(@NonNull final AllocablePackageable packageable)
	{
		return prepareRow()
				.rowType(ProductsToPickRowType.ISSUE_COMPONENTS_TO_PICKING_ORDER)
				.productId(packageable.getProductId())
				.shipmentScheduleId(packageable.getShipmentScheduleId())
				.salesOrderLineId(packageable.getSalesOrderLineIdOrNull())
				.shipperId(packageable.getShipperId())
				.issueToOrderBOMLineId(packageable.getIssueToOrderBOMLineId());

	}

	@Builder(builderMethodName = "prepareRow", builderClassName = "ProductsToPickRowBuilder")
	private ProductsToPickRow createRow(
			@NonNull final ProductsToPickRowType rowType,
			@NonNull final ProductId productId,
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			@Nullable final OrderLineId salesOrderLineId,
			@Nullable final ShipperId shipperId,
			@Nullable final PPOrderBOMLineId issueToOrderBOMLineId,
			@NonNull final Quantity qty,
			@Nullable final HuId pickFromHUId,
			@Nullable final PPOrderId pickFromPickingOrderId,
			@Nullable final PickingCandidate existingPickingCandidate,
			@Nullable final List<ProductsToPickRow> includedRows)
	{
		final ProductInfo productInfo = productInfos.getByProductId(productId);

		final boolean huReservedForThisRow = pickFromHUId != null
				&& salesOrderLineId != null
				&& huReservationService.isVhuIdReservedToSalesOrderLineId(pickFromHUId, salesOrderLineId);

		final LookupValue locator = pickFromHUId != null ? getLocatorLookupValueByHuId(pickFromHUId) : null;
		final ImmutableAttributeSet attributes = pickFromHUId != null ? getHUAttributes(pickFromHUId) : ImmutableAttributeSet.EMPTY;

		final ProductsToPickRowId rowId = ProductsToPickRowId.builder()
				.productId(productInfo.getProductId())
				.shipmentScheduleId(shipmentScheduleId)
				.pickFromHUId(pickFromHUId)
				.pickFromPickingOrderId(pickFromPickingOrderId)
				.issueToOrderBOMLineId(issueToOrderBOMLineId)
				.build();

		return ProductsToPickRow.builder()
				.rowId(rowId)
				.rowType(rowType)
				//
				.productInfo(productInfo)
				.huReservedForThisRow(huReservedForThisRow)
				//
				.locator(locator)
				//
				// Attributes:
				.lotNumber(attributes.getValueAsStringIfExists(ATTR_LotNumber).orElseGet(() -> buildLotNumberFromHuId(pickFromHUId)))
				.expiringDate(attributes.getValueAsLocalDateIfExists(ATTR_BestBeforeDate).orElse(null))
				.repackNumber(attributes.getValueAsStringIfExists(ATTR_RepackNumber).orElse(null))
				//
				.qty(qty)
				//
				.shipperId(shipperId)
				//
				.includedRows(includedRows)
				//
				.build()
				.withUpdatesFromPickingCandidateIfNotNull(existingPickingCandidate);
	}

	private String buildLotNumberFromHuId(final HuId huId)
	{
		if (huId == null)
		{
			return null;
		}

		if (!developerModeBL.isEnabled())
		{
			return null;
		}

		return "<" + huId.getRepoId() + ">";
	}

	private LookupValue getLocatorLookupValueByHuId(final HuId huId)
	{
		final I_M_HU hu = storages.getHU(huId);
		final int locatorId = hu.getM_Locator_ID();
		if (locatorId <= 0)
		{
			return null;
		}
		return locatorLookup.findById(locatorId);
	}

	private ImmutableAttributeSet getHUAttributes(final HuId huId)
	{
		return huAttributesCache.computeIfAbsent(huId, this::retrieveHUAttributes);
	}

	private ImmutableAttributeSet retrieveHUAttributes(final HuId huId)
	{
		final I_M_HU hu = storages.getHU(huId);
		final IAttributeStorage attributes = attributesFactory.getAttributeStorage(hu);
		return ImmutableAttributeSet.createSubSet(attributes, a -> ATTRIBUTES.contains(AttributeCode.ofString(a.getValue())));
	}

	private ProductsToPickRow createRowsFromPickingOrder(
			final QtyCalculationsBOM pickingOrderBOM,
			final AllocablePackageable finishedGoodPackageable)
	{
		final List<ProductsToPickRow> bomLineRows = new ArrayList<>();
		for (final QtyCalculationsBOMLine bomLine : pickingOrderBOM.getLines())
		{
			bomLineRows.addAll(createRowsFromPickingOrderBOMLine(bomLine, finishedGoodPackageable));
		}

		final Quantity qtyOfFinishedGoods = finishedGoodPackageable.getQtyToAllocate();
		finishedGoodPackageable.allocateQty(qtyOfFinishedGoods);

		return prepareRow_PickFromPickingOrder(finishedGoodPackageable)
				.pickFromPickingOrderId(pickingOrderBOM.getOrderId())
				.qty(qtyOfFinishedGoods)
				.includedRows(bomLineRows)
				.build();
	}

	private List<ProductsToPickRow> createRowsFromPickingOrderBOMLine(
			@NonNull final QtyCalculationsBOMLine bomLine,
			@NonNull final AllocablePackageable finishedGoodPackageable)
	{
		final AllocablePackageable bomLinePackageable = toBOMLineAllocablePackageable(bomLine, finishedGoodPackageable);

		final ArrayList<ProductsToPickRow> rows = new ArrayList<>();
		rows.addAll(createRowsFromHUs(bomLinePackageable));

		if (!bomLinePackageable.isAllocated())
		{
			rows.add(createQtyNotAvailableRowForRemainingQtyToAllocate(bomLinePackageable));
		}

		return rows;
	}

	private AllocablePackageable toBOMLineAllocablePackageable(final QtyCalculationsBOMLine bomLine, final AllocablePackageable finishedGoodPackageable)
	{
		final Quantity qty = bomLine.computeQtyRequired(finishedGoodPackageable.getQtyToAllocate());

		return finishedGoodPackageable.toBuilder()
				.productId(bomLine.getProductId())
				.qtyToAllocateTarget(qty)
				.issueToOrderBOMLineId(bomLine.getOrderBOMLineId())
				.build();
	}

	private AllocablePackageable toBOMLineAllocablePackageable(final PickingCandidateIssueToBOMLine issueToBOMLine, final AllocablePackageable finishedGoodPackageable)
	{
		return finishedGoodPackageable.toBuilder()
				.productId(issueToBOMLine.getProductId())
				.qtyToAllocateTarget(issueToBOMLine.getQtyToIssue())
				.issueToOrderBOMLineId(issueToBOMLine.getIssueToOrderBOMLineId())
				.build();
	}

}
