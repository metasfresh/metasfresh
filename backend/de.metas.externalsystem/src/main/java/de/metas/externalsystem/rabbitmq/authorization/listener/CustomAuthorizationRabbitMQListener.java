package de.metas.externalsystem.rabbitmq.authorization.listener;

import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemMessage;
import de.metas.externalsystem.externalservice.authorization.ExternalSystemAuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.metas.common.externalsystem.ExternalSystemConstants.QUEUE_NAME_ES_TO_MF_AUTH;

@Component
public class CustomAuthorizationRabbitMQListener
{
	private static final Logger logger = LoggerFactory.getLogger(CustomAuthorizationRabbitMQListener.class);

	@Autowired
	private ExternalSystemAuthorizationService authorizationService;

	@RabbitListener(queues = QUEUE_NAME_ES_TO_MF_AUTH)
	public void onRequestAuthorizationEvent(final JsonExternalSystemMessage message)
	{
		if (message.getType().equals(ExternalSystemConstants.TO_MF_AUTHORIZATION_REQUEST_MESSAGE_TYPE))
		{
			authorizationService.postAuthorizationReply();
		}
	}

}
