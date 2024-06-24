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

package de.metas.handlingunits.picking.plan.generator;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateIssueToBOMLine;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.PickingCandidateStatus;
import de.metas.handlingunits.picking.plan.generator.allocableHUStorages.AllocableStorage;
import de.metas.handlingunits.picking.plan.generator.allocableHUStorages.AllocableStoragesSupplier;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.AlternativePickFrom;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.AlternativePickFromKey;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.AlternativePickFromKeys;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.AlternativePickFromsList;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.HUsLoadingCache;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHU;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHUsGetRequest;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHUsSupplier;
import de.metas.handlingunits.picking.plan.model.IssueToBOMLine;
import de.metas.handlingunits.picking.plan.model.PickFromPickingOrder;
import de.metas.handlingunits.picking.plan.model.PickingPlan;
import de.metas.handlingunits.picking.plan.model.PickingPlanLine;
import de.metas.handlingunits.picking.plan.model.PickingPlanLineType;
import de.metas.handlingunits.picking.plan.model.SourceDocumentInfo;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.order.OrderAndLineId;
import de.metas.picking.api.Packageable;
import de.metas.picking.api.PackageableList;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.QtyCalculationsBOM;
import org.eevolution.api.QtyCalculationsBOMLine;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class CreatePickingPlanCommand
{
	private final IPPOrderBL ppOrdersBL = Services.get(IPPOrderBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IBPartnerBL bpartnersService;
	private final PickingCandidateRepository pickingCandidateRepository;

	//
	// Params
	private final PackageableList packageables;

	//
	// State
	private final PickFromHUsSupplier pickFromHUsSupplier;
	private final AllocableStoragesSupplier storages;

	@Builder
	private CreatePickingPlanCommand(
			@NonNull final IBPartnerBL bpartnersService,
			@NonNull final HUReservationService huReservationService,
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			//
			@NonNull final CreatePickingPlanRequest request,
			@Nullable final Boolean fallbackLotNumberToHUValue)
	{
		final IDeveloperModeBL developerModeBL = Services.get(IDeveloperModeBL.class);
		this.bpartnersService = bpartnersService;
		this.pickingCandidateRepository = pickingCandidateRepository;

		this.packageables = request.getPackageables();

		final HUsLoadingCache husCache = new HUsLoadingCache(PickFromHUsSupplier.ATTRIBUTES);
		this.storages = new AllocableStoragesSupplier(huReservationService, husCache);
		this.pickFromHUsSupplier = PickFromHUsSupplier.builder()
				.huReservationService(huReservationService)
				.sharedHUsCache(husCache)
				.considerAttributes(request.isConsiderAttributes())
				.fallbackLotNumberToHUValue(fallbackLotNumberToHUValue != null
						? fallbackLotNumberToHUValue
						: developerModeBL.isEnabled())
				.build();
	}

	public PickingPlan execute()
	{
		ImmutableList<PickingPlanLine> lines = packageables
				.stream()
				.map(CreatePickingPlanCommand::toAllocablePackageable)
				.flatMap(this::createLinesAndStream)
				.collect(ImmutableList.toImmutableList());

		lines = lines.stream()
				.map(this::updateAlternativeKeys)
				.collect(ImmutableList.toImmutableList());

		return PickingPlan.builder()
				.lines(lines)
				.alternatives(getRelevantAlternativesFor(lines))
				.build();
	}

	private static AllocablePackageable toAllocablePackageable(@NonNull final Packageable packageable)
	{
		return AllocablePackageable.builder()
				.sourceDocumentInfo(extractSourceDocumentInfo(packageable))
				.customerId(packageable.getCustomerId())
				.productId(packageable.getProductId())
				.asiId(packageable.getAsiId())
				.bestBeforePolicy(packageable.getBestBeforePolicy())
				.warehouseId(packageable.getWarehouseId())
				.pickFromOrderId(packageable.getPickFromOrderId())
				.qtyToAllocateTarget(packageable.getQtyToPick())
				.build();
	}

	@NonNull
	private PickFromHUsGetRequest getPickFromHUsGetRequest(@NonNull final AllocablePackageable packageable)
	{
		return PickFromHUsGetRequest.builder()
				.pickFromLocatorIds(warehouseBL.getLocatorIdsOfTheSamePickingGroup(packageable.getWarehouseId()))
				.partnerId(packageable.getCustomerId())
				.productId(packageable.getProductId())
				.asiId(packageable.getAsiId())
				.bestBeforePolicy(packageable.getBestBeforePolicy()
										  .orElseGet(() -> bpartnersService.getBestBeforePolicy(packageable.getCustomerId())))
				.reservationRef(packageable.getReservationRef())
				.enforceMandatoryAttributesOnPicking(true)
				.build();
	}

	private static SourceDocumentInfo extractSourceDocumentInfo(@NonNull final Packageable packageable)
	{
		return SourceDocumentInfo.builder()
				.shipmentScheduleId(packageable.getShipmentScheduleId())
				.salesOrderLineId(OrderAndLineId.ofNullable(packageable.getSalesOrderId(), packageable.getSalesOrderLineIdOrNull()))
				.shipperId(packageable.getShipperId())
				.packToSpec(extractPackToSpec(packageable))
				.existingPickingCandidate(null)
				.build();
	}

	private static PackToSpec extractPackToSpec(@NonNull final Packageable packageable)
	{
		return PackToSpec.ofTUPackingInstructionsId(packageable.getPackToHUPIItemProductId());
	}

	private Stream<PickingPlanLine> createLinesAndStream(final AllocablePackageable packageable)
	{
		final ArrayList<PickingPlanLine> lines = new ArrayList<>();
		lines.addAll(createLinesFromExistingPickingCandidates(packageable));
		lines.addAll(createLinesFromEligibleHUs(packageable));

		if (!packageable.isAllocated())
		{
			getPickingOrderBOM(packageable)
					.ifPresent(pickingOrderBOM -> lines.add(createLinesFromPickingOrder(pickingOrderBOM, packageable)));

		}

		if (!packageable.isAllocated())
		{
			lines.add(createUnallocableLineForRemainingQtyToAllocate(packageable));
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
		final List<PickingCandidate> pickingCandidates = pickingCandidateRepository.getByShipmentScheduleIdAndStatus(
				packageable.getSourceDocumentInfo().getShipmentScheduleId(),
				PickingCandidateStatus.Draft);

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
			final PickFromHU pickFromHU;
			final Quantity qty = existingPickingCandidate.getQtyPicked();
			if (pickFrom.getHuId() != null)
			{
				final ProductId productId = packageable.getProductId();
				final AllocableStorage storage = storages.getStorage(pickFrom.getHuId(), productId);
				storage.forceAllocate(packageable, qty);
				pickFromHU = pickFromHUsSupplier.createPickFromHUByTopLevelHUId(pickFrom.getHuId())
						.withHuReservedForThisLine(storage.hasReservedQtyFor(packageable));
			}
			else
			{
				pickFromHU = null;
			}

			return prepareLine_PickFromHU(packageable)
					.pickFromHU(pickFromHU)
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

	private ImmutableList<PickingPlanLine> createLinesFromEligibleHUs(@NonNull final AllocablePackageable packageable)
	{
		if (packageable.isAllocated())
		{
			return ImmutableList.of();
		}

		final List<PickFromHU> husEligibleToPick = pickFromHUsSupplier.getEligiblePickFromHUs(getPickFromHUsGetRequest(packageable));

		return husEligibleToPick.stream()
				.map(pickFromHU -> createZeroQtyLineFromHU(packageable, pickFromHU))
				.map(line -> allocateLineFromHU(line, packageable))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

	private Set<LocatorId> getPickFromLocatorIds(final AllocablePackageable packageable)
	{
		return warehouseBL.getLocatorIdsOfTheSamePickingGroup(packageable.getWarehouseId());
	}

	@NonNull
	private ShipmentAllocationBestBeforePolicy getBestBeforePolicy(final AllocablePackageable packageable)
	{
		return packageable.getBestBeforePolicy()
				.orElseGet(() -> bpartnersService.getBestBeforePolicy(packageable.getCustomerId()));
	}

	@Nullable
	private PickingPlanLine allocateLineFromHU(final PickingPlanLine zeroQtyLine, final AllocablePackageable packageable)
	{
		if (packageable.isAllocated())
		{
			return null;
		}

		final PickFromHU pickFromHU = Check.assumeNotNull(zeroQtyLine.getPickFromHU(), "No pickFromHU set for {}", zeroQtyLine);
		final AllocableStorage storage = storages.getStorage(pickFromHU.getTopLevelHUId(), packageable.getProductId());
		final Quantity qty = storage.allocate(packageable);
		if (qty.isZero())
		{
			return null;
		}

		return zeroQtyLine.withQty(qty);
	}

	private PickingPlanLine createZeroQtyLineFromHU(
			@NonNull final AllocablePackageable packageable,
			@NonNull final PickFromHU hu)
	{
		final Quantity zero = packageable.getQtyToAllocate().toZero();

		if (packageable.getIssueToBOMLine() == null)
		{
			return prepareLine_PickFromHU(packageable)
					.pickFromHU(hu)
					.qty(zero)
					.build();
		}
		else
		{
			return prepareLine_IssueComponentsToPickingOrder(packageable)
					.issueToBOMLine(packageable.getIssueToBOMLine().withIssueFromHUId(hu.getTopLevelHUId()))
					.qty(zero)
					.build();
		}
	}

	private PickingPlanLine createUnallocableLineForRemainingQtyToAllocate(@NonNull final AllocablePackageable packageable)
	{
		final Quantity qty = packageable.getQtyToAllocate();
		packageable.allocateQty(qty);

		return prepareLine()
				.type(PickingPlanLineType.UNALLOCABLE)
				.sourceDocumentInfo(packageable.getSourceDocumentInfo())
				.productId(packageable.getProductId())
				//.issueToOrderBOMLineId(packageable.getIssueToOrderBOMLineId()) // TODO
				.qty(qty)
				.build();
	}

	private $LineBuilder prepareLine_PickFromHU(@NonNull final AllocablePackageable packageable)
	{
		return prepareLine()
				.type(PickingPlanLineType.PICK_FROM_HU)
				.sourceDocumentInfo(packageable.getSourceDocumentInfo())
				.productId(packageable.getProductId());
	}

	private $LineBuilder prepareLine_PickFromPickingOrder(@NonNull final AllocablePackageable finishedGoodPackageable)
	{
		return prepareLine()
				.type(PickingPlanLineType.PICK_FROM_PICKING_ORDER)
				.sourceDocumentInfo(finishedGoodPackageable.getSourceDocumentInfo())
				.productId(finishedGoodPackageable.getProductId());
	}

	private $LineBuilder prepareLine_IssueComponentsToPickingOrder(@NonNull final AllocablePackageable packageable)
	{
		return prepareLine()
				.type(PickingPlanLineType.ISSUE_COMPONENTS_TO_PICKING_ORDER)
				.sourceDocumentInfo(packageable.getSourceDocumentInfo())
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
			@Nullable final PickFromHU pickFromHU,
			@Nullable final PickFromPickingOrder pickFromPickingOrder,
			@Nullable final IssueToBOMLine issueToBOMLine)
	{
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

		final ArrayList<PickingPlanLine> lines = new ArrayList<>(createLinesFromEligibleHUs(bomLinePackageable));

		if (!bomLinePackageable.isAllocated())
		{
			lines.add(createUnallocableLineForRemainingQtyToAllocate(bomLinePackageable));
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

	private PickingPlanLine updateAlternativeKeys(@NonNull final PickingPlanLine line)
	{
		final PickFromHU pickFromHU = line.getPickFromHU();
		if (pickFromHU == null)
		{
			return line;
		}

		final AlternativePickFromKeys alternativeKeys = pickFromHU.getAlternatives()
				.filter(alternativeKey -> storages.getStorage(alternativeKey.getHuId(), alternativeKey.getProductId()).hasQtyFreeToAllocate());

		return line.withPickFromHU(pickFromHU.withAlternatives(alternativeKeys));
	}

	private AlternativePickFromsList getRelevantAlternativesFor(final List<PickingPlanLine> lines)
	{
		final HashSet<AlternativePickFromKey> keysConsidered = new HashSet<>();
		final ArrayList<AlternativePickFrom> alternatives = new ArrayList<>();
		for (final PickingPlanLine line : lines)
		{
			final PickFromHU pickFromHU = line.getPickFromHU();
			if (pickFromHU == null)
			{
				continue;
			}

			for (final AlternativePickFromKey key : pickFromHU.getAlternatives())
			{
				if (keysConsidered.add(key))
				{
					storages.getStorage(key.getHuId(), key.getProductId())
							.getQtyFreeToAllocate()
							.filter(Quantity::isPositive)
							.map(availableQty -> AlternativePickFrom.of(key, availableQty))
							.ifPresent(alternatives::add);
				}
			}
		}

		return AlternativePickFromsList.ofList(alternatives);
	}
}
