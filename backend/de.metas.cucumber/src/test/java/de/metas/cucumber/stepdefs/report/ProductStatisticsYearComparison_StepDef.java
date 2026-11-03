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

import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.calendar.C_Year_StepDefData;
import de.metas.cucumber.stepdefs.org.AD_Org_StepDefData;
import de.metas.cucumber.stepdefs.productCategory.M_Product_Category_StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrx;
import org.assertj.core.api.SoftAssertions;
import org.compiere.util.DB;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Asserts the rows of the DB function
 * {@code report.Product_Statistics_Year_Comparison(p_AD_Org_ID, p_C_Year_ID, p_M_Product_ID, p_M_Product_To_ID, p_M_Product_Category_ID)}.
 * <p>
 * The step FAILS (does not skip) if the function does not exist.
 */
@RequiredArgsConstructor
public class ProductStatisticsYearComparison_StepDef
{
	private static final String COLUMN_M_Product_ID = "M_Product_ID";
	private static final String FUNCTION = "report.Product_Statistics_Year_Comparison";

	private static final Set<String> TEXT_COLUMNS = new HashSet<>(Arrays.asList("productvalue", "productname", "currency", "orgname", "fiscalyear", "notposted_months"));

	/** All result columns (besides M_Product_ID) in their canonical spelling. */
	private static final List<String> RESULT_COLUMNS = buildResultColumns();

	private final AD_Org_StepDefData orgTable;
	private final C_Year_StepDefData yearTable;
	private final M_Product_StepDefData productTable;
	private final M_Product_Category_StepDefData productCategoryTable;

	private static List<String> buildResultColumns()
	{
		final List<String> cols = new ArrayList<>(Arrays.asList("ProductValue", "ProductName"));
		for (final String prefix : new String[] { "Qty_", "Amt_", "PrevQty_", "PrevAmt_", "Dev_" })
		{
			for (int m = 1; m <= 12; m++)
			{
				cols.add(prefix + String.format("%02d", m));
			}
			if (!prefix.equals("Dev_"))
			{
				cols.add(prefix + "Total");
			}
		}
		cols.addAll(Arrays.asList("Currency", "OrgName", "FiscalYear", "NotPosted_Count", "NotPosted_Months"));
		return cols;
	}

	/**
	 * Calls {@code report.Product_Statistics_Year_Comparison} and compares the listed products only
	 * (unlisted rows are ignored; the relative order of the listed products is checked).
	 * Numeric columns are compared with {@link BigDecimal#compareTo}, text columns exactly; an empty cell matches NULL.
	 *
	 * @param orgIdentifier     {@code AD_Org} identifier, or {@code -} for the standard org
	 * @param yearIdentifier    {@code C_Year} identifier (never the FiscalYear text)
	 * @param productIdentifier {@code M_Product} identifier, or {@code -} for all products
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: AD_Org_StepDefData, C_Year_StepDefData, M_Product_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the product statistics year comparison report for org "-", year "y2026", product "-" returns:
	 *   | M_Product_ID | Qty_01 | Amt_01 | Currency | NotPosted_Count | NotPosted_Months |
	 *   | p1           | 10     | 100.00 | EUR      | 0                 |                    |
	 * </pre>
	 */
	@Then("the product statistics year comparison report for org {string}, year {string}, product {string} returns:")
	public void expect_report(
			@NonNull final String orgIdentifier,
			@NonNull final String yearIdentifier,
			@NonNull final String productIdentifier,
			@NonNull final DataTable dataTable)
	{
		assertReportRows(retrieveReportRows(orgIdentifier, yearIdentifier, productIdentifier, "-", "-"), dataTable);
	}

