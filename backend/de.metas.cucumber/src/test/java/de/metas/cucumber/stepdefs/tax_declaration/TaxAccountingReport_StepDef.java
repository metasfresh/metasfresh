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

package de.metas.cucumber.stepdefs.tax_declaration;

import com.google.common.collect.ImmutableList;
import de.metas.acct.vatcode.VATCode;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.tax.C_Tax_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.tax.C_VAT_Code_StepDefData;
import de.metas.cucumber.stepdefs.util.IdentifiersResolver;
import de.metas.tax.api.TaxId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.SoftAssertions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Step definitions for testing the Tax Accounting Report ("Mehrwertsteuer-Verprobung 3").
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29361">me03#29361</a>
 * @see TaxReportRow
 * @see TaxReportRowRepository
 * @see TaxReportRowMatcher
 * @see TaxInfoLoadingCache
 */
@RequiredArgsConstructor
public class TaxAccountingReport_StepDef
{
	@NonNull private final C_Tax_StepDefData taxTable;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;
	@NonNull private final C_VAT_Code_StepDefData vatCodeTable;
	@NonNull private final IdentifiersResolver identifiersResolver;
	@NonNull private final TaxReportRowRepository taxReportRowRepository;
	@NonNull private final TaxInfoLoadingCache taxInfoCache;

	/**
	 * Assert the output of {@code de_metas_acct.report_taxaccounts(...)} — the same DB function
	 * invoked by AD_Process 585325 (Mehrwertsteuer-Verprobung(Excel) 3). The underlying view
	 * {@code de_metas_acct.tax_accounts_details_v} is an implementation detail and is not asserted
	 * against directly.
	 *
	 * <p>For reliable, scenario-isolated tests, the function is filtered by {@code C_Tax_ID}. The
	 * {@code p_c_tax_id} parameter was added as part of me03#29361 for this purpose.
	 *
	 * <p><b>Supported DataTable columns</b>:
	 * <ul>
	 *     <li>{@code Level} — {@code 1} / {@code 2} / {@code 3} / {@code 4} / {@code ReCap} aggregation level</li>
	 *     <li>{@code C_VAT_Code_ID} — identifier; resolves to {@link VATCode#getCode()} and is compared
	 *         against the {@code vatcode} column the DB function emits (null placeholder {@code -} asserts NULL)</li>
	 *     <li>{@code AccountConceptualName} — {@code T_Due_Acct} / {@code T_Credit_Acct}; resolved to the
	 *         environment-specific {@code "<accountno> <name>"} string via {@link TaxInfo} (lazy per tax)</li>
	 *     <li>{@code TaxName} — the C_Tax.Name</li>
	 *     <li>{@code NetAmt_SUM} / {@code TaxAmt_SUM} — subtotal amounts on levels 1/2/3/ReCap</li>
	 *     <li>{@code NetAmt} / {@code TaxAmt} — per-document amounts on level 4</li>
	 *     <li>{@code DocumentNo} — identifier of a C_Invoice or C_AllocationHdr; null-placeholder asserts NULL</li>
	 *     <li>{@code C_BPartner_ID} — identifier; resolved to the BPartner's name; null-placeholder asserts NULL</li>
	 * </ul>
	 *
	 * <p>Numeric columns are parsed via {@link DataTableRow#getAsOptionalAmount(String, Supplier)} —
	 * a bare number inherits the actual row's currency; {@code 190 CHF} forces a currency check too.
	 *
	 * <p><b>Matching philosophy</b> (find-and-remove): for each expected row, find the first actual
	 * row whose non-blank columns all match (blank cells are "don't care"), then remove the match from
	 * the candidate pool so the same actual row can't be used twice. Any unmatched expected row fails
	 * the step; any actual row left unmatched also fails. Decoupled from DB row order.
	 *
	 * <p><b>Why two calls to the function</b>: {@code p_level=NULL} returns levels 1/2/3/4 (per the
	 * {@code p_level IS NULL OR p_level = '<n>'} conditions in the function body), but NOT ReCap —
	 * ReCap is only produced when {@code p_level='ReCap'}. So this step issues one call with NULL
	 * and one with ReCap and concatenates the results (see
	 * {@link TaxReportRowRepository#list}).
	 *
	 * <p><b>Gherkin usage example</b>:
	 * <pre>{@code
	 * Then report_taxaccounts for C_Tax "salesTax19" between "2024-01-01" and "2024-01-31" returns:
	 *   | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
	 *   | 1     | salesVat19    |                       | -1000      | -190       |        |        | -             | -          |
	 *   | 2     | salesVat19    | T_Due_Acct            | -1000      | -190       |        |        | -             | -          |
	 *   | 3     | salesVat19    | T_Due_Acct            | -1000      | -190       |        |        | -             | -          |
	 *   | 4     | salesVat19    | T_Due_Acct            |            |            | -1000  | -190   | customer      | ariInv     |
	 *   | ReCap | salesVat19    |                       | -1000      | -190       |        |        | -             | -          |
	 * }</pre>
	 */
	@Then("report_taxaccounts for C_Tax {string} between {string} and {string} returns:")
	public void expect_report_taxaccounts(
			@NonNull final String taxIdentifier,
			@NonNull final String dateFromStr,
			@NonNull final String dateToStr,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final TaxId taxId = taxTable.get(taxIdentifier).getTaxId();
		final LocalDate dateFrom = LocalDate.parse(dateFromStr);
		final LocalDate dateTo = LocalDate.parse(dateToStr);

		final TaxReportRowMatcher rowMatcher = TaxReportRowMatcher.builder()
				.bpartnerTable(bpartnerTable)
				.vatCodeTable(vatCodeTable)
				.identifiersResolver(identifiersResolver)
				.taxInfo(taxInfoCache.getById(taxId))
				.build();

		final ImmutableList<DataTableRow> expectedRows = DataTableRows.of(dataTable).toList();
		final ArrayList<TaxReportRow> actualRows = retrieveActualTaxReportRows(taxId, dateFrom, dateTo, expectedRows.size());

		final String actualRowsDump = renderActualRows(actualRows);
		final String ctx = "report_taxaccounts (C_Tax=" + taxIdentifier + ")";
		final SoftAssertions softly = new SoftAssertions();

		// For each expected row, find and remove a matching actual row.
		for (final DataTableRow expected : expectedRows)
		{
			final TaxReportRow match = rowMatcher.findAndRemoveMatch(expected, actualRows);
			softly.assertThat(match)
					.as("%s: no actual row matches expected %s\n    all actual rows returned by the function:\n%s",
							ctx, expected.asMap(), actualRowsDump)
					.isNotNull();
		}

		// Any actual rows left over are unexpected.
		softly.assertThat(actualRows)
				.as("%s: unexpected extra rows returned (all actual rows were:\n%s)", ctx, actualRowsDump)
				.isEmpty();

		softly.assertAll();
	}

