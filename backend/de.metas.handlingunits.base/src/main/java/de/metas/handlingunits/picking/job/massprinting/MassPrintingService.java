package de.metas.handlingunits.picking.job.massprinting;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileService;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.massprinting.MassPrintingResult.ProductResult;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobStepEvent;
import de.metas.handlingunits.picking.job.model.PickingJobStepEventType;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCreateRequest;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.handlingunits.picking.job.service.external.product.PickingJobProductService;
import de.metas.handlingunits.picking.job.service.external.shipmentschedule.PickingJobShipmentScheduleService;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.logging.LogManager;
import de.metas.picking.api.Packageable;
import de.metas.picking.api.PackageableQuery;
import de.metas.picking.api.PackageableQuery.OrderBy;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.product.Product;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Orchestration service for the mass-printing flow:
 * Scan LU → enumerate self-packed products → per-product FIFO selection →
 * create+pick+complete PRODUCT picking job → print one HU label per picked shippable HU.
 */
@Service
@RequiredArgsConstructor
public class MassPrintingService
{
	/**
	 * Thrown when the picker's profile does not have mass-printing enabled.
	 * Prevents any pick, shipment, or label action for a caller whose profile flag is off,
	 * ensuring the server enforces the same constraint as the frontend trigger guard.
	 */
	static final AdMessageKey MSG_MASS_PRINTING_NOT_ENABLED = AdMessageKey.of("de.metas.handlingunits.picking.massprinting.MassPrintingNotEnabled");

	private static final Logger logger = LogManager.getLogger(MassPrintingService.class);

	@NonNull private final MobileUIPickingUserProfileService profileService;
	@NonNull private final PickingJobService pickingJobService;
	@NonNull private final PickingJobHUService huService;
	@NonNull private final PickingJobProductService productService;
	@NonNull private final PickingJobShipmentScheduleService shipmentScheduleService;

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	/**
	 * Scan the LU and for each self-packed product on it:
	 * <ol>
	 *   <li>Guard: reject immediately if the picker's profile does not have mass-printing enabled
	 *       ({@code IsMassPrinting=N}). Prevents any pick, shipment, or label action for callers
	 *       whose profile disables the feature — the frontend hides the trigger when off, but the
	 *       server enforces the same constraint for all callers.</li>
	 *   <li>Select open shipment schedules FIFO by preparation date, capped at units on LU.</li>
	 *   <li>Create a PRODUCT picking job restricted to those schedules.</li>
	 *   <li>Pick each schedule from the scanned LU using the schedule's effective PI (finite PI → one box per unit; Virtual PI → one VHU/CU).</li>
	 *   <li>Complete the picking job.</li>
	 *   <li>Print one HU label per box (best-effort, after commit).</li>
	 * </ol>
	 *
	 * @param request scan request carrying the LU id and the picker's user id
	 * @return per-product result summary (boxes packed, labels printed, leftovers)
	 * @throws AdempiereException with {@link #MSG_MASS_PRINTING_NOT_ENABLED} when the profile flag is off
	 */
	@NonNull
	public MassPrintingResult scan(@NonNull final MassPrintingScanRequest request)
	{
		if (!profileService.getProfile().isMassPrinting())
		{
			throw new AdempiereException(MSG_MASS_PRINTING_NOT_ENABLED);
		}

		final HuId luId = request.getLuId();

		final List<IHUProductStorage> productStorages = huService.getProductStorages(luId);
		if (productStorages.isEmpty())
		{
			return MassPrintingResult.builder().build();
		}

		final ImmutableSet<ProductId> productIds = productStorages.stream()
				.map(IHUProductStorage::getProductId)
				.collect(ImmutableSet.toImmutableSet());
		final Map<ProductId, Product> productsById = productService.getByIdsAsMap(productIds);

		final LocatorId locatorId = huService.getLocatorId(luId);
		final WarehouseId warehouseId = locatorId.getWarehouseId();

		final ImmutableList.Builder<ProductResult> productResults = ImmutableList.builder();
		final ImmutableList.Builder<ProductId> skippedNonSelfPacked = ImmutableList.builder();

		for (final IHUProductStorage productStorage : productStorages)
		{
			final ProductId productId = productStorage.getProductId();
			final Product product = productsById.get(productId);
			if (product == null || !product.isSelfPacked())
			{
				logger.debug("Skipping non-self-packed product: {}", productId);
				skippedNonSelfPacked.add(productId);
				continue;
			}

			final Quantity unitsOnLU = productStorage.getQty();
			// intValueExact: a self-packed product is whole-unit; a fractional qty would be a data error
			// we must surface (ArithmeticException), not silently truncate (which would drop part of a box).
			final int unitsOnLUInt = unitsOnLU.toBigDecimal().intValueExact();
			if (unitsOnLUInt <= 0)
			{
				logger.debug("No units on LU for product: {}", productId);
				continue;
			}

			// Pack+ship inside a transaction; label printing happens AFTER the transaction commits
			// (best-effort: failures are counted but do not roll back the packed boxes).
			final PackAndPickResult packAndPickResult = processProductInTrx(request, luId, warehouseId, productId, unitsOnLUInt);

			int labelsPrinted = 0;
			int labelPrintFailures = 0;
			for (final HuId pickedHuId : packAndPickResult.getPickedHuIds())
			{
				try
				{
					huService.printHULabel(pickedHuId);
					labelsPrinted++;
				}
				catch (final Exception e)
				{
					logger.warn("Failed to print label for HU {}", pickedHuId, e);
					labelPrintFailures++;
				}
			}

			productResults.add(ProductResult.builder()
					.productId(productId)
					.boxesPacked(packAndPickResult.getBoxesPacked())
					.packedHUIds(packAndPickResult.getPackedHUIds())
					.labelsPrinted(labelsPrinted)
					.labelPrintFailures(labelPrintFailures)
					.unitsLeftOnLU(packAndPickResult.getUnitsLeftOnLU())
					.unitsOfOpenDemandRemaining(packAndPickResult.getOpenDemandRemaining())
					.build());
		}

		return MassPrintingResult.builder()
				.productResults(productResults.build())
				.skippedNonSelfPackedProductIds(skippedNonSelfPacked.build())
				.build();
	}

