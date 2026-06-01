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

	public void execute()
	{
		final List<OrderAndLineId> orderLineIds = orderDAO.retrieveAllOrderLineIds(orderId);

		// QtyReservationId -> (newQty, newQtyTU) for the rows that must shrink
		final Map<QtyReservationId, NewQty> newQtyByReservationId = new HashMap<>();
		final ImmutableSet.Builder<OrderLineId> affectedOrderLineIds = ImmutableSet.builder();

		for (final OrderAndLineId orderAndLineId : orderLineIds)
		{
			final OrderLineId orderLineId = orderAndLineId.getOrderLineId();
			final Quantity qtyOrdered = orderLineBL.getQtyOrdered(orderAndLineId);

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

			if (totalReserved == null || totalReserved.compareTo(qtyOrdered) <= 0)
			{
				continue; // nothing to shrink for this line
			}

			BigDecimal excess = totalReserved.subtract(qtyOrdered).toBigDecimal();

			// Reduce PS rows before OH rows. Within a group: stable by reservation id.
			final List<QtyReservation> ordered = sortPlannedSupplyBeforeOnHand(reservations);

			for (final QtyReservation reservation : ordered)
			{
				if (excess.signum() <= 0)
				{
					break;
				}

				final BigDecimal rowQty = reservation.getQty().toBigDecimal();
				if (rowQty.signum() <= 0)
				{
					continue;
				}

				final BigDecimal reductionFromThisRow = excess.min(rowQty);
				final BigDecimal newQtyBD = rowQty.subtract(reductionFromThisRow).max(BigDecimal.ZERO);

				final BigDecimal rowQtyTU = reservation.getQtyTU().toBigDecimal();
				final BigDecimal newQtyTUBD = rowQtyTU
						.multiply(newQtyBD)
						.divide(rowQty, 0, RoundingMode.HALF_UP);

				final Quantity newQty = reservation.getQty().toZero().add(newQtyBD);
				newQtyByReservationId.put(reservation.getId(), new NewQty(newQty, QtyTU.ofBigDecimal(newQtyTUBD)));

				excess = excess.subtract(reductionFromThisRow);
			}

			affectedOrderLineIds.add(orderLineId);
		}

		if (newQtyByReservationId.isEmpty())
		{
			return;
		}

		qtyReservationRepository.updateByOrderLineIds(
				affectedOrderLineIds.build(),
				before -> {
					final NewQty newQty = newQtyByReservationId.get(before.getId());
					return newQty != null ? before.withQty(newQty.qty, newQty.qtyTU) : before;
				});
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
				.addInArrayFilter(I_M_QtyReservation.COLUMNNAME_M_QtyReservation_ID, ids)
				.create()
				.stream()
				.forEach(record -> result.put(
						QtyReservationId.ofRepoId(record.getM_QtyReservation_ID()),
						SupplyType.PLANNED_SUPPLY.getCode().equals(record.getSupplyType())));
		return result;
	}

	private static final class NewQty
	{
		final Quantity qty;
		final QtyTU qtyTU;

		NewQty(@NonNull final Quantity qty, @NonNull final QtyTU qtyTU)
		{
			this.qty = qty;
			this.qtyTU = qtyTU;
		}
	}
}
