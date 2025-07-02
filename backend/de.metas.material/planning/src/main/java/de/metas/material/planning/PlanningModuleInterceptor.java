package de.metas.material.planning;

import com.google.common.collect.ImmutableList;
import de.metas.event.Topic;
import de.metas.material.planning.event.SupplyRequiredDecreasedNotificationProducer;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlanningModuleInterceptor extends AbstractModuleInterceptor
{

	@Override
	protected List<Topic> getAvailableUserNotificationsTopics()
	{
		return ImmutableList.of(SupplyRequiredDecreasedNotificationProducer.EVENTBUS_TOPIC);
	}

}
