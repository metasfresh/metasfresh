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

package de.metas.cucumber.stepdefs.resourceGroup;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.productCategory.M_Product_Category_StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.micrometer.core.lang.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.I_S_Resource_Group;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class S_Resource_Group_StepDef
{
	private final M_Product_Category_StepDefData productCategoryTable;

	public S_Resource_Group_StepDef(@NonNull final M_Product_Category_StepDefData productCategoryTable)
	{
		this.productCategoryTable = productCategoryTable;
	}

	@And("metasfresh contains S_Resource_Group with the following id:")
	public void create_S_Resource_Group(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createResourceGroup(tableRow);
		}
	}

	private void createResourceGroup(@NonNull final Map<String, String> row)
	{
		final int id = DataTableUtil.extractIntForColumnName(row, I_S_Resource_Group.COLUMNNAME_S_Resource_Group_ID);
		final String name = DataTableUtil.extractStringForColumnName(row, I_S_Resource_Group.COLUMNNAME_Name);
		final String durationUnit = DataTableUtil.extractStringForColumnName(row, I_S_Resource_Group.COLUMNNAME_DurationUnit);
		final String productCategoryIdentifier = DataTableUtil.extractStringForColumnName(row, I_S_Resource_Group.COLUMNNAME_M_Product_Category_ID + "." + TABLECOLUMN_IDENTIFIER);

		final Integer productCategoryId = productCategoryTable.getOptional(productCategoryIdentifier)
				.map(I_M_Product_Category::getM_Product_Category_ID)
				.orElseGet(() -> Integer.parseInt(productCategoryIdentifier));

		final I_S_Resource_Group resourceGroupRecord = InterfaceWrapperHelper.newInstance(I_S_Resource_Group.class);

		resourceGroupRecord.setS_Resource_Group_ID(id);
		resourceGroupRecord.setName(name);
		resourceGroupRecord.setDurationUnit(durationUnit);
		resourceGroupRecord.setM_Product_Category_ID(productCategoryId);

		saveRecord(resourceGroupRecord);
	}
}
