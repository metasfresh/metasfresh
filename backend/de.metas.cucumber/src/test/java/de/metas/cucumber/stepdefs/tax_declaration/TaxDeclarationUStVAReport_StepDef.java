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
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.model.I_C_VAT_Code;
import de.metas.acct.tax.TaxDeclarationId;
import de.metas.acct.tax.TaxDeclarationService;
import de.metas.acct.vatcode.VATCodeAmountType;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.currency.ICurrencyDAO;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.tax.C_VAT_Code_StepDefData;
import de.metas.cucumber.stepdefs.util.IdentifiersResolver;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.util.DB;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
 *     <li>{@code SUMMARY} — one row per VAT code of the declaration (+ one "type unknown" row per code with such lines), ordered by {@code vatcode}</li>
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
 *     <li>SUMMARY: {@code vatcode, description (C_VAT_Code.Description), net_amt (with cents,
 *         0 when the code has no Net line), tax_amt (NULL when the code has no Tax line)}, both with the display sign applied;
 *         a code's lines without amount type get an own row with {@code amount_type = 'U'} ("type unknown"), amount in net_amt</li>
 *     <li>BALANCE: {@code balance_amt = - sum of the declared Tax amounts (debit - credit)}</li>
 *     <li>DETAIL: {@code vatcode, amount_type ('N'/'T', 'U' when the posting has no amount type), description (C_TaxDeclarationAcct.Description), amount (display sign
 *         applied, not cut off), documentno (source document no.), posting_date, doc_date, bpartner_name, bpartner_vatid, currency}</li>
 * </ul>
 *
 * <p><b>Scenario isolation</b>: a declaration snapshots every VAT-coded posting of its period and accounting schema,
 * so other scenarios' postings of the same period may be part of it. Every VAT code created by the scenario is
 * therefore "in scope"; SUMMARY / DETAIL rows of other codes are ignored. For the same reason the BALANCE is
 * asserted as: (a) the function's own consistency ({@code balance = - sum(tax_amt of ALL SUMMARY rows) }) and
 * (b) the expected value against the in-scope rows only.
 *
 * <p>Expected values are read through DAOs / services; the only raw SQL is the call of the report function itself.
 */
@RequiredArgsConstructor
public class TaxDeclarationUStVAReport_StepDef
{
	private static final String LEVEL_HEADER = "HEADER";
	private static final String LEVEL_SUMMARY = "SUMMARY";
	/** amount_type of the rows whose postings carry no amount type */
	private static final String AMOUNT_TYPE_UNKNOWN = "U";
	private static final String LEVEL_BALANCE = "BALANCE";
	private static final String LEVEL_DETAIL = "DETAIL";
	/** document no. column of the source documents (C_Invoice, C_AllocationHdr, SAP_GLJournal, ...) */
	private static final String COLUMNNAME_DocumentNo = "DocumentNo";

	@NonNull private final C_TaxDeclaration_StepDefData taxDeclarationTable;
	@NonNull private final C_VAT_Code_StepDefData vatCodeTable;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;
	@NonNull private final IdentifiersResolver identifiersResolver;

