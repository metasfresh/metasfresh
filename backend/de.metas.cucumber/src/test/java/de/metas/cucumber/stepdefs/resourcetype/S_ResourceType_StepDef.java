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

package de.metas.cucumber.stepdefs.resourcetype;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.productCategory.M_Product_Category_StepDefData;
import de.metas.uom.IUOMDAO;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.I_S_ResourceType;

import java.sql.Timestamp;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.compiere.model.I_M_Product.COLUMNNAME_M_Product_Category_ID;

public class S_ResourceType_StepDef
{
	private final S_ResourceType_StepDefData resourceTypeTable;
	private final M_Product_Category_StepDefData productCategoryTable;

	private final IUOMDAO uomDao = Services.get(IUOMDAO.class);

	public S_ResourceType_StepDef(
			@NonNull final S_ResourceType_StepDefData resourceTypeTable,
			@NonNull final M_Product_Category_StepDefData productCategoryTable)
	{
		this.resourceTypeTable = resourceTypeTable;
		this.productCategoryTable = productCategoryTable;
	}

	@And("metasfresh contains S_ResourceType:")
	public void metasfresh_contains_S_ResourceType(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String name = DataTableUtil.extractStringForColumnName(row, I_S_ResourceType.COLUMNNAME_Name);
			final String value = DataTableUtil.extractStringForColumnName(row, I_S_ResourceType.COLUMNNAME_Value);
			final String productCategoryIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_Category_ID + "." + TABLECOLUMN_IDENTIFIER);
			final String uomCode = DataTableUtil.extractStringForColumnName(row, "UomCode");
			final boolean isTimeSlot = DataTableUtil.extractBooleanForColumnName(row, I_S_ResourceType.COLUMNNAME_IsTimeSlot);

			final I_S_ResourceType resourceType = InterfaceWrapperHelper.newInstance(I_S_ResourceType.class);

			resourceType.setName(name);
			resourceType.setValue(value);
			resourceType.setIsTimeSlot(isTimeSlot);
			resourceType.setTimeSlotStart(Timestamp.valueOf("2022-08-08 13:11:00"));
			resourceType.setTimeSlotEnd(Timestamp.valueOf("2022-08-08 20:11:00"));

			final I_C_UOM uom = uomDao.getByX12DE355(X12DE355.ofCode(uomCode));
			resourceType.setC_UOM_ID(uom.getC_UOM_ID());

			final I_M_Product_Category productCategory = productCategoryTable.get(productCategoryIdentifier);
			assertThat(productCategory).isNotNull();
			resourceType.setM_Product_Category_ID(productCategory.getM_Product_Category_ID());

			saveRecord(resourceType);

			final String resourceTypeIdentifier = DataTableUtil.extractStringForColumnName(row, I_S_ResourceType.COLUMNNAME_S_ResourceType_ID + "." + TABLECOLUMN_IDENTIFIER);
			resourceTypeTable.putOrReplace(resourceTypeIdentifier, resourceType);
		}
	}
}
