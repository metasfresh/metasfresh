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

package de.metas.cucumber.stepdefs.pricing;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_TaxCategory;

@RequiredArgsConstructor
public class C_TaxCategory_StepDef
{
	private final C_TaxCategory_StepDefData taxCategoryTable;
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@And("load C_TaxCategory:")
	public void load_c_taxCategory(@NonNull final DataTable dataTable)
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
