package de.metas.inoutcandidate.qty_reservation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.QtyTU;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_QtyReservation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Command that shrinks the active {@code M_QtyReservation} rows of each sales-order line
 * so that the line's TOTAL reserved Qty never exceeds the line's current {@code QtyOrdered}.
 * <p>
 * <b>One-directional</b>: it only ever reduces reservations; it never grows them.
 * <p>
 * Within a line, the excess is removed from PLANNED_SUPPLY (PS) rows before ON_HAND (OH) rows.
 */
public class ReconcileQtyReservationsCommand
{
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final QtyReservationRepository qtyReservationRepository;
	@NonNull private final OrderId orderId;

	@Builder
	private ReconcileQtyReservationsCommand(
			@NonNull final QtyReservationRepository qtyReservationRepository,
			@NonNull final OrderId orderId)
	{
		this.qtyReservationRepository = qtyReservationRepository;
		this.orderId = orderId;
	}

	/**
	 * Shrinks the total reserved Qty of each sales-order line down to the line's current {@code QtyOrdered}.
	 * <p>
	 * One-directional (only reduces, never grows). A row's {@code Qty} is never shrunk below that row's
	 * already-delivered qty ({@code QtyDelivered}). All comparison/subtraction arithmetic is performed in the
	 * order-line UOM; per-row reductions are converted back to each reservation's own UOM before being applied.
	 *
	 * @return the order-line IDs whose reservations were actually modified (empty if nothing changed)
	 */
	public ImmutableSet<OrderLineId> execute()
	{
		final List<OrderAndLineId> orderLineIds = orderDAO.retrieveAllOrderLineIds(orderId);

		// QtyReservationId -> (newQty, newQtyTU) for the rows that must shrink
		final Map<QtyReservationId, NewQty> newQtyByReservationId = new HashMap<>();
		final ImmutableSet.Builder<OrderLineId> affectedOrderLineIds = ImmutableSet.builder();

		for (final OrderAndLineId orderAndLineId : orderLineIds)
		{
			final OrderLineId orderLineId = orderAndLineId.getOrderLineId();
			final Quantity qtyOrdered = orderLineBL.getQtyOrdered(orderAndLineId);

			// cancelled / zero-qty lines have nothing to reconcile against
			if (qtyOrdered.signum() <= 0)
			{
				continue;
			}

			final ImmutableList<QtyReservation> reservations = qtyReservationRepository.getActiveByOrderLineId(orderLineId);
			if (reservations.isEmpty())
			{
				continue;
			}

			// total reserved, expressed in the order-line UOM
			Quantity totalReserved = null;
			for (final QtyReservation reservation : reservations)
			{
				final Quantity qtyInLineUOM = uomConversionBL.convertQuantityTo(
						reservation.getQty(),
						reservation.getProductId(),
						qtyOrdered.getUomId());
				totalReserved = totalReserved == null ? qtyInLineUOM : totalReserved.add(qtyInLineUOM);
			}

			// reservations is non-empty, so totalReserved is non-null here
			if (totalReserved.compareTo(qtyOrdered) <= 0)
			{
				continue; // nothing to shrink for this line
			}

			// excess to remove, expressed in the order-line UOM
			BigDecimal excessInLineUOM = totalReserved.subtract(qtyOrdered).toBigDecimal();

			// Reduce PS rows before OH rows. Within a group: stable by reservation id.
			final List<QtyReservation> ordered = sortPlannedSupplyBeforeOnHand(reservations);

			boolean lineChanged = false;
			for (final QtyReservation reservation : ordered)
			{
				if (excessInLineUOM.signum() <= 0)
				{
					break;
				}

				final BigDecimal rowQty = reservation.getQty().toBigDecimal();
				if (rowQty.signum() <= 0)
				{
					continue;
				}

				// already-delivered qty is the floor below which this row must never shrink
				final BigDecimal rowQtyDelivered = reservation.getQtyDelivered().toBigDecimal().max(BigDecimal.ZERO);

				// the most this row can give up (in the reservation's own UOM), never below delivered
				final BigDecimal reducibleRowQty = rowQty.subtract(rowQtyDelivered).max(BigDecimal.ZERO);
				if (reducibleRowQty.signum() <= 0)
				{
					continue;
				}

				// convert this row's reducible qty to the order-line UOM so excess/min/subtract are all in one UOM
				final BigDecimal reducibleRowQtyInLineUOM = uomConversionBL.convertQuantityTo(
								reservation.getQty().toZero().add(reducibleRowQty),
								reservation.getProductId(),
								qtyOrdered.getUomId())
						.toBigDecimal();

				final BigDecimal reductionInLineUOM = excessInLineUOM.min(reducibleRowQtyInLineUOM);

				// convert the reduction back to the reservation's own UOM
				final BigDecimal reductionInRowUOM = uomConversionBL.convertQuantityTo(
								qtyOrdered.toZero().add(reductionInLineUOM),
								reservation.getProductId(),
								reservation.getQty().getUomId())
						.toBigDecimal();

				// clamp: never shrink below already-delivered qty
				final BigDecimal newQtyBD = rowQty.subtract(reductionInRowUOM).max(rowQtyDelivered);

				final BigDecimal rowQtyTU = reservation.getQtyTU().toBigDecimal();
				final BigDecimal newQtyTUBD = rowQtyTU
						.multiply(newQtyBD)
						.divide(rowQty, 0, RoundingMode.HALF_UP);

				final Quantity newQty = reservation.getQty().toZero().add(newQtyBD);
				newQtyByReservationId.put(reservation.getId(), new NewQty(newQty, QtyTU.ofBigDecimal(newQtyTUBD)));
				lineChanged = true;

				// Subtract the ACTUAL applied reduction, not the intended one. The row is written `newQtyBD`,
				// which may differ from `rowQty - reductionInRowUOM` when the delivered-clamp kicks in or when a
				// non-integer UOM round-trip shifts the value. Tracking the real reduction (converted back to the
				// order-line UOM) keeps `excessInLineUOM` accurate and avoids a phantom residual across rows.
				// In same-UOM scenarios the conversion is the identity, so this equals `reductionInLineUOM` exactly.
				final BigDecimal actualReductionInRowUOM = rowQty.subtract(newQtyBD);
				final BigDecimal actualReductionInLineUOM = uomConversionBL.convertQuantityTo(
								reservation.getQty().toZero().add(actualReductionInRowUOM),
								reservation.getProductId(),
								qtyOrdered.getUomId())
						.toBigDecimal();
				excessInLineUOM = excessInLineUOM.subtract(actualReductionInLineUOM);
			}

			if (lineChanged)
			{
				affectedOrderLineIds.add(orderLineId);
			}
		}

		final ImmutableSet<OrderLineId> changedOrderLineIds = affectedOrderLineIds.build();
		if (newQtyByReservationId.isEmpty())
		{
			return ImmutableSet.of();
		}

		qtyReservationRepository.updateByOrderLineIds(
				changedOrderLineIds,
				before -> {
					final NewQty newQty = newQtyByReservationId.get(before.getId());
					return newQty != null ? before.withQty(newQty.getQty(), newQty.getQtyTU()) : before;
				});

		return changedOrderLineIds;
	}

