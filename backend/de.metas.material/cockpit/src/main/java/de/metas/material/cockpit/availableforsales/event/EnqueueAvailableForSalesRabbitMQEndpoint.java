package de.metas.material.cockpit.availableforsales.event;

import de.metas.Profiles;
import de.metas.event.Event;
import de.metas.event.IEventBusFactory;
import de.metas.event.Type;
import de.metas.event.impl.EventBusMonitoringService;
import de.metas.event.remote.rabbitmq.RabbitMQReceiveFromEndpointTemplate;
import lombok.NonNull;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
@Profile(Profiles.PROFILE_App) // IMPORTANT: make sure events are consumed only in the app server 
public class EnqueueAvailableForSalesRabbitMQEndpoint extends RabbitMQReceiveFromEndpointTemplate
{
	public EnqueueAvailableForSalesRabbitMQEndpoint(
			@NonNull final IEventBusFactory eventBusFactory,
			@NonNull final EventBusMonitoringService eventBusMonitoringService)
	{
		super(eventBusFactory, eventBusMonitoringService);
	}

	@RabbitListener(queues = { EnqueueAvailableForSalesConfiguration.QUEUE_NAME_SPEL })
	public void onAccountingEvent(
			@Payload final Event event,
			@Header(HEADER_SenderId) final String senderId,
			@Header(HEADER_TopicName) final String topicName,
			@Header(HEADER_TopicType) final Type topicType)
	{
		onRemoteEvent(event, senderId, topicName, topicType);
	}
}