	@NonNull
	private PackAndPickResult processProductInTrx(
			@NonNull final MassPrintingScanRequest request,
			@NonNull final HuId luId,
			@NonNull final WarehouseId warehouseId,
			@NonNull final ProductId productId,
			final int unitsOnLUInt)
	{
		return trxManager.callInThreadInheritedTrx(() -> processProduct(request, luId, warehouseId, productId, unitsOnLUInt));
	}

	@NonNull
	private PackAndPickResult processProduct(
			@NonNull final MassPrintingScanRequest request,
			@NonNull final HuId luId,
			@NonNull final WarehouseId warehouseId,
			@NonNull final ProductId productId,
			final int unitsOnLUInt)
	{
		// FIFO selection of open shipment schedules, capped at units on LU.
		// Fully fill each order before moving to the next; if the last selected order's demand
		// exceeds remaining LU capacity, that order is partially filled — pick only the remaining
		// capacity, and its unmet demand stays open.
		final PackageableQuery query = PackageableQuery.builder()
				.productId(productId)
				.warehouseId(warehouseId)
				.onlyFromSalesOrder(true)
				.orderBys(ImmutableSet.of(OrderBy.PreparationDate))
				.build();

		// Maps each selected schedule to the qty to actually pick from it (may be < full demand for the last order).
		final Map<ShipmentScheduleId, Integer> selectedScheduleQtys = new LinkedHashMap<>();
		int totalDemand = 0;
		int capacityRemaining = unitsOnLUInt;

		for (final Packageable packageable : (Iterable<Packageable>) shipmentScheduleService.stream(query)::iterator)
		{
			final int scheduleDemand = packageable.getQtyToPick().toBigDecimal().intValueExact();
			if (scheduleDemand <= 0)
			{
				continue;
			}

			totalDemand += scheduleDemand;

			if (capacityRemaining <= 0)
			{
				// LU is fully allocated; this schedule stays entirely open — include its demand in total but don't pick it.
				continue;
			}

			// Pick the minimum of full demand and remaining capacity (partial fill on the last order).
			final int qtyToPickFromThisSchedule = Math.min(scheduleDemand, capacityRemaining);
			selectedScheduleQtys.put(packageable.getShipmentScheduleId(), qtyToPickFromThisSchedule);
			capacityRemaining -= qtyToPickFromThisSchedule;
		}

		final List<ShipmentScheduleId> selectedScheduleIds = new ArrayList<>(selectedScheduleQtys.keySet());
		final int boxesToPack = unitsOnLUInt - capacityRemaining;

		if (selectedScheduleIds.isEmpty() || boxesToPack <= 0)
		{
			return PackAndPickResult.builder()
					.boxesPacked(0)
					.unitsLeftOnLU(unitsOnLUInt)
					.openDemandRemaining(totalDemand)
					.build();
		}

		// Comply with the shipment schedule's effective packing instruction: the pick uses whatever
		// PI the schedule already carries (finite 1-CU/TU → one box per unit; Virtual PI → one VHU/CU
		// shipped directly without a box wrapper).  No PI override is written here.
		final ShipmentScheduleAndJobScheduleIdSet scheduleIdSet = ShipmentScheduleAndJobScheduleIdSet.ofShipmentScheduleIds(selectedScheduleIds);
		final PickingJob pickingJob = pickingJobService.createPickingJob(
				PickingJobCreateRequest.builder()
						.pickerId(request.getPickerId())
						.aggregationType(PickingJobAggregationType.PRODUCT)
						.isAllowPickingAnyHU(true)
						.scheduleIds(scheduleIdSet)
						.build());

		final HUQRCode luQRCode = huService.getQRCodeByHuId(luId);

		// Pick each line from the scanned LU using the capped qty determined above.
		// The qty equals the schedule-specific capped qty so the last partially-filled order is not over-picked.
		final List<PickingJobStepEvent> pickEvents = new ArrayList<>();
		for (final PickingJobLine line : pickingJob.getLines())
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
			final BigDecimal qtyToPick = BigDecimal.valueOf(cappedQtyInt);
			pickEvents.add(PickingJobStepEvent.builder()
					.pickingLineId(line.getId())
					.eventType(PickingJobStepEventType.PICK)
					.qrCode(luQRCode.toScannedCode())
					.qtyPicked(qtyToPick)
					.isPickWholeTU(false)
					.build());
		}

