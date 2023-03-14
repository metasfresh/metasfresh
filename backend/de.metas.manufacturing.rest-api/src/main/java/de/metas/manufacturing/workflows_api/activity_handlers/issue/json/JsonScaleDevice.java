package de.metas.manufacturing.workflows_api.activity_handlers.issue.json;

import de.metas.device.accessor.DeviceId;
import de.metas.manufacturing.job.model.ScaleDevice;
import de.metas.websocket.WebsocketTopicName;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonScaleDevice
{
	@NonNull DeviceId deviceId;
	@NonNull String caption;
	@NonNull WebsocketTopicName websocketEndpoint;

	public static JsonScaleDevice of(final ScaleDevice scaleDevice, final String adLanguage)
	{
		return builder()
				.deviceId(scaleDevice.getDeviceId())
				.caption(scaleDevice.getCaption().translate(adLanguage))
				.websocketEndpoint(scaleDevice.getWebsocketEndpoint())
				.build();
	}

}
