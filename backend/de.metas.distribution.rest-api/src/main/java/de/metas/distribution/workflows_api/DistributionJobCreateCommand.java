package de.metas.distribution.workflows_api;

import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.distribution.ddorder.movement.schedule.plan.DDOrderMovePlan;
import de.metas.distribution.ddorder.movement.schedule.plan.DDOrderMovePlanCreateRequest;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import org.eevolution.model.I_DD_Order;

public class DistributionJobCreateCommand
{
	private final DDOrderService ddOrderService;
	private final DDOrderMoveScheduleService ddOrderMoveScheduleService;
	private final DistributionJobLoaderSupportingServices loadingSupportServices;

	private final @NonNull DDOrderId ddOrderId;
	private final @NonNull UserId responsibleId;

	@Builder
	private DistributionJobCreateCommand(
			final @NonNull DDOrderService ddOrderService,
			final @NonNull DDOrderMoveScheduleService ddOrderMoveScheduleService,
			final @NonNull DistributionJobLoaderSupportingServices loadingSupportServices,
			//
			final @NonNull DDOrderId ddOrderId,
			final @NonNull UserId responsibleId)
	{
		this.ddOrderService = ddOrderService;
		this.ddOrderMoveScheduleService = ddOrderMoveScheduleService;
		this.loadingSupportServices = loadingSupportServices;
		//
		this.ddOrderId = ddOrderId;
		this.responsibleId = responsibleId;
	}

	public DistributionJob execute()
	{
		final I_DD_Order ddOrder = ddOrderService.getById(ddOrderId);
		ddOrderService.assignToResponsible(ddOrder, responsibleId);

		final DDOrderMovePlan plan = ddOrderMoveScheduleService.createPlan(DDOrderMovePlanCreateRequest.builder()
				.ddOrder(ddOrder)
				.failIfNotFullAllocated(true)
				.build());

		ddOrderMoveScheduleService.savePlan(plan);

		return new DistributionJobLoader(loadingSupportServices)
				.load(ddOrder);
	}

}
