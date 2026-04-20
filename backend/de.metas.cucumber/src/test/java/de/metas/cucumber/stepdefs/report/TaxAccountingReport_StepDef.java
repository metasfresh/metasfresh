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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.SoftAssertions;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

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
 * <p><b>Why two calls</b>: {@code p_level=NULL} returns levels 1/2/3/4 (see the
 * {@code p_level IS NULL OR p_level = '<n>'} conditions in the function body), but <b>not</b> ReCap —
 * ReCap is only produced when {@code p_level='ReCap'}. So the step does one call with NULL and one
 * with ReCap and concatenates the results.
 *
 * <p><b>Matching</b>: for each expected row, the step finds the first actual row whose non-blank
 * columns all match (blank cells are "don't care"). The match is then removed from the candidate
 * pool so the same actual row can't be used twice. Any expected row with no match fails the step;
 * any actual row not matched by an expected row fails it too. This avoids coupling to the DB's row
 * order and lets feature authors list rows in whatever order reads best.
 *
 * <p><b>Usage</b>: each expected row uses {@code Level} to say which aggregation level it addresses;
 * only columns present in the DataTable are asserted.
 * <pre>{@code
 * Then report_taxaccounts for C_Tax "salesTax19" between "2024-01-01" and "2024-01-31" returns:
 *   | Level | AccountName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt |
 *   | 1     |             | -1000      | -190       |        |        |
 *   | 2     | T_Due_Acct  | -1000      | -190       |        |        |
 *   | 3     | T_Due_Acct  | -1000      | -190       |        |        |
 *   | 4     | T_Due_Acct  |            |            | -1000  | -190   |
 *   | ReCap |             | -1000      | -190       |        |        |
 * }</pre>
 *
 * <p>Numeric columns are parsed via {@link DataTableRow#getAsOptionalAmount(String, Supplier)} —
 * a bare number inherits the actual row's currency, {@code 190 CHF} forces a currency check too.
 *
 * <p><b>Supported columns</b>: {@code Level}, {@code VatCode}, {@code AccountName}, {@code TaxName},
 * {@code NetAmt_SUM}, {@code TaxAmt_SUM} (levels 1/2/3/ReCap), {@code NetAmt}, {@code TaxAmt}
 * (level 4), {@code DocumentNo}, {@code BPartnerName}.
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29361">me03#29361</a>
 */
@RequiredArgsConstructor
public class TaxAccountingReport_StepDef
{
	@NonNull private final C_Tax_StepDefData taxTable;

	/**
	 * Calls {@code de_metas_acct.report_taxaccounts(...)} for all levels (1/2/3/4 via
	 * {@code p_level=NULL}, plus ReCap) and asserts the returned rows against the expected
	 * data table by find-and-remove matching.
	 */
	@Then("report_taxaccounts for C_Tax {string} between {string} and {string} returns:")
	public void expect_report_taxaccounts(
			@NonNull final String taxIdentifier,
			@NonNull final String dateFromStr,
			@NonNull final String dateToStr,
			@NonNull final DataTable dataTable)
	{
		final TaxId taxId = taxTable.get(taxIdentifier).getTaxId();
		final LocalDate dateFrom = LocalDate.parse(dateFromStr);
		final LocalDate dateTo = LocalDate.parse(dateToStr);

		// p_level=NULL returns levels 1/2/3/4 but NOT ReCap, so we need a second call for that.
		final List<TaxReportRow> actualRows = new ArrayList<>();
		actualRows.addAll(queryReportRows(StepDefConstants.ORG_ID, taxId, dateFrom, dateTo, null));
		actualRows.addAll(queryReportRows(StepDefConstants.ORG_ID, taxId, dateFrom, dateTo, "ReCap"));

		final ImmutableList<DataTableRow> expectedRows = DataTableRows.of(dataTable).toList();
		final String ctx = "report_taxaccounts (C_Tax=" + taxIdentifier + ")";
		final SoftAssertions softly = new SoftAssertions();

		// For each expected row, find and remove a matching actual row.
		for (final DataTableRow expected : expectedRows)
		{
			final TaxReportRow match = findAndRemoveMatch(expected, actualRows);
			softly.assertThat(match)
					.as("%s: no actual row matches expected %s", ctx, expected.asMap())
					.isNotNull();
		}

		// Any actual rows left over are unexpected.
		softly.assertThat(actualRows)
				.as("%s: unexpected extra rows returned", ctx)
				.isEmpty();

		softly.assertAll();
	}

	@Nullable
	private static TaxReportRow findAndRemoveMatch(@NonNull final DataTableRow expected, @NonNull final List<TaxReportRow> candidates)
	{
		for (int i = 0; i < candidates.size(); i++)
		{
			if (matches(expected, candidates.get(i)))
			{
				return candidates.remove(i);
			}
		}
		return null;
	}

	/**
	 * Returns true iff every non-blank column in {@code expected} equals the corresponding field on {@code actual}.
	 * Blank cells are "don't care".
	 */
	private static boolean matches(@NonNull final DataTableRow expected, @NonNull final TaxReportRow actual)
	{
		if (!stringMatches(expected, "Level", actual.getLevel())) { return false; }
		if (!stringMatches(expected, "VatCode", actual.getVatCode())) { return false; }
		if (!stringMatches(expected, "AccountName", actual.getAccountName())) { return false; }
		if (!stringMatches(expected, "TaxName", actual.getTaxName())) { return false; }
		if (!stringMatches(expected, "DocumentNo", actual.getDocumentNo())) { return false; }
		if (!stringMatches(expected, "BPartnerName", actual.getBpartnerName())) { return false; }
		if (!amountMatches(expected, "TaxAmt", actual.getTaxAmt())) { return false; }
		if (!amountMatches(expected, "NetAmt", actual.getNetAmt())) { return false; }
		if (!amountMatches(expected, "TaxAmt_SUM", actual.getTaxAmtSum())) { return false; }
		if (!amountMatches(expected, "NetAmt_SUM", actual.getNetAmtSum())) { return false; }
		return true;
	}

	private static boolean stringMatches(@NonNull final DataTableRow expected, @NonNull final String column, @Nullable final String actual)
	{
		return expected.getAsOptionalString(column)
				.map(exp -> exp.equals(actual))
				.orElse(true);
	}

	private static boolean amountMatches(@NonNull final DataTableRow expected, @NonNull final String column, @Nullable final Amount actual)
	{
		final Supplier<CurrencyCode> defaultCurrency = actual != null ? actual::getCurrencyCode : () -> null;
		return expected.getAsOptionalAmount(column, defaultCurrency)
				.map(exp -> exp.equals(actual))
				.orElse(true);
	}

	private ImmutableList<TaxReportRow> queryReportRows(
			@NonNull final OrgId orgId,
			@NonNull final TaxId taxId,
			@NonNull final LocalDate dateFrom,
			@NonNull final LocalDate dateTo,
			@Nullable final String level)
	{
		final String sql = "SELECT level, vatcode, accountname, taxname,"
				+ "        netamt, taxamt, netamt_sum, taxamt_sum,"
				+ "        currency, source_currency, documentno, bpartnername"
				+ " FROM de_metas_acct.report_taxaccounts("
				+ "      p_ad_org_id      => ?,"
				+ "      p_account_id     => NULL,"
				+ "      p_c_vat_code_id  => NULL,"
				+ "      p_datefrom       => ?::date,"
				+ "      p_dateto         => ?::date,"
				+ "      p_isshowdetails  => 'Y',"
				+ "      p_level          => ?,"
				+ "      p_ad_language    => 'de_DE',"
				+ "      p_c_tax_id       => ?"
				+ " )";

		return DB.retrieveRowsOutOfTrx(
				sql,
				Arrays.asList(orgId, dateFrom, dateTo, level, taxId),
				rs -> {
					final CurrencyCode acctCurrency = CurrencyCode.ofThreeLetterCode(rs.getString("currency"));
					final CurrencyCode sourceCurrency = CurrencyCode.ofThreeLetterCode(rs.getString("source_currency"));
					return TaxReportRow.builder()
							.level(rs.getString("level"))
							.vatCode(rs.getString("vatcode"))
							.accountName(rs.getString("accountname"))
							.taxName(rs.getString("taxname"))
							.taxAmt(amountOrNull(rs.getBigDecimal("taxamt"), acctCurrency))
							.netAmt(amountOrNull(rs.getBigDecimal("netamt"), sourceCurrency))
							.taxAmtSum(amountOrNull(rs.getBigDecimal("taxamt_sum"), acctCurrency))
							.netAmtSum(amountOrNull(rs.getBigDecimal("netamt_sum"), sourceCurrency))
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
}
