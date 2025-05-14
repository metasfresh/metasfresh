package de.metas.distribution.ddorder.lowlevel.interceptor;

import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelService;
import de.metas.distribution.ddorder.lowlevel.callout.DD_Order;
import de.metas.distribution.ddorder.lowlevel.callout.DD_OrderLine;
import de.metas.distribution.ddordercandidate.DDOrderCandidateAllocRepository;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import de.metas.material.replenish.ReplenishInfoRepository;
import lombok.NonNull;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;

public class DDOrderLowLevelInterceptors extends AbstractModuleInterceptor
{
	private final DDOrderLowLevelService ddOrderLowLevelService;
	private final IDocumentNoBuilderFactory documentNoBuilderFactory;
	private final DDOrderCandidateAllocRepository ddOrderCandidateAllocRepository;
	private final DistributionNetworkRepository distributionNetworkRepository;
	private final ReplenishInfoRepository replenishInfoRepository;
	private final PostMaterialEventService materialEventService;

	public DDOrderLowLevelInterceptors(
			@NonNull final DDOrderLowLevelService ddOrderLowLevelService,
			@NonNull final IDocumentNoBuilderFactory documentNoBuilderFactory,
			@NonNull final DDOrderCandidateAllocRepository ddOrderCandidateAllocRepository,
			@NonNull final DistributionNetworkRepository distributionNetworkRepository,
			@NonNull final ReplenishInfoRepository replenishInfoRepository,
			@NonNull final PostMaterialEventService materialEventService)
	{
		this.ddOrderLowLevelService = ddOrderLowLevelService;
		this.documentNoBuilderFactory = documentNoBuilderFactory;
		this.ddOrderCandidateAllocRepository = ddOrderCandidateAllocRepository;
		this.distributionNetworkRepository = distributionNetworkRepository;
		this.replenishInfoRepository = replenishInfoRepository;
		this.materialEventService = materialEventService;
	}

	@Override
	protected void registerCallouts(final IProgramaticCalloutProvider calloutsRegistry)
	{
		calloutsRegistry.registerAnnotatedCallout(new DD_Order(documentNoBuilderFactory));
		calloutsRegistry.registerAnnotatedCallout(new DD_OrderLine(ddOrderLowLevelService));
	}

	@Override
	protected void registerInterceptors(final IModelValidationEngine engine)
	{
		engine.addModelValidator(new de.metas.distribution.ddorder.lowlevel.interceptor.DD_Order(ddOrderLowLevelService));
		//engine.addModelValidator(new org.eevolution.model.validator.DD_OrderFireMaterialEvent()); // gh #523 - not needed, spring component
		engine.addModelValidator(new de.metas.distribution.ddorder.lowlevel.interceptor.DD_OrderLine(ddOrderLowLevelService, ddOrderCandidateAllocRepository));
		engine.addModelValidator(new DD_OrderLine_PostMaterialEvent(distributionNetworkRepository,
																	ddOrderLowLevelService,
																	replenishInfoRepository,
																	materialEventService));
	}
}
