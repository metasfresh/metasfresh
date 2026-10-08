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
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.tax.C_VAT_Code_StepDefData;
import de.metas.cucumber.stepdefs.util.IdentifiersResolver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Asserts the output of the DB function {@code report.tax_declaration_ustva_report(C_TaxDeclaration_ID, AD_Language)}
 * that feeds the UStVA (German VAT return) PDF printed from the Tax Declaration window.
 *
 * <p>The function returns one flat set of rows, discriminated by {@code report_level}:
 * <ul>
 *     <li>{@code HEADER} — exactly one row</li>
 *     <li>{@code SUMMARY} — one row per VAT code of the declaration, ordered by {@code vatcode}</li>
 *     <li>{@code BALANCE} — exactly one row</li>
 *     <li>{@code DETAIL} — one row per non-zero {@code C_TaxDeclarationAcct} row</li>
 * </ul>
 *
 * <p><b>Columns the function must return</b> (see {@link UStVAReportRow}); columns not used by a level are NULL:
 * {@code report_level}, {@code vatcode}, {@code amount_type}, {@code description}, {@code net_amt}, {@code tax_amt},
 * {@code balance_amt}, {@code documentno}, {@code docstatus}, {@code is_correction}, {@code original_documentno},
 * {@code org_name}, {@code org_tax_id}, {@code org_vat_id}, {@code acctschema_name}, {@code period_from},
 * {@code period_to}, {@code currency}, {@code print_date}, {@code posting_date}, {@code doc_date},
 * {@code bpartner_name}, {@code bpartner_vatid}, {@code amount}.
 *
 * <p><b>Per level</b>:
 * <ul>
 *     <li>HEADER: {@code print_date, org_name, org_tax_id, org_vat_id, acctschema_name, period_from, period_to,
 *         documentno (of the declaration), docstatus, is_correction ('Y'/'N'), original_documentno, currency}</li>
 *     <li>SUMMARY: {@code vatcode, description (C_VAT_Code.Description), net_amt (whole euros, cut off toward zero,
 *         NULL when the code has no Net line), tax_amt (NULL when the code has no Tax line)}, both with the display sign applied</li>
 *     <li>BALANCE: {@code balance_amt = - sum of the declared Tax amounts (debit - credit)}</li>
 *     <li>DETAIL: {@code vatcode, amount_type ('N'/'T'), description (C_TaxDeclarationAcct.Description), amount (display sign
 *         applied, not cut off), documentno (source document no.), posting_date, doc_date, bpartner_name, bpartner_vatid, currency}</li>
 * </ul>
 *
 * <p><b>Scenario isolation</b>: a declaration snapshots every VAT-coded posting of its period and accounting schema,
 * so other scenarios' postings of the same period may be part of it. Every VAT code created by the scenario is
 * therefore "in scope"; SUMMARY / DETAIL rows of other codes are ignored. For the same reason the BALANCE is
 * asserted as: (a) the function's own consistency ({@code balance = - sum(tax_amt of ALL SUMMARY rows) }) and
 * (b) the expected value against the in-scope rows only.
 *
 * <p><b>Gherkin usage example</b>:
 * <pre>{@code
 * Then the UStVA report for tax declaration "td1" returns:
 *   | report_level | C_VAT_Code_ID | net_amt | tax_amt | balance_amt |
 *   | SUMMARY      | kz81          | 20      | -       |             |
 *   | SUMMARY      | kz66          | -       | 16.44   |             |
 *   | BALANCE      |               |         |         | -16.44      |
 * }</pre>
 *
 * <p><b>Cell conventions</b>: blank = "don't care"; {@code -} = must be NULL (for text: NULL or empty).
 * <p><b>Columns</b>: {@code report_level} (required); {@code C_VAT_Code_ID} (identifier, SUMMARY/DETAIL);
 * {@code AmountType} (DETAIL); {@code description}; {@code net_amt}; {@code tax_amt}; {@code balance_amt}; {@code amount};
 * {@code Record_ID} (identifier of the source document: C_Invoice / C_AllocationHdr; {@code -} = no source document found);
 * {@code posting_date}; {@code doc_date}; {@code C_BPartner_ID} (identifier; the partner name is compared); {@code bpartner_vatid};
 * HEADER only: {@code docstatus}, {@code is_correction}, {@code Original_ID} (identifier of the original declaration;
 * its document no. is expected in {@code original_documentno}, {@code -} = none).
 * Organisation name, tax number, VAT ID, accounting schema, period, document no. and currency of the HEADER are always asserted
 * against the values stored in the database for the declaration; {@code print_date} must be present.
 */
