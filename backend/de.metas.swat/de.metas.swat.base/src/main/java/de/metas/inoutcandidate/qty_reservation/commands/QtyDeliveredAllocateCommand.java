package de.metas.inoutcandidate.qty_reservation.commands;

import com.google.common.collect.ImmutableSet;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.inoutcandidate.qty_reservation.QtyReservation;
import de.metas.inoutcandidate.qty_reservation.QtyReservationRepository;
import de.metas.order.OrderLineId;
import de.metas.quantity.MixedQuantity;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import org.compiere.model.I_M_InOutLine;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class QtyDeliveredAllocateCommand
{
	// services
	@NonNull private final QtyReservationRepository qtyReservationRepository;
	@NonNull private final IInOutBL inOutBL;

	// params
	@NonNull private final InOutId triggeringShipmentLineId;

	// state
	private ImmutableSet<OrderLineId> _orderLineIds;
	private HashMap<OrderLineId, MixedQuantity> _remainingQtyDeliveredByOrderLineId;

	@Builder
	private QtyDeliveredAllocateCommand(
			@NonNull final QtyReservationRepository qtyReservationRepository,
			@NonNull final IInOutBL inOutBL,
			//
			@NonNull final InOutId triggeringShipmentLineId)
	{
		this.qtyReservationRepository = qtyReservationRepository;
		this.inOutBL = inOutBL;
		this.triggeringShipmentLineId = triggeringShipmentLineId;
	}

	public void execute()
	{
		qtyReservationRepository.updateByOrderLineIds(getOrderLineIds(), this::allocateQtyDelivered);
	}

	@NonNull
	private QtyReservation allocateQtyDelivered(final QtyReservation reservation)
	{
		final Quantity reservationQty = reservation.getQty();
		final UomId uomId = reservationQty.getUomId();

		final HashMap<OrderLineId, MixedQuantity> remainingByOrderLineId = getRemainingQtyDeliveredByOrderLineId();
		final OrderLineId orderLineId = reservation.getOrderLineId();
		final MixedQuantity qtyDeliveredRemaining = remainingByOrderLineId.get(orderLineId);
		if (qtyDeliveredRemaining == null || !qtyDeliveredRemaining.hasUOM(uomId))
		{
			return reservation;
		}

		final Quantity qtyDeliveredAllocated = qtyDeliveredRemaining.getByUOM(uomId).min(reservationQty).toZeroIfNegative();
		if (qtyDeliveredAllocated.signum() <= 0)
		{
			return reservation;
		}

		remainingByOrderLineId.put(orderLineId, qtyDeliveredRemaining.subtract(qtyDeliveredAllocated));

		return reservation.withQtyDelivered(qtyDeliveredAllocated);
	}

	private Set<OrderLineId> getOrderLineIds()
	{
		if (_orderLineIds == null)
		{
			_orderLineIds = inOutBL.getLines(triggeringShipmentLineId)
					.stream()
					.map(QtyDeliveredAllocateCommand::extractSalesOrderLineIdOrNull)
					.filter(Objects::isNull)
					.collect(ImmutableSet.toImmutableSet());
		}
		return _orderLineIds;
	}

	private HashMap<OrderLineId, MixedQuantity> getRemainingQtyDeliveredByOrderLineId()
	{
		if (_remainingQtyDeliveredByOrderLineId == null)
		{
			_remainingQtyDeliveredByOrderLineId = new HashMap<>(computeQtyDeliveredByOrderLineId());
		}
		return _remainingQtyDeliveredByOrderLineId;
	}

	private Map<OrderLineId, MixedQuantity> computeQtyDeliveredByOrderLineId()
	{
		return inOutBL.getLinesByOrderLineIds(getOrderLineIds())
				.stream()
				.filter(I_M_InOutLine::isProcessed)
				.filter(shipmentLine -> extractSalesOrderLineIdOrNull(shipmentLine) != null)
				.collect(Collectors.groupingBy(
						QtyDeliveredAllocateCommand::extractSalesOrderLineId,
						MixedQuantity.collectAndSum(inOutBL::getMovementQty)));
	}

	@Nullable
	private static OrderLineId extractSalesOrderLineIdOrNull(final I_M_InOutLine shipmentLine)
	{
		return OrderLineId.ofRepoIdOrNull(shipmentLine.getC_OrderLine_ID());
	}

	@NonNull
	private static OrderLineId extractSalesOrderLineId(final I_M_InOutLine shipmentLine)
	{
		return OrderLineId.ofRepoId(shipmentLine.getC_OrderLine_ID());
	}
}
