package de.metas.handlingunits.picking.job.massprinting;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileService;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.massprinting.MassPrintingResult.ProductResult;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobStepEvent;
import de.metas.handlingunits.picking.job.model.PickingJobStepEventType;
import de.metas.handlingunits.picking.job.model.PickingUnit;
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

	/** Processes an LU scan: picks and labels every self-packed product on the LU, FIFO by preparation date. */
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

			// intValueExact: a self-packed product is whole-unit; a fractional qty would be a data error
			// we must surface (ArithmeticException), not silently truncate (which would silently drop a shippable unit).
			final int unitsOnLUInt = productStorage.getQty().toBigDecimal().intValueExact();
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
		// callInThreadInheritedTrx (not callInNewTrx): all picking-job commands use this convention
		// so every sub-call in the chain (PickingJobCompleteCommand, ShipmentService.groupSchedulesByAsyncBatch)
		// joins the same transaction and avoids self-deadlock on M_ShipmentSchedule row-locks.
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
		final ScheduleSelection selection = selectSchedules(productId, warehouseId, unitsOnLUInt);

		if (selection.selectedScheduleIds.isEmpty() || selection.boxesToPack <= 0)
		{
			return PackAndPickResult.builder()
					.boxesPacked(0)
					.unitsLeftOnLU(unitsOnLUInt)
					.openDemandRemaining(selection.totalDemand)
					.build();
		}

		final PickingJob pickedJob = createAndPickJob(request, luId, selection);
		final ImmutableSet<HuId> pickedHuIds = pickedJob.getAllPickedHuIds();
		final ImmutableSet<HuId> packedHUIds = huService.getPackedBoxHUIds(pickedHuIds);
		pickingJobService.complete(pickedJob);

		return PackAndPickResult.builder()
				.boxesPacked(selection.boxesToPack)
				.pickedHuIds(pickedHuIds)
				.packedHUIds(packedHUIds)
				.unitsLeftOnLU(selection.unitsLeftOnLU)
				.openDemandRemaining(Math.max(0, selection.totalDemand - selection.boxesToPack))
				.build();
	}

	/**
	 * Selects shipment schedules FIFO by preparation date, capped at units on LU.
	 * Fully fills each order before moving to the next; the last order may be partially filled.
	 */
	@NonNull
	private ScheduleSelection selectSchedules(
			@NonNull final ProductId productId,
			@NonNull final WarehouseId warehouseId,
			final int unitsOnLUInt)
	{
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
				// LU is fully allocated; include demand in total but don't pick this schedule.
				continue;
			}

			// Pick the minimum of full demand and remaining capacity (partial fill on the last order).
			final int qtyToPickFromThisSchedule = Math.min(scheduleDemand, capacityRemaining);
			selectedScheduleQtys.put(packageable.getShipmentScheduleId(), qtyToPickFromThisSchedule);
			capacityRemaining -= qtyToPickFromThisSchedule;
		}

		return new ScheduleSelection(
				selectedScheduleQtys,
				totalDemand,
				unitsOnLUInt - capacityRemaining,
				capacityRemaining);
	}

	/**
	 * Creates a PRODUCT picking job for the selected schedules, then picks each line from the scanned LU.
	 *
	 * <p>CU (Virtual PI) lines: one pick event per unit → one VHU per unit → one label per unit.
	 * TU (finite PI) lines: one event with full capped qty; PI capacity=1 forces one box per unit.
	 */
	@NonNull
	private PickingJob createAndPickJob(
			@NonNull final MassPrintingScanRequest request,
			@NonNull final HuId luId,
			@NonNull final ScheduleSelection selection)
	{
		final ShipmentScheduleAndJobScheduleIdSet scheduleIdSet = ShipmentScheduleAndJobScheduleIdSet.ofShipmentScheduleIds(selection.selectedScheduleIds);
		final PickingJob pickingJob = pickingJobService.createPickingJob(
				PickingJobCreateRequest.builder()
						.pickerId(request.getPickerId())
						.aggregationType(PickingJobAggregationType.PRODUCT)
						.isAllowPickingAnyHU(true)
						.scheduleIds(scheduleIdSet)
						.build());

		final HUQRCode luQRCode = huService.getQRCodeByHuId(luId);

		PickingJob pickedJob = pickingJob;
		for (final PickingJobLine line : pickingJob.getLines())
		{
			pickedJob = pickLine(pickedJob, line, luQRCode, selection.selectedScheduleQtys);
		}
		return pickedJob;
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
			// VHU/CU path: one event per unit → one VHU per unit → one label per unit.
			// Splitting to N calls of qty=1 is the only way to obtain N separate VHUs.
			PickingJob result = pickedJob;
			for (int unit = 0; unit < cappedQtyInt; unit++)
			{
				result = pickingJobService.processStepEvent(result, PickingJobStepEvent.builder()
						.pickingLineId(line.getId())
						.eventType(PickingJobStepEventType.PICK)
						.qrCode(luQRCode.toScannedCode())
						.qtyPicked(BigDecimal.ONE)
						.isPickWholeTU(false)
						.build());
			}
			return result;
		}
		else
		{
			// TU path: one event with full qty; PI capacity=1 produces one TU per unit.
			return pickingJobService.processStepEvent(pickedJob, PickingJobStepEvent.builder()
					.pickingLineId(line.getId())
					.eventType(PickingJobStepEventType.PICK)
					.qrCode(luQRCode.toScannedCode())
					.qtyPicked(BigDecimal.valueOf(cappedQtyInt))
					.isPickWholeTU(false)
					.build());
		}
	}

	/** Result of FIFO schedule selection: which schedules to pick and how many units from each. */
	private static class ScheduleSelection
	{
		final ImmutableMap<ShipmentScheduleId, Integer> selectedScheduleQtys;
		final ImmutableList<ShipmentScheduleId> selectedScheduleIds;
		final int totalDemand;
		final int boxesToPack;
		final int unitsLeftOnLU;

		ScheduleSelection(
				@NonNull final Map<ShipmentScheduleId, Integer> selectedScheduleQtys,
				final int totalDemand,
				final int boxesToPack,
				final int unitsLeftOnLU)
		{
			this.selectedScheduleQtys = ImmutableMap.copyOf(selectedScheduleQtys);
			this.selectedScheduleIds = ImmutableList.copyOf(selectedScheduleQtys.keySet());
			this.totalDemand = totalDemand;
			this.boxesToPack = boxesToPack;
			this.unitsLeftOnLU = unitsLeftOnLU;
		}
	}

	/**
	 * Internal result of {@link #processProduct} holding both the picked shippable HU ids (for label printing after commit)
	 * and the resolved leaf HU ids (for the HU-shape assertion).
	 */
	@Value
	@lombok.Builder
	private static class PackAndPickResult
	{
		/** Number of shippable HUs packed (one per picked unit). */
		int boxesPacked;

		/**
		 * Top-level picked HU ids — used for post-commit label printing.
		 * One per picked unit: TU boxes for finite-PI lines; one VHU per unit for VHU/CU lines
		 * (the CU pick-loop fires one event per unit so each unit gets its own VHU).
		 * May be empty when nothing was packed.
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