@RequiredArgsConstructor
public class TaxDeclarationUStVAReport_StepDef
{
	private static final String LEVEL_HEADER = "HEADER";
	private static final String LEVEL_SUMMARY = "SUMMARY";
	private static final String LEVEL_BALANCE = "BALANCE";
	private static final String LEVEL_DETAIL = "DETAIL";

	@NonNull private final C_TaxDeclaration_StepDefData taxDeclarationTable;
	@NonNull private final C_VAT_Code_StepDefData vatCodeTable;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;
	@NonNull private final IdentifiersResolver identifiersResolver;

	@Then("the UStVA report for tax declaration {string} returns:")
	public void expect_ustva_report(
			@NonNull final String declarationIdentifier,
			@NonNull final DataTable dataTable)
	{
		final I_C_TaxDeclaration declaration = taxDeclarationTable.get(StepDefDataIdentifier.ofString(declarationIdentifier));
		final int declarationId = declaration.getC_TaxDeclaration_ID();

		final ImmutableList<UStVAReportRow> actualRows = retrieveReportRows(declarationId);
		final ImmutableList<DataTableRow> expectedRows = DataTableRows.of(dataTable).toList();
		final Set<String> scopeVatCodes = getScopeVatCodes();
		final String ctx = "UStVA report (declaration=" + declarationIdentifier + ")";
		final String dump = dump(actualRows);

		final SoftAssertions softly = new SoftAssertions();

		assertHeaderAlwaysChecks(softly, ctx, declarationId, actualRows, dump);
		assertSummaryOrdering(softly, ctx, actualRows, dump);
		assertBalanceConsistency(softly, ctx, declarationId, actualRows, dump);
		assertDetailSumsEqualSummary(softly, ctx, actualRows, scopeVatCodes, dump);

		for (final String level : Arrays.asList(LEVEL_HEADER, LEVEL_SUMMARY, LEVEL_BALANCE, LEVEL_DETAIL))
		{
			final List<DataTableRow> expectedOfLevel = new ArrayList<>();
			for (final DataTableRow expected : expectedRows)
			{
				if (level.equalsIgnoreCase(expected.getAsString("report_level")))
				{
					expectedOfLevel.add(expected);
				}
			}
			if (expectedOfLevel.isEmpty())
			{
				continue;
			}

			final List<UStVAReportRow> candidates = new ArrayList<>();
			for (final UStVAReportRow actual : actualRows)
			{
				if (!level.equals(actual.getReportLevel()))
				{
					continue;
				}
				final boolean inScope = LEVEL_HEADER.equals(level) || LEVEL_BALANCE.equals(level) || scopeVatCodes.isEmpty() || scopeVatCodes.contains(actual.getVatCode());
				if (inScope)
				{
					candidates.add(actual);
				}
			}

			for (final DataTableRow expected : expectedOfLevel)
			{
				UStVAReportRow match = null;
				for (final UStVAReportRow candidate : candidates)
				{
					if (matches(expected, candidate, declarationId, level))
					{
						match = candidate;
						break;
					}
				}
				softly.assertThat(match)
						.as("%s: no %s row matches expected %s\n  all actual rows:\n%s", ctx, level, expected.asMap(), dump)
						.isNotNull();
				if (match != null)
				{
					candidates.remove(match);
				}
			}

			softly.assertThat(candidates)
					.as("%s: unexpected extra %s rows (in scope)\n  all actual rows:\n%s", ctx, level, dump)
					.isEmpty();

			if (LEVEL_BALANCE.equals(level))
			{
				assertScopedBalance(softly, ctx, declarationId, expectedOfLevel, actualRows, scopeVatCodes, dump);
			}
		}

		softly.assertAll();
	}

