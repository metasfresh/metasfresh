package de.metas.common.handlingunits;

import de.metas.common.util.CoalesceUtil;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
public class JsonHUAttribute
{
	@NonNull String code;
	@NonNull String caption;
	@Nullable Object value;
	@Nullable Object valueDisplay;

	@Builder
	@Jacksonized
	private JsonHUAttribute(
			@NonNull final String code,
			@NonNull final String caption,
			@Nullable final Object value,
			@Nullable final Object valueDisplay)
	{
		this.code = code;
		this.caption = caption;
		this.value = convertValueToJson(value);
		this.valueDisplay = valueDisplay != null ? valueDisplay : CoalesceUtil.coalesceNotNull(this.value, "");
	}

	@Nullable
	public static Object convertValueToJson(final Object value)
	{
		if (value == null)
		{
			return null;
		}
		else if (value instanceof String)
		{
			return value;
		}
		else if (value instanceof Integer)
		{
			return value;
		}
		else if (value instanceof BigDecimal)
		{
			return value.toString();
		}
		else if (value instanceof Boolean)
		{
			return value;
		}
		else if (value instanceof java.sql.Timestamp)
		{
			final LocalDateTime localDateTime = ((Timestamp)value).toLocalDateTime();
			final LocalDate localDate = localDateTime.toLocalDate();
			if (localDateTime.equals(localDate.atStartOfDay()))
			{
				return localDate.toString();
			}
			else
			{
				return localDateTime.toLocalTime();
			}
		}
		else
		{
			return value.toString();
		}
	}

}
