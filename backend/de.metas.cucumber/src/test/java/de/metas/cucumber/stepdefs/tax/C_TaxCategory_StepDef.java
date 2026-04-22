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
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.Optionals;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_M_ProductPrice;

import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.DEFAULT_TaxCategory_InternalName;

@RequiredArgsConstructor
public class C_TaxCategory_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final ITaxBL taxBL = Services.get(ITaxBL.class);
	@NonNull private final C_TaxCategory_StepDefData taxCategoryTable;

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
	 *     <li>{@code Name}, {@code InternalName} — auto-generated via {@code suggestValueAndName} if absent</li>
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
		final String internalName = row.getAsOptionalString(I_C_TaxCategory.COLUMNNAME_InternalName).orElse(name);

		final I_C_TaxCategory taxCategoryRecord = InterfaceWrapperHelper.newInstance(I_C_TaxCategory.class);
		taxCategoryRecord.setName(name);
		taxCategoryRecord.setInternalName(internalName);
		InterfaceWrapperHelper.saveRecord(taxCategoryRecord);

		row.getAsOptionalIdentifier()
				.ifPresent(identifier -> taxCategoryTable.put(identifier, taxCategoryRecord));
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
}