	// ------------------------------------------------------------------------------------------------
	// invariants that hold for every report
	// ------------------------------------------------------------------------------------------------

	private void assertHeaderAlwaysChecks(
			final SoftAssertions softly, final String ctx, final int declarationId,
			final List<UStVAReportRow> actualRows, final String dump)
	{
		final List<UStVAReportRow> headers = rowsOfLevel(actualRows, LEVEL_HEADER);
		softly.assertThat(headers).as("%s: exactly one HEADER row\n%s", ctx, dump).hasSize(1);
		softly.assertThat(rowsOfLevel(actualRows, LEVEL_BALANCE)).as("%s: exactly one BALANCE row\n%s", ctx, dump).hasSize(1);
		if (headers.size() != 1)
		{
			return;
		}
		final UStVAReportRow h = headers.get(0);

		softly.assertThat(h.getPrintDate()).as("%s: HEADER print_date", ctx).isNotNull();
		softly.assertThat(h.getOrgName()).as("%s: HEADER org_name", ctx).isEqualTo(dbString(
				"SELECT o.Name FROM C_TaxDeclaration td JOIN AD_Org o ON o.AD_Org_ID=td.AD_Org_ID WHERE td.C_TaxDeclaration_ID=?", declarationId));
		softly.assertThat(h.getOrgTaxId()).as("%s: HEADER org_tax_id", ctx).isEqualTo(dbString(
				"SELECT oi.TaxID FROM C_TaxDeclaration td JOIN AD_OrgInfo oi ON oi.AD_Org_ID=td.AD_Org_ID WHERE td.C_TaxDeclaration_ID=?", declarationId));
		softly.assertThat(h.getOrgVatId()).as("%s: HEADER org_vat_id", ctx).isEqualTo(dbString(
				"SELECT bp.VATaxID FROM C_TaxDeclaration td JOIN AD_OrgInfo oi ON oi.AD_Org_ID=td.AD_Org_ID JOIN C_BPartner bp ON bp.C_BPartner_ID=oi.Org_BPartner_ID WHERE td.C_TaxDeclaration_ID=?", declarationId));
		softly.assertThat(h.getAcctSchemaName()).as("%s: HEADER acctschema_name", ctx).isEqualTo(dbString(
				"SELECT s.Name FROM C_TaxDeclaration td JOIN C_AcctSchema s ON s.C_AcctSchema_ID=td.C_AcctSchema_ID WHERE td.C_TaxDeclaration_ID=?", declarationId));
		softly.assertThat(h.getPeriodFrom()).as("%s: HEADER period_from", ctx).isEqualTo(dbString(
				"SELECT to_char(p.StartDate,'YYYY-MM-DD') FROM C_TaxDeclaration td JOIN C_Period p ON p.C_Period_ID=td.C_Period_ID WHERE td.C_TaxDeclaration_ID=?", declarationId));
		softly.assertThat(h.getPeriodTo()).as("%s: HEADER period_to", ctx).isEqualTo(dbString(
				"SELECT to_char(p.EndDate,'YYYY-MM-DD') FROM C_TaxDeclaration td JOIN C_Period p ON p.C_Period_ID=td.C_Period_ID WHERE td.C_TaxDeclaration_ID=?", declarationId));
		softly.assertThat(h.getDocumentNo()).as("%s: HEADER documentno", ctx).isEqualTo(dbString(
				"SELECT td.DocumentNo FROM C_TaxDeclaration td WHERE td.C_TaxDeclaration_ID=?", declarationId));
		softly.assertThat(h.getCurrency()).as("%s: HEADER currency (accounting schema currency)", ctx).isEqualTo(dbString(
				"SELECT c.ISO_Code FROM C_TaxDeclaration td JOIN C_AcctSchema s ON s.C_AcctSchema_ID=td.C_AcctSchema_ID JOIN C_Currency c ON c.C_Currency_ID=s.C_Currency_ID WHERE td.C_TaxDeclaration_ID=?", declarationId));
	}

