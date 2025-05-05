package de.metas.shippingnotification;

import com.google.common.collect.ImmutableSet;
import de.metas.document.engine.DocStatus;
import de.metas.order.OrderId;
import de.metas.util.Check;
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
		return completedOrClosedByOrderIds(ImmutableSet.of(orderId));
	}

	public static ShippingNotificationQuery completedOrClosedByOrderIds(@NonNull final Set<OrderId> orderIds)
	{
		Check.assumeNotEmpty(orderIds, "orderIds not empty");
		return builder()
				.orderIds(orderIds)
				.onlyDocStatus(DocStatus.Completed)
				.onlyDocStatus(DocStatus.Closed)
				.build();
	}

}
