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

import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_ModCntr_Settings;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.calendar.C_Calendar_StepDefData;
import de.metas.cucumber.stepdefs.calendar.C_Year_StepDefData;
import de.metas.cucumber.stepdefs.pricing.M_PricingSystem_StepDefData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Year;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class ModCntr_Settings_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_Product_StepDefData productTable;
	private final C_Calendar_StepDefData calendarTable;
	private final C_Year_StepDefData yearTable;
	private final ModCntr_Settings_StepDefData modCntrSettingsTable;
	private final M_PricingSystem_StepDefData pricingSysTable;

	public ModCntr_Settings_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_Calendar_StepDefData calendarTable,
			@NonNull final C_Year_StepDefData yearTable,
			@NonNull final ModCntr_Settings_StepDefData modCntrSettingsTable,
			@NonNull final M_PricingSystem_StepDefData pricingSysTable)
	{
		this.productTable = productTable;
		this.calendarTable = calendarTable;
		this.yearTable = yearTable;
		this.modCntrSettingsTable = modCntrSettingsTable;
		this.pricingSysTable = pricingSysTable;
	}

	@Given("metasfresh contains ModCntr_Settings:")
	public void metasfresh_contains_modcntr_settings(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createModCntrSettings(tableRow);
		}
	}

	private void createModCntrSettings(@NonNull final Map<String, String> tableRow)
	{
		final String name = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Settings.COLUMNNAME_Name);
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Settings.COLUMNNAME_M_Raw_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		final String calendarIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Settings.COLUMNNAME_C_Calendar_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Calendar calendarRecord = calendarTable.get(calendarIdentifier);

		final String yearIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Settings.COLUMNNAME_C_Year_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Year yearRecord = yearTable.get(yearIdentifier);

		final Boolean isSoTrx = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_ModCntr_Settings.COLUMNNAME_IsSOTrx, false);
		
		final I_ModCntr_Settings modCntrSettingsRecord = CoalesceUtil.coalesceSuppliers(
				() -> queryBL.createQueryBuilder(I_ModCntr_Settings.class)
						.addEqualsFilter(I_ModCntr_Settings.COLUMNNAME_M_Raw_Product_ID, productRecord.getM_Product_ID())
						.addEqualsFilter(I_ModCntr_Settings.COLUMNNAME_C_Calendar_ID, calendarRecord.getC_Calendar_ID())
						.addEqualsFilter(I_ModCntr_Settings.COLUMNNAME_C_Year_ID, yearRecord.getC_Year_ID())
						.addEqualsFilter(I_ModCntr_Settings.COLUMNNAME_IsSOTrx, isSoTrx)
						.create()
						.firstOnlyOrNull(I_ModCntr_Settings.class),
				() -> InterfaceWrapperHelper.newInstance(I_ModCntr_Settings.class));

		modCntrSettingsRecord.setName(name);
		modCntrSettingsRecord.setM_Raw_Product_ID(productRecord.getM_Product_ID());
		modCntrSettingsRecord.setC_Calendar_ID(calendarRecord.getC_Calendar_ID());
		modCntrSettingsRecord.setC_Year_ID(yearRecord.getC_Year_ID());
		modCntrSettingsRecord.setIsSOTrx(isSoTrx);
		modCntrSettingsRecord.setStorageCostStartDate(Timestamp.valueOf("2024-04-24 07:15:00"));

		final String pricingSystemIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Flatrate_Conditions.COLUMNNAME_M_PricingSystem_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(pricingSystemIdentifier))
		{
			final I_M_PricingSystem pricingSystem = pricingSysTable.get(pricingSystemIdentifier);
			modCntrSettingsRecord.setM_PricingSystem_ID(pricingSystem.getM_PricingSystem_ID());
		}

		InterfaceWrapperHelper.saveRecord(modCntrSettingsRecord);

		final String modCntrSettingsIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Settings.COLUMNNAME_ModCntr_Settings_ID + "." + TABLECOLUMN_IDENTIFIER);
		modCntrSettingsTable.putOrReplace(modCntrSettingsIdentifier, modCntrSettingsRecord);
	}
}