	@NonNull
	private ArrayList<TaxReportRow> retrieveActualTaxReportRows(
			final TaxId taxId,
			final LocalDate dateFrom,
			final LocalDate dateTo,
			final int expectedSize) throws InterruptedException
	{
		// Poll until the function returns as many rows as we expect — covers any async
		// posting lag (e.g. when payment allocations complete slightly after the invoice).
		// Once the count stabilises, we run the find-and-remove matching once for real.
		final ArrayList<TaxReportRow> actualRows = new ArrayList<>();
		StepDefUtil.tryAndWait(10, 500, () -> {
			actualRows.clear();
			// p_level=NULL returns levels 1/2/3/4 but NOT ReCap, so we need a second call for that.
			actualRows.addAll(taxReportRowRepository.list(StepDefConstants.ORG_ID, taxId, dateFrom, dateTo, null));
			actualRows.addAll(taxReportRowRepository.list(StepDefConstants.ORG_ID, taxId, dateFrom, dateTo, "ReCap"));
			return actualRows.size() == expectedSize;
		}, null);

		return actualRows;
	}

	private static String renderActualRows(@NonNull final List<TaxReportRow> rows)
	{
		if (rows.isEmpty())
		{
			return "      (none)";
		}
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < rows.size(); i++)
		{
			sb.append("      [").append(i).append("] ").append(rows.get(i)).append('\n');
		}
		return sb.toString();
	}
}
