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
import java.util.stream.Collectors;

/** Orchestration service for the mass-printing flow: scan LU → pick every self-packed product → print labels. */
@Service
@RequiredArgsConstructor
public class MassPrintingService
{
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

		final ImmutableSet<ProductId> productIds = extractProductIds(productStorages);
		final Map<ProductId, Product> productsById = productService.getByIdsAsMap(productIds);

		final LocatorId locatorId = huService.getLocatorId(luId);
		final WarehouseId warehouseId = locatorId.getWarehouseId();

		final ImmutableSet<ProductId> selfPackedProductIds = retainSelfPackedProducts(productIds, productsById);

		// Batch-load all packageables for self-packed products in one query (avoids N+1 per product).
		final Map<ProductId, List<Packageable>> packageablesByProduct = loadPackageablesGroupedByProduct(warehouseId, selfPackedProductIds);

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

			// Pack+ship inside a transaction; label printing happens AFTER the transaction commits
			// (best-effort: failures are counted but do not roll back the packed boxes).
			final PackAndPickResult packAndPickResult = processProductInTrx(request, luId, warehouseId, productId, productStorage.getQtyAsInt(), packageables);

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
			@NonNull final ImmutableSet<ProductId> productIds,
			@NonNull final Map<ProductId, Product> productsById)
	{
		return productIds.stream()
				.filter(productId -> {
					final Product product = productsById.get(productId);
					return product != null && product.isSelfPacked();
				})
				.collect(ImmutableSet.toImmutableSet());
	}

	/** Loads all packageables for the given products in one query, grouped by product. */
	private Map<ProductId, List<Packageable>> loadPackageablesGroupedByProduct(
			@NonNull final WarehouseId warehouseId,
			@NonNull final ImmutableSet<ProductId> selfPackedProductIds)
	{
		if (selfPackedProductIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final PackageableQuery query = PackageableQuery.builder()
				.warehouseId(warehouseId)
				.onlyFromSalesOrder(true)
				.orderBys(ImmutableSet.of(OrderBy.PreparationDate))
				.build();

		return shipmentScheduleService.stream(query)
				.filter(p -> selfPackedProductIds.contains(p.getProductId()))
				.collect(Collectors.groupingBy(Packageable::getProductId));
	}

	@NonNull
	private PackAndPickResult processProductInTrx(
			@NonNull final MassPrintingScanRequest request,
			@NonNull final HuId luId,
			@NonNull final WarehouseId warehouseId,
			@NonNull final ProductId productId,
			final int unitsOnLU,
			@NonNull final List<Packageable> packageables)
	{
		// callInThreadInheritedTrx (not callInNewTrx): all picking-job commands use this convention
		// so every sub-call in the chain (PickingJobCompleteCommand, ShipmentService.groupSchedulesByAsyncBatch)
		// joins the same transaction and avoids self-deadlock on M_ShipmentSchedule row-locks.
		return trxManager.callInThreadInheritedTrx(() -> processProduct(request, luId, productId, unitsOnLU, packageables));
	}

	@NonNull
	private PackAndPickResult processProduct(
			@NonNull final MassPrintingScanRequest request,
			@NonNull final HuId luId,
			@NonNull final ProductId productId,
			final int unitsOnLU,
			@NonNull final List<Packageable> packageables)
	{
		final ScheduleSelection selection = selectSchedulesFifo(packageables, unitsOnLU);

		if (selection.selectedScheduleIds.isEmpty() || selection.boxesToPack <= 0)
		{
			return PackAndPickResult.builder()
					.boxesPacked(0)
					.unitsLeftOnLU(unitsOnLU)
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

	@NonNull
	private ProductResult buildProductResult(
			@NonNull final ProductId productId,
			@NonNull final PackAndPickResult packAndPickResult)
	{
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

		return ProductResult.builder()
				.productId(productId)
				.boxesPacked(packAndPickResult.getBoxesPacked())
				.packedHUIds(packAndPickResult.getPackedHUIds())
				.labelsPrinted(labelsPrinted)
				.labelPrintFailures(labelPrintFailures)
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
				unitsOnLU - capacityRemaining,
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
			return pickCuLine(pickedJob, line, luQRCode, cappedQtyInt);
		}
		else
		{
			return pickTuLine(pickedJob, line, luQRCode, cappedQtyInt);
		}
	}

	/** VHU/CU path: one event per unit → one VHU per unit → one label per unit. */
	@NonNull
	private PickingJob pickCuLine(
			@NonNull final PickingJob pickedJob,
			@NonNull final PickingJobLine line,
			@NonNull final HUQRCode luQRCode,
			final int cappedQtyInt)
	{
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

	/** TU path: one event with full qty; PI capacity=1 produces one TU per unit. */
	@NonNull
	private PickingJob pickTuLine(
			@NonNull final PickingJob pickedJob,
			@NonNull final PickingJobLine line,
			@NonNull final HUQRCode luQRCode,
			final int cappedQtyInt)
	{
		return pickingJobService.processStepEvent(pickedJob, PickingJobStepEvent.builder()
				.pickingLineId(line.getId())
				.eventType(PickingJobStepEventType.PICK)
				.qrCode(luQRCode.toScannedCode())
				.qtyPicked(BigDecimal.valueOf(cappedQtyInt))
				.isPickWholeTU(false)
				.build());
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
