package de.metas.shippingnotification;

import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderAndLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import javax.annotation.Nullable;

@Data
@Builder(toBuilder = true)
public class ShippingNotificationLine
{
	@Nullable private ShippingNotificationLineId id;

	@NonNull private final ProductId productId;
	@NonNull private final AttributeSetInstanceId asiId;
	@NonNull private final Quantity qty;
	@NonNull private final ShipmentScheduleId shipmentScheduleId;
	@NonNull private final OrderAndLineId orderAndLineId;
	@Nullable @Getter @Setter private SeqNo line;

	void markAsSaved(@NonNull final ShippingNotificationLineId id)
	{
		this.id = id;
	}

	ShippingNotificationLine createReversal()
	{
		return toBuilder().id(null).qty(qty.negate()).build();
	}
}
