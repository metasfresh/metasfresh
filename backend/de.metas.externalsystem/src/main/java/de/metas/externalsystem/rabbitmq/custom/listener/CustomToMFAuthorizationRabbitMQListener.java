package de.metas.externalsystem.rabbitmq.custom.listener;

import de.metas.common.externalsystem.JsonExternalSystemMessage;
import de.metas.common.externalsystem.JsonExternalSystemMessageTypeEnum;
import de.metas.externalsystem.externalservice.authorization.ExternalSystemAuthorizationService;
import lombok.NonNull;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static de.metas.common.externalsystem.ExternalSystemConstants.QUEUE_NAME_ES_TO_MF_CUSTOM;

@Component
public class CustomToMFAuthorizationRabbitMQListener
{
	private final ExternalSystemAuthorizationService authorizationService;

	public CustomToMFAuthorizationRabbitMQListener(
			final ExternalSystemAuthorizationService authorizationService
	)
	{
		this.authorizationService = authorizationService;
	}

	@RabbitListener(queues = QUEUE_NAME_ES_TO_MF_CUSTOM)
	public void onMessage(@NonNull final JsonExternalSystemMessage message)
	{
		if (message.getType().equals(JsonExternalSystemMessageTypeEnum.REQUEST_AUTHORIZATION))
		{
			authorizationService.postAuthorizationReply();
		}
	}

}
