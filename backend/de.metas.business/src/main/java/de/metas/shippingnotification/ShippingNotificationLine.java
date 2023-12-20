package de.metas.shippingnotification;

import de.metas.document.dimension.Dimension;
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderAndLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.lang.SeqNo;
import lombok.AccessLevel;
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
	@NonNull private final OrderAndLineId salesOrderAndLineId;
	@NonNull @Getter @Setter(AccessLevel.PACKAGE) @Builder.Default private SeqNo line = SeqNo.ofInt(0);

	@Nullable private ShippingNotificationLineId reversalLineId;

	public ShippingNotificationLineId getIdNotNull() {return Check.assumeNotNull(id, "Shipment notification line is expected to be saved at this point: {}", this);}

	void markAsSaved(@NonNull final ShippingNotificationLineId id)
	{
		this.id = id;
	}

	ShippingNotificationLine createReversal()
	{
		return toBuilder().id(null).qty(qty.negate()).reversalLineId(id).build();
	}

	public Dimension getDimension()
	{
		return Dimension.builder()
				.salesOrderId(salesOrderAndLineId.getOrderId())
				.productId(productId)
				.build();
	}

}
