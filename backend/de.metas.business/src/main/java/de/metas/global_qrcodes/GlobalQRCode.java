package de.metas.global_qrcodes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import de.metas.JsonObjectMapperHolder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

@Value
@Builder
public class GlobalQRCode
{
	@NonNull GlobalQRCodeType type;

	@Builder.Default
	@NonNull GlobalQRCodeVersion version = GlobalQRCodeVersion.ofInt(1);

	@NonNull String payloadAsJson;

	public static final String SEPARATOR = "#";

	public static GlobalQRCode of(
			@NonNull final GlobalQRCodeType type,
			@NonNull final GlobalQRCodeVersion version,
			@NonNull final Object payload)
	{
		final String payloadAsJson;
		try
		{
			payloadAsJson = JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(payload);
		}
		catch (JsonProcessingException ex)
		{
			throw new AdempiereException("Failed converting payload to json", ex)
					.appendParametersToMessage()
					.setParameter("payload", payload);
		}

		return builder()
				.type(type)
				.version(version)
				.payloadAsJson(payloadAsJson)
				.build();
	}

	@JsonCreator
	public static GlobalQRCode ofString(@NonNull final String string)
	{
		String remainingString = string;

		//
		// Extract type
		final GlobalQRCodeType type;
		{
			int idx = remainingString.indexOf(SEPARATOR);
			if (idx <= 0)
			{
				throw new AdempiereException("Invalid global QR code(1): " + string);
			}
			type = GlobalQRCodeType.ofString(remainingString.substring(0, idx));
			remainingString = remainingString.substring(idx + 1);
		}

		//
		// Extract version
		final GlobalQRCodeVersion version;
		{
			int idx = remainingString.indexOf("#");
			if (idx <= 0)
			{
				throw new AdempiereException("Invalid global QR code(2): " + string);
			}
			version = GlobalQRCodeVersion.ofString(remainingString.substring(0, idx));
			remainingString = remainingString.substring(idx + 1);
		}

		//
		// Payload
		final String payloadAsJson = remainingString;

		//
		return builder()
				.type(type)
				.version(version)
				.payloadAsJson(payloadAsJson)
				.build();
	}

	public <T> T getPayloadAs(@NonNull final Class<T> type)
	{
		try
		{
			return JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(payloadAsJson, type);
		}
		catch (JsonProcessingException e)
		{
			throw new AdempiereException("Failed converting payload to " + type, e)
					.appendParametersToMessage()
					.setParameter("payloadAsJson", payloadAsJson);
		}
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	@JsonValue
	public String getAsString()
	{
		return type.toJson() + "#" + version + "#" + payloadAsJson;
	}
}
