package de.metas.ui.web.kpi.data.sql;

import de.metas.ui.web.kpi.data.KPIDataValue;
import de.metas.ui.web.kpi.descriptor.KPIField;
import de.metas.ui.web.kpi.descriptor.KPIFieldValueType;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@UtilityClass
class SQLRowLoaderUtils
{
	public static KPIDataValue retrieveValue(final ResultSet rs, final KPIField field) throws SQLException
	{
		final String fieldName = field.getFieldName();
		final KPIFieldValueType valueType = field.getValueType();

		if (valueType == KPIFieldValueType.String
				|| valueType == KPIFieldValueType.URL)
		{
			final String valueStr = rs.getString(fieldName);
			return KPIDataValue.ofValueAndField(valueStr, field);
		}
		else if (valueType == KPIFieldValueType.Number)
		{
			final BigDecimal valueBD = rs.getBigDecimal(fieldName);
			return KPIDataValue.ofValueAndField(valueBD, field);
		}
		else if (valueType == KPIFieldValueType.Date
				|| valueType == KPIFieldValueType.DateTime)
		{
			final Timestamp valueTS = rs.getTimestamp(fieldName);
			return KPIDataValue.ofValueAndField(valueTS != null ? valueTS.toInstant() : null, field);
		}
		else
		{
			throw new AdempiereException("Unknown value type `" + valueType + "` for " + field);
		}
	}
}