	/**
	 * As {@link #expect_report}, with a product range and a product category.
	 *
	 * @param productFromIdentifier {@code M_Product} identifier of "Produkt von", or {@code -}
	 * @param productToIdentifier   {@code M_Product} identifier of "Produkt bis", or {@code -}
	 * @param categoryIdentifier    {@code M_Product_Category} identifier, or {@code -} for all categories
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: AD_Org_StepDefData, C_Year_StepDefData, M_Product_StepDefData, M_Product_Category_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the product statistics year comparison report for org "-", year "y2026", products "p1" to "p3", category "cat1" returns:
	 *   | M_Product_ID | Amt_03 |
	 *   | p2           | 10.00  |
	 * </pre>
	 */
	@Then("the product statistics year comparison report for org {string}, year {string}, products {string} to {string}, category {string} returns:")
	public void expect_report_for_range(
			@NonNull final String orgIdentifier,
			@NonNull final String yearIdentifier,
			@NonNull final String productFromIdentifier,
			@NonNull final String productToIdentifier,
			@NonNull final String categoryIdentifier,
			@NonNull final DataTable dataTable)
	{
		assertReportRows(retrieveReportRows(orgIdentifier, yearIdentifier, productFromIdentifier, productToIdentifier, categoryIdentifier), dataTable);
	}

	private void assertReportRows(@NonNull final List<Map<String, Object>> actualRows, @NonNull final DataTable dataTable)
	{
		// validate columns (typo protection)
		final Map<String, String> canonicalByLower = new HashMap<>();
		canonicalByLower.put(COLUMN_M_Product_ID.toLowerCase(), COLUMN_M_Product_ID);
		RESULT_COLUMNS.forEach(c -> canonicalByLower.put(c.toLowerCase(), c));
		final List<Map<String, String>> expectedRows = dataTable.asMaps(String.class, String.class);
		for (final String column : dataTable.row(0))
		{
			if (!canonicalByLower.containsKey(column.toLowerCase()))
			{
				throw new AssertionError("Unknown column '" + column + "' in expected table; allowed: " + COLUMN_M_Product_ID + ", " + RESULT_COLUMNS);
			}
		}
		if (!dataTable.row(0).stream().anyMatch(c -> COLUMN_M_Product_ID.equalsIgnoreCase(c)))
		{
			throw new AssertionError("Expected table needs the column " + COLUMN_M_Product_ID);
		}

		final SoftAssertions softly = new SoftAssertions();
		int lastActualIndex = -1;
		String lastProductIdentifier = null;
		for (final Map<String, String> expectedRaw : expectedRows)
		{
			final Map<String, String> expected = new LinkedHashMap<>();
			expectedRaw.forEach((k, v) -> expected.put(canonicalByLower.get(k.toLowerCase()), v));

			final String rowProductIdentifier = expected.get(COLUMN_M_Product_ID);
			final int rowProductId = productTable.get(StepDefDataIdentifier.ofString(rowProductIdentifier)).getM_Product_ID();

			int actualIndex = -1;
			for (int i = 0; i < actualRows.size(); i++)
			{
				if (rowProductId == ((BigDecimal)actualRows.get(i).get(COLUMN_M_Product_ID.toLowerCase())).intValue())
				{
					actualIndex = i;
					break;
				}
			}
			if (actualIndex < 0)
			{
				softly.fail("Product " + rowProductIdentifier + " (M_Product_ID=" + rowProductId + "): no row returned by " + FUNCTION
						+ "; returned M_Product_IDs=" + actualRows.stream().map(r -> String.valueOf(r.get("m_product_id"))).collect(Collectors.toList()));
				continue;
			}
			if (actualIndex < lastActualIndex)
			{
				softly.fail("Order: product " + rowProductIdentifier + " is expected after " + lastProductIdentifier
						+ " but the report returns it before (row " + actualIndex + " < " + lastActualIndex + ")");
			}
			lastActualIndex = Math.max(lastActualIndex, actualIndex);
			lastProductIdentifier = rowProductIdentifier;

			final long rowCount = actualRows.stream().filter(r -> rowProductId == ((BigDecimal)r.get(COLUMN_M_Product_ID.toLowerCase())).intValue()).count();
			if (rowCount > 1)
			{
				softly.fail("Product " + rowProductIdentifier + " (M_Product_ID=" + rowProductId + "): expected exactly one row but " + FUNCTION + " returned " + rowCount);
			}

			final Map<String, Object> actual = actualRows.get(actualIndex);
			for (final Map.Entry<String, String> e : expected.entrySet())
			{
				if (COLUMN_M_Product_ID.equals(e.getKey()))
				{
					continue;
				}
				compareCell(softly, rowProductIdentifier, e.getKey(), e.getValue(), actual.get(e.getKey().toLowerCase()));
			}
		}
		softly.assertAll();
	}

