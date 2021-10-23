package de.metas.handlingunits.ddorder.movement;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.ddorder.IHUDDOrderBL;
import de.metas.handlingunits.ddorder.picking.DDOrderPickFromService;
import de.metas.handlingunits.ddorder.picking.DDOrderPickSchedule;
import de.metas.handlingunits.ddorder.picking.ScheduleToPickRequest;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.i18n.AdMessageKey;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Stream;

/**
 * Helper class to allocate HUs to a list of DDOrderLineToAllocate
 */
public class DDOrderLinesAllocator
{
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IHUDDOrderBL ddOrderBL;
	private final DDOrderPickFromService ddOrderPickFromService;

	private static final AdMessageKey MSG_DD_Order_NoLine_for_product = AdMessageKey.of("de.metas.handlingunits.ddorder.api.impl.DDOrderLinesAllocator.DD_Order_NoLine_for_product");

	private final ImmutableList<DDOrderLineToAllocate> ddOrderLines;
	private boolean failIfCannotAllocate = false; // default=false for backward compatibility

	DDOrderLinesAllocator(
			@NonNull final IHUDDOrderBL ddOrderBL,
			@NonNull final DDOrderPickFromService ddOrderPickFromService,
			@NonNull final ImmutableList<DDOrderLineToAllocate> ddOrderLines)
	{
		this.ddOrderBL = ddOrderBL;
		this.ddOrderPickFromService = ddOrderPickFromService;
		this.ddOrderLines = ddOrderLines;
	}

	public DDOrderLinesAllocator failIfCannotAllocate()
	{
		this.failIfCannotAllocate = true;
		return this;
	}

	public DDOrderLinesAllocator allocateHU(@NonNull final I_M_HU hu)
	{
		return allocateHUs(ImmutableList.of(hu));
	}

	public DDOrderLinesAllocator allocateHUs(final List<I_M_HU> hus)
	{
		huContextFactory.createMutableHUContext()
				.getHUStorageFactory()
				.streamHUProductStorages(hus)
				.forEach(this::allocateHUProductStorage);
		return this;
	}

	private ImmutableList<ScheduleToPickRequest> toScheduleToPickRequest()
	{
		return ddOrderLines.stream()
				.flatMap(DDOrderLinesAllocator::toScheduleToPickRequest)
				.collect(ImmutableList.toImmutableList());
	}

	private static Stream<ScheduleToPickRequest> toScheduleToPickRequest(@NonNull final DDOrderLineToAllocate line)
	{
		return line.getPickFromHUs()
				.stream()
				.map(pickFromHU -> toScheduleToPickRequest(line, pickFromHU));
	}

	private static ScheduleToPickRequest toScheduleToPickRequest(@NonNull final DDOrderLineToAllocate line, @NonNull final DDOrderLineToAllocate.PickFromHU pickFromHU)
	{
		return ScheduleToPickRequest.builder()
				.ddOrderId(line.getDDOrderId())
				.ddOrderLineId(line.getDDOrderLineId())
				.pickFromHUId(pickFromHU.getHuId())
				.qtyToPick(pickFromHU.getQty())
				.isPickWholeHU(true)
				.build();
	}

	public MovementsFromSchedulesGenerator thenPrepareGeneratingMovements()
	{
		// TODO make sure we are not calling this method twice
		final ImmutableList<DDOrderPickSchedule> schedules = ddOrderPickFromService.addScheduleToPickBulk(toScheduleToPickRequest());

		return MovementsFromSchedulesGenerator.fromSchedules(schedules, ddOrderBL, ddOrderPickFromService);
	}

