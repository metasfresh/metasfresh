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

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSet_StepDefData;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_Product_Category;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_M_Product_Category.COLUMNNAME_M_AttributeSet_ID;
import static org.compiere.model.I_M_Product_Category.COLUMNNAME_M_Product_Category_ID;

public class M_Product_Category_StepDef
{
	private final M_Product_Category_StepDefData productCategoryTable;
	private final M_AttributeSet_StepDefData attributeSetTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public M_Product_Category_StepDef(
			@NonNull final M_Product_Category_StepDefData productCategoryTable,
			@NonNull final M_AttributeSet_StepDefData attributeSetTable)
	{
		this.productCategoryTable = productCategoryTable;
		this.attributeSetTable = attributeSetTable;
	}

	@And("load M_Product_Category:")
	public void load_M_Product_Category(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			final String name = DataTableUtil.extractStringForColumnName(row, I_M_Product_Category.COLUMNNAME_Name);
			final String value = DataTableUtil.extractStringForColumnName(row, I_M_Product_Category.COLUMNNAME_Value);

			final I_M_Product_Category productCategory = queryBL.createQueryBuilder(I_M_Product_Category.class)
					.addEqualsFilter(I_M_Product_Category.COLUMNNAME_Name, name)
					.addEqualsFilter(I_M_Product_Category.COLUMNNAME_Value, value)
					.addOnlyActiveRecordsFilter()
					.create()
					.firstOnly(I_M_Product_Category.class);

			assertThat(productCategory).isNotNull();

			final String productCategoryIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_Category_ID + "." + TABLECOLUMN_IDENTIFIER);
			productCategoryTable.putOrReplace(productCategoryIdentifier, productCategory);
		}
	}

	@And("update M_Product_Category:")
	public void update_M_Product_Category(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String productCategoryIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_Product_Category_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Product_Category productCategory = productCategoryTable.get(productCategoryIdentifier);

			assertThat(productCategory).isNotNull();

			final String attributeSetIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_M_AttributeSet_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(attributeSetIdentifier))
			{
				final I_M_AttributeSet attributeSet = attributeSetTable.get(attributeSetIdentifier);
				assertThat(attributeSet).isNotNull();

				productCategory.setM_AttributeSet_ID(attributeSet.getM_AttributeSet_ID());
			}

			saveRecord(productCategory);
			productCategoryTable.putOrReplace(productCategoryIdentifier, productCategory);
		}
	}

	@Given("metasfresh contains M_Product_Categories:")
	public void create_M_Product_Categories(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String name = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product_Category.COLUMNNAME_Name);
			final String value = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product_Category.COLUMNNAME_Value);

			final I_M_Product_Category productCategoryRecord =
					CoalesceUtil.coalesceSuppliersNotNull(
							() -> queryBL.createQueryBuilder(I_M_Product_Category.class)
									.addEqualsFilter(I_M_Product_Category.COLUMNNAME_Value, value)
									.create()
									.firstOnly(I_M_Product_Category.class),
							() -> InterfaceWrapperHelper.newInstance(I_M_Product_Category.class)
					);

			productCategoryRecord.setIsActive(true);
			productCategoryRecord.setName(name);
			productCategoryRecord.setValue(value);
			InterfaceWrapperHelper.saveRecord(productCategoryRecord);
			
			final String productCategoryIdentifier = DataTableUtil.extractStringForColumnName(tableRow, TABLECOLUMN_IDENTIFIER);
			productCategoryTable.putOrReplace(productCategoryIdentifier, productCategoryRecord);
		}
	}
}
