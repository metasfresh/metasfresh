package de.metas.manufacturing.job.model;

import de.metas.device.accessor.DeviceId;
import de.metas.i18n.ITranslatableString;
import de.metas.websocket.WebsocketTopicName;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ScaleDevice
{
	@NonNull DeviceId deviceId;
	@NonNull ITranslatableString caption;
	@NonNull WebsocketTopicName websocketEndpoint;
}
