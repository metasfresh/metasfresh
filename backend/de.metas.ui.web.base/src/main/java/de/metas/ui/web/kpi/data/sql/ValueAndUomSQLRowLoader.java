package de.metas.ui.web.kpi.data.sql;

import de.metas.ui.web.kpi.data.KPIDataResult;
import de.metas.ui.web.kpi.data.KPIDataSetValuesAggregationKey;
import de.metas.ui.web.kpi.data.KPIDataValue;
import de.metas.ui.web.kpi.descriptor.KPIField;
import de.metas.ui.web.kpi.descriptor.sql.SQLDatasourceDescriptor;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

class ValueAndUomSQLRowLoader implements SQLRowLoader
{
	@NonNull private final KPIField valueField;
	@Nullable private final KPIField uomField;

	ValueAndUomSQLRowLoader(final List<KPIField> fields)
	{
		this.uomField = extractUOMField(fields);
		this.valueField = extractValueField(fields, uomField);
	}

	@Nullable
	private static KPIField extractUOMField(final List<KPIField> fields)
	{
		if (fields.size() < 2)
		{
			return null;
		}

		for (final KPIField field : fields)
		{
			final String fieldName = field.getFieldName();
			if ("Currency".equalsIgnoreCase(fieldName)
					|| "CurrencyCode".equalsIgnoreCase(fieldName)
					|| "UOMSymbol".equalsIgnoreCase(fieldName)
					|| "UOM".equalsIgnoreCase(fieldName))
			{
				return field;
			}
		}

		return null;
	}

	private static KPIField extractValueField(
			final List<KPIField> fields,
			final KPIField... excludeFields)
	{
		final List<KPIField> excludeFieldsList = Arrays.asList(excludeFields);

		for (final KPIField field : fields)
		{
			if (!excludeFieldsList.contains(field))
			{
				return field;
			}
		}

		throw new AdempiereException("Cannot determine value field: " + fields);
	}

	@Override
	public void loadRowToResult(@NonNull final KPIDataResult.Builder data, final @NonNull ResultSet rs) throws SQLException
	{
		final KPIDataValue value = SQLRowLoaderUtils.retrieveValue(rs, valueField);
		final String unit = uomField != null ? rs.getString(uomField.getFieldName()) : null;

		data.dataSet(valueField.getFieldName())
				.unit(unit)
				.dataSetValue(KPIDataSetValuesAggregationKey.NO_KEY)
				.put(valueField.getFieldName(), value);
	}
}
