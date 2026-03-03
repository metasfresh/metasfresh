package de.metas.inoutcandidate.qty_reservation;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_QtyReservation;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

		//
		// Bulk-load total delivered qty per order line (one SQL query)
		final ImmutableMap<OrderLineId, BigDecimal> totalDeliveredByOrderLineId = computeTotalDeliveredByOrderLineIds(orderLineIds);

		//
		// Bulk-load all reservation records for all order line IDs (one SQL query),
		// spread QtyDelivered, and batch-save
		final ImmutableListMultimap<OrderLineId, I_M_QtyReservation> reservationsByOrderLineId = qtyReservationRepository.getRecordsByOrderLineIds(orderLineIds);

		final List<I_M_QtyReservation> toSave = new ArrayList<>();
		for (final OrderLineId orderLineId : orderLineIds)
		{
			final BigDecimal totalDelivered = totalDeliveredByOrderLineId.getOrDefault(orderLineId, BigDecimal.ZERO);
			final List<I_M_QtyReservation> records = reservationsByOrderLineId.get(orderLineId);

			BigDecimal remaining = totalDelivered;
			for (final I_M_QtyReservation record : records)
			{
				final BigDecimal recordQty = record.getQty();
				final BigDecimal allocated = remaining.min(recordQty).max(BigDecimal.ZERO);
				record.setQtyDelivered(allocated);
				toSave.add(record);
				remaining = remaining.subtract(allocated);
			}
		}

		InterfaceWrapperHelper.saveAll(toSave);
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
