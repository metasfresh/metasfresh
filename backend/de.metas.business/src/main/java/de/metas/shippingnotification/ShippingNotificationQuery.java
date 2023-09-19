package de.metas.shippingnotification;

import com.google.common.collect.ImmutableSet;
import de.metas.document.engine.DocStatus;
import de.metas.order.OrderId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
 import java.util.Set;

@Value
@Builder
public class ShippingNotificationQuery
{
	@Nullable @Singular ImmutableSet<DocStatus> onlyDocStatuses;
	@Nullable @Singular Set<OrderId> orderIds;

	public static ShippingNotificationQuery completedOrClosedByOrderId(@NonNull final OrderId orderId)
	{
		return builder()
				.orderId(orderId)
				.onlyDocStatus(DocStatus.Completed)
				.onlyDocStatus(DocStatus.Closed)
				.build();

	}

}
