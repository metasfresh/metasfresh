package de.metas.handlingunits.picking.job.repository;

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class DefaultPickingJobLoaderSupportingServicesFactory implements PickingJobLoaderSupportingServicesFactory
{
	@NonNull private final PickingJobSlotService pickingJobSlotService;
	@NonNull private final IBPartnerBL bpartnerBL;

	public DefaultPickingJobLoaderSupportingServicesFactory(
			final @NonNull PickingJobSlotService pickingJobSlotService,
			final @NonNull IBPartnerBL bpartnerBL)
	{
		this.pickingJobSlotService = pickingJobSlotService;
		this.bpartnerBL = bpartnerBL;
	}

	@Override
	public PickingJobLoaderSupportingServices createLoaderSupportingServices()
	{
		return new DefaultPickingJobLoaderSupportingServices(bpartnerBL, pickingJobSlotService);
	}
}
