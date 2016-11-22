package de.metas.order.model.interceptor;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import de.metas.elasticsearch.IESModelIndexingService;

/**
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class OrderModuleInterceptor extends AbstractModuleInterceptor
{
	public static final OrderModuleInterceptor INSTANCE= new OrderModuleInterceptor();

	private OrderModuleInterceptor()
	{
	};

	@Override
	protected void registerInterceptors(final IModelValidationEngine engine, final I_AD_Client client)
	{
		engine.addModelValidator(de.metas.order.model.interceptor.C_Order.INSTANCE, client); // FRESH-348
		engine.addModelValidator(de.metas.order.model.interceptor.C_OrderLine.INSTANCE, client); // FRESH-348

		//
		// Elasticsearch indexing
		final boolean elasticSearchEnabled = !Adempiere.isUnitTestMode(); // FIXME: refactor
		if (elasticSearchEnabled)
		{
			final IESModelIndexingService indexingService = Services.get(IESModelIndexingService.class);

			indexingService.newModelIndexerBuilder("orders", I_C_OrderLine.class)
					.triggerOnDocumentChanged(I_C_Order.class, I_C_OrderLine.COLUMN_C_Order_ID)
					.triggerOnDelete()
					.buildAndRegister();
		}
	};

	@Override
	protected void registerCallouts(final IProgramaticCalloutProvider calloutsRegistry)
	{
		calloutsRegistry.registerAnnotatedCallout(de.metas.order.model.interceptor.C_Order.INSTANCE); // FRESH-348
	}
}
