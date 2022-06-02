package de.metas.device.websocket;

import de.metas.device.accessor.DeviceId;
import de.metas.util.StringUtils;
import de.metas.websocket.WebsocketTopicName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

@EqualsAndHashCode
@ToString(of = "prefix")
public class DeviceWebsocketNamingStrategy
{
	private final String prefix;

	@Getter
	private final String prefixAndSlash;

	public DeviceWebsocketNamingStrategy(@NonNull final String prefix)
	{
		this.prefix = StringUtils.trimBlankToNull(prefix);
		if (this.prefix == null)
		{
			throw new AdempiereException("Invalid prefix: " + prefix);
		}

		this.prefixAndSlash = prefix + "/";
	}

	public WebsocketTopicName toWebsocketEndpoint(@NonNull final DeviceId deviceId)
	{
		return WebsocketTopicName.ofString(prefixAndSlash + deviceId.getAsString());
	}

	public DeviceId extractDeviceId(@NonNull final WebsocketTopicName topicName)
	{
		final String topicNameString = topicName.getAsString();

		if (topicNameString.startsWith(prefixAndSlash))
		{
			return DeviceId.ofString(topicNameString.substring(prefixAndSlash.length()));
		}
		else
		{
			throw new AdempiereException("Cannot extract deviceId from topic name `" + topicName + "`");
		}
	}
}
