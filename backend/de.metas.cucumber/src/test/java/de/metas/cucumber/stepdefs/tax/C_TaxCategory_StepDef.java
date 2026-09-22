/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2025 metas GmbH
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

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.ValueAndName;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.Optionals;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_M_ProductPrice;

import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.DEFAULT_TaxCategory_InternalName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RequiredArgsConstructor
public class C_TaxCategory_StepDef
{
	/**
	 * The partial unique index shipped by {@code 5825720_sys_gh31985_TaxCategory_InternalName_unique.sql}:
	 * {@code ON C_TaxCategory (InternalName) WHERE IsActive='Y' AND InternalName IS NOT NULL}.
	 */
	private static final String IDX_InternalName_Unique = "C_TaxCategory_InternalName_Unique";

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final ITaxBL taxBL = Services.get(ITaxBL.class);
	@NonNull private final C_TaxCategory_StepDefData taxCategoryTable;
	@NonNull private final TestContext restTestContext;

	public Optional<TaxCategoryId> extractTaxCategoryId(@NonNull final DataTableRow row)
	{
		return extractTaxCategoryIdentifier(row).map(this::resolveTaxCategoryId);
	}

	@NonNull
	public TaxCategoryId extractTaxCategoryIdOrDefault(final DataTableRow row)
	{
		return extractTaxCategoryId(row).orElseGet(this::getDefaultTaxCategoryId);
	}

	private Optional<StepDefDataIdentifier> extractTaxCategoryIdentifier(final DataTableRow row)
	{
		return Optionals.firstPresentOfSuppliers(
				() -> row.getAsOptionalIdentifier(I_M_ProductPrice.COLUMNNAME_C_TaxCategory_ID + "." + I_C_TaxCategory.COLUMNNAME_InternalName),
				() -> row.getAsOptionalIdentifier(I_M_ProductPrice.COLUMNNAME_C_TaxCategory_ID)
		);
	}

	private TaxCategoryId resolveTaxCategoryId(final StepDefDataIdentifier identifier)
	{
		// Lookup into C_TaxCategory_StepDefData
		{
			final TaxCategoryId taxCategoryId = taxCategoryTable.getIdOptional(identifier).orElse(null);
			if (taxCategoryId != null)
			{
				return taxCategoryId;
			}
		}

		// Lookup by InternalName
		{
			final String internalName = identifier.getAsString();
			return taxBL.getTaxCategoryIdByInternalName(internalName)
					.orElseThrow(() -> new AdempiereException("Missing taxCategory for internalName=" + internalName));
		}
	}

	private TaxCategoryId getDefaultTaxCategoryId()
	{
		return taxBL.getTaxCategoryIdByInternalName(DEFAULT_TaxCategory_InternalName)
				.orElseThrow(() -> new AdempiereException("Missing default taxCategory for internalName=" + DEFAULT_TaxCategory_InternalName));
	}