	@NonNull private final TaxDeclarationService taxDeclarationService = SpringContextHolder.instance.getBean(TaxDeclarationService.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);
	@NonNull private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Runs the UStVA report function for the declaration and asserts its rows against the expected rows (one row per expected report row).
	 * <p>Cell conventions: blank = "don't care"; {@code -} = must be NULL (for text: NULL or empty).
	 * <p>Always asserted, whatever the table lists: exactly one HEADER and one BALANCE row; HEADER {@code print_date} present;
	 * HEADER organisation name, tax number and VAT ID (the organisation's active business partner), accounting schema name,
	 * period from/to, document no. and currency equal the declaration's records; BALANCE = - sum of the declared tax;
	 * the DETAIL rows of each in-scope code add up to its SUMMARY row.
	 * <p>SUMMARY order: when the table lists SUMMARY rows, the report's SUMMARY rows of the scenario's own codes must come in
	 * exactly the order of the expected SUMMARY rows (position by position, VAT code + amount type; a row without
	 * {@code AmountType} is the code's regular row, which comes before its "type unknown" row). The report orders by VAT code,
	 * and the codes of one {@code metasfresh contains C_VAT_Codes:} table sort in table order, so list the expected SUMMARY rows in that order.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>report_level</b> — (required) HEADER / SUMMARY / BALANCE / DETAIL<br>
	 *   <b>C_VAT_Code_ID</b> — (optional, identifier-ref) VAT code of a SUMMARY / DETAIL row<br>
	 *   <b>AmountType</b> — (optional) N / T / U ("type unknown"); SUMMARY: only U, empty = the code's regular row<br>
	 *   <b>description</b>, <b>net_amt</b>, <b>tax_amt</b>, <b>balance_amt</b>, <b>amount</b> — (optional) expected values<br>
	 *   <b>Record_ID</b> — (optional, identifier-ref) source document of a DETAIL row (C_Invoice / C_AllocationHdr / SAP_GLJournal); {@code -} = no source document found<br>
	 *   <b>posting_date</b>, <b>doc_date</b> — (optional) {@code YYYY-MM-DD}<br>
	 *   <b>C_BPartner_ID</b> — (optional, identifier-ref) partner of a DETAIL row; its name is compared<br>
	 *   <b>bpartner_vatid</b> — (optional) partner VAT ID of a DETAIL row<br>
	 *   <b>docstatus</b>, <b>is_correction</b>, <b>org_tax_id</b>, <b>org_vat_id</b> — (optional) HEADER only<br>
	 *   <b>Original_ID</b> — (optional, identifier-ref, null-allowed as {@code -}) HEADER only: original declaration, whose document no. is expected in {@code original_documentno}<br>
	 * @cucumber.depends StepDefData: C_TaxDeclaration_StepDefData, C_VAT_Code_StepDefData, C_BPartner_StepDefData; IdentifiersResolver (Record_ID)
	 * @cucumber.example
	 * <pre>
	 * Then the UStVA report for tax declaration "td" returns:
	 *   | report_level | C_VAT_Code_ID | net_amt | tax_amt | balance_amt |
	 *   | SUMMARY      | v81N          | 20      | 3.80    |             |
	 *   | SUMMARY      | v66T          | -       | 16.44   |             |
	 *   | BALANCE      |               |         |         | -12.64      |
	 * </pre>
	 */
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

			if (LEVEL_SUMMARY.equals(level))
			{
				assertSummaryOrder(softly, ctx, expectedOfLevel, ImmutableList.copyOf(candidates), dump);
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
		final I_C_TaxDeclaration declaration = taxDeclarationService.getById(TaxDeclarationId.ofRepoId(declarationId));
		final OrgId orgId = OrgId.ofRepoId(declaration.getAD_Org_ID());
		final AcctSchema acctSchema = acctSchemaDAO.getById(AcctSchemaId.ofRepoId(declaration.getC_AcctSchema_ID()));
		// tax number and VAT ID come only from the organisation's active business partner (C_BPartner.AD_OrgBP_ID; at most one is active)
		final I_C_BPartner orgBPartner = orgId.isRegular()
				? bpartnerOrgBL.retrieveLinkedBPartnerId(orgId).map(bpartnerDAO::getByIdOutOfTrx).orElse(null)
				: null;
		final I_C_Period period = declaration.getC_Period_ID() > 0
				? InterfaceWrapperHelper.loadOutOfTrx(declaration.getC_Period_ID(), I_C_Period.class)
				: null;

		softly.assertThat(h.getPrintDate()).as("%s: HEADER print_date", ctx).isNotNull();
		softly.assertThat(h.getOrgName()).as("%s: HEADER org_name", ctx).isEqualTo(blankToNull(orgDAO.getOrgName(orgId)));
		softly.assertThat(h.getOrgTaxId()).as("%s: HEADER org_tax_id", ctx).isEqualTo(orgBPartner != null ? blankToNull(orgBPartner.getTaxID()) : null);
		softly.assertThat(h.getOrgVatId()).as("%s: HEADER org_vat_id", ctx).isEqualTo(orgBPartner != null ? blankToNull(orgBPartner.getVATaxID()) : null);
		softly.assertThat(h.getAcctSchemaName()).as("%s: HEADER acctschema_name", ctx).isEqualTo(blankToNull(acctSchema.getName()));
		softly.assertThat(h.getPeriodFrom()).as("%s: HEADER period_from", ctx).isEqualTo(period != null ? toIsoDate(period.getStartDate()) : null);
		softly.assertThat(h.getPeriodTo()).as("%s: HEADER period_to", ctx).isEqualTo(period != null ? toIsoDate(period.getEndDate()) : null);
		softly.assertThat(h.getDocumentNo()).as("%s: HEADER documentno", ctx).isEqualTo(blankToNull(declaration.getDocumentNo()));
		softly.assertThat(h.getCurrency()).as("%s: HEADER currency (accounting schema currency)", ctx)
				.isEqualTo(currencyDAO.getCurrencyCodeById(acctSchema.getCurrencyId()).toThreeLetterCode());
	}

	/**
	 * The SUMMARY rows of the scenario's own codes must come in the order of the expected SUMMARY rows, position by position
	 * (VAT code + amount type; an expected row without amount type is the code's regular row, so it must come before the code's "type unknown" row).
	 * The VAT codes of one {@code C_VAT_Codes} data table sort in table order, so the feature lists the expected rows in that order.
	 */
	private void assertSummaryOrder(
			final SoftAssertions softly, final String ctx,
			final List<DataTableRow> expectedSummaries, final List<UStVAReportRow> actualSummariesInScope, final String dump)
	{
		final List<SummaryKey> expectedKeys = new ArrayList<>();
		for (final DataTableRow expected : expectedSummaries)
		{
			expectedKeys.add(SummaryKey.builder()
					.vatCode(cell(expected, "C_VAT_Code_ID").map(c -> vatCodeTable.get(StepDefDataIdentifier.ofString(c)).getCode()).orElse(null))
					.amountType(cell(expected, "AmountType").orElse(null))
					.build());
		}
		softly.assertThat(summaryOrderViolations(expectedKeys, actualSummariesInScope))
				.as("%s: SUMMARY rows (in scope) in the order of the expected SUMMARY rows\n%s", ctx, dump)
				.isEmpty();
	}

	/**
	 * Compares the actual SUMMARY rows with the expected ones position by position; returns one message per mismatch (empty = same order).
	 * An expected row without amount type is a regular row, i.e. its actual amount type must not be "type unknown".
	 */
	static List<String> summaryOrderViolations(final List<SummaryKey> expected, final List<UStVAReportRow> actual)
	{
		final List<String> violations = new ArrayList<>();
		if (expected.size() != actual.size())
		{
			violations.add("expected " + expected.size() + " SUMMARY rows but got " + actual.size());
		}
		for (int i = 0; i < Math.min(expected.size(), actual.size()); i++)
		{
			final SummaryKey e = expected.get(i);
			final UStVAReportRow a = actual.get(i);
			final boolean sameCode = e.getVatCode() == null || e.getVatCode().equals(a.getVatCode());
			final boolean sameAmountType = e.getAmountType() != null
					? e.getAmountType().equals(a.getAmountType())
					: !AMOUNT_TYPE_UNKNOWN.equals(a.getAmountType());
			if (!sameCode || !sameAmountType)
			{
				violations.add("position " + i + ": expected vatcode=" + e.getVatCode() + " amount_type=" + e.getAmountType()
						+ " but got vatcode=" + a.getVatCode() + " amount_type=" + a.getAmountType());
			}
		}
		return violations;
	}

	/** Expected position of a SUMMARY row: VAT code ({@code null} = any) and amount type ({@code null} = the code's regular row). */
	@Value
	@Builder
	static class SummaryKey
	{
		@Nullable String vatCode;
		@Nullable String amountType;
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
	private BigDecimal sumDeclaredTaxNegated(
			final List<UStVAReportRow> actualRows, final int declarationId, @Nullable final Set<String> scope)
	{
		final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(taxDeclarationService.getById(TaxDeclarationId.ofRepoId(declarationId)).getC_AcctSchema_ID());
		BigDecimal sum = BigDecimal.ZERO;
		for (final UStVAReportRow summary : rowsOfLevel(actualRows, LEVEL_SUMMARY))
		{
			if (summary.getTaxAmt() == null || (scope != null && !scope.isEmpty() && !scope.contains(summary.getVatCode())))
			{
				continue;
			}
			final BigDecimal declared = isSalesOnlyTaxCode(acctSchemaId, summary.getVatCode()) ? summary.getTaxAmt().negate() : summary.getTaxAmt();
			sum = sum.add(declared);
		}
		return sum.negate();
	}

	/** {@code true} when the code has at least one active Tax row on the schema and every such row is a sales row ({@code IsSOTrx='Y'}; NULL applies to both sides). */
	private boolean isSalesOnlyTaxCode(@NonNull final AcctSchemaId acctSchemaId, @Nullable final String vatCode)
	{
		final List<I_C_VAT_Code> taxRows = queryBL.createQueryBuilderOutOfTrx(I_C_VAT_Code.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_VAT_Code.COLUMNNAME_C_AcctSchema_ID, acctSchemaId)
				.addEqualsFilter(I_C_VAT_Code.COLUMNNAME_AmountType, VATCodeAmountType.Tax.getCode())
				.addEqualsFilter(I_C_VAT_Code.COLUMNNAME_VATCode, vatCode)
				.create()
				.list();
		return !taxRows.isEmpty() && taxRows.stream().allMatch(row -> "Y".equals(row.getIsSOTrx()));
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
			// a "type unknown" summary row carries the code's rows without amount type in its net column, and no tax
			final boolean typeUnknown = AMOUNT_TYPE_UNKNOWN.equals(summary.getAmountType());
			final BigDecimal netSum = detailSums.get(summary.getVatCode() + "|" + (typeUnknown ? AMOUNT_TYPE_UNKNOWN : "N"));
			final BigDecimal taxSum = typeUnknown ? null : detailSums.get(summary.getVatCode() + "|T");
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
				|| !matchText(expected, "org_tax_id", actual.getOrgTaxId())
				|| !matchText(expected, "org_vat_id", actual.getOrgVatId())
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
		// any document table (C_Invoice, C_AllocationHdr, SAP_GLJournal, ...): load the record fresh and read its DocumentNo column
		final Object sourceDocument = InterfaceWrapperHelper.create(Env.getCtx(), ref.getTableName(), ref.getRecord_ID(), Object.class, ITrx.TRXNAME_None);
		final String documentNo = sourceDocument != null ? blankToNull(InterfaceWrapperHelper.getValueOrNull(sourceDocument, COLUMNNAME_DocumentNo)) : null;
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
		final I_C_TaxDeclaration originalRef = taxDeclarationTable.get(StepDefDataIdentifier.ofString(c));
		final I_C_TaxDeclaration original = taxDeclarationService.getById(TaxDeclarationId.ofRepoId(originalRef.getC_TaxDeclaration_ID()));
		return Objects.equals(blankToNull(original.getDocumentNo()), actual.getOriginalDocumentNo());
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
	private static String blankToNull(@Nullable final Object value)
	{
		final String str = value != null ? value.toString() : null;
		return isBlankStr(str) ? null : str;
	}

	/** Same wall-clock date as the report's {@code ::date} cast (formatted {@code YYYY-MM-DD}). */
	@Nullable
	private static String toIsoDate(@Nullable final Timestamp timestamp)
	{
		return timestamp != null ? timestamp.toLocalDateTime().toLocalDate().toString() : null;
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

	/** The only raw SQL of this step definition: the report is a PostgreSQL {@code RETURNS TABLE} function, there is no DAO for it. */
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
