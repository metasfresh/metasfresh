/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.cucumber.stepdefs.report;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_ShipperTransportation_StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Asserts the rows a report's DB function returns, i.e. the data a jasper report prints, with a plain data table
 * instead of hand-written SQL assertions.
 */
public class ReportFunction_StepDef
{
	private static final Pattern SQL_FUNCTION_NAME = Pattern.compile("[A-Za-z_][A-Za-z0-9_]*(\\.[A-Za-z_][A-Za-z0-9_]*)?");
	private static final Pattern AD_LANGUAGE = Pattern.compile("[a-z]{2}_[A-Z]{2}");
	private static final String NULL_PLACEHOLDER = "null";

	private final Map<String, StepDefData<?>> recordTablesByTableName;

	public ReportFunction_StepDef(
			@NonNull final M_ShipperTransportation_StepDefData shipperTransportationTable,
			@NonNull final M_InOut_StepDefData inoutTable,
			@NonNull final C_Order_StepDefData orderTable)
	{
		this.recordTablesByTableName = ImmutableMap.of(
				"M_ShipperTransportation", shipperTransportationTable,
				"M_InOut", inoutTable,
				"C_Order", orderTable);
	}

	/**
	 * Calls {@code <function>(<record ID>)} or, with a language, {@code <function>(<record ID>, '<language>')} and asserts that it returns
	 * exactly the given rows (order-independent). Only the columns of the data table are compared, so a test pins just the fields it is about.
	 * <ul>
	 * <li>column names are the function's result column names (case-insensitive)</li>
	 * <li>{@code \n} in a cell stands for a line break, the cell {@code null} for SQL NULL</li>
	 * <li>numbers are compared without trailing zeros ({@code 12.000} = {@code 12})</li>
	 * <li>trailing spaces and line breaks are ignored, like in the printed report</li>
	 * </ul>
	 * On failure, all actual rows are printed.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: M_ShipperTransportation_StepDefData, M_InOut_StepDefData, C_Order_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the DB function de_metas_endcustomer_fresh_reports.Docs_Sales_TransportOrder_Root for M_ShipperTransportation transportOrder_1 and language de_DE returns exactly:
	 *   | docstatus |
	 *   | DR        |
	 * </pre>
	 */
	@Then("^the DB function (\\S+) for (\\S+) (\\S+?)(?: and language (\\S+))? returns exactly:$")
	public void dbFunctionReturnsExactly(
			@NonNull final String functionName,
			@NonNull final String tableName,
			@NonNull final String recordIdentifier,
			@Nullable final String adLanguage,
			@NonNull final DataTable dataTable)
	{
		assertThat(functionName).as("function name").matches(SQL_FUNCTION_NAME);
		final StepDefData<?> recordTable = recordTablesByTableName.get(tableName);
		assertThat(recordTable).as("supported tables: %s", recordTablesByTableName.keySet()).isNotNull();
		final int recordId = InterfaceWrapperHelper.getId(recordTable.get(recordIdentifier));

		final List<Object> params = new ArrayList<>();
		params.add(recordId);
		final String sql;
		if (adLanguage != null)
		{
			assertThat(adLanguage).as("language").matches(AD_LANGUAGE);
			params.add(adLanguage);
			sql = "SELECT * FROM " + functionName + "(?::numeric, ?::varchar)";
		}
		else
		{
			sql = "SELECT * FROM " + functionName + "(?::numeric)";
		}

		final ImmutableList<Map<String, String>> actualRows = DB.retrieveRowsOutOfTrx(sql, params, ReportFunction_StepDef::toRowMap);

		final List<String> columnNames = dataTable.row(0).stream().map(ReportFunction_StepDef::normalizeColumnName).collect(Collectors.toList());
		final List<Map<String, String>> expectedRows = dataTable.asMaps().stream()
				.map(row -> {
					final Map<String, String> expected = new LinkedHashMap<>();
					row.forEach((column, value) -> expected.put(normalizeColumnName(column), normalizeExpectedValue(value)));
					return expected;
				})
				.collect(Collectors.toList());

		final List<Map<String, String>> actualProjected = actualRows.stream()
				.map(actualRow -> {
					final Map<String, String> projected = new LinkedHashMap<>();
					for (final String column : columnNames)
					{
						assertThat(actualRow).as("result columns of %s: %s", functionName, actualRow.keySet()).containsKey(column);
						projected.put(column, actualRow.get(column));
					}
					return projected;
				})
				.collect(Collectors.toList());

		assertThat(actualProjected)
				.as("rows of %s(%s%s); all actual rows: %s", functionName, recordId, adLanguage != null ? ", " + adLanguage : "", actualRows)
				.containsExactlyInAnyOrderElementsOf(expectedRows);
	}

	private static Map<String, String> toRowMap(@NonNull final ResultSet rs) throws SQLException
	{
		final ResultSetMetaData metaData = rs.getMetaData();
		final Map<String, String> row = new LinkedHashMap<>();
		for (int i = 1; i <= metaData.getColumnCount(); i++)
		{
			row.put(normalizeColumnName(metaData.getColumnLabel(i)), toDisplayString(rs.getObject(i)));
		}
		return row;
	}

	private static String toDisplayString(@Nullable final Object value)
	{
		if (value == null)
		{
			return NULL_PLACEHOLDER;
		}
		if (value instanceof BigDecimal)
		{
			return ((BigDecimal)value).stripTrailingZeros().toPlainString();
		}
		if (value instanceof Number)
		{
			return new BigDecimal(value.toString()).stripTrailingZeros().toPlainString();
		}
		return stripTrailingWhitespace(value.toString());
	}

	private static String stripTrailingWhitespace(@NonNull final String value)
	{
		return value.replaceAll("\\s+$", "");
	}

	private static String normalizeColumnName(@NonNull final String columnName)
	{
		return columnName.trim().toLowerCase();
	}

	private static String normalizeExpectedValue(@Nullable final String value)
	{
		if (value == null)
		{
			return "";
		}
		if (NULL_PLACEHOLDER.equals(value))
		{
			return NULL_PLACEHOLDER;
		}
		final String withLineBreaks = stripTrailingWhitespace(value.replace("\\n", "\n"));
		return isNumber(withLineBreaks) ? new BigDecimal(withLineBreaks).stripTrailingZeros().toPlainString() : withLineBreaks;
	}

	private static boolean isNumber(@NonNull final String value)
	{
		return value.matches("-?\\d+(\\.\\d+)?");
	}
}