	/**
	 * Create a {@link I_C_TaxCategory} per data-table row.
	 *
	 * <p><b>Required columns</b>:
	 * <ul>
	 *     <li>{@code Identifier} — unique per scenario; registers the category in {@link C_TaxCategory_StepDefData}</li>
	 * </ul>
	 *
	 * <p><b>Optional columns</b>:
	 * <ul>
	 *     <li>{@code Name}, {@code InternalName} — auto-generated via {@code suggestValueAndName} if absent.
	 *         Leaving them out is the safest choice: {@code C_TaxCategory.InternalName} is unique among active
	 *         records, so a hard-coded value collides with the row a previous run of the same scenario left behind</li>
	 *     <li>{@code IsActive} — {@code Y}/{@code N}/{@code true}/{@code false}; defaults to {@code Y}. An
	 *         inactive category is invisible both to tax determination and to the REST-API identifier lookup</li>
	 *     <li>{@code REST.Context.C_TaxCategory_ID} — name of a REST context variable to hold the new record's
	 *         {@code C_TaxCategory_ID}, for use as {@code @name@} in a later REST payload</li>
	 *     <li>{@code REST.Context.InternalName} — name of a REST context variable to hold the new record's
	 *         {@code InternalName}; needed to name an auto-generated category in a REST payload</li>
	 *     <li>{@code InternalName.Identifier} — identifier of an already-registered category whose (generated)
	 *         {@code InternalName} the new record shall carry. The way to re-use a name without hard-coding it;
	 *         only legal once the referenced category is inactive, see {@link #deactivateTaxCategories(DataTable)}.
	 *         Ignored when an explicit {@code InternalName} is given</li>
	 * </ul>
	 *
	 * <p><b>Gherkin usage example</b>:
	 * <pre>{@code
	 * And metasfresh contains C_TaxCategory
	 *   | Identifier  |
	 *   | taxCategory |
	 * }</pre>
	 */
	@And("metasfresh contains C_TaxCategory")
	public void createTaxCategories(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_Tax.COLUMNNAME_C_TaxCategory_ID)
				.forEach(this::createTaxCategory);
	}

	private void createTaxCategory(@NonNull final DataTableRow row)
	{
		final ValueAndName valueAndName = row.suggestValueAndName();
		final String name = valueAndName.getName();
		final String internalName = row.getAsOptionalString(I_C_TaxCategory.COLUMNNAME_InternalName)
				.orElseGet(() -> extractInternalNameOfReferencedTaxCategory(row).orElse(name));

		final I_C_TaxCategory taxCategoryRecord = InterfaceWrapperHelper.newInstance(I_C_TaxCategory.class);
		taxCategoryRecord.setName(name);
		taxCategoryRecord.setInternalName(internalName);
		row.getAsOptionalBoolean(I_C_TaxCategory.COLUMNNAME_IsActive)
				.ifPresent(taxCategoryRecord::setIsActive);
		InterfaceWrapperHelper.saveRecord(taxCategoryRecord);

		row.getAsOptionalIdentifier()
				.ifPresent(identifier -> taxCategoryTable.put(identifier, taxCategoryRecord));

		row.getAsOptionalIdentifier("REST.Context.C_TaxCategory_ID")
				.ifPresent(id -> restTestContext.setVariable(id.getAsString(), taxCategoryRecord.getC_TaxCategory_ID()));
		row.getAsOptionalIdentifier("REST.Context.InternalName")
				.ifPresent(id -> restTestContext.setVariable(id.getAsString(), taxCategoryRecord.getInternalName()));
	}

	@And("load C_TaxCategory:")
	public void loadTaxCategories(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::loadTaxCategory);
	}

	private void loadTaxCategory(@NonNull final DataTableRow row)
	{
		final I_C_TaxCategory taxCategoryRecord = queryBL.createQueryBuilder(I_C_TaxCategory.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_TaxCategory.COLUMNNAME_InternalName, row.getAsString(I_C_TaxCategory.COLUMNNAME_InternalName))
				.create()
				.firstOnly();

		taxCategoryTable.put(row.getAsIdentifier(I_C_TaxCategory.COLUMNNAME_C_TaxCategory_ID), taxCategoryRecord);
	}

	@NonNull
	private Optional<String> extractInternalNameOfReferencedTaxCategory(@NonNull final DataTableRow row)
	{
		return row.getAsOptionalIdentifier(I_C_TaxCategory.COLUMNNAME_InternalName + "." + StepDefDataIdentifier.SUFFIX)
				.map(taxCategoryTable::get)
				.map(I_C_TaxCategory::getInternalName);
	}

	/**
	 * Give an already existing {@link I_C_TaxCategory} a freshly generated, unique {@code InternalName}.
	 *
	 * <p>Meant for the system-seeded categories that ship without one — most notably
	 * {@code C_TaxCategory_ID=100} ({@code Tax_Not_Found_Category}, i.e. {@code TaxCategoryId.NOT_FOUND}) — which the
	 * REST-API's {@code int-} identifier form can only name once they carry an {@code InternalName}. The name is
	 * generated rather than taken from the data table, because it has to stay unique among active categories.
	 *
	 * <p><b>Required columns</b>:
	 * <ul>
	 *     <li>{@code C_TaxCategory_ID} — identifier of an already-registered category, or a plain
	 *         {@code C_TaxCategory_ID} to load the record by</li>
	 * </ul>
	 *
	 * <p><b>Optional columns</b>:
	 * <ul>
	 *     <li>{@code REST.Context.InternalName} — name of a REST context variable to hold the generated
	 *         {@code InternalName}, for use as {@code @name@} in a later REST payload</li>
	 * </ul>
	 *
	 * <p><b>Gherkin usage example</b>:
	 * <pre>{@code
	 * And C_TaxCategory is given a fresh InternalName:
	 *   | C_TaxCategory_ID | REST.Context.InternalName |
	 *   | 100              | catNotFoundInternalName   |
	 * }</pre>
	 */
	@And("C_TaxCategory is given a fresh InternalName:")
	public void setFreshInternalNames(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::setFreshInternalName);
	}

	private void setFreshInternalName(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier identifier = row.getAsIdentifier(I_C_TaxCategory.COLUMNNAME_C_TaxCategory_ID);
		final I_C_TaxCategory taxCategoryRecord = taxCategoryTable.getOptional(identifier)
				.orElseGet(() -> InterfaceWrapperHelper.load(identifier.getAsInt(), I_C_TaxCategory.class));

		taxCategoryRecord.setInternalName(ValueAndName.unique(taxCategoryRecord.getName()).getName());
		InterfaceWrapperHelper.saveRecord(taxCategoryRecord);
		taxCategoryTable.putOrReplace(identifier, taxCategoryRecord);

		row.getAsOptionalIdentifier("REST.Context.InternalName")
				.ifPresent(id -> restTestContext.setVariable(id.getAsString(), taxCategoryRecord.getInternalName()));
	}

	/**
	 * Assert that a second <i>active</i> {@link I_C_TaxCategory} cannot take an {@code InternalName} that an active
	 * category already holds, i.e. that the partial unique index {@code C_TaxCategory_InternalName_Unique} is in
	 * place. The attempted save goes through the ordinary {@code InterfaceWrapperHelper} path — there is no Java-side
	 * guard on this column, so a rejection can only come from the database.
	 *
	 * <p>The second record gets a freshly generated {@code Name}, so the table's other unique index
	 * ({@code c_taxcategory_name} on {@code (AD_Client_ID, Name)}) cannot be what rejects it.
	 *
	 * <p><b>Required columns</b>:
	 * <ul>
	 *     <li>{@code Identifier} — identifier of the already-registered <i>active</i> category whose
	 *         {@code InternalName} the second record shall try to take</li>
	 * </ul>
	 *
	 * <p><b>Gherkin usage example</b>:
	 * <pre>{@code
	 * Then a second active C_TaxCategory with the same InternalName is rejected:
	 *   | Identifier |
	 *   | cat        |
	 * }</pre>
	 *
	 * @see #deactivateTaxCategories(DataTable) for the counter-case: an inactive holder frees its InternalName
	 */
	@Then("a second active C_TaxCategory with the same InternalName is rejected:")
	public void duplicateActiveInternalNamesAreRejected(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::assertDuplicateActiveInternalNameIsRejected);
	}

	private void assertDuplicateActiveInternalNameIsRejected(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier identifier = row.getAsIdentifier();
		final I_C_TaxCategory existingRecord = taxCategoryTable.get(identifier);
		final String internalName = existingRecord.getInternalName();
		assertThat(internalName)
				.as("C_TaxCategory %s needs an InternalName for this assertion to mean anything", identifier)
				.isNotBlank();

		final I_C_TaxCategory duplicateRecord = InterfaceWrapperHelper.newInstance(I_C_TaxCategory.class);
		duplicateRecord.setName(ValueAndName.unique("duplicate").getName());
		duplicateRecord.setInternalName(internalName);
		duplicateRecord.setIsActive(true);

		assertThatThrownBy(() -> InterfaceWrapperHelper.saveRecord(duplicateRecord))
				.as("saving a second active C_TaxCategory with InternalName=%s should be rejected by %s",
						internalName, IDX_InternalName_Unique)
				.isInstanceOf(DBException.class)
				.hasMessageFindingMatch("(?i)" + IDX_InternalName_Unique);
	}

	/**
	 * Assert that {@code ITaxBL.getTaxCategoryIdByInternalName} resolves the given category's {@code InternalName}
	 * back to exactly that category. The lookup is a {@code firstOnlyOptional}, so it blows up rather than picking
	 * one if a second active category ever carried the same name — which is what makes this the assertion that the
	 * uniqueness guard buys the REST-API's {@code int-} identifier form a single, unambiguous result.
	 *
	 * <p><b>Required columns</b>:
	 * <ul>
	 *     <li>{@code Identifier} — identifier of an already-registered category that carries an {@code InternalName}</li>
	 * </ul>
	 *
	 * <p><b>Gherkin usage example</b>:
	 * <pre>{@code
	 * Then C_TaxCategory is found by its InternalName:
	 *   | Identifier |
	 *   | cat        |
	 * }</pre>
	 */
	@Then("C_TaxCategory is found by its InternalName:")
	public void taxCategoriesAreFoundByInternalName(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::assertFoundByInternalName);
	}

	private void assertFoundByInternalName(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier identifier = row.getAsIdentifier();
		final I_C_TaxCategory taxCategoryRecord = taxCategoryTable.get(identifier);
		final String internalName = taxCategoryRecord.getInternalName();

		assertThat(taxBL.getTaxCategoryIdByInternalName(internalName))
				.as("getTaxCategoryIdByInternalName(%s) should return exactly C_TaxCategory %s", internalName, identifier)
				.contains(TaxCategoryId.ofRepoId(taxCategoryRecord.getC_TaxCategory_ID()));
	}

	/**
	 * Deactivate an already-registered {@link I_C_TaxCategory}.
	 *
	 * <p>An inactive category is invisible to tax determination, to the REST-API identifier lookup, and to the
	 * partial unique index on {@code InternalName} — so deactivating a category hands its {@code InternalName} back
	 * for a new active category to take.
	 *
	 * <p><b>Required columns</b>:
	 * <ul>
	 *     <li>{@code Identifier} — identifier of an already-registered category</li>
	 * </ul>
	 *
	 * <p><b>Gherkin usage example</b>:
	 * <pre>{@code
	 * When C_TaxCategory is deactivated:
	 *   | Identifier |
	 *   | cat        |
	 * }</pre>
	 */
	@When("C_TaxCategory is deactivated:")
	public void deactivateTaxCategories(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::deactivateTaxCategory);
	}

	private void deactivateTaxCategory(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier identifier = row.getAsIdentifier();
		final I_C_TaxCategory taxCategoryRecord = taxCategoryTable.get(identifier);

		taxCategoryRecord.setIsActive(false);
		InterfaceWrapperHelper.saveRecord(taxCategoryRecord);
		taxCategoryTable.putOrReplace(identifier, taxCategoryRecord);
	}
}
