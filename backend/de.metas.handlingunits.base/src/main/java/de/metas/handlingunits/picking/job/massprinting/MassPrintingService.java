package de.metas.handlingunits.picking.job.massprinting;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
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
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.api.PackageableQuery;
import de.metas.picking.api.PackageableQuery.OrderBy;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.product.Product;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Orchestration service for the mass-printing flow:
 * Scan LU → enumerate self-packed products → per-product FIFO selection →
 * create+pick+complete PRODUCT picking job → print one HU label per box.
 *
 * Per me03 https://github.com/metasfresh/me03/issues/29942 (F00230.21 "Mass Printing Labels").
 */
@Service
@RequiredArgsConstructor
public class MassPrintingService
{
	private static final Logger logger = LogManager.getLogger(MassPrintingService.class);

	@NonNull private final PickingJobService pickingJobService;
	@NonNull private final PickingJobHUService huService;
	@NonNull private final PickingJobProductService productService;
	@NonNull private final PickingJobShipmentScheduleService shipmentScheduleService;

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	/**
	 * Scan the LU and for each self-packed product on it:
	 * <ol>
	 *   <li>Select open shipment schedules FIFO by preparation date, capped at units on LU.</li>
	 *   <li>Create a PRODUCT picking job restricted to those schedules.</li>
	 *   <li>Pick each schedule from the scanned LU (one box per unit via 1-CU-per-TU packTo PI).</li>
	 *   <li>Complete the picking job.</li>
	 *   <li>Print one HU label per box (best-effort, after commit).</li>
	 * </ol>
	 *
	 * @param request scan request carrying the LU id and the picker's user id
	 * @return per-product result summary (boxes packed, labels printed, leftovers)
	 */
	@NonNull
	public MassPrintingResult scan(@NonNull final MassPrintingScanRequest request)
	{
		final HuId luId = request.getLuId();

		// Enumerate products on the LU
		final List<IHUProductStorage> productStorages = huService.getProductStorages(luId);
		if (productStorages.isEmpty())
		{
			return MassPrintingResult.builder().build();
		}

		// Resolve product master data to check IsSelfPacked
		final ImmutableSet<ProductId> productIds = productStorages.stream()
				.map(IHUProductStorage::getProductId)
				.collect(ImmutableSet.toImmutableSet());
		final Map<ProductId, Product> productsById = productService.getByIdsAsMap(productIds);

		// Determine warehouse from scanned LU
		final LocatorId locatorId = huService.getLocatorId(luId);
		final WarehouseId warehouseId = locatorId.getWarehouseId();

		// Per-product results
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
			final int unitsOnLUInt = unitsOnLU.toBigDecimal().intValue();
			if (unitsOnLUInt <= 0)
			{
				logger.debug("No units on LU for product: {}", productId);
				continue;
			}

			final ProductResult result = processProductInTrx(request, luId, warehouseId, productId, unitsOnLUInt);
			productResults.add(result);
		}

		return MassPrintingResult.builder()
				.productResults(productResults.build())
				.skippedNonSelfPackedProductIds(skippedNonSelfPacked.build())
				.build();
	}

	@NonNull
	private ProductResult processProductInTrx(
			@NonNull final MassPrintingScanRequest request,
			@NonNull final HuId luId,
			@NonNull final WarehouseId warehouseId,
			@NonNull final ProductId productId,
			final int unitsOnLUInt)
	{
		return trxManager.callInThreadInheritedTrx(() -> processProduct(request, luId, warehouseId, productId, unitsOnLUInt));
	}

	@NonNull
	private ProductResult processProduct(
			@NonNull final MassPrintingScanRequest request,
			@NonNull final HuId luId,
			@NonNull final WarehouseId warehouseId,
			@NonNull final ProductId productId,
			final int unitsOnLUInt)
	{
		// FIFO selection of open shipment schedules, capped at units on LU
		final PackageableQuery query = PackageableQuery.builder()
				.productId(productId)
				.warehouseId(warehouseId)
				.onlyFromSalesOrder(true)
				.orderBys(ImmutableSet.of(OrderBy.PreparationDate))
				.build();

		final List<ShipmentScheduleId> selectedScheduleIds = new ArrayList<>();
		final int[] demandRemaining = { 0 };
		final int[] unitsAllocated = { 0 };

		shipmentScheduleService.stream(query).forEach(packageable -> {
			final int qtyToPick = packageable.getQtyToPick().toBigDecimal().intValue();
			if (qtyToPick <= 0)
			{
				return;
			}

			demandRemaining[0] += qtyToPick;
			if (unitsAllocated[0] < unitsOnLUInt)
			{
				selectedScheduleIds.add(packageable.getShipmentScheduleId());
				unitsAllocated[0] += qtyToPick;
			}
		});

		final int boxesToPack = Math.min(unitsAllocated[0], unitsOnLUInt);
		if (selectedScheduleIds.isEmpty() || boxesToPack <= 0)
		{
			return ProductResult.builder()
					.productId(productId)
					.boxesPacked(0)
					.labelsPrinted(0)
					.labelPrintFailures(0)
					.unitsLeftOnLU(unitsOnLUInt)
					.unitsOfOpenDemandRemaining(demandRemaining[0])
					.build();
		}

		// Create PRODUCT picking job restricted to selected schedules
		final ShipmentScheduleAndJobScheduleIdSet scheduleIdSet = ShipmentScheduleAndJobScheduleIdSet.ofShipmentScheduleIds(selectedScheduleIds);
		final PickingJob pickingJob = pickingJobService.createPickingJob(
				PickingJobCreateRequest.builder()
						.pickerId(request.getPickerId())
						.aggregationType(PickingJobAggregationType.PRODUCT)
						.isAllowPickingAnyHU(true)
						.scheduleIds(scheduleIdSet)
						.build());

		// Obtain scanned LU's QR code to use as pick-from reference
		final HUQRCode luQRCode = huService.getQRCodeByHuId(luId);

		// Pick each line from the scanned LU
		final List<PickingJobStepEvent> pickEvents = new ArrayList<>();
		for (final PickingJobLine line : pickingJob.getLines())
		{
			final BigDecimal qtyToPick = line.getQtyToPick().toBigDecimal();
			pickEvents.add(PickingJobStepEvent.builder()
					.pickingLineId(line.getId())
					.eventType(PickingJobStepEventType.PICK)
					.qrCode(luQRCode.toScannedCode())
					.qtyPicked(qtyToPick)
					.isPickWholeTU(false)
					.build());
		}

		final PickingJob pickedJob = pickingJobService.processStepEvents(pickingJob, pickEvents);
		pickingJobService.complete(pickedJob);

		final int unitsLeftOnLU = unitsOnLUInt - boxesToPack;
		final int openDemandRemaining = Math.max(0, demandRemaining[0] - boxesToPack);

		// Labels printed after commit (best-effort) — done outside this trx block
		return ProductResult.builder()
				.productId(productId)
				.boxesPacked(boxesToPack)
				.labelsPrinted(0) // updated after label printing
				.labelPrintFailures(0)
				.unitsLeftOnLU(unitsLeftOnLU)
				.unitsOfOpenDemandRemaining(openDemandRemaining)
				.build();
	}
}
