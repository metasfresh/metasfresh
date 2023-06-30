package org.eevolution.api;

import de.metas.product.ResourceId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

import java.time.Instant;
import java.util.List;

public interface IPPOrderRoutingRepository extends ISingletonService
{
	PPOrderRouting getByOrderId(PPOrderId orderId);

	PPOrderRoutingActivity getOrderRoutingActivity(PPOrderRoutingActivityId orderRoutingActivityId);

	void save(PPOrderRouting orderRouting);

	void deleteByOrderId(PPOrderId orderId);

	String retrieveResourceNameForFirstNode(PPOrderId orderId);

	PPOrderRoutingActivity getFirstActivity(@NonNull PPOrderId orderId);

	void changeActivitiesScheduling(PPOrderId orderId, List<PPOrderActivityScheduleChangeRequest> changeRequests);

	List<PPOrderRoutingActivitySchedule> getActivitySchedulesByDateAndResource(Instant date, ResourceId resourceId);

}
