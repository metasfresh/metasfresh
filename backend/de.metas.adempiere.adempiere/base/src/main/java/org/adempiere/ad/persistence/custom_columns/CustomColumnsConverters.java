package org.adempiere.ad.persistence.custom_columns;

import com.google.common.collect.ImmutableMap;
import de.metas.i18n.AdMessageKey;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import de.metas.util.converter.DateTimeConverters;
import de.metas.util.lang.RepoIdAware;
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
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Map;

@UtilityClass
public class CustomColumnsConverters
{
	private static final AdMessageKey MSG_CUSTOM_REST_API_COLUMN = AdMessageKey.of("CUSTOM_REST_API_COLUMN");

	private static final String NULL_STRING = "null";

	@NonNull
	public static CustomColumnsPOValues convertToPOValues(
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

	@NonNull
	public static CustomColumnsJsonValues convertToJsonValues(final @NonNull PO record, final ZoneId zoneId)
	{
		final POInfo poInfo = record.getPOInfo();

		final ImmutableMap.Builder<String, Object> map = ImmutableMap.builder();

		poInfo.streamColumns(POInfoColumn::isRestAPICustomColumn)
				.forEach(customColumn -> {
					final String columnName = customColumn.getColumnName();
					final Object poValue = record.get_Value(columnName);
					final Object jsonValue = CustomColumnsConverters.convertToJsonValue(poValue, customColumn.getDisplayType(), zoneId);
					if (jsonValue != null)
					{
						map.put(columnName, jsonValue);
					}
				});

		return CustomColumnsJsonValues.ofJsonValuesMap(map.build());
	}

	@Nullable
	private static Object convertToPOValue(
			@NonNull final String columnName,
			@Nullable final Object value,
			@NonNull final POInfo poInfo,
			@NonNull final ZoneId zoneId)
	{
		if (!poInfo.isRestAPICustomColumn(columnName))
		{
			throw new AdempiereException(MSG_CUSTOM_REST_API_COLUMN, columnName)
					.markAsUserValidationError();
		}

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
	private static Object convertToJsonValue(@Nullable final Object value, final int displayType, @NonNull final ZoneId zoneId)
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

	@Nullable
	private static Object convertToPOValue(@Nullable final Object value, @NonNull final Class<?> targetClass, final int displayType, @NonNull final ZoneId zoneId)
	{
		if (value == null)
		{
			return null;
		}

		final Class<?> valueClass = value.getClass();

		if (String.class.equals(valueClass))
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

		if (targetClass.isAssignableFrom(valueClass))
		{
			return value;
		}
		else if (Integer.class.equals(targetClass))
		{
			if (String.class.equals(valueClass))
			{
				return new BigDecimal((String)value).intValue();
			}
			else if (RepoIdAware.class.isAssignableFrom(valueClass))
			{
				final RepoIdAware repoIdAware = (RepoIdAware)value;
				return repoIdAware.getRepoId();
			}
		}
		else if (String.class.equals(targetClass))
		{
			return String.valueOf(value);
		}
		else if (Timestamp.class.equals(targetClass))
		{
			final Object valueDate = DateTimeConverters.fromObject(value, displayType, zoneId);

			return TimeUtil.asTimestamp(valueDate);
		}
		else if (Boolean.class.equals(targetClass))
		{
			if (String.class.equals(valueClass))
			{
				return StringUtils.toBoolean(value);
			}
		}
		else if (BigDecimal.class.equals(targetClass))
		{
			if (Double.class.equals(valueClass))
			{
				return BigDecimal.valueOf((Double)value);
			}
			else if (Float.class.equals(valueClass))
			{
				return BigDecimal.valueOf((Float)value);
			}
		}

		throw new RuntimeException("TargetClass " + targetClass + " does not match any supported classes!");
	}
}
