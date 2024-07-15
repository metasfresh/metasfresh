package de.metas.handlingunits.picking.job.repository;

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.workplace.WorkplaceService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class DefaultPickingJobLoaderSupportingServicesFactory implements PickingJobLoaderSupportingServicesFactory
{
	@NonNull private final PickingJobSlotService pickingJobSlotService;
	@NonNull private final IBPartnerBL bpartnerBL;
	@NonNull private final HUQRCodesService huQRCodeService;
	@NonNull private final WorkplaceService workplaceService;

	public DefaultPickingJobLoaderSupportingServicesFactory(
			final @NonNull PickingJobSlotService pickingJobSlotService,
			final @NonNull IBPartnerBL bpartnerBL,
			final @NonNull HUQRCodesService huQRCodeService,
			final @NonNull WorkplaceService workplaceService)
	{
		this.pickingJobSlotService = pickingJobSlotService;
		this.bpartnerBL = bpartnerBL;
		this.huQRCodeService = huQRCodeService;
		this.workplaceService = workplaceService;
	}

	@Override
	public PickingJobLoaderSupportingServices createLoaderSupportingServices()
	{
		return new DefaultPickingJobLoaderSupportingServices(bpartnerBL, pickingJobSlotService, huQRCodeService, workplaceService);
	}
}
