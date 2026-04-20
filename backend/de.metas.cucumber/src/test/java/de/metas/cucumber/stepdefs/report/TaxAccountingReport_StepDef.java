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
 * Targets the user-facing DB function {@code de_metas_acct.report_taxaccounts(...)} — the same function
 * invoked by AD_Process 585325. The underlying view {@code de_metas_acct.tax_accounts_details_v} is
 * an implementation detail and is not asserted against directly.
 * <p>
 * For reliable, scenario-isolated tests, the function is filtered by {@code C_Tax_ID}. The
 * {@code p_c_tax_id} parameter was added as part of me03#29361 for this purpose.
 *
 * <p><b>Usage — function output at level 4 (per-document detail rows)</b>:
 * <pre>{@code
 * Then report_taxaccounts level 4 for C_Tax "rcTax19" between "2024-01-01" and "2024-01-31" returns:
 *   | AccountName   | TaxAmt | NetAmt |
 *   | T_Credit_Acct | 190    | 1000   |
 *   | T_Due_Acct    | -190   | 1000   |
 * }</pre>
 *
 * <p><b>Required columns</b>:
 * <ul>
 *     <li>{@code TaxAmt} — expected tax amount in the accounting schema's currency (signed, compared numerically)</li>
 * </ul>
 * <p><b>Optional columns</b>:
 * <ul>
 *     <li>{@code NetAmt} — expected base amount in the invoice's source currency (signed)</li>
 *     <li>{@code AccountName}, {@code TaxName}, {@code VatCode}, {@code DocumentNo}, {@code BPartnerName}</li>
 *     <li>{@code <any>} — use this literal on any column to skip it for matching</li>
 * </ul>
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29361">me03#29361</a>
 */
@RequiredArgsConstructor
public class TaxAccountingReport_StepDef
{
	@NonNull private final C_Tax_StepDefData taxTable;

	/**
	 * Calls {@code de_metas_acct.report_taxaccounts(...)} filtered by C_Tax_ID and date range, at
	 * {@code p_level='4'} (per-document detail rows). Asserts that the returned rows match the expected data table.
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

	private ImmutableList<TaxReportRow> queryReportLevel4Rows(
			@NonNull final OrgId orgId,
			@NonNull final TaxId taxId,
			@NonNull final LocalDate dateFrom,
			@NonNull final LocalDate dateTo)
	{
		final String sql = "SELECT vatcode, accountname, taxname, netamt, taxamt, currency, source_currency, documentno, bpartnername"
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
					final CurrencyCode acctCurrency = CurrencyCode.ofThreeLetterCode(rs.getString("currency"));
					final CurrencyCode sourceCurrency = CurrencyCode.ofThreeLetterCode(rs.getString("source_currency"));
					return TaxReportRow.builder()
							.vatCode(rs.getString("vatcode"))
							.accountName(rs.getString("accountname"))
							.taxName(rs.getString("taxname"))
							.taxAmt(amountOrNull(rs.getBigDecimal("taxamt"), acctCurrency))
							.netAmt(amountOrNull(rs.getBigDecimal("netamt"), sourceCurrency))
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
		@Nullable String vatCode;
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
				case "VatCode":
					return vatCode;
				case "AccountName":
					return accountName;
				case "TaxName":
					return taxName;
				case "TaxAmt":
					return taxAmt != null ? taxAmt.toBigDecimal().stripTrailingZeros().toPlainString() : null;
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
