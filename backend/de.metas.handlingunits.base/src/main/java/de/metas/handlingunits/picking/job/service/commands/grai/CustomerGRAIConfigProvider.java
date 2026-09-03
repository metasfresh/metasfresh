package de.metas.handlingunits.picking.job.service.commands.grai;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.grai.GRAIRequired;
import de.metas.handlingunits.picking.job.service.external.bpartner.PickingJobBPartnerService;
import lombok.Builder;
import lombok.NonNull;

import java.util.HashMap;

class CustomerGRAIConfigProvider
{
	@NonNull private final PickingJobBPartnerService bpartnerService;

	private final HashMap<BPartnerId, GRAIRequired> graiRequiredByCustomerId = new HashMap<>();

	@Builder
	private CustomerGRAIConfigProvider(@NonNull final PickingJobBPartnerService bpartnerService)
	{
		this.bpartnerService = bpartnerService;
	}

	@NonNull
	public GRAIRequired getGRAIRequired(@NonNull final BPartnerId customerId)
	{
		return graiRequiredByCustomerId.computeIfAbsent(customerId, bpartnerService::getGRAIRequired);
	}

}
