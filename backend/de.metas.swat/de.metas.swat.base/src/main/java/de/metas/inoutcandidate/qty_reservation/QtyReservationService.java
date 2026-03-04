package de.metas.inoutcandidate.qty_reservation;

import com.google.common.collect.ImmutableMap;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.order.OrderLineId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * Synchronizes QtyDelivered on M_QtyReservation records
 * based on actual shipment completions (M_InOut).
 */
@Service
public class QtyReservationService
{
	@NonNull private final QtyReservationRepository qtyReservationRepository;
	@NonNull private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

	public QtyReservationService(@NonNull final QtyReservationRepository qtyReservationRepository)
	{
		this.qtyReservationRepository = qtyReservationRepository;
	}

	/**
	 * Called on M_InOut completion/reactivation.
	 * For each order line referenced by this shipment's lines,
	 * computes total delivered qty across ALL processed shipment lines for that order line,
	 * then spreads it across M_QtyReservation records.
	 */
	public void updateQtyDeliveredFromShipment(@NonNull final InOutId inOutId)
	{
		final Set<OrderLineId> orderLineIds = inOutDAO.retrieveLines(inOutDAO.getById(inOutId))
				.stream()
				.filter(line -> line.getC_OrderLine_ID() > 0)
				.map(line -> OrderLineId.ofRepoId(line.getC_OrderLine_ID()))
				.collect(Collectors.toSet());

		if (orderLineIds.isEmpty())
		{
			return;
		}

		final ImmutableMap<OrderLineId, BigDecimal> totalDeliveredByOrderLineId = computeTotalDeliveredByOrderLineIds(orderLineIds);

		final UnaryOperator<QtyReservation> spreadUpdater = buildSpreadUpdater(totalDeliveredByOrderLineId);
		qtyReservationRepository.updateByOrderLineIds(orderLineIds, spreadUpdater);
	}

	/**
	 * Builds a stateful {@link UnaryOperator} that spreads delivered quantities across reservations.
	 * <p>
	 * Each call consumes from a mutable remaining-qty map, so records processed earlier
	 * (OH before PS) get priority. The operator must be called in the correct record order.
	 */
	@NonNull
	private static UnaryOperator<QtyReservation> buildSpreadUpdater(
			@NonNull final ImmutableMap<OrderLineId, BigDecimal> totalDeliveredByOrderLineId)
	{
		final HashMap<OrderLineId, BigDecimal> remainingByOrderLineId = new HashMap<>(totalDeliveredByOrderLineId);

		return reservation -> {
			final OrderLineId orderLineId = reservation.getOrderLineId();
			final BigDecimal remaining = remainingByOrderLineId.getOrDefault(orderLineId, BigDecimal.ZERO);
			final BigDecimal recordQty = reservation.getQty().toBigDecimal();
			final BigDecimal allocated = remaining.min(recordQty).max(BigDecimal.ZERO);

			remainingByOrderLineId.put(orderLineId, remaining.subtract(allocated));

			final Quantity allocatedQty = reservation.getQty().toZero().add(allocated);
			return reservation.withQtyDelivered(allocatedQty);
		};
	}

	/**
	 * For each order line ID, computes the sum of MovementQty across all processed InOut lines.
	 * Single SQL query for all order line IDs.
	 */
	private ImmutableMap<OrderLineId, BigDecimal> computeTotalDeliveredByOrderLineIds(@NonNull final Set<OrderLineId> orderLineIds)
	{
		final List<I_M_InOutLine> allLines = inOutDAO.retrieveProcessedLinesForOrderLineIds(orderLineIds);

		return allLines.stream()
				.collect(Collectors.groupingBy(
						line -> OrderLineId.ofRepoId(line.getC_OrderLine_ID()),
						Collectors.reducing(BigDecimal.ZERO, I_M_InOutLine::getMovementQty, BigDecimal::add)
				))
				.entrySet()
				.stream()
				.collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));
	}
}
