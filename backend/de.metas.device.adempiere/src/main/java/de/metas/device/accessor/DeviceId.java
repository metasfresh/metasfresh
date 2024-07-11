package de.metas.device.accessor;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

@EqualsAndHashCode
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public final class DeviceId
{
	private final String value;

	private DeviceId(@NonNull final String value)
	{
		this.value = StringUtils.trimBlankToNull(value);
		if (this.value == null)
		{
			throw new AdempiereException("Invalid deviceId: " + value);
		}
	}

	@JsonCreator
	public static DeviceId ofString(@NonNull final String value)
	{
		return new DeviceId(value);
	}

	@Nullable
	public static DeviceId ofNullableString(@Nullable final String value)
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		return valueNorm != null ? new DeviceId(valueNorm) : null;
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
		return value;
	}

	public static boolean equals(@Nullable final DeviceId deviceId1, @Nullable final DeviceId deviceId2) {return Objects.equals(deviceId1, deviceId2);}
}
