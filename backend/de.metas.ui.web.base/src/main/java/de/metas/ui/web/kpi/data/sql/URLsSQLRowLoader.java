package de.metas.ui.web.kpi.data.sql;

import com.google.common.collect.ImmutableList;
import de.metas.ui.web.kpi.data.KPIDataResult;
import de.metas.ui.web.kpi.data.KPIDataSetValuesAggregationKey;
import de.metas.ui.web.kpi.data.KPIDataSetValuesMap;
import de.metas.ui.web.kpi.data.KPIDataValue;
import de.metas.ui.web.kpi.descriptor.KPIField;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class URLsSQLRowLoader implements SQLRowLoader
{
	@NonNull private final KPIField urlField;
	@Nullable private final KPIField captionField;
	@Nullable private final KPIField openTargetField;

	URLsSQLRowLoader(@NonNull final List<KPIField> fields)
	{
		this.urlField = extractURLField(fields);
		this.captionField = extractCaptionField(fields, this.urlField);
		this.openTargetField = extractOpenTargetField(fields, this.urlField, this.captionField);

	}

	@NonNull
	private static KPIField extractURLField(final List<KPIField> fields)
	{
		// Try: first field of type URL
		for (final KPIField field : fields)
		{
			if (field.getValueType().isURL())
			{
				return field;
			}
		}

		// Try: in case we have only one field and that field is of type string then consider it
		if (fields.size() == 1)
		{
			final KPIField field = fields.get(0);
			if (field.getValueType().isString())
			{
				return field;
			}
		}

		// Try: field name starts/ends with URL and is String type
		for (final KPIField field : fields)
		{
			final String fieldName = field.getFieldName();
			if ((fieldName.startsWith("URL") || fieldName.endsWith("URL"))
					&& field.getValueType().isString())
			{
				return field;
			}
		}

		throw new AdempiereException("No URL field found in " + fields);
	}

	@Nullable
	private static KPIField extractCaptionField(final List<KPIField> fields, @Nullable final KPIField... excludeFields)
	{
		final List<KPIField> remainingFields = excludeFields(fields, excludeFields);

		for (final KPIField field : remainingFields)
		{
			final String fieldName = field.getFieldName();
			if ("Name".equals(fieldName)
					|| "Caption".equals(fieldName)
					|| "Title".equals(fieldName))
			{
				return field;
			}
		}

		return null;
	}

	@Nullable
	private static KPIField extractOpenTargetField(final List<KPIField> fields, @Nullable final KPIField... excludeFields)
	{
		final List<KPIField> remainingFields = excludeFields(fields, excludeFields);

		for (final KPIField field : remainingFields)
		{
			final String fieldName = field.getFieldName();
			if ("Target".equals(fieldName)
					|| "OpenTarget".equals(fieldName))
			{
				return field;
			}
		}

		return null;
	}

	private static List<KPIField> excludeFields(final List<KPIField> fields, @Nullable final KPIField... excludeFields)
	{
		final List<KPIField> excludeFieldsList = excludeFields != null && excludeFields.length > 0
				? Stream.of(excludeFields).filter(Objects::nonNull).collect(Collectors.toList())
				: null;
		if (excludeFieldsList == null || excludeFieldsList.isEmpty())
		{
			return fields;
		}

		return fields.stream()
				.filter(field -> !excludeFieldsList.contains(field))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public void loadRowToResult(@NonNull final KPIDataResult.Builder data, @NonNull final ResultSet rs) throws SQLException
	{
		final KPIDataValue url = SQLRowLoaderUtils.retrieveValue(rs, urlField);

		final KPIDataSetValuesMap.KPIDataSetValuesMapBuilder resultEntry = data
				.dataSet("URLs")
				.dataSetValue(KPIDataSetValuesAggregationKey.of(url))
				.put("url", url);

		loadField(resultEntry, rs, "caption", captionField);
		loadField(resultEntry, rs, "target", openTargetField);
	}

	public static void loadField(
			@NonNull final KPIDataSetValuesMap.KPIDataSetValuesMapBuilder resultEntry,
			@NonNull final ResultSet rs,
			@NonNull final String targetFieldName,
			@Nullable final KPIField field) throws SQLException
	{
		if (field == null)
		{
			return;
		}

		final KPIDataValue value = SQLRowLoaderUtils.retrieveValue(rs, field);
		resultEntry.put(targetFieldName, value);
	}
}
