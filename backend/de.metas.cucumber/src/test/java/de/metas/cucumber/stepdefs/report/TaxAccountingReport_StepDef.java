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
import de.metas.acct.AccountConceptualName;
import de.metas.cucumber.stepdefs.C_Tax_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.organization.OrgId;
import de.metas.tax.api.TaxId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.assertj.core.api.SoftAssertions;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
 *     <li>{@code TaxAmt} — expected tax amount (signed BigDecimal, compared numerically)</li>
 * </ul>
 * <p><b>Optional columns</b>:
 * <ul>
 *     <li>{@code TaxBaseAmt} — expected base amount (signed BigDecimal)</li>
 *     <li>{@code DocumentNo} — invoice DocumentNo. Use {@code <any>} to skip this column for matching</li>
 *     <li>{@code TaxName}, {@code AccountNo}, {@code AccountName}</li>
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
			@NonNull final DataTable dataTable)
	{
		final TaxId taxId = taxTable.get(taxIdentifier).getTaxId();
		final LocalDate dateFrom = LocalDate.parse(dateFromStr);
		final LocalDate dateTo = LocalDate.parse(dateToStr);

		final ImmutableList<TaxReportRow> actualRows = queryViewRows(taxId, dateFrom, dateTo);

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
			@NonNull final DataTable dataTable)
	{
		final TaxId taxId = taxTable.get(taxIdentifier).getTaxId();
		final LocalDate dateFrom = LocalDate.parse(dateFromStr);
		final LocalDate dateTo = LocalDate.parse(dateToStr);

		final ImmutableList<TaxReportRow> actualRows = queryReportLevel4Rows(StepDefConstants.ORG_ID, taxId, dateFrom, dateTo);

		assertRowsMatch(dataTable, actualRows, "report_taxaccounts level 4 (C_Tax=" + taxIdentifier + ")");
	}

	private ImmutableList<TaxReportRow> queryViewRows(
			@NonNull final TaxId taxId,
			@NonNull final LocalDate dateFrom,
			@NonNull final LocalDate dateTo)
	{
		final String sql = "SELECT v.accountconceptualname,"
				+ "        v.taxamt,"
				+ "        v.taxbaseamt,"
				+ "        v.documentno,"
				+ "        v.taxname,"
				+ "        v.currency,"
				+ "        ev.value         AS accountno,"
				+ "        ev.name          AS accountname"
				+ " FROM de_metas_acct.tax_accounts_details_v v"
				+ " LEFT JOIN C_ElementValue ev ON ev.C_ElementValue_ID = v.account_id"
				+ " WHERE v.C_Tax_ID = ?"
				+ "   AND v.DateAcct >= ? AND v.DateAcct <= ?"
				+ "   AND v.postingtype = 'A'"
				+ " ORDER BY v.accountconceptualname, v.documentno, v.taxamt";

		return DB.retrieveRowsOutOfTrx(sql, Arrays.asList(taxId.getRepoId(), dateFrom, dateTo), rs -> {
			final CurrencyCode currencyCode = CurrencyCode.ofThreeLetterCode(rs.getString("currency"));
			return TaxReportRow.builder()
					.accountConceptualName(AccountConceptualName.ofNullableString(rs.getString("accountconceptualname")))
					.taxAmt(amountOrNull(rs.getBigDecimal("taxamt"), currencyCode))
					.netAmt(amountOrNull(rs.getBigDecimal("taxbaseamt"), currencyCode))
					.documentNo(rs.getString("documentno"))
					.taxName(rs.getString("taxname"))
					.accountNo(rs.getString("accountno"))
					.accountName(rs.getString("accountname"))
					.build();
		});
	}

	private ImmutableList<TaxReportRow> queryReportLevel4Rows(
			@NonNull final OrgId orgId,
			@NonNull final TaxId taxId,
			@NonNull final LocalDate dateFrom,
			@NonNull final LocalDate dateTo)
	{
		final String sql = "SELECT level, vatcode, accountname, taxname, netamt, taxamt, currency, documentno, bpartnername"
				+ " FROM de_metas_acct.report_taxaccounts("
				+ "      p_ad_org_id      => ?,"
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

		return DB.retrieveRowsOutOfTrx(
				sql,
				Arrays.asList(orgId.getRepoId(), dateFrom, dateTo, taxId.getRepoId()),
				rs -> {
					final CurrencyCode currencyCode = CurrencyCode.ofThreeLetterCode(rs.getString("currency"));
					return TaxReportRow.builder()
							.level(rs.getString("level"))
							.vatCode(rs.getString("vatcode"))
							.accountName(rs.getString("accountname"))
							.taxName(rs.getString("taxname"))
							.netAmt(amountOrNull(rs.getBigDecimal("netamt"), currencyCode))
							.taxAmt(amountOrNull(rs.getBigDecimal("taxamt"), currencyCode))
							.documentNo(rs.getString("documentno"))
							.bpartnerName(rs.getString("bpartnername"))
							.build();
				});
	}

	@Nullable
	private static Amount amountOrNull(@Nullable final BigDecimal value, @NonNull final CurrencyCode currencyCode)
	{
		return value != null ? Amount.of(value, currencyCode) : null;
	}

	private void assertRowsMatch(
			@NonNull final DataTable expected,
			@NonNull final List<TaxReportRow> actualRows,
			@NonNull final String contextMessage)
	{
		final DataTableRows expectedRows = DataTableRows.of(expected);
		final SoftAssertions softly = new SoftAssertions();

		softly.assertThat(actualRows.size())
				.as(contextMessage + ": number of rows returned")
				.isEqualTo(expectedRows.size());

		final List<TaxReportRow> remaining = new ArrayList<>(actualRows);

		expectedRows.forEach(expectedRow -> {
			final TaxReportRow match = findAndRemoveMatch(expectedRow, remaining);
			softly.assertThat(match)
					.as(contextMessage + ": no match for expected row " + renderExpected(expectedRow))
					.isNotNull();
		});

		softly.assertThat(remaining)
				.as(contextMessage + ": unexpected extra rows returned")
				.isEmpty();

		softly.assertAll();
	}

	@Nullable
	private TaxReportRow findAndRemoveMatch(
			@NonNull final DataTableRow expectedRow,
			@NonNull final List<TaxReportRow> candidates)
	{
		for (int i = 0; i < candidates.size(); i++)
		{
			final TaxReportRow candidate = candidates.get(i);
			if (matches(expectedRow, candidate))
			{
				candidates.remove(i);
				return candidate;
			}
		}
		return null;
	}

	private boolean matches(@NonNull final DataTableRow expectedRow, @NonNull final TaxReportRow actual)
	{
		for (final java.util.Map.Entry<String, String> expected : expectedRow.asMap().entrySet())
		{
			final String column = expected.getKey();
			final String expectedVal = expected.getValue();
			if ("<any>".equals(expectedVal) || expectedVal == null)
			{
				continue;
			}
			final String actualVal = actual.getAsStringForColumn(column);
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

	private static String renderExpected(@NonNull final DataTableRow row)
	{
		return row.asMap().toString();
	}

	@Value
	@Builder
	public static class TaxReportRow
	{
		@Nullable String level;
		@Nullable String vatCode;
		@Nullable AccountConceptualName accountConceptualName;
		@Nullable String accountNo;
		@Nullable String accountName;
		@Nullable String taxName;
		@Nullable Amount taxAmt;
		@Nullable Amount netAmt;
		@Nullable String documentNo;
		@Nullable String bpartnerName;

		@Nullable
		public String getAsStringForColumn(@NonNull final String column)
		{
			switch (column)
			{
				case "Level":
					return level;
				case "VatCode":
					return vatCode;
				case "AccountConceptualName":
					return accountConceptualName != null ? accountConceptualName.getAsString() : null;
				case "AccountNo":
					return accountNo;
				case "AccountName":
					return accountName;
				case "TaxName":
					return taxName;
				case "TaxAmt":
					return taxAmt != null ? taxAmt.toBigDecimal().stripTrailingZeros().toPlainString() : null;
				case "TaxBaseAmt":
				case "NetAmt":
					return netAmt != null ? netAmt.toBigDecimal().stripTrailingZeros().toPlainString() : null;
				case "DocumentNo":
					return documentNo;
				case "BPartnerName":
					return bpartnerName;
				default:
					return null;
			}
		}
	}
}
