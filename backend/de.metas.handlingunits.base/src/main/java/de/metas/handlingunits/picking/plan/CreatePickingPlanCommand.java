/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.picking.plan;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateIssueToBOMLine;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.PickingCandidateStatus;
import de.metas.handlingunits.reservation.HUReservation;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.order.OrderLineId;
import de.metas.picking.api.Packageable;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.warehouse.LocatorId;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.QtyCalculationsBOM;
import org.eevolution.api.QtyCalculationsBOMLine;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class CreatePickingPlanCommand
{
	private final IDeveloperModeBL developerModeBL = Services.get(IDeveloperModeBL.class);
	private final IPPOrderBL ppOrdersBL = Services.get(IPPOrderBL.class);
	private final IBPartnerBL bpartnersService;
	private final HUReservationService huReservationService;
	private final PickingCandidateRepository pickingCandidateRepository;
	private final IAttributeStorageFactory attributesFactory;

	private final ImmutableList<Packageable> packageables;
	private final boolean considerAttributes;

	private final ProductsToPickSourceStorage storages = new ProductsToPickSourceStorage();
	private final Map<HuId, ImmutableAttributeSet> huAttributesCache = new HashMap<>();

	private static final AttributeCode ATTR_SerialNo = AttributeConstants.ATTR_SerialNo;
	private static final AttributeCode ATTR_LotNumber = AttributeConstants.ATTR_LotNumber;
	private static final AttributeCode ATTR_BestBeforeDate = AttributeConstants.ATTR_BestBeforeDate;
	private static final AttributeCode ATTR_RepackNumber = AttributeConstants.ATTR_RepackNumber;

	private static final ImmutableSet<AttributeCode> ATTRIBUTES = ImmutableSet.of(
			ATTR_SerialNo,
			ATTR_LotNumber,
			ATTR_BestBeforeDate,
			ATTR_RepackNumber);

	@Builder
	private CreatePickingPlanCommand(
			@NonNull final IBPartnerBL bpartnersService,
			@NonNull final HUReservationService huReservationService,
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			//
			@NonNull final CreatePickingPlanRequest request)
	{
		this.bpartnersService = bpartnersService;
		this.huReservationService = huReservationService;
		this.pickingCandidateRepository = pickingCandidateRepository;
		this.attributesFactory = Services.get(IAttributeStorageFactoryService.class).createHUAttributeStorageFactory();

		this.packageables = request.getPackageables();
		this.considerAttributes = request.isConsiderAttributes();
	}

	public PickingPlan execute()
	{
		final ImmutableList<PickingPlanLine> lines = packageables
				.stream()
				.map(CreatePickingPlanCommand::toAllocablePackageable)
				.flatMap(this::createLineAndStream)
				.collect(ImmutableList.toImmutableList());

		return PickingPlan.builder()
				.lines(lines)
				.build();
	}

	private static AllocablePackageable toAllocablePackageable(@NonNull final Packageable packageable)
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

	private SourceDocumentInfo extractSourceDocumentInfo(@NonNull final AllocablePackageable packageable)
	{
		return SourceDocumentInfo.builder()
				.shipmentScheduleId(packageable.getShipmentScheduleId())
				.salesOrderLineId(packageable.getSalesOrderLineIdOrNull())
				.shipperId(packageable.getShipperId())
				.existingPickingCandidate(null)
				.build();
	}

	private Stream<PickingPlanLine> createLineAndStream(final AllocablePackageable packageable)
	{
		final ArrayList<PickingPlanLine> lines = new ArrayList<>();
		lines.addAll(createLinesFromExistingPickingCandidates(packageable));
		lines.addAll(createLinesFromHUs(packageable));

		if (!packageable.isAllocated())
		{
			getPickingOrderBOM(packageable)
					.ifPresent(pickingOrderBOM -> lines.add(createLinesFromPickingOrder(pickingOrderBOM, packageable)));

		}

		if (!packageable.isAllocated())
		{
			lines.add(createQtyNotAvailableRowForRemainingQtyToAllocate(packageable));
		}

		return lines.stream();
	}

	private Optional<QtyCalculationsBOM> getPickingOrderBOM(final AllocablePackageable packageable)
	{
		final PPOrderId pickFromOrderId = packageable.getPickFromOrderId();
		return pickFromOrderId != null
				? ppOrdersBL.getOpenPickingOrderBOM(pickFromOrderId)
				: Optional.empty();
	}

	private List<PickingPlanLine> createLinesFromExistingPickingCandidates(@NonNull final AllocablePackageable packageable)
	{
		final List<PickingCandidate> pickingCandidates = pickingCandidateRepository.getByShipmentScheduleIdAndStatus(packageable.getShipmentScheduleId(), PickingCandidateStatus.Draft);

		return pickingCandidates
				.stream()
				.map(pickingCandidate -> createLineFromExistingPickingCandidate(packageable, pickingCandidate))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

	private PickingPlanLine createLineFromExistingPickingCandidate(
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

			return prepareLine_PickFromHU(packageable)
					.pickFromHUId(pickFromHUId)
					.qty(qty)
					.existingPickingCandidate(existingPickingCandidate)
					.build();
		}
		else if (pickFrom.isPickFromPickingOrder())
		{
			final ImmutableList<PickingPlanLine> issueToBOMLines = existingPickingCandidate.getIssuesToPickingOrder()
					.stream()
					.map(issueToBOMLine -> prepareLine_IssueComponentsToPickingOrder(toBOMLineAllocablePackageable(issueToBOMLine, packageable))
							.qty(issueToBOMLine.getQtyToIssue())
							.build())
					.collect(ImmutableList.toImmutableList());

			return prepareLine_PickFromPickingOrder(packageable)
					.qty(existingPickingCandidate.getQtyPicked())
					.pickFromPickingOrder(PickFromPickingOrder.builder()
							.orderId(Objects.requireNonNull(pickFrom.getPickingOrderId()))
							.issueToBOMLines(issueToBOMLines)
							.build())
					.existingPickingCandidate(existingPickingCandidate)
					.build();
		}
		else
		{
			throw new AdempiereException("Unknown " + pickFrom);
		}
	}

	private List<PickingPlanLine> createLinesFromHUs(final AllocablePackageable packageable)
	{
		if (packageable.isAllocated())
		{
			return ImmutableList.of();
		}

		final ImmutableSet<HuId> huIdsAvailableToPick = ImmutableSet.<HuId>builder()
				.addAll(getHuIdsReservedForSalesOrderLine(packageable)) // reserved HUs first
				.addAll(getHuIdsAvailableToAllocate(packageable))
				.build();

		final List<PickingPlanLine> lineWithZeroQty = huIdsAvailableToPick.stream()
				.map(pickFromHUId -> createZeroQtyLineFromHU(packageable, pickFromHUId))
				.collect(ImmutableList.toImmutableList());

		final ShipmentAllocationBestBeforePolicy bestBeforePolicy = getBestBeforePolicy(packageable);

		return lineWithZeroQty.stream()
				.sorted(Comparator
						.<PickingPlanLine>comparingInt(line -> isHUReservedForThisLine(line) ? 0 : 1) // consider reserved HU first
						.thenComparing(bestBeforePolicy.comparator(CreatePickingPlanCommand::getExpiringDate))) // then first/last expiring HU
				.map(line -> allocateLineFromHU(line, packageable))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

	private static boolean isHUReservedForThisLine(final PickingPlanLine line)
	{
		return line.getPickFromHU() != null && line.getPickFromHU().isHuReservedForThisLine();
	}

	@Nullable
	private static LocalDate getExpiringDate(final PickingPlanLine line)
	{
		return line.getPickFromHU() != null ? line.getPickFromHU().getExpiringDate() : null;
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
	private PickingPlanLine allocateLineFromHU(final PickingPlanLine line, final AllocablePackageable packageable)
	{
		if (packageable.isAllocated())
		{
			return null;
		}

		final PickFromHU pickFromHU = line.getPickFromHU();
		if (pickFromHU == null)
		{
			throw new AdempiereException("No pickFromHU set for " + line);
		}

		final ReservableStorage storage = storages.getStorage(pickFromHU.getHuId(), packageable.getProductId());
		final Quantity qty = storage.reserve(packageable);
		if (qty.isZero())
		{
			return null;
		}

		return line.withQty(qty);
	}

	private PickingPlanLine createZeroQtyLineFromHU(@NonNull final AllocablePackageable packageable, @NonNull final HuId huId)
	{
		if (packageable.getIssueToBOMLine() == null)
		{
			return prepareLine_PickFromHU(packageable)
					.pickFromHUId(huId)
					.qty(packageable.getQtyToAllocate().toZero())
					.build();
		}
		else
		{
			return prepareLine_IssueComponentsToPickingOrder(packageable)
					.issueToBOMLine(packageable.getIssueToBOMLine().withIssueFromHUId(huId))
					.qty(packageable.getQtyToAllocate().toZero())
					.build();
		}
	}

	private PickingPlanLine createQtyNotAvailableRowForRemainingQtyToAllocate(@NonNull final AllocablePackageable packageable)
	{
		final Quantity qty = packageable.getQtyToAllocate();
		packageable.allocateQty(qty);

		return prepareLine()
				.type(PickingPlanLineType.UNALLOCABLE)
				.sourceDocumentInfo(extractSourceDocumentInfo(packageable))
				.productId(packageable.getProductId())
				//.issueToOrderBOMLineId(packageable.getIssueToOrderBOMLineId()) // TODO
				.qty(qty)
				.build();
	}

	private $LineBuilder prepareLine_PickFromHU(@NonNull final AllocablePackageable packageable)
	{
		return prepareLine()
				.type(PickingPlanLineType.PICK_FROM_HU)
				.sourceDocumentInfo(extractSourceDocumentInfo(packageable))
				.productId(packageable.getProductId());
	}

	private $LineBuilder prepareLine_PickFromPickingOrder(@NonNull final AllocablePackageable finishedGoodPackageable)
	{
		return prepareLine()
				.type(PickingPlanLineType.PICK_FROM_PICKING_ORDER)
				.sourceDocumentInfo(extractSourceDocumentInfo(finishedGoodPackageable))
				.productId(finishedGoodPackageable.getProductId());
	}

	private $LineBuilder prepareLine_IssueComponentsToPickingOrder(@NonNull final AllocablePackageable packageable)
	{
		return prepareLine()
				.type(PickingPlanLineType.ISSUE_COMPONENTS_TO_PICKING_ORDER)
				.sourceDocumentInfo(extractSourceDocumentInfo(packageable))
				.productId(packageable.getProductId())
				.issueToBOMLine(packageable.getIssueToBOMLine());

	}

	@Builder(builderMethodName = "prepareLine", builderClassName = "$LineBuilder")
	private PickingPlanLine createLine(
			@NonNull final PickingPlanLineType type,
			@NonNull final SourceDocumentInfo sourceDocumentInfo,
			@Nullable final PickingCandidate existingPickingCandidate,
			//
			@NonNull final ProductId productId,
			@NonNull final Quantity qty,
			//
			@Nullable final HuId pickFromHUId,
			@Nullable final PickFromPickingOrder pickFromPickingOrder,
			@Nullable final IssueToBOMLine issueToBOMLine)
	{
		final PickFromHU pickFromHU;
		if (pickFromHUId != null)
		{
			final LocatorId locatorId = getLocatorIdByHuId(pickFromHUId);
			final OrderLineId salesOrderLineId = sourceDocumentInfo.getSalesOrderLineId();
			final boolean huReservedForThisLine = salesOrderLineId != null
					&& huReservationService.isVhuIdReservedToSalesOrderLineId(pickFromHUId, salesOrderLineId);
			final ImmutableAttributeSet attributes = getHUAttributes(pickFromHUId);

			pickFromHU = PickFromHU.builder()
					.huId(pickFromHUId)
					.huReservedForThisLine(huReservedForThisLine)
					.locatorId(locatorId)
					//
					.huCode(String.valueOf(pickFromHUId.getRepoId()))
					.serialNo(attributes.getValueAsStringIfExists(ATTR_SerialNo).orElse(null))
					.lotNumber(attributes.getValueAsStringIfExists(ATTR_LotNumber).orElseGet(() -> buildLotNumberFromHuId(pickFromHUId)))
					.expiringDate(attributes.getValueAsLocalDateIfExists(ATTR_BestBeforeDate).orElse(null))
					.repackNumber(attributes.getValueAsStringIfExists(ATTR_RepackNumber).orElse(null))
					//
					.build();
		}
		else if (pickFromPickingOrder != null)
		{
			pickFromHU = null;
		}
		else
		{
			// this is the NOT ALLOCABLE case
			pickFromHU = null;
		}

		if (CoalesceUtil.countNotNulls(pickFromHU, pickFromPickingOrder, issueToBOMLine) > 1)
		{
			throw new AdempiereException("Maxim one of the following shall be set: " + Arrays.asList(pickFromHU, pickFromPickingOrder, issueToBOMLine));
		}

		return PickingPlanLine.builder()
				.type(type)
				.sourceDocumentInfo(sourceDocumentInfo.withExistingPickingCandidate(existingPickingCandidate))
				//
				.productId(productId)
				.qty(qty)
				//
				.pickFromHU(pickFromHU)
				.pickFromPickingOrder(pickFromPickingOrder)
				.issueToBOMLine(issueToBOMLine)
				//
				.build()
				;
	}

	@Nullable
	private String buildLotNumberFromHuId(@Nullable final HuId huId)
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

	private LocatorId getLocatorIdByHuId(final HuId huId)
	{
		final I_M_HU hu = storages.getHU(huId);
		return IHandlingUnitsBL.extractLocatorId(hu);
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

	private PickingPlanLine createLinesFromPickingOrder(
			final QtyCalculationsBOM pickingOrderBOM,
			final AllocablePackageable finishedGoodPackageable)
	{
		final List<PickingPlanLine> bomLines = new ArrayList<>();
		for (final QtyCalculationsBOMLine bomLine : pickingOrderBOM.getLines())
		{
			bomLines.addAll(createLinesFromPickingOrderBOMLine(bomLine, finishedGoodPackageable));
		}

		final Quantity qtyOfFinishedGoods = finishedGoodPackageable.getQtyToAllocate();
		finishedGoodPackageable.allocateQty(qtyOfFinishedGoods);

		return prepareLine_PickFromPickingOrder(finishedGoodPackageable)
				.qty(qtyOfFinishedGoods)
				.pickFromPickingOrder(PickFromPickingOrder.builder()
						.orderId(Objects.requireNonNull(pickingOrderBOM.getOrderId()))
						.issueToBOMLines(bomLines)
						.build())
				.build();
	}

	private List<PickingPlanLine> createLinesFromPickingOrderBOMLine(
			@NonNull final QtyCalculationsBOMLine bomLine,
			@NonNull final AllocablePackageable finishedGoodPackageable)
	{
		final AllocablePackageable bomLinePackageable = toBOMLineAllocablePackageable(bomLine, finishedGoodPackageable);

		final ArrayList<PickingPlanLine> lines = new ArrayList<>(createLinesFromHUs(bomLinePackageable));

		if (!bomLinePackageable.isAllocated())
		{
			lines.add(createQtyNotAvailableRowForRemainingQtyToAllocate(bomLinePackageable));
		}

		return lines;
	}

	private AllocablePackageable toBOMLineAllocablePackageable(final QtyCalculationsBOMLine bomLine, final AllocablePackageable finishedGoodPackageable)
	{
		final Quantity qty = bomLine.computeQtyRequired(finishedGoodPackageable.getQtyToAllocate());

		return finishedGoodPackageable.toBuilder()
				.productId(bomLine.getProductId())
				.qtyToAllocateTarget(qty)
				.issueToBOMLine(IssueToBOMLine.builder()
						.issueToOrderBOMLineId(Objects.requireNonNull(bomLine.getOrderBOMLineId()))
						.issueFromHUId(null) // N/A
						.build())
				.build();
	}

	private AllocablePackageable toBOMLineAllocablePackageable(final PickingCandidateIssueToBOMLine candidate, final AllocablePackageable finishedGoodPackageable)
	{
		return finishedGoodPackageable.toBuilder()
				.productId(candidate.getProductId())
				.qtyToAllocateTarget(candidate.getQtyToIssue())
				.issueToBOMLine(IssueToBOMLine.builder()
						.issueToOrderBOMLineId(candidate.getIssueToOrderBOMLineId())
						.issueFromHUId(candidate.getIssueFromHUId())
						.build())
				.build();
	}
}
