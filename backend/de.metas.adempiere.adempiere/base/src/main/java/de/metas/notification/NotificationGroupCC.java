package de.metas.notification;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

@Value
@Builder
public class NotificationGroupCC
{
	@NonNull Recipient recipient;
	@NonNull ClientId clientId;
}
