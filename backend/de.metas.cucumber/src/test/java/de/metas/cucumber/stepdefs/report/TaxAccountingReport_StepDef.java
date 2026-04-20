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
import java.util.Comparator;
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
 * <p>The step calls the function twice — once with {@code p_level=NULL} (returns levels 1/2/3/4) and
 * once with {@code p_level='ReCap'} — and concatenates the results. Rows are sorted by
 * (level, accountname, taxname, taxamt, documentno) so feature files can assert them positionally.
 *
 * <p><b>Usage</b>: each expected row uses {@code Level} to say which aggregation level it addresses;
 * only columns present in the DataTable are asserted.
 * <pre>{@code
 * Then report_taxaccounts for C_Tax "salesTax19" between "2024-01-01" and "2024-01-31" returns:
 *   | Level | AccountName | TaxAmt | NetAmt | TaxAmt_SUM | NetAmt_SUM |
 *   | 1     |             |        |        | -190       | -1000      |
 *   | 2     | T_Due_Acct  |        |        | -190       | -1000      |
 *   | 3     | T_Due_Acct  |        |        | -190       | -1000      |
 *   | 4     | T_Due_Acct  | -190   | -1000  |            |            |
 *   | ReCap |             |        |        | -190       | -1000      |
 * }</pre>
 *
 * <p>Numeric columns are parsed via {@link DataTableRow#getAsOptionalAmount(String, Supplier)} —
 * a bare number inherits the actual row's currency, {@code 190 CHF} forces a currency check too.
 *
 * <p><b>Supported columns</b>: {@code Level}, {@code VatCode}, {@code AccountName}, {@code TaxName},
 * {@code TaxAmt}, {@code NetAmt} (level 4), {@code TaxAmt_SUM}, {@code NetAmt_SUM}
 * (levels 1/2/3/ReCap), {@code DocumentNo}, {@code BPartnerName}.
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
	 * data table positionally, field by field.
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

		final List<TaxReportRow> actualRows = new ArrayList<>();
		actualRows.addAll(queryReportRows(StepDefConstants.ORG_ID, taxId, dateFrom, dateTo, null));
		actualRows.addAll(queryReportRows(StepDefConstants.ORG_ID, taxId, dateFrom, dateTo, "ReCap"));
		actualRows.sort(ROW_ORDER);

		final ImmutableList<DataTableRow> expectedRows = DataTableRows.of(dataTable).stream().collect(ImmutableList.toImmutableList());

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(actualRows.size())
				.as("report_taxaccounts (C_Tax=%s): row count", taxIdentifier)
				.isEqualTo(expectedRows.size());

		final int common = Math.min(actualRows.size(), expectedRows.size());
		for (int i = 0; i < common; i++)
		{
			assertRowMatches(softly, expectedRows.get(i), actualRows.get(i), taxIdentifier, i);
		}

		softly.assertAll();
	}

	private static final Comparator<TaxReportRow> ROW_ORDER = Comparator
			.comparing(TaxReportRow::getLevel, Comparator.nullsFirst(Comparator.naturalOrder()))
			.thenComparing(TaxReportRow::getAccountName, Comparator.nullsFirst(Comparator.naturalOrder()))
			.thenComparing(TaxReportRow::getTaxName, Comparator.nullsFirst(Comparator.naturalOrder()))
			.thenComparing(r -> toBigDecimal(r.getTaxAmt()), Comparator.nullsFirst(Comparator.naturalOrder()))
			.thenComparing(TaxReportRow::getDocumentNo, Comparator.nullsFirst(Comparator.naturalOrder()));

	private static void assertRowMatches(
			@NonNull final SoftAssertions softly,
			@NonNull final DataTableRow expected,
			@NonNull final TaxReportRow actual,
			@NonNull final String taxIdentifier,
			final int rowIndex)
	{
		final String ctx = String.format("report_taxaccounts (C_Tax=%s), row %d [Level=%s]",
				taxIdentifier, rowIndex, actual.getLevel());

		assertAmount(softly, expected, "TaxAmt", actual.getTaxAmt(), ctx);
		assertAmount(softly, expected, "NetAmt", actual.getNetAmt(), ctx);
		assertAmount(softly, expected, "TaxAmt_SUM", actual.getTaxAmtSum(), ctx);
		assertAmount(softly, expected, "NetAmt_SUM", actual.getNetAmtSum(), ctx);

		expected.getAsOptionalString("Level").ifPresent(exp ->
				softly.assertThat(actual.getLevel()).as("%s: Level", ctx).isEqualTo(exp));
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

	/**
	 * If the DataTable has a column named {@code columnName}, assert that the {@link Amount} on the
	 * actual row equals it. A bare number in the feature file (e.g. {@code -190}) inherits the
	 * actual's currency; {@code -190 CHF} asserts the currency too.
	 */
	private static void assertAmount(
			@NonNull final SoftAssertions softly,
			@NonNull final DataTableRow expected,
			@NonNull final String columnName,
			@Nullable final Amount actual,
			@NonNull final String ctx)
	{
		final Supplier<CurrencyCode> defaultCurrency = actual != null ? actual::getCurrencyCode : () -> null;
		expected.getAsOptionalAmount(columnName, defaultCurrency).ifPresent(exp ->
				softly.assertThat(actual).as("%s: %s", ctx, columnName).isEqualTo(exp));
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

	@Nullable
	private static BigDecimal toBigDecimal(@Nullable final Amount amount)
	{
		return amount != null ? amount.toBigDecimal() : null;
	}
}
