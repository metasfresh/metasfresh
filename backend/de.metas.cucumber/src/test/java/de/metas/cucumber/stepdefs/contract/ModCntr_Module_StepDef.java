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

package de.metas.cucumber.stepdefs.contract;

import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.contracts.model.I_ModCntr_Settings;
import de.metas.contracts.model.I_ModCntr_Type;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class ModCntr_Module_StepDef
{
	private final ModCntr_Module_StepDefData modCntrModuleTable;
	private final ModCntr_Settings_StepDefData modCntrSettingsTable;
	private final ModCntr_Type_StepDefData modCntrTypeTable;
	private final M_Product_StepDefData productTable;

	public ModCntr_Module_StepDef(
			@NonNull final ModCntr_Module_StepDefData modCntrModuleTable,
			@NonNull final ModCntr_Settings_StepDefData modCntrSettingsTable,
			@NonNull final ModCntr_Type_StepDefData modCntrTypeTable,
			@NonNull final M_Product_StepDefData productTable)
	{
		this.modCntrModuleTable = modCntrModuleTable;
		this.modCntrSettingsTable = modCntrSettingsTable;
		this.modCntrTypeTable = modCntrTypeTable;
		this.productTable = productTable;
	}

	@Given("metasfresh contains ModCntr_Modules:")
	public void metasfresh_contains_modcntr_modules(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createModCntrModules(tableRow);
		}
	}

	private void createModCntrModules(@NonNull final Map<String, String> tableRow)
	{
		final int seqNo = DataTableUtil.extractIntForColumnName(tableRow, I_ModCntr_Module.COLUMNNAME_SeqNo);
		final String name = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Module.COLUMNNAME_Name);
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Module.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);
		
		final String invoicingGroup = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Module.COLUMNNAME_InvoicingGroup);
		final String modCntrSettingIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Module.COLUMNNAME_ModCntr_Settings_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_ModCntr_Settings modCntrSettingsRecord = modCntrSettingsTable.get(modCntrSettingIdentifier); 
		
		final String modCntrTypeIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Module.COLUMNNAME_ModCntr_Type_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_ModCntr_Type modCntrTypeRecord = modCntrTypeTable.get(modCntrTypeIdentifier);
		
		final I_ModCntr_Module modCntrModuleRecord = InterfaceWrapperHelper.newInstance(I_ModCntr_Module.class);

		modCntrModuleRecord.setSeqNo(seqNo);
		modCntrModuleRecord.setName(name);
		modCntrModuleRecord.setM_Product_ID(productRecord.getM_Product_ID());
		modCntrModuleRecord.setInvoicingGroup(invoicingGroup);
		modCntrModuleRecord.setModCntr_Settings_ID(modCntrSettingsRecord.getModCntr_Settings_ID());
		modCntrModuleRecord.setModCntr_Type_ID(modCntrTypeRecord.getModCntr_Type_ID());

		InterfaceWrapperHelper.saveRecord(modCntrModuleRecord);

		final String modCntrModuleIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Module.COLUMNNAME_ModCntr_Module_ID + "." + TABLECOLUMN_IDENTIFIER);
		modCntrModuleTable.put(modCntrModuleIdentifier, modCntrModuleRecord);
	}
}
