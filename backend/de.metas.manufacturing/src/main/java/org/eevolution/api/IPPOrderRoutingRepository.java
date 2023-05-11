package org.eevolution.api;

import de.metas.util.ISingletonService;
import lombok.NonNull;

import java.util.List;

public interface IPPOrderRoutingRepository extends ISingletonService
{
	PPOrderRouting getByOrderId(PPOrderId orderId);

	PPOrderRoutingActivity getOrderRoutingActivity(PPOrderRoutingActivityId orderRoutingActivityId);

	void save(PPOrderRouting orderRouting);

	void deleteByOrderId(PPOrderId orderId);

	PPOrderRoutingActivity getFirstActivity(@NonNull PPOrderId orderId);

	void changeActivitiesScheduling(PPOrderId orderId, List<PPOrderActivityScheduleChangeRequest> changeRequests);
}