	private void assertSummaryOrdering(
			final SoftAssertions softly, final String ctx, final List<UStVAReportRow> actualRows, final String dump)
	{
		String previous = null;
		for (final UStVAReportRow row : rowsOfLevel(actualRows, LEVEL_SUMMARY))
		{
			if (previous != null)
			{
				softly.assertThat(row.getVatCode().compareTo(previous))
						.as("%s: SUMMARY rows ordered by vatcode (%s after %s)\n%s", ctx, row.getVatCode(), previous, dump)
						.isGreaterThan(0);
			}
			previous = row.getVatCode();
		}
	}

	/** The function's own formula: balance = - sum of all declared tax amounts (all SUMMARY rows, in scope or not). */
	private void assertBalanceConsistency(
			final SoftAssertions softly, final String ctx, final int declarationId, final List<UStVAReportRow> actualRows, final String dump)
	{
		final List<UStVAReportRow> balances = rowsOfLevel(actualRows, LEVEL_BALANCE);
		if (balances.size() != 1)
		{
			return;
		}
		final BigDecimal expected = sumDeclaredTaxNegated(actualRows, declarationId, null);
		softly.assertThat(balances).hasSize(1);
		softly.assertThat(balances.get(0).getBalanceAmt())
				.as("%s: BALANCE = - sum of the declared tax amounts (all SUMMARY rows)\n%s", ctx, dump)
				.isEqualByComparingTo(expected);
	}

	/**
	 * Printed amounts carry the display sign: -1 when every C_VAT_Code row of the code and amount type on the declaration's
	 * accounting schema is a sales code (IsSOTrx='Y'), else +1. The declared amount (debit - credit) is the printed one times that sign.
	 */
	private static BigDecimal sumDeclaredTaxNegated(
			final List<UStVAReportRow> actualRows, final int declarationId, @Nullable final Set<String> scope)
	{
		BigDecimal sum = BigDecimal.ZERO;
		for (final UStVAReportRow summary : rowsOfLevel(actualRows, LEVEL_SUMMARY))
		{
			if (summary.getTaxAmt() == null || (scope != null && !scope.isEmpty() && !scope.contains(summary.getVatCode())))
			{
				continue;
			}
			final String sales = dbString("SELECT CASE WHEN COUNT(*) > 0 AND COUNT(*) = COUNT(CASE WHEN v.IsSOTrx='Y' THEN 1 END) THEN 'Y' ELSE 'N' END"
					+ " FROM C_VAT_Code v JOIN C_TaxDeclaration td ON td.C_AcctSchema_ID=v.C_AcctSchema_ID"
					+ " WHERE td.C_TaxDeclaration_ID=? AND v.IsActive='Y' AND v.AmountType='T' AND v.VATCode='" + summary.getVatCode().replace("'", "''") + "'", declarationId);
			final BigDecimal declared = "Y".equals(sales) ? summary.getTaxAmt().negate() : summary.getTaxAmt();
			sum = sum.add(declared);
		}
		return sum.negate();
	}

