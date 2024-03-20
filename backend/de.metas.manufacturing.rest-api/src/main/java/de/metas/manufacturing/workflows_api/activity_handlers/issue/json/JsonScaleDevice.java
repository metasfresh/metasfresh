package de.metas.manufacturing.workflows_api.activity_handlers.issue.json;

import de.metas.common.rest_api.v2.JsonQuantity;
import de.metas.device.accessor.DeviceId;
import de.metas.manufacturing.job.model.ScaleDevice;
import de.metas.websocket.WebsocketTopicName;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Optional;

@Value
@Builder
@Jacksonized
public class JsonScaleDevice
{
	@NonNull DeviceId deviceId;
	@NonNull String caption;
	@NonNull WebsocketTopicName websocketEndpoint;
	@Nullable JsonQuantity roundingToScale;

	public static JsonScaleDevice of(final ScaleDevice scaleDevice, final String adLanguage)
	{
		return builder()
				.deviceId(scaleDevice.getDeviceId())
				.caption(scaleDevice.getCaption().translate(adLanguage))
				.websocketEndpoint(scaleDevice.getWebsocketEndpoint())
				.roundingToScale(Optional.ofNullable(scaleDevice.getRoundingToScale())
										 .map(qty -> JsonQuantity.builder()
												 .uomSymbol(qty.getUOMSymbol())
												 .uomCode(qty.getX12DE355().getCode())
												 .qty(qty.toBigDecimal())
												 .build())
										 .orElse(null))
				.build();
	}

}
