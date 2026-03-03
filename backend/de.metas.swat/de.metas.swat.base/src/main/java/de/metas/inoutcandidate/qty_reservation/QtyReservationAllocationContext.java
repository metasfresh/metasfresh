package de.metas.inoutcandidate.qty_reservation;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.logging.LogManager;
import de.metas.material.event.commons.AttributesKey;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.warehouse.WarehouseId;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Tracks qty-reservation state during the shipment schedule allocation loop.
 * <p>
 * Non-reserved schedules should only see unreserved stock. Reserved schedules see the full pool.
 * This context is mutable: as reserved schedules consume stock, the remaining reserved qty decreases,
 * making more stock available for subsequent non-reserved schedules.
 */
public class QtyReservationAllocationContext
{
	private static final Logger logger = LogManager.getLogger(QtyReservationAllocationContext.class);

	private final ImmutableMap<OrderLineId, ReservationDetail> byOrderLine;
	private final Map<StockMatchingKey, BigDecimal> remainingByKey;

	private QtyReservationAllocationContext(
			@NonNull final ImmutableMap<OrderLineId, ReservationDetail> byOrderLine,
			@NonNull final Map<StockMatchingKey, BigDecimal> remainingByKey)
	{
		this.byOrderLine = byOrderLine;
		this.remainingByKey = remainingByKey;
	}

	/**
	 * empty constructor
	 */
	private QtyReservationAllocationContext()
	{
		this.byOrderLine = ImmutableMap.of();
		this.remainingByKey = ImmutableMap.of();
	}

	@NonNull
	private static QtyReservationAllocationContext empty() {return new QtyReservationAllocationContext();}

	public boolean isEmpty()
	{
		return byOrderLine.isEmpty();
	}

	public BigDecimal getMyReservedQty(@Nullable final OrderLineId orderLineId)
	{
		if (orderLineId == null)
		{
			return BigDecimal.ZERO;
		}
		final ReservationDetail detail = byOrderLine.get(orderLineId);
		return detail != null ? detail.getRemainingQty() : BigDecimal.ZERO;
	}

	public BigDecimal getReservedByOthers(@Nullable final OrderLineId orderLineId, @NonNull final StockMatchingKey key)
	{
		final BigDecimal totalRemaining = remainingByKey.getOrDefault(key, BigDecimal.ZERO);
		final BigDecimal myReserved = getMyReservedQty(orderLineId);
		final BigDecimal reservedByOthers = totalRemaining.subtract(myReserved);
		return reservedByOthers.max(BigDecimal.ZERO);
	}

	public void consumeReservation(@Nullable final OrderLineId orderLineId, @NonNull final BigDecimal consumed)
	{
		if (orderLineId == null || consumed.signum() <= 0)
		{
			return;
		}

		final ReservationDetail detail = byOrderLine.get(orderLineId);
		if (detail == null)
		{
			return;
		}

		final BigDecimal actualConsumed = consumed.min(detail.getRemainingQty());
		if (actualConsumed.signum() <= 0)
		{
			return;
		}

		detail.subtractRemainingQty(actualConsumed);

		remainingByKey.merge(detail.getKey(), actualConsumed.negate(), BigDecimal::add);
		// Ensure non-negative
		remainingByKey.computeIfPresent(detail.getKey(), (k, v) -> v.max(BigDecimal.ZERO));
	}

	@VisibleForTesting
	public static QtyReservationAllocationContext createForTesting(
			@NonNull final ImmutableMap<OrderLineId, ReservationDetail> byOrderLine,
			@NonNull final Map<StockMatchingKey, BigDecimal> remainingByKey)
	{
		return new QtyReservationAllocationContext(byOrderLine, remainingByKey);
	}