	private List<Map<String, Object>> retrieveReportRows(
			@NonNull final String orgIdentifier,
			@NonNull final String yearIdentifier,
			@NonNull final String productFromIdentifier,
			@NonNull final String productToIdentifier,
			@NonNull final String categoryIdentifier)
	{
		assertFunctionExists();

		final int orgId = "-".equals(orgIdentifier)
				? StepDefConstants.ORG_ID.getRepoId()
				: orgTable.get(StepDefDataIdentifier.ofString(orgIdentifier)).getAD_Org_ID();
		final int yearId = yearTable.get(StepDefDataIdentifier.ofString(yearIdentifier)).getC_Year_ID();
		final Integer productFromId = toProductIdOrNull(productFromIdentifier);
		final Integer productToId = toProductIdOrNull(productToIdentifier);
		final Integer categoryId = "-".equals(categoryIdentifier)
				? null
				: productCategoryTable.get(StepDefDataIdentifier.ofString(categoryIdentifier)).getM_Product_Category_ID();

		// the optional parameters are passed only when set, as the report does not pass empty ones either
		final StringBuilder sql = new StringBuilder("SELECT * FROM " + FUNCTION + "(p_AD_Org_ID => ?::numeric, p_C_Year_ID => ?::numeric, p_M_Product_ID => ?::numeric");
		final List<Object> sqlParams = new ArrayList<>(Arrays.<Object>asList(orgId, yearId, productFromId));
		if (productToId != null)
		{
			sql.append(", p_M_Product_To_ID => ?::numeric");
			sqlParams.add(productToId);
		}
		if (categoryId != null)
		{
			sql.append(", p_M_Product_Category_ID => ?::numeric");
			sqlParams.add(categoryId);
		}
		sql.append(")");

		return DB.retrieveRowsOutOfTrx(
				sql.toString(),
				sqlParams,
				ProductStatisticsYearComparison_StepDef::toRow);
	}

	private Integer toProductIdOrNull(@NonNull final String productIdentifier)
	{
		return "-".equals(productIdentifier)
				? null
				: productTable.get(StepDefDataIdentifier.ofString(productIdentifier)).getM_Product_ID();
	}

	/**
	 * Calls {@code report.Product_Statistics_Year_Comparison} and fails if the result contains a row for any of the listed products.
	 *
	 * @param orgIdentifier     {@code AD_Org} identifier, or {@code -} for the standard org
	 * @param yearIdentifier    {@code C_Year} identifier (never the FiscalYear text)
	 * @param productIdentifier {@code M_Product} identifier, or {@code -} for all products
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: AD_Org_StepDefData, C_Year_StepDefData, M_Product_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the product statistics year comparison report for org "-", year "y2026", product "-" contains no rows for products:
	 *   | M_Product_ID |
	 *   | pallet       |
	 * </pre>
	 */
	@Then("the product statistics year comparison report for org {string}, year {string}, product {string} contains no rows for products:")
	public void expect_no_rows_for_products(
			@NonNull final String orgIdentifier,
			@NonNull final String yearIdentifier,
			@NonNull final String productIdentifier,
			@NonNull final DataTable dataTable)
	{
		assertNoRowsForProducts(retrieveReportRows(orgIdentifier, yearIdentifier, productIdentifier, "-", "-"), dataTable);
	}

	/**
	 * As {@link #expect_no_rows_for_products}, with a product range and a product category.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: AD_Org_StepDefData, C_Year_StepDefData, M_Product_StepDefData, M_Product_Category_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the product statistics year comparison report for org "-", year "y2026", products "p1" to "p3", category "-" contains no rows for products:
	 *   | M_Product_ID |
	 *   | p4           |
	 * </pre>
	 */
	@Then("the product statistics year comparison report for org {string}, year {string}, products {string} to {string}, category {string} contains no rows for products:")
	public void expect_no_rows_for_products_for_range(
			@NonNull final String orgIdentifier,
			@NonNull final String yearIdentifier,
			@NonNull final String productFromIdentifier,
			@NonNull final String productToIdentifier,
			@NonNull final String categoryIdentifier,
			@NonNull final DataTable dataTable)
	{
		assertNoRowsForProducts(retrieveReportRows(orgIdentifier, yearIdentifier, productFromIdentifier, productToIdentifier, categoryIdentifier), dataTable);
	}

