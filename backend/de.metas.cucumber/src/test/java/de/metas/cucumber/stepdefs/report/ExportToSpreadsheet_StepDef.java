package de.metas.cucumber.stepdefs.report;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrx;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.assertj.core.api.SoftAssertions;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Reusable step definitions for testing any {@code ExportToSpreadsheetProcess}-based AD_Process.
 * <p>
 * Executes the process via {@link ProcessInfo#builder()}, retrieves the generated Excel file,
 * and provides verification steps for headers, data rows, cell values, and row counts.
 * <p>
 * The generated Excel is kept as instance state between the execute and verify steps
 * (scoped to the current Cucumber scenario via PicoContainer).
 *
 * <h3>Usage pattern in feature files:</h3>
 * <pre>
 * When the AD_Process "Package-Licencing-Report-Details" is executed with parameters:
 *   | ParameterName | Value      |
 *   | DateFrom      | 2090-05-01 |
 *   | DateTo        | 2090-05-31 |
 *   | C_Country_ID  | AT         |
 *
 * Then the exported Excel header row contains in order:
 *   | Belegnummer | Bewegungsdatum | Ländercode |
 *
 * Then the exported Excel data matched by "Belegnummer" contains:
 *   | Belegnummer      | Einkaufsmenge |
 *   | PLV10_RECEIPT_AT | 100           |
 * </pre>
 */
@RequiredArgsConstructor
public class ExportToSpreadsheet_StepDef
{
	@NonNull private final IADProcessDAO processDAO = Services.get(IADProcessDAO.class);

	/** The Excel headers from the last executed process (column index → header name). */
	private final List<String> excelHeaders = new ArrayList<>();

	/** The Excel data rows from the last executed process (list of maps: header → cell value as string). */
	private final List<Map<String, String>> excelDataRows = new ArrayList<>();

	/**
	 * Executes an {@code ExportToSpreadsheetProcess} by its {@code AD_Process.Value}.
	 * Parameters are passed via a DataTable with columns {@code ParameterName} and {@code Value}.
	 * <p>
	 * Supported parameter types (auto-detected):
	 * <ul>
	 *   <li>Dates (yyyy-MM-dd format) → {@link Timestamp}</li>
	 *   <li>{@code C_Country_ID} with a 2-letter code → resolved to numeric ID</li>
	 *   <li>Numeric strings → {@link BigDecimal}</li>
	 *   <li>Everything else → {@link String}</li>
	 * </ul>
	 * <p>
	 * After execution, the Excel result is parsed and stored for subsequent verification steps.
	 *
	 * @param processValue the {@code AD_Process.Value} (e.g. "Package-Licencing-Report-Details")
	 * @param dataTable    parameters with columns: ParameterName, Value
	 */
	@When("the AD_Process {string} is executed with parameters:")
	public void executeProcess(@NonNull final String processValue, @NonNull final DataTable dataTable) throws Exception
	{
		excelHeaders.clear();
		excelDataRows.clear();

		final AdProcessId processId = processDAO.retrieveProcessIdByValue(processValue);
		assertThat(processId).as("AD_Process with Value=" + processValue + " must exist").isNotNull();

		final ProcessInfo.ProcessInfoBuilder builder = ProcessInfo.builder();
		builder.setCtx(Env.getCtx());
		builder.setAD_Process_ID(processId);

		// Add parameters from DataTable
		DataTableRows.of(dataTable).forEach(row -> addParameter(builder, row));

		// Execute the process
		final ProcessExecutionResult result = builder
				.buildAndPrepareExecution()
				.executeSync()
				.getResult();

		assertThat(result.isError())
				.as("Process " + processValue + " should not fail. Error: " + result.getSummary())
				.isFalse();

		// Read the Excel result
		final Resource reportResource = result.getReportDataResource();
		assertThat(reportResource)
				.as("Process " + processValue + " should produce an Excel file")
				.isNotNull();

		try (final InputStream is = reportResource.getInputStream();
			 final Workbook workbook = WorkbookFactory.create(is))
		{
			final Sheet sheet = workbook.getSheetAt(0);
			parseExcel(sheet);
		}
	}

	/**
	 * Verifies that the Excel header row contains the expected column names in the given order.
	 * Column names are the translated labels produced by {@code IMsgBL.translatable()}.
	 *
	 * <pre>
	 * Then the exported Excel header row contains in order:
	 *   | Belegnummer | Bewegungsdatum | Ländercode | ... |
	 * </pre>
	 */
	@Then("the exported Excel header row contains in order:")
	public void verifyHeadersInOrder(@NonNull final DataTable dataTable)
	{
		final List<String> expectedHeaders = dataTable.row(0);

		final SoftAssertions softly = new SoftAssertions();
		for (int i = 0; i < expectedHeaders.size(); i++)
		{
			final String expected = expectedHeaders.get(i);
			final String actual = i < excelHeaders.size() ? excelHeaders.get(i) : null;
			softly.assertThat(actual)
					.as("Excel header at column " + (i + 1))
					.isEqualTo(expected);
		}
		softly.assertAll();
	}

	/**
	 * Verifies Excel data rows matched by a key column. Each expected row is looked up
	 * by matching the key column's value, then all other columns are compared.
	 * Empty expected cells assert that the actual value is null or empty.
	 * Numeric values are compared using {@link BigDecimal#compareTo}.
	 *
	 * <pre>
	 * Then the exported Excel data matched by "Belegnummer" contains:
	 *   | Belegnummer      | Einkaufsmenge | Lieferantenland |
	 *   | PLV10_RECEIPT_AT | 100           | AT              |
	 * </pre>
	 *
	 * @param keyColumn the column header used to match rows
	 * @param dataTable expected data with headers matching Excel column names
	 */
	@Then("the exported Excel data matched by {string} contains:")
	public void verifyDataByKey(@NonNull final String keyColumn, @NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> expectedRows = dataTable.asMaps();
		final SoftAssertions softly = new SoftAssertions();

		for (final Map<String, String> expectedRow : expectedRows)
		{
			final String keyValue = expectedRow.get(keyColumn);
			final String matchDesc = keyColumn + "=" + keyValue;

			final Map<String, String> actualRow = excelDataRows.stream()
					.filter(r -> keyValue.equals(r.get(keyColumn)))
					.findFirst()
					.orElse(null);

			softly.assertThat(actualRow)
					.as("Excel row for " + matchDesc)
					.isNotNull();

			if (actualRow == null)
			{
				continue;
			}

			for (final Map.Entry<String, String> entry : expectedRow.entrySet())
			{
				final String col = entry.getKey();
				if (col.equals(keyColumn))
				{
					continue;
				}

				final String expectedValue = entry.getValue();
				final String actualValue = actualRow.get(col);

				if (expectedValue == null || expectedValue.isEmpty())
				{
					softly.assertThat(actualValue)
							.as(col + " for " + matchDesc + " should be null/empty")
							.isNullOrEmpty();
				}
				else if (isNumeric(expectedValue))
				{
					softly.assertThat(actualValue)
							.as(col + " for " + matchDesc)
							.isNotNull();
					if (actualValue != null && isNumeric(actualValue))
					{
						softly.assertThat(new BigDecimal(actualValue))
								.as(col + " for " + matchDesc)
								.isEqualByComparingTo(new BigDecimal(expectedValue));
					}
				}
				else
				{
					softly.assertThat(actualValue)
							.as(col + " for " + matchDesc)
							.isEqualTo(expectedValue);
				}
			}
		}

		softly.assertAll();
	}

	/**
	 * Verifies that a specific cell in a matched row is not null.
	 *
	 * <pre>
	 * Then the exported Excel row with "Belegnummer" = "PLV10_RECEIPT_AT" has "Lieferant" not null
	 * </pre>
	 */
	@Then("the exported Excel row with {string} = {string} has {string} not null")
	public void verifyNotNull(@NonNull final String keyColumn, @NonNull final String keyValue, @NonNull final String targetColumn)
	{
		final Map<String, String> actualRow = excelDataRows.stream()
				.filter(r -> keyValue.equals(r.get(keyColumn)))
				.findFirst()
				.orElse(null);

		assertThat(actualRow)
				.as("Excel row with " + keyColumn + "=" + keyValue)
				.isNotNull();

		assertThat(actualRow.get(targetColumn))
				.as(targetColumn + " for " + keyColumn + "=" + keyValue + " should not be null")
				.isNotNull();
	}

	/**
	 * Verifies the exact number of data rows (excluding the header row) in the Excel.
	 */
	@Then("the exported Excel has {int} data rows")
	public void verifyRowCount(final int expectedCount)
	{
		assertThat(excelDataRows.size())
				.as("Excel data row count")
				.isEqualTo(expectedCount);
	}

	/**
	 * Verifies the Excel has at least N data rows (excluding the header row).
	 */
	@Then("the exported Excel has at least {int} data rows")
	public void verifyMinRowCount(final int minCount)
	{
		assertThat(excelDataRows.size())
				.as("Excel data row count")
				.isGreaterThanOrEqualTo(minCount);
	}

	/**
	 * Verifies that the exported Excel does NOT contain a row with the given key value.
	 */
	@Then("the exported Excel does not contain a row with {string} = {string}")
	public void verifyRowAbsent(@NonNull final String keyColumn, @NonNull final String keyValue)
	{
		final boolean found = excelDataRows.stream()
				.anyMatch(r -> keyValue.equals(r.get(keyColumn)));
		assertThat(found)
				.as("Excel should not contain row with " + keyColumn + "=" + keyValue)
				.isFalse();
	}

	// --- Private helpers ---

	private void addParameter(@NonNull final ProcessInfo.ProcessInfoBuilder builder, @NonNull final DataTableRow row)
	{
		final String paramName = row.getAsString("ParameterName");
		final String paramValue = row.getAsString("Value");

		// C_Country_ID with country code → resolve to numeric ID
		if ("C_Country_ID".equals(paramName) && paramValue.length() == 2 && !Character.isDigit(paramValue.charAt(0)))
		{
			final int countryId = DB.getSQLValueEx(ITrx.TRXNAME_None,
					"SELECT C_Country_ID FROM C_Country WHERE CountryCode='" + paramValue.replace("'", "''") + "'");
			builder.addParameter(paramName, countryId);
			return;
		}

		// Date format (yyyy-MM-dd)
		if (paramValue.matches("\\d{4}-\\d{2}-\\d{2}"))
		{
			builder.addParameter(paramName, Timestamp.valueOf(paramValue + " 00:00:00"));
			return;
		}

		// Boolean Y/N
		if ("Y".equals(paramValue) || "N".equals(paramValue))
		{
			builder.addParameter(paramName, paramValue);
			return;
		}

		// Numeric
		if (isNumeric(paramValue))
		{
			builder.addParameter(paramName, new BigDecimal(paramValue));
			return;
		}

		// Default: string
		builder.addParameter(paramName, paramValue);
	}

	private void parseExcel(@NonNull final Sheet sheet)
	{
		final Row headerRow = sheet.getRow(0);
		if (headerRow == null)
		{
			return;
		}

		// Parse headers
		for (int col = 0; col < headerRow.getLastCellNum(); col++)
		{
			final Cell cell = headerRow.getCell(col);
			excelHeaders.add(cell != null ? getCellValueAsString(cell) : "");
		}

		// Parse data rows
		for (int rowIdx = 1; rowIdx <= sheet.getLastRowNum(); rowIdx++)
		{
			final Row row = sheet.getRow(rowIdx);
			if (row == null)
			{
				continue;
			}

			final Map<String, String> dataRow = new LinkedHashMap<>();
			for (int col = 0; col < excelHeaders.size(); col++)
			{
				final Cell cell = row.getCell(col);
				final String value = cell != null ? getCellValueAsString(cell) : null;
				dataRow.put(excelHeaders.get(col), value);
			}
			excelDataRows.add(dataRow);
		}
	}

	private static String getCellValueAsString(@NonNull final Cell cell)
	{
		final CellType cellType = cell.getCellTypeEnum();
		if (cellType == CellType.STRING)
		{
			return cell.getStringCellValue();
		}
		else if (cellType == CellType.NUMERIC)
		{
			final double numericValue = cell.getNumericCellValue();
			if (numericValue == Math.floor(numericValue) && !Double.isInfinite(numericValue))
			{
				return String.valueOf((long)numericValue);
			}
			return BigDecimal.valueOf(numericValue).stripTrailingZeros().toPlainString();
		}
		else if (cellType == CellType.BOOLEAN)
		{
			return cell.getBooleanCellValue() ? "Y" : "N";
		}
		else if (cellType == CellType.BLANK)
		{
			return null;
		}
		else
		{
			return cell.toString();
		}
	}

	private static boolean isNumeric(@NonNull final String value)
	{
		try
		{
			new BigDecimal(value);
			return true;
		}
		catch (final NumberFormatException e)
		{
			return false;
		}
	}
}
