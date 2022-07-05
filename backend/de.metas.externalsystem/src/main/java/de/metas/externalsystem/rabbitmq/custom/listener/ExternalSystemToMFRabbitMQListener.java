package de.metas.externalsystem.rabbitmq.custom.listener;

import de.metas.Profiles;
import de.metas.common.externalsystem.JsonExternalSystemMessage;
import de.metas.common.externalsystem.JsonExternalSystemMessageType;
import de.metas.externalsystem.externalservice.authorization.ExternalSystemAuthorizationService;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static de.metas.common.externalsystem.ExternalSystemConstants.QUEUE_NAME_ES_TO_MF_CUSTOM;

@Component
@Profile(Profiles.PROFILE_App)
public class ExternalSystemToMFRabbitMQListener
{
	private final ExternalSystemAuthorizationService authorizationService;

	public ExternalSystemToMFRabbitMQListener(
			final ExternalSystemAuthorizationService authorizationService
	)
	{
		this.authorizationService = authorizationService;
	}

	@RabbitListener(queues = QUEUE_NAME_ES_TO_MF_CUSTOM)
	public void onMessage(@NonNull final JsonExternalSystemMessage message)
	{
		if (message.getType().equals(JsonExternalSystemMessageType.REQUEST_AUTHORIZATION))
		{
			authorizationService.postAuthorizationReply();
		}
		else
		{
			throw new AdempiereException("Received JsonExternalSystemMessageType is not supported!")
					.appendParametersToMessage()
					.setParameter("JsonExternalSystemMessageType", message.getType());
		}
	}

}
