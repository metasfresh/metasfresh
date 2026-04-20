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

import de.metas.cucumber.stepdefs.C_Tax_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.tax.api.Tax;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrx;
import org.assertj.core.api.SoftAssertions;
import org.compiere.util.DB;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Step definitions for testing the Tax Accounting Report ("Mehrwertsteuer-Verprobung 3").
 * <p>
 * Exercises two backends:
 * <ul>
 *     <li>The user-facing function {@code de_metas_acct.report_taxaccounts(...)} — called by AD_Process 585325</li>
 *     <li>The underlying view {@code de_metas_acct.tax_accounts_details_v} — raw per-Fact_Acct rows</li>
 * </ul>
 * <p>
 * For reliable, scenario-isolated tests, both the function and the view are filtered by {@code C_Tax_ID}.
 * The function's {@code p_c_tax_id} parameter was added as part of me03#29361 for this purpose.
 *
 * <p><b>Usage — view query</b>:
 * <pre>{@code
 * Then the tax_accounts_details_v for C_Tax "rcTax19" between "2024-01-01" and "2024-01-31" returns:
 *   | AccountConceptualName | TaxAmt | TaxBaseAmt | DocumentNo  |
 *   | T_Credit_Acct         | 190    | 1000       | <any>       |
 *   | T_Due_Acct            | -190   | 1000       | <any>       |
 * }</pre>
 *
 * <p><b>Required columns for view assertions</b>:
 * <ul>
 *     <li>{@code AccountConceptualName} — one of {@code T_Credit_Acct}, {@code T_Due_Acct}</li>
 *     <li>{@code TaxAmt} — expected tax amount (signed BigDecimal)</li>
 * </ul>
 * <p><b>Optional columns</b>:
 * <ul>
 *     <li>{@code TaxBaseAmt} — expected base amount (signed BigDecimal)</li>
 *     <li>{@code DocumentNo} — invoice DocumentNo. Use {@code <any>} to skip this column for matching</li>
 * </ul>
 *
 * <p><b>Usage — function output (Level 4 detail rows)</b>:
 * <pre>{@code
 * Then report_taxaccounts level 4 for C_Tax "rcTax19" between "2024-01-01" and "2024-01-31" returns:
 *   | AccountName | TaxAmt | NetAmt |
 *   | T_Credit... | 190    | 1000   |
 *   | T_Due...    | -190   | 1000   |
 * }</pre>
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29361">me03#29361</a>
 */
@RequiredArgsConstructor
public class TaxAccountingReport_StepDef
{
	@NonNull private final C_Tax_StepDefData taxTable;

	/**
	 * Queries {@code de_metas_acct.tax_accounts_details_v} filtered by C_Tax_ID and date range.
	 * Asserts that the returned rows match the expected data table.
	 *
	 * @see #expect_report_taxaccounts_level_4(String, String, String, DataTable) for the function-level assertion
	 */
	@Then("the tax_accounts_details_v for C_Tax {string} between {string} and {string} returns:")
	public void expect_tax_accounts_details_v(
			@NonNull final String taxIdentifier,
			@NonNull final String dateFromStr,
			@NonNull final String dateToStr,
			@NonNull final DataTable dataTable) throws SQLException
	{
		final Tax tax = taxTable.get(taxIdentifier);
		final LocalDate dateFrom = LocalDate.parse(dateFromStr);
		final LocalDate dateTo = LocalDate.parse(dateToStr);

		final List<Map<String, String>> actualRows = queryViewRows(tax.getTaxId().getRepoId(), dateFrom, dateTo);

		assertRowsMatch(dataTable, actualRows, "tax_accounts_details_v (C_Tax=" + taxIdentifier + ")");
	}

