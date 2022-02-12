package de.metas.device.websocket;

import com.google.common.collect.ImmutableList;
import de.metas.device.adempiere.AttributeDeviceAccessor;
import de.metas.device.adempiere.DeviceId;
import de.metas.device.adempiere.IDevicesHubFactory;
import de.metas.websocket.producers.WebSocketProducer;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.math.BigDecimal;
import java.util.List;

@ToString
final class DeviceWebSocketProducer implements WebSocketProducer
{
	private final IDevicesHubFactory devicesHubFactory;
	private final DeviceId deviceId;

	public DeviceWebSocketProducer(
			@NonNull final IDevicesHubFactory devicesHubFactory,
			@NonNull final DeviceId deviceId)
	{
		this.devicesHubFactory = devicesHubFactory;
		this.deviceId = deviceId;
	}

	@Override
	public List<JSONDeviceValueChangedEvent> produceEvents()
	{
		final AttributeDeviceAccessor deviceAccessor = devicesHubFactory.getAttributeDeviceAccessorById(deviceId);
		if (deviceAccessor == null)
		{
			throw new AdempiereException("Device accessor no longer exists for `" + deviceId+"`");
		}

		final BigDecimal valueBD = deviceAccessor.acquireValue();
		final Object valueJson = valueBD != null ? valueBD.toPlainString() : null;

		return ImmutableList.of(JSONDeviceValueChangedEvent.of(deviceId, valueJson));
	}
}