	/** Detail rows of a code add up to the summary: Net rows to the cut-off net, Tax rows to the tax. */
	private void assertDetailSumsEqualSummary(
			final SoftAssertions softly, final String ctx, final List<UStVAReportRow> actualRows,
			final Set<String> scopeVatCodes, final String dump)
	{
		final List<UStVAReportRow> details = rowsOfLevel(actualRows, LEVEL_DETAIL);
		final Map<String, BigDecimal> detailSums = new HashMap<>();
		for (final UStVAReportRow d : details)
		{
			detailSums.merge(d.getVatCode() + "|" + d.getAmountType(), d.getAmount(), BigDecimal::add);
		}

		for (final UStVAReportRow summary : rowsOfLevel(actualRows, LEVEL_SUMMARY))
		{
			if (!scopeVatCodes.isEmpty() && !scopeVatCodes.contains(summary.getVatCode()))
			{
				continue;
			}
			final BigDecimal netSum = detailSums.get(summary.getVatCode() + "|N");
			final BigDecimal taxSum = detailSums.get(summary.getVatCode() + "|T");
			if (summary.getNetAmt() == null)
			{
				softly.assertThat(netSum).as("%s: code %s has no net summary, so no Net detail rows\n%s", ctx, summary.getVatCode(), dump).isNull();
			}
			else
			{
				softly.assertThat(netSum).as("%s: code %s Net detail rows\n%s", ctx, summary.getVatCode(), dump).isNotNull();
				if (netSum != null)
				{
					softly.assertThat(netSum.setScale(0, RoundingMode.DOWN))
							.as("%s: code %s sum of Net detail rows cut off to whole euros = summary net\n%s", ctx, summary.getVatCode(), dump)
							.isEqualByComparingTo(summary.getNetAmt());
				}
			}
			if (summary.getTaxAmt() == null)
			{
				softly.assertThat(taxSum).as("%s: code %s has no tax summary, so no Tax detail rows\n%s", ctx, summary.getVatCode(), dump).isNull();
			}
			else
			{
				softly.assertThat(taxSum).as("%s: code %s Tax detail rows\n%s", ctx, summary.getVatCode(), dump).isNotNull();
				if (taxSum != null)
				{
					softly.assertThat(taxSum)
							.as("%s: code %s sum of Tax detail rows = summary tax\n%s", ctx, summary.getVatCode(), dump)
							.isEqualByComparingTo(summary.getTaxAmt());
				}
			}
		}
	}

	/** The expected balance is verified against the declared tax of the scenario's own codes (other scenarios' codes may share the period). */
	private void assertScopedBalance(
			final SoftAssertions softly, final String ctx, final int declarationId, final List<DataTableRow> expectedBalances,
			final List<UStVAReportRow> actualRows, final Set<String> scopeVatCodes, final String dump)
	{
		final BigDecimal scopedBalance = sumDeclaredTaxNegated(actualRows, declarationId, scopeVatCodes);
		for (final DataTableRow expected : expectedBalances)
		{
			cell(expected, "balance_amt").ifPresent(c -> softly.assertThat(new BigDecimal(c))
					.as("%s: expected balance = - sum of the declared tax of the scenario's own codes\n%s", ctx, dump)
					.isEqualByComparingTo(scopedBalance));
		}
	}

	// ------------------------------------------------------------------------------------------------
	// row matching
	// ------------------------------------------------------------------------------------------------

	private boolean matches(
			final DataTableRow expected, final UStVAReportRow actual, final int declarationId, final String level)
	{
		if (!matchVatCode(expected, actual))
		{
			return false;
		}
		if (!matchText(expected, "AmountType", actual.getAmountType()) && !isBlank(expected, "AmountType"))
		{
			return false;
		}
		if (!matchText(expected, "description", actual.getDescription()))
		{
			return false;
		}
		if (!matchAmount(expected, "net_amt", actual.getNetAmt())
				|| !matchAmount(expected, "tax_amt", actual.getTaxAmt())
				|| !matchAmount(expected, "amount", actual.getAmount()))
		{
			return false;
		}
		if (LEVEL_BALANCE.equals(level))
		{
			// the balance value itself is verified against the scenario's own codes in assertScopedBalance()
			return true;
		}
		if (!matchText(expected, "docstatus", actual.getDocStatus())
				|| !matchText(expected, "is_correction", actual.getIsCorrection())
				|| !matchText(expected, "bpartner_vatid", actual.getBpartnerVatId())
				|| !matchText(expected, "posting_date", actual.getPostingDate())
				|| !matchText(expected, "doc_date", actual.getDocDate()))
		{
			return false;
		}
		if (!matchPartner(expected, actual) || !matchSourceDocument(expected, actual))
		{
			return false;
		}
		if (LEVEL_HEADER.equals(level))
		{
			return matchOriginalDocumentNo(expected, actual);
		}
		return true;
	}