	/**
	 * Calls {@code de_metas_acct.report_taxaccounts(...)} filtered by C_Tax_ID and date range, at level='4'
	 * (per-document detail rows). Asserts that the returned rows match the expected data table.
	 *
	 * @see #expect_tax_accounts_details_v(String, String, String, DataTable) for the raw view assertion
	 */
	@Then("report_taxaccounts level 4 for C_Tax {string} between {string} and {string} returns:")
	public void expect_report_taxaccounts_level_4(
			@NonNull final String taxIdentifier,
			@NonNull final String dateFromStr,
			@NonNull final String dateToStr,
			@NonNull final DataTable dataTable) throws SQLException
	{
		final Tax tax = taxTable.get(taxIdentifier);
		final LocalDate dateFrom = LocalDate.parse(dateFromStr);
		final LocalDate dateTo = LocalDate.parse(dateToStr);

		final List<Map<String, String>> actualRows = queryReportLevel4Rows(tax.getTaxId().getRepoId(), dateFrom, dateTo);

		assertRowsMatch(dataTable, actualRows, "report_taxaccounts level 4 (C_Tax=" + taxIdentifier + ")");
	}

	private List<Map<String, String>> queryViewRows(final int taxId, final LocalDate dateFrom, final LocalDate dateTo) throws SQLException
	{
		final String sql = "SELECT v.accountconceptualname,"
				+ "        v.taxamt,"
				+ "        v.taxbaseamt,"
				+ "        v.documentno,"
				+ "        v.taxname,"
				+ "        ev.value         AS accountno,"
				+ "        ev.name          AS accountname"
				+ " FROM de_metas_acct.tax_accounts_details_v v"
				+ " LEFT JOIN C_ElementValue ev ON ev.C_ElementValue_ID = v.account_id"
				+ " WHERE v.C_Tax_ID = ?"
				+ "   AND v.DateAcct >= ? AND v.DateAcct <= ?"
				+ "   AND v.postingtype = 'A'"
				+ " ORDER BY v.accountconceptualname, v.documentno, v.taxamt";

		final List<Map<String, String>> rows = new ArrayList<>();
		try (final PreparedStatement pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None))
		{
			pstmt.setInt(1, taxId);
			pstmt.setObject(2, dateFrom);
			pstmt.setObject(3, dateTo);
			try (final ResultSet rs = pstmt.executeQuery())
			{
				while (rs.next())
				{
					final Map<String, String> row = new LinkedHashMap<>();
					row.put("AccountConceptualName", rs.getString("accountconceptualname"));
					row.put("TaxAmt", bdToString(rs.getBigDecimal("taxamt")));
					row.put("TaxBaseAmt", bdToString(rs.getBigDecimal("taxbaseamt")));
					row.put("DocumentNo", rs.getString("documentno"));
					row.put("TaxName", rs.getString("taxname"));
					row.put("AccountNo", rs.getString("accountno"));
					row.put("AccountName", rs.getString("accountname"));
					rows.add(row);
				}
			}
		}
		return rows;
	}

	private List<Map<String, String>> queryReportLevel4Rows(final int taxId, final LocalDate dateFrom, final LocalDate dateTo) throws SQLException
	{
		// p_ad_org_id=NULL means ALL orgs — the function still requires NOT NULL match, so use the view's org (1000000 is the default metasfresh org).
		// Better: look up the org from the tax's client/org.
		// For test isolation we rely on p_c_tax_id filtering — the org filter just needs to match the posting org.
		final String sql = "SELECT level, vatcode, accountname, taxname, netamt, taxamt, documentno, bpartnername"
				+ " FROM de_metas_acct.report_taxaccounts("
				+ "      p_ad_org_id      => (SELECT fa.ad_org_id FROM fact_acct fa WHERE fa.c_tax_id = ? LIMIT 1),"
				+ "      p_account_id     => NULL,"
				+ "      p_c_vat_code_id  => NULL,"
				+ "      p_datefrom       => ?::date,"
				+ "      p_dateto         => ?::date,"
				+ "      p_isshowdetails  => 'Y',"
				+ "      p_level          => '4',"
				+ "      p_ad_language    => 'de_DE',"
				+ "      p_c_tax_id       => ?"
				+ " )"
				+ " ORDER BY accountname, documentno, taxamt";

		final List<Map<String, String>> rows = new ArrayList<>();
		try (final PreparedStatement pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None))
		{
			pstmt.setInt(1, taxId);
			pstmt.setObject(2, dateFrom);
			pstmt.setObject(3, dateTo);
			pstmt.setInt(4, taxId);
			try (final ResultSet rs = pstmt.executeQuery())
			{
				while (rs.next())
				{
					final Map<String, String> row = new LinkedHashMap<>();
					row.put("Level", rs.getString("level"));
					row.put("VatCode", rs.getString("vatcode"));
					row.put("AccountName", rs.getString("accountname"));
					row.put("TaxName", rs.getString("taxname"));
					row.put("NetAmt", bdToString(rs.getBigDecimal("netamt")));
					row.put("TaxAmt", bdToString(rs.getBigDecimal("taxamt")));
					row.put("DocumentNo", rs.getString("documentno"));
					row.put("BPartnerName", rs.getString("bpartnername"));
					rows.add(row);
				}
			}
		}
		return rows;
	}

	private void assertRowsMatch(
			@NonNull final DataTable expected,
			@NonNull final List<Map<String, String>> actualRows,
			@NonNull final String contextMessage)
	{
		final DataTableRows expectedRows = DataTableRows.of(expected);
		final SoftAssertions softly = new SoftAssertions();

		softly.assertThat(actualRows.size())
				.as(contextMessage + ": number of rows returned")
				.isEqualTo(expectedRows.size());

		final List<Map<String, String>> remaining = new ArrayList<>(actualRows);

		expectedRows.forEach(expectedRow -> {
			final Map<String, String> match = findAndRemoveMatch(expectedRow, remaining);
			softly.assertThat(match)
					.as(contextMessage + ": no match for expected row " + renderExpected(expectedRow))
					.isNotNull();
		});

		softly.assertThat(remaining)
				.as(contextMessage + ": unexpected extra rows returned")
				.isEmpty();

		softly.assertAll();
	}

	private Map<String, String> findAndRemoveMatch(
			@NonNull final DataTableRow expectedRow,
			@NonNull final List<Map<String, String>> candidates)
	{
		for (int i = 0; i < candidates.size(); i++)
		{
			final Map<String, String> candidate = candidates.get(i);
			if (matches(expectedRow, candidate))
			{
				candidates.remove(i);
				return candidate;
			}
		}
		return null;
	}

	private boolean matches(@NonNull final DataTableRow expectedRow, @NonNull final Map<String, String> actual)
	{
		for (final Map.Entry<String, String> expected : expectedRow.asMap().entrySet())
		{
			final String column = expected.getKey();
			final String expectedVal = expected.getValue();
			if ("<any>".equals(expectedVal) || expectedVal == null)
			{
				continue;
			}
			final String actualVal = actual.get(column);
			if (isNumericColumn(column))
			{
				if (!numericEquals(expectedVal, actualVal))
				{
					return false;
				}
			}
			else
			{
				if (!Objects.equals(nullToBlank(expectedVal), nullToBlank(actualVal)))
				{
					return false;
				}
			}
		}
		return true;
	}

	private static boolean isNumericColumn(@NonNull final String column)
	{
		return "TaxAmt".equals(column)
				|| "TaxBaseAmt".equals(column)
				|| "NetAmt".equals(column);
	}

	private static boolean numericEquals(final String expected, final String actual)
	{
		if (expected == null || expected.isEmpty()) { return actual == null || actual.isEmpty(); }
		if (actual == null) { return false; }
		try
		{
			return new BigDecimal(expected).compareTo(new BigDecimal(actual)) == 0;
		}
		catch (final NumberFormatException e)
		{
			return Objects.equals(expected, actual);
		}
	}

	private static String nullToBlank(final String s) { return s == null ? "" : s; }

	private static String bdToString(final BigDecimal v)
	{
		return v == null ? null : v.stripTrailingZeros().toPlainString();
	}

	private static String renderExpected(@NonNull final DataTableRow row)
	{
		return row.asMap().toString();
	}
}
