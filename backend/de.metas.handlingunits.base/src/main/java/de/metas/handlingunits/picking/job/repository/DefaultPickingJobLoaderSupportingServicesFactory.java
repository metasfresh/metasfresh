package de.metas.handlingunits.picking.job.repository;

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileRepository;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class DefaultPickingJobLoaderSupportingServicesFactory implements PickingJobLoaderSupportingServicesFactory
{
	@NonNull private final PickingJobSlotService pickingJobSlotService;
	@NonNull private final IBPartnerBL bpartnerBL;
	@NonNull private final HUQRCodesService huQRCodeService;
	@NonNull private final MobileUIPickingUserProfileRepository mobileUIPickingUserProfileRepository;

	@Override
	public PickingJobLoaderSupportingServices createLoaderSupportingServices()
	{
		return new DefaultPickingJobLoaderSupportingServices(bpartnerBL, pickingJobSlotService, huQRCodeService, mobileUIPickingUserProfileRepository);
	}
}
