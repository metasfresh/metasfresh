package de.metas.shippingnotification;

import com.google.common.collect.ImmutableSet;
import de.metas.document.engine.DocStatus;
import de.metas.order.OrderId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class ShippingNotificationQuery
{
	@Nullable @Singular ImmutableSet<DocStatus> onlyDocStatuses;
	@Nullable OrderId orderId;

	public static ShippingNotificationQuery completedOrClosedByOrderId(@NonNull final OrderId orderId)
	{
		return builder()
				.orderId(orderId)
				.onlyDocStatus(DocStatus.Completed)
				.onlyDocStatus(DocStatus.Closed)
				.build();

	}

}