	private void allocateHUProductStorage(@NonNull final IHUProductStorage huProductStorage)
	{
		final ProductId productId = huProductStorage.getProductId();
		final ImmutableList<DDOrderLineToAllocate> ddOrderLinesToAllocate = getLinesToAllocateByProductId(productId);

		// No DD Order Lines were found for our Product
		// Shall not happen
		if (ddOrderLinesToAllocate.isEmpty())
		{
			throw new HUException(MSG_DD_Order_NoLine_for_product)
					.appendParametersToMessage()
					.setParameter("Product", productBL.getProductValueAndName(productId))
					.setParameter("HUProductStorage", huProductStorage);
		}

		final I_M_HU hu = huProductStorage.getM_HU();
		Quantity qtyToAllocateRemaining = huProductStorage.getQty();

		DDOrderLineToAllocate lastDDOrderLineToAllocate = null;
		for (final DDOrderLineToAllocate ddOrderLineToAllocate : ddOrderLinesToAllocate)
		{
			lastDDOrderLineToAllocate = ddOrderLineToAllocate;

			//
			// Check if there is more to allocate
			if (qtyToAllocateRemaining.isZero())
			{
				break;
			}

			//
			// Check if this line is fully shipped
			if (ddOrderLineToAllocate.isFullyShipped())
			{
				continue;
			}

			//
			// Allocate
			final Quantity qtyShipped = addQtyShipped(ddOrderLineToAllocate, hu, qtyToAllocateRemaining, false);

			//
			// Decrease our remaining quantity (from HU Product Storage) that we a struggling to allocate to existing DD Order Lines/Alternatives
			qtyToAllocateRemaining = qtyToAllocateRemaining.subtract(qtyShipped);
		}

		//
		// Allocate entire remaining qty on the last line
		if (!qtyToAllocateRemaining.isZero() && lastDDOrderLineToAllocate != null)
		{
			addQtyShipped(lastDDOrderLineToAllocate, hu, qtyToAllocateRemaining, true);
		}
	}

	/**
	 * @param force if <code>true</code> it will allocate entire quantity no matter what
	 * @return actual quantity that was added to shipped quantity
	 */
	private Quantity addQtyShipped(
			final DDOrderLineToAllocate ddOrderLineToAllocate,
			final I_M_HU hu,
			final Quantity qtyToAllocate,
			final boolean force)
	{
		final ProductId productId = ddOrderLineToAllocate.getProductId();

		//
		// Get how much we can allocate (maximum)
		final Quantity qtyToAllocateMax;
		if (force)
		{
			qtyToAllocateMax = qtyToAllocate;
		}
		else
		{
			qtyToAllocateMax = ddOrderLineToAllocate.getQtyToShipRemaining();
		}

		final Quantity qtyToAllocateConv = uomConversionBL.convertQuantityTo(qtyToAllocate, productId, qtyToAllocateMax.getUomId());
		final Quantity qtyToAllocateRemaining = qtyToAllocateMax.subtract(qtyToAllocateConv);

		final Quantity qtyShipped;
		if (qtyToAllocateRemaining.signum() < 0)
		{
			qtyShipped = qtyToAllocateMax; // allocate the maximum allowed qty if it's lower than the remaining qty
		}
		else
		{
			qtyShipped = qtyToAllocateConv; // allocate the full remaining qty because it's within the maximum boundaries
		}

		if (qtyShipped.isZero())
		{
			return qtyShipped;
		}

		final Quantity qtyShippedConv = uomConversionBL.convertQuantityTo(qtyShipped, productId, qtyToAllocateMax.getUomId());

		ddOrderLineToAllocate.addPickFromHU(DDOrderLineToAllocate.PickFromHU.builder()
				.hu(hu)
				.qty(qtyShippedConv)
				.build());

		return qtyShippedConv;
	}

	private ImmutableList<DDOrderLineToAllocate> getLinesToAllocateByProductId(@NonNull final ProductId productId)
	{
		return ddOrderLines.stream()
				.filter(ddOrderLineToAllocate -> ProductId.equals(ddOrderLineToAllocate.getProductId(), productId))
				.collect(ImmutableList.toImmutableList());
	}

}
