package de.metas.frontend_testing.masterdata.dd_order;

import de.metas.distribution.mobileui.job.model.DDOrderReference;
import de.metas.distribution.mobileui.job.model.DistributionJobId;
import de.metas.distribution.mobileui.job.service.DistributionJobLoaderSupportingServices;
import de.metas.distribution.mobileui.launchers.DDOrderReferenceCollector;
import de.metas.distribution.mobileui.launchers.DistributionLauncherCaptionProvider;
import de.metas.distribution.mobileui.launchers.facets.DistributionFacetId;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.product.ResourceId;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.model.I_DD_Order;

/**
 * Builds the {@link JsonDDOrderResponse} of a completed DD_Order; shared by {@link DDOrderCommand} and {@link DDOrderFromCandidatesCommand}.
 */
@Builder
class DDOrderResponseFactory
{
	@NonNull private final DistributionJobLoaderSupportingServices distributionJobLoaderSupportingServices;
	@NonNull private final DistributionLauncherCaptionProvider captionProvider;
	@NonNull private final MasterdataContext context;

	JsonDDOrderResponse toResponse(
			@NonNull final I_DD_Order ddOrder,
			@NonNull final WarehouseId fromWarehouseId,
			@NonNull final WarehouseId toWarehouseId,
			@NonNull final ResourceId plantId)
	{
		final DDOrderReferenceCollector collector = DDOrderReferenceCollector.builder()
				.loadingSupportServices(distributionJobLoaderSupportingServices)
				.build();
		collector.collect(ddOrder);
		final DDOrderReference ddOrderReference = CollectionUtils.singleElement(collector.getCollectedItems());

		return JsonDDOrderResponse.builder()
				.documentNo(ddOrder.getDocumentNo())
				.launcherCaption(captionProvider.compute(ddOrderReference).translate(context.getAdLanguage()))
				.launcherTestId(ddOrderReference.getTestId())
				.warehouseFromFacetId(DistributionFacetId.ofWarehouseFromId(fromWarehouseId).toWorkflowLaunchersFacetId().toJsonString())
				.warehouseToFacetId(DistributionFacetId.ofWarehouseFromId(toWarehouseId).toWorkflowLaunchersFacetId().toJsonString())
				.plantFacetId(DistributionFacetId.ofPlantId(plantId).toWorkflowLaunchersFacetId().toJsonString())
				.jobId(DistributionJobId.ofDDOrderId(ddOrderReference.getDdOrderId()))
				.build();
	}
}
