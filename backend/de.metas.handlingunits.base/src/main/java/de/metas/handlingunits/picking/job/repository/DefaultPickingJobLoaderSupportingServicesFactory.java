package de.metas.handlingunits.picking.job.repository;

import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileService;
import de.metas.handlingunits.picking.job.service.PickingJobLockService;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import de.metas.handlingunits.picking.job.service.external.bpartner.PickingJobBPartnerService;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.handlingunits.picking.job.service.external.product.PickingJobProductService;
import de.metas.handlingunits.picking.job.service.external.salesorder.PickingJobSalesOrderService;
import de.metas.handlingunits.picking.job.service.external.warehouse.PickingJobWarehouseService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultPickingJobLoaderSupportingServicesFactory implements PickingJobLoaderSupportingServicesFactory
{
	@NonNull private final MobileUIPickingUserProfileService profileService;
	@NonNull private final PickingJobSalesOrderService orderService;
	@NonNull private final PickingJobWarehouseService warehouseService;
	@NonNull private final PickingJobBPartnerService bpartnerService;
	@NonNull private final PickingJobProductService productService;
	@NonNull private final PickingJobSlotService pickingSlotService;
	@NonNull private final PickingJobLockService pickingJobLockService;
	@NonNull private final PickingJobHUService huService;

	@Override
	public PickingJobLoaderSupportingServices createLoaderSupportingServices()
	{
		return DefaultPickingJobLoaderSupportingServices.builder()
				.orderService(orderService)
				.warehouseService(warehouseService)
				.bpartnerService(bpartnerService)
				.productService(productService)
				.pickingSlotService(pickingSlotService)
				.pickingJobLockService(pickingJobLockService)
				.huService(huService)
				.profileService(profileService)
				.build();
	}
}