		final PickingJob pickedJob = pickingJobService.processStepEvents(pickingJob, pickEvents);

		// Capture the picked shippable HU ids BEFORE completing the job (for post-commit label printing).
		// getAllPickedHuIds() returns the top-level picked HUs — one per scheduled line
		// (finite PI: one TU per unit; Virtual PI: one VHU for all units in that schedule).
		final ImmutableSet<HuId> pickedHuIds = pickedJob.getAllPickedHuIds();

		// Resolve the leaf HUs (descend any target LU wrapper): one leaf shippable HU per picked unit.
		final ImmutableSet<HuId> packedHUIds = huService.getPackedBoxHUIds(pickedHuIds);
		pickingJobService.complete(pickedJob);

		final int unitsLeftOnLU = capacityRemaining;
		final int openDemandRemaining = Math.max(0, totalDemand - boxesToPack);

		return PackAndPickResult.builder()
				.boxesPacked(boxesToPack)
				.pickedHuIds(pickedHuIds)
				.packedHUIds(packedHUIds)
				.unitsLeftOnLU(unitsLeftOnLU)
				.openDemandRemaining(openDemandRemaining)
				.build();
	}

	/**
	 * Internal result of {@link #processProduct} holding both the picked shippable HU ids (for label printing after commit)
	 * and the resolved leaf HU ids (for the HU-shape assertion).
	 */
	@Value
	@lombok.Builder
	static class PackAndPickResult
	{
		/** Number of shippable HUs packed (one per picked unit). */
		int boxesPacked;

		/**
		 * Top-level picked HU ids (one per scheduled line) — used for post-commit label printing.
		 * May be TU boxes (finite PI) or VHUs/CUs (Virtual PI). May be empty when nothing was packed.
		 */
		@lombok.Builder.Default
		@NonNull ImmutableSet<HuId> pickedHuIds = ImmutableSet.of();

		/**
		 * Leaf shippable HU ids (descend any target-LU wrapper) — one per picked unit.
		 * May be empty when nothing was packed.
		 */
		@lombok.Builder.Default
		@NonNull ImmutableSet<HuId> packedHUIds = ImmutableSet.of();

		/** Units remaining on the LU after packing. */
		int unitsLeftOnLU;

		/** Units of open demand remaining after packing. */
		int openDemandRemaining;
	}
}
