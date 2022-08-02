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
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product;

import java.util.List;
import java.util.Map;

import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_IsInvoiceable;
import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_Name;

public class M_HU_PackingMaterial_StepDef
{
	private final M_Product_StepDefData productTable;
	private final StepDefData<I_M_HU_PackingMaterial> packingMaterial;

	public M_HU_PackingMaterial_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final StepDefData<I_M_HU_PackingMaterial> packingMaterial)
	{
		this.productTable = productTable;
		this.packingMaterial = packingMaterial;
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

	private void createM_HU_PackingMaterial(@NonNull final Map<String, String> tableRow)
	{
		final String packingMaterialName = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_Name);
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product.Table_Name + ".Identifier");
		final boolean isInvoiceable = DataTableUtil.extractBooleanForColumnName(tableRow, COLUMNNAME_IsInvoiceable);

		final I_M_Product product = productTable.get(productIdentifier);
		final I_M_HU_PackingMaterial productRecord = InterfaceWrapperHelper.newInstance(I_M_HU_PackingMaterial.class);
		productRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		productRecord.setName(packingMaterialName);
		productRecord.setM_Product_ID(product.getM_Product_ID());
		productRecord.setIsInvoiceable(isInvoiceable);

		InterfaceWrapperHelper.saveRecord(productRecord);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, "M_HU_PackingMaterial");
		packingMaterial.put(recordIdentifier, productRecord);
	}
}
