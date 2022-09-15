/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNEcleanupSS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.cucumber.stepdefs;

import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_IsInvoiceable;
import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_Name;

public class M_HU_PackingMaterial_StepDef
{
	private final M_Product_StepDefData productTable;
	private final M_HU_PackingMaterial_StepDefData packingMaterialTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public M_HU_PackingMaterial_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final M_HU_PackingMaterial_StepDefData packingMaterialTable)
	{
		this.productTable = productTable;
		this.packingMaterialTable = packingMaterialTable;
	}

	@Given("metasfresh contains M_HU_PackingMaterial")
	public void metasfresh_contains_m_hu_packingmaterial(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createM_HU_PackingMaterial(tableRow);
		}
	}

	@And("load M_HU_PackingMaterial")
	public void load_M_HU_PackingMaterial(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			loadPackingMaterial(tableRow);
		}
	}

	private void loadPackingMaterial(@NonNull final Map<String, String> tableRow)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_HU_PackingMaterial.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final int productId = productTable.get(productIdentifier).getM_Product_ID();

		final I_M_HU_PackingMaterial packingMaterialRecord = queryBL.createQueryBuilder(I_M_HU_PackingMaterial.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_PackingMaterial.COLUMNNAME_M_Product_ID, productId)
				.create()
				.firstOnlyNotNull(I_M_HU_PackingMaterial.class);

		final String recordIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_HU_PackingMaterial.COLUMNNAME_M_HU_PackingMaterial_ID + "." + TABLECOLUMN_IDENTIFIER);
		packingMaterialTable.put(recordIdentifier, packingMaterialRecord);
	}

	private void createM_HU_PackingMaterial(@NonNull final Map<String, String> tableRow)
	{
		final String packingMaterialName = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_Name);
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_HU_PackingMaterial.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final boolean isInvoiceable = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + COLUMNNAME_IsInvoiceable, true);

		final int productId = productTable.get(productIdentifier).getM_Product_ID();

		final I_M_HU_PackingMaterial packingMaterialRecord = InterfaceWrapperHelper.newInstance(I_M_HU_PackingMaterial.class);
		packingMaterialRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		packingMaterialRecord.setName(packingMaterialName);
		packingMaterialRecord.setM_Product_ID(productId);
		packingMaterialRecord.setIsInvoiceable(isInvoiceable);

		InterfaceWrapperHelper.saveRecord(packingMaterialRecord);

		final String recordIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_HU_PackingMaterial.COLUMNNAME_M_HU_PackingMaterial_ID + "." + TABLECOLUMN_IDENTIFIER);
		packingMaterialTable.put(recordIdentifier, packingMaterialRecord);
	}
}