	/**
	 * Build allocation context from pre-loaded domain objects.
	 */
	public static QtyReservationAllocationContext of(@NonNull final Collection<QtyReservation> reservations)
	{
		if (reservations.isEmpty())
		{
			return empty();
		}

		final ImmutableMap.Builder<OrderLineId, ReservationDetail> byOrderLineBuilder = ImmutableMap.builder();
		final Map<StockMatchingKey, BigDecimal> remainingByKey = new HashMap<>();

		// Group reservations by order line.
		// Note: there should be at most one reservation per order line, but we sum just in case.
		final Map<OrderLineId, BigDecimal> qtyByOrderLine = new HashMap<>();
		final Map<OrderLineId, StockMatchingKey> keyByOrderLine = new HashMap<>();

		for (final QtyReservation reservation : reservations)
		{
			final StockMatchingKey matchingKey = StockMatchingKey.of(
					reservation.getProductId(),
					reservation.getWarehouseId(),
					reservation.getAttributesKey());

			final BigDecimal qtyBD = reservation.getQty().toBigDecimal();
			qtyByOrderLine.merge(reservation.getOrderLineId(), qtyBD, BigDecimal::add);
			keyByOrderLine.put(reservation.getOrderLineId(), matchingKey);
			remainingByKey.merge(matchingKey, qtyBD, BigDecimal::add);
		}

		for (final Map.Entry<OrderLineId, BigDecimal> entry : qtyByOrderLine.entrySet())
		{
			byOrderLineBuilder.put(
					entry.getKey(),
					new ReservationDetail(keyByOrderLine.get(entry.getKey()), entry.getValue())
			);
		}

		return new QtyReservationAllocationContext(byOrderLineBuilder.build(), remainingByKey);
	}

	public BigDecimal reserve(@NonNull final OlAndSched olAndSched, @NonNull final BigDecimal qtyOnHandBeforeAllocation)
	{
		if (isEmpty())
		{
			return qtyOnHandBeforeAllocation;
		}

		final OrderLineId orderLineId = olAndSched.getSalesOrderLineId().orElse(null);
		if (orderLineId == null)
		{
			return qtyOnHandBeforeAllocation;
		}

		final AttributesKey attributesKey = AttributesKeys.createAttributesKeyFromASIStorageAttributes(olAndSched.getAttributeSetInstanceId())
				.orElse(AttributesKey.NONE);

		final StockMatchingKey matchingKey = StockMatchingKey.of(
				olAndSched.getProductId(),
				olAndSched.getWarehouseId(),
				attributesKey);

		final BigDecimal reservedByOthers = getReservedByOthers(orderLineId, matchingKey);
		final BigDecimal effectiveQtyOnHand = qtyOnHandBeforeAllocation.subtract(reservedByOthers).max(BigDecimal.ZERO);

		if (reservedByOthers.signum() > 0)
		{
			logger.debug("Reservation cap: rawQtyOnHand={}, reservedByOthers={}, effectiveQtyOnHand={}",
					qtyOnHandBeforeAllocation, reservedByOthers, effectiveQtyOnHand);
		}

		return effectiveQtyOnHand;
	}

	// --------------------------------------------------------------------------------------------
	// Inner classes
	// --------------------------------------------------------------------------------------------

	/**
	 * Matching key for stock reservation grouping: (ProductId, WarehouseId, AttributesKey).
	 * The AttributesKey is extracted from the shipment schedule's M_AttributeSetInstance_ID
	 * (via {@link org.adempiere.mm.attributes.keys.AttributesKeys#createAttributesKeyFromASIStorageAttributes})
	 * and from the M_QtyReservation record's AttributesKey column.
	 */
	@Value(staticConstructor = "of")
	public static class StockMatchingKey
	{
		@NonNull ProductId productId;
		@NonNull WarehouseId warehouseId;
		@NonNull AttributesKey attributesKey;
	}

	@Getter
	@RequiredArgsConstructor
	public static class ReservationDetail
	{
		@NonNull private final StockMatchingKey key;
		@NonNull private BigDecimal remainingQty;

		void subtractRemainingQty(@NonNull final BigDecimal qty)
		{
			this.remainingQty = this.remainingQty.subtract(qty).max(BigDecimal.ZERO);
		}
	}
}
