package de.metas.externalsystem.rabbitmq.custom.listener;

import de.metas.Profiles;
import de.metas.common.externalsystem.JsonExternalSystemMessage;
import de.metas.common.externalsystem.JsonExternalSystemMessageType;
import de.metas.externalsystem.externalservice.authorization.ExternalSystemAuthorizationService;
import de.metas.externalsystem.externalservice.authorization.SendAuthTokenProcess;
import de.metas.process.AdProcessId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.IADProcessDAO;
import de.metas.process.PInstanceId;
import de.metas.process.impl.ADProcessLoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static de.metas.common.externalsystem.ExternalSystemConstants.QUEUE_NAME_ES_TO_MF_CUSTOM;

@Component
@Profile(Profiles.PROFILE_App)
public class ExternalSystemToMFRabbitMQListener
{
	private final IADPInstanceDAO pInstanceDAO = Services.get(IADPInstanceDAO.class);
	private final IADProcessDAO processDAO = Services.get(IADProcessDAO.class);

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
			try (final IAutoCloseable ignored = Loggables.temporarySetLoggable(getProcessLoggable()))
			{
				authorizationService.postAuthorizationReply();
			}
		}
		else
		{
			throw new AdempiereException("Received JsonExternalSystemMessageType is not supported!")
					.appendParametersToMessage()
					.setParameter("JsonExternalSystemMessageType", message.getType());
		}
	}

	@NonNull
	private ADProcessLoggable getProcessLoggable()
	{
		final AdProcessId sendAuthTokenProcessID = processDAO.retrieveProcessIdByClass(SendAuthTokenProcess.class);

		final PInstanceId pInstanceId = PInstanceId.ofRepoId(pInstanceDAO.createAD_PInstance(sendAuthTokenProcessID).getAD_PInstance_ID());

		return ADProcessLoggable.builder()
				.pInstanceId(pInstanceId)
				.pInstanceDAO(pInstanceDAO)
				.build();
	}
}
