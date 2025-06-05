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

import de.metas.common.util.CoalesceUtil;
import de.metas.contracts.model.I_ModCntr_BaseModuleConfig;
import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.contracts.model.I_ModCntr_Settings;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

@RequiredArgsConstructor
public class ModCntr_BaseModuleConfig_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final ModCntr_BaseModuleConfig_StepDefData modCntrBaseModuleConfigTable;
	private final ModCntr_Module_StepDefData modCntrModuleTable;
	private final ModCntr_Settings_StepDefData modCntrSettingsTable;

	@Given("metasfresh contains ModCntr_BaseModuleConfigs:")
	public void metasfresh_contains_modcntr_baseModuleConfigs(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createModCntrBaseModuleConfigs(tableRow);
		}
	}

	private void createModCntrBaseModuleConfigs(@NonNull final Map<String, String> tableRow)
	{
		final String name = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_BaseModuleConfig.COLUMNNAME_Name);
		final String modCntrSettingIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_BaseModuleConfig.COLUMNNAME_ModCntr_Settings_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_ModCntr_Settings modCntrSettingsRecord = modCntrSettingsTable.get(modCntrSettingIdentifier);

		final String modCntrModuleIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_BaseModuleConfig.COLUMNNAME_ModCntr_Module_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_ModCntr_Module modCntrModuleRecord = modCntrModuleTable.get(modCntrModuleIdentifier);

		final String modCntrBaseModuleIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_BaseModuleConfig.COLUMNNAME_ModCntr_BaseModule_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_ModCntr_Module modCntrBaseModuleRecord = modCntrModuleTable.get(modCntrBaseModuleIdentifier);

		final I_ModCntr_BaseModuleConfig modCntrBaseModuleConfigRecord = CoalesceUtil.coalesceSuppliers(
				() -> queryBL.createQueryBuilder(I_ModCntr_BaseModuleConfig.class)
						.addEqualsFilter(I_ModCntr_BaseModuleConfig.COLUMNNAME_ModCntr_Settings_ID, modCntrSettingsRecord.getModCntr_Settings_ID())
						.addEqualsFilter(I_ModCntr_BaseModuleConfig.COLUMNNAME_ModCntr_Module_ID, modCntrModuleRecord.getModCntr_Module_ID())
						.addEqualsFilter(I_ModCntr_BaseModuleConfig.COLUMNNAME_ModCntr_BaseModule_ID, modCntrBaseModuleRecord.getModCntr_Module_ID())
						.create()
						.firstOnlyOrNull(I_ModCntr_BaseModuleConfig.class),
				() -> InterfaceWrapperHelper.newInstance(I_ModCntr_BaseModuleConfig.class));

		Check.assumeNotNull(modCntrBaseModuleConfigRecord, "modCntrBaseModuleConfigRecord shouldn't be null");

		modCntrBaseModuleConfigRecord.setName(name);
		modCntrBaseModuleConfigRecord.setModCntr_Settings_ID(modCntrSettingsRecord.getModCntr_Settings_ID());
		modCntrBaseModuleConfigRecord.setModCntr_Module_ID(modCntrModuleRecord.getModCntr_Module_ID());
		modCntrBaseModuleConfigRecord.setModCntr_BaseModule_ID(modCntrBaseModuleRecord.getModCntr_Module_ID());

		InterfaceWrapperHelper.saveRecord(modCntrBaseModuleConfigRecord);

		final String modCntrBaseModuleConfigIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_BaseModuleConfig.COLUMNNAME_ModCntr_BaseModuleConfig_ID + "." + TABLECOLUMN_IDENTIFIER);
		modCntrBaseModuleConfigTable.putOrReplace(modCntrBaseModuleConfigIdentifier, modCntrBaseModuleConfigRecord);
	}
}
