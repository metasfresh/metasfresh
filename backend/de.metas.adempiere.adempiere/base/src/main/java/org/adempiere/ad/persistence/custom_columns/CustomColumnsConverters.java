package org.adempiere.ad.persistence.custom_columns;

import com.google.common.collect.ImmutableMap;
import de.metas.util.Check;
import de.metas.util.converter.POValueConverters;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Null;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.model.POInfoColumn;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Map;

@UtilityClass
class CustomColumnsConverters
{
	private static final String NULL_STRING = "null";

	@Nullable
	public static Object convertToJsonValue(@Nullable final Object value, final int displayType, @NonNull final ZoneId zoneId)
	{
		if (value == null)
		{
			return null;
		}

		if (value.getClass().equals(Timestamp.class))
		{
			if (displayType == DisplayType.Time)
			{
				return TimeUtil.asLocalTime(value, zoneId);
			}
			else if (displayType == DisplayType.Date)
			{
				return TimeUtil.asLocalDate(value, zoneId);
			}
			else if (displayType == DisplayType.DateTime)
			{
				return TimeUtil.asInstant(value, zoneId);
			}
		}
		return value;
	}

	@NonNull
	public CustomColumnsPOValues convertToPOValues(
			@NonNull final Map<String, Object> valuesByColumnName,
			@NonNull final POInfo poInfo,
			@NonNull final ZoneId zoneId)
	{
		final ImmutableMap.Builder<String, Object> poValues = ImmutableMap.builder();
		valuesByColumnName.forEach((columnName, value) -> {
			final Object poValue = convertToPOValue(columnName, value, poInfo, zoneId);
			poValues.put(columnName, Null.box(poValue));
		});

		return CustomColumnsPOValues.ofPOValues(poValues.build());
	}

	@Nullable
	private static Object convertToPOValue(
			@NonNull final String columnName,
			@Nullable final Object value,
			@NonNull final POInfo poInfo,
			@NonNull final ZoneId zoneId)
	{
		final Class<?> columnTargetClass = poInfo.getColumnClass(columnName);
		if (columnTargetClass == null)
		{
			throw new AdempiereException("Cannot get the actual PO value if targetClass is missing!")
					.appendParametersToMessage()
					.setParameter("TableName", poInfo.getTableName())
					.setParameter("ColumnName", columnName);
		}

		final int displayType = poInfo.getColumnDisplayType(columnName);

		return convertToPOValue(value, columnTargetClass, displayType, zoneId);
	}

	@Nullable
	private static Object convertToPOValue(@Nullable final Object value, @NonNull final Class<?> targetClass, final int displayType, @NonNull final ZoneId zoneId)
	{
		if (value == null)
		{
			return null;
		}

		if (String.class.equals(value.getClass()))
		{
			final String valueStr = (String)value;

			if (String.class.equals(targetClass))
			{
				return valueStr;
			}
			// for all the other targetClass variants, an empty string or the "null" string are considered null values
			else if (Check.isBlank(valueStr) || NULL_STRING.equalsIgnoreCase(valueStr))
			{
				return null;
			}
		}

		return POValueConverters.convertToPOValue(value, targetClass, displayType, zoneId);
	}

	@Nullable
	public static Object convertFromPOValue(@NonNull final PO po, @NonNull final POInfoColumn poInfoColumn, @NonNull final ZoneId zoneId)
	{
		final Object poValue = po.get_Value(poInfoColumn.getColumnName());

		return POValueConverters.convertFromPOValue(poValue, poInfoColumn.getDisplayType(), zoneId);
	}

}
