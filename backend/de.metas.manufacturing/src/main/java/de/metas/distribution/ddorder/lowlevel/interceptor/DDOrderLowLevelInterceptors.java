package de.metas.distribution.ddorder.lowlevel.interceptor;

import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelService;
import de.metas.distribution.ddorder.lowlevel.callout.DD_Order;
import de.metas.distribution.ddorder.lowlevel.callout.DD_OrderLine;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import lombok.NonNull;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;

public class DDOrderLowLevelInterceptors extends AbstractModuleInterceptor
{
	private final DDOrderLowLevelService ddOrderLowLevelService;
	private final IDocumentNoBuilderFactory documentNoBuilderFactory;

	public DDOrderLowLevelInterceptors(
			@NonNull final DDOrderLowLevelService ddOrderLowLevelService,
			@NonNull final IDocumentNoBuilderFactory documentNoBuilderFactory)
	{
		this.ddOrderLowLevelService = ddOrderLowLevelService;
		this.documentNoBuilderFactory = documentNoBuilderFactory;
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
		engine.addModelValidator(new de.metas.distribution.ddorder.lowlevel.interceptor.DD_OrderLine(ddOrderLowLevelService));
	}
}