	private void assertNoRowsForProducts(@NonNull final List<Map<String, Object>> actualRows, @NonNull final DataTable dataTable)
	{
		if (!dataTable.row(0).stream().anyMatch(COLUMN_M_Product_ID::equalsIgnoreCase) || dataTable.row(0).size() != 1)
		{
			throw new AssertionError("The table needs exactly one column " + COLUMN_M_Product_ID);
		}
		final List<String> productIdentifiers = dataTable.asList().subList(1, dataTable.asList().size());
		if (productIdentifiers.isEmpty())
		{
			throw new AssertionError("The table needs at least one product row");
		}

		final SoftAssertions softly = new SoftAssertions();
		for (final String listedIdentifier : productIdentifiers)
		{
			final int listedProductId = productTable.get(StepDefDataIdentifier.ofString(listedIdentifier)).getM_Product_ID();
			final long rowCount = actualRows.stream()
					.filter(r -> listedProductId == ((BigDecimal)r.get(COLUMN_M_Product_ID.toLowerCase())).intValue())
					.count();
			if (rowCount > 0)
			{
				softly.fail("Product " + listedIdentifier + " (M_Product_ID=" + listedProductId + ") is expected to be absent but " + FUNCTION
						+ " returned " + rowCount + " row(s) for it");
			}
		}
		softly.assertAll();
	}

	private static void compareCell(final SoftAssertions softly, final String product, final String column, final String expectedRaw, final Object actual)
	{
		final String prefix = "Product " + product + ", column " + column + ": ";
		final String expected = expectedRaw == null || expectedRaw.trim().isEmpty() ? null : expectedRaw;
		if (expected == null)
		{
			if (actual != null)
			{
				softly.fail(prefix + "expected NULL but was <" + actual + ">");
			}
			return;
		}
		if (actual == null)
		{
			softly.fail(prefix + "expected <" + expected + "> but was NULL");
			return;
		}
		if (TEXT_COLUMNS.contains(column.toLowerCase()))
		{
			if (!expected.equals(actual.toString()))
			{
				softly.fail(prefix + "expected <" + expected + "> but was <" + actual + ">");
			}
			return;
		}
		final BigDecimal expectedNumber;
		try
		{
			expectedNumber = new BigDecimal(expected.trim());
		}
		catch (final NumberFormatException ex)
		{
			softly.fail(prefix + "expected value <" + expected + "> is not a number");
			return;
		}
		if (expectedNumber.compareTo((BigDecimal)actual) != 0)
		{
			softly.fail(prefix + "expected <" + expected + "> but was <" + actual + ">");
		}
	}

	private static void assertFunctionExists()
	{
		final int count = DB.getSQLValueEx(
				ITrx.TRXNAME_None,
				"SELECT COUNT(*) FROM pg_proc p JOIN pg_namespace n ON p.pronamespace = n.oid"
						+ " WHERE n.nspname = 'report' AND lower(p.proname) = 'product_statistics_year_comparison'");
		if (count == 0)
		{
			throw new AssertionError("The DB function " + FUNCTION + " does not exist - the report migration/DDL has not been applied");
		}
	}

	/** Reads all columns; integer columns become BigDecimal too, so numeric comparison is uniform. Keys are lower-case. */
	private static Map<String, Object> toRow(final ResultSet rs) throws SQLException
	{
		final Map<String, Object> row = new TreeMap<>();
		final java.sql.ResultSetMetaData md = rs.getMetaData();
		for (int i = 1; i <= md.getColumnCount(); i++)
		{
			final Object value = rs.getObject(i);
			row.put(md.getColumnLabel(i).toLowerCase(), value instanceof Number && !(value instanceof BigDecimal) ? rs.getBigDecimal(i) : value);
		}
		return row;
	}
}
