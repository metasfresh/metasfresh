package de.metas.handlingunits.picking.job.service.commands.get_qty_available;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobLineQtyAvailable;
import de.metas.handlingunits.picking.job.model.PickingJobQtyAvailable;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.handlingunits.picking.job.service.external.hu.ProductAvailableStocks;
import de.metas.handlingunits.picking.job.service.external.warehouse.PickingJobWarehouseService;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.user.UserId;
import de.metas.workplace.Workplace;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class PickingJobGetQtyAvailableCommand
{
	// services
	@NonNull private final PickingJobService pickingJobService;
	@NonNull private final PickingJobWarehouseService warehouseService;
	@NonNull private final PickingJobHUService huService;

	// params
	@NonNull private final PickingJobId pickingJobId;
	@NonNull private final UserId callerId;

	// state
	@NonNull private final Supplier<PickingJob> pickingJobSupplier = Suppliers.memoize(this::retrievePickingJob);
	@NonNull private final Supplier<ProductAvailableStocks> availableStocksSupplier = Suppliers.memoize(this::computeAvailableStocks);

	@Builder
	private PickingJobGetQtyAvailableCommand(
			@NonNull final PickingJobService pickingJobService,
			@NonNull final PickingJobWarehouseService warehouseService,
			@NonNull final PickingJobHUService huService,
			@NonNull final PickingJobId pickingJobId,
			@NonNull final UserId callerId)
	{
		this.pickingJobService = pickingJobService;
		this.warehouseService = warehouseService;
		this.huService = huService;
		this.pickingJobId = pickingJobId;
		this.callerId = callerId;
	}

	public PickingJobQtyAvailable execute()
	{
		final PickingJobQtyAvailable.PickingJobQtyAvailableBuilder result = PickingJobQtyAvailable.builder();

		final PickingJob pickingJob = getPickingJob();
		for (PickingJobLine line : pickingJob.getLines())
		{
			final Quantity qtyAvailableToPick = allocateLine(line);

			result.line(PickingJobLineQtyAvailable.builder()
					.lineId(line.getId())
					.qtyRemainingToPick(line.getQtyRemainingToPick())
					.qtyAvailableToPick(qtyAvailableToPick)
					.build());
		}

		return result.build();
	}

	private ImmutableSet<ProductId> getProductIdsRemainingToBePicked()
	{
		final PickingJob pickingJob = getPickingJob();
		return pickingJob.streamLines()
				.filter(line -> !line.isFullyPicked())
				.map(PickingJobLine::getProductId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	private PickingJob getPickingJob()
	{
		return pickingJobSupplier.get();
	}

	private PickingJob retrievePickingJob()
	{
		@NonNull final PickingJob pickingJob = pickingJobService.getById(pickingJobId);
		pickingJob.assertCanBeEditedBy(callerId);
		return pickingJob;
	}

	@Nullable
	private Quantity allocateLine(PickingJobLine line)
	{
		final Quantity qtyRemainingToPick = line.getQtyRemainingToPick();
		if (line.isFullyPicked())
		{
			return qtyRemainingToPick.toZeroIfNegative();
		}

		final ProductAvailableStocks availableStocks = getAvailableStocks();
		if (availableStocks == null)
		{
			return null; // N/A 
		}

		return availableStocks.allocateQty(line.getProductId(), qtyRemainingToPick);
	}

	@Nullable
	private ProductAvailableStocks getAvailableStocks()
	{
		return availableStocksSupplier.get();
	}

	@Nullable
	private ProductAvailableStocks computeAvailableStocks()
	{
		final Workplace workplace = warehouseService.getWorkplaceByUserId(callerId).orElse(null);
		if (workplace == null) {return null;}

		final ProductAvailableStocks availableStocks = huService.newAvailableStocksProvider(workplace);
		if (availableStocks == null) {return null;}

		availableStocks.warmUpByProductIds(getProductIdsRemainingToBePicked());

		return availableStocks;
	}

}
