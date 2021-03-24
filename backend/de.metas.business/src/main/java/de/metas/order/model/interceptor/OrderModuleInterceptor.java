package de.metas.order.model.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.elasticsearch.IESSystem;
import de.metas.elasticsearch.config.ESModelIndexerProfile;
import de.metas.event.Topic;
import de.metas.order.compensationGroup.OrderGroupCompensationChangesHandler;
import de.metas.order.event.OrderUserNotifications;
import de.metas.order.impl.OrderLineDetailRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import java.util.List;

/**
 * @author metas-dev <dev@metasfresh.com>
 */
public class OrderModuleInterceptor extends AbstractModuleInterceptor
{
	private final OrderGroupCompensationChangesHandler groupChangesHandler = SpringContextHolder.instance.getBean(OrderGroupCompensationChangesHandler.class);
	private final OrderLineDetailRepository orderLineDetailRepository = SpringContextHolder.instance.getBean(OrderLineDetailRepository.class);

	@Override
	protected List<Topic> getAvailableUserNotificationsTopics()
	{
		return ImmutableList.of(OrderUserNotifications.USER_NOTIFICATIONS_TOPIC);
	}

	@Override
	protected void registerInterceptors(@NonNull final IModelValidationEngine engine)
	{
		engine.addModelValidator(new de.metas.order.model.interceptor.C_Order(orderLineDetailRepository)); // FRESH-348
		engine.addModelValidator(new de.metas.order.model.interceptor.C_OrderLine(groupChangesHandler, orderLineDetailRepository));

		//
		// Elasticsearch indexing
		final IESSystem esSystem = Services.get(IESSystem.class);
		if (esSystem.isEnabled())
		{
			esSystem.newModelIndexerConfig(ESModelIndexerProfile.KPI, "orders", I_C_OrderLine.class)
					.triggerOnDocumentChanged(I_C_Order.class, I_C_OrderLine.COLUMN_C_Order_ID)
					.triggerOnDelete()
					.buildAndInstall();
		}
	}
}