	private boolean matchVatCode(final DataTableRow expected, final UStVAReportRow actual)
	{
		final String c = cell(expected, "C_VAT_Code_ID").orElse(null);
		if (c == null)
		{
			return true;
		}
		return Objects.equals(vatCodeTable.get(StepDefDataIdentifier.ofString(c)).getCode(), actual.getVatCode());
	}

	private boolean matchPartner(final DataTableRow expected, final UStVAReportRow actual)
	{
		final String c = cell(expected, "C_BPartner_ID").orElse(null);
		if (c == null)
		{
			return true;
		}
		if ("-".equals(c))
		{
			return isBlankStr(actual.getBpartnerName());
		}
		final I_C_BPartner bpartner = bpartnerTable.get(StepDefDataIdentifier.ofString(c));
		return Objects.equals(bpartner.getName(), actual.getBpartnerName());
	}

	private boolean matchSourceDocument(final DataTableRow expected, final UStVAReportRow actual)
	{
		final String c = cell(expected, "Record_ID").orElse(null);
		if (c == null)
		{
			return true;
		}
		if ("-".equals(c))
		{
			return isBlankStr(actual.getDocumentNo());
		}
		final TableRecordReference ref = identifiersResolver.getTableRecordReference(StepDefDataIdentifier.ofString(c));
		final String keyColumn = InterfaceWrapperHelper.getKeyColumnName(ref.getTableName());
		final String documentNo = dbString("SELECT DocumentNo FROM " + ref.getTableName() + " WHERE " + keyColumn + "=?", ref.getRecord_ID());
		return Objects.equals(documentNo, actual.getDocumentNo());
	}

	private boolean matchOriginalDocumentNo(final DataTableRow expected, final UStVAReportRow actual)
	{
		final String c = cell(expected, "Original_ID").orElse(null);
		if (c == null)
		{
			return true;
		}
		if ("-".equals(c))
		{
			return isBlankStr(actual.getOriginalDocumentNo());
		}
		final I_C_TaxDeclaration original = taxDeclarationTable.get(StepDefDataIdentifier.ofString(c));
		final String originalDocumentNo = dbString("SELECT DocumentNo FROM C_TaxDeclaration WHERE C_TaxDeclaration_ID=?", original.getC_TaxDeclaration_ID());
		return Objects.equals(originalDocumentNo, actual.getOriginalDocumentNo());
	}

	private static boolean matchText(final DataTableRow expected, final String column, @Nullable final String actual)
	{
		final String c = cell(expected, column).orElse(null);
		if (c == null)
		{
			return true;
		}
		if ("-".equals(c))
		{
			return isBlankStr(actual);
		}
		return c.equals(actual);
	}

	private static boolean matchAmount(final DataTableRow expected, final String column, @Nullable final BigDecimal actual)
	{
		final String c = cell(expected, column).orElse(null);
		if (c == null)
		{
			return true;
		}
		if ("-".equals(c))
		{
			return actual == null;
		}
		return actual != null && new BigDecimal(c).compareTo(actual) == 0;
	}

	// ------------------------------------------------------------------------------------------------
	// helpers
	// ------------------------------------------------------------------------------------------------

	private Set<String> getScopeVatCodes()
	{
		final Set<String> codes = new HashSet<>();
		vatCodeTable.forEach((identifier, vatCode) -> codes.add(vatCode.getCode()));
		return codes;
	}

	private static java.util.Optional<String> cell(final DataTableRow row, final String column)
	{
		return row.getAsOptionalString(column).map(String::trim).filter(s -> !s.isEmpty());
	}

	private static boolean isBlank(final DataTableRow row, final String column)
	{
		return !cell(row, column).isPresent();
	}

	private static boolean isBlankStr(@Nullable final String s)
	{
		return s == null || s.trim().isEmpty();
	}

	@Nullable
	private static String dbString(final String sql, final int id)
	{
		final String value = DB.getSQLValueStringEx(null, sql, id);
		return isBlankStr(value) ? null : value;
	}

