package de.metas.handlingunits.picking.job.massprinting;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsIdAndCaption;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileService;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.massprinting.MassPrintingResult.ProductResult;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobStepEvent;
import de.metas.handlingunits.picking.job.model.PickingJobStepEventType;
import de.metas.handlingunits.picking.job.model.PickingUnit;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfile;
import de.metas.handlingunits.picking.job.model.PickingJobQuery;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCreateRequest;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.handlingunits.picking.job.service.external.product.PickingJobProductService;
import de.metas.handlingunits.picking.job.service.external.warehouse.PickingJobWarehouseService;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.logging.LogManager;
import de.metas.picking.api.Packageable;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.picking.job_schedule.model.PickingJobSchedule;
import de.metas.picking.job_schedule.model.PickingJobScheduleCollection;
import de.metas.product.Product;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.workplace.Workplace;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/** Orchestration service for the mass-printing flow: scan LU → pick every self-packed product → print labels. */
@Service
@RequiredArgsConstructor
public class MassPrintingService
{
	private static final AdMessageKey MSG_MASS_PRINTING_NOT_ENABLED = AdMessageKey.of("de.metas.handlingunits.picking.massprinting.MassPrintingNotEnabled");
	private static final AdMessageKey MSG_LU_NOT_IN_PICKING_GROUP = AdMessageKey.of("de.metas.handlingunits.picking.massprinting.LUNotInWorkplacePickingGroup");

	private static final Logger logger = LogManager.getLogger(MassPrintingService.class);

	@NonNull private final MobileUIPickingUserProfileService profileService;
	@NonNull private final PickingJobService pickingJobService;
	@NonNull private final PickingJobHUService huService;
	@NonNull private final PickingJobProductService productService;
	@NonNull private final PickingJobWarehouseService warehouseService;

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	/** Processes an LU scan: picks and labels every self-packed product on the LU, FIFO by preparation date. */
	@NonNull
	public MassPrintingResult scan(@NonNull final MassPrintingScanRequest request)
	{
		final MobileUIPickingUserProfile profile = profileService.getProfile();
		if (!profile.isMassPrinting())
		{
			throw new AdempiereException(MSG_MASS_PRINTING_NOT_ENABLED);
		}

		final HuId luId = request.getLuId();

		final List<IHUProductStorage> productStorages = huService.getProductStorages(luId);
		if (productStorages.isEmpty())
		{
			return MassPrintingResult.EMPTY;
		}

		final ImmutableSet<ProductId> productIds = extractProductIds(productStorages);
		final Map<ProductId, Product> productsById = productService.getByIdsAsMap(productIds);

		final ImmutableSet<ProductId> selfPackedProductIds = retainSelfPackedProducts(productsById.values());

		// Search demand exactly like the picking launcher: scope by the operator's workplace warehouse
		// (which spans every locator of the same M_Warehouse_PickingGroup), not by the LU's own locator.
		final Workplace workplace = warehouseService.getWorkplaceByUserId(request.getPickerId()).orElse(null);
		final LocatorId luLocatorId = huService.getLocatorId(luId);

		// If the picker is assigned to a workplace, the scanned LU must belong to that workplace's picking group.
		if (workplace != null
				&& !warehouseService.getLocatorIdsOfTheSamePickingGroup(workplace.getWarehouseId()).contains(luLocatorId))
		{
			throw new AdempiereException(MSG_LU_NOT_IN_PICKING_GROUP)
					.appendParametersToMessage()
					.setParameter("luId", luId)
					.setParameter("luLocatorId", luLocatorId)
					.setParameter("workplaceWarehouseId", workplace.getWarehouseId());
		}

		final PickingJobQuery pickingJobQuery = PickingJobQuery.builder()
				.userId(request.getPickerId())
				.warehouseId(workplace != null ? workplace.getWarehouseId() : null)
				.scheduledForWorkplaceId(profile.isConsiderOnlyJobScheduledToWorkplace() && workplace != null ? workplace.getId() : null)
				.onlyCustomerIds(profile.getPickOnlyCustomerIds())
				.build();

		// Reuse the launcher's eligibility seam: streamPackageable applies the same launcher filters
		// (workplace warehouse, job-schedule branch, lockedBy(userId)+includeNotLocked → other-user-locked
		// schedules are excluded, excludeLockedForProcessing, etc.). Keep only the self-packed products.
		final Map<ProductId, List<Packageable>> packageablesByProduct =
				pickingJobService.streamPackageable(pickingJobQuery)
						.filter(p -> selfPackedProductIds.contains(p.getProductId()))
						.collect(Collectors.groupingBy(Packageable::getProductId));

		final ImmutableList.Builder<ProductResult> productResults = ImmutableList.builder();
		final ImmutableList.Builder<ProductId> skippedNonSelfPacked = ImmutableList.builder();

		for (final IHUProductStorage productStorage : productStorages)
		{
			final ProductId productId = productStorage.getProductId();
			if (!selfPackedProductIds.contains(productId))
			{
				logger.debug("Skipping non-self-packed product: {}", productId);
				skippedNonSelfPacked.add(productId);
				continue;
			}

			if (productStorage.getQtyAsInt() <= 0)
			{
				logger.debug("No units on LU for product: {}", productId);
				continue;
			}

			final List<Packageable> packageables = packageablesByProduct.getOrDefault(productId, ImmutableList.of());

			// Pack+ship inside a transaction. Labels print through the standard picking close path during the
			// pick (the physical print job is enqueued after the transaction commits, like a regular picking job).
			final PackAndPickResult packAndPickResult = processProductInTrx(request, pickingJobQuery, luId, productId, productStorage.getQtyAsInt(), packageables);

			productResults.add(buildProductResult(productId, packAndPickResult));
		}

		return MassPrintingResult.builder()
				.productResults(productResults.build())
				.skippedNonSelfPackedProductIds(skippedNonSelfPacked.build())
				.build();
	}

