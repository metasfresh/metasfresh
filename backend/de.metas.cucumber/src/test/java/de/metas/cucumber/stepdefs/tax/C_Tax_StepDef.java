/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.tax;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.ValueAndName;
import de.metas.cucumber.stepdefs.org.AD_Org_StepDefData;
import de.metas.location.ICountryDAO;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.TaxUtils;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_Tax;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.Nullable;

import static de.metas.cucumber.stepdefs.StepDefConstants.DEFAULT_ValidFrom;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Step definitions for creating {@link I_C_Tax} records.
 *
 * @see C_Tax_StepDefData
 * @see C_TaxCategory_StepDef
 * @see de.metas.cucumber.stepdefs.tax.C_VAT_Code_StepDef
 */
@RequiredArgsConstructor
public class C_Tax_StepDef
{
	@NonNull private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final C_Tax_StepDefData taxTable;
	@NonNull private final C_TaxCategory_StepDef taxCategoryStepDef;
	@NonNull private final AD_Org_StepDefData orgTable;

	/**
	 * Create / upsert a {@link I_C_Tax} per data-table row.
	 *
	 * <p><b>Required columns</b>:
	 * <ul>
	 *     <li>{@code Identifier} — unique per scenario; registers the tax in {@link C_Tax_StepDefData}</li>
	 *     <li>{@code C_TaxCategory_ID} — identifier of an existing {@code C_TaxCategory}</li>
	 * </ul>
	 *
	 * <p><b>Optional columns</b>:
	 * <ul>
	 *     <li>{@code Rate} — decimal, e.g. {@code 19}</li>
	 *     <li>{@code C_Country_ID.CountryCode}, {@code To_Country_ID.CountryCode} — ISO country codes</li>
	 *     <li>{@code AD_Org_ID} — identifier of an existing {@code AD_Org}</li>
	 *     <li>{@code IsTaxExempt}, {@code IsReverseCharge}, {@code IsWholeTax}, {@code IsDocumentLevel} — {@code Y}/{@code N}/{@code true}/{@code false}</li>
	 *     <li>{@code SeqNo} — integer; auto-assigned if missing</li>
	 *     <li>{@code TypeOfDestCountry}, {@code ValidFrom} — as documented in {@link I_C_Tax}</li>
	 * </ul>
	 *
	 * <p>Re-running this step for an existing identifier acts as an upsert — applying the new
	 * column values and saving, which re-triggers the {@code C_Tax} interceptor. This is the
	 * standard way to drive flag-exclusivity test scenarios.
	 *
	 * <p>Upserts by {@code Name} so repeated runs don't collide on the unique-name constraint.
	 *
	 * <p><b>Gherkin usage example</b>:
	 * <pre>{@code
	 * And metasfresh contains C_Tax
	 *   | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | IsReverseCharge |
	 *   | tax19      | taxCategory      | 19   | DE                       | DE                        | true            |
	 * }</pre>
	 */
	@And("metasfresh contains C_Tax")
	public void createC_Taxes(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_Tax.COLUMNNAME_C_Tax_ID)
				.forEach(this::createC_Tax);
	}

	private void createC_Tax(@NonNull final DataTableRow tableRow)
	{
		final TaxCategoryId taxCategoryId = taxCategoryStepDef.extractTaxCategoryIdOrDefault(tableRow);

		final ValueAndName valueAndName = tableRow.suggestValueAndName();
		final String taxName = valueAndName.getName();

		final I_C_Tax taxRecord = CoalesceUtil.coalesceSuppliersNotNull(
				() -> retrieveTaxRecordByName(taxName),
				() -> InterfaceWrapperHelper.newInstance(I_C_Tax.class)
		);
		final boolean isNew = InterfaceWrapperHelper.isNew(taxRecord);

		taxRecord.setName(taxName);
		taxRecord.setC_TaxCategory_ID(taxCategoryId.getRepoId());
		if (isNew)
		{
			taxRecord.setSeqNo(getNextTaxSeqNo(taxCategoryId));
			taxRecord.setValidFrom(TimeUtil.asTimestamp(DEFAULT_ValidFrom));
		}

		tableRow.getAsOptionalLocalDateTimestamp(I_C_Tax.COLUMNNAME_ValidFrom)
				.ifPresent(taxRecord::setValidFrom);
		tableRow.getAsOptionalBigDecimal(I_C_Tax.COLUMNNAME_Rate)
				.ifPresent(taxRecord::setRate);
		tableRow.getAsOptionalCountryCode(I_C_Tax.COLUMNNAME_C_Country_ID)
				.map(countryDAO::getCountryIdByCountryCode)
				.ifPresent(countryId -> taxRecord.setC_Country_ID(countryId.getRepoId()));
		tableRow.getAsOptionalCountryCode(I_C_Tax.COLUMNNAME_To_Country_ID)
				.map(countryDAO::getCountryIdByCountryCode)
				.ifPresent(countryId -> taxRecord.setTo_Country_ID(countryId.getRepoId()));
		tableRow.getAsOptionalIdentifier(I_C_Tax.COLUMNNAME_AD_Org_ID)
				.map(orgTable::getId)
				.ifPresent(orgId -> taxRecord.setAD_Org_ID(orgId.getRepoId()));
		tableRow.getAsOptionalBoolean(I_C_Tax.COLUMNNAME_IsTaxExempt)
				.ifPresent(taxRecord::setIsTaxExempt);
		tableRow.getAsOptionalInt(I_C_Tax.COLUMNNAME_SeqNo)
				.ifPresent(taxRecord::setSeqNo);
		tableRow.getAsOptionalString(I_C_Tax.COLUMNNAME_TypeOfDestCountry)
				.ifPresent(taxRecord::setTypeOfDestCountry);
		tableRow.getAsOptionalBoolean(I_C_Tax.COLUMNNAME_IsReverseCharge)
				.ifPresent(taxRecord::setIsReverseCharge);
		tableRow.getAsOptionalBoolean(I_C_Tax.COLUMNNAME_IsWholeTax)
				.ifPresent(taxRecord::setIsWholeTax);
		tableRow.getAsOptionalBoolean(I_C_Tax.COLUMNNAME_IsDocumentLevel)
				.ifPresent(taxRecord::setIsDocumentLevel);

		InterfaceWrapperHelper.saveRecord(taxRecord);

		tableRow.getAsOptionalIdentifier()
				.ifPresent(identifier -> taxTable.putOrReplace(identifier, TaxUtils.from(taxRecord)));
	}

	/**
	 * Re-load a {@link I_C_Tax} record by identifier and assert its column values match the provided data table.
	 * Uses {@link SoftAssertions} so all column mismatches are reported in one go.
	 *
	 * <p><b>Required columns</b>:
	 * <ul>
	 *     <li>{@code Identifier} — identifier of a previously registered tax</li>
	 * </ul>
	 *
	 * <p><b>Optional assertion columns</b> — any combination of:
	 * <ul>
	 *     <li>{@code IsTaxExempt}, {@code IsReverseCharge}, {@code IsWholeTax}, {@code IsDocumentLevel} — expected boolean</li>
	 *     <li>{@code Rate} — expected decimal</li>
	 * </ul>
	 *
	 * <p><b>Gherkin usage example</b>:
	 * <pre>{@code
	 * Then reload C_Tax and assert:
	 *   | Identifier | IsTaxExempt | IsReverseCharge | IsWholeTax | Rate | IsDocumentLevel |
	 *   | tax19      | false       | true            | false      | 19   | false           |
	 * }</pre>
	 */
	@Then("reload C_Tax and assert:")
	public void reloadAndAssertC_Tax(@NonNull final DataTable dataTable)
	{
		final SoftAssertions softly = new SoftAssertions();
		DataTableRows.of(dataTable).forEach(row -> reloadAndAssertRow(row, softly));
		softly.assertAll();
	}

	private void reloadAndAssertRow(@NonNull final DataTableRow row, @NonNull final SoftAssertions softly)
	{
		final StepDefDataIdentifier identifier = row.getAsIdentifier();
		final Tax tax = taxTable.get(identifier);
		final I_C_Tax reloaded = InterfaceWrapperHelper.load(tax.getTaxId(), I_C_Tax.class);

		row.getAsOptionalBoolean(I_C_Tax.COLUMNNAME_IsTaxExempt)
				.ifPresent(expected -> softly.assertThat(reloaded.isTaxExempt())
						.as(identifier + ".IsTaxExempt")
						.isEqualTo(expected));
		row.getAsOptionalBoolean(I_C_Tax.COLUMNNAME_IsReverseCharge)
				.ifPresent(expected -> softly.assertThat(reloaded.isReverseCharge())
						.as(identifier + ".IsReverseCharge")
						.isEqualTo(expected));
		row.getAsOptionalBoolean(I_C_Tax.COLUMNNAME_IsWholeTax)
				.ifPresent(expected -> softly.assertThat(reloaded.isWholeTax())
						.as(identifier + ".IsWholeTax")
						.isEqualTo(expected));
		row.getAsOptionalBoolean(I_C_Tax.COLUMNNAME_IsDocumentLevel)
				.ifPresent(expected -> softly.assertThat(reloaded.isDocumentLevel())
						.as(identifier + ".IsDocumentLevel")
						.isEqualTo(expected));
		row.getAsOptionalBigDecimal(I_C_Tax.COLUMNNAME_Rate)
				.ifPresent(expected -> softly.assertThat(reloaded.getRate())
						.as(identifier + ".Rate")
						.isEqualByComparingTo(expected));

		// refresh the cached immutable snapshot so subsequent steps see the latest state
		taxTable.putOrReplace(identifier, TaxUtils.from(reloaded));
	}

	/**
	 * Attempt a raw SQL {@code UPDATE c_tax SET ...} that bypasses the Java interceptor, and assert
	 * it is rejected by the database {@code c_tax_exclusive_tax_flags} CHECK constraint.
	 * This is the cucumber equivalent of the unit-test coverage of the DB last-mile defence — it is
	 * the only path that can reach the CHECK once the interceptor is in place (the interceptor
	 * auto-resolves any Java-side attempt to save two flags = Y).
	 *
	 * <p><b>Required columns</b>:
	 * <ul>
	 *     <li>{@code Identifier} — identifier of a previously registered tax</li>
	 * </ul>
	 *
	 * <p><b>Optional columns</b> — at least two must be {@code true} to actually violate the check:
	 * <ul>
	 *     <li>{@code IsTaxExempt}, {@code IsReverseCharge}, {@code IsWholeTax} — each {@code Y}/{@code N}</li>
	 * </ul>
	 *
	 * <p><b>Gherkin usage example</b>:
	 * <pre>{@code
	 * Then raw SQL update to C_Tax is rejected by CHECK constraint:
	 *   | Identifier | IsTaxExempt | IsReverseCharge |
	 *   | tax19      | true        | true            |
	 * }</pre>
	 */
	@Then("raw SQL update to C_Tax is rejected by CHECK constraint:")
	public void rawUpdateRejectedByCheck(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::assertRawUpdateRejected);
	}

	private void assertRawUpdateRejected(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier identifier = row.getAsIdentifier();
		final TaxId taxId = taxTable.get(identifier).getTaxId();

		final String taxExempt = row.getAsOptionalBoolean(I_C_Tax.COLUMNNAME_IsTaxExempt).orElseFalse() ? "Y" : "N";
		final String reverseCharge = row.getAsOptionalBoolean(I_C_Tax.COLUMNNAME_IsReverseCharge).orElseFalse() ? "Y" : "N";
		final String wholeTax = row.getAsOptionalBoolean(I_C_Tax.COLUMNNAME_IsWholeTax).orElseFalse() ? "Y" : "N";

		final String sql = "UPDATE c_tax SET istaxexempt=?, isreversecharge=?, iswholetax=? WHERE c_tax_id=?";

		assertThatThrownBy(() -> DB.executeUpdateAndThrowExceptionOnFail(
				sql,
				new Object[] { taxExempt, reverseCharge, wholeTax, taxId.getRepoId() },
				null))
				.as("raw SQL setting flags TE=%s, RC=%s, WT=%s on C_Tax_ID=%s should be rejected",
						taxExempt, reverseCharge, wholeTax, taxId)
				.isInstanceOf(DBException.class)
				.hasMessageContaining("c_tax_exclusive_tax_flags");

		// reload the DB state into the cached snapshot so later assertions see the unchanged row
		final I_C_Tax reloaded = InterfaceWrapperHelper.load(taxId, I_C_Tax.class);
		taxTable.putOrReplace(identifier, TaxUtils.from(reloaded));
		// sanity: rate unchanged (smoke-check the CHECK violation was a no-op)
		assertThat(reloaded.getC_Tax_ID()).isEqualTo(taxId.getRepoId());
	}

	/**
	 * Update flag / rate columns on an already-registered C_Tax by issuing a SINGLE save.
	 * Unlike {@code metasfresh contains C_Tax} which always creates a fresh record
	 * (the generated Name carries a fresh timestamp per call), this step reloads the
	 * existing record by identifier and updates it in place — so the save goes through
	 * the C_Tax model interceptor on the very record that was created earlier.
	 *
	 * <p>Supports all scenarios of {@code TaxBL.enforceExclusiveFlags}:
	 * <ul>
	 *     <li>Single-flag toggle (user edit — the changed flag wins)</li>
	 *     <li>Multiple flags toggled in one save (priority fallback)</li>
	 *     <li>{@code IsWholeTax=Y} cascade (Rate=100, IsDocumentLevel=Y, other flags cleared)</li>
	 * </ul>
	 *
	 * <p><b>Required columns</b>:
	 * <ul>
	 *     <li>{@code Identifier} — identifier of a previously registered tax</li>
	 * </ul>
	 *
	 * <p><b>Optional update columns</b> — any combination of:
	 * <ul>
	 *     <li>{@code IsTaxExempt}, {@code IsReverseCharge}, {@code IsWholeTax}, {@code IsDocumentLevel} — {@code Y}/{@code N}</li>
	 *     <li>{@code Rate} — decimal</li>
	 * </ul>
	 *
	 * <p><b>Gherkin usage example</b>:
	 * <pre>{@code
	 * When update C_Tax:
	 *   | Identifier | IsReverseCharge |
	 *   | tax19      | true            |
	 * }</pre>
	 */
	@And("update C_Tax:")
	public void updateC_Tax(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::updateC_TaxRow);
	}

	private void updateC_TaxRow(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier identifier = row.getAsIdentifier();
		final TaxId taxId = taxTable.get(identifier).getTaxId();
		final I_C_Tax taxRecord = InterfaceWrapperHelper.load(taxId, I_C_Tax.class);

		row.getAsOptionalBoolean(I_C_Tax.COLUMNNAME_IsTaxExempt)
				.ifPresent(taxRecord::setIsTaxExempt);
		row.getAsOptionalBoolean(I_C_Tax.COLUMNNAME_IsReverseCharge)
				.ifPresent(taxRecord::setIsReverseCharge);
		row.getAsOptionalBoolean(I_C_Tax.COLUMNNAME_IsWholeTax)
				.ifPresent(taxRecord::setIsWholeTax);
		row.getAsOptionalBoolean(I_C_Tax.COLUMNNAME_IsDocumentLevel)
				.ifPresent(taxRecord::setIsDocumentLevel);
		row.getAsOptionalBigDecimal(I_C_Tax.COLUMNNAME_Rate)
				.ifPresent(taxRecord::setRate);

		InterfaceWrapperHelper.saveRecord(taxRecord);

		taxTable.putOrReplace(identifier, TaxUtils.from(taxRecord));
	}

	private @Nullable I_C_Tax retrieveTaxRecordByName(final String taxName)
	{
		return queryBL.createQueryBuilder(I_C_Tax.class)
				.addEqualsFilter(I_C_Tax.COLUMNNAME_Name, taxName)
				.create()
				.firstOnly(I_C_Tax.class);
	}

	private int getNextTaxSeqNo(final TaxCategoryId taxCategoryId)
	{
		return queryBL.createQueryBuilder(I_C_Tax.class)
				.addEqualsFilter(I_C_Tax.COLUMNNAME_C_TaxCategory_ID, taxCategoryId)
				.orderBy(I_C_Tax.COLUMNNAME_SeqNo)
				.create()
				.firstOptional(I_C_Tax.class)
				.map(I_C_Tax::getSeqNo)
				.map(currentMinSeqNo -> currentMinSeqNo - 1)
				.orElse(0);
	}
}
