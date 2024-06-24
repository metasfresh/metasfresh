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

package de.metas.cucumber.stepdefs;

import de.metas.cucumber.stepdefs.hu.M_HU_PI_Item_StepDefData;
import de.metas.cucumber.stepdefs.hu.M_HU_PackingMaterial_StepDefData;
import de.metas.cucumber.stepdefs.hu.M_HU_StepDefData;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.util.Check;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class M_HU_Item_StepDef
{
	private final M_HU_StepDefData huTable;
	private final M_HU_PI_Item_StepDefData huPiItemTable;
	private final M_HU_PackingMaterial_StepDefData huPackingMaterialTable;

	public M_HU_Item_StepDef(
			@NonNull final M_HU_StepDefData huTable,
			@NonNull final M_HU_PI_Item_StepDefData huPiItemTable,
			@NonNull final M_HU_PackingMaterial_StepDefData huPackingMaterialTable)
	{
		this.huTable = huTable;
		this.huPiItemTable = huPiItemTable;
		this.huPackingMaterialTable = huPackingMaterialTable;
	}

	@And("metasfresh contains M_HU_Item:")
	public void create_M_HU_Item(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createHuItem(tableRow);
		}
	}

	private void createHuItem(@NonNull final Map<String, String> row)
	{
		final String huIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU_Item.COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);
		final int huId = huTable.get(huIdentifier).getM_HU_ID();

		final String huPiItemIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU_Item.COLUMNNAME_M_HU_PI_Item_ID + "." + TABLECOLUMN_IDENTIFIER);
		final int huPiItemId = huPiItemTable.get(huPiItemIdentifier).getM_HU_PI_Item_ID();

		final BigDecimal qty = DataTableUtil.extractBigDecimalForColumnName(row, I_M_HU_Item.COLUMNNAME_Qty);

		final String huPackingMaterialIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU_Item.COLUMNNAME_M_HU_PackingMaterial_ID + "." + TABLECOLUMN_IDENTIFIER);
		final int huPackingMaterialId = huPackingMaterialTable.get(huPackingMaterialIdentifier).getM_HU_PackingMaterial_ID();

		final I_M_HU_Item huItemRecord = InterfaceWrapperHelper.newInstance(I_M_HU_Item.class);
		huItemRecord.setM_HU_ID(huId);
		huItemRecord.setM_HU_Item_ID(huPiItemId);
		huItemRecord.setQty(qty);
		huItemRecord.setM_HU_PackingMaterial_ID(huPackingMaterialId);

		final String itemType = DataTableUtil.extractNullableStringForColumnName(row, "OPT." + I_M_HU_Item.COLUMNNAME_ItemType);
		if (Check.isNotBlank(itemType))
		{
			huItemRecord.setItemType(DataTableUtil.nullToken2Null(itemType));
		}

		saveRecord(huItemRecord);
	}
}
