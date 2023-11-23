package de.metas.manufacturing.job.model;

import de.metas.device.accessor.DeviceId;
import de.metas.i18n.ITranslatableString;
import de.metas.quantity.Quantity;
import de.metas.websocket.WebsocketTopicName;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class ScaleDevice
{
	@NonNull DeviceId deviceId;
	@NonNull ITranslatableString caption;
	@NonNull WebsocketTopicName websocketEndpoint;
	@Nullable Quantity roundingToScale;
}
