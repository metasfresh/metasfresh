package de.metas.inoutcandidate.qty_reservation;

import de.metas.handlingunits.QtyTU;
import de.metas.material.event.commons.AttributesKey;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.inout.util.StockMatchingKey;
import org.adempiere.warehouse.WarehouseId;

/**
 * Domain object representing a single qty reservation for a sales order line.
 * Loaded from the {@code M_QtyReservation} table via {@link QtyReservationRepository}.
 */
@Value
@Builder(toBuilder = true)
public class QtyReservation
{
	@NonNull QtyReservationId id;
	
	@NonNull OrderLineId orderLineId;

	@NonNull ProductId productId;
	@NonNull WarehouseId warehouseId;
	@NonNull AttributesKey attributesKey;

	@NonNull QtyTU qtyTU;
	@NonNull Quantity qty;
	@NonNull Quantity qtyDelivered;

	@NonNull
	public QtyReservation withQtyDelivered(@NonNull final Quantity newQtyDelivered)
	{
		return toBuilder().qtyDelivered(newQtyDelivered).build();
	}

	@NonNull
	public Quantity getEffectiveQty()
	{
		return qty.subtract(qtyDelivered).toZeroIfNegative();
	}

	/**
	 * @return {@code true} if the reservation is fully delivered (effective qty is zero).
	 */
	public boolean isFullyDelivered()
	{
		return getEffectiveQty().isZero();
	}

	public StockMatchingKey getStockMatchingKey()
	{
		return StockMatchingKey.builder()
				.productId(productId)
				.warehouseId(warehouseId)
				.attributesKey(attributesKey)
				.build();
	}
}
