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

package de.metas.cucumber.stepdefs.productCategory;

import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSet_StepDefData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_Product_Category;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_M_Product_Category.COLUMNNAME_M_AttributeSet_ID;
import static org.compiere.model.I_M_Product_Category.COLUMNNAME_M_Product_Category_ID;

@RequiredArgsConstructor
public class M_Product_Category_StepDef
{
	@NonNull
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull
	private final M_Product_Category_StepDefData productCategoryTable;
	@NonNull
	private final M_AttributeSet_StepDefData attributeSetTable;

	@And("load M_Product_Category:")
	public void load_M_Product_Category(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_M_Product_Category_ID)
				.forEach(row -> {
					final String name = row.getAsString(I_M_Product_Category.COLUMNNAME_Name);
					final String value = row.getAsString(I_M_Product_Category.COLUMNNAME_Value);

					final I_M_Product_Category productCategory = queryBL.createQueryBuilder(I_M_Product_Category.class)
							.addEqualsFilter(I_M_Product_Category.COLUMNNAME_Name, name)
							.addEqualsFilter(I_M_Product_Category.COLUMNNAME_Value, value)
							.addOnlyActiveRecordsFilter()
							.create()
							.firstOnlyNotNull(I_M_Product_Category.class);
					assertThat(productCategory).as("Unable to load active M_ProductCategory with name=%s and value=%s", name, value).isNotNull();
					productCategoryTable.putOrReplace(row.getAsIdentifier(), productCategory);
				});
	}

	@And("update M_Product_Category:")
	public void update_M_Product_Category(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_M_Product_Category_ID)
				.forEach(row -> {
					final StepDefDataIdentifier identifier = row.getAsIdentifier();
					final I_M_Product_Category productCategory = identifier.lookupIn(productCategoryTable);
					assertThat(productCategory).as("Unable to load active M_ProductCategory with identifier=%s", identifier).isNotNull();

					row.getAsOptionalIdentifier(COLUMNNAME_M_AttributeSet_ID)
							.map(attributeSetTable::getId)
							.ifPresent(attributeSetId -> productCategory.setM_AttributeSet_ID(attributeSetId.getRepoId()));

					saveRecord(productCategory);
					productCategoryTable.putOrReplace(identifier, productCategory);
				});
	}
}