	/**
	 * Returns the reservations ordered PLANNED_SUPPLY first, then ON_HAND;
	 * within each group ordered by reservation id for determinism.
	 * <p>
	 * The {@link QtyReservation} domain object does not expose SupplyType, so it is
	 * read directly from the {@code M_QtyReservation} records here.
	 */
	private List<QtyReservation> sortPlannedSupplyBeforeOnHand(@NonNull final List<QtyReservation> reservations)
	{
		final Map<QtyReservationId, Boolean> isPlannedSupplyById = loadIsPlannedSupplyById(reservations);

		return reservations.stream()
				.sorted(Comparator
						// PS (true) before OH (false): reverse boolean order
						.comparing((QtyReservation r) -> !isPlannedSupplyById.getOrDefault(r.getId(), Boolean.FALSE))
						.thenComparing(r -> r.getId().getRepoId()))
				.collect(ImmutableList.toImmutableList());
	}

	private Map<QtyReservationId, Boolean> loadIsPlannedSupplyById(@NonNull final List<QtyReservation> reservations)
	{
		final Set<Integer> ids = reservations.stream()
				.map(r -> r.getId().getRepoId())
				.collect(ImmutableSet.toImmutableSet());

		final Map<QtyReservationId, Boolean> result = new HashMap<>();
		queryBL.createQueryBuilder(I_M_QtyReservation.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_QtyReservation.COLUMNNAME_M_QtyReservation_ID, ids)
				.create()
				.stream()
				.forEach(record -> result.put(
						QtyReservationId.ofRepoId(record.getM_QtyReservation_ID()),
						SupplyType.ofCode(record.getSupplyType()).isPlannedSupply()));
		return result;
	}

	@Value
	private static class NewQty
	{
		@NonNull Quantity qty;
		@NonNull QtyTU qtyTU;
	}
}
