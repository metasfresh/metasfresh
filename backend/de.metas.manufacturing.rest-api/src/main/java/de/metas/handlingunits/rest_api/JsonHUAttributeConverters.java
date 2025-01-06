package de.metas.handlingunits.rest_api;

import de.metas.common.handlingunits.JsonHUAttribute;
import de.metas.common.util.CoalesceUtil;
import de.metas.i18n.TranslatableStrings;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.util.DisplayType;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@UtilityClass
class JsonHUAttributeConverters
{
	@NonNull
	public static Object toDisplayValue(@Nullable final Object value, @NonNull final String adLanguage)
	{
		if (value == null)
		{
			return "";
		}
		else if (value instanceof java.sql.Timestamp)
		{
			final LocalDateTime dateTime = ((Timestamp)value).toLocalDateTime();
			return toDisplayValue_fromLocalDateTime(dateTime, adLanguage);
		}
		else if (value instanceof LocalDateTime)
		{
			return toDisplayValue_fromLocalDateTime((LocalDateTime)value, adLanguage);
		}
		else if (value instanceof LocalDate)
		{
			return toDisplayValue_fromLocalDate((LocalDate)value, adLanguage);
		}
		else if (value instanceof BigDecimal)
		{
			return TranslatableStrings.number((BigDecimal)value, DisplayType.Number).translate(adLanguage);
		}
		else
		{
			return CoalesceUtil.coalesceNotNull(JsonHUAttribute.convertValueToJson(value), "");
		}
	}

	private static Object toDisplayValue_fromLocalDateTime(@NonNull final LocalDateTime value, @NonNull final String adLanguage)
	{
		final LocalDate date = value.toLocalDate();
		if (value.equals(date.atStartOfDay()))
		{
			return toDisplayValue_fromLocalDate(date, adLanguage);
		}
		else
		{
			return TranslatableStrings.dateAndTime(value).translate(adLanguage);
		}
	}

	private static Object toDisplayValue_fromLocalDate(@NonNull final LocalDate value, @NonNull final String adLanguage)
	{
		return TranslatableStrings.date(value).translate(adLanguage);
	}

}
