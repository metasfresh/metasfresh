/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs.hu;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.handlingunits.model.I_M_HU_PI_Item.COLUMNNAME_Included_HU_PI_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Item.COLUMNNAME_IsActive;
import static de.metas.handlingunits.model.I_M_HU_PI_Item.COLUMNNAME_ItemType;
import static de.metas.handlingunits.model.I_M_HU_PI_Item.COLUMNNAME_M_HU_PI_Item_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Item.COLUMNNAME_M_HU_PI_Version_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Item.COLUMNNAME_Qty;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class M_HU_PI_Item_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_HU_PI_StepDefData huPiTable;
	private final M_HU_PI_Version_StepDefData huPiVersionTable;
	private final M_HU_PI_Item_StepDefData huPiItemTable;
	private final M_HU_PackingMaterial_StepDefData huPackingMaterialTable;

	public M_HU_PI_Item_StepDef(
			@NonNull final M_HU_PI_StepDefData huPiTable,
			@NonNull final M_HU_PI_Version_StepDefData huPiVersionTable,
			@NonNull final M_HU_PI_Item_StepDefData huPiItemTable,
			@NonNull final M_HU_PackingMaterial_StepDefData huPackingMaterialTable)
	{
		this.huPiTable = huPiTable;
		this.huPiVersionTable = huPiVersionTable;
		this.huPiItemTable = huPiItemTable;
		this.huPackingMaterialTable = huPackingMaterialTable;
	}

	@And("metasfresh contains M_HU_PI_Item:")
	public void add_M_HU_PI_Item(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String huPiVersionIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_PI_Version_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_HU_PI_Version huPiVersion = huPiVersionTable.get(huPiVersionIdentifier);

			final BigDecimal qty = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_Qty);
			final String itemType = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_ItemType); //dev-note: ITEMTYPE_AD_Reference_ID=540395;
			final boolean active = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT" + COLUMNNAME_IsActive, true);

			final IQueryBuilder<I_M_HU_PI_Item> piItemQueryBuilder = queryBL.createQueryBuilder(I_M_HU_PI_Item.class)
					.addEqualsFilter(COLUMNNAME_M_HU_PI_Version_ID, huPiVersion.getM_HU_PI_Version_ID())
					.addEqualsFilter(COLUMNNAME_ItemType, itemType)
					.addEqualsFilter(COLUMNNAME_IsActive, active);

			final String includedHuPiIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_Included_HU_PI_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(includedHuPiIdentifier))
			{
				final I_M_HU_PI huPi = huPiTable.get(includedHuPiIdentifier);
				piItemQueryBuilder.addEqualsFilter(COLUMNNAME_Included_HU_PI_ID, huPi.getM_HU_PI_ID());
			}

			final I_M_HU_PI_Item existingHuPiItem = piItemQueryBuilder.create()
					.firstOnlyOrNull(I_M_HU_PI_Item.class);

			final I_M_HU_PI_Item huPiItemRecord = CoalesceUtil.coalesceSuppliers(() -> existingHuPiItem,
																				 () -> InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item.class));

			assertThat(huPiItemRecord).isNotNull();

			huPiItemRecord.setM_HU_PI_Version_ID(huPiVersion.getM_HU_PI_Version_ID());
			huPiItemRecord.setQty(qty);
			huPiItemRecord.setItemType(itemType);
			huPiItemRecord.setIsActive(active);

			if (Check.isNotBlank(includedHuPiIdentifier))
			{
				final I_M_HU_PI huPi = huPiTable.get(includedHuPiIdentifier);
				huPiItemRecord.setIncluded_HU_PI_ID(huPi.getM_HU_PI_ID());
			}

			final String huPackingMaterialIdentifier = DataTableUtil.extractNullableStringForColumnName(row, "OPT." + I_M_HU_PI_Item.COLUMNNAME_M_HU_PackingMaterial_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(huPackingMaterialIdentifier))
			{
				final int huPackingMaterialId = DataTableUtil.nullToken2Null(huPackingMaterialIdentifier) == null
						? -1
						: huPackingMaterialTable.get(huPackingMaterialIdentifier).getM_HU_PackingMaterial_ID();

				huPiItemRecord.setM_HU_PackingMaterial_ID(huPackingMaterialId);
			}

			saveRecord(huPiItemRecord);

			final String huPiItemIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_PI_Item_ID + "." + TABLECOLUMN_IDENTIFIER);
			huPiItemTable.put(huPiItemIdentifier, huPiItemRecord);
		}
	}
}
