package de.metas.distribution.workflows_api;

import de.metas.handlingunits.ddorder.IHUDDOrderBL;
import de.metas.handlingunits.ddorder.picking.DDOrderPickFromService;
import de.metas.handlingunits.ddorder.picking.DDOrderPickPlan;
import de.metas.handlingunits.ddorder.picking.PickingPlanCreateRequest;
import de.metas.logging.LogManager;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.DDOrderId;
import org.eevolution.model.I_DD_Order;
import org.slf4j.Logger;

public class DistributionJobCreateCommand
{
	private static final Logger logger = LogManager.getLogger(DistributionJobCreateCommand.class);
	private final IHUDDOrderBL ddOrderBL;
	private final DDOrderPickFromService ddOrderPickFromService;
	private final DistributionJobLoaderSupportingServices loadingSupportServices;

	private final @NonNull DDOrderId ddOrderId;
	private final @NonNull UserId responsibleId;

	@Builder
	private DistributionJobCreateCommand(
			final @NonNull IHUDDOrderBL ddOrderBL,
			final @NonNull DDOrderPickFromService ddOrderPickFromService,
			final @NonNull DistributionJobLoaderSupportingServices loadingSupportServices,
			//
			final @NonNull DDOrderId ddOrderId,
			final @NonNull UserId responsibleId)
	{
		this.ddOrderBL = ddOrderBL;
		this.ddOrderPickFromService = ddOrderPickFromService;
		this.loadingSupportServices = loadingSupportServices;
		//
		this.ddOrderId = ddOrderId;
		this.responsibleId = responsibleId;
	}

	public DistributionJob execute()
	{
		final I_DD_Order ddOrder = ddOrderBL.getById(ddOrderId);
		setResponsible(ddOrder);

		final DDOrderPickPlan plan = ddOrderPickFromService.createPlan(PickingPlanCreateRequest.builder()
				.ddOrder(ddOrder)
				.failIfNotFullAllocated(true)
				.build());

		ddOrderPickFromService.savePlan(plan);

		return new DistributionJobLoader(loadingSupportServices)
				.load(ddOrder);
	}

	private void setResponsible(final I_DD_Order ddOrder)
	{
		final UserId currentResponsibleId = UserId.ofRepoIdOrNullIfSystem(ddOrder.getAD_User_Responsible_ID());
		if (currentResponsibleId == null)
		{
			ddOrder.setAD_User_Responsible_ID(responsibleId.getRepoId());
			ddOrderBL.save(ddOrder);
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
