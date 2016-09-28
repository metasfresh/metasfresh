package de.metas.order.model.validator;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;

import de.metas.elasticsearch.IESModelIndexingService;

/**
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class OrderModuleInterceptor extends AbstractModuleInterceptor
{
	public static final OrderModuleInterceptor INSTANCE = new OrderModuleInterceptor();

	private OrderModuleInterceptor()
	{
	};

	@Override
	protected void registerInterceptors(final IModelValidationEngine engine, final I_AD_Client client)
	{
		engine.addModelValidator(de.metas.order.model.validator.C_Order.INSTANCE, client); // FRESH-348
		engine.addModelValidator(de.metas.order.model.validator.C_OrderLine.INSTANCE, client); // FRESH-348

		//
		// Elasticsearch indexing
		final IESModelIndexingService indexingService = Services.get(IESModelIndexingService.class);

		// FIXME: temporary commented out
//		//@formatter:off
//		indexingService.newIndexerBuilder(I_C_Order.class)
//				.setIndexName("orders")
//				.setIndexType(I_C_Order.Table_Name)
//				.child(I_C_OrderLine.class)
//					.setIndexType(I_C_OrderLine.Table_Name)
//					.setChildModelsExtractor((order) -> Services.get(IOrderDAO.class).retrieveOrderLines(order, I_C_OrderLine.class))
//					.endChild()
//				.buildAndRegister();
//		//@formatter:on
	};

	@Override
	protected void registerCallouts(final IProgramaticCalloutProvider calloutsRegistry)
	{
		calloutsRegistry.registerAnnotatedCallout(de.metas.order.model.validator.C_Order.INSTANCE); // FRESH-348
	}
}
