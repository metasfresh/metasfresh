package de.metas.device.websocket;

import com.google.common.collect.ImmutableList;
import de.metas.device.adempiere.AttributeDeviceAccessor;
import de.metas.device.adempiere.IDevicesHubFactory;
import de.metas.util.Check;
import de.metas.websocket.producers.WebSocketProducer;
import lombok.NonNull;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@ToString
final class DeviceWebSocketProducer implements WebSocketProducer
{
	private final IDevicesHubFactory devicesHubFactory;
	private final String deviceId;

	public DeviceWebSocketProducer(
			@NonNull final IDevicesHubFactory devicesHubFactory,
			@NonNull final String deviceId)
	{
		Check.assumeNotEmpty(deviceId, "deviceId is not empty");

		this.devicesHubFactory = devicesHubFactory;
		this.deviceId = deviceId;
	}

	@Override
	public List<JSONDeviceValueChangedEvent> produceEvents()
	{
		final AttributeDeviceAccessor deviceAccessor = devicesHubFactory
				.getDefaultAttributesDevicesHub()
				.getAttributeDeviceAccessorById(deviceId);
		if (deviceAccessor == null)
		{
			throw new RuntimeException("Device accessor no longer exists for: " + deviceId);
		}

		final BigDecimal valueBD = deviceAccessor.acquireValue();
		final Object valueJson = valueBD != null ? valueBD.toPlainString() : null;

		return ImmutableList.of(JSONDeviceValueChangedEvent.of(deviceId, valueJson));
	}
}
