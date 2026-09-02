package de.metas.order.model.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.event.Topic;
import de.metas.order.event.OrderUserNotifications;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;

import java.util.List;

/**
 * @author metas-dev <dev@metasfresh.com>
 */
public class OrderModuleInterceptor extends AbstractModuleInterceptor
{
	@Override
	protected List<Topic> getAvailableUserNotificationsTopics()
	{
		return ImmutableList.of(OrderUserNotifications.USER_NOTIFICATIONS_TOPIC);
	}

}
