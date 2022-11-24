package de.metas.distribution.workflows_api;

import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.distribution.ddorder.movement.schedule.plan.DDOrderMovePlan;
import de.metas.distribution.ddorder.movement.schedule.plan.DDOrderMovePlanCreateRequest;
import de.metas.logging.LogManager;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import de.metas.distribution.ddorder.DDOrderId;
import org.eevolution.model.I_DD_Order;
import org.slf4j.Logger;

public class DistributionJobCreateCommand
{
	private static final Logger logger = LogManager.getLogger(DistributionJobCreateCommand.class);
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
		setResponsible(ddOrder);

		final DDOrderMovePlan plan = ddOrderMoveScheduleService.createPlan(DDOrderMovePlanCreateRequest.builder()
				.ddOrder(ddOrder)
				.failIfNotFullAllocated(true)
				.build());

		ddOrderMoveScheduleService.savePlan(plan);

		return new DistributionJobLoader(loadingSupportServices)
				.load(ddOrder);
	}

	private void setResponsible(final I_DD_Order ddOrder)
	{
		final UserId currentResponsibleId = UserId.ofRepoIdOrNullIfSystem(ddOrder.getAD_User_Responsible_ID());
		if (currentResponsibleId == null)
		{
			ddOrder.setAD_User_Responsible_ID(responsibleId.getRepoId());
			ddOrderService.save(ddOrder);
		}
		else if (!UserId.equals(currentResponsibleId, responsibleId))
		{
			throw new AdempiereException("DD Order already assigned to a different responsible");
		}
		else
		{
			// already assigned to that responsible,
			// shall not happen but we can safely ignore the case
			logger.warn("Order {} already assigned to {}", ddOrderId, responsibleId);
		}
	}

}
