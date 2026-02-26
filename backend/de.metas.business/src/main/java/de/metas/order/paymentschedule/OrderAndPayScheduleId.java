package de.metas.order.paymentschedule;

import de.metas.order.OrderId;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value(staticConstructor = "of")
public class OrderAndPayScheduleId
{
	@NonNull OrderId orderId;
	@NonNull OrderPayScheduleId orderPayScheduleId;

	@Nullable
	public static OrderAndPayScheduleId ofRepoIdsOrNull(final int orderRepoId, final int orderPayScheduleRepoId)
	{
		final OrderId orderId = OrderId.ofRepoIdOrNull(orderRepoId);
		if (orderId == null) {return null;}

		final OrderPayScheduleId orderPayScheduleId = OrderPayScheduleId.ofRepoIdOrNull(orderPayScheduleRepoId);
		if (orderPayScheduleId == null) {return null;}

		return of(orderId, orderPayScheduleId);
	}
}