	private static List<UStVAReportRow> rowsOfLevel(final List<UStVAReportRow> rows, final String level)
	{
		final List<UStVAReportRow> result = new ArrayList<>();
		for (final UStVAReportRow row : rows)
		{
			if (level.equals(row.getReportLevel()))
			{
				result.add(row);
			}
		}
		return result;
	}

	private static String dump(final List<UStVAReportRow> rows)
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

	/** Raw SQL is unavoidable: the report is a PostgreSQL {@code RETURNS TABLE} function. */
	private static ImmutableList<UStVAReportRow> retrieveReportRows(final int declarationId)
	{
		final String sql = "SELECT report_level, vatcode, amount_type, description, net_amt, tax_amt, balance_amt,"
				+ "        documentno, docstatus, is_correction, original_documentno, org_name, org_tax_id, org_vat_id,"
				+ "        acctschema_name, to_char(period_from,'YYYY-MM-DD') AS period_from, to_char(period_to,'YYYY-MM-DD') AS period_to,"
				+ "        currency, print_date, to_char(posting_date,'YYYY-MM-DD') AS posting_date, to_char(doc_date,'YYYY-MM-DD') AS doc_date,"
				+ "        bpartner_name, bpartner_vatid, amount"
				+ " FROM report.tax_declaration_ustva_report(p_C_TaxDeclaration_ID => ?::numeric, p_AD_Language => ?::varchar)";
		return DB.retrieveRowsOutOfTrx(sql, Arrays.<Object>asList(declarationId, "de_DE"), UStVAReport_StepDefRowMapper::toRow);
	}

	private static final class UStVAReport_StepDefRowMapper
	{
		static UStVAReportRow toRow(final ResultSet rs) throws SQLException
		{
			return UStVAReportRow.builder()
					.reportLevel(rs.getString("report_level"))
					.vatCode(rs.getString("vatcode"))
					.amountType(rs.getString("amount_type"))
					.description(rs.getString("description"))
					.netAmt(rs.getBigDecimal("net_amt"))
					.taxAmt(rs.getBigDecimal("tax_amt"))
					.balanceAmt(rs.getBigDecimal("balance_amt"))
					.documentNo(rs.getString("documentno"))
					.docStatus(rs.getString("docstatus"))
					.isCorrection(rs.getString("is_correction"))
					.originalDocumentNo(rs.getString("original_documentno"))
					.orgName(rs.getString("org_name"))
					.orgTaxId(rs.getString("org_tax_id"))
					.orgVatId(rs.getString("org_vat_id"))
					.acctSchemaName(rs.getString("acctschema_name"))
					.periodFrom(rs.getString("period_from"))
					.periodTo(rs.getString("period_to"))
					.currency(rs.getString("currency"))
					.printDate(rs.getObject("print_date"))
					.postingDate(rs.getString("posting_date"))
					.docDate(rs.getString("doc_date"))
					.bpartnerName(rs.getString("bpartner_name"))
					.bpartnerVatId(rs.getString("bpartner_vatid"))
					.amount(rs.getBigDecimal("amount"))
					.build();
		}
	}

	/** One row of {@code report.tax_declaration_ustva_report(...)}; see the class Javadoc of the step definition for the column contract. */
	@Value
	@Builder
	static class UStVAReportRow
	{
		@Nullable String reportLevel;
		@Nullable String vatCode;
		@Nullable String amountType;
		@Nullable String description;
		@Nullable BigDecimal netAmt;
		@Nullable BigDecimal taxAmt;
		@Nullable BigDecimal balanceAmt;
		@Nullable String documentNo;
		@Nullable String docStatus;
		@Nullable String isCorrection;
		@Nullable String originalDocumentNo;
		@Nullable String orgName;
		@Nullable String orgTaxId;
		@Nullable String orgVatId;
		@Nullable String acctSchemaName;
		@Nullable String periodFrom;
		@Nullable String periodTo;
		@Nullable String currency;
		@Nullable Object printDate;
		@Nullable String postingDate;
		@Nullable String docDate;
		@Nullable String bpartnerName;
		@Nullable String bpartnerVatId;
		@Nullable BigDecimal amount;
	}
}
