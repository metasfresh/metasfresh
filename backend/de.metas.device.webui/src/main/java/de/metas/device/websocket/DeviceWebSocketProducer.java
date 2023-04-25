package de.metas.device.websocket;

import com.google.common.collect.ImmutableList;
import de.metas.device.accessor.DeviceAccessor;
import de.metas.device.accessor.DeviceId;
import de.metas.device.accessor.DeviceAccessorsHubFactory;
import de.metas.websocket.producers.WebSocketProducer;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.math.BigDecimal;
import java.util.List;

@ToString
final class DeviceWebSocketProducer implements WebSocketProducer
{
	private final DeviceAccessorsHubFactory deviceAccessorsHubFactory;
	private final DeviceId deviceId;

	public DeviceWebSocketProducer(
			@NonNull final DeviceAccessorsHubFactory deviceAccessorsHubFactory,
			@NonNull final DeviceId deviceId)
	{
		this.deviceAccessorsHubFactory = deviceAccessorsHubFactory;
		this.deviceId = deviceId;
	}

	@Override
	public List<JSONDeviceValueChangedEvent> produceEvents()
	{
		final DeviceAccessor deviceAccessor = deviceAccessorsHubFactory.getDeviceAccessorById(deviceId)
				.orElseThrow(() -> new AdempiereException("Device accessor no longer exists for `" + deviceId + "`"));

		final BigDecimal valueBD = deviceAccessor.acquireValue();
		final Object valueJson = valueBD != null ? valueBD.toPlainString() : null;

		return ImmutableList.of(JSONDeviceValueChangedEvent.of(deviceId, valueJson));
	}
}