	private static ImmutableSet<ProductId> extractProductIds(@NonNull final List<IHUProductStorage> productStorages)
	{
		return productStorages.stream()
				.map(IHUProductStorage::getProductId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private static ImmutableSet<ProductId> retainSelfPackedProducts(
			@NonNull final Collection<Product> products)
	{
		return products.stream()
				.filter(Product::isSelfPacked)
				.map(Product::getId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	private PackAndPickResult processProductInTrx(
			@NonNull final MassPrintingScanRequest request,
			@NonNull final PickingJobQuery pickingJobQuery,
			@NonNull final HuId luId,
			@NonNull final ProductId productId,
			final int unitsOnLU,
			@NonNull final List<Packageable> packageables)
	{
		// Shipment generation inside PickingJobCompleteCommand opens its own nested transaction
		// (callInNewTrx), so the async-batch assignment commits independently from the outer
		// picking transaction — no M_ShipmentSchedule row-lock deadlock is possible.
		return trxManager.callInThreadInheritedTrx(() -> processProduct(request, pickingJobQuery, luId, productId, unitsOnLU, packageables));
	}

	@NonNull
	private PackAndPickResult processProduct(
			@NonNull final MassPrintingScanRequest request,
			@NonNull final PickingJobQuery pickingJobQuery,
			@NonNull final HuId luId,
			@NonNull final ProductId productId,
			final int unitsOnLU,
			@NonNull final List<Packageable> packageables)
	{
		final ScheduleSelection selection = selectSchedulesFifo(packageables, unitsOnLU);

		if (selection.getSelectedScheduleIds().isEmpty() || selection.getUnitsToPack() <= 0)
		{
			return PackAndPickResult.builder()
					.unitsPacked(0)
					.unitsLeftOnLU(unitsOnLU)
					.openDemandRemaining(selection.getTotalDemand())
					.build();
		}

		// Abort any abortable pre-existing Draft picking job that already covers this product's selected
		// schedules, before creating our own job. A job that is unassigned or assigned to this mass-printing
		// picker is a leftover from a previous failed scan and is aborted here (self-cleanup; also prevents
		// DDOrderPickingReconcile_PickerBusy errors). A job held by a different picker is left untouched — its
		// schedules were already excluded before selection by the reused launcher query's lockedBy(pickerId) +
		// includeNotLocked filter (PickingJobQuery.toPackageableQueryBuilder), so they do not reach this point.
		pickingJobService.abortAbortablePickingJobsForSchedules(
				ImmutableSet.of(productId),
				ImmutableSet.copyOf(selection.getSelectedScheduleIds()),
				request.getPickerId());

		// Each line is picked-and-closed through the standard picking close path (see pickTuLine / pickCuLine),
		// so labels print exactly like a regular picking job — closeLUAndTUPickingTargets() collects every
		// top-level TU/VHU via addTopLevelTUId() and prints its label. No bespoke side-channel print here.
		final PickingJob pickedJob = createAndPickJob(request, pickingJobQuery, luId, selection);
		final ImmutableSet<HuId> pickedHuIds = pickedJob.getAllPickedHuIds();
		final ImmutableSet<HuId> packedHUIds = huService.getPackedBoxHUIds(pickedHuIds);
		pickingJobService.complete(pickedJob);

		return PackAndPickResult.builder()
				.unitsPacked(selection.getUnitsToPack())
				.packedHUIds(packedHUIds)
				.unitsLeftOnLU(selection.getUnitsLeftOnLU())
				.openDemandRemaining(Math.max(0, selection.getTotalDemand() - selection.getUnitsToPack()))
				.build();
	}

	@NonNull
	private ProductResult buildProductResult(
			@NonNull final ProductId productId,
			@NonNull final PackAndPickResult packAndPickResult)
	{
		return ProductResult.builder()
				.productId(productId)
				.unitsPacked(packAndPickResult.getUnitsPacked())
				.packedHUIds(packAndPickResult.getPackedHUIds())
				.unitsLeftOnLU(packAndPickResult.getUnitsLeftOnLU())
				.unitsOfOpenDemandRemaining(packAndPickResult.getOpenDemandRemaining())
				.build();
	}

	/**
	 * Selects shipment schedules FIFO by preparation date, capped at units on LU.
	 * Fully fills each order before moving to the next; the last order may be partially filled.
	 */
	@NonNull
	private ScheduleSelection selectSchedulesFifo(
			@NonNull final List<Packageable> packageables,
			final int unitsOnLU)
	{
		// Maps each selected schedule to the qty to actually pick from it (may be < full demand for the last order).
		final Map<ShipmentScheduleId, Integer> selectedScheduleQtys = new LinkedHashMap<>();
		int totalDemand = 0;
		int capacityRemaining = unitsOnLU;

		for (final Packageable packageable : packageables)
		{
			final BigDecimal qtyBD = packageable.getQtyToPick().toBigDecimal();
			if (qtyBD.stripTrailingZeros().scale() > 0)
			{
				logger.warn("Skipping schedule {} — fractional QtyToPick={} is not supported for mass printing",
						packageable.getShipmentScheduleId(), qtyBD);
				continue;
			}
			final int scheduleDemand = qtyBD.intValueExact();
			if (scheduleDemand <= 0)
			{
				continue;
			}

			totalDemand += scheduleDemand;

			if (capacityRemaining <= 0)
			{
				// LU is fully allocated; include demand in total but don't pick this schedule.
				continue;
			}

			// Pick the minimum of full demand and remaining capacity (partial fill on the last order).
			final int qtyToPickFromThisSchedule = Math.min(scheduleDemand, capacityRemaining);
			selectedScheduleQtys.put(packageable.getShipmentScheduleId(), qtyToPickFromThisSchedule);
			capacityRemaining -= qtyToPickFromThisSchedule;
		}

		return ScheduleSelection.of(
				selectedScheduleQtys,
				totalDemand,
				unitsOnLU - capacityRemaining,
				capacityRemaining);
	}

	/**
	 * Creates a PRODUCT picking job for the selected schedules, then picks each line from the scanned LU.
	 *
	 * <p>Both cases route each picked HU through the standard picking close path so labels print like a
	 * regular picking job: CU (Virtual PI) lines pick one VHU per unit and close it as an existing-TU target;
	 * TU (finite PI) lines pick-and-close one box per iteration (PI capacity=1 forces one box per unit).
	 */
	@NonNull
	private PickingJob createAndPickJob(
			@NonNull final MassPrintingScanRequest request,
			@NonNull final PickingJobQuery pickingJobQuery,
			@NonNull final HuId pickFromLUId,
			@NonNull final ScheduleSelection selection)
	{
		// Build the job's schedule-id set the same way regular picking does from a candidate.
		final ShipmentScheduleAndJobScheduleIdSet scheduleIdSet =
				resolveScheduleIdsForJobCreation(pickingJobQuery, ImmutableSet.copyOf(selection.getSelectedScheduleIds()));
		final PickingJob pickingJob = pickingJobService.createPickingJob(
				PickingJobCreateRequest.builder()
						.pickerId(request.getPickerId())
						.aggregationType(PickingJobAggregationType.PRODUCT)
						.isAllowPickingAnyHU(true)
						.scheduleIds(scheduleIdSet)
						.build());

		final HUQRCode luQRCode = huService.getQRCodeByHuId(pickFromLUId);

		PickingJob pickedJob = pickingJob;
		for (final PickingJobLine line : pickingJob.getLines())
		{
			pickedJob = pickLine(pickedJob, line, luQRCode, selection.getSelectedScheduleQtys());
		}
		return pickedJob;
	}

	/**
	 * Resolves the {@link ShipmentScheduleAndJobScheduleIdSet} to drive a PRODUCT picking-job creation for the
	 * selected shipment-schedule ids. The resolution branches on the query MODE — never on whether job-schedules
	 * happen to exist:
	 * <ul>
	 * <li><b>Warehouse mode</b> ({@code isScheduledForWorkplaceOnly() == false}): the job is driven by plain
	 * shipment-schedule ids.</li>
	 * <li><b>Job-scheduled-to-workplace mode</b> ({@code isScheduledForWorkplaceOnly() == true}): the job MUST be
	 * driven by the picking-job schedules (looked up via {@link PickingJobService#listJobSchedules}, the same
	 * job-schedule resolution the launcher / {@code streamPackageable} use). We never fall back to a plain
	 * shipment-schedule set here — that would defeat {@code isScheduledForWorkplaceOnly}. A selected schedule with
	 * no matching job-schedule is simply not included (we accept "no job schedules" rather than falling back).</li>
	 * </ul>
	 */
	@NonNull
	private ShipmentScheduleAndJobScheduleIdSet resolveScheduleIdsForJobCreation(
			@NonNull final PickingJobQuery query,
			@NonNull final Set<ShipmentScheduleId> selectedShipmentScheduleIds)
	{
		// Nothing selected → nothing to create or query for. Guard so we neither build an empty set nor hit the DB.
		if (selectedShipmentScheduleIds.isEmpty())
		{
			return ShipmentScheduleAndJobScheduleIdSet.EMPTY;
		}

		// Warehouse mode: the job is driven by plain shipment-schedule ids.
		if (!query.isScheduledForWorkplaceOnly())
		{
			return ShipmentScheduleAndJobScheduleIdSet.ofShipmentScheduleIds(selectedShipmentScheduleIds);
		}

		// Scheduled-to-workplace mode: the job MUST be driven by the picking-job schedules.
		// NEVER fall back to a plain shipment-schedule set here — that would defeat isScheduledForWorkplaceOnly.
		final PickingJobScheduleCollection jobSchedules = pickingJobService.listJobSchedules(query);
		return selectedShipmentScheduleIds.stream()
				.map(ssId -> jobSchedules.getSingleScheduleByShipmentScheduleId(ssId)
						.map(PickingJobSchedule::getShipmentScheduleAndJobScheduleId)
						.orElse(null))
				.filter(java.util.Objects::nonNull)
				.collect(ShipmentScheduleAndJobScheduleIdSet.collect());
	}

	/** Fires pick events for one line from the LU, using the capped qty from the FIFO selection. */
	@NonNull
	private PickingJob pickLine(
			@NonNull final PickingJob pickedJob,
			@NonNull final PickingJobLine line,
			@NonNull final HUQRCode luQRCode,
			@NonNull final Map<ShipmentScheduleId, Integer> selectedScheduleQtys)
	{
		final ShipmentScheduleId ssId = line.getScheduleId().getShipmentScheduleId();
		final Integer cappedQtyInt = selectedScheduleQtys.get(ssId);
		if (cappedQtyInt == null)
		{
			// Should not happen: every line in a PRODUCT-aggregation job was created from selectedScheduleIds.
			throw new AdempiereException("Picking job line has no capped-qty entry in FIFO selection map")
					.appendParametersToMessage()
					.setParameter("lineId", line.getId())
					.setParameter("ssId", ssId);
		}

		if (line.getPickingUnit() == PickingUnit.CU)
		{
			return pickCuLine(pickedJob, line, luQRCode, cappedQtyInt);
		}
		else
		{
			return pickTuLine(pickedJob, line, luQRCode, cappedQtyInt);
		}
	}

	/**
	 * VHU/CU path: one VHU per unit, each routed through the standard close path so its label prints
	 * like a regular picking job.
	 *
	 * <p>A bare/CU line cannot set a TU target during the pick (the TU-target machinery in
	 * {@code PickingJobPickCommand} is gated on {@code pickingUnit.isTU()}), so per unit we: (1) PICK one
	 * unit → one VHU, (2) set that materialized VHU as an existing-TU target, (3) close the line's target
	 * → {@code addTopLevelTUId(vhuId)} → label printed. The close resets the target to null, so the next
	 * unit's pick (target null) does not trip {@code PickingJobPickCommand}'s "block picking into existing TU".
	 */
	@NonNull
	private PickingJob pickCuLine(
			@NonNull final PickingJob pickedJob,
			@NonNull final PickingJobLine line,
			@NonNull final HUQRCode luQRCode,
			final int cappedQtyInt)
	{
		PickingJob result = pickedJob;
		// cappedQtyInt is already capped to schedule demand by selectSchedulesFifo (Math.min(scheduleDemand, capacityRemaining)),
		// so each 1-unit pick never exceeds the schedule's open QtyToDeliver — overpick is prevented at source.
		// checkIfAlreadyPacked=true (the default) additionally guards against re-picking an already-packed HU.
		for (int unit = 0; unit < cappedQtyInt; unit++)
		{
			result = pickingJobService.processStepEvent(result, PickingJobStepEvent.builder()
					.pickingLineId(line.getId())
					.eventType(PickingJobStepEventType.PICK)
					.qrCode(luQRCode.toScannedCode())
					.qtyPicked(BigDecimal.ONE)
					.isPickWholeTU(false)
					.checkIfAlreadyPacked(true)
					.build());

			final HuId vhuId = result.getLineById(line.getId()).getLastPickedHUId()
					.orElseThrow(() -> new AdempiereException("No VHU materialized by the CU pick")
							.appendParametersToMessage()
							.setParameter("lineId", line.getId())
							.setParameter("productId", line.getProductId()));

			// Set the just-materialized VHU as an existing-TU target, then close it so the standard close path
			// collects (addTopLevelTUId) and prints its label. The close also resets the target to null.
			result = pickingJobService.setTUPickingTarget(result, line.getId(), TUPickingTarget.ofExistingHU(vhuId, huService.getQRCodeByHuId(vhuId)));
			result = pickingJobService.closeLUAndTUPickingTargets(result, line.getId());
		}
		return result;
	}

	/**
	 * TU path: one box per iteration so each close sees exactly one materialized top-level TU.
	 *
	 * <p>Per box: set a new-TU target carrying the line's TU PI, then fire a PICK event with
	 * {@code isCloseTarget=true}. The framework materializes the box and rewrites the new-TU target to an
	 * existing-TU target on it ({@code PickingJobPickCommand.updatePickingTarget}); the close then collects
	 * it via {@code addTopLevelTUId} and prints its label through the standard close path — same as a regular
	 * picking job. With capacity-1 PIs (the customer case) one TU = one unit, so {@code cappedQtyInt} boxes.
	 */
	@NonNull
	private PickingJob pickTuLine(
			@NonNull final PickingJob pickedJob,
			@NonNull final PickingJobLine line,
			@NonNull final HUQRCode luQRCode,
			final int cappedQtyInt)
	{
		final TUPickingTarget tuTarget = newTuPickingTargetForLine(line);

		PickingJob result = pickedJob;
		// cappedQtyInt is already capped to schedule demand by selectSchedulesFifo (Math.min(scheduleDemand, capacityRemaining)),
		// so the total qty never exceeds the schedule's open QtyToDeliver — overpick is prevented at source.
		// checkIfAlreadyPacked=true (the default) additionally guards against re-picking an already-packed HU.
		for (int box = 0; box < cappedQtyInt; box++)
		{
			// Re-set the new-TU target before each box: the previous iteration's close reset the target to null.
			result = pickingJobService.setTUPickingTarget(result, line.getId(), tuTarget);
			result = pickingJobService.processStepEvent(result, PickingJobStepEvent.builder()
					.pickingLineId(line.getId())
					.eventType(PickingJobStepEventType.PICK)
					.qrCode(luQRCode.toScannedCode())
					.qtyPicked(BigDecimal.ONE)
					.isPickWholeTU(false)
					.checkIfAlreadyPacked(true)
					.isCloseTarget(true)
					.build());
		}
		return result;
	}

	/** Resolves the line's TU PI (and caption) from its packing info and builds a new-TU picking target. */
	@NonNull
	private TUPickingTarget newTuPickingTargetForLine(@NonNull final PickingJobLine line)
	{
		final ImmutableSet<HuPackingInstructionsIdAndCaption> piInfos = huService.retrievePIInfo(ImmutableSet.of(line.getPackingInfo().getPiItemId()));
		if (piInfos.size() != 1)
		{
			// A self-packed finite-TU line resolves to exactly one TU PI; anything else means the packing setup is
			// ambiguous and we cannot pick a single deterministic box type.
			throw new AdempiereException("Expected exactly one TU packing instruction for mass-printing line")
					.appendParametersToMessage()
					.setParameter("lineId", line.getId())
					.setParameter("productId", line.getProductId())
					.setParameter("piItemId", line.getPackingInfo().getPiItemId())
					.setParameter("piInfos", piInfos);
		}

		final HuPackingInstructionsIdAndCaption piInfo = piInfos.iterator().next();
		return TUPickingTarget.ofPackingInstructions(piInfo.getId(), piInfo.getCaption());
	}

	/** Result of FIFO schedule selection: which schedules to pick and how many units from each. */
	@Value
	@Builder
	private static class ScheduleSelection
	{
		/** Map from selected shipment-schedule ID to the capped qty to pick from it. */
		@NonNull ImmutableMap<ShipmentScheduleId, Integer> selectedScheduleQtys;
		/** Ordered list of the keys of {@link #selectedScheduleQtys} (insertion-order preserved). */
		@NonNull ImmutableList<ShipmentScheduleId> selectedScheduleIds;
		int totalDemand;
		int unitsToPack;
		int unitsLeftOnLU;

		static ScheduleSelection of(
				@NonNull final Map<ShipmentScheduleId, Integer> selectedScheduleQtys,
				final int totalDemand,
				final int unitsToPack,
				final int unitsLeftOnLU)
		{
			final ImmutableMap<ShipmentScheduleId, Integer> qtysMap = ImmutableMap.copyOf(selectedScheduleQtys);
			return ScheduleSelection.builder()
					.selectedScheduleQtys(qtysMap)
					.selectedScheduleIds(ImmutableList.copyOf(qtysMap.keySet()))
					.totalDemand(totalDemand)
					.unitsToPack(unitsToPack)
					.unitsLeftOnLU(unitsLeftOnLU)
					.build();
		}
	}

	/**
	 * Internal result of {@link #processProduct} holding the resolved leaf shippable HU ids
	 * (for the HU-shape / unit-count assertion).
	 */
	@Value
	@Builder
	private static class PackAndPickResult
	{
		/** Number of product units packed (in product UOM). */
		int unitsPacked;

		/**
		 * Leaf shippable HU ids (descend any target-LU wrapper) — one per picked unit.
		 * May be empty when nothing was packed.
		 */
		@Builder.Default
		@NonNull ImmutableSet<HuId> packedHUIds = ImmutableSet.of();

		/** Units remaining on the LU after packing. */
		int unitsLeftOnLU;

		/** Units of open demand remaining after packing. */
		int openDemandRemaining;
	}
}
