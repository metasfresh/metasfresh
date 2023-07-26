package org.eevolution.api;

import java.time.LocalDateTime;
import java.util.List;

import de.metas.product.ResourceId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

public interface IPPOrderRoutingRepository extends ISingletonService
{
	PPOrderRouting getByOrderId(PPOrderId orderId);

	PPOrderRoutingActivity getOrderRoutingActivity(PPOrderRoutingActivityId orderRoutingActivityId);

	void save(PPOrderRouting orderRouting);

	void deleteByOrderId(PPOrderId orderId);

	String retrieveResourceNameForFirstNode(PPOrderId orderId);

	PPOrderRoutingActivity getFirstActivity(@NonNull PPOrderId orderId);

	void changeActivitiesScheduling(PPOrderId orderId, List<PPOrderActivityScheduleChangeRequest> changeRequests);

	List<PPOrderRoutingActivitySchedule> getActivitySchedulesByDateAndResource(LocalDateTime date, ResourceId resourceId);

}
