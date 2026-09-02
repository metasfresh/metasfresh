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
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.HuPackingMaterialId;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;

import static de.metas.handlingunits.model.I_M_HU_PI_Item.COLUMNNAME_Included_HU_PI_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Item.COLUMNNAME_IsActive;
import static de.metas.handlingunits.model.I_M_HU_PI_Item.COLUMNNAME_ItemType;
import static de.metas.handlingunits.model.I_M_HU_PI_Item.COLUMNNAME_M_HU_PI_Item_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Item.COLUMNNAME_M_HU_PI_Version_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Item.COLUMNNAME_Qty;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
	public void add_M_HU_PI_Items(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_M_HU_PI_Item_ID)
				.forEach(row -> {
					final HuPackingInstructionsVersionId huPiVersionId = row.getAsIdentifier(COLUMNNAME_M_HU_PI_Version_ID).lookupNotNullIdIn(huPiVersionTable);

					final String itemType = row.getAsString(COLUMNNAME_ItemType); //dev-note: ITEMTYPE_AD_Reference_ID=540395;
					final boolean active = row.getAsOptionalBoolean(COLUMNNAME_IsActive).orElseTrue();
					final HuPackingInstructionsId includedHuPiId = row.getAsOptionalIdentifier(COLUMNNAME_Included_HU_PI_ID)
							.map(huPiTable::getIdOrParse)
							.orElse(null);
					final HuPackingMaterialId huPackingMaterialId = row.getAsOptionalIdentifier(I_M_HU_PI_Item.COLUMNNAME_M_HU_PackingMaterial_ID)
							.filter(StepDefDataIdentifier::isNotNullPlaceholder)
							.map(huPackingMaterialTable::getId)
							.orElse(null);

					final I_M_HU_PI_Item existingHuPiItem = queryBL.createQueryBuilder(I_M_HU_PI_Item.class)
							.addEqualsFilter(COLUMNNAME_M_HU_PI_Version_ID, huPiVersionId)
							.addEqualsFilter(COLUMNNAME_ItemType, itemType)
							.addEqualsFilter(COLUMNNAME_IsActive, active)
							.addEqualsFilter(COLUMNNAME_Included_HU_PI_ID, includedHuPiId)
							.addEqualsFilter(I_M_HU_PI_Item.COLUMNNAME_M_HU_PackingMaterial_ID, huPackingMaterialId)
							.create()
							.firstOnlyOrNull(I_M_HU_PI_Item.class);

					final I_M_HU_PI_Item huPiItemRecord = CoalesceUtil.coalesceSuppliersNotNull(
							() -> existingHuPiItem,
							() -> InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item.class));

					huPiItemRecord.setM_HU_PI_Version_ID(huPiVersionId.getRepoId());
					huPiItemRecord.setItemType(itemType);
					huPiItemRecord.setIsActive(active);
					huPiItemRecord.setIncluded_HU_PI_ID(HuPackingInstructionsId.toRepoId(includedHuPiId));
					huPiItemRecord.setM_HU_PackingMaterial_ID(HuPackingMaterialId.toRepoId(huPackingMaterialId));
					huPiItemRecord.setQty(row.getAsOptionalBigDecimal(COLUMNNAME_Qty).orElse(null));

					saveRecord(huPiItemRecord);

					row.getAsOptionalIdentifier().ifPresent(identifier -> huPiItemTable.put(identifier, huPiItemRecord));
				});
	}
}
