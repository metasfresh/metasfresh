package org.adempiere.ad.wrapper;

import com.google.common.base.Strings;
import de.metas.util.Check;
import de.metas.util.text.tabular.Cell;
import de.metas.util.text.tabular.Row;
import de.metas.util.text.tabular.Table;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

import java.util.Collection;
import java.util.Optional;

@UtilityClass
class POJOWrapperUtils
{
	public static String fromPOJOWrappers(final Collection<Object> records, int ident)
	{
		final Table table = toTable(records).orElse(null);
		if (table == null)
		{
			return "";
		}

		final StringBuilder result = new StringBuilder();
		table.removeColumnsWithSameValue().ifPresent(commonValueColumns -> result.append(commonValueColumns.toTabularString()));

		final String differentValueColumnsStr = table.toTabularString();
		if (!Check.isBlank(differentValueColumnsStr))
		{
			if (result.length() > 0)
			{
				result.append("\n\n");
			}
			result.append(differentValueColumnsStr);
		}

		String resultStr = result.toString();

		if (ident > 0)
		{
			final String identStr = Strings.repeat("\t", ident);
			resultStr = identStr + resultStr.replace("\n", "\n" + identStr);
		}

		return resultStr;
	}

	private static Optional<Table> toTable(final Collection<Object> records)
	{
		if (records.isEmpty())
		{
			return Optional.empty();
		}

		final Table table = new Table();
		String idColumnName = null;

		for (final Object recordObj : records)
		{
			final POJOWrapper record = POJOWrapper.getWrapper(recordObj);
			if (record == null)
			{
				throw new AdempiereException("Record " + record + " is not based on " + POJOWrapper.class);
			}

			table.addHeaderFromStrings(record.getColumnNames());
			table.addRow(toRow(record));

			if (idColumnName == null)
			{
				idColumnName = record.getIdColumnName();
			}
		}

		table.removeColumnsWithBlankValues();
		table.moveColumnsToStart(idColumnName);
		table.moveColumnsToEnd("AD_Client_ID", "AD_Org_ID", "IsActive", "Created", "CreatedBy", "Updated", "UpdatedBy");

		return Optional.of(table);
	}

	private static Row toRow(final POJOWrapper record)
	{
		final Row row = new Row();
		record.getValuesMap().forEach((columnName, valueObj) -> row.put(columnName, Cell.ofNullable(valueObj)));
		return row;
	}
}
