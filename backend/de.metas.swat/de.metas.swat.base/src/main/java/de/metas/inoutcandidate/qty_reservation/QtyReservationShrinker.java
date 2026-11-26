package de.metas.inoutcandidate.qty_reservation;

import de.metas.handlingunits.QtyTU;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The single implementation of "shrink one reservation row by a CU qty": never below the row's already-delivered qty;
 * the CU {@code Qty} keeps the exact reduced value, the integer {@code QtyTU} is rounded UP (a partially-filled TU
 * still occupies a whole TU); a row that ends up with Qty zero is to be deleted.
 * Used by {@link ReconcileQtyReservationsCommand} and {@link QtyReservationService#reduceReservation}.
 */
final class QtyReservationShrinker
{
	private QtyReservationShrinker() {}

	/**
	 * @param reductionInRowUOM the intended reduction, in the reservation's own UOM. Typed as {@link Quantity} so a
	 *                          reduction accidentally expressed in a foreign UOM throws (UOM-mismatch) rather than being
	 *                          silently subtracted; the cross-dimension TU ratio below necessarily stays on {@link BigDecimal}.
	 */
	static Result shrink(@NonNull final QtyReservation reservation, @NonNull final Quantity reductionInRowUOM)
	{
		final Quantity reserved = reservation.getQty();
		final BigDecimal rowQty = reserved.toBigDecimal();

		// clamp: never shrink below the already-delivered qty (floored at 0). subtract/max are UOM-aware,
		// so a reductionInRowUOM in a foreign UOM throws here instead of silently miscomputing.
		final Quantity deliveredFloor = reservation.getQtyDelivered().max(reserved.toZero());
		final BigDecimal newQtyBD = reserved.subtract(reductionInRowUOM).max(deliveredFloor).toBigDecimal();
		final BigDecimal actualReduction = rowQty.subtract(newQtyBD);

		if (newQtyBD.signum() <= 0)
		{
			return new Result(true, null, null, actualReduction);
		}

		// cross-dimension ratio (TU-scalar × CU/CU → TU count), rounded UP: a partially-filled TU still occupies a whole TU
		final BigDecimal rowQtyTU = reservation.getQtyTU().toBigDecimal();
		final BigDecimal newQtyTUBD = rowQtyTU
				.multiply(newQtyBD)
				.divide(rowQty, 0, RoundingMode.CEILING);

		return new Result(
				false,
				reserved.toZero().add(newQtyBD),
				QtyTU.ofBigDecimal(newQtyTUBD),
				actualReduction);
	}

	/**
	 * TUs of the row that can still be given up: {@code QtyTU - ceil(QtyTU * QtyDelivered / Qty)}, i.e. the TUs that
	 * are not needed to hold the already-delivered qty. Zero for a row without positive Qty/QtyTU.
	 */
	static int getReversibleTU(@NonNull final QtyReservation reservation)
	{
		final BigDecimal rowQty = reservation.getQty().toBigDecimal();
		final int rowQtyTU = reservation.getQtyTU().toInt();
		if (rowQty.signum() <= 0 || rowQtyTU <= 0)
		{
			return 0;
		}
		// clamp: an over-delivered row (QtyDelivered > Qty) would otherwise yield a negative value
		return Math.max(0, rowQtyTU - getMinQtyTU(reservation, rowQty, rowQtyTU));
	}

	private static int getMinQtyTU(final QtyReservation reservation, final BigDecimal rowQty, final int rowQtyTU)
	{
		final BigDecimal delivered = reservation.getQtyDelivered().toBigDecimal().max(BigDecimal.ZERO);
		return BigDecimal.valueOf(rowQtyTU).multiply(delivered).divide(rowQty, 0, RoundingMode.CEILING).intValue();
	}

	/**
	 * Reduce-by-TU variant (used by {@link QtyReservationService#reduceReservation}): QtyTU is decremented EXACTLY by
	 * {@code min(tuToTake, reversible TU of the row)}; the CU Qty is scaled proportionally, never below the delivered qty.
	 * Independent of the CU-target math of {@link #shrink}, which stays as is for the reconcile command.
	 *
	 * @return the result; {@code Result#getActualReduction()} is the CU reduction, the TUs actually taken are
	 * {@code row.QtyTU - result.newQtyTU} (or the whole row's QtyTU if {@code delete})
	 */
	static Result shrinkByTU(@NonNull final QtyReservation reservation, final int tuToTake)
	{
		final BigDecimal rowQty = reservation.getQty().toBigDecimal();
		final int rowQtyTU = reservation.getQtyTU().toInt();
		final BigDecimal delivered = reservation.getQtyDelivered().toBigDecimal().max(BigDecimal.ZERO);

		final int newQtyTU = Math.max(rowQtyTU - tuToTake, getMinQtyTU(reservation, rowQty, rowQtyTU));
		if (newQtyTU <= 0)
		{
			return new Result(true, null, null, rowQty);
		}

		// QtyTU is the exact quantity; the CU Qty is only scaled proportionally, so a scale-10 HALF_UP approximation is intended
		final BigDecimal newQtyBD = rowQty
				.multiply(BigDecimal.valueOf(newQtyTU))
				.divide(BigDecimal.valueOf(rowQtyTU), 10, RoundingMode.HALF_UP)
				.max(delivered)
				.min(rowQty);
		return new Result(false, reservation.getQty().toZero().add(newQtyBD), QtyTU.ofInt(newQtyTU), rowQty.subtract(newQtyBD));
	}

	@Value
	static class Result
	{
		/** true: row ends up with Qty zero and must be deleted (newQty/newQtyTU are null then) */
		boolean delete;
		Quantity newQty;
		QtyTU newQtyTU;
		/** actually applied reduction in the reservation's UOM (may be less than intended, due to the delivered clamp) */
		@NonNull BigDecimal actualReduction;
	}
}
