package de.metas.handlingunits.picking.job.repository;

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import lombok.NonNull;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class DefaultPickingJobLoaderSupportingServicesFactory implements PickingJobLoaderSupportingServicesFactory
{
	@NonNull private final PickingJobSlotService pickingJobSlotService;
	@NonNull private final IBPartnerBL bpartnerBL;
	@NonNull private final HUQRCodesService huQRCodeService;

	public DefaultPickingJobLoaderSupportingServicesFactory(
			final @NonNull PickingJobSlotService pickingJobSlotService,
			final @NonNull IBPartnerBL bpartnerBL,
			final @NonNull HUQRCodesService huQRCodeService)
	{
		this.pickingJobSlotService = pickingJobSlotService;
		this.bpartnerBL = bpartnerBL;
		this.huQRCodeService = huQRCodeService;
	}

	@Override
	public PickingJobLoaderSupportingServices createLoaderSupportingServices()
	{
		return new DefaultPickingJobLoaderSupportingServices(bpartnerBL, pickingJobSlotService, huQRCodeService);
	}
}
