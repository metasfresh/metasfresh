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
import java.util.Arrays;

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
 * <p><b>Usage</b> — level is one of {@code 1}, {@code 2}, {@code 3}, {@code 4}, {@code ReCap}:
 * <pre>{@code
 * Then report_taxaccounts level "4" for C_Tax "salesTax19" between "2024-01-01" and "2024-01-31" returns:
 *   | TaxAmt | NetAmt |
 *   | -190   | -1000  |
 *
 * Then report_taxaccounts level "1" for C_Tax "salesTax19" between "2024-01-01" and "2024-01-31" returns:
 *   | TaxAmt_SUM | NetAmt_SUM |
 *   | -190       | -1000      |
 * }</pre>
 *
 * <p>Expected rows are matched to actual rows <b>positionally</b> (ordered by AccountName, DocumentNo,
 * TaxAmt). Only columns present in the expected DataTable are asserted — other columns are ignored.
 *
 * <p><b>Supported columns</b> (all optional; only the columns listed in the DataTable are checked):
 * <ul>
 *     <li>Numeric (compared with {@code isEqualByComparingTo}):
 *         {@code TaxAmt}, {@code NetAmt} (levels 4 only),
 *         {@code TaxAmt_SUM}, {@code NetAmt_SUM} (levels 1, 2, 3, ReCap)</li>
 *     <li>String: {@code AccountName}, {@code TaxName}, {@code VatCode},
 *         {@code DocumentNo}, {@code BPartnerName}</li>
 * </ul>
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29361">me03#29361</a>
 */
@RequiredArgsConstructor
public class TaxAccountingReport_StepDef
{
	@NonNull private final C_Tax_StepDefData taxTable;

	/**
	 * Calls {@code de_metas_acct.report_taxaccounts(...)} filtered by C_Tax_ID, date range and
	 * {@code p_level}. Asserts the returned rows match the expected data table positionally,
	 * field by field.
	 */
	@Then("report_taxaccounts level {string} for C_Tax {string} between {string} and {string} returns:")
	public void expect_report_taxaccounts(
			@NonNull final String level,
			@NonNull final String taxIdentifier,
			@NonNull final String dateFromStr,
			@NonNull final String dateToStr,
			@NonNull final DataTable dataTable)
	{
		final TaxId taxId = taxTable.get(taxIdentifier).getTaxId();
		final LocalDate dateFrom = LocalDate.parse(dateFromStr);
		final LocalDate dateTo = LocalDate.parse(dateToStr);

		final ImmutableList<TaxReportRow> actualRows = queryReportRows(StepDefConstants.ORG_ID, taxId, dateFrom, dateTo, level);
		final ImmutableList<DataTableRow> expectedRows = DataTableRows.of(dataTable).stream().collect(ImmutableList.toImmutableList());

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(actualRows.size())
				.as("report_taxaccounts level %s (C_Tax=%s): row count", level, taxIdentifier)
				.isEqualTo(expectedRows.size());

		final int common = Math.min(actualRows.size(), expectedRows.size());
		for (int i = 0; i < common; i++)
		{
			assertRowMatches(softly, expectedRows.get(i), actualRows.get(i), level, taxIdentifier, i);
		}

		softly.assertAll();
	}

	private static void assertRowMatches(
			@NonNull final SoftAssertions softly,
			@NonNull final DataTableRow expected,
			@NonNull final TaxReportRow actual,
			@NonNull final String level,
			@NonNull final String taxIdentifier,
			final int rowIndex)
	{
		final String ctx = String.format("report_taxaccounts level %s (C_Tax=%s), row %d", level, taxIdentifier, rowIndex);

		// per-document (level 4) amounts
		expected.getAsOptionalString("TaxAmt").ifPresent(exp ->
				softly.assertThat(toBigDecimal(actual.getTaxAmt()))
						.as("%s: TaxAmt", ctx)
						.isEqualByComparingTo(new BigDecimal(exp)));
		expected.getAsOptionalString("NetAmt").ifPresent(exp ->
				softly.assertThat(toBigDecimal(actual.getNetAmt()))
						.as("%s: NetAmt", ctx)
						.isEqualByComparingTo(new BigDecimal(exp)));

		// summed amounts (levels 1, 2, 3, ReCap)
		expected.getAsOptionalString("TaxAmt_SUM").ifPresent(exp ->
				softly.assertThat(toBigDecimal(actual.getTaxAmtSum()))
						.as("%s: TaxAmt_SUM", ctx)
						.isEqualByComparingTo(new BigDecimal(exp)));
		expected.getAsOptionalString("NetAmt_SUM").ifPresent(exp ->
				softly.assertThat(toBigDecimal(actual.getNetAmtSum()))
						.as("%s: NetAmt_SUM", ctx)
						.isEqualByComparingTo(new BigDecimal(exp)));

		expected.getAsOptionalString("AccountName").ifPresent(exp ->
				softly.assertThat(actual.getAccountName()).as("%s: AccountName", ctx).isEqualTo(exp));
		expected.getAsOptionalString("TaxName").ifPresent(exp ->
				softly.assertThat(actual.getTaxName()).as("%s: TaxName", ctx).isEqualTo(exp));
		expected.getAsOptionalString("VatCode").ifPresent(exp ->
				softly.assertThat(actual.getVatCode()).as("%s: VatCode", ctx).isEqualTo(exp));
		expected.getAsOptionalString("DocumentNo").ifPresent(exp ->
				softly.assertThat(actual.getDocumentNo()).as("%s: DocumentNo", ctx).isEqualTo(exp));
		expected.getAsOptionalString("BPartnerName").ifPresent(exp ->
				softly.assertThat(actual.getBpartnerName()).as("%s: BPartnerName", ctx).isEqualTo(exp));
	}

	private ImmutableList<TaxReportRow> queryReportRows(
			@NonNull final OrgId orgId,
			@NonNull final TaxId taxId,
			@NonNull final LocalDate dateFrom,
			@NonNull final LocalDate dateTo,
			@NonNull final String level)
	{
		final String sql = "SELECT vatcode, accountname, taxname,"
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
				+ " )"
				+ " ORDER BY accountname NULLS FIRST, documentno NULLS FIRST, taxamt NULLS FIRST";

		return DB.retrieveRowsOutOfTrx(
				sql,
				Arrays.asList(orgId.getRepoId(), dateFrom, dateTo, level, taxId.getRepoId()),
				rs -> {
					final CurrencyCode acctCurrency = CurrencyCode.ofThreeLetterCode(rs.getString("currency"));
					final CurrencyCode sourceCurrency = CurrencyCode.ofThreeLetterCode(rs.getString("source_currency"));
					return TaxReportRow.builder()
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

	@Nullable
	private static BigDecimal toBigDecimal(@Nullable final Amount amount)
	{
		return amount != null ? amount.toBigDecimal() : null;
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
		@Nullable Amount taxAmtSum;
		@Nullable Amount netAmtSum;
		@Nullable String documentNo;
		@Nullable String bpartnerName;
	}
}
