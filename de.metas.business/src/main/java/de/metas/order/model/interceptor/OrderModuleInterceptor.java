package de.metas.order.model.interceptor;

import java.util.List;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import com.google.common.collect.ImmutableList;

import de.metas.elasticsearch.IESSystem;
import de.metas.event.Topic;
import de.metas.order.event.OrderUserNotifications;

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
	protected List<Topic> getAvailableUserNotificationsTopics()
	{
		return ImmutableList.of(OrderUserNotifications.EVENTBUS_TOPIC);
	}

	@Override
	protected void registerInterceptors(final IModelValidationEngine engine, final I_AD_Client client)
	{
		engine.addModelValidator(de.metas.order.model.interceptor.C_Order.INSTANCE, client); // FRESH-348
		engine.addModelValidator(de.metas.order.model.interceptor.C_OrderLine.INSTANCE, client); // FRESH-348

		//
		// Elasticsearch indexing
		final IESSystem esSystem = Services.get(IESSystem.class);
		if (esSystem.isEnabled())
		{
			esSystem.newModelIndexerConfig("orders", I_C_OrderLine.class)
					.triggerOnDocumentChanged(I_C_Order.class, I_C_OrderLine.COLUMN_C_Order_ID)
					.triggerOnDelete()
					.buildAndInstall();
		}
	};

	@Override
	protected void registerCallouts(final IProgramaticCalloutProvider calloutsRegistry)
	{
		calloutsRegistry.registerAnnotatedCallout(de.metas.order.model.interceptor.C_Order.INSTANCE); // FRESH-348
		calloutsRegistry.registerAnnotatedCallout(de.metas.order.model.interceptor.C_OrderLine.INSTANCE);
	}
}
