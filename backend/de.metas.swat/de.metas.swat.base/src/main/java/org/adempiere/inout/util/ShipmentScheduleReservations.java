package org.adempiere.inout.util;

import com.google.common.collect.ImmutableList;
import de.metas.inoutcandidate.qty_reservation.QtyReservation;
import de.metas.order.OrderLineId;
import de.metas.quantity.Quantity;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

@EqualsAndHashCode
@ToString
public class ShipmentScheduleReservations
{
	public static final ShipmentScheduleReservations EMPTY = new ShipmentScheduleReservations(ImmutableList.of());

	@NonNull private final ImmutableList<QtyReservation> reservations;

	private ShipmentScheduleReservations(@NonNull final Collection<QtyReservation> reservations)
	{
		this.reservations = ImmutableList.copyOf(reservations);
	}

	public static ShipmentScheduleReservations of(@NonNull final Collection<QtyReservation> reservations)
	{
		return reservations.isEmpty() ? EMPTY : new ShipmentScheduleReservations(reservations);
	}

	public ShipmentScheduleReservations filter(@NonNull final StockMatchingKey stockMatchingKey)
	{
		return filter(reservation -> stockMatchingKey.includes(reservation.getStockMatchingKey()));
	}

	private ShipmentScheduleReservations filter(@NonNull final Predicate<QtyReservation> filter)
	{
		if (reservations.isEmpty())
		{
			return EMPTY;
		}

		final ImmutableList<QtyReservation> reservationsFiltered = reservations.stream()
				.filter(filter)
				.collect(ImmutableList.toImmutableList());

		return reservations.size() != reservationsFiltered.size()
				? new ShipmentScheduleReservations(reservationsFiltered)
				: this;
	}

	public Optional<Quantity> getReservedQtyForOthersThan(final @NonNull ReservationKey reservationKey)
	{
		return reservations.stream()
				.filter(reservation -> !isMatching(reservation, reservationKey))
				.map(QtyReservation::getQty)
				.reduce(Quantity::add);
	}

	private static boolean isMatching(@NonNull final QtyReservation reservation, @NonNull final ReservationKey reservationKey)
	{
		if (reservationKey.isNoKey())
		{
			return false;
		}

		return OrderLineId.equals(reservation.getOrderLineId(), reservationKey.getSalesOrderLineId());
	}
}
